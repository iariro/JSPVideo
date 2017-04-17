package kumagai.av;

import ktool.datetime.*;

/**
 * 画像ファイルと更新日時の対
 */
public class FileAndDatetime
{
	public String filepath;
	public DateTime updatedate;

	/**
	 * 更新日付を取得
	 * @return 更新日付
	 */
	public String getUpdateDatetime()
	{
		return updatedate.toFullString();
	}

	/**
	 * 指定の値をフィールドに割り当てる
	 * @param filepath ファイルパス
	 * @param updatedate 更新日付
	 */
	public FileAndDatetime(String filepath, DateTime updatedate)
	{
		this.filepath = filepath;
		this.updatedate = updatedate;
	}
}
