package com.gabys.ps_tema1.View.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gabys.ps_tema1.View.Fragments.PropertyFragment;
import com.gabys.ps_tema1.View.Fragments.UserFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    PropertyCardAdapter propertyCardAdapter;
    UserCardAdapter userCardAdapter;

    public ViewPagerAdapter(FragmentManager fm, PropertyCardAdapter prop, UserCardAdapter user) {
        super(fm);
        this.propertyCardAdapter = prop;
        this.userCardAdapter = user;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new PropertyFragment(propertyCardAdapter);
        }
        else if (position == 1)
        {
            fragment = new UserFragment(userCardAdapter);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Proprietati";
        }
        else if (position == 1)
        {
            title = "Utilizatori";
        }
        return title;
    }
}
