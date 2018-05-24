var pagination = {};
var mainpagination={};
var chosesId=[];
/**
 * 初始化
 */
$(document).ready(function(){
	Navigation(menuCode, '', '', 'SC12-1', '林龙', '2017-09-12', '林龙', '2017-09-12');//加载导航栏
 	fjzchlist();  						//加载飞机注册号
 	fjzchlists();
 	refreshPermission(); 				//加载按钮权限
 	decodePageParam(); 					//初始化列表数据
	logModal.init({code : 'B_S_019'});  //日志
	
	$('#hbrq').datepicker({
		autoclose : true,
		clearBtn : true
	});
	
	$("#alertModalFjgzjk").on("hidden.bs.modal", function() {
		$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
		$(".modal-footer").find('.alert-info-message').attr('title','').empty();
	});
});

function fjzchlist(){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
	var planeRegOption = "<option value=''>显示全部All</option>";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
	}
	$("#fjzch").html(planeRegOption); 
}


function fjzchlists(){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:userJgdm});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
	}
	$("#Planefaultmonitoring_Open_Modal_fjzch").html(planeRegOption); 
	var SelectArr1 = $("#Planefaultmonitoring_Open_Modal_fjzch_div select");
	if(SelectArr1[0].options[0] != undefined){
		SelectArr1[0].options[0].selected = true;
	}
}

function encodePageParam() {
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword").val());
	params.dprtcode = $.trim($("#dprtcode").val());
	params.fjzch = $.trim($("#fjzch").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

function decodePageParam() {
	try {
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword").val(params.keyword);
		$("#dprtcode").val(params.dprtcode);
		$("#fjzch").val(params.fjzch);
		if (pageParamJson.pagination) {
			goPage(pageParamJson.pagination.page,
					pageParamJson.pagination.order,
					pageParamJson.pagination.sort);
		} else {
			goPage(1, "desc", "auto");
		}
	} catch (e) {
		goPage(1, "desc", "auto");
	}
}			

function goPage(pageNumber, sortType, sortField) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
}
 
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
		var searchParam = {};
		searchParam.keyword = $.trim($("#keyword").val());
		var dprtcode = $.trim($("#dprtcode").val());
		searchParam.zjh=$.trim($("#zjhMax").val());				//章节号
		if (dprtcode != '') {
			searchParam.dprtcode = dprtcode;
		}

		var fjzch = $.trim($("#fjzch").val());
		if (fjzch != '') {
			searchParam.fjzch = fjzch;
		}
		pagination = {
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 10
		};
		mainpagination={
				page : pageNumber,
				sort : sequence,
				order : sortType,
				rows : 10	
		};
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/produce/fault/monitoring/getListPage",
			type : "post",
			data : JSON.stringify(searchParam),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					appendContentHtml(data.rows);
					new Pagination({
						renderTo : "fjgzjk_pagination",
						data : data,
						sortColumn : sortType,
						orderType : sequence,
						goPage : function(a, b, c) {
							AjaxGetDatasWithSearch(a, b, c);
						}
					});
					// 替换null或undefined
					FormatUtil.removeNullOrUndefined();
					// 标记关键字
					signByKeyword($.trim($("#keyword").val()),[ 2,4 ],"#fjgzjk_list tr td");
				} else {
					$("#fjgzjk_list").empty();
					$("#fjgzjk_pagination").empty();
					$("#fjgzjk_list").append("<tr ><td  class='text-center'  colspan=\"11\">暂无数据 No data.</td></tr>");
				}
				hideBottom();
				new Sticky({tableId : 'fjgz_record_sheet_table'});
			}
		});
}
 	
/**
 * 加载数据
 * @param list
 */
