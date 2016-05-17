package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * タイトル追加アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/addtitle2.jsp")
public class AddTitle2Action
{
	public String title;
	public String type;
	public String memo;
	public String releaseDate;
	public int newId;
	public String dmmUrl;
	public String imageFile1;
	public String imageFile2;
	public String imageFile3;

	/**
	 * タイトル追加アクション。
	 * @return 処理結果
	 */
	@Action("addtitle2")
    public String execute()
    	throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		newId =
			TitleCollection.insert
				(connection, title, type, memo, releaseDate, dmmUrl);

		//WatchCollection.insertAsNew
		//	(connection, Integer.toString(newId), null, null, null, null);

		if (imageFile1 != null && imageFile1.length() > 0)
		{
			// 画像１あり。

			ImageCollection.insert(connection, newId, 1, imageFile1);
		}

		if (imageFile2 != null && imageFile2.length() > 0)
		{
			// 画像２あり。

			ImageCollection.insert(connection, newId, 2, imageFile2);
		}

		if (imageFile3 != null && imageFile3.length() > 0)
		{
			// 画像３あり。

			ImageCollection.insert(connection, newId, 3, imageFile3);
		}

		connection.close();

		return "success";
	}
}
