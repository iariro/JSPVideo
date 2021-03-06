package kumagai.av.struts2;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.av.TitleCollection;

/**
 * 視聴した作品の年代散布図表示アクション。
 * @author kumagai
 */
@Namespace("/av")
@Results
({
	@Result(name="success", location="/av/generationscatter.jsp"),
	@Result(name="error", location="/av/error.jsp")
})
public class GenerationScatterAction
{
	public String purchaseVhsGenerationScatterData;
	public String rentalVhsGenerationScatterData;
	public String purchaseDvdGenerationScatterData;
	public String rentalDvdGenerationScatterData;
	public String purchaseDLGenerationScatterData;
	public String rentalDLGenerationScatterData;

	/**
	 * 視聴した作品の年代散布図表示アクション。
	 * @return 処理結果
	 */
	@Action("generationscatter")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		String url = context.getInitParameter("AVSqlserverUrl");

		if (url != null)
		{
			// パラメータあり

			DriverManager.registerDriver(new SQLServerDriver());

			Connection connection = DriverManager.getConnection(url);
			TitleCollection titleCollection = new TitleCollection(connection, false, false, "buydate", null, null);
			connection.close();

			purchaseVhsGenerationScatterData = titleCollection.getPurchaseGenerationScatterData("VHS");
			rentalVhsGenerationScatterData = titleCollection.getRentalGenerationScatterData("VHS");
			purchaseDvdGenerationScatterData = titleCollection.getPurchaseGenerationScatterData("DVD");
			rentalDvdGenerationScatterData = titleCollection.getRentalGenerationScatterData("DVD");
			purchaseDLGenerationScatterData = titleCollection.getPurchaseGenerationScatterData("DL");
			rentalDLGenerationScatterData = titleCollection.getRentalGenerationScatterData("DL");

			return "success";
		}
		else
		{
			// パラメータなし

			return "error";
		}
	}
}
