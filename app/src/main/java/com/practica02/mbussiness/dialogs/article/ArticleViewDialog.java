package com.practica02.mbussiness.dialogs.article;

import android.app.AlertDialog;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.model.entity.DatabaseRegistry;
import com.practica02.mbussiness.model.entity.UnitOfMeasurement;
import com.practica02.mbussiness.repository.RequirementsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleViewDialog extends ArticleDialog {

    protected final ArticleDTO article;

    public ArticleViewDialog(ArticleDTO articleDTO) {
        this.article = articleDTO;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        this.identifier.setText(article.getIdentifier());
        String stateSpinner = getSpinnerFromRegistryState(article.getRegistryState());
        this.spinnerRegistryState.setSelection(this.optionsSpinner.indexOf(stateSpinner));
        this.name.setText(article.getName());
        this.unitaryPrice.setText(article.getUnitaryPrice());
        this.spinnerRegistryState.setEnabled(false);
        this.spinnerBrand.setEnabled(false);
        this.spinnerUnitMeasurement.setEnabled(false);
        this.identifier.setEnabled(false);
        this.name.setEnabled(false);
        this.unitaryPrice.setEnabled(false);
        this.brandViewModel.getAllBrandLiveData().observe(this, brands -> {
            int position = brandSpinnerAdapter.getBrands().parallelStream().map(DatabaseRegistry::getIdentifier).collect(Collectors.toList()).indexOf(article.getBrandId());
            if (position == -1) {
                position = brandSpinnerAdapter.getBrands().size() - 1;
                List<Brand> brandList = new ArrayList<>(brandSpinnerAdapter.getBrands());
                brandList.add(brands.get(position));
                this.brandSpinnerAdapter.setBrands(brandList);
                this.brandSpinnerAdapter.notifyDataSetChanged();
            }
            this.spinnerBrand.setSelection(position);
        });

        this.unitOfMeasurementViewModel.getAllUnitLiveData().observe(this, unit -> {
            int position = unitOfMeasurementSpinnerAdapter.getUnitOfMeasurements().parallelStream().map(DatabaseRegistry::getIdentifier).collect(Collectors.toList()).indexOf(article.getUnitOfMeasurementId());
            if (position == -1) {
                position = unitOfMeasurementSpinnerAdapter.getUnitOfMeasurements().size() - 1;
                List<UnitOfMeasurement> unitList = new ArrayList<>(unitOfMeasurementSpinnerAdapter.getUnitOfMeasurements());
                unitList.add(unit.get(position));
                this.unitOfMeasurementSpinnerAdapter.setUnitOfMeasurements(unitList);
                this.unitOfMeasurementSpinnerAdapter.notifyDataSetChanged();
            }
            this.spinnerUnitMeasurement.setSelection(position);
        });

        builder.setTitle("Ver Article")
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
