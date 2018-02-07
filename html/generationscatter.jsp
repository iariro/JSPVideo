<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
		<script type="text/javascript" src="dateformat.js"></script>
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
				chart: {'type': 'scatter', renderTo: 'chart', zoomType:'xy', plotBackgroundColor: 'lightgray'},
				title: {text: '視聴作品年代散布図'},
				xAxis: {title: '視聴時期', type: 'datetime'},
				yAxis: {title: '作品年代', type: 'datetime'},
				tooltip: { formatter: function () { return '<b>' + this.point.name + '</b><br/>' + dateFormat(new Date(this.point.x));}},
				series:
					[
					{name:'VHSレンタル', data:[ <s:property value="rentalVhsGenerationScatterData" /> ]},
					{name:'VHS購入', data:[ <s:property value="purchaseVhsGenerationScatterData" /> ]},
					{name:'DVDレンタル', data:[ <s:property value="rentalDvdGenerationScatterData" /> ]},
					{name:'DVD購入', data:[ <s:property value="purchaseDvdGenerationScatterData" /> ]},
					{name:'ネットレンタル', data:[ <s:property value="rentalDLGenerationScatterData" /> ]},
					{name:'ネット購入', data:[ <s:property value="purchaseDLGenerationScatterData" /> ]}
					]
			};
			chart = new Highcharts.Chart(options);
		};
		document.body.onload = draw();
		</script>
	</body>
</html>
