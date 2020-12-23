package com.practica02.mbussiness.dialogs.article;

import android.app.AlertDialog;
import android.util.Log;
import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.model.entity.UnitOfMeasurement;
import com.practica02.mbussiness.model.mapper.ArticleMapper;

public class ArticleAddDialog extends ArticleDialog {

    public void buildDialog(AlertDialog.Builder builder) {

        this.identifier.setText("Autogenerado");
        this.identifier.setEnabled(false);
        builder.setTitle("Añadir Articulo")
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String registryState = getRegistryStateFromSpinner();
                    Brand brand = this.brandSpinnerAdapter.getBrands().get(spinnerBrand.getSelectedItemPosition());
                    UnitOfMeasurement unitOfMeasurement = this.unitOfMeasurementSpinnerAdapter.getUnitOfMeasurements().get(spinnerUnitMeasurement.getSelectedItemPosition());
                    ArticleDTO dto = new ArticleDTO("", this.name.getText().toString(), this.unitaryPrice.getText().toString(), registryState, brand.getIdentifier(), unitOfMeasurement.getIdentifier());
                    Log.e(TAG, dto.toString());
                    Log.e(TAG, ArticleMapper.getMapper().dtoToEntity(dto).toString());
                    this.articleViewModel.save(ArticleMapper.getMapper().dtoToEntity(dto));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
    }
}
