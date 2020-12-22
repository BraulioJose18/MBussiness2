package com.practica02.mbussiness.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.dialogs.brand.AddBrand;

import java.util.Objects;

public class BrandFragment extends Fragment {

    private RecyclerView recyclerViewBrand;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        //recyclerViewBrand = view.findViewById(R.id.);
        return view;
    }

    public void openDialogAdd() {
        AddBrand brandAddDialog = new AddBrand();
        brandAddDialog.show(Objects.requireNonNull(this.getFragmentManager()), "example dialog");
    }
}
