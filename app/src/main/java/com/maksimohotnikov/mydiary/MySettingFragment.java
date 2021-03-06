package com.maksimohotnikov.mydiary;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.SettingConstants.*;

public class MySettingFragment extends Fragment {
    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.MySettingFragment";
    private Unbinder unbinder;
    @BindView(R.id.arrow_down) ImageView ivArrowDown;
    @BindView(R.id.arrow_down_1) ImageView ivArrowDown1;
    @BindView(R.id.arrow_down_2) ImageView ivArrowDown2;
    @BindView(R.id.arrow_down_3) ImageView ivArrowDown3;
    @BindView(R.id.tv_coefficients) TextView tvCoefficients;
    @BindView(R.id.et_morning_coefficient) EditText etMorningCoefficient;
    @BindView(R.id.et_day_coefficient) EditText etDayCoefficient;
    @BindView(R.id.et_evening_coefficient) EditText etEveningCoefficient;
    @BindView(R.id.et_night_coefficient) EditText etNightCoefficient;
    @BindView(R.id.group_coefficients) Group groupCoefficients;
    @BindView(R.id.group_compensation) Group groupCompensation;
    @BindView(R.id.tv_daily_dose_insulin_formula) TextView tvDailyDoseInsulinFormula;
    @BindView(R.id.tv_hint_bread_units) TextView tvHintBreadUnits;
    @BindView(R.id.tv_compensation) TextView tvCompensation;
    @BindView(R.id.switch_compensation_insulin) Switch switchCompensationInsulin;
    @BindView(R.id.et_target_glucose) EditText etTargetGlucose;
    @BindView(R.id.et_bottom_line) EditText etBottomLine;
    @BindView(R.id.et_top_line) EditText etTopLine;
    @BindView(R.id.et_sensitivity_coefficient) EditText etSensitivityCoefficient;
    @BindView(R.id.et_daily_dose_insulin) EditText etDailyDoseInsulin;
    @BindView(R.id.et_carbohydrates_in_bread_unit) EditText etCarbohydratesInBreadUnit;
    @BindString(R.string.default_coefficient) String defaultCoefficient;
    @BindColor(R.color.white) int whiteColor;
    private SharedPreferences settings;

