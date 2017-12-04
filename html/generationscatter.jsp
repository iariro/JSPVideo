<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
		<title>SMART Viewer</title>
	</head>

	<body>
		<div id="chart" style="width:1300px; height:700px"></div>
		<script type="text/javascript">
		function draw()
		{
			Highcharts.setOptions({ global: { useUTC: false } });
			options =
			{
				chart: {'type': 'scatter', renderTo: 'chart', zoomType:'xy'},
				title: {text: '作品年代散布図'},
				xAxis: {title: '視聴時期', type: 'datetime'},
				yAxis: {title: '作品年代', type: 'datetime'},
				series:
					[
					{name:'購入', data:[ <s:property value="purchaseGenerationScatterData" /> ]},
					{name:'レンタル', data:[ <s:property value="rentalGenerationScatterData" /> ]}
					]
			};
			chart = new Highcharts.Chart(options);
		};
		document.body.onload = draw();
		</script>
	</body>
</html>
