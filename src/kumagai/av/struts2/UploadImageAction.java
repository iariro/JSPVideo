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
			toJpegAndResize(sourceFile, destinationFile, 0, 0);
		}
	}

	/**
	 * PNGからjPEGへの変換とリサイズ。
	 * @param sourceFile 元ファイル
	 * @param destinationFile 出力ファイル
	 * @param marginX 左右の余白
	 * @param marginY 上下の余白
	 */
	static void toJpegAndResize(File sourceFile, File destinationFile, int marginX, int marginY)
		throws IOException
	{
		BufferedImage sourceImage = ImageIO.read(sourceFile);
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		int width2 = width - marginX * 2;
		int height2 = height - marginY * 2;

		while ((width2 > maxWidth) || (height2 > maxHeight))
		{
			width /= 2;
			height /= 2;
			width2 /= 2;
			height2 /= 2;
			marginX /= 2;
			marginY /= 2;
		}

		BufferedImage resizeImage =
			new BufferedImage(width2-1, height2, BufferedImage.TYPE_INT_RGB);
		java.awt.Image resizeImage2 =
			sourceImage.getScaledInstance
				(width, height, java.awt.Image.SCALE_AREA_AVERAGING);
		resizeImage.getGraphics().drawImage
			(resizeImage2, -marginX, -marginY, width, height, null);
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

				int lastImageId = 0;
				for (Image image : imageFiles)
				{
					if (lastImageId < Integer.valueOf(image.position))
					{
						lastImageId = Integer.valueOf(image.position);
					}
				}

				int imageId = lastImageId + 1;

				// 余白幅セット
				int uploadImageMarginX = 0;
				int uploadImageMarginY = 0;

				if (uploadImageMargin != null)
				{
					// 指定あり

					String [] uploadImageMargin2 = uploadImageMargin.split(",");

					if (uploadImageMargin2.length == 2)
					{
						// 値は２つ

						uploadImageMarginX = Integer.valueOf(uploadImageMargin2[0]);
						uploadImageMarginY = Integer.valueOf(uploadImageMargin2[1]);
					}
				}

				for (int i=0 ; i<uploadfile.length ; i++)
				{
					destinationFileName =
						String.format("%s_%02d.%s", dmmUrlCid, imageId, "jpg");

					File destinationFile = new File(subFolder, destinationFileName);

					// リサイズ
					toJpegAndResize(
						uploadfile[i],
						destinationFile,
						uploadImageMarginX,
						uploadImageMarginY);

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
						"uploadfile=%s folderPath=%s dbUrl=%s dmmUrlCid=%s",
						uploadfile,
						folderPath,
						dbUrl,
						dmmUrlCid);

				return "error";
			}
		}
		catch (Exception exception)
		{
			this.exception = exception.toString();

			return "error";
		}
	}
}
