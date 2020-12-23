package com.practica02.mbussiness.dialogs.brand;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
    private final BrandDTO brand;
    private View.OnClickListener onClickListener;
    private ImageView img;
    private BrandViewModel brandViewModel;

    public BrandDeleteDialog(BrandDTO brand) {
        this.brand = brand;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = Objects.requireNonNull(this.getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);
        this.img = view.findViewById(R.id.imageViewDelete);
        this.img.setImageResource(R.drawable.ic_danger);
        this.brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);
        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    this.brand.setRegistryState(RequirementsRepository.ELIMINATED);
                    this.brandViewModel.update(BrandMapper.getMapper().dtoToEntity(brand))
                            .addOnCompleteListener(task -> {
                                if (onClickListener != null) {
                                    onClickListener.onClick(view);
                                }
                            });
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
        return builder.create();
    }

    public void setOnPositiveEvent(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
