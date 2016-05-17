package kumagai.av.struts2;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import ktool.datetime.*;
import kumagai.av.*;

/**
 * 視聴情報更新ページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/updatewatch1.jsp")
public class UpdateWatch1Action
{
	static private final int columnSize = 10;
	static private final SimpleDateFormat formatDate;

	/**
	 * 日付書式オブジェクトを構築。
	 */
	static
	{
		formatDate = new SimpleDateFormat();

		formatDate.applyPattern("yyyy/MM/dd");
	}

	public String titleId;
	public boolean exist;
	public String title;
	public String rentalDate;
	public String buyDate;
	public int watch;
	public String memo;
	public ArrayList<WatchInformation> watchInformations;
	public String [][] diaryDateTable;
	public String diaryDate;
	public int diaryDateTableColspan;

	/**
	 * 視聴情報更新ページ表示アクション。
	 * @return 処理結果
	 */
	@Action("updatewatch1")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection(
				context.getInitParameter("AVSqlserverUrl"));

		Title2 title2 = TitleCollection.getOneTitle2(connection, titleId);

		if (title2 != null)
		{
			// タイトル情報あり。

			title = title2.title;
			rentalDate = title2.rentalDate;
			buyDate = title2.buyDate;
			memo = title2.memo;

			try
			{
				watch = Integer.valueOf(title2.watch);
			}
			catch (Exception exception)
			{
			}
		}

		watchInformations = WatchCollection.getByTitleId(connection, titleId);

		DiaryReviewCollection diaryReviewCollection =
			new DiaryReviewCollection(connection, titleId);

		connection.close();

		// １０件１列の表作成。
		int size = diaryReviewCollection.size();
		diaryDateTableColspan = (size + columnSize - 1) / columnSize;

		if (size < columnSize)
		{
			// １０件未満。

			diaryDateTable = new String [size][];

			for (int i=0 ; i<size ; i++)
			{
				diaryDateTable[i] = new String [1];
			}
		}
		else
		{
			// １０件以上。

			diaryDateTable = new String [columnSize][];

			for (int i=0 ; i<diaryDateTable.length ; i++)
			{
				diaryDateTable[i] = new String [diaryDateTableColspan];
			}
		}

		for (int i=0 ; i<columnSize && i<size ; i++)
		{
			for (int j=0 ; j<size ; j+=columnSize)
			{
				if (i + j < size)
				{
					// サイズ内。

					diaryDateTable[i][j / columnSize] =
						diaryReviewCollection.get(i + j).diarydate;
				}
			}
		}

		diaryDate = formatDate.format(new DateTime().getDate());

		return "success";
	}
}
