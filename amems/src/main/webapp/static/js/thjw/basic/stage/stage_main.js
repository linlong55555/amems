//页面初始化
var pageParam = {};
$(function(){
	//导航
	Navigation(menuCode, '', '', 'STAGE', '甘清', '2017-09-15', '甘清', '2017-09-15');
	//初始化校验
	validation();
	logModal.init({
		code : 'D_024' //加载日志
	});
	//校验权限
	goPage(1,"auto","desc");
});

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}

//收集前端查询参数
function gatherSearchParam() {
	 var searchParam = {};
	 searchParam.dprtcode = $("#dprtcode").val();
	 searchParam.keyword = $("#keyword_search").val().trim();
	 return searchParam;
}
//查询实现方法
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence) {
	 var searchParam = gatherSearchParam();
		pagination = {page:pageNumber, sort:sortType, order:sequence, rows:20};
		pageParam = pagination; //保留结果值 
	    searchParam.pagination = pagination;
	    var id =  $("#stageid").val();
	    if (id !="") {
	    	searchParam.id = id;
	    	$("#stageid").val("");
	    }
	    startWait();
	    AjaxUtil.ajax({
			   url: basePath+"/basic/stage/getstageList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   modal:$("#AddEditAlertModal"),
			   success:function(data){
			   finishWait();
			   if(data.rows!=null && data.rows!=""){
				    appendContentHtml(data.rows);
				    new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
				    FormatUtil.removeNullOrUndefined();
		 			signByKeyword($("#keyword_search").val(),[2,3,5],"#stage_list tr td");
			   } else {
				    $("#stage_list").empty();
					$("#pagination").empty();
					$("#stage_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
			   }
			 //锁定表头 & 操作列
			    new Sticky({
					tableId : 'stage_list_table'
				});
	 		}
	     }); 
}


//拼接content内容
function appendContentHtml(list) {
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr  bgcolor=\"#f9f9f9\" >";
		} else {
			htmlContent += "<tr  bgcolor=\"#fefefe\">";
		}
		 htmlContent = htmlContent + "<td class='text-center fixed-column'>" +
		    "<i name='edit' class='icon-edit color-blue cursor-pointer checkPermission' " +
		   "  onClick=edit('"+ row.id +"') title='修改 Edit' permissioncode='basic:stage:main:edit'></i>&nbsp;&nbsp;" +
			"<i name='remove' class='icon-trash  color-blue cursor-pointer checkPermission' " +
			"permissioncode='basic:stage:main:del'  onClick=del('"+ row.id +"') title='删除Delete'></i></td>"; 
		
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jdbh))+"'>"+StringUtil.escapeStr(formatUndefine(row.jdbh))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jdmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.jdmc))+"</td>";
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.xc))+"'>"+StringUtil.escapeStr(formatUndefine(row.xc))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jdms))+"'>"+StringUtil.escapeStr(formatUndefine(row.jdms))+"</td>";
	    /* htmlContent += "<td class='text-left' title='"+(row.dw_dprt?StringUtil.escapeStr(formatUndefine(row.dw_dprt.dprtname)):'')+"'>"+(row.dw_dprt?StringUtil.escapeStr(formatUndefine(row.dw_dprt.dprtname)):'')+"</td>"; */
	    htmlContent += "<td class='text-left' title='"+(row.whrid?StringUtil.escapeStr(formatUndefine(row.whr)):'')+"'>"+(row.whrid?StringUtil.escapeStr(formatUndefine(row.whr)):'')+"</td>"; 
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.whsjstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.whsjstr))+"</td>"; 
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";

	    htmlContent += "</tr>";	
 });
	$("#stage_list").empty();
	$("#stage_list").html(htmlContent);
	refreshPermission(); 
}

//modal添加弹窗
function add() {
	//移除验证
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	validation();
	
	 $('#jdbh').removeAttr("disabled"); 
	 
	 $("#modalName").html("新增阶段");
	 $("#modalEname").html("Add Stage");
	 $("#commitButton").attr("name","add");	
	 modalEmpty();
	 cleanInputContent();
	 $("#AddEditAlertModal").modal("show");
}

//删除阶段
function del(id) {
	 AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		 AjaxUtil.ajax({
			 url:basePath+"/basic/stage/del/"+id,
			 type:"post",
			 contentType:"application/json;charset=utf-8",
			 dataType:"json",
			 modal:$("#AddEditAlertModal"),
			 success:function(massge){
			     AlertUtil.showMessage("删除成功!");
				 goPage(1,"auto","desc");
			 }
		 });
	 }});
}

