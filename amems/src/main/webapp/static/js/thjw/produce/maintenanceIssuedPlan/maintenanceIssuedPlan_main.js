$(function(){
			Navigation(menuCode);
			maintencePlanList.init();
			maintencePlanList.goPage(1,"auto","desc");
			maintencePlanList.createToggle();
})
var dataArray;
var maintencePlanList={
	id : 'maintencePlanList',
	gddiv : 'gd_panel_div',
	paginationId:'pagination_gd',
	toggleId:"maintenance_toggle_div",
	toggleParentId:"right_div",
	jksjType : ["", "维修项目", "EO", "工单"],
	selectTR:null,
	gbList : [],
	lyfl_option : '',
	isHxpg : false,
	pagination : {},
	pagination_gd : {},
	gbid : '',
	gbbh : '',
	nbZxdwList : [],
	wwZxdwList : [],
	init : function(){
		$("#gb_sm", $("#"+this.gddiv)).html("");
		this.lyfl_option = $("#lyfl_search", $("#"+this.gddiv)).html();
		this.initfjzch();
		this.ininZxdw(userJgdm);
		this.loadWorkPackage();
		this.multiselect();
		this.initDic(userJgdm);
	},
	/**
	 * 初始化执行单位
	 */
	ininZxdw : function(dprtcode){
		var this_ = this;
		this_.initJd(dprtcode, function(nbZxdwList){
			this_.nbZxdwList = nbZxdwList;
			this_.initWwgys(dprtcode, function(wwZxdwList){
				this_.wwZxdwList = wwZxdwList;
				this_.clickZxdw();
			});
		});
	},
	/**
	 * 初始化多选下拉框
	 */
	multiselect : function(){
		var this_ = this;
		//生成多选下拉框区域
		$('#lyfl_div',$("#"+this.gddiv)).empty();
		$('#lyfl_div',$("#"+this.gddiv)).html("<select class='form-control' multiple='multiple' id='lyfl_search'></select>");
		$("#lyfl_search",$("#"+this.gddiv)).html(this.lyfl_option);
		//生成多选来源分类
		$("#lyfl_search", $("#"+this.gddiv)).multiselect({
			buttonClass: 'btn btn-default',
			buttonWidth:'100%' ,
			buttonHeight: '34px',
			buttonContainer: '<div class="btn-group base-data-syjx" style="min-width:100%;" />',
			numberDisplayed:100,
			includeSelectAllOption: true,
			nonSelectedText:'显示全部 All',
		    allSelectedText:'显示全部 All',
			selectAllText: '选择全部 Select All',
			onChange : function(element, checked) {
			}
		});
		
		// 生成多选下拉框(状态)
		var ztList = this.initGdztList();
		$("#gdztDiv").empty();
		$("#gdztDiv").html("<select multiple='multiple'  id='gdzt_search'></select>");
		$("#gdzt_search").append(ztList);
		$("#gdzt_search").multiselect({
			buttonClass : 'btn btn-default ',
			buttonWidth: '100%',
			numberDisplayed:100,
			buttonContainer: '<div class="btn-group" style="width:100%;" />',
			includeSelectAllOption : true,
			nonSelectedText:'显示全部 All',
			allSelectedText:'显示全部 All',
			selectAllText: '选择全部 Select All',
			onChange : function(element, checked) {
			}
		});
		$('#gdzt_search').multiselect('select', ['7','10']);
	},
	initGdztList: function (){
		var list='';
		var typeList = DicAndEnumUtil.data.enumMap.workorderStatusEnum;
		if (typeList.length > 0) {
			for (var i = 0; i < typeList.length; i++) {
				if(typeList[i].id != 1 && typeList[i].id != 2){
					list += "<option value='"
						+ StringUtil.escapeStr(typeList[i].id) + "'>"
						+ StringUtil.escapeStr(typeList[i].name) + "</option>";
				}
			}
		} 
		return list;
	},
	/**
	 * 初始化数据字典
	 */
	initDic : function(dprtcode){
		if(typeof(dprtcode) == "undefined"){
			dprtcode = userJgdm;
		}
		$("#gzlx_search", $("#"+this.gddiv)).empty();
		$("#gzlx_search", $("#"+this.gddiv)).append('<option value="" selected="selected">显示全部All</option>');
		DicAndEnumUtil.registerDic('17','gzlx_search',dprtcode);
		
		$("#wxlx_search", $("#"+this.gddiv)).empty();
		$("#wxlx_search", $("#"+this.gddiv)).append('<option value="" selected="selected">显示全部All</option>');
		DicAndEnumUtil.registerDic('71','wxlx_search',dprtcode);
		
	},
	/**
	 * 打开ATA章节号对话框
	 */
	openChapterWin : function(){
		var this_ = this;
		var zjh = $.trim($("#zjh_search", $("#"+this_.id)).val());
		FixChapterModal.show({
			parentWinId : '',
			selected:zjh,//原值，在弹窗中默认勾选
			dprtcode:this_.dprtcode,//机构代码
			callback: function(data){//回调函数			
				var zjh=data.zjh==null?"":data.zjh;
				$("#zjh_search", $("#"+this_.gddiv)).val(zjh);
			}
		});
	},
	/**
	 * 改变飞机注册号时触发
	 */
	changeModel : function(){
		var this_ = this;
		var searchParam ={};
		$.extend(searchParam, this_.getParams());
		searchParam.paramsMap.keyword = "";
		this_.performWPQuery(searchParam);
	},
	//加载数据
	loadWorkPackage : function(){
		var this_ = this;
		var searchParam ={};
		$.extend(searchParam, this_.getParams());
		this_.performWPQuery(searchParam);
	},
	performWPQuery : function(param){//执行工包查询
		var this_ = this;
		this_.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
		param.pagination = this_.pagination;
		this_.gbList = [];
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/produce/workpackage/queryWorkpackageList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(param),
			success:function(data){
				finishWait();
				this_.gbList = data;
				this_.appendWPHtml(data);
			}
	    }); 
	},
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_wp_search", $("#"+this.id)).val());
		var fjzch = $.trim($("#fjzch_search", $("#"+this.id)).val());
		var jhZxdwid = $.trim($("#zxdw_search", $("#"+this.id)).val());
		searchParam.dprtcode = userJgdm;
		paramsMap.keyword = keyword;
		if(fjzch != ''){
			searchParam.fjzch = fjzch == '-'?'':fjzch;
		}
		searchParam.jhZxdwid = jhZxdwid;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		searchParam.zt = 7;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	/**
	 * 拼接列表
	 */
	appendWPHtml: function(list){
		var this_ = this;
		var id = '';
		var htmlContent = '';
		$.each(list,function(index,row){
			
			if(index == 0){
				id = row.id;
			}
			
			htmlContent += "<tr id='"+row.id+"' gbbh='"+StringUtil.escapeStr(row.gbbh)+"' onclick="+this_.id+".initWorkOrder('"+row.id+"')>";
			htmlContent += "<td class='text-center'><input checkid='"+row.id+"' type='checkbox' />";
			htmlContent += "</td>";
			htmlContent += "<td>";
			htmlContent += "<p class='margin-bottom-0'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkpackage('"+row.id+"')>"+StringUtil.escapeStr(row.gbbh)+"</a>";
			htmlContent += "</p>";
			htmlContent += "<p title='"+StringUtil.escapeStr(row.gbmc)+"' class='tag-set margin-bottom-0'>"+StringUtil.escapeStr(row.gbmc)+"</p>";
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhZxdw)+"' style='text-align:center;vertical-align:middle;'>";
			htmlContent += StringUtil.escapeStr(row.jhZxdw);
			htmlContent +="</td>";
			
			htmlContent += "<td class='text-center'>";
			
			htmlContent += "<p class='margin-bottom-0'>"+(row.wgbs==1?"已反馈":"未反馈")+"</p>";
			htmlContent += "<p class='margin-bottom-0'>";
			htmlContent += "<i class='iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage:main:05' onClick=\""+this_.id+".doWgfk('"
			+ row.id + "')\" title='完工反馈 Feedback'></i>&nbsp;&nbsp;";
			
			htmlContent += "<i class='icon-print color-blue cursor-pointer checkPermission' permissioncode='project2:bulletin:02' onClick=\""+this_.id+".doPrint('"
			+ row.id + "')\" title='打印 Print'></i>";
			
			htmlContent += "</p>";
			
			htmlContent += "</td>";
			
			htmlContent += "</tr>"; 
		    
		});
		if(htmlContent != ''){
			$("#gb_list_tbody", $("#"+this_.id)).empty();
			$("#gb_list_tbody", $("#"+this_.id)).html(htmlContent);
		}else{
			this_.setWPNoData();
		}
		this_.initWorkOrder(id);//清空工单
	},
	/**
	 * 清空数据
	 */
	setWPNoData : function(){
		var this_ = this;
		$("#gb_list_tbody", $("#"+this_.id)).empty();
		$("#gb_list_tbody", $("#"+this_.id)).append("<tr><td colspan=\"4\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	/**
	 * 完工反馈
	 */
	doWgfk : function(id){
		var this_ = this;
		this_.getDataById(id,function(obj){
			if(!(obj.zt == 7 )){
				AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
				return;
			}
			workpackage_feedback_modal.init({
				obj:obj,
				dprtcode:obj.dprtcode,
				workpackageId:obj.id,
				operation:true,		
				callback:function(data){
					this_.doRequest(data,"工包完工反馈成功!",basePath + "/produce/workpackage/feedback","workpackage_feedback_modal");
				}
			});
		});
	},
	/**
	 * 打印
	 */
	doPrint : function(id){
		alert("打印功能未完善");
	},
	/**
	 * 执行
	 */
	doRequest : function(data,message,url,modal){
		var this_ = this;
		AjaxUtil.ajax({
			url : url,
			type : "post",
			data : JSON.stringify(data),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success : function(data) {
				AlertUtil.showMessage(message);
				$("#"+modal).modal("hide");
				this_.loadWorkPackage();
			}
		});
	},
	/**
	 * 获取工包数据
	 */
	getDataById : function(id,obj){
		var this_ = this;
		var param={};
		param.id=id;
		AjaxUtil.ajax({
			url : basePath + "/produce/workpackage/getRecord",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			success : function(data) {
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}			
			}
		});
	},
	/**
	 * 查看工包
	 */
	viewWorkpackage : function(id){
		window.open(basePath+"/produce/workpackage/view?id="+id);
	},
	/**
	 * 初始化
	 */
	initWorkOrder : function(id){
		this.gbid = id;
		this.isHxpg = false;
		this.clearGd();
		if(id == 9){
			this.isHxpg = true;
		}
		var gbbh = '';
		if(id != ''){
			$("#" + id, $("#gb_list_tbody")).addClass('bg_tr').siblings().removeClass('bg_tr');
			gbbh = $("#" + id, $("#gb_list_tbody")).attr("gbbh");
		}
		$('.webui-popover:visible').css("display","none");
		this.searchGds(gbbh);
		this.searchGd();
	},
	/**
	 * 清空工单数据
	 */
	clearGd : function(){
		this.searchreset();
//		$("#gb_sm", $("#"+this.gddiv)).html("");
		this.setNoData();
	},
	createToggle:function(){
		var toggleHtml='<div id="'+this.toggleId+'"  class="toggleLeftRight">'
			toggleHtml+='<i class="cursor-pointer icon-caret-left" onclick="maintencePlanList.toggleMaintenancePlan(this)"></i>'
			toggleHtml+='</div>'
		if($("#"+this.toggleId).length<1){
			$("#"+this.toggleParentId).append(toggleHtml);
		}
	},
	searchGds : function(gbbh){
		var this_ = this;
		var searchParam ={};
		$.extend(searchParam, this.getGdsParams());
		if(this.gbid == null || this.gbid == ''){
			$("#gb_sm", $("#"+this_.gddiv)).html("");
			return;
		}
		var url = basePath+"/produce/workorder/queryCount4WorkOrder";
		startWait();
		AjaxUtil.ajax({
			url : url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				$("#gb_sm", $("#"+this_.gddiv)).html("");
				if(null != data){
					var zCount = data.TCOUNT;
					var yfkCount = data.FCOUNT;
					var ygbCount = data.GCOUNT;
					var gb_sm = '';
					if(gbbh != null){
						gb_sm += gbbh + "工包：";
					}
					gb_sm += "工单总数"+zCount;
					gb_sm += "。已反馈"+yfkCount;
					gb_sm += "，未反馈"+(zCount - yfkCount);
					gb_sm += "；已关闭"+ygbCount;
					gb_sm += "；未关闭"+(zCount - ygbCount)+"。";
					$("#gb_sm", $("#"+this_.gddiv)).html(gb_sm);
				}
			}
	    }); 
	},
	getGdsParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var fjzch = $.trim($("#fjzch_search", $("#"+this.id)).val());
		var gbzt = $.trim($("#gbzt_search", $("#"+this.gddiv)).val());
		searchParam.dprtcode = userJgdm;
		searchParam.fjzch = fjzch;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		paramsMap.notGdzl = "notGdzl";
		if(this.isHxpg){
			searchParam.gdlx = this.gbid;
		}else{
			searchParam.gbid = this.gbid;
		}
		paramsMap.gbztList = [7,9,10];
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	searchGd : function(){
		this.goPage(1,"auto","desc");
	},
	goPage:function(pageNumber, sortColumn, orderType){
		this.AjaxGetDatasWithSearch(pageNumber, sortColumn, orderType);
	},
	//加载数据
	load : function(pageNumber, sortColumn, orderType){
		this.AjaxGetDatasWithSearch(pageNumber,sortColumn,orderType);
	},
	AjaxGetDatasWithSearch:function(pageNumber, sortColumn, orderType){
		var this_ = this;
		this_.hideBottom();
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		var searchParam ={};
		this_.pagination_gd = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		searchParam.pagination = this_.pagination_gd;
		$.extend(searchParam, this_.getGdParams());
		
		if(this_.gbid == null || this_.gbid == ''){
			this_.setNoData();
//			new Sticky({tableId:'maintenanceplan_table'});
			return;
		}
		var url = basePath+"/produce/workorder/queryAllPageWorkOrderList";
		startWait();
		AjaxUtil.ajax({
			url : url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
//				$("#gb_sm", $("#"+this_.gddiv)).html("");
				if(data.total >0){
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#keyword_gd_search", $("#"+this_.gddiv)).val(),[3,4,9,17],"#maintenanceIssuedPlan_list tr td");
				} else {
					this_.setNoData();
				}
				new Sticky({tableId:'maintenanceplane_workorder_table'});
			}
	    }); 
	},
	getGdParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_gd_search", $("#"+this.gddiv)).val());
		var fjzch = $.trim($("#fjzch_search", $("#"+this.gddiv)).val());
		var fkzt = $.trim($("#fkzt_search", $("#"+this.gddiv)).val());
		var lyfl = $.trim($("#lyfl_search", $("#"+this.gddiv)).val());
		var gdzt = $.trim($("#gdzt_search", $("#"+this.gddiv)).val());
		var zjh = $.trim($("#zjh_search", $("#"+this.gddiv)).val());
		var gzlx = $.trim($("#gzlx_search", $("#"+this.gddiv)).val());
		searchParam.dprtcode = userJgdm;
		searchParam.fjzch = fjzch;
		paramsMap.keyword = keyword;
		paramsMap.userId = userId;
		paramsMap.userType = userType;
		paramsMap.notGdzl = "notGdzl";
