<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - コスチューム情報追加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - コスチューム情報追加</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<s:form action="addcostume2" theme="simple">

			<table>

				<tr>
					<td>タイトルID</td>
					<td><input type="text" name="titleid" value="<s:property value="titleid" />" readonly="readonly"></td>
				</tr>
				<tr>
					<td>画像ID</td>
					<td><input type="text" name="imageid" value="<s:property value="imageid" />" readonly="readonly"></td>
				</tr>
				<tr>
					<td>コスチューム</td>
					<td><input type="text" name="costume" value="ブルマ"></td>
				</tr>
				<tr>
					<td>特徴</td>
					<td><input type="text" name="feature" value=""></td>
				</tr>
				<tr>
					<td>評価</td>
					<td><s:radio list="#{1:'見所なし', 2:'まあまあ', 3:'見応えあり'}" name="score" value="2"></s:radio></td>
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
