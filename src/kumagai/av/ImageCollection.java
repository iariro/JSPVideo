package kumagai.av;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * 画像情報コレクション。
 * @author kumagai
 */
public class ImageCollection
	extends ArrayList<Image>
{
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
		(Connection connection, String titleId, int position, String fileName)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"update image set filename=? where titleid=? and position=?");

		statement.setString(1, fileName);
		statement.setString(2, titleId);
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

		for (String file : new File(filePath).list())
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
}
