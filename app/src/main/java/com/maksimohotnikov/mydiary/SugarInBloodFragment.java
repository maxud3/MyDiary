package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.math.BigDecimal;

public class SugarInBloodFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "myLogs";
    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SugarInBloodFragment";

    float valueSugarInBlood = 4.4f;
    EditText etSugarInBlood;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sugar_in_blood, container, false);

        etSugarInBlood = view.findViewById(R.id.editTextSugarInBlood);
        String sugarInBlood = Float.toString(valueSugarInBlood);
        etSugarInBlood.setText(sugarInBlood);

        Button btnMinus = view.findViewById(R.id.btnMinus);
        Button btnPlus = view.findViewById(R.id.btnPlus);
        Button btnFurther = view.findViewById(R.id.btnFurther);
        btnFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSugarInBloodFragmentListener();
            }
        });

        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);

        Log.d(TAG, "onCreateView().SugarInBloodFragment");
        return view;
    }
    public interface OnSugarInBloodFragmentListener{
        void onSugarInBloodFragmentListener();
    }
    private OnSugarInBloodFragmentListener mListener;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnSugarInBloodFragmentListener){
            mListener = (OnSugarInBloodFragmentListener) context;
        } else {
            throw  new RuntimeException(context.toString()
                + "must implement OnFragment1DataListener");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinus:
                Log.d(TAG, "click btnMinus");
                valueSugarInBlood = decrementSugarInBlood(valueSugarInBlood);
                etSugarInBlood.setText(String.valueOf(roundDown(valueSugarInBlood,1)));
                break;
            case R.id.btnPlus:
                Log.d(TAG, "click btnPlus");
                //valueSugarInBlood = valueSugarInBlood + 0.1f;
                valueSugarInBlood = incrimentSugarInBlood(valueSugarInBlood);
                etSugarInBlood.setText(String.valueOf(roundUp(valueSugarInBlood, 1)));
                break;
        }
    }
    public float decrementSugarInBlood(float f){
        return f - 0.1f;
    }
    public float incrimentSugarInBlood(float f){
        return f + 0.1f;
    }
    public BigDecimal roundDown(float value, int digits){
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_DOWN);
    }
    public BigDecimal roundUp(float value, int digits){
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }
}
