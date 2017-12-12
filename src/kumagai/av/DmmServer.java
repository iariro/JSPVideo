package kumagai.av;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DMMの作品情報Webページ
 */
public class DmmServer
{
	static private final String empty = "";
	static private final Pattern patternTitle = Pattern.compile("<h1 id=\"title\" class=\"item fn\">([^<]*).*");

	static public void main(String [] args)
		throws IOException
	{
		InputStream in = new FileInputStream(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		String line;
		while ((line = br.readLine()) != null)
		{
			Matcher matcher = patternTitle.matcher(line);
			if (matcher.matches())
			{
				System.out.println(matcher.group(1));
			}
		}
		in.close();
	}

	/**
	 * pastorchestra.htmの内容からURLと楽団名を取得
	 * @param lines HTML行データ
	 * @return URLと楽団名リスト
	 */
	/*static public ArrayList<PastConcertInfo> getUrls(String[] lines)
	{
		ArrayList<PastConcertInfo> urls = new ArrayList<PastConcertInfo>();
		String url = null;
		String orchestra = null;
		for (String line : lines)
		{
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches())
			{
				// <a>行

				url = matcher.group(1);
			}
			else
			{
				// それ以外

				line = line.replaceAll("<.+?>", empty);
				if (url != null)
				{
					// URL確定

					line = line.trim();
					if (line.length() > 0)
					{
						if (orchestra == null)
						{
							// 楽団名がまだ→楽団名

							orchestra = line;
						}
						else
						{
							// 楽団名あり→日付

							urls.add(new PastConcertInfo(null, orchestra, url, line, null));
							url = null;
							orchestra = null;
						}
					}
				}
			}
		}
		return urls;
	}*/

	/**
	 * HTML読み込み。
	 * @param filename ファイル名
	 */
	/*static public String [] getHtmlLines(String filename) throws IOException
	{
		URL url = new URL(String.format(urlBase, filename));

		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while ((line = br.readLine()) != null)
		{
			lines.add(line);
		}

		in.close();

		return lines.toArray(new String [] {});
	}*/
}
