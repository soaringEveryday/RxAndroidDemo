package com.jason.rxjavademo.domian;

import android.graphics.drawable.Drawable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Chen Haitao on 2016/5/5.
 */
@Data
@Accessors(prefix = "m")
@NoArgsConstructor
public class AppInfo implements Comparable<Object> {

    String mName;
    Drawable mIconDra;

    public AppInfo(String nName, Drawable icon) {
        mName = nName;
        mIconDra = icon;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo)another;
        return getName().compareTo(f.getName());
    }
}
