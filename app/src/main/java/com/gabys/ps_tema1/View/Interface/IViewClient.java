package com.gabys.ps_tema1.View.Interface;

import androidx.recyclerview.widget.RecyclerView;

import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;

import java.util.ArrayList;

/**
 * Defineste metode pentru a prelucra informatiile din interfata grafica
 * */
public interface IViewClient{
    void setUserRole(int userRole);
    void bindAdapterToRecycler();
    void setProperties(ArrayList<Property> propertiesList);



}
