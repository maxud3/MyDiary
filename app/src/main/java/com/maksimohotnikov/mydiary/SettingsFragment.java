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

import static com.maksimohotnikov.mydiary.CoefficientFragment.APP_PREFERENCES;
import static com.maksimohotnikov.mydiary.CoefficientFragment.DAY_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.EVENING_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.MORNING_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.NIGHT_COEFFICIENT;
import static com.maksimohotnikov.mydiary.MainActivity.TAG;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SettingsFragment";

    private SharedPreferences mSettings;

    //private View viewCoefficients;

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
        getActivity().setTitle(R.string.settings);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        View viewCoefficients = view.findViewById(R.id.viewCoefficients);
        View viewCompensation = view.findViewById(R.id.viewCompensation);
        View viewActiveInsulin = view.findViewById(R.id.viewActiveInsulin);
        ImageView iwInfo = view.findViewById(R.id.iwInfo);

        viewCoefficients.setOnClickListener(this);
        viewCompensation.setOnClickListener(this);
        viewActiveInsulin.setOnClickListener(this);
        iwInfo.setOnClickListener(this);

        //onCoefficient();

        Log.d(TAG, "SettingsFragment. onCreateView");
        return view;
    }
    private void onCoefficient(){
        TextView tvCoefficients = getView().findViewById(R.id.tvCoefficients);
        String morningCoefficient = mSettings.getString(MORNING_COEFFICIENT, "");
        String dayCoefficient = mSettings.getString(DAY_COEFFICIENT, "");
        String eveningCoefficient = mSettings.getString(EVENING_COEFFICIENT, "");
        String nightCoefficient = mSettings.getString(NIGHT_COEFFICIENT, "");
        if (morningCoefficient.equals("") || dayCoefficient.equals("")
            || eveningCoefficient.equals("") || nightCoefficient.equals("")){
            tvCoefficients.setText(R.string.not_set);
            tvCoefficients.setTextColor(Color.RED);
            Toast toast = Toast.makeText(
                    getActivity(), R.string.indicate_coefficient, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else {
            tvCoefficients.setText(R.string.set);
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
    private OnFragmentInteractionListener mListener;

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
        onCoefficient();
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
        Log.d(TAG, "SettingsFragment. onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "SettingsFragment. onDetach");
    }

}
