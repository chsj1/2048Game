package com.example.hellolibgdx;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.example.yuansu.Cell;
import com.example.yuansu.Score;

public class Copy_2_of_GameScreen implements Screen {
	//����һ��MyGame�����
	MyGame game;
	//����һ����̨�����
	Stage stage;
	//���屳������
	Image bgImage;
	Image newImage;
	Image exitImage;
	// ���ڼ�¼�ո��ӵ�����
	private int emptyCellCount;
	// board�е�16����������
	private Cell[][] board;
	// ���ڷ�ֵ��ͬʱ�ĺϲ�
	// ��Ҫ�Ƴ���Ԫ��
	private Array<Cell> shouldRemoveSquareActors;
	// ��Ҫ˫����Ԫ��
	//����ʵ����Ϸ�еķ���
	private Array<Cell> shouldDoubleSquareActors;
	LabelStyle fenshuLabelStyle;
	//������ʾ��ǰ����
	Label fenshuLabel;
	//������ʾ��߷���
	Label maxFenshuLabel;
	public Copy_2_of_GameScreen(MyGame game) {
		super();
		this.game = game;
		//stage����ĳ�ʼ��
		stage = new Stage(480, 800, false);
		bgImage = new Image(Assets.bgRegion);
		bgImage.setSize(480, 800);
		//��ʼ��newImage
		newImage = new Image(Assets.newRegion);
		//����newImage��λ��
		newImage.setPosition(30, 585);
		//��ʼ��exitImage
		exitImage = new Image(Assets.exitRegion);
		//����exitImage��λ��
		exitImage.setPosition(110, 585);
		//����exitImage�Ĵ�С
		exitImage.setSize(Assets.newRegion.getRegionWidth(),
				Assets.newRegion.getRegionHeight());
		//��bgImage��ӵ���̨��
		stage.addActor(bgImage);
		stage.addActor(exitImage);
		stage.addActor(newImage);
		// ��ʼ��board
		initBoard();
		//���ڷ�ֵ��ͬʱ�ĺϲ�
		shouldRemoveSquareActors = new Array<Cell>();
		shouldDoubleSquareActors = new Array<Cell>();
		// ��new��ť���ʱ���������¿�ʼ��Ϸ
		addListenerOnNewBtn();
		// ��exit��ť��Ӽ�����..
		addListenerOnExitBtn();
		//��stage��Ӽ�����,���ڴ�����Ϸ�����߼�
		addListenerOnStage();
		//---------���ڴ�������߼�
		initFenshuLabel();
		//������Label��ӵ���̨
		addFenshuLabelToStage();
	}
	
	/**
	 * �ѷ�����ص�Label��ӵ���̨��
	 */
	public void addFenshuLabelToStage(){
		stage.addActor(fenshuLabel);
		stage.addActor(maxFenshuLabel);
	}
	
	/**
	 * ��ʼ��������ص�Label
	 */
	public void initFenshuLabel(){
		//��ʼ��LabelStyle����
		fenshuLabelStyle = new LabelStyle(Assets.font, Color.WHITE);
		//����LabelStyle����ʼ��Label
		fenshuLabel = new Label("0", fenshuLabelStyle);
		//����fenshuLabel,�������д���
		fenshuLabel.setPosition(291 - fenshuLabel.getTextBounds().width/2, 692);
		maxFenshuLabel = new Label("0", fenshuLabelStyle);
		maxFenshuLabel.setPosition(403 - maxFenshuLabel.getTextBounds().width/2, 692);
	}

	/**
	 * ���ںϲ�����
	 */
	public void unionFenshu(){
		{
			// ����Ҫɾ���ķ�������ɾ����
			Iterator<Cell> iters = shouldRemoveSquareActors
					.iterator();
			//�����������ݽṹ
			while (iters.hasNext()) {
				//ȡ����ǰ�������ķ�������
				Cell actor = iters.next();
				//�������Ԫ�ش�wutaistage���Ƴ�
				stage.getRoot().removeActor(actor);
				//���Ҵ����ݽṹ���Ƴ�
				iters.remove();
			}
		}
		{
			//���ڰѷ������ӵķ����ӱ�
			Iterator<Cell> iters = shouldDoubleSquareActors
					.iterator();
			//�������ݽṹ�е�����Ԫ��
			while (iters.hasNext()) {
				//ȥ����ǰ�������ķ�������
				Cell actor = iters.next();
				//�����ӱ�
				actor.doubleValue();
				//���Ҵ����ݽṹ���Ƴ�
				iters.remove();
			}
		}
	}
	
