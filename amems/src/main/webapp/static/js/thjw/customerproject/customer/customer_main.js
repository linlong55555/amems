//页面初始化
var pageParam = {};
$(function(){
	Navigation(menuCode, '', '', 'customer', '甘清', '2017-09-18', '甘清', '2017-09-19');
	validation();
	logModal.init({
		code : 'D_019' //加载日志
	});
	//initDict();
	goPage(1,"auto","desc");
});

//新增初始化数据字典内容
function initDictAdd(){
	$("#gj").empty();
	$("#gj").append("<option value=''>请选择国家</option>");
	DicAndEnumUtil.registerDic('9', 'gj', $("#dprtId").val());
	
	$("#khfl").empty();
	$("#khfl").append("<option value=''>请选择客户分类</option>");
	DicAndEnumUtil.registerDic('61', 'khfl', $("#dprtId").val());
}

//新增初始化数据字典内容
function initDictEdit(){
	$("#gj").empty();
	$("#gj").append("<option value=''>请选择国家</option>");
	DicAndEnumUtil.registerDic('9', 'gj', $("#dprtcode").val());
	
	$("#khfl").empty();
	$("#khfl").append("<option value=''>请选择客户分类</option>");
	DicAndEnumUtil.registerDic('61', 'khfl', $("#dprtcode").val());
}

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
	    var id =  $("#customerid").val();
	    if (id !="") {
	    	searchParam.id = id;
	    	$("#customerid").val("");
	    }
	    startWait();
	    AjaxUtil.ajax({
			   url: basePath+"/customerproject/customer/getcustomerList",
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
		 			signByKeyword($("#keyword_search").val(),[2,3],"#customer_list tr td");
			   } else {
				    $("#customer_list").empty();
					$("#pagination").empty();
					$("#customer_list").append("<tr><td class='text-center' colspan=\"13\">暂无数据 No data.</td></tr>");
			   }
			   //锁定表头 & 操作列
			    new Sticky({
					tableId : 'customer_list_table'
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
		   "<i name='edit' class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='customerproject:customer:main:edit'" +
		   "  onClick=edit('"+ row.id +"') title='修改 Edit' ></i>&nbsp;&nbsp;" +
			"<i name='remove' class='icon-trash  color-blue cursor-pointer checkPermission' permissioncode='customerproject:customer:main:del'" +
			" onClick=del('"+ row.id +"') title='删除Delete'></i></td>"; 
		
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.khbm))+"'>"+StringUtil.escapeStr(formatUndefine(row.khbm))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.khmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.khmc))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.lxdh))+"'>"+StringUtil.escapeStr(formatUndefine(row.lxdh))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.gj))+"'>"+StringUtil.escapeStr(formatUndefine(row.gj))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.cs))+"'>"+StringUtil.escapeStr(formatUndefine(row.cs))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.yb))+"'>"+StringUtil.escapeStr(formatUndefine(row.yb))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"'>"+StringUtil.escapeStr(formatUndefine(row.bz))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.khfl))+"'>"+StringUtil.escapeStr(formatUndefine(row.khfl))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.khjc))+"'>"+StringUtil.escapeStr(formatUndefine(row.khjc))+"</td>";
	    htmlContent += "<td class='text-left' title='"+(row.whrid?StringUtil.escapeStr(formatUndefine(row.whr)):'')+"'>"+(row.whrid?StringUtil.escapeStr(formatUndefine(row.whr)):'')+"</td>"; 
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.whsjstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.whsjstr))+"</td>"; 
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";

	    htmlContent += "</tr>";	
    });
	$("#customer_list").empty();
	$("#customer_list").html(htmlContent);
	refreshPermission(); 
}

//modal添加弹窗
function add() {
	initDictAdd(); //新增时的初始化
	//移除验证
	 $("#form").data('bootstrapValidator').destroy();
	 $('#form').data('bootstrapValidator', null);
	 validation();
	 
	 //移除只读属性
	 $('#khbm').removeAttr("disabled"); 
	 
	 $("#modalName").html("新增客户");
	 $("#modalEname").html("Add Customer");
	 $("#commitButton").attr("name","add");	
	 modalEmpty();
	 cleanInputContent();
	 $("#AddEditAlertModal").modal("show");
}

