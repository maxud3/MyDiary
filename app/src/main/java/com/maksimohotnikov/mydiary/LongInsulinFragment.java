package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import static com.maksimohotnikov.mydiary.SugarInBloodFragment.*;

public class LongInsulinFragment extends Fragment {

    final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.LongInsulinFragment";
    private OnLongInsulinFragmentListener mListener;
    @BindView(R.id.btn_plus_long_insulin)
    Button btnPlusLongInsulin;
    @BindView(R.id.btn_minus_long_insulin)
    Button btnMinusLongInsulin;
    @BindView(R.id.et_long_insulin)
    EditText etLongInsulin;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_long_insulin, container, false);
        unbinder =  ButterKnife.bind(this, view);

        etLongInsulin.setText("2.0");

        return view;
    }

    @OnClick({R.id.btn_minus_long_insulin, R.id.btn_plus_long_insulin, R.id.btn_further})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_minus_long_insulin:
                decrementLongInsulin();
                break;
            case R.id.btn_plus_long_insulin:
                incrementLongInsulin();
                break;
            case R.id.btn_further:
                mListener.openTotalRecordFragment();
        }
    }

    private void decrementLongInsulin(){
        float longInsulin = Float.valueOf(etLongInsulin.getText().toString());
        if (longInsulin > 0.0f){
            btnPlusLongInsulin.setEnabled(true);
            longInsulin = decrement(longInsulin);
            etLongInsulin.setText(String.valueOf(roundUp(longInsulin, 1)));
        }else {
            btnMinusLongInsulin.setEnabled(false);
        }
    }

    private void incrementLongInsulin(){
        float longInsulin = Float.valueOf(etLongInsulin.getText().toString());
        if (longInsulin <50.0f){
            btnMinusLongInsulin.setEnabled(true);
            longInsulin = increment(longInsulin);
            etLongInsulin.setText(String.valueOf(roundUp(longInsulin, 1)));
        }else {
            btnPlusLongInsulin.setEnabled(false);
        }
    }

    public interface OnLongInsulinFragmentListener{
        void openTotalRecordFragment();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLongInsulinFragmentListener) {
            mListener = (OnLongInsulinFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

   @Override
   public void onDestroy(){
       super.onDestroy();
       unbinder.unbind();
   }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
