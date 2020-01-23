package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import net.danlew.android.joda.JodaTimeAndroid;
import org.joda.time.LocalTime;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.CoefficientFragment.APP_PREFERENCES;
import static com.maksimohotnikov.mydiary.CoefficientFragment.DAY_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.EVENING_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.MORNING_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.NIGHT_COEFFICIENT;
import static com.maksimohotnikov.mydiary.MainActivity.TAG;
import static com.maksimohotnikov.mydiary.SugarInBloodFragment.*;


public class ShortInsulinFragment extends Fragment implements View.OnClickListener {

    public static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.ShortInsulinFragment";
    public static final String BREADUNITS = "breadUnits";
    private OnShortInsulinFragmentListener mListener;
    private SharedPreferences mSettings;
    @BindView(R.id.btnPlusShortInsulin) Button btnPlusShortInsulin;
    @BindView(R.id.btnMinusShortInsulin) Button btnMinusShortInsulin;
    @BindView(R.id.etShortInsulin) EditText etShortInsulin;
    @BindView(R.id.viewClickShortInsulin) View viewClickShortInsulin;
    private Unbinder unbinder;
    private float currentCoefficient;
    private float insulinEat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        selectCoefficient();

        float breadUnits = Float.valueOf(getArguments().getString(BREADUNITS));
        insulinEat = insulinEat(breadUnits, currentCoefficient);

        Log.d(TAG, "onCreate: ShortInsulinFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_short_insulin, container, false);
        unbinder = ButterKnife.bind(this, view);

        etShortInsulin.setText(String.valueOf(roundUp(insulinEat, 1)));
        btnMinusShortInsulin.setOnClickListener(this);
        btnPlusShortInsulin.setOnClickListener(this);
        viewClickShortInsulin.setOnClickListener(this);
        Log.d(TAG, "onCreateView: ShortInsulinFragment");
        return view;
    }

    private float insulinEat (float breadUnits, float currentCoefficient){
        float defaultCoefficient = 0.00f;
        if (currentCoefficient > defaultCoefficient) {
            return breadUnits * currentCoefficient;
        } else {
            Toast toast = Toast.makeText(
                    getActivity(), R.string.dose_not_calculated, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return defaultCoefficient;
        }
    }
    private void selectCoefficient(){

        // Текущее время
        JodaTimeAndroid.init(getActivity());
        LocalTime now = LocalTime.now();

        LocalTime afterMorningTime = LocalTime.parse("06:00");//заданное время
        LocalTime beforeMorningTime = LocalTime.parse("11:00");

        LocalTime afterDayTime = LocalTime.parse("12:00");//заданное время
        LocalTime beforeDayTime = LocalTime.parse("17:00");

        LocalTime afterEveningTime = LocalTime.parse("18:00");//заданное время
        LocalTime beforeEveningTime = LocalTime.parse("23:00");

        if (now.isAfter(afterMorningTime) && now.isBefore(beforeMorningTime)){
            currentCoefficient = Float.valueOf(mSettings.getString(MORNING_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "MorningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "MorningTime now " + now);
        }else if (now.isAfter(afterDayTime) && now.isBefore(beforeDayTime)){
            currentCoefficient = Float.valueOf(mSettings.getString(DAY_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "DayTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "DayTime " + now);
        }else if (now.isAfter(afterEveningTime) && now.isBefore(beforeEveningTime)){
            currentCoefficient = Float.valueOf(mSettings.getString(EVENING_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "EveningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "EveningTime " + now);
        }else {
            currentCoefficient = Float.valueOf(mSettings.getString(NIGHT_COEFFICIENT, ""));
            Toast.makeText(getActivity(), "NightTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "NightTime " + now);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinusShortInsulin:
                decrementShortInsulin();
                break;
            case R.id.btnPlusShortInsulin:
                incrementShortInsulin();
                break;
            case R.id.viewClickShortInsulin:
                mListener.openLongInsulinFragment();
                break;
            case R.id.btnFurther:
                mListener.openTotalRecordFragment();
        }
    }

    private void decrementShortInsulin(){
        float shortInsulin = Float.valueOf(etShortInsulin.getText().toString());
        if (shortInsulin >0.0f){
            btnPlusShortInsulin.setEnabled(true);
            shortInsulin = decrement(shortInsulin);
            etShortInsulin.setText(String.valueOf(roundUp(shortInsulin, 1)));
        }else {
            btnMinusShortInsulin.setEnabled(false);
        }
    }

    private void incrementShortInsulin(){
        float shortInsulin = Float.valueOf(etShortInsulin.getText().toString());
        if (shortInsulin <1.0f){
            btnMinusShortInsulin.setEnabled(true);
            shortInsulin = increment(shortInsulin);
            etShortInsulin.setText(String.valueOf(roundUp(shortInsulin, 1)));
        }else {
            btnPlusShortInsulin.setEnabled(false);
        }
    }
    public interface OnShortInsulinFragmentListener{
        void openLongInsulinFragment();
        void openTotalRecordFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.d(TAG, "onAttach: ShortInsulinFragment");
        if (context instanceof OnShortInsulinFragmentListener){
            mListener = (OnShortInsulinFragmentListener) context;
        } else {
            throw  new RuntimeException(context.toString()
                    + "must implement OnFragment1DataListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        Log.d(TAG, "onActivityCreated: ShortInsulinFragment");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart: ShortInsulinFragment");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: ShortInsulinFragment");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause: ShortInsulinFragment");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop: ShortInsulinFragment");
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ShortInsulinFragment");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(TAG, "onDestroy: ShortInsulinFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ShortInsulinFragment");
    }
}
