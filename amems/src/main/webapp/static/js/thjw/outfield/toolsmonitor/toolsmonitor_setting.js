
var flag = false;
var selectRowId = '';
var editRowId = '';
var measuringToolId = 'measuringTool';
var add = 'add';
var save = 'save';
var toolsDetailId = 'toolsDetailList';
var alertToolsDetailWinId = 'alertToolsDetailForm';

$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	if(bjxlh != null && bjxlh != '' && dprtcode != null && dprtcode != ''){
		$("#keyword_search").val(bjxlh);
		$("#dprtcode_search").val(dprtcode);
	}
	goPage(1,"auto","desc");//开始的加载默认的内容 
	initDate();
	formValidator();
	refreshPermission();
	//初始化日志功能
	logModal.init({code:'B_W_001'});
});

//form验证规则
function formValidator(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
            bjxlh: {
                validators: {
                    notEmpty: {
                        message: '编号不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            },
            ywms: {
                validators: {
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            },
            jyZq: {
                validators: {
                    notEmpty: {
                        message: '检验周期不能为空'
                    },
                    stringLength: {
                        max: 4,
                        message: '长度最多不超过4个字符'
                    }
                }
            },
            jyScrq: {
                validators: {
                    notEmpty: {
                        message: '最新检验日期不能为空'
                    }
                }
            }      
        }
    });
}

function initDate(){
	
	$("#jyScrq", $("#"+alertToolsDetailWinId)).datepicker({
		 autoclose: true,
		 clearBtn:false,
		 //startDate:'2016-12-30',
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
		    .updateStatus('jyScrq', 'NOT_VALIDATED',null)  
		    .validateField('jyScrq');  
		});
	 
}
//隐藏Modal时验证销毁重构
$("#"+alertToolsDetailWinId).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     formValidator();
});


	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		var url = basePath+"/outfield/toolsmonitor/queryAllStockPageList";
		startWait();
		AjaxUtil.ajax({
			 url:url,
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   $("#toolsDetailId").hide();
			   $("#selectTools").html('');
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
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,6],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"13\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var hclx = $.trim($("#hclx_search").val());
		 var isJl = $.trim($("#isJl_search").val());
		 paramsMap.dprtcode = $.trim($("#dprtcode_search").val());
		 paramsMap.keyword = keyword;
		 if('' != isJl){
			 searchParam.isJl = isJl;
		 }
		 if('' != hclx){
			 paramsMap.hclx = hclx;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var id = '';
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   
			   if(index == 0){
				   id = row.ID;
			   }
			   
			   if (index % 2 == 0) {
				   htmlContent += "<tr id='"+row.ID+"' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick=selectRow('"+row.ID+"')>";
			   } else {
				   htmlContent += "<tr id='"+row.ID+"' style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=selectRow('"+row.ID+"')>";
			   }
			   
			   htmlContent += "<td class='text-center'>";
			   htmlContent += (index+1);
			   htmlContent += "<input type='hidden' name='bjid' value='"+formatUndefine(row.BJID)+"' />";
			   htmlContent += "<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.ZWMS)+"' />";
			   htmlContent += "<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(row.YWMS)+"' />";
			   htmlContent += "<input type='hidden' name='jlid' value='"+formatUndefine(row.JLID)+"' />";
			   htmlContent += "<input type='hidden' name='isjl' value='"+formatUndefine(row.ISJL)+"' />";
			   htmlContent += "<input type='hidden' name='sn' value='"+StringUtil.escapeStr(row.SN)+"' />";
			   htmlContent += "<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(row.BJH)+"' />";
			   htmlContent += "<input type='hidden' name='gg' value='"+StringUtil.escapeStr(row.GG)+"' />";
			   htmlContent += "<input type='hidden' name='xh' value='"+StringUtil.escapeStr(row.XH)+"' />";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.SN)+"' class='text-left' >"+StringUtil.escapeStr(row.SN)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.BJH)+"' class='text-left' >"+StringUtil.escapeStr(row.BJH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"' class='text-left'>";
			   htmlContent += StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS);
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.GG)+"' class='text-left' >"+StringUtil.escapeStr(row.GG)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.XH)+"' class='text-left' >"+StringUtil.escapeStr(row.XH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.CKH)+" "+StringUtil.escapeStr(row.CKMC)+" "+StringUtil.escapeStr(row.KWH)+"' class='text-left'>"+StringUtil.escapeStr(row.CKH)+" "+StringUtil.escapeStr(row.CKMC)+" "+StringUtil.escapeStr(row.KWH)+"</td>";
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.WZ)+"</td>";
			   htmlContent += "<td class='text-center' name='isjl'>"+DicAndEnumUtil.getEnumName('measurementMarkEnum',row.ISJL) +"</td>";
			   htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX) +"</td>";
			   htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.RKSJ))+"</td>";
			   htmlContent += "<td class='text-center' name='jyscrq'>"+indexOfTime(formatUndefine(row.JYSCRQ))+"</td>";
			   htmlContent += "<td>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   selectRow(id);
		   if(bjxlh != null && bjxlh != ''){
			   bjxlh = '';
			   dprtcode = '';
		   }
	 }
