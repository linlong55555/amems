var zts=["","未确认","已确认","无需确认"];
var ids='';
$(document).ready(function(){
	Navigation(menuCode);

			$('#gdzt').multiselect({
				buttonClass: 'btn btn-default',
		        buttonWidth: 'auto',
		        numberDisplayed:20,
			    includeSelectAllOption: true,
			    onChange:function(element,checked){
			    }
			});
			changeOrganization();
			decodePageParam();
			refreshPermission();
			//初始化日志功能
			logModal.init({code:'B_S_009'});
	});


//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption = '';
	var planeData = acAndTypeUtil.getACRegList({DPRTCODE:StringUtil.escapeStr(dprtcode),FJJX:null});
	if(planeData != null && planeData.length >0){
		$.each(planeData,function(i,planeData){
			if(dprtcode == planeData.DPRTCODE){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			}
		});
		 planeRegOption+= '<option value="">非装机件</option>';
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}
	$("#fjzch_search").html(planeRegOption);
}


var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	
	params.fjzch = $.trim($("#fjzch_search").val());
	params.dprtcode = $.trim($("#dprtcode").val());
	params.zt = $.trim($("#zt").val());
	params.keyword = $.trim($("#keyword_search").val());
	params.gdzt = $("#gdzt").val();
	params.rwlx = $.trim($("#rwlx").val());
	params.rwzlx = $.trim($("#rwzlx").val());
	params.flightdate = $.trim($("#flightDate_search").val());
	
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#fjzch_search").val(params.fjzch);
		$("#dprtcode").val(params.dprtcode);
		$("#zt").val(params.zt);
		$("#keyword_search").val(params.keyword);
		$("#gdzt").val(params.gdzt);
	    $("#rwlx").val(params.rwlx);
	    $("#rwzlx").val(params.rwzlx);
	    $("#flightDate_search").val(params.flightdate);
		
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}
var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var isLoadMonitor = false;
//初始化系统阀值
function initMonitorsettings(){
	isLoadMonitor = false;
}

