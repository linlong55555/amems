var account = {
	id:'account',
	dprtcode:'',//默认组织机构
	pageSize: 20,
	operateId:"",//最近操作的ID
	pagination:{},
	init: function(){
		DicAndEnumUtil.registerEnum("enableEnum", "account_state");
		var this_ = this;
		this.load();
		this.initValidatorForm();
	},
	initValidatorForm: function(){
		$('#add_account_form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	account_username: {
	                validators: {
	                	notEmpty: {
	                        message: '账号不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_@]{0,50}$/,
	                        message: '只能包含字母数字_@且长度不超过50个字符' 	
	                        	
	                    }
	                }
	            },
	            jgdm: {
	                validators: {
	                	notEmpty: {
	                        message: '机构代码不能为空'
	                    }
	                }
	            }
	        }
	    });
	},
	load: function(pageNumber, sortColumn, orderType){
		if(typeof(pageNumber) == "undefined"){ pageNumber = 1; }
		if(typeof(sortColumn) == "undefined"){ sortColumn = "auto";} 
		if(typeof(orderType) == "undefined"){ orderType = "desc"; } 
		
		this.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:this.pageSize};

		var obj ={};
		obj["pagination.page"] = pageNumber;
		obj["pagination.sort"] = sortColumn;
		obj["pagination.order"] = orderType;
		obj["pagination.rows"] = this.pageSize;
		$.extend(obj, this.getParams());
		if(this.operateId != ""){
			obj.paramsMap.id = this.operateId;
			this.operateId = "";
		}
		
		startWait();
		
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/sys/account/list",
			type: "post",
			dataType: "json",
			data: obj,
			success:function(data){
				finishWait();
				if(data.rows !=""){
					this_.dicts = data.dicts;
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					});
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[3], "#"+this_.id+"_list tr td");
						   
	 			} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").html("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId: this_.id+'_table'});
		    }
		}); 
	},
	//获取查询参数
	getParams: function(){
		var params = {};
		var paramsMap = {};
		paramsMap.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		paramsMap.state = $("#"+this.id+"_state").val();
		paramsMap.jgdm = $("#"+this.id+"_dprtcode").val();
		params.paramsMap = paramsMap;
		return params;
	},
	//拼接列表内容
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			htmlContent += "<tr style=\"cursor:pointer\"  ;  >";
			htmlContent += "<td class='text-center'><div>";
			htmlContent += "<i class='icon-remove color-blue cursor-pointer' onClick=\""+this_.id+".deleteAccount('"+ row.id + "')\" title='删除 Delete'></i>&nbsp;&nbsp;";
			htmlContent += "<i name='enabled' class='"+(row.state==1?"":"display-none")+" icon-lock color-blue cursor-pointer' onClick=\""+this_.id+".Lock('"+ row.id + "', this)\" title='禁用 Disabled'></i>";
			htmlContent += "<i name='disabled' class='"+(row.state==1?"display-none":"")+" icon-unlock-alt color-blue cursor-pointer' onClick=\""+this_.id+".Unlock('"+ row.id + "', this)\" title='启用 Enabled'></i>";
			htmlContent += "&nbsp;&nbsp;<i class='icon-refresh color-blue cursor-pointer' onClick=\""+this_.id+".ResetPassword('"+ row.id + "')\" title='重置密码 Reset password'></i>";
			htmlContent += "</div></td>";
			htmlContent += "<td style='vertical-align: middle;'  align='center'>"+StringUtil.escapeStr(index+1)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;' align='left'>"+StringUtil.escapeStr(row.username)+"</td>";  
			htmlContent += "<td name='state' style='vertical-align: middle;' align='center'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName("enableEnum", formatUndefine(row.state)))+"</td>";  
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jgdm)+"</td>";
			htmlContent += "</tr>";  
		});
		$("#"+this.id+"_list").html(htmlContent);
		refreshPermission();
	},
	//列表头排序
	orderBy: function(sortColumn, _obj){
		var $column = $("tr th[name='"+this.id+"_column_"+sortColumn+"']", $("#"+this.id+"_table"));
		var orderStyle = $column.attr("class");
		$(".sorting_desc", $("#"+this.id+"_table_div")).removeClass("sorting_desc").addClass("sorting");
		$(".sorting_asc", $("#"+this.id+"_table_div")).removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc")>=0) {
			$("tr th[name='"+this.id+"_column_"+sortColumn+"']", $("#"+this.id+"_table_div")).removeClass("sorting").addClass("sorting_desc");
			orderType = "desc"
		} else {
			$("tr th[name='"+this.id+"_column_"+sortColumn+"']", $("#"+this.id+"_table_div")).removeClass("sorting").addClass("sorting_asc");
			orderType = "asc"
		}
		var currentPage = $("#"+this.id+"_pagination li[class='active']").text();
		if(currentPage == ""){currentPage = 1;}
		this.load(currentPage, sortColumn, orderType);
	},
	//打开新增账号的开窗
	add: function(){
		$("#"+this.id+"_username").val("");
		$("#add_account_form").data('bootstrapValidator').destroy();
        $('#add_account_form').data('bootstrapValidator', null);
		this.initValidatorForm();
		$("#addAccountModal").modal("show");
	},
	//保存账号
	saveAccount: function(){
		startWait($("#addAccountModal"));
		$('#add_account_form').data('bootstrapValidator').validate();
		if(!$('#add_account_form').data('bootstrapValidator').isValid()){
			finishWait($("#addAccountModal"));
			return false;
		}
		var username = $.trim($("#"+this.id+"_username").val());
		var jgdm = $.trim($("#"+this.id+"_jgdm").val());
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/sys/account/save",
			type: "post",
			dataType: "json",
			data: {username: username,
				   jgdm: jgdm},
			modal:$("#addAccountModal"),
			success:function(data){
				$("#addAccountModal").modal("hide");
				AlertUtil.showMessage("保存成功");
				this_.operateId = data;
				finishWait($("#addAccountModal"));
				this_.load(this_.pagination.page, this_.pagination.sort, this_.pagination.order);
		    }
		}); 
	},
	//重置密码
	ResetPassword: function(sid){
		AlertUtil.showConfirmMessage("确定要重置密码吗？",{callback:function(){
		startWait();	
		AjaxUtil.ajax({
			url:basePath+"/sys/account/reset/password/"+sid,
			type: "post",
			dataType: "json",
			success:function(data){
				AlertUtil.showMessage("密码重置成功");
				finishWait();
		    }
		}); 
		}});
	},
	Lock: function(sid, obj){
		AlertUtil.showConfirmMessage("确定要禁用账号吗？",{callback:function(){
		startWait();	
		AjaxUtil.ajax({
			url:basePath+"/sys/account/disable/"+sid,
			type: "post",
			dataType: "json",
			success:function(data){
				$(obj).hide();
				$(obj).siblings(".icon-unlock-alt").show();
				$(obj).parent().parent().parent().find("td:eq(3)").html(DicAndEnumUtil.getEnumName("enableEnum", 0));
				AlertUtil.showMessage("禁用账号成功");
				finishWait();
		    }
		}); 
		}});
	},
	Unlock: function(sid, obj){
		AlertUtil.showConfirmMessage("确定要启用账号吗？",{callback:function(){
		startWait();	
		AjaxUtil.ajax({
			url:basePath+"/sys/account/enable/"+sid,
			type: "post",
			dataType: "json",
			success:function(data){
				$(obj).hide();
				$(obj).siblings(".icon-lock").show();
				$(obj).parent().parent().parent().find("td:eq(3)").html(DicAndEnumUtil.getEnumName("enableEnum", 1));
				AlertUtil.showMessage("启用账号成功");
				finishWait();
		    }
		}); 
		}});
	},
	deleteAccount: function(sid){
		var this_ = this;
		AlertUtil.showConfirmMessage("确定要删除该账号吗？",{callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/sys/account/delete/"+sid,
				type: "post",
				dataType: "json",
				success:function(data){
					AlertUtil.showMessage("删除成功");
					this_.load(this_.pagination.page, this_.pagination.sort, this_.pagination.order);
					finishWait();
			    }
			}); 
			
		}});
	}
};

$(document).ready(function() {
	Navigation(menuCode);
	account.init();
});

//回车事件控制
document.onkeydown = function(event) {
	e = event ? event : (window.event ? window.event : null);
	if (e.keyCode == 13) {
		$('#zhglSearch').trigger('click');
	}
};
