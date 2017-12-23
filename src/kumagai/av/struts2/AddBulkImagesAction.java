package kumagai.av.struts2;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.DBInfo;
import kumagai.av.Image;
import kumagai.av.ImageCollection;
import kumagai.av.InvalidImageFiles;

class AddBulkImagesActionResult
{
	public ArrayList<Image> addedImage = new ArrayList<Image>();
	public ArrayList<Image> notAddImage = new ArrayList<Image>();
}

/**
 * 画像一括登録アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/addbulkimages.jsp")
public class AddBulkImagesAction
{
	static public void main(String [] args)
    	throws SQLException
	{
		String filePath = "C:/Users/kumagai/Pictures/AV/";

		new AddBulkImagesAction().addBulkImages(filePath, DBInfo.dbUrl);
	}

	public ArrayList<Image> addedImage;
	public ArrayList<Image> notAddImage;

	/**
	 * 画像一括登録アクション。
	 * @return 処理結果
	 */
	@Action("addbulkimages")
    public String execute()
    	throws SQLException
	{
		ServletContext context = ServletActionContext.getServletContext();

		String filePath = context.getInitParameter("AVImageFolder");
		String dbUrl = context.getInitParameter("AVSqlserverUrl");

		AddBulkImagesActionResult result = addBulkImages(filePath, dbUrl);

		addedImage = result.addedImage;
		notAddImage = result.addedImage;

		return "success";
	}

	/**
	 * 画像一括登録実処理。
	 * @param filePath ファイルパス
	 * @param dbUrl DB URL
	 * @return 処理結果
	 */
	public AddBulkImagesActionResult addBulkImages
		(String filePath, String dbUrl)
    	throws SQLException
	{
		ArrayList<String> files = new ArrayList<String>();

		for (String file : new File(filePath).list())
		{
			files.add(file);
		}

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection = DriverManager.getConnection(dbUrl);

		ImageCollection imageCollection = new ImageCollection(connection);
		InvalidImageFiles notRefferedImage =
			imageCollection.getNotExistFiles(filePath);

		Pattern pattern = Pattern.compile("_(\\d*)\\.");

		AddBulkImagesActionResult result = new AddBulkImagesActionResult();

		for (String fileName : notRefferedImage.notReferredFiles)
		{
			Matcher matcher = pattern.matcher(fileName);
			String num = matcher.find() ? matcher.group(1) : "";
			String fileName2 = matcher.replaceAll(".");

			for (Image image : imageCollection)
			{
				if (image.position.equals("1"))
				{
					// トップ画像分。

					if (fileName2.equalsIgnoreCase(image.fileName))
					{
						// ファイル名前半部分一致。

						String exist =
							ImageCollection.getOneFileName(
								connection,
								Integer.valueOf(image.titleId),
								Integer.valueOf(num));

						if (fileName.endsWith("JPG"))
						{
							// 拡張子は全角JPGである。

							File file1 = new File(filePath, fileName);
							fileName = fileName.replace("JPG", "jpg");
							File file2 = new File(filePath, fileName);

							file1.renameTo(file2);
						}

						Image addImage =
							new Image
								(image.id, image.titleId, num, fileName, null);

						if (exist == null)
						{
							// 存在しない。

							ImageCollection.insert(
								connection,
								Integer.valueOf(image.titleId),
								Integer.valueOf(num),
								fileName);

							result.addedImage.add(addImage);
						}
						else
						{
							// 既存。

							result.notAddImage.add(addImage);
						}
					}
				}
			}
		}

		connection.close();

		return result;
	}
}
