<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 画像一括登録</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 画像一括登録</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2>以下を登録しました</h2>
		<table>
			<tr>
				<th>タイトルID</td>
				<th>画像インデックス</td>
				<th>ファイル名</td>
			</tr>
			<s:iterator value="addedImage">
			<tr>
				<td><s:property value="titleId" /></td>
				<td><s:property value="position" /></td>
				<td><s:property value="fileName" /></td>
			</tr>
			</s:iterator>
		</table>

		<s:if test="%{notAddImage.length()>0}">
		<h2>以下を登録しませんでした</h2>
		<table>
			<tr>
				<th>タイトルID</td>
				<th>画像インデックス</td>
				<th>ファイル名</td>
			</tr>
			<s:iterator value="notAddImage">
			<tr>
				<td align="right"><s:property value="titleId" /></td>
				<td align="right"><s:property value="position" /></td>
				<td><s:property value="fileName" /></td>
			</tr>
			</s:iterator>
		</table>
		</s:if>

		</div>
		</div>
		</div>

	</body>
</html>
