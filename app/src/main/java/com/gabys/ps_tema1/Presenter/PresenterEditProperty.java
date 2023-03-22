package com.gabys.ps_tema1.Presenter;


import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.gabys.ps_tema1.Model.Persistence.PropertyPersistence;
import com.gabys.ps_tema1.Model.Persistence.UserPersistence;
import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewEditProperty;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class PresenterEditProperty {
    private IViewEditProperty iViewEditProperty;
    UserPersistence userPersistence;
    PropertyPersistence propertyPersistence;

    Property property;

    Context context;

    public PresenterEditProperty(IViewEditProperty iViewEditProperty, Context context) {
        this.iViewEditProperty = iViewEditProperty;
        this.userPersistence = new UserPersistence(context);
        this.propertyPersistence = new PropertyPersistence(context);
        this.context = context;

        Gson gson = new Gson();
        property = gson.fromJson(((Activity)context).getIntent().getStringExtra("property"), Property.class);

        Glide.with(context)
                .load(property.getImageURL())
                .centerCrop()
                .into(this.iViewEditProperty.getImageView());

        ArrayList<String> types = Arrays.stream(context.getResources().getStringArray(R.array.property_types)).sorted().collect(Collectors.toCollection(ArrayList::new));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.iViewEditProperty.setTypeSpinnerAdapter(typeAdapter);

        this.iViewEditProperty.setPropertyTitle(property.getTitle());
        this.iViewEditProperty.setPropertyLocation(property.getLocation());
        this.iViewEditProperty.setPropertyRoomsNo(property.getRoomsNo());
        this.iViewEditProperty.setPropertyType(types.indexOf(property.getType()));
        this.iViewEditProperty.setPropertyPrice(property.getPrice());
        this.iViewEditProperty.setPropertyAvailability(property.isAvailable());
        this.iViewEditProperty.setPropertyImageURL(property.getImageURL());
    }

    public void updateProperty() {
        property.setTitle(this.iViewEditProperty.getPropertyTitle());
        property.setLocation(this.iViewEditProperty.getPropertyLocation());
        property.setRoomsNo(this.iViewEditProperty.getPropertyRoomsNo());
        property.setType(this.iViewEditProperty.getPropertyType());
        property.setPrice(this.iViewEditProperty.getPropertyPrice());
        property.setAvailable(this.iViewEditProperty.getPropertyAvailability());
        property.setImageURL(this.iViewEditProperty.getPropertyImageURL());

        propertyPersistence.updateProperty(property);
        quitActivity();
    }

    public void quitActivity(){
        ((Activity) context).finish();
    }
}
