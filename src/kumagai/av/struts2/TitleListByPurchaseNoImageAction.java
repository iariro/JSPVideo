package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 購入日順全タイトルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/titlelistbypurchasenoimage.jsp")
public class TitleListByPurchaseNoImageAction
{
	public ArrayList<Title2> titleCollection;

	/**
	 * 購入日順全タイトルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("titlelistbypurchasenoimage")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		titleCollection = TitleCollection.getPurchasedAndNoImageList(connection);

		connection.close();

		return "success";
	}
}
