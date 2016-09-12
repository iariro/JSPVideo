package kumagai.av.struts2;

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
			new BufferedImage(width-1, height, BufferedImage.TYPE_INT_RGB);
		java.awt.Image resizeImage2 =
			sourceImage.getScaledInstance
				(width, height, java.awt.Image.SCALE_AREA_AVERAGING);
		resizeImage.getGraphics().drawImage
			(resizeImage2, 0, 0, width, height, null);
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

	public File [] uploadfile;
	public String [] uploadfileContentType;
	public String [] uploadfileFileName;
	public int titleId;
	public String dmmUrlCid;
	public String destinationFileName;
	public ArrayList<String> uploadedFiles = new ArrayList<String>();
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
				dbUrl != null)
			{
				DriverManager.registerDriver(new SQLServerDriver());

				Connection connection = DriverManager.getConnection(dbUrl);

				// 現在の画像数を求める
				ArrayList<Image> imageFiles =
					ImageCollection.getFileNamesById
						(connection, Integer.toString(titleId));

				File subFolder = new File(folderPath, dmmUrlCid.substring(0, 1));

				if (!subFolder.exists())
				{
					new File(subFolder.getPath()).mkdir();
				}

				int imageId = imageFiles.size() + 1;

				for (int i=0 ; i<uploadfile.length ; i++)
				{
					destinationFileName =
						String.format("%s_%02d.%s", dmmUrlCid, imageId, "jpg");

					File destinationFile = new File(subFolder, destinationFileName);

					// リサイズ
					toJpegAndResize(uploadfile[i], destinationFile);

					destinationFileName =
						new File(
							dmmUrlCid.substring(0, 1),
							destinationFileName).getPath();

					ImageCollection.insert(
						connection,
						titleId,
						imageId,
						destinationFileName);

					imageId++;

					uploadedFiles.add(destinationFileName);
				}

				connection.close();

				return "success";
			}
			else
			{
				exception =
					String.format(
						"uploadfile=%s folderPath=%s dbUrl=%s",
						uploadfile,
						folderPath,
						dbUrl);

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
