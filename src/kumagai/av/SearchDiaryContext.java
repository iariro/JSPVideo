package kumagai.av;

import ktool.datetime.*;

/**
 * 日記検索情報。
 */
public class SearchDiaryContext
{
	static private final SearchDiaryContext searchDiaryContext =
		new SearchDiaryContext();

	/**
	 * 唯一のインスタンスを返却。
	 * @return インスタンス
	 */
	static public SearchDiaryContext getInstance()
	{
		return searchDiaryContext;
	}

	public final int startYear = 2012;
	public final int startMonth = 1;
	public final int endYear = new DateTime().getYear();
	public final int endMonth = new DateTime().getMonth();
}
