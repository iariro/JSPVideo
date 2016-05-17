package kumagai.av;

/**
 * コスチュームの特徴と評価情報。
 */
public class FeatureAndScore
{
	public final String feature;
	public final int [] score;

	/**
	 * 指定の値をメンバーに割り当て。
	 * @param feature 特徴
	 * @param score 評価値
	 */
	public FeatureAndScore(String feature, int [] score)
	{
		this.feature = feature;
		this.score = score;
	}

	public int getSum()
	{
		int sum = 0;

		for (int i=0 ; i<score.length ; i++)
		{
			sum += score[i];
		}

		return sum;
	}
}
