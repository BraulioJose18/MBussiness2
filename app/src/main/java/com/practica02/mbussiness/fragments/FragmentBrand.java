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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.adapters.AdapterBrand;
import com.practica02.mbussiness.dialogs.brand.AddBrand;
import com.practica02.mbussiness.model.entity.Brand;

import java.util.ArrayList;
import java.util.List;

public class FragmentBrand extends Fragment {

    public static AdapterBrand adapterMarca;
    private static List<Brand> listaMarca;
    //private BrandViewModel viewModel;
    //AdapterMarca adapterMarca;
    RecyclerView rvBrand;
    //ArrayList<Marca> listaMarca;
    Button addBrand;
    EditText search;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand,container,false);
        rvBrand = view.findViewById(R.id.rvBrand);
        addBrand = view.findViewById(R.id.addMarca);
        search = view.findViewById(R.id.editSearchBrand);

        listaMarca = new ArrayList<>();

        addBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });

        rvBrand.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMarca = new AdapterBrand();
        adapterMarca.setBrand(listaMarca);
        rvBrand.setAdapter(adapterMarca);
        return view;
    }
    public void openDialogAdd() {
        AddBrand addMarcaDialog = new AddBrand();
        addMarcaDialog.show(this.getFragmentManager(), "example dialog");
    }
}
