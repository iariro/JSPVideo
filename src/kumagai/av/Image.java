package kumagai.av;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 画像情報。
 * @author kumagai
 */
public class Image
{
	/**
	 * DMMプレイヤーのタイムバー映り込みを判定
	 * @return true=移り込みあり／false=なし
	 */
	static public boolean judgeDmmWithTimebar(File file)
		throws IOException
	{
		BufferedImage image = ImageIO.read(file);

		if ((image != null) &&
			(image.getWidth() >= 20 && image.getHeight() >= 340))
		{
			int rgb = image.getRGB(20, 346);
			int g = (rgb & 0xff00) >> 8;
			int b = (rgb & 0xff);

			if ((getAbsoluteDiff((rgb & 0xff0000) >> 16, 0) <= 10) &&
				(getAbsoluteDiff(b, 0xff) <= 10) &&
				(getAbsoluteDiff(g, 0xa3) <= 10))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * 色の差を絶対値で求める
	 * @param color1 色１
	 * @param color2 色２
	 * @return 差
	 */
	static int getAbsoluteDiff(int color1, int color2)
	{
		int diff = color1 - color2;
		if (diff < 0)
		{
			diff *= -1;
		}
		return diff;
	}

	public final int id;
	public final int titleId;
	public final String position;
	public final String fileName;
	public final String comment;
	public ArrayList<Costume> costumes = new ArrayList<Costume>();

	/**
	 * 指定の値をメンバーに割り当て。
	 * @param id ID
	 * @param titleId タイトルID
	 * @param position 画像位置
	 * @param fileName ファイル名
	 * @param comment コメント
	 */
	public Image(int id, int titleId, String position, String fileName,
		String comment)
	{
		this.id = id;
		this.titleId = titleId;
		this.position = position;
		this.fileName = fileName;
		this.comment = comment;
	}

	/**
	 * １レコードからオブジェクトを構築する。
	 * @param results レコード情報
	 */
	public Image(ResultSet results)
		throws SQLException
	{
		id = results.getInt("id");
		titleId = results.getInt("titleid");
		position = results.getString("position");
		fileName = results.getString("filename");
		comment = results.getString("comment");
	}
}
