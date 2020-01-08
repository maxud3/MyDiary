package com.maksimohotnikov.mydiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import androidx.constraintlayout.widget.Group;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InsulinFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener{

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.InsulinFragment";

    private static final String TAG = "myLogs";

    private Button btnPlusShortInsulin;
    private Button btnMinusShortInsulin;
    private Button btnPlusLongInsulin;
    private Button btnMinusLongInsulin;
    private EditText etShortInsulin;
    private EditText etLongInsulin;
    private float shortInsulin = 2.0f;
    private float longInsulin = 1.0f;
    private Group groupLongInsulin;
    private Group groupShortInsulin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_insulin, container, false);

        etShortInsulin = view.findViewById(R.id.editTextShortInsulin);
        btnPlusShortInsulin = view.findViewById(R.id.btnPlusShortInsulin);
        btnMinusShortInsulin = view.findViewById(R.id.btnMinusShortInsulin);
        btnPlusShortInsulin.setOnClickListener(this);
        btnMinusShortInsulin.setOnClickListener(this);
        String stShortInsulin = Float.toString(shortInsulin);
        etShortInsulin.setText(stShortInsulin);

        etLongInsulin = view.findViewById(R.id.editTextLongInsulin);
        btnPlusLongInsulin = view.findViewById(R.id.btnPlusLongInsulin);
        btnMinusLongInsulin = view.findViewById(R.id.btnMinusLongInsulin);
        btnPlusLongInsulin.setOnClickListener(this);
        btnMinusLongInsulin.setOnClickListener(this);
        String stLongInsulin = Float.toString(longInsulin);
        etLongInsulin.setText(stLongInsulin);


        groupLongInsulin = view.findViewById(R.id.groupLongInsulin);
        groupShortInsulin = view.findViewById(R.id.groupShortInsulin);

        Switch switchLongInsulin = view.findViewById(R.id.switchLongInsulin);
        if (switchLongInsulin != null){
            switchLongInsulin.setOnCheckedChangeListener(this);
        }
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            groupShortInsulin.setVisibility(View.GONE);
            groupLongInsulin.setVisibility(View.VISIBLE);
        } else {
            groupShortInsulin.setVisibility(View.VISIBLE);
            groupLongInsulin.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinusShortInsulin:
                if (shortInsulin > 1.5f) {
                    btnPlusShortInsulin.setEnabled(true);
                    shortInsulin = SugarInBloodFragment.decrement(shortInsulin);
                    etShortInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundDown(shortInsulin, 1)));
                } else {
                    btnMinusShortInsulin.setEnabled(false);
                }
                break;
            case R.id.btnPlusShortInsulin:
                if (shortInsulin < 2.4f) {
                    btnMinusShortInsulin.setEnabled(true);
                    shortInsulin = SugarInBloodFragment.increment(shortInsulin);
                    etShortInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundUp(shortInsulin, 1)));
                } else {
                    btnPlusShortInsulin.setEnabled(false);
                }
                break;
            case R.id.btnMinusLongInsulin:
                if (longInsulin > 0.5f) {
                    btnPlusLongInsulin.setEnabled(true);
                    longInsulin = SugarInBloodFragment.decrement(longInsulin);
                    etLongInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundDown(longInsulin, 1)));
                } else {
                    btnMinusLongInsulin.setEnabled(false);
                }
                break;
            case R.id.btnPlusLongInsulin:
                if (longInsulin < 1.4f) {
                    btnMinusLongInsulin.setEnabled(true);
                    longInsulin = SugarInBloodFragment.increment(longInsulin);
                    etLongInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundUp(longInsulin, 1)));
                } else {
                    btnPlusLongInsulin.setEnabled(false);
                }
                break;
        }
    }
}
