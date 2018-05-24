$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		mgnt_main.init();
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
		});
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
	})
	
var mgnt_main = {
	id:'mgnt_main',
	operationId : '',
	tableDivId : 'mgnt_main_table_top_div',
	tableId : 'mgnt_main_table',
	tbodyId : 'mgnt_main_table_list',
	paginationId:'mgnt_main_table_pagination',
	dprtcode: userJgdm,
	pagination : {},
	mainData:[],
	init : function(){
		this.initInfo();
	},
	initInfo : function(){
		var this_ = this;
		var dprtcode = this_.dprtcode;
		/*this_.initWebuiPopover();*/
		this_.multiselect();
		this_.load();
		refreshPermission();
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
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('mgnt-add-type','body',function(obj){
			return this_.getContractTyleBtns(obj);
		}, 60);
	},
	getContractTyleBtns : function(obj){//获取合同类型按钮组
		var this_ = this;
		var str = "<div class='button-group-new' style='text-align:center;'>";
		str += "<p class='margin-bottom-0'>";
		str += "<a style='padding-left:0px;' href='javascript:void(0);' onclick="+this_.id+".openWinAdd(10)>采购</a>";
		str += "</p>";
		str += "<p class='margin-bottom-0'>";
		str += "<a style='padding-left:0px;' href='javascript:void(0);' onclick="+this_.id+".openWinAdd(20)>修理</a>";
		str += "</p>";
		str += "<p class='margin-bottom-0'>";
		str += "<a style='padding-left:0px;' href='javascript:void(0);' onclick="+this_.id+".openWinAdd(31)>租进</a>";
		str += "</p>";
		str += "<p class='margin-bottom-0'>";
		str += "<a style='padding-left:0px;' href='javascript:void(0);' onclick="+this_.id+".openWinAdd(32)>租出</a>";
		str += "</p>";
		str += "<p class='margin-bottom-0'>";
		str += "<a style='padding-left:0px;' href='javascript:void(0);' onclick="+this_.id+".openWinAdd(40)>交换</a>";
		str += "</p>";
		str += "<p class='margin-bottom-0'>";
		str += "<a style='padding-left:0px;' href='javascript:void(0);' onclick="+this_.id+".openWinAdd(50)>出售</a>";
		str += "</p>";
		str += "</div>";
		return str;
	},
	/**
	 * 初始化多选下拉框
	 */
	multiselect : function(){
		var this_ = this;
		// 生成多选下拉框(状态)
		var ztList = this.initZtList();
		$('#zt_div', $("#"+this.id)).empty();
		$('#zt_div', $("#"+this.id)).html("<select class='form-control' multiple='multiple' id='zt_search'></select>");
		$("#zt_search", $("#"+this.id)).append(ztList);
		$("#zt_search", $("#"+this.id)).multiselect({
			buttonClass: 'btn btn-default',
			buttonWidth: 'auto',
			buttonWidth:'100%' ,
			buttonHeight: '34px',
			buttonContainer: '<div class="btn-group base-data-syjx" style="min-width:100%;" />',
			numberDisplayed:100,
			includeSelectAllOption: true,
			nonSelectedText:'显示全部 All',
		    allSelectedText:'显示全部 All',
			selectAllText: '选择全部 Select All',
			onChange : function(element, checked) {
			}
		});
		$('#zt_search', $("#"+this.id)).multiselect('select', ['1','2','4','12']);
	},
	initZtList: function (){
		var list='';
		var typeList = DicAndEnumUtil.data.enumMap.contractMgntStatusEnum;
		if (typeList.length > 0) {
			for (var i = 0; i < typeList.length; i++) {
				list += "<option value='"
					+ StringUtil.escapeStr(typeList[i].id) + "'>"
					+ StringUtil.escapeStr(typeList[i].name) + "</option>";
			}
		} 
		return list;
	},
	/**
	 * 机构代码改变时执行
	 */
	changeDprt : function(){
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
		this.dprtcode = dprtcode;
		this.search();
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
		this.hideBottom();
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
			url:basePath+"/material/contract/queryAllPageList",
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
					signByKeyword($("#keyword_search").val(),[4,8,11],"#"+this_.tbodyId+" tr td");
				} else {
					$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
					$("#"+this_.paginationId, $("#"+this_.id)).empty();
					$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"15\" class='text-center'>暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId:this_.tableId});
	      }
	    }); 
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
		var htlx = $.trim($("#htlx_search", $("#"+this.id)).val());
		var htrq = $.trim($("#htrq_search", $("#"+this.id)).val());
		var ztStr = $.trim($("#zt_search", $("#"+this.id)).val());
		searchParam.dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
		paramsMap.keyword = keyword;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		if(null != htlx && "" != htlx){
			searchParam.htlx = htlx;
		}
		if(null != htrq && "" != htrq){
			 var htrqBegin = htrq.substring(0,10)+" 00:00:00";
			 var htrqEnd = htrq.substring(13,23)+" 23:59:59";
			 paramsMap.htrqBegin = htrqBegin;
			 paramsMap.htrqEnd = htrqEnd;
		}
		if(ztStr != null && ztStr.length > 0){
			var ztArr = ztStr.split(",");
			var ztList = [];
			for(var i = 0 ; i < ztArr.length ; i++){
				if('multiselect-all' != ztArr[i]){
					ztList.push(ztArr[i]);
				}
			}
			if(ztList.length > 0){
				paramsMap.ztList = ztList;
			}
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   
			var paramsMap = row.paramsMap?row.paramsMap:{};
			
			htmlContent = htmlContent + "<tr id='"+row.id+"' onclick="+this_.id+".showHiddenContent(this) >";
			htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;' >";
			
			if(row.zt == 1){
				htmlContent += "<i class='spacing icon-edit color-blue cursor-pointer checkPermission' permissioncode='material:contract:mgnt:02' onClick="+this_.id+".openWinEdit('"+ row.id + "','"+ row.dprtcode + "',event) title='修改 Edit'></i>";
				htmlContent += "<i class='spacing icon-trash color-blue cursor-pointer checkPermission' permissioncode='material:contract:mgnt:06' onClick="+this_.id+".del('"+ row.id + "',event) title='删除 Delete'></i>";  
			}
			if(row.zt == 2 || row.zt == 4 || row.zt == 12){
				htmlContent += "<i class='spacing fa fa-thumb-tack  color-blue cursor-pointer checkPermission' permissioncode='material:contract:mgnt:03' onClick="+this_.id+".openWinRevise('"+ row.id + "','"+ row.dprtcode + "',event) title='修订 Revision'></i>";
				htmlContent += "<i class='spacing icon-off color-blue cursor-pointer checkPermission' permissioncode='material:contract:mgnt:04' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.hth)+"' zdjsyy='' zdjsr='' zdjsrq='' zt='9' onClick='"+this_.id+".shutDown(this, true,event)' title='关闭 Close'></i>";  
			}
			
			htmlContent += "</td>"; 
			
			htmlContent += "<td class='text-left'>";
			   if(row.zt == 9 || row.zt == 10){
				   htmlContent += "<a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.hth)+"' zdjsyy='"+StringUtil.escapeStr(row.gbyy)+"' zdjsr='"+StringUtil.escapeStr(paramsMap.gbrstr)+"' zdjsrq='"+row.gbrq+"' zt='"+row.zt+"' onclick=\""+this_.id+".shutDown(this, false)\">"+(row.zt == 9?"指定结束":"完成")+"</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('contractMgntStatusEnum',row.zt);
			   }
			   htmlContent += "</td>";
			
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('contractMgntTypeEnum',row.htlx)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.hth)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+row.id+"')>"+StringUtil.escapeStr(row.hth)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.htrq)+"</td>";
			var biz = StringUtil.escapeStr(row.biz);
			htmlContent += "<td title='"+biz+"' style='text-align:left;vertical-align:middle;'>"+biz+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jffs)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jffs)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xgfms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xgfms)+"</td>";
			var htje = StringUtil.escapeStr(paramsMap.htje);
			if(row.htlx == 20){
				htje = "-";
			}else{
				if(htje == ''){
					htje = 0;
				}
				htje = htje.toFixed(2);
			}
			htmlContent += "<td title='"+htje+"' style='text-align:right;vertical-align:middle;'>"+htje+"</td>";
			
			htmlContent += "<td name='yjfje' title='"+this_.formatJfje(row)+"' style='text-align:rught;vertical-align:middle;'>"+this_.formatJfje(row)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
			
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if(paramsMap.attachCount != null && paramsMap.attachCount > 0){
				htmlContent += '<i qtid="'+row.id+'" class="attachment-mgnt-main glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.bzrstr)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.bzrstr)+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.whsj)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.dprtname)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.dprtname)+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		this_.initMainWebuiPopover();
		refreshPermission();
	},
	initMainWebuiPopover : function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-mgnt-main','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#"+this_.tableDivId).scroll(function(){
			$('.attachment-mgnt-main').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	formatJfje : function(row){
		var skje = StringUtil.escapeStr(row.paramsMap.skje);
		var fkje = StringUtil.escapeStr(row.paramsMap.fkje);
		return this.getJfje(skje, fkje);
	},
	getJfje : function(skje, fkje){
		var yjfje = '';
		if(skje == '' && fkje == ''){
			yjfje = "-";
		}
		if(skje != null && skje != ''){
			yjfje += "收：" + skje.toFixed(2) + " ";
		}
		if(fkje != null && fkje != ''){
			yjfje += "付：" + fkje.toFixed(2) + " ";
		}
		return yjfje;
	},
	openWinAdd : function(htlx){//打开新增合同弹出框
		var this_ = this;
		this_.dprtcode = userJgdm;
		var obj = {};
		obj.id = '';
		obj.htlx = htlx;
		obj.bzrstr = displayName;
		//初始化合同日期
		TimeUtil.getCurrentDate(function (htrq){
			obj.htrq = htrq;
			var modalHeadCN = '新增'+DicAndEnumUtil.getEnumName('contractMgntTypeEnum', htlx)+'合同';
			mgnt_add_alert_Modal.show({
		 		modalHeadCN : modalHeadCN,
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
						var url = basePath+"/material/contract/save";
						this_.performAction(url, data, message, true);
						$("#"+this_.id+" .modal-body").prop('scrollTop','0');
					}
				}
			});
		});
	},
	openWinEdit : function(id, dprtcode_, e){//打开修改合同弹出框
		this.stopPropagation(e);
		var this_ = this;
		this_.dprtcode = dprtcode_;
		this_.selectById(id, function(obj){
			if(obj.zt != 1){
				AlertUtil.showErrorMessage("该合同已更新，请刷新后再进行操作!");
				return;
			}
			obj.htrq = indexOfTime(obj.htrq);
			obj.bzrstr = obj.paramsMap.bzrstr;
			var modalHeadCN = '编辑'+DicAndEnumUtil.getEnumName('contractMgntTypeEnum', obj.htlx)+'合同';
			mgnt_add_alert_Modal.show({
		 		modalHeadCN : modalHeadCN,
				modalHeadENG : 'Edit',
				editParam:2,//编辑
				viewObj:obj,
				dprtcode:dprtcode_,//机构代码
				callback : function(data) {//回调函数
					if (data != null) {
						data.id = id;
						var message = '保存成功!';
						if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
							message = '提交成功!';
						}
						var url = basePath+"/material/contract/update";
						this_.performAction(url, data, message, true);
						$("#"+this_.id+" .modal-body").prop('scrollTop','0');
					}
				}
			});
		});
	},
	openWinRevise : function(id, dprtcode_, e){//打开修订合同弹出框
		this.stopPropagation(e);
		var this_ = this;
		this_.dprtcode = dprtcode_;
		this_.selectById(id, function(obj){
			if(obj.zt != 2 && obj.zt != 4 && obj.zt != 12){
				AlertUtil.showErrorMessage("该合同已更新，请刷新后再进行操作!");
				return;
			}
			obj.htrq = indexOfTime(obj.htrq);
			obj.bzrstr = obj.paramsMap.bzrstr;
			var modalHeadCN = '修订'+DicAndEnumUtil.getEnumName('contractMgntTypeEnum', obj.htlx)+'合同';
			mgnt_add_alert_Modal.show({
		 		modalHeadCN : modalHeadCN,
				modalHeadENG : 'Revision',
				editParam:3,//修订
				viewObj:obj,
				dprtcode:dprtcode_,//机构代码
				callback : function(data) {//回调函数
					if (data != null) {
						data.id = id;
						var message = '提交成功!';
						var url = basePath+"/material/contract/updateRevise";
						this_.performAction(url, data, message, true);
						$("#"+this_.id+" .modal-body").prop('scrollTop','0');
					}
				}
			});
		});
	},
	performAction : function(url, param, message, isScrollTop){//执行编辑合同
		var this_ = this;
		startWait($("#mgnt_add_alert_Modal"));
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			modal:$("#mgnt_add_alert_Modal"),
			success:function(data){
				finishWait($("#mgnt_add_alert_Modal"));
				AlertUtil.showMessage(message);
				mgnt_add_alert_Modal.close();
				this_.operationId = '';
				if(isScrollTop){
					this_.operationId = data;
				}
				this_.refreshPage();
			}
		});
	},
	/**
	 * 删除合同
	 */
	del : function(id, e){
		this.stopPropagation(e);
		var this_ = this;
		AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
			
			 AjaxUtil.ajax({
				 type:"post",
				 url:basePath+"/material/contract/delete",
				 dataType:"json",
				 data:{'id':id},
				 success:function(data) {
					AlertUtil.showMessage('删除成功!');
					this_.refreshPage();
				 }
			 });
			 
		}});
	},
	shutDown : function(e, isEdit, e2){//关闭
		this.stopPropagation(e2);
		var this_ = this;
		var id = $(e).attr("djid");
		var sqdh = $(e).attr("sqdh");
		var zt = $(e).attr("zt");
		var zdjsyy = $(e).attr("zdjsyy");
		var zdjsr = $(e).attr("zdjsr");
		var zdjsrq = $(e).attr("zdjsrq");
		AssignEndModal.show({
			chinaHead:'合同号',//单号中文名称
			englishHead:'Contract No.',//单号英文名称
			edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
			jsdh:sqdh,//指定结束单号
			jsr:zdjsr,//指定结束人
			zt:zt,//状态
			jssj:zdjsrq,//指定结束时间
			jsyy:zdjsyy,//指定结束原因
			callback: function(data){//回调函数
				if(null != data && data != ''){
					data.id = id;
					this_.sbShutDown(data);
				}
			}
		});
	},
	sbShutDown : function(obj){//关闭提交
		var this_ = this;
		startWait($("#AssignEndModal"));
		AjaxUtil.ajax({
			url:basePath + "/material/contract/shutDown",
			contentType:"application/json;charset=utf-8",
			type:"post",
			async: false,
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#AssignEndModal"),
			success:function(data){
				finishWait($("#AssignEndModal"));
				AlertUtil.showMessage('关闭成功!');
	 			$("#AssignEndModal").modal("hide");
				id = obj.id;
				this_.refreshPage();
			}
		});
	},
	/**
	 * 阻止冒泡
	 */
	stopPropagation : function(e){
		if(e!=undefined){
			e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
		}
	},
	selectById : function(id,obj){//通过id获取数据
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/material/contract/selectById",
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
	view : function(id){
		window.open(basePath+"/material/contract/view/" + id );
	},
	more: function() {//查询条件更多 展开/收缩
		if ($("#divSearch", $("#"+this.id)).css("display") == "none") {
			$("#divSearch", $("#"+this.id)).css("display", "block");
			App.resizeHeight();
			$("#icon", $("#"+this.id)).removeClass("icon-caret-down");
			$("#icon", $("#"+this.id)).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#"+this.id)).css("display", "none");
			App.resizeHeight();
			$("#icon", $("#"+this.id)).removeClass("icon-caret-up");
			$("#icon", $("#"+this.id)).addClass("icon-caret-down");
		}
	},
	showHiddenContent : function(obj){
		var id = $(obj).attr("id");
		$(obj).parent("tbody").find("tr").removeClass("bg_tr")
    	$(obj).addClass("bg_tr");
		var isBottomShown = false;
		if($(".displayContent").is(":visible")){
			isBottomShown = true;
		}
		$(".displayContent").css("display","block");
		App.resizeHeight();
		if(!isBottomShown){
			$("#"+this.tableDivId, $("#"+this.id)).animate({scrollTop:obj.offsetTop-43},"slow");
		}
     	this.loadDetail(id);
	},
	loadDetail : function(id){
		var this_ = this;
		this_.selectById(id, function(obj){
			contract_main_detail.init(obj);
			mgnt_payment_main.init({
				mainid : id,
				biz: obj.biz,
				zt : obj.zt,
				isView : false,
				callback: function(){//回调函数
					var data = mgnt_payment_main.getSFKXX();
					var yjfje = this_.getJfje(data.skje, data.fkje);
					$("#"+id, $("#"+this_.tbodyId)).find("td[name='yjfje']").html(yjfje);
					$("#"+id, $("#"+this_.tbodyId)).find("td[name='yjfje']").attr("title", yjfje);
				}
			});
		});
	},
	hideBottom:function(){
		$(".displayContent").css("display","none");	
		App.resizeHeight();
		$("#"+this.tbodyId, $("#"+this.id)).find("tr").removeClass("bg_tr");
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
		
		$("#divSearch", $("#"+this.id)).find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch", $("#"+this.id)).find("textarea").each(function(){
			$(this).val("");
		});

		$("#divSearch", $("#"+this.id)).find("select").each(function() {
			$(this).attr("value", "");
		});
		$("#htlx_search", $("#"+this.id)).val("");
		$("#htrq_search", $("#"+this.id)).val("");
		$("#dprtcode_search", $("#"+this.id)).val(userJgdm);
		this.multiselect();
		this.search();
	}
};
    
