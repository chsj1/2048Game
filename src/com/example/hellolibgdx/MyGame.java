package com.example.hellolibgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class MyGame extends Game {
	//ά��GameScreen������
	GameScreen gameScreen;
	@Override
	public void create() {
		//������Դ
		Assets.load();
		//��ʼ��GameScreen����
		gameScreen = new GameScreen(this);
		//����
		setScreen(gameScreen);
	}
	/**
	 * ������ִ��������Դ�Ĳ���
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	/**
	 * ���ڴ�����Ϸʧȥ����ʱ��һЩ����
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
	/**
	 * ÿһ֡����ִ��
	 */
	@Override
	public void render() {
		super.render();
	}
	/**
	 * ����Ϸ�ķֱ��ʷ����仯��ʱ������
	 */
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
}