//选中一行
function selectRow(id){
	selectRowId = id;
	$("#toolsDetailId").show();
	var row = getSelectRow(selectRowId);
	$("#selectTools").html('已选择编号'+row.bjxlh);
	initDetail();//初始化详情信息
	$("#"+selectRowId).addClass('bg_tr').siblings().removeClass('bg_tr');
}
//初始化详情信息
function initDetail(){
	var row = getSelectRow(selectRowId);
	initEffectorDisabled();//初始化生效或失效
	if(row.jlid != null && row.jlid != ''){
		var detailCount = loadToolsDetailList();
		if(row.isjl != 1){
			$("#"+measuringToolId).removeAttr("checked");//设置计量工具不选中
		}
		setEffectorDisabled();
		if(detailCount > 0){
			setEffective(save);//设置保存生效
			if(detailCount == 1){
				setEffective(measuringToolId);//设置计量工具生效
			}
		}
	}
}

//加载计量工具监控详情信息
function loadToolsDetailList(){
	var count = 0;
	var row = getSelectRow(selectRowId);
	if(row.jlid != null && row.jlid != ''){
		var url = basePath+"/outfield/toolsmonitor/queryDetailByMainId";
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			type:"post",
			async:false,
			data:{mainid : row.jlid},
			dataType:"json",
			success:function(data){
				finishWait();
				if(null != data && data.length > 0){
					appendToolsDetailHtml(data);
					count = data.length;
				}
			}
		});
	}
	return count;
}
//拼接计量工具监控详情信息
function appendToolsDetailHtml(list){
	 
	var htmlContent = '';
	var size = list.length;
	$.each(list,function(index,row){
	   
		if (index % 2 == 0) {
			htmlContent += "<tr id='"+row.id+"' bgcolor=\"#f9f9f9\" >";
		} else {
			htmlContent += "<tr id='"+row.id+"' bgcolor=\"#fefefe\" >";
		}
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
	   
		htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsmonitor:setting:03' onClick=\"openToolsDetailEditWin('" + row.id + "')\" title='修改 Edit'></i>";
		if(row.bjbs != 1 || (row.bjbs == 1 && size == 1)){
			htmlContent += "&nbsp;&nbsp;<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsmonitor:setting:04' bjxlh='"+StringUtil.escapeStr(row.bjxlh)+"' onClick=\"deleteDetails('"+ row.mainid +"',this,'" + row.bjbs + "')\" title='删除 Delete'></i>";
		}
	  
		htmlContent += "</td>";
	   
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.bjxlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjxlh)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"' style='text-align:left;vertical-align:middle;'>";
		htmlContent += StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms);
		htmlContent += "<input type='hidden' name='id' value='"+formatUndefine(row.id)+"' />";
		htmlContent += "<input type='hidden' name='mainid' value='"+formatUndefine(row.mainid)+"' />";
		htmlContent += "<input type='hidden' name='bjxlh' value='"+StringUtil.escapeStr(row.bjxlh)+"' />";
		htmlContent += "<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.zwms)+"' />";
		htmlContent += "<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(row.ywms)+"' />";
		htmlContent += "<input type='hidden' name='bjbs' value='"+StringUtil.escapeStr(row.bjbs)+"' />";
		htmlContent += "<input type='hidden' name='jyScrq' value='"+indexOfTime(formatUndefine(row.jyScrq))+"' />";
		htmlContent += "<input type='hidden' name='jyXcrq' value='"+indexOfTime(formatUndefine(row.jyXcrq))+"' />";
		htmlContent += "<input type='hidden' name='bz' value='"+StringUtil.escapeStr(row.bz)+"' />";
		htmlContent += "</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.gg)+"' style='text-align:left;vertical-align:middle;' name='gg'>"+StringUtil.escapeStr(row.gg)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.xh)+"' style='text-align:left;vertical-align:middle;' name='xh'>"+StringUtil.escapeStr(row.xh)+"</td>";
		htmlContent += "<td style='text-align:right;vertical-align:middle;' name='jyZq'>"+StringUtil.escapeStr(row.jyZq)+"</td>";
		htmlContent += "<td>";
		htmlContent += "<div class='col-xs-12 padding-left-8 padding-right-0'>";
		htmlContent += "<input type='text' class='form-control datepicker' name='newjyScrq' value="+formatUndefine(row.jyScrq)+" data-date-start-date='"+indexOfTime(formatUndefine(row.jyScrq))+"'  data-date-format='yyyy-mm-dd' placeholder=''  maxlength='10' onchange=changeDate('"+row.id+"') />";
		htmlContent += "</div>";
		htmlContent += "</td>";
		htmlContent += "<td style='text-align:center;vertical-align:middle;' name='newjyXcrq'>"+indexOfTime(formatUndefine(row.jyXcrq))+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"'  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		htmlContent += "</tr>";  
	});
	$("#"+toolsDetailId).empty();
	$("#"+toolsDetailId).html(htmlContent);
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:false
	});
	refreshPermission();
}

