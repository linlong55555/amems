
Equipment_list_edit = {
	id:'Equipment_list_edit',
	tbodyId:'equipment_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	bxxList : [],
	param: {
		djid:null,//关联单据id
		hclxlist : [1, 4],
		parentWinId : '',
		ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : true,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	numberValidator : {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，精确到两位小数"
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
		this.initViewOrHide();
		this.initDic();
		if(this.param.djid){
			this.initDataList();
		}else{
			this.setNoData();
		}
	},
	/**
	 * 初始化数据字典
	 */
	initDic : function(){
		this.bxxList = DicAndEnumUtil.getDicItems('23', this.param.dprtcode);
	},
	/**
	 * 拼装必需性select
	 */
	formatBxx : function(id){
		var option = '<option value="" ></option>';
		$.each(this.bxxList,function(i,o){
			if(o.id == id){
				option += "<option name='"+StringUtil.escapeStr(o.name)+"' value='"+o.id+"' selected='selected'>"+ StringUtil.escapeStr(o.name)+"</option>";
			}else{
				option += "<option name='"+StringUtil.escapeStr(o.name)+"' value='"+o.id+"'>"+StringUtil.escapeStr(o.name)+"</option>";
			}
 	   	}); 
		return option;
	},
	initViewOrHide : function(){
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("."+this.colOptionheadClass, $("#"+this.id)).show();
		}else{
			$("."+this.colOptionheadClass, $("#"+this.id)).hide();
		}
	},
	initDataList : function(){
		var this_ = this;
		var searchParam = {};
		var paramsMap = {};
		searchParam.ywid = this.param.djid;
		searchParam.ywlx = this.param.ywlx;
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.hclxlist = this_.param.hclxlist;
		searchParam.paramsMap = paramsMap;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/materialtool/queryEquipmentList",
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
			if(row.hcMainData != null){
				row.bjh = row.hcMainData.bjh;
				row.hclx = row.hcMainData.hclx;
				row.ywms = row.hcMainData.ywms;
				row.zwms = row.hcMainData.zwms;
				row.jldw = row.hcMainData.jldw;
				row.jhly = row.hcMainData.jhly;
				row.hclxEj = row.hcMainData.hclxEj;
				row.xingh = row.hcMainData.xingh;
			}
			row.tdjxx = row.paramsMap.tdjxx;
			this_.addRow(row);
		});
	},
	openWinAdd : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var existsIdList = [];
		$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(){
			var index = $(this).index(); //当前行
			var bjid =$.trim($(this).find("input[name='bjid']").val());
			existsIdList.push(bjid);
		});
		var hclxlist = this_.param.hclxlist;
		open_win_material_tools.show({
			multi : true,
			parentWinId : this_.param.parentWinId,
			existsIdList:existsIdList,//已经选择的集合,id
			existsBjhList:null,//已经选择的集合,部件号
			hclxlist:hclxlist,//已经选择的集合,航材类型
			search_criteria : true,
			modalHeadChina : '器材清单',
			modalHeadEnglish : 'Material',
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				if(data != null && data.length > 0){
					//先移除暂无数据一行
					var len = $("#"+this_.tbodyId, $("#"+this_.id)).length;
					if (len == 1) {
						if($("#"+this_.tbodyId,$("#"+this_.id)).find("td").length == 1){
							$("#"+this_.tbodyId, $("#"+this_.id)).empty();
						}
					}
					$.each(data,function(index,row){
						this_.rowCount++;
						row.bjid = row.id;
						row.id = '';
						row.tdjxx = row.paramsMap.tdjxx;
						this_.addRow(row);
					});
				}
			}
		},true);
	},
	addRow : function(obj){
		
		var hclx = DicAndEnumUtil.getEnumName('materialTypeEnum', obj.hclx);
		if(obj.hclx == 1 && obj.hclxEj != null && obj.hclxEj != ''){
			var hclxEj = obj.hclxEj;
//			if(hclxEj != 11 && hclxEj != 12){
//				hclxEj = 11;
//			}
			hclx = DicAndEnumUtil.getEnumName('materialSecondTypeEnum', hclxEj);
		}
		
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
		tr += 	  "<input type='hidden' name='bjid' value='"+obj.bjid+"'/>";
		tr += 	  "<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(obj.bjh)+"'/>";
		tr += 	  "<input type='hidden' name='hclx' value='"+obj.hclx+"'/>";
		tr += 	  "<input type='hidden' name='jhly' value='"+StringUtil.escapeStr(obj.jhly)+"'/>";
		tr += "</td>";
		tr += "<td title='"+hclx+"' style='text-align:left;vertical-align:middle;'>"+hclx+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.bjh)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(obj.bjh)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.xingh)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(obj.xingh)+"</td>";
		
		var name = StringUtil.escapeStr(obj.ywms) + " " + StringUtil.escapeStr(obj.zwms);
		tr += "<td title='"+name+"' style='text-align:left;vertical-align:middle;' >"+name+"</td>";
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:right;vertical-align:middle;'><input class='form-control' name='qcdh' type='text' value='"+StringUtil.escapeStr(obj.qcdh)+"' onkeyup='"+this_.id+".changeQcdh(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.qcdh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.qcdh)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:right;vertical-align:middle;'><input class='form-control' name='sl' type='text' value='"+StringUtil.escapeStr(obj.sl)+"' onkeyup='"+this_.id+".changeSl(this)' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.sl)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(obj.sl)+"</td>";
		}
		
		tr += "<td title='"+StringUtil.escapeStr(obj.jldw)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jldw) +"</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.jhly)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(obj.jhly) +"</td>";
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:right;vertical-align:middle;'><select name='bxx' onchange="+this_.id+".changeBxx(this) class='form-control'>"+this_.formatBxx(obj.bxx)+"</select></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.bxx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bxx)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:right;vertical-align:middle;'><input class='form-control' name='bz' type='text' value='"+StringUtil.escapeStr(obj.bz)+"' maxlength='300' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bz)+"</td>";
		}
		
		tr += "<td style='text-align:left;vertical-align:middle;'>";
	    var tdjxx = obj.tdjxx;
		var str = tdjxx==null?"":tdjxx.split(",");
		for (var i = 0; i < str.length; i++) {
			var dataArr = str[i].split("#_#")
			var bjh = dataArr[0];
			var sl = dataArr[1]?dataArr[1]:0;
			var ms = dataArr[3];
			var tdjtTitle = StringUtil.escapeStr(bjh) + " : " + sl + "," + StringUtil.escapeStr(ms);
			var tdj = "<a bjh='"+StringUtil.escapeStr(obj.bjh)+"' tdjh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick="+this_.id+".openSubstitutionWin(this)>"+StringUtil.escapeStr(bjh)+"</a>";
			tdj += " : ";
			tdj += "<a jh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick="+this_.id+".openStorageDetailWin(this)>"+sl+"</a>";
			tdj += " , " + StringUtil.escapeStr(ms);
			if(i==str.length-1){
				tr += "<span title='"+tdjtTitle+"'>"+tdj+"</span>";
			}else{
				tr += "<span title='"+tdjtTitle+"'>"+tdj+"</span>"+"<br>";	
			}								
		}				
		tr += "</td>";
		
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
	},
	removeRow : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='hiddenid']").val();
		if ('' != id && null != id) {
			
//			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				$(obj).parent().parent().remove();
				this_.resRowCount();
				this_.rowCount--;
//			}});
			
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
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr ><td colspan='13' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr ><td colspan='12' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	/**
	 * 查看替代件信息
	 */
	openSubstitutionWin : function(obj){
		var bjh = $(obj).attr("bjh");
		var tdjh = $(obj).attr("tdjh");
		var dprtcode = this.param.dprtcode;
		open_win_equivalent_substitution.show({
			bjh : bjh,
			tdjh : tdjh,
			isParamId : false,
			dprtcode:dprtcode
		});
	},
	/**
	 * 打开库存分布详情对话框
	 */
	openStorageDetailWin : function(obj){
		var this_ = this;
		var bjh = $(obj).attr("jh");
		var ckidList = [];
		//打开库存分布详情对活框
		open_win_inventory_distribution_details.show({
			bjh : bjh,
			ckidList:ckidList,
			dprtcode:this_.param.dprtcode
		});
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
			var xc = 0;
			$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
				var tr_this = this;
				var infos ={};
				var index=$(this).index(); //当前行
				var hiddenid = $(this).find("input[name='hiddenid']").val(); //当前行，隐藏id的值
				var bjid = $(this).find("input[name='bjid']").val();
				var bjh = $(this).find("input[name='bjh']").val();
				var hclx = $(this).find("input[name='hclx']").val();
				var sl = $(this).find("input[name='sl']").val();
				var bz = $(this).find("input[name='bz']").val();
				var jhly = $(this).find("input[name='jhly']").val();
				var qcdh = $(this).find("input[name='qcdh']").val();
				var bxx =$(tr_this).find("select[name='bxx']").val();
				
				if(qcdh != null && qcdh != ''){
					if(!this_.codeValidator.reg.test(qcdh)){  
						AlertUtil.showModalFooterMessage("器材代号/IPC No."+this_.codeValidator.message);
						$(tr_this).find("input[name='qcdh']").focus();
						$(tr_this).find("input[name='qcdh']").addClass("border-color-red");
						this_.isValid = false; 
						return false;
					}
				}
				
				if(null == sl || "" == sl){
					AlertUtil.showModalFooterMessage("请输入需求数量!");
					$(tr_this).find("input[name='sl']").focus();
					$(tr_this).find("input[name='sl']").addClass("border-color-red");
//					.css("border-color","#a94442");
					this_.isValid = false; 
					return false;
				}
				
				if(!this_.numberValidator.reg.test(sl)){  
					AlertUtil.showModalFooterMessage("需求数量"+this_.numberValidator.message);
					$(tr_this).find("input[name='sl']").focus();
					$(tr_this).find("input[name='sl']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				} 
				
				xc++;
				infos.id = hiddenid;
				infos.bjid = bjid;
				infos.jh  = bjh;
				infos.bjlx = hclx;
				infos.sl = sl;
				infos.bz = bz;
				infos.jhly = jhly;
				infos.qcdh = qcdh;
				infos.bxx = bxx;
				infos.xc = xc;
				param.push(infos);
			});
		}
		return param;
	},
	/**
	 * 改变必需性
	 */
	changeBxx : function(obj){
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	/**
	 * 改变器材代号
	 */
	changeQcdh : function(obj){
		this.onkeyup4Code(obj);
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	/**
	 * 改变数量
	 */
	changeSl : function(obj){
		this.onkeyup4Num(obj);
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	/**
	 * 不能输入中文
	 */
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
	}
}
