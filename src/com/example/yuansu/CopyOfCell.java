package com.example.yuansu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.example.hellolibgdx.Assets;

/**
 * 分数格子
 * 
 * @author Administrator
 * 
 */
public class CopyOfCell extends Actor {
	//成员变量的定义
	/**
	 * 分数加倍
	 */
	public void doubleValue() {
	}
	/**
	 *  在duration时间内移动到(boardX,boardY)这个位置上
	 * @param boardX
	 * @param boardY
	 * @param duration
	 */
	public void moveTo(int boardX, int boardY, int duration) {
	}
	/**
	 * 将在board中的位置转化成屏幕中的位置
	 * 
	 * @param boardX
	 * @param boardY
	 */
	private void convertBoardToScreen(int boardX, int boardY) {
	}
	/**
	 * 构造函数: 根据分数格子的分数值和在board中的坐标来生成一个分数格子
	 * 
	 * @param value
	 *            分数值
	 * @param boardX
	 *            在board中的坐标
	 * @param boardY
	 */
	public CopyOfCell(int value, int boardX, int boardY) {
	}
	//构造函数的定义
	/**
	 * 绘制分数格子
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
	}
	/**
	 * 绘制分数格子的背景
	 * @param batch
	 */
	public void drawBg(SpriteBatch batch) {
	}
	/**
	 * 绘制分数格子的分数
	 * @param batch
	 */
	public void drawFenshu(SpriteBatch batch) {
	}
}
