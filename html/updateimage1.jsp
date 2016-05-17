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
		<s:form action="updateimage2" theme="simple">

			<table>
				<tr bgcolor="#eeeeff">
					<td>ID</td>
					<td><input type="text" name="id" value="<s:property value="id" />" readonly="readonly"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>コメント</td>
					<td><input type="text" name="comment" value="<s:property value="comment" />" size="50"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td colspan="2" align="center"><s:submit value="変更" /></td>
				</tr>
			</table>

		</s:form>
		</div>
		</div>

	</body>
</html>
