<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv=Content-Style-Type content=text/css>
		<link media=all href="hatena.css" type=text/css rel=stylesheet>
		<title>AVリスト - タイトル追加</title>
	</head>

	<body>
		<h1>AVリスト - タイトル追加</h1>

		<div class=hatena-body>
		<div class=main>
		<s:form action="addtitle2" theme="simple">
			<table>
				<tr bgcolor="#eeeeff"><td>タイトル</td><td><input type="text" name="title" size="50" value="<s:property value='title' />"></td></tr>
				<tr bgcolor="#eeeeff"><td>メディア</td><td>
				<s:select name="type" list="#{ 'DL':'ダウンロード', 'DVD':'DVD', 'VHS':'VHS' }" value="<s:property value='type' />" />
				</td></tr>
				<tr bgcolor="#eeeeff"><td>メモ</td><td><input type="text" name="memo" size="50"></td></tr>
				<tr bgcolor="#eeeeff"><td>リリース日</td><td><input type="text" name="releaseDate" value="<s:property value='releaseDate' />"></td></tr>
				<tr bgcolor="#eeeeff"><td>DMM URL</td><td><input type="text" name="dmmUrl" size="100" value="<s:property value='dmmUrl' />"></td></tr>
				<tr bgcolor="#eeeeff"><td>画像1</td><td><input type="text" name="imageFile1" size="50"></td></tr>
				<tr bgcolor="#eeeeff"><td>画像2</td><td><input type="text" name="imageFile2" size="50"></td></tr>
				<tr bgcolor="#eeeeff"><td>画像3</td><td><input type="text" name="imageFile3" size="50"></td></tr>
				<tr><td colspan="2" align="center"><input type="submit"></td></tr>
			</table>
		</s:form>
		</div>
		</div>

	</body>
</html>
