package kumagai.av;

import java.sql.*;

/**
 * タイトル情報。title・watchテーブルに対応する内容。
 * @author kumagai
 */
public class Title2
{
	public final int id;
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
		id = results.getInt("id");
		title = results.getString("title");
		rentalDate = results.getString("rentaldate");
		buyDate = results.getString("buydate");
		watch = results.getString("watch");
		memo = results.getString("memo");
		dmmUrl = results.getString("dmmUrl");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format("%d %s %s", id, title, buyDate);
	}

	/**
	 * DMMのサムネイル画像URLを取得
	 * @return サムネイル画像URL
	 */
	public String getDmmImageUrlPs()
	{
		return Title1.getDmmImageUrlPs(dmmUrl);
	}

	/**
	 * DMMのフルサイズ画像URLを取得
	 * @return フルサイズ画像URL
	 */
	public String getDmmImageUrlPl()
	{
		return Title1.getDmmImageUrlPl(dmmUrl);
	}
}
