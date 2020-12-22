package com.practica02.mbussiness.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.practica02.mbussiness.R;

public class FragmentArticle extends Fragment {

    private Spinner fieldBrand, orderBrand;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article,container,false);

        this.fieldBrand = view.findViewById(R.id.tipo_dato_article);
        this.orderBrand = view.findViewById(R.id.asc_desc_article);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.filtro_article, android.R.layout.simple_spinner_item);
        fieldBrand.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.orden, android.R.layout.simple_spinner_item);
        orderBrand.setAdapter(adapter2);

        return view;
    }
}
