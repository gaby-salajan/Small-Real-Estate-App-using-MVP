package com.gabys.ps_tema1.View;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.Presenter.PresenterEmployee;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Adapters.PropertyCardAdapter;
import com.gabys.ps_tema1.View.Interface.IViewEmployee;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity implements IViewEmployee {
    Button filterButton;
    ExtendedFloatingActionButton logoutButton;
    ConstraintLayout layout;
    RecyclerView propertiesRV;
    PresenterEmployee presenter;
    PropertyCardAdapter propertyCardAdapter;

    ExtendedFloatingActionButton addPropertyButton;

    private void initComponents(){
        setContentView(R.layout.activity_employee);
        layout = findViewById(R.id.layout1);
        propertiesRV = findViewById(R.id.property_RecyclerView);
        logoutButton = findViewById(R.id.extended_fab);
        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(v -> onFilterButtonClick());
        logoutButton.setBackgroundColor(getColor(R.color.logout_tint));
        logoutButton.setOnClickListener(v -> onLogoutButtonClick());
        addPropertyButton = findViewById(R.id.addProperty_fab);
        addPropertyButton.setOnClickListener(v -> onAddPropertyButtonClick());

        propertyCardAdapter = new PropertyCardAdapter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

        presenter = new PresenterEmployee(this, this);
        propertyCardAdapter.addPresenter(presenter);
        presenter.setupRecyclerView(this, propertiesRV);
        presenter.fetchProperties();
    }

    void onFilterButtonClick() {
        presenter.onFilterButtonClick(layout.getContext(), getLayoutInflater());
    }

    private void onAddPropertyButtonClick(){
        presenter.onAddPropertyButtonClick(layout.getContext());
    }

    private void onLogoutButtonClick() {
        presenter.onLogoutButtonClick(layout.getContext());
    }

    @Override
    public void addProperty() {

    }

    @Override
    public void updateProperty() {

    }

    @Override
    public void deleteProperty() {

    }

    @Override
    public void setUserRole(int userRole) {
        this.propertyCardAdapter.setUserRole(userRole);
    }

    @Override
    public void bindAdapterToRecycler() {
        this.propertiesRV.setAdapter(propertyCardAdapter);
    }

    @Override
    public void setProperties(ArrayList<Property> propertiesList) {
        this.propertyCardAdapter.setItems(propertiesList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchProperties();
    }
}
