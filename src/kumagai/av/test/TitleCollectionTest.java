package kumagai.av.test;

import java.sql.*;
import java.util.*;
import java.text.ParseException;
import junit.framework.*;
import kumagai.av.*;

public class TitleCollectionTest
	extends TestCase
{
	public static void main(String[] args)
		throws Exception
	{
		Connection connection =
			DriverManager.getConnection(
				"jdbc:sqlserver://localhost:2144;DatabaseName=AV;User=sa;Password=p@ssw0rd;");

		DiaryReviewCollection diaryReviewCollection =
				new DiaryReviewCollection(connection, null);

		TitleCollection titleCollection =
			new TitleCollection(
				connection,
				false,
				false,
				"releasedate desc",
				null,
				diaryReviewCollection);

		connection.close();

		System.out.println(titleCollection.size());

		for (Title3 title : titleCollection)
		{
			System.out.println(title);
		}
	}

	public void test1()
		throws SQLException, ParseException
	{
		Connection connection =
			DriverManager.getConnection(
				"jdbc:sqlserver://localhost:2144;DatabaseName=AV;User=sa;Password=p@ssw0rd;");

		/*
		// by year
		new TitleCollection(
			connection,
			false,
			false,
			"releasedate desc",
			null,
			null);
		*/

		// by input
		DiaryReviewCollection diaryReviewCollection =
			new DiaryReviewCollection(connection, null);

		/*
		new TitleCollection(
			connection,
			false,
			false,
			"id desc",
			null,
			diaryReviewCollection);

		// by title
		new TitleCollection(
			connection,
			false,
			false,
			"title",
			null,
			null);
		*/

		// by purchase
		//TitleCollection titleCollection =
			new TitleCollection(
				connection,
				true,
				false,
				"w1.buydate desc",
				null,
				diaryReviewCollection);

		connection.close();
	}

	public void testGetPurchasedAndNoImageList()
		throws SQLException, ParseException
	{
		Connection connection =
			DriverManager.getConnection(
				"jdbc:sqlserver://localhost:2144;DatabaseName=AV;User=sa;Password=p@ssw0rd;");

		ArrayList<Title3> titleCollection =
			TitleCollection.getPurchasedAndNoImageList(connection, 1);

		connection.close();

		for (Title3 title : titleCollection)
		{
			System.out.println(title);
		}
	}
}
