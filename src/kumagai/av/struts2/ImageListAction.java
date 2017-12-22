package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import kumagai.av.Image;
import kumagai.av.ImageCollection;
import kumagai.av.ImageForChangePosition;
import kumagai.av.Title1;
import kumagai.av.TitleCollection;

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
	public ArrayList<ImageForChangePosition> images;
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

				ArrayList<Image> images = ImageCollection.getFileNamesById(connection, titleid);
				this.images = new ArrayList<ImageForChangePosition>();
				for (int i=0 ; i<images.size() ; i++)
				{
					Image imageBefore = null;
					Image imageAfter = null;
					if (i > 0)
					{
						// 前あり

						imageBefore = images.get(i - 1);
					}
					if (i + 1 < images.size())
					{
						// 後ろあり

						imageAfter = images.get(i + 1);
					}

					this.images.add(
						new ImageForChangePosition(
							images.get(i),
							imageBefore != null ? imageBefore.id : null,
							imageAfter != null ? imageAfter.id : null));
				}

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
