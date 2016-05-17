package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 日記レビュー追加アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/adddiaryreview.jsp")
public class AddDiaryReviewAction
{
	public String titleId;
	public String diaryDate;

	/**
	 * 日記レビュー追加アクション。
	 * @return 処理結果
	 */
	@Action("adddiaryreview")
    public String execute()
    	throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		DiaryReviewCollection.insert(connection, titleId, diaryDate);

		connection.close();

		return "success";
	}
}
