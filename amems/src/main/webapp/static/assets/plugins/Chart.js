
var option = {
		title : {
			x:'center',
	        y:'bottom'
		},
	    tooltip: {
	        trigger: 'axis'
	    },
	    grid: {
	    	left:'40px',
	        right: '70px'
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {show: true}
	        }
	    },
	    legend: {
	        data:['飞行时间']
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
	    series: [
	        {
	            name:'滑油消耗1',
	            type:'line',
	            yAxisIndex: 0,
	            selectedMode: 'single',
	            data:[] ,
	            markPoint: {
	                data: [
	                   
	                ]
	            }
	        },
	        {
	            name:'滑油消耗2',
	            type:'line',
	            yAxisIndex: 0,
	            data:[] ,
	            markPoint: {
	                data: [
	                  
	                ]
	            }
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
		/*		if(data.length>0){
					$.each(data,function(i,row){
					var obj = this_.chart[i];
					$("#"+this_.id+"_"+obj).show();															
					this_.setChartData(row,i);	
					});
				}	*/		
				
				//
				this_.fillData(this_.charFirst);	
		    }
		}); 
	},
	setChartData: function(data,i){
		var _this=this;
		var xAxisData = [];
		var leftData1 = [];//滑耗1
		var leftData2 = [];//滑耗2
		var hy=[];//发动机滑油
		var flb=[];//飞行记录本
		$.each(data.xz, function(index, row){
			xAxisData.push(row.substring(0,10));
		});
		$.each(data.sh, function(index, row){
			leftData1.push(row.hyxhx);
		});
		$.each(data.xh, function(index, row){
			leftData2.push(row.hytjllj);
		});
		$.each(data.hy, function(index, row){
			hy.push(row);
		});
		$.each(data.flb, function(index, row){
			flb.push(row);
		});
		if(i==0){
			_this.fillData(_this.charFirst,xAxisData,leftData1,leftData2,i);
		}
		$.each(leftData1,function(i,data){
			_this.dataMap[data]=hy[i];
			_this.flbMap[data]=flb[i];
		})
		$.each(xAxisData,function(i,data){
			_this.dataMap[data]=hy[i];
			_this.flbMap[data]=flb[i];
		})
	},
	fillData:function(obj){
		var _this=this;
		obj.setOption( option,true);
		
		obj.setOption({
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        formatter: function (params){
		            return params[0].name + '<br/>'
		                   + params[0].seriesName + ' : ' + params[0].value + '<br/>'
		                   + params[1].seriesName + ' : ' + (params[1].value + params[0].value);
		        }
		    },
		    legend: {
		        selectedMode:false,
		        data:['Acutal', 'Forecast']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : ['Cosco','CMA','APL','OOCL','Wanhai','Zim']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            boundaryGap: [0, 0.1]
		        }
		    ],
		    series : [
		        {
		            name:'Acutal',
		            type:'bar',
		            stack: 'sum',
		            barCategoryGap: '50%',
		            itemStyle: {
		                normal: {
		                    color: 'tomato',
		                    barBorderColor: 'tomato',
		                    barBorderWidth: 6,
		                    barBorderRadius:0,
		                    label : {
		                        show: true, position: 'insideTop'
		                    }
		                }
		            },
		            data:[260, 200, 220, 120, 100, 80]
		        },
		        {
		            name:'Forecast',
		            type:'bar',
		            stack: 'sum',
		            itemStyle: {
		                normal: {
		                    color: '#fff',
		                    barBorderColor: 'tomato',
		                    barBorderWidth: 6,
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
		                        },
		                        textStyle: {
		                            color: 'tomato'
		                        }
		                    }
		                }
		            },
		            data:[40, 80, 50, 80,80, 70]
		        }
		    ]
		});
	}
}

            