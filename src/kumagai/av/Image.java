package kumagai.av;

import java.sql.*;

/**
 * 画像情報。
 * @author kumagai
 */
public class Image
{
	public final int id;
	public final String titleId;
	public final String position;
	public final String fileName;
	public final String comment;

	/**
	 * 指定の値をメンバーに割り当て。
	 * @param id ID
	 * @param titleId タイトルID
	 * @param position 画像位置
	 * @param fileName ファイル名
	 * @param comment コメント
	 */
	public Image(int id, String titleId, String position, String fileName,
		String comment)
	{
		this.id = id;
		this.titleId = titleId;
		this.position = position;
		this.fileName = fileName;
		this.comment = comment;
	}

	/**
	 * １レコードからオブジェクトを構築する。
	 * @param results レコード情報
	 */
	public Image(ResultSet results)
		throws SQLException
	{
		id = results.getInt("id");
		titleId = results.getString("titleid");
		position = results.getString("position");
		fileName = results.getString("filename");
		comment = results.getString("comment");
	}
}
