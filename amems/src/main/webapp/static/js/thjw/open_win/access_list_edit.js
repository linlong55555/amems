/**
 * 盖板数据弹窗
 */
open_win_access = {
	id:'open_win_access',
	paginationId:'open_win_access_pagination',
	loaded: false,//是否已加载
	data:[],
	param: {
		parentWinId : '',
		multi:false, //是否多选 默认单选
		selected:null, //已经选择的
		jx:null,//已经选择的集合,id
		accessIdList : [],//回显
		callback:function(){},//回调函数
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		if(!this.loaded){
//			DicAndEnumUtil.registerEnum("materialTypeEnum", "open_win_material_basic_hclx_search");
		}
		if(!this.loaded || isReload === true){
			this.load();
			this.loaded = true;
		}
//		ModalUtil.showSearchModal(this.param.parentWinId,this.id,this.paginationId);
	},
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_hclx_search").val('');
		$("#"+this.id+"_keyword_search").val('');
		if(this.param.multi){
			$("#checkAll", $("#"+this.id)).show();
			$("#checkSingle", $("#"+this.id)).hide();
		}else{
			$("#checkAll", $("#"+this.id)).hide();
			$("#checkSingle", $("#"+this.id)).show();
		}
	},
	//加载数据
	load : 	function(){
		var obj ={};
		$.extend(obj, this.getParams());
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/basic/coverplateinformation/queryWinByFjjx",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				SelectUtil.selectNode('selectAllId',this_.id+"_list");
				if(data != null && data.length >0){
					this_.data = data;
					this_.appendContentHtml(data);
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.paginationId).empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
				}
				this_.loaded = true;
				finishWait();
				new Sticky({tableId:this_.id+"_table"});
		    }
		}); 
	},	
	getParams : function(){
		var searchParam = {};
		searchParam.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		searchParam.dprtcode = this.param.dprtcode;
		searchParam.fjjx = this.param.jx;
		return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			var checked = "";
			if(this_.param.accessIdList.indexOf(row.id) >= 0){
				  checked = "checked";
		  }
		  htmlContent += "<tr style=\"cursor:pointer\" >";
		  htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
		  if(this_.param.multi){
			  htmlContent += "<input "+checked+"  type='checkbox' name='"+this_.id+"_list_checkbox' value='"+index+"'>";  
		   }else{
			  htmlContent += "<input "+checked+"  type='radio' name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
		   }
		   
		   htmlContent += "</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gbbh)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.gbbh)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gbmc)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.gbmc)+"</td>";
		   htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	   SelectUtil.selectNode('selectAllId',this_.id+"_list");
	   this.rowonclick();
	},
	//搜索
	search: function(){
		this.load();
	},
	rowonclick: function(index){
		if(!this.param.multi){
			$("#"+this.id+"_list tr").click(function(event){
				$(this).find("input[type='radio']").attr("checked",true);
			});
		}else{
			$("#"+this.id+"_list tr").click(function(event){
				// 避免复选框重复选择
				if($(event.target).attr("type") == "checkbox"){
					return;
				}
				var checked = $(this).find("input[type='checkbox']").is(":checked");
				$(this).find("input[type='checkbox']").attr("checked",!checked);
			});
		}
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	save: function(){
		var this_ = this;
		if(this.param.callback && typeof(this.param.callback) == "function"){
			if(this.param.multi){
				var datas = [];
				$("#"+this_.id+"_list").find("tr input:checked").each(function(){
					var index = $(this).attr("value");	
					datas.push(this_.data[index]);
				});
				this.param.callback(datas);
			}else{
				var $checkedRadio = $("#"+this.id+"_list").find("input:checked");
				this.param.callback(this.data[$checkedRadio.attr("value")]);
			}
		}
		this.close();
	}
};