package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 年ごと統計表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/yearstat.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class YearStatAction
{
	public YearStat yearStat;
	public Exception exception;

	/**
	 * 年ごと統計表示アクション。
	 * @return 処理結果
	 */
	@Action("yearstat")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		try
		{
			Connection connection =
				DriverManager.getConnection
					(context.getInitParameter("AVSqlserverUrl"));

			ArrayList<YearAndCount> rental1Stat =
				new ReleaseYearAndCountCollection(connection, "rentaldate");

			ArrayList<YearAndCount> buy1Stat =
				new ReleaseYearAndCountCollection(connection, "buydate");

			ArrayList<YearAndCount> rental2Stat =
				new WatchYearAndCountCollection(connection, "rentaldate");

			ArrayList<YearAndCount> buy2Stat =
				new WatchYearAndCountCollection(connection, "buydate");

			connection.close();

			yearStat =
				new YearStat(rental1Stat, buy1Stat, rental2Stat, buy2Stat);

			return "success";
		}
		catch (SQLServerException exception)
		{
			this.exception = exception;

			return "error";
		}
	}
}
