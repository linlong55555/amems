
$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	decodePageParam();
	refreshPermission();
	//初始化日志功能
	logModal.init({code:'D_009'});
});

//验证
function validation(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	ckh: {
                validators: {
                	notEmpty: {
                        message: '仓库编号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            ckmc: {
                validators: {
                    notEmpty: {
                        message: '仓库名称不能为空'
                    },
                    /*regexp: {
                        regexp: /^([\u4E00-\u9FA5]+|[a-zA-Z]+)$/,
                        message: '只能输入中文和英文'
                    },*/
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            },
            cklb: {
                validators: {
                    notEmpty: {
                        message: '仓库类别不能为空'
                    }
                }
            }/*,
            ckdz: {
                validators: {
                	stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            }   */     
        }
    });
}


var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.cklb = $.trim($("#cklb_search").val());
	params.dprtcode = $.trim($("#dprtcode_search").val());
	params.whrq = $.trim($("#whrq_search").val());
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
		$("#keyword_search").val(params.keyword);
		$("#cklb_search").val(params.cklb);
		$("#dprtcode_search").val(params.dprtcode);
		$("#whrq_search").val(params.whrq);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}



	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			 url:basePath+"/material/store/queryAllPageList",
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
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,6,7,8],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'store_main_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 searchParam.zt = 1;
		 searchParam.keyword = keyword;
		 var cklb = $.trim($("#cklb_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 var whrq = $.trim($("#whrq_search").val()); 
		 if('' != cklb){
			 searchParam.cklb = cklb;
		 }
		 if(null != whrq && "" != whrq){
			 var whrqBegin = whrq.substring(0,10)+" 00:00:00";
			 var whrqEnd = whrq.substring(13,23)+" 23:59:59";
			 paramsMap.whrqBegin = whrqBegin;
			 paramsMap.whrqEnd = whrqEnd;
		 }
		 //区分仓库主数据查询和仓库数据管理
		 if($("#isStoreData").val()=='true'){//仓库数据管理
			 paramsMap.isStoreData=true
			 
		 }else if($("#isStoreData").val()=='false'){//仓库主数据
			 paramsMap.isStoreData=false		 
		 }
		 searchParam.paramsMap = paramsMap;
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
			   
			   htmlContent += "<td class='fixed-column text-center'>";
			   
			   
			   if($("#isStoreData").val()=='true'){
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='material:store2:main:02' onClick=\"edit('"+ row.id + "','" + row.dprtcode + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
				   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='material:store2:main:03' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>";  
			   }else{
				   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='material:store:main:02' onClick=\"edit('"+ row.id + "','" + row.dprtcode + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
				   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='material:store:main:03' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>";  
			   }
			  
			   
			   
			   
			   htmlContent += "</td>";  
			   
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ckh)+"' class='text-left'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id + "','" + row.dprtcode+"')\">"+StringUtil.escapeStr(row.ckh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ckmc)+"' class='text-left'>"+StringUtil.escapeStr(row.ckmc)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ckdz)+"' class='text-left'>"+StringUtil.escapeStr(row.ckdz)+"</td>"; 
			   
			   if($("#isStoreData").val()=='true'){
				   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('store2TypeEnum',row.cklb) +"</td>";  
			   }else if($("#isStoreData").val()=='false'){
				   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('storeTypeEnum',row.cklb) +"</td>";  
			   }
			   
			   htmlContent += "<td title='"+(StringUtil.escapeStr(row.kgyusername)+" "+StringUtil.escapeStr(row.kgyrealname))+"' class='text-left'>"+StringUtil.escapeStr(row.kgyusername)+" "+StringUtil.escapeStr(row.kgyrealname)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
			   if(null != row.zdr){
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
			   }else{
				   htmlContent += "<td class='text-left'></td>";
			   }
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.cjsj)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
//仓库新增
/*function add(){
	window.location = basePath+"/material/store/add?pageParam="+encodePageParam();
}
	*/ 
//仓库修改
/*function edit(id,dprtcode){
	window.location = basePath+"/material/store/edit?id=" + id + "&dprtcode=" + dprtcode + "&pageParam="+encodePageParam();
}*/
 
