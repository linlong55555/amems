var planTaskIds = [];
var currentScop = {param:{}}  ;
var resultMap = {};
//20170519修改
//空白位置的高度
function panelContentHeight(){
	 // 头高度
    var headerHeight = $('.header').outerHeight();
    // 头部菜单高度
    var headerDownHeight = $('.header-down').outerHeight();
    // 屏幕高度
    var windowHeight = $(window).height();
    // 底部高度
    var footerHeight = $('.footer').outerHeight()||0;
    // 分页控件高度
    var paginationHeight = $('.page-height:visible').outerHeight()||0;
    // 关键字高度
    var rowHeight = $('.row-height:visible').outerHeight()||0;
    // 查询高度
    var searchHeight = $('.search-height:visible').outerHeight()||0;
    if(searchHeight){searchHeight = searchHeight += 10}
    // 面板标题高度
    var panelHeadingHeight = $('.panel-heading').outerHeight();
    
    var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight-searchHeight-40 ;
    return bodyHeight;
}

$(document).ready(function() {
	
	Navigation(menuCode);
	
	validationRemark();
     $("#plantask_table_div").css("height",panelContentHeight()+"px");
	//开始的加载默认的内容 
	//goPage(1, "desc", "sydays");
    //移动窗口
	$(window).resize(function(){
		if($("#zyxx_div").css("display")=="block"){
			$("#plantask_table_div").css("height","auto");
		     $("#plantask_table_div").css("max-height",parseInt(panelContentHeight()*0.7)+"px");
		     $("#zyxx_div").css("max-height",(panelContentHeight()-parseInt($("#plantask_table_div").css("height")))-30+"px")
		}else{
			 $("#plantask_table_div").css("height",panelContentHeight()+"px");
			 $("#plantask_table_div").css("max-height","none");
		}
	})
	 $('#fjzch').on('change',function(){
		 goPage(1,"auto","sydays");
	 });
	 $('#dprtcode').on('change',function(){
		 	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		 	var planeRegOption = '';
			if(planeDatas != null && planeDatas.length >0){
				$.each(planeDatas,function(i,planeData){
					planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
				});
			}
			planeRegOption += '<option value="">非装机件</option>'
			$("#fjzch").html(planeRegOption);
			 
			$("#fjzch").trigger("change");
	 });
	 
	 
	 $("#dprtcode").trigger("change");
	//信息检索
	 $('#searchBtn').on('click',function(){
	 	goPage(1, "desc", "sydays");
	 })
	
	//航材缺件单击
	$('#hcStatisticsBtn').on('click', function() {
		var plans = [];
		var chks  = $("#plantask input[type='checkbox'][name='chk']:checked");
		var len = chks.length;
		if(len>0){
			if(len>1000){
				AlertUtil.showMessage("选择的计划限于1000个以内");
				return false;
			}
			else{
				
				var dprtcode = $('#dprtcode').val();
				var isLoadedParts = $('#fjzch').val()==''?'0':'1';
				var fjzch = $('#fjzch').val();
				var url = basePath+'/productionplan/plantask/hcStatistics?fjzch='+encodeURIComponent(fjzch)+'&dprtcode='+dprtcode+'&isLoadedParts='+isLoadedParts+'&planTaskIds='; 
				var dataArr = [];
				for(var i=0;i<len;i++) {
					chk =  $(chks[i]);
					dataArr.push(
						chk.attr('id')
					);
				}
				window.open(url+dataArr.join(","));
			}
		}
		else{
			AlertUtil.showMessage("请选择需要统计缺件的计划");
		}
	});

	//全选/取消
	/*$('#chk-att').on('click', function() {
		var checked = $(this).is(':checked');
		$("input[type='checkbox'][name='chk']").prop('checked', checked);
	});*/

	 endValidator();
 
/**
 * 指定结束提交
 */
 $('#endbtn').on('click',function(){
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		return false;
	}
	
	var attachments = [];
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	if (len != undefined && len > 0) {
		for (var i = 0; i < len; i++) {
			attachments
					.push({
						'yswjm' : responses[i].attachments[0].yswjm,
						'wbwjm' : responses[i].attachments[0].wbwjm,
						'nbwjm' : responses[i].attachments[0].nbwjm,
						'wjdx' : responses[i].attachments[0].wjdx,
						'cflj' : responses[i].attachments[0].cflj,
						'id' : responses[i].attachments[0].id,
						'operate' : responses[i].attachments[0].operate
	
					});
		}
	}
		
	   
	  id = $.trim($("#alertModalZdjs #id").val());	
	  //参数组装
	  var obj = {};
	  obj.id = $.trim($("#alertModalZdjs #id").val());				//主键id
      obj.zdjsyy =  $.trim($("#zdjsyy").val());		//指定结束原因
      obj.attachments = attachments;
	  AjaxUtil.ajax({
		  url:basePath+'/productionplan/plantask/end',						//发送请求的地址（默认为当前页地址）
		  type : "post",
		  data : JSON.stringify(obj),
		  dataType : "json",
		  contentType : "application/json",
		  async: false,
		  cache:false,
		  success:function(data) {	
			  //请求成功后调用的回调函数
			  if(data.isLegitimate){
				  AlertUtil.showMessage('操作成功');
				  $('#searchBtn').trigger('click');
			  }
			  else{
				  AlertUtil.showMessage(data.message);
			  }
			  
		  },
		  error:function(data) {								 
			  AlertUtil.showMessage('操作失败');
			  $('#searchBtn').trigger('click');
		  }
	  }); 
	 
  })
  
    $('#alertModalZdjs').on('hide.bs.modal', function () {
    	$('#searchBtn').trigger('click');
	})
		
	$('#alertModalZdjs').on('hidden.bs.modal', function () {
		$('#searchBtn').trigger('click');
	})
	
	$('#export').on('click', function () {
		
		//var url = basePath+'/productionplan/plantask/export/xls?';
		var url = basePath+'/productionplan/plantask/planTask.xls?';
		
		url += 'keyword='+encodeURIComponent(currentScop.param.keyword);
		var fjzch = currentScop.param.fjzch;
		if (fjzch != '') {
			url += '&fjzch='+encodeURIComponent(fjzch);
		} else {
			url += '&isLoadedParts=0';
		}
		url += '&dprtcode='+$('#dprtcode').val();
		url += '&pagination.sort='+currentScop.param['pagination.sort'];
		url += '&pagination.order='+currentScop.param['pagination.order'];
		window.open(url);
	})
	
	//初始化日志功能
	logModal.init({code:'B_S_009_PLAN'/*,extparam:{IN_ZTS:[1,2]}*/});

});


