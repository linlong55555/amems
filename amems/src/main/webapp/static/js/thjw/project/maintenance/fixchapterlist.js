$(document).ready(function(){
	goPage(1,"auto","desc");//开始的加载默认的内容 
	Navigation(menuCode);
	//菜单设置  自己设置
	refreshPermission();       //触发按钮权限监控
	validation();
	validation2();
	// 初始化导入
	importModal.init({
		importUrl:"/project/fixchapter/excelImport",
		downloadUrl:"/common/templetDownfile/2",
		callback: function(data){
			// 刷新ATA章节号
			goPage(1,"auto","desc");
			$("#ImportModal").modal("hide");
		}
	});
	//初始化日志功能
	logModal.init({code:'D_005'});
});
function  validation(){
	validatorForm =  $('#form').bootstrapValidator({
		message: '数据不合法',
		feedbackIcons: {
			//valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			zjh: {
				validators: {
					notEmpty: {
						message: 'ATA章节号不能为空'
					},
					regexp: {
						regexp: /^[^\u4e00-\u9fa5]{0,}$/,
						message: '不能输入中文'
					},
					stringLength: {
						max: 15,
						message: '长度最多不超过15个字符'
					}
				}
			},
			ywms: {
				validators: {
					regexp: {
						regexp: /^[^\u4e00-\u9fa5]{0,}$/,
						message: '不能输入中文'
					},
					stringLength: {
						max: 60,
						message: '长度最多不超过60个字符'
					}
				}
			},
			rjcsj: {
				validators: {
					stringLength: {
						max: 300,
						message: '长度最多不超过300个字符'
					},
				}
			}
		}
	});
}

function validation2(){
	validatorForm =  $('#form2').bootstrapValidator({
		message: '数据不合法',
		feedbackIcons: {
			//valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			ywms2: {
				validators: {
					regexp: {
						regexp: /^[^\u4e00-\u9fa5]{0,}$/,
						message: '不能输入中文'
					},
					stringLength: {
						max: 60,
						message: '长度最多不超过60个字符'
					}
				}
			},
			rjcsj2: {
				validators: {
					stringLength: {
						max: 300,
						message: '长度最多不超过300个字符'
					}
				}
			}
		}
	});
}


//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence,searchParam){
	var searchParam = gatherSearchParam();
	searchParam.pagination = {page:1,sort:sortType,order:sequence,rows:100000};
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/fixchapter/selectFixChapter",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(searchParam),
		success:function(data){
			finishWait();
			if(data.total >0){
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
				signByKeyword($("#keyword_search").val(),[2,3,4]);
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
			}
			new Sticky({tableId:'fixchapter_table'});
		},
	}); 

}
//将搜索条件封装 
function gatherSearchParam(){
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());
	var dprtcode = $.trim($("#dprtcode_search").val());
	if('' != dprtcode){
		searchParam.dprtcode = dprtcode;
	}
	return searchParam;
}


function appendContentHtml(list){
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		} else {
			htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		}

		htmlContent = htmlContent + "<td class='text-center'>"+ "<div><i class='icon-edit color-blue cursor-pointer checkPermission'  permissioncode='project:fixchapter:main:02' zjh = '"+StringUtil.escapeStr(row.zjh) + "' onClick=\"edit(this,'"+row.dprtcode +"')\"  title='修改 Edit'></i>&nbsp;&nbsp;" +
		"<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='project:fixchapter:main:03' zjh = '"+StringUtil.escapeStr(row.zjh) + "' onClick=\"Delete(this,'"+row.dprtcode+"')\"  title='删除 Delete '></i>"+"</td>"; 
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zjh)+"'>"+StringUtil.escapeStr(row.zjh)+"</td>";  
		htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
		htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.rJcsj)+"'>"+StringUtil.escapeStr(row.rJcsj)+"</td>"; 
		htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.displayname)+"'>"+StringUtil.escapeStr(row.displayname)+"</td>";  
		htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.cjsj)+"</td>";  
		htmlContent = htmlContent + "<td>"+formatUndefine(row.dprtname)+"</td>";  
		htmlContent = htmlContent + "</tr>";  
	});
	$("#list").empty();
	$("#list").html(htmlContent);
	refreshPermission();       //触发按钮权限监控
}

//查看revision对应的工卡列表
function goToLinkPage(obj,rid){
	obj.stopPropagation(); //屏蔽父元素的click事件
	window.location =basePath+"/main/work/listpage?rid="+rid;
}

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass().addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle == "sorting_asc") {
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

//搜索条件重置
function searchreset(){
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});
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
};

