<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 画像削除</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 画像削除</h1>

		<div class=hatena-body>
		<div class=main>
		画像ID=<s:property value="imageid" />を削除しました。<br>
		ファイル名=<s:property value="filename" /><br>
		</div>
		</div>

	</body>
</html>
