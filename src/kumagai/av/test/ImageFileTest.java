package kumagai.av.test;

import java.io.*;
import java.util.*;
import ktool.datetime.*;
import kumagai.av.*;

public class ImageFileTest
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ArrayList<String> files = new RecursiveFilePathArray(AddImagesTest.filePath);

		ArrayList<FileAndDatetime> fileAndDatetimes = new ArrayList<FileAndDatetime>();
		for (String file : files)
		{
			long updateDate = new File(AddImagesTest.filePath, file).lastModified();
			fileAndDatetimes.add(new FileAndDatetime(file, new DateTime(updateDate)));
		}

		Collections.sort(
			fileAndDatetimes,
			new Comparator<FileAndDatetime>()
			{
				public int compare(FileAndDatetime item1, FileAndDatetime item2)
				{
					return - item1.updatedate.compareTo(item2.updatedate);
				}
			});

		for (FileAndDatetime file : fileAndDatetimes)
		{
			System.out.printf("%s %s\n", file.filepath, file.updatedate);
		}
	}
}