/**
 * 修改监控备注
 */
$('#remarkbtn').on('click', function() {
	  $('#remarkform').data('bootstrapValidator').validate();
	  if(!$('#remarkform').data('bootstrapValidator').isValid()){
		return false;
	  }	
	id = $("#remarkform #id").val();
	startWait();
	AjaxUtil.ajax({
		cache:false,
		url : basePath + "/productionplan/plantask/editJkRemark",
		type : "post",
		data : {
			id:$("#remarkform #id").val()
			,bz:$("#remarkform #bz").val()
		},
		dataType:"json",
		async : false,
		success : function(data) {
			finishWait();
			AlertUtil.showMessageCallBack({
				message:"监控备注修改成功"
				,callback:function(){
					$('#searchBtn').trigger('click');
				}	
			});
		}
	});
	
});



var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var isLoadMonitor = false;

//加载系统阀值
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/productionplan/scheduledcheckitem/getByKeyDprtcode",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb1 = data.monitorsettings.yjtsJb1;
				yjtsJb2 = data.monitorsettings.yjtsJb2;
				yjtsJb3 = data.monitorsettings.yjtsJb3;
			}
			isLoadMonitor = true;
		}
	});
}

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber, sortType, sortField) {
	
	if(!isLoadMonitor){
		loadMonitorsettings();
	}
	
	var param = {};
	param.keyword = $.trim($("#keyword").val());
	var fjzch = $.trim($("#fjzch").val());
	if (fjzch != '') {
		param.fjzch = fjzch;
		param.isLoadedParts = '1'
	} else {
		param.isLoadedParts = '0'
	}

	param.dprtcode = $.trim($("#dprtcode").val());
	param['pagination.sort'] = sortField; //排序列字段名
	param['pagination.order'] = sortType; //排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
	
	if (id != "") {
		param.id = id;
	}
	currentScop.param = param;
	id = "";
	
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/productionplan/plantask/queryList",
		type : "post",
		data : param,
		dataType:"json",
		cache:false,
		async : false,
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtml(data.rows);
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append("<tr><td colspan=\"17\">暂无数据 No data.</td></tr>");
			}
			refreshPermission();
		}
	});
	
	new Sticky({tableId:'plantask'});
}

