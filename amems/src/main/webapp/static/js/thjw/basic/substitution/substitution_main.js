var alertModalAdd='alertModalAdd';
var pagination={};
$(document).ready(function(){
		 decodePageParam();
		//初始化日志功能
		logModal.init({code:'D_017'});
		validation();
		
		$('#bjh').on('change', function(e) {
			$('#form').data('bootstrapValidator')  
			.updateStatus('bjh', 'NOT_VALIDATED',null)  
			.validateField('bjh');   
		});
		$('#tdjh').on('change', function(e) {
			$('#form').data('bootstrapValidator')  
			.updateStatus('tdjh', 'NOT_VALIDATED',null)  
			.validateField('tdjh');   
		});
	});

function validation(){
	$('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            bjh: {
                validators: {
                	notEmpty: {
                        message: '件号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            tdjh: {
                validators: {
                    notEmpty: {
                        message: '替换件号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
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
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword=searchParam.keyword;
		obj.dprtcode=searchParam.dprtcode;
		obj.knxbs=searchParam.knxbs_search;
		if(id != ""){
			obj.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/basic/substitution/main",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   var page_ = new Pagination({
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
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,6]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"13\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());//关键字查询
		 searchParam.knxbs_search = $.trim($("#knxbs_search").val());
		 searchParam.dprtcode = $.trim($("#zzjg").val());
		 
		 
		 return searchParam;
	 }
	 function encodePageParam(){
		 var pageParam={};
		 var params={};
		 params.keyword=$.trim($("#keyword_search").val());
		 params.knxbs_search=$.trim($("#knxbs_search").val());
		 params.dprtcode=$.trim($("#zzjg").val());
		 pageParam.params=params;
		 pageParam.pagination=pagination;
		 return Base64.encode(JSON.stringify(pageParam));
	 }

function appendContentHtml(list){
	   console.log(JSON.stringify(list));
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		   htmlContent = htmlContent + "<td class='text-center fixed-column'>";
		   htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='basic:substitution:main:02' id='"+formatUndefine(row.id)+"' " +
		       "bjh='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"' ms='"+StringUtil.escapeStr(formatUndefine(row.ms))+"' tdjh='"+StringUtil.escapeStr(formatUndefine(row.tdjh))+"' " +
		       "tdjms='"+StringUtil.escapeStr(formatUndefine(row.tdjms))+"' jxbs='"+StringUtil.escapeStr(formatUndefine(row.jxbs))+"' gkbs='"+StringUtil.escapeStr(formatUndefine(row.gkbs))+"' " +
		       "knxbs='"+StringUtil.escapeStr(formatUndefine(row.knxbs))+"' dprtcode='"+StringUtil.escapeStr(formatUndefine(row.dprtcode))+"' onClick=\"edit(this)\" title='修改 Edit'></i>&nbsp;&nbsp;";
		   
		   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='basic:substitution:main:03' onClick=\"invalid('" + row.id + "')\" title='作废 Invalid '></i>&nbsp;&nbsp;";
		   htmlContent = htmlContent +"<i class='spacing icon-eye-open color-blue cursor-pointer' onClick=\"show('" + row.id + "')\" title='查看 show '></i>&nbsp;&nbsp;";
		   htmlContent = htmlContent + "</td>";
		   
			var sujxbs = '';
			var sugkbs = '';
			if(row.applicabilityList != null){
				$.each(row.applicabilityList,function(index,d){
					if(d.syxlx==1){
						sujxbs += StringUtil.escapeStr(d.sydx) + ",";
					}else{
						sugkbs += StringUtil.escapeStr(d.sydx) + ",";
					}
				});
				if(sujxbs != ''){
					sujxbs = sujxbs.substring(0,sujxbs.length - 1);
				}
				if(sugkbs != ''){
					sugkbs = sugkbs.substring(0,sugkbs.length - 1);
				}
			}
		   
		   htmlContent = htmlContent + "<td class='text-left fixed-column'>"+StringUtil.escapeStr(formatUndefine(row.bjh))+"</td>";   
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ms))+"'>"+StringUtil.escapeStr(formatUndefine(row.ms))+"</td>";  
		   
		   htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if(row.attachments!= null && row.attachments.length > 0){
				htmlContent += '<i qtid="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
		   htmlContent += "</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.tdjh))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.tdjms))+"'>"+StringUtil.escapeStr(formatUndefine(row.tdjms))+"</td>";
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jxbs=='00000'?'通用':sujxbs ))+"'>"+StringUtil.escapeStr(formatUndefine(row.jxbs=='00000'?'通用':sujxbs ))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.gkbs=='00000'?'通用':sugkbs ))+"'>"+StringUtil.escapeStr(formatUndefine(row.gkbs=='00000'?'通用':sugkbs ))+"</td>";  
		   if(row.knxbs==1){
			   htmlContent = htmlContent + "<td class='text-left' >否</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left' >是</td>";  
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.whr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.whr_user.displayName))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.whsj)+"</td>";
		  /* htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.bm_dprt.dprtname)+"</td>";*/
		   if (row.bm_dprt) {
			 htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.bm_dprt.dprtname)+"</td>";   
		   }else {
			 htmlContent = htmlContent + "<td class='text-left'></td>";
		   }
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"</td>";  
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#list").empty();
	   $("#list").html(htmlContent);
	   initWebuiPopover();
	   refreshPermission();
}

