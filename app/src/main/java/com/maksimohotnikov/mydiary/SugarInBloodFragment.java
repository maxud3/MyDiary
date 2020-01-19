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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SugarInBloodFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "myLogs";
    public static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SugarInBloodFragment";

    @BindView(R.id.etSugarInBlood) EditText etSugarInBlood;
    @BindView(R.id.btnMinus) Button btnMinus;
    @BindView(R.id.btnPlus) Button btnPlus;
    @BindView(R.id.btnFurther) Button btnFurther;
    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_sugar_in_blood, container, false);
        unbinder = ButterKnife.bind(this, view);
        etSugarInBlood.setText("4.4");

        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnFurther.setOnClickListener(this);

        Log.d(TAG, "onCreateView().SugarInBloodFragment");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinus:
                Log.d(TAG, "click btnMinus");
                decrementSugarInBlood();
                break;
            case R.id.btnPlus:
                Log.d(TAG, "click btnPlus");
                incrementSugarInBlood();
                break;
            case R.id.btnFurther:
                mListener.onSugarInBloodFragmentListener();
        }
    }

    private void decrementSugarInBlood(){
        float sugarInBlood = Float.valueOf(etSugarInBlood.getText().toString());
        if (sugarInBlood > 0.0f){
            sugarInBlood = decrement(sugarInBlood);
            etSugarInBlood.setText(String.valueOf(roundUp(sugarInBlood, 1)));
        }else {
            btnMinus.setEnabled(false);
        }
    }

    private void incrementSugarInBlood(){
        float sugarInBlood = Float.valueOf(etSugarInBlood.getText().toString());
        if (sugarInBlood < 34.0f){
            sugarInBlood = increment(sugarInBlood);
            etSugarInBlood.setText(String.valueOf(roundUp(sugarInBlood,1)));
        }else {
            btnPlus.setEnabled(false);
        }
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
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }
}
