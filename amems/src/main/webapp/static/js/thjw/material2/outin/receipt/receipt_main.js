	
$(function(){
	// 收货单初始化
	receipt.init();
});

/**
 * 收货单主单
 */
var receipt = {
	param: {
		idList : [],//用于查询退料信息
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	/**  */
	id : "",
	
	/** 收货明细列表id */
	tbodyId : "receipt_list",
	
	/**
	 * 初始化
	 */
	init : function(){
		
		var this_ = this;
		
		// 初始化导航
		this_.initNavigation();
		
		// 初始化控件
		this_.initWidget();
		
		// 初始化数据字典和枚举
		this_.initDicAndEnum();
		
		// 加载数据
		this_.loadData();
		
		//加载退料数据
		this_.loadReturnData();
	},
	/**
	 * 加载退料数据
	 */
	loadReturnData : function(){
		try{
			//是否支持本地存储
			if(window.localStorage){
				var param = window.localStorage.getItem(type);
				var obj = JSON.parse(param);
				this.param.dprtcode = obj.dprtcode;
				this.param.idList = obj.idList;
				window.localStorage.removeItem(type);
			}
			this.initReturnData();
		}catch(e){
			this.initReturnData();
		}
	},
	/**
	 * 初始化退料数据
	 */
	initReturnData : function(){
		var this_ = this;
		if(type == "returnData" && this_.param.idList.length > 0){
			$("#shlx").val("60");
			$("#shlx").change();//触发change事件
			
			var obj = {};
			var paramsMap = {};
			obj.dprtcode=this_.param.dprtcode;
			paramsMap.idList=this_.param.idList;
			obj.paramsMap = paramsMap;
			startWait();
			AjaxUtil.ajax({
				url: basePath+"/material/outin/returnDatalist",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(obj),
				success:function(data){
					finishWait();
					if(data.length >0){
						$.each(data||[], function(i, obj){
							obj.lsckid = $("#shck").val();
							obj.sn = obj.xlh;
							obj.lymxid = obj.id;
							obj.lymxlx = 1;
							obj.dw = obj.jldw;
							delete obj.xlh;
							delete obj.id;
							receiptDetail.merge(obj);
							this_.buildReceiptRow(obj);
						});
					} else {
						receiptDetail.set([]);
						$("#"+this_.tbodyId).html('<tr class="no-data"><td class="text-center" colspan="10" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
					}
			    }
			}); 
		}
	},
	/**
	 * 初始化导航
	 */
	initNavigation : function(){
		Navigation(menuCode, "", "");
	},
	
	/**
	 * 初始化控件
	 */
	initWidget : function(){
		
		// 初始化日期选择控件
		$('.date-picker').datepicker({
			autoclose : true,
			clearBtn : true
		});
		// 初始化日期范围控件
		$('.date-range-picker').daterangepicker().prev().on("click", function() {
			$(this).next().focus();
		});
	},
	
	/**
	 * 初始化数据字典和枚举
	 */
	initDicAndEnum : function(){
		this.buildStore();
	},
	
	/**
	 * 获取组织机构
	 */
	getId : function(){
		return $.trim($("#id").val());
	},
	
	/**
	 * 获取组织机构
	 */
	getDprtcode : function(){
		return $.trim($("#dprtId").val());
	},
	
	/**
	 * 获取收货类型
	 */
	getShlx : function(){
		return $.trim($("#shlx").val());
	},
	
	/**
	 * 加载数据
	 */
	loadData : function(){
		
		var this_ = this;
		
		var id = $("#id").val();
		if(id){	// 按照id加载数据
			startWait();
			AjaxUtil.ajax({
				url: basePath+"/material/outin/detail",
				type: "post",
				dataType:"json",
				data:{
					id : id,
				},
				success:function(data){
					finishWait();
					this_.fillData(data);
					this_.loadByPageType();
			    }
			}); 
		}else{ // 重置数据
			this_.fillData({});
			this_.loadByPageType();
			receiptDetail.set([]);
		}
	},
	
	/**
	 * 填充数据
	 */
	fillData : function(data){
		
		var this_ = this;
		$("#shdh").val(data.shdh);
		$("#shlx").val(data.shlx || "10");
		$("#lyid").val(data.lyid);
		$("#lybh").val(data.lybh);
		$("#lydw").val(data.lydw);
		$("#bz").val(data.bz);
		$("#zt").val(data.zt);
		$("#dprtId").val(data.dprtcode || userJgdm);
		$("#shck option:first").prop("selected", 'selected');  
		// 收货日期
		if(data.shrq){
			$("#shrq").val(data.shrq.substr(0, 10));
			$("#shrq").datepicker("update");
		}else{
			TimeUtil.getCurrentDate(function(data){
				$("#shrq").val(data);
				$("#shrq").datepicker("update");
			});  
		}
		
		// 收货人
		if(data.shrid){
			$("#shrid").val(data.shrid);
			$("#shr").val(data.shr.displayName);
			$("#shbmid").val(data.shbmid);
		}else{
			$("#shrid").val(currentUser.id);
			$("#shr").val(currentUser.displayName);
			$("#shbmid").val(currentUser.bmdm);
		}
		
		$("#"+this_.tbodyId).html('<tr class="no-data"><td class="text-center" colspan="10" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
		if(data.details && data.details.length > 0){
			receiptDetail.set(data.details);
			$.each(data.details, function(i, detail){
				this_.buildReceiptRow(detail);
			});
		}
		
		// 切换显示按钮
		this_.switchButton();
	},
	
	/**
	 * 选择收货单
	 */
	chooseReceipt : function(){
		
		var this_ = this;
		open_win_receipt.show({
			multi : false,
			loaded : false,
			cleanData : true,
			dprtcode : this_.getDprtcode(),
			selected : $("#id").val(),
			callback: function(data){//回调函数
				$("#id").val(data.id);
				this_.loadData();
			}
		},true);
	},
	
	/**
	 * 选择来源单据
	 */
	chooseSourceDoc : function(){
		
		var this_ = this;
		open_win_contract.show({
			multi : false,
			loaded : false,
			cleanData : true,
			dprtcode : this_.getDprtcode(),
			selected : $("#lyid").val(),
			htlx : this_.getShlx(),
			callback: function(data){//回调函数
				$("#lyid").val(data.id);
				$("#lybh").val(data.hth);
				$("#lydw").val(data.xgfms);
				
				receiptDetail.set([]);
				$("#"+this_.tbodyId).html('<tr class="no-data"><td class="text-center" colspan="10" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
			}
		},true);
	},
	
	/**
	 * 切换收货类型
	 */
	switchShlx : function(){
		var this_ = this;
		var shlx = this_.getShlx();
		if(shlx == 10 || shlx == 20 || shlx == 31 || shlx == 32 || shlx == 40){
			$("#lybh").addClass("readonly-style").removeAttr("disabled");
			$("#lybh_btn").show();
			$("#lybh").val("");
		}else{
			$("#lybh").removeClass("readonly-style").attr("disabled", "disabled");
			$("#lybh_btn").hide();
			$("#lybh").val("无");
		}
		$("#lyid").val("");
		$("#lydw").val("");
		
		receiptDetail.set([]);
		$("#"+this_.tbodyId).html('<tr class="no-data"><td class="text-center" colspan="10" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
	},
	
	/**
	 * 新增/修改收货单明细弹窗
	 */
	receiptOpen : function(){
		var this_ = this;
		var existsIdList = [];
		var existsBjList = [];
		$.each(receiptDetail.getAll(), function(i, detail){
			if(detail.lymxid){
				existsIdList.push(detail.lymxid);
			}else if(detail.bjid){
				existsBjList.push(detail.bjid);
			}
		});
		open_win_receipt_info.show({
			multi : true,
			loaded : false,
			cleanData : false,
			dprtcode : this_.getDprtcode(),
			shlx : this_.getShlx(),
			lyid : $("#lyid").val(),
//			existsIdList : existsIdList,
//			existsBjList : existsBjList,
			callback: function(data){//回调函数
				
				$.each(data||[], function(i, obj){
					obj.lsckid = $("#shck").val();
					receiptDetail.merge(obj);
					this_.buildReceiptRow(obj);
				});
			}
		},true);
	},
	
	/**
	 * 新增收货明细行
	 */
	buildReceiptRow : function(obj){
		var this_ = this;
		obj.rowid = Math.uuid().toLowerCase();
		$("#"+this_.tbodyId+">.no-data").remove();
		var html = '';
		html += '<tr rowid="'+obj.rowid+'">';
		html += this_.isDisabledPage() ? '' : '<td style="vertical-align: middle;" class="text-center">';
		html += this_.isDisabledPage() ? '' : '<button class="line6 line6-btn" onclick="receipt.deleteReceiptRow(this)" type="button">';
		html += this_.isDisabledPage() ? '' : '<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>';
		html += this_.isDisabledPage() ? '' : '</button>';
		html += this_.isDisabledPage() ? '' : '</td>';
		html += '<td class="text-center" title="'+DicAndEnumUtil.getEnumName('materialTypeEnum', obj.hclx)+'" style="vertical-align: middle;">'+DicAndEnumUtil.getEnumName('materialTypeEnum', obj.hclx)+'</td>';
		html += '<td title="'+StringUtil.escapeStr(obj.bjh)+'" style="vertical-align: middle;">'+StringUtil.escapeStr(obj.bjh)+'</td>';
		html += '<td title="'+StringUtil.escapeStr(obj.bjmc)+'" style="vertical-align: middle;">'+StringUtil.escapeStr(obj.bjmc)+'</td>';
		html += '<td style="vertical-align: middle;">';
		html += '<input name="pch" type="text" maxlength="50" onkeyup="receipt.validateNonChinese(this)" class="form-control" value="'+StringUtil.escapeStr(obj.pch)+'" placeholder="不填写时系统自动生成"/>';
		html += '</td>';
		html += '<td>';
		html += '<div class="input-group col-xs-12">';
		html += '<input name="xlh" class="form-control" maxlength="50" onblur="receipt.limitShsl(this)" onkeyup="receipt.validateNonChinese(this)"  value="'+StringUtil.escapeStr(obj.sn)+'" type="text">';
		html += '</div>';
		html += '</td>';
		html += '<td style="vertical-align: middle;">';
		html += '<div class="input-group col-xs-12">';
		if(obj.sn){
			html += '<input name="sl" maxlength="10" disabled="disabled" class="form-control" onkeyup="receipt.validateDecimal(this)" type="text" value="'+StringUtil.escapeStr(obj.sl || 1)+'">';
		}else{
			html += '<input name="sl" maxlength="10" class="form-control" onkeyup="receipt.validateDecimal(this)" type="text" value="'+StringUtil.escapeStr(obj.sl)+'">';
		}
		html += '<span name="dw" class="input-group-addon" style="min-width: 35px;">'+StringUtil.escapeStr(obj.dw)+'</span>';
		html += '</div>';
		html += '</td>';
		html += '<td style="vertical-align: middle;" class="text-center">';
		if((obj.id && obj.isZj) || (!obj.id && obj.hclx != 2 && obj.hclx != 3)){
			html += '<input type="checkbox" name="isZj" checked="checked" onchange="receipt.switchStore(this)"/>';
		}else{
			html += '<input type="checkbox" name="isZj" onchange="receipt.switchStore(this)"/>';
		}
		html += '</td>';
		html += '<td style="vertical-align: middle;" name="storeTd"></td>';
		html += '<td style="vertical-align: middle;">';
		html += '<div class="input-group col-xs-12">';
		html += '<input name="cqbh" class="form-control readonly-style" ondblclick="receipt.choosePropertyRight(this)" readonly="readonly" type="text" value="'+StringUtil.escapeStr(obj.cqbh)+'">';
		html += '<span class="input-group-addon btn btn-default notView" onclick="receipt.choosePropertyRight(this)" style="display: table-cell;">';
		html += '<i class="icon-search"></i>';
		html += '</span>';
		html += '</div>';
		html += '</td>';
		html += '<input type="hidden" name="bjid" value="'+StringUtil.escapeStr(obj.bjid)+'" />';
		html += '</tr>';
		$("#" + this_.tbodyId).append(html);
		this_.switchStore(obj.rowid);
	},
	
	/**
	 * 删除收货明细行
	 */
	deleteReceiptRow : function(obj){
		var this_ = this;
		var $tr = $(obj).parents("tr[rowid]");
		var rowid = $tr.attr("rowid");
		// 列表移除该列
		$tr.remove();
		// 如果列表无数据了，则添加暂无数据
		if($("#"+this_.tbodyId+">tr").length == 0){
			$("#"+this_.tbodyId).append('<tr class="no-data"><td class="text-center" colspan="10" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
		}
		// 删除页面数据
		receiptDetail.remove(rowid);
	},
	
	/**
	 * 根据是否质检切换上架与临时存放位置
	 */
	switchStore : function(obj){
		var this_ = this;
		var $tr = null;
		if (typeof (obj) == "undefined") {
			return;
		}
		if (typeof (obj) == "string") {
			$tr = $("tr[rowid='"+obj+"']");
		}
		if (typeof (obj) == "object") {
			$tr = $(obj).parent().parent();
		}
		
		var rowid = $tr.attr("rowid");
		var detail = receiptDetail.get(rowid);
		if($tr.find("[name='isZj']").is(":checked")){
			// 生成临时仓库库位内容
			this_.buildTempStoreContent($tr, detail);
		}else{
			// 生成仓库库位内容
			this_.buildStoreContent($tr, detail.shelves);
		}
	},
	
	/**
	 * 生成临时仓库库位内容
	 */
	buildTempStoreContent : function($tr, detail){
		var this_ = this;
		var html = '';
		if(this_.isDisabledPage()){
			html = '无临时存放位置';
		}else{
			html = '<a href="javascript:;" onclick="receipt.storagePosition(this)">临时存放位置</a>';
		}
		if(detail.lsckid){
			var store = this_.getStoreByCkid(detail.lsckid);
			var storage = this_.getStorageLocationByKwid(detail.lskwid);
			html = '<a href="javascript:;" onclick="receipt.storagePosition(this)">临时存放位置</a>';
			html += '<span style="padding-left:10px;">';
			html += StringUtil.escapeStr(store.ckh) + "   ";
			html += StringUtil.escapeStr(storage.kwh);
			html += '</span>';
		}
		$tr.find("[name='storeTd']").html(html);
		$tr.find("[name='storeTd']").attr("title", $tr.find("[name='storeTd']>span").text());
	},
	
	/**
	 * 根据仓库id获取仓库
	 */
	getStoreByCkid : function(ckid){
		var store = {};
		if(!ckid){
			return store;
		}
		$.each(userStoreList, function(index, row){
			if(ckid == row.id){
				store = row;
				return false;
			}
		})
		return store;
	},
	
	/**
	 * 根据库位id获取库位
	 */
	getStorageLocationByKwid : function(kwid){
		var storage = {};
		if(!kwid){
			return storage;
		}
		$.each(userStoreList, function(index, row){
			for(var i = 0 ; i < row.storageList.length ; i++){
				if(kwid == row.storageList[i].id){
					storage = row.storageList[i];
				}
		 	}
		})
		return storage;
	},
	
	/**
	 * 上架
	 */
	shelvesOpen : function(obj){
		var this_ = this;
		var $tr = $(obj).parents("tr[rowid]");
		var rowid = $tr.attr("rowid");
		var detail = receiptDetail.get(rowid);
		detail.sn = $tr.find("[name='xlh']").val();
		detail.sl = $tr.find("[name='sl']").val();
		if(!detail.sl){
			AlertUtil.showErrorMessage("请先填写收货数量！");
			return false;
		}
		shelf.show({
			detail : detail,
			dprtcode : this_.getDprtcode(),
			callback : function(data){
				// 生成仓库库位内容
				this_.buildStoreContent($tr, data);
			}
		});
	},
	
	/**
	 * 生成仓库库位内容
	 */
	buildStoreContent : function($tr, data){
		var this_ = this;
		var html = '';
		var text = '';
		if(this_.isDisabledPage()){
			html = '未上架';
			text = '查看';
		}else{
			html = '<a href="javascript:;" onclick="receipt.shelvesOpen(this)">上架</a>';
			text = '修改';
		}
		if(data && data.length > 0 && data[0].ckid){
			html = '<a href="javascript:;" onclick="receipt.shelvesOpen(this)">' + text+ '</a>';
			$.each(data||[], function(i, shelf){
				if(i == 0){
					html += '<span style="padding-left:10px;">';
					html += StringUtil.escapeStr(shelf.ckh) + "   ";
				}
				
				if(i == data.length - 1){
					html += StringUtil.escapeStr(shelf.kwh);
					html += '</span>';
				}else{
					html += shelf.kwh + ",";
				}
			});
		}
		
		$tr.find("[name='storeTd']").html(html);
		$tr.find("[name='storeTd']").attr("title", $tr.find("[name='storeTd']>span").text());
	},
	
	/**
	 * 临时存放位置
	 */
	storagePosition : function(obj){
		var this_ = this;
		var $tr = $(obj).parents("tr[rowid]");
		var rowid = $tr.attr("rowid");
		var detail = receiptDetail.get(rowid);
		position.show({
			detail : detail,
			dprtcode : this_.getDprtcode(),
			callback : function(data){
				this_.buildTempStoreContent($tr, detail);
			}
		});
	},
	
	/**
	 * 选择产权
	 */
	choosePropertyRight : function(obj){
		var this_ = this;
		var $tr = $(obj).parents("tr[rowid]");
		var rowid = $tr.attr("rowid");
		var detail = receiptDetail.get(rowid);
		cqModal.show({
			selected : detail.cqid,
			dprtcode : this_.getDprtcode(),
			callback : function(data){
				detail.cqid = data.id;
				$tr.find("[name='cqbh']").val(data.cqbh);
			}
		});
	},
	
	/**
	 * 查看上架信息
	 */
	shelvesView : function(){
		$("#shelves_view_alert").modal("show");
	},
	
	/**
	 * 获取页面数据
	 */
	getData : function(){
		var this_ = this;
		var obj = {};
		obj.id = this_.getId();
		obj.dprtcode = this_.getDprtcode();
		obj.shlx = this_.getShlx();
		obj.shdh = $.trim($("#shdh").val());
		obj.shrq = $.trim($("#shrq").val());
		obj.lyid = $.trim($("#lyid").val());
		obj.lybh = $.trim($("#lybh").val());
		obj.lymc = $.trim($("#lymc").val());
		obj.lydw = $.trim($("#lydw").val());
		obj.shck = $.trim($("#shck").val());
		obj.bz = $.trim($("#bz").val());
		obj.details = receiptDetail.getAll();
		$.each(obj.details, function(i, detail){
			var $tr = $("[rowid='"+detail.rowid+"']");
			detail.pch = $tr.find("[name='pch']").val();
			detail.sn = $tr.find("[name='xlh']").val();
			detail.sl = $tr.find("[name='sl']").val();
			detail.dw = $tr.find("[name='dw']").text();
			detail.isZj = $tr.find("[name='isZj']").is(":checked") ? 1 : 0;
			if(detail.isZj == 0){
				detail.lsckid == "";
				detail.lskwid == "";
				detail.lscfwz == "";
			}else if(detail.isZj == 1){
				detail.shelves = [];
			}
		});
		
		return obj;
	},
	
	/**
	 * 验证数据
	 */
	validateData : function(obj){
		AlertUtil.hideModalFooterMessage();
		var details = obj.details;
		var msg = "";
		if(!details || details.length == 0){
			AlertUtil.showModalFooterMessage("请至少添加一条收货信息！");
			return false;
		}
		
		$.each(obj.details, function(i, detail){
			if(detail.pch && !/^[\x00-\xff]*$/.test(detail.pch)){
				msg = "批次号不能输入中文！";
				return false;
			}
			if(detail.sn && !/^[\x00-\xff]*$/.test(detail.sn)){
				msg = "序列号不能输入中文！";
				return false;
			}
			if(detail.gljb == 3 && !detail.sn){
				msg = "部件："+detail.bjh+"为序列号管理件，序列号必填！";
				return false;
			}
			if(!detail.sl){
				msg = "请填写收货数量！";
				return false;
			}else if(!/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(detail.sl) || detail.sl == "0"){
				msg = "收货数量必须大于零，且小数点后最多保留两位！";
				return false;
			}
			
			if(detail.shelves && detail.shelves.length > 0){
				var sjsl = 0;
				var shsl = parseFloat(detail.sl);
				$.each(detail.shelves, function(j, shelf){
					sjsl += parseFloat(shelf.sjsl);
				});
				if(shsl.toFixed(2) != sjsl.toFixed(2)){
					msg = "上架数量之和必须和收货数量相同！";
					return false;
				}
			}
		});
		
		if(msg){
			AlertUtil.showModalFooterMessage(msg);
			return false;
		}
		
		return true;
	},
	
	/**
	 * 保存数据
	 */
	saveData : function(){
		var this_ = this;
		var obj = this_.getData();
		obj.zt = 1;
		if(!this_.validateData(obj)){
			return false;
		}
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/outin/save",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				$("#id").val(data.id);
				this_.loadData();
				AlertUtil.showMessage("保存成功！");
			}
		});
	},
	
	/**
	 * 提交数据
	 */
	submitData : function(){
		var this_ = this;
		var obj = this_.getData();
		obj.zt = 2;
		if(!this_.validateData(obj)){
			return false;
		}
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/outin/submit",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				$("#id").val(data.id);
				this_.loadData();
				AlertUtil.showMessage("提交成功！");
			}
		});
	},
	
	/**
	 * 切换显示按钮
	 */
	switchButton : function(){
		
		var this_ = this;
		$("#save_btn,#submit_btn,#delete_btn,#revoke_btn").hide()
		
		var zt = $("#zt").val();
		if(!zt || zt == 1){
			$("#save_btn,#submit_btn").show();
		}
		if(zt == 1){
			$("#delete_btn").show();
		}
		if(zt == 2){
			this_.disabledPage();
			$("#revoke_btn").show();
		}
	},
	
	/**
	 * 验证小数
	 */
	validateDecimal : function(obj){
		var value = $(obj).val();
		while(!(/^(0|[1-9]\d*)(\.[0-9]?[0-9]?)?$/.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
	},
	
	/**
	 * 验证非中文
	 */
	validateNonChinese : function(obj){
		var value = $(obj).val();
		while(!(/^[\x00-\xff]*$/.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
	},
	
	/**
	 * 删除数据
	 */
	deleteData : function(){
		var this_ = this;
		AlertUtil.showConfirmMessage("是否确定删除？", {callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/material/outin/delete",
				type:"post",
				data:{id : $("#id").val()},
				dataType:"json",
				success:function(data){
					finishWait();
					$("#id").val("");
					this_.loadData();
					AlertUtil.hideModalFooterMessage();
					AlertUtil.showMessage("删除成功！");
				}
			});
		}});
	},
	
	/**
	 * 撤销数据
	 */
	revokeData : function(){
		var this_ = this;
		AlertUtil.showConfirmMessage("是否确定撤销？", {callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/material/outin/revoke",
				type:"post",
				data:{id : $("#id").val()},
				dataType:"json",
				success:function(data){
					finishWait();
					$("#id").val("");
					this_.loadData();
					AlertUtil.hideModalFooterMessage();
					AlertUtil.showMessage("撤销成功！");
				}
			});
		}});
	},
	
	/**
	 * 限制收货数量
	 */
	limitShsl : function(obj){
		var $tr = $(obj).parents("tr");
		var xlh = $tr.find("[name='xlh']").val();
		if(xlh){
			$tr.find("[name='sl']").attr("disabled","disabled").val("1");
		}else{
			$tr.find("[name='sl']").removeAttr("disabled");
		}
	},
	
	/**
	 * 根据页面类型加载页面
	 */
	loadByPageType : function(){
		var this_ = this;
		if(this_.isDisabledPage()){
			this_.disabledPage();
		}else{
			this_.resotrePage();
		}
		if(this_.isViewPage()){
			$("#shdh").removeClass("readonly-style");
			$("#shdh_btn").hide();
			$("#save_btn,#submit_btn,#delete_btn,#revoke_btn").remove();
		}
	},
	
	/**
	 * 禁用页面
	 */
	disabledPage : function(){
		$("#demand_apply_body").find("input,select,textarea").attr("disabled","disabled");
		$("#demand_apply_body .readonly-style:not(#shdh)").removeClass("readonly-style");
		$("#position_open_win").find("input,select,textarea").attr("disabled","disabled");
		$("#shelf_ck,#shelf_kccb,#shelf_biz,#shelf_jz,#shelf_jzbz").attr("disabled","disabled");
		$(".notView").hide();
	},
	
	/**
	 * 还原页面
	 */
	resotrePage : function(){
		$("#demand_apply_body").find("input:not(#shr,[name='sl']),select,textarea").removeAttr("disabled");
		$("#position_open_win").find("input,select,textarea").removeAttr("disabled");
		$("#shelf_ck,#shelf_kccb,#shelf_biz,#shelf_jz,#shelf_jzbz").removeAttr("disabled");
		$(".notView").show();
		
		var shlx = $("#shlx").val();
		if(shlx == 10 || shlx == 20 || shlx == 31 || shlx == 32 || shlx == 40){
			$("#lybh").addClass("readonly-style").removeAttr("disabled");
			$("#lybh_btn").show();
		}else{
			$("#lybh").removeClass("readonly-style").attr("disabled", "disabled");
			$("#lybh_btn").hide();
			$("#lybh").val("无");
		}
	},
	
	/**
	 * 是否查看界面
	 */
	isViewPage : function(){
		return $("#type").val() == "view";
	},
	
	/**
	 * 是否禁用页面
	 */
	isDisabledPage : function(){
		var type = $("#type").val();
		var zt = $("#zt").val();
		if(type == "view" || zt == 2){
			return true;
		}
		return false;
	},
	
	/**
	 * 生成仓库下拉框
	 */
	buildStore : function(){
		var this_ = this;
		var dprtcode = this_.getDprtcode();
		var storeHtml = "";
		$.each(userStoreList, function(index, row){
			if(dprtcode == row.dprtcode){
	 			storeHtml += "<option value=\""+row.id+"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>"
			}
		})
		$("#shck").html(storeHtml);
	},
	
	/**
	 * 切换收货仓库
	 */
	switchShck : function(){
		var this_ = this;
		var shck = $("#shck").val();
		var store = this_.getStoreByCkid(shck);
		var storage = store.storageList[0];
		$.each(receiptDetail.getAll(), function(i, detail){
			var $tr = $("[rowid='"+detail.rowid+"']");
			if($tr.find("[name='isZj']").is(":checked")){
				if(!detail.lsckid){
					detail.lsckid = shck;
					detail.lskwid = null;
				}
				// 生成临时仓库库位内容
				this_.buildTempStoreContent($tr, detail);
			}else{
				$.each(detail.shelves || [], function(j, shelf){
					if(!shelf.ckid){
						shelf.ckid = shck;
						shelf.cklb = store.cklb;
						shelf.ckh = store.ckh;
						shelf.ckmc = store.ckmc;
						shelf.kwid = storage.id;
						shelf.kwh = storage.kwh;
					}
				});
				// 生成仓库库位内容
				this_.buildStoreContent($tr, detail.shelves);
			}
		});
	},
	
	/**
	 * 批量设置产权
	 */
	batchSetPropertyRight : function(){
		var this_ = this;
		cqModal.show({
			dprtcode : this_.getDprtcode(),
			callback : function(data){
				$.each(receiptDetail.getAll(), function(i, detail){
					var $tr = $("[rowid='"+detail.rowid+"']");
					detail.cqid = data.id;
					$tr.find("[name='cqbh']").val(data.cqbh);
				});
			}
		});
	},
};


function customResizeHeight(bodyHeight, tableHeight){
	$("#demand_apply_body").removeAttr("style");
	var panelFooter=$("#demand_apply_body").siblings(".panel-footer").outerHeight()||0
	var scrollDiv=$("#scrollDiv").outerHeight()||0;
	var panelBody=bodyHeight-panelFooter-scrollDiv;
	$("#receipt_table").parent().css({"height":(panelBody)+"px","overflow":"auto"});
}