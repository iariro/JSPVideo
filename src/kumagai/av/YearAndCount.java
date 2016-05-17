package kumagai.av;

import java.sql.*;

/**
 * 年とカウントの対。
 */
public class YearAndCount
{
	public final int year;
	public final int count;

	/**
	 * DBレコードからメンバーを取得。
	 * @param results DBレコード
	 */
	public YearAndCount(ResultSet results)
		throws SQLException
	{
		this.year = results.getInt("year");
		this.count = results.getInt("count");
	}
}
