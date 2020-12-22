package com.practica02.mbussiness.dialogs.article;

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

public class AddArticle extends AppCompatDialogFragment {
    private EditText code, name;
    Spinner spinnerUnidadMedida, spinnerEstado;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_article, null);
        code = view.findViewById(R.id.codeArticulo);
        name = view.findViewById(R.id.nameArticulo);
        //spinnerUnidadMedida = view.findViewById(R.id.spinnerUnidadMedida); //goes?
        spinnerEstado = view.findViewById(R.id.spinnerEstadoArticulo);

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
