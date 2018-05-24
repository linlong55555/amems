$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	initfjzch($("#dprtcode_search").val());
	$("#sy_search").val(aircraftstatus.surplus.monitorStr);
	
	if(paramJgdm != null && paramJgdm != ''){
    	$("#dprtcode_search").val(paramJgdm);
    	paramJgdm = null;
	}
    if(paramFjzch != null && paramFjzch != ''){
    	var fjzch = decodeURIComponent(Base64.decode(paramFjzch));
    	$("#fjzch").val(fjzch);
    	paramFjzch = null;
	}
	
	aircraftstatus.reload();
});

//组织机构改变事件
function dprtChange(){
	initfjzch($("#dprtcode_search").val());
	aircraftstatus.reload();
}

//飞机搜索框
function initfjzch(dprtcode){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
	}else{
		planeRegOption += "<option value='' >"+"暂无飞机"+"</option>";
	}
	$("#fjzch").html(planeRegOption); 
}

var aircraftstatus={
		surplus : {rl:30,monitorStr:"30D",rldw:11,rlhk:0},//剩余对象
		fjzch : null,
		dprtcode : null,
		fjjx: null,
		tab_type : 1,
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam={};
			 var fjzch = $("#fjzch").val();
			 if(fjzch){
				 searchParam.fjzch = fjzch;
			 }else{
				 _this.noData();
			 }
			 searchParam.dprtcode=$("#dprtcode_search").val();
			 searchParam.pagination = pagination;
			 AjaxUtil.ajax({
				 url:basePath+"/aircraftinfo/status/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					    $("#jssj").html("0 FH");
						$("#jsxh").html("0 FC");
			 			if(data.row){
			 				_this.appendStatus(data.row);
			 				if(data.rows.length > 0){
			 					_this.appendStatusContentHtml(data.rows);
			 				}else{
			 					$("#aircraftstatusList").empty();
			 					$("#aircraftstatusList").append("<tr><td rowspan='3'>暂无数据 No data.</td></tr>");
			 				}
				 		}else{
				 			_this.noData();
						}
			 		}
			 });
		},
		noData:function(){
			$("#jssj").html("0 FH");
			$("#jsxh").html("0 FC");
			$("#status").html("<i class='icon-fighter-jet font-size-20 color_red'></i>不适航");
			$("#using_json").empty();
			$("#aircraftstatusList").empty();
			$("#aircraftstatusList").append("<tr><td rowspan='3'>暂无数据 No data.</td></tr>");
			$("#aircraft_maintenance_status_main_tbody").empty();
			$("#aircraft_maintenance_status_main_tbody").append("<tr><td colspan=\"13\" class='text-center'>暂无数据 No data.</td></tr>");
			$("#eo_status_main_tbody").empty();
			$("#eo_status_main_tbody").append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
			$("#nrc_workOrder_status_main_tbody").empty();
			$("#nrc_workOrder_status_main_tbody").append("<tr><td colspan=\"9\" class='text-center'>暂无数据 No data.</td></tr>");
		},
		reload:function(){
			var this_ = this;
			this_.getFjzchAndFjjxAndDprtcode();
			this_.goPage(1,"auto","desc");
			this_.sjtree();
			this_.loadMonitoringData();
			this_.loadperformHistory();
		},
		appendStatus:function(row){
			if(row.paramsMap.shzt == 0){
				$("#status").html("<i class='icon-fighter-jet font-size-20 color_green'></i>适航");
			}else{
				$("#status").html("<i class='icon-fighter-jet font-size-20 color_red'></i>不适航");
			}
		},
		// 表格拼接
		appendStatusContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			var b = true;
			$.each(list,function(index,row){
				if(row.wz == 11){
					_this.formatLastJs(row.paramsMap.jkz);
					b = false;
				}else{
					htmlContent += "<tr >";
					htmlContent += "<td class='text-center colwidth-4' title='"+DicAndEnumUtil.getEnumName('installedPositionEnum',row.wz)+"' >"+DicAndEnumUtil.getEnumName('installedPositionEnum',row.wz)+"</td>";
					htmlContent += "<td class='text-left colwidth-10' title='P/N: "+row.jh+"\nS/N: "+row.xlh+"' ><p >P/N: "+row.jh+"</p><p >S/N: "+row.xlh+"</p></td>";
					htmlContent +=_this.formatLastData(row.paramsMap.jkz);
					htmlContent += "</tr>" ;
				}
		   $("#aircraftstatusList").empty();
		   $("#aircraftstatusList").html(htmlContent);
		   if(htmlContent == ""){
			   $("#aircraftstatusList").html("<tr><td rowspan='3'>暂无数据 No data.</td></tr>");
		   }
		   
		 });
			if(b){
				$("#jssj").html("0 FH");
				$("#jsxh").html("0 FC");
			}
		},
		getFjzchAndFjjxAndDprtcode : function (){
			var this_ = this;
			var fjzch = $("#fjzch").val();
			this_.fjzch = fjzch;
			this_.dprtcode = $("#dprtcode_search").val();
		},
		formatLastJs: function(row){
			$("#jssj").html("0 FH");
			$("#jsxh").html("0 FC");
			var this_ = this;
			var list = row.split(",");
			$.each(list,function(index,row){
				var strList = row.split("#_#");
				if(strList[1] == "2_10_FH"){
					$("#jssj").html(this_.formatJkz(strList[1],strList[2]));
				}else{
					$("#jsxh").html(this_.formatJkz(strList[1],strList[2]));
				}
			});
		},
		/**
		 * 格式化监控值
		 */
		formatJkz : function(jklbh, value){
			if(value != null && value != ''){
				value = this.convertToHour(jklbh, value) + " "+MonitorUnitUtil.getMonitorUnit(jklbh, "")+"</br>";
			}else{
				value = " "+"</br>";
			}
			return value;
		},
		/**
		 * 分钟转小时
		 */
		convertToHour : function(jklbh, value){
			if(MonitorUnitUtil.isTime(jklbh)){
				value = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON);
			}
			return value;
		},
		formatLastData : function(data){
			var this_ = this;
			var str = "";
			if(data == null || data == ""){
				str += "<td class='colwidth-5'></td>";
				return str;
			}
			var list = data.split(",");
			var jkz = '';
			$.each(list,function(index,row){
				var tdArr = row.split("#_#");
				jkz += this_.formatJkz(tdArr[1],tdArr[2]);
			});
			str += "<td title='"+jkz.replaceAll("</br>","\n")+"' style='text-align:center;' class='colwidth-8'>"+jkz+"</td>";
			return str;
		},
		/**
		 * 打开剩余对话框
		 */
		openSurplusWin : function(){
			var this_ = this;
			var sy = this_.surplus;
			SurplusWinModal.show({
				type:1,//1:剩余,2:计划
				viewObj:sy,//原值，在弹窗中回显
				callback: function(data){//回调函数
					var obj = "";
					this_.surplus = {};
					if(data != null && data.monitorStr != null && data.monitorStr != ""){
						obj = data.monitorStr;
						this_.surplus = data;
					}
					$("#sy_search").val(obj);
					this_.loadMonitoringData();
				}
			});
		},
		/**
		 * 加载数据
		 */
		loadMonitoringData : function(){
			var this_ = this;
			if(this_.tab_type == 1){
				this_.loadAircaraftMaintenanceMonitoring();
			}else if(this_.tab_type == 2){
				this_.loadEOMonitoring();
			}else if(this_.tab_type == 3){
				this_.loadPOMonitoring();
			}else if(this_.tab_type == 4){
				this_.loadNRCWorkOrder();
			}
			/*$("#status_main_rightSecondDiv").css("display","block");
			App.resizeHeight();*/
		},
		/**
		 * 加载飞机维修项目
		 */
		loadAircaraftMaintenanceMonitoring : function(){
			var this_ = this;
			this_.getFjzchAndFjjxAndDprtcode();
			//this_.hideBottom();
			aircraft_maintenance_status_main.init({
				parentObj : this_,
				fjzch : this_.fjzch,
				surplus : this_.surplus,//剩余对象
				zjh : null,//章节号
				dprtcode:this_.dprtcode
			});
			this_.tab_type = 1;
			this_.setHistoryShow();
		},
		/**
		 * 加载EO
		 */
		loadEOMonitoring : function(){
			var this_ = this;
			this_.getFjzchAndFjjxAndDprtcode();
			//this_.hideBottom();
			eo_status_main.init({
				parentObj : this_,
				fjzch : this_.fjzch,
				surplus : this_.surplus,//剩余对象
				zjh : null,//章节号
				dprtcode:this_.dprtcode
			});
			this_.tab_type = 2;
			this_.setHistoryShow();
		},
		/**
		 * 加载生产指令
		 */
		loadPOMonitoring : function(){
			var this_ = this;
			this_.getFjzchAndFjjxAndDprtcode();
			//this_.hideBottom();
			po_status_main.init({
				parentObj : this_,
				fjzch : this_.fjzch,
				surplus : this_.surplus,//剩余对象
				zjh : null,//章节号
				dprtcode:this_.dprtcode
			});
			this_.tab_type = 3;
			this_.setHistoryShow();
		},
		hideBottom:function(){
			$("#status_main_rightSecondDiv").css("display","none");
			$("#status_main_rightSecondDiv_block").css("display","block");
			App.resizeHeight();
		},
		blockBottom:function(){
			$("#status_main_rightSecondDiv").css("display","block");
			$("#status_main_rightSecondDiv_block").css("display","none");
			App.resizeHeight();
		},
		/**
		 * 加载NRC工单
		 */
		loadNRCWorkOrder : function(){
			var this_ = this;
			this_.getFjzchAndFjjxAndDprtcode();
			//this_.hideBottom();
			nrc_workOrder_status_main.init({
				parentObj : this_,
				fjzch : this_.fjzch,
				surplus : this_.surplus,//剩余对象
				zjh : null,//章节号
				dprtcode:this_.dprtcode
			});
			this_.tab_type = 4;
			this_.setHistoryShow();
		},
		/**
		 * 加载执行历史
		 */
		loadperformHistory : function(){
			aircraft_performHisotry_status.search();
		},
		
		/**
		 * 设置执行历史显示
		 */
		setHistoryShow : function(){
			$("#history_tab", $("#"+this.tab_ui_Id)).show();
			$("#historyTab", $("#"+this.tab_ui_Id)).show();
		},
		/**
		 * 加载适航性资料文件
		 */
		 sjtree : function (){
			 var this_ = this;
			 $("#using_json").jstree("destroy"); 
			 AjaxUtil.ajax({
				   url:basePath+"/aircraftinfo/status/queryAllAir",
				   type: "post",
				   data:JSON.stringify({
					   "fjzch":this_.fjzch,
					   "fjjx":this_.fjjx,
					   "dprtcode":this_.dprtcode
				   }),
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   success:function(data){
					   if(data.rows.length==0){
						   var leftFirstOuterDiv=$("#status_main_leftDiv").outerHeight()||0;
						   var leftFirstHeader=$("#status_main_leftFirstDiv .panel-heading").outerHeight()||0;
						   var leftFirstBody=(leftFirstOuterDiv-leftFirstHeader-7);
						   $("#status_main_leftFirstDiv .panel-body").css({"height":leftFirstBody+"px","overflow":"auto"});
						   $("#status_main_leftsecondDiv .panel-body").removeAttr("style");
						   $("#status_main_leftsecondDiv").css("display","none");
					   }else{
						   $("#status_main_leftsecondDiv").css("display","block");
						   this_.createPermitTree(data.rows);
					  }
					   App.resizeHeight();
					   //date=data;
			      },
			    }); 
		},
		/**
		 * 树状显示适航性资料文件
		 */
		 createPermitTree : function(datastr) {  
		    $('#using_json').jstree({
		        'core': {
		            'data':   datastr,
		            "force_text": true
		        }
		    }); 

		    /*$('#using_json').bind("select_node.jstree", function (e, data) {
		    	   var a = data.node.text.split("(");
		    	   var str="";
					for (var i = 0; i < a.length-1; i++) {
						str+=a[i];
					}
				
		    	   $('#mlmc1').val(str);
		    	   $('#id').val(data.node.id);
		    	   parentId = data.node.parent=="#"?"":data.node.parent;
		    });*/
		    
		}  
};

