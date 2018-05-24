
PublicationUtil = {
	id:'PublicationUtil',
	tbodyId:'syxcbw_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	param: {
		djid:null,//关联单据id
		ywlx:6,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : true,
		dprtcode:userJgdm//默认登录人当前机构代码
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
			url:basePath+"/project2/pulication/queryAllList",
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
			tr += "<td><input class='form-control' name='wjlx' type='text' value='"+StringUtil.escapeStr(obj.lx)+"' onkeyup='"+this_.id+".changeInput(this)' maxlength='50' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.lx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.lx)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='wjbhm' type='text' value='"+StringUtil.escapeStr(obj.wjh)+"' onkeyup='"+this_.id+".changeInput(this)' maxlength='50' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.wjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.wjh)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='wjsm' type='text' value='"+StringUtil.escapeStr(obj.sm)+"' onkeyup='"+this_.id+".changeInput(this)' maxlength='600' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.sm)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.sm)+"</td>";
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
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='5' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='4' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
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
				var wjlx =$("input[name='wjlx']").eq(index).val();
				var wjbh =$("input[name='wjbhm']").eq(index).val();
				var wjsm =$("input[name='wjsm']").eq(index).val();
				var xc =$("span[name='orderNumber']").eq(index).html();
				
				
				if(null == wjlx || "" == wjlx){
					AlertUtil.showModalFooterMessage("请输入文件类型!");
					$(tr_this).find("input[name='wjlx']").focus();
					$(tr_this).find("input[name='wjlx']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(null == wjbh || "" == wjbh){
					AlertUtil.showModalFooterMessage("请输入文件号!");
					$(tr_this).find("input[name='wjbhm']").focus();
					$(tr_this).find("input[name='wjbhm']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				/*if(null == wjsm || "" == wjsm){
					AlertUtil.showModalFooterMessage("请输入说明!");
					$(tr_this).find("input[name='wjsm']").focus();
					$(tr_this).find("input[name='wjsm']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}*/
				
				infos.id = hiddenid;
				infos.lx = wjlx;
				infos.wjh = wjbh;
				infos.sm = wjsm;
				infos.xc = xc;
				
				param.push(infos);
			});
		}
		return param;
	},
	changeInput : function(obj){
		$(obj).removeClass("border-color-red");
	},
};
