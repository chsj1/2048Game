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
 * ��������
 * 
 * @author Administrator
 * 
 */
public class Cell extends Actor {
	// ����������ӵķ���
	private int value;
	//����Ļ�еĺ�����
	private int screenX;
	//����Ļ�е�������
	private int screenY;
	//��board�еĺ�����
	private int boardX;
	//��board�е�������
	private int boardY;

	
	/**
	 * �����ӱ�
	 */
	public void doubleValue() {
		//����ֵ�ӱ�
		value *= 2;
		//�������������һ������
		addAction(Actions.sequence(Actions.scaleTo(1.12f, 1.12f, 0.035f), Actions.scaleTo(1, 1, 0.035f)));
	}
	
	
	/**
	 *  ��durationʱ�����ƶ���(boardX,boardY)���λ����
	 * @param boardX
	 * @param boardY
	 * @param duration
	 */
	public void moveTo(int boardX, int boardY, int duration) {
		// ����board�е�λ��ת������Ļ�е�λ��
		convertBoardToScreen(boardX, boardY);
		// ��Ԫ�����Action
		this.addAction(Actions.moveTo(screenX, screenY, 0.1f / duration,
				Interpolation.linear));
	}

	/**
	 * ����board�е�λ��ת������Ļ�е�λ��
	 * 
	 * @param boardX
	 * @param boardY
	 */
	private void convertBoardToScreen(int boardX, int boardY) {
		//����Ա����boardX��ֵ
		this.boardX = boardX;
		this.boardY = boardY;
		/**
		 * ��Ļ�е�λ��=�������+ÿ���������ӵĿ��*���� + ��������֮��ľ���*����
		 */
		screenX = Constants.BOARD_POS_BOT + Constants.BOARD_GRID_WIDTH
				* (this.boardY + 1) + this.boardY * Constants.CELL_WIDTH;
		screenY = (int) (Constants.BOARD_POS_BOT + Constants.BOARD_GRID_WIDTH
				* Constants.STAGE_STRETCH * (Constants.BOARD_COL - this.boardX) + (Constants.BOARD_COL - 1 - this.boardX)
				* Constants.CELL_WIDTH * Constants.STAGE_STRETCH);
		// �����������Ļ�,���ܻ���һ��ƫ��
		screenY += 7;
	}

	/**
	 * ���캯��: ���ݷ������ӵķ���ֵ����board�е�����������һ����������
	 * 
	 * @param value
	 *            ����ֵ
	 * @param boardX
	 *            ��board�е�����
	 * @param boardY
	 */
	public Cell(int value, int boardX, int boardY) {
		//���ø���Ĺ��췽��
		super();
		//����Ա����value��ֵ
		this.value = value;
		// ����board�е�����ת��������Ļ�е�����
		convertBoardToScreen(boardX, boardY);
		// ���÷������ӵĴ�С
		setPosition(screenX, screenY);
		// ���÷������ӵĴ�С
		setSize(Constants.CELL_WIDTH, Constants.CELL_WIDTH);
		// ���÷������ӵ����ű���
		setScale(0.8f);
		// ����������������һ������
		addAction(Actions.scaleTo(1, 1, 0.035f));
	}

	/**
	 * ��ȡ�������ӵķ�ֵ
	 * @return
	 */
	public int getValue() {
		return value;
	}
	/**
	 * ���÷������ӵķ�ֵ
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * ��ȡ������������Ļ�еĺ�����
	 * @return
	 */
	public int getScreenX() {
		return screenX;
	}
	/**
	 * ���÷�����������Ļ�еĺ�����
	 * @param screenX
	 */
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}
	/**
	 * ��ȡ������������Ļ�е�������
	 * @return
	 */
	public int getScreenY() {
		return screenY;
	}
	/**
	 * ���÷�����������Ļ�е�������
	 * @param screenY
	 */
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}
    /**
     * ��ȡ����������board�еĺ�����
     * @return
     */
	public int getBoardX() {
		return boardX;
	}
	/**
	 * ���÷���������board�еĺ�����
	 * @param boardX
	 */
	public void setBoardX(int boardX) {
		this.boardX = boardX;
	}
	/**
	 * ��ȡ����������board�е�������
	 * @return
	 */
	public int getBoardY() {
		return boardY;
	}
	/**
	 * ���÷���������board�е�������
	 * @param boardY
	 */
	public void setBoardY(int boardY) {
		this.boardY = boardY;
	}

	/**
	 * ��дActor�Ļ��ơ�
	 * ʵ�ַ������ӵĻ���
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//���ø����draw(...)����
		super.draw(batch, parentAlpha);
		// �ѱ������ƻ���
		drawBg(batch);
		// �ѷ������Ƴ���
		drawFenshu(batch);
	}
	/**
	 * ���Ʒ������ӵı���
	 * 
	 * @param batch
	 */
	public void drawBg(SpriteBatch batch) {
		//ʹ��SpriteBatch���������ӻ��Ƴ���
		batch.draw(Assets.squares.get(value).getTexture(), getX(), getY(),
				Constants.CELL_WIDTH / 2, Constants.CELL_WIDTH / 2,
				Constants.CELL_WIDTH, Constants.CELL_WIDTH
						* Constants.STAGE_STRETCH, getScaleX(), getScaleX(), 0);
	}
	/**
	 * ���Ʒ���
	 * @param batch
	 */
	public void drawFenshu(SpriteBatch batch) {
		/**
		 * ��������Oλ��
		 */
		float x = getX() + Constants.CELL_WIDTH / 2
				- Assets.font.getBounds(Integer.toString(value)).width / 2;
		float y = getY() + Constants.CELL_WIDTH * Constants.STAGE_STRETCH / 2
				+ Assets.font.getBounds("9985").height
				+ Constants.BITMAPFONT_LINEHEIGHT / 2
				* Assets.squares.get(value).getScale() * getScaleX();
		/**
		 * ���ݷ������ӵķ�ֵ����һ���������ɫ
		 */
		// ��ֵ>4
		if (value > 4) {
			// ���������ɫ���óɰ�ɫ
			Assets.font.setColor(Color.WHITE);
		} else {// �����ֵ<=4
			// ���������ɫ���Ƴɻ�ɫ
			Assets.font.setColor(Color.GRAY);
		}
		// ���Ʒ�ֵ
		Assets.font.draw(batch, Integer.toString(value), x, y);
	}
}
