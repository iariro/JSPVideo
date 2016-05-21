package kumagai.av.struts2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.imageio.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

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
	static private final int maxWidth = 700;
	static private final int maxHeight = 480;

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
			toJpegAndResize(sourceFile, destinationFile);
		}
	}

	/**
	 * PNGからjPEGへの変換とリサイズ。
	 * @param sourceImage
	 * @param width リサイズする幅
	 * @param height リサイズする高さ
	 * @return リサイズした画像
	 */
	static void toJpegAndResize(File sourceFile, File destinationFile)
		throws IOException
	{
		BufferedImage sourceImage = ImageIO.read(sourceFile);
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();

		while ((width > maxWidth) || (height > maxHeight))
		{
			width /= 2;
			height /= 2;
		}

		BufferedImage resizeImage =
			new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D off = resizeImage.createGraphics();
		off.drawImage(sourceImage, 0, 0, Color.WHITE, null);
		ImageIO.write(resizeImage, "jpg", destinationFile);
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

	public File uploadfile;
	public String uploadfileContentType;
	public String uploadfileFileName;
	public int titleId;
	public String dmmUrlCid;
	public String destinationFileName;
	public String message;
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

			// 画像形式取得
			String imageType = getImageType(uploadfileContentType);

			if (uploadfile != null &&
				folderPath != null &&
				dbUrl != null &&
				imageType != null)
			{
				DriverManager.registerDriver(new SQLServerDriver());

				Connection connection = DriverManager.getConnection(dbUrl);

				// 現在の画像数を求める
				ArrayList<Image> imageFiles =
					ImageCollection.getFileNamesById
						(connection, Integer.toString(titleId));

				// リサイズ
				File destinationFile =
					new File(
						folderPath,
						String.format(
							"%s_%02d.%s",
							dmmUrlCid,
							imageFiles.size() + 1,
							imageType));

				toJpegAndResize(uploadfile, destinationFile);

				ImageCollection.insert(
					connection,
					titleId,
					imageFiles.size(),
					destinationFileName);

				message =
					String.format(
						"%s %s %s",
						titleId,
						imageFiles.size(),
						destinationFileName);

				connection.close();

				return "success";
			}
			else
			{
				exception =
					String.format(
						"uploadfile=%s folderPath=%s dbUrl=%s imageType=%s",
						uploadfile,
						folderPath,
						dbUrl,
						imageType);

				return "error";
			}
		}
		catch (Exception exception)
		{
			this.exception = exception.getMessage();

			return "error";
		}
	}
}
