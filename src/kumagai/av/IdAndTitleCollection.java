package kumagai.av;

import java.sql.*;
import java.util.*;

/**
 * タイトルID・タイトルのコレクション。
 * @author kumagai
 */
public class IdAndTitleCollection
	extends ArrayList<IdAndTitle>
{
	/**
	 * コレクションを構築する。
	 * @param connection DB接続オブジェクト
	 * @param asc true=昇順／false=降順
	 */
	public IdAndTitleCollection(Connection connection, boolean asc)
		throws SQLException
	{
		String sql = "select id, title from title order by id";

		if (! asc)
		{
			// 降順。

			sql += " desc";
		}

		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(sql);

		while (results.next())
		{
			add(new IdAndTitle(results.getInt(1), results.getString(2)));
		}

		results.close();
		statement.close();
	}
}
