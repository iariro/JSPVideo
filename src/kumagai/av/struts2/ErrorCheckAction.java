package kumagai.av.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.av.*;

/**
 * DB・ファイルエラーチェックアクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/errorcheck.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class ErrorCheckAction
{
	public ArrayList<String> notReferredFiles;
	public ArrayList<String> notExistFiles;
	public ArrayList<FilenameAndCount> duplicateFiles;
	public ArrayList<Image> noRefferedImage;
	public HashMap<String, String> titleMemo;
	public ArrayList<Title1> notWatchTitles;
	public ArrayList<WatchInformation> notExistTitleWatch;
	public ArrayList<WatchInformation> invalidDate;
	public ArrayList<DateAndInt> duplicateReview;

	/**
	 * DB・ファイルエラーチェックアクション。
	 * @return 処理結果
	 */
	@Action("errorcheck")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		String url = context.getInitParameter("AVSqlserverUrl");
		String filePath = context.getInitParameter("AVImageFolder");

		if (url != null && filePath != null)
		{
			DriverManager.registerDriver(new SQLServerDriver());

			Connection connection = DriverManager.getConnection(url);

			ImageCollection imageCollection = new ImageCollection(connection);

			InvalidImageFiles invalidImageFiles =
				imageCollection.getNotExistFiles(filePath);

			notReferredFiles = invalidImageFiles.notReferredFiles;
			notExistFiles = invalidImageFiles.notExistFiles;
			duplicateFiles = ImageCollection.getDuplicateFile(connection);
			noRefferedImage = ImageCollection.getNoRefferedImage(connection);
			titleMemo = TitleCollection.getTitleMemo(connection);
			notWatchTitles = TitleCollection.getNotWatchTitles(connection);
			notExistTitleWatch = WatchCollection.getNotExistTitleWatch(connection);
			invalidDate = WatchCollection.getInvalidDate(connection);
			duplicateReview = DiaryReviewCollection.getDuplicateReview(connection);

			connection.close();

			return "success";
		}
		else
		{
			return "error";
		}
	}
}
