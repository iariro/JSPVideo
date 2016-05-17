<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 視聴データ変更</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 視聴データ変更</h1>

		<div class=hatena-body>
		<div class=main>
			<table cellpadding="5">
				<tr bgcolor="#eeeeff"><td>処理</td><td><s:property value="query" /></td></tr>
				<tr bgcolor="#eeeeff"><td>ID</td><td><s:property value="titleId" /></td></tr>
				<tr bgcolor="#eeeeff"><td>タイトル</td><td><s:property value="title" /></td></tr>
				<tr bgcolor="#eeeeff"><td>レンタル日</td><td><s:property value="rentalDate" /></td></tr>
				<tr bgcolor="#eeeeff"><td>購入日</td><td><s:property value="buyDate" /></td></tr>
				<tr bgcolor="#eeeeff"><td>メモ</td><td><s:property value="memo" /></td></tr>
				<tr bgcolor="#eeeeff"><td>注目フラグ</td><td><s:property value="watch" /></td></tr>
			</table>
		</div>
		</div>
	</body>
</html>
