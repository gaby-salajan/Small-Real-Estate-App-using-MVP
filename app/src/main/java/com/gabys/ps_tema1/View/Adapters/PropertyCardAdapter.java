package com.gabys.ps_tema1.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Presenter.PresenterAdmin;
import com.gabys.ps_tema1.Presenter.PresenterEmployee;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.EditPropertyActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PropertyCardAdapter extends RecyclerView.Adapter<PropertyCardAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Property> propertiesList;
    private PresenterEmployee presenter;
    private int role;

    public PropertyCardAdapter(Context context) {
        this.context = context;
        this.propertiesList = new ArrayList<>();
        this.role = 0;
    }
    public void setUserRole(int userRole){
        this.role = userRole;
    }

    public void setItems(ArrayList<Property> properties) {
        this.propertiesList.clear();
        this.propertiesList.addAll(properties);
        notifyDataSetChanged();
    }

    public void addPresenter(PresenterEmployee presenter){
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public PropertyCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_property, parent, false);
        return new PropertyCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyCardAdapter.ViewHolder holder, int position) {
        Property model = propertiesList.get(position);
        Glide.with(this.context)
                .load(model.getImageURL())
                .centerCrop()
                .into(holder.propertyImage);
        holder.propertyTitle.setText(model.getTitle());
        holder.propertyLocation.setText("Locatie: " + model.getLocation());
        holder.propertyRoomsNo.setText("Camere: " + String.valueOf(model.getRoomsNo()));
        holder.propertyType.setText("Tip: " + model.getType());
        holder.propertyPrice.setText("Pret: " + String.valueOf(model.getPrice()));
        holder.propertyAvailability.setText(model.isAvailable() ? "Disponibil" : "Indisponibil");

        switch (role){
            case 1:
                holder.modifyButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setVisibility(View.VISIBLE);
                break;
            case 2:
            default:
                holder.buttonLayout.setVisibility(View.GONE);
        }

        holder.modifyButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPropertyActivity.class);

            Gson gson = new Gson();
            String myJson = gson.toJson(model);
            intent.putExtra("property", myJson);

            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            presenter.deleteProperty(model.getId());
        });
    }

    @Override
    public int getItemCount() {
        return propertiesList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //complete with card details

        private final TextView propertyTitle;
        private final TextView propertyLocation;
        private final TextView propertyRoomsNo;
        private final TextView propertyType;
        private final TextView propertyPrice;
        private final TextView propertyAvailability;
        private final ImageView propertyImage;
        private final LinearLayout buttonLayout;
        private final Button modifyButton;
        private final Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            propertyTitle = itemView.findViewById(R.id.card_title);
            propertyLocation = itemView.findViewById(R.id.card_location);
            propertyRoomsNo = itemView.findViewById(R.id.card_roomsNo);
            propertyType = itemView.findViewById(R.id.card_type);
            propertyPrice = itemView.findViewById(R.id.card_price);
            propertyAvailability = itemView.findViewById(R.id.card_isAvailable);
            propertyImage = itemView.findViewById(R.id.card_image);

            buttonLayout = itemView.findViewById(R.id.button_layout);

            modifyButton = itemView.findViewById(R.id.modify_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
