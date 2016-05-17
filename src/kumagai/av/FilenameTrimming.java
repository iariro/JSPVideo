package kumagai.av;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

/**
 * 画像ファイルの連番のゼロパディング。
 */
public class FilenameTrimming
{
	static private final Pattern pattern = Pattern.compile("(.*)_(\\d*)\\.");

	/**
	 * @param args 未使用
	 */
	public static void main(String[] args)
		throws SQLException
	{
		Connection connection =
			DriverManager.getConnection(
				"jdbc:sqlserver://localhost:2144;DatabaseName=AV;User=sa;Password=p@ssw0rd;");

		String filePath = "C:/Users/kumagai/Pictures/AV/";

		ImageCollection images = new ImageCollection(connection);

		HashMap<String, Integer> imageCountPerTitle =
			new HashMap<String, Integer>();

		// タイトルごとの画像数をカウント。
		for (Image image : images)
		{
			Matcher matcher = pattern.matcher(image.fileName);

			if (matcher.find())
			{
				// 条件に合致する。

				String filenameZenhan = matcher.group(1);

				if (imageCountPerTitle.containsKey(filenameZenhan))
				{
					// 既出。

					imageCountPerTitle.put(
						filenameZenhan,
						imageCountPerTitle.get(filenameZenhan) + 1);
				}
				else
				{
					// 新規。

					imageCountPerTitle.put(filenameZenhan, 1);
				}
			}
		}

		for (Image image : images)
		{
			Matcher matcher = pattern.matcher(image.fileName);

			if (matcher.find())
			{
				// 条件に合致する。

				String filenameZenhan = matcher.group(1);
				String position2String = matcher.group(2);

				int position1 = Integer.valueOf(image.position);
				int position2 = Integer.valueOf(position2String);

				if (position1 == position2 &&
					position1 >= 2 &&
					position2String.length() == 1 &&
					imageCountPerTitle.get(filenameZenhan) >= 3)
				{
					// ファイル名のインデックス値がDBのインデックス値と一致し、
					// ２番以降で、１桁で、同一作品で３番まではある。

					String filename2 = matcher.replaceAll("$1_0$2\\.");

					File fileFrom =
						new File(filePath, image.fileName);
					File fileTo =
						new File(filePath, filename2);

					fileFrom.renameTo(fileTo);

					ImageCollection.update(
						connection,
						image.titleId,
						Integer.valueOf(image.position),
						filename2);

					System.out.printf("%s -> %s\n", image.fileName, filename2);
				}
			}
		}

		connection.close();
	}
}
