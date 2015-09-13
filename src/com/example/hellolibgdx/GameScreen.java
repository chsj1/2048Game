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

public class GameScreen implements Screen {
	// ����һ��MyGame�����
	MyGame game;
	// ����һ����̨�����
	Stage stage;
	// ���屳������
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
	// ����ʵ����Ϸ�еķ���
	private Array<Cell> shouldDoubleSquareActors;
	LabelStyle fenshuLabelStyle;
	// ������ʾ��ǰ����
	Label fenshuLabel;
	// ������ʾ��߷���
	Label maxFenshuLabel;

	public GameScreen(MyGame game) {
		super();
		this.game = game;
		// stage����ĳ�ʼ��
		stage = new Stage(480, 800, false);
		bgImage = new Image(Assets.bgRegion);
		bgImage.setSize(480, 800);
		// ��ʼ��newImage
		newImage = new Image(Assets.newRegion);
		// ����newImage��λ��
		newImage.setPosition(30, 585);
		// ��ʼ��exitImage
		exitImage = new Image(Assets.exitRegion);
		// ����exitImage��λ��
		exitImage.setPosition(110, 585);
		// ����exitImage�Ĵ�С
		exitImage.setSize(Assets.newRegion.getRegionWidth(),
				Assets.newRegion.getRegionHeight());
		// ��bgImage��ӵ���̨��
		stage.addActor(bgImage);
		stage.addActor(exitImage);
		stage.addActor(newImage);
		// ��ʼ��board
		initBoard();
		// ���ڷ�ֵ��ͬʱ�ĺϲ�
		shouldRemoveSquareActors = new Array<Cell>();
		shouldDoubleSquareActors = new Array<Cell>();
		// ��new��ť���ʱ���������¿�ʼ��Ϸ
		addListenerOnNewBtn();
		// ��exit��ť��Ӽ�����..
		addListenerOnExitBtn();
		// ��stage��Ӽ�����,���ڴ�����Ϸ�����߼�
		addListenerOnStage();
		// ---------���ڴ�������߼�
		initFenshuLabel();
		// ������Label��ӵ���̨
		addFenshuLabelToStage();
	}

	/**
	 * �ѷ�����ص�Label��ӵ���̨��
	 */
	public void addFenshuLabelToStage() {
		stage.addActor(fenshuLabel);
		stage.addActor(maxFenshuLabel);
	}

	/**
	 * ��ʼ��������ص�Label
	 */
	public void initFenshuLabel() {
		// ��ʼ��LabelStyle����
		fenshuLabelStyle = new LabelStyle(Assets.font, Color.WHITE);
		// ����LabelStyle����ʼ��Label
		fenshuLabel = new Label("0", fenshuLabelStyle);
		// ����fenshuLabel,�������д���
		fenshuLabel.setPosition(291 - fenshuLabel.getTextBounds().width / 2,
				692);
		maxFenshuLabel = new Label("0", fenshuLabelStyle);
		maxFenshuLabel.setPosition(
				403 - maxFenshuLabel.getTextBounds().width / 2, 692);
	}

	/**
	 * ���ںϲ�����
	 */
	public void unionFenshu() {
		{
			// ����Ҫɾ���ķ�������ɾ����
			Iterator<Cell> iters = shouldRemoveSquareActors.iterator();
			// �����������ݽṹ
			while (iters.hasNext()) {
				// ȡ����ǰ�������ķ�������
				Cell actor = iters.next();
				// �������Ԫ�ش�wutaistage���Ƴ�
				stage.getRoot().removeActor(actor);
				// ���Ҵ����ݽṹ���Ƴ�
				iters.remove();
			}
		}
		{
			// ���ڰѷ������ӵķ����ӱ�
			Iterator<Cell> iters = shouldDoubleSquareActors.iterator();
			// �������ݽṹ�е�����Ԫ��
			while (iters.hasNext()) {
				// ȥ����ǰ�������ķ�������
				Cell actor = iters.next();
				// �����ӱ�
				actor.doubleValue();
				// ���Ҵ����ݽṹ���Ƴ�
				iters.remove();
			}
		}
	}

