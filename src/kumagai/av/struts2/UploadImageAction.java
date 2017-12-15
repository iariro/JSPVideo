package kumagai.av.struts2;

import java.io.File;
import java.io.IOException;
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

import kumagai.av.Image;
import kumagai.av.ImageCollection;

/**
 * 画像ファイルアップロードアクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/uploadimage.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class UploadImageAction
{
	/**
	 * 画像変換テストコード。
	 * @param args [0]=変換元 [1]=変換先
	 * @throws IOException
	 */
	static public void main(String [] args)
		throws IOException
	{
		if (args.length >= 2)
		{
			File sourceFile = new File(args[0]);
			File destinationFile = new File(args[1]);
			ImageCollection.toJpegAndResize(sourceFile, destinationFile, 0, 0);
		}
	}

	/**
	 * 画像形式取得
	 * @param contentType image/pngといった文字列
	 * @return pngやjpgといった文字列
	 */
	static public String getImageType(String contentType)
	{
		String imageType = null;
		String [] contentTypeFields = contentType.split("/");

		if (contentTypeFields.length == 2)
		{
			if (contentTypeFields[0].equals("image"))
			{
				imageType = contentTypeFields[1];
			}
		}

		return imageType;
	}

	public File [] uploadfile;
	public String [] uploadfileContentType;
	public String [] uploadfileFileName;
	public int titleId;
	public String dmmUrlCid;
	public String destinationFileName;
	public ArrayList<String> uploadedFiles;
	public String uploadImageMargin;
	public String exception;

	/**
	 * 画像情報更新ページ表示アクション。
	 * @return 処理結果
	 */
	@Action("uploadimage")
	public String execute()
		throws Exception
	{
		try
		{
			ServletContext context = ServletActionContext.getServletContext();

			String folderPath = context.getInitParameter("AVImageFolder");
			String dbUrl = context.getInitParameter("AVSqlserverUrl");

			if (uploadfile != null &&
				folderPath != null &&
				dbUrl != null &&
				dmmUrlCid != null &&
				dmmUrlCid.length() > 0)
			{
				// 必要なパラメータあり

				DriverManager.registerDriver(new SQLServerDriver());

				Connection connection = DriverManager.getConnection(dbUrl);

				// 現在の画像数を求める
				ArrayList<Image> imageFiles =
					ImageCollection.getFileNamesById
						(connection, Integer.toString(titleId));

				int lastImageId = 0;
				for (Image image : imageFiles)
				{
					if (lastImageId < Integer.valueOf(image.position))
					{
						lastImageId = Integer.valueOf(image.position);
					}
				}

				int imageId = lastImageId + 1;

				uploadedFiles =
					ImageCollection.uploadFiles
						(connection, folderPath, uploadfile, dmmUrlCid, titleId, imageId, uploadImageMargin);

				connection.close();

				return "success";
			}
			else
			{
				// 必要なパラメータなし

				exception =
					String.format(
						"uploadfile=%s folderPath=%s dbUrl=%s dmmUrlCid=%s",
						uploadfile,
						folderPath,
						dbUrl,
						dmmUrlCid);
			}
		}
		catch (Exception exception)
		{
			this.exception = exception.toString();
		}

		return "error";
	}
}
