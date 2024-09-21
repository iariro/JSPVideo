package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.TitleCollection;
import kumagai.av.WatchCollection;

/**
 * DMMタイトル追加アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/adddmmtitle3.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class AddDmmTitle3Action
{
	public String title;
	public String type;
	public String memo;
	public String releaseDate;
	public String dmmUrl;
	public boolean useDmmTopImage;
	public String rentalDate;
	public String buyDate;

	public int titleId;
	public String message;

	/**
	 * DMMタイトル追加アクション。
	 * @return 処理結果
	 */
	@Action("adddmmtitle3")
    public String execute()
    	throws Exception
	{
		try
		{
			ServletContext context = ServletActionContext.getServletContext();

			String dbUrl = context.getInitParameter("AVSqlserverUrl");
			if (dbUrl != null)
			{
				// 必要なパラメータあり

				DriverManager.registerDriver(new SQLServerDriver());
				Connection connection = DriverManager.getConnection(dbUrl);

				// タイトル追加
				titleId = TitleCollection.insert(connection, title, type, memo, releaseDate, dmmUrl, useDmmTopImage);

				// 視聴情報追加
				WatchCollection.insertAsNew(connection, titleId, rentalDate, buyDate, null, memo);

				connection.close();

				return "success";
			}
		}
		catch (Exception exception)
		{
			message = exception.getMessage();
		}

		return "error";
	}
}
