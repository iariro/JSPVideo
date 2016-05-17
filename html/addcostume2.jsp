<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - コスチューム情報追加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - コスチューム情報追加</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		以下を登録しました。<br>
		<table>
			<tr><td>タイトルID</td><td><s:property value="titleid" /></td></tr>
			<tr><td>画像ID</td><td><s:property value="imageid" /></td></tr>
			<tr><td>コスチューム</td><td><s:property value="costume" /></td></tr>
			<tr><td>特徴</td><td><s:property value="feature" /></td></tr>
			<tr><td>評価</td><td><s:property value="score" /></td></tr>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
