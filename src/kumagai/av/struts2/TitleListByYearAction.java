package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.interceptor.*;
import kumagai.av.*;

/**
 * リリース日順全タイトルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/titlelistbyyear.jsp")
public class TitleListByYearAction
	implements SessionAware
{
	private Map<String, Object> sessionMap;

	public int page;
	public int maxpage;
	public ArrayList<TitleCollectionAndYear> titleCollectionAndYearCollection;

	/**
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> sessionMap)
	{
		this.sessionMap = sessionMap;
	}

	/**
	 * トータルのタイトル件数取得。
	 * @return トータルのタイトル件数
	 */
	public int getTotalCount()
	{
		int count = 0;

		for (TitleCollectionAndYear titleCollection :
			titleCollectionAndYearCollection)
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
	 * リリース日順全タイトルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("titlelistbyyear")
	public String execute()
		throws Exception
	{
		titleCollectionAndYearCollection =
			new ArrayList<TitleCollectionAndYear>();

		TitleListByYearActionContext titleListByYearActionContext =
			(TitleListByYearActionContext)sessionMap.get("TitleListByYearActionContext");

		if (titleListByYearActionContext == null || page <= 0)
		{
			// １ページ目

			ServletContext context = ServletActionContext.getServletContext();

			DriverManager.registerDriver(new SQLServerDriver());

			Connection connection =
				DriverManager.getConnection
					(context.getInitParameter("AVSqlserverUrl"));

			DiaryReviewCollection diaryReviewCollection =
				new DiaryReviewCollection(connection, null);

			TitleCollection titleCollection =
				new TitleCollection(
					connection,
					false,
					false,
					"releasedate desc",
					null,
					diaryReviewCollection);

			connection.close();

			TitleCollectionAndYearCollection titleCollectionAndYearCollection =
				new TitleCollectionAndYearCollection(titleCollection);

			sessionMap.put(
				"TitleListByYearActionContext",
				new TitleListByYearActionContext
					(titleCollectionAndYearCollection));

			if (titleCollectionAndYearCollection.size() > 0)
			{
				// １個でもある

				this.titleCollectionAndYearCollection.add
					(titleCollectionAndYearCollection.get(0));
			}

			maxpage = titleCollectionAndYearCollection.size();
		}
		else
		{
			// ２ページ目以降

			if (titleListByYearActionContext.titleCollectionAndYearCollection.size() > page)
			{
				// 範囲内

				this.titleCollectionAndYearCollection.add
					(titleListByYearActionContext.titleCollectionAndYearCollection.get(page));
			}

			maxpage = titleListByYearActionContext.titleCollectionAndYearCollection.size();
		}

		page++;

		return "success";
	}
}
