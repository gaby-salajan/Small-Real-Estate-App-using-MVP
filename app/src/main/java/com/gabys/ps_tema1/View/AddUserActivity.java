package com.gabys.ps_tema1.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.gabys.ps_tema1.Presenter.PresenterAddUser;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewAddUser;

public class AddUserActivity extends AppCompatActivity implements IViewAddUser {
    TextView userId;
    EditText usernameEt, passwordEt;
    Spinner roleSpinner;
    Button finishB, cancelB;
    PresenterAddUser presenter;

    private void initComponents(){
        setContentView(R.layout.activity_edit_user);
        userId = findViewById(R.id.id_et);
        userId.setVisibility(View.GONE);
        usernameEt = findViewById(R.id.username_et);
        passwordEt = findViewById(R.id.password_et);
        roleSpinner = findViewById(R.id.spinner_role);
        finishB = findViewById(R.id.finish_button);
        cancelB = findViewById(R.id.cancel_button);
        roleSpinner.setSelection(0);

        finishB.setOnClickListener(v -> presenter.addUser());
        cancelB.setOnClickListener(v -> presenter.quitActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        this.presenter = new PresenterAddUser(this, this);
    }

    @Override
    public void setRoleSpinnerAdapter(ArrayAdapter<String> roleAdapter){
        this.roleSpinner.setAdapter(roleAdapter);
    }

    @Override
    public String getUsername(){
        return usernameEt.getText().toString();
    }

    @Override
    public String getPassword(){
        return passwordEt.getText().toString();
    }

    @Override
    public String getRoleS(){
        return roleSpinner.getSelectedItem().toString();
    }
}