function appendContentHtml(list) {
	var htmlContent = '';
	$
			.each(list,function(index, row) {
						resultMap[row.id] = row;
						if(parseInt(row.sydays)<=parseInt(yjtsJb1)&&row.jhrw==null){
							   htmlContent = htmlContent + "<tr     bgcolor=\""+warningColor.level1+"\" onclick='showzyxx(this)' style='cursor:pointer;'>";
						   }else if(parseInt(row.sydays)>parseInt(yjtsJb1)&&parseInt(row.sydays)<=parseInt(yjtsJb2)&&row.jhrw==null){
							   htmlContent = htmlContent + "<tr        bgcolor=\""+warningColor.level2+"\" onclick='showzyxx(this)' style='cursor:pointer;'>";
						   }else{
							   htmlContent = htmlContent + "<tr     >";
						   }

						htmlContent = htmlContent
								+ "<td class=\"fixed-column\" style=\"vertical-align: middle;\"  >"
								+ "<input name='chk' type='checkbox' " +
										"id='"+row.id+"'" +
										" gddlx='"+row.rwlx+"' " +
										" rwdh='"+StringUtil.escapeStr(row.rwdh) +"' " +
										" rwxx='"+StringUtil.escapeStr(row.rwxx) +"' " +
										" onclick=\"checkbox_change(event,'"+index+"',this);\"" +
										
										" style='cursor:pointer;'/>"
								+ "</td>";
								
								htmlContent = htmlContent
								+ "<td style=\"vertical-align: middle; \" class='text-center fixed-column'>"
								+ "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='productionplan:plantask:manage:01' onClick=\"editJkRemark(event,'"
								+ row.id
								+"')\" title='编辑备注 EditRemark'></i>&nbsp;&nbsp;"
								
						htmlContent = htmlContent		
								+ "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='productionplan:plantask:manage:02' onClick=\"end(event,'"
								+ row.id
								+ "','"
								+ StringUtil.escapeStr(row.rwdh) 
								+ "','"
								+ row.rwlx 
								+ "')\" title='指定结束 End'></i>&nbsp;&nbsp;"
								
							/*	+ "<i class='icon-print color-blue cursor-pointer checkPermission'  permissioncode='productionplan:plantask:print' onClick=\"print('"
								+ row.id
								+ "')\" title='打印 Print'></i>"*/
								+ "</td>";

						var gddlxForOrder = formatUndefine(row.rwlx)=='1'?'4':row.rwlx;
						var apath = basePath
								+ "/project/workorder/Looked?id="
								+ row.xggdid + "&gddlx=" + gddlxForOrder + "";
						
						htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.rwxx))+"' > "
								+ formatUndefine(StringUtil.escapeStr(row.rwxx))
								+ "</td>";
						
						htmlContent = htmlContent
								+ "<td style=\"vertical-align: middle; \" class='text-left'>"
								+ DicAndEnumUtil.getEnumName('planTaskType',
										row.rwlx)
								+ ''
								+ (row.rwzlx=='0'?'':'-'+DicAndEnumUtil.getEnumName('planTaskSubType',
										row.rwzlx)) + "</td>";

						htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'>"
								+ formatUndefine(StringUtil.escapeStr(row.fjzch))+ "</td>";

						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"
								+ formatUndefine(row.jh) + "</td>";
						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"
								+ formatUndefine(row.syStr) + "</td>";
						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-right'>"
								+ formatUndefine(row.sydays) + "</td>";

						
						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" title='"+formatUndefine(StringUtil.escapeStr(row.bz))+"'  class='text-left' >"
								+ formatUndefine(StringUtil.escapeStr(row.bz))
								+ "</td>";
						htmlContent = htmlContent
						+ "<td style=\"vertical-align: middle; \" class='text-center' >"
						+ formatUndefine((row.dysj || '').substring(0,
								10)) + "</td>";
						var displayName = '';
						if (row.dyr != null) {
							displayName = formatUndefine(row.dyr.displayName)
						}
						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"
								+ StringUtil.escapeStr(displayName) + "</td>";
						
						htmlContent = htmlContent
						+ "<td style=\"vertical-align: middle; \">"
						+ DicAndEnumUtil.getEnumName('planTaskState',
								row.zt) + "</td>";
						 
				htmlContent = htmlContent
						+ "<td style=\"vertical-align: middle; \">"
						+ DicAndEnumUtil.getEnumName(
								'planTaskDispalyState', row.xszt)
						+ "</td>";
				
						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \"class='text-left'>"
						+ formatUndefine(StringUtil.escapeStr(row.bjh)) + "</td>";
						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"
						+ formatUndefine(StringUtil.escapeStr(row.xlh)) + "</td>";
						htmlContent = htmlContent
						+ "<td style=\"vertical-align: middle; \" class='text-left'><a class='cursor-pointer'  onClick=\"viewTask('"
								+ apath+ "')\" href='#'  >" + StringUtil.escapeStr(row.rwdh) + "</a></td>";
						
						htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"
						+ formatUndefine(row.dprtname) + "</td>";
						
						htmlContent = htmlContent + "</tr>";

					});
	$("#list").empty();
	$("#list").html(htmlContent);

	var keyword = $.trim($("#keyword").val());
	signByKeyword(keyword, [  3, 5, 13,14,15,16 ])
