package com.gabys.ps_tema1.View.Interface;

import android.widget.ArrayAdapter;

public interface IViewEditUser {
    void setRoleSpinnerAdapter(ArrayAdapter<String> roleAdapter);
    String getUsername();
    String getPassword();
    String getRoleS();
    void setUsername(String username);
    void setPassword(String password);
    void setRole(int role);
    void setUserId(int id);
}
