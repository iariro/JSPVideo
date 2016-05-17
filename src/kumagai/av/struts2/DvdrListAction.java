package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * DVD-Rリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/dvdrlist.jsp")
public class DvdrListAction
{
	public DvdrCollection dvdrCollection;

	/**
	 * DVD-Rリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("dvdrlist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		dvdrCollection = new DvdrCollection(connection);

		connection.close();

		return "success";
	}
}