function appendContentHtml(list){
 		chosesId=[];
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent +="<tr id='"+row.id+"'  dprtId='"+row.dprtcode+"' fjzch='"+row.fjzch+"' gbzt='"+row.gbzt+"' onclick='showDcgzcl(this)'  >";
			htmlContent += "<td style=\"vertical-align: middle;\" class='fixed-column  text-center '>" ;
				if(row.gbzt!=1){
					htmlContent += "<i class='icon-edit color-blue cursor-pointer' permissioncode='produce:fault:monitoring:main:02' title='修改 Edit' onclick=\"Planefaultmonitoring_Update_Modal.open('"+row.id+"')\" ></i>&nbsp;&nbsp;" ;
					htmlContent +=	"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission'  permissioncode='produce:fault:monitoring:main:07' title='删除 Delete' onClick='deleteFjgzjk(\""+ row.id+ "\",\""+ row.zt+ "\")' ></i>&nbsp;&nbsp;";
					htmlContent +=	"<i class='icon-power-off color-blue cursor-pointer checkPermission'  permissioncode='produce:fault:monitoring:main:03' title='关闭  Close' onClick='addFjgzjk(\""+ row.id+ "\")' ></i>&nbsp;&nbsp;";
				}
				htmlContent +="<i class='icon-search color-blue cursor-pointer '  title='查看  View' onClick='look(\""+ row.id+ "\")' ></i>";
			htmlContent +="</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center ' >"+StringUtil.escapeStr(row.fjzch)+"</td>"; 
//		    var zjhRow=getZjhDescription(row.zjh);
//		    if(zjhRow){
//			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center ' >"+StringUtil.escapeStr(zjhRow.zjh)+"  "+StringUtil.escapeStr(zjhRow.ywms)+"</td>"; 
//		    }else{
//			    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center ' >  </td>"; 
//
//		    }
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center ' >"+StringUtil.escapeStr(row.paramsMap.zjhms)+"</td>"; 
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' >"+StringUtil.escapeStr(row.gzxx)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' >"+row.gzcs+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' >"+(row.zjfsrq!=null?row.zjfsrq.substring(0,10):'')+"</td>";
		    if(row.gbzt==1){
		    	htmlContent +="<td style=\"vertical-align: middle;\" ><a href='javascript:;' onClick='getFjgzjk(\""+ row.id+ "\")'>"+"关闭"+"</a></td>";
		    }else{
		    	htmlContent +="<td style=\"vertical-align: middle;\" >"+"未关闭"+"</td>";
		    }
		    htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) 
					|| (row.gkfjid != null && row.gkfjid != "")
					|| (row.gznrfjid != null && row.gznrfjid != "")){
					htmlContent += '<i qtid="'+row.id+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
			htmlContent += "</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' >"+StringUtil.escapeStr(row.bz)+"</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left'  >"+(StringUtil.escapeStr(row.zdrname)) +"</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' >"+row.whsj+"</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' >"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
		    htmlContent += "</tr>";
		    $("#fjgzjk_list").empty();
		    $("#fjgzjk_list").html(htmlContent);
		    initWebuiPopover();
		    TableUtil.addTitle("#fjgzjk_list tr td");
		    refreshPermission(); 				//加载按钮权限
	});
}

/**
 *删除
 */
function deleteFjgzjk(id,zt){
	var obj = {};
	var paramsMap = {};
	paramsMap.currentZt =zt; //当前状态
	obj.paramsMap = paramsMap;
	obj.id=id;
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/produce/fault/monitoring/delete",
			contentType:"application/json;charset=utf-8",
			type:"post",
			async: false,
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage("删除成功!");
				decodePageParam();
			}
		});
	}});
}

function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#work_card_main_table_top_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}

function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
         {mainid : $(obj).attr('qtid'), type : '附件'}
    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}


/**
 * 点击行选择
 */
