<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - DVD-Rダビング情報追加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - DVD-Rダビング情報追加</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		以下を登録しました。<br>
		<table>
			<tr><td>ディスクID</td><td><s:property value="diskId" /></td></tr>
			<tr><td>トラック数</td><td><s:property value="position" /></td></tr>
			<tr><td>タイトルID</td><td><s:property value="titleId" /></td></tr>
			<tr><td>画像インデックス</td><td><s:property value="imageIndex" /></td></tr>
			<tr><td>メモ</td><td><s:property value="memo" /></td></tr>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
