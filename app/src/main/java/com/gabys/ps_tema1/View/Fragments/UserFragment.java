package com.gabys.ps_tema1.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Adapters.UserCardAdapter;

public class UserFragment extends Fragment {
    View view;
    UserCardAdapter userCardAdapter;

    public UserFragment(UserCardAdapter user) {
        this.userCardAdapter = user;
    }

    private void initComponents(){
        RecyclerView usersRV = view.findViewById(R.id.user_RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        usersRV.setLayoutManager(linearLayoutManager);
        usersRV.setAdapter(userCardAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycler_user, container, false);
        initComponents();
        return view;
    }
}
