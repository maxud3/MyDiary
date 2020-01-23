package com.maksimohotnikov.mydiary;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import static com.maksimohotnikov.mydiary.SugarInBloodFragment.*;

public class LongInsulinFragment extends Fragment implements View.OnClickListener{

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.LongInsulinFragment";

    @BindView(R.id.btnPlusLongInsulin) Button btnPlusLongInsulin;
    @BindView(R.id.btnMinusLongInsulin) Button btnMinusLongInsulin;
    @BindView(R.id.etLongInsulin) EditText etLongInsulin;
    private Unbinder unbinder;

    public LongInsulinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_long_insulin, container, false);
        unbinder =  ButterKnife.bind(this, view);

        etLongInsulin.setText("2.0");

        btnMinusLongInsulin.setOnClickListener(this);
        btnPlusLongInsulin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinusLongInsulin:
                decrementLongInsulin();
                break;
            case R.id.btnPlusLongInsulin:
                incrementLongInsulin();
                break;
        }
    }

    private void decrementLongInsulin(){
        float longInsulin = Float.valueOf(etLongInsulin.getText().toString());
        if (longInsulin > 0.0f){
            btnPlusLongInsulin.setEnabled(true);
            longInsulin = decrement(longInsulin);
            etLongInsulin.setText(String.valueOf(roundUp(longInsulin, 1)));
        }else {
            btnMinusLongInsulin.setEnabled(false);
        }
    }

    private void incrementLongInsulin(){
        float longInsulin = Float.valueOf(etLongInsulin.getText().toString());
        if (longInsulin <50.0f){
            btnMinusLongInsulin.setEnabled(true);
            longInsulin = increment(longInsulin);
            etLongInsulin.setText(String.valueOf(roundUp(longInsulin, 1)));
        }else {
            btnPlusLongInsulin.setEnabled(false);
        }
    }

   /* @Override
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
   }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
