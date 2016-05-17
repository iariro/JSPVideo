package kumagai.av.struts2;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 画像削除アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/deleteimage.jsp")
public class DeleteImageAction
{
	public int imageid;
	public String filename;

	/**
	 * 画像削除アクション。
	 * @return 処理結果
	 */
	@Action("deleteimage")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		ImageCollection.delete(connection, imageid);

		connection.close();

		String filePath = context.getInitParameter("AVImageFolder");

		File file = new File(filePath, filename);

		if (file.exists())
		{
			// ファイルは存在する。

			file.delete();
		}

		return "success";
	}
}
