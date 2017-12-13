<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv=Content-Style-Type content=text/css>
		<link media=all href="hatena.css" type=text/css rel=stylesheet>
		<title>AVリスト - DMMタイトル追加</title>
	</head>

	<body>
		<h1>AVリスト - DMMタイトル追加</h1>

		<div class=hatena-body>
		<div class=main>
		<s:form action="addtitle2" theme="simple">
			URL : <input type="text" name="dmmUrl" size="50">
			<input type="submit">
		</s:form>
		</div>
		</div>

	</body>
</html>
