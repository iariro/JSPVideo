package kumagai.av.struts2;

import org.apache.struts2.convention.annotation.*;

/**
 * コスチューム情報追加ページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/addcostume1.jsp")
public class AddCostume1Action
{
	public int titleid;
	public int imageid;

	/**
	 * コスチューム情報追加ページ表示アクション。
	 * @return 処理結果
	 */
	@Action("addcostume1")
    public String execute()
    	throws Exception
	{
		return "success";
	}
}
