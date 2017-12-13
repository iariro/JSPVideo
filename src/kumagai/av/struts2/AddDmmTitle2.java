package kumagai.av.struts2;

import java.io.IOException;
import java.net.URL;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import kumagai.av.DmmServer;
import kumagai.av.DmmTitleInfo;

/**
 * タイトル追加アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/addtitle1.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class AddDmmTitle2
{
	public String dmmUrl;
	public String title;
	public String type = "DL";
	public String releaseDate;

	/**
	 * タイトル追加アクション。
	 * @return 処理結果
	 */
	@Action("adddmmtitle2")
    public String execute()
    	throws Exception
	{
		try
		{
			URL url = new URL(dmmUrl);
			DmmTitleInfo titleInfo = DmmServer.getTitleInfo(url.openStream());
			title = titleInfo.title;
			releaseDate = titleInfo.webReleaseDate;
			return "success";
		}
		catch (IOException exception)
		{
			return "error";
		}
	}
}