//	任务相关信息 来源类型 部件号 部件序列号 计划
	
}

//查看revision对应的工卡列表
function goToLinkPage(obj, rid) {
	obj.stopPropagation(); //屏蔽父元素的click事件
	window.location = basePath + "/main/work/listpage?rid=" + rid;
}

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc") >=0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "asc";
	} else {
		orderType = "desc";
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage,orderType,obj);
}

/**
 * 跳转至指定页数:三个参数依次为:当前页码,排序规则 排序字段,
 * @param pageNumber
 * @param sortType
 * @param sortField
 */
function goPage(pageNumber, sortType, sortField) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
}

//搜索条件重置
function searchreset() {
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function() {
		$(this).val("");
	})

	$("#divSearch").find("select").each(function() {
		$(this).val("");
	})
}



/**
 * 实例化附件上传组件
 */ 
var uploadObj = $("#fileuploader")
		.uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showStatusAfterError: false,
					showDelete : false,
					maxFileCount : 10,
					formData : {
						"fileType" : "legacytrouble",
						"wbwjm" : function() {
							return $('#wbwjm').val()
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
					/*statusBarWidth : 150,
					dragdropWidth : 150,*/
					uploadStr:"<div >上传</div><div class=\"font-size-9\">Upload</div>",
					uploadButtonClass: "ajax-file-upload_ext",
					onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
					{
						var trHtml = '<tr  bgcolor="#f9f9f9" id=\''
								+ data.attachments[0].uuid + '\'>';
						trHtml = trHtml + '<td><div>';
						trHtml = trHtml
								+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
								+ data.attachments[0].uuid
								+ '\')" title="删除附件">  ';
						trHtml = trHtml + '</div></td>';
						
						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-20\" title=\"'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\">'
						 + StringUtil.escapeStr(data.attachments[0].wbwjm)
						 + '</td>';

						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-10\">'
								+ data.attachments[0].wjdxStr + '</td>';
						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-13\">'
						+ StringUtil.escapeStr(data.attachments[0].user.displayName)
								+ '</td>';
						trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-center colwidth-13\">'
						+ data.attachments[0].czsj
								+ '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ data.attachments[0].relativePath + '\'/>';

						trHtml = trHtml + '</tr>';
						$('#filelist').append(trHtml);
					}
					
					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm').val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	$('#wbwjm').focus();
				            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
				            	
				            	$('.ajax-file-upload-container').html("");
								uploadObj.selectedFiles -= 1;
								return false;
				            } 
				            else{
				            	return true; 
				            }
						}else{
							return true;
						}
					}

				});

