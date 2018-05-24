
$(document).ready(function(){
	Navigation(menuCode);
	engine_health_monitor.init();
});

var option = {
		title : {
			x:'center',
	        y:'bottom'
		},
	    tooltip: {
	        trigger: 'axis'
	    },
	    grid: {
	    	left:'25%',
	        right: '12%'
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {show: true}
	        }
	    },
	    legend: {
	        data:['功率余度','温度余度','滑油消耗']
	    },
	    xAxis: [
	        {
	            name: '飞行时间',
	        	type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            data: []
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value',
	            name: '滑油消耗',
	            position: 'left',
	            splitLine:{show:false}
	        },
	        {
	            type: 'value',
	            name: '温度余度',
	            position: 'left',
	            offset: 55,
	            splitLine:{show:false}
	        },
	        {
	            type: 'value',
	            name: '功率余度',
	            position: 'left',
	            offset: 110,
	            splitLine:{show:false}
	        }
	    ],
	    series: [
	        {
	            name:'滑油消耗',
	            type:'line',
	            yAxisIndex: 0,
	            data:[] ,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'温度余度',
	            type:'line',
	            yAxisIndex: 1,
	            data:[],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'功率余度',
	            type:'line',
	            yAxisIndex: 2,
	            data:[] ,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        }
	    ]
	};

