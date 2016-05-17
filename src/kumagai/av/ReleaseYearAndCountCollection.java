package kumagai.av;

import java.sql.*;
import java.util.*;

/**
 * 購入数統計情報。
 */
public class ReleaseYearAndCountCollection
	extends ArrayList<YearAndCount>
{
	/**
	 * 購入数統計情報構築。
	 * @param connection DB接続オブジェクト
	 * @param column 対象カラム
	 */
	public ReleaseYearAndCountCollection(Connection connection, String column)
		throws SQLException
	{
		String sql =
			String.format(
				"select YEAR(releasedate) as year, COUNT(*) as count from title join watch on title.id=watch.titleid where watch.%s<>'' group by YEAR(releasedate) order by YEAR(releasedate) desc",
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
