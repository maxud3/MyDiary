package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.SettingConstants.*;


public class SugarInBloodFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SugarInBloodFragment";
    private SharedPreferences settings;
    @BindView(R.id.tv_warning)
    TextView tvWarning;
    @BindView(R.id.integer_number_picker)
    NumberPicker integerNumberPicker;
    @BindView(R.id.decimal_number_picker)
    NumberPicker decimalNumberPicker;
    @BindView(R.id.switch_no_measuring)
    Switch switchNoMeasuring;
    private Unbinder unbinder;
    private String s;
    private String integerValue;
    private String decimalValue;
    private String sugarInBlood;
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        settings = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sugar_in_blood, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadSugarInBlood();
        integerNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            s =  String.valueOf(newVal);
            String s1 = s + "." + decimalValue;
            showWarning(s1);
        });
        decimalNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            decimalValue = String.valueOf(newVal);
            if (s == null){
                s = integerValue;
            }else {
                sugarInBlood = s + "." + newVal;
                //sugarInBlood = integerValue + "." + newVal;
                showWarning(sugarInBlood);
            }

        });
        integerNumberPicker.setMinValue(0);
        integerNumberPicker.setMaxValue(40);
        integerNumberPicker.setValue(Integer.parseInt(integerValue));
        decimalNumberPicker.setMinValue(0);
        decimalNumberPicker.setMaxValue(9);
        decimalNumberPicker.setValue(Integer.parseInt(decimalValue));

        loadSugarInBlood();

        Log.d(MainActivity.TAG, "onCreateView().SugarInBloodFragment");
        return view;
    }

    @OnCheckedChanged(R.id.switch_no_measuring)
    void checkedSwitchNoMeasuring(boolean checked){
        if (checked){
            integerNumberPicker.setEnabled(false);
            decimalNumberPicker.setEnabled(false);
        }else {
            integerNumberPicker.setEnabled(true);
            decimalNumberPicker.setEnabled(true);
        }
    }

    //Сохраняем значение сахара в крови
    private void saveSugarInBlood(){
        int i = integerNumberPicker.getValue();
        int i1 = decimalNumberPicker.getValue();
        if (switchNoMeasuring.isChecked()){
            sugarInBlood = getString(R.string.zero_zero);
        }else {
            sugarInBlood = i + "." +i1;
        }
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(SUGAR_IN_BLOOD, sugarInBlood);
        prefEditor.putBoolean(SWITCH_NO_MEASURING, switchNoMeasuring.isChecked());
        prefEditor.apply();
    }

//устанавливаем предыдущее сохраненное значение сахара в крови
    private void loadSugarInBlood(){
        String s = settings.getString(SUGAR_IN_BLOOD, "0.0");
        String[] arrSplit = s.split("\\.");
        for (int i = 0; i < arrSplit.length; i++){
            integerValue = arrSplit[0];
            decimalValue = arrSplit[1];
        }
    }
    @OnClick(R.id.btn_further)
        void onClick() {
        saveSugarInBlood();
        mListener.onSugarInBloodFragmentListener();
    }

    //Выводим предупреждение о гипо или гиперглекимии
    private void showWarning(String s){
        float f = Float.parseFloat(s);
        if (f > 1.5 && f < 3.4){
            tvWarning.setText(R.string.hypoglycemia);
            //etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (f < 1.6){
            tvWarning.setText(R.string.severe_hypoglycemia);
            //etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (f > 3.3 && f < 5.8){
            tvWarning.setText("");
            //etSugarInBlood.setTextColor(getResources().getColor(R.color.white));
        }else if (f > 5.7 && f < 8.0){
            tvWarning.setText(R.string.mild_hyperglycemia);
            //etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (f > 7.9 && f < 11.0){
            tvWarning.setText(R.string.hyperglycemia);
            //etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (f > 10.9 && f < 16.5){
            tvWarning.setText(R.string.severe_hyperglycemia);
            //etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else if (f > 16.4 && f < 33.0){
            tvWarning.setText(R.string.attention_precoma);
           // etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
        }else {
            tvWarning.setText(R.string.attention_coma);
            //etSugarInBlood.setTextColor(getResources().getColor(R.color.red500));
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

     /*static float decrement(float f){
        return f - 0.1f;
    }
     static float increment(float f){
        return f + 0.1f;
    }*/

    @Override
    public void onPause(){
        super.onPause();
        //saveSugarInBlood();
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
