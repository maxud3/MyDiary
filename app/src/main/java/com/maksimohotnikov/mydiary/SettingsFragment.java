package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SettingsFragment";

    //private View viewCoefficients;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle(R.string.settings);
        View viewCoefficients = view.findViewById(R.id.viewCoefficients);
        View viewCompensation = view.findViewById(R.id.viewCompensation);
        View viewActiveInsulin = view.findViewById(R.id.viewActiveInsulin);
        ImageView iwInfo = view.findViewById(R.id.iwInfo);

        viewCoefficients.setOnClickListener(this);
        viewCompensation.setOnClickListener(this);
        viewActiveInsulin.setOnClickListener(this);
        iwInfo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewCoefficients:
                mListener.openCoefficientFragment();
                break;
            case R.id.viewCompensation:
                mListener.openCompensationFragment();
                break;
            case R.id.viewActiveInsulin:
                mListener.openActiveInsulinFragment();
                break;
            case R.id.iwInfo:
                mListener.openInfoFragment();
        }
    }

    public interface OnFragmentInteractionListener{
        void openCoefficientFragment();
        void openCompensationFragment();
        void openActiveInsulinFragment();
        void openInfoFragment();
    }
    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


}
