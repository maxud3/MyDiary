package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;
import static com.maksimohotnikov.mydiary.CoefficientFragment.APP_PREFERENCES;

public class CompensationFragment extends Fragment {
    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.CompensationFragment";
    static final String SWITCH_COMPENSATION_INSULIN = "switchCompensationInsulin";
    static final String TARGET_GLUCOSE = "targetGlucose";
    private static final String BOTTOM_LINE = "bottomLine";
    private static final String TOP_LINE = "topLine";
    private static final String SENSITIVITY_COEFFICIENT = "sensitivityCoefficient";
    @BindView(R.id.group_compensation_insulin)
    Group groupCompensationInsulin;
    @BindView(R.id.et_target_glucose)
    EditText etTargetGlucose;
    @BindView(R.id.et_bottom_line)
    EditText etBottomLine;
    @BindView(R.id.et_top_line)
    EditText etTopLine;
    @BindView(R.id.switch_compensation_insulin)
    Switch switchCompensationInsulin;
    @BindView(R.id.tv_compensation_insulin_formula)
    TextView tvCompensationInsulinFormula;
    @BindView(R.id.et_sensitivity_coefficient)
    EditText etSensitivityCoefficient;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compensation, container, false);
        unbinder = ButterKnife.bind(this, view);

        //loadCompensationInsulinSettings();
        compensationInsulinFormulaValue();
        return view;
    }
    //Передаем в формулу расчета компенсационного инсулина значения
    //целевой глюкозы и коэффициент чувствительности
    //TODO нужна ли эта формула пользователю
    private void compensationInsulinFormulaValue(){
        String targetGlucose = etTargetGlucose.getText().toString();
        String sensitivityCoefficient = etSensitivityCoefficient.getText().toString();
        tvCompensationInsulinFormula.setText(getString(R.string.compensation_insulin_formula,
                targetGlucose, sensitivityCoefficient));
    }

    //Показываем или скрываем группу значений компенсации
    @OnCheckedChanged(R.id.switch_compensation_insulin)
    void showGroupCompensationInsulin(boolean checked){
        if (checked){
            groupCompensationInsulin.setVisibility(View.VISIBLE);
            tvCompensationInsulinFormula.setVisibility(View.VISIBLE);
        }else {
            groupCompensationInsulin.setVisibility(View.GONE);
            tvCompensationInsulinFormula.setVisibility(View.GONE);
        }
    }

    //Сохраняем параметры компенсации
    /*@SuppressWarnings("ConstantConditions")
    private void saveCompensationInsulinSettings(){
        SharedPreferences.Editor prefEditor = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit();
        prefEditor.putBoolean(SWITCH_COMPENSATION_INSULIN, switchCompensationInsulin.isChecked());
        prefEditor.putString(TARGET_GLUCOSE, etTargetGlucose.getText().toString());
        prefEditor.putString(BOTTOM_LINE, etBottomLine.getText().toString());
        prefEditor.putString(TOP_LINE, etTopLine.getText().toString());
        prefEditor.putString(SENSITIVITY_COEFFICIENT, etSensitivityCoefficient.getText().toString());
        prefEditor.apply();
    }*/

    //Загружаем параметры компенсации
    /*@SuppressWarnings("ConstantConditions")
    private void loadCompensationInsulinSettings(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean switchState = prefs.getBoolean(SWITCH_COMPENSATION_INSULIN, true);
        etTargetGlucose.setText(prefs.getString(TARGET_GLUCOSE, ""));
        etBottomLine.setText(prefs.getString(BOTTOM_LINE, ""));
        etTopLine.setText(prefs.getString(TOP_LINE, ""));
        etSensitivityCoefficient.setText(prefs.getString(SENSITIVITY_COEFFICIENT, ""));
        switchCompensationInsulin.setChecked(switchState);
    }*/

    @Override
    public void onPause(){
        super.onPause();
        //saveCompensationInsulinSettings();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
