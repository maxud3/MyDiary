package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import net.danlew.android.joda.JodaTimeAndroid;
import org.joda.time.LocalTime;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.SugarInBloodFragment.*;

public class ShortInsulinFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.ShortInsulinFragment";
    private static final int DIGITS = 1;
    private OnShortInsulinFragmentListener mListener;
    private SharedPreferences settings;

    @BindView(R.id.number_picker)
    NumberPicker integerPicker;
    @BindView(R.id.number_picker_2)
    NumberPicker fractionPicker;
    private Unbinder unbinder;
    private float currentCoefficient;
    private float totalInsulin;
    private String valueIntegerPicker;
    private String valueFractionPicker;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getActivity()
                .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        selectCurrentCoefficient();

        Log.d(MainActivity.TAG, "onCreate: ShortInsulinFragment");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_short_insulin, container, false);
        unbinder = ButterKnife.bind(this, view);

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
        String totalShortInsulinDose = String.valueOf(roundUp(totalInsulin, DIGITS));
        SharedPreferences.Editor prefEditor =settings.edit();
        String SHORT_INSULIN_DOSE = "shortInsulinDose";
        prefEditor.putString(SHORT_INSULIN_DOSE, totalShortInsulinDose);
        prefEditor.apply();
    }
    //устанавливаем итоговую дозу инсулина
    private void setTotalInsulinNumberPicker(float totalInsulin){
        if (totalInsulin < 0.0f){
            valueIntegerPicker = getString(R.string.zero);
            valueFractionPicker = getString(R.string.zero);
        }else {
            String s = String.valueOf(roundUp(totalInsulin, DIGITS));
            String[] arrSplit = s.split("\\.");
            for (int i = 0; i < arrSplit.length; i++) {
                valueIntegerPicker = arrSplit[0];
                valueFractionPicker = arrSplit[1];
            }
        }
    }

    //вычисляем итоговую дозу инсулина
    @SuppressWarnings("ConstantConditions")
    private void calculateTotalInsulin(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean switchNoMeasuringState = prefs.getBoolean(SWITCH_NO_MEASURING, false);
        boolean switchCompensationInsulin = prefs.getBoolean(MySettingFragment.
                SWITCH_COMPENSATION_INSULIN, false);

        if (switchNoMeasuringState || !switchCompensationInsulin){
            totalInsulin = calculateInsulinEat(currentCoefficient);
        }else {
            float insulinEat = calculateInsulinEat(currentCoefficient);
            float compensationInsulin =  calculateCompensationInsulin();
            totalInsulin = insulinEat + compensationInsulin;

        }
    }

    //Расчитываем инсулин на еду по коэффициенту на текущее время
    @SuppressWarnings("ConstantConditions")
    private float calculateInsulinEat (float currentCoefficient){
        float defaultCoefficient = Float.parseFloat(getString(R.string.default_coefficient));
        if (currentCoefficient > defaultCoefficient) {
            SharedPreferences prefs = getActivity()
                    .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
            float breadUnits = Float.parseFloat(prefs.getString(MenuFragment.BREAD_UNITS, ""));
            return breadUnits * currentCoefficient;
        } else {
            Toast toast = Toast.makeText(
                    getActivity(), R.string.dose_not_calculated, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return defaultCoefficient;
        }
    }

    @SuppressWarnings("ConstantConditions")
    private float calculateCompensationInsulin(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean switchState = prefs.getBoolean(MySettingFragment
                .SWITCH_COMPENSATION_INSULIN, false);
        if (!switchState){
            return 0.0f;
        }else {
            float sugarInBlood = Float.parseFloat(prefs.getString(SUGAR_IN_BLOOD, ""));
            float targetGlucose = Float.parseFloat(prefs.getString(MySettingFragment
                    .TARGET_GLUCOSE, ""));
            float sensitivityCoefficient = Float.parseFloat(prefs.getString(MySettingFragment
                    .SENSITIVITY_COEFFICIENT, ""));
            return (sugarInBlood - targetGlucose) /sensitivityCoefficient;
        }
    }

    //Получаем коэффициент по текущему времени
    @SuppressWarnings("ConstantConditions")
    private void selectCurrentCoefficient(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

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
            currentCoefficient = Float.parseFloat(prefs.getString(MySettingFragment
                    .MORNING_COEFFICIENT, getString(R.string.default_coefficient)));
            Toast.makeText(getActivity(), "MorningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(MainActivity.TAG, "MorningTime now " + now);
        }else if (now.isAfter(afterDayTime) && now.isBefore(beforeDayTime)){
            currentCoefficient = Float.parseFloat(prefs.getString(MySettingFragment
                    .DAY_COEFFICIENT, getString(R.string.default_coefficient)));
            Toast.makeText(getActivity(), "DayTime " + now, Toast.LENGTH_LONG).show();
            Log.d(MainActivity.TAG, "DayTime " + now);
        }else if (now.isAfter(afterEveningTime) && now.isBefore(beforeEveningTime)){
            currentCoefficient = Float.parseFloat(prefs.getString(MySettingFragment
                    .EVENING_COEFFICIENT, getString(R.string.default_coefficient)));
            Toast.makeText(getActivity(), "EveningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(MainActivity.TAG, "EveningTime " + now);
        }else {
            currentCoefficient = Float.parseFloat(prefs.getString(MySettingFragment
                    .NIGHT_COEFFICIENT, getString(R.string.default_coefficient)));
            Toast.makeText(getActivity(), "NightTime " + now, Toast.LENGTH_LONG).show();
            Log.d(MainActivity.TAG, "NightTime " + now);
        }
    }

    @OnClick({R.id.view_click_long_insulin, R.id.btn_further})
    void onClick(View view){
        switch (view.getId()){
            case R.id.view_click_long_insulin:
                mListener.openLongInsulinFragment();
                break;
            case R.id.btn_further:
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
        saveShortInsulinDose();
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