//加载计量工具监控历史详情信息
function loadHistoryDetailList(){
	$("#historyDetailList").empty();
	$("#historyDetailList").append("<tr><td  colspan='6' style='text-align:right;vertical-align:middle;'>暂无数据 No data.</td></tr>");
	var row = getToolsDetailFormData();
	var url = basePath+"/outfield/toolsmonitor/queryDetailByMainIdAndBjxlh";
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		type:"post",
		async:false,
		data:{mainid : row.mainid,bjxlh : row.bjxlh},
		dataType:"json",
		success:function(data){
			finishWait();
			if(null != data && data.length > 0){
				appendHistoryDetailHtml(data);
			}
		}
	});
}
//拼接计量工具监控详情信息
function appendHistoryDetailHtml(list){
	 
	var htmlContent = '';
	var size = list.length;
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr id='"+row.id+"' bgcolor=\"#f9f9f9\" >";
		} else {
			htmlContent += "<tr id='"+row.id+"' bgcolor=\"#fefefe\" >";
		}
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
	   
		if(size != 1){
			htmlContent += "&nbsp;&nbsp;<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='outfield:toolsmonitor:setting:04' onClick=\"deleteHistoryDetail('"+ row.id +"','" + row.mainid + "')\" title='删除 Delete'></i>";
		}
	  
		htmlContent += "</td>";
	   
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(formatUndefine(row.jyScrq))+"</td>";
		htmlContent += "<td title='"+(row.whr?(StringUtil.escapeStr(row.whr.displayName)):'')+"' style='text-align:left;vertical-align:middle;'>";
		htmlContent += row.whr?(StringUtil.escapeStr(row.whr.displayName)):'';
		htmlContent += "</td>";
		htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+formatUndefine(row.whsj)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		htmlContent += "</tr>";  
	});
	$("#historyDetailList").empty();
	$("#historyDetailList").html(htmlContent);
	refreshPermission();
}

//打开工具/设备详情弹出框(新增)
function openToolsDetailAddWin(){
	$("#historyDetail").hide();
	//初始化最新检验日期
	TimeUtil.getCurrentDate(function (data){
		$("#jyScrq", $("#"+alertToolsDetailWinId)).val(data);
	});
	$("#jyScrq", $("#"+alertToolsDetailWinId)).datepicker('setStartDate','');
	var row = getSelectRow(selectRowId);
	if(row.jlid == null || row.jlid == ''){
		row.bjbs = 1;
		setToolsDetailFormData(row);
		setOneDisabled();//第一次新增时设置失效
		setEffective("isJl");
	}else{
		setToolsDetailFormData({jlid : row.jlid,bjbs : 0});
		setNotOneEffective();//非第一次新增时设置生效
		setDisabled("isJl");
	}
	$("#"+alertToolsDetailWinId).modal("show");
	AttachmengsList.show({
		djid:'',
		fileType:"contract"
	});//显示附件
}