    public MySettingFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE );
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_setting, container, false);
        getActivity().setTitle(R.string.settings);
        unbinder = ButterKnife.bind(this, view);
        loadCoefficient();
        onOffCoefficient(morningCoefficient(), dayCoefficient(), eveningCoefficient(),
                nightCoefficient());
        loadCompensationInsulinSettings();
        loadDailyDoseInsulin();
        loadCarbohydratesInBreadUnit();

        return view;
    }

    //Сохраняем коэффициенты
    private void saveCoefficients(){
        SharedPreferences.Editor prefEditor =settings.edit();
        prefEditor.putString(MORNING_COEFFICIENT, morningCoefficient());
        prefEditor.putString(DAY_COEFFICIENT, dayCoefficient());
        prefEditor.putString(EVENING_COEFFICIENT, eveningCoefficient());
        prefEditor.putString(NIGHT_COEFFICIENT, nightCoefficient());
        prefEditor.apply();
        Toast toast = Toast.makeText(getActivity(), R.string.coefficient_saved, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
    }

//Загружаем коэффициенты
    private void loadCoefficient() {
        etMorningCoefficient.setText(settings.getString(MORNING_COEFFICIENT, ""));
        etDayCoefficient.setText(settings.getString(DAY_COEFFICIENT, ""));
        etEveningCoefficient.setText(settings.getString(EVENING_COEFFICIENT, ""));
        etNightCoefficient.setText(settings.getString(NIGHT_COEFFICIENT, ""));
    }

    //Отображаем указаны или нет коэффициенты
    private void onOffCoefficient(String morningCoefficient, String dayCoefficient,
                                  String eveningCoefficient, String nightCoefficient){
        if (morningCoefficient.equals(defaultCoefficient) || dayCoefficient.equals(defaultCoefficient)
                || eveningCoefficient.equals(defaultCoefficient) || nightCoefficient.equals(defaultCoefficient)){
            tvCoefficients.setText(R.string.not_set);
            tvCoefficients.setTextColor(Color.RED);
            Toast toast = Toast.makeText(
                    getActivity(), R.string.indicate_coefficient, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else {
            tvCoefficients.setText(R.string.set);
            tvCoefficients.setTextColor(whiteColor);
        }
    }

    private String morningCoefficient(){
        String morningCoefficient = etMorningCoefficient.getText().toString();
        if (morningCoefficient.equals("")) {
            morningCoefficient = defaultCoefficient;
        }
        return morningCoefficient;
    }

    private String dayCoefficient(){
        String dayCoefficient = etDayCoefficient.getText().toString();
        if (dayCoefficient.equals("")){
            dayCoefficient = defaultCoefficient;
        }
        return dayCoefficient;
    }

    private String eveningCoefficient(){
        String eveningCoefficient = etEveningCoefficient.getText().toString();
        if (eveningCoefficient.equals("")){
            eveningCoefficient = defaultCoefficient;
        }
        return eveningCoefficient;
    }

    private String nightCoefficient(){
        String nightCoefficient = etNightCoefficient.getText().toString();
        if (nightCoefficient.equals("")){
            nightCoefficient = defaultCoefficient;
        }
        return nightCoefficient;
    }

    //Сохраняем параметры компенсации
    private void saveCompensationInsulinSettings() {
        SharedPreferences.Editor prefEditor =settings.edit();
            prefEditor.putBoolean(SWITCH_COMPENSATION_INSULIN, switchCompensationInsulin.isChecked());
            prefEditor.putString(TARGET_GLUCOSE, etTargetGlucose.getText().toString());
            prefEditor.putString(BOTTOM_LINE, etBottomLine.getText().toString());
            prefEditor.putString(TOP_LINE, etTopLine.getText().toString());
            prefEditor.putString(SENSITIVITY_COEFFICIENT, etSensitivityCoefficient.getText().toString());
            prefEditor.apply();
            Toast toast = Toast.makeText(getActivity(), R.string.parameters_saved, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

    }

    //Загружаем параметры компенсации
    private void loadCompensationInsulinSettings(){
        boolean switchState = settings.getBoolean(SWITCH_COMPENSATION_INSULIN, true);
        if (switchState){
            String targetGlucose = etTargetGlucose.getText().toString();
            tvCompensation.setText(getString(R.string.compensation_on, targetGlucose));
        }else {
            tvCompensation.setText(R.string.off);
        }
        etTargetGlucose.setText(settings.getString(TARGET_GLUCOSE, ""));
        etBottomLine.setText(settings.getString(BOTTOM_LINE, ""));
        etTopLine.setText(settings.getString(TOP_LINE, ""));
        etSensitivityCoefficient.setText(settings.getString(SENSITIVITY_COEFFICIENT, ""));
        switchCompensationInsulin.setChecked(switchState);
    }
    //Сохраняем суточную дозу инсулина
    private void saveDailyDoseInsulin (){
        String dailyDoseInsulin = etDailyDoseInsulin.getText().toString();
        String defaultDailyDoseInsulin = "0";
        SharedPreferences.Editor prefEditor =settings.edit();
        if (dailyDoseInsulin.equals("")){
            prefEditor.putString(DAILY_DOSE_INSULIN, defaultDailyDoseInsulin);
            prefEditor.apply();
        }else {
            prefEditor.putString(DAILY_DOSE_INSULIN, dailyDoseInsulin);
            prefEditor.apply();
        }
    }

    //Выводим суточную дозу инсулина
    private void loadDailyDoseInsulin(){
        etDailyDoseInsulin.setText(settings.getString(DAILY_DOSE_INSULIN, ""));
    }

    //Сохраняем колличество углеводов в одной хлебной единице
    private void saveCarbohydratesInBreadUnit(){
        SharedPreferences.Editor prefEditor =settings.edit();
        prefEditor.putString(CARBOHYDRATES_IN_BREAD_UNIT, etCarbohydratesInBreadUnit
                .getText().toString());
        prefEditor.apply();
    }

    //Выводим колличество углеводов в одной хлебной единице
    private void loadCarbohydratesInBreadUnit(){
        etCarbohydratesInBreadUnit.setText(settings
                .getString(CARBOHYDRATES_IN_BREAD_UNIT, ""));
    }
    @OnCheckedChanged(R.id.switch_compensation_insulin)
    void onCheckedChanged(boolean checked){
        if (checked){
            etTargetGlucose.setEnabled(true);
            etBottomLine.setEnabled(true);
            etTopLine.setEnabled(true);
            etSensitivityCoefficient.setEnabled(true);
            String targetGlucose = etTargetGlucose.getText().toString();
            tvCompensation.setText(getString(R.string.compensation_on, targetGlucose));
        }else {
            etTargetGlucose.setEnabled(false);
            etBottomLine.setEnabled(false);
            etTopLine.setEnabled(false);
            etSensitivityCoefficient.setEnabled(false);
            tvCompensation.setText(R.string.off);
        }
    }

    @OnClick({R.id.arrow_down, R.id.arrow_down_1, R.id.arrow_down_2, R.id.arrow_down_3})
    void onClick(View view){
        switch (view.getId()){
            case R.id.arrow_down:
                if (!groupCoefficients.isShown()){
                    rotateUp(ivArrowDown);
                    groupCoefficients.setVisibility(View.VISIBLE);
                }else {
                    rotateDown(ivArrowDown);
                    groupCoefficients.setVisibility(View.GONE);
                    onOffCoefficient(morningCoefficient(), dayCoefficient(), eveningCoefficient(),
                            nightCoefficient());
                }
                break;
            case R.id.arrow_down_1:
                if (!groupCompensation.isShown()){
                    rotateUp(ivArrowDown1);
                    groupCompensation.setVisibility(View.VISIBLE);
                }else {
                    if (etTargetGlucose.getText().toString().equals("")
                            || etBottomLine.getText().toString().equals("")
                            || etTopLine.getText().toString().equals("")
                            || etSensitivityCoefficient.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getActivity(), R.string.error_parameters_saved,
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0,0);
                        toast.show();
                    }else {
                        rotateDown(ivArrowDown1);
                        groupCompensation.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.arrow_down_2:
                if (!tvDailyDoseInsulinFormula.isShown()){
                    rotateUp(ivArrowDown2);
                    tvDailyDoseInsulinFormula.setVisibility(View.VISIBLE);
                }else {
                    rotateDown(ivArrowDown2);
                    tvDailyDoseInsulinFormula.setVisibility(View.GONE);
                }
                break;
            case R.id.arrow_down_3:
                if (!tvHintBreadUnits.isShown()){
                    rotateUp(ivArrowDown3);
                    tvHintBreadUnits.setVisibility(View.VISIBLE);
                }else {
                    rotateDown(ivArrowDown3);
                    tvHintBreadUnits.setVisibility(View.GONE);
                }
                break;
        }
    }

    //Вращаем стрелку вверх
    private void rotateUp(ImageView imageView) {
        ObjectAnimator rotate = ObjectAnimator
                .ofFloat(imageView, "rotation", 0f, 180f);
        //rotate.setRepeatCount(10);  колличество вращений
        rotate.setDuration(100);
        rotate.start();
    }

    //Вращаем стрелку вниз
    private void rotateDown(ImageView imageView) {
        ObjectAnimator rotate = ObjectAnimator
                .ofFloat(imageView, "rotation", 180f, 360f);
        //rotate.setRepeatCount(10);  колличество вращений
        rotate.setDuration(100);
        rotate.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        saveCoefficients();
        saveCompensationInsulinSettings();
        saveCarbohydratesInBreadUnit();
        saveDailyDoseInsulin();
        //Log.d(TAG, "CoefficientFragment. onPause");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}
