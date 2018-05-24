$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	var dprtcode=$("#dprtcode_search").val();
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
	
	//飞机注册号
	initfjzch(dprtcode);
	
	componenthistoryMain.reload();
	
	
});

//切换组织机构
function dprtChange(){
	initfjzch($("#dprtcode_search").val());
}
//飞机搜索框
function initfjzch(dprtcode){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		planeRegOption += "<option value='' >"+"显示全部 All"+"</option>";
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'>"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
		planeRegOption += "<option value='-' >N/A</option>";
	}else{
		planeRegOption += "<option value='-1' >"+"暂无飞机"+"</option>";
	}
	$("#fjzch").html(planeRegOption); 
	componenthistoryMain.reload();
}
var componenthistoryMain={	
		gatherSearchParam:function(){
			var searchParam={};
			 searchParam.dprtcode=$("#dprtcode_search").val();
			 searchParam.fjzch=$("#fjzch").val();
			 var paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
			 paramsMap.keyword=$.trim($('#keyword_search').val());;
			 
			 searchParam.paramsMap=paramsMap;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = pagination;
			 startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/produce/componenthistory/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					   finishWait();
			 			if(data.rows.length > 0){
				 			_this.appendContentHtml(data.rows);
				 			//分页
				 			var page_ = new Pagination({
								renderTo : "pagination",
								data: data,
								sortColumn : sortType,
								orderType : sequence,
								extParams:{},
								goPage: function(a,b,c){
									_this.AjaxGetDatasWithSearch(a,b,c);
								}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[2,3,4,5]);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#list").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'main_list_table'});
			 		}
			 });
		},
		reload:function(){
			TableUtil.resetTableSorting("main_list_table");
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td class='text-center fixed-column' title='查看部件履历'><i class='spacing icon-eye-open color-blue cursor-pointer' onClick=\"view('"+row.bjh+"','"+row.xlh+"','"+row.dprtcode+"')\" ></i></td>"; 
			    htmlContent += "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>"; 
			    htmlContent += "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.bjywms)+" "+StringUtil.escapeStr(row.paramsMap.bjzwms)+"'>"+StringUtil.escapeStr(row.paramsMap.bjywms)+" "+StringUtil.escapeStr(row.paramsMap.bjzwms)+"</td>";
			   // htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.cjjh)+"'>"+StringUtil.escapeStr(row.paramsMap.cjjh)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.jhly)+"'>"+StringUtil.escapeStr(row.paramsMap.jhly)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"'>"+StringUtil.escapeStr(row.paramsMap.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"</td>";
			    htmlContent += "<td class='text-center' title='"+DicAndEnumUtil.getEnumName('componenthistoryStatusEnum',row.paramsMap.dqzt)+"'>"+ DicAndEnumUtil.getEnumName('componenthistoryStatusEnum',row.paramsMap.dqzt)+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.dqwz)+"'>"+StringUtil.escapeStr(row.paramsMap.dqwz)+"</td>"; 
			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.paramsMap.cal?parseInt(row.paramsMap.cal):'')+"'>"+StringUtil.escapeStr(row.paramsMap.cal?parseInt(row.paramsMap.cal):'')+"</td>"; 
			    htmlContent += "<td class='text-right' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.tsn), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.tsn), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.csn)+"'>"+StringUtil.escapeStr(row.csn)+"</td>"; 
			    htmlContent += "<td class='text-right' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.tso), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.tso), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.cso)+"'>"+StringUtil.escapeStr(row.cso)+"</td>"; 
			    htmlContent += "<td title='证书列表 Certificate List' style='text-align:center;vertical-align:middle;'>";
				if(row.paramsMap.certificatecount && row.paramsMap.certificatecount > 0){
					htmlContent += '<i bjh="'+StringUtil.escapeStr(row.bjh)+'" xlh="'+StringUtil.escapeStr(row.xlh)+'" dprtcode="'+StringUtil.escapeStr(row.dprtcode)+'" class="certificate-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
			   /* htmlContent += "<td class='text-left' title='证书列表' style='text-align:center;vertical-align:middle;'>";
			    htmlContent += '<i bjh="'+StringUtil.escapeStr(row.bjh)+'" xlh="'+StringUtil.escapeStr(row.xlh)+'" dprtcode="'+StringUtil.escapeStr(row.dprtcode)+'" class="certificate-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer " onclick="componenthistoryMain.openEOStationWin(this)"  style="font-size:15px"></i>';
			    htmlContent += "</td>" ;*/
			    htmlContent += "</tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission(); 
		   _this.initWebuiPopover();
		 });
		},
		initWebuiPopover: function(){//初始化
			var this_=this;
			WebuiPopoverUtil.initWebuiPopover('certificate-view','body',function(obj){
				return this_.getHistoryCertificateList(obj);
			},700);
			$("#structrucRepair_table").scroll(function(){
				$('.certificate-view').webuiPopover('hide');
			});
		},
		getHistoryCertificateList: function(obj){//获取历史附件列表
			var jsonData ={jh : $(obj).attr('bjh'),xlh : $(obj).attr('xlh'),dprtcode : $(obj).attr('dprtcode') };
		    
			return history_certificate_alert_Modal.getHistoryCertificateList(jsonData);
		},
		
		
		//打开证书信息窗口
		 openEOStationWin:function(obj){
			var bjh = $(obj).attr('bjh');
			var xlh = $(obj).attr('xlh');
			var dprtcode = $(obj).attr('dprtcode');
			var this_ = this;
			history_certificate_alert_Modal.show({
				dprtcode:dprtcode,//默认登录人当前机构代码
				jh :bjh,
				xlh :xlh,
				callback : function(data) {//回调函数
					positionPublicList = [];
					fjzwList = data; 
					var str = '';
					if (data != null && data.length > 0) {
						$.each(data, function(index, row) {
							positionPublicList.push(row);
							str += formatUndefine(row.sz) + ",";
						});
					}
					if (str != '') {
						str = str.substring(0, str.length - 1);
					}
					$("#efjzw_public").html(str);
				}
			});
		}
};
function view(bjh,xlh,dprtcode){
	window.open(basePath+"/produce/componenthistory/view?pageParam=" + encodePageParam(bjh,xlh,dprtcode));
}

function encodePageParam(bjh,xlh,dprtcode){
	var pageParam = {};
	var params = {};
	params.bjh = bjh;
	params.xlh = xlh;
	params.dprtcode = dprtcode;
	pageParam.params = params; 
	return Base64.encode(JSON.stringify(pageParam));
}
function orderBy(obj){
	// 字段排序
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
			orderType = "asc";
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		componenthistoryMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}
