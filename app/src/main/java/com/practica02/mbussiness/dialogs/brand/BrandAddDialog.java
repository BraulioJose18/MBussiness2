package com.practica02.mbussiness.dialogs.brand;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import androidx.lifecycle.ViewModelProvider;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.model.mapper.BrandMapper;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.viewmodel.BrandViewModel;

import java.util.Objects;

public class BrandAddDialog extends AppCompatDialogFragment {
    private EditText identifier;
    private EditText name;
    private Spinner spinnerRegistryState;
    private BrandViewModel brandViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = Objects.requireNonNull(this.getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_brand, null);
        this.identifier = view.findViewById(R.id.code);
        this.name = view.findViewById(R.id.name);
        this.spinnerRegistryState = view.findViewById(R.id.spinnerEstado);
        this.brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.combo_status,
                android.R.layout.simple_spinner_item);
        this.spinnerRegistryState.setAdapter(adapter);
        this.identifier.setText("Autogenerado");
        this.identifier.setEnabled(false);
        builder
                .setView(view)
                .setTitle("Añadir Marca")
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String actualState = this.spinnerRegistryState.getSelectedItem().toString();
                    String registryState = "";
                    if (actualState.equalsIgnoreCase("Inactivo")) registryState = RequirementsRepository.INACTIVE;
                    else if (actualState.equalsIgnoreCase("Eliminado"))
                        registryState = RequirementsRepository.ELIMINATED;
                    else registryState = RequirementsRepository.ACTIVE;
                    BrandDTO dto = new BrandDTO(null, this.name.getText().toString(), registryState);
                    this.brandViewModel.save(BrandMapper.getMapper().dtoToEntity(dto));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
        return builder.create();
    }

}
