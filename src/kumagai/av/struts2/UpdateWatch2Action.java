package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 視聴情報更新アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/updatewatch2.jsp")
public class UpdateWatch2Action
{
	public String titleId;
	public boolean exist;
	public String rentalDate;
	public String buyDate;
	public String watch;
	public String memo;

	/**
	 * 視聴情報更新アクション。
	 * @return 処理結果
	 */
	@Action("updatewatch2")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection(
				context.getInitParameter("AVSqlserverUrl"));

		exist = WatchCollection.exists(connection, titleId);

		if (exist)
		{
			// 存在する。変更。

			WatchCollection.insertAsUpdate
				(connection, titleId, rentalDate, buyDate, watch, memo);
		}
		else
		{
			// 存在しない。新規。

			WatchCollection.insertAsNew
				(connection, titleId, rentalDate, buyDate, watch, memo);
		}

		connection.close();

		return "success";
	}
}
