package kumagai.av;

import java.sql.*;
import java.util.*;

/**
 * DVD-Rダビング情報コレクション。
 * @author kumagai
 */
public class DvdrDubbingCollection
	extends ArrayList<DvdrDubbing>
{
	static public void main(String [] args)
		throws SQLException
	{
		Connection connection = DriverManager.getConnection(
			"jdbc:sqlserver://localhost:2144;DatabaseName=AV;User=sa;Password=p@ssw0rd;");

		DvdrDubbingCollection dvdrDubbingCollection =
			new DvdrDubbingCollection
				(connection, "2", 8);

		for (DvdrDubbing dubbing : dvdrDubbingCollection)
		{
			System.out.printf("%s %s\n", dubbing.position, dubbing.titleId);
		}

		connection.close();
	}

	/**
	 * ダビング情報を追加。
	 * @param connection DB接続オブジェクト
	 * @param diskId ディスクID
	 * @param position ディスク位置
	 * @param titleId タイトルID
	 * @param imageIndex 画像インデックス
	 * @param memo メモ
	 */
	static public void insert(Connection connection, String diskId,
		String position, String titleId, String imageIndex, String memo)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into dvdrdubbing (diskid, position, titleid, imageindex, memo) values (?, ?, ?, ?, ?)");

		statement.setString(1, diskId);
		statement.setString(2, position);
		statement.setString(3, titleId);
		statement.setString(4, imageIndex);
		statement.setString(5, memo);
		statement.executeUpdate();

		statement.close();
	}

	/**
	 * DBから値を読み取りコレクションを構築。
	 * @param connection DB接続オブジェクト
	 * @param diskId ディスクID
	 * @param trackNum トラック数
	 */
	public DvdrDubbingCollection
		(Connection connection, String diskId, int trackNum)
		throws SQLException
	{
		String dvdrTitle = DvdrCollection.getTitleById(connection, diskId);

		String sql =
			"select dvdrdubbing.position, dvdrdubbing.titleid, imageindex, title, filename from dvdrdubbing join title on title.id=dvdrdubbing.titleid join image on image.titleid=dvdrdubbing.titleid where diskid=? and image.position=1 order by diskid, position";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, diskId);

		ResultSet results = statement.executeQuery();

		boolean loop = true;
		int position2 = 0;

		DvdrDubbing dvdrDubbing = null;

		for (int i=1 ; i<=trackNum ; i++)
		{
			if (i > position2)
			{
				// 次を読むべきタイミング。

				if (loop)
				{
					// 続行する。

					if (results.next())
					{
						// 読み込み成功。

						int imageIndex = results.getInt("imageindex");
						int titleId = results.getInt("titleid");

						String fileName2 = null;

						if (imageIndex > 0)
						{
							// シーン画像あり。

							fileName2 =
								ImageCollection.getOneFileName
									(connection, titleId, imageIndex);
						}

						dvdrDubbing =
							new DvdrDubbing(results, dvdrTitle, fileName2);
						position2 = dvdrDubbing.position;
					}
					else
					{
						// 読み込み失敗。

						loop = false;
					}
				}
			}

			if (loop)
			{
				// 続行する。

				if (i == position2)
				{
					// 表示するタイミング。

					add(dvdrDubbing);
				}
				else
				{
					// スキップするタイミング。

					add(new DvdrDubbing(dvdrTitle, i));
				}
			}
			else
			{
				// 最後。

				add(new DvdrDubbing(dvdrTitle, i));
			}
		}

		results.close();
		statement.close();
	}
}
