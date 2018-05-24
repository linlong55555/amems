

/**
 * 航材入库上架-上架页面
 */
var materialShelf = {
		
	id : "material_shelf",
	
	tbodyId : "material_shelf_list",
	
	certificateUtil : null,
	
	param: {
		id:null,
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	},
	
	/**
	 * 显示模态框
	 */
	show : function(param){
		var this_ = this;
		if(param){
			$.extend(this_.param, param);
		}
		// 初始化数据字典和枚举
		this_.initDicAndEnum();
		// 初始化证书
		this_.initCertificate();
		this_.load();
		$("#"+this_.id).modal("show");
	},
	
	/**
	 * 初始化数据字典和枚举
	 */
	initDicAndEnum : function(){
		var this_ = this;
		// 生成仓库下拉框
		this_.buildStore();
		// 生成库位下拉框
		this_.buildStorageLocation();
		// 生成币种下拉框
		$("#biz").empty();
		DicAndEnumUtil.registerDic('3', 'biz', this_.param.dprtcode);
//		$("#jzbz").empty();
//		DicAndEnumUtil.registerDic('3', 'jzbz', this_.param.dprtcode);
		// 生成航材来源下拉框
		$("#hcly").empty();
	   	DicAndEnumUtil.registerDic('85',"hcly",this_.param.dprtcode);
	   	// 生产器材状态下拉框
	   	$("#qczt").empty();
	   	DicAndEnumUtil.registerDic('86',"qczt",this_.param.dprtcode);
	},
	
	/**
	 * 加载数据
	 */
	load : function(){
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
			url: basePath+"/material/outin/shelfdetail",
			type: "post",
			dataType:"json",
			data:{
				id : this_.param.id,
			},
			success:function(data){
				finishWait();
				this_.fillData(data);
				this_.loadCertificate();
		    }
		}); 
	},
	
	/**
	 * 填充数据
	 */
	fillData : function(data){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();
		
		$("#hclx").val(DicAndEnumUtil.getEnumName('materialTypeEnum', data.hCMainData ? data.hCMainData.hclx : ""));
		$("#bjh").val(data.bjh);
		$("#bjmc").val(StringUtil.escapeStr(data.ywms)+" "+StringUtil.escapeStr(data.zwms));
		$("#kcsl").val(data.kcsl);
		$("#dw").text(data.jldw||"");
		$("#pch").val(data.pch);
		$("#sn").val(data.sn);
		
		if(data.biz){
			$("#biz").val(data.biz);
		}
		if(data.jzbz){
			$("#jzbz").val(data.jzbz);
		}
		if(data.hcly){
			$("#hcly").val(data.hcly);
		}
		$("#kccb").val(data.kccb ? parseFloat(data.kccb).toFixed(2) : "");
		$("#jz").val(data.jz ? parseFloat(data.jz).toFixed(2) : "");
		$("#grn").val(data.grn);
		$("#scrq").val((data.scrq || "").substr(0, 10));
		$("#hjsm").val((data.hjsm || "").substr(0, 10));
		$("#tsn").val(TimeUtil.convertToHour(data.tsn, TimeUtil.Separator.COLON));
		$("#tso").val(TimeUtil.convertToHour(data.tso, TimeUtil.Separator.COLON));
		$("#csn").val(data.csn);
		$("#cso").val(data.cso);
		$("#cfyq").val(data.cfyq);
		
		$("#shdh").val(StringUtil.escapeStr(data.receipt ? data.receipt.shdh : ""));
		var zjzt = "";
		if(!data.inspection){
			zjzt = "无需质检";
		}else if(data.inspection.zt == -1 || data.inspection.zt == 1){
			zjzt = "质检中";
		}else{
			zjzt = StringUtil.escapeStr(data.inspection.jydh);
		}
		$("#jydh").val(zjzt);
		$("#shlx").val(DicAndEnumUtil.getEnumName('receiptTypeEnum', data.receipt ? data.receipt.shlx : ""));
		$("#shr").val(StringUtil.escapeStr(data.receipt ? (data.receipt.shr ? data.receipt.shr.displayName : "") : ""));
		$("#shsj").val(((data.receipt ? data.receipt.shrq : "") || "").substr(0, 10));
		$("#lydw").val(StringUtil.escapeStr(data.receipt ? data.receipt.lydw : ""));
		
		$("#scrq,#hjsm").datepicker("update");
		
		$(".selectCheckbox").prop("checked",false);
		$('#additionalInfo').removeClass("in").height(0);
		$('#certificate_info').removeClass("in").height(0);
		
		$("#"+this_.tbodyId).html('<tr class="no-data"><td class="text-center" colspan="4" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
		this_.addRow();
	},
	
	/**
	 * 生成仓库下拉框
	 */
	buildStore : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var storeHtml = "";
		$.each(userStoreList, function(index, row){
			if(dprtcode == row.dprtcode){
	 			storeHtml += "<option value=\""+row.id+"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>"
			}
		})
		$("#ck").html(storeHtml);
	},
	
	/**
	 * 生成库位下拉框
	 */
	buildStorageLocation : function($select){
		var this_ = this;
		var option = "";
		var ckid = $("#ck").val();
		$.each(userStoreList, function(index, row){
			if(row.id == ckid){
				for(var i = 0 ; i < row.storageList.length ; i++){
			 		var storage = row.storageList[i];
		 			option += '<option value="'+storage.id+'" kwh="'+StringUtil.escapeStr(storage.kwh)+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
			 	}
			}
		});
		if($select){
			$select.html(option);
			$select.selectpicker('refresh');
		}else{
			$("#kw_onekey").html(option);
			$("#"+this_.tbodyId+">tr select[name='shelf_kw']").html(option);
			$("#"+this_.tbodyId+">tr select[name='shelf_kw']").selectpicker('refresh');
		}
	},
	
	/**
	 * 一键设置库位
	 */
	setStorageLocation : function(){
		var this_ = this;
		var kwid = $("#kw_onekey").val();
		$("#"+this_.tbodyId+">tr select[name='shelf_kw']").val(kwid);
		$("#"+this_.tbodyId+">tr select[name='shelf_kw']").selectpicker('refresh');
	},
	
	/**
	 * 添加行
	 */
	addRow : function(obj){
		var this_ = this;
		var sn = $.trim($("#sn").val());
		if(sn && $("#"+this_.tbodyId+">tr:not(.no-data)").length >= 1){
			AlertUtil.showErrorMessage("序列号管理的部件只能添加一个库位信息！");
			return false;
		}
		obj = obj || {};
		$("#"+this_.tbodyId+">.no-data").remove();
		var str  = '<tr>';
	    str += '<td class="text-center">';
	    str += '<button class="line6 line6-btn" onclick="materialShelf.deleteRow(this)" type="button">';
	    str += '<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>';
	    str += '</button>';
	    str += '</td>';
	    str += '<td>';
	    str += '<select name="shelf_kw" class="form-control selectpicker" data-live-search="true" data-container="#material_shelf" data-size="10">';
	    str += '</select>';
	    str += '</td>';
	    str += '<td><input name="shelf_sjsl" maxlength="10" onkeyup="materialShelf.validateDecimal(this)" type="text" class="form-control" value="'+(obj.sjsl || ( sn ? "1" : ""))+'"/></td>';
	    str += '<td>'+StringUtil.escapeStr(sn)+'</td>';
	    str += '<input id="shelf_id" type="hidden" value="'+(obj.id || "")+'"/>';
	    str += '</tr>';
	    
	    var $tr = $(str);
	    $tr.appendTo("#"+this_.tbodyId);
	    
	    this_.buildStorageLocation($tr.find("[name='shelf_kw']"));
	    if(obj.kwid){
	    	$tr.find("[name='shelf_kw']").val(obj.kwid);
	    }else{
	    	$tr.find("[name='shelf_kw']").val($("#kw_onekey").val());
	    }
	},
	
	/**
	 * 删除行
	 */
	deleteRow : function(obj){
		var this_ = this;
		if($("#"+this_.tbodyId+">tr:not(.no-data)").length == 1){
			AlertUtil.showErrorMessage("至少保留一个库位信息！");
			return false;
		}
		$(obj).parent().parent("tr").remove();
		if($("#"+this_.tbodyId+">tr").length == 0){
			$("#"+this_.tbodyId).append('<tr class="no-data"><td class="text-center" colspan="4" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
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
	 * 验证整数
	 */
	validateCycle : function(obj){
		var value = $(obj).val();
		while(!(/^(0|[1-9]\d*)$/.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
	},
	
	/**
	 * 验证时间
	 */
	validateTime : function(obj){
		var value = $(obj).val();
		while(!(/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		$(obj).val(value);
	},
	
	/**
	 * 初始化证书
	 */
	initCertificate : function(){
		var this_ = this;
		this_.certificateUtil = new CertificateUtil({
			parentId : this_.id,
			dprtcode : this_.param.dprtcode,
			tbody : $("#material_shelf_certificate"),
			container : '#' + this_.id,
			ope_type : 1
		});
	},
	
	/**
	 * 加载证书
	 */
	loadCertificate : function(){
		var this_ = this;
		this_.certificateUtil.load({
			bjh : $.trim($("#bjh").val()),
			xlh : $.trim($("#sn").val()),
			pch : $.trim($("#pch").val()),
			dprtcode :this_.param.dprtcode
		});
	},
	
	/**
	 * 获取数据
	 */
	getData : function(){
		var this_ = this;
		var obj = {};
		obj.kcid = this_.param.id,
		obj.dprtcode = this_.param.dprtcode,
		obj.bjh = $.trim($("#bjh").val());
		obj.xlh = $.trim($("#sn").val());
		obj.pch = $.trim($("#pch").val());
		obj.kccb = $.trim($("#kccb").val());
		obj.biz = $.trim($("#biz").val());
		obj.jz = $.trim($("#jz").val());
		obj.jzbz = $.trim($("#jzbz").val());
		obj.dw = $.trim($("#dw").text());
		obj.hcly = $.trim($("#hcly").val());
		obj.grn = $.trim($("#grn").val());
		obj.scrq = $.trim($("#scrq").val());
		obj.hjsm = $.trim($("#hjsm").val());
		obj.tsn = TimeUtil.convertToMinute($("#tsn").val()) || null;
		obj.tso = TimeUtil.convertToMinute($("#tso").val()) || null;
		obj.csn = $.trim($("#csn").val());
		obj.cso = $.trim($("#cso").val());
		obj.qczt = $.trim($("#qczt").val());
		obj.cfyq = $.trim($("#cfyq").val());
		obj.certificates = this_.certificateUtil.getData();
		obj.shelves = [];
		$("#"+this_.tbodyId+">tr:not(.no-data)").each(function(){
			var shelf = {};
			var $tr = $(this);
			shelf.ckid = $("#ck").val();
			shelf.ckh = $("#ck option:selected").attr("ckh");
			shelf.ckmc = $("#ck option:selected").attr("ckmc");
			shelf.cklb = $("#ck option:selected").attr("cklb");
			shelf.kccb = obj.kccb;
			shelf.biz = obj.biz;
			shelf.jz = obj.jz;
			shelf.jzbz = obj.jzbz;
			
			shelf.kwid = $tr.find("[name='shelf_kw']").val();
			shelf.kwh = $tr.find("[name='shelf_kw'] option:selected").attr("kwh");
			shelf.sjsl = $tr.find("[name='shelf_sjsl']").val();
			shelf.jldw = obj.dw;
			
			obj.shelves.push(shelf);
		});
		return obj;
	},
	
	/**
	 * 验证数据
	 */
	validateData : function(obj){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();
		
		var shsl = parseFloat($("#kcsl").val());
		var xlh = $("#sn").val();
		var total = 0;
		var msg = "";
		$("#"+this_.tbodyId+">tr:not(.no-data)").each(function(){
			var $tr = $(this);
			var sjsl = $tr.find("[name='shelf_sjsl']").val();
			var kw = $tr.find("[name='shelf_kw']").val();
			if(!kw){
				msg = "请选择库位！";
				return false;
			}
			if(!sjsl){
				msg = "请填写上架数量！";
				return false;
			}else if(!/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(sjsl) || sjsl == "0"){
				msg = "上架数量必须大于零，且小数点后最多保留两位！";
				return false;
			}else if(xlh && sjsl != "1"){
				msg = "序列号管理的部件，上架数量只能为1！";
				return false;
			}
			total += parseFloat(sjsl);
		});
		
		var kccb = $("#kccb").val();
		var jz = $("#jz").val();
		if(kccb && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(kccb)){
			msg = "成本必须大于等于零，且小数点后最多保留两位！";
		}
		if(jz && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(jz)){
			msg = "价值必须大于等于零，且小数点后最多保留两位！";
		}
		
		var tsn = $("#tsn").val();
		var tso = $("#tso").val();
		var csn = $.trim($("#csn").val());
		var cso = $.trim($("#cso").val());
		if(tsn && !/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/.test(tsn)){
			msg = "TSN格式不正确！";
		}else if(tso && !/^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/.test(tso)){
			msg = "TSO格式不正确！";
		}else if(csn && !/^(0|[1-9]\d*)$/.test(csn)){
			msg = "CSN格式不正确！";
		}else if(cso && !/^(0|[1-9]\d*)$/.test(cso)){
			msg = "CSO格式不正确！";
		}
		
		if(msg){
			AlertUtil.showModalFooterMessage(msg, this_.id);
			return false;
		}else if(shsl.toFixed(2) != total.toFixed(2)){
			AlertUtil.showModalFooterMessage("上架数量之和必须和待上架数量相同！", this_.id);
			return false;
		}
		return true;
	},
	
	/**
	 * 上架提交
	 */
	submitData : function(){
		var this_ = this;
		var obj = this_.getData();
		if(!this_.validateData(obj)){
			return false;
		}
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/outin/putonshelf",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage("上架成功！");
				$("#"+this_.id).modal("hide");
				materialStockIn.search();
			}
		});
	},
};