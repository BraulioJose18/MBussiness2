package com.practica02.mbussiness.dialogs.unit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO;
import com.practica02.mbussiness.model.mapper.BrandMapper;
import com.practica02.mbussiness.model.mapper.UnitOfMeasurementMapper;

public class AddUnit extends UnitDialog {

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