function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#substitution__main_table_top_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
	   	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	   	    ];
	 return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
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
	 var zzjgid=$('#zzjgid').val();
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#zzjg").val(zzjgid);
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
 
 //查看跳转
function viewMainAnnunciate(id,dprtcode){
	window.open(basePath+"/project/annunciate/intoViewMainAnnunciate?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam());
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

function selectPgdBypgdh(id,dprtcode){
	 window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	
}




function add(){
	var substitutionId=$("#substitutionId").val();
	//清空
	$('#substitutionId').val("");
	$('#bjh').val("");
	$('#ms').val("");
	$('#tdjh').val("");
	$('#tdjms').val("");
	$('#jxbs').val("");
	$('#gkbs').val("");
	/*$('#jxbs').val("00000");
	$('#gkbs').val("00000");*/
	$('#knxbs').val("2");
	$(":radio[name='knxbs'][value='2']").prop("checked", "checked");
	
	$("#titleName").html("新增");
	$("#titleEname").html("Add");
	
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents("dxtd_modal_attachments_list_edit");
	attachmentsObj.show({
		djid:"",
		fileHead : true,
		colOptionhead : true,
		fileType:"substitution",
		chinaHead:"等效替代评估单",
	    englishHead : 'Attachments'
	});//显示附件
	
	
	
	$("#alertModalAdd").modal("show");
	findJxAndGk($("#zzjgid").val());
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
		$("#zt").val(params.zt);
		$("#zzjg").val(params.zzjg);
		$("#tgqx").val(params.tgqx);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}
//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}
//替换
function replace(){
	var a=$("#bjh").val();
	$("#bjh").val($("#tdjh").val());
	$("#tdjh").val(a);
	
	$("#bjh").change();
	$("#tdjh").change();
}

function findJxAndGk(dprtcode){
	AjaxUtil.ajax({
		   url:basePath+"/basic/substitution/queryFjzchAndGk",
			 type:"post",
			 async: false,
			 data:{
				 'dprtcode' : dprtcode
			 },
			 dataType:"json",
			 success:function(data){
		    finishWait();
		    appendJxAndGk(data);
	      }
	    });
}

/*function appendJxAndGk(list){
	//生成多选下拉框(机型标识)
	$('#jxbsDiv').empty();
	$('#jxbsDiv').html("<select multiple='multiple' id='jxbs' ></select>");
	//生成多选下拉框(工卡标识)
	$('#gkbsDiv').empty();
	$('#gkbsDiv').html("<select multiple='multiple' id='gkbs' ></select>");
	var appendhtlmJx = "<option value='00000'>全部 All</option>";
	var appendhtlmGk = "<option value='00000'>全部 All</option>";
	 $.each(list.gkList,function(index,row){
		 appendhtlmGk = appendhtlmGk + "<option value='"+row.gdbh+"'>"+row.gdbh+"</option>";
	 });
	 $.each(list.jxList,function(index,row){
		 appendhtlmJx = appendhtlmJx + "<option value='"+row+"'>"+row+"</option>";
	 });
	 $("#jxbs").empty();
	 $("#gkbs").empty();
	 $("#jxbs").append(appendhtlmJx);
	 $("#gkbs").append(appendhtlmGk);
	//生成多选下拉框动
		$('#jxbs').multiselect({
			buttonClass: 'btn btn-default',
	        buttonWidth: 'auto',
	        numberDisplayed:2,
		    includeSelectAllOption: false,
		    onChange:function(element,checked){
		    	isCheckedAndDisabled(element,checked,'#jxbs');
		    }
		});
		$('#gkbs').multiselect({
			buttonClass: 'btn btn-default',
			buttonWidth: 'auto',
			numberDisplayed:2,
			includeSelectAllOption: false,
			onChange:function(element,checked){
				isCheckedAndDisabled(element,checked,'#gkbs');
			}
		});
		
}*/

function appendJxAndGk(list){
	//生成多选下拉框(机型标识)
	$('#jxbsDiv').empty();
	$('#jxbsDiv').html("<select multiple='multiple' id='jxbs' ></select>");
	//生成多选下拉框(工卡标识)
	$('#gkbsDiv').empty();
	$('#gkbsDiv').html("<select multiple='multiple' id='gkbs' ></select>");
	var appendhtlmJx = "<option value='00000'>通用 Currency</option>";
	var appendhtlmGk = "<option value='00000'>通用 Currency</option>";
	 $.each(list.gkList,function(index,row){
		 appendhtlmGk = appendhtlmGk + "<option value='"+row.gkh+"'>"+row.gkh+"</option>";
	 });
	 $.each(list.jxList,function(index,row){
		 appendhtlmJx = appendhtlmJx + "<option value='"+row+"'>"+row+"</option>";
	 });
	 $("#jxbs").empty();
	 $("#gkbs").empty();
	 $("#jxbs").append(appendhtlmJx);
	 $("#gkbs").append(appendhtlmGk);
	//生成多选下拉框动
		$('#jxbs').multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',
		    numberDisplayed:100,
		    buttonContainer: '<div class="btn-group" style="width:100%;" />',
		    
		    includeSelectAllOption: true,
		    	
			/*buttonClass: 'btn btn-default',
	        buttonWidth: '100%',
	        numberDisplayed:4,
		    includeSelectAllOption: true,*/
		    onChange:function(element,checked){
		    	
		    }
		});
		$('#gkbs').multiselect({
			buttonClass: 'btn btn-default',
		    buttonWidth: '100%',
		    numberDisplayed:100,
		    buttonContainer: '<div class="btn-group" style="width:100%;" />',
		    includeSelectAllOption: true,
		   /* 	
			buttonClass: 'btn btn-default',
			buttonWidth: '100%',
			numberDisplayed:4,
			includeSelectAllOption: true,*/
			onChange:function(element,checked){
				
			}
		});
}

