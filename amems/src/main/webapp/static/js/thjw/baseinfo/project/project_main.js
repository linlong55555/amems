//页面初始化
var cycleReg = /^(0|[1-9]\d*)$/;
var timeReg = /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/;
var pageParam = {};
$(function(){
	Navigation(menuCode, '', '', 'Project', '甘清', '2017-09-20', '甘清', '2017-09-21');
	validation();
	logModal.init({
		code : 'D_020' //加载日志
	});
	//初始化时间控件
	initDateWidget();
	
	//initFjjxOption();//初始化飞机机型
	
	initDict();
	
	//formatCycleInput(); //处理文本输入
	
	//添加keyup事件
	bindEvent();
	
	//重置css
	changeResetCss();
	
	loadingUploadButton(); //加载上传控件
	
	goPage(1,"auto","desc");
	
	
});

//格式化循环文本输入框
//function formatCycleInput() {
//	TimeUtil.addTimeValidate("input[name='fxsj']");
//	TimeUtil.addTimeValidate("input[name='init_time_apuh']");
//	TimeUtil.addTimeValidate("input[name='init_time_1eh']");
//	TimeUtil.addTimeValidate("input[name='init_time_2eh']");
//	TimeUtil.addTimeValidate("input[name='init_time_3eh']");
//	TimeUtil.addTimeValidate("input[name='init_time_4eh']");
//}

/**
 * 添加事件
 */
function bindEvent(){
	$("#fxsj").keyup(function(){
		backspace($(this), timeReg, true);
	});
	$("#apu_hv").keyup(function(){
		backspace($(this), timeReg, true);
	});
	$("#e1_hv").keyup(function(){
		backspace($(this), timeReg, true);
	});
	$("#e2_hv").keyup(function(){
		backspace($(this), timeReg, true);
	});
	$("#e3_hv").keyup(function(){
		backspace($(this), timeReg, true);
	});
	$("#e4_hv").keyup(function(){
		backspace($(this), timeReg, true);
	});
}

function backspace(obj, reg, replace){
	var value = obj.val();
	if(replace){
		value = value.replace(/：/g, ":");
	}
	while(!(reg.test(value)) && value.length > 0){
		value = value.substr(0, value.length-1);
    }
	obj.val(value);
}

/**
 * 初始化飞机机型下拉框
 */
function initFjjxOption() {
	$("#fjjx option").remove();
	var jgzzm;
	var operType = $("#materialSave").attr("name");
	if (operType == "edit") { //编辑时取组织机构码
		jgzzm = $("#dprtcode").val();  //当前用户的组织机构码（编辑时取下拉组织机构码）
	 } else {
	    jgzzm = $("#dprtId").val(); //当前用户的组织机构码
	 }
	var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : jgzzm});
	
	if(typeList.length > 0){
		for(var i = 0; i < typeList.length; i++){
			$("#fjjx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
			$("#aircraft_model").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
		}
		$("#fjjx").append("<option value=''>N/A</option>");
		$("#aircraft_model").append("<option value=''>N/A</option>");
	}else{
		$("#fjjx").append("<option value=''>N/A</option>");
		$("#aircraft_model").append("<option value=''>N/A</option>"); //搜索
	}
}

//初始化数据字典内容
function initDict(){
//	$("#xmzl").empty();
//	$("#xmzl").append("<option value=''>请选择项目种类</option>");
	$("#proiect_zl").empty();
	$("#proiect_zl").append("<option value=''>显示全部All</option>");
//	DicAndEnumUtil.registerDic('63', 'xmzl', $("#dprtId").val());
	DicAndEnumUtil.registerDic('63', 'proiect_zl', $("#dprtId").val());
	
//	$("#fjzt").empty();
//	$("#fjzt").append("<option value=''>请选择飞机状态</option>");
//	DicAndEnumUtil.registerDic('62', 'fjzt', $("#dprtId").val());
}

//初始化数据字典内容（编辑）
function initDictEdit(){
	$("#xmzl").empty();
	DicAndEnumUtil.registerDic('63', 'xmzl', $("#dprtcode").val());
	$("#fjzt").empty();
	DicAndEnumUtil.registerDic('62', 'fjzt', $("#dprtcode").val());
}
//初始化数据字典内容(添加)
function initDictAdd(){
	$("#xmzl").empty();
	DicAndEnumUtil.registerDic('63', 'xmzl', $("#dprtId").val());
	$("#fjzt").empty();
	DicAndEnumUtil.registerDic('62', 'fjzt', $("#dprtId").val());
	$("#AddEditAlertModal #fjzch").attr("disabled",false); 
	$("#AddEditAlertModal #fjxlh").attr("disabled",false);
	$("#AddEditAlertModal #fjInfo_div").show();
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}

