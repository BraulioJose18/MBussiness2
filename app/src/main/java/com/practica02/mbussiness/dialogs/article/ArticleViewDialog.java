package com.practica02.mbussiness.dialogs.article;

import android.app.AlertDialog;

import com.practica02.mbussiness.model.dto.ArticleDTO;
import com.practica02.mbussiness.repository.RequirementsRepository;

public class ArticleViewDialog extends ArticleDialog {

    protected final ArticleDTO article;

    public ArticleViewDialog(ArticleDTO articleDTO) {
        this.article = articleDTO;
    }

    @Override
    public void buildDialog(AlertDialog.Builder builder) {
        this.identifier.setText(article.getIdentifier());
        String stateSpinner = getSpinnerFromRegistryState(article.getRegistryState());
        this.spinnerRegistryState.setSelection(this.optionsSpinner.indexOf(stateSpinner));
        this.name.setText(article.getName());
        this.spinnerRegistryState.setEnabled(false);
        this.identifier.setEnabled(false);
        this.name.setEnabled(false);
        builder.setTitle("Ver Article")
                .setNegativeButton("Salir", (dialog, which) -> {
                });
    }

    public static String getSpinnerFromRegistryState(String registryState) {
        if (registryState.equalsIgnoreCase(RequirementsRepository.INACTIVE))
            return "Inactivo";
        else if (registryState.equalsIgnoreCase(RequirementsRepository.ELIMINATED))
            return "Eliminado";
        else return "Activo";
    }
}
