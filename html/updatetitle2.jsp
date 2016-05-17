<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 作品データ変更</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 作品データ変更</h1>

		<div class=hatena-body>
		<div class=main>
		<table>
			<tr bgcolor="#eeeeff"><td>ID</td><td><s:property value="titleId" /></td></tr>
			<tr bgcolor="#eeeeff"><td>タイトル</td><td><s:property value="title" /></td></tr>
			<tr bgcolor="#eeeeff"><td>短縮タイトル</td><td><s:property value="shortTitle" /></td></tr>
			<tr bgcolor="#eeeeff"><td>メディア</td><td><s:property value="type" /></td></tr>
			<tr bgcolor="#eeeeff"><td>リリース日</td><td><s:property value="releaseDate" /></td></tr>
			<tr bgcolor="#eeeeff"><td>メモ</td><td><s:property value="memo" /></td></tr>
		</table>
		</div>
		</div>

	</body>
</html>