	/**
	 * �����ƶ�
	 * ��Ҫ��סboard�е������������ӵ�. boardX,boardY (0,0),(0,1) (1,0),(1,1) (2,0),(2,1)
	 * (3,0),(3,1)
	 */
	private void touchDragToUp() {
		// ����ÿһ��
		for (int j = 0; j < 4; j++) {
			// �������õķ�������(�����õĵ���һ��Ԫ��)
			Cell lastActor = null;
			// ��ǰ���ڵ�����λ��,Ĭ�ϴ�0��ʼ
			int adjustPosition = 0;
			// ����ÿһ��...(��Ҫע�����ʱ���һЩ˳��.�����j.�����i)
			for (int i = 0; i < 4; i++) {
				// �����ǰ�ĸ���Ϊnull
				if (board[i][j] == null) {
					// ������һ��ѭ��
					continue;
				} else {// �����ǰ���Ӳ�Ϊ��
					// ���֮ǰ��û�е�������������,����ǵ�һ�������ķ�������
					if (lastActor == null) {
						// �����ǰ�����ķ������Ӳ������ڵ�����λ��
						if (adjustPosition != i) {
							// ��ô�������ú�ķ�������ָ��ǰ��������
							board[adjustPosition][j] = board[i][j];
							// ����ǰ����������Ϊ��
							board[i][j] = null;
							// �����������ƶ���ָ��λ��
							board[adjustPosition][j].moveTo(adjustPosition, j,
									i - adjustPosition);
						}
						// �����������õķ�������lastActor
						lastActor = board[adjustPosition][j];
						// ���ڵ�����λ�õ�����+1
						adjustPosition++;
					} else {// ���֮ǰ�Ѿ���������������
						// ����������ķ������ӵķ���ֵ != ��ǰ�����ķ������ӵķ���ֵ.���ǲ���Ҫ�ϲ�
						if (lastActor.getValue() != board[i][j].getValue()) {
							// ��ǰ�����ķ������Ӳ������ڵ�����λ����
							if (adjustPosition != i) {
								// �ѵ�ǰ�����ķ��������ѵ����ڵ�����λ����
								board[adjustPosition][j] = board[i][j];
								board[i][j] = null;
								board[adjustPosition][j].moveTo(adjustPosition,
										j, i - adjustPosition);
							}
							// �����������õķ�������
							lastActor = board[adjustPosition][j];
							// �������ڵ�����λ��
							adjustPosition++;
						} else {// �����ǰԪ�غ���һ��Ԫ�صķ�ֵһ��
							// ����ǰԪ���ƶ�����һ��Ԫ������
							board[i][j].moveTo(lastActor.getBoardX(),
									lastActor.getBoardY(),
									i - lastActor.getBoardX());
							// ���ϵ�ǰԪ�صķ���
							 Score.instance.addScore(board[i][j].getValue());
							//��board[i][j]��ӵ���Ҫ�Ƴ���������
							 shouldRemoveSquareActors.add(board[i][j]);
							//��lastActor��ӵ�������˫������������
							 shouldDoubleSquareActors.add(lastActor);
							// ��Ϊ�ϲ���,���Կո�����+1
							emptyCellCount++;
							// lastActor��Ϊ��
							lastActor = null;
							// board[i][j]��Ϊ��
							board[i][j] = null;
						}
					}

				}
			}
		}
	}

	/**
	 * �����ƶ�
	 */
	private void touchDragToDown() {
		// ����ÿһ��
		for (int j = 0; j < 4; j++) {
			Cell lastActor = null;
			int adjustPosition = 3;
			for (int i = 3; i >= 0; i--) {
				if (board[i][j] == null)
					continue;
				else {
					if (lastActor == null) {
						if (adjustPosition != i) {
							board[adjustPosition][j] = board[i][j];
							board[i][j] = null;
							board[adjustPosition][j].moveTo(adjustPosition, j,
									adjustPosition - i);
						}
						lastActor = board[adjustPosition][j];
						adjustPosition--;
					} else {
						if (lastActor.getValue() != board[i][j].getValue()) {

							if (adjustPosition != i) {

								board[adjustPosition][j] = board[i][j];
								board[i][j] = null;
								board[adjustPosition][j].moveTo(adjustPosition,
										j, adjustPosition - i);
							}
							lastActor = board[adjustPosition][j];
							adjustPosition--;

						} else {

							board[i][j].moveTo(lastActor.getBoardX(),
									lastActor.getBoardY(),
									lastActor.getBoardX() - i);
							 shouldRemoveSquareActors.add(board[i][j]);
							 Score.instance.addScore(board[i][j].getValue());
							 shouldDoubleSquareActors.add(lastActor);
							emptyCellCount++;
							lastActor = null;
							board[i][j] = null;

						}
					}

				}
			}
		}
	}

