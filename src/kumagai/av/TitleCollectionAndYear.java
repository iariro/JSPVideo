package kumagai.av;

import java.util.*;

/**
 * リリース年ごとのタイトル情報。
 * @author kumagai
 */
public class TitleCollectionAndYear
	extends ArrayList<Title3>
{
	private final int year;

	/**
	 * リリース年を取得。
	 * @return リリース年
	 */
	public String getYear()
	{
		return Integer.toString(year);
	}

	/**
	 * コレクションの数を取得。
	 * @return コレクションの数
	 */
	public int getSize()
	{
		return super.size();
	}

	/**
	 * タイトルのコレクションとリリース年を集約。
	 * @param titleCollection タイトルのコレクション
	 * @param year リリース年
	 */
	public TitleCollectionAndYear(TitleCollection titleCollection, int year)
	{
		super(titleCollection);

		this.year = year;
	}
}
