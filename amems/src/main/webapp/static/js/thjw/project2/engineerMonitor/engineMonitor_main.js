
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
	    	left:'40px',
	        right: '70px'
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {show: true}
	        }
	    },
	    legend: {
	        data:['滑油消耗1','滑油消耗2']
	    },
	    xAxis: [
	        {
	            name: '日期时间',
	        	type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            data: [],
	            axisLabel :{  
	                interval:0,
	                rotate:45,
	                textStyle:{
	                	fontSize:'9'
	                }
	            } 
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value',
	            name: '滑油消耗率',
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
	chartSecond: null,
	chartThree: null,
	chartFour: null,
	data:[],
	dataMap:{},
	flbMap:{},
	fj: {},
	chart:{0:'chartFirst',
		   1:'chartSecond',
		   2:'chartThree',
		   3:'chartFour'},
	init: function(){
		//初始化chart组件
		var domcharFirst = document.getElementById(this.id+"_chartFirst");
		this.charFirst = echarts.init(domcharFirst);
		var domcharSecond = document.getElementById(this.id+"_chartSecond");
		this.chartSecond = echarts.init(domcharSecond);
		var domcharThree = document.getElementById(this.id+"_chartThree");
		this.chartThree = echarts.init(domcharThree);
		var domcharFour = document.getElementById(this.id+"_chartFour");		
		this.chartFour = echarts.init(domcharFour);
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
			return false;
		}
		$("#"+this.id+"_chartFirst").hide();
		$("#"+this.id+"_chartSecond").hide();
		$("#"+this.id+"_chartThree").hide();
		$("#"+this.id+"_chartFour").hide();
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/project2/oil/consumption/monitor/queryEngineList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:  JSON.stringify(obj),
			success:function(data){
				if(data!=null && data.length>0){
					$.each(data,function(i,row){
					var obj = this_.chart[i];
					$("#"+this_.id+"_"+obj).show();															
					this_.setChartData(row,i);	
					})
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
				this_.fj[row.FJZCH]=row;
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
		var paramsMap={};
		var fjzch = $.trim($("#"+this.id+"_fjzch").val());
		if(fjzch!=''){
			paramsMap.fdjsl=this.fj[fjzch].FDJSL;
		}
		paramsMap.fjzch = fjzch;
		/*if(paramsMap.fjzch == ""){
			AlertUtil.showMessage("飞机注册号不能为空");
			return false;
		}*/
		var betweenDate = $("#"+this.id+"_between_date").val();
		if(null != betweenDate && "" != betweenDate){
			paramsMap.fxrqBegin = betweenDate.substring(0,4)+"-"+betweenDate.substring(5,7)+"-"+betweenDate.substring(8,10);
			paramsMap.fxrqEnd = betweenDate.substring(12,17)+"-"+betweenDate.substring(18,20)+"-"+betweenDate.substring(21,23);
		      var d1 = paramsMap.fxrqBegin;//日期1
              var d2 = paramsMap.fxrqEnd;//日期2
              //年*12+月
              var m1 = parseInt(d1.split("-")[1].replace(/^0+/, "")) + parseInt(d1.split("-")[0]) * 12;
              var m2 = parseInt(d2.split("-")[1].replace(/^0+/, "")) + parseInt(d2.split("-")[0]) * 12;
              if((m2-m1)>18){
            	  AlertUtil.showMessage("日期间隔不能超过18个月");
            	  return false;
              }
		}else{
			AlertUtil.showMessage("飞行日期不能为空");
			return false;
		}
		paramsMap.dprtcode = $("#"+this.id+"_dprtcode").val();
		params.paramsMap=paramsMap;
		return params;
	},
	setChartData: function(data,i){
		var _this=this;
		var xAxisData = [];
		var leftData1 = [];//滑耗1
		var leftData2 = [];//滑耗2
		var sjqys = [];//实际取样数
		var hy=[];//发动机滑油
		var flb=[];//飞行记录本
		$.each(data.xz, function(index, row){
			xAxisData.push(row.substring(0,10));
		});
		$.each(data.sh, function(index, row){
			leftData1.push(row.hyxhx);
			sjqys.push(row.sjqys);
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
			_this.fillData(_this.charFirst,xAxisData,leftData1,leftData2,i,sjqys,flb);
		}else if(i==1){
			_this.fillData(_this.chartSecond,xAxisData,leftData1,leftData2,i,sjqys,flb);
		}else if(i==2){
			_this.fillData(_this.chartThree,xAxisData,leftData1,leftData2,i,sjqys,flb);
		}else if(i==3){
			_this.fillData(_this.chartFour,xAxisData,leftData1,leftData2,i,sjqys,flb);
		}
		$.each(leftData1,function(i,data){
			_this.dataMap[data]=hy[i];
		})
		$.each(xAxisData,function(i,data){
			_this.dataMap[data]=hy[i];
		})
	},
	fillData:function(obj,xAxisData,leftData1,leftData2,i,sjqys,flb){
		var _this=this;
		obj.setOption(option, true);
		obj.on('click', function (params) {
			if(flb[params.dataIndex]){
				window.open(basePath + "/produce/flb/view?id=" + flb[params.dataIndex]);
			}
			
		});
		
		obj.setOption({
			
			title:{
				text: $("#"+_this.id+"_fjzch").val() + "     " +(i+1)+ "#"
			},
			xAxis: [
		        {
		            name:'飞行日期',
		        	data: xAxisData
		        }
			],
			tooltip:{
				formatter: function(params){
					if(params.componentType && params.componentType == "markPoint"){
						return params.name+"<br/>"+params.seriesName+"："+StringUtil.escapeStr(params.value);
					}
					var value = params[0].name;
					for (var i = 0; i < params.length; i++) {
						value += "<br/>"+ params[i].seriesName +"："+ StringUtil.escapeStr(params[i].value);
					}
					value += "<br/>"+"发动机滑油消耗:"+ StringUtil.escapeStr(_this.dataMap[params[0].value]);
					value += "<br/>"+"实际取样数:"+ StringUtil.escapeStr(sjqys[params[0].dataIndex]);
//					value += "<br/>件号：" + formatUndefine(_this.data[params[0].dataIndex].JH_F1);
//					value += "<br/>序列号：" + formatUndefine(_this.data[params[0].dataIndex].XLH_F1);
					return value;
				}
			},
			series: [
		        {
		            name:'滑油消耗率1',
		            data:leftData1
		        },
		        {
		            name:'滑油消耗率2',
		            data:leftData2
		        }
		    ]
		});
	}
	
}





