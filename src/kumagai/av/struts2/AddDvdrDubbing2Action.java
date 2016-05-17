package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * DVD-R情報追加アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/adddvdrdubbing2.jsp")
public class AddDvdrDubbing2Action
{
	public String diskId;
	public String position;
	public String titleId;
	public String imageIndex;
	public String memo;

	/**
	 * DVD-R情報追加アクション。
	 * @return 処理結果
	 */
	@Action("adddvdrdubbing2")
    public String execute()
    	throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		DvdrDubbingCollection.insert(
			connection,
			diskId,
			position,
			titleId,
			imageIndex,
			memo);

		connection.close();

		return "success";
	}
}
