package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.Group;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import static com.maksimohotnikov.mydiary.SugarInBloodFragment.*;


public class MenuFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.MenuFragment";
    private static final String TAG = "myLogs";
    private OpenInsulinFragment mListener;

    @BindView(R.id.btnBreadUnits) Button btnBreadUnits;
    @BindView(R.id.btnMinusBreadUnits) Button btnMinusBreadUnits;
    @BindView(R.id.btnPlusBreadUnits) Button btnPlusBreadUnits;
    @BindView(R.id.btnFurther) Button btnFurther;
    @BindView(R.id.btnCancel) Button btnCancel;
    @BindView(R.id.btnOK) Button btnOK;
    @BindView(R.id.tvBreadUnits) TextView tvBreadUnits;
    @BindView(R.id.etBreadUnits) EditText etBreadUnits;
    @BindView(R.id.groupBreadUnits) Group group;
    @BindView(R.id.groupSelectedBreadUnits) Group groupSelectedBreadUnits;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);

        etBreadUnits.setText("1.0");
        btnBreadUnits.setOnClickListener(this);
        btnMinusBreadUnits.setOnClickListener(this);
        btnPlusBreadUnits.setOnClickListener(this);
        btnFurther.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBreadUnits:
                btnFurther.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCancel:
                btnFurther.setVisibility(View.VISIBLE);
                group.setVisibility(View.GONE);
                break;
            case R.id.btnOK:
                btnFurther.setVisibility(View.VISIBLE);
                group.setVisibility(View.GONE);
                groupSelectedBreadUnits.setVisibility(View.VISIBLE);
                tvBreadUnits.setText(etBreadUnits.getText().toString());
                break;
            case R.id.btnMinusBreadUnits:
                decrementBreadUnits();
                break;
            case R.id.btnPlusBreadUnits:
                incrementBreadUnits();
                break;
            case R.id.btnFurther:
                String breadUnits = tvBreadUnits.getText().toString();
                if (breadUnits.equals("")) {
                    breadUnits = "0";
                }
                mListener.openInsulinFragment(breadUnits);
        }
    }

    private void decrementBreadUnits (){
        float breadUnits = Float.valueOf(etBreadUnits.getText().toString());
        if (breadUnits > 0.0f){
            btnPlusBreadUnits.setEnabled(true);
            breadUnits = decrement(breadUnits);
            etBreadUnits.setText(String
                    .valueOf(roundUp(breadUnits, 1)));
        }else {
            btnMinusBreadUnits.setEnabled(false);
        }
    }

    private void incrementBreadUnits(){
        float breadUnits = Float.valueOf(etBreadUnits.getText().toString());
        if (breadUnits < 50.0f){
            btnMinusBreadUnits.setEnabled(true);
            breadUnits = increment(breadUnits);
            etBreadUnits.setText(String
                    .valueOf(roundUp(breadUnits, 1)));
        }else {
            btnPlusBreadUnits.setEnabled(false);
        }
    }
    public interface OpenInsulinFragment{
        void openInsulinFragment(String string);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OpenInsulinFragment){
            mListener = (OpenInsulinFragment) context;
        } else {
            throw  new RuntimeException(context.toString()
                    + "must implement OnFragment1DataListener");
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }
}
