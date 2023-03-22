package com.gabys.ps_tema1.View.Interface;

import com.gabys.ps_tema1.Model.Property;
import com.gabys.ps_tema1.Model.User;
import com.gabys.ps_tema1.Presenter.PresenterAdmin;
import com.gabys.ps_tema1.View.Adapters.PropertyCardAdapter;
import com.gabys.ps_tema1.View.Adapters.UserCardAdapter;
import com.gabys.ps_tema1.View.Adapters.ViewPagerAdapter;

import java.util.ArrayList;

public interface IViewAdmin{
    void setProperties(ArrayList<Property> propertiesList);
    void setUsers(ArrayList<User> userList);
    void setUserRole(int userRole);
    void setAdapters(ViewPagerAdapter viewPagerAdapter);
    PropertyCardAdapter getPropertyCardAdapter();
    UserCardAdapter getUserCardAdapter();
}