//收集前端查询参数
function gatherSearchParam() {
	 var searchParam = {};
	 searchParam.dprtcode = $("#dprtcode").val(); //组织机构码
	 searchParam.keyword = $.trim($("#keyword_search").val()); //关键字
	 searchParam.xmzl = $("#proiect_zl").val(); //项目种类
	// searchParam.fjzch = $("#aircraft_no").val().trim(); //飞机注册号
	 //searchParam.fjjx = $("#aircraft_model").val().trim(); //机型
	// var wbbs = $("#cust_category").val();
	// if (wbbs != "") {
	//	 searchParam.wbbs = wbbs;
	//	 if (wbbs == 1) { //外部
	//		 searchParam.khid = $("#cust_id_1").val();
	//	 } else if (wbbs == 0) {
	//		 searchParam.khid = $("#dept_id_1").val();
	//	 }
	// }
	 return searchParam;
}
//查询实现方法
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence) {
	 var searchParam = gatherSearchParam();
		pagination = {page:pageNumber, sort:sortType, order:sequence, rows:20};
		pageParam = pagination; //保留结果值
	    searchParam.pagination = pagination;
	    var id =  $("#project_id").val();
	    if (id !="") {
	    	searchParam.id = id;
	    	$("#project_id").val("");
	    }
	    startWait();
	    AjaxUtil.ajax({
			   url: basePath+"/baseinfo/project/getprojectList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   modal:$("#AddEditAlertModal"),
			   success:function(data){
			   finishWait();
			   if(data.rows!=null && data.rows!="") {
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
		 			signByKeyword($("#keyword_search").val(),[2,3,4,5],"#project_list tr td");
			   } else {
				    $("#project_list").empty();
					$("#pagination").empty();
					$("#project_list").append("<tr><td class='text-center' colspan=\"19\">暂无数据 No data.</td></tr>");
			   }
			   //锁定表头 & 操作列
			    new Sticky({
					tableId : 'project_list_table'
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
		   "<i name='edit' class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='baseinfo:project:main:edit' " +
		   "  onClick=edit('"+ row.id +"') title='修改 Edit'></i>&nbsp;&nbsp;" +
			"<i  name='remove' class='icon-trash  color-blue cursor-pointer checkPermission' " +
			"permissioncode='baseinfo:project:main:del'  onClick=del('"+ row.id +"') title='删除Delete'></i>"
		//   	"<i  name='detail' class='icon-trash  color-blue cursor-pointer checkPermission' " +
		//   	"permissioncode='baseinfo:project:main:show'  onClick=showProject('"+ row.id +"') title='详细Detail'></i>"
		  +"</td>"; 
		
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xmbm))+"'>" +
	    		"<a href='javascript:void(0)' onclick='showProject(\""+row.id+"\")'>"+StringUtil.escapeStr(formatUndefine(row.xmbm))+"</a></td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xmmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.xmmc))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.khbm))+"'>"+StringUtil.escapeStr(formatUndefine(row.khbm))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fjzch))+"'>"+StringUtil.escapeStr(formatUndefine(row.fjzch))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fjxlh))+"'>"+StringUtil.escapeStr(formatUndefine(row.fjxlh))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fjbzm))+"'>"+StringUtil.escapeStr(formatUndefine(row.fjbzm))+"</td>";
	  //  htmlContent += "<td class='text-left'>飞机描述</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xmzl))+"'>"+StringUtil.escapeStr(formatUndefine(row.xmzl))+"</td>";
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.jhksrqstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.jhksrqstr))+"</td>";
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.jhjsrqstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.jhjsrqstr))+"</td>";
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.sjksrqstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.sjksrqstr))+"</td>";
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.sjjsrqstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.sjjsrqstr))+"</td>"; 
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xsddh))+"'>"+StringUtil.escapeStr(formatUndefine(row.xsddh))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fstk))+"'>"+StringUtil.escapeStr(formatUndefine(row.fstk))+"</td>";
	    htmlContent += "<td class='text-center' title='附件 Attachment'>";
	    if (row.attachCount != null && row.attachCount > 0) {
	        htmlContent += "<i qtid='"+row.id+"'"
	    		+' class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"></i>';
	    }
	    htmlContent +="</td>";
	   // htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.ztstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.ztstr))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.whr))+"'>"+StringUtil.escapeStr(formatUndefine(row.whr))+"</td>"; 
	    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.whsjstr))+"'>"+StringUtil.escapeStr(formatUndefine(row.whsjstr))+"</td>"; 
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";
	    htmlContent += "</tr>";	
 });
	$("#project_list").empty();
	$("#project_list").html(htmlContent);
	initWebuiPopover();
	refreshPermission(); 
}
//附件展示面板
function initWebuiPopover() {
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#project_main_table_top_div").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){
	var jsonData = [
	   	         {mainid : $(obj).attr('qtid'), type : '合同附件'}
	   	    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}

//上传
function loadingUploadButton() {
	$("#uploadarea").find(".uploaderDiv").uploadFile({
		url:basePath+"/common/ajaxUploadFile",
		multiple:true,
		dragDrop:false,
		showStatusAfterSuccess: false,
		showDelete: false,
		
		maxFileCount:100,
		formData: {
			"fileType":"project"
		},//参考FileType枚举（技术文件类型）
		fileName: "myfile",
		returnType: "json",
		removeAfterUpload:true,
		showStatusAfterSuccess : false,
		showStatusAfterError: false,
		/*<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>*/
		uploadStr:"<i class='fa fa-upload'></i>",
		uploadButtonClass: "btn btn-default",
		onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
		{
			var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\'tr_'+data.attachments[0].uuid+'\'>';
			trHtml = trHtml+'<td class="text-center"><div>';
//				 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
			trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="Delete 删除">  ';
			trHtml = trHtml+'</div></td>';
			//trHtml = trHtml+'<td>'+data.attachments[0].yswjm+'</td>';
			//trHtml = trHtml+'<td>'+data.attachments[0].nbwjm+'</td>';
			trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wbwjm) + "." + data.attachments[0].hzm
			+"<input type='hidden' value='"+data.attachments[0].nbwjm+"'></td>";
			
			trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wjdxStr)
			+"<input type='hidden' value='"+data.attachments[0].wjdx+"'></td>";
			trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
			trHtml = trHtml+"<td class='text-center'>"+data.attachments[0].czsj
			+"<input type=\"hidden\" name=\"relativePath\" value='"+data.attachments[0].cflj+"'/>" 
			+"<input type='hidden' name='hzm' value='"+data.attachments[0].hzm+"'/>"
			+"<input type='hidden' name='bs' value='00'/></td>"; //00标识新增
			trHtml = trHtml+'</tr>';
			$("#noAttachment_x").remove(); //移除暂无合同提示
			$('#filelist').append(trHtml);
		}
	});
}

/**
* 删除附件
* @param newFileName
*/
function delfile(uuid) {
	/*var  responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	var fileArray = [];
	var waitDelArray = [];
	if(len > 0 ) {
		for(var i =0;i<len;i++){
			if(responses[i].attachments[0].uuid != uuid){
				fileArray.push(responses[i]);
			}
		}
		uploadObj.responses = fileArray;
		uploadObj.selectedFiles -= 1;
		$('#'+uuid).remove();
	}*/
	if(uuid!=null && uuid!=""){
		$('#tr_'+uuid).remove();
	}
	if($('#filelist').find("tr").length < 1){
		$("#filelist").append('<tr id="noAttachment_x"><td colspan="5" class="text-center">暂无数据 No data.</td></tr>');
	}
}

//附件下载
function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

//获得所有上传的合同附件
function getWoJobenclosureParenm(){
	 var woJobenclosure=[];
	  /*var responses = uploadObj.responses;
	  var len = uploadObj.responses.length;
	  if(len != undefined && len>0){
		  for(var i =0;i<len;i++){
			  woJobenclosure.push({
						'yswjm':responses[i].attachments[0].yswjm
						,'wbwjm':responses[i].attachments[0].wbwjm
						,'nbwjm':responses[i].attachments[0].nbwjm
						,'cflj':responses[i].attachments[0].cflj
						,'wjdx':responses[i].attachments[0].wjdx
						,'hzm':responses[i].attachments[0].hzm
					});
			}
	  }*/
	 var len = $("#filelist").find("tr").find("td").length;
	 if (len < 2) {
		 return woJobenclosure;
	 }
	 $("#filelist").find("tr").each(function() {
		 var obj ={};
		 obj.id = $(this).attr('id').split("_")[1];
		 obj.yswjm = $(this).find("td:eq(1)").text();
		 obj.wbwjm = $(this).find("td:eq(1)").text();
		 obj.nbwjm = $(this).find("td:eq(1)").find("input:eq(0)").val();
		 obj.wjdx = $(this).find("td:eq(2)").find("input:eq(0)").val();
		 if (obj.wjdx == null || obj.wjdx == "null") {
			 obj.wjdx = 0;
		 }
		 obj.cflj = $(this).find("td:eq(4)").find("input:eq(0)").val();
		 obj.hzm = $(this).find("td:eq(4)").find("input:eq(1)").val();
		 obj.biaoshi = $(this).find("td:eq(4)").find("input:eq(2)").val();
		 woJobenclosure.push(obj);
	 });
	 return woJobenclosure;
}


/**
 * 初始化时间控件
 */
function initDateWidget(){
	// $(".date-picker").datepicker({
	//		format:'yyyy-mm-dd',
	//		autoclose : true,
	//		clearBtn : true
	// });
	$('.date-picker').datepicker({
		autoclose : true,
		clearBtn:true
	}).on('hide', function(e) {
		var name = $(this).attr("id");
		  $('#form_project').data('bootstrapValidator')  
	        .updateStatus(name, 'NOT_VALIDATED',null)  
	        .validateField(name);  
	  });
}
//------------------------客户部分 START-------------------------------------------------
//客户信息搜索
function searchCustomer() {
	AjaxGetCustomerDatasWithSearch(1, "auto", "desc");
}