//判断多选下拉是否全不选和不可修改
/*function isCheckedAndDisabled(element,checked,selectId){
	if(checked){
		if(element.val()=="00000"){
			 var nonSelectedOptions = $(selectId+' option').filter(function() {
				 if($(this).val()=="00000"){
					 return false;
				 }else{
					 return true;
				 }
                    
    		});
            nonSelectedOptions.each(function() {
            	$(selectId).multiselect('deselect',$(this).val());
               //var input = $('input[value="' + $(this).val() + '"]');
               // input.prop('disabled', true);
                //input.parent('li').addClass('disabled');
                
            });
		}else{
			$(selectId).multiselect('deselect',"00000");
		}
	}
}*/
//加载回显机型标识或工卡标识
function bsLoing(id){
	AjaxUtil.ajax({
		   url:basePath+"/basic/substitutionApplicability/queryAllByMainid",
			 type:"post",
			 async: true,
			 data:{
				 'mainid' : id
			 },
			 dataType:"json",
			 success:function(data){
		    finishWait();
		    appendbs(data.bsList);
	      }
	    });
}
function appendbs(list){
	 $.each(list,function(index,row){
		 if(row.syxlx==1){
			 $('#jxbs').multiselect('select', row.sydx);
		 }else{
			 $("#gkbs").multiselect('select',row.sydx);
		 }
	 });
}
function edit(obj){
	
	$("#titleName").html("修改");
	$("#titleEname").html("Edit");
	//清空
	$('#bjh').val("");
	$('#ms').val("");
	$('#tdjh').val("");
	$('#tdjms').val("");
	$('#jxbs').val("");
	$('#gkbs').val("");
	$('#substitutionId').val("");
	findJxAndGk($(obj).attr("dprtcode"));
	$("#substitutionDprtcode").val($(obj).attr("dprtcode"));
	 var bjh = $(obj).attr("bjh");
	 var ms = $(obj).attr("ms");
	 var tdjh = $(obj).attr("tdjh");
	 var tdjms = $(obj).attr("tdjms");
	 var jxbs = $(obj).attr("jxbs");
	 var gkbs = $(obj).attr("gkbs");
	 var knxbs = $(obj).attr("knxbs");
	 var id = $(obj).attr("id");
	 
	 if(jxbs=="00000" ){
		 $("#jxbs").multiselect('select',"00000");
	 }
	 if(gkbs=="00000" ){
		 $("#gkbs").multiselect('select',"00000");
	 }
	 //加载回显机型标识或工卡标识
	 bsLoing(id);
	$('#bjh').val(bjh);
	$('#ms').val(ms);
	$('#tdjh').val(tdjh);
	$('#tdjms').val(tdjms);
	$('#jxbs').val(jxbs);
	$('#gkbs').val(gkbs);
	$("input:radio[name='knxbs']:checked").val(); 
	$(":radio[name='knxbs'][value='" + knxbs + "']").prop("checked", "checked");
	$('#substitutionId').val(id);
	
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents("dxtd_modal_attachments_list_edit");
	attachmentsObj.show({
		djid:id,
		fileHead : true,
		colOptionhead : true,
		fileType:"substitution"
	});//显示附件
	
	
	$("#alertModalAdd").modal("show");
}
function saveUpdate(){
	validation();
	//验证
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	//件号
	var bjh=$('#bjh').val();
	var ms=$('#ms').val();
	//替代件号
	var tdjh=$('#tdjh').val();
	var tdjms=$('#tdjms').val();
	var jxbs="";
	var gkbs="";
	var jxbsList=$('#jxbs').val();
	var gkbsList=$('#gkbs').val();
	if(jxbsList==null){
		AlertUtil.showMessage('请选择机型适用性!');
		return false;
	}
	if(gkbsList==null){
		AlertUtil.showMessage('请选择工卡适用性!');
		return false;
	}
	if($.inArray("00000", jxbsList)!=-1){
		var jxbs="00000";
		jxbsList=[];
	}else{
		var jxbs="-";
	}
	if($.inArray("00000", gkbsList)!=-1){
		var gkbs="00000";
		gkbsList=[];
	}else{
		var gkbs="-";
	}
	var knxbs=$("input:radio[name='knxbs']:checked").val(); 
	
	var id=$('#substitutionId').val();
	var obj={};
	obj.id=id;
	obj.bjh=bjh;
	obj.ms=ms;
	obj.tdjh=tdjh;
	obj.tdjms=tdjms;
	obj.jxbs=jxbs;
	obj.gkbs=gkbs;
	obj.knxbs=knxbs;
	obj.jxbsList=jxbsList;
	obj.gkbsList=gkbsList;
	
	
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents("dxtd_modal_attachments_list_edit");
	obj.attachments = attachmentsObj.getAttachments();	

	var url='';
	if(id!=null && id!=""){
		url = basePath+"/basic/substitution/edit";//修改
		obj.dprtcode=$("#substitutionDprtcode").val();
	}else{
		url = basePath+"/basic/substitution/add";//新增
		obj.dprtcode=$("#zzjgid").val();
	}
	
	//校验部件号和替代件号不能一样
	  if(bjh==tdjh){
		  AlertUtil.showMessage('件号替代件号不能一样'); 
		    return;
	  }
	
	
	//校验唯一性(件号+替代件号确定唯一);
	var isUnique=validateUnique(bjh,tdjh,id,obj.dprtcode);
	  if(isUnique){
		  AlertUtil.showMessage('件号+替代件号已存在,数据添加失败');
		    return;
	  }
	
	submitAjax(obj,url);
}
//作废
function invalid(id){
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		var obj={};
		obj.id=id;
		url = basePath+"/basic/substitution/invalid";//作废
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
 			id=data;
 			finishWait();
 			$("#alertModalAdd").modal("hide");
 			AlertUtil.showMessage('操作成功!');
 			decodePageParam();
 			 
 		}
 	});
}
//隐藏Modal时验证销毁重构
$("#"+alertModalAdd).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     validation();
});

