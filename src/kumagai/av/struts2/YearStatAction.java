package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import kumagai.av.ReleaseYearAndCountCollection;
import kumagai.av.WatchYearAndCountCollection;
import kumagai.av.YearAndCount;
import kumagai.av.YearStat;
import kumagai.av.YearStatRecord;
import net.arnx.jsonic.JSON;

/**
 * 年ごと統計表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/yearstat.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class YearStatAction
{
	public YearStat yearStat;
	public Exception exception;
	public String years;
	public String series;

	/**
	 * Highchartsのseries JSON用の型
	 */
	class NameAndData
	{
		public String name;
		public ArrayList<Integer> data = new ArrayList<Integer>();

		/**
		 * nameを割り当て
		 * @param name 軸名
		 */
		public NameAndData(String name)
		{
			this.name = name;
		}
	}

	/**
	 * 年ごと統計表示アクション。
	 * @return 処理結果
	 */
	@Action("yearstat")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		try
		{
			Connection connection =
				DriverManager.getConnection
					(context.getInitParameter("AVSqlserverUrl"));

			ArrayList<YearAndCount> rental1Stat =
				new ReleaseYearAndCountCollection(connection, "rentaldate");

			ArrayList<YearAndCount> buy1Stat =
				new ReleaseYearAndCountCollection(connection, "buydate");

			ArrayList<YearAndCount> rental2Stat =
				new WatchYearAndCountCollection(connection, "rentaldate");

			ArrayList<YearAndCount> buy2Stat =
				new WatchYearAndCountCollection(connection, "buydate");

			connection.close();

			yearStat =
				new YearStat(rental1Stat, buy1Stat, rental2Stat, buy2Stat);
			Collections.reverse(yearStat);

			ArrayList<String> years = new ArrayList<String>();
			NameAndData rental1 = new NameAndData("年毎 レンタル数");
			NameAndData buy1 = new NameAndData("年毎 購入数");
			NameAndData rental2 = new NameAndData("リリース年毎 レンタル数");
			NameAndData buy2 = new NameAndData("リリース年毎 購入数");

			for (YearStatRecord yearStatRecord : yearStat)
			{
				years.add(String.format("%d年", yearStatRecord.year));
				rental1.data.add(yearStatRecord.rental1);
				buy1.data.add(yearStatRecord.buy1);
				rental2.data.add(yearStatRecord.rental2);
				buy2.data.add(yearStatRecord.buy2);
			}

			this.years = JSON.encode(years);
			this.series = JSON.encode(new NameAndData [] {rental2, buy2, rental1, buy1});

			return "success";
		}
		catch (SQLServerException exception)
		{
			this.exception = exception;

			return "error";
		}
	}
}