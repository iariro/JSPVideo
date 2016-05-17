package kumagai.av.test;

import junit.framework.*;
import kumagai.av.*;

public class Title3Test
	extends TestCase
{
	public void test1()
	{
		String url = "http://www.dmm.co.jp/mono/dvd/-/detail/=/cid=84mkmp009/?i3_ref=search&i3_ord=4";

		System.out.println(Title1.getDmmImageUrlPl(url));

		assertEquals("84mkmp009", Title1.getCid(url));
	}
}
