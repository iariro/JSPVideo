package kumagai.av;

import java.sql.*;
import java.util.*;

/**
 * DVD-R情報コレクション。
 * @author kumagai
 */
public class DvdrCollection
	extends ArrayList<Dvdr>
{
	/**
	 * ディスクIDをキーとしてディスクタイトル取得。
	 * @param connection DB接続オブジェクト
	 * @param diskid ディスクID
	 * @return ディスクタイトル／null=取得失敗
	 */
	static public String getTitleById(Connection connection, String diskid)
		throws SQLException
	{
		String title = null;

		String sql = "select title from dvdr where diskid=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, diskid);
		ResultSet results = statement.executeQuery();

		if (results.next())
		{
			// シーク成功。

			title = results.getString("title");
		}

		results.close();
		statement.close();

		return title;
	}

	/**
	 * ディスクIDをキーとしてDVD-R情報を取得。
	 * @param connection DB接続オブジェクト
	 * @param diskid ディスクID
	 * @return DVD-R情報
	 */
	static public Dvdr getDvdrById(Connection connection, String diskid)
		throws SQLException
	{
		Dvdr dvdr = null;

		String sql = "select diskid, title, tracknum, 0 as tokuteicount from dvdr where diskid=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, diskid);
		ResultSet results = statement.executeQuery();

		if (results.next())
		{
			// シーク成功。

			dvdr = new Dvdr(results);
		}

		results.close();
		statement.close();

		return dvdr;
	}

	/**
	 * 全DVD-R情報コレクションを構築。
	 * @param connection DB接続オブジェクト
	 */
	public DvdrCollection(Connection connection)
		throws SQLException
	{
		String sql =
			"select dvdrdubbing.diskid, dvdr.title, count(*) as tokuteicount, dvdr.tracknum from dvdrdubbing join dvdr on dvdr.diskid=dvdrdubbing.diskid group by dvdrdubbing.diskid, dvdr.title, dvdr.tracknum";

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		while (results.next())
		{
			add(new Dvdr(results));
		}

		results.close();
		statement.close();
	}
}
