package com.gabys.ps_tema1.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabys.ps_tema1.Model.Persistence.PropertyPersistence;
import com.gabys.ps_tema1.Model.Persistence.UserPersistence;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.AdminActivity;
import com.gabys.ps_tema1.View.EmployeeActivity;
import com.gabys.ps_tema1.View.Interface.IViewClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
* PresenterClient-ul efectueaza operatiile
* */
public class PresenterClient{
    private IViewClient iViewClient;
    UserPersistence userPersistence;
    PropertyPersistence propertyPersistence;
    private User user;
    ArrayList<Property> propertiesList;

    public PresenterClient(IViewClient iViewClient, Context context) {
        this.iViewClient = iViewClient;
        this.propertiesList = new ArrayList<>();
        this.userPersistence = new UserPersistence(context);
        this.propertyPersistence = new PropertyPersistence(context);

        /*propertyPersistence.createPropertyTable();
        propertyPersistence.insertDummyValues();

        userPersistence.createUserTable();
        userPersistence.insertDummyValues();*/

        //fetchProperties();
        this.iViewClient.setUserRole(0);
        this.iViewClient.bindAdapterToRecycler();
    }

    public PresenterClient(Context context) {
        this.propertiesList = new ArrayList<>();
        this.userPersistence = new UserPersistence(context);
        this.propertyPersistence = new PropertyPersistence(context);
    }

    public void setupRecyclerView(Context context, RecyclerView recyclerView){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void onFilterButtonClick(Context context, LayoutInflater inflater){

        View dialogView = inflater.inflate(R.layout.dialog_filter_properties, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog filterDialog = builder.create();

        filterDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        // Set up views in filter dialog
        EditText minPriceEditText = dialogView.findViewById(R.id.price_min);
        EditText maxPriceEditText = dialogView.findViewById(R.id.price_max);
        Spinner locationSpinner = dialogView.findViewById(R.id.spinner_location);
        Spinner typeSpinner = dialogView.findViewById(R.id.spinner_type);
        Spinner roomSpinner = dialogView.findViewById(R.id.spinner_roomsNo);

        ArraySet<String> locations = new ArraySet<>();
        locations.add("Toate");
        for(Property p : propertiesList){
            locations.add(p.getLocation());
        }

        ArrayList<String> types = Arrays.stream(context.getResources().getStringArray(R.array.property_types)).sorted().collect(Collectors.toCollection(ArrayList::new));


        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, locations.stream().sorted().collect(Collectors.toList()));
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setSelection(locations.size()-1);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setSelection(types.size()-1);

        ArrayList<String> roomsNo = Arrays.stream(context.getResources().getStringArray(R.array.rooms_no)).sorted().collect(Collectors.toCollection(ArrayList::new));
        ArrayAdapter<String> roomsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, roomsNo);
        roomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(roomsAdapter);
        roomSpinner.setSelection(roomsNo.size()-1);

        Button yesButton = dialogView.findViewById(R.id.filter_yes);
        Button noButton = dialogView.findViewById(R.id.filter_no);

        yesButton.setOnClickListener(v -> {
            String minPriceS = String.valueOf(minPriceEditText.getText());
            String maxPriceS = String.valueOf(maxPriceEditText.getText());
            String location = (String) locationSpinner.getSelectedItem();
            String type = (String) typeSpinner.getSelectedItem();
            String roomNo = (String) roomSpinner.getSelectedItem();

            float minPrice = 0f;
            float maxPrice = Float.MAX_VALUE;

            if(!minPriceS.isEmpty())
                minPrice = Float.parseFloat(minPriceS);

            if(!maxPriceS.isEmpty())
                maxPrice = Float.parseFloat(maxPriceS);


            performFilter(minPrice, maxPrice, location, type, roomNo);

            filterDialog.dismiss();
        });

        noButton.setOnClickListener(v -> filterDialog.dismiss());

        filterDialog.show();
    }

    private void performFilter(Float minPrice, Float maxPrice, String location, String type, String roomsNo) {
        ArrayList<Property> filteredProperties = new ArrayList<>();

        for (Property p : propertiesList) {
            if (roomsNo != null && !roomsNo.isEmpty()) {
                if(roomsNo.equals("Toate")){
                    filteredProperties.add(p);
                    continue;
                }
                if(roomsNo.equals("4+")){
                    if(p.getRoomsNo() >= 4){
                        filteredProperties.add(p);
                    }
                    continue;
                }

                if(Integer.parseInt(roomsNo) == p.getRoomsNo()){
                    filteredProperties.add(p);
                }

            }
        }

        filteredProperties = filteredProperties.stream()
                .filter(p -> p.getLocation().equals(location) || location.equals("Toate"))
                .filter(p -> p.getType().equals(type) || type.equals("Toate"))
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toCollection(ArrayList::new));

        this.iViewClient.setProperties(filteredProperties);
    }

    public void sortProperties(){
        propertiesList = (ArrayList<Property>) propertiesList.stream()
                .sorted(Comparator.comparing(Property::getLocation)
                        .thenComparing(Property::getPrice))
                .collect(Collectors.toList());

    }

    public void fetchProperties(){
        propertiesList = propertyPersistence.getAvailableProperties();
        sortProperties();
        this.iViewClient.setProperties(propertiesList);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void onLoginButtonClick(Context context, LayoutInflater inflater) {
        View dialogView = inflater.inflate(R.layout.dialog_login, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog loginDialog = builder.create();

        loginDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_login_bg);

        // Set up views in filter dialog
        EditText usernameEditText = dialogView.findViewById(R.id.usernameField);
        EditText passwordEditText = dialogView.findViewById(R.id.passwordField);

        ArraySet<String> locations = new ArraySet<>();
        locations.add("Toate");
        for(Property p : propertiesList){
            locations.add(p.getLocation());
        }

        Button yesButton = dialogView.findViewById(R.id.filter_yes);
        Button noButton = dialogView.findViewById(R.id.filter_no);

        yesButton.setOnClickListener(v -> {
            String username = String.valueOf(usernameEditText.getText());
            String password = String.valueOf(passwordEditText.getText());

            performLogin(context, username, password);

            loginDialog.dismiss();
        });

        noButton.setOnClickListener(v -> loginDialog.dismiss());

        loginDialog.show();
    }

    private void performLogin(Context context, String username, String password) {
        User res = userPersistence.getLoginUser(username);
        if(res != null){
            this.user = res;

            if(password.equals(user.getPassword())){
                Intent intent = null;
                if(user.getRole() == 1){
                    intent = new Intent(context, EmployeeActivity.class);

                    Gson gson = new Gson();
                    String myJson = gson.toJson(user);
                    intent.putExtra("user", myJson);
                }
                if(user.getRole() == 2){
                    intent = new Intent(context, AdminActivity.class);
                    Gson gson = new Gson();
                    String myJson = gson.toJson(user);
                    intent.putExtra("user", myJson);
                }

                if(intent != null){
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                else{
                    Toast.makeText(context, "Utilizatorul este doar un client", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }
}
