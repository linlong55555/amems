/**
 * 机型
 */
plane_model_list_edit = {
	id:'plane_model_list_edit',
	tbodyId:'reference_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	planeModelList : [],
	param: {
		djid:null,//关联单据id
		colOptionhead : true,
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		var this_ = this;
		this_.rowCount = 0;
		this_.initViewOrHide();
		this_.initSelect(function(){
			if(this_.param.djid){
				this_.initDataList();
			}else{
				this_.setNoData();
			}
		});
	},
	initViewOrHide : function(){
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("."+this.colOptionheadClass, $("#"+this.id)).show();
		}else{
			$("."+this.colOptionheadClass, $("#"+this.id)).hide();
		}
	},
	initSelect : function(obj){
		var this_ = this;
		AjaxUtil.ajax({
		 	   url:basePath+"/project/planemodeldata/findAllType",
		 	   async: false,
		 	   type: "post",
		 	   dataType:"json",
		 	   data:{dprtcode:this_.param.dprtcode},
		 	   success:function(data){
		 		   this_.planeModelList = data;
		 		   if(typeof(obj)=="function"){
		 			   obj(data); 
		 		   }
		 	   }
		    }); 
	},
	initDataList : function(){
		var this_ = this;
		var mainid = this.param.djid;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/quality/post/application/querySQJXByMainid",
			type:"post",
			data:{mainid:mainid},
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
		if(list.length == 1 && (list[0].fjjx == null || list[0].fjjx == '') ){
			this_.setNoData();
		}else{
			$.each(list, function(i, row) {
				this_.rowCount++;
				this_.addRow(row);
			});
		}
		
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
		this_.callback();
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
			tr += "<td><select onchange="+this_.id+".changeFjjx(this) name='fjjx' class='form-control'>"+this_.formatPlaneModelSelect(obj.fjjx)+"</select></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.fjjx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.fjjx)+"</td>";
		}
		
		if(this_.param.colOptionhead){
			tr += "<td><input class='form-control' name='lb' type='text' value='"+StringUtil.escapeStr(obj.lb)+"' maxlength='100' /></td>";
		}else{
			tr += "<td title='"+StringUtil.escapeStr(obj.lb)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.lb)+"</td>";
		}
		
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
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
		this_.callback();
	},
	/**
	 * 设置暂无数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='4' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='3' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	/**
	 * 拼装机型select
	 */
	formatPlaneModelSelect : function(fjjx){
		var option = '';
	 	if(this.planeModelList.length >0){
			$.each(this.planeModelList,function(i, jx){
				if(jx == fjjx){
					option += "<option value='"+StringUtil.escapeStr(jx)+"' selected='selected'>"+StringUtil.escapeStr(jx)+"</option>";
				}else{
					option += "<option value='"+StringUtil.escapeStr(jx)+"' >"+StringUtil.escapeStr(jx)+"</option>";
				}
			});
	  	}
		return option;
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
				var info ={};
				info.id = "";
				info.fjjx = "";
				info.lb = "";
				param.push(info);
				return param;
			}
		}
		if (len > 0){
			var fjjxArr = [];
			$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
				var tr_this = this;
				var infos ={};
				var index=$(this).index(); //当前行
				var hiddenid =$("input[name='hiddenid']").eq(index).val(); //当前行，隐藏id的值
				var fjjx =$(tr_this).find("select[name='fjjx']").val();
				var lb =$(tr_this).find("input[name='lb']").val();
				
				if(null == fjjx || "" == fjjx){
					AlertUtil.showModalFooterMessage("请选择机型!");
					$(tr_this).find("select[name='fjjx']",$("#"+this_.id)).focus();
					$(tr_this).find("select[name='fjjx']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				if(this_.isValid && fjjxArr.indexOf(fjjx) != -1){
					AlertUtil.showModalFooterMessage("对不起,机型不能重复！");
					$(tr_this).find("select[name='fjjx']",$("#"+this_.id)).focus();
					$(tr_this).find("select[name='fjjx']").addClass("border-color-red");
					this_.isValid = false; 
			        return false;
				}
				fjjxArr.push(fjjx);
				infos.id = hiddenid;
				infos.fjjx = fjjx;
				infos.lb = lb;
				param.push(infos);
			});
		}
		return param;
	},
	changeFjjx : function(obj){
		$(obj).removeClass("border-color-red");
		this.callback();
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	/**
	 * 获取机型数组
	 */
	getFjjxList : function(){
		var this_ = this;
		var data = [];
		$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(){
			var fjjx =$(this).find("select[name='fjjx']").val();
			if(fjjx != null && fjjx != "" && data.indexOf(fjjx) == -1){
				data.push(fjjx);
			}
		});
		return data;
	},
	/**
	 * 执行回调函数
	 */
	callback : function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(this.getFjjxList());
		}
	}
}
