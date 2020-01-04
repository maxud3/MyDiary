package com.maksimohotnikov.mydiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class MenuFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.MenuFragment";

    private static final String TAG = "myLogs";

    private Button btnBreadUnits;
    private Button btnFurther;
    private Button btnCancel;
    private Button btnOK;
    private TextView textViewBreadUnits;
    private EditText editTextBreadUnits;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        btnFurther = view.findViewById(R.id.btnFurther);
        btnFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.openInsulinFragment();
            }
        });
        textViewBreadUnits = view.findViewById(R.id.textViewBreadUnits);
        editTextBreadUnits = view.findViewById(R.id.editTextBreadUnits);
        btnBreadUnits = view.findViewById(R.id.btnBreadUnits);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOK = view.findViewById(R.id.btnOK);
        btnBreadUnits.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        /*btnBreadUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFurther.setVisibility(View.GONE);
                editTextBreadUnits.setVisibility(View.VISIBLE);
            }
        });*/
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBreadUnits:
               btnFurther.setVisibility(View.GONE);
               btnCancel.setVisibility(View.VISIBLE);
               btnOK.setVisibility(View.VISIBLE);
               editTextBreadUnits.setVisibility(View.VISIBLE);
               break;
            case R.id.btnCancel:
                btnFurther.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                btnOK.setVisibility(View.GONE);
                editTextBreadUnits.setVisibility(View.GONE);
                break;
            case R.id.btnOK:
                btnFurther.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                btnOK.setVisibility(View.GONE);
                editTextBreadUnits.setVisibility(View.GONE);
                textViewBreadUnits.setText(editTextBreadUnits.getText().toString());
                break;
        }
    }


    public interface OpenInsulinFragment{
        void openInsulinFragment();
    }
    private OpenInsulinFragment mListener;
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
}
