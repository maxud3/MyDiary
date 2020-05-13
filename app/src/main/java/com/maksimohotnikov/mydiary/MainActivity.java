package com.maksimohotnikov.mydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.format.DateUtils;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity  {

    static final String TAG = "myLogs";
    Calendar date = Calendar.getInstance();
    private Unbinder unbinder;
    @BindView (R.id.current_date)
    TextView currentDate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    List<State> states = new ArrayList<>();
    //StateAdapter stateAdapter;



    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate().MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.diary_actionbar_title);

        setInitialRecord();
        //создаем адаптер
        DataAdapter adapter = new DataAdapter(this, states);
        //устанавливаем адаптер
        recyclerView.setAdapter(adapter);
        //удаляем свайпом item RecyclerView
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                states.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_view_menu, menu);

    }

   /* @Override
    public boolean onContextItemSelected(MenuItem item){
        *//*AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();*//*
        switch (item.getItemId()){
            case R.id.open:
                String s = "open";
                tvMenu.setText(s);
                return true;
            case R.id.delete:
                String ss = "delete";
                tvMenu.setText(ss);
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }*/

    private void setInitialRecord(){
        states.add(new State("08 : 00", "4.4", "6.5", "3.0"));
        states.add(new State("13 : 00", "5.0", "7.1", "2.0"));
        states.add(new State("18 : 00", "4.8", "7.5", "2.0"));
        states.add(new State("08 : 00", "4.4", "6.5", "3.0"));
        states.add(new State("13 : 00", "5.0", "7.1", "2.0"));
        states.add(new State("18 : 00", "4.8", "7.5", "2.0"));
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
        currentDate.setText(DateUtils.formatDateTime(this,
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

    @OnClick({R.id.btnAddRecord, R.id.btnSettings, R.id.current_date})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.current_date:
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
