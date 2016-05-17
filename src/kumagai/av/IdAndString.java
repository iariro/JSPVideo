package kumagai.av;

/**
 * IDと文字列。
 */
public class IdAndString
{
	public final int id;
	public final String string;

	/**
	 * 指定の値をフィールドに割り当てる。
	 * @param id ID
	 * @param string 文字列
	 */
	public IdAndString(int id, String string)
	{
		this.id = id;
		this.string = string;
	}
}