//打开客户弹窗[ajax加载客户信息]
function openCustomerWin(backType) {
	modalEmpty();
	if (backType == 0) {
		$("#appendCustomer_id").attr("name","search");
	} else if (backType == 1) {
		$("#appendCustomer_id").attr("name","add");
	}
	var operType = $("#materialSave").attr("name");
	//if(operType == "edit") {
	//	return false; //客户信息不允许修改
	//}
	AjaxGetCustomerDatasWithSearch(1, "auto", "desc");
	$("#customer_choose").modal("show");
}
//获得客户信息
function AjaxGetCustomerDatasWithSearch(pageNumber,sortType,sequence) {
	   var searchParam = {};
	   var operType = $("#materialSave").attr("name");
	   if (operType == "edit") { //编辑时取组织机构码
		   searchParam.dprtcode = $("#dprtcode").val(); //当前用户的组织机构码（编辑时取下拉组织机构码）
	   } else {
		  searchParam.dprtcode = $("#dprtId").val(); //当前用户的组织机构码
	   }
	    searchParam.keyword = $("#keyword_user_search").val().trim();
		pagination = {page:pageNumber, sort:sortType, order:sequence, rows:10};
	    searchParam.pagination = pagination;
	    startWait();
	    AjaxUtil.ajax({
			   url: basePath+"/baseinfo/customer/getcustomerList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   modal:$("#AddEditAlertModal"),
			   success:function(data){
			   finishWait();
			   if(data.rows!=null && data.rows!=""){
				    appendCustContentHtml(data.rows);
				    new Pagination({
						renderTo : "custpagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetCustomerDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
				    FormatUtil.removeNullOrUndefined();
			   } else {
				    $("#customer_choose_list").empty();
					$("#custpagination").empty();
					$("#customer_choose_list").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
			   }
	 		}
	     }); 
}
//回写客户信息
function appendCustomer(){
	$("#customer_choose_list").find("tr").each(function(){
		var obj = $(this).find("td:eq(0)").find("input:eq(0)");
		if (obj.attr("checked") =="checked") {
			var btn_name = $("#appendCustomer_id").attr("name");
			if (btn_name =="add") {
				$("#cust_no").val($(this).find("td:eq(1)").text());
				$("#cust_id").val(obj.val());
				$("#cust_name").val($(this).find("td:eq(2)").text());
			} else {
				$("#cust_no_1").val($(this).find("td:eq(1)").text());
				$("#cust_id_1").val(obj.val());
			}
			$('#form_project').data('bootstrapValidator')
			.updateStatus('cust_no', 'NOT_VALIDATED', null).validateField('cust_no');  //移除校验
		}
	});
}


//拼接客户信息
function appendCustContentHtml(list) {
	var htmlContent = '';
	var cust_id = $("#cust_id").val();
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr  bgcolor=\"#f9f9f9\" onclick='selectTrCust(this)'>";
		} else {
			htmlContent += "<tr  bgcolor=\"#fefefe\" onclick='selectTrCust(this)'>";
		}
		if (cust_id != "" && cust_id == row.id) {
			htmlContent = htmlContent + "<td class='text-center'><input type='radio' name='custradio' value='"+row.id+"' checked='checked'/></td>"; 
		} else {
			htmlContent = htmlContent + "<td class='text-center'><input type='radio' name='custradio' value='"+row.id+"'/></td>"; 
		}
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.khbm))+"'>"+StringUtil.escapeStr(formatUndefine(row.khbm))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.khmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.khmc))+"</td>";
	    htmlContent += "</tr>";	
 });
	$("#customer_choose_list").empty();
	$("#customer_choose_list").html(htmlContent);
}

//双击行选择
function selectTrCust(obj){
	$(obj).find("td:eq(0)").find("input[type='radio']").attr('checked',true);
}

//------------------------客户部分 END-------------------------------------------------

//------------------------部门部分 START-------------------------------------------------------------
function searchDeptment() {
	AjaxGetDeptmentDatasWithSearch(1, "auto", "desc");
}

function openDeptWin(backType) {
	if (backType == 0) {
		$("#appendDept_id").attr("name","search");
	} else if (backType == 1) {
		$("#appendDept_id").attr("name","add");
	}
	modalEmpty();
	AjaxGetDeptmentDatasWithSearch(1, "auto", "desc");
	$("#dept_choose").modal("show");
}

//获得部门信息
function AjaxGetDeptmentDatasWithSearch(pageNumber,sortType,sequence) {
	   var searchParam = {};
	    searchParam.dprtcode = $("#dprtId").val(); //当前用户的组织机构码
	    searchParam.keyword = $("#keyword_dept_search").val().trim();
	    searchParam.dprttype = 2;// 获得部门
		pagination = {page:pageNumber, sort:sortType, order:sequence, rows:10};
	    searchParam.pagination = pagination;
	    startWait();
	    AjaxUtil.ajax({
			   url: basePath+"/sys/department/queryDept",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   modal:$("#AddEditAlertModal"),
			   success:function(data){
			   finishWait();
			   if(data.rows!=null && data.rows!=""){
				    appendDeptContentHtml(data.rows);
				    new Pagination({
						renderTo : "deptpagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDeptmentDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
				    FormatUtil.removeNullOrUndefined();
			   } else {
				    $("#deptment_choose_list").empty();
					$("#deptpagination").empty();
					$("#deptment_choose_list").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
			   }
	 		}
	     }); 
}

//拼接部门信息
function appendDeptContentHtml(list) {
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			htmlContent += "<tr  bgcolor=\"#f9f9f9\" >";
		} else {
			htmlContent += "<tr  bgcolor=\"#fefefe\">";
		}
		htmlContent = htmlContent + "<td class='text-center'><input type='radio' name='deptradio' value='"+row.id+"'/></td>"; 
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dprtcode))+"'>"+StringUtil.escapeStr(formatUndefine(row.dprtcode))+"</td>";
	    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";
	    htmlContent += "</tr>";	
    });
	$("#deptment_choose_list").empty();
	$("#deptment_choose_list").html(htmlContent);
}

//回写客户信息
function appendDept(){
	$("#deptment_choose_list").find("tr").each(function(){
		var obj = $(this).find("td:eq(0)").find("input:eq(0)");
		if (obj.attr("checked") =="checked") {
			var btn_name = $("#appendDept_id").attr("name");
			if (btn_name == "add") {
				$("#dept_no").val($(this).find("td:eq(1)").text());
				$("#dept_id").val(obj.val());
				$("#dept_name").val($(this).find("td:eq(2)").text());
			} else {
				$("#dept_no_1").val($(this).find("td:eq(1)").text());
				$("#dept_id_1").val(obj.val());
			}
		}
	});
}
//------------------------部门部分 END-------------------------------------------------------------



//modal添加弹窗
function add() {
	//移除验证
	 $("#form_project").data('bootstrapValidator').destroy();
	 $('#form_project').data('bootstrapValidator', null);
	 validation();
	 
	 initDictAdd();
	 openWindowResetCss();
	 $("#dept_edit_20180115").css("display","none"); 
	 $("#dept_add_20180115").css("display","block"); 
	 
	 $("#cust_edit_20180115").css("display","none"); 
	 $("#cust_add_20180115").css("display","block");
	 
	 $('#xmbm').removeAttr("disabled");  //项目编号
	 $("#xmzl").removeAttr("disabled");  //项目种类
	 $("input[type='radio'][name='pro_fl']").removeAttr("disabled"); //客户类型
	 $("#fjzch").removeAttr("disabled");  //飞机注册号
	 $("#hbh").removeAttr("disabled");  //航班号
	 $("#fjbzm").removeAttr("disabled");  //飞机描述（飞机备注名）
	 
	 $("#materialHeadChina").html("新增项目");
	 $("#materialHeadEnglish").html("Add Project");
	 $("#materialSave").attr("name","add");	
	 modalEmpty();
	 cleanInputContent(); //清除内容
	 
	 setFaultValue(); //设置默认选中
	 //-------------客户种类 默认选中内部 START------------
	 $("input[type='radio'][value='0'][name='pro_fl']").attr('checked',true);
	 $("#customer_No_1").css("display","none");
	 $("#customer_Name_1").css("display","none");
	 $("#deptment_Name_1").css("display","block");
	 $("#departmentModalInput").css("display","block");
	//-------------客户种类 默认选中内部 END------------
	 
	 $("#AddEditAlertModal").modal("show");
	 $("#filelist").empty(); //附件列表
	 $("#filelist").append('<tr id="noAttachment_x"><td colspan="5" class="text-center">暂无数据 No data.</td></tr>');
	 //加载机型
	 initFjjxOption();
}

