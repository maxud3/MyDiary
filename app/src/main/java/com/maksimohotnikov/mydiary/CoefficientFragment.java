package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.MainActivity.TAG;

public class CoefficientFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.CoefficientFragment";
    static final String APP_PREFERENCES = "mysettings";
    static final String MORNING_COEFFICIENT = "morningCoefficient";
    static final String DAY_COEFFICIENT = "dayCoefficient";
    static final String EVENING_COEFFICIENT = "eveningCoefficient";
    static final String NIGHT_COEFFICIENT = "nightCoefficient";
    private SharedPreferences mSettings;
    @BindView(R.id.etMorningCoefficient) EditText etMorningCoefficient;
    @BindView(R.id.etDayCoefficient) EditText etDayCoefficient;
    @BindView(R.id.etEveningCoefficient) EditText etEveningCoefficient;
    @BindView(R.id.etNightCoefficient) EditText etNightCoefficient;
    @BindString(R.string.default_coefficient) String default_coefficient;
    Unbinder unbinder;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        //SharedPreferences.Editor prefEditor = mSettings.edit();
        //prefEditor.clear();
        Log.d(TAG, "CoefficientFragment. onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_coefficient, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.coefficients);

        onSetCoefficient();
        Log.d(TAG, "CoefficientFragment. onCreateView");
    return view;
    }

    private void onSaveCoefficients(){
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putString(MORNING_COEFFICIENT, morningCoefficient());
        prefEditor.putString(DAY_COEFFICIENT, dayCoefficient());
        prefEditor.putString(EVENING_COEFFICIENT, eveningCoefficient());
        prefEditor.putString(NIGHT_COEFFICIENT, nightCoefficient());
        prefEditor.apply();

    }
    private String morningCoefficient(){
        String morningCoefficient = etMorningCoefficient.getText().toString();
        if (morningCoefficient.equals("")) {
            morningCoefficient = default_coefficient;
        }
        return morningCoefficient;
    }

    private String dayCoefficient(){
        String dayCoefficient = etDayCoefficient.getText().toString();
        if (dayCoefficient.equals("")){
            dayCoefficient = default_coefficient;
        }
        return dayCoefficient;
    }
    private String eveningCoefficient(){
        String eveningCoefficient = etEveningCoefficient.getText().toString();
        if (eveningCoefficient.equals("")){
            eveningCoefficient = default_coefficient;
        }
        return eveningCoefficient;
    }
    private String nightCoefficient(){
        String nightCoefficient = etNightCoefficient.getText().toString();
        if (nightCoefficient.equals("")){
            nightCoefficient = default_coefficient;
        }
        return nightCoefficient;
    }
    private void onSetCoefficient() {
        etMorningCoefficient.setText(mSettings.getString(MORNING_COEFFICIENT, ""));
        etDayCoefficient.setText(mSettings.getString(DAY_COEFFICIENT, ""));
        etEveningCoefficient.setText(mSettings.getString(EVENING_COEFFICIENT, ""));
        etNightCoefficient.setText(mSettings.getString(NIGHT_COEFFICIENT, ""));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "CoefficientFragment. onAttach");
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        Log.d(TAG, "CoefficientFragment. onActivityCreated");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "CoefficientFragment. onStart");
    }
    @Override
    public void onResume(){
        super.onResume();

        Log.d(TAG, "CoefficientFragment. onResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        onSaveCoefficients();
        Log.d(TAG, "CoefficientFragment. onPause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "CoefficientFragment. onStop");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG, "CoefficientFragment. onDestroyView");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(TAG, "CoefficientFragment. onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "CoefficientFragment. onDetach");
    }

}
