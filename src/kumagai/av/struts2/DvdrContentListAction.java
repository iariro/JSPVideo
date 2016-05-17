package kumagai.av.struts2;

import java.sql.*;
import javax.servlet.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * DVD-R内容リスト表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/dvdrcontentlist.jsp")
public class DvdrContentListAction
{
	public String diskId;
	public DvdrDubbingCollection dvdrDubbingCollection;

	/**
	 * DVD-R内容リスト表示アクション。
	 * @return 処理結果
	 */
	@Action("dvdrcontentlist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		Connection connection =
			DriverManager.getConnection(
				context.getInitParameter("AVSqlserverUrl"));

		Dvdr dvdr = DvdrCollection.getDvdrById(connection, diskId);

		String result;

		if (dvdr != null)
		{
			// 取得成功。

			dvdrDubbingCollection =
				new DvdrDubbingCollection
					(connection, diskId, dvdr.getTrackNum());

			result = "success";
		}
		else
		{
			// 取得失敗。

			System.out.println(diskId);
			result = "error";
		}

		connection.close();

		return result;
	}
}
