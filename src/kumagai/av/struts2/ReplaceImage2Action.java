package kumagai.av.struts2;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import kumagai.av.ImageCollection;

/**
 * 画像ファイル更新アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/replaceimage2.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class ReplaceImage2Action
{
	public File uploadfile;
	public String uploadfileContentType;
	public String uploadfileFileName;
	public String fileName;
	public String uploadImageMargin;

	public ArrayList<String> uploadedFiles;
	public String exception;
	public String message;

	/**
	 * 画像ファイル更新アクション。
	 * @return 処理結果
	 */
	@Action("replaceimage2")
	public String execute()
		throws Exception
	{
		try
		{
			ServletContext context = ServletActionContext.getServletContext();

			String folderPath = context.getInitParameter("AVImageFolder");

			if (uploadfile != null &&
				folderPath != null &&
				fileName != null &&
				fileName.length() > 0)
			{
				// 必要なパラメータあり

				ImageCollection.replaceFile(folderPath, uploadfile, fileName, uploadImageMargin);

				return "success";
			}
			else
			{
				// 必要なパラメータなし

				message = String.format("uploadfile=%s folderPath=%s", uploadfile, folderPath);
			}
		}
		catch (Exception exception)
		{
			this.exception = exception.toString();
		}

		return "error";
	}
}
