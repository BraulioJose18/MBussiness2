package com.practica02.mbussiness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.model.entity.Brand;

import java.util.List;

public class AdapterBrand extends RecyclerView.Adapter<AdapterBrand.ViewHolder> {

    LayoutInflater inflater;
    List<Brand> listBrand;

    @NonNull
    @Override
    public AdapterBrand.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBrand.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setBrand(List<Brand> listaMarca) {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
