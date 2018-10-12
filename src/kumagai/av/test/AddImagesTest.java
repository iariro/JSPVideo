package kumagai.av.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.DBInfo;
import kumagai.av.Image;
import kumagai.av.ImageCollection;
import kumagai.av.InvalidImageFiles;
import kumagai.av.RecursiveFilePathArray;

public class AddImagesTest
{
	static public final String filePath = "C:/Users/kumagai/Pictures/AV";

	public static void main(String[] args)
		throws SQLException
	{
		ArrayList<String> files = new ArrayList<String>();

		for (String file : new File(filePath).list())
		{
			files.add(file);
		}

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		ImageCollection imageCollection = new ImageCollection(connection);
		InvalidImageFiles notRefferedImage =
			imageCollection.getNotExistFiles(filePath, new RecursiveFilePathArray(filePath));

		Pattern pattern = Pattern.compile("_(\\d*)\\.");

		for (String fileName : notRefferedImage.notReferredFiles)
		{
			Matcher matcher = pattern.matcher(fileName);
			String num = matcher.find() ? matcher.group(1) : "";
			String fileName2 = matcher.replaceAll(".");

			System.out.println(fileName2);

			for (Image image : imageCollection)
			{
				if (image.position.equals("1"))
				{
					if (fileName2.equals(image.fileName))
					{
						String exist =
							ImageCollection.getOneFileName(
								connection,
								Integer.valueOf(image.titleId),
								Integer.valueOf(num));

						System.out.printf(
							"%s %s %s %s\n",
							image.titleId,
							fileName2,
							num,
							exist);

						/*
						ImageCollection.insert(
							connection,
							Integer.valueOf(image.titleId),
							Integer.valueOf(num),
							fileName2);
						*/
					}
				}
			}
		}
	}
}
