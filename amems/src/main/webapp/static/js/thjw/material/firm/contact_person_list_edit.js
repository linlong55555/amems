
Contact_person_list_edit = {
	id:'Contact_person_list_edit',
	tbodyId:'Contact_person_list_edit_Tbody',
	personHeadId : "personHead",
	colOptionheadId : "colOptionhead",
	param: {
		djid:null,//关联单据id
		personHead : true,
		dprtcode:userJgdm,//默认登录人当前机构代码
		colOptionhead : true
	},
	delIds : [],
	orderNumber : 0,
	show : function(param){
		this.delIds = [];
		this.orderNumber = 0;
		if(param){
			$.extend(this.param, param);
		}
		this.initViewOrHide();
		//回显数据
		if(this.param.djid){
			this.loadData();
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			this.setNoData();
		}
		$("#"+this.id).show();
	},
	initViewOrHide : function(){
		//显示或隐藏头
		if(this.param.personHead){
			$("#"+this.personHeadId, $("#"+this.id)).show();
		}else{
			$("#"+this.personHeadId, $("#"+this.id)).hide();
		}
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("#"+this.colOptionheadId, $("#"+this.id)).show();
			$(".text-muted", $("#"+this.id)).show();
			
		}else{
			$("#"+this.colOptionheadId, $("#"+this.id)).hide();
			$(".text-muted", $("#"+this.id)).hide();
		}
	},
	loadData : function(){
		var this_ = this;
		AjaxUtil.ajax({
			url : basePath + "/sys/contactperson/queryListByCsId",
			type : "post",
			async : false,
			data : {
				csid : this_.param.djid
			},
			success : function(data) {
				$("#" + this_.tbodyId, $("#"+this_.id)).empty();
				if(data != null && data.length > 0){
					var htmlContent = '';
					$.each(data,function(index,row){
						this_.orderNumber++;
						this_.addRow(row);
					});
				} else {
					this_.setNoData();
				}
			}
		});
	},
	addRow : function(row){
		
		var htmlContent = "";
	   
		if(this.param.colOptionhead){
			htmlContent += "<tr style='cursor:pointer;' ondblclick="+this.id+".openContactPersonWinEdit(this)>";
			htmlContent += "<td class='text-center'>";
			htmlContent += "<button class='line6' onclick="+this.id+".removeRow(this)>";
			htmlContent += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
			htmlContent += "</button>";
			htmlContent += "<input type='hidden' class='form-control' name='hiddenid' value='" + row.id + "'/>";
			htmlContent += "</td>";
		}else{
			htmlContent += "<tr>";
		}
	   
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
		htmlContent += "<span name='orderNumber'>" + this.orderNumber + "</span>";
		htmlContent += "</td>";	
		htmlContent += "<td name='lxr' title='"+StringUtil.escapeStr(row.lxr)+"' style='text-align:left;vertical-align:middle;' >";
		htmlContent += StringUtil.escapeStr(row.lxr);
		htmlContent += "</td>";
		htmlContent += "<td name='zw' title='"+StringUtil.escapeStr(row.zw)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.zw)+"</td>";
		htmlContent += "<td name='sj' title='"+StringUtil.escapeStr(row.sj)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sj)+"</td>"; 
		htmlContent += "<td name='zj' title='"+StringUtil.escapeStr(row.zj)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zj)+"</td>"; 
		htmlContent += "<td name='cz' title='"+StringUtil.escapeStr(row.cz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.cz)+"</td>"; 
		htmlContent += "<td name='yxdz' title='"+StringUtil.escapeStr(row.yxdz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.yxdz)+"</td>"; 
		htmlContent += "<td name='qq' title='"+StringUtil.escapeStr(row.qq)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.qq)+"</td>"; 
		htmlContent += "<td name='wx' title='"+StringUtil.escapeStr(row.wx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.wx)+"</td>"; 
		htmlContent += "<td name='bz' title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		htmlContent += "</tr>";  
			    
		$("#" + this.tbodyId, $("#"+this.id)).append(htmlContent);
	},
	removeRow : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='hiddenid']").val();
		if ('' != id && null != id) {
			
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				$(obj).parent().parent().remove();
				this_.resxh();
				this_.orderNumber--;
				this_.delIds.push(id);
				
			}});
			
		} else {
			$(obj).parent().parent().remove();
			this_.resxh();
			this_.orderNumber--;
		}
	},
	resxh : function(){
		var this_ = this;
		var len = $("#"+this_.tbodyId, $("#"+this_.id)).find("tr").length;
		if (len >= 1){
			$("#"+this_.tbodyId, $("#"+this_.id)).find("tr").each(function(index){
				$(this).find("span[name='orderNumber']").html(index+1);
			});
		}else{
			this_.setNoData();
		}
	},
	setNoData : function(){
		if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr style='height:35px;'><td colspan='11' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr style='height:35px;'><td colspan='10' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	openContactPersonWinAdd : function(){
		var this_ = this;
		Contact_Person_Win_Modal.show({
			viewObj:null,//对象，在弹窗中回显
			callback: function(data){//回调函数
				if(data != null){
					
					//先移除暂无数据一行
					var len = $("#"+this_.tbodyId, $("#"+this_.id)).length;
					if (len == 1) {
						if($("#"+this_.tbodyId, $("#"+this_.id)).find("td").length == 1){
							$("#"+this_.tbodyId, $("#"+this_.id)).empty();
						}
					}
					
					this_.orderNumber++;
					data.id = "";
					this_.addRow(data);
				}
			}
		})
	},
	openContactPersonWinEdit : function(node){
		var obj = {};
		obj.lxr = $.trim($(node).find("td[name='lxr']").text());
		obj.zw = $.trim($(node).find("td[name='zw']").text());
		obj.sj = $.trim($(node).find("td[name='sj']").text());
		obj.zj = $.trim($(node).find("td[name='zj']").text());
		obj.cz = $.trim($(node).find("td[name='cz']").text());
		obj.yxdz = $.trim($(node).find("td[name='yxdz']").text());
		obj.qq = $.trim($(node).find("td[name='qq']").text());
		obj.wx = $.trim($(node).find("td[name='wx']").text());
		obj.bz = $.trim($(node).find("td[name='bz']").text());
		Contact_Person_Win_Modal.show({
			viewObj:obj,//对象，在弹窗中回显
			callback: function(data){//回调函数
				if(data != null){
					$(node).find("td[name='lxr']").text(data.lxr);
					$(node).find("td[name='lxr']").attr("title",data.lxr);
					$(node).find("td[name='zw']").text(data.zw);
					$(node).find("td[name='zw']").attr("title",data.zw);
					$(node).find("td[name='sj']").text(data.sj);
					$(node).find("td[name='sj']").attr("title",data.sj);
					$(node).find("td[name='zj']").text(data.zj);
					$(node).find("td[name='zj']").attr("title",data.zj);
					$(node).find("td[name='cz']").text(data.cz);
					$(node).find("td[name='cz']").attr("title",data.cz);
					$(node).find("td[name='yxdz']").text(data.yxdz);
					$(node).find("td[name='yxdz']").attr("title",data.yxdz);
					$(node).find("td[name='qq']").text(data.qq);
					$(node).find("td[name='qq']").attr("title",data.qq);
					$(node).find("td[name='wx']").text(data.wx);
					$(node).find("td[name='wx']").attr("title",data.wx);
					$(node).find("td[name='bz']").text(data.bz);
					$(node).find("td[name='bz']").attr("title",data.bz);
				}
			}
		})
	},
	getData : function(){
		var personParam = [];
		var len = $("#"+this.tbodyId, $("#"+this.id)).find("tr").length;
		if (len == 1) {
			if ($("#"+this.tbodyId, $("#"+this.id)).find("td").length == 1) {
				return personParam;
			}
		}

		if (len > 0) {
			$("#"+this.tbodyId, $("#"+this.id)).find("tr").each(function() {
				var obj = {};
				obj.id = $.trim($(this).find("input[name='hiddenid']").val());
				obj.lxr = $.trim($(this).find("td[name='lxr']").text());
				obj.zw = $.trim($(this).find("td[name='zw']").text());
				obj.sj = $.trim($(this).find("td[name='sj']").text());
				obj.zj = $.trim($(this).find("td[name='zj']").text());
				obj.cz = $.trim($(this).find("td[name='cz']").text());
				obj.yxdz = $.trim($(this).find("td[name='yxdz']").text());
				obj.qq = $.trim($(this).find("td[name='qq']").text());
				obj.wx = $.trim($(this).find("td[name='wx']").text());
				obj.bz = $.trim($(this).find("td[name='bz']").text());
				personParam.push(obj);
			});
		}
		return personParam;
	}
}

