package com.example.yuansu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * 用于存储每种分值对应的分数格子的背景的颜色
 * @author Administrator
 *
 */
public class Square {
	//分值
	private int key;
	//对应背景
	private TextureRegion smallTexture;
	//背景的颜色
	private Color color;
	//缩放系数
	private float scale;

	/**
	 * 根据分数格子的分值、和对应的RGB值来构造一个square
	 * @param key 分数格子的分值
	 * @param r 颜色的RGB值
	 * @param g
	 * @param b
	 */
	public Square(int key, int r, int g, int b) {
		//给成员变量key赋值
		this.key = key;
		//给成员变量color赋值
		this.color = new Color((r) / 255f, (g) / 255f, (b) / 255f, 1.0f);
		//如果当前元素分分值<=10000
		if (Integer.toString(key).length() <= 4){
			//那么当前分值保持原大
			this.scale = 1;
		}else{//如果当前分值>10000
			//那么则进行一定的缩放
			this.scale = 4f / Integer.toString(key).length();
		}
		//给分数格子创建背景
		this.smallTexture = createBackground(color);
	}
	/**
	 * 根据float型的(r,g,b)来创建一个Square
	 * @param key
	 * @param r
	 * @param g
	 * @param b
	 */
	public Square(int key, float r, float g, float b) {
		//给成员变量key赋值
		this.key = key;
		//给成员变量color赋值
		this.color = new Color(r, g, b, 1.0f);
		//如果当前元素分分值<=10000
		if (Integer.toString(key).length() <= 4){
			//那么当前分值保持原大
			this.scale = 1;
		}else{//如果当前分值>10000
			//那么则进行一定的缩放
			this.scale = 4f / Integer.toString(key).length();
		}
		//给分数格子创建背景
		this.smallTexture = createBackground(color);
	}
	/**
	 * 根据Color对象与key值构建Square
	 * @param key
	 * @param color
	 */
	public Square(int key, Color color) {
		//给成员变量key赋值
		this.key = key;
		//给成员变量color赋值
		this.color = color;
		//如果当前元素分分值<=10000
		if (Integer.toString(key).length() <= 4){
			//那么当前分值保持原大
			this.scale = 1;
		}else{//如果当前分值>10000
			//那么则进行一定的缩放
			this.scale = 4f / Integer.toString(key).length();
		}
		//给分数格子创建背景
		this.smallTexture = createBackground(color);
	}

	/**
	 * 根据颜色，利用Pixmap来生成一个分数格子的背景
	 * @param color
	 * @return
	 */
	private TextureRegion createBackground(Color color) {
		//如果分数格子的分值>=128
		if (key >= 128) {
			//构造Pixmap
			Pixmap pixmap = new Pixmap(MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), Format.RGBA8888);
			//设置Pixmap的颜色
			pixmap.setColor(Color.WHITE);
			//利用Pimxap绘制一个空心的矩形
			pixmap.drawRectangle(0, 0,MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH));
			//设置颜色
			pixmap.setColor(color);
			//利用Pixmap绘制一个实心的矩形
			pixmap.fillRectangle(1, 1,MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH) -2,MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH) -2);
			//初始化Texture
			Texture texture = new Texture(pixmap);
			//设置一下Texture对象的Filter
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			//初始化TextureRegion
			TextureRegion region = new TextureRegion(texture,90,90);
			//将Pixmap销毁掉
			pixmap.dispose();
			return region;
		} else {//如果分数格子的分值< 128
			//构造Pixmap
			Pixmap pixmap = new Pixmap(MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), Format.RGBA8888);
			//设置颜色
			pixmap.setColor(color);
			//绘制一个实心矩形
			pixmap.fillRectangle(0, 0, MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH) );
			//初始化一个Texture
			Texture texture = new Texture(pixmap);
			//初始化一个TextureRegion
			TextureRegion region = new TextureRegion(texture,90,90);
			//将pixmap销毁掉
			pixmap.dispose();
			return region;
		}
	}

	/**
	 * 获取分值
	 * @return
	 */
	public int getKey() {
		return key;
	}
	/**
	 * 设置分值
	 * @param key
	 */
	public void setKey(int key) {
		this.key = key;
	}
	/**
	 * 获取分数格子的背景纹理
	 * @return
	 */
	public TextureRegion getTexture() {
		return smallTexture;
	}
	/**
	 * 设置分数格子对应的背景纹理
	 * @param texture
	 */
	public void setTexture(TextureRegion texture) {
		this.smallTexture = texture;
	}
	/**
	 * 获取某一种分值对应的颜色
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * 设置某一种分值对应的颜色
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * 获取缩放倍数
	 * @return
	 */
	public float getScale() {
		return scale;
	}
	/**
	 * 设置缩放倍数
	 * @param scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}
}

