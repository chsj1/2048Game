package com.example.yuansu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * ���ڴ洢ÿ�ַ�ֵ��Ӧ�ķ������ӵı�������ɫ
 * @author Administrator
 *
 */
public class Square {
	//��ֵ
	private int key;
	//��Ӧ����
	private TextureRegion smallTexture;
	//��������ɫ
	private Color color;
	//����ϵ��
	private float scale;

	/**
	 * ���ݷ������ӵķ�ֵ���Ͷ�Ӧ��RGBֵ������һ��square
	 * @param key �������ӵķ�ֵ
	 * @param r ��ɫ��RGBֵ
	 * @param g
	 * @param b
	 */
	public Square(int key, int r, int g, int b) {
		//����Ա����key��ֵ
		this.key = key;
		//����Ա����color��ֵ
		this.color = new Color((r) / 255f, (g) / 255f, (b) / 255f, 1.0f);
		//�����ǰԪ�طַ�ֵ<=10000
		if (Integer.toString(key).length() <= 4){
			//��ô��ǰ��ֵ����ԭ��
			this.scale = 1;
		}else{//�����ǰ��ֵ>10000
			//��ô�����һ��������
			this.scale = 4f / Integer.toString(key).length();
		}
		//���������Ӵ�������
		this.smallTexture = createBackground(color);
	}
	/**
	 * ����float�͵�(r,g,b)������һ��Square
	 * @param key
	 * @param r
	 * @param g
	 * @param b
	 */
	public Square(int key, float r, float g, float b) {
		//����Ա����key��ֵ
		this.key = key;
		//����Ա����color��ֵ
		this.color = new Color(r, g, b, 1.0f);
		//�����ǰԪ�طַ�ֵ<=10000
		if (Integer.toString(key).length() <= 4){
			//��ô��ǰ��ֵ����ԭ��
			this.scale = 1;
		}else{//�����ǰ��ֵ>10000
			//��ô�����һ��������
			this.scale = 4f / Integer.toString(key).length();
		}
		//���������Ӵ�������
		this.smallTexture = createBackground(color);
	}
	/**
	 * ����Color������keyֵ����Square
	 * @param key
	 * @param color
	 */
	public Square(int key, Color color) {
		//����Ա����key��ֵ
		this.key = key;
		//����Ա����color��ֵ
		this.color = color;
		//�����ǰԪ�طַ�ֵ<=10000
		if (Integer.toString(key).length() <= 4){
			//��ô��ǰ��ֵ����ԭ��
			this.scale = 1;
		}else{//�����ǰ��ֵ>10000
			//��ô�����һ��������
			this.scale = 4f / Integer.toString(key).length();
		}
		//���������Ӵ�������
		this.smallTexture = createBackground(color);
	}

	/**
	 * ������ɫ������Pixmap������һ���������ӵı���
	 * @param color
	 * @return
	 */
	private TextureRegion createBackground(Color color) {
		//����������ӵķ�ֵ>=128
		if (key >= 128) {
			//����Pixmap
			Pixmap pixmap = new Pixmap(MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), Format.RGBA8888);
			//����Pixmap����ɫ
			pixmap.setColor(Color.WHITE);
			//����Pimxap����һ�����ĵľ���
			pixmap.drawRectangle(0, 0,MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH));
			//������ɫ
			pixmap.setColor(color);
			//����Pixmap����һ��ʵ�ĵľ���
			pixmap.fillRectangle(1, 1,MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH) -2,MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH) -2);
			//��ʼ��Texture
			Texture texture = new Texture(pixmap);
			//����һ��Texture�����Filter
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			//��ʼ��TextureRegion
			TextureRegion region = new TextureRegion(texture,90,90);
			//��Pixmap���ٵ�
			pixmap.dispose();
			return region;
		} else {//����������ӵķ�ֵ< 128
			//����Pixmap
			Pixmap pixmap = new Pixmap(MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), Format.RGBA8888);
			//������ɫ
			pixmap.setColor(color);
			//����һ��ʵ�ľ���
			pixmap.fillRectangle(0, 0, MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH), MathUtils.nextPowerOfTwo(Constants.CELL_WIDTH) );
			//��ʼ��һ��Texture
			Texture texture = new Texture(pixmap);
			//��ʼ��һ��TextureRegion
			TextureRegion region = new TextureRegion(texture,90,90);
			//��pixmap���ٵ�
			pixmap.dispose();
			return region;
		}
	}

	/**
	 * ��ȡ��ֵ
	 * @return
	 */
	public int getKey() {
		return key;
	}
	/**
	 * ���÷�ֵ
	 * @param key
	 */
	public void setKey(int key) {
		this.key = key;
	}
	/**
	 * ��ȡ�������ӵı�������
	 * @return
	 */
	public TextureRegion getTexture() {
		return smallTexture;
	}
	/**
	 * ���÷������Ӷ�Ӧ�ı�������
	 * @param texture
	 */
	public void setTexture(TextureRegion texture) {
		this.smallTexture = texture;
	}
	/**
	 * ��ȡĳһ�ַ�ֵ��Ӧ����ɫ
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * ����ĳһ�ַ�ֵ��Ӧ����ɫ
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * ��ȡ���ű���
	 * @return
	 */
	public float getScale() {
		return scale;
	}
	/**
	 * �������ű���
	 * @param scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}
}

