package com.example.hellolibgdx;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.yuansu.Square;

/**
 * 游戏中的资源管理类
 * @author Administrator
 *
 */
public class Assets {
	//用于管理和土资源
	static TextureAtlas atlas;
	//用于保存背景图的TextureRegion对象
	static TextureRegion bgRegion;
	//用于保存exit按钮的TextureRegion对象
	static TextureRegion exitRegion;
	//用于保存new按钮的TextureRegion对象
	static TextureRegion newRegion;
	//用于保存gameover的TextureRegion对象
	static TextureRegion gameoverRegion;
	//用于保存胜利界面和失败界面的白色背景的TextureRegion对象
	static TextureRegion opacityRegion;
	//用于保存重新开始按钮的TextureRegion对象
	static TextureRegion restartRegion;
	//用于保存再次尝试按钮的TextureRegion对象
	static TextureRegion tryagainRegion;
	//游戏中用到的字体
	public static BitmapFont font;
	//用于存储各种分值对
	public static Map<Integer,Square> squares;
	
	/**
	 * 资源的加载
	 */
	public static void load(){
		//把合图以后的图片加载进来
		atlas = new TextureAtlas(Gdx.files.internal("data/2048.txt"));
		//找到合图中的背景图
		bgRegion = atlas.findRegion("background");
		//找到合图中的退出按钮
		exitRegion = atlas.findRegion("exit");
		//找到合图中的gameover图片
		gameoverRegion = atlas.findRegion("game-over");
		//找到gameover对象的白色背景图
		opacityRegion = atlas.findRegion("opacity");
		//找到重新开始按钮对应的图片
		restartRegion = atlas.findRegion("restart");
		//找到再次尝试按钮对应的图片
		tryagainRegion = atlas.findRegion("try-again");
		//找到new按钮对应的图片
		newRegion = atlas.findRegion("new");
		//初始化BitgmapFont对象
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		//初始化各种分数对应的颜色
		initColor();
	}
	
	
	/**
	 * 初始化各种分数对应的颜色
	 */
	public static void initColor() {
		//初始化HashMap用来保存各种分值所对应的颜色
		squares = new HashMap<Integer, Square>();
		//保存分值2所对应的颜色
		squares.put(2, new Square(2, 238, 228, 218));
		//保存分值4所对应的颜色
		squares.put(4, new Square(4, 237, 205, 123));
		//保存分值8所对应的颜色
		squares.put(8, new Square(8, 255, 131, 30));
		//保存分值16所对应的颜色
		squares.put(16, new Square(16, 255, 129, 8));
		//保存分值32所对应的颜色
		squares.put(32, new Square(32, 237, 88, 52));
		//保存分值64所对应的颜色
		squares.put(64, new Square(64, 217, 54, 17));
		//保存分值128所对应的颜色
		squares.put(128, new Square(128, 255, 220, 102));
		//保存分值256所对应的颜色
		squares.put(256, new Square(256, 255, 215, 80));
		//保存分值512所对应的颜色
		squares.put(512, new Square(512, 255, 210, 60));
		//保存分值1024所对应的颜色
		squares.put(1024, new Square(1024, 255, 211, 1));
		//保存分值2048所对应的颜色
		squares.put(2048, new Square(2048, 255, 195, 0));
	}
}
