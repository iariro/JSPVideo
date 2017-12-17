package kumagai.av;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;

import ktool.datetime.DateTime;

/**
 * 画像情報コレクション。
 * @author kumagai
 */
public class ImageCollection
	extends ArrayList<Image>
{
	public static final int maxWidth = 700;
	public static final int maxHeight = 480;

	/**
	 * 画像情報を追加。
	 * @param connection DB接続オブジェクト
	 * @param newId 新しいタイトルID
	 * @param position シーン
	 * @param imageFile 画像ファイルパス
	 */
	static public void insert
		(Connection connection, int newId, int position, String imageFile)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into image (titleid, position, filename) values (?, ?, ?)");

		statement.setInt(1, newId);
		statement.setInt(2, position);
		statement.setString(3, imageFile);

		statement.executeUpdate();
	}

	/**
	 * 画像情報を更新。
	 * @param connection DB接続オブジェクト
	 * @param titleId タイトルID
	 * @param position 画像インデックス
	 * @param fileName 画像ファイル名
	 */
	static public void update
		(Connection connection, int titleId, int position, String fileName)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"update image set filename=? where titleid=? and position=?");

		statement.setString(1, fileName);
		statement.setInt(2, titleId);
		statement.setInt(3, position);

		statement.executeUpdate();
	}

	/**
	 * 画像コメント情報を更新。
	 * @param connection DB接続オブジェクト
	 * @param id ID
	 * @param comment コメント
	 */
	static public void updateComment
		(Connection connection, int id, String comment)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("update image set comment=? where id=?");

		statement.setString(1, comment);
		statement.setInt(2, id);

		statement.executeUpdate();
	}

	/**
	 * 画像削除。
	 * @param connection DB接続オブジェクト
	 * @param imageId 画像ID
	 */
	static public void delete(Connection connection, int imageId)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement("delete image where id=?");

		statement.setInt(1, imageId);

		statement.executeUpdate();
	}

	/**
	 * 指定のタイトルID・画像インデックスの画像ファイル名を取得する。
	 * @param connection DB接続オブジェクト
	 * @param titleId タイトルID
	 * @param imageIndex 画像インデックス
	 * @return ファイル名
	 */
	static public String getOneFileName
		(Connection connection, int titleId, int imageIndex)
		throws SQLException
	{
		String sql =
			"select filename from image where titleid=? and image.position=?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, titleId);
		statement.setInt(2, imageIndex);

		ResultSet results = statement.executeQuery();

		String filename = null;

		if (results.next())
		{
			// シーク成功。

			filename = results.getString(1);
		}

		results.close();
		statement.close();

		return filename;
	}

	/**
	 * 画像の重複参照エントリを取得。
	 * @param connection DB接続オブジェクト
	 * @return 画像の重複参照エントリ
	 */
	static public ArrayList<FilenameAndCount> getDuplicateFile
		(Connection connection) throws SQLException
	{
		String sql =
			"select filename, count(filename) from image group by filename having count(filename) > 1 order by count(filename) desc";

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		ArrayList<FilenameAndCount> duplicateFiles =
			new ArrayList<FilenameAndCount>();

		while (results.next())
		{
			String fileName = results.getString(1);
			int count = results.getInt(2);

			duplicateFiles.add(new FilenameAndCount(fileName, count));
		}

		results.close();
		statement.close();

		return duplicateFiles;
	}

	/**
	 * 指定のタイトルID分の画像ファイル名を取得。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトルID
	 * @return 画像ファイル名コレクション
	 */
	static public ArrayList<Image> getFileNamesById
		(Connection connection, String titleid)
		throws SQLException
	{
		String sql =
			"select id, titleid, position, filename, comment from image where titleid=? order by position";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, titleid);

		ResultSet results = statement.executeQuery();

		ArrayList<Image> images = new ArrayList<Image>();

		while (results.next())
		{
			images.add(new Image(results));
		}

		results.close();
		statement.close();

		// コスチューム情報
		CostumeCollection costumeCollection =
			new CostumeCollection(connection, Integer.valueOf(titleid));
		for (Image image : images)
		{
			for (Costume costume : costumeCollection)
			{
				if (costume.imageId == image.id)
				{
					// 対象の画像に対するコスチューム情報である

					image.costumes.add(costume);
				}
			}
		}

		return images;
	}

	/**
	 * 日付不明タイトルを取得。1/1のものを日付不明として扱う。
	 * @param connection DB接続オブジェクト
	 * @return タイトルのコレクション
	 */
	static public ArrayList<String> getHidukeFumeiTitle(Connection connection)
		throws SQLException
	{
		String sql =
			"select title from title where month(releasedate) = 1 and day(releasedate) = 1";

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		ArrayList<String> hidukeFumeiTitle = new ArrayList<String>();

		while (results.next())
		{
			String title = results.getString(1);

			hidukeFumeiTitle.add(title);
		}

		results.close();
		statement.close();

		return hidukeFumeiTitle;
	}

	/**
	 * 参照されていない画像情報を取得。
	 * @param connection DB接続オブジェクト
	 * @return 参照されていない画像情報
	 */
	static public ArrayList<Image> getNoRefferedImage(Connection connection)
		throws SQLException
	{
		String sql =
			"select id, titleid, position, filename, comment from image where titleid not in (select id from title)";

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		ArrayList<Image> images = new ArrayList<Image>();

		while (results.next())
		{
			images.add(new Image(results));
		}

		results.close();
		statement.close();

		return images;
	}

	/**
	 * 画像位置情報を入れ替え
	 * @param connection DB接続オブジェクト
	 * @param position1 位置１
	 * @param position2 位置２
	 */
	static public void swapImagePosition(Connection connection, int position1, int position2)
		throws SQLException
	{
		String sql =
			"update image SET position = CASE " +
			"WHEN id=? THEN (SELECT position FROM image WHERE id=?) " +
			"WHEN id=? THEN (SELECT position FROM image WHERE id=?) " +
			"ELSE position END " +
			"WHERE id in (?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, position1);
		statement.setInt(2, position2);
		statement.setInt(3, position2);
		statement.setInt(4, position1);
		statement.setInt(5, position1);
		statement.setInt(6, position2);
		ResultSet results = statement.executeQuery();
		results.close();
		statement.close();
	}

	/**
	 * 画像ファイルの更新日時から最新の画像ファイルを取得
	 * @param filePath 画像フォルダ
	 * @return 画像リスト
	 */
	static public ArrayList<FileAndDatetime> getNewImageFilst(String filePath)
	{
		ArrayList<String> files = new RecursiveFilePathArray(filePath);

		ArrayList<FileAndDatetime> fileAndDatetimes = new ArrayList<FileAndDatetime>();
		for (String file : files)
		{
			if (file.endsWith("jpg") || file.endsWith("jpeg"))
			{
				// JPEGファイル

				long updateDate = new File(filePath, file).lastModified();
				fileAndDatetimes.add(new FileAndDatetime(file, new DateTime(updateDate)));
			}
		}

		Collections.sort(
			fileAndDatetimes,
			new Comparator<FileAndDatetime>()
			{
				public int compare(FileAndDatetime item1, FileAndDatetime item2)
				{
					return - item1.updatedate.compareTo(item2.updatedate);
				}
			});

		ArrayList<FileAndDatetime> fileAndDatetimes2 = new ArrayList<FileAndDatetime>();
		for (int i=0 ; i<fileAndDatetimes.size() ; i++)
		{
			fileAndDatetimes2.add(fileAndDatetimes.get(i));
		}

		return fileAndDatetimes2;
	}

	/**
	 * 全画像情報のコレクションを構築。
	 * @param connection DB接続オブジェクト
	 */
	public ImageCollection(Connection connection)
		throws SQLException
	{
		String sql = "select id, titleid, position, filename, comment from image";

		Statement statement = connection.createStatement();

		ResultSet results = statement.executeQuery(sql);

		while (results.next())
		{
			add(new Image(results));
		}

		results.close();
		statement.close();
	}

	/**
	 * 存在しないファイルへの参照を取得する。
	 * @param filePath 画像フォルダパス
	 * @return 存在しないファイル参照コレクション
	 */
	public InvalidImageFiles getNotExistFiles(String filePath)
	{
		InvalidImageFiles invalidImageFiles = new InvalidImageFiles();

		ArrayList<String> files = new RecursiveFilePathArray(filePath);

		for (String file : files)
		{
			invalidImageFiles.notReferredFiles.add(file);
		}

		for (Image image : this)
		{
			if (! invalidImageFiles.notReferredFiles.contains(image.fileName))
			{
				// 存在しないファイルである。

				invalidImageFiles.notExistFiles.add(
					String.format(
						"%s:%s:「%s」",
						image.titleId,
						image.position,
						image.fileName));
			}
		}

		for (Image image : this)
		{
			if (invalidImageFiles.notReferredFiles.contains(image.fileName))
			{
				// 存在するファイルである。

				invalidImageFiles.notReferredFiles.remove(image.fileName);
			}
		}

		return invalidImageFiles;
	}

	/**
	 * PNGからjPEGへの変換とリサイズ。
	 * @param sourceFile 元ファイル
	 * @param destinationFile 出力ファイル
	 * @param marginX 左右の余白
	 * @param marginY 上下の余白
	 */
	public static void toJpegAndResize(File sourceFile, File destinationFile, int marginX, int marginY)
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
	 * 画像のアップロード
	 * @param connection DB接続オブジェクト
	 * @param subFolder 画像格納フォルダ
	 * @param uploadfiles 画像の配列
	 * @param dmmUrlCid DMM上のタイトルCID
	 * @param titleId タイトルID
	 * @param imageId １個目の画像ID
	 * @param uploadImageMargin 画像切り出し座標X,Y
	 * @return アップロードした画像の配列
	 */
	static public ArrayList<String> uploadFiles(Connection connection, String folderPath, File [] uploadfiles, String dmmUrlCid, int titleId, int imageId, String uploadImageMargin)
		throws IOException, SQLException
	{
		ArrayList<String> uploadedFiles = new ArrayList<String>();

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

		File subFolder = new File(folderPath, dmmUrlCid.substring(0, 1));
		if (!subFolder.exists())
		{
			// 存在しない

			new File(subFolder.getPath()).mkdir();
		}

		for (int i=0 ; i<uploadfiles.length ; i++)
		{
			String destinationFileName =
				String.format("%s_%02d.%s", dmmUrlCid, imageId, "jpg");

			File destinationFile = new File(subFolder, destinationFileName);

			// リサイズ
			ImageCollection.toJpegAndResize(
				uploadfiles[i],
				destinationFile,
				uploadImageMarginX,
				uploadImageMarginY);

			destinationFileName =
				new File(
					dmmUrlCid.substring(0, 1),
					destinationFileName).getPath();

			if (connection != null)
			{
				insert(
					connection,
					titleId,
					imageId,
					destinationFileName);
			}

			imageId++;

			uploadedFiles.add(destinationFileName);
		}

		return uploadedFiles;
	}

	/**
	 * 画像のアップロード
	 * @param folderPath 画像トップフォルダパス
	 * @param uploadfile アップロードするファイル
	 * @param fileName ファイル名
	 * @param uploadImageMargin 画像切り出し座標X,Y
	 */
	static public void replaceFile(String folderPath, File uploadfile, String fileName, String uploadImageMargin)
		throws IOException, SQLException
	{
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

		File subFolder = new File(folderPath, fileName.substring(0, 1));
		if (!subFolder.exists())
		{
			// 存在しない

			new File(subFolder.getPath()).mkdir();
		}

		File destinationFile = new File(folderPath, fileName);

		// リサイズ
		ImageCollection.toJpegAndResize(
			uploadfile,
			destinationFile,
			uploadImageMarginX,
			uploadImageMarginY);
	}
}
