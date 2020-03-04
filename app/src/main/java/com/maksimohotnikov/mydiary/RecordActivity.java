package com.maksimohotnikov.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import static com.maksimohotnikov.mydiary.MainActivity.TAG;


public class RecordActivity extends AppCompatActivity implements SugarInBloodFragment
        .OnSugarInBloodFragmentListener, MenuFragment.OpenInsulinFragment,
        BreadUnitsFragment.OnBreadUnitsFragmentListener,
        ShortInsulinFragment.OnShortInsulinFragmentListener,
        LongInsulinFragment.OnLongInsulinFragmentListener{

    private FragmentManager fm;

    @SuppressWarnings ("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment == null){
            fragment = new SugarInBloodFragment();
            fm.beginTransaction()
                    .add(R.id.container, fragment, SugarInBloodFragment.TAG_FRAGMENT)
                    .addToBackStack(SugarInBloodFragment.TAG_FRAGMENT)
                    .commit();
        }
        Log.d(TAG, "onCreate().RecordActivity");
    }

    @Override
    public void onSugarInBloodFragmentListener() {
        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof SugarInBloodFragment){
            Fragment fragmentReplace;
            fragmentReplace = new BreadUnitsFragment();

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace, BreadUnitsFragment.TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(BreadUnitsFragment.TAG_FRAGMENT)
                    .commit();
        }
    }
    @Override
    public void openShortInsulinFragment() {
        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof BreadUnitsFragment){
            Fragment fragmentReplace;
            fragmentReplace = new ShortInsulinFragment();

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace,ShortInsulinFragment.TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(ShortInsulinFragment.TAG_FRAGMENT)
                    .commit();
        }
    }
    @Override
    public void openInsulinFragment() {
        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof MenuFragment){
            Fragment fragmentReplace;
            fragmentReplace = new ShortInsulinFragment();

            /*Bundle bundle = new Bundle();
            bundle.putString(ShortInsulinFragment.BREAD_UNITS, string);
            fragmentReplace.setArguments(bundle);*/

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace, ShortInsulinFragment.TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(ShortInsulinFragment.TAG_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void openLongInsulinFragment() {
        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof ShortInsulinFragment){
            Fragment fragmentReplace;
            fragmentReplace = new LongInsulinFragment();

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace, LongInsulinFragment.TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(LongInsulinFragment.TAG_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void openTotalRecordFragment() {
        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container);
        if (fragment instanceof ShortInsulinFragment || fragment instanceof LongInsulinFragment){
            Fragment fragmentReplace;
            fragmentReplace = new TotalRecordFragment();

            fm.beginTransaction()
                    .replace(R.id.container, fragmentReplace,TotalRecordFragment.TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(TotalRecordFragment.TAG_FRAGMENT)
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
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1){
            fm.popBackStack();
        }else {
            finish();
        }
    }

   @Override
   protected void onStart() {
       super.onStart();
       Log.i(TAG, "onStart().RecordActivity");
   }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume().RecordActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause().RecordActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop().RecordActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart().RecordActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy().RecordActivity");
    }



}
