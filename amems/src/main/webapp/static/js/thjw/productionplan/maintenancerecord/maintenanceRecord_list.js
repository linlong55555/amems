
var id = "";
var dprtcode = "";
var pagination = {};
var delAttachements = [];
var attachNumber = 0;
var uploadObj = null;

var orderNumber = 1;
var oldDetailIdArr = [];// 已拥有的库位id数组
var flag = true;
var alertFormId = 'alertRecordForm';
var headChinaId = 'recordHeadChina';
var headEnglishId = 'recorHeadEnglish';
var isEditFile = true;

var kjfw = '';
var jd = '';
var zy = '';

$(function(){
	initInfo();
	formValidator();
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
        	gzms: {
                validators: {
                    notEmpty: {
                        message: '故障描述不能为空'
                    }
                }
            }
        }
    });
}

//隐藏Modal时验证销毁重构
$("#"+alertFormId).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     formValidator();
});

function initInfo(){
	//数据字典
	DicAndEnumUtil.registerDic('4','zy',$("#dprtId").val());
	DicAndEnumUtil.registerDic('4','zy_search',$("#dprtId").val());
	kjfw = $.trim($("#kjfw", $("#"+alertFormId)).val());
	jd = $.trim($("#jd", $("#"+alertFormId)).val());
	zy = $.trim($("#zy", $("#"+alertFormId)).val());
}

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var searchParam = gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	searchParam.pagination = pagination;
	searchParam.paramsMap.id = id;
	id = "";
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/production/maintenancerecord/queryAllPageList",
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
		  		signByKeyword($("#keyword_search").val(),[3,4,5,6,7,8,10,11],"#summaryList tr td");
		  	} else {
		  		$("#summaryList").empty();
		  		$("#pagination").empty();
		  		$("#summaryList").append("<tr><td colspan=\"14\" class='text-center'>暂无数据 No data.</td></tr>");
		  	}
		  	new Sticky({tableId:'record_list_table'});
		}
	}); 
}

//将搜索条件封装 
function gatherSearchParam(){
	var searchParam = {};
	var paramsMap = {};
	var keyword = $.trim($("#keyword_search").val());
	paramsMap.dprtcode = userJgdm;
	paramsMap.zdrid = $.trim($("#userId").val());
	paramsMap.kjfw = $.trim($("#kjfw_search").val());
	paramsMap.zy = $.trim($("#zy_search").val());
	paramsMap.jd = $.trim($("#jd_search").val());
	var zdrq = $.trim($("#zdrq_search").val());
	if(keyword != null && keyword != ""){
		paramsMap.keyword = keyword;
	}
	if(null != zdrq && "" != zdrq){
		var zdrqBegin = zdrq.substring(0,10)+" 00:00:00";
		var zdrqEnd = zdrq.substring(13,23)+" 23:59:59";
		paramsMap.zdrqBegin = zdrqBegin;
		paramsMap.zdrqEnd = zdrqEnd;
	}
	searchParam.paramsMap = paramsMap;
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
		htmlContent += "<td class='fixed-column text-center'>";
		htmlContent += "<i class='icon-edit color-blue cursor-pointer' onClick=\"edit('"+ row.ID + "','" +row.DPRTCODE + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		htmlContent += "<i class='icon-trash color-blue cursor-pointer' onClick=\"del('"+ row.ID + "')\" title='作废 Invalid'></i>";  
		htmlContent += "</td>";  
		htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('visibleRangeEnum',row.KJFW) +"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.GJC)+"' class='text-left'>"+StringUtil.escapeStr(row.GJC)+"</td>"; 
		htmlContent += "<td title='"+StringUtil.escapeStr(row.BJH)+"' class='text-left'>";
		htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.ID+"')\">"+StringUtil.escapeStr(row.BJH)+"</a>";
		htmlContent += "</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.FJJX)+"' class='text-left'>"+StringUtil.escapeStr(row.FJJX)+"</td>"; 
		htmlContent += "<td title='"+StringUtil.escapeStr(row.FJZCH)+"' class='text-left'>"+StringUtil.escapeStr(row.FJZCH)+"</td>"; 
		htmlContent += "<td title='"+StringUtil.escapeStr(row.JDMS)+"' class='text-left'>"+StringUtil.escapeStr(row.JDMS)+"</td>"; 
		htmlContent += "<td title='"+(StringUtil.escapeStr(row.FZJH)+" "+StringUtil.escapeStr(row.FZWMS))+"' class='text-left'>"+StringUtil.escapeStr(row.FZJH)+" "+StringUtil.escapeStr(row.FZWMS)+"</td>";
		htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.ZY) +"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.GZMS)+"' class='text-left'>"+StringUtil.escapeStr(row.GZMS)+"</td>";
		htmlContent += "<td title='"+StringUtil.escapeStr(row.JYZJ)+"' class='text-left'>"+StringUtil.escapeStr(row.JYZJ)+"</td>";
		htmlContent += "<td class='text-center'>"+formatUndefine(row.ZDSJ)+"</td>";
		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>";
		htmlContent += "</tr>";  
	    
	});
	$("#summaryList").empty();
	$("#summaryList").html(htmlContent);
}

