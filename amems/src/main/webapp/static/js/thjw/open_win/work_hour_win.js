Work_Hour_Win_Modal = {
		id : "Work_Hour_Win_Modal",
		tbodyId:'work_hour_list',
		colOptionheadClass : "colOptionhead",
	rowCount : 0,
	workGroupList : [],
	stageList : [],
	param: {
		parentWinId : '',
		workHourData:[],
		colOptionhead : true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	isValid : true,
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数"
	},
	data:[],
	show : function(param){
		$("#"+this.id).modal("show");
		this.rowCount = 0;
		if(param){
			$.extend(this.param, param);
		}
		AlertUtil.hideModalFooterMessage(this.id);
		this.initViewOrHide();
		this.initInfo();
//		ModalUtil.showSearchModal(this.param.parentWinId,this.id,'');
	},
	initInfo : function(){
		this.initWorkGroup();
		this.initStage();
		if(this.param.workHourData && this.param.workHourData.length > 0){
			this.appendHtml(this.param.workHourData);
		}else{
			this.setNoData();
		}
		this.sumTotal();
	},
	initViewOrHide : function(){
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("."+this.colOptionheadClass, $("#"+this.id)).show();
			$("#save_btn", $("#"+this.id)).show();
		}else{
			$("."+this.colOptionheadClass, $("#"+this.id)).hide();
			$("#save_btn", $("#"+this.id)).hide();
		}
	},
	/**
	 * 初始化工作组
	 */
	initWorkGroup : function(){
		var this_ = this;
		var obj={};
		obj.dprtcode = this_.param.dprtcode;
		if(this_.param.colOptionhead){
			obj.zt = 1;
		}
		AjaxUtil.ajax({
			   url:basePath+"/sys/workgroup/loadWorkGroup",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   async: false,
			   data:JSON.stringify(obj),
			   success:function(data){
				   this_.workGroupList = [];
				   if(data != null && data.wgList != null && data.wgList.length > 0){
					   this_.workGroupList = data.wgList;
				   }
		       },
		 });
	},
	/**
	 * 初始化阶段
	 */
	initStage : function(){
		var this_ = this;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/basic/stage/getStageListByDrpt",
			type:"post",
			data:{dprtcode:this_.param.dprtcode},
			dataType:"json",
			success:function(data){
				this_.stageList = [];
				if(data != null && data.length > 0){
					this_.stageList = data;
				}
			}
		});
	},
	/**
	 * 拼装阶段select
	 */
	formatStage : function(id){
		var option = '<option value="" >请选择 Choose</option>';
		$.each(this.stageList,function(i,o){
			if(o.id == id){
				option += "<option name='"+StringUtil.escapeStr(o.jdbh)+"' value='"+o.id+"' selected='selected'>"+StringUtil.escapeStr(o.jdbh)+" "+StringUtil.escapeStr(o.jdmc)+"</option>";
			}else{
				option += "<option name='"+StringUtil.escapeStr(o.jdbh)+"' value='"+o.id+"'>"+StringUtil.escapeStr(o.jdbh)+" "+StringUtil.escapeStr(o.jdmc)+"</option>";
			}
 	   	}); 
		return option;
	},
	/**
	 * 拼装阶段字符串
	 */
	getStage : function(id){
		var str = '';
		$.each(this.stageList,function(i,o){
			if(o.id == id){
				str = StringUtil.escapeStr(o.jdbh)+" "+StringUtil.escapeStr(o.jdmc);
			}
 	   	}); 
		return str;
	},
	/**
	 * 拼装工作组select
	 */
	formatWorlGroup : function(id){
		var option = '<option value="" >请选择 Choose</option>';
		$.each(this.workGroupList,function(i,o){
			if(o.id == id){
				option += "<option name='"+StringUtil.escapeStr(o.gzzdm)+"' value='"+o.id+"' selected='selected'>"+StringUtil.escapeStr(o.gzzdm)+" "+StringUtil.escapeStr(o.gzzmc)+"</option>";
			}else{
				option += "<option name='"+StringUtil.escapeStr(o.gzzdm)+"' value='"+o.id+"'>"+StringUtil.escapeStr(o.gzzdm)+" "+StringUtil.escapeStr(o.gzzmc)+"</option>";
			}
 	   	}); 
		return option;
	},
	/**
	 * 拼装工作组字符串
	 */
	getWorlGroup : function(id){
		var str = '';
		$.each(this.workGroupList,function(i,o){
			if(o.id == id){
				str = StringUtil.escapeStr(o.gzzdm)+" "+StringUtil.escapeStr(o.gzzmc);
			}
 	   	}); 
		return str;
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
			tr += "<td><select onchange="+this_.id+".changeJd(this) name='jd' class='form-control'>"+this_.formatStage(obj.jdid)+"</select></td>";
		}else{
			tr += "<td title='"+this_.getStage(obj.jdid)+"' style='text-align:left;vertical-align:middle;'>"+this_.getStage(obj.jdid)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><select onchange="+this_.id+".changeGz(this) name='gzz' class='form-control'>"+this_.formatWorlGroup(obj.gzzid)+"</select></td>";
		}else{
			tr += "<td title='"+this_.getWorlGroup(obj.gzzid)+"' style='text-align:left;vertical-align:middle;'>"+this_.getWorlGroup(obj.gzzid)+"</td>";
		}
		//禁止右键输入onpaste='return false' 
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='jhgsXss' type='text' value='"+StringUtil.escapeStr(obj.jhgsXss)+"' onkeyup='"+this.id+".changeXss(this)' /></td>";
		}else{
			tr += "<td name='jhgsXss' title='"+StringUtil.escapeStr(obj.jhgsXss)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jhgsXss)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='rw' type='text' value='"+StringUtil.escapeStr(obj.rw)+"' maxlength='300' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.rw)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.rw)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='bz' type='text' value='"+StringUtil.escapeStr(obj.bz)+"' maxlength='300' /></td>";
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
		this.sumTotal();
	},
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='7' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='6' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	setData : function(){
		
		var data = this.getData();
		
		if(!this.isValid){
			return false;
		}
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		this.close();
	},
	getData : function(){
		var this_ = this;
		this.isValid = true;
		var param = [];
		var len = $("#"+this.tbodyId,$("#"+this.id)).find("tr").length;
		if (len == 1) {
			if($("#"+this.tbodyId,$("#"+this.id)).find("td").length ==1){
				return param;
			}
		}
		if (len > 0){
			var gzzJdArr = [];
			var xc = 0;
			$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
				var tr_this = this;
				var infos = {};
				var index= $(this).index(); //当前行
				var hiddenid = $(tr_this).find("input[name='hiddenid']").val(); //当前行，隐藏id的值
				var jdid = $(tr_this).find("select[name='jd']").val();
				var jdbm = $(tr_this).find("select[name='jd'] option:selected").attr("name");
				var gzzid = $(tr_this).find("select[name='gzz']",$("#"+this_.id)).val();
				var gzzbh = $(tr_this).find("select[name='gzz'] option:selected").attr("name");
				var jhgsXss = $(tr_this).find("input[name='jhgsXss']").val();
				var rw = $(tr_this).find("input[name='rw']").val();
				var bz = $(tr_this).find("input[name='bz']").val();
				
				if(null == jdid || "" == jdid){
					AlertUtil.showModalFooterMessage("请选择阶段!", this_.id);
					$(tr_this).find("select[name='jdid']",$("#"+this_.id)).focus();
					$(tr_this).find("select[name='jd']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == gzzid || "" == gzzid){
					AlertUtil.showModalFooterMessage("请选择工种!", this_.id);
					$(tr_this).find("select[name='gzz']",$("#"+this_.id)).focus();
					$(tr_this).find("select[name='gzz']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(this_.isValid && gzzJdArr.indexOf(jdid+gzzid) != -1){
					AlertUtil.showModalFooterMessage("对不起,阶段和工种不能重复！", this_.id);
					$(tr_this).find("select[name='gzz']",$("#"+this_.id)).focus();
					$(tr_this).find("select[name='gzz']").addClass("border-color-red");
					this_.isValid = false; 
			        return false;
				}
				gzzJdArr.push(jdid+gzzid);
				if(null == jhgsXss || "" == jhgsXss){
					AlertUtil.showModalFooterMessage("请输入工时!", this_.id);
					$(tr_this).find("input[name='jhgsXss']").focus()
					$(tr_this).find("input[name='jhgsXss']").addClass("border-color-red");
					/*AlertUtil.showMessageCallBack({
						message : '请输入工时!',
						callback : function(option){
							$(tr_this).find("input[name='jhgsXss']").focus()
						}
					});*/
					this_.isValid = false; 
					return false;
				}
				if(!this_.numberValidator.reg.test(jhgsXss)){  
					AlertUtil.showModalFooterMessage("工时"+this_.numberValidator.message, this_.id);
					$(tr_this).find("input[name='jhgsXss']").focus();
					$(tr_this).find("input[name='jhgsXss']").addClass("border-color-red");
					this_.isValid = false;
					return false;
				} 
				xc++;
				infos.id = hiddenid;
				infos.jdid = jdid;
				infos.gzzid = gzzid;
				infos.jdbm = jdbm;
				infos.gzzbh  = gzzbh;
				infos.jhgsXss = jhgsXss;
				infos.rw = rw;
				infos.bz = bz;
				infos.xc = xc;
				param.push(infos);
			});
		}
		return param;
	},
	changeJd : function(obj){
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	changeGz : function(obj){
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	//改变小时数触发
	changeXss : function(obj){
		this.onkeyup4Num(obj);
		this.sumTotal();
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	//计算总工时
	sumTotal : function(){
		var this_ = this;
		var total = 0;
		
		$("#"+this_.tbodyId, $("#"+this_.id)).find("tr").each(function(){
			if(this_.param.colOptionhead){
				total += Number($.trim($(this).find("input[name='jhgsXss']").val()));
			}else{
				total += Number($.trim($(this).find("td[name='jhgsXss']").html()));
			}
		});
		if(total == ''){
			total = 0;
		}
		if(String(total).indexOf(".") >= 0){
			total = total.toFixed(2);
		}
		$("#zgs", $("#"+this_.id)).html(total);
	},
	//只能输入数字和小数,保留两位
	onkeyup4Num : function(obj){
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
	}
}