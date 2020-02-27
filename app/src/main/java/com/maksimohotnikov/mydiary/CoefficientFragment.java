package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import static com.maksimohotnikov.mydiary.MainActivity.TAG;

public class CoefficientFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.CoefficientFragment";
    static final String APP_PREFERENCES = "my_settings";
    static final String MORNING_COEFFICIENT = "morningCoefficient";
    static final String DAY_COEFFICIENT = "dayCoefficient";
    static final String EVENING_COEFFICIENT = "eveningCoefficient";
    static final String NIGHT_COEFFICIENT = "nightCoefficient";

    @BindView(R.id.et_morning_coefficient)
    EditText etMorningCoefficient;
    @BindView(R.id.et_day_coefficient)
    EditText etDayCoefficient;
    @BindView(R.id.et_evening_coefficient)
    EditText etEveningCoefficient;
    @BindView(R.id.et_night_coefficient)
    EditText etNightCoefficient;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CoefficientFragment. onCreate");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_coefficient, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.coefficients);

        loadCoefficient();
        Log.d(TAG, "CoefficientFragment. onCreateView");
    return view;
    }

    //Сохраняем коэффициенты
    @SuppressWarnings("ConstantConditions")
    private void saveCoefficients(){
        SharedPreferences.Editor prefEditor = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit();
        prefEditor.putString(MORNING_COEFFICIENT, morningCoefficient());
        prefEditor.putString(DAY_COEFFICIENT, dayCoefficient());
        prefEditor.putString(EVENING_COEFFICIENT, eveningCoefficient());
        prefEditor.putString(NIGHT_COEFFICIENT, nightCoefficient());
        prefEditor.apply();
    }

    private String morningCoefficient(){
        String defaultCoefficient = getString(R.string.default_coefficient);
        String morningCoefficient = etMorningCoefficient.getText().toString();
        if (morningCoefficient.equals("")) {
            morningCoefficient = defaultCoefficient;
        }
        return morningCoefficient;
    }

    private String dayCoefficient(){
        String defaultCoefficient = getString(R.string.default_coefficient);
        String dayCoefficient = etDayCoefficient.getText().toString();
        if (dayCoefficient.equals("")){
            dayCoefficient = defaultCoefficient;
        }
        return dayCoefficient;
    }

    private String eveningCoefficient(){
        String defaultCoefficient = getString(R.string.default_coefficient);
        String eveningCoefficient = etEveningCoefficient.getText().toString();
        if (eveningCoefficient.equals("")){
            eveningCoefficient = defaultCoefficient;
        }
        return eveningCoefficient;
    }

    private String nightCoefficient(){
        String defaultCoefficient = getString(R.string.default_coefficient);
        String nightCoefficient = etNightCoefficient.getText().toString();
        if (nightCoefficient.equals("")){
            nightCoefficient = defaultCoefficient;
        }
        return nightCoefficient;
    }

    //Загружаем коэффициенты
    @SuppressWarnings("ConstantConditions")
    private void loadCoefficient() {
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        etMorningCoefficient.setText(prefs.getString(MORNING_COEFFICIENT, ""));
        etDayCoefficient.setText(prefs.getString(DAY_COEFFICIENT, ""));
        etEveningCoefficient.setText(prefs.getString(EVENING_COEFFICIENT, ""));
        etNightCoefficient.setText(prefs.getString(NIGHT_COEFFICIENT, ""));
    }

    @Override
    public void onAttach(@NonNull Context context) {
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
        saveCoefficients();
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
