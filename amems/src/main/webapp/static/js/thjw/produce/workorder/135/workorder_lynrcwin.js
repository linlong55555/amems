/**
 * 135工单，来源任务号，来源任务NRC
 */
Workorder135NrcWin = {
	id:'workorder135_lynrc_Modal', //窗口ID
	baseId:"workorder135_lynrc", //基础ID
	tbodyId:'', //列表ID
	colOptionheadClass : "colOptionhead",
	data:[],
	param: {
		modalHeadCN : '',//窗口中文名称
		modalHeadENG : '',//窗口英文名称
		parentWinId : '',//父窗口ID
		colOptionhead : true,
		multi:true, //是否多选 默认单选
		selected:null, //已经选择的
		gdlx:"",//工单类型
		gdzt:"",//工单状态
		gdid:null,//工单id
		fjzch:"",//飞机注册号
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){},//回调函数
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	/**显示窗口*/
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();
		this.initInfo();
	},
	clearData : function(){
		$("#workorder135_lynrc_keyword").val("");
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
		var this_ = this;
		if('' != this_.param.modalHeadCN){
			$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
			$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
		}
		
		if(this_.param.multi){
			$("#checkAll", $("#"+this.id)).show();
			$("#checkSingle", $("#"+this.id)).hide();
		}else{
			$("#checkAll", $("#"+this.id)).hide();
			$("#checkSingle", $("#"+this.id)).show();
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
			url:basePath+"/produce/workorder/queryAllPageList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data.rows.length > 0){
					this_.data = data.rows;
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
					signByKeyword($("#workorder135_lynrc_keyword").val(),[2,3],"#"+this_.baseId+"_list tr td");
				}else{
					$("#"+this_.baseId+"_list").empty();
					$("#"+this_.baseId+"_pagination").empty();
					$("#"+this_.baseId+"_list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
				}
			}
		});
	},/**将搜索条件封装*/
	getParams : function(){
		var this_ = this;
		var searchParam = {};
		
		var paramsMap = {};
		searchParam.fjzch = this_.param.fjzch;
		paramsMap.keyword = $("#workorder135_lynrc_keyword").val();
		paramsMap.gdlx = this_.param.gdlx;
		paramsMap.gdzt = this_.param.gdzt;
		
		searchParam.paramsMap = paramsMap;
		searchParam.dprtcode = this_.param.dprtcode;
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
			
		   //操作
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
		   if(this_.param.multi){
			   htmlContent += "<input type='checkbox' name='"+this_.id+"_list_checkbox' value='"+index+"'>";  
			}else{
				if(null != row.paramsMap && this_.param.selected == row.paramsMap.gdid){
					htmlContent += "<input type='radio' checked='checked' name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}else{
					htmlContent += "<input type='radio'  name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}
			}
		    htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"'>"+StringUtil.escapeStr(row.gdbh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
			var lyfx = formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(row.gdlx)));
			if(row.paramsMap.gdzlx){
				lyfx = formatUndefine(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum', StringUtil.escapeStr(row.paramsMap.gdzlx)))
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(lyfx)+"'>"+StringUtil.escapeStr(lyfx)+"</td>";
			htmlContent += "<td title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"</td>";
			htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.jhKsrq).substring(0, 10)+"'>"+StringUtil.escapeStr(row.jhKsrq).substring(0, 10)+"</td>";
			htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.jhJsrq).substring(0, 10)+"'>"+StringUtil.escapeStr(row.jhJsrq).substring(0, 10)+"</td>";
			htmlContent +="</tr>";	
		});
		$("#"+this_.baseId+"_list").html(htmlContent);
		SelectUtil.selectNode('selectAllId',this_.baseId+"_list");
		this_.rowonclick();
	},
	rowonclick: function(index){
		if(!this.param.multi){
			$("#"+this.baseId+"_list tr").click(function(event){
				$(this).find("input[type='radio']").attr("checked",true);
			});
		}else{
			$("#"+this.baseId+"_list tr").click(function(event){
				// 避免复选框重复选择
				if($(event.target).attr("type") == "checkbox"){
					return;
				}
				var checked = $(this).find("input[type='checkbox']").is(":checked");
				$(this).find("input[type='checkbox']").attr("checked",!checked);
			});
		}
	},
	/**确定*/
	save: function(){
		var this_ = this;
		if(this_.param.callback && typeof(this_.param.callback) == "function"){
			if(this_.param.multi){
				var datas = [];
				$("#"+this_.baseId+"_list").find("tr input:checked").each(function(){
					var index = $(this).attr("value");	
					datas.push(this_.data[index]);
				});
				this_.param.callback(datas);
			}else{
				var $checkedRadio = $("#"+this_.baseId+"_list").find("input:checked");
				this_.param.callback(this_.data[$checkedRadio.attr("value")]);
			}
		}
		this.close();
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	/**开始搜索*/
	doSearch : function(){
		this.loadTableInfo();
	},
	//清空数据
	clear : function(){
		var this_ = this;
		if(this_.param.multi){
			$("#"+this_.baseId+"_table :checkbox[name='"+this_.id+"_list_checkbox']").attr("checked", false);
			$(".sticky-col :checkbox[name='"+this_.id+"_list_checkbox']").attr("checked", false);
		}else{
			$("#"+this_.baseId+"_table :radio[name='"+this_.id+"_list_checkbox']").attr("checked", false);
			$(".sticky-col :radio[name='"+this_.id+"_list_checkbox']").attr("checked", false);
		}
		this_.save();
	}
};