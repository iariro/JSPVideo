package kumagai.av;

import java.sql.*;

/**
 * DVD-Rへのダビング情報。
 * @author kumagai
 */
public class DvdrDubbing
{
	static private final String [] area =
		{
			"25,10,196,146",
			"442,10,196,146",

			"25,167,196,146",
			"442,167,196,146",

			"25,326,196,146",
			"442,324,196,146"
		};

	public final int position;
	public final int titleId;
	public final int imageIndex;
	public final String title;
	public final String fileName0;
	public final String fileName0Area;
	public final String fileName1;
	public final String fileName2;

	/**
	 * ディスク内位置情報取得。
	 * @return ディスク内位置情報
	 */
	public String getPosition()
	{
		return Integer.toString(position);
	}

	/**
	 * タイトル取得。
	 * @return タイトル
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * タイトルID取得。
	 * @return タイトルID
	 */
	public String getTitleId()
	{
		return Integer.toString(titleId);
	}

	/**
	 * サムネイル画像ファイル名取得。
	 * @return サムネイル画像ファイル名
	 */
	public String getFileName0()
	{
		return fileName0;
	}

	/**
	 * サムネイル画像の切り出し座標取得。
	 * @return サムネイル画像の切り出し座標
	 */
	public String getFileName0Area()
	{
		return fileName0Area;
	}

	/**
	 * タイトル画像ファイル名取得。
	 * @return タイトル画像ファイル名
	 */
	public String getFileName1()
	{
		return fileName1;
	}

	/**
	 * シーン画像ファイル名取得。
	 * @return シーン画像ファイル名
	 */
	public String getFileName2()
	{
		return fileName2;
	}

	/**
	 * DB読み取り１レコードからオブジェクトを構築。
	 * @param results DB読み取り１レコード
	 * @param dvdrTitle DVD-Rタイトル
	 * @param fileName2 シーン画像ファイルパス
	 */
	public DvdrDubbing(ResultSet results, String dvdrTitle, String fileName2)
		throws SQLException
	{
		this.position = results.getInt("position");
		this.titleId = results.getInt("titleid");
		this.imageIndex = results.getInt("imageindex");
		this.title = results.getString("title");
		this.fileName0 =
			String.format("%s_%d.jpg", dvdrTitle, 1 + (position - 1) / 6);
		this.fileName0Area = area[(position - 1) % 6];
		this.fileName1 = results.getString("filename");
		this.fileName2 = fileName2;
	}

	/**
	 * 情報未特定エントリとなるオブジェクトを構築。
	 * @param dvdrTitle DVD-Rタイトル
	 * @param position ディスク位置
	 */
	public DvdrDubbing(String dvdrTitle, int position)
	{
		this.position = position;
		this.titleId = -1;
		this.imageIndex = -1;
		this.title = null;
		this.fileName0 =
			String.format("%s_%d.jpg", dvdrTitle, 1 + (position - 1) / 6);
		this.fileName0Area = area[(position - 1) % 6];
		this.fileName1 = null;
		this.fileName2 = null;
	}
}