//modal编辑 加载项目信息弹窗
function edit(id) {
	//移除验证
	 $("#form_project").data('bootstrapValidator').destroy();
	 $('#form_project').data('bootstrapValidator', null);
	 validation();
	 
	 initDictEdit(); //初始化字典数据
	 openWindowResetCss();//重置css样式
	// $("#dept_edit_20180115").css("display","block"); 
	 //$("#dept_add_20180115").css("display","none"); 
	 
	// $("#cust_edit_20180115").css("display","block"); 
	// $("#cust_add_20180115").css("display","none");
	 
	 showProjectRestore(); //恢复原状
	 
	  $("#xmbm").attr("disabled","disabled");  //项目编号
	// $("#xmzl").attr("disabled","disabled");  //项目种类
	// $("input[type='radio'][name='pro_fl']").attr("disabled","disabled"); //客户类型
	// $("#fjzch").attr("disabled","disabled");  //飞机注册号
	 //$("#hbh").attr("disabled","disabled");  //航班号
	// $("#fjbzm").attr("disabled","disabled");  //飞机描述（飞机备注名）
	 
	 $("#materialHeadChina").html("项目编辑");
	 $("#materialHeadEnglish").html("Edit Project");
	 $("#materialSave").attr("name","edit");	
	 modalEmpty();
	 cleanInputContent();
	 //加载机型
	 initFjjxOption();
	 
	 var object={};
		 object.id = id;
	 
	 startWait($("#AddEditAlertModal"));
	 AjaxUtil.ajax({
			url: basePath+"/baseinfo/project/getprojectbyid",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(object),
			dataType:"json",
			modal:$("#AddEditAlertModal"),
			success:function(massge){
				finishWait($("#AddEditAlertModal"));
				//赋值操作
				reproduceDatas(massge);
			}
		});
	 $("#AddEditAlertModal").modal("show");
	 //检查是否有合同附件
	 if ($("#filelist").find("tr").length < 1) {
		 $("#filelist").append('<tr id="noAttachment_x"><td colspan="5" class="text-center">暂无数据 No data.</td></tr>');
	 }
}

//编辑项目赋值方法
function reproduceDatas(massge) {
	
	$("#project_id").val(massge.project.id);
	$("#xmbm").val(massge.project.xmbm);
	$("#xmmc").val(massge.project.xmmc);
	$("#xmzl").find("option[value='"+massge.project.xmzl+"']").attr("selected",true);
	if (massge.project.wbbs == 1) {
		//客户信息 
		$("#cust_no").val(massge.project.khbm);
		$("#cust_name").val(massge.project.khmc);
		$("#cust_edit_no").val(massge.project.khbm);
		$("#cust_id").val(massge.project.khid);
		
		$("input[type='radio'][value='1'][name='pro_fl']").attr('checked',true);
		$("#customer_No_1").css("display","block");
		$("#customer_Name_1").css("display","block");
		$("#deptment_Name_1").css("display","none");
		$("#departmentModalInput").css("display","none");
	} else { //部门编号
		$("#dept_no").val(massge.project.khbm);
		$("#dept_name").val(massge.project.khmc);
		$("#dept_edit_no").val(massge.project.khbm);
		$("#dept_id").val(massge.project.khid);
		
		$("input[type='radio'][value='0'][name='pro_fl']").attr('checked',true);
		
		$("#customer_No_1").css("display","none");
		$("#customer_Name_1").css("display","none");
		$("#deptment_Name_1").css("display","block");
		$("#departmentModalInput").css("display","block");
	}
	console.info(massge.project);
	$("#fjzch").val(massge.project.fjzch);
	$("#fjxlh").val(massge.project.fjxlh);
	//$("#fjjx").val(massge.project.fjjx); //飞机机型
	$("#fjjx").find("option[value='"+StringUtil.escapeStr(massge.project.fjjx)+"']").attr("selected",true); //飞机状态
	changeFjjx();
	$("#fjbzm").val(massge.project.fjbzm); //飞机备注名
	$("#hbh").val(massge.project.hbh); //航班号
	$("#fjxlh").val(massge.project.fjxlh);
	$("#jh_ksrq").val(massge.project.jhksrqstr);
	$("#jh_jsrq").val(massge.project.jhjsrqstr);
	$("#sj_ksrq").val(massge.project.sjksrqstr);
	$("#sj_jsrq").val(massge.project.sjjsrqstr);
	
	//飞机飞行小时
	$("#fxsj").val(massge.project.fxsjstr);
	
	$("#fxxh").val(massge.project.fxxh);
	$("#ipcyxxh").val(massge.project.ipcyxxh);
	$("#rhyzph").val(massge.project.rhyzph);
	$("#yyyph").val(massge.project.yyyph);
	$("#yyyph").val(massge.project.yyyph);
	$("#fjzt").find("option[value='"+massge.project.fjzt+"']").attr("selected",true); //飞机状态
	
	// engine & apu
	//alert(JSON.stringify(massge.project.projectMainAreas));
	if (massge.project.projectMainAreas != null) {
		$.each(massge.project.projectMainAreas, function(i, obj) {
			switch(obj.wz) {
			case 31:   //APU
				$("#apu_jh").val(obj.jh);
				$("#apu_xh").val(obj.xh);
				$("#apu_xlh").val(obj.xlh);
				if (obj.jklbh=="2_20_AH" && obj.jkflbh=="2T") {
					$("#apu_id_h").val(obj.id);
					$("#apu_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_20_AC" && obj.jkflbh=="3C") {
					$("#apu_id_c").val(obj.id);
					$("#apu_cv").val(obj.sjz);
				}
				break;
			case 21:  //1#
				$("#e1_jh").val(obj.jh);
				$("#e1_xh").val(obj.xh);
				$("#e1_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e1_id_h").val(obj.id);
					$("#e1_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e1_id_c").val(obj.id);
					$("#e1_hc").val(obj.sjz);
				}
				break;
			case 22:  //2#
				$("#e2_jh").val(obj.jh);
				$("#e2_xh").val(obj.xh);
				$("#e2_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e2_id_h").val(obj.id);
					$("#e2_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e2_id_c").val(obj.id);
					$("#e2_hc").val(obj.sjz);
				}
				break;
			case 23:  //3#
				$("#e3_jh").val(obj.jh);
				$("#e3_xh").val(obj.xh);
				$("#e3_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e3_id_h").val(obj.id);
					$("#e3_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e3_id_c").val(obj.id);
					$("#e3_hc").val(obj.sjz);
				}
				break;
			case 24:  //4#
				$("#e4_jh").val(obj.jh);
				$("#e4_xh").val(obj.xh);
				$("#e4_xlh").val(obj.xlh);
				if (obj.jklbh=="2_30_EH" && obj.jkflbh=="2T") {
					$("#e4_id_h").val(obj.id);
					$("#e4_hv").val(obj.sjzstr);
				} else if (obj.jklbh=="3_30_EC" && obj.jkflbh=="3C") {
					$("#e4_id_c").val(obj.id);
					$("#e4_hc").val(obj.sjz);
				}
				break;
			}
		});
	}
	
	$("#xsddh").val(massge.project.xsddh);
	$("#fstk").val(massge.project.fstk);
	$("#xmjl").val(massge.project.xmjl);
	$("#jhzk").val(massge.project.jhzk);
	$("#kzs").val(massge.project.kzs);
	$("#qsgs").val(massge.project.qsgs);
	$("#kzsdh").val(massge.project.kzsdh);
	//获得附件信息
	if (massge.project.attachments != null) {
		var trHtml = "";
		$.each(massge.project.attachments,function(i, att){
			trHtml = trHtml + '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\'tr_'+att.id+'\'>';
			trHtml = trHtml+'<td class="text-center"><div>';
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
			trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+att.id+ '\')" title="Delete 删除"> ';
			trHtml = trHtml+'</div></td>';
			//trHtml = trHtml+'<td>'+data.attachments[0].yswjm+'</td>';
			//trHtml = trHtml+'<td>'+data.attachments[0].nbwjm+'</td>';
			trHtml = trHtml+"<td class='text-left'><a onclick=\"downloadfile('"+att.id+"')\">"+StringUtil.escapeStr(att.wbwjm)+"</a><input type='hidden' value='"+att.nbwjm+"'></td>";
			
			trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(att.wjdxStr)+"<input type='hidden' value='"+att.wjdx+"'></td>";
			//trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
			trHtml = trHtml+"<td class='text-left'>"+att.czrname+"</td>";
			trHtml = trHtml+"<td class='text-center'>"+att.czsj
			+'<input type="hidden" name="relativePath" value=\''+att.cflj+'\'/>'
			+'<input type="hidden" name="hzm" value=\''+att.hzm+'\'/>'
			+'<input type="hidden" name="bs" value="11"/></td>'; //11，标识原始数据
			// trHtml = trHtml+"<td class='text-left'>"+att.cflj+"</td>";
			
			trHtml = trHtml+'</tr>';
		});
		$("#noAttachment_x").remove(); //移除暂无合同提示
		$("#filelist").append(trHtml);
	} else {
		if ($("#filelist").find("tr").length < 1) {
			 $("#filelist").append('<tr id="noAttachment_x"><td colspan="5" class="text-center">暂无数据 No data.</td></tr>');
		 }
	}
	
	//格式化循环值文本框
	//formatCycleInput();
}


