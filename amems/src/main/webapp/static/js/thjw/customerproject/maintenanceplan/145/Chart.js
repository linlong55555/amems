 
var option = {
		title : {
			x:'center',
	        y:'bottom'
		},
	    tooltip: {
	        trigger: 'axis'
	    },
	    grid: {
	    	top:'40px',
	    	left:'40px',
	        right: '70px',
	        bottom: '25px'	
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {show: true}
	        }
	    },
	    legend: {
	        selectedMode:false,
	        data:['剩余数量', '完成数量']
	    },
	    xAxis: [
	        {
	            name: '工作组',
	        	type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            data: [],
	            axisLabel :{  
	                interval:0   
	            }
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value',
	            name: '数量',
	            position: 'left',
	            splitLine:{show:true},
	            axisLabel : { 
                    formatter : '{value}' 
	            }
	        }
	    ],
	    series : [
			        {
			            name:'完成数量',
			            type:'bar',
			            stack: 'sum',
			            barCategoryGap: '50%',
			            barMaxWidth: '20', 
			            itemStyle: {
			                normal: {
			                    color: '#05A1F6',
			                    barBorderColor: 'tomato',
			                    barBorderWidth: 1,
			                    barBorderRadius:0,
			                    label : {
			                        show: true, position: 'insideTop'
			                    }
			                }
			            },
			            data:[]
			        },
			        {
			            name:'总计数量',
			            type:'bar',
			            stack: 'sum',
			            barMaxWidth: '20', 
			            itemStyle: {
			                normal: {
			                    color: '#EAEAEA',
			                    barBorderColor: 'tomato',
			                    barBorderWidth: 1,
			                    barBorderRadius:0,
			                    label : {
			                        show: true, 
			                        position: 'top',
			                        formatter: function (params) {
			                            for (var i = 0, l = option.xAxis[0].data.length; i < l; i++) {
			                                if (option.xAxis[0].data[i] == params.name) {
			                                    return option.series[0].data[i] + params.value;
			                                }
			                            }
			                        }
			                    }
			                }
			            },
			            data:[]
			        }
			        
			    ]
	};

engine_health_monitor = {
	id: 'engine_health_monitor',
	charFirst: null,
	data:[],
	dataMap:{},
	flbMap:{},
	fj: {},
	chart:{0:'chartFirst'
		   },
	init: function(id){
		//初始化chart组件
		var domcharFirst = document.getElementById(this.id+"_chartFirst");
		this.charFirst = echarts.init(domcharFirst);
		var this_ = this;
		this_.load(id);
	},
	//加载数据
	load: function(id){
		var this_ = this;
		var obj = {};
		obj.id=id;
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/maintenanceplan145/queryDiagramList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:  JSON.stringify(obj),
			success:function(data){
				finishWait();
				if(data.length>0){
					this_.setChartData(data);	
				}
		    }
		}); 
	},
	setChartData: function(data){
		var this_=this;
		var xAxisData = [];
		var leftData1 = [];//完成
		var leftData2 = [];//总计
		var leftData3 = [];//完成率
		$.each(data, function(index, row){
			xAxisData.push(row.GZZMC);
			leftData1.push(row.NUM_WC); //完成        
			leftData2.push(row.NUM_ZJ-row.NUM_WC); //总计
			leftData3.push(row.WCL); //完成率
		});
		this_.fillData(this_.charFirst,xAxisData,leftData1,leftData2,leftData3);
	},
	fillData:function(obj,xAxisData,leftData1,leftData2,leftData3){
		obj.setOption(option,true);
		obj.setOption({
			xAxis: [
		        {
		            name:'工作组',
		        	data: xAxisData
		        }
			],
		   legend: {
	            selectedMode:false,
	            data:['Acutal', 'Forecast']
	        },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        formatter: function (params){
		        	var  wc= params[0].value;
		        	var  zj= params[1].value+wc;
		            if(zj==0||zj==null){
	                	   zj=1;
	                   }
	                   if(wc==null){
	                	   wc=0;
	                   }
                    var wcl=(wc/zj*100).toFixed(2);
		            return params[0].name + '<br/>'+
		                    params[0].seriesName + ' : ' + wc + '<br/>'+
		                    params[1].seriesName + ' : ' + zj+ '<br/> '+
		                    '完成率 : '+wcl+"%" ;
		        }
		    },
			series: [
		        {
		            name:'完成数量',
		            data:leftData1
		        },
		        {
		            name:'总计数量',
		            data:leftData2
		        }
		   
		    ]
		});
	}
}

            