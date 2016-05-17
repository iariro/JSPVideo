package kumagai.av;

import java.util.*;

/**
 * TitleCollectionAndYearオブジェクトのコレクション。
 * @author kumagai
 */
public class TitleCollectionAndYearCollection
	extends ArrayList<TitleCollectionAndYear>
{
	/**
	 * タイトルのコレクションから年ごとのタイトルコレクションのコレクションを
	 * 構築する。
	 * @param titleCollection タイトルのコレクション
	 */
	public TitleCollectionAndYearCollection(TitleCollection titleCollection)
	{
		int year = 0;
		int year2 = 0;
		TitleCollection titleCollection2 = new TitleCollection();

		for (Title3 title : titleCollection)
		{
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(title.releaseDate);
			year = calendar.get(Calendar.YEAR);

			if (year2 > 0 && year2 != year)
			{
				// 年の変わり目。

				add(new TitleCollectionAndYear(titleCollection2, year2));
				titleCollection2 = new TitleCollection();
			}

			titleCollection2.add(title);

			year2 = year;
		}

		add(new TitleCollectionAndYear(titleCollection2, year));
	}
}
