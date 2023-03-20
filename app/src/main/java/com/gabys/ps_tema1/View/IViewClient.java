package com.gabys.ps_tema1.View;

import androidx.recyclerview.widget.RecyclerView;

import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;

import java.util.ArrayList;

/**
 * Defineste metode pentru a prelucra informatiile din interfata grafica
 * */
public interface IViewClient{

    void bindAdapterToRecycler();
    void setProperties(ArrayList<Property> propertiesList);
    void setUser(User user);



}
