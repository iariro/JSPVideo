<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 視聴データ変更</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 視聴データ変更</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2>全履歴</h2>
		<table>
			<tr>
				<th>通し番号</td>
				<th>更新日</td>
				<th>レンタル日</td>
				<th>購入日</td>
				<th>注目</td>
				<th>メモ</td>
			</tr>
			<s:iterator value="watchInformations">
				<tr>
					<td align="right"><s:property value="sequence" /></td>
					<td><s:property value="updatedate" /></td>
					<td><s:property value="rentaldate" /></td>
					<td><s:property value="buydate" /></td>
					<td><s:property value="watchMark" /></td>
					<td><s:property value="memo" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>編集</h2>
		<s:form action="updatewatch2" theme="simple">
			<table>
				<tr bgcolor="#eeeeff">
					<td>ID</td>
					<td><input type="text" name="titleId" value="<s:property value="titleId" />" readonly="readonly"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>タイトル</td>
					<td><input type="text" name="title" value="<s:property value="title" />" readonly="readonly" size="50"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>レンタル日</td>
					<td><input type="text" name="rentalDate" value="<s:property value="rentalDate" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>購入日</td>
					<td><input type="text" name="buyDate" value="<s:property value="buyDate" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>メモ</td>
					<td><input type="text" name="memo" value="<s:property value="memo" />"></td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td>注目</td>
					<td>
					<s:if test="%{watch>0}">
						<input type="checkbox" name="watch" checked="checked">
					</s:if>
					<s:else>
						<input type="checkbox" name="watch">
					</s:else>
					</td>
				</tr>
				<tr bgcolor="#eeeeff">
					<td colspan="2" align="center"><input type="submit" value="変更"></td>
				</tr>
			</table>
		</s:form>

		<h2>日記レビュー</h2>
		<table>
			<tr>
				<th colspan="<s:property value="diaryDateTableColspan" />">日記日付</td>
			</tr>
			<s:iterator value="diaryDateTable">
				<tr>
					<s:iterator>
						<td align="right"><s:property /></td>
					</s:iterator>
				</tr>
			</s:iterator>
		</table>

		<s:form action="adddiaryreview" theme="simple">
			<input type="hidden" name="titleId" value="<s:property value="titleId" />" readonly="readonly">
			日付：<input type="text" name="diaryDate" value="<s:property value="diaryDate" />">
			<input type="submit" value="追加">
		</s:form>

		</div>
		</div>
		</div>

	</body>
</html>
