package com.gabys.ps_tema1.View.Interface;

import android.widget.ArrayAdapter;

public interface IViewAddProperty {
    String getPropertyTitle();
    String getPropertyLocation();
    int getPropertyRoomsNo();
    String getPropertyType();
    Float getPropertyPrice();
    boolean getPropertyAvailability();
    String getPropertyImageURL();
    void setTypeSpinnerAdapter(ArrayAdapter<String> typeAdapter);

    void setPropertyType(int typeIndex);
}
