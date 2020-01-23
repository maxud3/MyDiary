package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;


public class CompensationFragment extends Fragment {
    static final String TAG_FRAGMENT = "com.maksimohotnikov.mydiary.CompensationFragment";

    @BindView(R.id.groupCompensationInsulin)
    Group groupCompensationInsulin;
    //@BindView(R.id.switchCompensationInsulin)
    //Switch switchCompensationInsulin;
    Unbinder unbinder;

    //private OnFragmentInteractionListener mListener;

    public CompensationFragment() {
        // Required empty public constructor
    }



    @OnCheckedChanged(R.id.switchCompensationInsulin)
    void setGroupCompensationInsulin(boolean checked){
        if (checked){
            groupCompensationInsulin.setVisibility(View.VISIBLE);
        }else {
            groupCompensationInsulin.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compensation, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
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
   }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }


    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
