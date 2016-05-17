package kumagai.av;

import java.sql.*;
import java.text.*;

/**
 * 日記レビュー情報。
 * @author kumagai
 */
public class DiaryReview
{
	static private final SimpleDateFormat formatDate;

	/**
	 * 日付フォーマットオブジェクト構築。
	 */
	static
	{
		formatDate = new SimpleDateFormat();
		formatDate.applyPattern("yyyy/MM/dd");
	}

	public final int id;
	public final int titleid;
	public final String diarydate;

	/**
	 * DB情報から日記レビュー情報を構築。
	 * @param results DB情報
	 */
	public DiaryReview(ResultSet results)
		throws SQLException
	{
		id = results.getInt("id");
		titleid = results.getInt("titleid");
		diarydate = formatDate.format(results.getDate("diarydate"));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("%d %d %s", id, titleid, diarydate);
	}
}
