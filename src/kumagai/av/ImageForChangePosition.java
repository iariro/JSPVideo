package kumagai.av;

import java.util.ArrayList;

/**
 * 順番変更用画像情報
 */
public class ImageForChangePosition
{
	public final int id;
	public final Integer idBefore;
	public final Integer idAfter;
	public final int titleId;
	public final String position;
	public final String fileName;
	public final String comment;
	public final ArrayList<Costume> costumes;

	/**
	 * 指定の画像情報と前後位置情報をメンバーに割り当てる
	 * @param image 画像情報
	 * @param positionBefore
	 * @param positionAfter
	 */
	public ImageForChangePosition(Image image, Integer idBefore, Integer idAfter)
	{
		this.id = image.id;
		this.titleId = image.titleId;
		this.position = image.position;
		this.idBefore = idBefore;
		this.idAfter = idAfter;
		this.fileName = image.fileName;
		this.comment = image.comment;
		this.costumes = image.costumes;
	}
}
