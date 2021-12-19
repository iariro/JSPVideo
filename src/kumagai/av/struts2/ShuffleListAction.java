package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import ktool.datetime.DateTime;
import kumagai.av.Title3;
import kumagai.av.Title3ShuffleCollection;
import kumagai.av.TitleCollection;

/**
 * シャッフルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/shufflelist.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class ShuffleListAction
{
	public Title3ShuffleCollection titleCollectionRandom;

	public String message;
	public Exception exception;

	/**
	 * シャッフルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("shufflelist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		if (context.getInitParameter("AVSqlserverUrl") == null ||
			context.getInitParameter("AVRandomOriginDate") == null ||
			context.getInitParameter("AVRandomAdjust") == null)
		{
			message = "必要なパラメータ定義が揃っていません";

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

			titleCollectionRandom =
				new Title3ShuffleCollection
					(titleCollection, originDate, today, randomAdjust);

			return "success";
		}
		catch (SQLServerException exception)
		{
			this.exception = exception;

			return "error";
		}
	}
}
