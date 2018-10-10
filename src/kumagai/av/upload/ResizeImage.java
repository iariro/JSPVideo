package kumagai.av.upload;

import java.io.File;
import java.io.IOException;

import kumagai.av.ImageCollection;

/**
 * 画像リサイズ。
 * @author kumagai
 */
public class ResizeImage
{
	/**
	 * 画像リサイズ。
	 * @param args [0]=変換元 [1]=変換先
	 * @throws IOException
	 */
	static public void main(String [] args)
		throws IOException
	{
		if (args.length >= 2)
		{
			File sourceFile = new File(args[0]);
			File destinationFile = new File(args[1]);
			ImageCollection.toJpegAndResize(sourceFile, destinationFile, 0, 0);
		}
	}
}
