package com.gabys.ps_tema1.Presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.gabys.ps_tema1.Model.Persistence.PropertyPersistence;
import com.gabys.ps_tema1.Model.Persistence.UserPersistence;
import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewAddProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PresenterAddProperty {
    IViewAddProperty iViewAddProperty;
    UserPersistence userPersistence;
    PropertyPersistence propertyPersistence;

    Context context;
    public PresenterAddProperty(IViewAddProperty iViewAddProperty, Context context) {
        this.iViewAddProperty = iViewAddProperty;
        this.userPersistence = new UserPersistence(context);
        this.propertyPersistence = new PropertyPersistence(context);
        this.context = context;

        ArrayList<String> types = Arrays.stream(context.getResources().getStringArray(R.array.property_types)).sorted().collect(Collectors.toCollection(ArrayList::new));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.iViewAddProperty.setTypeSpinnerAdapter(typeAdapter);
        this.iViewAddProperty.setPropertyType(types.size()-1);
    }

    public void addProperty() {
        Property property = new Property();

        property.setTitle(this.iViewAddProperty.getPropertyTitle());
        property.setLocation(this.iViewAddProperty.getPropertyLocation());
        property.setRoomsNo(this.iViewAddProperty.getPropertyRoomsNo());
        property.setType(this.iViewAddProperty.getPropertyType());
        property.setPrice(this.iViewAddProperty.getPropertyPrice());
        property.setAvailable(this.iViewAddProperty.getPropertyAvailability());
        property.setImageURL(this.iViewAddProperty.getPropertyImageURL());

        propertyPersistence.addProperty(property);

        quitActivity();
    }

    public void quitActivity(){
        ((Activity) context).finish();
    }
}
