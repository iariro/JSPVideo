<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - DVD-Rダビング</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - DVD-Rダビング</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<s:form action="adddvdrdubbing2" theme="simple">

			<table>

				<tr>
					<td>ディスク</td>
					<td><s:property value="title" /></td>
				</tr>
				<tr>
					<td>ディスクID</td>
					<td><input type="text" name="diskId" value="<s:property value="diskId" />" readonly="readonly"></td>
				</tr>
				<tr>
					<td>トラック</td>
					<td><input type="text" name="position" value="<s:property value="position" />" readonly="readonly"></td>
				</tr>
				<tr>
					<td>オリジナルタイトル</td>
					<td>
						<s:select name="titleId" list="idAndTitleCollection" listKey="id" listValue="title" />
					</td>
				</tr>
				<tr>
					<td>画像番号</td>
					<td><input type="text" name="imageIndex" value="0"></td>
				</tr>
				<tr>
					<td>メモ</td>
					<td><input type="text" name="memo" value=""></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"></td>
				</tr>

			</table>

		</s:form>

		</div>
		</div>
		</div>

	</body>
</html>
