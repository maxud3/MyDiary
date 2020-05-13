package com.maksimohotnikov.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TotalRecordActivity extends AppCompatActivity {

    final String TAG = "myLogs";
    private Unbinder unbinder;

    @BindView(R.id.et_glucose_level)
    EditText etSugarInBlood;
    @BindView(R.id.et_total_bread_units)
    EditText etBreadUnits;
    @BindView(R.id.et_short_insulin)
    EditText etShortInsulin;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_record);

        unbinder = ButterKnife.bind(this);

        dbHelper = new DBHelper(this);
    }

    @OnClick({R.id.btn_read, R.id.btn_further})
    void onClick(View view){
        String sugar = etSugarInBlood.getText().toString();
        String breadUnits = etBreadUnits.getText().toString();
        String shortInsulin = etShortInsulin.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        switch (view.getId()){
            case R.id.btn_further:
                contentValues.put(DBHelper.KEY_SUGAR, sugar);
                contentValues.put(DBHelper.KEY_BREAD_UNITS, breadUnits);
                contentValues.put(DBHelper.KEY_SHORT_INSULIN, shortInsulin);

                database.insert(DBHelper.TABLE_NAME, null, contentValues);
                Log.d(TAG, "onClick кнопка далее");
                break;
            case R.id.btn_read:
                Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null,
                        null, null, null);

                if(cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int sugarIndex = cursor.getColumnIndex(DBHelper.KEY_SUGAR);
                    int breadUnitsIndex = cursor.getColumnIndex(DBHelper.KEY_BREAD_UNITS);
                    int shortInsulinIndex = cursor.getColumnIndex(DBHelper.KEY_SHORT_INSULIN);
                    do {
                        Log.d(TAG, "ID = " + cursor.getInt(idIndex)
                                + ", сахар = " + cursor.getString(sugarIndex)
                                + ", ХЕ = " + cursor.getString(breadUnitsIndex)
                                + ", короткий инсулин = " + cursor.getString(shortInsulinIndex));
                    } while (cursor.moveToNext());
                }else
                    Log.d(TAG, "0 rows");
                cursor.close();
                break;
        }
        dbHelper.close();

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(MainActivity.TAG, "onDestroy: ShortInsulinFragment");
    }
}
