package kumagai.av;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 視聴情報のコレクション。
 * @author kumagai
 */
public class WatchCollection
{
	/**
	 * 視聴情報を取得。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトル情報
	 * @return 視聴情報
	 */
	static public ArrayList<WatchInformation> getByTitleId
		(Connection connection, String titleid)
		throws SQLException
	{
		String sql = "select * from watch where titleid=? order by updatedate";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, titleid);
		ResultSet results = statement.executeQuery();

		ArrayList<WatchInformation> watchInformations =
			new ArrayList<WatchInformation>();

		while (results.next())
		{
			watchInformations.add(new WatchInformation(results));
		}

		results.close();
		statement.close();

		return watchInformations;
	}

	/**
	 * タイトルが存在しないタイトルID分視聴情報を検出。
	 * @param connection DB接続オブジェクト
	 * @return タイトルが存在しないタイトルID分視聴情報
	 */
	static public ArrayList<WatchInformation> getNotExistTitleWatch
		(Connection connection)
		throws SQLException
	{
		String sql =
			"select * from watch where titleid not in (select id from title)";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet results = statement.executeQuery();

		ArrayList<WatchInformation> watchInformations =
			new ArrayList<WatchInformation>();

		while (results.next())
		{
			watchInformations.add(new WatchInformation(results));
		}

		results.close();
		statement.close();

		return watchInformations;
	}

	/**
	 * 不正な日付の視聴情報を検出。
	 * @param connection DB接続オブジェクト
	 * @return 不正な日付の視聴情報
	 */
	static public ArrayList<WatchInformation> getInvalidDate
		(Connection connection)
		throws SQLException
	{
		// 購入日が10文字未満（ただし空文字列・“くらい”を含むものは除外）
		// レンタル日が10文字未満（ただし空文字列・“くらい”を含むものは除外）
		// 購入日＜リリース日
		// レンタル日＜リリース日
		String sql =
			"select * from watch join title on title.id=watch.titleid where (LEN(buydate) between 1 and 9 and buydate not like '%くらい') or (LEN(rentaldate) between 1 and 9 and rentaldate not like '%くらい') or (case when isdate(rentaldate)=1 then rentaldate else null end)<releasedate or (case when isdate(buydate)=1 then buydate else null end)<releasedate";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet results = statement.executeQuery();

		ArrayList<WatchInformation> watchCollection =
			new ArrayList<WatchInformation>();

		while (results.next())
		{
			watchCollection.add(new WatchInformation(results));
		}

		results.close();
		statement.close();

		return watchCollection;
	}

	/**
	 * 指定のIDのタイトルが存在するかを取得。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトルID
	 * @return true=存在する／false=しない
	 */
	static public boolean exists(Connection connection, String titleid)
		throws SQLException
	{
		String sql = "select * from watch where titleid=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, titleid);
		ResultSet results = statement.executeQuery();

		boolean exist = results.next();
		results.close();
		statement.close();

		return exist;
	}

	/**
	 * 視聴情報を新規登録。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトルID
	 * @param rentaldate レンタル日付
	 * @param buydate 購入日付
	 * @param watch 注目フラグ
	 * @param memo メモ
	 */
	static public void insertAsNew(Connection connection, int titleid,
		String rentaldate, String buydate, String watch, String memo)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into watch (titleid, rentaldate, buydate, watch, memo, updatedate, sequence) values (?, ?, ?, ?, ?, getdate(), 0)");

		if (watch != null)
		{
			// 注目フラグ指定あり。

			watch = "1";
		}
		else
		{
			// 注目フラグ指定なし。

			watch = "0";
		}

		statement.setInt(1, titleid);
		statement.setString(2, rentaldate);
		statement.setString(3, buydate);
		statement.setString(4, watch);
		statement.setString(5, memo);
		statement.executeUpdate();

		statement.close();
	}

	/**
	 * 視聴情報を上書き登録。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトルID
	 * @param rentaldate レンタル日付
	 * @param buydate 購入日付
	 * @param watch 注目フラグ
	 * @param memo メモ
	 */
	static public void insertAsUpdate(Connection connection, int titleid,
		String rentaldate, String buydate, String watch, String memo)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into watch (titleid, rentaldate, buydate, watch, memo, updatedate, sequence) values (?, ?, ?, ?, ?, getdate(), (select max(sequence) from watch where titleid=?)+1)");

		if (watch != null)
		{
			// 注目フラグ指定あり。

			watch = "1";
		}
		else
		{
			// 注目フラグ指定なし。

			watch = "0";
		}

		statement.setInt(1, titleid);
		statement.setString(2, rentaldate);
		statement.setString(3, buydate);
		statement.setString(4, watch);
		statement.setString(5, memo);
		statement.setInt(6, titleid);
		statement.executeUpdate();

		statement.close();
	}

	/**
	 * 「除外予定」「購入予定」といったメモを「除外」「購入」に確定。
	 * @param connection DB接続オブジェクト
	 * @param before 変更前メモ
	 * @param after 変更後メモ
	 * @return 変更件数
	 */
	static public int updateUnfixMemo
		(Connection connection, String before, String after)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into watch (titleid, rentaldate, buydate, watch, memo, updatedate, sequence) select titleid, rentaldate, buydate, watch, ?, getdate(), sequence+1 from watch w1 where memo=? and sequence=(select max(sequence) from watch w2 where w1.titleid=w2.titleid)");

		statement.setString(1, after);
		statement.setString(2, before);

		int count = statement.executeUpdate();

		statement.close();

		return count;
	}

	/**
	 * 視聴情報を変更。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトルID
	 * @param rentaldate レンタル日付
	 * @param buydate 購入日付
	 * @param watch 注目フラグ
	 * @param memo メモ
	 */
	static public void update(Connection connection, String titleid,
		String rentaldate, String buydate, String watch, String memo)
		throws SQLException
	{
		String sql =
			"update watch set rentaldate=?, buydate=?, watch=?, memo=? where titleid=?";

		PreparedStatement statement = connection.prepareStatement(sql);

		if (watch != null)
		{
			// 注目フラグ指定あり。

			watch = "1";
		}
		else
		{
			// 注目フラグ指定なし。

			watch = "0";
		}

		statement.setString(1, rentaldate);
		statement.setString(2, buydate);
		statement.setString(3, watch);
		statement.setString(4, memo);
		statement.setString(5, titleid);

		statement.executeUpdate();

		statement.close();
	}

	/**
	 * 基準日当時の視聴情報を取得。
	 * @param connection DB接続オブジェクト
	 * @param date 基準日
	 */
	static ArrayList<WatchInformation> getWatchHistory
		(Connection connection, String date)
		throws SQLException
	{
		String sql = "select * from watch w1 where len(w1.buydate)>0 and w1.sequence=(select MAX(w2.sequence) from watch w2 where w2.titleid=w1.titleid and updatedate<=?)";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, date);
		ResultSet results = statement.executeQuery();

		ArrayList<WatchInformation> watches = new ArrayList<WatchInformation>();

		while (results.next())
		{
			watches.add(new WatchInformation(results));
		}

		results.close();
		statement.close();

		return watches;
	}
}