//校验唯一性(件号+替代件号确定唯一);
function validateUnique(bjh,tdjh,id,dprtcode){
	var obj={};
	 obj.bjh=bjh;
	 obj.tdjh=tdjh;
	 obj.id=id;
	 obj.dprtcode=dprtcode;
	var boolean=false;
	AjaxUtil.ajax({
 		url:basePath+"/basic/substitution/validateUnique",
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		success:function(data){
 			if(data){
 				  boolean=true;
 			}
 			//AlertUtil.showMessage('操作成功!');
 		}
 	});
	
	    return boolean;
}

function show(id){
	
	AjaxUtil.ajax({
		   url:basePath+"/basic/substitution/selectById",
			 type:"post",
			 async: true,
			 data:{
				 'id' : id
			 },
			 dataType:"json",
			 success:function(data){
              if(data){
             	 appendHtml(data);
              }else{
             	 AlertUtil.showMessage('未查到相关数据');
              }
	      }
	    });	
	
	$("#alertModalShow").modal("show");
}

function appendHtml(row){
	// 件号
	var bjh = row.bjh;
	// 替代件号
	var tdjh = row.tdjh;
	// 描述
	var ms = row.ms;
	// 替代描述
	var tdjms = row.tdjms;

	// 机器适用性
	var jxbs = [];
	// 工卡适用性
	var gkbs = [];

	var applicabilityList = row.applicabilityList;
	if (applicabilityList instanceof Array) {
		if (applicabilityList && applicabilityList.length >= 2) {
			for ( var i = 0; i < applicabilityList.length; i++) {
				if (applicabilityList[i].syxlx == "1") {
					jxbs.push(applicabilityList[i].sydx);
				} else if (applicabilityList[i].syxlx == "2") {
					gkbs.push(applicabilityList[i].sydx);
				}
				if (jxbs.length == applicabilityList.length) {
					gkbs.push("通用currency");
				}
				if (gkbs.length == applicabilityList.length) {
					jxbs.push("通用currency");
				}
			}

		} else if (applicabilityList && applicabilityList.length == "1") {
			var applicability = applicabilityList[0];
			if (applicability.syxlx == "1") {
				jxbs.push(applicability.sydx);
				gkbs.push("通用currency");
			} else if (applicability.syxlx == "2") {
				gkbs.push(applicability.sydx);
				jxbs.push("通用currency");
			}
		} else {
			jxbs.push("通用currency");
			gkbs.push("通用currency");
		}
	}

	// 是否可逆
	var knxbs = row.knxbs;
	if (knxbs == "2") {
		knxbs = "是";
	} else if (knxbs == "1") {
		knxbs = "否";
	}

	$("#bjh_show").val(bjh);
	$("#tdjh_show").val(tdjh);
	$("#ms_show").val(ms);
	$("#tdjms_show").val(tdjms);
	$("#jxbs_show").val(jxbs.join(","));
	$("#gkbs_show").val(gkbs.join(","));
	$("#knxbs_show").val(knxbs);

	var attachmentsObj = attachmentsUtil
			.getAttachmentsComponents("dxtd_modal_attachments_list_show");
	attachmentsObj.show({
		djid : row.id,
		fileHead : false,
		colOptionhead : false,
		fileType : "substitution"
	});//显示附件
	$("#alertModalShow").modal("show");
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
