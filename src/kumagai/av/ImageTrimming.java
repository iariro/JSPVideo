package kumagai.av;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 画像の動画部分の切り出し処理
 */
public class ImageTrimming
{
	static int colorThresh = 0xfcfcfc;

	/**
	 * 画像の動画部分の切り出し処理
	 * @param args [0]=inputfile [1]=outputfile
	 */
	static public void main(String [] args)
		throws IOException
	{
		String infile;
		String outfile;

		if (args.length < 1)
		{
			System.out.println("Usage : inputfile [outputfile]");
			return;
		}
		infile = args[0];
		if (args.length < 2)
		{
			int dotPosition = infile.lastIndexOf(".");
			outfile = String.format("%s_%s", infile.substring(0, dotPosition), infile.substring(dotPosition));
		}
		else
		{
			outfile = args[1];
		}

		File file = new File(infile);
		File destFile = new File(outfile);
		BufferedImage sourceImage = ImageIO.read(file);
		MovieRectangle outline = ImageTrimming.findMovieOutline(sourceImage);
		if (!outline.isAnyNull())
		{
			// 境界検出成功

			ImageTrimming.cutImage(sourceImage, outline, destFile, "png");
		}
		else
		{
			// 境界検出失敗

			System.out.printf("境界検出失敗 %s\n", outline.toString());
		}
	}

	/**
	 * 画像の一部を別ファイル化
	 * @param sourceImage 対象イメージ
	 * @param outline 切り出し座標
	 * @param destFile 保存ファイル
	 * @param contentType 画像フォーマット
	 */
	static public void cutImage(BufferedImage sourceImage, MovieRectangle outline, File destFile, String contentType)
		throws IOException
	{
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		int width2 = outline.x2 - outline.x1;
		int height2 = outline.y2 - outline.y1;

		BufferedImage resizeImage =
			new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		java.awt.Image resizeImage2 =
			sourceImage.getScaledInstance
				(width, height, java.awt.Image.SCALE_AREA_AVERAGING);
		resizeImage.getGraphics().drawImage
			(resizeImage2, -outline.x1, -outline.y1, width, height, null);
		ImageIO.write(resizeImage, contentType, destFile);
	}

	/**
	 * 動画左側の境界を見つける
	 * @param image 対象画像
	 * @return 境界X座標
	 */
	static public Integer findLeftBorderline(BufferedImage image)
	{
		int centerX = image.getWidth() / 2;
		int maxmaxXDiffCount = 0;
		Integer maxmaxXDiffX = null;
		for (int x=centerX ; x>=0 ; x--)
		{
			int xDiffCount = 0;
			int maxXDiffCount = 0;
			for (int y=0 ; y<image.getHeight() ; y++)
			{
				int rgb1 = image.getRGB(x, y) & colorThresh;
				int rgb2 = image.getRGB(x+1, y) & colorThresh;

				if (rgb1 == rgb2)
				{
					xDiffCount = 0;
				}
				else
				{
					xDiffCount++;
					if (maxXDiffCount < xDiffCount)
					{
						maxXDiffCount = xDiffCount;
					}
				}
			}

			if (maxmaxXDiffCount < maxXDiffCount)
			{
				maxmaxXDiffCount = maxXDiffCount;
				maxmaxXDiffX = x;

				if ((maxXDiffCount * 100 / image.getHeight()) >= 60)
				{
					break;
				}
			}
		}

		return maxmaxXDiffX;
	}

