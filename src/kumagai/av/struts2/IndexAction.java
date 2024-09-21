package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import ktool.datetime.DateTime;
import kumagai.av.DBInfo;
import kumagai.av.Title3;
import kumagai.av.Title3ShuffleCollection;
import kumagai.av.TitleCollection;

/**
 * トップページ表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/index.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class IndexAction
{
	static private final SimpleDateFormat formatDate1;
	static private final SimpleDateFormat formatDate2;

	/**
	 * 日付書式オブジェクトを構築。
	 */
	static
	{
		formatDate1 = new SimpleDateFormat();
		formatDate2 = new SimpleDateFormat();

		formatDate1.applyPattern("yyyy/MM/dd");
		formatDate2.applyPattern("yyyy/MM/dd(E)");
	}

	public static void main(String[] args) throws SQLException, ParseException
	{
		IndexAction action = new IndexAction();
		Connection connection = DriverManager.getConnection(DBInfo.dbUrl);
		DateTime today = new DateTime();
		action.readTitleCollection(0, today, today, connection);
		System.out.println(action.title1);
		System.out.println(action.title2);
		System.out.println(action.title3);
	}

	public Title3 title1;
	public Title3 title2;
	public Title3 title3;
	public String date1;
	public String date2;
	public String message;
	public Exception exception;

	/**
	 * トップページ表示アクション。
	 * @return 処理結果
	 */
	@Action("index")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		if (context.getInitParameter("AVSqlserverUrl") == null ||
			context.getInitParameter("AVRandomOriginDate") == null ||
			context.getInitParameter("AVRandomAdjust") == null)
		{
			message = "必要なパラメータ定義が揃っていません";

			return "error";
		}

		DriverManager.registerDriver(new SQLServerDriver());

		try
		{
			String originDateString =
				context.getInitParameter("AVRandomOriginDate");
			int randomAdjust =
				Integer.valueOf(context.getInitParameter("AVRandomAdjust"));

			DateTime originDate = DateTime.parseDateString(originDateString);

			DateTime today = new DateTime();

			Connection connection =
					DriverManager.getConnection
						(context.getInitParameter("AVSqlserverUrl"));

			readTitleCollection(randomAdjust, originDate, today, connection);

			connection.close();

			return "success";
		}
		catch (SQLServerException exception)
		{
			this.exception = exception;

			return "error";
		}
	}

	/**
	 * タイトル情報を読み込む
	 * @param randomAdjust 日補正
	 * @param originDate 基準日
	 * @param today 今日の日付
	 * @param connection DB接続オブジェクト
	 */
	private void readTitleCollection(int randomAdjust, DateTime originDate, DateTime today, Connection connection)
			throws SQLException, ParseException
	{
		ArrayList<Title3> titleCollection =
			new TitleCollection(
				connection,
				true,
				true,
				"buydate desc, indexinday",
				null,
				null);

		Title3ShuffleCollection titleCollectionRandom =
			new Title3ShuffleCollection(titleCollection, originDate, today, randomAdjust);

		date1 = formatDate1.format(today.getDate());
		date2 = formatDate2.format(today.getDate());

		title1 = titleCollectionRandom.getTitle1();
		title2 = titleCollectionRandom.getTitle2();
		title3 = titleCollectionRandom.getTitle3();
	}
}
