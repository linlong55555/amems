
/**
 * 上架
 */
var shelf = {
		
	id : "shelf_open_win",
	
	tbodyId : "shelf_tbody",
	
	param: {
		detail:null,//收货明细
		callback:function(){},//回调函数
		dprtcode:userJgdm,//默认登录人当前机构代码
	},
	
	show : function(param){
		AlertUtil.hideModalFooterMessage(this.id);
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		// 初始化数据字典和枚举
		this.initDicAndEnum();
		// 加载数据
		this.load();
	},
	
	/**
	 * 初始化数据字典和枚举
	 */
	initDicAndEnum : function(){
		// 生成仓库下拉框
		this.buildStore();
		// 生成库位下拉框
		this.buildStorageLocation();
		// 生成币种下拉框
		$("#shelf_biz").empty();
		DicAndEnumUtil.registerDic('3', 'shelf_biz', this.param.dprtcode);
		$("#shelf_jzbz").empty();
		DicAndEnumUtil.registerDic('3', 'shelf_jzbz', this.param.dprtcode);
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
	 * 加载数据
	 */
	load : function(){
		var this_ = this;
		var detail = this_.param.detail;
		$("#shelf_wllb").val(DicAndEnumUtil.getEnumName('materialTypeEnum',detail.hclx));
		$("#shelf_bjh").val(detail.bjh);
		$("#shelf_bjmc").val(detail.bjmc);
		$("#shelf_shsl").val(detail.sl);
		$("#shelf_unit").html(detail.dw);
		$("#shelf_kccb").val(detail.cb ? parseFloat(detail.cb).toFixed(2) : "");
		$("#shelf_jz").val(detail.jz ? parseFloat(detail.jz).toFixed(2) : "");
		$("#shelf_biz option:first").prop("selected", 'selected');  
		$("#shelf_jzbz option:first").prop("selected", 'selected');  
		detail.cbbz && $("#shelf_biz").val(detail.cbbz);
		detail.jzbz && $("#shelf_jzbz").val(detail.jzbz);
		
		$("#"+this_.tbodyId).empty();
		if(detail.shelves && detail.shelves.length > 0){
			$.each(detail.shelves || [], function(){
				var shelf = this;
				$("#shelf_ck").val(shelf.ckid);
				$("#shelf_kccb").val(shelf.kccb ? parseFloat(shelf.kccb).toFixed(2) : "");
				$("#shelf_jz").val(shelf.jz ? parseFloat(shelf.jz).toFixed(2) : "");
				if(shelf.biz){
					$("#shelf_biz").val(shelf.biz);
				}
				if(shelf.jzbz){
					$("#shelf_jzbz").val(shelf.jzbz);
				}
				this_.addRow(shelf);
				this_.buildStorageLocation($("#shelf_kw_onekey"));
			});
		}else{
			$("#"+this_.tbodyId).append('<tr class="no-data"><td class="text-center" colspan="4" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
		}
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
		$("#shelf_ck").html(storeHtml);
		$("#shelf_ck").val($("#shck").val());
	},
	
	/**
	 * 生成库位下拉框
	 */
	buildStorageLocation : function($select){
		var this_ = this;
		var option = "";
		var ckid = $("#shelf_ck").val();
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
			$("#shelf_kw_onekey").html(option);
			$("#shelf_kw_onekey").selectpicker('refresh');
			$("#"+this_.tbodyId+">tr select[name='shelf_kw']").html(option);
			$("#"+this_.tbodyId+">tr select[name='shelf_kw']").selectpicker('refresh');
		}
	},
	
	/**
	 * 一键设置库位
	 */
	setStorageLocation : function(){
		var this_ = this;
		var kwid = $("#shelf_kw_onekey").val();
		$("#"+this_.tbodyId+">tr select[name='shelf_kw']").val(kwid);
		$("#"+this_.tbodyId+">tr select[name='shelf_kw']").selectpicker('refresh');
	},
	
	/**
	 * 添加行
	 */
	addRow : function(obj){
		var this_ = this;
		var detail = this_.param.detail;
		obj = obj || {};
		$("#"+this_.tbodyId+">.no-data").remove();
		var str  = '<tr>';
	    str += receipt.isDisabledPage() ? '' : '<td class="text-center">';
	    str += receipt.isDisabledPage() ? '' : '<button class="line6 line6-btn" onclick="shelf.deleteRow(this)" type="button">';
	    str += receipt.isDisabledPage() ? '' : '<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>';
	    str += receipt.isDisabledPage() ? '' : '</button>';
	    str += receipt.isDisabledPage() ? '' : '</td>';
	    str += '<td style="overflow: visible;">';
	    str += '<select name="shelf_kw" class="form-control selectpicker" data-live-search="true" data-container="#shelf_open_win" data-size="10">';
	    str += '</select>';
	    str += '</td>';
	    str += '<td><input name="shelf_sjsl"  maxlength="10" onkeyup="shelf.validateDecimal(this)" type="text" class="form-control" value="'+(obj.sjsl || ( detail.sn ? "1" : ""))+'"/></td>';
	    str += '<td>'+StringUtil.escapeStr(detail.sn)+'</td>';
	    str += '<input id="shelf_id" type="hidden" value="'+(obj.id || "")+'"/>';
	    str += '</tr>';
	    
	    var $tr = $(str);
	    $tr.appendTo("#"+this_.tbodyId);
	    
	    this_.buildStorageLocation($tr.find("[name='shelf_kw']"));
	    if(obj.kwid){
	    	$tr.find("[name='shelf_kw']").val(obj.kwid);
	    }else{
	    	$tr.find("[name='shelf_kw']").val($("#shelf_kw_onekey").val());
	    }
	    
	    if(receipt.isDisabledPage()){
	    	$("#shelf_tbody").find("input,select,textarea").attr("disabled","disabled");
	    	$tr.find("[name='shelf_kw']").selectpicker('refresh');
	    }
	},
	
	/**
	 * 删除行
	 */
	deleteRow : function(obj){
		$(obj).parent().parent("tr").remove();
		if($("#"+this.tbodyId+">tr").length == 0){
			$("#"+this.tbodyId).append('<tr class="no-data"><td class="text-center" colspan="4" title="暂无数据 No data.">暂无数据 No data.</td></tr>');
		}
	},
	
	/**
	 * 验证数据
	 */
	validataData : function(){
		var this_ = this;
		
		var kccb = $("#shelf_kccb").val();
		var jz = $("#shelf_jz").val();
		if(kccb && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(kccb)){
			AlertUtil.showModalFooterMessage("成本必须大于等于零，且小数点后最多保留两位！", this_.id);
			return false;
		}
		if(jz && !/^(0|[1-9]\d*)(\.[0-9][0-9]?)?$/.test(jz)){
			AlertUtil.showModalFooterMessage("价值必须大于等于零，且小数点后最多保留两位！", this_.id);
			return false;
		}
		
		if($("#"+this_.tbodyId+">tr:not(.no-data)").length == 0){
			AlertUtil.showModalFooterMessage("请添加库位信息！", this_.id);
			return false;
		}
		
		var shsl = parseFloat($("#shelf_shsl").val());
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
			}
			total += parseFloat(sjsl);
		});
		
		if(msg){
			AlertUtil.showModalFooterMessage(msg, this_.id);
			return false;
		}else if(shsl.toFixed(2) != total.toFixed(2)){
			AlertUtil.showModalFooterMessage("上架数量之和必须和收货数量相同！", this_.id);
			return false;
		}
		return true;
	},
	
	/**
	 * 保存
	 */
	save : function(){
		
		var this_ = this;
		// 验证物料详情数据
		if(!this_.validataData()){
			return false;
		}
		
		// 获取数据
		var shelves = [];
		var detail = this_.param.detail;
		$("#"+this.tbodyId+">tr:not(.no-data)").each(function(){
			var shelf = {};
			var $tr = $(this);
			shelf.ckid = $("#shelf_ck").val();
			shelf.ckh = $("#shelf_ck option:selected").attr("ckh");
			shelf.ckmc = $("#shelf_ck option:selected").attr("ckmc");
			shelf.cklb = $("#shelf_ck option:selected").attr("cklb");
			shelf.kccb = $("#shelf_kccb").val();
			shelf.biz = $("#shelf_biz").val();
			shelf.jz = $("#shelf_jz").val();
			shelf.jzbz = $("#shelf_jzbz").val();
			
			shelf.id = $tr.find("[name='shlef_id']").val();
			shelf.kwid = $tr.find("[name='shelf_kw']").val();
			shelf.kwh = $tr.find("[name='shelf_kw'] option:selected").attr("kwh");
			shelf.sjsl = $tr.find("[name='shelf_sjsl']").val();
			shelf.jldw = detail.dw;
			
			shelves.push(shelf);
		});
		
		detail.shelves = shelves;
		
		// 运行回调方法
		if(this_.param.callback && typeof(this_.param.callback) == "function"){
			this_.param.callback(shelves);
		}
		
		// 隐藏模态框
		$("#"+this_.id).modal("hide");
	},
}