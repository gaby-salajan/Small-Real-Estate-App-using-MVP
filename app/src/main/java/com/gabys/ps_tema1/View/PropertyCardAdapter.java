package com.gabys.ps_tema1.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.R;

import java.util.ArrayList;

public class PropertyCardAdapter extends RecyclerView.Adapter<PropertyCardAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Property> propertiesList;

    public PropertyCardAdapter(Context context) {
        this.context = context;
        this.propertiesList = new ArrayList<>();
    }
    public PropertyCardAdapter(Context context, ArrayList<Property> properties) {
        this.context = context;
        this.propertiesList = new ArrayList<>(properties);
    }

    public void setItems(ArrayList<Property> properties) {
        this.propertiesList.clear();
        this.propertiesList.addAll(properties);
        notifyDataSetChanged();
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
        holder.propertyLocation.setText(model.getLocation());
        holder.propertyPrice.setText(String.valueOf(model.getPrice()));
    }

    @Override
    public int getItemCount() {
        return propertiesList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //complete with card details
        private final ImageView propertyImage;
        private final TextView propertyTitle;
        private final TextView propertyLocation;
        private final TextView propertyPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyImage = itemView.findViewById(R.id.card_image);
            propertyTitle = itemView.findViewById(R.id.card_title);
            propertyLocation = itemView.findViewById(R.id.card_location);
            propertyPrice = itemView.findViewById(R.id.card_price);
        }
    }
}
