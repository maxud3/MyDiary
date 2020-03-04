package com.maksimohotnikov.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateUtils;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.Menu;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
//import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "myLogs";
    static final String APP_PREFERENCES = "my_settings";
    //static final String MORNING_COEFFICIENT = "morningCoefficient";
    //static final String DAY_COEFFICIENT = "dayCoefficient";
    //static final String EVENING_COEFFICIENT = "eveningCoefficient";
    //static final String NIGHT_COEFFICIENT = "nightCoefficient";
    //static final String SWITCH_COMPENSATION_INSULIN = "switchCompensationInsulin";
    //static final String TARGET_GLUCOSE = "targetGlucose";
    //static final String BOTTOM_LINE = "bottomLine";
    //static final String TOP_LINE = "topLine";
    //static final String SENSITIVITY_COEFFICIENT = "sensitivityCoefficient";
    //static final String DAILY_DOSE_INSULIN = "dailyDoseInsulin";
    //static final String CARBOHYDRATES_IN_BREAD_UNIT = "carbohydratesInBreadUnit";




    //SharedPreferences settings;

    Calendar date = Calendar.getInstance();
    @BindView (R.id.currentDateTime) TextView currentDateTime;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private Unbinder unbinder;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate().MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        //settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.diary_actionbar_title);

        setInitialDateTime();
    }

    // отображаем диалоговое окно для выбора даты
    public void setData(){
        new DatePickerDialog(MainActivity.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime(){
        currentDateTime.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnAddRecord, R.id.btnSettings, R.id.currentDateTime})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.currentDateTime:
                Log.d(TAG, "currentDateTime");
                setData();
                break;
            case R.id.btnAddRecord:
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
                Log.d(TAG, "btnAddRecord");
                break;
            case R.id.btnSettings:
                Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent1);
                break;
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart().MainActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume().MainActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause().MainActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop().MainActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart().MainActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        Log.i(TAG, "onDestroy().MainActivity");
    }
}
