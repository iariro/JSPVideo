package kumagai.av.imageutility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.DBInfo;
import kumagai.av.ImageCollection;

/**
 * 画像の余白やDMMプレイヤーのコントロールを検出
 */
public class ImageMarginCheck
{
	/**
	 * 画像の余白やDMMプレイヤーのコントロールを検出
	 */
	public static void main(String[] args)
		throws SQLException, IOException
	{
		DriverManager.registerDriver(new SQLServerDriver());
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);
		FilenameAndTitleCollection filenameAndTitleCollection =
			ImageCollection.getAllImageAndTitle(connection);

		FilenameAndTitleCollection checkResult = checkMargin(filenameAndTitleCollection);
		for (FilenameAndTitle filenameAndTitle : checkResult)
		{
			System.out.println(filenameAndTitle);
		}

		FilenameAndTitleCollection checkResult2 = checkDmmBar(filenameAndTitleCollection);
		for (FilenameAndTitle filenameAndTitle : checkResult2)
		{
			System.out.println(filenameAndTitle);
		}
	}

	/**
	 * 画像の左右の黒い余白を検出する
	 * @param filenameAndTitleCollection チェック対象
	 * @return 余白ありのエントリ
	 */
	static public FilenameAndTitleCollection checkMargin
		(FilenameAndTitleCollection filenameAndTitleCollection)
		throws IOException
	{
		FilenameAndTitleCollection filenameAndTitleCollection2 =
			new FilenameAndTitleCollection();
		for (FilenameAndTitle filenameAndTitle : filenameAndTitleCollection)
		{
			BufferedImage image =
				ImageIO.read(new File(DBInfo.imageBasePath, filenameAndTitle.filename));
			boolean findColor = false;
			for (int y=0 ; y<image.getHeight() ; y += 20)
			{
				// 左右の余白となりうる部分の色取得
				int rgb1 = image.getRGB(10, y) & 0xffffff;
				int rgb2 = image.getRGB(image.getWidth() - 10, y) & 0xffffff;

				if ((rgb1 > 0) || (rgb2 > 0))
				{
					// 色あり

					findColor = true;
					break;
				}
			}

			if (!findColor)
			{
				// 色なし

				filenameAndTitleCollection2.add(filenameAndTitle);
			}
		}

		return filenameAndTitleCollection2;
	}

	/**
	 * DMMプレイヤーのスクリーンショットの検出
	 * @param filenameAndTitleCollection チェック対象
	 * @return 検出されたエントリ
	 */
	static public FilenameAndTitleCollection checkDmmBar
		(FilenameAndTitleCollection filenameAndTitleCollection)
		throws IOException
	{
		FilenameAndTitleCollection filenameAndTitleCollection2 =
			new FilenameAndTitleCollection();
		for (FilenameAndTitle filenameAndTitle : filenameAndTitleCollection)
		{
			BufferedImage image =
				ImageIO.read(new File(DBInfo.imageBasePath, filenameAndTitle.filename));

			if ((image.getWidth() > 10) && (image.getHeight() > 345))
			{
				// 所定の大きさを満たしている

				// DMMで再生時のバーが表示されるエリアの色取得
				int rgb = image.getRGB(20, 346) & 0xffffff;

				if (((rgb & 0xff0000) == 0) &&
					((rgb & 0xf000) == 0xa000) &&
					((rgb & 0xf0) == 0xf0))
				{
					// 該当する色

					filenameAndTitleCollection2.add(filenameAndTitle);
				}
			}
		}

		return filenameAndTitleCollection2;
	}
}
