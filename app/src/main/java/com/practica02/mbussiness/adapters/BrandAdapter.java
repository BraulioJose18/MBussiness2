package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.dialogs.brand.BrandViewDialog;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.model.mapper.BrandMapper;
import com.practica02.mbussiness.utils.OnClickDataListener;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Brand> brandList;

    private OnClickDataListener<Brand> viewListener;
    private OnClickDataListener<Brand> modifyListener;
    private OnClickDataListener<Brand> deleteListener;

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
        BrandDTO dto = BrandMapper.getMapper().entityToDto(this.brandList.get(position));
        holder.id.setText(dto.getIdentifier());
        holder.name.setText(dto.getName());
        holder.registryState.setText(BrandViewDialog.getSpinnerFromRegistryState(dto.getRegistryState()));
        holder.view.setOnClickListener(v -> this.viewListener.onClick(this.brandList.get(position)));
        holder.modify.setOnClickListener(v -> this.modifyListener.onClick(this.brandList.get(position)));
        holder.delete.setOnClickListener(v -> this.deleteListener.onClick(this.brandList.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.brandList.size();
    }

    public void setBrand(List<? extends Brand> brandList) {
        this.brandList.clear();
        this.brandList.addAll(brandList);
    }

    public void setViewListener(OnClickDataListener<Brand> viewListener) {
        this.viewListener = viewListener;
    }

    public void setModifyListener(OnClickDataListener<Brand> modifyListener) {
        this.modifyListener = modifyListener;
    }

    public void setDeleteListener(OnClickDataListener<Brand> deleteListener) {
        this.deleteListener = deleteListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final TextView id, name, registryState;
        protected final Button view, modify, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.idBrand);
            this.name = itemView.findViewById(R.id.nameBrand);
            this.registryState = itemView.findViewById(R.id.registryStateBrand);
            this.view = itemView.findViewById(R.id.bViewBrand);
            this.modify = itemView.findViewById(R.id.bModifyBrand);
            this.delete = itemView.findViewById(R.id.bDeleteBrand);

        }
    }

}
