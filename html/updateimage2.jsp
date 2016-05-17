<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 画像コメント変更</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 画像コメント変更</h1>

		<div class=hatena-body>
		<div class=main>
		<table>
			<tr bgcolor="#eeeeff"><td>ID</td><td><s:property value="id" /></td></tr>
			<tr bgcolor="#eeeeff"><td>コメント</td><td><s:property value="comment" /></td></tr>
		</table>
		</div>
		</div>

	</body>
</html>