engine_health_monitor = {
	id: 'engine_health_monitor',
	chartLeft: null,
	chartRight: null,
	data:[],
	pageSize: 20,
	init: function(){
		//初始化chart组件
		var domChartLeft = document.getElementById(this.id+"_left_chart");
		this.chartLeft = echarts.init(domChartLeft);
		var domChartRight = document.getElementById(this.id+"_right_chart");
		this.chartRight = echarts.init(domChartRight);
		
//		this.chartLeft.on('click', function (params) {
	        // 控制台打印数据的名称
//	        alert("fxsj="+params.name);
//	        alert("seriesName="+params.seriesName);
//	        alert("value="+params.value);
//	    });
		$('.date-range-picker').daterangepicker({cancelClass:"hide"}).prev().on("click", function(){
			$(this).next().focus();
		});
		var this_ = this;
		TimeUtil.getCurrentDate(function(date){
			var curDate = new Date(Date.parse(date.replace(/-/g,"/")));
			curDate.setDate(curDate.getDate()-30);
			var year = curDate.getFullYear();
			var month = (curDate.getMonth()+1)<10?"0"+(curDate.getMonth()+1):(curDate.getMonth()+1);
			var day = curDate.getDate()<10?"0"+curDate.getDate():curDate.getDate();
			$("#"+this_.id+"_between_date").val( year+"-"+month+"-"+day+" ~ "+date);
			$("#"+this_.id+"_between_date").data('daterangepicker').setStartDate(year+"-"+month+"-"+day);
			$("#"+this_.id+"_between_date").data('daterangepicker').setEndDate(date);
			this_.dprtChanged();
		});
		$("#"+this.id+"_dprtcode").on("change", function(){
			this_.dprtChanged();
		});
		$("#"+this.id+"_fjzch").on("change", function(){
			this_.load();
		});
	},
	//加载数据
	load: function(){
		var obj = this.getParams();
		if(obj === false){
			this.data = [];
			this.setChartData();
			$("#"+this.id+"_left_pagination").empty();
			$("#"+this.id+"_left_list").html("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
			$("#"+this.id+"_right_pagination").empty();
			$("#"+this.id+"_right_list").html("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
			return false;
		}
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/flightdata/report/list",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:  JSON.stringify(obj),
			success:function(data){
				if(data.rows && data.rows.length > 0){
					this_.data = data.rows;
					this_.formatFXSJ();
					this_.setChartData();
					this_.appendContentHtmlLeft();
					this_.appendContentHtmlRight();
				} else {
					this_.data = [];
					this_.setChartData();
					$("#"+this_.id+"_left_pagination").empty();
					$("#"+this_.id+"_left_list").html("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
					$("#"+this_.id+"_right_pagination").empty();
					$("#"+this_.id+"_right_list").html("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
				}
				finishWait();
		    }
		}); 
	},
	dprtChanged: function(){
		var this_ = this;
		var dprtcode = $.trim($("#"+this_.id+"_dprtcode").val());
		var planeData = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
		var planeRegOption = '';
		if(planeData && planeData.length >0){
			$.each(planeData,function(i,row){
				planeRegOption += "<option value='"+StringUtil.escapeStr(row.FJZCH)+"' >"+StringUtil.escapeStr(row.FJZCH)+"</option>";
			});
		}
		if(planeRegOption == ''){
			planeRegOption = '<option value="">暂无飞机 No plane</option>';
		}
		$("#"+this_.id+"_fjzch").html(planeRegOption);
		$("#"+this_.id+"_fjzch").trigger("change");
	},
	//格式化飞行时间
	formatFXSJ: function(){
		$.each(this.data, function(index, row){
			if(row.FXSJ){
				row.FXSJ = TimeUtil.operateTime(formatUndefine(row.FXSJ||0), 0, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
			}else{
				row.FXSJ = "";
			}
		})
	},
	//获取查询参数
	getParams: function(){
		var params = {};
		params.fjzch = $.trim($("#"+this.id+"_fjzch").val());
		if(params.fjzch == ""){
//			AlertUtil.showMessage("飞机注册号不能为空");
			return false;
		}
		var betweenDate = $("#"+this.id+"_between_date").val();
		if(null != betweenDate && "" != betweenDate){
			params.dateBegin = betweenDate.substring(0,4)+"-"+betweenDate.substring(5,7)+"-"+betweenDate.substring(8,10);
			params.dateEnd = betweenDate.substring(12,17)+"-"+betweenDate.substring(18,20)+"-"+betweenDate.substring(21,23);
		}else{
			AlertUtil.showMessage("飞行日期不能为空");
			return false;
		}
		params.dprtcode = $("#"+this.id+"_dprtcode").val();
		return params;
	},
	setChartData: function(){
		
		var xAxisData = [];
		var leftData1 = [];//滑耗
		var leftData2 = [];//温度余度
		var leftData3 = [];//功率余度
		var rightData1 = [];//滑耗
		var rightData2 = [];//温度余度
		var rightData3 = [];//功率余度
		$.each(this.data, function(index, row){
			xAxisData.push(row.FXSJ);
			leftData1.push(row.F1_HY);
			leftData2.push(row.F1_WDYD);
			leftData3.push(row.F1_GLYD);
			rightData1.push(row.F2_HY);
			rightData2.push(row.F2_WDYD);
			rightData3.push(row.F2_GLYD);
		});
		var _this = this;
		this.chartLeft.setOption(option, true);
		this.chartLeft.setOption({
			
			title:{
				text: $("#"+_this.id+"_fjzch").val() + "     " + $("#"+_this.id+"_between_date").val() + "    左发"
			},
			xAxis: [
		        {
		            name:'飞行时间',
		        	data: xAxisData
		        }
			],
			tooltip:{
				formatter: function(params){
					if(params.componentType && params.componentType == "markPoint"){
						return params.name+"<br/>"+params.seriesName+"："+params.value;
					}
					var value = params[0].name;
					for (var i = 0; i < params.length; i++) {
						value += "<br/>"+ params[i].seriesName +"："+  params[i].value;
					}
					value += "<br/>件号：" + formatUndefine(_this.data[params[0].dataIndex].JH_F1);
					value += "<br/>序列号：" + formatUndefine(_this.data[params[0].dataIndex].XLH_F1);
					return value;
				}
			},
			series: [
		        {
		            name:'滑油消耗',
		            data:leftData1
		        },
		        {
		            name:'温度余度',
		            data:leftData2
		        },
		        {
		            name:'功率余度',
		            data:leftData3
		        }
		    ]
		});
		this.chartRight.setOption(option, true);
		this.chartRight.setOption({
			
			title:{
				text: $("#"+_this.id+"_fjzch").val() + "     " + $("#"+_this.id+"_between_date").val() + "    右发"
			},
			tooltip:{
				formatter: function(params){
					if(params.componentType && params.componentType == "markPoint"){
						return params.name+"<br/>"+params.seriesName+"："+params.value;
					}
					var value = params[0].name;
					for (var i = 0; i < params.length; i++) {
						value += "<br/>"+ params[i].seriesName +"："+  params[i].value;
					}
					value += "<br/>件号：" + formatUndefine(_this.data[params[0].dataIndex].JH_F2);
					value += "<br/>序列号：" + formatUndefine(_this.data[params[0].dataIndex].XLH_F2);
					return value;
				}
			},
			xAxis: [
			        {
			        	name:'飞行时间',
			        	data: xAxisData
			        }
			        ],
			        series: [
			                 {
			                	 name:'滑油消耗',
			                	 data:rightData1
			                 },
			                 {
			                	 name:'温度余度',
			                	 data:rightData2
			                 },
			                 {
			                	 name:'功率余度',
			                	 data:rightData3
			                 }
			                 ]
		});
	},
	appendContentHtmlLeft: function(pageNumber){
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		var htmlContent = '';
		var index = 1;
		for (var i = (pageNumber-1)*this.pageSize; i < this.data.length && i < pageNumber*this.pageSize; i++) {
			htmlContent += "<tr     >";
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+index+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(this.data[i].FXRQ)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+formatUndefine(this.data[i].FXSJ)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(this.data[i].JH_F1)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(this.data[i].XLH_F1)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(this.data[i].F1_HY)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(this.data[i].F1_WDYD)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(this.data[i].F1_GLYD)+"</td>";  
			htmlContent += "</tr>"; 
			index ++;
		}
		
		if(htmlContent == ""){
			$("#"+this.id+"_left_list").html("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
			return;
		}
		$("#"+this.id+"_left_list").html(htmlContent);
		
		var this_ = this;
		var pageable = {page:pageNumber, rows:this_.pageSize};
		this.data.pageable = pageable;
		this.data.total = this_.data.length;
		new Pagination({
			renderTo : this_.id+"_left_pagination",
			data: this_.data,
			sortColumn : "1",
			orderType : "2",
			goPage:function(a){
				this_.appendContentHtmlLeft(a);
			}
		});	
		
	},
	appendContentHtmlRight: function(pageNumber){
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		var htmlContent = '';
		var index = 1;
		for (var i = (pageNumber-1)*this.pageSize; i < this.data.length && i < pageNumber*this.pageSize; i++) {
			htmlContent += "<tr     >";
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+index+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(this.data[i].FXRQ)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+formatUndefine(this.data[i].FXSJ)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+formatUndefine(this.data[i].JH_F2)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+formatUndefine(this.data[i].XLH_F2)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(this.data[i].F2_HY)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(this.data[i].F2_WDYD)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(this.data[i].F2_GLYD)+"</td>";  
			htmlContent += "</tr>"; 
			index ++;
		}
		if(htmlContent == ""){
			$("#"+this.id+"_right_list").html("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
			return;
		}
		$("#"+this.id+"_right_list").html(htmlContent);
		
		var this_ = this;
		var pageable = {page:pageNumber, rows:this_.pageSize};
		this.data.pageable = pageable;
		this.data.total = this_.data.length;
		new Pagination({
			renderTo :this_.id+"_right_pagination",
			data: this_.data,
			sortColumn : "1",
			orderType : "2",
			goPage:function(a){
				this_.appendContentHtmlRight(a);
			}
		});		
	}
	
}





