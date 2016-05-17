package kumagai.av;

import java.sql.*;
import java.text.*;

/**
 * 視聴情報。
 */
public class WatchInformation
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
	public final String rentaldate;
	public final String buydate;
	public final boolean watch;
	public final String memo;
	public final int indexinday;
	public final String updatedate;
	public final int sequence;

	/**
	 * 注目マークを取得。
	 * @return 注目=○／空文字列
	 */
	public String getWatchMark()
	{
		return watch ? "○" : null;
	}

	/**
	 * DB取得値から視聴情報を構築する。
	 * @param results DBレコード
	 */
	public WatchInformation(ResultSet results)
		throws SQLException
	{
		this.id = results.getInt("id");
		this.titleid = results.getInt("titleid");
		this.rentaldate = results.getString("rentaldate");
		this.buydate = results.getString("buydate");
		this.watch = results.getInt("watch") > 0;
		this.memo = results.getString("memo");
		this.indexinday = results.getInt("indexinday");
		this.updatedate = formatDate.format(results.getDate("updatedate"));
		this.sequence = results.getInt("sequence");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format(
			"%d %d %s %s %s %s %d %s %d",
			id,
			titleid,
			rentaldate,
			buydate,
			watch,
			memo,
			indexinday,
			updatedate,
			sequence);
	}
}