function showDcgzcl(this_){
    	 var id = $(this_).attr("id");
    	 var fjzch = $(this_).attr("fjzch");
    	 var dprtcode=$(this_).attr("dprtId");
    	 $("#manidinfo").val(id);
		 $("#fjzchid").val(fjzch);
		 $("#dprtId").val(dprtcode);
		 showInfo(1, "desc", "auto");
		 $("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
		 
	 	if($(".bottom_hidden_content").css("display")=='block'){
	 		$(".bottom_hidden_content").slideUp(100);
	 	}
	 	$(".bottom_hidden_content").slideDown(100);
	 	new Sticky({tableId:'fjgz_record_sheet_table'}); //初始化表头浮动
	 	
	 	var isBottomShown = false;
		if($(".displayContent").is(":visible")){
			isBottomShown = true;
		}
	 	TableUtil.showDisplayContent();
	 	if($("#hideIcon").length==0){
	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($(".fenye"));
		}
	 	App.resizeHeight();
		if(!isBottomShown){
			TableUtil.makeTargetRowVisible($(this_), $("#fjgz_record_sheet_table"));
		}
	 	 
	 	//根据状态确定是否添加
	 	 var gbzt = $(this_).attr('gbzt');
	 	 if(gbzt == '1'){
	 		 $("#bottom_hidden_content").find('#addBtn').hide();
	 	 }else{
	 		$("#bottom_hidden_content").find('#addBtn').show();
	 	 }
}
     

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(
		function() { // 重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc")
					.addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#fjgzjk_pagination li[class='active']").text();
	goPage(currentPage, orderStyle.split("_")[1], obj);
}

function searchFjgzRecord(){
	 goPage(1, "desc", "auto");
	 TableUtil.resetTableSorting("fjgz_record_sheet_table");
}
//重置
function hideBottom(){
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}

//打开关闭对话框
function addFjgzjk(id){
	 clear();
	 $("#gbyymark").show();
	 $("#gbyyid").val(id);
	 $("#gbrname").val(userName+" "+$("#realname").val());
	 $("#gb").hide();
	 $("#qd").show();
	 $("#gbyy").attr("readonly",false);
	 $("#alertModalFjgzjk").modal("show");
}

//清空字段
function clear(){
	$("#gbyyid").val("");
	$("#gbr").val("");
	$("#gbyy").val("");
	$("#gbsj").val("");
 }

//确认关闭故障监控
function zdjsOver(){
	var param={};
	param.gbrid=$("#gbrid").val();
	param.gbyy=$("#gbyy").val();
	param.id=$("#gbyyid").val();
	if($.trim($("#gbyy").val())!=''){
	 AjaxUtil.ajax({
			url : basePath
					+ "/produce/fault/monitoring/addgbyy",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				AlertUtil.showMessage('保存成功!');
				searchFjgzRecord();
				$("#alertModalFjgzjk").modal('hide');
				refreshPermission();
			}
		});
	}else{
//		AlertUtil.showMessage("关闭原因不能为空！");
		AlertUtil.showModalFooterMessage("关闭原因不能为空！");
	}
 }

function look(id){
	window.open(basePath+"/produce/fault/monitoring/look?id="+id);
}

//切换组织机构时更改注册号
function changeDpr(){
	$("#dprtcode").val();
	fjzchlist();
	searchFjgzRecord();
}


//获取关闭原因
function getFjgzjk(id){
	 clear();
	 $("#gbyymark").hide();
	 $("#gb").show();
	 $("#qd").hide();
	 var param={};
	 param.id=id;
	 AjaxUtil.ajax({
			url : basePath
					+ "/produce/fault/monitoring/getgbyy",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				$("#gbrname").val(data.gbrname);
				$("#gbyy").val(data.gbyy);
				$("#gbsj").val(data.gbsj);
				$("#gbyy").attr("readonly",true);
				$("#alertModalFjgzjk").modal("show");
			}
		});   	
}

//章节弹框
function openChapterWin(){
	FixChapterModal.show({
		selected:$.trim($("#common_zjh_value").val()),
		dprtcode:$.trim($("#dprtcode").val()), //机构代码
		callback: function(data){//回调函数
			if(data != null && data.zjh != null){
				$("#common_zjh_value").val(data.zjh);
				$("#common_zjh_display").val(data.zjh+" "+data.ywms+" "+data.zwms);
				$('#monitoringForm').data('bootstrapValidator')  
	 		      .updateStatus("common_zjh_display", 'NOT_VALIDATED',null)  
	 		      .validateField("common_zjh_display"); 
			}else{
				$("#common_zjh_value").val("");
				$("#common_zjh_display").val("");
			}

		}
	});
}

function getZjhDescription(zjhNo){	
	var result=null;
	 AjaxUtil.ajax({
		url : basePath + "/project/fixchapter/upFixChapter",
		async: false,
		type : "get",
		data : {
			zjh : zjhNo,
			dprtcode :$("#dprtcode").val()
		},
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			if (data)
				result=data;
		}
	});
	   return result;
}

/**
 * //初始化章节号
 */
function initFixChapter(){
	var zjh = $.trim($("#zjhMax").val());
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode: $("#dprtcode").val(),
		callback: function(data){//回调函数
			if(data != null){
				var wczjhName = formatUndefine(data.zjh) + " " +formatUndefine(data.ywms);
				$("#zjhMax").val(data.zjh);
				$("#zjhmsMax").val(wczjhName);
			}else{
				$("#zjhMax").val('');
				$("#zjhmsMax").val('');
			}
		}
	});
}

/**
 * 搜索条件重置
 */
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
	 $("#keyword").val("");
	 $("#dprtcode").val(userJgdm);
	 fjzchlist();  						//加载飞机注册号
	 fjzchlists();
}

//搜索条件显示与隐藏 
function openOrHide() {
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