//modal编辑 加载客户信息弹窗
function edit(id) {
	 initDictEdit();
	 $("#form").data('bootstrapValidator').destroy();
	 $('#form').data('bootstrapValidator', null);
	 validation(); 
	 
	 //添加只读属性
	 $("#khbm").attr("disabled","disabled"); 
	 
	 $("#modalName").html("编辑客户");
	 $("#modalEname").html("Edit Customer");
	 $("#commitButton").attr("name","edit");	
	 modalEmpty();
	 cleanInputContent();
	// var id = $(obj).attr("objid");
	// var dprtcode = $(obj).attr("dprtcode");
	 var object={};
	//	object.dprtcode = dprtcode;
		object.id = id;
	 
	 startWait($("#AddEditAlertModal"));
	 AjaxUtil.ajax({
			url: basePath+"/customerproject/customer/getcustomerbyid",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(object),
			dataType:"json",
			modal:$("#AddEditAlertModal"),
			success:function(massge){
				finishWait($("#AddEditAlertModal"));
				$("#khbm").val(massge.customer.khbm);
				$("#khmc").val(massge.customer.khmc);
				//国家
				//$("#gj").val(massge.customer.gj);
				$("#gj").find("option[value='"+massge.customer.gj+"']").attr("selected",true);
				
				$("#cs").val(massge.customer.cs);
				$("#khfl").val(massge.customer.khfl);
				//$("#khfl").val(massge.customer.khfl);
				$("#gj").find("option[value='"+massge.customer.khfl+"']").attr("selected",true);
				$("#lxdh").val(massge.customer.lxdh);
				$("#yb").val(massge.customer.yb);
				$("#khjc").val(massge.customer.khjc);
				$("#bz").val(massge.customer.bz);
				$("#customerid").val(massge.customer.id);
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

//删除客户信息
function del(id) {
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		 AjaxUtil.ajax({
			 url:basePath+"/customerproject/customer/del/"+id,
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

//保存编辑的信息
function editObject() {
	var dprtcode = $("#dprtcode").val();
	var khbm = $.trim($("#khbm").val());
	var khmc = $.trim($("#khmc").val());
	var gj = $.trim($("#gj").val());
	var cs = $.trim($("#cs").val());
	var khfl = $.trim($("#khfl").val());
	var lxdh = $.trim($("#lxdh").val());
	var yb = $.trim($("#yb").val());
	var khjc = $.trim($("#khjc").val());
	var bz = $.trim($("#bz").val());
	var obj={};
	obj.dprtcode = dprtcode;
	obj.khbm = khbm;
	obj.khmc = khmc;
	obj.gj = gj;
	obj.cs = cs;
	obj.khfl = khfl;
	obj.lxdh = lxdh;
	obj.yb = yb;
	obj.khjc = khjc;
	obj.bz = bz;
    obj.id = $("#customerid").val();
	
	  startWait($("#AddEditAlertModal"));
	  AjaxUtil.ajax({
			url: basePath+"/customerproject/customer/updatecustomer",
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
        	khbm: {
                validators: {
                	notEmpty: {
                        message: '客户编号不能为空'
                    }
                }
            },
        	khmc: {
	                validators: {
	                	notEmpty: {
	                        message: '客户名称不能为空'
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
	var khbm = $.trim($("#khbm").val());
	var khmc = $.trim($("#khmc").val());
	var gj = $.trim($("#gj").val());
	var cs = $.trim($("#cs").val());
	var khfl = $.trim($("#khfl").val());
	var lxdh = $.trim($("#lxdh").val());
	var yb = $.trim($("#yb").val());
	var khjc = $.trim($("#khjc").val());
	var bz = $.trim($("#bz").val());
	var obj={};
	obj.dprtcode = dprtcode;
	obj.khbm = khbm;
	obj.khmc = khmc;
	obj.gj = gj;
	obj.cs = cs;
	obj.khfl = khfl;
	obj.lxdh = lxdh;
	obj.yb = yb;
	obj.khjc = khjc;
	obj.bz = bz;
	startWait($("#AddEditAlertModal"));
	AjaxUtil.ajax({
		url: basePath + "/customerproject/customer/addcustomer",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		modal:$("#AddEditAlertModal"),
		success:function(massge){
			finishWait($("#AddEditAlertModal"));
			$("#AddEditAlertModal").modal("hide");
			AlertUtil.showMessage("增加成功！");
			$("#customerid").val(massge.customer.id);
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
	$("#form").find("input[type='text']").val("");
	$("#bz").val("");
	$("#form").find("select").val("");
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
function search() {
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	goPage(1,"auto","desc");
}

//切换组织机构码
function changeList() {
	search();
}

function exportExcel(){
	var keyword = $.trim($("#keyword_search").val());
	var dprtcode = $("#dprtcode").val();
	window.open(basePath+"/customerproject/customer/CustomerInfo.xls?dprtcode="
 				+ dprtcode + "&&keyword="+ encodeURIComponent(keyword));
	
}