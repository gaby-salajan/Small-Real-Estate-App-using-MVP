package com.gabys.ps_tema1.View;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.Presenter.PresenterClient;
import com.gabys.ps_tema1.R;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity implements IViewClient {
    private Button filterButton;

    private Button loginButton;
    private ConstraintLayout layout;
    private PresenterClient presenter;
    PropertyCardAdapter propertyCardAdapter;
    RecyclerView propertiesRV;

    private void initComponents(){
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout1);
        propertiesRV = findViewById(R.id.property_RecyclerView);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> onLoginButtonClick());
        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(v -> onFilterButtonClick());
        propertyCardAdapter = new PropertyCardAdapter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

        presenter = new PresenterClient( this, this);
        presenter.setupRecyclerView(this, propertiesRV);
    }

    private void onLoginButtonClick(){
        presenter.onLoginButtonClick(layout.getContext(), getLayoutInflater());
    }

    private void onFilterButtonClick() {
        presenter.onFilterButtonClick(layout.getContext(), getLayoutInflater());
    }

    @Override
    public void setProperties(ArrayList<Property> propertiesList) {
        this.propertyCardAdapter.setItems(propertiesList);
    }

    @Override
    public void setUser(User user) {

    }
    @Override
    public void bindAdapterToRecycler() {
        this.propertiesRV.setAdapter(propertyCardAdapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
