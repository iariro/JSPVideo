package kumagai.av;

import java.util.*;

/**
 * タイトルコレクションとグループ名。
 */
public class Title3CollectionAndGroup
	extends ArrayList<Title3>
{
	public final String group;

	/**
	 * @param titleCollection タイトルコレクション
	 * @param group グループ名
	 */
	public Title3CollectionAndGroup
		(ArrayList<Title3> titleCollection, String group)
	{
		super(titleCollection);

		this.group = group;
	}

	/**
	 * 要素数を返却。
	 * @return 要素数
	 */
	public int getSize()
	{
		return size();
	}
}
