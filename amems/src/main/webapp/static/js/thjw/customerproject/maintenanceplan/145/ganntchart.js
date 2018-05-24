
var option1 = {
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
	        data:['飞行时间']
	    },
	    xAxis: [
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
	    yAxis: [
            {
	            name: '阶段',
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
	    series: [
	             {
	                 name: '完成数量',
	                 type: 'bar',
	                 stack: '总量',
	                 barMaxWidth: '20', 
	                 label: {
	                     normal: {
	                         show: true,
	                         position: 'insideRight'
	                     }
	                 },
	                 data: []
	             },
	             {
	                 name: '总计数量',
	                 type: 'bar',
	                 stack: '总量',
	                 barMaxWidth: '20', 
	                 label: {
	                     normal: {
	                         show: true,
	                         position: 'insideRight'
	                     }
	                 },
	                 data: []
	             }
	    ]
	};

maintenanceplan_gannt = {
	id: 'maintenanceplan_gannt',
	gannt: null,
	data:[],
	dataMap:{},
	flbMap:{},
	fj: {},
	chart:{0:'chartFirst'
		   },
	init: function(id){
		//初始化chart组件
		var domcharFirst = document.getElementById(this.id+"_gannt");
		this.gannt = echarts.init(domcharFirst);
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
			url:basePath+"/produce/maintenanceplan145/queryProgressList",
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
		var yAxisData = [];
		var leftData1 = [];//完成
		var leftData2 = [];//总计
		var leftData3 = [];//完成率
		$.each(data, function(index, row){
			yAxisData.push(row.JDBH);
			leftData1.push(row.NUM_WC); //完成        
			leftData2.push(row.NUM_ZJ-row.NUM_WC); //总计
			leftData3.push(row.WCL); //总计
		});
		this_.fillData(this_.gannt,yAxisData,leftData1,leftData2,leftData3);
	},
	fillData:function(obj,yAxisData,leftData1,leftData2,leftData3){
		obj.setOption(option1,true);
		obj.setOption({
				yAxis: [
			        {
			            name:'阶段',
			        	data: yAxisData
			        }
				],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        },
			        formatter: function (params){
			        	var wc= params[0].value;
			        	var zj=params[1].value+wc;
			            if(zj==0||zj==null){
		                	   zj=1;
		                   }
		                   if(wc==null){
		                	   wc=0;
		                   }
	                    var wcl=(wc/(zj)*100).toFixed(2);
			            return params[0].name + '<br/>'+
			                    params[0].seriesName + ' : ' + wc + '<br/>'+
			                    params[1].seriesName + ' : ' + zj+ '<br/> 完成率 : '+wcl+"%" ;
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

            