<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 日記レビュー情報追加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 日記レビュー情報追加</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		以下を登録しました。<br>
		<table>
			<tr><td>タイトルID</td><td><s:property value="titleId" /></td></tr>
			<tr><td>レビュー日記日付</td><td><s:property value="diaryDate" /></td></tr>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
