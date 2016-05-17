package kumagai.av;

import java.util.*;

/**
 * 作品の状態履歴１件。
 * @author kumagai
 */
public class WatchMemoTableEntry
{
	static public HashMap<String, String> colorTable =
		new HashMap<String, String>();

	static
	{
		colorTable.put("○", "#ccffff");
		colorTable.put("◎", "white");
		colorTable.put("△", "lightgray");
		colorTable.put("－", "white");
		colorTable.put("×", "gray");
	}

	public final Title3 title;
	public final String [][] memos;

	/**
	 * 指定の値をメンバーに割り当てる。
	 * @param title タイトル情報
	 * @param memos メモ
	 */
	public WatchMemoTableEntry(Title3 title, String [] memos)
	{
		this.title = title;
		this.memos = new String [memos.length][];

		for (int i=0 ; i<memos.length ; i++)
		{
			this.memos[i] = new String [2];

			this.memos[i][0] = colorTable.get(memos[i]);
			this.memos[i][1] = memos[i];
		}
	}
}
