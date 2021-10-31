<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
		<title>AV</title>
	</head>

	<body>
		<div id="chart" style="width:1300px; height:700px"></div>
		<script type="text/javascript">
		function draw()
		{
			options =
			{
				chart: {type: 'column', renderTo: 'chart', plotBackgroundColor: 'lightgray'},
				title: {text: '年ごと統計'},
				xAxis: {categories: <s:property value="years" escapeHtml="false" /> },
				yAxis: {title: '作品数'},
				series: <s:property value="series" escapeHtml="false" />
			};
			chart = new Highcharts.Chart(options);
		};
		document.body.onload = draw();
		</script>

	</body>
</html>
