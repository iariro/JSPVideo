package kumagai.av;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import ktool.datetime.DateTime;
import ktool.datetime.TimeSpan;

/**
 * シャッフル済みタイトルコレクション。
 */
public class Title3ShuffleCollection
	extends ArrayList<Title3AndRandomValue>
{
	private final Title3 title1;
	private final Title3 title2;
	private final Title3 title3;
	private final DateTime startDate;

	/**
	 * 指定のコレクションのシャッフル済みコレクションを生成する。
	 * @param titleCollection 対象のタイトルコレクション
	 * @param originDate 基点となる日付
	 * @param today 今日の日付
	 * @param randomAdjust インデックスの調整
	 */
	public Title3ShuffleCollection(ArrayList<Title3> titleCollection,
		DateTime originDate, DateTime today, int randomAdjust)
	{
		// 基点日をシードにランダムオブジェクト生成。
		int seed =
			originDate.getYear() * 10000 +
			originDate.getMonth() * 100 +
			originDate.getDay();

		Random random = new Random(seed);

		DateTime today2 =
			new DateTime(
				today.getYear(),
				today.getMonth(),
				today.getDay(),
				0,
				0,
				0);

		DateTime originDate2 =
			new DateTime(
				originDate.getYear(),
				originDate.getMonth(),
				originDate.getDay(),
				0,
				0,
				0);

		TimeSpan diff = today2.diff(originDate2);

		int index = diff.getDay() + randomAdjust;

		startDate = new DateTime(originDate2);
		startDate.addDay(-randomAdjust);

		// ２周目以降の場合は並びを変えるようランダム値調整。
		if (titleCollection.size() > 0)
		{
			// １個でもある。

			while (index >= titleCollection.size())
			{
				for (int i=0 ; i<titleCollection.size() ; i++)
				{
					random.nextInt();
					startDate.addDay(1);
				}

				index -= titleCollection.size();
			}
		}


		// タイトルのコレクションをシャッフル。
		for (Title3 title : titleCollection)
		{
			add(new Title3AndRandomValue(title, random));
		}

		Collections.sort(
			this,
			new Comparator<Title3AndRandomValue>()
			{
				public int compare
					(Title3AndRandomValue title1, Title3AndRandomValue title2)
				{
					return
						Integer.compare(title1.randomValue, title2.randomValue);
				}
			});

		if (index - 1 >= 0 && index - 1 < size())
		{
			// インデックスは範囲内である。

			title1 = get(index - 1).title;
		}
		else
		{
			// インデックスは範囲外である。

			title1 = null;
		}

		if (index >= 0 && index < size())
		{
			// インデックスは範囲内である。

			title2 = get(index).title;
		}
		else
		{
			// インデックスは範囲外である。

			title2 = null;
		}

		if (index + 1 >= 0 && index + 1 < size())
		{
			// インデックスは範囲内である。

			title3 = get(index + 1).title;
		}
		else
		{
			// インデックスは範囲外である。

			title3 = null;
		}
	}

	/**
	 * 昨日の１件を取得。
	 * @return 昨日の１件
	 */
	public Title3 getTitle1()
	{
		return title1;
	}

	/**
	 * 今日の１件を取得。
	 * @return 今日の１件
	 */
	public Title3 getTitle2()
	{
		return title2;
	}

	/**
	 * 今日の１件を取得。
	 * @return 今日の１件
	 */
	public Title3 getTitle3()
	{
		return title3;
	}

	/**
	 * 開始日を取得
	 * @return 開始日
	 */
	public DateTime getStartDate()
	{
		return startDate;
	}
}
