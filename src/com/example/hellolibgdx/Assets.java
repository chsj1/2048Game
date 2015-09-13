package com.example.hellolibgdx;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.example.yuansu.Square;

/**
 * ��Ϸ�е���Դ������
 * @author Administrator
 *
 */
public class Assets {
	//���ڹ��������Դ
	static TextureAtlas atlas;
	//���ڱ��汳��ͼ��TextureRegion����
	static TextureRegion bgRegion;
	//���ڱ���exit��ť��TextureRegion����
	static TextureRegion exitRegion;
	//���ڱ���new��ť��TextureRegion����
	static TextureRegion newRegion;
	//���ڱ���gameover��TextureRegion����
	static TextureRegion gameoverRegion;
	//���ڱ���ʤ�������ʧ�ܽ���İ�ɫ������TextureRegion����
	static TextureRegion opacityRegion;
	//���ڱ������¿�ʼ��ť��TextureRegion����
	static TextureRegion restartRegion;
	//���ڱ����ٴγ��԰�ť��TextureRegion����
	static TextureRegion tryagainRegion;
	//��Ϸ���õ�������
	public static BitmapFont font;
	//���ڴ洢���ַ�ֵ��
	public static Map<Integer,Square> squares;
	
	/**
	 * ��Դ�ļ���
	 */
	public static void load(){
		//�Ѻ�ͼ�Ժ��ͼƬ���ؽ���
		atlas = new TextureAtlas(Gdx.files.internal("data/2048.txt"));
		//�ҵ���ͼ�еı���ͼ
		bgRegion = atlas.findRegion("background");
		//�ҵ���ͼ�е��˳���ť
		exitRegion = atlas.findRegion("exit");
		//�ҵ���ͼ�е�gameoverͼƬ
		gameoverRegion = atlas.findRegion("game-over");
		//�ҵ�gameover����İ�ɫ����ͼ
		opacityRegion = atlas.findRegion("opacity");
		//�ҵ����¿�ʼ��ť��Ӧ��ͼƬ
		restartRegion = atlas.findRegion("restart");
		//�ҵ��ٴγ��԰�ť��Ӧ��ͼƬ
		tryagainRegion = atlas.findRegion("try-again");
		//�ҵ�new��ť��Ӧ��ͼƬ
		newRegion = atlas.findRegion("new");
		//��ʼ��BitgmapFont����
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		//��ʼ�����ַ�����Ӧ����ɫ
		initColor();
	}
	
	
	/**
	 * ��ʼ�����ַ�����Ӧ����ɫ
	 */
	public static void initColor() {
		//��ʼ��HashMap����������ַ�ֵ����Ӧ����ɫ
		squares = new HashMap<Integer, Square>();
		//�����ֵ2����Ӧ����ɫ
		squares.put(2, new Square(2, 238, 228, 218));
		//�����ֵ4����Ӧ����ɫ
		squares.put(4, new Square(4, 237, 205, 123));
		//�����ֵ8����Ӧ����ɫ
		squares.put(8, new Square(8, 255, 131, 30));
		//�����ֵ16����Ӧ����ɫ
		squares.put(16, new Square(16, 255, 129, 8));
		//�����ֵ32����Ӧ����ɫ
		squares.put(32, new Square(32, 237, 88, 52));
		//�����ֵ64����Ӧ����ɫ
		squares.put(64, new Square(64, 217, 54, 17));
		//�����ֵ128����Ӧ����ɫ
		squares.put(128, new Square(128, 255, 220, 102));
		//�����ֵ256����Ӧ����ɫ
		squares.put(256, new Square(256, 255, 215, 80));
		//�����ֵ512����Ӧ����ɫ
		squares.put(512, new Square(512, 255, 210, 60));
		//�����ֵ1024����Ӧ����ɫ
		squares.put(1024, new Square(1024, 255, 211, 1));
		//�����ֵ2048����Ӧ����ɫ
		squares.put(2048, new Square(2048, 255, 195, 0));
	}
}