//		searchParam.zt = 7;
		paramsMap.gbztList = [7,9,10];
		if(this.isHxpg){
			searchParam.gdlx = this.gbid;
		}else{
			searchParam.gbid = this.gbid;
		}
		if(fkzt != null && fkzt != ''){
			searchParam.wgbs = fkzt;
		}
		if(lyfl != null && lyfl.length > 0){
			var gdlxArr = lyfl.split(",");
			var gdlxList = [];
			for(var i = 0 ; i < gdlxArr.length ; i++){
				if('multiselect-all' != gdlxArr[i]){
					gdlxList.push(gdlxArr[i]);
				}
			}
			if(gdlxList.length > 0){
				paramsMap.gdlxList = gdlxList;
			}
		}
		
		if(gdzt != null && gdzt.length > 0){
			var gdztArr = gdzt.split(",");
			var gdztList = [];
			for(var i = 0 ; i < gdztArr.length ; i++){
				if('multiselect-all' != gdztArr[i]){
					gdztList.push(gdztArr[i]);
				}
			}
			if(gdztList.length > 0){
				paramsMap.gdztList = gdztList;
			}
		}
		if(zjh != null && zjh != ''){
			searchParam.zjh = zjh;
		}
		if(gzlx != null && gzlx != ''){
			searchParam.gzlb = gzlx;
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml:function(list){
		var this_ =this;
		var htmlContent = '';
		var wfkCount = 0;
		var yfkCount = 0;
		var gbbh = '';
		$.each(list,function(index,row){
			var paramsMap = row.paramsMap?row.paramsMap:{};
			
			var chlidStr = '';
			
			if(row.wgbs == 1){
				yfkCount++;
			}else{
				wfkCount++;
			}
			var haveZgd = false;
			gbbh = paramsMap.gbbh;
			htmlContent += "<tr class='cursor-pointer tr-bold' jksjid='"+StringUtil.escapeStr(row.jksjid)+"' fjzch='"+row.fjzch+"' gdbh='"+StringUtil.escapeStr(row.gdbh)+"' gdid='"+row.id+"' id='"+row.id+"' onclick='"+this_.id+".showHiddenContent(this)' >";
			htmlContent += "<td class='' style='text-align:center;vertical-align:middle;'><input checkid='"+row.id+"' type='checkbox'/></td>";
			var lyfl = this_.jksjType[row.gdlx];
			lyfl = DicAndEnumUtil.getEnumName('workorderTypeEnum', row.gdlx);
			if(row.gdlx == 1){
				lyfl = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.gdzlx); 
				if(paramsMap.gdzlx == 4){
					if(paramsMap.zgdCount > 0){
						haveZgd = true;
						chlidStr += "<span class='badge' style='background:#3598db;' name='badgeCount'>"+paramsMap.zgdCount+"</span>";
						chlidStr += '  <i class="fa fa-angle-down tb cursor-pointer" ></i>  '
					}
				}
			}
			
			if(haveZgd){
				htmlContent += "<td style='text-align:left;vertical-align:middle;' onclick="+this_.id+".showChildTd(event,this,'"+row.gdsbid+"','"+row.id+"')>";
			}else{
				htmlContent += "<td style='text-align:left;vertical-align:middle;' >";
			}
			
			
			if(row.zt == 7){
				htmlContent += "<i class='spacing iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:04' onclick="+this_.id+".openWinFeedback('"+ row.id + "') title='完工反馈 Feedback'></i>";
			}
			
			htmlContent += chlidStr;  
			
			htmlContent += "</td>";  
			
//			if(haveZgd){
//				htmlContent += "<td style='text-align:left;vertical-align:middle;' onclick="+this_.id+".showChildTd(event,this,'"+row.gdsbid+"','"+row.id+"')>"+formatUndefine(lyfl)+"</td>";
//			}else{
//				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+formatUndefine(lyfl)+"</td>";
//			}
			htmlContent += this_.getGdTd(row);
			htmlContent += "</tr>";
	   
		});
		var gb_sm = gbbh+"工包：工单总数"+list.length+",已反馈"+yfkCount+",未反馈"+wfkCount;
//		$("#gb_sm", $("#"+this_.gddiv)).html(gb_sm);
	 
	 	$("#maintenanceIssuedPlan_list", $("#"+this_.gddiv)).empty();
		$("#maintenanceIssuedPlan_list", $("#"+this_.gddiv)).html(htmlContent);
		this_.initWebuiPopover();
		this_.initGkWebuiPopover();
		refreshPermission();
	},
	getGdTd : function(row){
		var this_ =this;
		var htmlContent = '';
		
		var paramsMap = row.paramsMap?row.paramsMap:{};
		var zjh = StringUtil.escapeStr(row.zjh) + " " + StringUtil.escapeStr(paramsMap.zjhywms);
		var zxdx = '';
		var zxdxTitle = '';
		var zy_gzlb_qy = '';
		var yjwwbs = '';
		var yjzxdw = '';
		
		if(row.zy != null){
			zy_gzlb_qy += row.zy;
		}
		if(row.gzlb != null){
			if(row.zy != null){
				zy_gzlb_qy += "/";
			}
			zy_gzlb_qy += row.gzlb;
		}
		if(paramsMap.qy != null){
			if(row.zy != null || row.gzlb != null){
				zy_gzlb_qy += "/";
			}
			zy_gzlb_qy += paramsMap.qy;
		}
		
		/*if(row.fjzch != null){
			zxdx += "<p class='margin-bottom-0'>";
			zxdx += "机号/MSN："+row.fjzch;
			zxdxTitle += "机号/MSN："+row.fjzch;
			if(paramsMap.msn != null){
				zxdx += "/" + paramsMap.msn;
				zxdxTitle += "/" + paramsMap.msn;
			}
			zxdx += "</p>";
			zxdxTitle += "\n";
		}
		*/
		if(paramsMap.bjh != null){
			zxdx += "PN："+paramsMap.bjh + " ";
			zxdxTitle += "PN："+paramsMap.bjh + " ";
		}
		if(paramsMap.xlh != null){
			zxdx += "SN："+paramsMap.xlh;
			zxdxTitle += "SN："+paramsMap.xlh;
		}
		yjwwbs = '内部';
		if(paramsMap.yjWwbs == 1){
			yjwwbs = '外委';
		}
		if(paramsMap.yjZxdw != null && paramsMap.yjZxdw != ''){
			yjzxdw = yjwwbs + " " + StringUtil.escapeStr(paramsMap.yjZxdw);
		}
		
		/*任务信息*/
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
		htmlContent += "<p title='"+StringUtil.escapeStr(row.lyrwh)+"' class='tag-set margin-bottom-0'>";
		htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewTask('"+StringUtil.escapeStr(row.lyrwid)+"','"+StringUtil.escapeStr(row.jksjid)+"',"+row.gdlx+")>"+StringUtil.escapeStr(row.lyrwh)+"</a>";
		htmlContent += "</p>";
//		htmlContent += "<p title='"+StringUtil.escapeStr(paramsMap.ckh)+"' class='tag-set margin-bottom-0'>"+StringUtil.escapeStr(paramsMap.ckh)+"</p>";
		htmlContent +="</td>";
		
		/*工单标题*/
		htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
		/*关闭状态*/
		if(row.zt==9){
			htmlContent += "<td style='text-align:center;vertical-align:middle;' title='指定结束'>";
			htmlContent += "<a href=\"javascript:"+this_.id+".openWin4ViewZDClose('"+ row.id + "')\" >指定结束</a>";
			htmlContent += "</td>";
		}else if(row.zt==10){
			htmlContent += "<td style='text-align:center;vertical-align:middle;' title='已关闭'>";
			htmlContent += "<a href=\"javascript:"+this_.id+".openWin4ViewWGClose('"+ row.id + "')\" >已关闭</a>";
			htmlContent += "</td>";
		}else{
			htmlContent += "<td style='text-align:center;vertical-align:middle;' title='未关闭'>未关闭</td>";
		}
		/*反馈状态*/
		if(row.wgbs==0){
			htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+"' >"+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+ "</td>";
		}else{
			htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+"' >";
			htmlContent += "<a href=\"javascript:"+this_.id+".openWin4ViewFeedback('"+ row.id + "')\" > "+ StringUtil.escapeStr(DicAndEnumUtil.getEnumName('feedbackStatusEnum', row.wgbs))+ "</a>";
			htmlContent += "</td>";
		}
		
		/*工卡附件*/
		if((paramsMap.cardAttachCount != null && paramsMap.cardAttachCount > 0) 
			|| (paramsMap.cardFjid != null && paramsMap.cardFjid != "")
			|| (paramsMap.cardGznrfjid != null && paramsMap.cardGznrfjid != "")){
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			htmlContent += '<i qtid="'+row.gkid+'" gkfjid="'+paramsMap.cardFjid+'" gznrfjid="'+paramsMap.cardGznrfjid+'" class="attachment-n-gk-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			htmlContent += "</td>";
		}else{
			htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
		}
		
		/*来源分类*/
		var lyfl = this_.jksjType[row.gdlx];
		lyfl = DicAndEnumUtil.getEnumName('workorderTypeEnum', row.gdlx);
		if(row.gdlx == 1){
			lyfl = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.gdzlx); 
		}
		htmlContent += "<td title='"+lyfl+"' style='text-align:left;vertical-align:middle;'>"+lyfl+"</td>";
		
		
		/*工单编号*/
		htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:center;vertical-align:middle;'>";
		htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkOrder('"+StringUtil.escapeStr(row.id)+"')>"+StringUtil.escapeStr(row.gdbh)+"</a>";
		htmlContent +="</td>";
		
		/*ATA章节号*/
		htmlContent += "<td title='"+zjh+"' style='text-align:center;vertical-align:middle;'>"+zjh+"</td>";
		
		/*执行对象*/
		htmlContent += "<td title='"+zxdxTitle+"' style='text-align:center;vertical-align:middle;' >"+zxdx+"</td>";
		/*完成时限*/
		htmlContent += this_.formatLastData(paramsMap.jhsjsj);
		/*专业*/
		htmlContent += "<td title='"+StringUtil.escapeStr(row.zy)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.zy)+"</td>";
		
		/*工作类别*/
		htmlContent += "<td title='"+StringUtil.escapeStr(row.gzlb)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzlb)+"</td>";
		
		/*区域*/
		htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.qy)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.qy)+"</td>";
		
		/*工作地点*/
		htmlContent += "<td title='"+StringUtil.escapeStr(row.jhZd)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhZd)+"</td>";
		/*工卡编号*/
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
		htmlContent += "<p title='"+StringUtil.escapeStr(row.gkbh)+"' class='tag-set margin-bottom-0'>";
		htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewWorkCard('"+StringUtil.escapeStr(row.gkid)+"')>"+StringUtil.escapeStr(row.gkbh)+"</a>";
		htmlContent += "</p>";
		htmlContent +="</td>";
		
		/*计划/实际工时*/
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
		htmlContent += "<p class='margin-bottom-0'>"+StringUtil.escapeStr(paramsMap.jhgs)+"/"+StringUtil.escapeStr(paramsMap.sjgs)+"</p>";
		htmlContent +="</td>";
		/*预计执行单位*/
		htmlContent += "<td title='"+yjzxdw+"' style='text-align:center;vertical-align:middle;'>";
		htmlContent += yjzxdw;
		htmlContent +="</td>";
		/*工单附件*/
		if((row.paramsMap.woAttachCount != null && row.paramsMap.woAttachCount > 0) || (row.gznrfjid != null && row.gznrfjid != "")){
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			htmlContent += '<i qtid="'+row.id+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			htmlContent += "</td>";
		}else{
			htmlContent += "<td style='text-align:center;vertical-align:middle;' ></td>";
		}
		/*维护人*/
		var zdr = StringUtil.escapeStr(paramsMap.whrbh) + " " + StringUtil.escapeStr(paramsMap.whrxm);
		htmlContent += "<td title='"+zdr+"' style='text-align:center;vertical-align:middle;'>";
		htmlContent += "<p title='"+zdr+"' class='tag-set margin-bottom-0'>"+zdr+"</p>";
		htmlContent += "</td>";
		/*维护时间*/
		htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
		htmlContent += "<p class='margin-bottom-0'>"+StringUtil.escapeStr(row.whsj)+"</p>";
		htmlContent +="</td>";
		/*机构代码*/
		htmlContent += "<td title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' style='text-align:center;vertical-align:middle;'>";
		htmlContent += StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode));
		htmlContent +="</td>";
		
		return htmlContent;
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#maintenanceIssuedPlan_list", $("#"+this_.gddiv)).empty();
		$("#"+this_.paginationId, $("#"+this_.gddiv)).empty();
		$("#maintenanceIssuedPlan_list", $("#"+this_.gddiv)).append("<tr><td colspan=\"23\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	initGkWebuiPopover : function(){//初始化工卡
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-n-gk-view',"body",function(obj){
			return this_.getGkHistoryAttachmentList(obj);
		});
		$("#maintenanceIssuedPlane_workorder_div").scroll(function(){
			$('.attachment-n-gk-view').webuiPopover('hide');
		});
	},
	getGkHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
   	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
   	        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
   	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
   	    ];
   		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	initWebuiPopover : function(){//初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view',"body",function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#maintenanceIssuedPlane_workorder_div").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList : function(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	/**
	 * 工单完工反馈
	 */
	openWinFeedback : function(id){
		var this_ = this;
		this_.selectById(id,function(obj){
			if(obj.zt != "7"){
				AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
				return;
			}
			Workorder135FeedbackWin.show({
				viewObj:obj,
				isShowReplacementRecord:true,
				colOptionhead : true,
				callback : function(data) {//回调函数
					if (data != null) {
						var message = '提交成功!';
						var url = basePath+"/produce/workorder/doFeedback";
						this_.performAction(url, data, message, "workorder135_feedback_Modal", function(obj){
							if(obj){
								this_.goPage(1,"auto","desc");
								refreshPermission();
							}
						});
					}
				}
			});	
		});
	},
	/**后台数据请求*/
	performAction : function(url, param, message, winId, o){
		var this_ = this;
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:url,
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(param),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage(message);
				$("#"+winId).modal("hide");
				if(typeof(o)=="function"){
					o(true);
				}
			}
		});
	},
	/**根据ID查找数据*/
	selectById : function(id,obj){//通过id获取数据
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/produce/workorder/selectWOById",
			type:"post",
			data:{gdid:id},
			dataType:"json",
			success:function(data){
				if(data != null){
					if(typeof(obj)=="function"){
						obj(data); 
					}
				}
			}
		});
	},
	/**
	 * 下载附件
	 */
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	/**
	 * 查看工单完工反馈
	 */
	openWin4ViewFeedback : function(id){
		var this_ = this;
		this_.selectById(id,function(obj){
			Workorder135FeedbackWin.show({
				viewObj:obj,
				isShowReplacementRecord:true,
				colOptionhead:false,
			});	
		});
	},
	/**
	 * 查看工单关闭(正常完成)弹框
	 */
	openWin4ViewWGClose : function(id){
		var this_ = this;
		this_.selectById(id,function(obj){
			Workorder135WGCloseWin.show({
				viewObj:obj,
				isShowReplacementRecord:true,
				colOptionhead:false,
			});	
		});
	},
	/**
	 * 查看工单关闭(指定)弹框
	 */
	openWin4ViewZDClose : function(id){
		var this_ = this;
		this_.selectById(id,function(obj){
			Workorder135ZDCloseWin.show({
				viewObj:obj,
				isShowReplacementRecord:true,
				colOptionhead : false,				
			});	
		});
	},
	/**
	 * 查看任务
	 */
	viewTask : function(id, jksjid, type){
		if(type == 1){
			window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
		}
		if(type == 2){
			window.open(basePath + "/project2/eo/view?id=" + id);
		}
		if(type == 3 || type == 4 || (type == 5 && !jksjid)){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
		}
		if(type == 5 && jksjid){
			window.open(basePath+"/project2/production/view?id="+id);
		}
	},
	/**
	 * 查看工单
	 */
	viewWorkOrder : function(id){
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
	},
	/**
	 * 查看工卡
	 */
	viewWorkCard : function(id){
		window.open(basePath+"/project2/workcard/view/" + id );
	},
	/**
	 * 格式化上次数据
	 */
	formatLastData : function(data){
		var this_ = this;
		var str = "";
		if(data == null || data == ""){
			str += "<td></td>";
			return str;
		}
		var list = data.split(",");
		var scjh = '';
		MonitorUnitUtil.sortByStrList(list);
		$.each(list,function(index,row){
			var tdArr = row.split("#_#");
			scjh += this_.formatJkz(tdArr[0], tdArr[1]);
		});
		if(scjh != ''){
			scjh = scjh.substring(0, scjh.length - 1);
		}
		str += "<td title='"+scjh.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scjh+"</td>";
		return str;
	},
	/**
	 * 格式化监控值
	 */
	formatJkz : function(jklbh, value){
		if(value != null && value != ''){
			value = this.convertToHour(jklbh, value) + MonitorUnitUtil.getMonitorUnit(jklbh, "")+"/";
		}else{
			value = "";
		}
		return value;
	},
	/**
	 * 分钟转小时
	 */
	convertToHour : function(jklbh, value){
		if(MonitorUnitUtil.isTime(jklbh)){
			value = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON);
		}
		return value;
	},
	/*mouseover TR的背景色*/
	mouseOverStyle:function(obj){
		$(obj).addClass('tr-hover');
		new Sticky({tableId:'maintenanceplan_table'}); //初始化表头浮动
	},
	mouseOutStyle:function(obj){
		$(obj).removeClass('tr-hover');
		new Sticky({tableId:'maintenanceplan_table'}); //初始化表头浮动
	},
	showHiddenContent:function(this_){
		/*hc_list_view_modal.init({
	 		isExportShow:false,
	 	})*/
	 	var id = $(this_).attr("id");
		var gdid = $(this_).attr("gdid");
		var gdbh = $(this_).attr("gdbh");
		var fjzch = $(this_).attr("fjzch");
		var jksjid = $(this_).attr("jksjid");
		//加载航材工具
		material_tool_list.init({
			id : gdid,
			type : 3,
			fjzch : fjzch,
			dprtcode:userJgdm
		});
		//加载执行历史
	 	executionHistory.init({
			zt : 10,
			jksjid : jksjid,
			gdid : gdid,
			fjzch : fjzch,
			dprtcode:userJgdm
		});
		//加载完工反馈和拆换件记录
	 	taskhistoryFeedbackMain.AjaxGetDatasWithSearch(gdid, "plan_feedback_replace_tab");
		taskhistoryRecordMain.AjaxGetDatasWithSearch(gdid,gdbh, "plan_feedback_replace_tab");
	 	if($(".bottom_hidden_content").css("display")=='block'){
	 		$(".bottom_hidden_content").slideUp(100);
	 	}
	 	$(".bottom_hidden_content").slideDown(100);	
		var isBottomShown = false;
		if($(".displayContent").is(":visible")){
			isBottomShown = true;
		}
	 	TableUtil.showDisplayContent();
	 	if($("#hideIcon").length==0){
	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($(".fenye"));
		}
	 	$("#maintenanceIssuedPlane_workorder_div").css("display","none");
		$("#maintenanceIssuedPlane_workorder_div").css("display","block");
		$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
		 App.resizeHeight();
		 if(!isBottomShown){
				$("#maintenanceIssuedPlane_workorder_div").animate({scrollTop:this_.offsetTop-43},"slow");
//				TableUtil.makeTargetRowVisible($(this_), $("#maintenanceIssuedPlane_workorder_div"));
			}
	},
	/*显示和隐藏*/
	toggleMaintenancePlan:function(obj){
		var i = $(obj);
		if(i.hasClass("icon-caret-left")){
			i.removeClass("icon-caret-left").addClass("icon-caret-right");
			$("#left_div").hide();
			$("#right_div").removeClass("col-sm-new-75 col-xs-12").addClass("col-sm-12 col-xs-12");
		}else{
			i.removeClass("icon-caret-right").addClass("icon-caret-left");
			$("#left_div").show();
			$("#right_div").removeClass("col-sm-12 col-xs-12").addClass("col-sm-new-75 col-xs-12");
		}
		App.resizeHeight();
		
	},
	hideBottom:function(){
		
			$("#"+this.selectTR).removeClass("bg_tr");
			this.selectTR=null;
		
		$(".displayContent").css("display","none");	
		App.resizeHeight();
	},
	initfjzch : function(){//飞机注册号
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:userJgdm});
		var planeRegOption = "<option value='' selected='selected'>显示全部All</option>";
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'  msn='"+StringUtil.escapeStr(planeData.XLH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
			});
			planeRegOption += "<option value='-' >"+"N/A"+"</option>";
		}else{
			planeRegOption += "<option value='' >"+"暂无飞机"+"</option>";
		}
		$("#fjzch_search", $("#"+this.id)).html(planeRegOption); 
	},
	initJd : function(dprtcode, obj){//内部
		AjaxUtil.ajax({
			url: basePath+"/sys/department/getBmList",
			type: "post",
			dataType:"json",
			data:{
				  dprtcode:dprtcode
			},
			success:function(data){
				if(data == null){
					data = [];
				}
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}
		});	
	},
	initWwgys : function(dprtcode, obj){//外委
		AjaxUtil.ajax({
			url: basePath+"/aerialmaterial/aerialmaterialfirm/getFirmList",
			type: "post",
			dataType:"json",
			data:{
				  dprtcode:dprtcode
			},
			success:function(data){
				if(data == null){
					data = [];
				}
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}
		});	
	},
	/**
	 * 获取内部执行单位option
	 */
	getNbzxdwOption : function(){
		var nbzxdwhtml = "";
		$.each(this.nbZxdwList,function(i,department){
			nbzxdwhtml += "<option value='"+StringUtil.escapeStr(department.id)+"' >"+StringUtil.escapeStr(department.dprtname)+"</option>";
		});
		return nbzxdwhtml;
	},
	/**
	 * 获取内部执行单位option
	 */
	getWwzxdwOption : function(){
		var wbzxdwhtml = "";
		$.each(this.wwZxdwList,function(i,firm){
			wbzxdwhtml += "<option value='"+StringUtil.escapeStr(firm.id)+"' >"+StringUtil.escapeStr(firm.gysmc)+"</option>";
		});
		return wbzxdwhtml;
	},
	/**
	 * 选中单选框
	 */
	clickRadio : function(){
		this.clickZxdw();
		this.changeModel();
	},
	clickZxdw : function(){
		var zxdw = $("input:radio[name='zxdw_search_radio']:checked", $("#"+this.id)).val(); 
		var zxdwhtml = "<option value='' >显示全部 All</option>";
		if(zxdw == 1){
			zxdwhtml += this.getNbzxdwOption();
			zxdwhtml += this.getWwzxdwOption();
		}
		if(zxdw == 2){
			zxdwhtml += this.getNbzxdwOption();
		}
		if(zxdw == 3){
			zxdwhtml += this.getWwzxdwOption();
		}
		$("#zxdw_search", $("#"+this.id)).empty();
		$("#zxdw_search", $("#"+this.id)).html(zxdwhtml);
	},
	/**
	 * 输入工包时改变事件
	 */
	changeWP : function(obj){
		var this_ = this;
		var list = this_.gbList;
		var gbList = this_.gbList;
		var keyword = $(obj).val();
		if(keyword != ''){
			list = [];
			$.each(gbList,function(index,row){
				if((row.gbbh != null && row.gbbh.indexOf(keyword) != -1) || (row.gbmc != null && row.gbmc.indexOf(keyword) != -1)){
					list.push(row);
				}
			});
		}
		this_.appendWPHtml(list);
	},
	/**搜索条件重置*/
	searchreset : function(){

		//反馈状态
		$("#fkzt_search option:first", $("#"+this.gddiv)).prop("selected", 'selected');  
		
		//来源分类
		this.multiselect();
		
		//工作类别
		$("#gzlx_search option:first", $("#"+this.gddiv)).prop("selected", 'selected');  
		
		//关键搜索框
		$("#keyword_gd_search", $("#"+this.gddiv)).val("");
		//ATA章节号
		$("#zjh_search", $("#"+this.gddiv)).val("");
		
	},
	search:function(){
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}
		App.resizeHeight();
	},
	/**
	 * 航材工具需求清单(工包)
	 */
	openMaterialToolDetailGb : function(){
		var this_ = this;
		if($("#gb_list_tbody",$("#"+this_.id)).find("tr input:checked").length > 0){
			this_.setParam("gb_list_tbody", this_.id, "2");
			window.open(basePath+"/produce/maintenance/monitoring/material/tool/detail?type=2");
		}else{
			AlertUtil.showMessage("请选择数据!");
		}
	},
	/**
	 * 航材工具需求清单(工单)
	 */
	openMaterialToolDetail : function(){
		var this_ = this;
		if($("#maintenanceIssuedPlan_list",$("#"+this_.gddiv)).find("tr input:checked").length > 0){
			this_.setParam("maintenanceIssuedPlan_list", this_.gddiv, "3");
			window.open(basePath+"/produce/maintenance/monitoring/material/tool/detail?type=3");
		}else{
			AlertUtil.showMessage("请选择数据!");
		}
	},
	/**
	 * 设置参数
	 */
	setParam : function(tbodyId, id, key){
		var this_ = this;
		var idList = [];
		$("#"+tbodyId, $("#"+id)).find("tr input:checked").each(function(){
			var checkid = $(this).attr("checkid");
			idList.push(checkid);
		});
		var msn = $("#fjzch_search ", $("#"+this_.id)).find("option:selected").attr("msn");
		var fjzch = $.trim($("#fjzch_search", $("#"+this_.id)).val());
		var param = {};
		param.fjzch = fjzch;
		param.msn = msn;
		param.dprtcode = userJgdm;
		param.idList = idList;
		param.lyidList = idList;
		localStorage.setItem(key, JSON.stringify(param));
	},
	/**
	 * 根据工单识别id查询工单
	 */
	queryByGdsbid : function(gdsbid, obj){
		var gbzt = $.trim($("#gbzt_search", $("#"+this.gddiv)).val());
		var searchParam = {};
		var paramsMap = {};
		searchParam.gdsbid = gdsbid;
		if(gbzt != null && gbzt != ''){
			var gbztList = gbzt.split(",");
			paramsMap.gbztList = gbztList;
		}
		searchParam.paramsMap = paramsMap;
		var url = basePath+"/produce/workorder/queryByGdsbid";
		startWait();
		AjaxUtil.ajax({
			url : url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				obj(data); 
			}
	    }); 
	},
	showChildTd:function(e,id,gdsbid,list){
		e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	    var this_=this;
	    var obj=$(id).find("i");
	    if($(obj).hasClass("fa-angle-down")){
			$(obj).removeClass("fa-angle-down");
			$(obj).addClass("fa-angle-up");
			
			this_.queryByGdsbid(gdsbid, function(gdList){
			
				var htmlContent='';
				$.each(gdList,function(index,row){
					
					var paramsMap = row.paramsMap?row.paramsMap:{};
					var lyfl = this_.jksjType[row.gdlx];
					lyfl = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.gdzlx); 
					
					htmlContent += "<tr class='cursor-pointer "+list+"_tr tr-nontopbordered-1' jksjid='"+StringUtil.escapeStr(row.jksjid)+"' gdid='"+row.id+"' fjzch='"+row.fjzch+"' gdbh='"+StringUtil.escapeStr(row.gdbh)+"' id='"+row.id+"' onclick='"+this_.id+".showHiddenContent(this)' >";
					htmlContent += "<td  style='text-align:center;vertical-align:middle;'><input checkid='"+row.id+"' type='checkbox'/></td>";
					
					htmlContent += "<td style='text-align:left;vertical-align:middle;' >";
					
					if(row.zt == 7){
						htmlContent += "<i class='spacing iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:04' onclick="+this_.id+".openWinFeedback('"+ row.id + "') title='完工反馈 Feedback'></i>";
					}
					
					htmlContent += "</td>";  
					
//					htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(lyfl)+"</td>";
					htmlContent += this_.getGdTd(row);
					htmlContent += "</tr>";
						
				});
				$(htmlContent).insertAfter($("#"+list));
				this_.initWebuiPopover();
				this_.initGkWebuiPopover();
				refreshPermission();
			});
		}else{
			$(obj).removeClass("fa-angle-up");
			$(obj).addClass("fa-angle-down");
			$("."+list+"_tr").remove();
		}
		
	},
	showChildTr:function(e,obj,gdsbid,list){
		 e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
		var this_=this;
		if($(obj).hasClass("fa-angle-down")){
			$(obj).removeClass("fa-angle-down");
			$(obj).addClass("fa-angle-up");
			
			this_.queryByGdsbid(gdsbid, function(gdList){
			
				var htmlContent='';
				$.each(gdList,function(index,row){
					
					var paramsMap = row.paramsMap?row.paramsMap:{};
					var lyfl = this_.jksjType[row.gdlx];
					lyfl = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',paramsMap.gdzlx); 
					
					htmlContent += "<tr class='cursor-pointer "+list+"_tr tr-nontopbordered-1' jksjid='"+StringUtil.escapeStr(row.jksjid)+"' gdid='"+row.id+"' fjzch='"+row.fjzch+"' gdbh='"+StringUtil.escapeStr(row.gdbh)+"' id='"+row.id+"' onclick='"+this_.id+".showHiddenContent(this)' >";
					htmlContent += "<td  style='text-align:center;vertical-align:middle;'><input checkid='"+row.id+"' type='checkbox'/></td>";
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;' >";
					
					if(row.zt == 7){
						htmlContent += "<i class='spacing iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workorder:main:04' onclick="+this_.id+".openWinFeedback('"+ row.id + "') title='完工反馈 Feedback'></i>";
					}
					
					htmlContent += "</td>";  
					
					htmlContent += "<td style='text-align:center;vertical-align:middle;text-decoration:underline;'>"+formatUndefine(lyfl)+"</td>";
					htmlContent += this_.getGdTd(row);
					this_.initWebuiPopover();
					this_.initGkWebuiPopover();
					htmlContent += "</tr>";
						
				});
				$(htmlContent).insertAfter($("#"+list));
				refreshPermission();
			});
		}else{
			$(obj).removeClass("fa-angle-up");
			$(obj).addClass("fa-angle-down");
			$("."+list+"_tr").remove();
		}
		
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		if(this.setNoData == null || this.setNoData == ''){
			AlertUtil.showMessage("请选择工包!");
			return;
		}
		var param = this.getGdParams();
		param.paramsMap.notGdzl = "";
		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		window.open(basePath+"/produce/workorder/workorder135.xls?paramjson="+JSON.stringify(param));
	}
}

