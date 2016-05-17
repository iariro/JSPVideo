package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 作品の状態履歴一覧表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/titlestatushistory.jsp")
public class TitleStatusHistoryAction
{
	public String [] dates;
	public ArrayList<WatchMemoTableEntry> watchTable;

	/**
	 * 作品の状態履歴一覧表示アクション。
	 * @return 処理結果
	 */
	@Action("titlestatushistory")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		dates = context.getInitParameter("AVHistoryPoint").split(",");

		watchTable = new WatchMemoTable(connection, dates);

		connection.close();

		for (int i=0 ; i<dates.length ; i++)
		{
			if (dates[i].equals("today"))
			{
				// todayである。

				dates[i] = "今日";
			}
		}

		return "success";
	}
}
