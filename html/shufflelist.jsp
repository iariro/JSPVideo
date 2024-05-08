<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>シャッフルリスト</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>シャッフルリスト</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<s:property value="titleCollectionRandom.size" />件<br>

		<table>
			<tr>
				<th></th>
				<th>日</th>
				<th>タイトル</th>
			</tr>

			<s:iterator value="titleCollectionRandom" var="title">
				<tr>
				<td><s:if test="today">★</s:if></td>
				<td bgcolor='<s:property value="bgcolor" />'><s:property value="date" /></td>
				<td><s:property value="title" /></td>
				</tr>
			</s:iterator>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
