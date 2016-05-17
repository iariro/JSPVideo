package kumagai.av.struts2;

import org.apache.struts2.convention.annotation.*;

/**
 * 画像情報更新ページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/updateimage1.jsp")
public class UpdateImage1Action
{
	public int id;
	public String comment;

	/**
	 * 画像情報更新ページ表示アクション。
	 * @return 処理結果
	 */
	@Action("updateimage1")
	public String execute()
		throws Exception
	{
		return "success";
	}
}
