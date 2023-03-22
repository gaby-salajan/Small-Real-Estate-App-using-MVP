package com.gabys.ps_tema1.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Adapters.PropertyCardAdapter;

public class PropertyFragment extends Fragment {

    PropertyCardAdapter propertyCardAdapter;
    public PropertyFragment(PropertyCardAdapter pca) {
        propertyCardAdapter = pca;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_properties, container, false);

        RecyclerView propertyRV = view.findViewById(R.id.property_RecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        propertyRV.setLayoutManager(linearLayoutManager);

        propertyRV.setAdapter(propertyCardAdapter);

        return view;
    }
}
