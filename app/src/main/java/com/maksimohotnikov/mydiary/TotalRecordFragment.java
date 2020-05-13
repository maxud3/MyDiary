package com.maksimohotnikov.mydiary;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.SettingConstants.APP_PREFERENCES;
import static com.maksimohotnikov.mydiary.SettingConstants.BREAD_UNITS;
import static com.maksimohotnikov.mydiary.SettingConstants.LONG_INSULIN_DOSE;
import static com.maksimohotnikov.mydiary.SettingConstants.SHORT_INSULIN_DOSE;
import static com.maksimohotnikov.mydiary.SettingConstants.SUGAR_IN_BLOOD;

public class TotalRecordFragment extends Fragment {
    final String TAG = "myLogs";
    final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.TotalRecordFragment";
    private Unbinder unbinder;
    @BindView(R.id.current_date)
    TextView tvCurrentDate;
    @BindView(R.id.current_time)
    TextView tvCurrentTime;
    @BindString(R.string.zero_zero)
    String zeroZero;
    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @BindView(R.id.time_picker)
    TimePicker timePicker;
    @BindView(R.id.type_record_picker)
    NumberPicker typeRecordPicker;
    @BindView(R.id.tv_type_record)
    TextView tvTypeRecord;
    @BindView(R.id.et_glucose_level)
    EditText etGlucoseInBlood;
    @BindView(R.id.et_short_insulin)
    EditText etShortInsulin;
    @BindView(R.id.et_long_insulin)
    EditText etLongInsulin;
    @BindView(R.id.et_total_bread_units)
    EditText etTotalBreadUnits;
    @BindView(R.id.et_comment)
    EditText etComment;
    private int mMonth;
    private int mDay;
    private int mYear;
    private int mHour;
    private int mMinute;
    private String sugarInBlood;
    private String shortInsulin;
    private String longInsulin;
    private String breadUnits;
    private boolean visibleDatePicker = false;
    private boolean visibleTimePicker = false;
    private boolean visibleTypeRecordPicker = false;
    private SharedPreferences settings;

    DBHelper dbHelper;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //создаем БД
        dbHelper = new DBHelper(getActivity());
        settings = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        getSettings();
        timePicker.setIs24HourView(true);
        setTvCurrentDateTime();
        setCurrentTime();

        etGlucoseInBlood.setText(sugarInBlood);
        etShortInsulin.setText(shortInsulin);
        etLongInsulin.setText(longInsulin);
        etTotalBreadUnits.setText(breadUnits);



