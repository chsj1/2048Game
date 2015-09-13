package com.example.yuansu;

/**
 * 分数逻辑体.
 * 用于处理与分数相关的逻辑
 * @author Administrator
 *
 */
public class Score {
	//在Score类内本身维持一个引用
	public static Score instance = new Score();
	//当前分数
	private int score;
	//最高分数
	private int best;
	/**
	 * 无参构造函数
	 */
	public Score() {
		//默认情况下当前分数为0
		score = 0;
		//默认情况下最高分数为0
		best = 0;
	}
	/**
	 * 成员变量score的getXXX()方法
	 * @return
	 */
	public int getScore() {
		return score;
	}
	/**
	 * 成员变量score的setXXX()方法
	 * @param score
	 */
	public void setScore(int score) {
		//将传入的score的值传递给成员变量score
		this.score = score;
		//如果当前分数>最高分数
		if (score>best)
		{
			//更新最高分数的值
			best = score;
		}
	}
	/**
	 * 加分并且更新游戏的分值
	 * @param score
	 */
	public void addScore(int score) {
		//将当前分数的值加上score
		this.score += score;
		//如果当前分数 > 最高分数.更新最高分数
		this.best = (this.score > best) ? this.score : best;
	}
	/**
	 * 最高分数的getXXX()方法
	 * @return
	 */
	public int getBest() {
		return best;
	}
}
