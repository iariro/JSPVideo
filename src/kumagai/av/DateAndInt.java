package kumagai.av;

import java.sql.*;

/**
 * 日付とカウントの対。
 * @author kumagai
 */
public class DateAndInt
{
	public final String date;
	public final int count;

	/**
	 * DB取得値から日付とカウントの対を構築。
	 * @param results DB取得レコード
	 */
	public DateAndInt(ResultSet results)
		throws SQLException
	{
		this.date = results.getString("diarydate");
		this.count = results.getInt("count");
	}
}
