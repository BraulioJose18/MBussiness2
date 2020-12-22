package com.practica02.mbussiness.dialogs.article;

import android.app.AlertDialog;

import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.model.mapper.ArticleMapper;

public class ArticleModifyDialog extends ArticleViewDialog {

    public ArticleModifyDialog(ArticleDTO articleDTO){
        super(articleDTO);
    }

    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        super.buildDialog(builder);
        this.name.setEnabled(true);
        this.spinnerRegistryState.setEnabled(true);
        builder.setTitle("Modificar Articulo")
                .setPositiveButton("Modificar", (dialog, which) -> {
                    this.article.setName(this.name.getText().toString());
                    this.article.setRegistryState(getRegistryStateFromSpinner());
                    this.articleViewModel.update(ArticleMapper.getMapper().dtoToEntity(article));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });
    }
}
