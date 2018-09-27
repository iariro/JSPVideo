package kumagai.av.upload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
 
public class PostTask {
    private final static String TWO_HYPHEN = "--";
    private final static String EOL = "\r\n";
    private final static String BOURDARY = String.format("%x", new Random().hashCode());
    private final static String CHARSET = "UTF-8";
 
    public String post(String requestUrl, HashMap<String, Object> postData)
	{
        String result = "";
 
        // 送信するコンテンツを成形する
        StringBuilder contentsBuilder = new StringBuilder();
        String closingContents = "";
        int iContentsLength = 0;
        String fileTagName = "";
        String filePath = "";
        File file = null;
        FileInputStream fis = null;
 
        for (Map.Entry<String, Object> data : postData.entrySet()) {
            String key = data.getKey();
            Object val = data.getValue();
 
            // ファイル以外
            if (val instanceof String) {
                contentsBuilder.append(String.format("%s%s%s", TWO_HYPHEN, BOURDARY, EOL));
                contentsBuilder.append(String.format("Content-Disposition: form-data; name=\"%s\"%s", key, EOL));
                contentsBuilder.append(EOL);
                contentsBuilder.append(val);
                contentsBuilder.append(EOL);
            }
            // ファイル
            else {
                // ファイル情報を保持しておく
                file = (File)val;
            }
        }
 
        // ファイル情報のセット
        contentsBuilder.append(String.format("%s%s%s", TWO_HYPHEN, BOURDARY, EOL));
        contentsBuilder.append(String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"%s", fileTagName, filePath, EOL));
 
        // ファイルがあるとき
        if (file != null) {
            // ファイルサイズの取得
            iContentsLength += file.length();
 
            // MIME取得
            int iExtPos = filePath.lastIndexOf(".");
            String ext = (iExtPos > 0) ? filePath.substring(iExtPos + 1) : "";
            String mime = "image/png";
 
            contentsBuilder.append(String.format("Content-Type: %s%s", mime, EOL));
        }
        // ファイルがないとき
        else {
            contentsBuilder.append(String.format("Content-Type: application/octet-stream%s", EOL));
        }
 
        contentsBuilder.append(EOL);
        closingContents = String.format("%s%s%s%s%s", EOL, TWO_HYPHEN, BOURDARY, TWO_HYPHEN, EOL);
 
        // コンテンツの長さを取得
        try {
            // StringBuilderを文字列に変化してからバイト長を取得しないと
            // 実際送ったサイズと異なる場合があり、コンテンツを正しく送信できなくなる
            iContentsLength += contentsBuilder.toString().getBytes(CHARSET).length;
            iContentsLength += closingContents.getBytes(CHARSET).length;
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
 
 
        // サーバへ接続する
        HttpURLConnection connection = null;
        DataOutputStream os = null;
        BufferedReader br = null;
 
        try {
            URL url = new URL(requestUrl);
 
            connection = (HttpURLConnection)url.openConnection();
 
            connection.setDoInput(true);
            connection.setDoOutput(true);
 
            // キャッシュを使用しない
            connection.setUseCaches(false);
 
            // HTTPストリーミングを有効にする
            connection.setChunkedStreamingMode(0);
 
            // リクエストヘッダを設定する
            // リクエストメソッドの設定
            connection.setRequestMethod("POST");
 
            // 持続接続を設定
            connection.setRequestProperty("Connection", "Keep-Alive");
 
            // ユーザエージェントの設定（必須ではない）
            connection.setRequestProperty("User-Agent", "AVImageUploader");
 
            // POSTデータの形式を設定
            connection.setRequestProperty("Content-Type", String.format("multipart/form-data; boundary=%s", BOURDARY));
            // POSTデータの長さを設定
            connection.setRequestProperty("Content-Length", String.valueOf(iContentsLength));
 
 
            // データを送信する
            os = new DataOutputStream(connection.getOutputStream());
            os.writeBytes(contentsBuilder.toString());
 
            // ファイルの送信
            if (file != null) {
                byte buffer[] = new byte[1024];
                fis = new FileInputStream(file);
 
                while (fis.read(buffer, 0, buffer.length) > -1) {
                    os.write(buffer, 0, buffer.length);
                }
            }
 
            os.writeBytes(closingContents);
 
 
            // レスポンスを受信する
            int iResponseCode = connection.getResponseCode();
 
            // 接続が確立したとき
            if (iResponseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder resultBuilder = new StringBuilder();
                String line = "";
 
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
                // レスポンスの読み込み
                while ((line = br.readLine()) != null) {
                    resultBuilder.append(String.format("%s%s", line, EOL));
                }
                result = resultBuilder.toString();
            }
            // 接続が確立できなかったとき
            else {
                result = String.valueOf(iResponseCode);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 開いたら閉じる
            try {
                if (br != null) br.close();
                if (os != null) {
                    os.flush();
                    os.close();
                }
                if (fis != null) fis.close();
                if (connection != null) connection.disconnect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
 
        // 代入したレスポンスを返す
        return result;
    }
}