//删除ATA章节号 
function Delete(obj,dprtcode){
	AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
		AjaxUtil.ajax({
			type : "post",
			url : basePath+"/project/fixchapter/deleteFixChapter",
			dataType : "json",
			data:{zjh:$(obj).attr("zjh"), dprtcode:$.trim(dprtcode)},
			success : function(data) {
				finishWait();
				AlertUtil.showMessage('删除成功！');
				searchRevision();
			}
		});
	}});
} 
function openAddATA(){
	validation();
	$("#alertModalAddATA").modal("show");
	AlertUtil.hideModalFooterMessage();
	$("#zjh").val("");
	$("#ywms").val("");
	$("#zwms").val("");
	$("#rJcsj").val("");
};


function saveFixChapter() {

	startWait($("#alertModalAddATA"));	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
		finishWait($("#alertModalAddATA"));
		return false;
	}

	var obj={};
	obj.zjh=$('#zjh').val();                   
	obj.ywms =$('#ywms').val();                
	obj.zwms= $('#zwms').val();                                            
	obj.rJcsj= $('#rJcsj').val();     
	AjaxUtil.ajax({
		type : 'post',
		url : basePath+'/project/fixchapter/getfixchapterConut',                                                              //验证ATA章节号是否重复
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType : 'json',
		modal:$("#alertModalAddATA"),
		success : function(data) {
			if (data == 0) {                                           //ATA章节号不存在则可以执行增加操作
				AjaxUtil.ajax({
					type : 'post',
					url : basePath+'/project/fixchapter/addFixChapter',                                                             //验证ATA章节号是否重复
					contentType:"application/json;charset=utf-8",
					data:JSON.stringify(obj),
					dataType : 'json',
					success : function(data) {
						finishWait($("#alertModalAddATA"));
						if (data == 1) {
							AlertUtil.showMessage("增加成功");
							$("#alertModalAddATA").modal("hide");
							searchRevision();
						}
					}
				});
			} else {
				finishWait($("#alertModalAddATA"));
				AlertUtil.showErrorMessage("该ATA章节号已经存在");
			}
		}
	});
}

//修改章节号
function edit(obj, dprtcode){
	validation2();
	$("#alertModalUpdateATA").modal("show");
	AlertUtil.hideModalFooterMessage();
	AjaxUtil.ajax({
		type : "post",
		url : basePath+"/project/fixchapter/upFixChapter",
		dataType : "json",
		data:{zjh:$.trim($(obj).attr("zjh")), dprtcode:$.trim(dprtcode)},
		success : function(data) {
			if(data!=null){
				$("#zjh2").val(data.zjh);
				$("#zwms2").val(data.zwms);
				$("#ywms2").val(data.ywms);
				$("#rJcsj2").val(data.rJcsj);
				$("#dprtcode2").val(data.dprtcode);
			}
		}
	});
	// window.location =basePath+"/project/fixchapter/upFixChapter/"+$.trim(zjh)+"";
}

function updateFixChapter(){
	startWait($("#alertModalUpdateATA"));	
	$('#form2').data('bootstrapValidator').validate();
	if(!$('#form2').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
		finishWait($("#alertModalUpdateATA"));
		return false;
	}
	var obj={};
	obj.zjh= $('#zjh2').val();                
	obj.zwms= $('#zwms2').val();                 
	obj.ywms= $('#ywms2').val();                                     
	obj.rJcsj=$('#rJcsj2').val(); 
	obj.dprtcode=$('#dprtcode2').val(); 
	// 提交数据
	AjaxUtil.ajax({
		type : 'post',
		url:basePath+"/project/fixchapter/updateFixchapter", 
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		modal:$("#alertModalUpdateATA"),
		data:JSON.stringify(obj),		
		success : function(data) {
			finishWait($("#alertModalUpdateATA"));
			if (data == 1) {
				AlertUtil.showMessage("修改成功！");
				$("#alertModalUpdateATA").modal("hide");
				searchRevision();
			}
		}
	});
}
/**
 * 显示导入模态框
 */
function showImportModal(){
	importModal.show();
}
//隐藏Modal时验证销毁重构
$("#alertModalAddATA").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	validation();
});

//隐藏Modal时验证销毁重构
$("#alertModalUpdateATA").on("hidden.bs.modal", function() {
	$("#form2").data('bootstrapValidator').destroy();
	$('#form2').data('bootstrapValidator', null);
	validation2();
});

function exportExcel(){
	var keyword = $("#keyword_search").val();
	var dprtcode = $("#dprtcode_search").val();
	window.open(basePath+"/project/fixchapter/ATA.xls?dprtcode="+ dprtcode + "&keyword="+ encodeURIComponent(keyword));
}

//回车事件控制
document.onkeydown = function(event) {
	e = event ? event : (window.event ? window.event : null);
	if (e.keyCode == 13) {
		$('#fixchapterMainSearch').trigger('click');
	}
};