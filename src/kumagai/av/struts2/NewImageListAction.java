package kumagai.av.struts2;

import java.util.*;
import javax.servlet.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * 画像リスト表示アクション用フォーム。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/newimagelist.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class NewImageListAction
{
	public ArrayList<FileAndDatetime> newImageFilst;
	public String message;

	/**
	 * 画像リスト表示アクション用フォーム。
	 * @return 処理結果
	 */
	@Action("newimagelist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();
		String filePath = context.getInitParameter("AVImageFolder");

		if (filePath != null)
		{
			try
			{
				newImageFilst = ImageCollection.getNewImageFieList(filePath);

				return "success";
			}
			catch (Exception exception)
			{
				this.message = exception.getMessage();

				return "error";
			}
		}
		else
		{
			message = "必要なパラメータ定義が揃っていません";

			return "error";
		}
	}
}
