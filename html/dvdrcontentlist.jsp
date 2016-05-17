<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - DVD-R一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
	<h1>AV - DVD-R一覧</h1>

	<div class=hatena-body>
	<div class=main>
	<div class=day>

		<table>
			<tr>
			<th>ID</th>
			<th>タイトル</th>
			<th>サムネイル</th>
			<th>メイン画像</th>
			<th>シーン画像</th>
			<th>編集</th>
			</tr>

			<s:iterator value="dvdrDubbingCollection">

			<s:if test="%{titleId>=0}">
				<tr>
					<td><s:property value="position" /></td>
					<td><s:property value="title" /></td>
					<td>
					<img src="/kumagai/image?folder=AVVHSImageFolder&filename=<s:property value="fileName0" />&area=<s:property value="fileName0Area" />" width="98" height="73">
					</td>
					<td>
					<a href="imagelist.action?titleid=<s:property value="titleId" />">
					<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="fileName1" />" width="80" height="59">
					</a>
					</td>

					<s:if test="%{fileName2.length()>0}">
						<td>
						<a href="imagelist.action?titleid=<s:property value="titleId" />">
						<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="fileName2" />" width="80" height="59">
						</a>
						</td>
					</s:if>

					<s:else>
						<td>-</td>
					</s:else>
					<td>-</td>
				</tr>
			</s:if>

			<s:else>
				<tr>
					<td><s:property value="position" /></td>
					<td>-</td>
					<td>
					<img src="/kumagai/image?folder=AVVHSImageFolder&filename=<s:property value="fileName0" />&area=<s:property value="fileName0Area" />" width="98" height="73">
					</td>
					<td>-</td>
					<td>-</td>
					<td>
					<s:form action="adddvdrdubbing1" theme="simple">
						<input type="hidden" name="diskId" value="<s:property value='diskId' />">
						<input type="hidden" name="position" value="<s:property value='position' />">
						<input type="submit" value="編集">
					</s:form>
					</td>
				</tr>
			</s:else>

			</s:iterator>
		</table>

	</div>
	</div>
	</div>

	</body>
</html>
