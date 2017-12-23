package kumagai.av;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import ktool.string.DateCollection;

/**
 * 日記レビュー情報コレクション。
 * @author kumagai
 */
public class DiaryReviewCollection
	extends ArrayList<DiaryReview>
{
	/**
	 * テストコード。
	 * @param args 未使用
	 */
	public static void main(String[] args)
		throws SQLException, ParseException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		DiaryReviewCollection diaryReviewCollection =
			new DiaryReviewCollection(connection, null);

		connection.close();

		for (int id : new int [] {191, 60})
		{
			DateCollection dateCollection =
				diaryReviewCollection.getDiaryDatesByTitleId(id);

			int size = dateCollection.size();

			String [][] dateTable;

			if (size < 10)
			{
				// １０件未満。

				dateTable = new String [size][];

				for (int i=0 ; i<size ; i++)
				{
					dateTable[i] = new String [1];
				}
			}
			else
			{
				// １０件以上。

				dateTable = new String [10][];

				for (int i=0 ; i<dateTable.length ; i++)
				{
					dateTable[i] = new String [(size + 9) / 10];
				}
			}

			for (int i=0 ; i<10 && i<size ; i++)
			{
				for (int j=0 ; j<size ; j+=10)
				{
					if (i + j < size)
					{
						dateTable[i][j / 10] = dateCollection.get(i + j);
					}
					else
					{
						dateTable[i][j / 10] = "----------";
					}
				}
			}

			for (int i=0 ; i<dateTable.length ; i++)
			{
				for (int j=0 ; j<dateTable[i].length ; j++)
				{
					System.out.print(dateTable[i][j]);
					System.out.print(" ");
				}
				System.out.println();
			}

			System.out.println();
		}
	}

	/**
	 * レビュー日付の追加
	 * @param connection DB接続オブジェクト
	 * @param titleId タイトルID
	 * @param diaryDate レビュー日付
	 */
	static public void insert
		(Connection connection, String titleId, String diaryDate)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into diaryreview (titleid, diarydate) values (?, ?)");

		statement.setString(1, titleId);
		statement.setString(2, diaryDate);
		statement.executeUpdate();

		statement.close();
	}

	/**
	 * １日複数レビュー検出。
	 * @param connection DB接続オブジェクト
	 * @return １日複数レビュー情報
	 */
	static public ArrayList<DateAndInt> getDuplicateReview
		(Connection connection)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"select diarydate,COUNT(*) as count from diaryreview group by diarydate having COUNT(*)>1");

		ResultSet results = statement.executeQuery();

		ArrayList<DateAndInt> dateAndIntCollection =
			new ArrayList<DateAndInt>();

		while (results.next())
		{
			dateAndIntCollection.add(new DateAndInt(results));
		}

		statement.close();

		return dateAndIntCollection;
	}

	/**
	 * DB値から日記レビュー情報コレクションを構築。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトルID
	 */
	public DiaryReviewCollection(Connection connection, String titleid)
		throws SQLException
	{
		String sql = "select * from diaryreview ";

		if (titleid != null)
		{
			// タイトルID指定あり。

			sql += "where titleid=? ";
		}

		sql += "order by titleid,diarydate";

		PreparedStatement statement = connection.prepareStatement(sql);

		if (titleid != null)
		{
			// タイトルID指定あり。

			statement.setString(1, titleid);
		}

		ResultSet results = statement.executeQuery();

		while (results.next())
		{
			add(new DiaryReview(results));
		}
	}

	/**
	 * 指定のタイトル分のレビュー情報を取得。
	 * @param titleid タイトルID
	 * @return 指定のタイトル分のレビュー情報
	 */
	public DateCollection getDiaryDatesByTitleId(int titleid)
		throws ParseException
	{
		DateCollection dates = new DateCollection();

		boolean find = false;

		for (int i=0 ; i<size() ; i++)
		{
			if (get(i).titleid == titleid)
			{
				// 指定のタイトル。

				dates.add(get(i).diarydate);

				find = true;
			}
			else
			{
				// 指定のタイトルではない。

				if (find)
				{
					// 指定のタイトルを通り過ぎた。

					break;
				}
			}
		}

		return dates;
	}
}
