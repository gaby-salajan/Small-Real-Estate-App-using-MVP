package com.gabys.ps_tema1.View.Interface;

import android.widget.ArrayAdapter;

public interface IViewAddUser {
    void setRoleSpinnerAdapter(ArrayAdapter<String> roleAdapter);
    String getUsername();
    String getPassword();
    String getRoleS();
}
