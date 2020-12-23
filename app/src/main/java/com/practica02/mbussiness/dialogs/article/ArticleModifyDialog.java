package com.practica02.mbussiness.dialogs.article;

import android.app.AlertDialog;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.model.entity.UnitOfMeasurement;
import com.practica02.mbussiness.model.mapper.ArticleMapper;

public class ArticleModifyDialog extends ArticleViewDialog {

    public ArticleModifyDialog(ArticleDTO articleDTO) {
        super(articleDTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        super.buildDialog(builder);
        this.name.setEnabled(true);
        this.unitaryPrice.setEnabled(true);
        this.spinnerRegistryState.setEnabled(true);
        this.spinnerBrand.setEnabled(true);
        this.spinnerUnitMeasurement.setEnabled(true);
        builder.setTitle("Modificar Articulo")
                .setPositiveButton("Modificar", (dialog, which) -> {
                    Brand brand = this.brandSpinnerAdapter.getBrands().get(spinnerBrand.getSelectedItemPosition());
                    UnitOfMeasurement unitOfMeasurement = this.unitOfMeasurementSpinnerAdapter.getUnitOfMeasurements().get(spinnerUnitMeasurement.getSelectedItemPosition());
                    this.article.setName(this.name.getText().toString());
                    this.article.setUnitaryPrice(this.unitaryPrice.getText().toString());
                    this.article.setRegistryState(getRegistryStateFromSpinner());
                    this.article.setBrandId(brand.getIdentifier());
                    this.article.setUnitOfMeasurementId(unitOfMeasurement.getIdentifier());
                    this.articleViewModel.update(ArticleMapper.getMapper().dtoToEntity(article));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
    }
}
