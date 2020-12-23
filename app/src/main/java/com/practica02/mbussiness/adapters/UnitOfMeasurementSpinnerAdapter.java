package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.practica02.mbussiness.model.entity.UnitOfMeasurement;

import java.util.List;
import java.util.stream.Collectors;

public class UnitOfMeasurementSpinnerAdapter extends ArrayAdapter<String> {
    private List<? extends UnitOfMeasurement> unitOfMeasurements;

    public List<? extends UnitOfMeasurement> getUnitOfMeasurements() {
        return unitOfMeasurements;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setUnitOfMeasurements(List<? extends UnitOfMeasurement> unitOfMeasurements) {
        this.clear();
        this.addAll(unitOfMeasurements.parallelStream().map(UnitOfMeasurement::getName).collect(Collectors.toList()));
        this.unitOfMeasurements = unitOfMeasurements;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public UnitOfMeasurementSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<UnitOfMeasurement> unitOfMeasurements) {
        super(context, resource, unitOfMeasurements.parallelStream().map(UnitOfMeasurement::getName).collect(Collectors.toList()));
    }
}