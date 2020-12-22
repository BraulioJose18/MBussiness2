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

import com.practica02.mbussiness.R;

public class AddUnit extends AppCompatDialogFragment {
    private EditText code, name;
    Spinner spinnerEstado;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_unit, null);
        code = view.findViewById(R.id.codeUnidad);
        name = view.findViewById(R.id.nameUnidad);
        spinnerEstado = view.findViewById(R.id.spinnerEstadoUnidad);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_status, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);

        builder
                .setView(view)
                .setTitle("Añadir Item")
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String actualState = spinnerEstado.getSelectedItem().toString();
                    String registryState = "";
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });

        return builder.create();
    }

}