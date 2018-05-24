
ReferenceUtil = {
	id:'ReferenceUtil',
	tbodyId:'reference_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	param: {
		djid:null,//关联单据id
		ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : true,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.rowCount = 0;
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
		DicAndEnumUtil.registerDic('16','wjlxSelect',this.param.dprtcode);
		$("#wjlySelect", $("#"+this.id)).empty();
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
					
 					// 替换输入框、下拉框为文本
					//StringUtil.replaceAsText("reference_list");
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
			tr += "<td class='colOptionhead' style='text-align:center;vertical-align:middle;'>";
			tr += "<button class='line6 line6-btn' onclick='"+this_.id+".removeRow(this)'>";
			tr += "    <i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
			tr += "</button>";
			tr += "</td>";
		}
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.rowCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		tr += "<td><select name='wjlx' class='form-control' onchange="+this_.id+".changeWjlx(this)><option value='' >请选择please choose</option>"+wjlxOption+"</select></td>";
		tr += "<td><select name='wjly' class='form-control' onchange="+this_.id+".changeWjly(this)><option value='' >请选择please choose</option>"+wjlyOption+"</select></td>";
		tr += "<td><input class='form-control' onkeyup='"+this_.id+".changeWjbh(this)' name='wjbh' type='text' value='"+StringUtil.escapeStr(obj.wjbh)+"' maxlength='16' /></td>";
		tr += "<td><input class='form-control' name='wjbt' type='text' value='"+StringUtil.escapeStr(obj.wjbt)+"' maxlength='300' /></td>";
		tr += "<td><input class='form-control date-picker' onchange="+this_.id+".changeBfrq(this) name='bfrq' type='text' value='"+StringUtil.escapeStr(obj.bfrq).substring(0,10)+"' maxlength='16' /></td>";
		tr += "<td><input class='form-control date-picker' onchange="+this_.id+".changeSxrq(this) name='sxrq' type='text' value='"+StringUtil.escapeStr(obj.sxrq).substring(0,10)+"' maxlength='16' /></td>";
		tr += "<td><input class='form-control date-picker' onchange="+this_.id+".changeDqrq(this) name='dqrq' type='text' value='"+StringUtil.escapeStr(obj.dqrq).substring(0,10)+"' maxlength='300' /></td>";
		
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
		
		$('.date-picker').datepicker({
			format:'yyyy-mm-dd',
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
	},
	changeBfrq : function(obj){
		$(obj).removeClass("border-color-red");
		if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}
	},
	changeSxrq : function(obj){
		$(obj).removeClass("border-color-red");
		if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}
	},
	changeDqrq : function(obj){
		$(obj).removeClass("border-color-red");
		if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
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
	},
	changeWjbh : function(obj){
		this.onkeyup4Code(obj);
		$(obj).removeClass("border-color-red");
		if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}
	},
	changeWjlx : function(obj){
		$(obj).removeClass("border-color-red");
		if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}
	},
	changeWjly : function(obj){
		$(obj).removeClass("border-color-red");
		if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}
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
	codeValidator : {
		reg : new RegExp("^[a-zA-Z0-9-_\x21-\x7e]{1,50}$"),
		message: "只能输入长度不超过50个字符的英文、数字、英文特殊字符!"
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
			$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
				var tr_this = this;
				var infos ={};
				var index=$(this).index(); //当前行
				var hiddenid =$("input[name='hiddenid']").eq(index).val(); //当前行，隐藏id的值
				var wjlx =$("select[name='wjlx']").eq(index).val();
				var wjly =$("select[name='wjly']").eq(index).val();
				var wjbh =$("input[name='wjbh']").eq(index).val();
				var wjbt =$("input[name='wjbt']").eq(index).val();
				var bfrq =$("input[name='bfrq']").eq(index).val();
				var sxrq =$("input[name='sxrq']").eq(index).val();
				var dqrq =$("input[name='dqrq']").eq(index).val();
				
				if(wjlx == null || wjlx == ''){
						AlertUtil.showModalFooterMessage("类型不能为空!");
						$(tr_this).find("select[name='wjlx']").focus();
						$(tr_this).find("select[name='wjlx']").addClass("border-color-red");
						this_.isValid = false; 
						return false;
				}
				if(wjly == null || wjly == ''){
					AlertUtil.showModalFooterMessage("来源不能为空!");
					$(tr_this).find("select[name='wjly']").focus();
					$(tr_this).find("select[name='wjly']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(wjbh == null || wjbh == ''){
					AlertUtil.showModalFooterMessage("文件编号不能为空!");
					$(tr_this).find("input[name='wjbh']").focus();
					$(tr_this).find("input[name='wjbh']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
					
				 var oDate3 = new Date(bfrq); //颁发日期
			 	 var oDate4 = new Date(sxrq); //生效日期
			 	 var oDate5 = new Date(dqrq); //到期日期
		 		 if(oDate3.getTime()>oDate4.getTime()){
		 			$(tr_this).find("input[name='bfrq']").focus();
		 			$(tr_this).find("input[name='bfrq']").addClass("border-color-red");
		 			AlertUtil.showModalFooterMessage('颁发日期必须小于等于生效日期!');
		 			this_.isValid = false; 
					return false;
		 	     }
		 		 
		 		 if(oDate3.getTime()>oDate5.getTime()){
		 			$(tr_this).find("input[name='bfrq']").focus();
		 			$(tr_this).find("input[name='bfrq']").addClass("border-color-red");
		 			AlertUtil.showModalFooterMessage('颁发日期必须小于等于到期日期!');
		 			this_.isValid = false; 
					return false;
		 		 }
		 		 
		 		 if(oDate4.getTime()>oDate5.getTime()){
		 			$(tr_this).find("input[name='sxrq']").focus();
		 			$(tr_this).find("input[name='sxrq']").addClass("border-color-red");
		 			AlertUtil.showModalFooterMessage('生效日期必须小于等于到期日期!');
		 			this_.isValid = false; 
					return false;
		 		 }
				
				
				infos.id = hiddenid;
				infos.wjlx = wjlx;
				infos.wjly  = wjly;
				infos.wjbh = wjbh;
				infos.wjbt = wjbt;
				infos.bfrq = bfrq;
				infos.sxrq = sxrq;
				infos.dqrq = dqrq;
				param.push(infos);
			});
		}
		return param;
	}
}