/**
 * 指定结束
 * @param id
 */
function end(event,id,rwdh,rwlx) { 
	event.stopPropagation(); 
	AjaxUtil.ajax({
		  url:basePath+'/productionplan/plantask/alreadyCompleted',
		  type : "post",
		  data : {id:id,idSource:5},
		  dataType : "json",
		  async: false,
		  cache:false,
		  success:function(data) {
			  if(data.alreadyCompleted){
				  AlertUtil.showConfirmMessage("该任务已完工，您确定指定结束吗？",{callback: function(){
						//将工单参数赋值
						$("#alertModalZdjs #id").val(id);    
						$("#alertModalZdjs #zdjsyy").val('');
						if(rwlx=='1'){
							$("#alertModalZdjs #rwdh_label").show();
							$("#alertModalZdjs #gdh_label").hide();
						}
						else{
							$("#alertModalZdjs #rwdh_label").hide();
							$("#alertModalZdjs #gdh_label").show();
						}
						$("#alertModalZdjs #rwdh").val(rwdh);
						$("#alertModalZdjs").modal("show");
					 
					}});
			  }
			  else{
				//将工单参数赋值
				$("#alertModalZdjs #id").val(id);    
				$("#alertModalZdjs #zdjsyy").val('');
				if(rwlx=='1'){
					$("#alertModalZdjs #rwdh_label").show();
					$("#alertModalZdjs #gdh_label").hide();
				}
				else{
					$("#alertModalZdjs #rwdh_label").hide();
					$("#alertModalZdjs #gdh_label").show();
				}
				$("#alertModalZdjs #rwdh").val(rwdh);
				$("#alertModalZdjs").modal("show");
			  }
			  	
		  },
		  error:function(data) {								 
			  AlertUtil.showMessage('检查失败');
			  
		  }
	  }); 
	
	
}

/**
 * 显示查看/修改监控备注界面
 * @param id
 */
function editJkRemark(event,id) {
	event.stopPropagation(); 
	var bz = resultMap[id].bz;
	var rwdh = resultMap[id].rwdh;
	var rwlx = resultMap[id].rwlx;
	 
	$("#remarkModal #id").val(id);    
	$("#remarkModal #bz").val((bz==undefined || bz==null)?'':bz);
	if(rwlx=='1'){
		$("#remarkModal #rwdh_label").show();
		$("#remarkModal #gdh_label").hide();
	}
	else{
		$("#remarkModal #rwdh_label").hide();
		$("#remarkModal #gdh_label").show();
	}
	$("#remarkModal #rwdh").val(rwdh);
	$("#remarkModal").modal("show");
	
}

$("#remarkModal").on("hidden.bs.modal", function() {
	 $("#remarkform").data('bootstrapValidator').destroy();
     $('#remarkform').data('bootstrapValidator', null);
     validationRemark();
});

function validationRemark()
{
	$('#remarkform').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            bz: {
                validators: {
                	notEmpty: {
                        message: '监控备注不能为空'
                    }
		            ,stringLength: {
		                max: 300,
		                message: '监控备注不能超出300个字符'
		            }
                }
            } 
        }
    });
}