//新增或更新stage
function saveProject(){
	var operation = $("#materialSave").attr("name");
	if (operation == "add") { //新增操作
		addProjectObject();
	} else if (operation == "edit"){ //编辑
		editObject();
	}
}

//保存编辑的信息
function editObject() {
	 $('#form_project').data('bootstrapValidator').validate();
	 if(!$('#form_project').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
		return false;
	 }
	 var oxt = checkDatBeforeSubmit();
	 if (!oxt.f) {
		 AlertUtil.showModalFooterMessage("时间和循环必须为数字!");
	     return false; 
	 }
	  var obj = gatherSubmitParam();
	  startWait($("#AddEditAlertModal"));
	  AjaxUtil.ajax({
			url: basePath+"/baseinfo/project/updateproject",
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
	validatorForm =  $('#form_project').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
         fields: {
        	/*xmbm: {
	                validators: {
	                	notEmpty: {
	                        message: '项目编号不能为空'
	                    }
	                }
	            },*/
	         xmmc: {
	            validators: {
	            	notEmpty: {
	                    message: '项目名称不能为空'
	                }
	            }
	        },
	        cust_no: {
	            validators: {
	            	callback: {
	            		message: '客户编号不能为空',
	            		callback: function() {
	            			if($("#customer_No_1").css("display") == 'block' ){
	            				if ($("#cust_no").val() =='') {
	            					return false;
	            				}
	            			}
	            			return true;
	            		}
	            	}
	            }
		      },
		      qsgs: {
		            validators: {
		            	callback: {
		            		message: '起算工时必须为数字',
		            		callback: function() {
		            			var cap = $.trim($("#qsgs").val());
		            			var reg = /^[1-9]\d*(\.\d+)?$/;
		            			if (cap != "") {
		            				if(!reg.test(cap)) {
		            					return false;
		            				}
		            			}
		            			return true;
		            		}
		            	}
		            }
			      },
			      jh_ksrq: {  //计划开始
			    	  validators: {
			    		  callback: {
			    			  message: '计划进场时间必须小于或等于计划出场时间',
			    			  callback: function(value, validator) {
			    				 validator.revalidateField($("#jh_jsrq"));
			    				  var jh_ksrq = $("#jh_ksrq").val();
			    				  var jh_jsrq = $("#jh_jsrq").val();
			    				  if (jh_ksrq != "" && jh_jsrq != "") {
			    					   var jhks = parseInt(jh_ksrq.replace(/-/g, ''), 10);
			    					   var jhjs = parseInt(jh_jsrq.replace(/-/g, ''), 10);
			    					   if (jhks >  jhjs) {
			    				   	      return false;
			    					    }
			    				  } 
			    				  return true;
			    			  }
			    		  }
			    	  }
			      },
			      jh_jsrq: {  //计划结束
			    	  validators: {
				    		  callback: {
					    			  message: '计划出场时间必须大于或等于计划进场时间',
					    			  callback: function(value, validator) {
					    				  var jh_ksrq = $("#jh_ksrq").val();
					    				  var jh_jsrq = $("#jh_jsrq").val();
					    				  if (jh_ksrq != "" && jh_jsrq != "") {
					    					  var jhks = parseInt(jh_ksrq.replace(/-/g, ''), 10);
					    					  var jhjs = parseInt(jh_jsrq.replace(/-/g, ''), 10);
					    					  if (jhjs < jhks) {
					    						  return false;
					    					  }
					    				  }
					    				  validator.updateStatus('jh_ksrq', 'VALID', 'callback'); 
					    				  return true;
					    			  }
				    		  }
			    	  }
			      },
			     sj_ksrq: {  //实际开始时间
			    	 validators: {
			    		  callback: {
			    			  message: '实际进场时间必须小于或等于实际出场时间',
			    			  callback: function(value, validator) {
			    				 validator.revalidateField($("#sj_jsrq"));
			    				  var sj_ksrq = $("#sj_ksrq").val();
			    				  var sj_jsrq = $("#sj_jsrq").val();
			    				  if (sj_ksrq != "" && sj_jsrq != "") {
			    					   var sjks = parseInt(sj_ksrq.replace(/-/g, ''), 10);
			    					   var sjjs = parseInt(sj_jsrq.replace(/-/g, ''), 10);
			    					   if (sjks > sjjs) {
			    				   	      return false;
			    					   }
			    				  } 
			    				  return true;
			    			  }
			    		  }
		    	     }
			     },
			    sj_jsrq:{
			    	validators: {
			    		  callback: {
				    			  message: '实际出场时间必须大于或等于实际进场时间',
				    			  callback: function(value, validator) {
				    				  var sj_ksrq = $("#sj_ksrq").val();
				    				  var sj_jsrq = $("#sj_jsrq").val();
				    				  if (sj_ksrq != "" && sj_jsrq != "") {
				    					  var sjks = parseInt(sj_ksrq.replace(/-/g, ''), 10);
				    					  var sjjs = parseInt(sj_jsrq.replace(/-/g, ''), 10);
				    					  if (sjks > sjjs) {
				    						  return false;
				    					  }
				    				  }
				    				  validator.updateStatus('sj_ksrq', 'VALID', 'callback'); 
				    				  return true;
				    			  }
			    		  }
		    	     }
			    },
		      /*fjzch: {
		            validators: {
		            	notEmpty: {
		                    message: '飞机注册号不能为空'
		                }
		            }
			      },*/
			  /*   fjjx: {
			    	  validators: {
			    		  notEmpty: {
			    			  message: '飞机机型不能为空'
			    		  }
			    	  }
			      }, */
			  /*fjxlh: {
			            validators: {
			            	notEmpty: {
			                    message: '飞机序列号不能为空'
			                }
			            }
				      },*/
			 dept_no: {
		            validators: {
		            	callback: {
		            		message: '部门编号不能为空',
		            		callback: function() {
		            			if($("#departmentModalInput").css("display") == 'block' ){
		            				if ($("#dept_no").val() =='') {
		            					return false;
		            				}
		            			}
		            			return true;
		            		}
		            	}
		             }
			      },
			      fxsj: {
			            validators: {
			            	callback: {
			            		message: '飞机飞行小时必须为数字',
			            		callback: function() {
			            			var fxsj = $.trim($("#fxsj").val());
			            			var reg = /^[1-9]\d*(\.\d+)?$/;
			            			if (fxsj != "") {
			            				if(!reg.test(fxsj)) {
			            					return false;
			            				}
			            			}
			            			return true;
			            		}
			            	}
			            }
				    },
				    fxxh: {
			            validators: {
			            	callback: {
			            		message: '飞机飞行循环必须为数字',
			            		callback: function() {
			            			var fxxh = $.trim($("#fxxh").val());
			            			var reg = /^[1-9]\d*(\.\d+)?$/;
			            			if (fxxh != "") {
			            				if(!reg.test(fxxh)) {
			            					return false;
			            				}
			            			}
			            			return true;
			            		}
			            	}
			            }
				    }
         }
    });
}

