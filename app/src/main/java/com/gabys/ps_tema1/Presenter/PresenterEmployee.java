package com.gabys.ps_tema1.Presenter;

import android.content.Context;

import com.gabys.ps_tema1.View.IViewEmployee;

public class PresenterEmployee extends PresenterClient{
    public PresenterEmployee(IViewEmployee iViewEmployee, Context context) {
        super(iViewEmployee, context);
        ((IViewEmployee)getInterface()).addProperty();
    }
}
