package kumagai.av.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

/**
 * [Tomcat] ファイルアップロードサンプル。
 */
public class UploadServlet
	extends HttpServlet
{
	// (1) ファイルアップロードする時は、doPostメソッドを使います。
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		// (2) アップロードファイルを受け取る準備
		// ディスク領域を利用するアイテムファクトリーを作成
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// tempディレクトリをアイテムファクトリーの一次領域に設定
		ServletContext servletContext = this.getServletConfig().getServletContext();
		factory.setRepository((File)servletContext.getAttribute("javax.servlet.context.tempdir"));

		// ServletFileUploadを作成
		ServletFileUpload upload = new ServletFileUpload(factory);

		try
		{
			// (3) リクエストをファイルアイテムのリストに変換
			List<FileItem> items = upload.parseRequest(request);

			// アップロードパス取得
			String upPath = servletContext.getInitParameter("AVImageFolder");
			byte[] buff = new byte[1024];
			int size = 0;

			System.out.println(items.size());

			for (FileItem item : items)
			{
				// (4) アップロードファイルの処理
				if (!item.isFormField())
				{
					// ファイルをuploadディレクトリに保存
					BufferedInputStream in =
						new BufferedInputStream(item.getInputStream());
					String filename = request.getParameter("filename");
					File f = new File(upPath + item.getName());
					BufferedOutputStream out =
						new BufferedOutputStream(new FileOutputStream(f));

					while ((size = in.read(buff)) > 0)
					{
						out.write(buff, 0, size);
					}

					out.close();
					in.close();

					// アップロードしたファイルへのリンクを表示
					response.getWriter().print("<a href='");
					response.getWriter().print(servletContext.getContextPath() + "/upload/" + item.getName());
					response.getWriter().print("'>" + item.getName() + "</a>");

					// (5) フォームフィールド（ファイル以外）の処理
				}
				else
				{
					// ここでは処理せず、直接requestからgetParamしてもいいと思います。
				}
			}
		}
		catch (FileUploadException e)
		{
			// 例外処理
			e.printStackTrace();
		}

		response.getWriter().flush();
	}
}
