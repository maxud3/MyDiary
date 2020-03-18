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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.maksimohotnikov.mydiary.SettingConstants.*;


public class LongInsulinFragment extends Fragment {

    final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.LongInsulinFragment";

    private SharedPreferences settings;
    private OnLongInsulinFragmentListener mListener;
    @BindView(R.id.number_picker_long_insulin)
    NumberPicker npLongInsulin;
    private String longInsulinDose;
    private Unbinder unbinder;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getActivity()
                .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        setLongInsulinDose();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_long_insulin, container, false);
        unbinder =  ButterKnife.bind(this, view);

        npLongInsulin.setMinValue(0);
        npLongInsulin.setMaxValue(50);
        npLongInsulin.setValue(Integer.parseInt(longInsulinDose));
        return view;
    }

    //Сохраняем дозу длинного инсулина
    private void saveLongInsulinDose(){
            String longInsulinDose = String.valueOf(npLongInsulin.getValue());
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putString(LONG_INSULIN_DOSE, longInsulinDose);
            prefEditor.apply();

    }
    //устанавливаем предыдущую дозу длинного инсулина
    private void setLongInsulinDose(){
        longInsulinDose =  settings.getString(LONG_INSULIN_DOSE, "0");
    }
    @OnClick(R.id.btn_further)
    void onClick(){
                mListener.openTotalRecordFragment();
    }

    public interface OnLongInsulinFragmentListener{
        void openTotalRecordFragment();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLongInsulinFragmentListener) {
            mListener = (OnLongInsulinFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        saveLongInsulinDose();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
