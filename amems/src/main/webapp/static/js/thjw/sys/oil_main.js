var alertModalAdd='alertModalAdd';
$(document).ready(function() {
			Navigation(menuCode);
			refreshPermission();
			var zzjgid=$('#zzjgid').val();
			$("#dprtcode").val(zzjgid);
			//获取当前组织机构的油品规格
			decodePageParam();
			validation();
			//初始化日志功能
			logModal.init({code:'B_J_005'});
			
});
//验证
function validation(){
	validatorForm = $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	            ypgg: {
	                validators: {
	                	notEmpty: {
	                        message: '油品规格不能为空'
	                    }
	                }
	            },
	            ypmd: {
	                validators: {
	                    notEmpty: {
	                        message: '油品密度不能为空'
	                    },
	                    regexp: {
		                    regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
		                    message: '只能输入10位整数和2位小数'
		                }
	                }
	            }
	        }
	    });
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
///字段排序
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
//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.dprtcode = $("#dprtcode").val();
	 return searchParam;
}

	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.pagination = pagination;
	obj.keyword = $.trim($("#keyword_search").val());	
	obj.dprtcode = searchParam.dprtcode;
	if(id != ""){
		obj.id = id;
		id = "";
	}
	startWait();
	
	AjaxUtil.ajax({
		   url:basePath+"/basedata/oil/queryOilList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   
			    finishWait();
			    if(data.rows.length>0){
					   
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
					   signByKeyword($("#keyword_search").val(),[2,3,4,5]);
					   
				   } else {
					  $("#OilList").empty();
					  $("#pagination").empty();
					  $("#OilList").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
				   }
			    /*new Sticky({tableId:'quality_check_list_table'});*/
	      }
	    }); 
}

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   
		
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		   
		   htmlContent =htmlContent+"<td class='text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='basedata:oil:edit:02' " +
		   		" ypgg='"+StringUtil.escapeStr(formatUndefine(row.ypgg))+"' id='"+formatUndefine(row.id)+"' ms='"+formatUndefine(row.ms)+"' ypmd='"+formatUndefine(row.ypmd)+"' "+
		   				"onClick=\"edit(this)\" title='修改 Edit '></i>&nbsp;&nbsp;";
		   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='basedata:oil:invalid:03' onClick=\"invalid('"
				+ row.id + "')\" title='作废 Invalid '></i>&nbsp;&nbsp;</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ypgg))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.ypmd))+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.ms))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ms))+"</td>";  
		   if(row.whr_user==null){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.whr_user.displayName)+"'>"+StringUtil.escapeStr(row.whr_user.displayName)+"</td>";  
		   }
		   if(row.bm_dprt==null){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.bm_dprt.dprtname)+"</td>";  
		   }
		   
		   htmlContent = htmlContent + "<td class='text-center' >"+formatUndefine(row.whsj)+"</td>"; 
		   
		   if(row.jg_dprt==null){
			   htmlContent = htmlContent + "<td class='text-left'></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
		   }
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#OilList").empty();
	   $("#OilList").html(htmlContent);
	   refreshPermission();
}
function searchRevision(){
	goPage(1,"auto","desc");
}
function add(){
	
	//清空
	$('#oilId').val("");
	$("#titleName").text("新增");
	$("#titleEname").text("Add");
	//清空
	$('#ypgg').val("");
	 $('#oldypgg').val("");
	$('#ypmd').val("");
	$('#ms').val("");
	validation();
	$("#alertModalAdd").modal("show");
}
function edit(obj){
	validation();
	$("#titleName").text("修改");
	$("#titleEname").text("Edit");
	//清空
	$('#oilId').val("");
	$('#ypgg').val("");
	$('#ypmd').val("");
	$('#ms').val("");
	 var id = $(obj).attr("id");
	 var ypgg = $(obj).attr("ypgg");
	 var ypmd = $(obj).attr("ypmd");
	 var ms = $(obj).attr("ms");
	 $('#oilId').val(id);
	 $('#ypgg').val(ypgg);
	 $('#oldypgg').val(ypgg);
	 $('#ypmd').val(ypmd);
	 $('#ms').val(ms);
	 
	 $("#alertModalAdd").modal("show");
}
//保存，修改
function saveUpdate(){
	var ypgg=$('#ypgg').val();
	var oldypgg=$('#oldypgg').val();
	var ypmd=$('#ypmd').val();
	var ms=$('#ms').val();
	var id=$('#oilId').val();
	var obj={};
	obj.id=id;
	obj.ypgg=ypgg;
	obj.ypmd=ypmd;
	obj.ms=ms;
	
	var b=validationYpgg(ypgg,oldypgg);
	//验证
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	  if(!b){
			 AlertUtil.showErrorMessage("油品规格已存在,请修改");
				return false;
		}
	  var url='';
	if(id!=null && id!=""){
		url = basePath+"/basedata/oil/updateOil";//修改
	}else{
		url = basePath+"/basedata/oil/saveOil";//新增
		
	}
	submitAjax(obj,url);
}
//作废
function invalid(id){
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		var obj={};
		obj.id=id;
		url = basePath+"/basedata/oil/invalidOil";//作废
		submitAjax(obj,url);
	 }});
	
}
//提交ajax
function submitAjax(obj,url){
	startWait();
	AjaxUtil.ajax({
 		url:url,
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		success:function(data){
 			finishWait();
 			$("#alertModalAdd").modal("hide");
 			AlertUtil.showMessage('操作成功!');
 			searchRevision();
 		}
 	});
}
//验证油品规格是否重复
function validationYpgg(ypgg,oldypgg){
	var falot=true;
	var dprtcode=$("#dprtcode").val();
	 AjaxUtil.ajax({
	   url: basePath+"/basedata/oil/validationYpgg",
	   type:"post",
		async: false,
		data:{
			'ypgg' : ypgg,
			'oldypgg':oldypgg,
			'dprtcode':dprtcode
		},
		dataType:"json",
	   success:function(data){
		   if(data.total>0){
			   falot = false;
			   return falot;
		   }
		   return falot;
    }
  }); 
	 return falot;
}

//隐藏Modal时验证销毁重构
$("#"+alertModalAdd).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     validation();
});