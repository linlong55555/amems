
var id = "";
var pagination = {};
var dprtcode = userJgdm;
var mainData = [];

$(document).ready(function(){
	Navigation(menuCode, '', '', 'GC-7-1', '刘兵', '2017-08-01', '刘兵', '2017-08-01');//初始化导航
//	initInfo();
	//初始化日志功能
	logModal.init({code:'B_G2_013'});
	work_card_main.init();
	
	//执行待办
    if(todo_ywid){
    	if(todo_jd == 1 || todo_jd == 5 || todo_jd == 6){
    		work_card_main.openWinEdit(todo_ywid, todo_dprtcode);
    	}else if(todo_jd == 2){
    		work_card_main.audit(todo_ywid, todo_dprtcode);
    	}else if(todo_jd == 3){
    		work_card_main.approve(todo_ywid, todo_dprtcode);
    	}else{
    		work_card_main.openWinAdd();
    	}
    	todo_ywid = null;
    }
    else if(todo_lyid) {
    	work_card_main.openWinAdd();
		//默认机型
		if(todo_fjjx) {
			todo_fjjx = decodeURIComponent(Base64.decode(todo_fjjx));
			if(todo_fjjx){
				$("#jx").val(todo_fjjx);
			}
			todo_fjjx = null;
		}
	}
    
	
});

var work_card_main = {
	id:'work_card_main',
	operationId : '',
	tableDivId : 'work_card_main_table_top_div',
	tableId : 'work_card_main_table',
	tbodyId : 'work_card_main_table_list',
	paginationId:'work_card_main_table_pagination',
	dprtcode: userJgdm,
	pagination : {},
	accessId : '',
	accessIdList : [],
	mainData:[],
	init : function(){
		this.initInfo();
	},
	initInfo : function(){
		var this_ = this;
		var dprtcode = this_.dprtcode;
		this.initDic(this_.dprtcode);
		this.initPlaneModel(this_.dprtcode);
		this_.initZoneInformation(dprtcode, function(option){
			$("#qy_search", $("#"+this_.id)).empty();
			$("#qy_search", $("#"+this_.id)).append(option);
			this_.load();
			refreshPermission();
		});
		//回车事件控制
		document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#keyword_search", $("#"+this_.id)).is(":focus") 
						|| $("#keyword_search", $("#"+this_.id)).is(":focus")
				){
					this_.load();      
				}
			}
		};
	},
	/**
	 * 机构代码改变时执行
	 */
	changeDprt : function(){
		var this_ = this;
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
		this.dprtcode = dprtcode;
		this.initDic(dprtcode);
		this.initPlaneModel(dprtcode);
		this.accessIdList = [];
		$("#zjh_search", $("#"+this.id)).val("");
		$("#jj_search", $("#"+this.id)).val("");
		this_.initZoneInformation(dprtcode, function(option){
			$("#qy_search", $("#"+this_.id)).empty();
			$("#qy_search", $("#"+this_.id)).append(option);
			this_.search();
		});
	},
	/**
	 * 改变机型时触发
	 */
	changeModel : function(){
		var this_ = this;
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
		this.accessIdList = [];
		$("#jj_search", $("#"+this.id)).val("");
		this_.initZoneInformation(dprtcode, function(option){
			$("#qy_search", $("#"+this_.id)).empty();
			$("#qy_search", $("#"+this_.id)).append(option);
			this_.search();
		});
	},
	initDic : function(dprtcode){
		if(typeof(dprtcode) == "undefined"){
			dprtcode = userJgdm;
		}
		$("#gzlx_search", $("#"+this.id)).empty();
		$("#gzlx_search", $("#"+this.id)).append('<option value="" selected="selected">显示全部All</option>');
		DicAndEnumUtil.registerDic('17','gzlx_search',dprtcode);
		
		$("#gklx_search", $("#"+this.id)).empty();
		$("#gklx_search", $("#"+this.id)).append('<option value="" selected="selected">显示全部All</option>');
		DicAndEnumUtil.registerDic('22','gklx_search',dprtcode);
		
		$("#zy_search", $("#"+this.id)).empty();
		$("#zy_search", $("#"+this.id)).append('<option value="" selected="selected">显示全部All</option>');
		DicAndEnumUtil.registerDic('4','zy_search',dprtcode);
		
	},
	/**
	 * 初始化区域下拉框
	 */
	initZoneInformation : function(dprtcode, obj){
		var this_ = this;
		var searchParam = {};
		searchParam.dprtcode = dprtcode;
		searchParam.jx = $("#jx_search", $("#"+this_.id)).val();
		searchParam.lx = 1;
		startWait();
		AjaxUtil.ajax({
			async: false,
			url: basePath+"/basic/zone/zoneList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				var zoneOption = '<option value="" selected="selected">显示全部All</option>';
				if(data != null && data.length > 0){
					$.each(data,function(i,row){
						var tempOption = "<option value='"+StringUtil.escapeStr(row.id)+"' >"+StringUtil.escapeStr(row.sz)+"</option>";
						zoneOption += tempOption;
					});
				}
				if(typeof(obj)=="function"){
					obj(zoneOption); 
				}
		    }
		}); 
	},
	initPlaneModel : function(dprtcode){//初始化机型
		if(typeof(dprtcode) == "undefined"){
			dprtcode = userJgdm;
		}
		var data = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 	var option = '<option value="" selected="selected">显示全部All</option>';
	 	if(data.length >0){
			$.each(data,function(i,obj){
				option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
			});
	 	}
	 	$("#jx_search", $("#"+this.id)).empty();
	 	$("#jx_search", $("#"+this.id)).append(option);
	},
	//加载数据
	load : 	function(pageNumber, sortColumn, orderType){
		var this_ = this;
		this_.mainData = [];
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		searchParam.pagination = this_.pagination;
		if(this_.operationId != ""){
			searchParam.id = this_.operationId;
			this_.operationId = "";
		}
		$.extend(searchParam, this_.getParams());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/project2/workcard/queryAllPageList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data.total >0){
					this_.mainData = data.rows;
					this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#keyword_search").val(),[4,6,10],"#"+this_.tbodyId+" tr td");
				} else {
					$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
					$("#"+this_.paginationId, $("#"+this_.id)).empty();
					$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"23\" class='text-center'>暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId:this_.tableId});
	      }
	    }); 
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
		var jx = $.trim($("#jx_search", $("#"+this.id)).val());
		var bb = $.trim($("#bb_search", $("#"+this.id)).val());
		var zjh = $.trim($("#zjh_search", $("#"+this.id)).val());
		var gzlx = $.trim($("#gzlx_search", $("#"+this.id)).val());
		var gklx = $.trim($("#gklx_search", $("#"+this.id)).val());
		var zy = $.trim($("#zy_search", $("#"+this.id)).val());
		var isBj = $.trim($("#isBj_search", $("#"+this.id)).val());
		var qy = $.trim($("#qy_search", $("#"+this.id)).val());
		var bz = $.trim($("#bz_search", $("#"+this.id)).val());
