package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 画像リスト表示アクション用フォーム。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/imagelist.jsp")
public class ImageListAction
{
	public String titleid;
	public String dmmImageUrl;
	public ArrayList<Image> images;

	/**
	 * 画像リスト表示アクション用フォーム。
	 * @return 処理結果
	 */
	@Action("imagelist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		Title1 title = TitleCollection.getOneTitle1(connection, titleid);

		images = ImageCollection.getFileNamesById(connection, titleid);

		if (title.dmmUrl != null)
		{
			// DMM URLは存在する

			dmmImageUrl = Title1.getDmmImageUrlPl(title.dmmUrl);
		}

		connection.close();

		return "success";
	}
}
