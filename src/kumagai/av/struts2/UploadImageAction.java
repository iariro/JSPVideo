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
	public File uploadfile;
	public int titleId;
	public String dmmUrlCid;
	public String createFileName;
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
		ServletContext context = ServletActionContext.getServletContext();

		String filePath = context.getInitParameter("AVImageFolder");
		String dbUrl = context.getInitParameter("AVSqlserverUrl");

		if (uploadfile != null && filePath != null && dbUrl != null)
		{
			try
			{
				DriverManager.registerDriver(new SQLServerDriver());

				Connection connection = DriverManager.getConnection(dbUrl);

				ArrayList<Image> imageFiles =
					ImageCollection.getFileNamesById(connection, Integer.toString(titleId));

				BufferedImage sourceImage = ImageIO.read(uploadfile);
				int width = sourceImage.getWidth();
				int height = sourceImage.getHeight();
				int maxWidth = 700;
				int maxHeight = 480;

				while ((width > maxWidth) || (height > maxHeight))
				{
					width /= 2;
					height /= 2;
				}

				File destinationFile =
					new File(
						filePath,
						String.format("%s_%s.png", dmmUrlCid, imageFiles.size() + 1));

				createFileName = destinationFile.getName();

				BufferedImage image = ImageIO.read(uploadfile);
				BufferedImage thumb = new BufferedImage(width, height, image.getType());
				thumb.getGraphics().drawImage(image.getScaledInstance(width, height, java.awt.Image.SCALE_AREA_AVERAGING), 0, 0, width, height, null);
				ImageIO.write(thumb, "PNG", destinationFile);

				ImageCollection.insert(
					connection,
					titleId,
					imageFiles.size(),
					createFileName);

				message =
					String.format(
						"%s %s %s",
						titleId,
						imageFiles.size(),
						createFileName);

				connection.close();

				return "success";
			}
			finally
			{
			}
		}
		else
		{
			exception = String.format("uploadfile = %s || filePath = %s || dbUrl = %s", uploadfile, filePath, dbUrl);

			return "error";
		}
	}
}
