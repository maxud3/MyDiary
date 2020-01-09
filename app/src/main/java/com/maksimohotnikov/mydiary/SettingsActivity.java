package com.maksimohotnikov.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SettingsActivity extends AppCompatActivity implements
        SettingsFragment.OnFragmentInteractionListener{

    private FragmentManager fm;

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
            fragment = new SettingsFragment();
            fm.beginTransaction()
                    .add(R.id.container, fragment, SettingsFragment.TAG_FRAGMENT)
                    .addToBackStack(SettingsFragment.TAG_FRAGMENT)
                    .commit();
        }
    }

    @Override
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
    }

    @Override
    public void openCompensationFragment() {
        Toast toast = Toast.makeText(this, "click on viewCompensation", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void openActiveInsulinFragment() {
        Toast toast = Toast.makeText(this, "click on viewActiveInsulin", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void openInfoFragment() {
        Toast toast = Toast.makeText(this, "click on imageViewInfo", Toast.LENGTH_LONG);
        toast.show();
    }
}
