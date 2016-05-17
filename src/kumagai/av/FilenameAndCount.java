package kumagai.av;

/**
 * ファイル名とカウントの対。ファイルの重複参照チェック用。
 * @author kumagai
 */
public class FilenameAndCount
{
	private final String fileName;
	private final int count;

	/**
	 * ファイル名を取得。
	 * @return ファイル名
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * カウントを取得。
	 * @return カウント
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * メンバーに値を割り当てる。
	 * @param fileName ファイル名
	 * @param count カウント
	 */
	public FilenameAndCount(String fileName, int count)
	{
		this.fileName = fileName;
		this.count = count;
	}
}
