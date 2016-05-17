package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 入力順全タイトルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/titlelistbyinput.jsp")
public class TitleListByInputAction
{
	public ArrayList<Title3> titleCollection;
	public String date;

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
	 * 入力順全タイトルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("titlelistbyinput")
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
				"updatedate desc",
				date,
				diaryReviewCollection);

		connection.close();

		return "success";
	}
}
