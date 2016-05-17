<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>AV - 作品状態履歴</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>AV - 作品状態履歴</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

			<s:property value="watchTable2" />

			<table>
				<tr>
					<th>作品</th>
					<s:iterator value="dates">
						<th width="80"><s:property /></th>
					</s:iterator>
				</tr>

				<s:iterator value="watchTable">

					<tr>
						<td><s:property value="title.title" /></td>
						<s:iterator value="memos">
							<td align="center" bgcolor="<s:property value="top[0]" />">
							<s:property value="top[1]" />
							</td>
						</s:iterator>
					</tr>

				</s:iterator>

				<tr>
					<td align="right">件数</td>
					<s:iterator value="watchTable.activeCount">
						<td align="right"><s:property /></td>
					</s:iterator>
				</tr>
			</table>

		</div>
		</div>
		</div>

	</body>
</html>
