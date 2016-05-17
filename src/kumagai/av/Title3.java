package kumagai.av;

import java.sql.*;
import java.text.*;
import ktool.string.*;

/**
 * タイトル情報。title・image・watchテーブルに対応する内容。
 * @author kumagai
 */
public class Title3
{
	static private final SimpleDateFormat formatDate;

	/**
	 * 日付フォーマットオブジェクト構築。
	 */
	static
	{
		formatDate = new SimpleDateFormat();
		formatDate.applyPattern("yyyy/MM/dd");
	}

	public final int id;
	public final String title;
	public final String shortTitle;
	public final String media;
	public final String fileName;
	public final String dmmUrl;
	public final Date releaseDate;
	public final String rentalDate;
	public final String buyDate;
	public final String titlememo;
	public final String watchmemo;
	public final String diarydates;
	public final String reviewCount;

	/**
	 * テスト用コンストラクタ。
	 * @param i 通し番号
	 * @param dmmUrl DMM URL
	 */
	public Title3(int i, String dmmUrl)
	{
		id = i;
		title = String.format("%d", i);
		shortTitle = String.format("%d", i);
		media = "DVD";
		fileName = null;
		this.dmmUrl = dmmUrl;
		releaseDate = null;
		rentalDate = null;
		buyDate = null;
		titlememo = null;
		watchmemo = null;
		diarydates = null;
		reviewCount = null;
	}

	/**
	 * DBのレコードからオブジェクトを構築する。
	 * @param results DBのレコードオブジェクト
	 * @param diaryReviewCollection 日記レビュー日付コレクション
	 */
	public Title3
		(ResultSet results, DiaryReviewCollection diaryReviewCollection)
		throws SQLException, ParseException
	{
		id = results.getInt("id");
		title = results.getString("title");
		shortTitle = results.getString("shorttitle");
		media = results.getString("type");
		fileName = results.getString("filename");
		dmmUrl = results.getString("dmmUrl");
		releaseDate = results.getDate("releasedate");
		rentalDate = results.getString("rentaldate");
		buyDate = results.getString("buydate");
		titlememo = results.getString("titlememo");
		watchmemo = results.getString("watchmemo");

		if (diaryReviewCollection != null)
		{
			// 日記レビュー日付コレクション指定あり。

			DateCollection dateCollection =
				diaryReviewCollection.getDiaryDatesByTitleId(id);

			diarydates = dateCollection.toShortString();

			if (dateCollection.size() > 0)
			{
				// １個でもある。

				reviewCount = Integer.toString(dateCollection.size());
			}
			else
			{
				// １個でもある。

				reviewCount = null;
			}
		}
		else
		{
			// 日記レビュー日付コレクション指定なし。

			diarydates = null;
			reviewCount = null;
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format(
			"%d %s %s %s %s %s releaseDate=%s rentalDate=%s %s %s %s %s %s",
			id,
			title,
			shortTitle,
			media,
			fileName,
			dmmUrl,
			releaseDate,
			rentalDate,
			buyDate,
			titlememo,
			watchmemo,
			diarydates,
			reviewCount);
		/*
	id;
	title;
	shortTitle;
	media;
	fileName;
	dmmUrl;
	releaseDate;
	rentalDate;
	buyDate;
	titlememo;
	watchmemo;
	diarydates;
	reviewCount;
	*/
	}

	/**
	 * aタグ用リリース日を取得。
	 * @return aタグ用リリース日
	 */
	public String getRentalDate2()
	{
		try
		{
			formatDate.parse(rentalDate);

			if (rentalDate.length() == 10)
			{
				// 長さは正しい。

				return rentalDate;
			}
			else
			{
				// 長さは正しくない。

				return rentalDate.substring(0, 10);
			}
		}
		catch (ParseException exception)
		{
			return null;
		}
	}

	/**
	 * リリース日取得。
	 * @return リリース日
	 */
	public String getReleaseDateString()
	{
		if (releaseDate != null)
		{
			// リリース日あり。

			return formatDate.format(releaseDate);
		}
		else
		{
			// リリース日なし。

			return new String();
		}
	}

	/**
	 * aタグ用リリース日を取得。
	 * @return aタグ用リリース日
	 */
	public String getBuyDate2()
	{
		try
		{
			formatDate.parse(buyDate);

			if (buyDate.length() == 10)
			{
				// 長さは正しい。

				return buyDate;
			}
			else
			{
				// 長さは正しくない。

				return buyDate.substring(0, 10);
			}
		}
		catch (ParseException exception)
		{
			return null;
		}
	}

	/**
	 * 購入日が確かな日かを取得。「くらい」という文字列を含んでいないかで判断。
	 * @return true=確か／false=確かでない
	 */
	public boolean buyDateIsSure()
	{
		if (buyDate != null)
		{
			// 購入日あり。

			return buyDate.indexOf("くらい") < 0;
		}
		else
		{
			// 購入日なし。

			return true;
		}
	}

	/**
	 * 見たかどうかを取得。メモに「これだと思う」「見たと思う」を含んでいない
	 * ことで判断。
	 * @return true=確か／false=確かでない
	 */
	public boolean isSure()
	{
		if (titlememo != null)
		{
			// メモ入力あり。

			return ! titlememo.equals("これだと思う") && ! titlememo.equals("見たと思う");
		}
		else
		{
			// メモ入力なし。

			return true;
		}
	}

	/**
	 * isSureゲッター。
	 * @return isSure()に同じ
	 */
	public boolean getIsSure()
	{
		return isSure();
	}

	/**
	 * buydateIsSureゲッター。
	 * @return buydateIsSureに同じ
	 */
	public boolean getBuyDateIsSure()
	{
		return buyDateIsSure();
	}

	/**
	 * DMMのサムネイル画像URLを取得
	 * @return サムネイル画像URL
	 */
	public String getDmmImageUrlPs()
	{
		return Title1.getDmmImageUrlPs(dmmUrl);
	}

	/**
	 * DMMのフルサイズ画像URLを取得
	 * @return フルサイズ画像URL
	 */
	public String getDmmImageUrlPl()
	{
		return Title1.getDmmImageUrlPl(dmmUrl);
	}
}
