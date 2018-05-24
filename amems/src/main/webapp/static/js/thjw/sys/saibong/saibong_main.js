var type=["","IP","MAC"];
$(document).ready(function(){
	Navigation(menuCode);
	var zzjgid=$('#zzjgid').val();
	$("#dprtcode").val(zzjgid);
	 goPage(1,"auto","desc");//开始的加载默认的内容 
	 validation();
	  refreshPermission();
});

//验证
function validation(){
	validatorForm =  $('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	ggdwz: {
                validators: {
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
                        message: '不包含中文且长度不超过20个字符'
                    }
                }
            },llshcd: {
                validators: {
                	notEmpty: {
                        message: '流水号长度不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]*[1-9][0-9]*$/,
                        message: '只可输入正整数'
                    }
                }
            },fgf: {
                validators: {
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,10}$/,
                        message: '不包含中文且长度不超过10个字符'
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
		obj.dprtcode = $.trim($("#dprtcode").val());
		obj.paramsMap = {sort:sortType,order:sequence,keyword:$.trim($("#keyword_search").val())};
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/sys/saibong/saibongList",
			   type: "post",
			   dataType:"json",
			   async : false,
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify(obj),
			   success:function(data){
			    finishWait();
			    if(data.rows!=undefined&&data.rows.length>0){
			    	
					   appendContentHtml(data.rows);
						// 标记关键字
				       signByKeyword($("#keyword_search").val(),[3,4]);
					   
			   } else {
				  $("#list").empty();
				  $("#list").append("<tr style='vertical-align: middle;text-align:center;'><td colspan=\"12\">暂无数据 No data.</td></tr>");
			   }
			    new Sticky({tableId:'list'});
		      }
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 return searchParam;
	 }
	 
	 //切换组织机构
	 function load(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
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
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:saibong:main:01' " +
			   				  "cfkey='"+formatUndefine(row.cfkey)+"' ggdwz='"+StringUtil.escapeStr(row.ggdwz)+"' " +
						   	  "gcfzcsx='"+formatUndefine(row.gcfzcsx)+"' trqgs='"+formatUndefine(row.trqgs)+"' " +
						   	  "tcfzcsx='"+formatUndefine(row.tcfzcsx)+"' llshcd='"+formatUndefine(row.llshcd)+"' " +
						   	  "ltbgz='"+formatUndefine(row.ltbgz)+"' lcfzcsx='"+formatUndefine(row.lcfzcsx)+"' " +
						   	  "fgf='"+StringUtil.escapeStr(row.fgf)+"' cfsm='"+StringUtil.escapeStr(row.cfsm)+"' " +
						   	  "dtwzwz='"+StringUtil.escapeStr(row.dtwzwz) +"'"+
						   	  " jgdm='"+formatUndefine(row.dprtcode)+"' " +
						   	  "onClick=\"editSub(event)\" title='修改 Edit'></i>" ;
			   htmlContent += "<td class='' class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td  class='text-left' >"+formatUndefine(row.cfkey)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.cfsm)+"'>"+StringUtil.escapeStr(row.cfsm)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.ggdwz)+"' >"+StringUtil.escapeStr(row.ggdwz)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+formatUndefine(row.gcfzcsx)+"'>"+formatUndefine(row.gcfzcsx)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+formatUndefine(row.trqgs)+"'>"+formatUndefine(row.trqgs)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+formatUndefine(row.tcfzcsx)+"'>"+formatUndefine(row.tcfzcsx)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+formatUndefine(row.llshcd)+"'>"+formatUndefine(row.llshcd)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+formatUndefine(row.ltbgz)+"'>"+formatUndefine(row.ltbgz)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+formatUndefine(row.lcfzcsx)+"'>"+formatUndefine(row.lcfzcsx)+"</td>";  
			   htmlContent += "<td  class='text-left' title='"+StringUtil.escapeStr(row.fgf)+"'>"+StringUtil.escapeStr(row.fgf)+"</td>";  
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
		
		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",function() {
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
		 
		 var cfkey = $(e.target).attr("cfkey");
		 var ggdwz = $(e.target).attr("ggdwz");
		 var gcfzcsx = $(e.target).attr("gcfzcsx");
		 var trqgs = $(e.target).attr("trqgs");
		 var tcfzcsx = $(e.target).attr("tcfzcsx");
		 var llshcd = $(e.target).attr("llshcd");
		 var ltbgz = $(e.target).attr("ltbgz");
		 var lcfzcsx = $(e.target).attr("lcfzcsx");
		 var fgf = $(e.target).attr("fgf");
		 var cfsm = $(e.target).attr("cfsm");
		 var jgdm = $(e.target).attr("jgdm");
		 
		 var dtwzwz = $(e.target).attr("dtwzwz");
		 
		 $("#lshyl").text("");
		 $("#cfkey").val(cfkey);
		 $("#ggdwz").val(ggdwz);
		 $("#gcfzcsx").text(gcfzcsx);
		 $("#trqgs").val(trqgs);
		 $("#tcfzcsx").text(tcfzcsx);
		 $("#llshcd").val(llshcd);
		 $("#ltbgz").val(ltbgz);
		 $("#lcfzcsx").text(lcfzcsx);
		 $("#fgf").val(fgf);
		 $("#cfsm").val(cfsm);
		 $("#jgdm").val(jgdm);
		 
		 $("#dtwzwz").val(dtwzwz);
		 
	/*	 var str=[2,3,1];
		 
		 console.info(str.sort(str));*/
		 
		 
		 $("#accredit").html("修改");
		 $("#accreditrec").html("Edit");
		 $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);
		 
		 $("#cfsm").attr("disabled",true);
		 $("#cfkey").attr("disabled",true);
	 	 $("#alertModaladdupdate").modal("show");
	 }	
		 
	 //保存修改
	 function saveUpdate(){
		 $('#form1').data('bootstrapValidator').validate();
		  if(!$('#form1').data('bootstrapValidator').isValid()){
			return false;
		  }			 
		var obj ={};
		var cfkey = $("#cfkey").val();
	 	var ggdwz = $("#ggdwz").val();
	 	var gcfzcsx = $("#gcfzcsx").text();
	 	var trqgs = $("#trqgs").val();
	 	var tcfzcsx = $("#tcfzcsx").text();
	 	var llshcd = $("#llshcd").val();
	 	var ltbgz = $("#ltbgz").val();
	 	var lcfzcsx = $("#lcfzcsx").text();
	 	var fgf = $("#fgf").val();
	 	var cfsm = $("#cfsm").val();
	 	var dprtcode = $("#dprtcode").val();
	 	
	 	var dtwzwz = $("#dtwzwz").val();
		
	 	obj.cfkey = cfkey;
		obj.ggdwz = ggdwz;
		obj.gcfzcsx = gcfzcsx;
		obj.trqgs = trqgs;
		obj.tcfzcsx = tcfzcsx;
		obj.llshcd = llshcd;
		obj.ltbgz = ltbgz;
		obj.lcfzcsx = lcfzcsx;
		obj.fgf = fgf;
		obj.cfsm = cfsm;
		obj.dprtcode = dprtcode;
		obj.dtwzwz = dtwzwz;
	 	startWait($("#alertModaladdupdate"));
				 	
	 	AjaxUtil.ajax({
	 		url:basePath+"/sys/saibong/edit",
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
	 
	 //预览
	 function preview(){
		 $('#form1').data('bootstrapValidator').validate();
		  if(!$('#form1').data('bootstrapValidator').isValid()){
			return false;
		  }			 
		var obj ={};
		var cfkey = $("#cfkey").val();
	 	var ggdwz = $("#ggdwz").val();
	 	var gcfzcsx = $("#gcfzcsx").text();
	 	var trqgs = $("#trqgs").val();
	 	var tcfzcsx = $("#tcfzcsx").text();
	 	var llshcd = $("#llshcd").val();
	 	var ltbgz = $("#ltbgz").val();
	 	var lcfzcsx = $("#lcfzcsx").text();
	 	var fgf = $("#fgf").val();
	 	var cfsm = $("#cfsm").val();
	 	var dtwzwz = $("#dtwzwz").val();
	 	var dprtcode = $("#dprtcode").val();
		
	 	obj.cfkey = cfkey;
		obj.ggdwz = ggdwz;
		obj.gcfzcsx = gcfzcsx;
		obj.trqgs = trqgs;
		obj.tcfzcsx = tcfzcsx;
		obj.llshcd = llshcd;
		obj.ltbgz = ltbgz;
		obj.lcfzcsx = lcfzcsx;
		obj.fgf = fgf;
		obj.cfsm = cfsm;
		obj.dprtcode = dprtcode;
		if(dtwzwz){
			obj.dtwzwz = dtwzwz;	
		}
					
	 	startWait($("#alertModaladdupdate"));
				 	
	 	AjaxUtil.ajax({
	 		url:basePath+"/sys/saibong/previewSaibong",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#alertModaladdupdate"),
	 		success:function(data){
	 			finishWait($("#alertModaladdupdate"));
	 			$("#lshyl").text(StringUtil.escapeStr(data));
	 			AlertUtil.showMessage('生成成功!');
	 	
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
	 		$('#cfgzSearch').trigger('click');
	 	}
	 };