package kumagai.av.test;

import junit.framework.*;
import kumagai.av.struts2.*;

public class EtcTest
	extends TestCase
{
	public void test1()
	{
		assertEquals("jpg", UploadImageAction.getImageType("image/jpg"));
		assertEquals("png", UploadImageAction.getImageType("image/png"));
	}
}
