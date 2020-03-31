package com.maksimohotnikov.mydiary;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.GregorianCalendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TotalRecordFragment extends Fragment {

    final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.TotalRecordFragment";
    private Unbinder unbinder;
    @BindView(R.id.current_date)
    TextView tvCurrentDate;
    @BindView(R.id.current_time)
    TextView tvCurrentTime;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.linear_layout_1)
    LinearLayout linearLayout1;
    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @BindView(R.id.time_picker)
    TimePicker timePicker;
    //@BindView(R.id.group_date_time_pickers)
    //Group groupDateTimePickers;
    private int mMonth;
    private int mDay;
    private int mYear;
    private int mHour;
    private int mMinute;
    private boolean visibleDatePicker = false;
    private boolean visibleTimePicker = false;

    //private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        timePicker.setIs24HourView(true);
        setTvCurrentDateTime();
        setCurrentTime();


        Calendar today = Calendar.getInstance();
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), (view1, year, monthOfYear, dayOfMonth) -> {
                    String s = dayOfMonth + ". " +(monthOfYear + 1) + ". " + year;
                    tvCurrentDate.setText(s);
});

        timePicker.setOnTimeChangedListener((view12, hourOfDay, minute) -> {
            String timeNow = hourOfDay + " : " + minute;
            tvCurrentTime.setText(timeNow);
        });

        return view;
    }
    /*private void callDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (DatePickerDialog.OnDateSetListener)getParentFragment() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        })
    }*/
    //устанавливаем текущую дату
    private void setTvCurrentDateTime(){
        GregorianCalendar grCalendar = new GregorianCalendar();
        mYear = grCalendar.get(Calendar.YEAR);
        mMonth = grCalendar.get(Calendar.MONTH);
        mDay = grCalendar.get(Calendar.DAY_OF_MONTH);
        String s = mDay + ". " +(mMonth + 1) + ". " + mYear;
        tvCurrentDate.setText(s);
    }
    //устанавливаем текущее время
    private void setCurrentTime(){
        //final Calendar calendar = Calendar.getInstance();
        //TimeZone tz = TimeZone.getDefault();
        GregorianCalendar grCalendar = new GregorianCalendar();
        mHour = grCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = grCalendar.get(Calendar.MINUTE);
        String s = mHour + " : " + mMinute;
       /* Date date = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String s = timeFormat.format(date);*/
        tvCurrentTime.setText(s);

    }
    @OnClick({R.id.current_date, R.id.current_time})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.current_date:
                if (!visibleDatePicker) {
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout1.setVisibility(View.GONE);
                    visibleDatePicker = true;
                    visibleTimePicker = false;
                } else {
                    linearLayout.setVisibility(View.GONE);
                    visibleDatePicker = false;
                }
                break;
            case R.id.current_time:
                if (!visibleTimePicker){
                    linearLayout1.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    visibleTimePicker = true;
                    visibleDatePicker = false;
                }else {
                    linearLayout1.setVisibility(View.GONE);
                    visibleTimePicker = false;
                }
                break;
        }
}

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
    @Override
    public void onDestroy(){
        super.onDestroy();
        unbinder.unbind();
        Log.d(MainActivity.TAG, "onDestroy: ShortInsulinFragment");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}
