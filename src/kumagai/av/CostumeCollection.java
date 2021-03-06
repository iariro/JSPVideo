package kumagai.av;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * コスチューム情報コレクション。
 */
public class CostumeCollection
	extends ArrayList<Costume>
{
	static public void main(String [] args)
		throws SQLException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		CostumeCollection costumeCollection = new CostumeCollection(connection, null);

		connection.close();

		ArrayList<FeatureAndScore> stat = costumeCollection.getStatistics();

		for (int j=0 ; j<stat.size() ; j++)
		{
			FeatureAndScore featureAndScore = stat.get(j);

			System.out.printf(
				"%s %d ",
				featureAndScore.feature,
				featureAndScore.getSum());

			int [] perScore = featureAndScore.score;

			for (int i=0 ; i<perScore.length ; i++)
			{
				System.out.printf("%d ", perScore[i]);
			}

			System.out.println();
		}

		ArrayList<CostumeGroup> costumeGroups =
			costumeCollection.getAsGroup("ブルマ", 1);

		System.out.println(costumeGroups.size());

		for (CostumeGroup costumeGroup : costumeGroups)
		{
			System.out.println(costumeGroup);
		}
	}

	/**
	 * コスチューム情報登録。
	 * @param connection DB接続オブジェクト
	 * @param titleid タイトルID
	 * @param imageid 画像ID
	 * @param costume コスチューム
	 * @param feature 特徴
	 * @param score 点数
	 */
	static public void insert(Connection connection, int titleid, int imageid,
		String costume, String feature, int score)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into costume (titleid, imageid, costume, feature, score) values (?, ?, ?, ?, ?)");

		statement.setInt(1, titleid);
		statement.setInt(2, imageid);
		statement.setString(3, costume);
		statement.setString(4, feature);
		statement.setInt(5, score);
		statement.executeUpdate();

		statement.close();
	}

	/**
	 * コスチューム情報削除。コスチュームIDによる。
	 * @param connection DB接続オブジェクト
	 * @param costumeid コスチュームID
	 */
	static public void deleteByCostumeId(Connection connection, int costumeid)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement("delete costume where id=?");

		statement.setInt(1, costumeid);

		statement.executeUpdate();
	}

	/**
	 * コスチューム情報削除。画像IDによる。
	 * @param connection DB接続オブジェクト
	 * @param imageid 画像ID
	 */
	static public void deleteByImageId(Connection connection, int imageid)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement("delete costume where imageid=?");

		statement.setInt(1, imageid);

		statement.executeUpdate();
	}

	/**
	 * 全コスチューム情報を取得。
	 * @param connection DB接続オブジェクト
	 * @param titleId 作品ID
	 */
	public CostumeCollection(Connection connection, Integer titleId)
		throws SQLException
	{
		String sql = "select costume.id as costumeid, image.id as imageid, filename, costume, feature, score from costume join image on image.id=costume.imageid join title on title.id=costume.titleid";
		if (titleId != null)
		{
			// タイトルID指定あり

			sql += " where title.id=?";
		}
		sql += " order by score desc, costume, title.id";

		PreparedStatement statement = connection.prepareStatement(sql);

		if (titleId != null)
		{
			// タイトルID指定あり

			statement.setInt(1, titleId);
		}

		ResultSet results = statement.executeQuery();

		while (results.next())
		{
			add(new Costume(results));
		}

		results.close();
		statement.close();
	}

	/**
	 * グループ化したリストを生成。
	 * @param costumeName コスチューム名
	 * @param score 点数
	 * @return グループ化したリスト
	 */
	public ArrayList<CostumeGroup> getAsGroup(String costumeName, int score)
	{
		ArrayList<CostumeGroup> costumeGroups = new ArrayList<CostumeGroup>();

		CostumeGroup costumeGroup = null;

		for (Costume costume : this)
		{
			if (costumeGroup == null ||
				! costumeGroup.costume.equals(costume.costume) ||
				costumeGroup.score != costume.score)
			{
				// 新規またはコスチューム・点数の変わり目。

				costumeGroup = new CostumeGroup(costume.costume, costume.score);

				if (costumeGroup.costume.equals(costumeName) &&
					costumeGroup.score == score)
				{
					// 絞り込み条件に一致。

					costumeGroups.add(costumeGroup);
				}
			}

			costumeGroup.add(costume);
		}

		return costumeGroups;
	}

	/**
	 * 統計取得。
	 * @return 統計情報
	 */
	public ArrayList<FeatureAndScore> getStatistics()
	{
		HashMap<String, int []> features = new HashMap<String, int []>();

		for (Costume costume : this)
		{
			if (! features.containsKey(costume.feature))
			{
				// 初出。

				features.put(costume.feature, new int [3]);
			}

			int [] stat = features.get(costume.feature);

			stat[costume.score - 1]++;
		}

		ArrayList<FeatureAndScore> featureAndScores =
			new ArrayList<FeatureAndScore>();

		for (Map.Entry<String, int []> feature : features.entrySet())
		{
			featureAndScores.add(
				new FeatureAndScore(feature.getKey(), feature.getValue()));
		}

		Collections.sort(
			featureAndScores,
			new Comparator<FeatureAndScore>()
			{
				public int compare
					(FeatureAndScore a, FeatureAndScore b)
				{
					return - Integer.compare(a.getSum(), b.getSum());
				}
			});

		FeatureAndScore total = new FeatureAndScore("合計", new int [3]);

		for (FeatureAndScore featureAndScore : featureAndScores)
		{
			for (int i=0 ; i<featureAndScore.score.length ; i++)
			{
				total.score[i] += featureAndScore.score[i];
			}
		}

		featureAndScores.add(total);

		return featureAndScores;
	}
}
