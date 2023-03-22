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

    UserCardAdapter userCardAdapter;
    public UserFragment(UserCardAdapter user) {
        this.userCardAdapter = user;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_user, container, false);

        RecyclerView usersRV = view.findViewById(R.id.user_RecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        usersRV.setLayoutManager(linearLayoutManager);

        usersRV.setAdapter(userCardAdapter);

        return view;
    }
}
