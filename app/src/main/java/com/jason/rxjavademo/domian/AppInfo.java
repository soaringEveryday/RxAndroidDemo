package com.jason.rxjavademo.domian;

import android.graphics.drawable.Drawable;

/**
 * Created by Chen Haitao on 2016/5/5.
 */
public class AppInfo implements Comparable<Object> {

    String mName;
    Drawable mIconDra;

    public AppInfo(String nName, Drawable icon) {
        mName = nName;
        mIconDra = icon;
    }

    public AppInfo() {
    }

    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo)another;
        return getName().compareTo(f.getName());
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Drawable getIconDra() {
        return mIconDra;
    }

    public void setIconDra(Drawable iconDra) {
        mIconDra = iconDra;
    }
}
