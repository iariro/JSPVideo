package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * DVD-R情報追加ページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/adddvdrdubbing1.jsp")
public class AddDvdrDubbing1Action
{
	public ArrayList<IdAndTitle> idAndTitleCollection;
	public String title;
	public String diskId;
	public String position;

	/**
	 * DVD-R情報追加ページ表示アクション。
	 * @return 処理結果
	 */
	@Action("adddvdrdubbing1")
    public String execute()
    	throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		title = DvdrCollection.getTitleById(connection, diskId);

		idAndTitleCollection = new IdAndTitleCollection(connection, false);

		connection.close();

		return "success";
	}
}