        Calendar today = Calendar.getInstance();
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), (view1, year, monthOfYear, dayOfMonth) ->
                        tvCurrentDate.setText(new StringBuilder().append(pad(dayOfMonth))
                        .append(". ").append(pad(monthOfYear + 1)).append(". ")
                        .append(year)));

        timePicker.setOnTimeChangedListener((view12, hourOfDay, minute) ->
                tvCurrentTime.setText(new StringBuilder().append(pad(hourOfDay))
                .append(" : ").append(pad(minute))));

        //Picker: тип записи
        String[] typeRecord = getResources().getStringArray(R.array.type_record);
        typeRecordPicker.setMinValue(0);
        typeRecordPicker.setMaxValue(18);
        typeRecordPicker.setDisplayedValues(typeRecord);
        typeRecordPicker.setValue(18);
        //Устанавливаем выбранный тип записи
        typeRecordPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            //int i = newVal;
            String s = typeRecord[newVal];
            tvTypeRecord.setText(s);
        });
        return view;
    }
    //Добавляем 0 перед однозначным числом месяца, часа, минут.
    private String pad(int c){
        if (c >= 10){
            return String.valueOf(c);
        }else {
            return "0" + c;
        }
    }
    /*private void callDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener)getParentFragment() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        })
    }*/
    //устанавливаем текущую дату
    private void setTvCurrentDateTime(){
        GregorianCalendar grCalendar = new GregorianCalendar();
        mYear = grCalendar.get(Calendar.YEAR);
        mMonth = grCalendar.get(Calendar.MONTH);
        mDay = grCalendar.get(Calendar.DAY_OF_MONTH);
        tvCurrentDate.setText(new StringBuilder().append(pad(mDay)).append(". ")
        .append(pad(mMonth + 1)).append(". ").append(mYear));
    }
    //устанавливаем текущее время
    private void setCurrentTime(){
        GregorianCalendar grCalendar = new GregorianCalendar();
        mHour = grCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = grCalendar.get(Calendar.MINUTE);
        tvCurrentTime.setText(new StringBuilder().append(pad(mHour)).append(" : ")
        .append(pad(mMinute)));

    }
    @OnClick({R.id.current_date, R.id.current_time, R.id.tv_type_record, R.id.btn_further, R.id.btn_read})
    void onClick(View view){
        String date = tvCurrentDate.getText().toString();
        String time = tvCurrentTime.getText().toString();
        String sugar = etGlucoseInBlood.getText().toString();
        String breadUnits = etTotalBreadUnits.getText().toString();
        String shortInsulin = etShortInsulin.getText().toString();
        String longInsulin = etLongInsulin.getText().toString();
        String comment = etComment.getText().toString();



        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (view.getId()) {
            case R.id.current_date:
                if (!visibleDatePicker) {
                    datePicker.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.GONE);
                    visibleDatePicker = true;
                    visibleTimePicker = false;
                } else {
                    datePicker.setVisibility(View.GONE);
                    visibleDatePicker = false;
                }
                break;
            case R.id.current_time:
                if (!visibleTimePicker){
                    timePicker.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.GONE);
                    visibleTimePicker = true;
                    visibleDatePicker = false;
                }else {
                    timePicker.setVisibility(View.GONE);
                    visibleTimePicker = false;
                }
                break;
            case R.id.tv_type_record:
                if (!visibleTypeRecordPicker){
                    typeRecordPicker.setVisibility(View.VISIBLE);
                    visibleTypeRecordPicker = true;
                }else {
                    typeRecordPicker.setVisibility(View.GONE);
                    visibleTypeRecordPicker = false;
                }
                break;
            case R.id.btn_further:
                contentValues.put(DBHelper.KEY_DATE, date);
                contentValues.put(DBHelper.KEY_TIME, time);
                contentValues.put(DBHelper.KEY_SUGAR, sugar);
                contentValues.put(DBHelper.KEY_BREAD_UNITS, breadUnits);
                contentValues.put(DBHelper.KEY_SHORT_INSULIN, shortInsulin);
                contentValues.put(DBHelper.KEY_LONG_INSULIN, longInsulin);
                contentValues.put(DBHelper.KEY_COMMENT, comment);

                database.insert(DBHelper.TABLE_NAME, null, contentValues);

                Log.d(TAG, "onClick кнопка далее");
                break;
            case R.id.btn_read:
                Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null,
                        null, null, null, null);

                if(cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
                    int sugarIndex = cursor.getColumnIndex(DBHelper.KEY_SUGAR);
                    int breadUnitsIndex = cursor.getColumnIndex(DBHelper.KEY_BREAD_UNITS);
                    int shortInsulinIndex = cursor.getColumnIndex(DBHelper.KEY_SHORT_INSULIN);
                    int longInsulinIndex = cursor.getColumnIndex(DBHelper.KEY_LONG_INSULIN);
                    int commentInsulinIndex = cursor.getColumnIndex(DBHelper.KEY_COMMENT);
                        do {
                            Log.d(TAG, "ID = " + cursor.getInt(idIndex)
                            + ", дата = " + cursor.getString(dateIndex)
                            + ", время = " + cursor.getString(timeIndex)
                            + ", сахар = " + cursor.getString(sugarIndex)
                            + ", ХЕ = " + cursor.getString(breadUnitsIndex)
                            + ", короткий инсулин = " + cursor.getString(shortInsulinIndex)
                            + ", длиный инсулин = " + cursor.getString(longInsulinIndex)
                            + ", комментарий = " + cursor.getString(commentInsulinIndex));
                        } while (cursor.moveToNext());
                }else
                    Log.d(TAG, "0 rows");
                    cursor.close();

        }
        dbHelper.close();
    }
    private void getSettings(){
        sugarInBlood = settings.getString(SUGAR_IN_BLOOD, zeroZero);
        shortInsulin = settings.getString(SHORT_INSULIN_DOSE, zeroZero);
        longInsulin = settings.getString(LONG_INSULIN_DOSE, zeroZero);
        breadUnits = settings.getString(BREAD_UNITS, zeroZero);
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(MainActivity.TAG, "onDestroy: ShortInsulinFragment");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}
