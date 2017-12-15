package kumagai.av.struts2;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.CostumeCollection;
import kumagai.av.ImageCollection;

/**
 * 画像削除アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/deleteimage.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class DeleteImageAction
{
	public int imageid;
	public String filename;

	public String message;

	/**
	 * 画像削除アクション。
	 * @return 処理結果
	 */
	@Action("deleteimage")
	public String execute()
		throws Exception
	{
		try
		{
			ServletContext context = ServletActionContext.getServletContext();

			String dbUrl = context.getInitParameter("AVSqlserverUrl");
			String filePath = context.getInitParameter("AVImageFolder");
			if (dbUrl != null && filePath != null)
			{
				// 必要なパラメータはある

				DriverManager.registerDriver(new SQLServerDriver());

				Connection connection = DriverManager.getConnection(dbUrl);
				ImageCollection.delete(connection, imageid);
				CostumeCollection.deleteByImageId(connection, imageid);
				connection.close();

				File file = new File(filePath, filename);
				if (file.exists())
				{
					// ファイルは存在する。

					file.delete();
				}

				return "success";
			}
			else
			{
				// 必要なパラメータはない

				message = "AVSqlserverUrl and/or AVImageFolder定義なし";
			}
		}
		catch (Exception exception)
		{
			message = exception.getMessage();
		}

		return "error";
	}
}
