package com.gabys.ps_tema1.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.Presenter.PresenterAdmin;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Adapters.PropertyCardAdapter;
import com.gabys.ps_tema1.View.Adapters.UserCardAdapter;
import com.gabys.ps_tema1.View.Adapters.ViewPagerAdapter;
import com.gabys.ps_tema1.View.Interface.IViewAdmin;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements IViewAdmin {
    Button filterButton;
    ExtendedFloatingActionButton logoutButton;
    LinearLayout layout;
    PropertyCardAdapter propertyCardAdapter;
    UserCardAdapter userCardAdapter;
    ExtendedFloatingActionButton addUserButton;
    PresenterAdmin presenter;
    TabLayout tabLayout;
    ViewPager viewPager;

    private void initComponents(){
        setContentView(R.layout.activity_admin);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);

        layout = findViewById(R.id.layout1);
        logoutButton = findViewById(R.id.extended_fab);
        filterButton = findViewById(R.id.filterButton);
        logoutButton.setBackgroundColor(getColor(R.color.logout_tint));
        addUserButton = findViewById(R.id.addUser_fab);

        filterButton.setOnClickListener(v -> onFilterButtonClick());
        logoutButton.setOnClickListener(v -> onLogoutButtonClick());
        addUserButton.setOnClickListener(v -> onAddUserButtonClick());

        propertyCardAdapter = new PropertyCardAdapter(this);
        userCardAdapter = new UserCardAdapter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

        presenter = new PresenterAdmin(this, this);
        userCardAdapter.addPresenter(presenter);
        presenter.fetchProperties();
        presenter.fetchUsers();
    }

    private void onAddUserButtonClick() {
        presenter.onAddUserButtonClick(layout.getContext());
    }

    private void onLogoutButtonClick() {
        presenter.onLogoutButtonClick(layout.getContext());
    }

    private void onFilterButtonClick() {
        this.presenter.onFilterButtonClick(this, getLayoutInflater());
    }

    @Override
    public void setProperties(ArrayList<Property> propertiesList) {
        this.propertyCardAdapter.setItems(propertiesList);
    }

    @Override
    public void setUsers(ArrayList<User> userList) {
        this.userCardAdapter.setItems(userList);
    }

    @Override
    public void setUserRole(int userRole) {
        this.propertyCardAdapter.setUserRole(userRole);
    }

    @Override
    public void setAdapters(ViewPagerAdapter viewPagerAdapter) {
        this.viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public PropertyCardAdapter getPropertyCardAdapter() {
        return this.propertyCardAdapter;
    }

    @Override
    public UserCardAdapter getUserCardAdapter() {
        return this.userCardAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchProperties();
        presenter.fetchUsers();
    }
}
