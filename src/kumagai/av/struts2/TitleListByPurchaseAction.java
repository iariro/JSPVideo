package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 購入日順全タイトルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/titlelistbypurchase.jsp")
public class TitleListByPurchaseAction
{
	public ArrayList<Title3CollectionAndGroup> titleCollections;
	public String date;

	/**
	 * 件数を取得。
	 * @return 件数
	 */
	public int getSize()
	{
		int count = 0;

		for (ArrayList<Title3> titleCollection : titleCollections)
		{
			count += titleCollection.size();
		}

		return count;
	}

	/**
	 * 日記検索開始年を取得。
	 * @return 日記検索開始年
	 */
	public int getSearchDiaryStartYear()
	{
		return SearchDiaryContext.getInstance().startYear;
	}

	/**
	 * 日記検索開始月を取得。
	 * @return 日記検索開始月
	 */
	public int getSearchDiaryStartMonth()
	{
		return SearchDiaryContext.getInstance().startMonth;
	}

	/**
	 * 日記検索終了年を取得。
	 * @return 日記検索終了年
	 */
	public int getSearchDiaryEndYear()
	{
		return SearchDiaryContext.getInstance().endYear;
	}

	/**
	 * 日記検索終了月を取得。
	 * @return 日記検索終了月
	 */
	public int getSearchDiaryEndMonth()
	{
		return SearchDiaryContext.getInstance().endMonth;
	}

	/**
	 * 購入日順全タイトルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("titlelistbypurchase")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		DiaryReviewCollection diaryReviewCollection =
			new DiaryReviewCollection(connection, null);

		ArrayList<Title3> titleCollection =
			new TitleCollection(
				connection,
				true,
				false,
				"w1.buydate desc",
				date,
				diaryReviewCollection);

		connection.close();

		ArrayList<Title3> titleCollectionDispose0 = new ArrayList<Title3>();
		ArrayList<Title3> titleCollectionDispose1 = new ArrayList<Title3>();
		ArrayList<Title3> titleCollectionDispose2 = new ArrayList<Title3>();
		ArrayList<Title3> titleCollectionDispose3 = new ArrayList<Title3>();

		for (int i=titleCollection.size()-1 ; i >=0 ; i--)
		{
			if (titleCollection.get(i).watchmemo != null)
			{
				// メモ欄指定あり。

				if (titleCollection.get(i).watchmemo.equals("追加予定"))
				{
					// 追加予定。

					titleCollectionDispose0.add(titleCollection.get(i));
					titleCollection.remove(i);
				}
				else if (titleCollection.get(i).watchmemo.equals("除外予定"))
				{
					// 除外予定。

					titleCollectionDispose1.add(titleCollection.get(i));
					titleCollection.remove(i);
				}
				else if (titleCollection.get(i).watchmemo.equals("除外"))
				{
					// 除外。

					titleCollectionDispose2.add(titleCollection.get(i));
					titleCollection.remove(i);
				}
				else if (titleCollection.get(i).watchmemo.equals("売却"))
				{
					// 売却。

					titleCollectionDispose3.add(titleCollection.get(i));
					titleCollection.remove(i);
				}
			}
		}

		titleCollections = new ArrayList<Title3CollectionAndGroup>();
		titleCollections.add
			(new Title3CollectionAndGroup(titleCollectionDispose0, "追加予定"));
		titleCollections.add
			(new Title3CollectionAndGroup(titleCollection, "購入"));
		titleCollections.add
			(new Title3CollectionAndGroup(titleCollectionDispose1, "除外予定"));
		titleCollections.add
			(new Title3CollectionAndGroup(titleCollectionDispose2, "除外"));
		titleCollections.add
			(new Title3CollectionAndGroup(titleCollectionDispose3, "手放した"));

		return "success";
	}
}
