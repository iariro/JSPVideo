<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 画像アップロード</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 画像アップロード</h1>

		<div class=hatena-body>
		<div class=main>
		<s:property value='createFileName' />をアップロードしました。<br>
		<s:property value='message' /><br>
		</div>
		</div>

	</body>
</html>
