package com.gabys.ps_tema1.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.gabys.ps_tema1.Presenter.PresenterAddProperty;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewAddProperty;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AddPropertyActivity extends AppCompatActivity implements IViewAddProperty {
    ImageView propertyImg;
    EditText titleEt, locationEt, roomsNoEt, priceEt, imageURLEt;
    Spinner typeSpinner;

    SwitchMaterial isAvailableSwitch;

    Button finishB, cancelB;

    PresenterAddProperty presenter;

    private void initComponents(){
        propertyImg = findViewById(R.id.property_image);
        propertyImg.setVisibility(View.GONE);

        titleEt = findViewById(R.id.title_et);
        locationEt = findViewById(R.id.location_et);
        roomsNoEt = findViewById(R.id.roomsNo_et);
        priceEt = findViewById(R.id.price_et);
        imageURLEt = findViewById(R.id.imageURL_et);
        typeSpinner = findViewById(R.id.spinner_type);
        finishB = findViewById(R.id.finish_button);
        cancelB = findViewById(R.id.cancel_button);
        isAvailableSwitch = findViewById(R.id.isAvailable_switch);
        isAvailableSwitch.setChecked(true);

        finishB.setOnClickListener(v -> {
            presenter.addProperty();
        });

        cancelB.setOnClickListener(v -> {
            presenter.quitActivity();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);
        initComponents();

        this.presenter = new PresenterAddProperty(this, this);
    }

    @Override
    public String getPropertyTitle() {
        return titleEt.getText().toString();
    }
    @Override
    public String getPropertyLocation() {
        return locationEt.getText().toString();
    }

    @Override
    public int getPropertyRoomsNo() {
        return Integer.parseInt(roomsNoEt.getText().toString());
    }

    @Override
    public String getPropertyType() {
        return typeSpinner.getSelectedItem().toString();
    }

    @Override
    public Float getPropertyPrice() {
        return Float.parseFloat(priceEt.getText().toString());
    }

    @Override
    public boolean getPropertyAvailability() {
        return isAvailableSwitch.isChecked();
    }

    @Override
    public String getPropertyImageURL() {
        return imageURLEt.getText().toString();
    }

    @Override
    public void setTypeSpinnerAdapter(ArrayAdapter<String> typeAdapter) {
        typeSpinner.setAdapter(typeAdapter);
    }

    @Override
    public void setPropertyType(int typeIndex) {
        typeSpinner.setSelection(typeIndex);
    }
}
