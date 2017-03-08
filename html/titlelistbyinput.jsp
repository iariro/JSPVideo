<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>全タイトル一覧 - 入力日順</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>全タイトル一覧 - 入力日順</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<table>
			<tr>
				<th>ID</th>
				<th width="250">タイトル</th>
				<th>検索</th>
				<th>メディア</th>
				<th>画像</th>
				<th>リリース日</th>
				<th width="80">レンタル日</th>
				<th width="80">購入日</th>
				<th>レビュー</th>
				<th>編集</th>
				<th>日記</th>
			</tr>

			<s:iterator value="titleCollection" var="title">
				<s:if test="%{isSure}">
					<tr>
				</s:if>
				<s:else>
					<tr bgcolor="lightgray">
				</s:else>

				<td><s:property value="id" /></td>
				<td><s:property value="title" /></td>
				<td>
				<a href="http://search.yahoo.co.jp/search?p=<s:property value="title" />&ei=UTF-8&fr=mozff">Y!</a>
				<a href="http://auctions.search.yahoo.co.jp/search?p=<s:property value="title" />&auccat=26164&aq=-1&oq=&ei=UTF-8&tab_ex=commerce&slider=0">Y!</a>

				<s:if test="%{dmmUrl!=null}">
				<a href="<s:property value="dmmUrl" />">DMM</a>
				</s:if>
				<s:else>
				<a href="http://www.dmm.co.jp/search/=/searchstr=<s:property value="title" />/analyze=V1EBC1YOUAo_/n1=FgRCTw9VBA4GCF5WXA__/n2=Aw1fVhQKX19XC15nV0AC/sort=ranking/">DMM</a>
				</s:else>

				<a href="http://www.arzon.jp/itemlist.html?t=&m=all&s=&q=<s:property value="title" />">arzon</a>
				</td>
				<td><s:property value="media" /></td>
				<td><a href="imagelist.action?titleid=<s:property value="id" />">
					<s:if test="%{dmmUrl.length()>0}">
					<img src="<s:property value="dmmImageUrlPs" />" width="78" height="100">
					</s:if>
					<s:else>
					<img src="/kumagai/image?folder=AVImageFolder&filename=<s:property value="fileName" />" width="80" height="59">
					</s:else>
				</a></td>
				<td><s:property value="releaseDateString" /></td>

				<td>
					<s:if test="%{rentalDate.length()>0}">
					<s:if test="%{rentalDate2.length()>0}">
						<a href="../diary/oneday1.action?date=<s:property value='rentalDate2' />&usetag=true&category=AV鑑賞"><s:property value="rentalDate" /></a>
					</s:if>
					<s:else>
						<s:property value="rentalDate" />
					</s:else>
					</s:if>
				</td>
				<td>
					<s:if test="%{buyDate.length()>0}">
					<s:if test="%{buyDate2.length()>0}">
						<a href="../diary/oneday1.action?date=<s:property value='buyDate2' />&usetag=true&category=AV鑑賞"><s:property value="buyDate" /></a>
					</s:if>
					<s:else>
						<s:property value="buyDate" />
					</s:else>
					</s:if>
				</td>

				<td align="right">
					<s:property value="reviewCount" />
				</td>

				<td>
				<s:form action="updatetitle1" theme="simple">
					<input type="hidden" name="titleId" value="<s:property value='id' />">
					<input type="submit" value="作品">
				</s:form>

				<s:form action="updatewatch1" theme="simple">
					<input type="hidden" name="titleId" value="<s:property value='id' />">
					<input type="submit" value="視聴">
				</s:form>
				</td>

				<td>
				<s:form action="oneday1" namespace="/diary" theme="simple">
					<input type="hidden" name="category" value="AV鑑賞">
					<input type="hidden" name="date" value="<s:property value="diarydates" />">
					<s:submit value="日記" />
				</s:form>
				</td>

				</tr>
			</s:iterator>
		</table>

		<s:property value="size" />件

		</div>
		</div>
		</div>

	</body>
</html>
