package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.practica02.mbussiness.model.entity.Brand;

import java.util.List;
import java.util.stream.Collectors;

public class BrandSpinnerAdapter extends ArrayAdapter<String> {
    private List<? extends Brand> brands;

    public List<? extends Brand> getBrands() {
        return brands;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setBrands(List<? extends Brand> brands) {
        this.clear();
        this.addAll(brands.parallelStream().map(Brand::getName).collect(Collectors.toList()));
        this.brands = brands;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public BrandSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Brand> brands) {
        super(context, resource, brands.parallelStream().map(Brand::getName).collect(Collectors.toList()));
    }
}
