package kumagai.av.test;

import java.sql.*;
import java.text.*;
import java.util.*;
import kumagai.av.*;

public class YearCountTest
{
	static public void main(String [] args)
		throws SQLException, ParseException
	{
		Connection connection =
			DriverManager.getConnection(
				"jdbc:sqlserver://localhost:2144;DatabaseName=AV;User=sa;Password=p@ssw0rd;");

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
