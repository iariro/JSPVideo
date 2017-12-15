<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 画像更新</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 画像更新</h1>

		<div class=hatena-body>
		<div class=main>
		<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="fileName" />"><br>
		<s:property value="fileName" />をアップロードしました。<br>
		</div>
		</div>

	</body>
</html>
