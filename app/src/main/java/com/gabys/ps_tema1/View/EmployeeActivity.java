package com.gabys.ps_tema1.View;

import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;

import java.util.ArrayList;

public class EmployeeActivity extends ClientActivity implements IViewEmployee {


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
    public void bindAdapterToRecycler() {
        this.propertiesRV.setAdapter(propertyCardAdapter);
    }

    @Override
    public void setProperties(ArrayList<Property> propertiesList) {
        this.propertyCardAdapter.setItems(propertiesList);
    }

    @Override
    public void setUser(User user) {

    }
}
