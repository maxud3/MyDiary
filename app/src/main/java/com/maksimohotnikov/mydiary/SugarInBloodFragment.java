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
import java.math.BigDecimal;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;



public class SugarInBloodFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SugarInBloodFragment";
    static final String SUGAR_IN_BLOOD = "sugarInBlood";
    static final String SWITCH_NO_MEASURING = "switchNoMeasuring";
    private SharedPreferences settings;
    @BindView(R.id.tv_warning)
    TextView tvWarning;
    @BindView(R.id.number_picker1_sugar)
    NumberPicker numberPicker1SugarInBlood;
    @BindView(R.id.number_picker2_sugar)
    NumberPicker numberPicker2SugarInBlood;
    @BindView(R.id.switch_no_measuring)
    Switch switchNoMeasuring;
    private Unbinder unbinder;
    private String s;
    private String s1;
    private String s2;
    private String sugarInBlood;
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        settings = getActivity()
                .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        loadSugarInBlood();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sugar_in_blood, container, false);
        unbinder = ButterKnife.bind(this, view);
        numberPicker1SugarInBlood.setOnValueChangedListener((picker, oldVal, newVal) -> {
            s =  String.valueOf(newVal);
            showWarning(s);
        });
        numberPicker2SugarInBlood.setOnValueChangedListener((picker, oldVal, newVal) -> {
            sugarInBlood = s + "." + newVal;
            showWarning(sugarInBlood);
        });
        numberPicker1SugarInBlood.setMinValue(0);
        numberPicker1SugarInBlood.setMaxValue(40);
        numberPicker1SugarInBlood.setValue(Integer.parseInt(s1));
        numberPicker2SugarInBlood.setMinValue(0);
        numberPicker2SugarInBlood.setMaxValue(9);
        numberPicker2SugarInBlood.setValue(Integer.parseInt(s2));

        loadSugarInBlood();

        Log.d(MainActivity.TAG, "onCreateView().SugarInBloodFragment");
        return view;
    }

    @OnCheckedChanged(R.id.switch_no_measuring)
    void checkedSwitchNoMeasuring(boolean checked){
        if (checked){
            numberPicker1SugarInBlood.setEnabled(false);
            numberPicker2SugarInBlood.setEnabled(false);
        }else {
            numberPicker1SugarInBlood.setEnabled(true);
            numberPicker2SugarInBlood.setEnabled(true);
        }
    }

    //Сохраняем значение сахара в крови
    private void saveSugarInBlood(){
        int i = numberPicker1SugarInBlood.getValue();
        int i1 = numberPicker2SugarInBlood.getValue();
        String sugarInBlood = i + "." + i1;
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(SUGAR_IN_BLOOD, sugarInBlood);
        prefEditor.putBoolean(SWITCH_NO_MEASURING, switchNoMeasuring.isChecked());
        prefEditor.apply();
    }


    private void loadSugarInBlood(){
        String s = settings.getString(SUGAR_IN_BLOOD, "0.0");
        String[] arrSplit = s.split("\\.");
        for (int i = 0; i < arrSplit.length; i++){
            s1 = arrSplit[0];
            s2 = arrSplit[1];
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
            tvWarning.setText(R.string.severe_hyperglycemia);
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
