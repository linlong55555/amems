/**
 * 拆下件弹窗
 */
material_main = {
	id:'material_main',
	paginationId:'material_main_pagination',
	loaded: false,//是否已加载 
	data:[],
	param: {
		multi:false, //是否多选 默认单选
		clearProject: false,
		selected:null, //已经选择的
		fjzch:'', //飞机注册号
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	}, 
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		if(param){
			$.extend(this.param, param);
		}
		//$("#"+this.id).modal("show");
		this.initParam();
		this.load();
		if(param.clearProject == false){
			$("#project_btn_clear").hide();
		}
		if(!this.loaded || isReload === true){
		}
		var this_ = this;
	},
	//初始化参数
	initParam: function(){
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
	load : 	function(pageNumber, sortColumn, orderType){
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		
		var obj = this.getParams();
		obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:10};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/material/material/queryAllPageList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				SelectUtil.selectNode('selectAllId',this_.id+"_list");
				if(data.total >0){
					this_.data = data.rows;
					this_.appendContentHtml(data.rows);
					TableUtil.addTitle("#"+this_.id+"_list tr td");
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#materialAndOutfield_keyword_search").val(),[2,3,4,5,13,16], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"17\">暂无数据 No data.</td></tr>");
				}
				//new Sticky({tableId:"open_win_regist_basic_table"});
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},	
	getParams : function(){
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#materialAndOutfield_keyword_search").val());
		searchParam.dprtcode = this.param.dprtcode;
		if('' != keyword){
			searchParam.keyword = keyword;
		}
		var existsIdList = this.param.existsIdList;
		if(existsIdList != null && existsIdList != '' && existsIdList.length > 0){
		 paramsMap.existsIdList = existsIdList;
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='"+this_.id+".rowonclick(event);' >";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.id+".rowonclick(event);' >";
			}
		   
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
			if(this_.param.multi){
				htmlContent += "<input type='checkbox' name='"+this_.id+"_list_radio' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_list')\" >"; 
			}else{
				var checked = "";
				if(this_.param.selected && this_.param.selected == row.id){
					checked = "checked";
				}
				htmlContent += "<input type='radio' name='"+this_.id+"_list_radio' value='"+index+"' "+checked+" >"; 
			}
			htmlContent += "</td>";
	   
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' class='text-left'>";
		   htmlContent += StringUtil.escapeStr(row.bjh);
		   htmlContent += "</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.cjjh)+"' class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+"' class='text-left'>"+StringUtil.escapeStr(row.ywms)+"</td>"; 
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' class='text-left'>"+StringUtil.escapeStr(row.zwms)+"</td>"; 
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' class='text-left'>"+StringUtil.escapeStr(row.jhly)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bzjh)+"' class='text-left'>"+StringUtil.escapeStr(row.bzjh)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gse)+"' class='text-left'>"+StringUtil.escapeStr(row.gse)+"</td>";
		   var zzjbs=row.zzjbs=="1"?"是":"否";
		   htmlContent += "<td  title='"+StringUtil.escapeStr(zzjbs)+"' class='text-center'>"+StringUtil.escapeStr(zzjbs)+"</td>";
		   if('00000' == formatUndefine(row.xh)){
			   htmlContent = htmlContent + "<td>通用Currency</td>";
		   }else{
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.xh)+"' class='text-left'>"+StringUtil.escapeStr(row.xh)+"</td>";
		   }
		   
		   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb) +"</td>";
		   if(1 == row.isMel){
			   htmlContent += "<td class='text-center'>是</td>";
		   }else{
			   htmlContent += "<td class='text-center'>否</td>";
		   }
		   var zjh = '';
		   if(row.fixChapter != null){
			   zjh = StringUtil.escapeStr(row.fixChapter.zjh) + " " + StringUtil.escapeStr(row.fixChapter.zwms);
		   }
		   htmlContent += "<td title='"+zjh+"' class='text-left'>"+zjh+"</td>";
		   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx) +"</td>";
		   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jldw) +"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		   htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	   SelectUtil.selectNode('selectAllId',this_.id+"_list");
	},
	//搜索
	search: function(){
		this.load();
	},
	clearProject:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
	},
	//确认
	rowonclick: function(e){
		if(!this.param.multi){
			$(e.target).parent().find("input[type='radio']").attr("checked",true);
		}else{
			SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");
		}
	}
};