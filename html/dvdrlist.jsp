<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>DVD-R一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>DVD-R一覧</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<table>
			<tr>
				<th>タイトル</th>
				<th>特定済み数</th>
				<th>タイトル数</th>
				<th>表示</th>
			</tr>
			<s:iterator value="dvdrCollection">
				<tr>
				<td><s:property value="title" /></td>
				<td align="right"><s:property value="tokuteiCount" />／</td>
				<td><s:property value="trackNum" /></td>
				<td>
				<s:form action="dvdrcontentlist" theme="simple">
					<input type="hidden" name="diskId" value="<s:property value='diskId' />">
					<s:submit value="表示" />
				</s:form>
				</td>
				</tr>
			</s:iterator>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