	/**
	 * 背景色を持つ水平線を探しY座標を求める
	 * @param image 対象イメージ
	 * @return 背景色を持つ水平線のY座標
	 */
	static public Integer findTopBorderline(BufferedImage image)
	{
		int centerY = image.getHeight() / 2;
		int maxmaxYDiffCount = 0;
		Integer maxmaxYDiffY = null;
		for (int y=centerY ; y>=0 ; y--)
		{
			int yDiffCount = 0;
			int maxYDiffCount = 0;
			for (int x=10 ; x<image.getWidth()-10 ; x++)
			{
				int rgb1 = image.getRGB(x, y) & colorThresh;
				int rgb2 = image.getRGB(x, y+1) & colorThresh;

				if (rgb1 == rgb2)
				{
					yDiffCount = 0;
				}
				else
				{
					yDiffCount++;
					if (maxYDiffCount < yDiffCount)
					{
						maxYDiffCount = yDiffCount;
					}
				}
			}

			if (maxmaxYDiffCount < maxYDiffCount)
			{
				maxmaxYDiffCount = maxYDiffCount;
				maxmaxYDiffY = y;

				if ((maxYDiffCount * 100 / image.getHeight()) >= 70)
				{
					break;
				}
			}
		}

		return maxmaxYDiffY;
	}

	/**
	 * 動画右側の境界を見つける
	 * @param image 対象画像
	 * @return 境界X座標
	 */
	static public Integer findRightBorderline(BufferedImage image)
	{
		int centerX = image.getWidth() / 2;
		int maxmaxXDiffCount = 0;
		Integer maxmaxXDiffX = null;
		for (int x=centerX ; x<image.getWidth() ; x++)
		{
			int xDiffCount = 0;
			int maxXDiffCount = 0;
			for (int y=0 ; y<image.getHeight() ; y++)
			{
				int rgb1 = image.getRGB(x, y) & colorThresh;
				int rgb2 = image.getRGB(x-1, y) & colorThresh;

				if (rgb1 == rgb2)
				{
					xDiffCount = 0;
				}
				else
				{
					xDiffCount++;
					if (maxXDiffCount < xDiffCount)
					{
						maxXDiffCount = xDiffCount;
					}
				}
			}

			if (maxmaxXDiffCount < maxXDiffCount)
			{
				maxmaxXDiffCount = maxXDiffCount;
				maxmaxXDiffX = x;

				if ((maxXDiffCount * 100 / image.getHeight()) > 60)
				{
					break;
				}
			}
		}

		return maxmaxXDiffX;
	}

	/**
	 * 背景色を持つ水平線を探しY座標を求める
	 * @param image 対象イメージ
	 * @return 背景色を持つ水平線のY座標
	 */
	static public Integer findBottomBorderline(BufferedImage image)
	{
		int centerY = image.getHeight() / 2;
		int maxmaxYDiffCount = 0;
		Integer maxmaxYDiffY = null;
		for (int y=centerY ; y<image.getHeight() ; y++)
		{
			int yDiffCount = 0;
			int maxYDiffCount = 0;
			for (int x=10 ; x<image.getWidth()-10 ; x++)
			{
				int rgb1 = image.getRGB(x, y) & colorThresh;
				int rgb2 = image.getRGB(x, y-1) & colorThresh;

				if (rgb1 == rgb2)
				{
					yDiffCount = 0;
				}
				else
				{
					yDiffCount++;
					if (maxYDiffCount < yDiffCount)
					{
						maxYDiffCount = yDiffCount;
					}
				}
			}

			if (maxmaxYDiffCount < maxYDiffCount)
			{
				maxmaxYDiffCount = maxYDiffCount;
				maxmaxYDiffY = y;

				if ((maxYDiffCount * 100 / image.getHeight()) >= 70)
				{
					break;
				}
			}
		}

		return maxmaxYDiffY;
	}

	/**
	 * 境界切り出し
	 * @param image 対象画像
	 * @return 境界
	 */
	static public MovieRectangle findMovieOutline(BufferedImage image)
		throws IOException
	{
		Integer leftX = findLeftBorderline(image);
		Integer rightX = findRightBorderline(image);
		Integer topY = findTopBorderline(image);
		Integer bottomY = findBottomBorderline(image);

		if (leftX != null && rightX != null && topY != null && bottomY != null)
		{
			// 上下左右の境界が得られた

			MovieRectangle outline =
				new MovieRectangle(
					leftX,
					topY,
					rightX,
					bottomY);

			return outline;
		}
		else
		{
			// 上下左右の境界が得られなかった

			return new MovieRectangle(rightX, topY, null, bottomY);
		}
	}
}
