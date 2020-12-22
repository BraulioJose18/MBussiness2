package com.practica02.mbussiness.dialogs.unit;

import android.app.AlertDialog;

import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO;
import com.practica02.mbussiness.repository.RequirementsRepository;

public class UnitViewDialog extends UnitDialog{

    protected final UnitOfMeasurementDTO unit;

    public UnitViewDialog(UnitOfMeasurementDTO unitDTO){
        this.unit = unitDTO;
    }

    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        this.identifier.setText(unit.getIdentifier());
        String stateSpinner = getSpinnerFromRegistryState(unit.getRegistryState());
        this.spinnerRegistryState.setSelection(this.optionsSpinner.indexOf(stateSpinner));
        this.name.setText(unit.getName());
        this.spinnerRegistryState.setEnabled(false);
        this.identifier.setEnabled(false);
        this.name.setEnabled(false);
        builder.setTitle("Ver Unidad")
                .setNegativeButton("Salir", (dialog, which) -> {
                });
    }

    public static String getSpinnerFromRegistryState(String registryState) {
        if (registryState.equalsIgnoreCase(RequirementsRepository.INACTIVE))
            return "Inactivo";
        else if (registryState.equalsIgnoreCase(RequirementsRepository.ELIMINATED))
            return "Eliminado";
        else return "Activo";
    }
}
