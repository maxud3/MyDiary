package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import net.danlew.android.joda.JodaTimeAndroid;
import org.joda.time.LocalTime;
import java.math.BigDecimal;
import androidx.constraintlayout.widget.Group;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.SettingConstants.*;


public class ShortInsulinFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.ShortInsulinFragment";
    private static final int DIGITS = 1;
    private OnShortInsulinFragmentListener mListener;
    private SharedPreferences settings;

    @BindView(R.id.number_picker)
    NumberPicker integerPicker;
    @BindView(R.id.number_picker_2)
    NumberPicker fractionPicker;
    @BindView(R.id.image_view_info)
    ImageView imageViewInfo;
    @BindView(R.id.group_formula_insulin_for_food)
    Group groupFormulaInsulinForFood;
    @BindView(R.id.text_view_formula_insulin_for_food)
    TextView tvFormulaInsulinForFood;
    @BindView(R.id.formula_compensation)
    TextView tvFormulaCompensation;
    @BindView(R.id.tvTotalInsulin)
    TextView tvTotalInsulin;


    private Unbinder unbinder;
    private float currentCoefficient;
    private float totalInsulin;
    private float compensationInsulin;
    private float topLine;
    private float bottomLine;
    private float insulinForFood;
    private String valueIntegerPicker;
    private String valueFractionPicker;
    private String breadUnits;
    private String sugarInBlood;
    private String targetGlucose;
    private String sensitivityCoefficient;
    private String morningCoefficient;
    private String dayCoefficient;
    private String eveningCoefficient;
    private String nightCoefficient;
    private boolean switchCompensationInsulin;
    private boolean switchNoMeasuringState;
    private boolean visibleGroup = false;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Log.d(MainActivity.TAG, "onCreate: ShortInsulinFragment");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_short_insulin, container, false);
        unbinder = ButterKnife.bind(this, view);
        getSavedStringsParameters();
        getSavedBooleanParameters();
        selectCurrentCoefficient();
        calculateInsulinEat(currentCoefficient);
        calculateCompensationInsulin();
        calculateTotalInsulin();
        setTotalInsulinNumberPicker(totalInsulin);
        setFormulaInsulinForFood();
        setFormulaCompensation();
        setFormulaTotalInsulin();
        integerPicker.setMaxValue(9);
        integerPicker.setMinValue(0);
        integerPicker.setValue(Integer.parseInt(valueIntegerPicker));
        fractionPicker.setMaxValue(9);
        fractionPicker.setMinValue(0);
        fractionPicker.setValue(Integer.parseInt(valueFractionPicker));

        Log.d(MainActivity.TAG, "onCreateView: ShortInsulinFragment");
        return view;
    }

    //Сохраняем дозу короткого инсулина
    private void saveShortInsulinDose(){
        int i1 = fractionPicker.getValue();
        int i = integerPicker.getValue();
        String totalShortInsulinDose = i +"." + i1;
        SharedPreferences.Editor prefEditor =settings.edit();
        prefEditor.putString(SHORT_INSULIN_DOSE, totalShortInsulinDose);
        prefEditor.apply();
    }
    private void setTotalInsulinNumberPicker(float totalInsulin){
        //устанавливаем итоговую дозу инсулина
        if (totalInsulin < 0.0f){
            valueIntegerPicker = getString(R.string.zero);
            valueFractionPicker = getString(R.string.zero);
        }else if (switchNoMeasuringState){
            valueIntegerPicker = getString(R.string.zero);
            valueFractionPicker = getString(R.string.zero);
            Toast toast = Toast.makeText(
                    getActivity(), R.string.dose_not_calculated_no_sugar, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else{
            String s = String.valueOf(roundUp(totalInsulin, DIGITS));
            String[] arrSplit = s.split("\\.");
            for (int i = 0; i < arrSplit.length; i++) {
                valueIntegerPicker = arrSplit[0];
                valueFractionPicker = arrSplit[1];
            }
        }
    }

    //вычисляем итоговую дозу инсулина
    private void calculateTotalInsulin(){
        if (switchNoMeasuringState){
            totalInsulin = insulinForFood;
        }else {
            totalInsulin = insulinForFood + compensationInsulin;
        }
    }

    private void setFormulaTotalInsulin(){
        String s = String.valueOf(roundUp(insulinForFood, 1));
        String s1 = String.valueOf(roundUp(compensationInsulin, 1));
        String s2 = String.valueOf(roundUp(totalInsulin, 1));
        tvTotalInsulin.setText(getString(R.string.total_insulin, s, s1, s2));
    }
    //Расчитываем инсулин на еду по коэффициенту на текущее время
    private void calculateInsulinEat (float currentCoefficient){
        float defaultCoefficient = Float.parseFloat(getString(R.string.default_coefficient));
        if (currentCoefficient > defaultCoefficient) {
            float f = Float.parseFloat(breadUnits);
            insulinForFood =  f * currentCoefficient;
            setFormulaInsulinForFood();
        } else {
            Toast toast = Toast.makeText(
                    getActivity(), R.string.dose_not_calculated, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            insulinForFood =  defaultCoefficient;
        }
    }
    //Вычисляем компенсацию
    private void calculateCompensationInsulin(){
        float sgrInBlood = Float.parseFloat(sugarInBlood);
        float trgGlucose = Float.parseFloat(targetGlucose);
        float sensCoefficient = Float.parseFloat(sensitivityCoefficient);
        if (!switchCompensationInsulin){
            compensationInsulin =  0.0f;
        }else if (sgrInBlood >= bottomLine && sgrInBlood <= topLine){
            compensationInsulin =  0.0f;
        } else if (switchNoMeasuringState){
          compensationInsulin = 0.0f;
        } else {
            compensationInsulin =  (sgrInBlood - trgGlucose) /sensCoefficient;
        }
    }
    private void setFormulaCompensation(){
        float sgrInBlood = Float.parseFloat(sugarInBlood);
        if(!switchCompensationInsulin){
            tvFormulaCompensation.setText(R.string.compensation_off);
        }else if (switchNoMeasuringState){
            tvFormulaCompensation.setText(R.string.compensation_no_sugar);
        }else if (sgrInBlood >= bottomLine && sgrInBlood <= topLine){
            tvFormulaCompensation.setText(R.string.no_compensation_required);
        }else {
            String s = String.valueOf(roundUp(compensationInsulin, 1));
            tvFormulaCompensation.setText(getString(R.string
                    .formula_compensation, sugarInBlood, targetGlucose, sensitivityCoefficient, s));
        }
    }

    //Получаем коэффициент по текущему времени
    private void selectCurrentCoefficient(){
        // Текущее время
        JodaTimeAndroid.init(getActivity());
        LocalTime now = LocalTime.now();

        LocalTime afterMorningTime = LocalTime.parse("06:00");//заданное время
        LocalTime beforeMorningTime = LocalTime.parse("11:00");

        LocalTime afterDayTime = LocalTime.parse("12:00");//заданное время
        LocalTime beforeDayTime = LocalTime.parse("17:00");

        LocalTime afterEveningTime = LocalTime.parse("18:00");//заданное время
        LocalTime beforeEveningTime = LocalTime.parse("23:00");

        if (now.isAfter(afterMorningTime) && now.isBefore(beforeMorningTime)){
            currentCoefficient = Float.parseFloat(morningCoefficient);
        }else if (now.isAfter(afterDayTime) && now.isBefore(beforeDayTime)){
            currentCoefficient = Float.parseFloat(dayCoefficient);
        }else if (now.isAfter(afterEveningTime) && now.isBefore(beforeEveningTime)){
            currentCoefficient = Float.parseFloat(eveningCoefficient);
        }else {
            currentCoefficient = Float.parseFloat(nightCoefficient);
        }
    }

    private void setFormulaInsulinForFood(){
        String currCoefficient = String.valueOf(currentCoefficient);
        String insForFood = String.valueOf(roundUp(insulinForFood, 1));
        tvFormulaInsulinForFood
                .setText(getString(R.string
                        .formula_insulin_for_food, breadUnits, currCoefficient, insForFood));
    }
    @OnClick({R.id.view_click_long_insulin, R.id.btn_further, R.id.image_view_info})
    void onClick(View view){
        switch (view.getId()){
            case R.id.image_view_info:
                if (!visibleGroup){
                    visibleGroup = true;
                    groupFormulaInsulinForFood.setVisibility(View.VISIBLE);
                    //setFormulaInsulinForFood();
                }else {
                    visibleGroup = false;
                    groupFormulaInsulinForFood.setVisibility(View.GONE);
                }
                break;
            case R.id.view_click_long_insulin:
                saveShortInsulinDose();
                mListener.openLongInsulinFragment();
                break;
            case R.id.btn_further:
                saveShortInsulinDose();
                mListener.openTotalRecordFragment();
                break;
        }
    }

    public interface OnShortInsulinFragmentListener{
        void openLongInsulinFragment();
        void openTotalRecordFragment();
    }

    //Получаем boolean сохраненные параметры
    private void getSavedBooleanParameters(){
        switchNoMeasuringState = settings
                .getBoolean(SWITCH_NO_MEASURING, false);
        switchCompensationInsulin = settings
                .getBoolean(SWITCH_COMPENSATION_INSULIN, false);
    }
    //Получаем строковые сохраненные параметры
    private void getSavedStringsParameters(){
        morningCoefficient = settings
                .getString(MORNING_COEFFICIENT, getString(R.string.zero_zero));
        dayCoefficient = settings
                .getString(DAY_COEFFICIENT, getString(R.string.zero_zero));
        eveningCoefficient = settings
                .getString(EVENING_COEFFICIENT, getString(R.string.zero_zero));
        nightCoefficient = settings
                .getString(NIGHT_COEFFICIENT, getString(R.string.zero_zero));
        sugarInBlood = settings
                .getString(SUGAR_IN_BLOOD, getString(R.string.zero_zero));
        targetGlucose = settings
                .getString(TARGET_GLUCOSE, getString(R.string.zero_zero));
        topLine = Float.parseFloat(settings
                .getString(TOP_LINE, getString(R.string.zero_zero)));
        bottomLine = Float.parseFloat(settings
                .getString(BOTTOM_LINE, getString(R.string.zero_zero)));
        sensitivityCoefficient = settings
                .getString(SENSITIVITY_COEFFICIENT, getString(R.string.zero_zero));
        breadUnits = settings
                .getString(BREAD_UNITS, "0.0");
    }
    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        Log.d(MainActivity.TAG, "onAttach: ShortInsulinFragment");
        if (context instanceof OnShortInsulinFragmentListener){
            mListener = (OnShortInsulinFragmentListener) context;
        } else {
            throw  new RuntimeException(context.toString()
                    + "must implement OnFragment1DataListener");
        }
    }

    static BigDecimal roundUp(float value, int digits){
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        Log.d(MainActivity.TAG, "onActivityCreated: ShortInsulinFragment");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(MainActivity.TAG, "onStart: ShortInsulinFragment");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(MainActivity.TAG, "onResume: ShortInsulinFragment");
    }
    @Override
    public void onPause(){
        super.onPause();
        //saveShortInsulinDose();
        Log.d(MainActivity.TAG, "onPause: ShortInsulinFragment");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(MainActivity.TAG, "onStop: ShortInsulinFragment");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(MainActivity.TAG, "onDestroyView: ShortInsulinFragment");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(MainActivity.TAG, "onDestroy: ShortInsulinFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(MainActivity.TAG, "onDetach: ShortInsulinFragment");
    }
}
