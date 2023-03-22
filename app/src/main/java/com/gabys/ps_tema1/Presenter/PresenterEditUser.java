package com.gabys.ps_tema1.Presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.gabys.ps_tema1.Model.Persistence.UserPersistence;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewEditUser;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PresenterEditUser {
    IViewEditUser iViewEditUser;
    UserPersistence userPersistence;
    Context context;
    User user;

    public PresenterEditUser(IViewEditUser iViewEditUser, Context context) {
        this.iViewEditUser = iViewEditUser;
        this.userPersistence = new UserPersistence(context);
        this.context = context;

        Gson gson = new Gson();
        user = gson.fromJson(((Activity)context).getIntent().getStringExtra("user"), User.class);

        this.iViewEditUser.setUserId(user.getId());
        this.iViewEditUser.setUsername(user.getUsername());
        this.iViewEditUser.setPassword(user.getPassword());

        ArrayList<String> types = Arrays.stream(context.getResources().getStringArray(R.array.user_roles)).collect(Collectors.toCollection(ArrayList::new));
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.iViewEditUser.setRoleSpinnerAdapter(typeAdapter);
        this.iViewEditUser.setRole(user.getRole());
    }

    public void updateUser() {
        user.setUsername(this.iViewEditUser.getUsername());
        user.setPassword(this.iViewEditUser.getPassword());

        int role = 0;
        String roleS = this.iViewEditUser.getRoleS();
        switch(roleS){
            case "Client":
                role = 0;
                break;
            case "Angajat":
                role = 1;
                break;
            case "Admin":
                role = 2;
                break;
        }
        user.setRole(role);
        userPersistence.updateUser(user);
        quitActivity();
    }

    public void quitActivity(){
        ((Activity) context).finish();
    }
}
