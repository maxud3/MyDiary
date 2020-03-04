package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.Group;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//import static com.maksimohotnikov.mydiary.MainActivity.*;

import static com.maksimohotnikov.mydiary.SugarInBloodFragment.*;


public class MenuFragment extends Fragment {

    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.MenuFragment";
    static final String BREAD_UNITS = "breadUnits";
    private OpenInsulinFragment mListener;
    @BindView(R.id.btn_minus_bread_units)
    Button btnMinusBreadUnits;
    @BindView(R.id.btn_plus_bread_units)
    Button btnPlusBreadUnits;
    @BindView(R.id.btn_further)
    Button btnFurther;
    @BindView(R.id.tv_bread_units)
    TextView tvBreadUnits;
    @BindView(R.id.et_bread_units)
    EditText etBreadUnits;
    @BindView(R.id.group_bread_units)
    Group group;
    @BindView(R.id.group_selected_bread_units)
    Group groupSelectedBreadUnits;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(MainActivity.TAG, "onCreate: MenuFragment");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        etBreadUnits.setText(R.string.default_bread_units);

        return view;
    }

    @OnClick({R.id.btn_bread_units, R.id.btn_minus_bread_units, R.id.btn_plus_bread_units,
    R.id.btn_further, R.id.btn_cancel, R.id.btn_ok})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bread_units:
                btnFurther.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_cancel:
                btnFurther.setVisibility(View.VISIBLE);
                group.setVisibility(View.GONE);
                break;
            case R.id.btn_ok:
                btnFurther.setVisibility(View.VISIBLE);
                group.setVisibility(View.GONE);
                groupSelectedBreadUnits.setVisibility(View.VISIBLE);
                tvBreadUnits.setText(etBreadUnits.getText().toString());
                break;
            case R.id.btn_minus_bread_units:
                decrementBreadUnits();
                break;
            case R.id.btn_plus_bread_units:
                incrementBreadUnits();
                break;
            case R.id.btn_further:
                mListener.openInsulinFragment();
                //saveBreadUnits();
        }
    }

    //Уменьшаем хлебные единицы
    private void decrementBreadUnits (){
        float breadUnits = Float.parseFloat(etBreadUnits.getText().toString());
        if (breadUnits > 0.0f){
            btnPlusBreadUnits.setEnabled(true);
            breadUnits = decrement(breadUnits);
            etBreadUnits.setText(String
                    .valueOf(roundUp(breadUnits, 1)));
        }else {
            btnMinusBreadUnits.setEnabled(false);
        }
    }

    //Увеличиваем хлебные единицы
    private void incrementBreadUnits(){
        float breadUnits = Float.parseFloat(etBreadUnits.getText().toString());
        if (breadUnits < 50.0f){
            btnMinusBreadUnits.setEnabled(true);
            breadUnits = increment(breadUnits);
            etBreadUnits.setText(String
                    .valueOf(roundUp(breadUnits, 1)));
        }else {
            btnPlusBreadUnits.setEnabled(false);
        }
    }

    //Сохраняем хлебные единицы
    @SuppressWarnings("ConstantConditions")
    private void saveBreadUnits(){
        String breadUnits = etBreadUnits.getText().toString();
        if (breadUnits.equals("")){
            breadUnits = getString(R.string.zero_zero);
        }
        SharedPreferences.Editor prefEditor = getActivity()
                .getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE)
                .edit();
        prefEditor.putString(BREAD_UNITS, breadUnits);
        prefEditor.apply();
    }
    public interface OpenInsulinFragment{
        void openInsulinFragment();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof OpenInsulinFragment){
            mListener = (OpenInsulinFragment) context;
        } else {
            throw  new RuntimeException(context.toString()
                    + "must implement OnFragment1DataListener");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(MainActivity.TAG, "onPause: MenuFragment");
        saveBreadUnits();
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
