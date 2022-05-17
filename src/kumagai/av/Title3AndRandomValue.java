package kumagai.av;

import java.util.Random;

/**
 * タイトル情報。ソート用。
 * @author kumagai
 */
public class Title3AndRandomValue
{
	public final Title3 title;
	public final int randomValue;

	/**
	 * 指定のタイトル情報とともにランダム値をメンバーに割り当て。
	 * @param title タイトル情報
	 * @param random ランダムオブジェクト
	 */
	public Title3AndRandomValue(Title3 title, Random random)
	{
		this.title = title;
		this.randomValue = random.nextInt();
	}
}
