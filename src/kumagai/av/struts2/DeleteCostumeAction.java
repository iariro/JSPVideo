package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * コスチューム情報削除アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/deletecostume.jsp")
public class DeleteCostumeAction
{
	public int costumeid;

	/**
	 * コスチューム情報削除アクション。
	 * @return 処理結果
	 */
	@Action("deletecostume")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		CostumeCollection.deleteByCostumeId(connection, costumeid);

		connection.close();

		return "success";
	}
}
