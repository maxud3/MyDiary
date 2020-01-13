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

import static com.maksimohotnikov.mydiary.MainActivity.TAG;

public class CoefficientFragment extends Fragment {

    public static final  String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.CoefficientFragment";
    public static final String APP_PREFERENCES = "mysettings";
    public static final String MORNING_COEFFICIENT = "morningCoefficient";
    public static final String DAY_COEFFICIENT = "dayCoefficient";
    public static final String EVENING_COEFFICIENT = "eveningCoefficient";
    public static final String NIGHT_COEFFICIENT = "nightCoefficient";
    private SharedPreferences mSettings;
    private EditText etMorningCoefficient;
    private EditText etDayCoefficient;
    private EditText etEveningCoefficient;
    private EditText etNightCoefficient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CoefficientFragment. onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_coefficient, container, false);
        getActivity().setTitle(R.string.coefficients);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        etMorningCoefficient = view.findViewById(R.id.etMorningCoefficient);
        etDayCoefficient = view.findViewById(R.id.etDayCoefficient);
        etEveningCoefficient = view.findViewById(R.id.etEveningCoefficient);
        etNightCoefficient = view.findViewById(R.id.etNightCoefficient);
        Log.d(TAG, "CoefficientFragment. onCreateView");
    return view;
    }

    private void onSaveCoefficients(){
        onSaveMorningCoefficient();
        onSaveDayCoefficient();
        onSaveEveningCoefficient();
        onSaveNightCoefficient();
    }
    private void onSaveMorningCoefficient(){
        String morningCoefficient = etMorningCoefficient.getText().toString();
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putString(MORNING_COEFFICIENT, morningCoefficient);
        prefEditor.apply();
        Toast toast = Toast.makeText(getActivity(), "onClick saveMorningCoefficient()", Toast.LENGTH_LONG);
        toast.show();
    }

    private void onSaveDayCoefficient(){
        String dayCoefficient = etDayCoefficient.getText().toString();
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putString(DAY_COEFFICIENT, dayCoefficient);
        prefEditor.apply();
    }
    private void onSaveEveningCoefficient(){
        String eveningCoefficient = etEveningCoefficient.getText().toString();
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putString(EVENING_COEFFICIENT, eveningCoefficient);
        prefEditor.apply();
    }
    private void onSaveNightCoefficient(){
        String nightCoefficient = etNightCoefficient.getText().toString();
        SharedPreferences.Editor prefEditor = mSettings.edit();
        prefEditor.putString(NIGHT_COEFFICIENT, nightCoefficient);
        prefEditor.apply();
    }
    private void onSetCoefficient(){
        onSetMorningCoefficient();
        onSetDayCoefficient();
        onSetEveningCoefficient();
        onSetNightCoefficient();
    }
    private void onSetMorningCoefficient(){
        String st = mSettings.getString(MORNING_COEFFICIENT, "");
        etMorningCoefficient.setText(st);
    }

    private void onSetDayCoefficient(){
        String st = mSettings.getString(DAY_COEFFICIENT, "");
        etDayCoefficient.setText(st);
    }

    private void onSetEveningCoefficient(){
        String st = mSettings.getString(EVENING_COEFFICIENT, "");
        etEveningCoefficient.setText(st);
    }
    private void onSetNightCoefficient(){
        String st = mSettings.getString(NIGHT_COEFFICIENT, "");
        etNightCoefficient.setText(st);
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
        onSetCoefficient();
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
        Log.d(TAG, "CoefficientFragment. onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "CoefficientFragment. onDetach");
    }

}
