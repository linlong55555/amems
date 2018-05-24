var json = {fxsj:[], mgb:[], igb:[], tgb:[]};

$(document).ready(function(){
	var dom = document.getElementById("demo");
	var myChart = echarts.init(dom);
	
	var data = [
	            {fxsj:'1', mgb:'1.00', igb:'0.00', tgb:'3.00'},
	            {fxsj:'2', mgb:'2', igb:'2.00', tgb:'0.00'},
	            {fxsj:'3', mgb:'3', igb:'0.00', tgb:'3.00'},
	            {fxsj:'4', mgb:'5', igb:'2.00', tgb:'0.00'},
	            {fxsj:'5', mgb:'9', igb:'0.00', tgb:'3.00'},
	            {fxsj:'6', mgb:'10', igb:'2.00', tgb:'0.00'},
	            {fxsj:'7', mgb:'11.00', igb:'0.00', tgb:'3.00'},
	            {fxsj:'8', mgb:'08.00', igb:'2.00', tgb:'0.00'},
	            {fxsj:'9', mgb:'19.00', igb:'0.00', tgb:'3.00'},
	            {fxsj:'10', mgb:'01.00', igb:'2.00', tgb:'0.00'},
	            {fxsj:'11', mgb:'199.00', igb:'0.00', tgb:'3.00'},
	            {fxsj:'12', mgb:'09.00', igb:'2.00', tgb:'0.00'}
	            ];
	$.each(data, function(index, row){
		json.fxsj.push(row.fxsj);
		json.mgb.push(row.mgb);
		json.igb.push(row.igb);
		json.tgb.push(row.tgb);
	})
	
	option = {
		title : {
			text:'Demo',
			x:'center',
	        y:'bottom'
		},
	    tooltip: {
	        trigger: 'axis'
	    },
	    grid: {
	    	left:'15%',
	        right: '10%'
	    },
	    toolbox: {
	        feature: {
//	            dataView: {show: true, readOnly: false},
	            restore: {show: true},
	            saveAsImage: {show: true}
//	            magicType: {
//	                type: ['line', 'bar', 'stack', 'tiled']
//	            }
	        }
	    },
	    legend: {
	        data:['MGB','IGB','TGB']
	    },
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            data: json.fxsj
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value',
	            name: 'MGB',
	            position: 'left'
	        },
	        {
	            type: 'value',
	            name: 'IGB',
	            position: 'left',
	            offset: 50
	        },
	        {
	            type: 'value',
	            name: 'TGB',
	            position: 'left',
	            offset: 100,
	            axisLabel: {
            		//rotate: 60,
	                formatter: '{value}'
	            }
	        }
	    ],
	    series: [
	        {
	            name:'MGB',
	            type:'line',
	            yAxisIndex: 0,
	            data:json.mgb ,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'IGB',
	            type:'line',
	            yAxisIndex: 1,
	            data:json.igb ,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'TGB',
	            type:'line',
	            yAxisIndex: 2,
	            data:json.tgb ,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        }
	    ]
	};
    myChart.setOption(option, true);
    
    myChart.on('click', function (params) {
        // 控制台打印数据的名称
        alert("fxsj="+params.name);
        alert("seriesName="+params.seriesName);
        alert("value="+params.value);
    });
});


