package kumagai.av;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ktool.datetime.DateTime;

/**
 * 再帰的なファイルパスのコレクション。
 * @author kumagai
 */
public class RecursiveFilePathArray
	extends ArrayList<File>
{
	/**
	 * テストコード
	 * @param args [0]=対象パス
	 */
	public static void main(String[] args)
		throws IOException
	{
		if (args.length >= 1)
		{
			String basePath = args[0];
			RecursiveFilePathArray files = new RecursiveFilePathArray(args[0]);
			DateTime time1 = new DateTime();
			ArrayList<File> dmmPlayerSliderFiles = files.findDmmPlayerSlider();
			DateTime time2 = new DateTime();
			System.out.println(time2.diff(time1));

			for (File file : dmmPlayerSliderFiles)
			{
				System.out.println(file.getAbsolutePath().substring(basePath.length()));
			}
		}
	}

	/**
	 * 再帰的にファイルパスのコレクションを構築。
	 * @param file ディレクトリ
	 */
	public RecursiveFilePathArray(String file)
	{
		searchRecursive(null, new File(file));
	}

	/**
	 * 再帰的にファイルパスのコレクションを構築。
	 * @param relativePath 相対パス
	 * @param file ディレクトリ
	 */
	private void searchRecursive(String relativePath, File path)
	{
		for (File file : path.listFiles())
		{
			if (file.isDirectory())
			{
				// ディレクトリ

				searchRecursive(file.getPath(), file);
			}
			else if (file.isFile())
			{
				// ファイル

				add(file);
			}
		}
	}

	/**
	 * 画像ファイルの更新日時から最新の画像ファイルを取得
	 * @return 画像リスト
	 */
	public ArrayList<FileAndDatetime> getNewImageFiles()
	{
		ArrayList<FileAndDatetime> fileAndDatetimes = new ArrayList<FileAndDatetime>();
		for (File file : this)
		{
			if (file.getName().endsWith("jpg") || file.getName().endsWith("jpeg"))
			{
				// JPEGファイル

				long updateDate = file.lastModified();
				fileAndDatetimes.add(new FileAndDatetime(file.getPath(), new DateTime(updateDate)));
			}
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

		ArrayList<FileAndDatetime> fileAndDatetimes2 = new ArrayList<FileAndDatetime>();
		for (int i=0 ; i<fileAndDatetimes.size() ; i++)
		{
			fileAndDatetimes2.add(fileAndDatetimes.get(i));
		}

		return fileAndDatetimes2;
	}

	/**
	 * DMMプレイヤーのタイムバー映り込みを検出
	 * @return DMMプレイヤーのタイムバーが映り込んだ画像のリスト
	 */
	public ArrayList<File> findDmmPlayerSlider()
		throws IOException
	{
		ArrayList<File> files = new ArrayList<File>();

		for (File file : this)
		{
			if (Image.judgeDmmWithTimebar(file))
			{
				files.add(file);
			}
		}

		return files;
	}
}
