package com.practica02.mbussiness.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.adapters.UnitAdapter;
import com.practica02.mbussiness.dialogs.unit.UnitAddDialog;
import com.practica02.mbussiness.dialogs.unit.UnitDeleteDialog;
import com.practica02.mbussiness.dialogs.unit.UnitModifyDialog;
import com.practica02.mbussiness.dialogs.unit.UnitViewDialog;
import com.practica02.mbussiness.model.dto.UnitOfMeasurementDTO;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.model.entity.UnitOfMeasurement;
import com.practica02.mbussiness.model.mapper.UnitOfMeasurementMapper;
import com.practica02.mbussiness.repository.UnitOfMeasurementRepository;
import com.practica02.mbussiness.repository.FirestoreRepository;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.repository.liveData.MultipleDocumentReferenceLiveData;
import com.practica02.mbussiness.viewmodel.UnitOfMeasurementViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FragmentUnitOfMeasurement extends Fragment {

    private final String TAG = FragmentUnitOfMeasurement.class.getSimpleName();
    //Principal Components
    private RecyclerView recyclerViewUnit;
    private Button btnAddUnit;
    private EditText editSearch;
    private ImageButton btnSearch;
    private UnitOfMeasurementViewModel unitViewModel;
    private UnitAdapter unitAdapter;

    //Filter Components
    private Spinner fieldUnit, orderUnit;
    private List<String> fieldOptions, orderOptions;
    private CheckBox active, inactive, eliminated;
    private Button btnFilter;

    //Data Source
    private Query collectionQuery;
    private Query filterResultQuery;
    private MediatorLiveData<List<? extends UnitOfMeasurement>> resultLiveData;
    private MultipleDocumentReferenceLiveData<UnitOfMeasurement, ?> allLiveData;
    private MultipleDocumentReferenceLiveData<UnitOfMeasurement, ?> activeLiveData;
    private MultipleDocumentReferenceLiveData<UnitOfMeasurement, ?> liveDataByRegistry;
    private MultipleDocumentReferenceLiveData<UnitOfMeasurement, ?> liveDataByName;
    private MultipleDocumentReferenceLiveData<UnitOfMeasurement, ?> filterLiveData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit,container,false);

        this.fieldUnit = view.findViewById(R.id.tipo_dato_unit);
        this.orderUnit = view.findViewById(R.id.asc_desc_unit);
        this.active = view.findViewById(R.id.check_activo_unit);
        this.inactive = view.findViewById(R.id.check_inactivo_unit);
        this.eliminated = view.findViewById(R.id.check_eliminado_unit);
        this.btnFilter = view.findViewById(R.id.filtrar_unit);

        this.recyclerViewUnit = view.findViewById(R.id.rvUnit);
        this.btnAddUnit = view.findViewById(R.id.addUnit);
        this.editSearch = view.findViewById(R.id.editSearchUnit);
        this.btnSearch = view.findViewById(R.id.searchUnit);


        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(getContext(), R.array.filtro_brand_unit, android.R.layout.simple_spinner_item);ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        this.fieldOptions = Arrays.asList(Objects.requireNonNull(this.getContext()).getResources().getStringArray(R.array.filtro_brand_unit).clone());
        this.orderOptions = Arrays.asList(Objects.requireNonNull(this.getContext()).getResources().getStringArray(R.array.orden).clone());

        this.fieldUnit.setAdapter(fieldAdapter);
        this.orderUnit.setAdapter(orderAdapter);
        this.unitAdapter = new UnitAdapter(this.getContext());
        this.unitViewModel = new ViewModelProvider(this).get(UnitOfMeasurementViewModel.class);
        this.collectionQuery = this.unitViewModel.getBuilderQuery();
        this.filterResultQuery = this.unitViewModel.getBuilderQuery();
        this.resultLiveData = new MediatorLiveData<>();

        this.unitAdapter.setViewListener(data -> createViewDialog(UnitOfMeasurementMapper.getMapper().entityToDto(data)));
        this.unitAdapter.setModifyListener(data -> createModifyDialog(UnitOfMeasurementMapper.getMapper().entityToDto(data)));
        this.unitAdapter.setDeleteListener(data -> createDeleteDialog(UnitOfMeasurementMapper.getMapper().entityToDto(data)));
        this.btnAddUnit.setOnClickListener(v -> createAddDialog());
        this.recyclerViewUnit.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerViewUnit.setAdapter(this.unitAdapter);

        this.activeLiveData = this.unitViewModel.getActiveUnitLiveData();
        this.resultLiveData.addSource(this.activeLiveData, brands -> resultLiveData.setValue(brands));
        this.active.setChecked(true);

        this.allLiveData = this.unitViewModel.getAllUnitLiveData();
        //this.resultLiveData.addSource(allLiveData, units -> resultLiveData.setValue(units));

        this.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    if (liveDataByName != null && liveDataByRegistry != null) {
                        resultLiveData.removeSource(liveDataByName);
                        resultLiveData.removeSource(liveDataByRegistry);
                        resultLiveData.addSource(allLiveData, units -> resultLiveData.setValue(units));
                    }
                } else {
                    Log.e(TAG, "No change data result");
                }
            }
        });

        this.btnSearch.setOnClickListener(v -> {
            String searchString = this.editSearch.getText().toString();
            Query queryByName = UnitOfMeasurementRepository.Companion.filterByName(this.collectionQuery, searchString);
            Query queryByRegistryState = FirestoreRepository.Companion.filterByRegistryState(this.collectionQuery, searchString);
            this.liveDataByName = this.unitViewModel.queryLiveData(queryByName);
            this.liveDataByRegistry = this.unitViewModel.queryLiveData(queryByRegistryState);
            this.cleanResults();
            Log.e(TAG, "Add New Resource");
            this.resultLiveData.addSource(this.liveDataByName, units -> {
                List<UnitOfMeasurement> unitList = new ArrayList<>(units);
                unitList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(unitList);
            });
            this.resultLiveData.addSource(this.liveDataByRegistry, units -> {
                List<UnitOfMeasurement> unitList = new ArrayList<>(units);
                unitList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(unitList);
            });
        });

        this.btnFilter.setOnClickListener(v -> {
            this.filterResultQuery = this.unitViewModel.getBuilderQuery();
            List<String> registryStateFilter = new ArrayList<>();
            if (this.active.isChecked())
                registryStateFilter.add(RequirementsRepository.ACTIVE);
            if (this.inactive.isChecked())
                registryStateFilter.add(RequirementsRepository.INACTIVE);
            if (this.eliminated.isChecked())
                registryStateFilter.add(RequirementsRepository.ELIMINATED);
            if (registryStateFilter.size() == 1) {
                this.filterResultQuery = FirestoreRepository.Companion
                        .filterByRegistryState(this.filterResultQuery, registryStateFilter.get(0));
            } else if (registryStateFilter.size() > 1) {
                this.filterResultQuery = FirestoreRepository.Companion
                        .filterByRegistryStates(this.filterResultQuery, registryStateFilter);
            }
            if (fieldUnit.getSelectedItem().toString().equalsIgnoreCase("Nombre")) {
                Log.e(TAG, "Filter Nombre");
                if (orderUnit.getSelectedItem().toString().equalsIgnoreCase("Ascendente")) {
                    Log.e(TAG, "Filter ASC");
                    this.filterResultQuery = UnitOfMeasurementRepository.Companion.orderAscendingByName(this.filterResultQuery);
                } else if (orderUnit.getSelectedItem().toString().equalsIgnoreCase("Descendente")) {
                    Log.e(TAG, "Filter DESC");
                    this.filterResultQuery = UnitOfMeasurementRepository.Companion.orderDescendingByName(this.filterResultQuery);
                }
            } else if (fieldUnit.getSelectedItem().toString().equalsIgnoreCase("Estado de Registro")) {
                Log.e(TAG, "Filter Estado de registro");
                if (orderUnit.getSelectedItem().toString().equalsIgnoreCase("Ascendente")) {
                    Log.e(TAG, "Filter ASC");
                    this.filterResultQuery = FirestoreRepository.Companion.orderAscendingByRegistryState(this.filterResultQuery);
                } else if (orderUnit.getSelectedItem().toString().equalsIgnoreCase("Descendente")) {
                    Log.e(TAG, "Filter DESC");
                    this.filterResultQuery = FirestoreRepository.Companion.orderDescendingByRegistryState(this.filterResultQuery);
                }
            }
            this.cleanResults();
            Log.e(TAG, "Add New Resource");
            this.filterLiveData = this.unitViewModel.queryLiveData(this.filterResultQuery);
            this.resultLiveData.addSource(this.filterLiveData, units -> {
                List<UnitOfMeasurement> unitList = new ArrayList<>(units);
                unitList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(unitList);
            });

        });

        this.resultLiveData.observe(this, units -> {
            Log.e(TAG, "Updating UI");
            unitAdapter.setUnitOfMeasurment(units);
            unitAdapter.notifyDataSetChanged();
        });

        return view;
    }

    private void cleanResults() {
        Log.e(TAG, "Remove all resources");
        if (this.allLiveData != null) {
            this.resultLiveData.removeSource(this.allLiveData);
        }
        if (this.liveDataByName != null && this.liveDataByRegistry != null) {
            this.resultLiveData.removeSource(this.liveDataByName);
            this.resultLiveData.removeSource(this.liveDataByRegistry);
        }
        if (this.filterLiveData != null) {
            this.resultLiveData.removeSource(this.filterLiveData);
        }
        if (this.activeLiveData != null) {
            this.resultLiveData.removeSource(this.activeLiveData);
        }
        this.resultLiveData.setValue(new ArrayList<>());
    }

    private void createDeleteDialog(UnitOfMeasurementDTO dto) {
        UnitDeleteDialog deleteDialog = new UnitDeleteDialog(dto);
        deleteDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createModifyDialog(UnitOfMeasurementDTO dto) {
        UnitModifyDialog modifyDialog = new UnitModifyDialog(dto);
        modifyDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createAddDialog() {
        UnitAddDialog addDialog = new UnitAddDialog();
        addDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createViewDialog(UnitOfMeasurementDTO dto) {
        UnitViewDialog viewDialog = new UnitViewDialog(dto);
        viewDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }
}