	/**
	 * �����ƶ�
	 */
	private void touchDragToLeft() {
		for (int i = 0; i < 4; i++) {
			Cell lastActor = null;
			int adjustPosition = 0;
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == null)
					continue;
				else {
					if (lastActor == null) {

						if (adjustPosition != j) {
							board[i][adjustPosition] = board[i][j];
							board[i][j] = null;
							board[i][adjustPosition].moveTo(i, adjustPosition,
									j - adjustPosition);
						}
						lastActor = board[i][adjustPosition];

						adjustPosition++;
					} else {
						if (lastActor.getValue() != board[i][j].getValue()) {
							if (adjustPosition != j) {
								board[i][adjustPosition] = board[i][j];
								board[i][j] = null;
								board[i][adjustPosition].moveTo(i,
										adjustPosition, j - adjustPosition);
							}
							lastActor = board[i][adjustPosition];

							adjustPosition++;

						} else {
							board[i][j].moveTo(lastActor.getBoardX(),
									lastActor.getBoardY(),
									j - lastActor.getBoardY());
							 shouldDoubleSquareActors.add(lastActor);
							 Score.instance.addScore(board[i][j].getValue());
							 shouldRemoveSquareActors.add(board[i][j]);
							emptyCellCount++;

							lastActor = null;
							board[i][j] = null;

						}
					}

				}
			}
		}

	}

	/**
	 * �����ƶ�
	 */
	private void touchDragToRight() {
		for (int i = 0; i < 4; i++) {
			Cell lastActor = null;
			int adjustPosition = 3;
			for (int j = 3; j >= 0; j--) {
				if (board[i][j] == null)
					continue;
				else {
					if (lastActor == null) {

						if (adjustPosition != j) {
							board[i][adjustPosition] = board[i][j];
							board[i][j] = null;
							board[i][adjustPosition].moveTo(i, adjustPosition,
									adjustPosition - j);
						}
						lastActor = board[i][adjustPosition];

						adjustPosition--;
					} else {
						if (lastActor.getValue() != board[i][j].getValue()) {
							if (adjustPosition != j) {
								board[i][adjustPosition] = board[i][j];
								board[i][j] = null;
								board[i][adjustPosition].moveTo(i,
										adjustPosition, adjustPosition - j);
							}
							lastActor = board[i][adjustPosition];
							adjustPosition--;

						} else {
							board[i][j].moveTo(lastActor.getBoardX(),
									lastActor.getBoardY(),
									lastActor.getBoardY() - j);

							 shouldRemoveSquareActors.add(board[i][j]);
							 Score.instance.addScore(board[i][j].getValue());
							 shouldDoubleSquareActors.add(lastActor);
							emptyCellCount++;
							lastActor = null;
							board[i][j] = null;
						}
					}

				}
			}
		}
	}

	public void addListenerOnStage() {
		stage.addListener(new InputListener() {//��stage��Ӽ�����
			/**
			 * keycode���ص����㰴�µļ����еİ�����Ψһ��ʶ
			 */
			@Override
			public boolean keyDown(InputEvent event, int keycode) {

				if (keycode == Keys.UP) {//����㰴�µ����ϵİ�ť
					touchDragToUp();//��ô���еķ������������ƶ�
				} else if (keycode == Keys.DOWN) {
					touchDragToDown();
				} else if (keycode == Keys.LEFT) {
					touchDragToLeft();
				} else if (keycode == Keys.RIGHT) {
					touchDragToRight();
				}

				return true;
			}
			
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				unionFenshu();//�����ƶ���ϲ���������
				addCell();//ÿ�β���������µķ�������
				
				updateFenshu();//���µ�ǰ����..
				
				return true;
			}
		});
	}

	/**
	 * ���·���
	 */
	public void updateFenshu(){
		//���µ�ǰ����
		fenshuLabel.setText(Score.instance.getScore() + "");
		fenshuLabel.setPosition(291 - fenshuLabel.getTextBounds().width/2, 692);
		
		//������߷���
		maxFenshuLabel.setText(Score.instance.getBest() + "");
		maxFenshuLabel.setPosition(403 - maxFenshuLabel.getTextBounds().width/2, 692);
	}
	
	public void addListenerOnExitBtn() {
		exitImage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();// libGDX�ṩ���˳���Ϸ�ķ���

				return true;
			}
		});
	}

	/**
	 * ��new��ť���ʱ���������¿�ʼ��Ϸ
	 */
	public void addListenerOnNewBtn() {
		newImage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				System.out.println("--------->click new");
				restartGame();// ���¿�ʼ��Ϸ

				return true;
			}
		});
	}

	/**
	 * ��ʼ��board
	 */
	private void initDataForBoard() {

		// ���������������
		for (int i = 0; i < 2; i++) {
			addCell();
		}
		// addCell(2, 3, 1);
		// addCell(4, 3, 0);
		// addCell(2, 1, 1);
		// addCell(4, 1, 0);

		// isMove = false;
		// state = GameState.PLAYING;// ����Ϸ��״̬���ó�PLAYING״̬
		// Score.instance.setScore(0);// ����Ϸ�������ó�0
		// maxValue = 0;// ����Ϸ����߷������ó�0
	}

	/**
	 * ��ʼ��board���ڵ�һ�ν�����Ϸ || �˳���Ϸ���ٽ�����Ϸ
	 */
	private void initBoard() {
		emptyCellCount = 16;
		board = new Cell[4][4];
		initDataForBoard();// ��ʼ��board
	}

	/**
	 * ���board�е����з�������
	 */
	public void removeCells() {
		int i;
		int j;
		for (i = 0; i < 4; ++i) {
			for (j = 0; j < 4; ++j) {
				if (board[i][j] != null) {
					board[i][j].remove();
				}
			}
		}
	}

	/**
	 * ���¿�ʼ��Ϸ
	 */
	public void restartGame() {
		removeCells();// ���board�е����з�������
		initBoard();// ���³�ʼ��board
	}

	/**
	 * �ҵ����ʵ�λ��,��ӷ�������
	 */
	private void addCell() {
		if (emptyCellCount > 0) {// �����ǰ�Ŀո��ӵ�����>0

			/*
			 * choose random pos at null cell of board, after,we find pos in
			 * board, and random value for it call addCell(value, posX, posY) to
			 * add to board.
			 */
			int pos = MathUtils.random(emptyCellCount - 1);// ���������ӷ����ĸ��ӵ�����
			boolean findEmpty = false;// Ĭ�Ͻ� �Ƿ��ҵ����� ���Ϊfalse
			int i = 0;// ��ӷ����ĸ��ӵ�����
			int j = 0;
			int d = 0;// Ŀǰ�ո��ӵĸ���
			for (i = 0; i < 4 && !findEmpty; i++) {
				for (j = 0; j < 4 && !findEmpty; j++) {
					if (board[i][j] == null) {// �����ǰ����Ϊnull
						if (d == pos) {// �����ǰ�Ŀո��ӵĸ��� == ��ӷ����ĸ��ӵ�����
							findEmpty = true;// ���Ƿ��ҵ�Ŀ����ӱ��Ϊ true
						}
						d++;// Ŀǰ�ո��ӵĸ����� 1
					}
				}
			}
			i--;
			j--;

			int value;// ����ӵĸ��ӵķ�ֵ
			// if (MathUtils.randomBoolean(0.9f)) {
			if (MathUtils.random() < 0.9f) {// �������������� < 0.9
				value = 2;// ����ӵĸ��ӵķ�ֵΪ 2
			} else {// �������������� >= 0.9
				value = 4;// ����ӵĸ��ӵķ�ֵΪ2
			}

			addCell(value, i, j);// ����ֵΪvalue�ĸ�����ӵ�(i,j)��
			// addCell(value, 0, 0);// ����ֵΪvalue�ĸ�����ӵ�(i,j)��
			emptyCellCount--;// �ո�������1
		}
	}

	/**
	 * ����ֵΪvalue�ĸ�����ӵ�(boardX,boardY)��
	 * 
	 * @param value
	 *            ��ֵ
	 * @param boardX
	 *            ������
	 * @param boardY
	 *            ������
	 */
	private void addCell(int value, int boardX, int boardY) {
		Cell actor = new Cell(value, boardX, boardY);
		board[boardX][boardY] = actor;
		stage.addActor(actor);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
