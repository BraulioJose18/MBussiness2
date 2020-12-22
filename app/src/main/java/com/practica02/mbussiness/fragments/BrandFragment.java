package com.practica02.mbussiness.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.adapters.BrandAdapter;
import com.practica02.mbussiness.dialogs.brand.BrandAddDialog;
import com.practica02.mbussiness.dialogs.brand.BrandDeleteDialog;
import com.practica02.mbussiness.dialogs.brand.BrandModifyDialog;
import com.practica02.mbussiness.dialogs.brand.BrandViewDialog;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.model.mapper.BrandMapper;
import com.practica02.mbussiness.repository.BrandRepository;
import com.practica02.mbussiness.repository.FirestoreRepository;
import com.practica02.mbussiness.repository.RequirementsRepository;
import com.practica02.mbussiness.repository.liveData.MultipleDocumentReferenceLiveData;
import com.practica02.mbussiness.viewmodel.BrandViewModel;

import java.util.*;

public class BrandFragment extends Fragment {
    private final String TAG = BrandFragment.class.getSimpleName();
    //Principal Components
    private RecyclerView recyclerViewBrand;
    private Button btnAddBrand;
    private EditText editSearch;
    private ImageButton btnSearch;
    private BrandViewModel brandViewModel;
    private BrandAdapter brandAdapter;
    //Filter Components
    private Spinner fieldBrand, orderBrand;
    private List<String> fieldOptions, orderOptions;
    private CheckBox active, inactive, eliminated;
    private Button btnFilter;

