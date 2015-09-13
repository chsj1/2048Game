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
public class Cell extends Actor {
	// 这个分数格子的分数
	private int value;
	//在屏幕中的横坐标
	private int screenX;
	//在屏幕中的纵坐标
	private int screenY;
	//在board中的横坐标
	private int boardX;
	//在board中的纵坐标
	private int boardY;

	
	/**
	 * 分数加倍
	 */
	public void doubleValue() {
		//分数值加倍
		value *= 2;
		//给分数格子添加一个动作
		addAction(Actions.sequence(Actions.scaleTo(1.12f, 1.12f, 0.035f), Actions.scaleTo(1, 1, 0.035f)));
	}
	
	
	/**
	 *  在duration时间内移动到(boardX,boardY)这个位置上
	 * @param boardX
	 * @param boardY
	 * @param duration
	 */
	public void moveTo(int boardX, int boardY, int duration) {
		// 将在board中的位置转换成屏幕中的位置
		convertBoardToScreen(boardX, boardY);
		// 给元素添加Action
		this.addAction(Actions.moveTo(screenX, screenY, 0.1f / duration,
				Interpolation.linear));
	}

	/**
	 * 将在board中的位置转化成屏幕中的位置
	 * 
	 * @param boardX
	 * @param boardY
	 */
	private void convertBoardToScreen(int boardX, int boardY) {
		//给成员变量boardX赋值
		this.boardX = boardX;
		this.boardY = boardY;
		/**
		 * 屏幕中的位置=起点坐标+每个分数格子的宽度*个数 + 分数格子之间的距离*个数
		 */
		screenX = Constants.BOARD_POS_BOT + Constants.BOARD_GRID_WIDTH
				* (this.boardY + 1) + this.boardY * Constants.CELL_WIDTH;
		screenY = (int) (Constants.BOARD_POS_BOT + Constants.BOARD_GRID_WIDTH
				* Constants.STAGE_STRETCH * (Constants.BOARD_COL - this.boardX) + (Constants.BOARD_COL - 1 - this.boardX)
				* Constants.CELL_WIDTH * Constants.STAGE_STRETCH);
		// 如果不加这个的话,可能会有一点偏差
		screenY += 7;
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
	public Cell(int value, int boardX, int boardY) {
		//调用父类的构造方法
		super();
		//给成员变量value赋值
		this.value = value;
		// 将在board中的坐标转化成在屏幕中的坐标
		convertBoardToScreen(boardX, boardY);
		// 设置分数格子的大小
		setPosition(screenX, screenY);
		// 设置分数格子的大小
		setSize(Constants.CELL_WIDTH, Constants.CELL_WIDTH);
		// 设置分数格子的缩放倍数
		setScale(0.8f);
		// 给这个分数格子添加一个动画
		addAction(Actions.scaleTo(1, 1, 0.035f));
	}

	/**
	 * 获取分数格子的分值
	 * @return
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 设置分数格子的分值
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * 获取分数格子在屏幕中的横坐标
	 * @return
	 */
	public int getScreenX() {
		return screenX;
	}
	/**
	 * 设置分数格子在屏幕中的横坐标
	 * @param screenX
	 */
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}
	/**
	 * 获取分数格子在屏幕中的纵坐标
	 * @return
	 */
	public int getScreenY() {
		return screenY;
	}
	/**
	 * 设置分数格子在屏幕中的纵坐标
	 * @param screenY
	 */
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}
    /**
     * 获取分数格子在board中的横坐标
     * @return
     */
	public int getBoardX() {
		return boardX;
	}
	/**
	 * 设置分数格子在board中的横坐标
	 * @param boardX
	 */
	public void setBoardX(int boardX) {
		this.boardX = boardX;
	}
	/**
	 * 获取分数格子在board中的纵坐标
	 * @return
	 */
	public int getBoardY() {
		return boardY;
	}
	/**
	 * 设置分数格子在board中的纵坐标
	 * @param boardY
	 */
	public void setBoardY(int boardY) {
		this.boardY = boardY;
	}

	/**
	 * 重写Actor的绘制。
	 * 实现分数格子的绘制
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//调用父类的draw(...)方法
		super.draw(batch, parentAlpha);
		// 把背景绘制回来
		drawBg(batch);
		// 把分数绘制出来
		drawFenshu(batch);
	}
	/**
	 * 绘制分数格子的背景
	 * 
	 * @param batch
	 */
	public void drawBg(SpriteBatch batch) {
		//使用SpriteBatch将分数格子绘制出来
		batch.draw(Assets.squares.get(value).getTexture(), getX(), getY(),
				Constants.CELL_WIDTH / 2, Constants.CELL_WIDTH / 2,
				Constants.CELL_WIDTH, Constants.CELL_WIDTH
						* Constants.STAGE_STRETCH, getScaleX(), getScaleX(), 0);
	}
	/**
	 * 绘制分书
	 * @param batch
	 */
	public void drawFenshu(SpriteBatch batch) {
		/**
		 * 计算字体扥位置
		 */
		float x = getX() + Constants.CELL_WIDTH / 2
				- Assets.font.getBounds(Integer.toString(value)).width / 2;
		float y = getY() + Constants.CELL_WIDTH * Constants.STAGE_STRETCH / 2
				+ Assets.font.getBounds("9985").height
				+ Constants.BITMAPFONT_LINEHEIGHT / 2
				* Assets.squares.get(value).getScale() * getScaleX();
		/**
		 * 根据分数格子的分值控制一下字体的颜色
		 */
		// 分值>4
		if (value > 4) {
			// 将字体的颜色设置成白色
			Assets.font.setColor(Color.WHITE);
		} else {// 如果分值<=4
			// 将字体的颜色绘制成灰色
			Assets.font.setColor(Color.GRAY);
		}
		// 绘制分值
		Assets.font.draw(batch, Integer.toString(value), x, y);
	}
}
