package kumagai.av;

/**
 * タイトルIDとタイトルの対。
 * @author kumagai
 */
public class IdAndTitle
{
	public final int id;
	public final String title;

	/**
	 * タイトルID取得。
	 * @return タイトルID
	 */
	public String getId()
	{
		return Integer.toString(id);
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
	 * メンバーに値を割り当てる。
	 * @param id タイトルID
	 * @param title タイトル
	 */
	public IdAndTitle(int id, String title)
	{
		this.id = id;
		this.title = title;
	}
}
