package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * コスチューム情報追加アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/addcostume2.jsp")
public class AddCostume2Action
{
	public int titleid;
	public int imageid;
	public String costume;
	public String feature;
	public int score;

	/**
	 * コスチューム情報追加アクション。
	 * @return 処理結果
	 */
	@Action("addcostume2")
    public String execute()
    	throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		CostumeCollection.insert(
			connection,
			titleid,
			imageid,
			costume,
			feature,
			score);

		connection.close();

		return "success";
	}
}
