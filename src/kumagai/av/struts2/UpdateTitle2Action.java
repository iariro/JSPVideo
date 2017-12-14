package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.ImageCollection;
import kumagai.av.TitleCollection;

/**
 * タイトル情報変更アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/updatetitle2.jsp")
public class UpdateTitle2Action
{
	public String titleId;
	public String title;
	public String shortTitle;
	public String type;
	public String releaseDate;
	public String memo;
	public String dmmUrl;
	public String imageFile1;
	public String imageFile2;
	public String imageFile3;

	/**
	 * タイトル情報変更アクション。
	 * @return 処理結果
	 */
	@Action("updatetitle2")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection(
				context.getInitParameter("AVSqlserverUrl"));

		TitleCollection.update(
			connection,
			titleId,
			title,
			shortTitle,
			type,
			releaseDate,
			memo,
			dmmUrl);

		String [] imageFiles =
			new String [] { imageFile1, imageFile2, imageFile3 };

		for (int i=0 ; i<imageFiles.length ; i++)
		{
			if (imageFiles[i] != null)
			{
				// 画像の指定はある。

				if (imageFiles[i].length() > 0)
				{
					// 空文字列ではない。

					String existFileName =
						ImageCollection.getOneFileName(
							connection,
							Integer.valueOf(titleId),
							i + 1);

					if (existFileName != null)
					{
						// 画像は既存。

						ImageCollection.update
							(connection, Integer.valueOf(titleId), i + 1, imageFiles[i]);
					}
					else
					{
						// 画像は未登録。

						ImageCollection.insert(
							connection,
							Integer.valueOf(titleId),
							i + 1,
							imageFiles[i]);
					}
				}
			}
		}

		connection.close();

		return "success";
	}
}
