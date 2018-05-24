/**
 * 拆下件弹窗
 */
open_win_installationlist_removed = {
	id:'open_win_installationlist_removed',
	paginationId:'open_win_installationlist_removed_pagination',
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
		$("#"+this.id).modal("show");
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
			url: basePath+"/aircraftinfo/installationlist/effectpagelist",
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3,5,6,9], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
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
		var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		searchParam.dprtcode = this.param.dprtcode;
		searchParam.fjzch = this.param.fjzch;
		searchParam.fjdid = this.param.fjdid;
		searchParam.wz = this.param.wz;
		searchParam.jssj = this.param.jssj;
		searchParam.zjzt= 1;
		if('' != keyword){
		 paramsMap.cxKeyword = keyword;
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
		   
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr((row.ywmc||"") + " " + (row.zwmc||""))+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hcMainData ? row.hcMainData.xingh : "")+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hcMainData ? row.hcMainData.gg : "")+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('installedPositionEnum', (row.wz))+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hcMainData ? row.hcMainData.cjjh : "")+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fixChapter ? row.fixChapter.displayName : "")+"</td>";  
			var xh = StringUtil.escapeStr(row.hcMainData ? row.hcMainData.xh : "");
			if('00000' == xh){
				xh = "通用Currency";
			}
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+xh+"</td>";  
			
		   	htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	   SelectUtil.selectNode('selectAllId',this_.id+"_list");
	},
	//搜索
	search: function(){
		this.load();
	},
	//确认
	rowonclick: function(e){
		if(!this.param.multi){
			$(e.target).parent().find("input[type='radio']").attr("checked",true);
		}else{
			SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");
		}
	},
	save: function(){
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			if(this.param.multi){
				var this_ = this;
				var data = [];
				$("#"+this_.id+"_list", $("#"+this_.id)).find("tr input:checked").each(function(){
					var index = $(this).attr("value");	
					data.push(this_.data[index]);
				});
				this.param.callback(data);
			}else{
				var $checkedRadio = $("#"+this.id+"_list").find("input:checked");
				var index = $checkedRadio.attr("value");	
				var data = this.data;
				if(data.length > 0 && index != undefined){			
					this.param.callback(this.data[index]);
				}else{
					
				}
			}
		}
	},
	clearProject:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
	},
};