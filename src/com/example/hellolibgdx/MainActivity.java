package com.example.hellolibgdx;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	/**
    	 * ���ø����onCreate(...)����.
    	 * ����savedInstanceState�ǵ�ǰActivity��״̬��Ϣ
    	 */
        super.onCreate(savedInstanceState);
        //���һЩ��ʼ����Ϣ
        initialize(new MyGame(), false);
    }
}
