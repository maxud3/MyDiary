package com.maksimohotnikov.mydiary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;

public class FormulaDialogFragment extends DialogFragment {
    //@BindView(R.id.text_view_formula_insulin_for_food)
    //TextView tvFormulaInsulinForFood;

    TextView tvFormulaInsulinForFood;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setTitle("Расчет инсулина")
                .setView(R.layout.formula_dialog)
                .setPositiveButton("Ok", null)
                .create();
    }
}
