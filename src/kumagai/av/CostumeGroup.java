package kumagai.av;

import java.util.*;

/**
 * コスチュームごとのコスチューム情報。
 */
public class CostumeGroup
	extends ArrayList<Costume>
{
	public final String costume;
	public final int score;

	/**
	 * 指定の値をフィールドに割り当て。
	 * @param costume コスチューム
	 * @param score 点数
	 */
	public CostumeGroup(String costume, int score)
	{
		this.costume = costume;
		this.score = score;
	}
}
