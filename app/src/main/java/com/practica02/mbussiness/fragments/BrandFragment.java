package com.practica02.mbussiness.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.adapters.BrandAdapter;
import com.practica02.mbussiness.dialogs.brand.BrandAddDialog;
import com.practica02.mbussiness.model.entity.Brand;
import com.practica02.mbussiness.viewmodel.BrandViewModel;

import java.util.List;
import java.util.Objects;

public class BrandFragment extends Fragment {
    private final String TAG = BrandFragment.class.getSimpleName();
    private RecyclerView recyclerViewBrand;
    private Button btnAddBrand;
    private EditText editSearch;
    private BrandViewModel brandViewModel;
    private BrandAdapter brandAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        this.recyclerViewBrand = view.findViewById(R.id.rvBrand);
        this.btnAddBrand = view.findViewById(R.id.addBrand);
        this.editSearch = view.findViewById(R.id.editSearchBrand);
        this.brandAdapter = new BrandAdapter(this.getContext());
        this.brandViewModel = new ViewModelProvider(this).get(BrandViewModel.class);
        this.btnAddBrand.setOnClickListener(v -> createAddDialog());
        this.recyclerViewBrand.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerViewBrand.setAdapter(this.brandAdapter);
        this.brandViewModel.getAllBrandLiveData().observe(this, brands -> {
            brandAdapter.setBrand((List<Brand>) brands);
            brandAdapter.notifyDataSetChanged();
        });
        return view;
    }

    private void createAddDialog() {
        BrandAddDialog brandAddDialog = new BrandAddDialog();
        brandAddDialog.show(Objects.requireNonNull(this.getFragmentManager()), TAG);
    }
}
