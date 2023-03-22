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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Adapters.ViewPagerAdapter;
import com.gabys.ps_tema1.View.AddUserActivity;
import com.gabys.ps_tema1.View.ClientActivity;
import com.gabys.ps_tema1.View.Interface.IViewAdmin;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PresenterAdmin extends PresenterClient {

    IViewAdmin iViewAdmin;
    ArrayList<User> userList;
    User user;
    Context context;

    public PresenterAdmin(IViewAdmin iViewAdmin, Context context) {
        super(context);
        this.iViewAdmin = iViewAdmin;
        this.context = context;

        Gson gson = new Gson();
        user = gson.fromJson(((Activity)context).getIntent().getStringExtra("user"), User.class);

        this.iViewAdmin.setAdapters(new ViewPagerAdapter(
                ((FragmentActivity)context).getSupportFragmentManager(),
                this.iViewAdmin.getPropertyCardAdapter(),
                this.iViewAdmin.getUserCardAdapter()));

        this.iViewAdmin.setUserRole(user.getRole());
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

        this.iViewAdmin.setProperties(filteredProperties);
    }

    public void onLogoutButtonClick(Context context) {
        Intent intent = new Intent(context, ClientActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void fetchUsers() {
        userList = userPersistence.getAuthUsers();
        this.iViewAdmin.setUsers(userList);
    }
    public void fetchProperties(){
        propertiesList = propertyPersistence.getProperties();
        sortProperties();
        this.iViewAdmin.setProperties(propertiesList);
    }

    public void onAddUserButtonClick(Context context) {
        Intent intent = new Intent(context, AddUserActivity.class);
        context.startActivity(intent);
    }

    public void deleteUser(int id) {
        userPersistence.deleteUser(id);
        fetchUsers();
    }


}
