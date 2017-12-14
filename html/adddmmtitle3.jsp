<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - DMMタイトル追加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - DMMタイトル追加</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<s:property value="title" />をID=<s:property value="titleId" />番で登録しました。<br>

		</div>
		</div>
		</div>

	</body>
</html>
