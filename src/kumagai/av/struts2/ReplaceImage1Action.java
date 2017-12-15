package kumagai.av.struts2;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 画像ファイル更新ページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/replaceimage1.jsp")
})
public class ReplaceImage1Action
{
	public int imageId;
	public int titleId;
	public String fileName;

	/**
	 * 画像ファイル更新ページ表示アクション。
	 * @return 処理結果
	 */
	@Action("replaceimage1")
	public String execute()
		throws Exception
	{
		return "success";
	}
}
