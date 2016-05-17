package kumagai.av;

import java.sql.*;
import java.util.*;

/**
 * レンタル数統計情報。
 */
public class WatchYearAndCountCollection
	extends ArrayList<YearAndCount>
{
	/**
	 * レンタル数統計情報構築。
	 * @param connection DB接続オブジェクト
	 * @param column 対象カラム
	 */
	public WatchYearAndCountCollection(Connection connection, String column)
		throws SQLException
	{
		String sql =
			String.format(
				"select YEAR(%s) as year, COUNT(*) as count from watch where isdate(%s)>0 group by YEAR(%s) order by YEAR(%s) desc",
				column,
				column,
				column,
				column);

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		while (results.next())
		{
			add(new YearAndCount(results));
		}

		results.close();
		statement.close();
	}
}
