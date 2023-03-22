package com.gabys.ps_tema1.View.Interface;

import android.widget.ArrayAdapter;
import android.widget.ImageView;

public interface IViewEditProperty {
    ImageView getImageView();

    void setPropertyTitle(String title);
    void setPropertyLocation(String location);
    void setPropertyRoomsNo(int roomsNo);

    void setTypeSpinnerAdapter(ArrayAdapter<String> typeAdapter);

    void setPropertyType(int typeIndex);
    void setPropertyPrice(Float price);
    void setPropertyAvailability(boolean availability);
    void setPropertyImageURL(String imageURL);

    String getPropertyTitle();
    String getPropertyLocation();
    int getPropertyRoomsNo();
    String getPropertyType();
    Float getPropertyPrice();
    boolean getPropertyAvailability();
    String getPropertyImageURL();
}
