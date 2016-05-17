package kumagai.av;

import java.sql.*;
import java.text.*;
import java.util.regex.*;

/**
 * タイトル情報。titleテーブルに対応する内容。
 * @author kumagai
 */
public class Title1
{
	static private final SimpleDateFormat formatDate;

	// http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=miad00650/
	// http://www.dmm.co.jp/mono/dvd/-/detail/=/cid=84mkmp009/?i3_ref=search&i3_ord=4
	static public final Pattern pattern = Pattern.compile(".*cid=(.*)/.*");

	/**
	 * 日付フォーマットオブジェクト構築。
	 */
	static
	{
		formatDate = new SimpleDateFormat();
		formatDate.applyPattern("yyyy/MM/dd");
	}

	/**
	 * cidを取得
	 * @param dmmUrl DMMの作品ページURL
	 * @return cid
	 */
	static public String getCid(String dmmUrl)
	{
		Matcher matcher = pattern.matcher(dmmUrl);

		if (matcher.find())
		{
			// マッチする

			return matcher.group(1);
		}
		else
		{
			// マッチしない

			return null;
		}
	}

	/**
	 * DMMのサムネイル画像URLを取得
	 * 例：
	 * http://pics.dmm.co.jp/mono/movie/adult/84mkmp009/84mkmp009ps.jpg
	 * http://pics.dmm.co.jp/digital/video/miad00650/miad00650pl.jpg
	 * @param dmmUrl DMMの作品ページURL
	 * @return サムネイル画像URL
	 */
	static public String getDmmImageUrlPs(String dmmUrl)
	{
		if (dmmUrl.indexOf("rental") >= 0 ||
			dmmUrl.indexOf("mono") >= 0)
		{
			return
				String.format(
					"http://pics.dmm.co.jp/mono/movie/adult/%1$s/%1$sps.jpg",
					Title1.getCid(dmmUrl));
		}
		else if (dmmUrl.indexOf("digital/video") >= 0)
		{
			return
				String.format(
					"http://pics.dmm.co.jp/digital/video/%1$s/%1$sps.jpg",
					Title1.getCid(dmmUrl));
		}
		else
		{
			return null;
		}
	}

	/**
	 * DMMのフルサイズ画像URLを取得
	 * 例：
	 * http://pics.dmm.co.jp/mono/movie/adult/84mkmp009/84mkmp009pl.jpg
	 * http://pics.dmm.co.jp/digital/video/miad00650/miad00650pl.jpg
	 * @param dmmUrl DMMの作品ページURL
	 * @return フルサイズ画像URL
	 */
	static public String getDmmImageUrlPl(String dmmUrl)
	{
		if (dmmUrl.indexOf("rental") >= 0 ||
			dmmUrl.indexOf("mono") >= 0)
		{
			return
				String.format(
					"http://pics.dmm.co.jp/mono/movie/adult/%1$s/%1$spl.jpg",
					Title1.getCid(dmmUrl));
		}
		else if (dmmUrl.indexOf("digital/video") >= 0)
		{
			return
				String.format(
					"http://pics.dmm.co.jp/digital/video/%1$s/%1$spl.jpg",
					Title1.getCid(dmmUrl));
		}
		else
		{
			return null;
		}
	}

	public final String title;
	public final String shortTitle;
	public final String type;
	public final Date releaseDate;
	public final String memo;
	public final String dmmUrl;

	/**
	 * DBのレコードからオブジェクトを構築する。
	 * @param results DBのレコードオブジェクト
	 */
	public Title1(ResultSet results)
		throws SQLException
	{
		title = results.getString("title");
		shortTitle = results.getString("shorttitle");
		type = results.getString("type");
		releaseDate = results.getDate("releasedate");
		memo = results.getString("memo");
		dmmUrl = results.getString("dmmUrl");
	}

	/**
	 * リリース日取得。
	 * @return リリース日文字列
	 */
	public String getReleasedate()
	{
		if (releaseDate != null)
		{
			// リリース日はある。

			return formatDate.format(releaseDate);
		}
		else
		{
			// リリース日はない。

			return new String();
		}
	}

	/**
	 * メモ取得。
	 * @return メモ文字列
	 */
	public String getMemo()
	{
		if (memo != null)
		{
			// メモあり。

			return memo;
		}
		else
		{
			// メモあり。

			return new String();
		}
	}
}
