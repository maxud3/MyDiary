package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.CoefficientFragment.*;
import static com.maksimohotnikov.mydiary.MainActivity.TAG;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SettingsFragment";
    private OnFragmentInteractionListener mListener;
    private SharedPreferences mSettings;
    @BindView(R.id.viewCoefficients) View viewCoefficients;
    @BindView(R.id.viewCompensation) View viewCompensation;
    @BindView(R.id.viewActiveInsulin) View viewActiveInsulin;
    @BindView(R.id.iwInfo) ImageView iwInfo;
    @BindView(R.id.tvCoefficients) TextView tvCoefficients;
    private Unbinder unbinder;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "SettingsFragment. onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.settings);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        viewCoefficients.setOnClickListener(this);
        viewCompensation.setOnClickListener(this);
        viewActiveInsulin.setOnClickListener(this);
        iwInfo.setOnClickListener(this);

        Log.d(TAG, "SettingsFragment. onCreateView");
        return view;
    }
    private void setCoefficient(){

        String morningCoefficient = mSettings.getString(MORNING_COEFFICIENT, "");
        String dayCoefficient = mSettings.getString(DAY_COEFFICIENT, "");
        String eveningCoefficient = mSettings.getString(EVENING_COEFFICIENT, "");
        String nightCoefficient = mSettings.getString(NIGHT_COEFFICIENT, "");
        if (morningCoefficient.equals("0.00") || dayCoefficient.equals("0.00")
            || eveningCoefficient.equals("0.00") || nightCoefficient.equals("0.00")){
            tvCoefficients.setText(R.string.not_set);
            tvCoefficients.setTextColor(Color.RED);
            Toast toast = Toast.makeText(
                    getActivity(), R.string.indicate_coefficient, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else {
            tvCoefficients.setText(R.string.set);
            Toast toast = Toast.makeText(
                    getActivity(), R.string.coefficient_saved, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "SettingsFragment. onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        Log.d(TAG, "SettingsFragment. onActivityCreated");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "SettingsFragment. onStart");
    }
    @Override
    public void onResume(){
        super.onResume();
        setCoefficient();
        Log.d(TAG, "SettingsFragment. onResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "SettingsFragment. onPause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "SettingsFragment. onStop");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG, "SettingsFragment. onDestroyView");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(TAG, "SettingsFragment. onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "SettingsFragment. onDetach");
    }

}