//修改故障总结
function edit(id,dprtcode){
	openSummaryEdit(id,dprtcode);
}

//查看详情
function viewDetail(id){
	openSummaryView(id);
}

//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#zjh").val());
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			var wczjh = '';
			var wczjhName = '';
			if(data != null){
				wczjh = data.zjh;
				wczjhName = data.zwms;
				wczjhName = wczjh + " " + wczjhName;
			}
			$("#zjh", $("#"+alertFormId)).val(wczjh);
			$("#zjhName", $("#"+alertFormId)).val(wczjhName);
		}
	})
}

//打开航材对话框
function openMaterialWin(){
	open_win_material_basic.show({
		callback: function(data){//回调函数
			if(data != null && data != ""){
				$("#bjh", $("#"+alertFormId)).val(data.bjh);
			}
		}
	},true)
}

//打开机型对话框
function openPlaneModelWin(){
	var fjjx = $.trim($("#fjjx").val());
	open_win_plane_model.show({
		selected:fjjx, //原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			if(data != null && data != ""){
				$("#fjjx", $("#"+alertFormId)).val(data.fjjx);
			}
		}
	})
}

//打开飞机注册号对话框
function openPlaneRegWin(){
	var fjjx = $.trim($("#fjjx").val());
	var fjzch = $.trim($("#fjzch").val());
	open_win_plane_regist.show({
		selected:fjzch, //原值，在弹窗中默认勾选
		fjjx:fjjx, //机型,条件查询
		callback: function(data){//回调函数
			if(data != null && data != ""){
				$("#fjzch", $("#"+alertFormId)).val(data.fjzch);
			}
		}
	})
}

//打开用户列表对话框
function openUserWin() {
	userModal.show({
		selected:$("#clrid").val(),//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			if(data != null){
				var str = StringUtil.escapeStr(data.username) + " " + StringUtil.escapeStr(data.realname);
				$("#clrid").val(data.id);
				$("#clr").val(data.realname);
			}
		}
	})
}	 

