package kumagai.av;

import java.sql.*;

/**
 * タイトル情報。title・watchテーブルに対応する内容。
 * @author kumagai
 */
public class Title2
{
	public final String title;
	public final String rentalDate;
	public final String buyDate;
	public final String watch;
	public final String memo;
	public final String dmmUrl;

	/**
	 * メンバーに値を割り当てる。
	 * @param results DBの１レコード
	 */
	public Title2(ResultSet results)
		throws SQLException
	{
		title = results.getString("title");
		rentalDate = results.getString("rentaldate");
		buyDate = results.getString("buydate");
		watch = results.getString("watch");
		memo = results.getString("memo");
		dmmUrl = results.getString("dmmUrl");
	}
}
