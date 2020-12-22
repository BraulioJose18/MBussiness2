package com.practica02.mbussiness.dialogs.article;

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
import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.model.mapper.ArticleMapper;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.viewmodel.ArticleViewModel;

import java.util.Objects;

public class ArticleDeleteDialog extends AppCompatDialogFragment {
    private final ArticleDTO article;
    private ImageView img;
    private ArticleViewModel articleViewModel;

    public ArticleDeleteDialog(ArticleDTO article) {
        this.article = article;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = Objects.requireNonNull(this.getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);
        this.img = view.findViewById(R.id.imageViewDelete);
        this.img.setImageResource(R.drawable.ic_danger);
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    this.article.setRegistryState(RequirementsRepository.ELIMINATED);
                    this.articleViewModel.update(ArticleMapper.getMapper().dtoToEntity(article));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
        return builder.create();
    }

}
