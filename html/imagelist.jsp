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

			<s:if test="%{dmmImageUrl!=null}">
				<img src="<s:property value="dmmImageUrl" />">
			</s:if>
			<br>
			<br>

			<s:iterator value="images">

				<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="fileName" />"><br>
				<s:if test="%{comment.length()>0}">
					<s:property value="comment" /><br>
				</s:if>

				<table style='margin:0px; border:0px;'><tr>

				<td style='padding:0px; border:0px;'>
				<s:form action="addcostume1" theme="simple">
					<input type="hidden" name="titleid" value="<s:property value='titleid' />">
					<input type="hidden" name="imageid" value="<s:property value='id' />">
					<input type="submit" value="コスチューム">
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
				<s:form action="deleteimage" theme="simple">
					<input type="hidden" name="imageid" value="<s:property value='id' />">
					<input type="hidden" name="filename" value="<s:property value='fileName' />">
					<input type="submit" value="削除">
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
