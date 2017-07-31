package com.vurtex.weituo.base;

import android.app.Application;

import com.vurtex.weituo.entity.Token;
import com.vurtex.weituo.entity.User;

/**
 * Created by Vurtex on 2017/7/23.
 */

public class PowerApp extends Application {
    public static User user=new User();
    public static Token token=new Token();
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