//打开工具/设备详情弹出框(编辑)
function openToolsDetailEditWin(id){
	$("#historyDetail").show();
	editRowId = id;
	var row = getToolsDetailRow(editRowId);
	setToolsDetailFormData(row);
	$("#jyScrq", $("#"+alertToolsDetailWinId)).datepicker('setStartDate',row.jyScrq);
	setOneDisabled();//编辑时设置失效
	setDisabled("isJl");
	loadHistoryDetailList();//加载历史详情信息
	$("#"+alertToolsDetailWinId).modal("show");
	AttachmengsList.show({
		djid:editRowId,
		fileType:"contract"
	});//显示附件
}

//保存监控详情
function saveDetail(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	
	var url = basePath+"/outfield/toolsmonitor/addToolsMonitorDetail";
	var param = getToolsDetailFormData();
	var row = getSelectRow(selectRowId);
	param.bjh = row.bjh;
	param.attachments = AttachmengsList.getAttachments();
	var toolsMonitor = {};
	if(row.jlid == null || row.jlid == ''){
		url = basePath+"/outfield/toolsmonitor/addToolsMonitor";
		toolsMonitor.bjid = row.bjid;
		toolsMonitor.bjxlh = row.bjxlh;
	}
	toolsMonitor.isJl = 0;
	if($("#isJl", $("#"+alertToolsDetailWinId)).is(":checked")){
		toolsMonitor.isJl = 1;
	}
	param.toolsMonitor = toolsMonitor;
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		async:false,
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			if(null != data){
				AlertUtil.showMessage('保存成功!');
				$("#"+alertToolsDetailWinId).modal("hide");
				setRowDate(data);//设置行数据
				initDetail();//初始化详情信息
			}
		}
	});
}

//保存所有详情信息
function saveAll(){
	
	var isJl = 0;
	if($("#"+measuringToolId).is(":checked")){
		isJl = 1;
	}
	var toolsMonitorDetailList = getToolsDetailListData();
	var row = getSelectRow(selectRowId);
	var param = {};
	param.id = row.jlid;
	param.isJl = isJl;
	param.toolsMonitorDetailList = toolsMonitorDetailList;
	var url = basePath+"/outfield/toolsmonitor/addToolsMonitorDetailList";
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		async:false,
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			if(null != data){
				AlertUtil.showMessage('保存成功!');
				setRowDate(data);//设置行数据
				initDetail();//初始化详情信息
			}
		}
	});
}


//删除计量工具监控详情信息
function deleteDetails(mainid,this_,bjbs){
	var bjxlh = $(this_).attr("bjxlh");
	AlertUtil.showConfirmMessage("确定删除吗？", {callback: function(){
	
		var param = {
				mainid : mainid,
				bjxlh : bjxlh,
				bjbs : bjbs
		}
		var url = basePath+"/outfield/toolsmonitor/deleteDetailByMainIdAndBjxlh";
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			async:false,
			data:JSON.stringify(param),
			dataType:"json",
			success:function(data){
				finishWait();
				if(null != data){
					AlertUtil.showMessage('删除成功!');
					setRowDate(data);//设置行数据
					initDetail();//初始化详情信息
				}
			}
		});
	
		}
	});
}

//删除计量工具监控历史详情信息
function deleteHistoryDetail(id,mainid){
	AlertUtil.showConfirmMessage("确定删除吗？", {callback: function(){
	
		var url = basePath+"/outfield/toolsmonitor/deleteDetail";
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			type:"post",
			async:false,
			data:{detailId : id,mainid : mainid},
			dataType:"json",
			success:function(data){
				finishWait();
				if(null != data){
					AlertUtil.showMessage('删除成功!');
					setRowDate(data);//设置行数据
					initDetail();//初始化详情信息
					loadHistoryDetailList();//加载历史详情信息
				}
			}
		});
	
		}
	});
}
//根据行id获取行数据
function getSelectRow(id){
	var row = {};
	row.bjid = $.trim($("#"+id).find("input[name='bjid']").val());
	row.zwms = $.trim($("#"+id).find("input[name='zwms']").val());
	row.ywms = $.trim($("#"+id).find("input[name='ywms']").val());
	row.jlid = $.trim($("#"+id).find("input[name='jlid']").val());
	row.isjl = $.trim($("#"+id).find("input[name='isjl']").val());
	row.bjxlh = $.trim($("#"+id).find("input[name='sn']").val());
	row.bjh = $.trim($("#"+id).find("input[name='bjh']").val());
	row.gg = $.trim($("#"+id).find("input[name='gg']").val());
	row.xh = $.trim($("#"+id).find("input[name='xh']").val());
	row.jyscrq = $.trim($("#"+id).find("td[name='jyscrq']").html());
	row.detailId = '';
	row.jyZq = '';
	row.jyScrq = '';
	row.jyXcrq = '';
	row.bz = '';
	return row;
}

