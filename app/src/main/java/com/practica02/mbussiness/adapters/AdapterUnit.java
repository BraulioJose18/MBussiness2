package com.practica02.mbussiness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.practica02.mbussiness.model.entity.UnitOfMeasurement;

public class AdapterUnit extends RecyclerView.Adapter<AdapterUnit.ViewHolder> {

    LayoutInflater inflater;
    List<UnitOfMeasurement> listUnidad;


    @NonNull
    @Override
    public AdapterUnit.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUnit.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
