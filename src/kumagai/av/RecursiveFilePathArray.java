package kumagai.av;

import java.io.*;
import java.util.*;

/**
 * 再帰的なファイルパスのコレクション。
 * @author kumagai
 */
public class RecursiveFilePathArray
	extends ArrayList<String>
{
	/**
	 * テストコード
	 * @param args [0]=対象パス
	 */
	public static void main(String[] args)
	{
		if (args.length >= 1)
		{
			ArrayList<String> files = new RecursiveFilePathArray(args[0]);

			for (String file : files)
			{
				System.out.println(file);
			}
		}
	}

	/**
	 * 再帰的にファイルパスのコレクションを構築。
	 * @param relativePath 相対パス
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

				searchRecursive(
					new File(relativePath, file.getName()).getPath(),
					file);
			}
			else if (file.isFile())
			{
				// ファイル

				add(new File(relativePath, file.getName()).getPath());
			}
		}
	}
}
