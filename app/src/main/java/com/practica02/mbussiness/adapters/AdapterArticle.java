package com.practica02.mbussiness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.model.entity.Article;
import com.practica02.mbussiness.model.entity.Brand;

import java.util.List;

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ViewHolder> {

    LayoutInflater inflater;
    List<Article> listArtcile;

    @NonNull
    @Override
    public AdapterArticle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArticle.ViewHolder holder, int position) {

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
