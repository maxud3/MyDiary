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
    private Unbinder unbinder;
    private float currentCoefficient;
    private float totalInsulin;
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
    //Group group;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getActivity()
                .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);



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
        calculateTotalInsulin();
        setTotalInsulinNumberPicker(totalInsulin);
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
        int i = integerPicker.getValue();
        int i1 = fractionPicker.getValue();
        String totalShortInsulinDose = i +"." + i1;
        SharedPreferences.Editor prefEditor =settings.edit();
        final String SHORT_INSULIN_DOSE = "shortInsulinDose";
        prefEditor.putString(SHORT_INSULIN_DOSE, totalShortInsulinDose);
        prefEditor.apply();
    }
    //устанавливаем итоговую дозу инсулина
    private void setTotalInsulinNumberPicker(float totalInsulin){
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
        if (switchNoMeasuringState || !switchCompensationInsulin){
            totalInsulin = calculateInsulinEat(currentCoefficient);
        }else {
            float insulinEat = calculateInsulinEat(currentCoefficient);
            float compensationInsulin =  calculateCompensationInsulin();
            totalInsulin = insulinEat + compensationInsulin;
        }
    }
    //Получаем boolean сохраненные параметры
    private void getSavedBooleanParameters(){
        switchNoMeasuringState = settings
                .getBoolean(SugarInBloodFragment.SWITCH_NO_MEASURING, false);
        switchCompensationInsulin = settings
                .getBoolean(MySettingFragment.SWITCH_COMPENSATION_INSULIN, false);
    }
    //Получаем строковые сохраненные параметры
    private void getSavedStringsParameters(){
        morningCoefficient = settings
                .getString(MySettingFragment.MORNING_COEFFICIENT, getString(R.string.zero_zero));
        dayCoefficient = settings
                .getString(MySettingFragment.DAY_COEFFICIENT, getString(R.string.zero_zero));
        eveningCoefficient = settings
                .getString(MySettingFragment.EVENING_COEFFICIENT, getString(R.string.zero_zero));
        nightCoefficient = settings
                .getString(MySettingFragment.NIGHT_COEFFICIENT, getString(R.string.zero_zero));
        sugarInBlood = settings
                .getString(SugarInBloodFragment.SUGAR_IN_BLOOD, getString(R.string.zero_zero));
        targetGlucose = settings
                .getString(MySettingFragment.TARGET_GLUCOSE, getString(R.string.zero_zero));
        sensitivityCoefficient = settings
                .getString(MySettingFragment.SENSITIVITY_COEFFICIENT, getString(R.string.zero_zero));
        breadUnits = settings
                .getString(BreadUnitsFragment.BREAD_UNITS, "0.0");
    }
    //Расчитываем инсулин на еду по коэффициенту на текущее время
    private float calculateInsulinEat (float currentCoefficient){
        float defaultCoefficient = Float.parseFloat(getString(R.string.default_coefficient));
        if (currentCoefficient > defaultCoefficient) {
            float f = Float.parseFloat(breadUnits);
            return f * currentCoefficient;
        } else {
            Toast toast = Toast.makeText(
                    getActivity(), R.string.dose_not_calculated, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return defaultCoefficient;
        }
    }
    //Вычисляем компенсацию
    private float calculateCompensationInsulin(){
        if (!switchCompensationInsulin){
            return 0.0f;
        }else {
            float sgrInBlood = Float.parseFloat(sugarInBlood);
            float trgGlucose = Float.parseFloat(targetGlucose);
            float sensCoefficient = Float.parseFloat(sensitivityCoefficient);
            return (sgrInBlood - trgGlucose) /sensCoefficient;
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

    @OnClick({R.id.view_click_long_insulin, R.id.btn_further, R.id.image_view_info})
    void onClick(View view){
        switch (view.getId()){
            case R.id.image_view_info:
                groupFormulaInsulinForFood.setVisibility(View.VISIBLE);
                String s = String.valueOf(currentCoefficient);
                String s1 = String.valueOf(roundUp(totalInsulin, DIGITS));
                tvFormulaInsulinForFood
                        .setText(getString(R.string.formula_insulin_for_food, breadUnits, s, s1));
                /*FormulaDialogFragment dialog = new FormulaDialogFragment();
                dialog.show(Objects.requireNonNull(getActivity())
                        .getSupportFragmentManager(), "custom");*/
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
