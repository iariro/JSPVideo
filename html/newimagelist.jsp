<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 画像一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 画像一覧</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

			<s:iterator value="newImageFilst">

				<s:property value="filepath" /> - <s:property value="updateDatetime" /><br>
				<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="filepath" />"><br>

			</s:iterator>

		</div>
		</div>
		</div>

	</body>
</html>