function customResizeHeight(bodyHeight, tableHeight){
	
	var panelHeadingHeight = $('.panel-heading').outerHeight();
	if(panelHeadingHeight<35){
		bodyHeight = bodyHeight + panelHeadingHeight - 35;
		tableHeight = tableHeight + panelHeadingHeight - 35;
    }
	var gb_body_divSearch=$("#gb_body_div .searchContent").outerHeight()||0;
	$("#gb_table_top_div").css({"height":(bodyHeight-8 -gb_body_divSearch)+"px","overflow":"auto"});
	$("#gd_panel_div_body").height(bodyHeight);
    $("#gd_panel_div_body").css({"overflow-y":"auto"});
    //多列表高度设定
    if($(".displayContent").is(":hidden")){
    	$(".table-height").attr('style', 'height:' + tableHeight + 'px !important;overflow-x: auto;');
    }else{
    	$table = $(".table-height");
    	if($table.length > 0){
    		var cHeight = $table.attr("c-height");
    		var tempTableHight = tableHeight*0.45;
    		if(cHeight){
    			if(cHeight.indexOf("%") > 0){
    				cHeight = cHeight.replace("%","");
    				cHeight = cHeight/100;
    				tempTableHight = tableHeight * cHeight;
    			}else{
    				tempTableHight = cHeight;
    			}
    		}
    	 $table.attr('style', 'height:' + tempTableHight + 'px !important;overflow: auto;');
		 var maintenancePlaneWorkOrder=$("#maintenanceIssuedPlane_workorder_div").outerHeight()||0;
		 var rowHeight=$("#gd_panel_div .row-height").outerHeight()||0;
		 var tabHeader=$("#gd_panel_div ul#myChildTab").eq(0).outerHeight()||0;
		 var paginationHeight = $('#gd_panel_div .page-height:visible').outerHeight()||0;
		 var bottom_hidden_tab = bodyHeight - maintenancePlaneWorkOrder-rowHeight-paginationHeight -5;
		
		 $("#bottom_hidden_content").css({"height":bottom_hidden_tab+"px","overflow":"auto"});
		 
		 $("#feedbackTool #attach_div .panel-body").removeAttr("style");
		 $("#feedbackTool #attach_div .panel-body").addClass("padding-top-0 padding-bottom-0");
		
		
		
		 var tabBodyHeight=bodyHeight-paginationHeight-tabHeader-maintenancePlaneWorkOrder-rowHeight-10;
		 if(navigator.userAgent.indexOf("Chrome") > -1){
			 tabBodyHeight -= 10;
	     }
		 $("#gd_panel_div #plan_feedback_replace_tab .tab-pane").css({"height":tabBodyHeight+"px","overflow":"auto"});
		 $("#gd_panel_div #plan_feedback_replace_tab .tab-pane .panel-body").removeAttr("style");
		 $("#gd_panel_div #plan_feedback_replace_tab .tab-pane:not(#feedbackTool)").find("table").parent("div").css({"height":(tabBodyHeight-20)+"px","overflow":"auto"})
    	}
    	}
   
}

function workorder2feddback(){
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_workorderedit');
	attachmentsObj.show({
		djid:"",
		fileHead : true,
		colOptionhead : true,
		fileType:"bulletin"
	});//显示附件
	AlertUtil.hideModalFooterMessage();
	$("#workorder135_feedback_Modal").modal("show");
}

function workpackage2feddback(){
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:"",
		fileHead : true,
		colOptionhead : true,
		fileType:"bulletin"
	});//显示附件
	AlertUtil.hideModalFooterMessage();
	$("#workpackage_feedback_Modal").modal("show");
}

//关闭工包弹窗
function closeWorkpackage(){
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:"",
		fileHead : true,
		colOptionhead : true,
		fileType:"bulletin"
	});//显示附件
	AlertUtil.hideModalFooterMessage();
	$("#package_close_modal").modal("show");
}
//关闭工单弹窗
function closeWorkorder(){
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:"",
		fileHead : true,
		colOptionhead : true,
		fileType:"bulletin"
	});//显示附件
	AlertUtil.hideModalFooterMessage();
	$("#package_close_modal").modal("show");
}