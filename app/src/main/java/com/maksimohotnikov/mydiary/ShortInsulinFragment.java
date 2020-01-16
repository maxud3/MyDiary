package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


import static com.maksimohotnikov.mydiary.CoefficientFragment.APP_PREFERENCES;
import static com.maksimohotnikov.mydiary.CoefficientFragment.DAY_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.EVENING_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.MORNING_COEFFICIENT;
import static com.maksimohotnikov.mydiary.CoefficientFragment.NIGHT_COEFFICIENT;
import static com.maksimohotnikov.mydiary.MainActivity.TAG;


public class ShortInsulinFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.ShortInsulinFragment";
    private OnShortInsulinFragmentListener mListener;
    private Button btnPlusShortInsulin;
    private Button btnMinusShortInsulin;
    private EditText etShortInsulin;
    private float shortInsulin = 2.0f;
    private SharedPreferences mSettings;
    private String currentCoefficient;

    //Calendar date = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        // Текущее время
        JodaTimeAndroid.init(getActivity());
        LocalTime now = LocalTime.now();

        LocalTime afterMorningTime = LocalTime.parse("06:00");//заданное время
        LocalTime beforeMorningTime = LocalTime.parse("11:00");

        LocalTime afterDayTime = LocalTime.parse("12:00");//заданное время
        LocalTime beforeDayTime = LocalTime.parse("17:00");

        LocalTime afterEveningTime = LocalTime.parse("18:00");//заданное время
        LocalTime beforeEveningTime = LocalTime.parse("23:00");

        //LocalTime afterNightTime = LocalTime.parse("00:00");//заданное время
        //LocalTime beforeNightTime = LocalTime.parse("06:00");

        if (now.isAfter(afterMorningTime) && now.isBefore(beforeMorningTime)){
            currentCoefficient = mSettings.getString(MORNING_COEFFICIENT, "");
            Toast.makeText(getActivity(), "MorningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "MorningTime now " + now);
        }else if (now.isAfter(afterDayTime) && now.isBefore(beforeDayTime)){
            currentCoefficient = mSettings.getString(DAY_COEFFICIENT, "");
            Toast.makeText(getActivity(), "DayTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "DayTime " + now);
        }else if (now.isAfter(afterEveningTime) && now.isBefore(beforeEveningTime)){
            currentCoefficient = mSettings.getString(EVENING_COEFFICIENT, "");
            Toast.makeText(getActivity(), "EveningTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "EveningTime " + now);
        }else {
            currentCoefficient = mSettings.getString(NIGHT_COEFFICIENT, "");
            Toast.makeText(getActivity(), "NightTime " + now, Toast.LENGTH_LONG).show();
            Log.d(TAG, "NightTime " + now);
        }
        Log.d(TAG, "onCreate: ShortInsulinFragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_short_insulin, container, false);

        // Форматирование времени как "часы:минуты:секунды"
        /*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date strDate1 = sdf.parse()*/


        //ZoneId z = ZoneId.of("Pacific/Auckland");
        //LocalTime time = LocalTime.now(z);
        //LocalTime time = LocalTime.now();

        btnMinusShortInsulin = view.findViewById(R.id.btnMinusLongInsulin);
        btnPlusShortInsulin = view.findViewById(R.id.btnPlusLongInsulin);
        etShortInsulin = view.findViewById(R.id.etLongInsulin);
        View viewLongInsulin = view.findViewById(R.id.viewLongInsulin);

        String stShortInsulin = Float.toString(shortInsulin);
        etShortInsulin.setText(stShortInsulin);

        btnMinusShortInsulin.setOnClickListener(this);
        btnPlusShortInsulin.setOnClickListener(this);
        viewLongInsulin.setOnClickListener(this);

        Log.d(TAG, "onCreateView: ShortInsulinFragment");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinusLongInsulin:
                if (shortInsulin > 1.5f) {
                    btnPlusShortInsulin.setEnabled(true);
                    shortInsulin = SugarInBloodFragment.decrement(shortInsulin);
                    etShortInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundDown(shortInsulin, 1)));
                } else {
                    btnMinusShortInsulin.setEnabled(false);
                }
                break;
            case R.id.btnPlusLongInsulin:
                if (shortInsulin < 2.4f) {
                    btnMinusShortInsulin.setEnabled(true);
                    shortInsulin = SugarInBloodFragment.increment(shortInsulin);
                    etShortInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundUp(shortInsulin, 1)));
                } else {
                    btnPlusShortInsulin.setEnabled(false);
                }
                break;
            case R.id.viewLongInsulin:
                mListener.openLongInsulinFragment();
                break;
            case R.id.btnFurther:
                mListener.openTotalRecordFragment();
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
        Log.d(TAG, "onDestroy: ShortInsulinFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ShortInsulinFragment");
    }
}
