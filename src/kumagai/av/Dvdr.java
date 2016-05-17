package kumagai.av;

import java.sql.*;

/**
 * DVD-Rディスク情報。
 *
 * @author kumagai
 */
public class Dvdr
{
	private final int diskId;
	private final String title;
	private final int tokuteiCount;
	private final int trackNum;

	/**
	 * DB読み込みレコードからオブジェクトを構築。
	 * @param results dvdrテーブル１レコード
	 */
	public Dvdr(ResultSet results)
		throws SQLException
	{
		diskId = results.getInt("diskId");
		title = results.getString("title");
		tokuteiCount = results.getInt("tokuteicount");
		trackNum = results.getInt("trackNum");
	}

	/**
	 * ディスクID取得。
	 * @return ディスクID
	 */
	public String getDiskId()
	{
		return Integer.toString(diskId);
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
	 * 特定済みトラック数取得。
	 * @return 特定済みトラック数
	 */
	public int getTokuteiCount()
	{
		return tokuteiCount;
	}

	/**
	 * トラック数取得。
	 * @return トラック数
	 */
	public int getTrackNum()
	{
		return trackNum;
	}
}
