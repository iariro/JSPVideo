package kumagai.av;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import ktool.datetime.DateTime;

/**
 * タイトル情報のコレクション。
 * @author kumagai
 */
public class TitleCollection
	extends ArrayList<Title3>
{
	/**
	 * テストコード。
	 * @param args 未使用
	 */
	public static void main(String[] args)
		throws Exception
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		Title1 title = getOneTitle1(connection, "157");

		System.out.printf("%s %s\n", title.title, title.shortTitle);

		connection.close();
	}

	/**
	 * タイトル情報の編集。
	 * @param connection DB接続オブジェクト
	 * @param titleId タイトルID
	 * @param title タイトル
	 * @param shortTitle 短縮タイトル
	 * @param type メディアタイプ
	 * @param releaseDate リリース日
	 * @param memo メモ
	 * @param dmmUrl DMM URL
	 */
	static public void update(Connection connection, String titleId,
		String title, String shortTitle, String type, String releaseDate,
		String memo, String dmmUrl)
		throws SQLException
	{
		String sql =
			"update title set title=?, shorttitle=?, type=?, releasedate=?, memo=?, dmmurl=? where id=?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, title);
		statement.setString(2, shortTitle);
		statement.setString(3, type);
		statement.setString(4, releaseDate);
		statement.setString(5, memo);
		statement.setString(6, dmmUrl);
		statement.setString(7, titleId);

		statement.executeUpdate();

		statement.close();
	}

	/**
	 * タイトル情報追加。
	 * @param connection DB接続オブジェクト
	 * @param title タイトル
	 * @param type メディアタイプ
	 * @param memo メモ
	 * @param releaseDate リリース日
	 * @param dmmUrl DMM URL
	 * @return 生成したタイトルID
	 */
	static public int insert(Connection connection, String title, String type,
		String memo, String releaseDate, String dmmUrl)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into title (title, type, memo, releasedate, dmmurl) values (?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, title);
		statement.setString(2, type);
		statement.setString(3, memo);
		statement.setString(4, releaseDate);
		statement.setString(5, dmmUrl);
		statement.executeUpdate();

		int newid;

		ResultSet keys = statement.getGeneratedKeys();

		try
		{
			if (keys.next())
			{
				// 読み込み成功。

				newid = keys.getInt(1);
			}
			else
			{
				// 読み込み失敗。

				throw new SQLException();
			}
		}
		finally
		{
			keys.close();
			statement.close();
		}

		return newid;
	}

	/**
	 * 指定のタイトルIDのタイトル情報１を取得。
	 * @param connection DB接続オブジェクト
	 * @param titleId タイトルID
	 * @return タイトル情報
	 */
	static public Title1 getOneTitle1(Connection connection, String titleId)
		throws SQLException
	{
		String sql =
			"select title, shorttitle, type, releasedate, memo, dmmurl from title where id=?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, titleId);
		ResultSet results = statement.executeQuery();

		Title1 title = null;

		if (results.next())
		{
			// シーク成功。

			title = new Title1(results);
		}

		results.close();
		statement.close();

		return title;
	}

	/**
	 * 指定のタイトルIDのタイトル情報２を取得。
	 * @param connection DB接続オブジェクト
	 * @param titleId タイトルID
	 * @return タイトル情報
	 */
	static public Title2 getOneTitle2(Connection connection, String titleId)
		throws SQLException
	{
		String sql =
			"select title.id, title, rentaldate, buydate, watch, dmmurl, watch.memo from title left join watch on watch.titleid=title.id where title.id=? order by sequence desc";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, titleId);
		ResultSet results = statement.executeQuery();

		Title2 title = null;

		if (results.next())
		{
			// シーク成功。

			title = new Title2(results);
		}

		results.close();
		statement.close();

		return title;
	}

	/**
	 * タイトルとメモの対の情報を取得する。
	 * @param connection DB接続オブジェクト
	 * @return タイトルとメモの対の情報
	 */
	static public HashMap<String, String> getTitleMemo(Connection connection)
		throws SQLException
	{
		String sql =
			"select title, memo from title where memo in (select memo from title group by memo having memo<>'')";

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		HashMap<String, String> titleMemo = new HashMap<String, String>();

		while (results.next())
		{
			String title = results.getString(1);
			String memo = results.getString(2);

			titleMemo.put(title, memo);
		}

		results.close();
		statement.close();

		return titleMemo;
	}

	/**
	 * 注目作品リストを取得。
	 * @param connection DB接続オブジェクト
	 * @return 注目作品リスト
	 */
	static public ArrayList<Title3> getWatchList(Connection connection)
		throws SQLException, ParseException
	{
		String sql =
			"select title.id, title, shorttitle, type, filename, releasedate, rentaldate, buydate, title.memo as titlememo, w1.memo as watchmemo from title join image on title.id=image.titleid left join watch w1 on title.id=w1.titleid where image.position=1 and watch=1 and w1.sequence=(select MAX(w2.sequence) from watch w2 where w2.titleid=w1.titleid) order by releasedate desc";

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		ArrayList<Title3> titleCollection = new ArrayList<Title3>();

		while (results.next())
		{
			titleCollection.add(new Title3(results, null));
		}

		results.close();
		statement.close();

		return titleCollection;
	}

	/**
	 * 画像なし購入作品リストを取得。
	 * @param connection DB接続オブジェクト
	 * @param imageCount 画像数
	 * @return 画像なし購入作品リスト
	 */
	static public ArrayList<Title3> getPurchasedAndNoImageList(Connection connection, int imageCount)
		throws SQLException, ParseException
	{
		PreparedStatement statement;

		if (imageCount == 0)
		{
			String sql = "select title.id,title,shorttitle,type,null as filename,dmmurl,releasedate, w1.rentaldate, w1.buydate,title.memo as titlememo, w1.memo as watchmemo from title join watch w1 on w1.titleid=title.id where title.id not in (select titleid from image) and (w1.sequence=(select MAX(w2.sequence) from watch w2 where w2.titleid=w1.titleid) or w1.sequence is null) and buydate<>'' and w1.memo<>'売却' order by buydate";
			statement = connection.prepareStatement(sql);
		}
		else
		{
			String sql = String.format("select title.id,title,shorttitle,type,filename,dmmurl,releasedate, w1.rentaldate, w1.buydate,title.memo as titlememo, w1.memo as watchmemo from title join watch w1 on w1.titleid=title.id join image on title.id=image.titleid where title.id in (select titleid from image group by titleid having count(*)=?) and (w1.sequence=(select MAX(w2.sequence) from watch w2 where w2.titleid=w1.titleid) or w1.sequence is null) and buydate<>'' and w1.memo<>'売却' and image.position=(select min(position) from image as img where img.titleid=image.titleid) order by buydate");
			statement = connection.prepareStatement(sql);
			statement.setInt(1, imageCount);
		}

		ResultSet results = statement.executeQuery();

		ArrayList<Title3> titleCollection = new ArrayList<Title3>();

		while (results.next())
		{
			titleCollection.add(new Title3(results, null));
		}

		results.close();
		statement.close();

		return titleCollection;
	}

	/**
	 * 視聴されていないタイトルを検出。
	 * @param connection DB接続オブジェクト
	 * @return 視聴されていないタイトル
	 */
	static public ArrayList<Title1> getNotWatchTitles(Connection connection)
		throws SQLException
	{
		String sql =
			"select title, shorttitle, type, releasedate, memo, dmmurl from title where id not in (select titleid from watch)";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet results = statement.executeQuery();

		ArrayList<Title1> titles = new ArrayList<Title1>();

		while (results.next())
		{
			titles.add(new Title1(results));
		}

		results.close();
		statement.close();

		return titles;
	}

	/**
	 * 何もしないコンストラクタ。
	 */
	public TitleCollection()
	{
		// 何もしない。
	}

	/**
	 * タイトルのコレクションを構築。
	 * @param connection DB接続オブジェクト
	 * @param buy true=購入したもののみ
	 * @param excludeDispose true=処分したものを除く
	 * @param order ソート列
	 * @param date 取得する日
	 * @param diaryReviewCollection 日記レビュー日付コレクション
	 */
	public TitleCollection(Connection connection, boolean buy,
		boolean excludeDispose, String order, String date,
		DiaryReviewCollection diaryReviewCollection)
		throws SQLException, ParseException
	{
		String sql =
			"select title.id, title, shorttitle, type, filename, dmmurl, releasedate, w1.rentaldate, w1.buydate, title.memo as titlememo, w1.memo as watchmemo from title left join image on title.id=image.titleid left join watch w1 on title.id=w1.titleid where isnull(image.position,0) in (select min(position) from image where title.id=image.titleid union select 0) ";

		if (date != null)
		{
			// 取得日指定あり。

			sql +=
				String.format(
					"and (w1.sequence=(select MAX(w2.sequence) from watch w2 where w2.titleid=w1.titleid and updatedate<='%s') or w1.sequence is null) ",
					date);
		}
		else
		{
			// 取得日指定なし。

			sql += "and (w1.sequence=(select MAX(w2.sequence) from watch w2 where w2.titleid=w1.titleid) or w1.sequence is null) ";
		}

		if (buy)
		{
			// 購入限定フラグon。

			sql += "and buydate<>'' ";
		}

		if (excludeDispose)
		{
			// 処分除外フラグon。

			sql += "and (w1.memo is null or w1.memo not in ('売却', '除外', '追加予定'))";
		}

		sql += "order by " + order;

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		while (results.next())
		{
			add(new Title3(results, diaryReviewCollection));
		}

		results.close();
		statement.close();
	}

	/**
	 * 購入した作品の年代散布図データ取得
	 * @param media メディア
	 * @return 購入した作品の年代散布図データ
	 */
	public String getPurchaseGenerationScatterData(String media)
	{
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		for (Title3 title : this)
		{
			if (title.buyDate != null && title.buyDateIsSure() &&
				(media != null && title.media.equals(media)))
			{
				// 購入日あり、指定のメディアである

				try
				{
					String point =
						String.format(
							"{x:%d,y:%d,name:'%s'}",
							DateTime.parseDateString(title.buyDate).getCalendar().getTimeInMillis(),
							new DateTime(title.releaseDate).getCalendar().getTimeInMillis(),
							title.title);

					if (count > 0)
					{
						// ２個目以降

						buffer.append(",");
					}

					buffer.append(point);

					count++;
				}
				catch (ParseException exception)
				{
				}
			}
		}

		return buffer.toString();
	}

	/**
	 * レンタルした作品の年代散布図データ取得
	 * @param media メディア
	 * @return レンタルした作品の年代散布図データ
	 */
	public String getRentalGenerationScatterData(String media)
	{
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		for (Title3 title : this)
		{
			if (title.rentalDate != null &&
				(media != null && title.media.equals(media)))
			{
				// 購入日あり、指定のメディアである

				try
				{
					String point =
						String.format(
							"{x:%d,y:%d,name:'%s'}",
							DateTime.parseDateString(title.rentalDate).getCalendar().getTimeInMillis(),
							new DateTime(title.releaseDate).getCalendar().getTimeInMillis(),
							title.title);
					if (count > 0)
					{
						// ２個目以降

						buffer.append(",");
					}

					buffer.append(point);

					count++;
				}
				catch (ParseException exception)
				{
				}
			}
		}

		return buffer.toString();
	}
}
