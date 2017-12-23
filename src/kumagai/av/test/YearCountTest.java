package kumagai.av.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import kumagai.av.DBInfo;
import kumagai.av.ReleaseYearAndCountCollection;
import kumagai.av.WatchYearAndCountCollection;
import kumagai.av.YearAndCount;
import kumagai.av.YearStat;

public class YearCountTest
{
	static public void main(String [] args)
		throws SQLException, ParseException
	{
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		ArrayList<YearAndCount> rental1Stat =
			new ReleaseYearAndCountCollection(connection, "rentaldate");

		ArrayList<YearAndCount> buy1Stat =
			new ReleaseYearAndCountCollection(connection, "buydate");

		ArrayList<YearAndCount> rental2Stat =
			new WatchYearAndCountCollection(connection, "rentaldate");

		ArrayList<YearAndCount> buy2Stat =
			new WatchYearAndCountCollection(connection, "buydate");

		connection.close();

		YearStat yearStat =
			new YearStat(rental1Stat, buy1Stat, rental2Stat, buy2Stat);

		yearStat.dump();
	}
}
