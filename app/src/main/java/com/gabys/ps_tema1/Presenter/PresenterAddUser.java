package com.gabys.ps_tema1.Presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import com.gabys.ps_tema1.Model.Persistence.UserPersistence;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.R;
import com.gabys.ps_tema1.View.Interface.IViewAddUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PresenterAddUser {
    IViewAddUser iViewAddUser;
    UserPersistence userPersistence;
    Context context;

    public PresenterAddUser(IViewAddUser iViewAddUser, Context context) {
        this.iViewAddUser = iViewAddUser;
        this.userPersistence = new UserPersistence(context);
        this.context = context;

        ArrayList<String> roles = Arrays.stream(context.getResources().getStringArray(R.array.user_roles)).collect(Collectors.toCollection(ArrayList::new));
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, roles);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.iViewAddUser.setRoleSpinnerAdapter(roleAdapter);
    }

    public void addUser() {
        User user = new User();

        user.setUsername(this.iViewAddUser.getUsername());
        user.setPassword(this.iViewAddUser.getPassword());

        int role = 0;
        String roleS = this.iViewAddUser.getRoleS();
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
        userPersistence.addUser(user);
        quitActivity();
    }

    public void quitActivity(){
        ((Activity) context).finish();
    }
}
