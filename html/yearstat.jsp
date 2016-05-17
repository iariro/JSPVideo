<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>AV</title>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>

		<h1>年ごと統計</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<table>
			<tr>
				<th>年</th>
				<th width="80">年毎<br>レンタル数</th>
				<th width="80">年毎<br>購入数</th>
				<th width="80">リリース年毎<br>レンタル数</th>
				<th width="80">リリース年毎<br>購入数</th>
			</tr>

			<s:iterator value="yearStat">
				<tr>
					<td><s:property value="year" />年</td>
					<td align="right"><s:property value="rental2" />本</td>
					<td align="right"><s:property value="buy2" />本</td>
					<td align="right"><s:property value="rental1" />本</td>
					<td align="right"><s:property value="buy1" />本</td>
				</tr>
			</s:iterator>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
