package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ShortInsulinFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.ShortInsulinFragment";
    private OnShortInsulinFragmentListener mListener;
    private Button btnPlusShortInsulin;
    private Button btnMinusShortInsulin;
    private EditText etShortInsulin;
    private float shortInsulin = 2.0f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_short_insulin, container, false);

        btnMinusShortInsulin = view.findViewById(R.id.btnMinusLongInsulin);
        btnPlusShortInsulin = view.findViewById(R.id.btnPlusLongInsulin);
        etShortInsulin = view.findViewById(R.id.etLongInsulin);
        View viewLongInsulin = view.findViewById(R.id.viewLongInsulin);

        String stShortInsulin = Float.toString(shortInsulin);
        etShortInsulin.setText(stShortInsulin);

        btnMinusShortInsulin.setOnClickListener(this);
        btnPlusShortInsulin.setOnClickListener(this);
        viewLongInsulin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinusLongInsulin:
                if (shortInsulin > 1.5f) {
                    btnPlusShortInsulin.setEnabled(true);
                    shortInsulin = SugarInBloodFragment.decrement(shortInsulin);
                    etShortInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundDown(shortInsulin, 1)));
                } else {
                    btnMinusShortInsulin.setEnabled(false);
                }
                break;
            case R.id.btnPlusLongInsulin:
                if (shortInsulin < 2.4f) {
                    btnMinusShortInsulin.setEnabled(true);
                    shortInsulin = SugarInBloodFragment.increment(shortInsulin);
                    etShortInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundUp(shortInsulin, 1)));
                } else {
                    btnPlusShortInsulin.setEnabled(false);
                }
                break;
            case R.id.viewLongInsulin:
                mListener.openLongInsulinFragment();
                break;
            case R.id.btnFurther:
                mListener.openTotalRecordFragment();
        }
    }

    public interface OnShortInsulinFragmentListener{
        void openLongInsulinFragment();
        void openTotalRecordFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnShortInsulinFragmentListener){
            mListener = (OnShortInsulinFragmentListener) context;
        } else {
            throw  new RuntimeException(context.toString()
                    + "must implement OnFragment1DataListener");
        }
    }
}
