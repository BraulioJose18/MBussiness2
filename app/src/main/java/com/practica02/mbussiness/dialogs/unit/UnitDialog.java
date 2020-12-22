package com.practica02.mbussiness.dialogs.unit;

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
import com.google.common.collect.Lists;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.viewmodel.UnitOfMeasurementViewModel;

import java.util.List;
import java.util.Objects;

public abstract class UnitDialog extends AppCompatDialogFragment {
    protected EditText identifier;
    protected EditText name;
    protected Spinner spinnerRegistryState;
    protected UnitOfMeasurementViewModel UnitOfMeasurementViewModel;
    protected final String TAG = UnitAddDialog.class.getSimpleName();
    protected List<String> optionsSpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = Objects.requireNonNull(this.getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_unit, null);
        this.identifier = view.findViewById(R.id.codeUnidad);
        this.name = view.findViewById(R.id.nameUnidad);
        this.spinnerRegistryState = view.findViewById(R.id.spinnerEstadoUnidad);
        this.UnitOfMeasurementViewModel = new ViewModelProvider(this).get(UnitOfMeasurementViewModel.class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.combo_status,
                android.R.layout.simple_spinner_item);
        this.spinnerRegistryState.setAdapter(adapter);
        this.optionsSpinner = Lists.newArrayList(Objects.requireNonNull(this.getContext()).getResources().getStringArray(R.array.combo_status));
        builder.setView(view);
        buildDialog(builder);
        return builder.create();
    }

    public abstract void buildDialog(AlertDialog.Builder builder);

    public String getRegistryStateFromSpinner() {
        String currentState = this.spinnerRegistryState.getSelectedItem().toString();
        if (currentState.equalsIgnoreCase("Inactivo"))
            return RequirementsRepository.INACTIVE;
        else if (currentState.equalsIgnoreCase("Eliminado"))
            return RequirementsRepository.ELIMINATED;
        else return RequirementsRepository.ACTIVE;

    }
}
