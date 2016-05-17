<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - コスチューム一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - コスチューム一覧</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

			<table>
			<tr>
				<th></th>
				<th>見所なし</th>
				<th>まあまあ</th>
				<th>見応えあり</th>
				<th>合計</th>
			</tr>

			<s:iterator value="statistics">
				<tr>
					<td><s:property value="feature" /></td>
					<s:iterator value="score">
						<td align="right" width="70"><s:property /></td>
					</s:iterator>
					<td align="right" width="70"><s:property value="sum" /></td>
				</tr>
			</s:iterator>
			</table>

			<s:iterator value="costumeGroups">
			<h2><s:property value="costume" /> - <s:property value="score" /></h2>
			<s:iterator>

				<h3><s:property value="title" /></h3>
				<blockquote>
				<s:property value="feature" /><br>
				<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="filename" />">

				<s:form action="deletecostume" theme="simple">
					<input type="hidden" name="costumeid" value="<s:property value='costumeid' />">
					<input type="submit" value="削除">
				</s:form>
				</blockquote>

			</s:iterator>
			</s:iterator>

		</div>
		</div>
		</div>

	</body>
</html>
