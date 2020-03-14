package com.maksimohotnikov.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SettingsActivity extends AppCompatActivity /*implements
        SettingsFragment.OnFragmentInteractionListener*/{

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
                    .add(R.id.container, fragment, MySettingFragment/*SettingsFragment*/.TAG_FRAGMENT)
                    .addToBackStack(MySettingFragment/*SettingsFragment*/.TAG_FRAGMENT)
                    .commit();
        }
    }

   /* @Override
    public void openCoefficientFragment() {
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof SettingsFragment){
            Fragment fragmentReplace;
            fragmentReplace = new CoefficientFragment();

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace, CoefficientFragment.TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(CoefficientFragment.TAG_FRAGMENT)
                    .commit();
        }
        Toast toast = Toast.makeText(this, "click on viewCoefficients", Toast.LENGTH_LONG);
        toast.show();
    }*/

   /* @Override
    public void openCompensationFragment() {
        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof SettingsFragment){
            Fragment fragmentReplace;
            fragmentReplace = new CompensationFragment();

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace,CompensationFragment.TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(CompensationFragment.TAG_FRAGMENT)
                    .commit();

        }
        Toast toast = Toast.makeText(this, "click on viewCompensation", Toast.LENGTH_LONG);
        toast.show();
    }*/

    /*@Override
    public void openActiveInsulinFragment() {
        Toast toast = Toast.makeText(this, "click on viewActiveInsulin", Toast.LENGTH_LONG);
        toast.show();
    }*/

   /* @Override
    public void openInfoFragment() {
        Toast toast = Toast.makeText(this, "click on imageViewInfo", Toast.LENGTH_LONG);
        toast.show();
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
    public void onBackPressed(){
        //FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1){
            fm.popBackStack();
        }else {
            finish();
        }
    }
}