//集中处理数据
function gatherSubmitParam() {
	var obj = {};
	obj.id = $("#project_id").val();
	obj.dprtcode = $.trim($("#dprtId").val()); //jsp页面组织机构码
	obj.xmbm = $.trim($("#xmbm").val());  //项目编号
	obj.xmmc = $.trim($("#xmmc").val());  //项目名称
	obj.xmzl = $.trim($("#xmzl").val());  //项目种类
	
	var wbflbs = $("input[type='radio'][name='pro_fl']:checked").val();
	if (wbflbs == 0) { //内部，取部门
		obj.wbbs = 0;
		obj.khid = $.trim($("#dept_id").val());  //部门编号
		obj.khbm = $.trim($("#dept_no").val());  //部门编码
	} else { //外部取客户
		obj.wbbs = 1;
		obj.khid = $.trim($("#cust_id").val());  //客户编号
		obj.khbm = $.trim($("#cust_no").val());  //客户编码
	}
	obj.fjzch = $.trim($("#fjzch").val());   //飞机注册号
	obj.fjxlh = $.trim($("#fjxlh").val());  //飞机序列号
	obj.hbh = $.trim($("#hbh").val());  //航班号
	obj.fjjx = $.trim($("#fjjx").val());  //飞机机型  
	obj.fjbzm = $.trim($("#fjbzm").val());  //飞机描述【对应飞机备注名】 
	obj.jhksrqstr = $.trim($("#jh_ksrq").val());  //计划开始
	obj.jhjsrqstr = $.trim($("#jh_jsrq").val());  //计划结束
	obj.sjksrqstr = $.trim($("#sj_ksrq").val());  //实际开始
	obj.sjjsrqstr = $.trim($("#sj_jsrq").val());  //实际结束
	
	obj.fxsjstr = $.trim($("#fxsj").val());  //飞行小时
	obj.fxxh = $.trim($("#fxxh").val());  //飞行循环
	obj.ipcyxxh = $.trim($("#ipcyxxh").val());  //ipc有效性
	obj.rhyzph = $.trim($("#rhyzph").val());  //滑油牌号
	obj.yyyph = $.trim($("#yyyph").val());  //液压油牌号
	obj.fjzt = $.trim($("#fjzt").val());  //飞机状态
	
	var projectMainAreas = []; //存放发动机信息
	$("#engine_list_info").find("tr").each(function(i){
		var o1 = {};
		var id_h = $(this).find("td:eq(0)").find("input:eq(0)").val();
		var wz = $(this).find("td:eq(0)").find("input:eq(1)").val(); //位置
		var jh = $(this).find("td:eq(1)").find("input:eq(0)").val(); //件号
		var xh = $(this).find("td:eq(2)").find("input:eq(0)").val(); //型号
		var xlh = $(this).find("td:eq(3)").find("input:eq(0)").val(); //序号
		var sjz_1 = $(this).find("td:eq(4)").find("input:eq(0)").val(); //数据值
		var jklbh_1 =  $(this).find("td:eq(4)").find("input:eq(1)").val(); // 监控类编号
		var jkflbh_1 = $(this).find("td:eq(4)").find("input:eq(2)").val(); //监控分类编号
		o1.id = id_h;  //时间ID
		o1.wz = wz;
		o1.jh = jh;
		o1.xh = xh;
		o1.xlh = xlh;
		o1.jklbh = jklbh_1;
		o1.jkflbh = jkflbh_1;
		o1.sjzstr = sjz_1;
		
		var o2 = {};
		var id_c = $(this).find("td:eq(0)").find("input:eq(2)").val(); //循环ID
		var sjz_2 = $(this).find("td:eq(5)").find("input:eq(0)").val(); //发动机飞行循环
		var jklbh_2 =  $(this).find("td:eq(5)").find("input:eq(1)").val(); // 监控类编号
		var jkflbh_2 = $(this).find("td:eq(5)").find("input:eq(2)").val(); //监控分类编号
		
		o2.id = id_c;
		o2.wz = wz;
		o2.jh = jh;
		o2.xh = xh;
		o2.xlh = xlh;
		o2.jklbh = jklbh_2;
		o2.jkflbh = jkflbh_2;
		o2.sjzstr = sjz_2;  //记录数值
		
		projectMainAreas.push(o1);
		projectMainAreas.push(o2);
		
	});
	obj.projectMainAreas = projectMainAreas;  //引擎信息
	
	obj.xsddh = $.trim($("#xsddh").val());  //销售订单号
	obj.fstk = $.trim($("#fstk").val());  //附属条款
	
	obj.xmjl = $.trim($("#xmjl").val());  //项目经理
	obj.jhzk = $.trim($("#jhzk").val());  //计划主控
	obj.kzs = $.trim($("#kzs").val());  //控制室
	obj.qsgs = $.trim($("#qsgs").val());  //起算工时
	obj.kzsdh = $.trim($("#kzsdh").val());  //控制室电话
	
	obj.attachments = getWoJobenclosureParenm(); //合同附件
	
	//alert(JSON.stringify(obj.attachments));
	return obj;
}

//新增信息至后台
function addProjectObject() {
	 $('#form_project').data('bootstrapValidator').validate();
	 if(!$('#form_project').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
		return false;
	 }	
	 var oxt = checkDatBeforeSubmit();
	 if (!oxt.f) {
		 AlertUtil.showModalFooterMessage("时间和循环必须为数字!");
	     return false; 
	 }
	 
	 var obj = gatherSubmitParam();
	startWait($("#AddEditAlertModal"));
	AjaxUtil.ajax({
		url: basePath + "/baseinfo/project/addproject",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
	    modal:$("#AddEditAlertModal"),
		success:function(massge){
			finishWait($("#AddEditAlertModal"));
			$("#AddEditAlertModal").modal("hide");
			AlertUtil.showMessage("增加成功！");
			//goPage(1,"auto","desc");
			$("#project_id").val(massge.project.id);
			goPage(pageParam.page, pageParam.sort, pageParam.order);
		}
	});
}