function customResizeHeight(bodyHeight, tableHeight){
	 if(navigator.userAgent.indexOf("Chrome") > -1){
		 bodyHeight -= 3;
     }
	  var rowHeight = $('.row-height:visible').outerHeight()||0;
	  
	  var mainHeight=(bodyHeight-rowHeight);
	  $("#status_main_leftDiv").css({"height":mainHeight+"px","overflow":"auto"});
	  var leftFirstHeader=$("#status_main_leftFirstDiv .panel-heading").outerHeight()||0;
	  var leftFirstBody=(mainHeight*0.5-leftFirstHeader);
	  if($("#status_main_leftsecondDiv").css("display")=="none"){
		  $("#status_main_leftFirstDiv .panel-body").css({"height":(mainHeight-leftFirstHeader-7)+"px","overflow":"auto"});
	  }else{
		  $("#status_main_leftFirstDiv .panel-body").css({"height":leftFirstBody+"px","overflow":"auto"});
		  var leftsecondBody=mainHeight- ($("#status_main_leftFirstDiv").outerHeight()||0)-5;
		
		  $("#status_main_leftsecondDiv .panel-body").css({"height":leftsecondBody+"px","overflow":"auto"}); 
	  }
	  
	  if($("#status_main_rightSecondDiv").css("display")=='none'){
		 
		  var rightFirstHeader=$("#status_main_rightFirstDiv ul").outerHeight()||0;
		  $("#status_main_rightFirstDiv .tab-pane:visible").find("table").parent("div").css({"height":(mainHeight-rightFirstHeader-20)+"px","overflow":"auto"})
	  }else{
		 
		  $("#status_main_rightFirstDiv .tab-pane:visible").find("table").parent("div").css({"height":"200px","overflow":"auto"})
		  var rightFirstHeight= $("#status_main_rightFirstDiv").outerHeight()||0;
		  var rightSecondHeader=$("#status_main_rightSecondDiv .panel-heading").outerHeight()||0;
		  var rightSecondBody=mainHeight-rightFirstHeight-rightSecondHeader-25;
		 var pageHeight=$("#status_main_rightSecondDiv .page-height:visible").outerHeight()||0;
		  $("#status_main_rightSecondDiv .panel-body").find("table").parent("div").css({"height":(rightSecondBody-pageHeight)+"px","overflow":"auto"})
	  }
}
