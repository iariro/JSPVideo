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

		<s:form action="uploadimage" method="post" enctype="multipart/form-data" theme="simple">
			<input type="file" name="uploadfile" multiple="multiple" size="75" />
			<input type="hidden" name="fileName" value="<s:property value='fileName' />">
			余白：
			<s:select name="uploadImageMargin" list="#{ '0,0':'0,0', '8,0':'8,0', '171,0':'171,0', '179,98':'179,98' }" />
			<input type="submit" value="アップロード" />
		</s:form>
		</div>
		</div>

	</body>
</html>
