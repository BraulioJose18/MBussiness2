package com.practica02.mbussiness.dialogs.unit;

import android.app.AlertDialog;

import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO;
import com.practica02.mbussiness.model.mapper.UnitOfMeasurementMapper;

public class UnitModifyDialog extends UnitViewDialog {

    public UnitModifyDialog(UnitOfMeasurementDTO unitDTO){
        super(unitDTO);
    }

    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        super.buildDialog(builder);
        this.name.setEnabled(true);
        this.spinnerRegistryState.setEnabled(true);
        builder.setTitle("Modificar Unidad")
                .setPositiveButton("Modificar", (dialog, which) -> {
                    this.unit.setName(this.name.getText().toString());
                    this.unit.setRegistryState(getRegistryStateFromSpinner());
                    this.UnitOfMeasurementViewModel.update(UnitOfMeasurementMapper.getMapper().dtoToEntity(unit));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
    }
}
