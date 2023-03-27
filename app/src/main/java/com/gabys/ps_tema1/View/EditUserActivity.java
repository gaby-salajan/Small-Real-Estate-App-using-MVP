package com.gabys.ps_tema1.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.gabys.ps_tema1.Presenter.PresenterEditUser;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewEditUser;

public class EditUserActivity extends AppCompatActivity implements IViewEditUser {
    TextView userId;
    EditText usernameEt, passwordEt;
    Spinner roleSpinner;

    Button finishB, cancelB;

    PresenterEditUser presenter;

    private void initComponents(){
        setContentView(R.layout.activity_edit_user);
        userId = findViewById(R.id.id_et);
        usernameEt = findViewById(R.id.username_et);
        passwordEt = findViewById(R.id.password_et);
        roleSpinner = findViewById(R.id.spinner_role);
        finishB = findViewById(R.id.finish_button);
        cancelB = findViewById(R.id.cancel_button);

        finishB.setOnClickListener(v -> presenter.updateUser());
        cancelB.setOnClickListener(v -> presenter.quitActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        this.presenter = new PresenterEditUser(this, this);
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

    @Override
    public void setUsername(String username) {
        this.usernameEt.setText(username);
    }

    @Override
    public void setPassword(String password) {
        this.passwordEt.setText(password);
    }

    @Override
    public void setRole(int role) {
        this.roleSpinner.setSelection(role);
    }

    @Override
    public void setUserId(int id) {
        this.userId.setText(String.valueOf(id));
    }
}
