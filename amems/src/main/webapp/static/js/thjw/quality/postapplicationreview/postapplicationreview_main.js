var sqlx=[];
	sqlx[1]="初次";
	sqlx[2]="延期";
	sqlx[3]="增加";
/**
 *初始化当前js
 */
$(function(){
	Navigation(menuCode, '', '', 'SC11-1', '林龙', '2017-10-12', '林龙', '2017-10-14');//加载导航栏
    decodePageParam();				//解码页面查询条件和分页 并加载数据
    refreshPermission();			//权限初始化
	logModal.init({code:'B_Z_005'});//初始化日志功能
	
	//执行待办
    if(todo_ywid){
    	if(todo_jd == 2){
    		Post_Update_Review_Modal.open(todo_ywid);
    	}
    	todo_ywid = null;
    }
	
});

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	TableUtil.resetTableSorting("postapplicationTable");
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#dprtcode").val(params.dprtcode);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}

/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#dprtcode").val());     //组织机构
	params.keyword = $.trim($("#keyword_search").val());//关键字
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
 */
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	

/**
 * 切换组织机构
 */
function onchangeDprtcode(){
	
}

function search(){
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("postapplicationTable");
}

/**
 * 查询条件
 */
function gatherSearchParam (){
	var searchParam={};
	var paramsMap={};
	searchParam.zt=$.trim($("#zts").val()); 				//状态
	searchParam.dprtcode=$.trim($("#dprtcode").val());		//组织机构
	searchParam.keyword=$.trim($('#keyword_search').val()); //关键字
	
	searchParam.shrid=userId; 								//审核人为当前登录人
	paramsMap.sqgw=$.trim($("#sqgw").val()); 				//申请岗位
	paramsMap.sqrs=$.trim($("#sqrs").val()); 				//申请人
	searchParam.paramsMap=paramsMap;
	return searchParam;
}

/**
 * 查询主单分页列表
 */
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	if (id != "") {
		searchParam.id = id;
		id = "";
	}
	AjaxUtil.ajax({
	   url:basePath+"/quality/post/application/queryAllPageList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
 			if(data.total >0){
 			appendContentHtml(data.rows);
 			new Pagination({
				renderTo : "pagination",
				data: data,
				sortColumn : sortType,
				orderType : sequence,
				extParams:{},
				goPage: function(a,b,c){
					AjaxGetDatasWithSearch(a,b,c);
				}
			});
 			// 标记关键字
			signByKeyword($("#keyword_search").val(), [2,3,4,6]);
 			}else{
	 			$("#postapplication_list").empty();
				$("#pagination").empty();
				$("#postapplication_list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
 			}
 			new Sticky({tableId:'postapplicationTable'}); //初始化表头浮动
	   }
  }); 	
}

/**
 * 表格拼接
 */
function appendContentHtml(list){
	 var htmlContent = '';
	 $.each(list,function(index,row){
			htmlContent += "<tr>";
			htmlContent += "<td class='fixed-column text-center'>";
		    if( row.zt ==2 ){
			    htmlContent += "<i class=' icon-foursquare color-blue cursor-pointer checkPermission' " 
    			+ " permissioncode='quality:post:review:main:01' onClick=\"Post_Update_Review_Modal.open('"+row.id+"')\" title='审核 Audit'></i>";//修改
		    }
		    htmlContent += "</td>";
		    
		    htmlContent += "<td class='fixed-column text-left' ><a href=\"javascript:findView('"+row.id+"')\">"+StringUtil.escapeStr(row.sqsqdh)+"</a></td>";
		    htmlContent += "<td class='text-left' ><a href=\"javascript:findWxdaView('"+row.sqrdaid+"')\">"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.rybh:'')+"</a></td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.xm:'')+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.xrzz:'')+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.szdw:'')+"</td>";
		    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(sqlx[row.sqlx])+"</td>";
		    htmlContent += "<td class='text-left' >"; 
		    if(row.zt==9){
		    	htmlContent += "<a href='javascript:void(0);'  " 
		    		+ "sqsqdh='"+StringUtil.escapeStr(row.sqsqdh)+"'   zt='"+row.zt+"' gbyy='"+StringUtil.escapeStr(row.gbyy)+"'" 
		    		+ "gbczsj='"+StringUtil.escapeStr(row.gbrq)+"'   gbrid='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.gbr:'')+"' " 
		    		+"onclick=\"zdjs('"+row.id+"', this , false)\">"+DicAndEnumUtil.getEnumName('postStatusEnum',row.zt)+"</a></td>";
		    }else{
		    	htmlContent += DicAndEnumUtil.getEnumName('postStatusEnum',row.zt); 
		    }
		    htmlContent += "</td>"; 
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgbh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgmc:'')+"</td>";
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.whr?row.whr.username:'')+" "+StringUtil.escapeStr(row.whr?row.whr.realname:'')+"</td>";
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
		    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.dprt?row.dprt.dprtname:'')+"</td>"; 
		    htmlContent += "</tr>" ;
		    
		    $("#postapplication_list").empty();
		    $("#postapplication_list").html(htmlContent);
		    refreshPermission();			//权限初始化
		    TableUtil.addTitle("#postapplication_list tr td"); //加载td title
	 });
}

/**
 *跳转当前页面查看页面
 */
function findView(id){
	window.open(basePath+"/quality/post/application/find/"+id);
}

/**
 * 查看维修档案人员
 */
function findWxdaView(id){
	window.open(basePath + "/quality/maintenancepersonnel/view/"+id);
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
	 
	 var SelectArr1 = $("#ztsDiv select"); //状态重置为第一个
	 SelectArr1[0].options[2].selected = true;
	 
	 $("#keyword_search").val("");
	 $("#sqgw").val("");
	 $("#dprtcode").val(userJgdm);
}

/**
 *删除
 */
function invalid(id,zt){
	var obj = {};
	var paramsMap = {};
	paramsMap.currentZt =zt; //当前状态
	obj.paramsMap = paramsMap;
	obj.id=id;
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/quality/post/application/delete",
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

//指定结束
function zdjs(id,e,isEdit){
	var sqsqdh=$(e).attr("sqsqdh"); //单号
	var gbyy=$(e).attr("gbyy"); 	//完成原因
	var jsr=$(e).attr("gbrid");		//完成人
	var jssj=$(e).attr("gbczsj");	//完成日期
	var zt=$(e).attr("zt"); 		//状态
	ConfirmModal.show({
 		chinaHead:'授权单号',//单号中文名称
 		englishHead:'Post No.',//单号英文名称
 		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
 		jsdh:sqsqdh,//保留单号
 		jsr:jsr,//完成人
 		jssj:jssj,//完成时间
 		jsyy:gbyy,//完成原因
 		zt:zt,//状态
 		callback: function(data){//回调函数
 			if(null != data && data != ''){
 				
 				var paramsMap = {};
 				paramsMap.currentZt = zt; //当前状态
 				data.paramsMap = paramsMap;
 				
 				data.id=id; //id
 				gConfirm(data);//完成提交
 			}
 		}
 	});
}

//完成
function gConfirm(obj){
	 startWait($("#ConfirmModal"));
   	 AjaxUtil.ajax({
 		url:basePath+"/quality/post/application/gConfirm",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		modal:$("#ConfirmModal"),
 		success:function(data){
 			finishWait($("#ConfirmModal"));
 			AlertUtil.showMessage('指定结束成功!');
 			$("#ConfirmModal").modal("hide");
 			decodePageParam();
 		}
   	  });
}
/**
 * 字段排序
 */
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

/**
 * 搜索条件显示与隐藏 
 */
function openOrHide(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
	App.resizeHeight();
}
