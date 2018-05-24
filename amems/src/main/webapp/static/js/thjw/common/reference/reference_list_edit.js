
ReferenceUtil = {
	id:'ReferenceUtil',
	tbodyId:'reference_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	param: {
		djid:null,//关联单据id
		ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : true,
		changeCss:false,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数!"
	},
	codeValidator : {
		reg : new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符!"
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.rowCount = 0;
		if(this.param.changeCss){
			$("#reference_div", $("#"+this.id)).removeClass("padding-right-8").addClass("padding-right-0");
		}
		this.initViewOrHide();
		this.initDic();
		if(this.param.djid){
			this.initDataList();
		}else{
			this.setNoData();
		}
	},
	initViewOrHide : function(){
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("."+this.colOptionheadClass, $("#"+this.id)).show();
		}else{
			$("."+this.colOptionheadClass, $("#"+this.id)).hide();
		}
	},
	initDic : function(){
		$("#wjlxSelect", $("#"+this.id)).empty();
		$("#wjlxSelect", $("#"+this.id)).append('<option value="" >请选择 Choose</option>');
		DicAndEnumUtil.registerDic('16','wjlxSelect',this.param.dprtcode);
		$("#wjlySelect", $("#"+this.id)).empty();
		$("#wjlySelect", $("#"+this.id)).append('<option value="" >请选择 Choose</option>');
		DicAndEnumUtil.registerDic('8','wjlySelect',this.param.dprtcode);
	},
	initDataList : function(){
		var this_ = this;
		var searchParam = {};
		searchParam.ywid = this.param.djid;
		searchParam.ywlx = this.param.ywlx;
		searchParam.dprtcode = this.param.dprtcode;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/reference/queryAllList",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(searchParam),
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendHtml(data);
				}else{
					this_.setNoData();
				}
			}
		});
	},
	appendHtml : function(list){
		var this_ = this;
		// 先移除暂无数据一行
		$("#"+this_.tbodyId, $("#"+this_.id)).empty();
		$.each(list, function(i, row) {
			this_.rowCount++;
			this_.addRow(row);
		});
	},
	add : function(){
		var this_ = this;
		//先移除暂无数据一行
		var len = $("#"+this_.tbodyId, $("#"+this_.id)).length;
		if (len == 1) {
			if($("#"+this_.tbodyId,$("#"+this_.id)).find("td").length == 1){
				$("#"+this_.tbodyId, $("#"+this_.id)).empty();
			}
		}
		var row = {};
		this_.rowCount++;
		row.id = '';
		this_.addRow(row);
	},
	addRow : function(obj){
		var this_ = this;
		$("#wjlxSelect", $("#"+this_.id)).find("option").removeAttr("selected");
		$("#wjlxSelect", $("#"+this_.id)).find("option[value='"+obj.wjlx+"']").attr("selected",true);
		var wjlxOption = $("#wjlxSelect", $("#"+this_.id)).html();
		$("#wjlySelect", $("#"+this_.id)).find("option").removeAttr("selected");
		$("#wjlySelect", $("#"+this_.id)).find("option[value='"+obj.wjly+"']").attr("selected",true);
		var wjlyOption = $("#wjlySelect", $("#"+this_.id)).html();
		var tr = "";
		tr += "<tr>";
		
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:center;vertical-align:middle;'>";
			tr += "<button type='button' class='line6 line6-btn' onclick='"+this_.id+".removeRow(this)'>";
		    tr += "    <i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		    tr += "</button>";
			tr += "</td>";
		}
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.rowCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		if(this_.param.colOptionhead){
			tr += "<td><select name='wjlx' onchange="+this_.id+".changeWjlx(this) class='form-control'>"+wjlxOption+"</select></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.wjlx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.wjlx)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><select name='wjly' onchange="+this_.id+".changeWjly(this) class='form-control'>"+wjlyOption+"</select></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.wjly)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.wjly)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='wjbh' type='text' value='"+StringUtil.escapeStr(obj.wjbh)+"' onkeyup='"+this_.id+".changeWjbh(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.wjbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.wjbh)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='ym' type='text' value='"+StringUtil.escapeStr(obj.ym)+"' maxlength='16' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.ym)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ym)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='wjbb' type='text' value='"+StringUtil.escapeStr(obj.wjbb)+"' onkeyup='"+this_.id+".changeBb(this)' /></td>";
		}else{
			var wjbb = '';
			if(obj.wjbb != null && obj.wjbb != ''){
				wjbb = "R"+StringUtil.escapeStr(obj.wjbb);
			}
			tr += "<td title='"+wjbb+"' style='text-align:center;vertical-align:middle;'>"+wjbb+"</td>";
		}
		
		
		var bfrq = "";
		if(StringUtil.escapeStr(obj.bfrq) != ''){
			bfrq = obj.bfrq.substring(0, 10);
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control datepicker' name='ckwjBfrq' data-date-format='yyyy-mm-dd' value='"+bfrq+"' type='text' maxlength='10'></td>";
		}else{
			tr += "<td style='text-align:left;vertical-align:middle;'>"+bfrq+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='wjbt' type='text' value='"+StringUtil.escapeStr(obj.wjbt)+"' maxlength='300' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.wjbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.wjbt)+"</td>";
		}
		
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
		
		
		$('.datepicker').datepicker({
			format:'yyyy-mm-dd',
			autoclose : true,
			clearBtn : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
	},
	/**
	 * 删除一行
	 */
	removeRow : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='hiddenid']").val();
		if ('' != id && null != id) {
			
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				$(obj).parent().parent().remove();
				this_.resRowCount();
				this_.rowCount--;
			}});
			
		} else {
			$(obj).parent().parent().remove();
			this_.resRowCount();
			this_.rowCount--;
		}
	},
	/**
	 * 重置序号
	 */
	resRowCount : function(){
		var this_ = this;
		var len = $("#"+this_.tbodyId,$("#"+this_.id)).find("tr").length;
		if (len >= 1){
			$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(index){
				$(this).find("span[name='orderNumber']").html(index+1);
			});
		}else{
			this_.setNoData();
		}
	},
	/**
	 * 设置暂无数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='9' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	/**
	 * 获取参考文件数据
	 */
	getData : function(){
		var this_ = this;
		this_.isValid = true;
		var param = [];
		var len = $("#"+this.tbodyId,$("#"+this.id)).find("tr").length;
		if (len == 1) {
			if($("#"+this.tbodyId,$("#"+this.id)).find("td").length ==1){
				return param;
			}
		}
		if (len > 0){
			var xc = 0;
			$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
				var tr_this = this;
				var infos ={};
				var index=$(this).index(); //当前行
				var hiddenid =$("input[name='hiddenid']").eq(index).val(); //当前行，隐藏id的值
				var wjlx =$(tr_this).find("select[name='wjlx']").val();
				var wjly =$(tr_this).find("select[name='wjly']").val();
				var wjbh =$(tr_this).find("input[name='wjbh']").val();
				var ym =$(tr_this).find("input[name='ym']").val();
				var wjbb =$(tr_this).find("input[name='wjbb']").val();
				var ckwjBfrq =$(tr_this).find("input[name='ckwjBfrq']").val();
				var wjbt =$(tr_this).find("input[name='wjbt']").val();
				
				if(null == wjlx || "" == wjlx){
					AlertUtil.showModalFooterMessage("请选择文件类型!");
					$(tr_this).find("select[name='wjlx']",$("#"+this_.id)).focus();
					$(tr_this).find("select[name='wjlx']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == wjly || "" == wjly){
					AlertUtil.showModalFooterMessage("请选择文件来源!");
					$(tr_this).find("select[name='wjly']",$("#"+this_.id)).focus();
					$(tr_this).find("select[name='wjly']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == wjbh || "" == wjbh){
					AlertUtil.showModalFooterMessage("请输入文件编号!");
					$(tr_this).find("input[name='wjbh']",$("#"+this_.id)).focus();
					$(tr_this).find("input[name='wjbh']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				wjbh = wjbh.replaceAll("−","-");
				if(!this_.codeValidator.reg.test(wjbh)){  
					AlertUtil.showModalFooterMessage("文件编号"+this_.codeValidator.message);
					$(tr_this).find("input[name='wjbh']").focus();
					$(tr_this).find("input[name='wjbh']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(wjbb != null && wjbb != ''){
					if(!this_.numberValidator.reg.test(wjbb)){  
						AlertUtil.showModalFooterMessage("版本"+this_.numberValidator.message);
						$(tr_this).find("input[name='wjbb']").focus();
						$(tr_this).find("input[name='wjbb']").addClass("border-color-red");
						this_.isValid = false; 
						return false;
					}
				}
				
				xc++;
				infos.id = hiddenid;
				infos.wjlx = wjlx;
				infos.wjly  = wjly;
				infos.wjbh = wjbh;
				infos.ym = ym;
				infos.wjbb = wjbb;
				infos.wjbt = wjbt;
				infos.xc = xc;
				infos.bfrq = ckwjBfrq;
				/*Start:不需判空，要清空的字段 */
				if(ckwjBfrq==""){
					var paramsMap = {};
					paramsMap.ckwjBfrq = "ND";
					infos.paramsMap = paramsMap;
				}
				/*End:不需判空，要清空的字段 */
				param.push(infos);
			});
		}
		return param;
	},
	changeBb : function(obj){
		this.onkeyup4Num(obj);
		$(obj).removeClass("border-color-red");
	},
	changeWjbh : function(obj){
		this.onkeyup4Code(obj);
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	changeWjlx : function(obj){
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	changeWjly : function(obj){
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	//只能输入数字和小数,保留两位
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
	onkeyup4Code : function(obj){
		obj.value = obj.value.replaceAll("−","-");
		var reg = new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$");
		
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
	}
}
