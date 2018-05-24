
WorkordeProcessUtil = {
	id:'WorkordeProcessUtil',
	tbodyId:'process_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 1,
	param: {
		djid:null,//关联单据id
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : true,
		dprtcode:userJgdm,//默认登录人当前机构代码
		workHourOptions : [],//工序
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	init : function(param){
		this.rowCount = 1;
		if(param){
			$.extend(this.param, param);
		}
		if(null != this.param.djid){
			this.initDataList();
		}else{
			this.setNoData();
		}
		this.initInfo();
	},
	initInfo : function(){
		var this_ = this;
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
			url:basePath+"/produce/processHours145/queryAllList",
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
		this_.rowCount = 1;
		$.each(list, function(i, row) {
			if(i==0 && StringUtil.escapeStr(row.gxbh)=="0010"){
				
				var gzzname = "";
				$.each(this_.param.workHourOptions,function(i,list){
					if(list.id==row.gzzid){
						gzzname = StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc);
						return false;
					}
		 	    }); 
				
				var processTR = "<tr id='process0010'>";
				
				if(this_.param.colOptionhead){
					processTR +=
					"    <td>" +
					"			<span style='display: none;' name='gxOrderNumber'>0010</span>" +
					"			<input type='hidden' id='process0010WorkcenterId' value='"+StringUtil.escapeStr(row.gzzid)+"'/>" +
					"    </td>";
				}
				processTR += 
				"    <td>0010</td>"+
				"    <td id='process0010Title' style='text-align:left;vertical-align:middle;'>"+ StringUtil.escapeStr(row.gxms)+"</td>"+
				"    <td id='process0010Workcenter' style='text-align:left;vertical-align:middle;'>"+ gzzname +"</td>"+
				"    <td id='process0010WorkHours' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhgs)+"</td>"+
				"</tr>";
				$("#"+this_.tbodyId, $("#"+this_.id)).append(processTR);
			}else{
				this_.rowCount++;
				this_.addRow(row);
			}
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
		
		if(this_.rowCount > 999){
			return false; //相当于break
		}
		
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
		tr += 	  "<span name='gxOrderNumber'>"+this_.getProcessNum(this_.rowCount)+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='gxms' type='text' value='"+StringUtil.escapeStr(obj.gxbh)+"' maxlength='300' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.gxms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.gxms)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:left;vertical-align:middle;'>"
			   +"    <select class='form-control' name='workcenter'>";
							$.each(this_.param.workHourOptions,function(i,list){
								if(list.id==obj.gzzid){
									tr += "<option value='"+list.id+"' selected='selected'>"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
								}else{
									tr += "<option value='"+list.id+"' >"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
								}
					 	    }); 
			tr += "  </select>"
			   +" </td>";
		}else{
			
			var gzzname = "";
			$.each(this_.param.workHourOptions,function(i,list){
				if(list.id==obj.gzzid){
					gzzname = StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc);
					return false;
				}
	 	    }); 
			
			tr += "<td title='"+gzzname+"' style='text-align:left;vertical-align:middle;'>"+gzzname+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='workhour' type='text' value='"+StringUtil.escapeStr(obj.jhgs)+"' maxlength='12' onkeyup='"+this_.id+".clearNoNumTwo(this)'  onpaste='return false' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.jhgs)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.jhgs)+"</td>";
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
				$(this).find("span[name='gxOrderNumber']").html(this_.getProcessNum(index+1));
			});
		}else{
			this_.setNoData();
		}
	},
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		/*if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='7' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}*/
		var processTR = "<tr id='process0010'>"+
						"    <td>" +
						"			<span style='display: none;' name='gxOrderNumber'>0010</span>" +
						"			<input type='hidden' id='process0010WorkcenterId' value=''/>" +
						"    </td>"+
						"    <td>0010</td>"+
						"    <td id='process0010Title' style='text-align:left;vertical-align:middle;'></td>"+
						"    <td id='process0010Workcenter' style='text-align:left;vertical-align:middle;'></td>"+
						"    <td id='process0010WorkHours' style='text-align:left;vertical-align:middle;'>0</td>"+
						"</tr>";
		$("#"+this.tbodyId, $("#"+this.id)).append(processTR);
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
				var gxbh = $(this).find("span[name='gxOrderNumber']").html(); //当前行，隐藏工序编号值
				
				var gxms =$(this).find("input[name='gxms']").val(); // 工作内容
				var gzzid = $(this).find("select[name='workcenter']").val(); //工种
				var jhgs = $(this).find("input[name='workhour']").val(); //工时
				
				if(gxbh=="0010"){
					gxms = $("#process0010Title").html(); // 工作内容
					gzzid = $("#process0010WorkcenterId").val(); //工种
					jhgs = $("#process0010WorkHours").html(); //工时
				}
			
				if(null == jhgs || "" == jhgs){
					AlertUtil.showModalFooterMessage("请输入工时!");
					$(tr_this).find("input[name='workhour']").focus();
					$(tr_this).find("input[name='workhour']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				infos.gxbh = gxbh; //序号
				infos.gxms = gxms; //内容
				infos.gzzid = gzzid; //工种id
				infos.jhgs = jhgs; //工时
				
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
	/**工序号：每次加10，最大不超过9990*/
	getProcessNum : function(rowNum){
		var rowNum = rowNum * 10+"";
		var zeroNum = "";
		for (var k = 0; k < 4-rowNum.length; k++) {
			zeroNum += "0";
		}
		return zeroNum+rowNum;
	},
}
