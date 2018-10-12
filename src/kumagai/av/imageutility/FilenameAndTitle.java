package kumagai.av.imageutility;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 画像ファイル名とタイトル情報
 */
public class FilenameAndTitle
{
	public final String filename;
	public final String title;
	public int width;
	public int height;

	/**
	 * DBレコードの値をフィールドに割り当てる
	 * @param results DBレコード
	 */
	public FilenameAndTitle(ResultSet results)
		throws SQLException
	{
		this.filename = results.getString("filename");
		this.title = results.getString("title");
	}

	@Override
	public String toString()
	{
		return String.format("%s %s %d %d", filename, title, width, height);
	}
}
