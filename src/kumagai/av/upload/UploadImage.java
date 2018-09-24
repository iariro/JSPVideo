package kumagai.av.uploadfile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.Image;
import kumagai.av.ImageCollection;
import kumagai.av.TitleCollection;

/**
 * 画像ファイルアップロード。
 * @author kumagai
 */
public class UploadImage
{
	/**
	 * 画像ファイルアップロード。
	 * @param args [0]=変換元 [1]=変換先
	 * @throws IOException
	 */
	static public void main(String [] args)
		throws IOException
	{
		if (args.length >= 3)
		{
			// 必要なパラメータあり

			String dbUrl = args[0];
			String titleId = args[1];
			String folderPath = args[2];
			File [] uploadfile = new File(args[3]).listFiles();
			String uploadImageMargin = args[4];

			DriverManager.registerDriver(new SQLServerDriver());

			Connection connection = DriverManager.getConnection(dbUrl);

			Title1 title = TitleCollection.getOneTitle(connection, titleId);

			// 現在の画像数を求める
			ArrayList<Image> imageFiles = ImageCollection.getFileNamesById(connection, titleId);

			int lastImageId = 0;
			for (Image image : imageFiles)
			{
				if (lastImageId < Integer.valueOf(image.position))
				{
					lastImageId = Integer.valueOf(image.position);
				}
			}

			int imageId = lastImageId + 1;

			ArrayList<String> uploadedFiles =
				ImageCollection.uploadFiles
					(connection, folderPath, uploadfile, dmmUrlCid, titleId, imageId, uploadImageMargin);

			connection.close();
		}
	}
}
