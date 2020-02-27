package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

//import static com.maksimohotnikov.mydiary.CoefficientFragment.APP_PREFERENCES;
//import static com.maksimohotnikov.mydiary.CoefficientFragment.DAY_COEFFICIENT;
//import static com.maksimohotnikov.mydiary.CoefficientFragment.EVENING_COEFFICIENT;
//import static com.maksimohotnikov.mydiary.CoefficientFragment.MORNING_COEFFICIENT;
//import static com.maksimohotnikov.mydiary.CoefficientFragment.NIGHT_COEFFICIENT;
import static com.maksimohotnikov.mydiary.MenuFragment.BREAD_UNITS;
import static com.maksimohotnikov.mydiary.MainActivity.TAG;
import static com.maksimohotnikov.mydiary.MySettingFragment.*;
import static com.maksimohotnikov.mydiary.SugarInBloodFragment.*;


public class ShortInsulinFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.ShortInsulinFragment";
    private OnShortInsulinFragmentListener mListener;
    @BindView(R.id.btn_plus_short_insulin) Button btnPlusShortInsulin;
    @BindView(R.id.btn_minus_short_insulin) Button btnMinusShortInsulin;
    @BindView(R.id.et_short_insulin) EditText etShortInsulin;
    private Unbinder unbinder;
    private float currentCoefficient;
    private float totalInsulin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectCurrentCoefficient();

        Log.d(TAG, "onCreate: ShortInsulinFragment");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_short_insulin, container, false);
        unbinder = ButterKnife.bind(this, view);

        //float insulinEat = calculateInsulinEat(currentCoefficient);
        //float compensationInsulin =  calculateCompensationInsulin();
        //calculateTotalInsulin(insulinEat, compensationInsulin);
        calculateTotalInsulin();
        setTotalInsulin(totalInsulin);

        Log.d(TAG, "onCreateView: ShortInsulinFragment");
        return view;
    }

    //устанавливаем итоговую дозу инсулина
    private void setTotalInsulin(float totalInsulin){
        if (totalInsulin < 0.0f){
            etShortInsulin.setText(getString(R.string.zero_zero));
        }else {
            etShortInsulin.setText(String.valueOf(roundUp(totalInsulin, 1)));
        }
    }

    //вычисляем итоговую дозу инсулина
    @SuppressWarnings("ConstantConditions")
    private void calculateTotalInsulin(/*float insulinEat, float compensationInsulin*/){
        //totalInsulin = insulinEat + compensationInsulin;
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean switchNoMeasuringState = prefs.getBoolean(SWITCH_NO_MEASURING, false);
        boolean switchCompensationInsulin = prefs.getBoolean(SWITCH_COMPENSATION_INSULIN, false);

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
        float defaultCoefficient = 0.00f;
        if (currentCoefficient > defaultCoefficient) {
            SharedPreferences prefs = getActivity()
                    .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            float breadUnits = Float.valueOf(prefs.getString(BREAD_UNITS, ""));
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
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean switchState = prefs.getBoolean(SWITCH_COMPENSATION_INSULIN, false);
        if (!switchState){
            return 0.0f;
        }else {
            float sugarInBlood = Float.valueOf(prefs.getString(SUGAR_IN_BLOOD, ""));
            float targetGlucose = Float.valueOf(prefs.getString(TARGET_GLUCOSE, ""));
            float sensitivityCoefficient = Float.valueOf(prefs.getString(SENSITIVITY_COEFFICIENT, ""));
            return (sugarInBlood - targetGlucose) /sensitivityCoefficient;
        }
    }

    //Получаем коэффициент по текущему времени
    @SuppressWarnings("ConstantConditions")
    private void selectCurrentCoefficient(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

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
            currentCoefficient = Float.valueOf(prefs.getString(MORNING_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "MorningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "MorningTime now " + now);
        }else if (now.isAfter(afterDayTime) && now.isBefore(beforeDayTime)){
            currentCoefficient = Float.valueOf(prefs.getString(DAY_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "DayTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "DayTime " + now);
        }else if (now.isAfter(afterEveningTime) && now.isBefore(beforeEveningTime)){
            currentCoefficient = Float.valueOf(prefs.getString(EVENING_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "EveningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "EveningTime " + now);
        }else {
            currentCoefficient = Float.valueOf(prefs.getString(NIGHT_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "NightTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "NightTime " + now);
        }
    }

    @OnClick({R.id.btn_minus_short_insulin, R.id.btn_plus_short_insulin,
            R.id.view_click_long_insulin, R.id.btn_further})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_minus_short_insulin:
                decrementShortInsulin();
                break;
            case R.id.btn_plus_short_insulin:
                incrementShortInsulin();
                break;
            case R.id.view_click_long_insulin:
                mListener.openLongInsulinFragment();
                break;
            case R.id.btn_further:
                mListener.openTotalRecordFragment();
                break;
        }
    }

    //Уменьшаем короткий инсулин
    private void decrementShortInsulin(){
        float shortInsulin = Float.valueOf(etShortInsulin.getText().toString());
        if (shortInsulin >0.0f){
            btnPlusShortInsulin.setEnabled(true);
            shortInsulin = decrement(shortInsulin);
            etShortInsulin.setText(String.valueOf(roundUp(shortInsulin, 1)));
        }else {
            btnMinusShortInsulin.setEnabled(false);
        }
    }

    //Увеличиваем короткий инсулин
    private void incrementShortInsulin(){
        float shortInsulin = Float.valueOf(etShortInsulin.getText().toString());
        if (shortInsulin <1.0f){
            btnMinusShortInsulin.setEnabled(true);
            shortInsulin = increment(shortInsulin);
            etShortInsulin.setText(String.valueOf(roundUp(shortInsulin, 1)));
        }else {
            btnPlusShortInsulin.setEnabled(false);
        }
    }
    public interface OnShortInsulinFragmentListener{
        void openLongInsulinFragment();
        void openTotalRecordFragment();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        Log.d(TAG, "onAttach: ShortInsulinFragment");
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
        Log.d(TAG, "onActivityCreated: ShortInsulinFragment");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart: ShortInsulinFragment");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: ShortInsulinFragment");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause: ShortInsulinFragment");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop: ShortInsulinFragment");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ShortInsulinFragment");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(TAG, "onDestroy: ShortInsulinFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(TAG, "onDetach: ShortInsulinFragment");
    }
}
