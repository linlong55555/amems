var type=["","IP","MAC"];
$(document).ready(function(){
	Navigation(menuCode);
	goPage(1,"auto","desc");//开始的加载默认的内容 
	validation();
	refreshPermission();
});

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
 
	//验证
	function validation(){
		validatorForm =  $('#form1').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	value1: {
	                validators: {
	                	notEmpty: {
	                        message: '值不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
	                        message: '不包含中文且长度不超过50个字符'
	                    }
	                }
	            }
	    	}
	    });
	}

 	//带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.keyword =  $.trim($("#keyword_search").val());
		startWait();
		 AjaxUtil.ajax({
			   url:basePath+"/sys/loginlimit/loginlimitList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.total>0){
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
				       signByKeyword($("#keyword_search").val(),[3,4,5]);
					   } else {
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'sqzz'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			  if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor=\"#f9f9f9\">";
			  } else {
			   htmlContent += "<tr bgcolor=\"#fefefe\">";
			  }
			   htmlContent += "<td class='fixed-column' class='text-center'>";
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:loginlimit:main:02' " 
					   	   +  "type='"+StringUtil.escapeStr(row.type)+"' value1='"+StringUtil.escapeStr(row.value1)+"' " 
					   	   +  "value2='"+StringUtil.escapeStr(row.value2)+"' id='"+formatUndefine(row.id)+"' " 
					   	   +  "onClick=\"editSub(event)\" title='修改 Edit'></i>" ;
	   		   htmlContent += "&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='sys:loginlimit:main:03' onClick=\"cancel('"+ row.id + "')\" title='删除  Delete'></i>";
			   htmlContent += "<td class='' class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(type[row.type])+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.value1)+"' >"+StringUtil.escapeStr(row.value1)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.value2)+"'>"+StringUtil.escapeStr(row.value2)+"</td>";  
			   htmlContent += "</tr>";  
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	//字段排序
	function orderBy(obj, _obj) {
		$obj = $("#sqzz th[name='column_"+obj+"']");
		var orderStyle = $obj.attr("class");
		$("#sqzz .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#sqzz .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf ( "sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj, orderType);
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
		
		$("#divSearch").find("select").each(function(){
				$(this).val("");
		});
	 
		 var zzjgid=$('#zzjgid').val();
		 $("#keyword_search").val("");
		 $("#dprtcode").val(zzjgid);
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
		 
	 //作废
	 function cancel(id) {
			AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
	 		startWait();
	 		AjaxUtil.ajax({
	 			url:basePath + "/sys/loginlimit/cancel",
	 			type:"post",
	 			async: false,
	 			data:{
	 				id : id
	 			},
	 			dataType:"json",
	 			success:function(data){
	 				finishWait();
	 				AlertUtil.showMessage('作废成功!');
	 				goPage(1,"auto","desc");//开始的加载默认的内容 
	 				refreshPermission();
	 			}
	 		});
		}});
	 }
		 
		//新增初始化
	 function addinto(){
		 validation();
	    $("#alertModaladdupdate").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#alertModaladdupdate").find("textarea").each(function(){
			 $(this).val("");
		 });
		 
		 $("#id").val("");
		 $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);
		 
		$("#accredit").html("新增");
		$("#accreditrec").html("Add");
	 	$("#alertModaladdupdate").modal("show");
	 	
	 	 var SelectArr1 = $("#fjzchs select");
	 	 if(SelectArr1[0].options[0] != undefined){
	 		 
		 SelectArr1[0].options[0].selected = true;
	 	 }
	 }
		 
		//修改
	 function editSub(e){
		 validation();
		 var id = $(e.target).attr("id");
		 var type = $(e.target).attr("type");
		 var value1 = $(e.target).attr("value1");
		 var value2 = $(e.target).attr("value2");
		 
		 $("#id").val(id);
		 $("#type").val(type);
		 $("#value1").val(value1);
		 $("#value2").val(value2);
		 
		 $("#accredit").html("修改");
		 $("#accreditrec").html("Update");
		 $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);
	 	 $("#alertModaladdupdate").modal("show");
	 }	
		 
	 //保存修改
	 function saveUpdate(){
		 $('#form1').data('bootstrapValidator').validate();
		  if(!$('#form1').data('bootstrapValidator').isValid()){
			return false;
		  }			 
		var obj ={};
		var id = $("#id").val();
		var url ="";
		if(id==""){
			 url = basePath+"/sys/loginlimit/save";//新增
		}else{
			 url = basePath+"/sys/loginlimit/edit";//修改
			 obj.id = id;
		}
		 	var type = $("#type").val();
		 	var value1 = $("#value1").val();
		 	var value2 = $("#value2").val();
			obj.type = type;
			obj.value1 = value1;
			obj.value2 = value2;
					
		 	startWait($("#alertModaladdupdate"));
		 	AjaxUtil.ajax({
		 		url:url,
		 		contentType:"application/json;charset=utf-8",
		 		type:"post",
		 		async: false,
		 		data:JSON.stringify(obj),
		 		dataType:"json",
		 		modal:$("#alertModaladdupdate"),
		 		success:function(data){
		 			finishWait($("#alertModaladdupdate"));
		 			AlertUtil.showMessage('保存成功!');
		 			$("#alertModaladdupdate").modal("hide");
		 			goPage(1,"auto","desc");
		 			refreshPermission();
		 		}
		 	});
	 }
	
		//隐藏Modal时验证销毁重构
	 $("#alertModaladdupdate").on("hidden.bs.modal", function() {
	 	 $("#form1").data('bootstrapValidator').destroy();
	      $('#form1').data('bootstrapValidator', null);
	      validation();
	 });
	 

	//回车事件控制
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			$('#loginlimitMainSearch').trigger('click');
		}
	};