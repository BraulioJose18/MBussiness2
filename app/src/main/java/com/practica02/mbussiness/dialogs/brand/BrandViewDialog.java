package com.practica02.mbussiness.dialogs.brand;

import android.app.AlertDialog;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.mapper.BrandMapper;
import com.practica02.mbussiness.repository.RequirementsRepository;

public class BrandViewDialog extends BrandDialog {

    protected final BrandDTO brand;

    public BrandViewDialog(BrandDTO brandDTO) {
        this.brand = brandDTO;
    }

    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        this.identifier.setText(brand.getIdentifier());
        String stateSpinner = getSpinnerFromRegistryState(brand.getRegistryState());
        this.spinnerRegistryState.setSelection(this.optionsSpinner.indexOf(stateSpinner));
        this.name.setText(brand.getName());
        this.spinnerRegistryState.setEnabled(false);
        this.identifier.setEnabled(false);
        this.name.setEnabled(false);
        builder.setTitle("Ver Marca")
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
