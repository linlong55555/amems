/**
 * 145工单，来源任务号，EO
 */
Workorder145EOWin = {
	id:'workorder145_eo_Modal', //窗口ID
	baseId:"workorder145_eo", //基础ID
	tbodyId:'', //列表ID
	colOptionheadClass : "colOptionhead",
	param: {
		modalHeadCN : '',//窗口中文名称
		modalHeadENG : '',//窗口英文名称
		parentWinId : '',//父窗口ID
		colOptionhead : true,
		gdid:null,//工单id
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){},//回调函数
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	/**显示窗口*/
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	/**初始数据*/
	initInfo : function(){
		//初始窗口标题信息
		this.initModelHead();
		
		//显示窗口
		ModalUtil.showModal(this.id);
		AlertUtil.hideModalFooterMessage();
		
		//加载数据
		this.loadTableInfo();
	},
	/**初始化对话框头部*/
	initModelHead : function(){
		if('' != this.param.modalHeadCN){
			$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
			$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
		}
	},
	/**获取数据*/
	getData : function(){
		
	},
	/**加载表格信息*/
	loadTableInfo:function(){
		this.goPage(1,"auto","desc");
	},
	goPage:function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
		var this_ = this;
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());

		startWait();
		AjaxUtil.ajax({
			url:basePath+"/project2/eo/queryAllPageList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data.rows.length > 0){
					this_.appendContentHtml(data.rows);
					//分页
					var page_ = new Pagination({
						renderTo : this_.baseId+"_pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage : function(a, b, c) {
							this_.goPage(a, b, c);
						}
					});
					// 标记关键字
					signByKeyword($("#keyword_search").val(),[2,7],"#"+this_.baseId+"_list tr td");
				}else{
					$("#"+this_.baseId+"_list").empty();
					$("#"+this_.baseId+"_pagination").empty();
					$("#"+this_.baseId+"_list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
				}
			}
		});
	},/**将搜索条件封装*/
	getParams : function(){
		var searchParam = {};
		/*searchParam.zt = '0';*/
		return searchParam;
	},
	/**表格拼接*/
	appendContentHtml:function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent +="<tr class='cursor-pointer' bgcolor=\"#f9f9f9\">";
			} else {
				htmlContent +="<tr class='cursor-pointer' bgcolor=\"#fefefe\">";
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zt)+"'>"+StringUtil.escapeStr(row.zt)+"</td>";
			htmlContent +="</tr>";	
		});
		$("#"+this_.baseId+"_list").html(htmlContent);
	},
};