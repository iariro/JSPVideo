package kumagai.av;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DMMの作品情報Webページ
 */
public class DmmServer
{
	static private final Pattern patternTitle = Pattern.compile(".*<h1 id=\"title\" [^>]*>([^<]*).*");
	static private final Pattern patternDate = Pattern.compile(".*([0-9]{4}/[0-9]{2}/[0-9]{2}).*");

	static public void main(String [] args)
		throws IOException
	{
		InputStream in = new FileInputStream(args[0]);
		DmmTitleInfo titleInfo = getTitleInfo(in);
		System.out.println(titleInfo);
	}

	/**
	 * 作品情報読み取り内部状態
	 */
	enum DmmReadStatus
	{
		none,
		webReleaseDate,
		mediaReleaseDate
	}

	/**
	 * HTMLから作品情報を読み取る
	 * @param html 作品ページHTML
	 * @return 作品情報
	 */
	static public DmmTitleInfo getTitleInfo(InputStream html)
		throws UnsupportedEncodingException, IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(html, "utf-8"));
		DmmTitleInfo titleInfo = new DmmTitleInfo();
		DmmReadStatus readStatus = DmmReadStatus.none;
		String line;
		while ((line = reader.readLine()) != null)
		{
			if (readStatus == DmmReadStatus.none)
			{
				// 普通の場所

				if (line.indexOf("配信開始日：") >= 0)
				{
					// 配信開始日が次に続く

					readStatus = DmmReadStatus.webReleaseDate;
				}
				else if (line.indexOf("商品発売日：") >= 0)
				{
					// 商品発売日が次に続く

					readStatus = DmmReadStatus.mediaReleaseDate;
				}
				else
				{
					// 普通の場所

					Matcher matcher = patternTitle.matcher(line);
					if (matcher.matches())
					{
						// タイトルを含む行である

						titleInfo.title = matcher.group(1);
					}
				}
			}
			else if (readStatus == DmmReadStatus.webReleaseDate)
			{
				// 配信開始日

				Matcher matcher = patternDate.matcher(line);
				if (matcher.matches())
				{
					// 日付を検出

					titleInfo.webReleaseDate = matcher.group(1);
					readStatus = DmmReadStatus.none;
				}
			}
			else if (readStatus == DmmReadStatus.mediaReleaseDate)
			{
				// 商品発売日

				Matcher matcher = patternDate.matcher(line);
				if (matcher.matches())
				{
					// 日付を検出

					titleInfo.mediaReleaseDate = matcher.group(1);
					readStatus = DmmReadStatus.none;
				}
			}
		}
		html.close();

		return titleInfo;
	}
}
