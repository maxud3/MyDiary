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


public class MenuFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.MenuFragment";

    private static final String TAG = "myLogs";

    //private Button btnBreadUnits;
    private Button btnMinusBreadUnits;
    private Button btnPlusBreadUnits;
    private Button btnFurther;
    private Button btnCancel;
    private Button btnOK;
    private TextView textViewBreadUnits;
    private EditText editTextBreadUnits;
    private Group group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        textViewBreadUnits = view.findViewById(R.id.textViewBreadUnits);
        editTextBreadUnits = view.findViewById(R.id.editTextBreadUnits);

        btnFurther = view.findViewById(R.id.btnFurther);
        btnFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.openInsulinFragment();
            }
        });

        group = view.findViewById(R.id.groupBreadUnits);
        Button btnBreadUnits = view.findViewById(R.id.btnBreadUnits);
        btnMinusBreadUnits = view.findViewById(R.id.btnMinusBreadUnits);
        btnPlusBreadUnits = view.findViewById(R.id.btnPlusBreadUnits);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnOK = view.findViewById(R.id.btnOK);

        btnBreadUnits.setOnClickListener(this);
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
