
StoppingUtil = {
	id:'StoppingUtil',
	tbodyId:'gstcsj_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	param: {
		djid:null,//关联单据id
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : true,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数"
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	init : function(param){
		this.rowCount = 0;
		if(param){
			$.extend(this.param, param);
		}
		if(this.param.djid){
			this.initDataList();
		}else{
			this.setNoData();
		}
		this.initInfo();
	},
	initInfo : function(){
		this.initViewOrHide();
		this.initDic();
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
		
	},
	initDataList : function(){
		var this_ = this;
		var searchParam = {};
		searchParam.mainid = this.param.djid;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/manhourparking/queryAllList",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(searchParam),
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendHtml(data);
			   } else {
				   this_.setNoData();
			   }
			}
		});
	},
	appendHtml : function(list){
		var this_ = this;
		// 先移除暂无数据一行
		$("#"+this_.tbodyId, $("#"+this_.id)).empty();
		this_.rowCount = 0;
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
		var tr = "";
		tr += "<tr>";
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:center;vertical-align:middle;'>";
			tr += "<button class='line6 line6-btn' onclick='"+this_.id+".removeRow(this)'>";
			tr += "    <i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
			tr += "</button>";
			tr += "</td>";
		}
		
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.rowCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='rw' type='text' value='"+StringUtil.escapeStr(obj.rw)+"' maxlength='300' onkeyup='"+this_.id+".changeInput(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.rw)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.rw)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='zb' type='text' value='"+StringUtil.escapeStr(obj.zb)+"' maxlength='50' onkeyup='"+this_.id+".changeInput(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.zb)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zb)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='rs' type='text' value='"+StringUtil.escapeStr(obj.rs)+"' maxlength='12' onkeyup='"+this_.id+".clearNoNumber(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.rs)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.rs)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='zgs' type='text' value='"+StringUtil.escapeStr(obj.zgs)+"' maxlength='12' onkeyup='"+this_.id+".clearNoNumTwo(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.zgs)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zgs)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='ztcsj' type='text' value='"+StringUtil.escapeStr(obj.ztcsj)+"' maxlength='12'  onkeyup='"+this_.id+".clearNoNumTwo(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.ztcsj)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.ztcsj)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><textarea style='height:35px;' class='form-control' name='bz' maxlength='300'>"+StringUtil.escapeStr(obj.bz)+"</textarea></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bz)+"</td>";
		}
		
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
	},
	removeRow : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='hiddenid']").val();
		if ('' != id && null != id) {
			
			/*AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){*/
				
				$(obj).parent().parent().remove();
				this_.resRowCount();
				this_.rowCount--;
			/*}});*/
			
		} else {
			$(obj).parent().parent().remove();
			this_.resRowCount();
			this_.rowCount--;
		}
	},
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
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='7' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
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
				var rw =$("input[name='rw']").eq(index).val();
				var zb =$("input[name='zb']").eq(index).val();
				var rs =$("input[name='rs']").eq(index).val();
				var zgs =$("input[name='zgs']").eq(index).val();
				var ztcsj =$("input[name='ztcsj']").eq(index).val();
				var bz =$("textarea[name='bz']").eq(index).val();
				
				if(null == rw || "" == rw){
					AlertUtil.showModalFooterMessage("请输入任务!");
					$(tr_this).find("input[name='rw']").focus();
					$(tr_this).find("input[name='rw']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == zb || "" == zb){
					AlertUtil.showModalFooterMessage("请输入组别!");
					$(tr_this).find("input[name='zb']").focus();
					$(tr_this).find("input[name='zb']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == rs || "" == rs){
					AlertUtil.showModalFooterMessage("请输入人数!");
					$(tr_this).find("input[name='rs']").focus();
					$(tr_this).find("input[name='rs']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == zgs || "" == zgs){
					AlertUtil.showModalFooterMessage("请输入总工时!");
					$(tr_this).find("input[name='zgs']").focus();
					$(tr_this).find("input[name='zgs']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == ztcsj || "" == ztcsj){
					AlertUtil.showModalFooterMessage("请输入总停场时间!");
					$(tr_this).find("input[name='ztcsj']").focus();
					$(tr_this).find("input[name='ztcsj']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				
				if(!this_.numberValidator.reg.test(rs)){  
					AlertUtil.showModalFooterMessage("人数"+this_.numberValidator.message);
					$(tr_this).find("input[name='rs']").focus();
					$(tr_this).find("input[name='rs']").addClass("border-color-red");
					this_.isValid = false;
					return false;
				} 
				
				
				if(!this_.numberValidator.reg.test(zgs)){  
					AlertUtil.showModalFooterMessage("总工时"+this_.numberValidator.message);
					$(tr_this).find("input[name='zgs']").focus();
					$(tr_this).find("input[name='zgs']").addClass("border-color-red");
					this_.isValid = false;
					return false;
				} 
				
				
				infos.id = hiddenid;
				infos.xc = index; //项次
				infos.rw = rw;
				infos.zb  = zb;
				infos.rs = rs;
				infos.zgs = zgs;
				infos.ztcsj = ztcsj;
				infos.bz = bz;
				param.push(infos);
			});
		}
		return param;
	},
	/**
	 * 只能输入数字
	 */
	clearNoNumber : function(obj){
		 this.changeInput(obj);
		 //先把非数字的都替换掉，除了数字
	     obj.value = obj.value.replace(/[^\d]/g,"");
	     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
	  		obj.value = 0;
	  	 }
	     obj.value = validateMax(obj.value);
	     function validateMax(_value){
	    	 if(Number(_value) > 9999999999){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//只能输入数字和小数,保留两位
	clearNoNumTwo : function(obj){
		 this.changeInput(obj);
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
	    	 if(Number(_value) > 9999999999.99){
	    		return validateMax(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	changeInput : function(obj){
		$(obj).removeClass("border-color-red");
	},
}
