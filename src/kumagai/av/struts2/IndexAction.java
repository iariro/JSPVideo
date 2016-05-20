package kumagai.av.struts2;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import ktool.datetime.*;
import kumagai.av.*;

/**
 * トップページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/index.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class IndexAction
{
	static private final SimpleDateFormat formatDate1;
	static private final SimpleDateFormat formatDate2;

	/**
	 * 日付書式オブジェクトを構築。
	 */
	static
	{
		formatDate1 = new SimpleDateFormat();
		formatDate2 = new SimpleDateFormat();

		formatDate1.applyPattern("yyyy/MM/dd");
		formatDate2.applyPattern("yyyy/MM/dd(E)");
	}

	public Title3 title1;
	public Title3 title2;
	public Title3 title3;
	public String date1;
	public String date2;
	public Exception exception;

	/**
	 * トップページ表示アクション。
	 * @return 処理結果
	 */
	@Action("index")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		if (context.getInitParameter("AVSqlserverUrl") == null ||
			context.getInitParameter("AVRandomOriginDate") == null ||
			context.getInitParameter("AVRandomAdjust") == null)
		{
			this.exception = "必要なパラメータ定義が揃っていません";

			return "error";
		}

		DriverManager.registerDriver(new SQLServerDriver());

		try
		{
			Connection connection =
				DriverManager.getConnection
					(context.getInitParameter("AVSqlserverUrl"));

			ArrayList<Title3> titleCollection =
				new TitleCollection(
					connection,
					true,
					true,
					"buydate desc, indexinday",
					null,
					null);

			connection.close();

			String originDateString =
				context.getInitParameter("AVRandomOriginDate");
			int randomAdjust =
				Integer.valueOf(context.getInitParameter("AVRandomAdjust"));

			DateTime originDate = DateTime.parseDateString(originDateString);

			DateTime today = new DateTime();

			Title3ShuffleCollection titleCollectionRandom =
				new Title3ShuffleCollection
					(titleCollection, originDate, today, randomAdjust);

			date1 = formatDate1.format(today.getDate());
			date2 = formatDate2.format(today.getDate());

			title1 = titleCollectionRandom.getTitle1();
			title2 = titleCollectionRandom.getTitle2();
			title3 = titleCollectionRandom.getTitle3();

			return "success";
		}
		catch (SQLServerException exception)
		{
			this.exception = exception;

			return "error";
		}
	}
}
