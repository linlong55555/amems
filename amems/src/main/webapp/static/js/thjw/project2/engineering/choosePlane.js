choosePlaneModal = {
	id:'choosePlaneModal',
	paginationId:'choosePlaneModal_pagination',
	loaded: false,//是否已加载
	data:[],
	param: {
		parentid:null,//第一层模态框id
		dprtcode:userJgdm,//默认登录人当前机构代码
		fjjx:null,//机型
		sy:1,//全部适用默认1勾选，0不勾选
		existList:null,//回传集合
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		this.data=[];
		$("#"+this.id+"_list").empty();
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		AlertUtil.hideModalFooterMessage();
		this.load();
	},
	//初始化参数
	initParam: function(){		
			if(this.param.sy==1){
				$("#choosePlaneModal input[name='qbsy']").attr("checked",true);
			}else{
				$("#choosePlaneModal input[name='qbsy']").attr("checked",false);
			}
	},
	//加载数据
	load : function(pageNumber, sortColumn, orderType){
		var param ={};
		param.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:10};
		param.fjjx=this.param.fjjx;
		param.dprtcode=this.param.dprtcode;
		var paramsMap={};
		var data=this.param.existList;
		if(data != null && data.length !=0){
			paramsMap.existFjzch=data;			
		}
		param.paramsMap=paramsMap;
		console.info(param);
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url : basePath + "/productionplan/planeData/getFj",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success:function(data){
				if(data.total >0){
					this_.data = data.rows;
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[1,2,3], "#"+this_.id+"_leftlist tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
					$("#"+this_.id+"_pagination").empty();
				}
				finishWait();
		    }
		}); 
	},	
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			htmlContent += "<tr onclick='"+this_.id+".rowonclick(event);' >";
			htmlContent += "<td class='text-center'><input type='checkbox' name='"+this_.id+"_list_radio' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_list')\" ></td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.fjzch)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjzch)+"</td>"; 
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.bzm)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.bzm)+"</td>"; 
		   	htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	},
	rowonclick: function(e){		
		SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");		
	},
	save: function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){		
			var this_ = this;
			var data = [];
			var map={};
			$("#"+this_.id+"_list", $("#"+this_.id)).find("tr input:checked").each(function(){
				var index = $(this).attr("value");	
				data.push(this_.data[index]);
			});
			var sy=0;
			if($("#choosePlaneModal input[name='qbsy']").attr("checked")){
				sy=1;
			}
			if(data==null||data==undefined||data.length==0 && sy==0){
				AlertUtil.showModalFooterMessage("请选择飞机!",this_.id);
				return false;
			}
			map.sy=sy;
			map.fj=data;
			this.param.callback(map);			
			$("#"+this.id).modal("hide");
		}
		applicable_settings.showHidePlaneMinus(); //显示或隐藏删除图标
	},
	loadAllDatas : function(dprtcode){
		var this_ = this;
		this_.data = [];
		this_.param.fjjx = $("#jx_add").val();//机型
		this_.param.dprtcode=dprtcode;
		this_.param.existList = []; 
		this_.sendRequest();
		return this_.data;
	},
	sendRequest:function(){
		var this_=this;
		var param ={};
		param.fjjx=this_.param.fjjx;
		param.dprtcode=this_.param.dprtcode;
		AjaxUtil.ajax({
			url : basePath + "/aircraftinfo/selectByFjjx",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success:function(data){
				this_.data = data;
		    }
		});
	}
};