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
		id : null,
		djid:null,//关联单据id
		colOptionhead : true,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		if(this.param.id){
			this.id = this.param.id;
		}
		this.initInfo();
	},
	initInfo : function(){
		this.rowCount = 0;
		this.initViewOrHide();
		this.initSelect();
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
	initSelect : function(){
		this.planeModelList = acAndTypeUtil.getACTypeList({DPRTCODE:this.param.dprtcode});
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
		$.each(list, function(i, row) {
			this_.rowCount++;
			this_.addRow(row);
		});
		$('.datepicker', $("#"+this_.id)).datepicker({
			 autoclose: true,
			 clearBtn:false
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
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.rowCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += 	  "<input type='hidden' name='fjjx' value='"+StringUtil.escapeStr(obj.fjjx)+"'/>";
		tr += 	  "<input type='hidden' name='lb' value='"+StringUtil.escapeStr(obj.lb)+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.fjjx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.fjjx)+"</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.lb)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.lb)+"</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		if(this_.param.colOptionhead){
			tr += "<input type='text' class='form-control datepicker' name='yxqKs' value='"+indexOfTime(formatUndefine(obj.yxqKs))+"' data-date-format='yyyy-mm-dd' placeholder=''  maxlength='10' />";
		}else{
			tr += indexOfTime(formatUndefine(obj.yxqKs));
		}
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		if(this_.param.colOptionhead){
			tr += "<input type='text' class='form-control datepicker' name='yxqJs' value='"+indexOfTime(formatUndefine(obj.yxqJs))+"' data-date-format='yyyy-mm-dd' placeholder=''  maxlength='10' />";
		}else{
			tr += indexOfTime(formatUndefine(obj.yxqJs));
		}
		tr += "</td>";
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
	},
	/**
	 * 设置暂无数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='5' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
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
				var yxqKs =$(tr_this).find("input[name='yxqKs']").val();
				var yxqJs =$(tr_this).find("input[name='yxqJs']").val();
				var fjjx =$(tr_this).find("input[name='fjjx']").val();
				var lb =$(tr_this).find("input[name='lb']").val();
				
				if(this_.id == 'setting_plane_model'){
					if(yxqKs == null || yxqKs == ''){
						AlertUtil.showModalFooterMessage("授权开始日期不能为空!");
						this_.isValid = false; 
						return false;
					}
					
					if(yxqJs == null || yxqJs == ''){
						AlertUtil.showModalFooterMessage("授权截止日期不能为空!");
						this_.isValid = false; 
						return false;
					}
				}
				
				if(yxqKs != "" && yxqJs != null){
					if(TimeUtil.compareDate(yxqKs +" " + "00:00:00", yxqJs +" " + "00:00:00")){
						AlertUtil.showModalFooterMessage("授权开始日期不能大于授权截止日期!");
						this_.isValid = false; 
						return false;
					}
				}
				
				infos.id = hiddenid;
				infos.fjjx = fjjx;
				infos.lb = lb;
				infos.yxqKs = yxqKs;
				infos.yxqJs = yxqJs;
				param.push(infos);
			});
		}
		return param;
	}
}
