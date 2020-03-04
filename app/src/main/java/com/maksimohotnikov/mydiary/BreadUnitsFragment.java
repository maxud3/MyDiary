package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BreadUnitsFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.BreadUnitsFragment";
    private final String APP_PREFERENCES = "my_settings";
    private final String CARBOHYDRATES = "carbohydrates";
    private final String BREAD_UNITS = "breadUnits";
    private static final int DIGITS = 1;
    private OnBreadUnitsFragmentListener mListener;

    @BindView(R.id.np_bread_units)
    NumberPicker npBreadUnits;
    @BindView(R.id.tv_total_carbohydrates)
    TextView tvTotalCarbohydrates;
    @BindView(R.id.tv_total_bread_units)
    TextView tvTotalBreadUnits;
    @BindView(R.id.textView37)
    TextView tv37;
    private Unbinder unbinder;

    private String carbohydrates;

    public BreadUnitsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_bread_units, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadCarbohydratesAndBreadUnits();

        npBreadUnits.setMinValue(0);
        npBreadUnits.setMaxValue(100);
        npBreadUnits.setValue(Integer.parseInt(carbohydrates));
        npBreadUnits.setOnValueChangedListener((picker, oldVal, newVal) -> {
            float breadUnits = newVal / 12.0f;
            tvTotalCarbohydrates.setText(String.valueOf(newVal));
            tvTotalBreadUnits.setText(String.valueOf(SugarInBloodFragment.roundUp(breadUnits, DIGITS)));
        });
        String s = "2";
        String ss = "5";
        String sss = s + "." + ss;
        float f = Float.parseFloat(sss);
        float ff = f + 1.1f;
        tv37.setText(String.valueOf(ff));
        return view;
    }
    @OnClick(R.id.btn_further)
    void onClick(){
        mListener.openShortInsulinFragment();
    }
    public interface OnBreadUnitsFragmentListener{
        void openShortInsulinFragment();
    }
    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof OnBreadUnitsFragmentListener){
            mListener = (OnBreadUnitsFragmentListener) context;
        }else {
            throw  new RuntimeException(context.toString()
                    + "must implement OnFragment1DataListener");
        }
    }
    //Сохраняем углеводы и хлебные единици
    @SuppressWarnings("ConstantConditions")
    private void saveCarbohydratesAndBreadUnits(){
        String carbohydrates = String.valueOf(tvTotalCarbohydrates.getText());
        String breadUnits = String.valueOf(tvTotalBreadUnits.getText());
        SharedPreferences.Editor prefEditor = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit();
        prefEditor.putString(CARBOHYDRATES, carbohydrates);
        prefEditor.putString(BREAD_UNITS, breadUnits);
        prefEditor.apply();
    }
    //Загружаем предыдущие значения углеводов и хлебных единиц
    @SuppressWarnings("ConstantConditions")
    private void loadCarbohydratesAndBreadUnits(){
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        carbohydrates = prefs.getString(CARBOHYDRATES, getString(R.string.zero));
        tvTotalCarbohydrates.setText(carbohydrates);

        tvTotalBreadUnits.setText(prefs.getString(BREAD_UNITS, getString(R.string.zero_zero)));
    }
    @Override
    public void onPause(){
        super.onPause();
        saveCarbohydratesAndBreadUnits();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }
    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }
}
