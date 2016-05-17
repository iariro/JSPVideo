package kumagai.av.test;

import java.text.*;
import java.util.*;
import junit.framework.*;
import ktool.datetime.*;
import kumagai.av.*;

public class Title3ShuffleCollectionTest2
	extends TestCase
{
	static final String originDateString = "2013/12/03";
	static final String todayDateString = "2013/12/03";
	private DateTime originDate;
	private DateTime todayDate;
	private ArrayList<Title3> titleCollection = new ArrayList<Title3>();
	private Title3ShuffleCollection titleCollectionRandom;

	public void setUp()
		throws ParseException
	{
		originDate = DateTime.parseDateString(originDateString);
		todayDate = DateTime.parseDateString(originDateString);

		for (int i=0 ; i<5 ; i++)
		{
			titleCollection.add(new Title3(i, null));
		}
	}

	public void test1()
	{
		titleCollectionRandom =
			new Title3ShuffleCollection(titleCollection, originDate, todayDate, 0);

		// 32014
		assertEquals(3, titleCollectionRandom.getTitle2().id);
	}

	public void test2()
	{
		titleCollectionRandom =
			new Title3ShuffleCollection(titleCollection, originDate, todayDate, 1);

		// 32014
		assertEquals(2, titleCollectionRandom.getTitle2().id);
	}

	public void test3()
	{
		titleCollectionRandom =
			new Title3ShuffleCollection(titleCollection, originDate, todayDate, 5);

		// 42310
		assertEquals(4, titleCollectionRandom.getTitle2().id);
	}

	public void tearDown()
	{
		for (Title3AndRandomValue title3AndRandomValue : titleCollectionRandom)
		{
			System.out.print(title3AndRandomValue.title.id);
		}
		System.out.println();
	}
}
