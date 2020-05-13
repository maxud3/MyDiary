package com.maksimohotnikov.mydiary;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<State> states;

    DataAdapter(Context context, List<State> states){
        this.states = states;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_records, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        State state = states.get(position);
        holder.timeView.setText(state.getTime());
        holder.sugarInBloodView.setText(state.getSugarInBlood());
        holder.breadInBloodView.setText(state.getBreadUnits());
        holder.insulinView.setText(state.getDoseInsulin());
    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView timeView;
        final TextView sugarInBloodView;
        final TextView breadInBloodView;
        final TextView insulinView;
        ViewHolder(View view){
            super(view);
            timeView = view.findViewById(R.id.tv_time);
            sugarInBloodView = view.findViewById(R.id.tv_sugar);
            breadInBloodView = view.findViewById(R.id.tv_bread_units);
            insulinView = view.findViewById(R.id.tv_insulin);
        }
    }
}
