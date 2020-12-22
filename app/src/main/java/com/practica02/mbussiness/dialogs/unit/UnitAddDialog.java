package com.practica02.mbussiness.dialogs.unit;

import android.app.AlertDialog;
import android.util.Log;
import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO;
import com.practica02.mbussiness.model.mapper.UnitOfMeasurementMapper;

public class UnitAddDialog extends UnitDialog {

    public void buildDialog(AlertDialog.Builder builder) {
        this.identifier.setText("Autogenerado");
        this.identifier.setEnabled(false);
        builder.setTitle("Añadir Unidad")
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String registryState = getRegistryStateFromSpinner();
                    UnitOfMeasurementDTO dto = new UnitOfMeasurementDTO("", this.name.getText().toString(), registryState);
                    Log.e(TAG, dto.toString());
                    Log.e(TAG, UnitOfMeasurementMapper.getMapper().dtoToEntity(dto).toString());
                    this.UnitOfMeasurementViewModel.save(UnitOfMeasurementMapper.getMapper().dtoToEntity(dto));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
    }
}