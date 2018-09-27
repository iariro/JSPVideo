package kumagai.av.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * 画像ファイルアップロード。
 * @author kumagai
 */
public class UploadImage
{
	/**
	 * 画像ファイルアップロード。
	 * @param args [0]=titleID [1]=DMMCID [2]=folderPath [3]=imageMargin
	 */
	static public void main(String [] args)
	{
		if (args.length < 3)
		{
			System.out.println("Usage : titleId DMMCID folderPath imageMargin");
			return;
		}

		String titleId = args[0];
		String dmmUrlCid = args[1];
		File [] files = new File(args[2]).listFiles();
		String uploadImageMargin = args[3];

		for (File file : files)
		{
			if (file.isFile())
			{
				MultipartUtility multipartUtility =
					new MultipartUtility("http://192.168.10.10:8080/kumagai/av/uploadimage.action", "utf-8");
				multipartUtility.addFormField("titleId", titleId);
				multipartUtility.addFormField("dmmUrlCid", dmmUrlCid);
				multipartUtility.addFilePart("uploadfile", file);
				ArrayList<String> results = multipartUtility.finish();
				for (String result : results)
				{
					System.out.printf("%s %s\n", file, result);
				}
				break;
			}
		}
	}
}