function openWindow(url, dataArr, name,method) {    
    var tempForm = document.createElement("form");    
    tempForm.id="tempForm1";    
    tempForm.method=method;    
    tempForm.action=url;    
    tempForm.target=name;    
 
    if(dataArr != undefined && dataArr!=null){
    	var len = dataArr.length;
        if(len >0){
       	 for(var i=0;i<len;i++){
       		 var hideInput = document.createElement("input");    
       	     hideInput.type="hidden";    
       	     hideInput.name= dataArr[i].name  
       	     hideInput.value= dataArr[i].value;  
       	     tempForm.appendChild(hideInput);
       	 }
        }
    }
    
    $(tempForm).on("onsubmit",function(){ 
   	     window.open('about:blank',name,'height="'+window.screen.availHeight+'", width="'+window.screen.availWidth+'", top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
    }); 
    
    document.body.appendChild(tempForm);    
    $(tempForm).trigger("onsubmit");  
    tempForm.submit();  
    document.body.removeChild(tempForm);  
}

function viewTask(url) {
	//window.open(url,'任务单、工单查看','height="'+window.screen.availHeight+'", width="'+window.screen.availWidth+'", top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
	window.open(url);
}
var check_id=[]; /*20170518 修改*/
function checkbox_change(event,index, _this){
	
	event.stopPropagation(); 
	$("#plantask :checkbox[name=chk]:eq("+index+")").attr("checked", $(_this).is(":checked"));
	 /*20170518 修改*/
	/*if($("#plantask :checkbox[name=chk]").is(":checked")){
		
		$("#zyxx_div").css("display","block");
		 $("#plantask_table_div").css("height","auto");
	     $("#plantask_table_div").css("max-height",parseInt(panelContentHeight()*0.7)+"px");
	     $("#zyxx_div").css("max-height",(panelContentHeight()-parseInt($("#plantask_table_div").css("height")))-30+"px")
		
	}else{
		
		$("#zyxx_div").css("display","none");
		$("#plantask_table_div").css("height",panelContentHeight()+"px");
		$("#plantask_table_div").css("max-height","none");
	};*/
	
}


function showzyxx(obj){
	/*var _this,_tr,index;
	var isChecked=false;
	var index=$(obj).index();
	_tr=$(obj).parent("tbody").find("tr");
	for(var i=0;i<_tr.length;i++){
     if(i!=index){
    	 if(_tr.eq(i).find("td").eq(0).find("input").is(":checked")){
    		 isChecked=true; 
    	 }
     }		
	}
	_this=$(obj).find("td").eq(0).find("input");
	_this.attr("checked",!(_this.is(":checked")))
	if(_this.is(":checked") || isChecked){
		if($("#zyxx_div").css("display")=='none'){
		     $("#zyxx_div").css("display","block");
		     $("#plantask_table_div").css("height","auto");
		     $("#plantask_table_div").css("max-height",parseInt(panelContentHeight()*0.7)+"px");
		     $("#zyxx_div").css("max-height",(panelContentHeight()-parseInt($("#plantask_table_div").css("height")))-30+"px")
		 }
	}else{
		$("#zyxx_div").css("display","none");
		$("#plantask_table_div").css("height",panelContentHeight()+"px");
		$("#plantask_table_div").css("max-height","none");
	};*/
}
//全选
function checkAll(){
		$("[name=chk]:checkbox").each(function() { 
			 var index=$(this).index(); /*20170518 修改*/
			 $(this).attr("checked", 'checked'); 
			 check_id.push(index) /*20170518 修改*/
		 });
		 /*20170518 修改*/
		if(check_id.length>0){
			 if($("#zyxx_div").css("display")=='none'){
			     $("#zyxx_div").css("display","block");
			     $("#plantask_table_div").css("height","auto");
			     $("#plantask_table_div").css("max-height",parseInt(panelContentHeight()*0.7)+"px");
			     $("#zyxx_div").css("max-height",(panelContentHeight()-parseInt($("#plantask_table_div").css("height")))-30+"px")
			 }
		}
		
		 
	}
//全不选
function notCheckAll(){
		 $("[name=chk]:checkbox").each(function() { 
			 $(this).removeAttr("checked"); 
		 });
		 /*20170518 修改*/
		 if($("#zyxx_div").css("display")=='block'){
			 $("#zyxx_div").css("display","none");
			 $("#plantask_table_div").css("height",panelContentHeight()+"px");
			 $("#plantask_table_div").css("max-height","none");
		 }
		 
	}


$("#alertModalZdjs").on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     endValidator();
});
function endValidator(){
	 $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	                       
	        	zdjsyy: {
	                validators: {
	                    notEmpty: {
	                        message: '指定结束原因不能为空'
	                    }
				        , 
				        stringLength: {
	                        max: 160,
	                        message: '指定结束原因最大长度不能超过160个字符'
	                    }
	        
	                }
	            }
	        }
	    });
}
