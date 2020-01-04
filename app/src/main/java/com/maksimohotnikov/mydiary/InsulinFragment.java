package com.maksimohotnikov.mydiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class InsulinFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener{

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.InsulinFragment";

    private static final String TAG = "myLogs";

    Button btnShortInsulin;//TODO сделать отдельный фрагмент для Long Insulin
    Button btnLongInsulin;
    private Button btnPlusShortInsulin;
    private Button btnMinusShortInsulin;
    private Button btnPlusLongInsulin;
    private Button btnMinusLongInsulin;
    private TextView textViewShortInsulin;
    private EditText editTextShortInsulin;
    private EditText editTextLongInsulin;
    private String shortInsulin = "2.0";
    //Switch switchLongInsulin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_insulin, container, false);
        //btnShortInsulin = view.findViewById(R.id.btnShortInsulin);
        //btnLongInsulin = view.findViewById(R.id.btnLongInsulin);
        btnPlusShortInsulin = view.findViewById(R.id.btnPlusShortInsulin);
        btnMinusShortInsulin = view.findViewById(R.id.btnMinusShortInsulin);
        textViewShortInsulin = view.findViewById(R.id.textViewShortInsulin);
        editTextShortInsulin = view.findViewById(R.id.editTextShortInsulin);
        editTextLongInsulin = view.findViewById(R.id.editTextLongInsulin);
        btnPlusLongInsulin = view.findViewById(R.id.btnPlusLongInsulin);
        btnMinusLongInsulin = view.findViewById(R.id.btnMinusLongInsulin);

        editTextShortInsulin.setText(shortInsulin);

        /*btnShortInsulin.setOnClickListener(this);
        btnLongInsulin.setOnClickListener(this);*/
        Switch switchLongInsulin = view.findViewById(R.id.switchLongInsulin);
        if (switchLongInsulin != null){
            switchLongInsulin.setOnCheckedChangeListener(this);
        }
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            btnMinusLongInsulin.setVisibility(View.VISIBLE);
            btnPlusLongInsulin.setVisibility(View.VISIBLE);
            editTextLongInsulin.setVisibility(View.VISIBLE);
            textViewShortInsulin.setVisibility(View.GONE);
            editTextShortInsulin.setVisibility(View.GONE);
            btnMinusShortInsulin.setVisibility(View.GONE);
            btnPlusShortInsulin.setVisibility(View.GONE);
        } else {
            btnMinusLongInsulin.setVisibility(View.INVISIBLE);
            btnPlusLongInsulin.setVisibility(View.INVISIBLE);
            editTextLongInsulin.setVisibility(View.INVISIBLE);
            textViewShortInsulin.setVisibility(View.VISIBLE);
            editTextShortInsulin.setVisibility(View.VISIBLE);
            btnMinusShortInsulin.setVisibility(View.VISIBLE);
            btnPlusShortInsulin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinusShortInsulin:

        }
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLongInsulin:
                btnLongInsulin.setBackgroundResource(R.color.btn);
                btnShortInsulin.setBackgroundResource(R.color.inactive_button);
                btnPlusShortInsulin.setEnabled(false);
                btnMinusShortInsulin.setEnabled(false);
                break;
            case R.id.btnShortInsulin:
                btnShortInsulin.setBackgroundResource(R.color.btn);
                btnLongInsulin.setBackgroundResource(R.color.inactive_button);
        }
    }*/
}
