package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.CostumeCollection;
import kumagai.av.CostumeGroup;
import kumagai.av.FeatureAndScore;

/**
 * コスチューム情報一覧表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Result(name="success", location="/av/costumelist.jsp")
public class CostumeListAction
{
	public String costumeAndScore;
	public ArrayList<CostumeGroup> costumeGroups;
	public ArrayList<FeatureAndScore> statistics;

	/**
	 * コスチューム情報一覧表示アクション。
	 * @return 処理結果
	 */
	@Action("costumelist")
    public String execute()
    	throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("AVSqlserverUrl"));

		CostumeCollection costumes = new CostumeCollection(connection, null);

		connection.close();

		String [] costumeAndScore2 = costumeAndScore.split(",");

		costumeGroups =
			costumes.getAsGroup
				(costumeAndScore2[0], Integer.valueOf(costumeAndScore2[1]));

		statistics = costumes.getStatistics();

		return "success";
	}
}
