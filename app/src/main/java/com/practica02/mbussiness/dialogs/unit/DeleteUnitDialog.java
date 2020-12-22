package com.practica02.mbussiness.dialogs.unit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO;
import com.practica02.mbussiness.model.mapper.UnitOfMeasurementMapper;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.viewmodel.UnitOfMeasurementViewModel;

import java.util.Objects;

public class DeleteUnitDialog extends AppCompatDialogFragment {
    private final UnitOfMeasurementDTO unit;
    private ImageView img;
    private UnitOfMeasurementViewModel unitViewModel;


    public DeleteUnitDialog(UnitOfMeasurementDTO unit) {
        this.unit = unit;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = Objects.requireNonNull(this.getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);
        this.img = view.findViewById(R.id.imageViewDelete);
        this.img.setImageResource(R.drawable.ic_danger);
        this.unitViewModel = new ViewModelProvider(this).get(UnitOfMeasurementViewModel.class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.combo_status,
                android.R.layout.simple_spinner_item);
        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    this.unit.setRegistryState(RequirementsRepository.ELIMINATED);
                    this.unitViewModel.update(UnitOfMeasurementMapper.getMapper().dtoToEntity(unit));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
        return builder.create();
    }
}
