/**
 * 145工单，来源任务号，来源任务NRC
 */
Workorder145LycardWin = {
	id:'workorder145_lycard_Modal', //窗口ID
	baseId:"workorder145_lycard", //基础ID
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
		var this_ = this;
		if('' != this.param.modalHeadCN){
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
		
		//工卡类型
		$("#gklx_search", $("#"+this.id)).empty();
		$("#gklx_search", $("#"+this.id)).append('<option value="" selected="selected">显示全部All</option>');
		DicAndEnumUtil.registerDic('22','gklx_search',this_.param.dprtcode);
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
			url:basePath+"/project2/workcard/queryOriginatingCardList",
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
					signByKeyword($("#keyword_search").val(),[2,7],"#"+this_.baseId+"_list tr td");
				}else{
					$("#"+this_.baseId+"_list").empty();
					$("#"+this_.baseId+"_pagination").empty();
					$("#"+this_.baseId+"_list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				}
			}
		});
	},/**将搜索条件封装*/
	getParams : function(){
		var this_ = this;
		var searchParam = {};
		
		var paramsMap = {};
		paramsMap.keyword = $("#workorder145_lycard_keyword_search").val();
		paramsMap.widType = "145"; //145工单的来源工卡
		paramsMap.jx = StringUtil.escapeStr($("#wo145add_gbjx").val())==""?"-":$("#wo145add_gbjx").val(); //机型
		searchParam.paramsMap = paramsMap;
		searchParam.dprtcode = this_.param.dprtcode;
		searchParam.gklx = $("#gklx_search").val();
		
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
				if(this_.param.selected == row.id){
					htmlContent += "<input type='radio' checked='checked' name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}else{
					htmlContent += "<input type='radio'  name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}
			}
		    htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gkh)+"'>"+StringUtil.escapeStr(row.gkh)+"</td>";
			var zjh = '';
			if(row.fixChapter != null){
				zjh = StringUtil.escapeStr(row.fixChapter.displayName);
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(zjh)+"'>"+StringUtil.escapeStr(zjh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gzbt)+"'>"+StringUtil.escapeStr(row.gzbt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gklx)+"'>"+StringUtil.escapeStr(row.gklx)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zy)+"'>"+StringUtil.escapeStr(row.zy)+"</td>";
			htmlContent += "<td title='"+this_.formatCoverPlate(row.coverPlateList, 1)+"' style='text-align:left;vertical-align:middle;' >"+this_.formatCoverPlate(row.coverPlateList, 1)+"</td>";
			htmlContent += "<td title='"+this_.formatCoverPlate(row.coverPlateList, 2)+"' style='text-align:left;vertical-align:middle;'>"+this_.formatCoverPlate(row.coverPlateList, 2)+"</td>";
			htmlContent += "<td title='"+this_.formatCoverPlate(row.coverPlateList, 3)+"' style='text-align:left;vertical-align:middle;'>"+this_.formatCoverPlate(row.coverPlateList, 3)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gklx)+"'>"+StringUtil.escapeStr(row.gklx)+"</td>";
			var gzzstr = '';
			if(row.workGroup != null){
				gzzstr = StringUtil.escapeStr(row.workGroup.gzzdm) + " " + StringUtil.escapeStr(row.workGroup.gzzmc);
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(gzzstr)+"'>"+StringUtil.escapeStr(gzzstr)+"</td>";
			var bzgs = '';
			if(row.jhgsRs != null && row.jhgsRs != "" && row.jhgsXss != null && row.jhgsXss != ""){
				var total = row.jhgsRs*row.jhgsXss;
				if(total == ''){
					total = 0;
				}
				if(String(total).indexOf(".") >= 0){
					total = total.toFixed(2);
				}
				bzgs = row.jhgsRs+"人x"+row.jhgsXss+"时="+total+"时";
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(bzgs)+"'>"+StringUtil.escapeStr(bzgs)+"</td>";
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
				var $checkedRadio = $("#"+this_.baseId+"_list").find("tr input:checked");
				if($checkedRadio.length > 0){
					this_.param.callback(this_.data[$checkedRadio.attr("value")]);
				}else{
					this_.param.callback(null);
				}
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
			$("#workorder145_lycard_list :checkbox[name='"+this_.id+"_list_checkbox']").attr("checked", false);
		}else{
			$("#workorder145_lycard_list :radio[name='"+this_.id+"_list_checkbox']").attr("checked", false);
		}
		this_.save();
	},
	/**
	 * 格式化接近盖板
	 */
	formatCoverPlate : function(coverPlateList, type){
		var str = '';
		if(coverPlateList != null && coverPlateList.length >0){
			$.each(coverPlateList,function(i,obj){
				if(obj.lx == type){
					str += StringUtil.escapeStr(obj.gbh)+" "+StringUtil.escapeStr(obj.coverPlateInformation?obj.coverPlateInformation.gbmc:"") + ",";
				}
			});
	  	}
		if(str != ''){
			str = str.substring(0,str.length - 1);
		}
		return str;
	},
};