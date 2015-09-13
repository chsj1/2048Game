package com.example.hellolibgdx;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	/**
    	 * 调用父类的onCreate(...)方法.
    	 * 其中savedInstanceState是当前Activity的状态信息
    	 */
        super.onCreate(savedInstanceState);
        //完成一些初始化信息
        initialize(new MyGame(), false);
    }
}
