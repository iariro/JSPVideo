<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - エラーチェック</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - エラーチェック</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

			<h2>参照されていないファイル</h2>

			<ul>
			<s:iterator value="notReferredFiles">
				<li><s:property /><br>
			</s:iterator>
			</ul>

			<h2>存在しないファイル</h2>

			<ul>
			<s:iterator value="notExistFiles">
				<li><s:property /><br>
			</s:iterator>
			</ul>

			<h2>重複参照しているファイル</h2>

			<ul>
			<s:iterator value="duplicateFiles">
				<li>「
				<s:property value="fileName" />」=
				<s:property value="count" /><br>
			</s:iterator>
			</ul>

			<h2>参照されていない画像情報</h2>

			<ul>
			<s:iterator value="noRefferedImage">
				<li><s:property value="id" />
			</s:iterator>
			</ul>

			<h2>視聴されていないタイトル</h2>

			<ul>
			<s:iterator value="notWatchTitles">
				<li><s:property value="title" />
			</s:iterator>
			</ul>

			<h2>タイトルが存在しないタイトルID分視聴情報</h2>

			<ul>
			<s:iterator value="notExistTitleWatch">
				<li><s:property value="titleid" />
			</s:iterator>
			</ul>

			<h2>不正な日付の視聴情報</h2>

			<ul>
			<s:iterator value="invalidDate">
				<li><s:property value="titleid" />
			</s:iterator>
			</ul>

			<h2>１日複数レビュー</h2>

			<ul>
			<s:iterator value="duplicateReview">
				<li><s:property value="date" />=<s:property value="count" />
			</s:iterator>
			</ul>

			<s:if test="%{checkDmmTimebar != null}">
				<h2>DMMタイムバー映り込み</h2>

				<ul>
				<s:iterator value="dmmTimeberImage">
					<li><s:property value="title" /> - <s:property value="filename" />
				</s:iterator>
				</ul>
			</s:if>

			<h2>タイトル メモ</h2>

			<table>
			<tr>
			<th>タイトル</th>
			<th>メモ</th>
			</tr>

			<s:iterator value="titleMemo">
				<tr>
				<td><s:property value="key" /></td>
				<td><s:property value="value" /></td>
				</tr>
			</s:iterator>
			</table>

		</div>
		</div>
		</div>

	</body>
</html>
