 var repairAddData={
		id:'repairAddData',
		tbodyId:'repairAddTbody',
		colOptionheadClass : "colOptionhead",
		rowCount : 0,
		wlztList : [],//物料状态
		param: {
			djid:null,//关联单据id
			cqid : '',//默认产权id
			cqbh : '',//默认产权名
			htlx : 10,//合同类型
			xqqdIdList : [],//需求清单id集合
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
				$("#repair_table_div", $("#"+this.id)).removeClass("padding-right-8").addClass("padding-right-0");
			}
			this.initModelHead();
			this.initViewOrHide();
			this.initDic();
			// 先移除暂无数据一行
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			if(this.param.djid){
				this.initDataList();
			}else{
				if(this.param.xqqdIdList.length > 0){
					this.initXqqdDataList();
				}else{
					this.setNoData();
				}
			}
		},
		/**
		 * 初始化对话框头部
		 */
		initModelHead : function(){
			var cn = "修理";
			var eng = "Repair";
			$("#xlmodalHeadCN", $("#"+this.id)).html(cn);
			$("#xlmodalHeadENG", $("#"+this.id)).html(eng);
			$("#xlcolHeadCN", $("#"+this.id)).html(cn);
			$("#xlcolHeadENG", $("#"+this.id)).html(eng);
		},
		initViewOrHide : function(){
			//显示或隐藏操作列
			if(this.param.colOptionhead){
				$("."+this.colOptionheadClass, $("#"+this.id)).show();
			}else{
				$("."+this.colOptionheadClass, $("#"+this.id)).hide();
			}
		},
		initDic : function(obj){
			this.wlztList = DicAndEnumUtil.getDicItems('82', this.param.dprtcode);
		},
		/**
		 * 加载需求清单信息
		 */
		initXqqdDataList : function(){
			var this_ = this;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/material/contractinfo/queryByXqqdIdList",
				type:"post",
				data:{xqqdIdList : this_.param.xqqdIdList},
				dataType:"json",
				success:function(data){
					if(data != null && data.length > 0){
						$.each(data, function(i, row) {
							this_.rowCount++;
							row.id = '';
							row.zywms = row.paramsMap.zywms;
							row.cqbh = '';
							row.cqid = '',
							this_.addRow(row);
						});
					}
					if(this_.rowCount == 0){
						this_.setNoData();
					}
					this_.sumTotal();
				}
			});
		},
		initDataList : function(){
			var this_ = this;
			var searchParam = {};
			searchParam.mainid = this.param.djid;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/material/contractinfo/queryAllList",
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
					this_.sumTotal();
				}
			});
		},
		appendHtml : function(list){
			var this_ = this;
			$.each(list, function(i, row) {
				this_.rowCount++;
				row.zywms = row.paramsMap.zywms;
				row.cqbh = row.paramsMap.cqbh;
				this_.addRow(row);
			});
			if(this_.param.xqqdIdList.length > 0){
				this_.initXqqdDataList();
			}
		},
		addTrShow : function(){
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
			row.bjid = '';
			row.cqid = this_.param.cqid;
			row.cqbh = this_.param.cqbh;
			row.dw = '';
			this_.addRow(row);
		},
		addRow : function(obj){
			var this_ = this;
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
				/*件号*/
				tr += '<td>';
				tr += '<div class="input-group col-xs-12">';
				tr += '<input name="bjh" class="form-control" value="'+StringUtil.escapeStr(obj.bjh)+'" onBlur="'+this_.id+'.loadMaterialRel(this)" onkeyup="'+this_.id+'.changeBjh(this)" type="text" />';
				tr += "<input type='hidden' name='bjid' value='"+StringUtil.escapeStr(obj.bjid)+"'/>";
				tr += "<input type='hidden' name='dw' value='"+StringUtil.escapeStr(obj.dw)+"'/>";
				tr += '<span id="" class="input-group-addon btn btn-default" onclick="'+this_.id+'.openMaterialWin(this)"><i class="icon-search"></i></span>';
				tr += '</div>';
				tr += '</td>';
			}else{
				tr += "<td title='"+StringUtil.escapeStr(obj.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.bjh)+"</td>";
			}
			/*名称*/
			tr += "<td title='"+StringUtil.escapeStr(obj.zywms)+"' name='zywms' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.zywms)+"</td>";
			
			/*序列号  */
			tr += '<td><input type="text" class="form-control" name="xlh" value="'+StringUtil.escapeStr(obj.xlh)+'" onkeyup="'+this_.id+'.changeBjh(this)" /></td>';
			
			/*原因  */
			tr += '<td><input type="text" class="form-control" name="sxyy" value="'+StringUtil.escapeStr(obj.sxyy)+'" maxlength="1300" /></td>';
			/*产权*/
			if(this_.param.colOptionhead){
				tr += '<td>';
				tr += '<div class="input-group col-xs-12">';
				tr += '<input name="cqbh" class="form-control readonly-style" value="'+StringUtil.escapeStr(obj.cqbh)+'" ondblclick="'+this_.id+'.openCqWin(this)" type="text" readonly/>';
				tr += "<input type='hidden' name='cqid' value='"+StringUtil.escapeStr(obj.cqid)+"'/>";
				tr += '<span id="" class="input-group-addon btn btn-default" onclick="'+this_.id+'.openCqWin(this)"><i class="icon-search"></i></span>';
				tr += '</div>';
				tr += '</td>';
			}else{
				tr += "<td title='"+StringUtil.escapeStr(obj.cqbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.cqbh)+"</td>";
			}
			/*交货期*/
			var jhq = "";
			if(StringUtil.escapeStr(obj.jhq) != ''){
//					indexOfTime(row.htrq)
				jhq = obj.jhq;
			}
			if(this_.param.colOptionhead){
				tr += "<td><div class='input-group col-xs-12'>";
				
				if(obj.jhqdw == 11 || obj.jhqdw == 12 ){
					tr += "<input  class='form-control' name='jhq' value='"+jhq+"' onkeyup='"+this.id+".changeJhq(this)' type='text' maxlength='20'>";
				}else{
					tr += "<input  class='form-control datepicker' name='jhq' data-date-format='yyyy-mm-dd' value='"+jhq+"' type='text' maxlength='20'>";
				}
				
				tr += "<span  class='input-group-addon' style='background:none;border:0px;padding-top:0px;padding-bottom:0px;'>";
            	tr += "<select name='jhqdw' class='form-control' style='width:50px;border-left:0px;' onchange="+this_.id+".switchValidity(this,'"+jhq+"')>"+this_.formatJhqSelect(obj.jhqdw)+"</select>";
            	tr += "</span>";
            	tr += "</div></td>";
				/*tr += "<td><input class='form-control datepicker' name='jhq' data-date-format='yyyy-mm-dd' value='"+jhq+"' type='text' maxlength='20'></td>";*/
			}else{
				tr += "<td style='text-align:left;vertical-align:middle;'>"+obj.jhq+"</td>";
			}
			
			tr += "</tr>";
			$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
			$('.datepicker').datepicker({
				format:'yyyy-mm-dd',
				autoclose : true,
				clearBtn : true
			});
		},
		/**
		 * 切换有效期效果
		 */
		switchValidity:function(obj,jhq){
			if(obj.value != 10){
				$(obj).parent().siblings("input").remove();
				$(obj).parent().parent().prepend("<input  class='form-control' name='jhq'  onkeyup='"+this.id+".changeJhq(this)' type='text' maxlength='20'>");
			}else{
				$(obj).parent().siblings("input").remove();
				$(obj).parent().parent().prepend("<input  class='form-control datepicker' name='jhq' data-date-format='yyyy-mm-dd' value='"+jhq+"' type='text' maxlength='20'>");
				$('.datepicker').datepicker({
					format:'yyyy-mm-dd',
					autoclose : true,
					clearBtn : true
				});
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
			this.sumTotal();
		},
		/**
		 * 设置暂无数据
		 */
		setNoData : function(){
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			if(this.param.colOptionhead){
				$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
			}else{
				$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='7' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
			}
		},
		/**
		 * 拼装交货期
		 */
		formatJhqSelect : function(jhqdw){
			var option = '';
			option += "<option value='10' "+(jhqdw == 10?"selected='selected'":"")+">CAL</option>";
			option += "<option value='12' "+(jhqdw == 12?"selected='selected'":"")+">M</option>";
			option += "<option value='11' "+(jhqdw == 11?"selected='selected'":"")+">D</option>";
			return option;
		},
		/**
		 * 拼装select
		 */
		formatSelect : function(wlzt){
			var option = '';
		 	if(this.wlztList.length >0){
				$.each(this.wlztList,function(i, obj){
					if(obj.id == wlzt){
						option += "<option value='"+StringUtil.escapeStr(obj.id)+"' selected='selected'>"+StringUtil.escapeStr(obj.name)+"</option>";
					}else{
						option += "<option value='"+StringUtil.escapeStr(obj.id)+"' >"+StringUtil.escapeStr(obj.name)+"</option>";
					}
				});
		  	}
			return option;
		},
		/**
		 * 加载部件相关信息
		 */
		loadMaterialRel : function(obj){
			var this_ = this;
			var dprtcode = this_.param.dprtcode;
			var bjh = obj.value;
			AjaxUtil.ajax({
				url:basePath+"/material/material/selectByBjhAndDprcode",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					'bjh':bjh,
					'dprtcode':this_.param.dprtcode
				}),
				success:function(data){
					if(data.hCMainData != null){
						this_.setMaterialData($(obj).parent().parent().parent(), data.hCMainData);
					}else{
						this_.clearMaterialData($(obj).parent().parent().parent());
						obj.value = bjh;
					}
			    }
			}); 
		},
		setAllCq : function(cqid, cqbh){//设置所有产权
			var this_ = this;
			this_.param.cqid = cqid;
			this_.param.cqbh = cqbh;
			var param = [];
			var len = $("#"+this.tbodyId,$("#"+this.id)).find("tr").length;
			if (len == 1) {
				if($("#"+this.tbodyId,$("#"+this.id)).find("td").length ==1){
					return;
				}
			}
			if (len > 0){
				$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(){
					$(this).find("input[name='cqid']").val(cqid);
					$(this).find("input[name='cqbh']").val(cqbh);
				});
			}
		},
		/**
		 * 打开产权弹窗
		 */
		openCqWin : function(obj){
			var this_ = this;
			var dprtcode = this_.param.dprtcode;
			var cqId = $(obj).parent().find("input[name='cqid']").val();
			cqModal.show({
				selected : cqId,//原值，在弹窗中默认勾选
				dprtcode:dprtcode,
				callback: function(data){//回调函数
					var cqText = '';
					var cqid = '';
					if(data != null ){
						cqText = data.cqbh;
						cqid = data.id;
					}
					$(obj).parent().find("input[name='cqbh']").val(cqText);
					$(obj).parent().find("input[name='cqid']").val(cqid);
				}
			});
		},
		/**
		 * 打开部件弹窗
		 */
		openMaterialWin : function(obj){
			var this_ = this;
			open_win_material_basic.show({
				multi:false,
				showStock:false,
				dprtcode : this_.param.dprtcode,
				callback: function(data){//回调函数
					if(data){
						this_.setMaterialData($(obj).parent().parent().parent(), data);
					}else{
						this_.clearMaterialData($(obj).parent().parent().parent(), data);
					}
				}
			},true);
		},
		clearMaterialData : function($obj){
			$obj.find("input[name='bjid']").val('');
			$obj.find("input[name='bjh']").val('');
			$obj.find("input[name='dw']").val('');
			$obj.find("td[name='zywms']").html('');
			$obj.find("td[name='zywms']").attr("title", '');
		},
		setMaterialData : function($obj, obj){
			var zywms = StringUtil.escapeStr(obj.ywms) + " " + StringUtil.escapeStr(obj.zwms);
			$obj.find("input[name='bjid']").val(obj.id);
			$obj.find("input[name='bjh']").val(obj.bjh);
			$obj.find("input[name='dw']").val(obj.jldw);
			$obj.find("td[name='zywms']").html(zywms);
			$obj.find("td[name='zywms']").attr("title", zywms);
		},
		//计算小计
		sumxj : function(obj){
			var this_ = this;
			var xj = 0;
			var sl = Number($.trim($(obj).parent().parent().find("input[name='sl']").val()));
			var dj = Number($.trim($(obj).parent().parent().find("input[name='dj']").val()));
			xj = sl * dj;
			if(xj == ''){
				xj = 0;
			}
			if(String(xj).indexOf(".") >= 0){
				xj = xj.toFixed(2);
			}
			$(obj).parent().parent().find("td[name='xj']").html(xj);
			$(obj).parent().parent().find("td[name='xj']").attr("title", xj);
			this.sumTotal();
		},
		//计算总计
		sumTotal : function(){
			var this_ = this;
			var total = 0;
			$("#"+this_.tbodyId, $("#"+this_.id)).find("tr").each(function(){
				total += Number($.trim($(this).find("td[name='xj']").html()));
			});
			if(total == ''){
				total = 0;
			}
			if(String(total).indexOf(".") >= 0){
				total = total.toFixed(2);
			}
		},
		/**
		 * 获取明细数据
		 */
		getData : function(){
			var this_ = this;
			this_.isValid = true;
			var param = [];
			var len = $("#"+this.tbodyId,$("#"+this.id)).find("tr").length;
			if (len == 1) {
				if($("#"+this.tbodyId,$("#"+this.id)).find("td").length ==1){
					this_.isValid = false; 
					AlertUtil.showModalFooterMessage("请新增合同明细!");
					return param;
				}
			}
			if (len > 0){
				var bjxlhArr = [];
				$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
					var tr_this = this;
					var infos = {};
					var index= $(this).index(); //当前行
					var hiddenid = $("input[name='hiddenid']").eq(index).val(); //当前行，隐藏id的值
					var bjid =$(tr_this).find("input[name='bjid']").val();
					var bjh =$(tr_this).find("input[name='bjh']").val();
					var dw =$(tr_this).find("input[name='dw']").val();
					var xlh =$(tr_this).find("input[name='xlh']").val();
					var sxyy =$(tr_this).find("input[name='sxyy']").val();
					var cqid =$(tr_this).find("input[name='cqid']").val();
					var jhq =$(tr_this).find("input[name='jhq']").val();
					var jhqdw =$(tr_this).find("select[name='jhqdw']").val();
					
					if(null == bjh || "" == bjh){
						AlertUtil.showModalFooterMessage("部件号不能为空!");
						$(tr_this).find("input[name='bjh']",$("#"+this_.id)).focus();
						$(tr_this).find("input[name='bjh']").addClass("border-color-red");
						this_.isValid = false; 
						return false;
					}
					if(!this_.codeValidator.reg.test(bjh)){  
						AlertUtil.showModalFooterMessage("部件号"+this_.codeValidator.message);
						$(tr_this).find("input[name='bjh']").focus();
						$(tr_this).find("input[name='bjh']").addClass("border-color-red");
						this_.isValid = false; 
						return false;
					}
					
					if(null == bjid || "" == bjid){
						AlertUtil.showModalFooterMessage("部件号不存在!");
						$(tr_this).find("input[name='bjh']",$("#"+this_.id)).focus();
						$(tr_this).find("input[name='bjh']").addClass("border-color-red");
						this_.isValid = false; 
						return false;
					}
					
					if(null != xlh && "" != xlh){
						
						if(bjxlhArr.indexOf(bjh+xlh) != -1){
							AlertUtil.showModalFooterMessage("对不起,部件和序列号不能重复！");
							$(tr_this).find("input[name='xlh']",$("#"+this_.id)).focus();
							$(tr_this).find("input[name='xlh']").addClass("border-color-red");
							this_.isValid = false; 
					        return false;
						}
						
						bjxlhArr.push(bjh+xlh);
						
						if(!this_.codeValidator.reg.test(xlh)){  
							AlertUtil.showModalFooterMessage("序列号号"+this_.codeValidator.message);
							$(tr_this).find("input[name='xlh']").focus();
							$(tr_this).find("input[name='xlh']").addClass("border-color-red");
							this_.isValid = false; 
							return false;
						}
					}
					
					if(jhqdw != 10 && jhq != ''){
						if(!this_.numberValidator.reg.test(jhq)){  
							AlertUtil.showModalFooterMessage("交货期"+this_.numberValidator.message);
							$(tr_this).find("input[name='jhq']").focus();
							$(tr_this).find("input[name='jhq']").addClass("border-color-red");
							this_.isValid = false; 
							return false;
						}
					}
					
					infos.id = hiddenid;
					infos.bjid = bjid;
					infos.dw = dw;
					infos.bjh  = bjh;
					infos.xlh = xlh;
					infos.sxyy = sxyy;
					infos.cqid = cqid;
					infos.jhq = jhq;
					infos.jhqdw = jhqdw;
					param.push(infos);
				});
			}
			return param;
		},
		changeJhq : function(obj){
			this.onkeyup4Num(obj);
			$(obj).removeClass("border-color-red");
		},
		changeBjh : function(obj){
			this.onkeyup4Code(obj);
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