//加载系统阀值
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/productionmessage/workersimplex/getByKeyDprtcode",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb1 = data.monitorsettings.yjtsJb1;
				yjtsJb2 = data.monitorsettings.yjtsJb2;
				yjtsJb3 = data.monitorsettings.yjtsJb3;
			}
			isLoadMonitor = true;
		}
	});
}
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 if(!isLoadMonitor){
				loadMonitorsettings();
			}
		var obj ={};
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.fjzch = searchParam.fjzch;
		obj.dprtcode = searchParam.dprtcode;
		obj.zt = searchParam.zt;
		obj.fxDateBegin = searchParam.fxDateBegin;
		obj.fxDateEnd = searchParam.fxDateEnd;
		obj.keyword = $("#keyword_search").val();
		obj.gdzt = searchParam.gdzt;
		obj.rwlx = searchParam.rwlx;
		obj.rwzlx = searchParam.rwzlx;
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		
		AjaxUtil.ajax({
		   url:basePath+"/productionmessage/workersimplex/scheduledTaskList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			    finishWait();
			    if(data.rows !=""){
			    	
					   appendContentHtml(data.rows);
					   
					 	 new Pagination({
								renderTo : "pagination",
								data: data,
								sortColumn : sortType,
								orderType : sequence,
								goPage:function(a,b,c){
									AjaxGetDatasWithSearch(a,b,c);
								}
							}); 
					   
					// 标记关键字
					   signByKeyword($("#keyword_search").val(),[3,5,11,14]);
					   
				   } else {
				
					  $("#planelist").empty();
					  $("#pagination").empty();
					  $("#planelist").append("<tr><td class='text-center' colspan=\"16\">暂无数据 No data.</td></tr>");
				   }
			    new Sticky({tableId:'gdgs'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.fjzch = $("#fjzch_search").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 
		var jxArr ="";
		var jxAll = $('#gdzt').val();
		if(jxAll != null){
			for(var i = 0 ; i < jxAll.length ; i++){
				if('multiselect-all' != jxAll[i]){
					jxArr+=jxAll[i]+",";
				}
			}
		}
		 
		 searchParam.gdzt =jxArr.substring(0,jxArr.length-1);
		 searchParam.rwlx = $("#rwlx").val();
		 searchParam.rwzlx = $("#rwzlx").val();
	 
		 var flightdate = $("#flightDate_search").val();
		 if(null != flightdate && "" != flightdate){
			 searchParam.fxDateBegin = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
			 searchParam.fxDateEnd = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
		 }
		
		 
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if(Math.abs(row.pcl)>=Math.abs(yjtsJb1)&&row.gdzt!=2){
				   htmlContent = htmlContent + "<tr    bgcolor=\""+warningColor.level1+"\">";
			   }else{
				   htmlContent = htmlContent + "<tr     >";
			   }
			   
			   if(row.gdzt==1){
				   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align: middle;'>"+ "<i class='icon-ok color-blue cursor-pointer ' permissioncode='productionmessage:workersimplex:manage:01' onClick=\"edit('"
					+ row.id + "')\" title='确认  Confirm'></i></td>"; 
			   }else{
				   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align: middle;'></td>"; 
			   }
			   
			   htmlContent += "<td class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>"; 
			   
			   if(row.rwlx==1){
				   htmlContent = htmlContent + "<td class='fixed-column text-left' ><a href='javascript:void(0);' onclick=\"viewDetail('"+row.xggdid+"')\">"+StringUtil.escapeStr(row.rwdh)+"</a></td>"; 
			   }else if (row.rwlx==2){
				   if(row.rwzlx==1){
					   htmlContent = htmlContent + "<td class='fixed-column text-left'><a href='javascript:void(0);' onclick=\"viewDetail1('"+row.xggdid+"')\">"+StringUtil.escapeStr(row.rwdh)+"</a></td>"; 
				   }else if(row.rwlx==2){
					   htmlContent = htmlContent + "<td class='fixed-column text-left'><a href='javascript:void(0);' onclick=\"viewDetail2('"+row.xggdid+"')\">"+StringUtil.escapeStr(row.rwdh)+"</a></td>"; 
				   }
			   }else{
				   htmlContent = htmlContent + "<td class='fixed-column text-left' ><a href='javascript:void(0);' onclick=\"viewDetail3('"+row.xggdid+"')\">"+StringUtil.escapeStr(row.rwdh)+"</a></td>"; 
			   }
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.rwxx)+"'>"+StringUtil.escapeStr(row.rwxx)+"</td>";  
			   if(row.rwlx==1){
				   htmlContent = htmlContent + "<td class='text-left'>定检执行任务</td>";   
			   } else if (row.rwlx==2){
				   if(row.rwzlx==1){
					   htmlContent = htmlContent + "<td class='text-left'>非例行-时控件工单</td>";   
				   }else if(row.rwzlx==2){
					   htmlContent = htmlContent + "<td class='text-left'>非例行-附加工单</td>";   
				   }else{
					   htmlContent = htmlContent + "<td class='text-left'>非例行-排故工单</td>";   
				   }
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>EO工单任务</td>"; 
			   }
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.fxrq).substring(0,10)+"</td>"; 
			   
				 if(row.jhgsRs!=0&&row.jhgsXs!=0){
					 htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.jhgsRs)+"人x"+formatUndefine(row.jhgsXs)+"时="+formatUndefine(row.jhgsRs)*formatUndefine(row.jhgsXs)+"时</td>";  
				 }else{
					 htmlContent = htmlContent + "<td class='text-left'>0</td>";  
				 }
				 if(row.sjgsRs!=0&&row.sjgsXs!=0){
					 htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.sjgsRs)+"人x"+formatUndefine(row.sjgsXs)+"时="+formatUndefine(row.sjgsRs)*formatUndefine(row.sjgsXs)+"时</td>";  
				 }else{
					 htmlContent = htmlContent + "<td class='text-left'>0</td>";  
				 }
			   
			   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.pcl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.dcbbh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.tjrxx)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.tjrq)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.gsshBz)+"'>"+StringUtil.escapeStr(row.gsshBz)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(zts[row.gdzt])+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>"; 
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#planelist").empty();
		   $("#planelist").html(htmlContent);
		   refreshPermission();
	 }
	
	 
	//查看详情
	 function viewDetail(xggdid){
		 window.open(basePath+"/project/checklist/Looked?id="+ xggdid);
	 }
	 //查看详情
	 function viewDetail1(xggdid){
		 window.open(basePath+"/project/workorder/intoEdit?id="+xggdid + "&gddlx=2");
	 }
	 //查看详情
	 function viewDetail2(xggdid){
		 window.open(basePath+"/project/workorder/Looked?id="+xggdid + "&gddlx=2");
	 }
	 //查看详情
	 function viewDetail3(xggdid){
		 window.open(basePath+"/project/workorder/Looked?id="+xggdid + "&gddlx=3");
	 }
	 
	 //修改技术文件上传
	 function edit(id){
		 window.location =basePath+"/productionmessage/workersimplex/initModify/"+$.trim(id)+"?pageParam="+encodePageParam();
	 }
	 
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf("sorting_asc")>=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderStyle.split("_")[1]);
	}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
		 //信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
		
	/**
	 * 更改选中的飞机
	 */
	function changeSelectedPlane(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
	}
	
	
	 //搜索条件重置
	 function searchreset(){
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch").find("textarea").each(function(){
			 $(this).val("");
		 });
		 
		 $("#divSearch").find("select").each(function(){
				$(this).val("");
			});
	
		 var zzjgid=$('#zzjgid').val();
		 $("#keyword_search").val("");
		 $("#dprtcode").val(zzjgid);
		 
		$('#gdzt_sel').empty();
		$('#gdzt_sel').html("<select multiple='multiple' id='gdzt' >" +
				"<option value='1' selected='selected'>未确认</option>"+
							"		<option value='2'>已确认</option>"+
							"		<option value='3'>无需确认</option>" +
				"</select>");
		
		$('#gdzt').multiselect({
		    buttonClass: 'btn btn-default',
		    buttonWidth: 'auto',
		    numberDisplayed:8,
		    includeSelectAllOption: true,
		    onChange:function(element,checked){
	  	    }
	    });
		
		 var SelectArr = $("#fjzch_search_sel select");
		  SelectArr[0].options[0].selected = true; 
		  changeOrganization();
		  decodePageParam();
	 }
	 
	//搜索条件显示与隐藏 
	function search() {
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}
	}
		
		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				});
		 $('#example27').multiselect({
			buttonClass : 'btn btn-default',
			buttonWidth : 'auto',

			includeSelectAllOption : true
		}); 
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		}
		 
		//只能输入数字和小数
		 function clearNoNum(obj){
		      //先把非数字的都替换掉，除了数字和.
		      obj.value = obj.value.replace(/[^\d.]/g,"");
		      //必须保证第一个为数字而不是.
		      obj.value = obj.value.replace(/^\./g,"");
		      //保证只有出现一个.而没有多个.
		      obj.value = obj.value.replace(/\.{2,}/g,".");
		      //保证.只出现一次，而不能出现两次以上
		      obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		 }
