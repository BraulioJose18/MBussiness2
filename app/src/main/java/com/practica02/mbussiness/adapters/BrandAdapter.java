package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.model.entity.Brand;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Brand> brandList;

    public BrandAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.brandList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BrandAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_brand, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.brandList.size();
    }

    public void setBrand(List<Brand> brandList) {
        this.brandList.clear();
        this.brandList.addAll(brandList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
