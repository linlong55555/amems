
$(document).ready(function(){
	Navigation(menuCode);
	reducer_oil_consumption.init();
});

var option = {
		title : {
			text:'Demo',
			x:'center',
	        y:'bottom'
		},
	    tooltip: {
	        trigger: 'axis'
	    },
	    grid: {
	    	left:'17%',
	        right: '10%'
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {show: true}
	        }
	    },
	    legend: {
	        data:['TGB','IGB','MGB']
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
	            name: 'MGB',
	            position: 'left',
	            splitLine:{show:false}
	        },
	        {
	            type: 'value',
	            name: 'IGB',
	            position: 'left',
	            offset: 50,
	            splitLine:{show:false}
	        },
	        {
	            type: 'value',
	            name: 'TGB',
	            position: 'left',
	            offset: 100,
	            splitLine:{show:false}
	        }
	    ],
	    series: [
	        {
	            name:'MGB',
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
	            name:'IGB',
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
	            name:'TGB',
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

reducer_oil_consumption = {
	id: 'reducer_oil_consumption',
	chart: null,
	data:[],
	pageSize: 20,
	init: function(){
		//初始化chart组件
		var domChart = document.getElementById(this.id+"_chart");
		this.chart = echarts.init(domChart);
		
//		this.chart.on('click', function (params) {
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
			$("#"+this.id+"_pagination").empty();
			$("#"+this.id+"_list").html("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
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
					this_.appendContentHtml();
				} else {
					this_.data = [];
					this_.setChartData();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").html("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
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
		var MGBData = [];
		var IGBData = [];
		var TGBData = [];
		$.each(this.data, function(index, row){
			xAxisData.push(row.FXSJ);
			MGBData.push(row.MGB);
			IGBData.push(row.IGB);
			TGBData.push(row.TGB);
		});
		
		var _this = this;
		this.chart.setOption(option, true);
		this.chart.setOption({
			
			title:{
				text: $("#"+_this.id+"_fjzch").val() + "     " + $("#"+_this.id+"_between_date").val() + "    减速器滑耗"
			},
			xAxis: [
		        {
		            name:'飞行时间',
		        	data: xAxisData
		        }
			],
			series: [
		        {
		            name:'MGB',
		            data:MGBData
		        },
		        {
		            name:'IGB',
		            data:IGBData
		        },
		        {
		            name:'TGB',
		            data:TGBData
		        }
		    ]
		});
	},
	appendContentHtml:function(pageNumber){
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		var htmlContent = '';
		var index = 1;
		for (var i = (pageNumber-1)*this.pageSize; i < this.data.length && i < pageNumber*this.pageSize; i++) {
			htmlContent += "<tr     >";
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+index+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(this.data[i].FXRQ)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(this.data[i].FXSJ)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(this.data[i].MGB)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(this.data[i].IGB)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='center'>"+formatUndefine(this.data[i].TGB)+"</td>";  
			htmlContent += "</tr>"; 
			index ++;
		}
		if(htmlContent == ""){
			$("#"+this.id+"_list").html("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
			return;
		}
		
		$("#"+this.id+"_list").html(htmlContent);
		var this_ = this;
		var pageable = {page:pageNumber, rows:this_.pageSize};
		this.data.pageable = pageable;
		this.data.total = this_.data.length;
		new Pagination({
			renderTo : this_.id+"_pagination",
			data: this_.data,
			sortColumn : "1",
			orderType : "2",
			goPage:function(a){
				this_.appendContentHtml(a);
			}
		});
	}
	
}





