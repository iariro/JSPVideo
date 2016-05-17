<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - タイトル追加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - タイトル追加</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<s:property value="title" />を
		ID=<s:property value="newId" />番で登録しました。<br>
		ファイル名：<s:property value="imageFile1" />

		</div>
		</div>
		</div>

	</body>
</html>
