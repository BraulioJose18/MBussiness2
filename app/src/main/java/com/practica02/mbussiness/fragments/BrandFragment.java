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
import com.practica02.mbussiness.repository.liveData.MultipleDocumentReferenceLiveData;
import com.practica02.mbussiness.viewmodel.BrandViewModel;

import java.util.*;

public class BrandFragment extends Fragment {
    private final String TAG = BrandFragment.class.getSimpleName();
    private RecyclerView recyclerViewBrand;
    private Button btnAddBrand;
    private EditText editSearch;
    private ImageButton btnSearch;
    private BrandViewModel brandViewModel;
    private BrandAdapter brandAdapter;

    //Spinner
    private Spinner fieldBrand, orderBrand;

    private Query collectionQuery;
    private MediatorLiveData<List<? extends Brand>> queryMediatorLiveData;
    private MultipleDocumentReferenceLiveData<Brand, ?> allLiveData;
    private MultipleDocumentReferenceLiveData<Brand, ?> liveDataByRegistry;
    private MultipleDocumentReferenceLiveData<Brand, ?> liveDataByName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);

        this.fieldBrand = view.findViewById(R.id.field_brand);
        this.orderBrand = view.findViewById(R.id.order_brand);

        this.recyclerViewBrand = view.findViewById(R.id.rvBrand);
        this.btnAddBrand = view.findViewById(R.id.addBrand);
        this.editSearch = view.findViewById(R.id.editSearchBrand);
        this.btnSearch = view.findViewById(R.id.searchBrand);


        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(getContext(), R.array.filtro_brand_unit, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        this.fieldBrand.setAdapter(fieldAdapter);
        this.orderBrand.setAdapter(orderAdapter);
        this.brandAdapter = new BrandAdapter(this.getContext());
        this.brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);
        this.collectionQuery = this.brandViewModel.getBuilderQuery();
        this.queryMediatorLiveData = new MediatorLiveData<>();

        this.brandAdapter.setViewListener(data -> createViewDialog(BrandMapper.getMapper().entityToDto(data)));
        this.brandAdapter.setModifyListener(data -> createModifyDialog(BrandMapper.getMapper().entityToDto(data)));
        this.brandAdapter.setDeleteListener(data -> createDeleteDialog(BrandMapper.getMapper().entityToDto(data)));
        this.btnAddBrand.setOnClickListener(v -> createAddDialog());
        this.recyclerViewBrand.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerViewBrand.setAdapter(this.brandAdapter);

        this.allLiveData = this.brandViewModel.getAllBrandLiveData();
        this.queryMediatorLiveData.addSource(allLiveData, brands -> queryMediatorLiveData.setValue(brands));

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
                        queryMediatorLiveData.removeSource(liveDataByName);
                        queryMediatorLiveData.removeSource(liveDataByRegistry);
                        queryMediatorLiveData.addSource(allLiveData, brands -> queryMediatorLiveData.setValue(brands));
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
            Log.e(TAG, "Remove principal resource");
            this.queryMediatorLiveData.removeSource(this.allLiveData);
            this.queryMediatorLiveData.setValue(new ArrayList<>());
            Log.e(TAG, "Add New Resource");
            this.queryMediatorLiveData.addSource(this.liveDataByName, brands -> {
                List<Brand> brandList = new ArrayList<>(brands);
                brandList.addAll(Objects.requireNonNull(this.queryMediatorLiveData.getValue()));
                this.queryMediatorLiveData.setValue(brandList);
            });
            this.queryMediatorLiveData.addSource(this.liveDataByRegistry, brands -> {
                List<Brand> brandList = new ArrayList<>(brands);
                brandList.addAll(Objects.requireNonNull(this.queryMediatorLiveData.getValue()));
                this.queryMediatorLiveData.setValue(brandList);
            });
        });

        this.queryMediatorLiveData.observe(this, brands -> {
            Log.e(TAG, "Updating UI");
            brandAdapter.setBrand(brands);
            brandAdapter.notifyDataSetChanged();
        });

        return view;
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