//获取计量工具监控详情信息
function getToolsDetailRow(id){
	var row = {};
	row.detailId = id;
	row.jlid = $.trim($("#"+id).find("input[name='mainid']").val());
	row.bjxlh = $.trim($("#"+id).find("input[name='bjxlh']").val());
	row.zwms = $.trim($("#"+id).find("input[name='zwms']").val());
	row.ywms = $.trim($("#"+id).find("input[name='ywms']").val());
	row.bjbs = $.trim($("#"+id).find("input[name='bjbs']").val());
	row.jyScrq = $.trim($("#"+id).find("input[name='jyScrq']").val());
	row.jyXcrq = $.trim($("#"+id).find("input[name='jyXcrq']").val());
	row.bz = $.trim($("#"+id).find("input[name='bz']").val());
	row.gg = $.trim($("#"+id).find("td[name='gg']").text());
	row.xh = $.trim($("#"+id).find("td[name='xh']").text());
	row.jyZq = $.trim($("#"+id).find("td[name='jyZq']").html());
	return row;
}

//获取详情表单数据
function getToolsDetailFormData(){
	var toolsMonitorDetail = {};
	
	toolsMonitorDetail.id = $.trim($("#detailId", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.mainid = $.trim($("#mainid", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.bjbs = $.trim($("#bjbs", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.bjxlh = $.trim($("#bjxlh", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.zwms = $.trim($("#zwms", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.ywms = $.trim($("#ywms", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.gg = $.trim($("#gg", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.xh = $.trim($("#xh", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.jyZq = $.trim($("#jyZq", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.jyScrq = $.trim($("#jyScrq", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.jyXcrq = $.trim($("#jyXcrq", $("#"+alertToolsDetailWinId)).val());
	toolsMonitorDetail.bz = $.trim($("#bz", $("#"+alertToolsDetailWinId)).val());
	
	return toolsMonitorDetail;
}

//获取详情列表数据
function getToolsDetailListData(){
	var toolsMonitorDetailList = [];
	var row = getSelectRow(selectRowId);
	$("#"+toolsDetailId).find("tr").each(function(){
		var oldjyScrq = $(this).find("input[name='jyScrq']").val();
		var newjyScrq = $(this).find("input[name='newjyScrq']").val();
		if(oldjyScrq != newjyScrq){
			var toolsMonitorDetail = {};
			toolsMonitorDetail.bjh = row.bjh;
			toolsMonitorDetail.id = $.trim($(this).find("input[name='id']").val());
			toolsMonitorDetail.mainid = $.trim($(this).find("input[name='mainid']").val());
			toolsMonitorDetail.bjbs = $.trim($(this).find("input[name='bjbs']").val());
			toolsMonitorDetail.bjxlh = $.trim($(this).find("input[name='bjxlh']").val());
			toolsMonitorDetail.zwms = $.trim($(this).find("input[name='zwms']").val());
			toolsMonitorDetail.ywms = $.trim($(this).find("input[name='ywms']").val());
			toolsMonitorDetail.gg = $.trim($(this).find("td[name='gg']").text());
			toolsMonitorDetail.xh = $.trim($(this).find("td[name='xh']").text());
			toolsMonitorDetail.jyZq = $.trim($(this).find("td[name='jyZq']").html());
			toolsMonitorDetail.jyScrq = newjyScrq;
			toolsMonitorDetail.jyXcrq = $(this).find("td[name='newjyXcrq']").html();
			toolsMonitorDetail.bz = $.trim($(this).find("input[name='bz']").val());
			toolsMonitorDetailList.push(toolsMonitorDetail);
		}
	});
	return toolsMonitorDetailList;
}

//保存后更新行数据
function setRowDate(data){
	var isJlStr = DicAndEnumUtil.getEnumName('measurementMarkEnum',data.isJl);
	if(data.isJl == null || data.isJl === ''){
		isJlStr = '';
	}
	$("#"+selectRowId).find("input[name='jlid']").val(formatUndefine(data.jlid));
	$("#"+selectRowId).find("input[name='isjl']").val(formatUndefine(data.isJl));
	$("#"+selectRowId).find("td[name='isjl']").html(isJlStr);
	$("#"+selectRowId).find("td[name='jyscrq']").html(indexOfTime(formatUndefine(data.jyscrq)));
}

//设置详情表单数据
function setToolsDetailFormData(row){
	$("#detailId", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.detailId));
	$("#mainid", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.jlid));
	$("#bjbs", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.bjbs));
	$("#bjxlh", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.bjxlh));
	$("#zwms", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.zwms));
	$("#ywms", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.ywms));
	$("#gg", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.gg));
	$("#xh", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.xh));
	if($("#"+measuringToolId).is(":checked")){
		$("#isJl", $("#"+alertToolsDetailWinId)).attr("checked",'true');
	}else{
		$("#isJl", $("#"+alertToolsDetailWinId)).removeAttr("checked");
	}
	
	$("#jyZq", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.jyZq));
	$("#jyScrq", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.jyScrq));
	$("#jyXcrq", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.jyXcrq));
	$("#bz", $("#"+alertToolsDetailWinId)).val(formatUndefine(row.bz));
}
//改变周期或最新检验日期时加载下次校验日期
function loadNextDate(){
	var jyZq = $("#jyZq", $("#"+alertToolsDetailWinId)).val();
	var jyScrq = $("#jyScrq", $("#"+alertToolsDetailWinId)).val();
	$("#jyXcrq", $("#"+alertToolsDetailWinId)).val(TimeUtil.dateOperator(jyScrq,jyZq));
}
//改变最新检验日期时加载下次校验日期
function changeDate(id){
	var jyZq = $("#"+id, $("#"+toolsDetailId)).find("td[name='jyZq']").html();
	var jyScrq = $("#"+id, $("#"+toolsDetailId)).find("input[name='newjyScrq']").val();
	$("#"+id, $("#"+toolsDetailId)).find("td[name='newjyXcrq']").html(TimeUtil.dateOperator(jyScrq,jyZq));
}

//清空详情数据
function clearToolsDetail(){
	$("#"+toolsDetailId).empty();
	$("#"+toolsDetailId).append("<tr><td  colspan='10' class='text-center'>暂无数据 No data.</td></tr>");
}

//初始化生效或失效
function initEffectorDisabled(){
	$("#"+measuringToolId).attr("checked",'true');//设置计量工具选中
	setDisabled(measuringToolId);//设置计量工具失效
	setDisabled(save);//设置保存失效
	setEffectorDisabled();//设置添加生效或失效
	clearToolsDetail();//清空详情数据
}

//设置添加生效或失效
function setEffectorDisabled(){
	if($("#"+measuringToolId).is(":checked")){
		setEffective(add);
	}else{
		setDisabled(add);
	}
}

//第一次新增时或编辑时设置失效
function setOneDisabled(){
	$("#bjxlh", $("#"+alertToolsDetailWinId)).attr("disabled",'true');
	$("#zwms", $("#"+alertToolsDetailWinId)).attr("disabled",'true');
	$("#ywms", $("#"+alertToolsDetailWinId)).attr("disabled",'true');
	$("#gg", $("#"+alertToolsDetailWinId)).attr("disabled",'true');
	$("#xh", $("#"+alertToolsDetailWinId)).attr("disabled",'true');
}

//非第一次新增时设置生效
function setNotOneEffective(){
	$("#bjxlh", $("#"+alertToolsDetailWinId)).removeAttr("disabled");
	$("#zwms", $("#"+alertToolsDetailWinId)).removeAttr("disabled");
	$("#ywms", $("#"+alertToolsDetailWinId)).removeAttr("disabled");
	$("#gg", $("#"+alertToolsDetailWinId)).removeAttr("disabled");
	$("#xh", $("#"+alertToolsDetailWinId)).removeAttr("disabled");
}

//设置失效
function setDisabled(id){
	$("#"+id).attr("disabled",'true');
}

//设置生效
function setEffective(id){
	$("#"+id).removeAttr("disabled");
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
		goPage(1,"auto","desc");
	}
		
	//搜索条件重置
	function searchreset(){
		var dprtId = $.trim($("#dprtId").val());
		
		$("#keyword_search").val("");
		
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
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

		 
 //回车事件控制
 document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		if($("#keyword_search").is(":focus") 
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
				|| $("#keyword_search").is(":focus")
		){
			searchRevision();      
		}
	}
};
		 
//只能输入数字
function clearNoNum(obj){
	//先把非数字的都替换掉，除了数字
    obj.value = obj.value.replace(/[^\d]/g,"");
    if(obj.value.length >= 1 && obj.value.substring(0,1) == 0){
 		obj.value = 1;
 	 }
    loadNextDate();
}