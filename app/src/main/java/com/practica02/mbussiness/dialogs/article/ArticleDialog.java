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
import androidx.lifecycle.ViewModelProvider;
import com.google.common.collect.Lists;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.adapters.BrandSpinnerAdapter;
import com.practica02.mbussiness.adapters.UnitOfMeasurementSpinnerAdapter;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.viewmodel.ArticleViewModel;
import com.practica02.mbussiness.viewmodel.BrandViewModel;
import com.practica02.mbussiness.viewmodel.UnitOfMeasurementViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ArticleDialog extends AppCompatDialogFragment {
    protected final String TAG = ArticleAddDialog.class.getSimpleName();
    protected EditText identifier;
    protected EditText name;
    protected EditText unitaryPrice;
    protected Spinner spinnerRegistryState;
    protected Spinner spinnerBrand;
    protected Spinner spinnerUnitMeasurement;

    protected ArticleViewModel articleViewModel;
    protected BrandViewModel brandViewModel;
    protected UnitOfMeasurementViewModel unitOfMeasurementViewModel;

    protected BrandSpinnerAdapter brandSpinnerAdapter;
    protected UnitOfMeasurementSpinnerAdapter unitOfMeasurementSpinnerAdapter;

    protected List<String> optionsSpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = Objects.requireNonNull(this.getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_article, null);
        this.identifier = view.findViewById(R.id.idArticle);
        this.name = view.findViewById(R.id.nameArticle);
        this.unitaryPrice = view.findViewById(R.id.unitaryPriceArticle);
        this.spinnerBrand = view.findViewById(R.id.spinnerBrand);
        this.spinnerUnitMeasurement = view.findViewById(R.id.spinnerUnitOfMeasurement);
        this.spinnerRegistryState = view.findViewById(R.id.registryStateArticle);
        this.brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);
        this.unitOfMeasurementViewModel = new ViewModelProvider(this).get(UnitOfMeasurementViewModel.class);
        this.articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        this.brandSpinnerAdapter = new BrandSpinnerAdapter(Objects.requireNonNull(this.getContext()), android.R.layout.simple_spinner_item, new ArrayList<>());
        this.unitOfMeasurementSpinnerAdapter = new UnitOfMeasurementSpinnerAdapter(Objects.requireNonNull(this.getContext()), android.R.layout.simple_spinner_item, new ArrayList<>());

        ArrayAdapter<CharSequence> registryStateAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.combo_status,
                android.R.layout.simple_spinner_item);
        this.spinnerRegistryState.setAdapter(registryStateAdapter);
        this.spinnerBrand.setAdapter(brandSpinnerAdapter);
        this.spinnerUnitMeasurement.setAdapter(unitOfMeasurementSpinnerAdapter);
        this.brandViewModel.getActiveBrandLiveData().observe(this, brands -> {
            this.brandSpinnerAdapter.setBrands(brands);
            this.brandSpinnerAdapter.notifyDataSetChanged();
        });

        this.unitOfMeasurementViewModel.getActiveUnitLiveData().observe(this, unitOfMeasurements -> {
            this.unitOfMeasurementSpinnerAdapter.setUnitOfMeasurements(unitOfMeasurements);
            this.unitOfMeasurementSpinnerAdapter.notifyDataSetChanged();
        });

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
