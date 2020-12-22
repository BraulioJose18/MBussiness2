package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.dialogs.unit.ViewUnitDialog;
import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO;
import com.practica02.mbussiness.model.entity.UnitOfMeasurement;
import com.practica02.mbussiness.model.mapper.UnitOfMeasurementMapper;
import com.practica02.mbussiness.utils.OnClickDataListener;

public class AdapterUnit extends RecyclerView.Adapter<AdapterUnit.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<UnitOfMeasurement> listUnidad;

    private OnClickDataListener<UnitOfMeasurement> viewListener;
    private OnClickDataListener<UnitOfMeasurement> modifyListener;
    private OnClickDataListener<UnitOfMeasurement> deleteListener;


    public AdapterUnit(Context context){
        this.inflater = LayoutInflater.from(context);
        this.listUnidad = new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterUnit.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUnit.ViewHolder holder, int position) {
        UnitOfMeasurementDTO dto = UnitOfMeasurementMapper.getMapper().entityToDto(this.listUnidad.get(position));
        holder.id.setText(dto.getIdentifier());
        holder.name.setText(dto.getName());
        holder.registryState.setText(ViewUnitDialog.getSpinnerFromRegistryState(dto.getRegistryState()));
        holder.view.setOnClickListener(v->this.viewListener.onClick(this.listUnidad.get(position)));
        holder.modify.setOnClickListener(v->this.modifyListener.onClick(this.listUnidad.get(position)));
        holder.delete.setOnClickListener(v->this.deleteListener.onClick(this.listUnidad.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.listUnidad.size();
    }

    public void setUnitOfMeasurment(List<? extends UnitOfMeasurement> unitList){
        this.listUnidad.clear();
        this.listUnidad.addAll(unitList);
    }

    public void setViewListener(OnClickDataListener<UnitOfMeasurement> viewListener){
        this.viewListener = viewListener;
    }

    public void setModifyListener(OnClickDataListener<UnitOfMeasurement> modifyListener){
        this.modifyListener = modifyListener;
    }

    public void setDeleteListener(OnClickDataListener<UnitOfMeasurement> deleteListener){
        this.deleteListener = deleteListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected final TextView id, name, registryState;
        protected final Button view, modify, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.idDocumentoUnidadMedida);
            this.name = itemView.findViewById(R.id.nameUnidad);
            this.registryState = itemView.findViewById(R.id.statusUnidadMedida);
            this.view = itemView.findViewById(R.id.bVerUnidadMedida);
            this.modify = itemView.findViewById(R.id.bModificarUnidadMedida);
            this.delete = itemView.findViewById(R.id.bEliminarUnidadMedida);
        }
    }

}
