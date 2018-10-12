<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>AV</title>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>

		<h1>AV検索</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<table>

		<tr>
			<th>一覧</th>
			<th>視聴</th>
			<th>DVD-R</th>
			<th>入力</th>
			<th>管理</th>
		</tr>

		<td>

			<s:form action="titlelistbyyear" theme="simple">
				<input type="hidden" name="page" value="0">
				<s:submit value="全リスト表示 リリース年順" />
			</s:form>

			<s:form action="titlelistbyinput" theme="simple">
				<s:submit value="全リスト表示 入力順" />
				<s:textfield name="date" value="%{date1}" />
			</s:form>

			<s:form action="titlelistbytitle" theme="simple">
				<s:submit value="全リスト表示 タイトル順" />
			</s:form>

			<s:form action="newimagelist" theme="simple">
				<s:submit value="登録画像一覧" />
			</s:form>

		</td>
		<td>

			<s:form action="titlelistbypurchase" theme="simple">
				<s:submit value="全リスト表示 購入順" />
				<s:textfield name="date" value="%{date1}" />
			</s:form>

			<s:form action="titlelistbypurchasenoimage" theme="simple">
				<s:submit value="画像なし購入済みリスト表示" />
				<s:select name="imageCount" list="#{ '9':'9', '8':'8', '7':'7', '6':'6', '5':'5', '4':'4', '3':'3', '2':'2', '1':'1', '0':'0' }" />
			</s:form>

			<s:form action="titlestatushistory" theme="simple">
				<s:submit value="作品の状態履歴一覧表示" />
			</s:form>

			<s:form action="titlelistbywatch" theme="simple">
				<s:submit value="注目作品表示" />
			</s:form>

			<s:form action="generationscatter" theme="simple">
				<s:submit value="視聴作品年代散布図表示" />
			</s:form>

			<s:form action="costumelist" theme="simple">
				<s:submit value="コスチューム一覧" />
				<s:select name="costumeAndScore" list="#{ 'ブルマ,3':'ブルマ３', 'ブルマ,2':'ブルマ２', 'ブルマ,1':'ブルマ１', 'ネクタイ,2':'ネクタイ' }" />
			</s:form>

		</td>
		<td>

			<s:form action="dvdrlist" theme="simple">
				<s:submit value="DVD-Rリスト表示" />
			</s:form>

		</td>
		<td>

			<a href="adddmmtitle1.jsp">DMMタイトル入力</a><br>
			<a href="addtitle1.jsp">タイトル手入力</a><br>

			<s:form action="addbulkimages" theme="simple">
				<s:submit value="画像一括登録" />
			</s:form>

		</td>
		<td>

			<s:form action="yearstat" theme="simple">
				<s:submit value="年ごと統計" />
			</s:form>

			<s:form action="errorcheck" theme="simple">
				<s:submit value="エラーチェック" /><br>
				<input type="checkbox" name="checkDmmTimebar">DMMタイムバー検出
			</s:form>

		</td>
		</tr>
		</table>

		<table>
		<tr>
			<th>昨日</th>
			<th>本日<s:property value="date2" /></th>
			<th>明日</th>
		</tr>

		<tr>
			<td align="center" bgcolor="#dddddd">
			<s:if test="%{title1 != null}">
				<a href="imagelist.action?titleid=<s:property value="title1.id" />">
				<s:if test="%{title1.dmmUrl != null && title1.dmmUrl.length()>0}">
					<img src="<s:property value="title1.dmmImageUrlPl" />" width="100" height="67">
				</s:if>
				<s:else>
					<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="title1.fileName" />" width="100" height="67">
				</s:else>
				</a>
			</s:if>
			<s:else>
				-
			</s:else>
			</td>

			<td align="center">
			<s:if test="%{title2 != null}">
				「<s:property value="title2.title" />」<br>
				<a href="imagelist.action?titleid=<s:property value="title2.id" />">
				<s:if test="%{title2.dmmUrl != null && title2.dmmUrl.length()>0}">
					<img src="<s:property value="title2.dmmImageUrlPl" />" width="400" height="270">
				</s:if>
				<s:else>
					<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="title2.fileName" />" width="400" height="270">
				</s:else>
				</a>
			</s:if>
			<s:else>
				-
			</s:else>
			</td>

			<td align="center" bgcolor="#dddddd">
			<s:if test="%{title3 != null}">
				<a href="imagelist.action?titleid=<s:property value="title3.id" />">
				<s:if test="%{title3.dmmUrl != null && title3.dmmUrl.length()>0}">
					<img src="<s:property value="title3.dmmImageUrlPl" />" width="100" height="67">
				</s:if>
				<s:else>
					<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="title3.fileName" />" width="100" height="67">
				</s:else>
				</a>
			</s:if>
			<s:else>
				-
			</s:else>
			</td>
		</tr>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
