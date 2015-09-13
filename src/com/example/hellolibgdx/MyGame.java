package com.example.hellolibgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class MyGame extends Game {
	//维持GameScreen的引用
	GameScreen gameScreen;
	@Override
	public void create() {
		//加载资源
		Assets.load();
		//初始化GameScreen对象
		gameScreen = new GameScreen(this);
		//切屏
		setScreen(gameScreen);
	}
	/**
	 * 可用于执行销毁资源的操作
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	/**
	 * 用于处理游戏失去焦点时的一些操作
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
	/**
	 * 每一帧都会执行
	 */
	@Override
	public void render() {
		super.render();
	}
	/**
	 * 当游戏的分辨率发生变化的时候会调用
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
