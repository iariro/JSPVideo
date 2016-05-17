package kumagai.av;

import java.sql.*;

/**
 * コスチューム情報。
 * @author kumagai
 */
public class Costume
{
	public final int costumeid;
	public final String title;
	public final String filename;
	public final String costume;
	public final String feature;
	public final int score;

	/**
	 * 指定の値をフィールドに割り当て。
	 * @param results DBレコード
	 */
	public Costume(ResultSet results)
		throws SQLException
	{
		this.costumeid = results.getInt("costumeid");
		this.title = results.getString("title").trim();
		this.filename = results.getString("filename").trim();
		this.costume = results.getString("costume").trim();
		this.feature = results.getString("feature").trim();
		this.score = results.getInt("score");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("%s %s %s %d", title, costume, feature, score);
	}
}
