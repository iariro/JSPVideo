package kumagai.av;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * コスチューム情報。
 * @author kumagai
 */
public class Costume
{
	public final int costumeid;
	public final int imageId;
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
		this.imageId = results.getInt("imageid");
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
		return String.format("%d %s %s %d", imageId, costume, feature, score);
	}
}
