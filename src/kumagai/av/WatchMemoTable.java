package kumagai.av;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * 作品の状態履歴一覧情報。
 * @author kumagai
 */
public class WatchMemoTable
	extends ArrayList<WatchMemoTableEntry>
{
	/**
	 * テストコード。
	 * @param args 未使用
	 */
	static public void main(String [] args)
		throws SQLException, ParseException
	{
		Connection connection = DriverManager.getConnection(
			"jdbc:sqlserver://localhost:2144;DatabaseName=AV;User=sa;Password=p@ssw0rd;");

		String [] dates = { "2013/12/13", "2014/03/13", "today" };

		WatchMemoTable watchTable = new WatchMemoTable(connection, dates);

		connection.close();

		for (int i=0 ; i<watchTable.size() ; i++)
		{
			System.out.printf("%s : ", watchTable.get(i).title.title);

			for (int j=0 ; j<watchTable.get(i).memos.length ; j++)
			{
				System.out.printf("%2s|", watchTable.get(i).memos[j][1]);
			}

			System.out.println();
		}

		for (int i=0 ; i<watchTable.activeCount.length ; i++)
		{
			System.out.printf("%s ", watchTable.activeCount[i]);
		}
	}

	public String [] activeCount;

	/**
	 * 作品の状態履歴一覧情報。
	 * @param connection DB接続オブジェクト
	 * @param dates 日付配列
	 */
	public WatchMemoTable(Connection connection, String [] dates)
		throws SQLException, ParseException
	{
		int [] activeCount = new int [dates.length];
		int [] totalCount = new int [dates.length];

		this.activeCount = new String [dates.length];

		for (int i=0 ; i<dates.length ; i++)
		{
			String date = null;

			if (! dates[i].equals("today"))
			{
				// todayではない。

				date = dates[i];
			}

			TitleCollection titleCollection =
				new TitleCollection(
					connection,
					true,
					false,
					"updatedate",
					date,
					null);

			for (Title3 title : titleCollection)
			{
				int index = -1;

				for (int j=0 ; j<size() ; j++)
				{
					if (title.id == get(j).title.id)
					{
						// ID一致。

						index = j;
						break;
					}
				}

				String mark = "○";

				if (title.watchmemo == null)
				{
				}
				else if (title.watchmemo.length() == 0)
				{
				}
				else if (title.watchmemo.equals("除外") ||
						title.watchmemo.equals("売却"))
				{
					mark = "×";
				}
				else if (title.watchmemo.equals("除外予定"))
				{
					mark = "△";
				}
				else if (title.watchmemo.equals("追加予定"))
				{
					mark = "◎";
				}
				else
				{
					mark = "？";
				}

				boolean buy = true;

				if (title.buyDate != null && date != null)
				{
					// 購入日付と基準日比較可能。

					if (title.buyDate.compareTo(dates[i]) > 0)
					{
						// まだ購入はしていない。

						buy = false;
					}
				}

				if (buy)
				{
					// 購入している。

					if (index >= 0)
					{
						// 既存。

						get(index).memos[i][0] =
							WatchMemoTableEntry.colorTable.get(mark);
						get(index).memos[i][1] = mark;
					}
					else
					{
						// 初登場。

						String [] memos = new String [dates.length];

						memos[i] = mark;

						for (int j=0 ; j<i ; j++)
						{
							memos[j] = "－";
						}

						add(new WatchMemoTableEntry(title, memos));
					}

					if (title.watchmemo == null ||
						title.watchmemo.length() == 0)
					{
						// メモが空文字列＝有効状態。

						activeCount[i]++;
					}
					totalCount[i]++;
				}
			}

			this.activeCount[i] =
				String.format("%d/%d", activeCount[i], totalCount[i]);
		}
	}
}