//查看详情
 function viewDetail(id,dprtcode){
	 var isStoreData=$("#isStoreData").val();
 	window.open(basePath+"/material/store/view?id=" + id + "&dprtcode=" + dprtcode + "&pageParam="+encodePageParam()+"&isStoreData="+isStoreData);
 }

//作废
function del(id) {
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
	
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/material/store/cancel",
			type:"post",
			async: false,
			data:{
				'ids' : id
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('作废成功!');
				refreshPage();
			}
		});
		
	}});
	
}

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
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
	 TableUtil.resetTableSorting("store_main_table"); //重置排序图标
		var pgd = $("th[name='pgd']");
		if(pgd.hasClass("upward")) {
			pgd.removeClass("upward").addClass("downward");
		}
		var lywj = $("th[name='lywj']");
		if(lywj.hasClass("upward")) {
			lywj.removeClass("upward").addClass("downward");
		}
	goPage(1,"cjsj","desc");
}

//改变类型时查询仓库信息
function changeType(){
	goPage(1,"auto","desc");
}

//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}
		
//搜索条件重置
function searchreset(){
	var dprtId = $.trim($("#dprtId").val());
	$("#cklb_search").val("");
	$("#keyword_search").val("");
	
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});

	$("#divSearch").find("select").each(function() {
		$(this).attr("value", "");
	});
	
	$("#dprtcode_search").val(dprtId);
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

//新增
function add(){
	//修改时弹框上传附件按钮显示
	$("#button1").hide();
	dprtcode = userJgdm;
	storefoModal.show({
 		modalHeadChina : '新增',
		modalHeadEnglish : 'Add',
		type:1,//1:新增,2:修改,3:审核,4:批准
		blOption:true,
		dprtcode:dprtcode,//机构代码
		callback : function(data) {// 回调函数
			if (data != null) {
				var message = '保存成功!';
				var url = basePath+"/material/store/add";
				performAction(url, data, message);
				
			}
		}
	});
}
//修改
function edit(id){
	 //修改时弹框上传附件按钮显示
	$("#button1").show();
	selectById(id,function(obj){
		storefoModal.show({
				modalHeadChina : '编辑',
				modalHeadEnglish : 'Edit',
				type:2,//编辑
				viewObj:obj,
				dprtcode:obj.dprtcode,//机构代码
				callback : function(data) {//回调函数
				if (data != null) {
					var message = '修改成功!';
					var url = basePath+"/material/store/edit";
					performAction(url, data, message);
				}
			}
		});
	});
}
function selectById(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/material/store/selectById",
		type:"post",
		data:{
			"id":id
		},
		dataType:"json",
		success:function(data){
			if(data != null){
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}
		}
	});
}
function performAction(url, param, message){
	var this_ = this;
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage(message);
			$("#storefoModal").modal("hide");
			searchRevision();
		}
	});
}

function initStorage() {
	AjaxUtil.ajax({
		async : false,
		url : basePath + "/material/store/queryStorageListByStoreId",
		type : "post",
		data : {
			storeId : $("#storeId").val()
		},
		dataType : "json",
		success : function(data) {
			initTableCol(data);
		}
	});
}

function showImportModal(){
	var storeId=$("#storeId").val();
	importModal.init({
		importUrl : "/material/store/excelImport?id=" +storeId,
		downloadUrl : "/common/templetDownfile/4",
		callback : function(data) {
			selectById(storeId,function(obj){
				storefoModal.show({
						modalHeadChina : '编辑',
						modalHeadEnglish : 'Edit',
						type:2,//编辑
						viewObj:obj
				});
			});	     
		}
	});
	
	importModal.show();
}

function exportExcel(){
	var dprtcode = $("#dprtcode_search").val();
	var whrq = $.trim($("#whrq_search").val());
	var keyword = $.trim($("#keyword_search").val());
	var cklb = $.trim($("#cklb_search").val());
	var isStoreData=$.trim($("#isStoreData").val());
	window.open(basePath+"/material/store/WarehouseMainData.xls?dprtcode="+ dprtcode + "&keyword="+ encodeURIComponent(keyword) +"&cklb=" +cklb
	+"&whrq="+whrq+"&isStoreData="+isStoreData);

}


//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
		if(formatUndefine(winId) != ""){
			$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
		}else{
			searchRevision(); //调用主列表页查询
		}
	}
};