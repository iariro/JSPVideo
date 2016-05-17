package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * タイトル順全タイトルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/titlelistbytitle.jsp")
public class TitleListByTitleAction
{
	public ArrayList<Title3> titleCollection;

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
	 * タイトル順全タイトルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("titlelistbytitle")
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

		titleCollection =
			new TitleCollection(
				connection,
				false,
				false,
				"title",
				null,
				diaryReviewCollection);

		connection.close();

		return "success";
	}
}
