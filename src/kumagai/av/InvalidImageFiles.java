package kumagai.av;

import java.util.*;

/**
 * 不正な画像エントリ情報。
 * @author kumagai
 */
public class InvalidImageFiles
{
	public final ArrayList<String> notReferredFiles;
	public final ArrayList<String> notExistFiles;

	/**
	 * メンバーの初期化を行う。
	 */
	public InvalidImageFiles()
	{
		notReferredFiles = new ArrayList<String>();
		notExistFiles = new ArrayList<String>();
	}
}