//oncheange重置css样式
function changeResetCss() {
	var myElememt=new Array("apu_hv","apu_cv","e1_hv","e1_hc","e2_hv","e2_hc","e3_hv","e3_hc","e4_hv","e4_hc");
	for (var i=0; i< myElememt.length; i++) {
		$("#" + myElememt[i]).change(function() {
			if ($(this).hasClass("border-color-red")) {
				$(this).removeClass("border-color-red");
			}
		});
	}
}

//弹窗时重置CSS
function openWindowResetCss() {
	var myElememt=new Array("apu_hv","apu_cv","e1_hv","e1_hc","e2_hv","e2_hc","e3_hv","e3_hc","e4_hv","e4_hc");
	for (var i=0; i< myElememt.length; i++) {
		if ($("#" + myElememt[i]).hasClass("border-color-red")) {
			$("#" + myElememt[i]).removeClass("border-color-red");
		}
	}
}
//在提交前检查数据的合法性
function checkDatBeforeSubmit() {
	var objt = {};
	var f = true;
	var apu_hv = $.trim($("#apu_hv").val());
	var apu_cv = $.trim($("#apu_cv").val());
	
	var e1_hv = $.trim($("#e1_hv").val());
	var e1_hc = $.trim($("#e1_hc").val());
	
	var e2_hv = $.trim($("#e2_hv").val());
	var e2_hc = $.trim($("#e2_hc").val());
	
	var e3_hv = $.trim($("#e3_hv").val());
	var e3_hc = $.trim($("#e3_hc").val());
	
	var e4_hv = $.trim($("#e4_hv").val());
	var e4_hc = $.trim($("#e4_hc").val());
	if (apu_hv !="" && !(timeReg.test(apu_hv))) {
		f = false;
		$("#apu_hv").addClass("border-color-red");
	}
	if (apu_cv !="" && !(cycleReg.test(apu_cv))) {
		f = false;
		$("#apu_cv").addClass("border-color-red");
	}
	
	if (e1_hv !="" && !(timeReg.test(e1_hv))) {
		f = false;
		$("#e1_hv").addClass("border-color-red");
	}
	
	if (e1_hc !="" && !(cycleReg.test(e1_hc))) {
		f = false;
		$("#e1_hc").addClass("border-color-red");
	}
	
	if (e2_hv !="" && !(timeReg.test(e2_hv))) {
		f = false;
		$("#e2_hv").addClass("border-color-red");
	}
	
	if (e2_hc !="" && !(cycleReg.test(e2_hc))) {
		$("#e2_hc").addClass("border-color-red");
	}
	
	if (e3_hv !="" && !(timeReg.test(e3_hv))) {
		$("#e3_hv").addClass("border-color-red");
	}
	
	if (e3_hc !="" && !(cycleReg.test(e3_hc))) {
		$("#e3_hc").addClass("border-color-red");
	}
	
	if (e4_hv !="" && !(timeReg.test(e4_hv))) {
		$("#e4_hv").addClass("border-color-red");
	}
	
	if (e4_hc !="" && !(cycleReg.test(e4_hc))) {
		$("#e4_hc").addClass("border-color-red");
	}
	objt.f = f;
	return objt;
}


//隐藏出现异常的感叹号
function modalEmpty(){
	 AlertUtil.hideModalFooterMessage();
}

//清除文本域的内容
function cleanInputContent() {
	$("#form_project").find("input[type='text']").val("");
	$("#cust_id").val(""); //客户ID 隐藏域清空
	$("#dept_id").val(""); //部门ID 隐藏域清空
	$("#fstk").val("");
	$("#form_project").find("select").val("");
	$("#project_id").val(""); //清除项目编号
	$("#cust_id").val("");   //客户编号
	$("#filelist").empty();  //清除上传附件列表
	
	$("#fjxlh").val(""); //MSN
	$("#fjbzm").val(""); //飞机描述
	
	//formatCycleInput(); //格式化文本框
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


//切换组织机构码
function changeList() {
	sreload();
}

function changedrpt_List() {
	$("#proiect_zl").empty();
	$("#proiect_zl").append("<option value=''>显示全部All</option>");
	DicAndEnumUtil.registerDic('63', 'proiect_zl', $("#dprtcode").val());//组织机构码//重新加载项目种类
	sreload(); //重新加载内容
}

//搜索按钮
function sreload() {
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	goPage(1,"auto","desc");
}

//外部标识切换
function changeWbbs(obj) {
	var v = $(obj).val();
	if (v == 0) { //内部
		$("#customer_No_1").css("display","none");
		$("#customer_Name_1").css("display","none");
		$("#deptment_Name_1").css("display","block");
		$("#departmentModalInput").css("display","block");
	} else { //外部
		$("#customer_No_1").css("display","block");
		$("#customer_Name_1").css("display","block");
		$("#deptment_Name_1").css("display","none");
		$("#departmentModalInput").css("display","none");
	}
	 $("#dept_edit_20180115").css("display","none");
	 $("#cust_edit_20180115").css("display","none");
}
//切换飞机机型
function changeFjjx(){
	var fjjx = $("#AddEditAlertModal #fjjx").val();
	if(fjjx==""){
		$("#AddEditAlertModal #fjzch").attr("disabled",true);
		$("#AddEditAlertModal #fjxlh").attr("disabled",true);
		$("#AddEditAlertModal #fjzch").val("");
		$("#AddEditAlertModal #fjxlh").val("");
		$("#AddEditAlertModal #fjInfo_div").hide();
	}else{
		$("#AddEditAlertModal #fjzch").attr("disabled",false);
		$("#AddEditAlertModal #fjxlh").attr("disabled",false);
		$("#AddEditAlertModal #fjInfo_div").show();
	}
}


//部门
function openzrdw() {
	var dprtname = $("#dept_name").val();
	var dprtcode = $("#dept_id").val();
	var zzjgm;
	var operType = $("#materialSave").attr("name");
	if (operType == "edit") { //编辑时取组织机构码
		zzjgm = $("#dprtcode").val(); //编辑时取下拉组织机构码
		//return false; //不允许修改
	} else {
		zzjgm = $("#dprtId").val(); //当前用户的组织机构码
	}
	departmentModal.show({
		dprtnameList : dprtname,// 部门名称
		dprtcodeList : dprtcode,// 部门id
		type : false,// 勾选类型true为checkbox
		parentid : "AddEditAlertModal",
		dprtcode : zzjgm, //$("#dprtId").val(),// 默认登录人当前机构代码
		callback : function(data) {// 回调函数
			$("#dept_name").val(data.dprtname);
			$("#dept_id").val(data.dprtcode);
			//回调内容缺少部门编码，再次ajax加载获得部门的编码
			if (data.dprtcode !="") {
				loadDepartCode(data.dprtcode);
			} else { //清空内容
				$("#dept_no").val("");
			}
		}
	});
}

//加载部门信息
function loadDepartCode(deptid) {
	//startWait($("#AddEditAlertModal"));
	AjaxUtil.ajax({
		url: basePath + "/sys/department/queryDepartment/" + deptid,
		type: "post",
		contentType:"application/json;charset=utf-8",
		//data:JSON.stringify(obj),
		dataType:"json",
	   // modal:$("#AddEditAlertModal"),
		success:function(massage){
		    //finishWait($("#AddEditAlertModal"));
			$("#dept_no").val(massage.department.dprtcode);
			$('#form_project').data('bootstrapValidator')
			.updateStatus('dept_no', 'NOT_VALIDATED', null).validateField('dept_no');  //移除校验
		}
	});
}

//验证数字
function clearNoNumber(obj) {
	 obj.value = obj.value.replace(/[^\d]/g,"");
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		obj.value = 0;
  	 }
     obj.value = validateMax(obj.value);
}