	/**
	 * �����ƶ� ��Ҫ��סboard�е������������ӵ�. boardX,boardY (0,0),(0,1) (1,0),(1,1)
	 * (2,0),(2,1) (3,0),(3,1)
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
							// ��board[i][j]��ӵ���Ҫ�Ƴ���������
							shouldRemoveSquareActors.add(board[i][j]);
							// ��lastActor��ӵ�������˫������������
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
			// �������õķ�������(�����õĵ���һ��Ԫ��)
			Cell lastActor = null;
			// ��ǰ���ڵ�����λ��,Ĭ�ϴ�0��ʼ
			int adjustPosition = 3;
			// ����ÿһ��...(i�ǴӴ�С��ʼ����)
			for (int i = 3; i >= 0; i--) {
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
									adjustPosition - i);
						}
						// �����������õķ�������lastActor
						lastActor = board[adjustPosition][j];
						// ���ڵ�����λ�õ�����+1
						adjustPosition--;
					} else {// ���֮ǰ�Ѿ���������������
						// ����������ķ������ӵķ���ֵ != ��ǰ�����ķ������ӵķ���ֵ.���ǲ���Ҫ�ϲ�
						if (lastActor.getValue() != board[i][j].getValue()) {
							// ��ǰ�����ķ������Ӳ������ڵ�����λ����
							if (adjustPosition != i) {
								// �ѵ�ǰ�����ķ��������ѵ����ڵ�����λ����
								board[adjustPosition][j] = board[i][j];
								board[i][j] = null;
								board[adjustPosition][j].moveTo(adjustPosition,
										j, adjustPosition - i);
							}
							// �����������õķ�������
							lastActor = board[adjustPosition][j];
							// �������ڵ�����λ��
							adjustPosition--;
						} else {// �����ǰԪ�غ���һ��Ԫ�صķ�ֵһ��
							// ����ǰԪ���ƶ�����һ��Ԫ������
							board[i][j].moveTo(lastActor.getBoardX(),
									lastActor.getBoardY(),
									lastActor.getBoardX() - i);
							// ��board[i][j]��ӵ���Ҫ�Ƴ���������
							shouldRemoveSquareActors.add(board[i][j]);
							Score.instance.addScore(board[i][j].getValue());
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

	/**
	 * ��������̨��Ӽ�����
	 */
	public void addListenerOnStage() {
		// ��stage��Ӽ�����
		stage.addListener(new InputListener() {
			/**
			 * keycode���ص����㰴�µļ����еİ�����Ψһ��ʶ
			 */
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// ����㰴�µ����ϵİ�ť
				if (keycode == Keys.UP) {
					// ��ô���еķ������������ƶ�
					touchDragToUp();
				} else if (keycode == Keys.DOWN) {
					// ��ô���еķ������������ƶ�
					touchDragToDown();
				} else if (keycode == Keys.LEFT) {
					// ��ô���еķ������������ƶ�
					touchDragToLeft();
				} else if (keycode == Keys.RIGHT) {
					// ��ô���еķ������������ƶ�
					touchDragToRight();
				}
				return true;
			}
			/**
			 * �����̵�������ִ�еĲ���
			 */
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				// �����ƶ���ϲ���������
				unionFenshu();
				// ÿ�β���������µķ�������
				addCell();
				// ���µ�ǰ����..
				updateFenshu();
				return true;
			}
		});
	}

	/**
	 * ���·���
	 */
	public void updateFenshu() {
		// ���µ�ǰ����
		fenshuLabel.setText(Score.instance.getScore() + "");
		//����һ�µ�ǰ������λ��
		fenshuLabel.setPosition(291 - fenshuLabel.getTextBounds().width / 2,
				692);
		// ������߷���
		maxFenshuLabel.setText(Score.instance.getBest() + "");
		//������߷�����ǩ��λ��
		maxFenshuLabel.setPosition(
				403 - maxFenshuLabel.getTextBounds().width / 2, 692);
	}

	/**
	 * ��Exit��ť��Ӽ����¼����ڴ����˳��¼�
	 */
	public void addListenerOnExitBtn() {
		//��exitImageע�������
		exitImage.addListener(new InputListener() {
			/**
			 * ����ָ����ʱִ��
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// libGDX�ṩ���˳���Ϸ�ķ���
				Gdx.app.exit();
				return true;
			}
		});
	}

	/**
	 * ��new��ť���ʱ���������¿�ʼ��Ϸ
	 */
	public void addListenerOnNewBtn() {
		//��newImage��ӵ���¼�
		newImage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//�����ʾ"--------->click new",�������
				System.out.println("--------->click new");
				// ���¿�ʼ��Ϸ
				restartGame();
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
			//��ӷ�������
			addCell();
		}
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
		//������
		int i;
		//������
		int j;
		//����ÿһ��
		for (i = 0; i < 4; ++i) {
			//����ÿһ��
			for (j = 0; j < 4; ++j) {
				//�����ǰ��board�ĸ÷������Ӳ�Ϊnull
				if (board[i][j] != null) {
					//��ô����board�ķ��������Ƴ�
					board[i][j].remove();
				}
			}
		}
	}

	/**
	 * ���¿�ʼ��Ϸ
	 */
	public void restartGame() {
		// ���board�е����з�������
		removeCells();
		// ���³�ʼ��board
		initBoard();
	}
	
	/**
	 * �ҵ����ʵ�λ��,��ӷ�������
	 */
	private void addCell() {
		// �����ǰ�Ŀո��ӵ�����>0
		if (emptyCellCount > 0) {
			/**
			 * �������ڿյķ���������Ŀ����һ�������
			 * Ȼ�󽫷���������ӵ���λ����
			 */
			int pos = MathUtils.random(emptyCellCount - 1);
			// Ĭ�Ͻ� �Ƿ��ҵ����� ���Ϊfalse
			boolean findEmpty = false;
			// ��ӷ����ĸ��ӵ�������
			int i = 0;
			//�������ӵ�������
			int j = 0;
			// Ŀǰ�ո��ӵĸ���
			int d = 0;
			//�������е���
			for (i = 0; i < 4 && !findEmpty; i++) {
				//�������е���
				for (j = 0; j < 4 && !findEmpty; j++) {
					// �����ǰ����Ϊnull
					if (board[i][j] == null) {
						// �����ǰ�Ŀո��ӵĸ��� == ��ӷ����ĸ��ӵ�����
						if (d == pos) {
							// ���Ƿ��ҵ�Ŀ����ӱ��Ϊ true
							findEmpty = true;
						}
						// Ŀǰ�ո��ӵĸ����� 1
						d++;
					}
				}
			}
			i--;
			j--;
			// ����ӵĸ��ӵķ�ֵ
			int value;
			// �������������� < 0.9
			if (MathUtils.random() < 0.9f) {
				// ����ӵĸ��ӵķ�ֵΪ 2
				value = 2;
			} else {// �������������� >= 0.9
				// ����ӵĸ��ӵķ�ֵΪ2
				value = 4;
			}
			// ����ֵΪvalue�ĸ�����ӵ�(i,j)��
			addCell(value, i, j);
			// �ո�������1
			emptyCellCount--;
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
		//��ʼ��һ���µķ�������
		Cell actor = new Cell(value, boardX, boardY);
		//����ǰ����ָ��÷�������
		board[boardX][boardY] = actor;
		//���÷���������ӵ���̨
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
