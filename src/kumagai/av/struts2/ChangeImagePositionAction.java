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
import com.microsoft.sqlserver.jdbc.SQLServerException;

import kumagai.av.ImageCollection;

/**
 * 画像順番変更アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/changeimageposition.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class ChangeImagePositionAction
{
	public Integer imageId1;
	public Integer imageId2;
	public String message;

	/**
	 * 画像順番変更アクション。
	 * @return 処理結果
	 */
	@Action("changeimageposition")
	public String execute()
		throws Exception
	{
		if (imageId1 == null || imageId2 == null)
		{
			message = "終端であり交換不能です";
			return "error";
		}

		ServletContext context = ServletActionContext.getServletContext();
		String url = context.getInitParameter("AVSqlserverUrl");

		if (url != null)
		{
			DriverManager.registerDriver(new SQLServerDriver());

			try
			{
				Connection connection = DriverManager.getConnection(url);
				ImageCollection.swapImagePosition(connection, imageId1, imageId2);
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
