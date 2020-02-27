package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.math.BigDecimal;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.MySettingFragment.APP_PREFERENCES;

public class SugarInBloodFragment extends Fragment {

    private static final String TAG = "myLogs";
    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SugarInBloodFragment";
    static final String SUGAR_IN_BLOOD = "sugarInBlood";
    static final String SWITCH_NO_MEASURING = "switchNoMeasuring";
    private static final int DIGITS = 1;
    @BindView(R.id.et_sugar_in_blood)
    EditText etSugarInBlood;
    @BindView(R.id.tv_warning)
    TextView tvWarning;
    @BindView(R.id.btn_minus)
    Button btnMinus;
    @BindView(R.id.btn_plus)
    Button btnPlus;
    @BindView(R.id.switch_no_measuring)
    Switch switchNoMeasuring;
    private Unbinder unbinder;
    private String sugarInBlood;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sugar_in_blood, container, false);
        unbinder = ButterKnife.bind(this, view);
        //etSugarInBlood.setText(R.string.default_sugar_in_blood);
        loadSugarInBlood();

        Log.d(TAG, "onCreateView().SugarInBloodFragment");
        return view;
    }

    @OnCheckedChanged(R.id.switch_no_measuring)
    void checkedSwitchNoMeasuring(boolean checked){
        if (checked){
            btnMinus.setEnabled(false);
            btnPlus.setEnabled(false);
            etSugarInBlood.setEnabled(false);
            //sugarInBlood = getString(R.string.zero_zero);
        }else {
            btnMinus.setEnabled(true);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void saveSugarInBlood(){
        String sugarInBlood = etSugarInBlood.getText().toString();
        if (sugarInBlood.equals("")){
            sugarInBlood = getString(R.string.zero_zero);
        }

        /*if (!switchNoMeasuring.isChecked()) {
            sugarInBlood = etSugarInBlood.getText().toString();
        }*/

        SharedPreferences.Editor prefEditor = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit();
        prefEditor.putBoolean(SWITCH_NO_MEASURING, switchNoMeasuring.isChecked());
        prefEditor.putString(SUGAR_IN_BLOOD, sugarInBlood);
        prefEditor.apply();
    }

    @SuppressWarnings("ConstantConditions")
    private void loadSugarInBlood(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        etSugarInBlood
                .setText(prefs.getString(SUGAR_IN_BLOOD, getString(R.string.default_sugar_in_blood)));
    }
    @OnClick({R.id.btn_minus, R.id.btn_plus, R.id.btn_further})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_minus:
                decrementSugarInBlood();
                showWarning();
                break;
            case R.id.btn_plus:
                incrementSugarInBlood();
                showWarning();
                break;
            case R.id.btn_further:

                mListener.onSugarInBloodFragmentListener();
        }
    }

    //Уменьшение значения сахара в крови
    private void decrementSugarInBlood(){
        float sugarInBlood = Float.valueOf(etSugarInBlood.getText().toString());
        if (sugarInBlood > 0.0f){
            btnPlus.setEnabled(true);
            sugarInBlood = decrement(sugarInBlood);
            //sugarInBlood = sugarInBlood - 0.1f;
            etSugarInBlood.setText(String.valueOf(roundUp(sugarInBlood, DIGITS)));
        }else {
            btnMinus.setEnabled(false);
        }
    }

    //Увеличиваем значение сахара в крови
    private void incrementSugarInBlood(){
        float sugarInBlood = Float.valueOf(etSugarInBlood.getText().toString());
        if (sugarInBlood < 34.0f){
            btnMinus.setEnabled(true);
            sugarInBlood = increment(sugarInBlood);
            //sugarInBlood = sugarInBlood + 0.1f;
            etSugarInBlood.setText(String.valueOf(roundUp(sugarInBlood, DIGITS)));
        }else {
            btnPlus.setEnabled(false);
        }
    }

    //Выводим предупреждение о гипо или гиперглекимии
    private void showWarning(){
        float sugarInBlood = Float.valueOf(etSugarInBlood.getText().toString());
        if (sugarInBlood > 1.5 && sugarInBlood < 3.4){
            tvWarning.setText(R.string.hypoglycemia);
            etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (sugarInBlood < 1.6){
            tvWarning.setText(R.string.severe_hyperglycemia);
            etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (sugarInBlood > 3.3 && sugarInBlood < 5.8){
            tvWarning.setText("");
            etSugarInBlood.setTextColor(getResources().getColor(R.color.white));
        }else if (sugarInBlood > 5.7 && sugarInBlood < 8.0){
            tvWarning.setText(R.string.mild_hyperglycemia);
            etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (sugarInBlood > 7.9 && sugarInBlood < 11.0){
            tvWarning.setText(R.string.hyperglycemia);
            etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (sugarInBlood > 10.9 && sugarInBlood < 16.5){
            tvWarning.setText(R.string.severe_hyperglycemia);
            etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (sugarInBlood > 16.4 && sugarInBlood < 33.0){
            tvWarning.setText(R.string.attention_precoma);
            etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else {
            tvWarning.setText(R.string.attention_coma);
            etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }
    }
    public interface OnSugarInBloodFragmentListener{
        void onSugarInBloodFragmentListener();
    }
    private OnSugarInBloodFragmentListener mListener;


    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof OnSugarInBloodFragmentListener){
            mListener = (OnSugarInBloodFragmentListener) context;
        } else {
            throw  new RuntimeException(context.toString()
                + "must implement OnFragment1DataListener");
        }
    }

     static float decrement(float f){
        return f - 0.1f;
    }
     static float increment(float f){
        return f + 0.1f;
    }

     static BigDecimal roundUp(float value, int digits){
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public void onPause(){
        super.onPause();
        saveSugarInBlood();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }
}
