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

			<h2><s:property value="title" /></h2>
			<br>

			<s:if test="%{useDmmTopImage}">
				<img src="<s:property value="dmmImageUrl" />">
				<br>
				<br>
			</s:if>

			<s:iterator value="images">

				<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="fileName" />"><br>
				<s:if test="%{comment.length()>0}">
					<s:property value="comment" /><br>
				</s:if>

				<s:if test="%{costumes.size() > 0}">
					コスチューム：
					<table><tr>
					<s:iterator value="costumes">
						<td><s:property value='costume' /></td>
						<td><s:property value='feature' /></td>
						<td><s:property value='score' /></td>
					</s:iterator>
					</tr></table>
				</s:if>

				<table style='margin:0px; border:0px;'><tr>

				<td style='padding:0px; border:0px;'>
				<s:form action="replaceimage1" theme="simple">
					<input type="hidden" name="imageid" value="<s:property value='id' />">
					<input type="hidden" name="fileName" value="<s:property value='fileName' />">
					<input type="submit" value="画像更新" />
				</s:form>
				</td>

				<td style='padding:0px; border:0px;'>
				<s:form action="updateimage1" theme="simple">
					<input type="hidden" name="id" value="<s:property value='id' />">
					<input type="hidden" name="comment" value="<s:property value='comment' />">
					<input type="submit" value="コメント編集">
				</s:form>
				</td>

				<td style='padding:0px; border:0px;'>
				<s:form action="addcostume1" theme="simple">
					<input type="hidden" name="titleid" value="<s:property value='titleid' />">
					<input type="hidden" name="imageid" value="<s:property value='id' />">
					<input type="submit" value="コスチューム">
				</s:form>
				</td>

				<td style='padding:0px; border:0px;'>
				<s:form action="deleteimage" theme="simple">
					<input type="hidden" name="imageid" value="<s:property value='id' />">
					<input type="hidden" name="filename" value="<s:property value='fileName' />">
					<input type="submit" value="削除">
				</s:form>
				</td>

				<td style='padding:0px; border:0px;'>
				<s:form action="changeimageposition" theme="simple">
					<input type="hidden" name="imageId1" value="<s:property value='id' />">
					<input type="hidden" name="imageId2" value="<s:property value='idBefore' />">
					<input type="submit" value="↑">
				</s:form>
				</td>

				<td style='padding:0px; border:0px;'>
				<s:form action="changeimageposition" theme="simple">
					<input type="hidden" name="imageId1" value="<s:property value='id' />">
					<input type="hidden" name="imageId2" value="<s:property value='idAfter' />">
					<input type="submit" value="↓">
				</s:form>
				</td>

				</tr>
				</table>

			</s:iterator>

		</div>
		</div>
		</div>

	</body>
</html>
