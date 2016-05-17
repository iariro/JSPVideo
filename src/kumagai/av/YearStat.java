package kumagai.av;

import java.util.*;

/**
 * 年ごと統計情報。
 */
public class YearStat
	extends ArrayList<YearStatRecord>
{
	public int minYear;
	public int maxYear;

	/**
	 * 年ごと統計情報構築。
	 * @param rental1Stat 年毎レンタル数
	 * @param buy1Stat 年毎購入数
	 * @param rental2Stat リリース年毎レンタル数
	 * @param buy2Stat リリース年毎購入数
	 */
	public YearStat(ArrayList<YearAndCount> rental1Stat,
		ArrayList<YearAndCount> buy1Stat, ArrayList<YearAndCount> rental2Stat,
		ArrayList<YearAndCount> buy2Stat)
	{
		ArrayList<ArrayList<YearAndCount>> statCollection =
			new ArrayList<ArrayList<YearAndCount>>();

		statCollection.add(rental1Stat);
		statCollection.add(buy1Stat);
		statCollection.add(rental2Stat);
		statCollection.add(buy2Stat);

		for (ArrayList<YearAndCount> stat : statCollection)
		{
			for (YearAndCount yearAndCount : stat)
			{
				if (minYear == 0 || minYear > yearAndCount.year)
				{
					// 最小年を下回る。

					minYear = yearAndCount.year;
				}

				if (maxYear == 0 || maxYear < yearAndCount.year)
				{
					// 最大年を上回る。

					maxYear = yearAndCount.year;
				}
			}
		}

		int size = maxYear - minYear + 1;

		for (int i=0 ; i<size ; i++)
		{
			add(new YearStatRecord(minYear + i));
		}

		for (YearAndCount yearAndCount : rental1Stat)
		{
			get(yearAndCount.year - minYear).rental1 =
				yearAndCount.count;
		}

		for (YearAndCount yearAndCount : buy1Stat)
		{
			get(yearAndCount.year - minYear).buy1 =
				yearAndCount.count;
		}

		for (YearAndCount yearAndCount : rental2Stat)
		{
			get(yearAndCount.year - minYear).rental2 =
				yearAndCount.count;
		}

		for (YearAndCount yearAndCount : buy2Stat)
		{
			get(yearAndCount.year - minYear).buy2 =
				yearAndCount.count;
		}

		Collections.sort(
			this,
			new Comparator<YearStatRecord>()
			{
				public int compare(YearStatRecord y1, YearStatRecord y2)
				{
					return - Integer.compare(y1.year, y2.year);
				}
			});
	}

	/**
	 * 内容ダンプ。
	 */
	public void dump()
	{
		for (int i=0 ; i<size() ; i++)
		{
			System.out.printf(
				"%d : %2d %2d %2d %2s\n",
				minYear + i,
				get(i).rental1,
				get(i).buy1,
				get(i).rental2,
				get(i).buy2);
		}
	}
}
