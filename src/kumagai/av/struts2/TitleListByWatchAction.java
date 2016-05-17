package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 注目作品全タイトルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/titlelistbywatch.jsp")
public class TitleListByWatchAction
{
	public ArrayList<Title3> titleCollection;

	/**
	 * コレクションのサイズ取得。
	 * @return コレクションのサイズ
	 */
	public int getSize()
	{
		return titleCollection.size();
	}

	/**
	 * 注目作品全タイトルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("titlelistbywatch")
    public String execute()
    	throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		titleCollection = TitleCollection.getWatchList(connection);

		connection.close();

		return "success";
	}
}
