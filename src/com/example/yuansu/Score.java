package com.example.yuansu;

/**
 * �����߼���.
 * ���ڴ����������ص��߼�
 * @author Administrator
 *
 */
public class Score {
	//��Score���ڱ���ά��һ������
	public static Score instance = new Score();
	//��ǰ����
	private int score;
	//��߷���
	private int best;
	/**
	 * �޲ι��캯��
	 */
	public Score() {
		//Ĭ������µ�ǰ����Ϊ0
		score = 0;
		//Ĭ���������߷���Ϊ0
		best = 0;
	}
	/**
	 * ��Ա����score��getXXX()����
	 * @return
	 */
	public int getScore() {
		return score;
	}
	/**
	 * ��Ա����score��setXXX()����
	 * @param score
	 */
	public void setScore(int score) {
		//�������score��ֵ���ݸ���Ա����score
		this.score = score;
		//�����ǰ����>��߷���
		if (score>best)
		{
			//������߷�����ֵ
			best = score;
		}
	}
	/**
	 * �ӷֲ��Ҹ�����Ϸ�ķ�ֵ
	 * @param score
	 */
	public void addScore(int score) {
		//����ǰ������ֵ����score
		this.score += score;
		//�����ǰ���� > ��߷���.������߷���
		this.best = (this.score > best) ? this.score : best;
	}
	/**
	 * ��߷�����getXXX()����
	 * @return
	 */
	public int getBest() {
		return best;
	}
}
