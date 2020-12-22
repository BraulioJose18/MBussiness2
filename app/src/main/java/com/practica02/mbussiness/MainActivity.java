package com.practica02.mbussiness;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.practica02.mbussiness.fragments.BrandFragment;
import com.practica02.mbussiness.fragments.FragmentArticle;
import com.practica02.mbussiness.fragments.FragmentUnitOfMeasurement;
import com.practica02.mbussiness.fragments.HelpMBusiness;
import com.practica02.mbussiness.model.dto.BrandDTO;
import com.practica02.mbussiness.model.entity.Brand;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Unidad de Medida");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigataion_drawer_open, R.string.navigataion_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        //navigationView.getMenu().getItem(0).setChecked(false);

        /*fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new FragmentUnitOfMeasurement());
        fragmentTransaction.commit();*/

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        item.setChecked(true);
        myUnchecked(item);

        if (item.getItemId() == R.id.item1) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new FragmentUnitOfMeasurement());
            fragmentTransaction.commit();
            toolbar.setTitle("Unidad de Medida");
        }
        if (item.getItemId() == R.id.item2) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new FragmentArticle());
            fragmentTransaction.commit();
            toolbar.setTitle("Maestro de Artículos");
        }
        if (item.getItemId() == R.id.item3) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new BrandFragment());
            fragmentTransaction.commit();
            toolbar.setTitle("Marcas");
        }
        if(item.getItemId() == R.id.item4){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new HelpMBusiness());
            fragmentTransaction.commit();
            toolbar.setTitle("Ayuda/Información");
        }

        return false;
    }

    private void myUnchecked(MenuItem item) {
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            if (navigationView.getMenu().getItem(i).getItemId() != item.getItemId())
                navigationView.getMenu().getItem(i).setChecked(false);
        }
    }
}
