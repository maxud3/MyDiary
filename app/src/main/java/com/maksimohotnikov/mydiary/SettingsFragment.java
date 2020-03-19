package com.maksimohotnikov.mydiary;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import static com.maksimohotnikov.mydiary.CoefficientFragment.*;
import static com.maksimohotnikov.mydiary.CompensationFragment.SWITCH_COMPENSATION_INSULIN;
import static com.maksimohotnikov.mydiary.CompensationFragment.TARGET_GLUCOSE;
import static com.maksimohotnikov.mydiary.MainActivity.TAG;


public class SettingsFragment extends Fragment {

    //static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.SettingsFragment";
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.tv_coefficients)
    TextView tvCoefficients;
    @BindView(R.id.tv_compensation)
    TextView tvCompensation;
    @BindView(R.id.arrow_down_3)
    ImageView ivArrowUp;
    @BindView(R.id.tv_daily_dose_insulin_formula)
    TextView tvDailyDoseInsulinFormula;
    @BindView(R.id.arrow_down_3)
    ImageView ivArrowDown;
    @BindView(R.id.image_view_arrow_up)
    Unbinder unbinder;
    private boolean showHint = true;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "SettingsFragment. onCreate");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.settings);
        onOffCompensationInsulin();

        Log.d(TAG, "SettingsFragment. onCreateView");
        return view;
    }

    @OnClick({R.id.view_coefficients, R.id.view_compensation, R.id.view_active_insulin,
            R.id.iw_info, R.id.arrow_down_3, R.id.image_view_arrow_up})
    void viewOnClick(View view){
        switch (view.getId()){
            case R.id.view_compensation:
                mListener.openCompensationFragment();
                break;
            case R.id.view_coefficients:
                mListener.openCoefficientFragment();
            case R.id.view_active_insulin:
                mListener.openActiveInsulinFragment();
                break;
            case R.id.iw_info:
                mListener.openInfoFragment();
                break;
            case R.id.arrow_down_3:
                if (showHint) {
                    rotateUp(ivArrowDown);
                } else {
                    rotateDown(ivArrowDown);
                }
                break;
        }
    }
    //Вращаем стрелку вверх
    private void rotateUp(ImageView ivArrowDown) {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(ivArrowDown, "rotation", 0f, 180f);
        //rotate.setRepeatCount(10);  колличество вращений
        rotate.setDuration(100);
        rotate.start();
        tvDailyDoseInsulinFormula.setVisibility(View.VISIBLE);
        showHint = false;
    }

    //Вращаем стрелку вниз
    private void rotateDown(ImageView ivArrowDown) {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(ivArrowDown, "rotation", 180f, 360f);
        //rotate.setRepeatCount(10);  колличество вращений
        rotate.setDuration(100);
        rotate.start();
        tvDailyDoseInsulinFormula.setVisibility(View.GONE);
        showHint = true;
    }

    //олучаем коэффициенты из my_setting.xml
    @SuppressWarnings("ConstantConditions")
    private void getCoefficient(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String morningCoefficient = prefs.getString(MORNING_COEFFICIENT, "");
        String dayCoefficient = prefs.getString(DAY_COEFFICIENT, "");
        String eveningCoefficient = prefs.getString(EVENING_COEFFICIENT, "");
        String nightCoefficient = prefs.getString(NIGHT_COEFFICIENT, "");
        onOffCoefficient(morningCoefficient, dayCoefficient, eveningCoefficient, nightCoefficient);

    }

    //Отображаем указаны или нет коэффициенты
    private void onOffCoefficient(String morningCoefficient, String dayCoefficient,
                                  String eveningCoefficient, String nightCoefficient){
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
        }
    }

    //Отображаем включена или нет компенсация
    @SuppressWarnings("ConstantConditions")
    private void onOffCompensationInsulin(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean switchState = prefs.getBoolean(SWITCH_COMPENSATION_INSULIN, true);

        if (switchState) {
            String targetGlucose = prefs.getString(TARGET_GLUCOSE, "");
            tvCompensation.setText(getString(R.string.compensation_on, targetGlucose));
        }else {
            tvCompensation.setText(R.string.compensation_off);
        }
    }

    public interface OnFragmentInteractionListener{
        void openCoefficientFragment();
        void openCompensationFragment();
        void openActiveInsulinFragment();
        void openInfoFragment();
    }

    public void onAttach(@NonNull Context context) {
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
        getCoefficient();
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
        mListener = null;
        Log.d(TAG, "SettingsFragment. onDetach");
    }

}