//modal编辑弹窗
function edit(id) {
	//移除验证
	 $("#form").data('bootstrapValidator').destroy();
	 $('#form').data('bootstrapValidator', null);
	 validation();
	
	 $("#modalName").html("编辑阶段");
	 $("#modalEname").html("Edit Stage");
	 $("#commitButton").attr("name","edit");	
	 modalEmpty();
	 cleanInputContent();
	 var object={};
		object.id = id;
	 
	$("#jdbh").attr("disabled","disabled"); 
		
	 startWait($("#AddEditAlertModal"));
	 AjaxUtil.ajax({
			url: basePath+"/basic/stage/getstagebyid",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(object),
			dataType:"json",
			modal:$("#AddEditAlertModal"),
			success:function(massge){
				finishWait($("#AddEditAlertModal"));
				$("#jdbh").val(massge.stage.jdbh);
				$("#jdmc").val(massge.stage.jdmc);
				$("#xc").val(massge.stage.xc);
				$("#jdms").val(massge.stage.jdms);
				$("#stageid").val(massge.stage.id);
			}
		});
	 $("#AddEditAlertModal").modal("show");
}


//新增或更新stage
function saveUpdate(){
	var operation = $("#commitButton").attr("name");
	if (operation == "add") { //新增操作
		addObject();
	} else if (operation == "edit"){ //编辑
		editObject();
	}
}

//保存编辑的信息
function editObject() {
	var dprtcode = $("#dprtcode").val();
	var jdbh = $.trim($("#jdbh").val());
	var jdmc = $.trim($("#jdmc").val());
	var xc = $.trim($("#xc").val());
	var jdms = $.trim($("#jdms").val());
	var obj={};
		obj.dprtcode = dprtcode;
		obj.jdbh = jdbh;
		obj.jdmc = jdmc;
		obj.xc = xc;
		obj.jdms = jdms;
		obj.id = $("#stageid").val();
	    startWait($("#AddEditAlertModal"));
	  AjaxUtil.ajax({
			url: basePath+"/basic/stage/updatestage",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#AddEditAlertModal"),
			success:function(massge){
				finishWait($("#AddEditAlertModal"));
				$("#AddEditAlertModal").modal("hide");
				AlertUtil.showMessage("编辑成功！");
				//goPage(1,"auto","desc");	
				goPage(pageParam.page, pageParam.sort, pageParam.order);	
			}
		});
}

//验证
function validation(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	xc: {
	                validators: {
 	                    regexp: {
 	                        regexp: /^[0-9]*$/,
 	                        message: '项次必须为数字'
 	                    }
	                }
	            },
	            jdbh: {
	            	validators: {
	            		notEmpty: {
	            			message: '阶段编号不能为空'
	            		}
	            	}
	            }
        	}
    });
}


//新增信息至后台
function addObject() {
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
	   return false;
	}
	var dprtcode = $("#dprtcode").val();
	var jdbh = $.trim($("#jdbh").val());
	var jdmc = $.trim($("#jdmc").val());
	var xc = $.trim($("#xc").val());
	var jdms = $.trim($("#jdms").val());
	var obj={};
	obj.dprtcode = dprtcode;
	obj.jdbh = jdbh;
	obj.jdmc = jdmc;
	obj.xc = xc;
	obj.jdms = jdms;
	
	startWait($("#AddEditAlertModal"));
	AjaxUtil.ajax({
		url: basePath+"/basic/stage/addstage",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		modal:$("#AddEditAlertModal"),
		success:function(massge){
			finishWait($("#AddEditAlertModal"));
			$("#AddEditAlertModal").modal("hide");
			AlertUtil.showMessage("增加成功！");
			$("#stageid").val(massge.stage.id); //赋值
			//goPage(1,"auto","desc");	
			goPage(pageParam.page, pageParam.sort, pageParam.order);
		}
	});
}


//隐藏出现异常的感叹号
function modalEmpty(){
	 AlertUtil.hideModalFooterMessage();
}

//清除文本域的内容
function cleanInputContent() {
	$("#jdbh").val("");
	$("#jdmc").val("");
	$("#xc").val("");
	$("#jdms").val("");
	$("#stageid").val("");
}

// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { // 重置class
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
// 信息检索
function search(){
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	goPage(1,"auto","desc");
}


//组织机构发生变化时
function changeList() {
	search();
}

//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
		if(formatUndefine(winId) != ""){
			$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
		}else{
			search(); //调用主列表页查询
		}
	}
};