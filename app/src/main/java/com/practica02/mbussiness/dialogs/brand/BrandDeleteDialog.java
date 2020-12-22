package com.practica02.mbussiness.dialogs.brand;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.mapper.BrandMapper;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.viewmodel.BrandViewModel;

import java.util.Objects;

public class BrandDeleteDialog extends AppCompatDialogFragment {


    private String TAG = BrandDeleteDialog.class.getSimpleName();

    ImageView img;
    private BrandViewModel brandViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = Objects.requireNonNull(this.getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_brand, null);
        img = view.findViewById(R.id.imageViewDelete);
        img.setImageResource(R.drawable.ic_danger);
        this.brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.combo_status,
                android.R.layout.simple_spinner_item);
        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {

                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
        return builder.create();
    }

}