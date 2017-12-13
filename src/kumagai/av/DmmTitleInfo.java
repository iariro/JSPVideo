package kumagai.av;

/**
 * DMMサイト上の情報から取得する作品情報
 */
public class DmmTitleInfo
{
	public String title;
	public String webReleaseDate;
	public String mediaReleaseDate;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format("%s %s %s", title, webReleaseDate, mediaReleaseDate);
	}
}
