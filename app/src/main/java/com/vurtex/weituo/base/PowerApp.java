package com.vurtex.weituo.base;

import android.app.Application;

import com.vurtex.weituo.entity.User;

/**
 * Created by Vurtex on 2017/7/23.
 */

public class PowerApp extends Application {
    public static User user;
    @Override
    public void onCreate() {
        super.onCreate();
        user=new User();
    }
}
