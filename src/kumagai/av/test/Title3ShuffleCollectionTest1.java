package kumagai.av.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import ktool.datetime.DateTime;
import kumagai.av.DBInfo;
import kumagai.av.Title3;
import kumagai.av.Title3AndRandomValue;
import kumagai.av.Title3ShuffleCollection;
import kumagai.av.TitleCollection;

public class Title3ShuffleCollectionTest1
{
	static public void main(String [] args)
		throws SQLException, ParseException
	{
		int randomAdjust = 0;

		String originDateString = args[0];

		if (args.length >= 2)
		{
			randomAdjust = Integer.valueOf(args[1]);
		}

		String date = null;

		if (args.length >= 3)
		{
			date = args[2];
		}

		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);

		ArrayList<Title3> titleCollection =
			new TitleCollection(
				connection,
				true,
				true,
				"buydate desc, indexinday",
				date,
				null);

		connection.close();

		System.out.printf("%d件\n", titleCollection.size());

		DateTime originDate = DateTime.parseDateString(originDateString);

		Title3ShuffleCollection titleCollectionRandom =
			new Title3ShuffleCollection
				(titleCollection, originDate, new DateTime(), randomAdjust);

		for (Title3AndRandomValue title3AndRandomValue : titleCollectionRandom)
		{
			if (title3AndRandomValue.title == titleCollectionRandom.getTitle2())
			{
				System.out.print("≫");
			}
			else
			{
				System.out.print("  ");
			}

			String jogai = " ";

			if (title3AndRandomValue.title.watchmemo != null &&
				title3AndRandomValue.title.watchmemo.equals("除外予定"))
			{
				// 除外予定。

				jogai = "x";
			}

			System.out.printf(
				"%12d %s %s\n",
				title3AndRandomValue.randomValue,
				jogai,
				title3AndRandomValue.title.title);
		}
	}
}
