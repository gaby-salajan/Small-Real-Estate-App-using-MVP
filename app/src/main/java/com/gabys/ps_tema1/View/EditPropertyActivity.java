package com.gabys.ps_tema1.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.gabys.ps_tema1.Presenter.PresenterEditProperty;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewEditProperty;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class EditPropertyActivity extends AppCompatActivity implements IViewEditProperty {

    ImageView propertyImg;
    EditText titleEt, locationEt, roomsNoEt, priceEt, imageURLEt;
    Spinner typeSpinner;
    SwitchMaterial isAvailableSwitch;
    Button finishB, cancelB;
    PresenterEditProperty presenter;
    private void initComponents(){
        setContentView(R.layout.activity_edit_property);

        propertyImg = findViewById(R.id.property_image);
        titleEt = findViewById(R.id.title_et);
        locationEt = findViewById(R.id.location_et);
        roomsNoEt = findViewById(R.id.roomsNo_et);
        priceEt = findViewById(R.id.price_et);
        imageURLEt = findViewById(R.id.imageURL_et);
        typeSpinner = findViewById(R.id.spinner_type);
        finishB = findViewById(R.id.finish_button);
        cancelB = findViewById(R.id.cancel_button);
        isAvailableSwitch = findViewById(R.id.isAvailable_switch);

        finishB.setOnClickListener(v -> presenter.updateProperty());
        cancelB.setOnClickListener(v -> presenter.quitActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

        this.presenter = new PresenterEditProperty(this, this);
    }

    @Override
    public ImageView getImageView(){
        return this.propertyImg;
    }

    @Override
    public void setPropertyTitle(String title) {
        titleEt.setText(title);
    }

    @Override
    public void setPropertyLocation(String location) {
        locationEt.setText(location);
    }

    @Override
    public void setPropertyRoomsNo(int roomsNo) {
        roomsNoEt.setText(String.valueOf(roomsNo));
    }

    @Override
    public void setTypeSpinnerAdapter(ArrayAdapter<String> typeAdapter){
        typeSpinner.setAdapter(typeAdapter);
    }

    @Override
    public void setPropertyType(int typeIndex) {
        typeSpinner.setSelection(typeIndex);
    }

    @Override
    public void setPropertyPrice(Float price) {
        priceEt.setText(String.valueOf(price));
    }

    @Override
    public void setPropertyAvailability(boolean availability) {
        isAvailableSwitch.setChecked(availability);
    }

    @Override
    public void setPropertyImageURL(String imageURL) {
        imageURLEt.setText(imageURL);
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
}