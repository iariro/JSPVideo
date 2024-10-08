<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 作品データ変更</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 作品データ変更</h1>

		<div class=hatena-body>
		<div class=main>
		<s:form action="updatetitle2" theme="simple">
			<table>
				<tr bgcolor="#eeeeff">
					<td>ID</td>
					<td><input type="text" name="titleId" value="<s:property value="titleId" />" readonly="readonly"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>タイトル</td>
					<td><input type="text" name="title" value="<s:property value="title" />" size="50"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>短縮タイトル</td>
					<td><input type="text" name="shortTitle" value="<s:property value="shortTitle" />" size="50"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>メディア</td>
					<td><input type="text" name="type" value="<s:property value="type" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>リリース日</td>
					<td><input type="text" name="releaseDate" value="<s:property value="releaseDate" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>メモ</td>
					<td><input type="text" name="memo" value="<s:property value="memo" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>DMM URL</td>
					<td><input type="text" name="dmmUrl" value="<s:property value="dmmUrl" />" size="100"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>DMMの画像を表示する</td>
					<td>
						<s:if test="useDmmTopImage"><input type="checkbox" name="useDmmTopImage" checked></s:if>
						<s:else><input type="checkbox" name="useDmmTopImage"></s:else>
					</td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>画像１</td>
					<td><input type="text" name="imageFile1" value="<s:property value="imageFile1" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>画像２</td>
					<td><input type="text" name="imageFile2" value="<s:property value="imageFile2" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>画像３</td>
					<td><input type="text" name="imageFile3" value="<s:property value="imageFile3" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td colspan="2" align="center"><s:submit value="変更" /></td>
				</tr>
			</table>
		</s:form>

		<s:form action="uploadimage" method="post" enctype="multipart/form-data" theme="simple">
			<input type="file" name="uploadfile" multiple="multiple" size="75" />
			余白：
			<s:select name="uploadImageMargin" list="#{ '0,0':'0,0', '8,0':'8,0', '171,0':'171,0', '177,96':'177,96', 'auto':'auto' }" />
			<input type="hidden" name="titleId" value="<s:property value='titleId' />">
			<input type="hidden" name="dmmUrlCid" value="<s:property value='dmmUrlCid' />">
			<input type="submit" value="アップロード" />
		</s:form>
		</div>
		</div>

	</body>
</html>
