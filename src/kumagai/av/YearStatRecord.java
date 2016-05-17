package kumagai.av;

/**
 * 年ごと統計情報レコード。
 */
public class YearStatRecord
{
	public final int year;

	public int rental1;
	public int rental2;
	public int buy1;
	public int buy2;

	/**
	 * メンバーを初期化。
	 * @param year 年
	 */
	public YearStatRecord(int year)
	{
		this.year = year;
	}
}
