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
@Results
({
	@Result(name="success", location="/av/imagelist.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class ImageListAction
{
	public String titleid;
	public String dmmImageUrl;
	public ArrayList<Image> images;
	public String message;

	/**
	 * 画像リスト表示アクション用フォーム。
	 * @return 処理結果
	 */
	@Action("imagelist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();
		String url = context.getInitParameter("AVSqlserverUrl");

		if (url != null)
		{
			DriverManager.registerDriver(new SQLServerDriver());

			try
			{
				Connection connection = DriverManager.getConnection(url);

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
			catch (SQLServerException exception)
			{
				this.message = exception.getMessage();

				return "error";
			}
		}
		else
		{
			message = "必要なパラメータ定義が揃っていません";

			return "error";
		}
	}
}