    //Data Source
    private Query collectionQuery;
    private Query filterResultQuery;
    private MediatorLiveData<List<? extends Brand>> resultLiveData;
    private MultipleDocumentReferenceLiveData<Brand, ?> allLiveData;
    private MultipleDocumentReferenceLiveData<Brand, ?> liveDataByRegistry;
    private MultipleDocumentReferenceLiveData<Brand, ?> liveDataByName;
    private MultipleDocumentReferenceLiveData<Brand, ?> filterLiveData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);

        this.fieldBrand = view.findViewById(R.id.field_brand);
        this.orderBrand = view.findViewById(R.id.order_brand);
        this.active = view.findViewById(R.id.check_active_brand);
        this.inactive = view.findViewById(R.id.check_inactive_brand);
        this.eliminated = view.findViewById(R.id.check_eliminated_brand);
        this.btnFilter = view.findViewById(R.id.btn_filter_brand);

        this.recyclerViewBrand = view.findViewById(R.id.rvBrand);
        this.btnAddBrand = view.findViewById(R.id.addBrand);
        this.editSearch = view.findViewById(R.id.editSearchBrand);
        this.btnSearch = view.findViewById(R.id.searchBrand);


        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(getContext(), R.array.filtro_brand_unit, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        this.fieldOptions = Arrays.asList(Objects.requireNonNull(this.getContext()).getResources().getStringArray(R.array.filtro_brand_unit).clone());
        this.orderOptions = Arrays.asList(Objects.requireNonNull(this.getContext()).getResources().getStringArray(R.array.orden).clone());

        this.fieldBrand.setAdapter(fieldAdapter);
        this.orderBrand.setAdapter(orderAdapter);
        this.brandAdapter = new BrandAdapter(this.getContext());
        this.brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);
        this.collectionQuery = this.brandViewModel.getBuilderQuery();
        this.filterResultQuery = this.brandViewModel.getBuilderQuery();
        this.resultLiveData = new MediatorLiveData<>();

        this.brandAdapter.setViewListener(data -> createViewDialog(BrandMapper.getMapper().entityToDto(data)));
        this.brandAdapter.setModifyListener(data -> createModifyDialog(BrandMapper.getMapper().entityToDto(data)));
        this.brandAdapter.setDeleteListener(data -> createDeleteDialog(BrandMapper.getMapper().entityToDto(data)));
        this.btnAddBrand.setOnClickListener(v -> createAddDialog());
        this.recyclerViewBrand.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerViewBrand.setAdapter(this.brandAdapter);

        this.allLiveData = this.brandViewModel.getAllBrandLiveData();
        this.resultLiveData.addSource(allLiveData, brands -> resultLiveData.setValue(brands));

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
                        resultLiveData.addSource(allLiveData, brands -> resultLiveData.setValue(brands));
                    }
                } else {
                    Log.e(TAG, "No change data result");
                }
            }
        });

        this.btnSearch.setOnClickListener(v -> {
            String searchString = this.editSearch.getText().toString();
            Query queryByName = BrandRepository.Companion.filterByName(this.collectionQuery, searchString);
            Query queryByRegistryState = FirestoreRepository.Companion.filterByRegistryState(this.collectionQuery, searchString);
            this.liveDataByName = this.brandViewModel.queryLiveData(queryByName);
            this.liveDataByRegistry = this.brandViewModel.queryLiveData(queryByRegistryState);
            this.cleanResults();
            Log.e(TAG, "Add New Resource");
            this.resultLiveData.addSource(this.liveDataByName, brands -> {
                List<Brand> brandList = new ArrayList<>(brands);
                brandList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(brandList);
            });
            this.resultLiveData.addSource(this.liveDataByRegistry, brands -> {
                List<Brand> brandList = new ArrayList<>(brands);
                brandList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(brandList);
            });
        });

        this.btnFilter.setOnClickListener(v -> {
            this.filterResultQuery = this.brandViewModel.getBuilderQuery();
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
            if (fieldBrand.getSelectedItem().toString().equalsIgnoreCase("Nombre")) {
                Log.e(TAG, "Filter Nombre");
                if (orderBrand.getSelectedItem().toString().equalsIgnoreCase("Ascendente")) {
                    Log.e(TAG, "Filter ASC");
                    this.filterResultQuery = BrandRepository.Companion.orderAscendingByName(this.filterResultQuery);
                } else if (orderBrand.getSelectedItem().toString().equalsIgnoreCase("Descendente")) {
                    Log.e(TAG, "Filter DESC");
                    this.filterResultQuery = BrandRepository.Companion.orderDescendingByName(this.filterResultQuery);
                }
            } else if (fieldBrand.getSelectedItem().toString().equalsIgnoreCase("Estado de Registro")) {
                Log.e(TAG, "Filter Estado de registro");
                if (orderBrand.getSelectedItem().toString().equalsIgnoreCase("Ascendente")) {
                    Log.e(TAG, "Filter ASC");
                    this.filterResultQuery = FirestoreRepository.Companion.orderAscendingByRegistryState(this.filterResultQuery);
                } else if (orderBrand.getSelectedItem().toString().equalsIgnoreCase("Descendente")) {
                    Log.e(TAG, "Filter DESC");
                    this.filterResultQuery = FirestoreRepository.Companion.orderDescendingByRegistryState(this.filterResultQuery);
                }
            }
            this.cleanResults();
            Log.e(TAG, "Add New Resource");
            this.filterLiveData = this.brandViewModel.queryLiveData(this.filterResultQuery);
            this.resultLiveData.addSource(this.filterLiveData, brands -> {
                List<Brand> brandList = new ArrayList<>(brands);
                brandList.addAll(Objects.requireNonNull(this.resultLiveData.getValue()));
                this.resultLiveData.setValue(brandList);
            });

        });

        this.resultLiveData.observe(this, brands -> {
            Log.e(TAG, "Updating UI");
            brandAdapter.setBrand(brands);
            brandAdapter.notifyDataSetChanged();
        });

        return view;
    }

    private void cleanResults() {
        Log.e(TAG, "Remove principal resource");
        if (this.allLiveData != null) {
            this.resultLiveData.removeSource(this.allLiveData);
        }
        if (liveDataByName != null && liveDataByRegistry != null) {
            this.resultLiveData.removeSource(this.liveDataByName);
            this.resultLiveData.removeSource(this.liveDataByRegistry);
        }
        if (this.filterLiveData != null) {
            this.resultLiveData.removeSource(this.filterLiveData);
        }
        this.resultLiveData.setValue(new ArrayList<>());
    }

    private void createDeleteDialog(BrandDTO dto) {
        BrandDeleteDialog viewDialog = new BrandDeleteDialog(dto);
        viewDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createModifyDialog(BrandDTO dto) {
        BrandModifyDialog viewDialog = new BrandModifyDialog(dto);
        viewDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createAddDialog() {
        BrandAddDialog addDialog = new BrandAddDialog();
        addDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }

    private void createViewDialog(BrandDTO dto) {
        BrandViewDialog viewDialog = new BrandViewDialog(dto);
        viewDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }
}