//		var jj = $.trim($("#jj_search", $("#"+this.id)).val());
		searchParam.dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
		paramsMap.keyword = keyword;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		if(jx != null && jx != ''){
			searchParam.jx = jx;
		}
		if(bb == 'current'){
			paramsMap.current = bb;
		}
		if(bb == 'history'){
			paramsMap.history = bb;
		}
		if(qy != null && qy != ''){
			paramsMap.qy = qy;
		}
		if(this.accessIdList.length > 0){
			paramsMap.accessIdList = this.accessIdList;
		}
		if(zjh != null && zjh != ''){
			searchParam.zjh = zjh;
		}
		if(gzlx != null && gzlx != ''){
			searchParam.gzlx = gzlx;
		}
		if(gklx != null && gklx != ''){
			searchParam.gklx = gklx;
		}
		if(zy != null && zy != ''){
			searchParam.zy = zy;
		}
		if(isBj != null && isBj != ''){
			searchParam.isBj = isBj;
		}
		if(bz != null && bz != ''){
			searchParam.bz = bz;
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   
			htmlContent = htmlContent + "<tr onclick=SelectUtil.clickRow("+index+",'work_card_main_table_list','work_card_row')>";
			
			htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input type='checkbox' name='work_card_row' index="+index+"  onclick=selectworkcard(event,"+index+",'work_card_main_table_list','work_card_row',this) /></td>";
			htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;' >";
			
			if(row.zt == 1 || row.zt == 5 || row.zt == 6){
				htmlContent += "<i class='spacing icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:workcard:main:02' onClick="+this_.id+".openWinEdit('"+ row.id + "','"+ row.dprtcode + "') title='修改 Edit'></i>";
				htmlContent += "<i class='spacing icon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:workcard:main:06' onClick="+this_.id+".del('"+ row.id + "') title='删除 Delete'></i>";  
			}
			if(row.zt == 2){
				htmlContent += "<i class='spacing icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project2:workcard:main:03' onClick="+this_.id+".audit('"+ row.id + "','"+ row.dprtcode + "') title='审核 Review'></i>";
			}
			if(row.zt == 3){
				htmlContent += "<i class='spacing icon-check color-blue cursor-pointer checkPermission' permissioncode='project2:workcard:main:04' onClick="+this_.id+".approve('"+ row.id + "','"+ row.dprtcode + "') title='批准 Approved'></i>";
			}
			if(row.zt == 7 && row.zxbs == 2 && (row.bBbid == null || row.bBbid == '')){
				htmlContent += "<i class='spacing icon-pencil color-blue cursor-pointer checkPermission' permissioncode='project2:workcard:main:05' onClick="+this_.id+".openWinModify('"+ row.id + "','"+ row.dprtcode + "') title='改版 Revision'></i>";
				htmlContent += "<i class='spacing iconnew-forbid color-blue cursor-pointer checkPermission' permissioncode='project2:workcard:main:07' onClick="+this_.id+".lose('"+ row.id + "') title='失效 Lose'></i>";  
			}
			
			htmlContent += "</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jx)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gkh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+row.id+"')>"+StringUtil.escapeStr(row.gkh)+"</a>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr("R"+row.bb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr("R"+row.bb)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gzbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzbt)+"</td>";
			if(row.zt == 7 && row.zxbs != 2){
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>失效</td>";
			}else{
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('workCardStatusEnum',row.zt)+"</td>";
			}
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) 
				|| (row.gkfjid != null && row.gkfjid != "")
				|| (row.gznrfjid != null && row.gznrfjid != "")){
				htmlContent += '<i qtid="'+row.id+'" gkfjid="'+row.gkfjid+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gklx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gklx)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.wxxmbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.wxxmbh)+"</td>";
			
			var zjh = '';
			if(row.fixChapter != null){
				zjh = StringUtil.escapeStr(row.fixChapter.displayName);
			}
			htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gzlx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzlx)+"</td>";
		   
			var bzgs = '';
			if(row.jhgsRs != null && row.jhgsRs != "" && row.jhgsXss != null && row.jhgsXss != ""){
				var total = row.jhgsRs*row.jhgsXss;
				if(total == ''){
					total = 0;
				}
				if(String(total).indexOf(".") >= 0){
					total = total.toFixed(2);
				}
				bzgs = row.jhgsRs+"人x"+row.jhgsXss+"时="+total+"时";
			}
			htmlContent += "<td title='"+bzgs+"' style='text-align:left;vertical-align:middle;'>"+bzgs+"</td>";  
		   
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zy)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.zy)+"</td>";
			
			var gzzstr = '';
			if(row.workGroup != null){
				gzzstr = StringUtil.escapeStr(row.workGroup.gzzdm) + " " + StringUtil.escapeStr(row.workGroup.gzzmc);
			}
			if(row.gzzid == "-"){
				gzzstr = "N/A";
			}
			htmlContent += "<td title='"+gzzstr+"' style='text-align:left;vertical-align:middle;'>"+gzzstr+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.isBj == 1?'是':'否')+"</td>";
			
			htmlContent += "<td title='"+this_.formatCoverPlate(row.coverPlateList, 1)+"' style='text-align:left;vertical-align:middle;' >"+this_.formatCoverPlate(row.coverPlateList, 1)+"</td>";
			htmlContent += "<td title='"+this_.formatCoverPlate(row.coverPlateList, 2)+"' style='text-align:left;vertical-align:middle;'>"+this_.formatCoverPlate(row.coverPlateList, 2)+"</td>";
			htmlContent += "<td title='"+this_.formatCoverPlate(row.coverPlateList, 3)+"' style='text-align:left;vertical-align:middle;'>"+this_.formatCoverPlate(row.coverPlateList, 3)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.whsj)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		this_.initWebuiPopover();
		refreshPermission();
	},
	initWebuiPopover : function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#work_card_main_table_top_div").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	openChapterWin : function(){//打开ATA章节号对话框
		var this_ = this;
		var zjh = $.trim($("#zjh_search", $("#"+this_.id)).val());
		FixChapterModal.show({
			parentWinId : '',
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:this_.dprtcode,//机构代码
			callback: function(data){//回调函数			
					var zjh=data.zjh==null?"":data.zjh;
					$("#zjh_search", $("#"+this_.id)).val(zjh);
			}
		});
	},
	openAccessWin : function(){//打开盖板对话框
		var this_ = this;
		var jx = $("#jx_search", $("#"+this_.id)).val();
		open_win_access.show({
			multi : true,
			parentWinId : '',
			jx:jx,
			accessIdList : this_.accessIdList,//回显
			dprtcode:this_.dprtcode,
			callback: function(data){//回调函数
				var str = '';
				this_.accessIdList = [];
				if(data != null && data.length > 0){
					$.each(data,function(index,row){
						this_.accessIdList.push(row.id);
						str += StringUtil.escapeStr(row.gbbh) + ",";
					});
				}
				if(str != ''){
					str = str.substring(0,str.length - 1);
				}
				$("#jj_search", $("#"+this_.id)).val(str);
			}
		},true);
	},
	openWinAdd : function(){//打开新增工卡弹出框
		var this_ = this;
		this_.dprtcode = userJgdm;
		var obj = {};
		obj.id = '';
		obj.gkh = '';
		obj.zt = 1;
		work_card_edit_alert_Modal.show({
	 		modalHeadCN : '新增工卡',
			modalHeadENG : 'Add',
			editParam:1,//新增
			viewObj:obj,
			dprtcode:userJgdm,//机构代码
			callback : function(data) {//回调函数
				if (data != null) {
					var message = '保存成功!';
					if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
						message = '提交成功!';
					}
					var url = basePath+"/project2/workcard/save";
					this_.performAction(url, data, message, true);
					$("#"+this_.id+" .modal-body").prop('scrollTop','0');
				}
			}
		});
	},
	openWinEdit : function(id,dprtcode_){//打开修改工卡弹出框
		var this_ = this;
		this_.dprtcode = dprtcode_;
		this_.selectById(id,function(obj){
			if(!(obj.zt == 1 || obj.zt == 5 || obj.zt == 6)){
				AlertUtil.showErrorMessage("该工卡已更新，请刷新后再进行操作!");
				return;
			}
			work_card_edit_alert_Modal.show({
		 		modalHeadCN : '编辑工卡',
				modalHeadENG : 'Edit',
				editParam:2,//编辑
				viewObj:obj,
				dprtcode:dprtcode_,//机构代码
				callback : function(data) {//回调函数
					if (data != null) {
						var message = '保存成功!';
						if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
							message = '提交成功!';
						}
						data.id = id;
						var url = basePath+"/project2/workcard/update";
						this_.performAction(url, data, message, true);
						$("#"+this_.id+" .modal-body").prop('scrollTop','0');
					}
				}
			});
		});
	},
	openWinModify : function(id,dprtcode_){//打开改版工卡弹出框
		var this_ = this;
		this_.dprtcode = dprtcode_;
		this_.selectById(id,function(obj){
			if(!(obj.zt == 7 && obj.zxbs == 2 && (obj.bBbid == null || obj.bBbid == ''))){
				AlertUtil.showErrorMessage("该工卡已更新，请刷新后再进行操作!");
				return;
			}
			work_card_edit_alert_Modal.show({
		 		modalHeadCN : '改版工卡',
				modalHeadENG : 'Modify',
				editParam:5,//改版
				viewObj:obj,
				dprtcode:dprtcode_,//机构代码
				callback : function(data) {//回调函数
					if (data != null) {
						var message = '保存成功!';
						if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
							message = '提交成功!';
						}
						data.id = id;
						var url = basePath+"/project2/workcard/modify";
						this_.performAction(url, data, message, true);
						$("#"+this_.id+" .modal-body").prop('scrollTop','0');
					}
				}
			});
		});
	},
	audit : function(id,dprtcode_){//打开审核工卡弹出框
		var this_ = this;
		this_.dprtcode = dprtcode_;
		this_.selectById(id,function(obj){
			if(obj.zt != 2){
				AlertUtil.showErrorMessage("该工卡已更新，请刷新后再进行操作!");
				return;
			}
			work_card_edit_alert_Modal.show({
		 		modalHeadCN : '审核工卡',
				modalHeadENG : 'Audit',
				editParam:3,//审核
				viewObj:obj,
				dprtcode:dprtcode_,//机构代码
				callback : function(data,message) {//回调函数
					if (data != null) {
						data.id = id;
						var url = basePath+"/project2/workcard/doAudit";
						this_.performAction(url, data, message, false);
					}
				}
			});
		});
	},
	approve : function(id,dprtcode_){//打开批准工卡弹出框
		var this_ = this;
		this_.dprtcode = dprtcode_;
		this_.selectById(id,function(obj){
			if(obj.zt != 3){
				AlertUtil.showErrorMessage("该工卡已更新，请刷新后再进行操作!");
				return;
			}
			work_card_edit_alert_Modal.show({
		 		modalHeadCN : '批准工卡',
				modalHeadENG : 'Approve',
				editParam:4,//批准
				viewObj:obj,
				dprtcode:dprtcode_,//机构代码
				callback : function(data,message) {//回调函数
					if (data != null) {
						data.id = id;
						var url = basePath+"/project2/workcard/doApprove";
						this_.performAction(url, data, message, false);
					}
				}
			});
		});
	},
	performAction : function(url, param, message, isScrollTop){//执行编辑工卡
		var this_ = this;
		startWait($("#work_card_edit_alert_Modal"));
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#work_card_edit_alert_Modal"),
			success:function(data){
				finishWait($("#work_card_edit_alert_Modal"));
				AlertUtil.showMessage(message);
				work_card_edit_alert_Modal.close();
				this_.operationId = '';
				if(isScrollTop){
					this_.operationId = data;
				}
				this_.refreshPage();
			}
		});
	},
	/**
	 * 删除工卡
	 */
	del : function(id){
		var this_ = this;
		AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
			
			 AjaxUtil.ajax({
				 type:"post",
				 url:basePath+"/project2/workcard/delete",
				 dataType:"json",
				 data:{'id':id},
				 success:function(data) {
					AlertUtil.showMessage('删除成功!');
					this_.refreshPage();
				 }
			 });
			 
		}});
	},
	/**
	 * 失效工卡
	 */
	lose : function(id){
		var this_ = this;
		AlertUtil.showConfirmMessage("确定要失效吗？", {callback: function(){
			
			 AjaxUtil.ajax({
				 type:"post",
				 url:basePath+"/project2/workcard/doInvalid",
				 dataType:"json",
				 data:{'id':id},
				 success:function(data) {
					AlertUtil.showMessage('失效成功!');
					this_.refreshPage();
				 }
			 });
			 
		}});
	},
	/**
	 * 打开批量审核批准对话框
	 */
	batchApproveWin : function(isApprovel){
		var this_ = this;
		var idArr = [];
		var approvalArr = [];
		var approvalNotArr = [];
		
		var zt = isApprovel?3:2;
		$("#"+this_.tbodyId,$("#"+this_.tableDivId)).find("tr input:checked").each(function(){
			var index = $(this).attr("index");
			var work_card = this_.mainData[index];
			if(work_card.zt == zt){
				idArr.push(work_card.id);
				approvalArr.push(work_card.gkh);
	   	 	}else{
	   	 		approvalNotArr.push(work_card.gkh);
	   	 	}
		});
		
		if(approvalArr.length == 0 && approvalNotArr.length == 0){
			AlertUtil.showMessage("请选中数据后再进行操作！");
		}else{
			BatchApprovelModal.show({
				approvalArr:approvalArr,//审核可操作的数据
				approvalNotArr:approvalNotArr,//审核不可操作的数据
				isApprovel:isApprovel,//判断审核还是批准
				callback: function(data){//回调函数
					if(idArr.length > 0){
						this_.batchApproval(idArr,data,isApprovel);
					}
				}
			});
		}
	},
	/**
	 * 执行批量审核批准
	 */
	batchApproval : function(idList,data,isApprovel){
		var this_ = this;
		var url = basePath+"/project2/workcard/updateBatchAudit";
		if(isApprovel){
			url = basePath+"/project2/workcard/updateBatchApprove";
		}
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			type:"post",
			data:{
				idList:idList,yj:data.yj
			},
			dataType:"json",
			success:function(message){
				finishWait();
				AlertUtil.showMessage(message);
				this_.refreshPage();
			}
		});
	},
	selectById : function(id,obj){//通过id获取数据
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/workcard/selectById",
			type:"post",
			data:{id:id},
			dataType:"json",
			success:function(data){
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}
			}
		});
	},
	/**
	 * 格式化接近盖板
	 */
	formatCoverPlate : function(coverPlateList, type){
		var str = '';
		if(coverPlateList != null && coverPlateList.length >0){
			$.each(coverPlateList,function(i,obj){
				if(obj.lx == type){
					str += StringUtil.escapeStr(obj.gbh) + ",";
				}
			});
	  	}
		if(str != ''){
			str = str.substring(0,str.length - 1);
		}
		return str;
	},
	view : function(id){
		window.open(basePath+"/project2/workcard/view/" + id );
	},
	more: function() {//查询条件更多 展开/收缩
		if ($("#divSearch", $("#"+this.id)).css("display") == "none") {
			$("#divSearch", $("#"+this.id)).css("display", "block");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-down");
			$("#icon", $("#"+this.id)).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#"+this.id)).css("display", "none");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-up");
			$("#icon", $("#"+this.id)).addClass("icon-caret-down");
		}
	},
	refreshPage : function(){//刷新页面
		this.load(this.pagination.page, this.pagination.sort, this.pagination.order);
	},
	//搜索
	search: function(){
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load();
	},
	orderBy : function(sortColumn, prefix){//字段排序
		var this_ = this;
		var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableDivId));
		var orderStyle = $obj.attr("class");
		$(".sorting_desc", $("#"+this_.tableDivId)).removeClass("sorting_desc").addClass("sorting");
		$(".sorting_asc", $("#"+this_.tableDivId)).removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf ("sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#"+this_.paginationId+" li[class='active']", $("#"+this_.id)).text();
		if(currentPage == ""){currentPage = 1;}
		if(prefix != null && prefix != '' && typeof prefix != undefined){
			sortColumn = prefix+"."+sortColumn;
		}
		this_.load(currentPage, sortColumn, orderType);
	},
	searchreset : function(){
		$("#keyword_search", $("#"+this.id)).val("");
		this.initDic(userJgdm);
		this.initPlaneModel(userJgdm);
		$("#bb_search", $("#"+this.id)).val("current");
		
		$("#divSearch", $("#"+this.id)).find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch", $("#"+this.id)).find("textarea").each(function(){
			$(this).val("");
		});

		$("#divSearch", $("#"+this.id)).find("select").each(function() {
			$(this).attr("value", "");
		});
		
		this.accessIdList = [];
		
		$("#dprtcode_search", $("#"+this.id)).val(userJgdm);
		
	},
	onkeyup4Num : function(obj){//只能输入数字和小数,保留两位
		//先把非数字的都替换掉，除了数字和.
	     obj.value = obj.value.replace(/[^\d.]/g,"");
	     //必须保证第一个为数字而不是.
	     obj.value = obj.value.replace(/^\./g,"");
	     //保证只有出现一个.而没有多个.
	     obj.value = obj.value.replace(/\.{2,}/g,".");
	     //保证.只出现一次，而不能出现两次以上
	     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     
	     var str = obj.value.split(".");
	     if(str.length > 1){
	    	 if(str[1].length > 2){
	    		 obj.value = str[0] +"." + str[1].substring(0,2);
	    	 }
	     }
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		 if(obj.value.substring(1,2) != "."){
	  			obj.value = 0;
	  		 }
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 99999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	onkeyup4Code : function(){
		var reg = new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,50}$");
	     obj.value = validate(obj.value);
	     function validate(_value){
	    	 if(_value.length == 0){
	    		 return "";
	    	 }
	    	 if(!reg.test(_value)){
	    		return validate(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//导入工卡信息：显示导入模态框
	showImportModal : function(){
		var this_ = this;
		// 初始化导入
	    importModal.init({
		    importUrl:"/project2/workcard/excelImport",
		    downloadUrl:"/common/templetDownfile/20", //工卡导入
			callback: function(data){
				// 重新加载table数据
				this_.load();
				$("#ImportModal").modal("hide");
			}
		});
		importModal.show();
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		var param = this.getParams();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		param.keyword = encodeURIComponent(param.keyword);
		window.open(basePath+"/project2/workcard/workcard.xls?paramjson="+JSON.stringify(param));
	}
};

function selectworkcard(e,index,id,name,obj){
	 e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	    var $checkbox1 = $("#" + id + " :checkbox[name='" + name + "']:eq("
 				+ index + ")");
 		var $checkbox2 = $(".sticky-col :checkbox[name='" + name + "']:eq("
 				+ index + ")");
	    if(!$(obj).parents("table").hasClass("sticky-col")){
	    	var checked = $checkbox1.is(":checked");
	 		$checkbox1.attr("checked", checked);
	 		$checkbox2.attr("checked", checked);	
	    }else{
	    	var checked = $checkbox2.is(":checked");
	 		$checkbox1.attr("checked", checked);
	 		$checkbox2.attr("checked", checked);
	    }
	   
}