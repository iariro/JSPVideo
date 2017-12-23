package kumagai.av.imageutility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 画像ファイル名とタイトルのコレクション
 * 画像チェック用
 */
public class FilenameAndTitleCollection
	extends ArrayList<FilenameAndTitle>
{
	/**
	 * 空のコレクションを生成
	 */
	public FilenameAndTitleCollection()
	{
		// 何もしない
	}

	/**
	 * DBデータからコレクションを生成
	 * @param results DBデータ
	 */
	public FilenameAndTitleCollection(ResultSet results)
		throws SQLException
	{
		while (results.next())
		{
			add(new FilenameAndTitle(results));
		}
	}
}
