package com.practica02.mbussiness.dialogs.brand;

import android.app.AlertDialog;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.mapper.BrandMapper;

public class BrandModifyDialog extends BrandViewDialog {

    public BrandModifyDialog(BrandDTO brandDTO) {
        super(brandDTO);
    }

    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        super.buildDialog(builder);
        this.name.setEnabled(true);
        this.spinnerRegistryState.setEnabled(true);
        builder.setTitle("Modificar Marca")
                .setPositiveButton("Modificar", (dialog, which) -> {
                    this.brand.setName(this.name.getText().toString());
                    this.brand.setRegistryState(getRegistryStateFromSpinner());
                    this.brandViewModel.update(BrandMapper.getMapper().dtoToEntity(brand))
                            .addOnCompleteListener(task -> {
                                if (onPositiveEvent != null) {
                                    onPositiveEvent.onClick(getView());
                                }
                            });
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
    }
}