//打开新增故障总结弹出框
function openSummaryAdd(){
	setEdit();//设置可编辑
	$("#"+headChinaId, $("#"+alertFormId)).html('新增档案');
	$("#"+headEnglishId, $("#"+alertFormId)).html('Add');
	var obj = {};
	obj.kjfw = kjfw;
	obj.jd = jd;
	obj.zy = zy;
	setSummaryData(obj);//设置故障总结表单数据
	loadDetailData('',true);//设置故障总结详情数据
	$("#"+alertFormId).modal("show");
	attachNumber = 0;
	isEditFile = true;
	initUploadObj();
	$("#profileList").empty();
	if(isEditFile){
		$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}else{
		$("#profileList").append("<tr style='height:35px;'><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
}

//修改故障总结
function openSummaryEdit(id,dprt){
	dprtcode = dprt;
	selectById(id,function(obj){
		$("#"+headChinaId, $("#"+alertFormId)).html('修改档案');
		$("#"+headEnglishId, $("#"+alertFormId)).html('Eidt');
		setSummaryData(obj);//设置故障总结表单数据
		loadDetailData(obj.summaryDetailList,true);//设置故障总结详情数据
		$("#"+alertFormId).modal("show");
		setEdit();//设置可编辑
		attachNumber = 0;
		isEditFile = true;
		initUploadObj();
		loadAttachements(id);
	});
}

//查看故障总结
function openSummaryView(id){
	selectById(id,function(obj){
		$("#"+headChinaId, $("#"+alertFormId)).html('查看档案');
		$("#"+headEnglishId, $("#"+alertFormId)).html('View');
		setSummaryData(obj);//设置故障总结表单数据
		loadDetailData(obj.summaryDetailList,false);//设置故障总结详情数据
		$("#"+alertFormId).modal("show");
		setView();//设置只读/失效
		attachNumber = 0;
		isEditFile = false;
		initUploadObj();
		loadAttachements(id);
	});
}

alertThoughtForm = {
	id : 'alertThoughtForm',
	headChinaId : 'thoughtHeadChina',
	headEnglishId : 'thoughtHeadEnglish',
	param: {
		viewObj:{},
		headChina : '',
		headEnglish : '',
		callback:function(){}
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.headChinaId, $("#"+this.id)).html(this.param.headChina);
		$("#"+this.headEnglishId, $("#"+this.id)).html(this.param.headEnglish);
		//回显数据
		this.returnViewData(this.param.viewObj);
		$("#"+this.id).modal("show");
	},
	returnViewData : function(obj){
		$("#clrid", $("#"+this.id)).val(obj.clrid);
		$("#clr", $("#"+this.id)).val(obj.clr);
		$("#clsj", $("#"+this.id)).val(obj.clsj);
		$("#pgjg", $("#"+this.id)).val(obj.pgjg);
	},
	vilidateData : function(){
		var this_ = this;
		var clr = $.trim($("#clr", $("#"+this.id)).val());
		var clsj = $.trim($("#clsj", $("#"+this.id)).val());
		var pgjg = $.trim($("#pgjg", $("#"+this.id)).val());
		if(clr == null || clr == ''){
			AlertUtil.showMessageCallBack({
				message : '处理人不能为空!',
				option : 'clr',
				callback : function(option){
					$("#"+option,$("#"+this_.id)).focus();
				}
			});
			return false;
		}
		if(clsj == null || clsj == ''){
			AlertUtil.showMessageCallBack({
				message : '处理日期不能为空!',
				option : 'clsj',
				callback : function(option){
					$("#"+option,$("#"+this_.id)).focus();
				}
			});
			return false;
		}
		if(pgjg == null || pgjg == ''){
			AlertUtil.showMessageCallBack({
				message : '排故经过不能为空!',
				option : 'pgjg',
				callback : function(option){
					$("#"+option,$("#"+this_.id)).focus();
				}
			});
			return false;
		}
		return true;
	},
	setData: function(){
		
		if(!this.vilidateData()){
			return false;
		}
		
		var data = {};
		data.clrid = $.trim($("#clrid", $("#"+this.id)).val());
		data.clr = $.trim($("#clr", $("#"+this.id)).val());
		data.clsj = $.trim($("#clsj", $("#"+this.id)).val());
		data.pgjg = $.trim($("#pgjg", $("#"+this.id)).val());
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	}
	
}

//打开新增经过思路弹出框
function openThoughtWinAdd(){
	var obj = {};
	alertThoughtForm.show({
		viewObj:obj,
		headChina : '新增经过思路',
		headEnglish : 'Add',
		callback: function(data){//回调函数
			if(data != null && data != ""){
				//先移除暂无数据一行
				var len = $("#detailTable").length;
				if (len == 1) {
					if($("#detailTable").find("td").length == 1){
						$("#detailTable").empty();
					}
				}
				data.id = '';
				addRow(data,true);
			}
		}
	})
}

//打开修改经过思路弹出框
function openThoughtWinEdit(node){
	var obj = {};
	obj.clrid = $.trim($(node).find("input[name='clrid']").val());
	obj.pgjg = $.trim($(node).find("input[name='pgjg']").val());
	obj.clr = $.trim($(node).find("td[name='clr']").html());
	obj.clsj = $.trim($(node).find("td[name='clsj']").html());
	alertThoughtForm.show({
		viewObj:obj,
		headChina : '新增经过思路',
		headEnglish : 'Add',
		callback: function(data){//回调函数
			if(data != null && data != ""){
				$(node).find("input[name='clrid']").val(data.clrid);
				$(node).find("input[name='pgjg']").val(data.pgjg);
				$(node).find("td[name='clr']").html(data.clr);
				$(node).find("td[name='clsj']").html(data.clsj);
				$(node).find("td[name='pgjg']").attr('title',StringUtil.title(StringUtil.escapeStr(data.pgjg),50));
				$(node).find("td[name='pgjg']").html(StringUtil.subString(StringUtil.escapeStr(data.pgjg),50));
			}
		}
	})
}

//保存或修改故障总结
function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	var paramsMap = {};
	var bjh = $.trim($("#bjh", $("#"+alertFormId)).val());
	var fjjx = $.trim($("#fjjx", $("#"+alertFormId)).val());
	var fjzch = $.trim($("#fjzch", $("#"+alertFormId)).val());
	var zjh = $.trim($("#zjh", $("#"+alertFormId)).val());
	var gjc = $.trim($("#gjc", $("#"+alertFormId)).val());
	var kjfw = $.trim($("#kjfw", $("#"+alertFormId)).val());
	var jd = $.trim($("#jd", $("#"+alertFormId)).val());
	var zy = $.trim($("#zy", $("#"+alertFormId)).val());
	var gzms = $.trim($("#gzms", $("#"+alertFormId)).val());
	var jyzj = $.trim($("#jyzj", $("#"+alertFormId)).val());
	
	if(null != bjh && "" != bjh){
		var reg = /^[^\u4e00-\u9fa5]+$/;
		if(!reg.test(bjh)){
			AlertUtil.showMessageCallBack({
				message : '部件号不能输入中文!',
				option : 'bjh',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
	}
	
	if(null != fjjx && "" != fjjx){
		var reg = /^[^\u4e00-\u9fa5]+$/;
		if(!reg.test(fjjx)){
			AlertUtil.showMessageCallBack({
				message : '机型不能输入中文!',
				option : 'fjjx',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
	}
	
	if(null != fjzch && "" != fjzch){
		var reg = /^[^\u4e00-\u9fa5]+$/;
		if(!reg.test(fjzch)){
			AlertUtil.showMessageCallBack({
				message : '飞机注册号不能输入中文!',
				option : 'fjzch',
				callback : function(option){
					$("#"+option).focus();
				}
			});
			return false;
		}
	}

	if(/[<>\/\\|"*]/gi.test(gjc)){
    	AlertUtil.showMessageCallBack({
			message : "关键字不能包含如下特殊字符 < > / \\ | \" * ",
			option : 'gjc',
			callback : function(option){
				$("#"+option).focus();
			}
		});
		return false;
    } 
	
	if(/[<>\/\\|"*]/gi.test(gzms)){
    	AlertUtil.showMessageCallBack({
			message : "故障描述不能包含如下特殊字符 < > / \\ | \" * ",
			option : 'gzms',
			callback : function(option){
				$("#"+option).focus();
			}
		});
		return false;
    } 
	
	if(/[<>\/\\|"*]/gi.test(jyzj)){
    	AlertUtil.showMessageCallBack({
			message : "经验总结不能包含如下特殊字符 < > / \\ | \" * ",
			option : 'jyzj',
			callback : function(option){
				$("#"+option).focus();
			}
		});
		return false;
    } 
	
	var summary = {
			id : $.trim($("#id", $("#"+alertFormId)).val()),
			bjh : bjh,
			fjjx : fjjx,
			fjzch : fjzch,
			zjh : zjh,
			gjc : gjc,
			kjfw : kjfw,
			jd : jd,
			zy : zy,
			gzms : gzms,
			dprtcode : dprtcode,
			jyzj : jyzj
		};
	paramsMap.jdstr = $.trim($("#jd", $("#"+alertFormId)).find("option:selected").text());
	paramsMap.zjhstr = $.trim($("#zjhName", $("#"+alertFormId)).val());
	paramsMap.zystr = $.trim($("#zy", $("#"+alertFormId)).find("option:selected").text());
	summary.detailIdList = oldDetailIdArr;
	summary.summaryDetailList = getSummaryDetailListParam();
	summary.paramsMap = paramsMap;
	summary.attachments = getAttachments();
	if(!flag){
		return false;
	}
	var url = basePath+"/production/maintenancerecord/edit";
	if($.trim($("#id", $("#"+alertFormId)).val()) == null || $.trim($("#id", $("#"+alertFormId)).val()) == ''){
		url = basePath+"/production/maintenancerecord/add";
	}
	
	startWait($('#alertRecordForm'));
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(summary),
		dataType:"json",
		modal:$("#alertRecordForm"),
		success:function(data){
			finishWait($('#alertRecordForm'));
			AlertUtil.showMessage('保存成功!');
			id = data;
			refreshPage();
			$("#"+alertFormId).modal("hide");
		}
	});
}

//作废
function del(id) {
	
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/production/maintenancerecord/delete",
			type:"post",
			async: false,
			data:{
				'id' : id
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

//通过id获取故障总结数据
function selectById(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/production/maintenancerecord/selectById",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(data != null){
				var zjhName = '';
				if(data.fixChapter != null){
					zjhName = StringUtil.escapeStr(data.fixChapter.zjh) + " " + StringUtil.escapeStr(data.fixChapter.zwms);
				}
				data.zjhName = zjhName;
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}
		}
	});
}

//获取详情参数
function getSummaryDetailListParam() {
	flag = true;
	var detailParam = [];
	var len = $("#detailTable").find("tr").length;
	if (len == 1) {
		if ($("#detailTable").find("td").length == 1) {
			return detailParam;
		}
	}

	if (len > 0) {
		$("#detailTable").find("tr").each(function() {
			var infos = {};
			var hiddenid = $.trim($(this).find("input[name='hiddenid']").val());
			var clrid = $.trim($(this).find("input[name='clrid']").val());
			var clr = $.trim($(this).find("td[name='clr']").html());
			var clsj = $.trim($(this).find("td[name='clsj']").html());
			var pgjg = $.trim($(this).find("input[name='pgjg']").val());
			
			if(/[<>\/\\|:"?*]/gi.test(pgjg)){
		    	AlertUtil.showMessageCallBack({
					message : "排故经过不能包含如下特殊字符 < > / \\ | : \" * ?",
					option : 'pgjg',
					callback : function(option){
						$(this).find("input[name='pgjg']").focus();
					}
				});
		    	flag = false;
				return false;
		    } 
			
			infos.id = hiddenid;
			infos.clrid = clrid;
			infos.clr = clr;
			infos.clsj = clsj;
			infos.pgjg = pgjg;
			detailParam.push(infos);
		});
	}
	return detailParam;
}

function loadDetailData(list,isEidt){
	orderNumber = 1;
	oldDetailIdArr = [];
	$("#detailTable").empty();
	if(list == null || list.length == 0){
		if(isEidt){
			$("#detailTable").append("<tr><td  colspan='5'  class='text-center'>暂无数据 No data.</td></tr>");
		}else{
			$("#detailTable").append("<tr><td  colspan='4'  class='text-center'>暂无数据 No data.</td></tr>");
		}
		return false;
	}
	$.each(list, function(i, obj) {
		oldDetailIdArr.push(obj.id);
		obj.clsj = indexOfTime(obj.clsj);
		addRow(obj,isEidt);
	});
}

//向table新增一行
function addRow(obj,isEidt){
	
	var tr = "";
		
		if(isEidt){
			tr += "<tr style='cursor:pointer;' ondblclick=openThoughtWinEdit(this)>";
			
			tr += "<td style='text-align:center;vertical-align:middle;'>";
			tr += "<button class='line6' onclick='removeCol(this)'>";
		    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		    tr += "</button>";
		    tr += "</td>";
		}else{
			tr += "<tr>";
		}
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += 	  "<input type='hidden' name='clrid' value='"+obj.clrid+"'/>";
		tr += 	  "<input type='hidden' name='pgjg' value='"+StringUtil.escapeStr(obj.pgjg)+"'/>";
		tr += "</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.clr)+"' style='text-align:left;vertical-align:middle;' name='clr' >"+StringUtil.escapeStr(obj.clr)+"</td>";
		tr +=  "<td style='text-align:center;vertical-align:middle;' name='clsj'>"+formatUndefine(obj.clsj)+"</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(obj.pgjg)+"' style='text-align:left;vertical-align:middle;' name='pgjg'>"+StringUtil.escapeStr(obj.pgjg)+"</td>";
		tr += "</tr>";
		
	$("#detailTable").append(tr);
	orderNumber++;
}

//移除一行
function removeCol(obj){
	$(obj).parent().parent().remove();
	orderNumber--;
	resXh();
	var len = $("#detailTable").find("tr").length;
	if(len < 1){
		$("#detailTable").append("<tr><td  colspan='5'  class='text-center'>暂无数据 No data.</td></tr>");
	}
}

function resXh(){
	var len = $("#detailTable").find("tr").length;
	if (len == 1) {
		if($("#detailTable").find("td").length ==1){
			return false;
		}
	}
	var orderNumber = 1;
	if (len > 0){
		$("#detailTable").find("tr").each(function(){
			$(this).find("span[name='orderNumber']").html(orderNumber++);
		});
	}
}


//设置故障总结表单数据
function setSummaryData(obj){
	$("#id", $("#"+alertFormId)).val(obj.id);
	$("#bjh", $("#"+alertFormId)).val(obj.bjh);
	$("#fjjx", $("#"+alertFormId)).val(obj.fjjx);
	$("#fjzch", $("#"+alertFormId)).val(obj.fjzch);
	$("#zjh", $("#"+alertFormId)).val(obj.zjh);
	$("#zjhName", $("#"+alertFormId)).val(obj.zjhName);
	$("#gjc", $("#"+alertFormId)).val(obj.gjc);
	$("#kjfw", $("#"+alertFormId)).val(obj.kjfw);
	$("#jd", $("#"+alertFormId)).val(obj.jd);
	$("#zy", $("#"+alertFormId)).val(obj.zy);
	$("#gzms", $("#"+alertFormId)).val(obj.gzms);
	$("#jyzj", $("#"+alertFormId)).val(obj.jyzj);
}

//设置可编辑
function setEdit(){
	$("#bjh", $("#"+alertFormId)).removeAttr("readonly");
	$("#fjjx", $("#"+alertFormId)).removeAttr("readonly");
	$("#fjzch", $("#"+alertFormId)).removeAttr("readonly");
	$("#bjhbtn", $("#"+alertFormId)).removeAttr("disabled");
	$("#fjjxbtn", $("#"+alertFormId)).removeAttr("disabled");
	$("#fjzchbtn", $("#"+alertFormId)).removeAttr("disabled");
	$("#zjhbtn", $("#"+alertFormId)).removeAttr("disabled");
	$("#gjc", $("#"+alertFormId)).removeAttr("readonly");
	$("#kjfw", $("#"+alertFormId)).removeAttr("disabled");
	$("#jd", $("#"+alertFormId)).removeAttr("disabled");
	$("#zy", $("#"+alertFormId)).removeAttr("disabled");
	$("#gzms", $("#"+alertFormId)).removeAttr("readonly");
	$("#jyzj", $("#"+alertFormId)).removeAttr("readonly");
	$("#summarySave", $("#"+alertFormId)).show();
	$("#addDetailBtn", $("#"+alertFormId)).show();
	$("#fileHead", $("#"+alertFormId)).show();
	$("#colhead", $("#"+alertFormId)).show();
	$("#bjhbtn", $("#"+alertFormId)).show();
	$("#fjjxbtn", $("#"+alertFormId)).show();
	$("#fjzchbtn", $("#"+alertFormId)).show();
	$("#zjhbtn", $("#"+alertFormId)).show(); 
	$(".identifying", $("#"+alertFormId)).show();
}

//设置只读/失效/隐藏
function setView(){
	$("#bjh", $("#"+alertFormId)).attr("readonly","readonly");
	$("#fjjx", $("#"+alertFormId)).attr("readonly","readonly");
	$("#fjzch", $("#"+alertFormId)).attr("readonly","readonly");
	$("#bjhbtn", $("#"+alertFormId)).attr("disabled","true");
	$("#fjjxbtn", $("#"+alertFormId)).attr("disabled","true");
	$("#fjzchbtn", $("#"+alertFormId)).attr("disabled","true");
	$("#zjhbtn", $("#"+alertFormId)).attr("disabled","true");
	$("#gjc", $("#"+alertFormId)).attr("readonly","readonly");
	$("#kjfw", $("#"+alertFormId)).attr("disabled","true");
	$("#jd", $("#"+alertFormId)).attr("disabled","true");
	$("#zy", $("#"+alertFormId)).attr("disabled","true");
	$("#gzms", $("#"+alertFormId)).attr("readonly","readonly");
	$("#jyzj", $("#"+alertFormId)).attr("readonly","readonly");
	$("#summarySave", $("#"+alertFormId)).hide();
	$("#addDetailBtn", $("#"+alertFormId)).hide();
	$("#fileHead", $("#"+alertFormId)).hide();
	$("#colhead", $("#"+alertFormId)).hide();
	$("#bjhbtn", $("#"+alertFormId)).hide();
	$("#fjjxbtn", $("#"+alertFormId)).hide();
	$("#fjzchbtn", $("#"+alertFormId)).hide();
	$("#zjhbtn", $("#"+alertFormId)).hide(); 
	$(".identifying", $("#"+alertFormId)).hide();
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}

//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
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

//搜索条件重置
function searchreset(){
	var dprtId = $.trim($("#dprtId").val());
	
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
 
//加载附件信息begin*************************************************************************************************************

 /**
  * 实例化附件上传组件
  */ 
 function initUploadObj(){

   uploadObj = $("#fileuploader")
 		.uploadFile(
 				{
 					url : basePath + "/common/ajaxUploadFile",
 					multiple : true,
 					dragDrop : false,
 					showStatusAfterSuccess : false,
 					showStatusAfterError: false,
 					showDelete : false,
 					maxFileCount : 100,
 					formData : {
 						"fileType" : "legacytrouble",
 						"wbwjm" : function() {
 							return $('#wbwjm').val()
 						}
 					}, 
 					fileName : "myfile",
 					returnType : "json",
 					removeAfterUpload : true,
 					uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
 					uploadButtonClass:"ajax-file-upload_ext",
 					statusBarWidth : 150,
 					dragdropWidth : 150,
 					onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
 					{
 						if (1 == $("#profileList").find("tr").length && $("#profileList").find("td").length ==1) {
 							$("#profileList").empty();
 						}
 						attachNumber++;
 						var trHtml = '<tr bgcolor="#f9f9f9" id=\''+ data.attachments[0].uuid + '\'>';
 						trHtml += '<td><div>';
 						trHtml += '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+ data.attachments[0].uuid + '\')" title="删除 Delete">  ';
 						trHtml += '</div></td>';
 						
 						trHtml += '<td style="text-align:center;vertical-align:middle;">';
 						trHtml += 	  "<span name='orderNumber'>"+attachNumber+"</span>";
 						trHtml += '</td>';
 						
 						trHtml += '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" class="text-left">';
 						trHtml += StringUtil.escapeStr(data.attachments[0].wbwjm);
 						trHtml += '</td>';
 						
 						trHtml += '<td class="text-left">' + data.attachments[0].wjdxStr + ' </td>';
 						trHtml += '<td title="'+StringUtil.escapeStr(data.attachments[0].user.displayName)+'" class="text-left">' + StringUtil.escapeStr(data.attachments[0].user.displayName) + '</td>';
 						trHtml += '<td>' + data.attachments[0].czsj + '</td>';
 						trHtml += '<input type="hidden" name="relativePath" value=\'' + StringUtil.escapeStr(data.attachments[0].relativePath) + '\'/>';

 						trHtml += '</tr>';
 						$("#profileList").append(trHtml);
 					}
 					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm').val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	AlertUtil.showMessageCallBack({
									message : "文件说明不能包含如下特殊字符 < > / \\ | : \" * ?",
									option : 'wbwjm',
									callback : function(option){
										$("#"+option).focus();
									}
								});
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


 }	
 function loadAttachements(djid){
 	AjaxUtil.ajax({
 		url : basePath + "/common/loadAttachments",
 		type : "post",
 		async : false,
 		data : {
 			mainid : djid
 		},
 		success : function(data) {
 			if (data.success) {
 				var attachments = data.attachments;
 				var len = data.attachments.length;
 				$("#profileList").empty();
 				if (len > 0) {
 					var trHtml = '';
 					
 					for (var i = 0; i < len; i++) {
 						attachNumber++;
 						trHtml += '<tr bgcolor="#f9f9f9" name="'+djid+'" id=\''+ attachments[i].uuid + '\' key=\''+ attachments[i].id + '\' >';
 						if(isEditFile){
 							trHtml += '<td><div>';
 	 						trHtml += '<i class="icon-trash color-blue cursor-pointer checkPermission" permissioncode="aerialmaterial:enquiry:main:02" onclick="delfile(\''+ data.attachments[0].uuid + '\')" title="删除 Delete">  ';
 	 						trHtml += '</div></td>';
 						}
 						trHtml += '<td style="text-align:center;vertical-align:middle;">';
 						trHtml += 	  "<span name='orderNumber'>"+attachNumber+"</span>";
 						trHtml += '</td>';
 						
 						trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].wbwjm)+'" class="text-left">';
 						trHtml += "<a href='javascript:void(0);' onclick=\"downloadfile('"+ attachments[i].id+"')\">"+StringUtil.escapeStr(attachments[i].wbwjm)+"</a>";
 						trHtml += '</td>';
 						
 						trHtml += '<td class="text-left">' + attachments[i].wjdxStr + ' </td>';
 						trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].czrname)+'" class="text-left">'+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
 						trHtml += '<td style="text-align:center;vertical-align:middle;">' + attachments[i].czsj+ '</td>';
 						trHtml += '<input type="hidden" name="relativePath" value=\''+ StringUtil.escapeStr(attachments[i].relativePath) + '\'/>';
 						trHtml += '</tr>';
 					}
 					$("#profileList").append(trHtml);
 					refreshPermission();
 				}else{
 					if(isEditFile){
 						$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
 					}else{
 						$("#profileList").append("<tr style='height:35px;'><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
 					}
 				}
 			}
 		}
 	});
 }

 function downloadfile(id){
 	//window.open(basePath + "/common/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
 }

 function delfile(uuid){
 	var key = $('#' + uuid).attr('key');
 	if (key == undefined || key == null || key == '') {
 		var responses = uploadObj.responses;
 		var len = uploadObj.responses.length;
 		var fileArray = [];
 		var waitDelArray = [];
 		if (len > 0) {
 			for (var i = 0; i < len; i++) {
 				if (responses[i].attachments[0].uuid != uuid) {
 					fileArray.push(responses[i]);
 				}
 			}
 			uploadObj.responses = fileArray;
 			uploadObj.selectedFiles -= 1;
 			$('#' + uuid).remove();
 		}

 	} else {
 		$('#' + uuid).remove();
 		delAttachements.push({
 			id : key
 		});
 	}
 	attachNumber--;
 	if($("#profileList").find("tr").length == 0){
 		$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
 	}
 	var len = $("#profileList").find("tr").length;
 	if (len >= 1){
 		$("#profileList").find("tr").each(function(index){
 			$(this).find("span[name='orderNumber']").html(index+1);
 		});
 	}else{
 		$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
 	}
 }

 function getAttachments(){
 	var attachments = [];
 	var responses = uploadObj.responses;
 	var len = uploadObj.responses.length;
 	if (len != undefined && len > 0) {
 		for (var i = 0; i < len; i++) {
 			attachments.push({
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

 	var dellen = delAttachements.length;
 	if (dellen != undefined && dellen > 0) {
 		for (var i = 0; i < dellen; i++) {
 			attachments.push({
 				'id' : delAttachements[i].id,
 				'operate' : 'DEL'
 			});
 		}
 	}
 	return attachments;
 }


 //加载附件信息end*************************************************************************************************************

