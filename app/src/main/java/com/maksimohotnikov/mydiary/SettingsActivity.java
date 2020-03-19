package com.maksimohotnikov.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SettingsActivity extends AppCompatActivity {

    private FragmentManager fm;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment == null){
            fragment = new MySettingFragment()/*SettingsFragment()*/;
            fm.beginTransaction()
                    .add(R.id.container, fragment, MySettingFragment.TAG_FRAGMENT)
                    .addToBackStack(MySettingFragment.TAG_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
    public void onBackPressed(){
        if (fm.getBackStackEntryCount() > 1){
            fm.popBackStack();
        }else {
            finish();
        }
    }
}
