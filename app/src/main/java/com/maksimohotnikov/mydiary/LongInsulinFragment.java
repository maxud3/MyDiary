package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LongInsulinFragment extends Fragment implements View.OnClickListener {

    public final static String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.LongInsulinFragment";

    private Button btnPlusLongInsulin;
    private Button btnMinusLongInsulin;
    private EditText etLongInsulin;
    private float longInsulin = 2.0f;
    //private OnFragmentInteractionListener mListener;

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

        btnMinusLongInsulin = view.findViewById(R.id.btnMinusLongInsulin);
        btnPlusLongInsulin = view.findViewById(R.id.btnPlusLongInsulin);
        etLongInsulin = view.findViewById(R.id.etLongInsulin);

        String stLongInsulin = Float.toString(longInsulin);
        etLongInsulin.setText(stLongInsulin);

        btnMinusLongInsulin.setOnClickListener(this);
        btnPlusLongInsulin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinusLongInsulin:
                if (longInsulin > 1.5f) {
                    btnPlusLongInsulin.setEnabled(true);
                    longInsulin = SugarInBloodFragment.decrement(longInsulin);
                    etLongInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundDown(longInsulin, 1)));
                } else {
                    btnMinusLongInsulin.setEnabled(false);
                }
                break;
            case R.id.btnPlusLongInsulin:
                if (longInsulin < 2.4f) {
                    btnMinusLongInsulin.setEnabled(true);
                    longInsulin = SugarInBloodFragment.increment(longInsulin);
                    etLongInsulin.setText(String.valueOf(SugarInBloodFragment
                            .roundUp(longInsulin, 1)));
                } else {
                    btnPlusLongInsulin.setEnabled(false);
                }
                break;
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
