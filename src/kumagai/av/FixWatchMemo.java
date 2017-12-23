package kumagai.av;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 購入・除外予定の確定処理。
 */
public class FixWatchMemo
{
	/**
	 * @param args 未使用
	 */
	public static void main(String[] args)
		throws SQLException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		int count1 =
			WatchCollection.updateUnfixMemo
				(connection, "追加予定", new String());
		int count2 =
			WatchCollection.updateUnfixMemo
				(connection, "除外予定", "除外");

		connection.close();

		System.out.printf("購入：%d件確定\n", count1);
		System.out.printf("除外：%d件確定\n", count2);
	}
}
