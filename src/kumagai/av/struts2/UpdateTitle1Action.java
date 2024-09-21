package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.Image;
import kumagai.av.ImageCollection;
import kumagai.av.Title1;
import kumagai.av.TitleCollection;

/**
 * タイトル情報更新ページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/updatetitle1.jsp")
public class UpdateTitle1Action
{
	public String titleId;
	public String title;
	public String shortTitle;
	public String type;
	public String releaseDate;
	public String memo;
	public String dmmUrl;
	public String dmmUrlCid;
	public boolean useDmmTopImage;
	public String imageFile1;
	public String imageFile2;
	public String imageFile3;
	public int imageFileCount;

	/**
	 * タイトル情報更新ページ表示アクション。
	 * @return 処理結果
	 */
	@Action("updatetitle1")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		Title1 title2 = TitleCollection.getOneTitle1(connection, titleId);

		ArrayList<Image> imageFiles =
			ImageCollection.getFileNamesById(connection, titleId);

		connection.close();

		imageFileCount = imageFiles.size();

		if (title2 != null)
		{
			// タイトル情報は取得できた。

			title = title2.title;
			shortTitle = title2.shortTitle;
			type = title2.type;
			releaseDate = title2.getReleasedate();
			memo = title2.getMemo();
			dmmUrl = title2.dmmUrl;
			if (dmmUrl != null)
			{
				dmmUrlCid = Title1.getCid(dmmUrl);
			}
			useDmmTopImage = title2.useDmmTopImage;

			if (imageFiles.size() >= 1)
			{
				// 画像は１個はある。

				imageFile1 = imageFiles.get(0).fileName;
			}

			if (imageFiles.size() >= 2)
			{
				// 画像は２個はある。

				imageFile2 = imageFiles.get(1).fileName;
			}

			if (imageFiles.size() >= 3)
			{
				// 画像は３個はある。

				imageFile3 = imageFiles.get(2).fileName;
			}
		}

		return "success";
	}
}