function validateMax(_value){
	 if(Number(_value) > 9999999999){
		return validateMax(_value.substr(0, _value.length-1));
	 }
	 return _value;
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

//搜索界面切换客户是内部还是外部
function custCategorySelect() {
	var v = $("#cust_category").val();
	$("#cust_no_1").val("");
	$("#cust_id_1").val("");
	$("#dept_no_1").val("");
	$("#dept_id_1").val("");
	if (v == "") { 
		$("#dept_select_1").css("display", "none");
		$("#cust_select_1").css("display", "none");
	} else if (v == 1) { //外部
		$("#dept_select_1").css("display", "none");
		$("#cust_select_1").css("display", "block");
	} else if(v == 0) { //内部
		$("#dept_select_1").css("display", "block");
		$("#cust_select_1").css("display", "none");
	}
}

//删除project信息
function del(id) {
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		 AjaxUtil.ajax({
			 url:basePath+"/baseinfo/project/del/"+id,
			 type:"post",
			 contentType:"application/json;charset=utf-8",
			 dataType:"json",
			 modal:$("#AddEditAlertModal"),
			 success:function(massge) {
				 if (massge.revs == "") {
					 AlertUtil.showMessage("删除成功!");
					 goPage(1,"auto","desc");
				 } else {
					 var rids = "";
					 $.each(massge.revs,function(i, o){
						 rids = rids + o.gbbh + ",";
					 });
					 rids = rids.substr(0, rids.length - 1);
					 AlertUtil.showMessage("删除失败!请先解除与之关联的工包:" + rids);
				 }
			 }
		 });
	 }});
}

//显示项目详细信息
function showProject(id) {
	
	window.open(basePath + "/baseinfo/project/showProject?id=" + id);
	
	//移除验证
//	 $("#form_project").data('bootstrapValidator').destroy();
//	 $('#form_project').data('bootstrapValidator', null);
//	 validation();
	 
//	 $("#xmbm").attr("disabled","disabled"); 
	
//	 $("#materialHeadChina").html("项目详情");
//	 $("#materialHeadEnglish").html("Project Detail");
//	 $("#materialSave").attr("name","Detail");	
//	 modalEmpty();
//	 cleanInputContent();
	 
//	 var object={};
//		 object.id = id;
	 
//	 startWait($("#AddEditAlertModal"));
//	 AjaxUtil.ajax({
//			url: basePath+"/baseinfo/project/getprojectbyid",
//			type: "post",
//			contentType:"application/json;charset=utf-8",
//			data:JSON.stringify(object),
//			dataType:"json",
//			modal:$("#AddEditAlertModal"),
//			success:function(massge){
//				finishWait($("#AddEditAlertModal"));
//				//赋值操作
//				reproduceDatas(massge);
//				showProjectextend();
//			}
//		});
//	 $("#AddEditAlertModal").modal("show");
	 //检查是否有合同附件
//	 if ($("#filelist").find("tr").length < 1) {
//		 $("#filelist").append('<tr id="noAttachment_x"><td colspan="5" class="text-center">暂无数据 No data.</td></tr>');
//	 }
}

//移除按钮,文本框置灰
function showProjectextend(){
//	$("#AddEditAlertModal").find("input[type='text']").attr("disabled","disabled"); 
//	$("#AddEditAlertModal").find("input[type='radio']").attr("disabled","disabled"); 
//	$("#AddEditAlertModal").find("textarea").attr("disabled","disabled"); 
//	$("#AddEditAlertModal").find("select").attr("disabled","disabled"); 
//	$("#materialSave").hide();  //按钮隐藏
	//对上传的合同附件单独处理
//	if ($("#noAttachment_x").length > 0) {
//		$('#table_procet_20171011').find('tr').find('th:eq(0)').hide();
//		$("#filelist").empty();
//		$("#filelist").append('<tr id="noAttachment_x"><td colspan="4" class="text-center">暂无数据 No data.</td></tr>');
//	} else {
//		$('#table_procet_20171011').find('tr').find('th:eq(0)').hide();
//		$('#table_procet_20171011').find('tr').find('td:eq(0)').hide();
//	}
}

//设置默认选项
function setFaultValue(){
	$("#xmzl").find("option:eq(0)").attr("selected",true); //项目种类
	$("#fjjx").find("option:eq(0)").attr("selected",true); //飞机机型
	$("#fjzt").find("option:eq(0)").attr("selected",true); //飞机状态
}


//所有页面元素恢复原状
function showProjectRestore() {
	$("#xmzl").find("option:eq(0)").attr("selected",false); //项目种类
	$("#fjjx").find("option:eq(0)").attr("selected",false); //飞机机型
	$("#fjzt").find("option:eq(0)").attr("selected",false); //飞机状态
	$("#AddEditAlertModal #fjzch").attr("disabled",false); 
	$("#AddEditAlertModal #fjxlh").attr("disabled",false);
	$("#AddEditAlertModal #fjInfo_div").show();
//	$("#AddEditAlertModal").find("input[type='text']").removeAttr("disabled"); ; 
//	$("#AddEditAlertModal").find("input[type='radio']").removeAttr("disabled"); ; 
//	$("#AddEditAlertModal").find("textarea").removeAttr("disabled"); 
//	$("#AddEditAlertModal").find("select").removeAttr("disabled"); 
//	$("#materialSave").show();  //按钮隐藏
//	$('#table_procet_20171011').find('tr').find('th:eq(0)').show();
}

//根据组织机构码 & 飞机注册号获得飞机基本信息
function getProjectAircraftInfo() {
	var dprtcode = $("#dprtId").val();
	var fjzch = $.trim($("#fjzch").val());
	var obj = {};
	obj.dprtcode = dprtcode;
	obj.fjzch = fjzch;
	startWait($("#AddEditAlertModal"));
	AjaxUtil.ajax({
		url: basePath + "/baseinfo/project/getprojectaircraft",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
	    modal:$("#AddEditAlertModal"),
		success:function(message){
			finishWait($("#AddEditAlertModal"));
			if (message.projectaircraft != "") { //赋值操作
				if (message.projectaircraft.fjjx !="" && message.projectaircraft.fjjx != undefined) { //飞机机型
					$("#fjjx").find("option[value='"+message.projectaircraft.fjjx+"']").attr("selected",true); //飞机状态
				}
				if (message.projectaircraft.xlh !="" && message.projectaircraft.xlh != undefined) { 
					$("#fjxlh").val(message.projectaircraft.xlh); //序列号
				}
				if (message.projectaircraft.bzm !="" && message.projectaircraft.bzm != undefined) { //备注名
					$("#fjbzm").val(message.projectaircraft.bzm); 
				}
				if (message.projectaircraft.rhyzph !="" && message.projectaircraft.rhyzph != undefined) { //润滑油脂牌号
					$("#rhyzph").val(message.projectaircraft.rhyzph); 
				}
				if (message.projectaircraft.yyyph !="" && message.projectaircraft.yyyph != undefined) { //润滑油脂牌号
					$("#yyyph").val(message.projectaircraft.yyyph); 
				}
				if (message.projectaircraft.ipcyxxh !="" && message.projectaircraft.ipcyxxh != undefined) { //IPC有效性号
					$("#ipcyxxh").val(message.projectaircraft.ipcyxxh); 
				}
			}
		}
	});
}

function exportExcel(){
	var keyword = $.trim($("#keyword_search").val());
	var dprtcode = $("#dprtcode").val();
	window.open(basePath+"/baseinfo/project/ProjectInfo.xls?dprtcode="
 				+ dprtcode + "&keyword="+ encodeURIComponent(keyword));
	
}
