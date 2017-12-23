package kumagai.av.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;
import kumagai.av.DBInfo;
import kumagai.av.WatchCollection;
import kumagai.av.WatchInformation;

public class WatchCollectionTest
	extends TestCase
{
	public static void main(String[] args)
		throws SQLException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		WatchCollection.insertAsUpdate(connection, 183, "2000/1/1", "2000/1/1", null, "test");

		connection.close();
	}

	public void testGet()
		throws SQLException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		ArrayList<WatchInformation> watchInformations =
			WatchCollection.getByTitleId(connection, "183");

		connection.close();

		for (WatchInformation watchInformation : watchInformations)
		{
			System.out.println(watchInformation);
		}
	}

	public void testGetNotExistTitleWatch()
		throws SQLException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		ArrayList<WatchInformation> watchInformations =
			WatchCollection.getNotExistTitleWatch(connection);

		connection.close();

		System.out.println(watchInformations.size());

		for (WatchInformation watchInformation : watchInformations)
		{
			System.out.println(watchInformation);
		}
	}

	public void testGetInvalidDate()
		throws SQLException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		ArrayList<WatchInformation> watchInformations =
			WatchCollection.getInvalidDate(connection);

		connection.close();

		System.out.println(watchInformations.size());

		for (WatchInformation watchInformation : watchInformations)
		{
			System.out.println(watchInformation);
		}
	}
}
