package kumagai.av.struts2;

import kumagai.av.*;

/**
 * リリース日順全タイトルリスト表示アクション用セッションコンテキスト。
 * @author kumagai
 */
public class TitleListByYearActionContext
{
	public final TitleCollectionAndYearCollection titleCollectionAndYearCollection;

	/**
	 * 指定の値をメンバーに割り当てる。
	 * @param titleCollectionAndYearCollection 年ごとのタイトルのコレクション
	 */
	public TitleListByYearActionContext
		(TitleCollectionAndYearCollection titleCollectionAndYearCollection)
	{
		this.titleCollectionAndYearCollection = titleCollectionAndYearCollection;
	}
}
