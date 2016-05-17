package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 画像情報更新アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/updateimage2.jsp")
public class UpdateImage2Action
{
	public int id;
	public String comment;

	/**
	 * 画像情報更新アクション。
	 * @return 処理結果
	 */
	@Action("updateimage2")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection(
				context.getInitParameter("AVSqlserverUrl"));

		ImageCollection.updateComment(connection, id, comment);

		connection.close();

		return "success";
	}
}
