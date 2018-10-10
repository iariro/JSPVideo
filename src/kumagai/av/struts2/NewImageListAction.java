package kumagai.av.struts2;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import kumagai.av.FileAndDatetime;
import kumagai.av.RecursiveFilePathArray;

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
				RecursiveFilePathArray recursiveFilePathArray = new RecursiveFilePathArray(filePath);
				newImageFilst = recursiveFilePathArray.getNewImageFiles();

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
