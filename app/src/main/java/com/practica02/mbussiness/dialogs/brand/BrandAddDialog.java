package com.practica02.mbussiness.dialogs.brand;

import android.app.AlertDialog;
import android.util.Log;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.mapper.BrandMapper;

public class BrandAddDialog extends BrandDialog {

    public void buildDialog(AlertDialog.Builder builder) {
        this.identifier.setText("Autogenerado");
        this.identifier.setEnabled(false);
        builder.setTitle("Añadir Marca")
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String registryState = getRegistryStateFromSpinner();
                    BrandDTO dto = new BrandDTO("", this.name.getText().toString(), registryState);
                    Log.e(TAG, dto.toString());
                    Log.e(TAG, BrandMapper.getMapper().dtoToEntity(dto).toString());
                    this.brandViewModel.save(BrandMapper.getMapper().dtoToEntity(dto)).addOnCompleteListener(task -> {
                        if (onPositiveEvent != null) {
                            onPositiveEvent.onClick(getView());
                        }
                    });
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
    }

}
