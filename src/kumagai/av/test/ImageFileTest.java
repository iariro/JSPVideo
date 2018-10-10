package kumagai.av.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ktool.datetime.DateTime;
import kumagai.av.FileAndDatetime;
import kumagai.av.RecursiveFilePathArray;

public class ImageFileTest
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ArrayList<File> files = new RecursiveFilePathArray(AddImagesTest.filePath);

		ArrayList<FileAndDatetime> fileAndDatetimes = new ArrayList<FileAndDatetime>();
		for (File file : files)
		{
			long updateDate = file.lastModified();
			fileAndDatetimes.add(new FileAndDatetime(file.getPath(), new DateTime(updateDate)));
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
