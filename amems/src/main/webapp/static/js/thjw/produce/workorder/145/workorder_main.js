/** 工单145,主列表信息 */
var workorder145_main = {
	id : "workorder145_mainpage",
	lastSelect : null,
	lastColor : null,
	dprtcode : userJgdm,
	/** 初始化页面信息 */
	initInfo : function() {
		this.loadSearchFilterDatas();
		this.loadTableInfo();
		App.resizeHeight();
		$(window).resize(function() {
			App.resizeHeight();
		});
	},
	/** 根据选中的组织机构，查询加载检索条件 */
	loadSearchFilterDatas : function() {
		var this_ = this;
		this_.dprtcode = $("#dprtcode_search", $("#" + this_.id)).val(); // 当前选中的查询条件
		// 工作类别
		$("#wo145gzlx_search").empty();
		$("#wo145gzlx_search").append('<option value="" selected="selected">显示全部All</option>');
		DicAndEnumUtil.registerDic('17', 'wo145gzlx_search', this_.dprtcode);
		// 生成多选来源分类
		this_.initMultiselect();

	},
	// 初始化来源分类多选下拉框
	initMultiselect : function() {
		// 生成多选下拉框(机型)
		var list = this.initLyflList();
		$("#gdlxDiv").empty();
		$("#gdlxDiv").html("<select multiple='multiple'  id='lyfl_search'></select>");
		$("#lyfl_search").append(list);
		$("#lyfl_search").multiselect({
			buttonClass : 'btn btn-default ',
			buttonWidth : '100%',
			numberDisplayed : 100,
			buttonContainer : '<div class="btn-group" style="width:100%;" />',
			includeSelectAllOption : true,
			nonSelectedText : '显示全部 All',
			allSelectedText : '显示全部 All',
			selectAllText : '选择全部 Select All',
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
		if(paramGdzt != null && paramGdzt != ''){
		    var arr = [];
		    arr.push(paramGdzt);
	    	$('#gdzt_search').multiselect('select', arr);
	    	paramGdzt = null;
		 }else{
			 $('#gdzt_search').multiselect('select', ['1','2','7','10']);
		 }
	},
	initGdztList: function (){
		var list='';
		var typeList = DicAndEnumUtil.data.enumMap.workorderStatusEnum;
		if (typeList.length > 0) {
			for (var i = 0; i < typeList.length; i++) {
				list += "<option value='"
					+ StringUtil.escapeStr(typeList[i].id) + "'>"
					+ StringUtil.escapeStr(typeList[i].name) + "</option>";
			}
		} 
		return list;
	},
	initLyflList : function() {
		var list = '';
		var typeList = DicAndEnumUtil.data.enumMap.workorderTypeEnum;
		if (typeList.length > 0) {
			for (var i = 0; i < typeList.length; i++) {
				if(typeList[i].id != 3 && typeList[i].id != 9){
					list += "<option value='"+ StringUtil.escapeStr(typeList[i].id) + "'>"+ StringUtil.escapeStr(typeList[i].name) + "</option>";
				}
			}
		}
		return list;
	},
	loadFjList:function(){
		var param = {};
		param.dprtcode = $("#dprtcode_search").val();
		AjaxUtil.ajax({
			url: basePath+"/produce/workorder145/getFjList",
			contentType : "application/json;charset=utf-8",
			type : "post",
			data : JSON.stringify(param),
			dataType : "json",
			async : false,
			success : function(data) {
				finishWait();
				var fjList = '<option value="" selected="selected">显示全部All</option>';
				if (data != null && data.length > 0) {
					$.each(data, function(i, row) {
						fjList += "<option value='"+ StringUtil.escapeStr(row.fjzch) + "' >"+ StringUtil.escapeStr(row.fjzch)+ "</option>";
					});
				}
				$("#fjzch_search").empty();
				$("#fjzch_search").html(fjList);
			}
		});
	},
	/** 加载表格信息 */
	loadTableInfo : function() {
		this.goPage(1, "auto", "desc");
	},
	goPage : function(pageNumber, sortType, sequence) {
		this.hideBottom();
		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},
	AjaxGetDatasWithSearch : function(pageNumber, sortType, sequence) {
		var this_ = this;
		var searchParam = {};
		this_.pagination = {
			page : pageNumber,
			sort : sortType,
			order : sequence,
			rows : 20
		};
		searchParam.pagination = this_.pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		$.extend(searchParam, this_.getParams());

		startWait();
		AjaxUtil
				.ajax({
					url : basePath + "/produce/workorder145/queryAllPageList",
					type : "post",
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					data : JSON.stringify(searchParam),
					success : function(data) {
						finishWait();
						if (data.rows.length > 0) {
							this_.appendContentHtml(data.rows);
							// 分页
							var page_ = new Pagination({
								renderTo : "workorder145_pagination",
								data : data,
								sortColumn : sortType,
								orderType : sequence,
								goPage : function(a, b, c) {
									this_.goPage(a, b, c);
								}
							});
							// 标记关键字
							signByKeyword($.trim($("#wo145search_keyword").val()), [ 3,
									9, 15,17 ], "#workorder145_list tr td");
							signByKeyword($.trim($("#wo145qy_search").val()), [13 ], "#workorder145_list tr td");
							signByKeyword($.trim($("#gbbh_search").val()), [17 ], "#workorder145_list tr td");
						} else {
							$("#workorder145_list").empty();
							$("#workorder145_pagination").empty();
							$("#workorder145_list").append("<tr><td class='text-center' colspan=\"23\">暂无数据 No data.</td></tr>");
						}
						// 翻动列表,表头浮动
						new Sticky({
							tableId : 'workorder145_table'
						});
					}
				});
	},
	/** 将搜索条件封装 */
	getParams : function() {
		var this_ = this;
		var searchParam = {};

		searchParam.dprtcode = $.trim($("#dprtcode_search", $("#" + this_.id))
				.val()); // 组织编码
		searchParam.fjzch = $("#fjzch_search").val(); // 飞机注册号
		//searchParam.zt = $("#gdzt_search").val(); // 工单状态
		searchParam.wgbs = $("#feedback_search").val();//反馈状态
		searchParam.gzlb = $("#wo145gzlx_search").val(); // 工作类别

		var paramsMap = {};
		paramsMap.mainkeyword = $.trim($("#wo145search_keyword").val());// 关键字
		paramsMap.gbbh = $.trim($("#gbbh_search").val());// 工包编号
		paramsMap.zjh = $("#zjhid_search").val();// 章节号
		paramsMap.qy = $("#wo145qy_search").val();// 区域
		// 来源分类（工单类型）
		var lyflList = [];
		var lyflStr = $.trim($("#lyfl_search").val());
		if (lyflStr != null && lyflStr.length > 0) {
			var lyflArr = lyflStr.split(",");
			for (var i = 0; i < lyflArr.length; i++) {
				if ('multiselect-all' != lyflArr[i]) {
					lyflList.push(parseInt(lyflArr[i]));
				}
			}
		}
		if (null != lyflList && lyflList.length > 0) {
			paramsMap.gdlx = lyflList;
		} else {
			paramsMap.gdlx = [1,2,4,5];
		}
		/*var gdzt = [];
		var gbzt = $("#gb_search").val();
		if(gbzt == "1"){
			gdzt = [ 9, 10 ];// 指定结束9完工关闭10
		}else if(gbzt == "0"){
			gdzt = [ 1, 2, 7 ];//除指定结束和完工关闭
		}else{
			gdzt = [ 1, 2, 7 ,9,10];//所有状态
		}
		paramsMap.gdzt = gdzt;*/
		
		//工单状态
		var gdztList = [];
		var gdztStr = $.trim($("#gdzt_search").val()); 
		if(gdztStr != null && gdztStr.length > 0){
			var gdztArr = gdztStr.split(",");
			for(var i = 0 ; i < gdztArr.length ; i++){
				if('multiselect-all' != gdztArr[i]){
					gdztList.push(parseInt(gdztArr[i]));
				}
			}
		}
		
		if(paramGdzt != null && paramGdzt != ''){
			gdztList = [];
			gdztList.push(paramGdzt);
		 }
		
		if(null != gdztList && gdztList.length > 0){
			paramsMap.gdzt = gdztList;
		}else{
			paramsMap.gdzt = [1,2,7,9,10];
		}
		searchParam.paramsMap = paramsMap;

		return searchParam;
	},
	/** 表格拼接 */
	appendContentHtml : function(list) {
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index, row) {
			if (index % 2 == 0) {
				htmlContent += "<tr class='cursor-pointer' onclick=workorder145_main.loadChildInfo(this,'"+ row.id + "') bgcolor=\"#f9f9f9\">";
			} else {
				htmlContent += "<tr class='cursor-pointer' onclick=workorder145_main.loadChildInfo(this,'"+ row.id + "') bgcolor=\"#fefefe\">";
			}
			/* 全选 */
			/*
			 * htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'>" + "
			 * <input
			 * onclick=SelectUtil.clickRow("+index+",'workorder145_list','workorder145_row')
			 * type='checkbox' name='workorder145_row'
			 * index="+index+" />" + "</td>";
			 */
			/* 操作 */
			htmlContent += "<td class='text-center fixed-column'>";
			htmlContent += "<i class='icon-print color-blue cursor-pointer checkPermission' permissioncode='produce:workorder145:main:08' onClick=workorder145_main.print('"+ row.id +"','"+row.gdlx+ "',event) title='打印 Print'></i>&nbsp;&nbsp;";
			/*
			 * if((row.zt != "9" && row.zt != "10")||(row.zt ==
			 * "1")||(row.zt == "7")||(row.zt == "2" || row.zt ==
			 * "7")||(row.zt == "7" && row.xmblbs==0)||(row.zt ==
			 * "7")){ htmlContent += " <button type='button'
			 * zt='"+row.zt+"' xmblbs='"+row.xmblbs+"'
			 * id='"+row.id+"' class='btn btn-xs btn-primary
			 * attachment-workorder145-view'
			 * data-toggle='dropdown'>操作"; htmlContent += "
			 * <span class='caret'></span>"; htmlContent += "
			 * </button>"; }
			 */
			if (row.zt != "9" && row.zt != "10") { // 9、10以外状态,可编辑
				htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='produce:workorder145:main:02' onClick=workorder145_main.openWinEdit('"+ row.id+ "',true,event) title='修改 Edit'></i>&nbsp;&nbsp;";
			}
			if (row.zt == "1") { // 状态为保存,可删除
				htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='produce:workorder145:main:03' onClick=workorder145_main.openWinDel('"+ row.id+ "',true,event) title='删除 Delete'></i>&nbsp;&nbsp;";
			}
			if (row.zt == "7") { // 状态为生效下发,可反馈
				htmlContent += "<i class='iconnew-feedback color-blue cursor-pointer checkPermission' permissioncode='produce:workorder145:main:04' onClick=workorder145_main.openWinFeedback('"+ row.id+ "',true,event)  title='完工反馈'></i>&nbsp;&nbsp;";
			}
			if (row.zt == "7" && row.xmblbs == 0) { // 状态为生效下发且项目保留标识=0,可指正常完成
				htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='produce:workorder145:main:05' onClick=workorder145_main.openWinWGClose('"+ row.id+ "',true,event)  title='完工关闭'></i>&nbsp;&nbsp;";
			}
			if (row.zt == "2" || row.zt == "7") { // 状态为提交、生效下发,可指定结束
				htmlContent += "<i class='iconnew-end color-blue cursor-pointer checkPermission' permissioncode='produce:workorder145:main:06' onClick=workorder145_main.openWinZDClose('"+ row.id+ "',true,event)  title='指定结束'></i>&nbsp;&nbsp;";
			}
			if (row.zt == "7" && (row.fjzch !="" && row.fjzch != null && row.fjzch !="-" )&& row.xmblbs==0) { // 状态为生效,可进行,工作项目保留
				htmlContent += "<i class='iconnew-projectKeep color-blue cursor-pointer checkPermission'  permissioncode='produce:reservation:item:main:01'  onClick=workorder145_main.openWinView('"+ row.id+ "',true,8)  title='工作项目保留'></i>&nbsp;&nbsp;";
			}
			if (row.zt == "9" || row.zt == "10") { // 9、10状态,可编辑
				htmlContent += "<i class='fa  fa-thumb-tack color-blue cursor-pointer checkPermission' permissioncode='produce:workorder145:main:07' onClick=workorder145_main.openWinXDClose('"+ row.id+ "',true,event)  title='修订 Revised'></i>&nbsp;&nbsp;";
			}
			htmlContent += "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.lyrwh)+ "'>"+ "<a href=javascript:workorder145_main.openWinView('"+ row.lyrwid+"','"+row.jksjid+"','"+row.gdlx+ "'); class='text-left ' style='margin-right:5px;' >"+ StringUtil.escapeStr(row.lyrwh) + "</a>"+ "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.gdbt) + "'>"+ StringUtil.escapeStr(row.gdbt) + "</td>";
			/* 工单状态 */
			if (row.zt == 9) {
				htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt))+ "'><a href='#' onClick=workorder145_main.openWin4ViewZDClose('"+ row.id+ "',event) > "+ formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt)) + "</a></td>";
			} else if (row.zt == 10) {
				htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt))+ "'><a href='#' onClick=workorder145_main.openWin4ViewWGClose('"+ row.id+ "',event) > "+ formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt)) + "</a></td>";
			} else {
				htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+ formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt))+ "'>"+ formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt)) + "</td>";
			}
			htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
			if ((row.paramsMap.cardAttachCount != null && row.paramsMap.cardAttachCount > 0)|| (row.paramsMap.cardGznrfjid != null && row.paramsMap.cardGznrfjid != undefined)||(row.paramsMap.cardFjid != null && row.paramsMap.cardFjid != undefined)) {
				htmlContent += '<i qtid="'+ row.gkid+ '" gkfjid="'+row.paramsMap.cardFjid+'"  gznrfjid="'+row.paramsMap.cardGznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			htmlContent += "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhKsrq).substring(0, 10)+ "'>"+ StringUtil.escapeStr(row.jhKsrq).substring(0, 10) + "</td>";
			htmlContent += "<td class='text-center' title='"+ StringUtil.escapeStr(row.jhJsrq).substring(0, 10) + "'>"+ StringUtil.escapeStr(row.jhJsrq).substring(0, 10) + "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.fixChapter ? (formatUndefine(row.fixChapter.zjh)+ " " + formatUndefine(row.fixChapter.ywms)): "")+ "'>"+ StringUtil.escapeStr(row.fixChapter ? (formatUndefine(row.fixChapter.zjh)+ " " + formatUndefine(row.fixChapter.ywms)): "") + "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.gdbh)+ "'>"+ "<a href=javascript:workorder145_main.openWinView('"+ row.id+"','"+row.jksjid+"',3); class='text-left ' style='margin-right:5px;' >"+ StringUtil.escapeStr(row.gdbh) + "</a>"+ "</td>";
			htmlContent += "<td title='"+ formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum',StringUtil.escapeStr(row.gdlx)))+ "'>"+ formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum',StringUtil.escapeStr(row.gdlx)))+ "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.zdrq).substring(0, 10)+ "'>"+ StringUtil.escapeStr(row.zdrq).substring(0, 10) + "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.gzlb) + "'>"+ StringUtil.escapeStr(row.gzlb) + "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.paramsMap.qy) + "'>"+ StringUtil.escapeStr(row.paramsMap.qy) + "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.bgr) + "'>"+ StringUtil.escapeStr(row.bgr) + "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.gkbh)+ "'>"+ "<a href=javascript:workorder145_main.openWinView('"+ row.gkid+"','"+row.jksjid+"',7); class='text-left ' style='margin-right:5px;' >"+ StringUtil.escapeStr(row.gkbh) + "</a>"+ "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.fjzch) + " "+ StringUtil.escapeStr(row.fjxlh) + "'>"+ StringUtil.escapeStr(row.fjzch) + " "+ StringUtil.escapeStr(row.fjxlh) + "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.paramsMap.gbbh)+ "'>"+ "<a href=javascript:workorder145_main.openWinView('"+ row.gbid+"','"+row.jksjid+"',6); class='text-left ' style='margin-right:5px;' >"+ StringUtil.escapeStr(row.paramsMap.gbbh)+ "</a>" + "</td>";

			htmlContent += "<td class='text-right' title='"+ StringUtil.escapeStr(row.paramsMap.jhgs)+ "'>"+ StringUtil.escapeStr(row.paramsMap.jhgs)+ "</td>";
			htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
			if ((row.paramsMap.woAttachCount != null && row.paramsMap.woAttachCount > 0)|| (row.gznrfjid != null && row.gznrfjid != "")) {
				htmlContent += '<i qtid="'+ row.id + '" gznrfjid="' + row.gznrfjid + '" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			htmlContent += "</td>";
			htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.paramsMap.printCount)+"'>"+StringUtil.escapeStr(row.paramsMap.printCount)+"</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.paramsMap.whrbh) + " "+ StringUtil.escapeStr(row.paramsMap.whrxm)+ "'>"+ StringUtil.escapeStr(row.paramsMap.whrbh)+ " "+ StringUtil.escapeStr(row.paramsMap.whrxm)+ "</td>";
			htmlContent += "<td title='"+ StringUtil.escapeStr(row.whsj == null ? "" : row.whsj)+ "'>"+ StringUtil.escapeStr(row.whsj == null ? "": row.whsj) + "</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"' >"+ StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+ "</td>";
			htmlContent += "</tr>";
			
		});
		$("#workorder145_list").html(htmlContent);
		// this_.initWebuiPopoverWorkOrder();
		this_.initWebuiPopover(); // 显示附件
		 refreshPermission(); //刷新权限
	},
	// 操作弹出框
	initWebuiPopoverWorkOrder : function() {// 初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-workorder145-view',
				"body", function(obj) {
					return this_.getWorkOrderButtonList(obj);
				}, 120);
		/*
		 * $("#gb_body_div").scroll(function(){
		 * $('.attachment-gb-view').webuiPopover('hide'); });
		 */
	},
	getWorkOrderButtonList : function(obj) {// 获取操作信息
		var this_ = this;
		var zt = $(obj).attr('zt');
		var xmblbs = $(obj).attr('xmblbs');
		var id = $(obj).attr('id');
		var str = "<div class='button-group-new'>";
		if (zt != "9" && zt != "10") { // 9、10以外状态,可编辑
			str += "<p class='margin-bottom-0 ' title='修改 Edit'>";
			str += "<a href='javascript:void(0);' onClick=workorder145_main.openWinEdit('"
					+ id
					+ "',true,event)><i class='icon-edit color-blue cursor-pointer' ></i>&nbsp;修改</a>";
			str += "</p>";
		}
		if (zt == "1") { // 状态为保存,可删除
			str += "<p class='margin-bottom-0' title='删除 Delete'>";
			str += "<a href='javascript:void(0);' onClick=workorder145_main.openWinDel('"
					+ id
					+ "',true,event)><i class='icon-trash color-blue cursor-pointer'></i>&nbsp;删除</a>";
			str += "</p>";
		}
		if (zt == "7") { // 状态为生效下发,可反馈
			str += "<p class='margin-bottom-0' title='完工反馈'>";
			str += "<a href='javascript:void(0);' onClick=workorder145_main.openWinFeedback('"
					+ id
					+ "',true,event)><i class='iconnew-feedback color-blue cursor-pointer'  ></i>&nbsp;完工反馈</a>";
			str += "</p>";
		}
		if (zt == "2" || zt == "7") { // 状态为提交、生效下发,可指定结束
			str += "<p class='margin-bottom-0' title='指定结束'>";
			str += "<a href='javascript:void(0);' onClick=workorder145_main.openWinZDClose('"
					+ id
					+ "',true,event)><i class='iconnew-end color-blue cursor-pointer'  ></i>&nbsp;指定结束</a>";
			str += "</p>";
		}
		if (zt == "7" && xmblbs == 0) { // 状态为生效下发且项目保留标识=0,可指正常完成
			str += "<p class='margin-bottom-0' title='正常完成'>";
			str += "<a href='javascript:void(0);' onClick=workorder145_main.openWinWGClose('"+ id + "',true,event)><i class='iconnew-normalComp color-blue cursor-pointer'  ></i>&nbsp;正常完成</a>";
			str += "</p>";
		}
		if (zt == "7") { // 状态为生效,可进行,工作项目保留
			str += "<p class='margin-bottom-0' title='工作项目保留'>";
			str += "<a href='javascript:void(0);' onClick=workorder145_main.openWinView('"+ id + "',,9)><i class='iconnew-projectKeep color-blue cursor-pointer'></i>&nbsp;工作项目保留</a>";
			str += "</p>";
		}
		str += "</div>";
		return str;
	},
	loadChildInfo : function(thisRow, gdid) {
		var this_ = $(thisRow);

		if (this.lastSelect != null) {
			this.lastSelect.removeClass("bg_tr");
		}

		var isBottomShown = false;
		if ($(".displayContent").is(":visible")) {
			isBottomShown = true;
		}
		$(".displayContent").css("display", "block");
		this_.addClass("bg_tr");

		new Sticky({
			tableId : 'workorder145_table'
		}); // 初始化表头浮动

		this.lastSelect = this_;
		if ($("#hideIcon").length == 0) {
			$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+ basePath+ '/static/images/down.png" onclick="workorder145_main.hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#workorder145_pagination  .fenye"));
		}
		App.resizeHeight();

		/* Start:处理逻辑 */
		Workorder145MainTab.loadTabInfo(gdid);
		/* End:处理逻辑 */

		if (!isBottomShown) {
			TableUtil.makeTargetRowVisible(this_, $("#workorder145_table_div")); // 放置到最后,点击最后一行时,定位表格行
		}
	},
	/** 搜索条件显示与隐藏 */
	search : function() {
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			App.resizeHeight();
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			App.resizeHeight();
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}
	},
	/** 搜索条件重置 */
	searchreset : function() {
		// 飞机注册号
		$("#fjzch_search option:first").prop("selected", 'selected');

		// 来源分类
		$("#lyfl_search option:first").prop("selected", 'selected');

		// 工单状态
		// $("#gdzt_search option:first").prop("selected", 'selected');

		// 工包编号
		$("#gbbh_search").val("");

		// 关键搜索框
		$("#wo145search_keyword").val("");

		// 反馈状态
		// $("#fkzt_search option:first").prop("selected", 'selected');

		// ATA章节号
		$("#zjhid_search").val("");
		$("#zjhName_search").val("");
		// checkbox
		$("#feedback_search").val("");
		$("#gb_search").val("");
		// 工作类别
		$("#gzlx_search option:first").prop("selected", 'selected');
		$("#wo145qy_search").val("");
		// 组织机构
		$("#dprtcode_search").val(userJgdm);

		this.changeDprtcode(); // 重新加载数据
	},
	/** 开始搜索 */
	doSearch : function() {
		TableUtil.resetTableSorting("workorder145_table");
		this.loadTableInfo();
	},
	changefkzt : function(){
		this.doSearch();
	},
	changegbzt:function(){
		this.doSearch();
	},
	/** 隐藏底部列表 */
	hideBottom : function() {
		if (this.lastSelect != null) {
			this.lastSelect.removeClass("bg_tr");
			this.lastSelect = null;
			this.lastColor = null;
		}
		$(".displayContent").css("display", "none");
		$("#hideIcon").remove();
		App.resizeHeight();
	},
	/** 打开完工反馈弹框 */
	openWinFeedback : function(id, isRefresh, o) {
		o = o || window.event;
		if (o.stopPropagation) { // W3C阻止冒泡方法
			o.stopPropagation();
		} else {
			o.cancelBubble = true; // IE阻止冒泡方法
		}
		var this_ = this;
		this_.selectById(id, function(obj) {
			if(obj.gdlx == "3"){
				if(obj.zt != "7" && obj.zt != "2"){
					AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
			}else{
				if(obj.zt != "7"){
					AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
			}
			Workorder145FeedbackWin.show({
				viewObj : obj,
				isShowReplacementRecord : true,
				colOptionhead : true,
				callback : function(data) {// 回调函数
					if (data != null) {
						var message = '反馈成功!';
						var url = basePath+ "/produce/workorder145/doFeedback";
						this_.performAction(url, data, message,"workorder145_feedback_Modal",isRefresh, o);
					}
				}
			});
		});
	},
	// 查看工单完工反馈
	openWin4ViewFeedback : function(id) {
		var this_ = this;
		this_.selectById(id, function(obj) {
			Workorder145FeedbackWin.show({
				viewObj : obj,
				isShowReplacementRecord : true,
				colOptionhead : false,
			});
		});
	},
	/** 打开工单关闭(指定)弹框 */
	openWinZDClose : function(id, isRefresh, o) {
		o = o || window.event;
		if (o.stopPropagation) { // W3C阻止冒泡方法
			o.stopPropagation();
		} else {
			o.cancelBubble = true; // IE阻止冒泡方法
		}
		var this_ = this;
		this_.selectById(id, function(obj) {
			if (!(obj.zt == "2" || obj.zt == "7")) {
				AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
				this_.refreshPage();
				return;
			}
			Workorder145ZDCloseWin.show({
				viewObj : obj,
				isShowReplacementRecord : true,
				colOptionhead : true,
				callback : function(data) {// 回调函数
					if (data != null) {
						var message = '指定结束成功!';
						var url = basePath + "/produce/workorder145/doZDClose";
						this_.performAction(url, data, message,
								"workorder145_zdclose_Modal", isRefresh, o);
					}
				}
			});
		});
	},
	/** 查看工单关闭(指定)弹框 */
	openWin4ViewZDClose : function(id, e) {
		if(e!=undefined){
			e = e || window.event;
			if (e.stopPropagation) { // W3C阻止冒泡方法
				e.stopPropagation();
			} else {
				e.cancelBubble = true; // IE阻止冒泡方法
			}
		}
		var this_ = this;
		this_.selectById(id, function(obj) {
			Workorder145ZDCloseWin.show({
				viewObj : obj,
				isShowReplacementRecord : true,
				colOptionhead : false,
			});
		});
	},
	/** 打开工单关闭(正常完成)弹框 */
	openWinWGClose : function(id, isRefresh, o) {
		o = o || window.event;
		if (o.stopPropagation) { // W3C阻止冒泡方法
			o.stopPropagation();
		} else {
			o.cancelBubble = true; // IE阻止冒泡方法
		}
		var this_ = this;
		this_.selectById(id, function(obj) {
			if(obj.gdlx == "3"){
				if(!((obj.zt == "2" ||obj.zt == "7") && obj.xmblbs == 0)){
					AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
			}else{
				if(!(obj.zt == "7" && obj.xmblbs == 0)){
					AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
			}
			Workorder145WGCloseWin.show({
				viewObj : obj,
				isShowReplacementRecord : true,
				colOptionhead : true,
				callback : function(data) {// 回调函数
					if (data != null) {
						var message = '关闭成功!';
						var url = basePath + "/produce/workorder145/doWGClose";
						this_.performAction(url, data, message,
								"workorder145_wgclose_Modal", isRefresh, o);
					}
				}
			});
		});
	},
	/** 查看工单关闭(正常完成)弹框 */
	openWin4ViewWGClose : function(id, e) {
		if (e != undefined) {
			e = e || window.event;
			if (e.stopPropagation) { // W3C阻止冒泡方法
				e.stopPropagation();
			} else {
				e.cancelBubble = true; // IE阻止冒泡方法
			}
		}
		var this_ = this;
		this_.selectById(id, function(obj) {
			Workorder145WGCloseWin.show({
				viewObj : obj,
				isShowReplacementRecord : true,
				colOptionhead : false,
			});
		});
	},
	/** 打开工单新增弹框 */
	openWinAdd : function(isRefresh) {
		var this_ = this;
		var obj = {};
		obj.id = 0;
		Workorder145AddWin.show({
			modalHeadCN : '新增例行工单',
			modalHeadENG : 'Add RTN',
			editParam : 1,// 新增
			blLx : blLx, // 保留类型 1:故障保留 2:缺陷保留
			blId : blId, // 保留类型ID
			viewObj : obj,
			callback : function(data) {// 回调函数
				if (data != null) {
					var message = '保存成功!';
					if (data.paramsMap != null
							&& data.paramsMap.operationType != null
							&& data.paramsMap.operationType != '') {
						if (data.paramsMap.operationType == 2) {
							message = '提交成功!';
						}
					}
					var url = basePath + "/produce/workorder145/save";
					this_.performAction(url, data, message,
							"workorder145_add_Modal", isRefresh);
				}
			}
		});
	},
	/** 打开修改弹框 */
	openWinEdit : function(id, isRefresh, e) {
		if (e != undefined) {
			if (e.stopPropagation) { // W3C阻止冒泡方法
				e.stopPropagation();
			} else {
				e.cancelBubble = true; // IE阻止冒泡方法
			}
		}
		var this_ = this;
		this_.selectById(id, function(obj) {
			if (obj.zt == "9" || obj.zt == "10") {
				AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
				this_.refreshPage();
				return;
			}
			Workorder145AddWin.show({
				modalHeadCN : '编辑例行工单',
				modalHeadENG : 'Edit RTN',
				editParam : 2, // 编辑
				viewObj : obj,
				callback : function(data) {// 回调函数
					if (data != null) {
						var message = '保存成功!';
						if (data.paramsMap != null&& data.paramsMap.operationType != null&& data.paramsMap.operationType != '') {
							if (data.paramsMap.operationType == 2) {
								message = '提交成功!';
							}
						}
						var url = basePath + "/produce/workorder145/update";
						this_.performAction(url, data, message,"workorder145_add_Modal", isRefresh);
					}
				}
			});
		});
	},
	/** 打开删除 */
	openWinDel : function(id, isRefresh, o) {
		o = o || window.event;
		if (o.stopPropagation) { // W3C阻止冒泡方法
			o.stopPropagation();
		} else {
			o.cancelBubble = true; // IE阻止冒泡方法
		}
		var this_ = this;
		this_.selectById(id, function(obj) {
			if (!(obj.zt == "1")) {
				AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
				this_.refreshPage();
				return;
			}
			AlertUtil.showConfirmMessage("确定要删除吗？", {
				callback : function() {
					AjaxUtil.ajax({
						type : "post",
						url : basePath + "/produce/workorder145/doDelete",
						dataType : "json",
						async : false,
						data : {
							'woId' : id
						},
						success : function(data) {
							pageParam = this_.encodePageParam();
							AlertUtil.showMessage('删除成功!');
							if (isRefresh) {
								/* this_.refreshPage(); */
								this_.decodePageParam();
							}
							if (typeof (o) == "function") {
								o(true);
							}
						}
					});
				}
			});
		});
	},
	/**
	 * 打开链接查看页面
	 */
	openWinView : function(id, jksjid, pageSwitch) {
		var this_ = this;
		if (pageSwitch == 1) {
			window.open(basePath + "/project2/maintenanceproject/view?id="+ id);
		} else if (pageSwitch == 2) {
			window.open(basePath + "/project2/eo/view?id=" + id);
		}else if(pageSwitch == 3 || pageSwitch == 4 || pageSwitch == 9 || (pageSwitch == 5 && !jksjid)){
			window.open(basePath+"/produce/workorder/woView?gdid="+id);
		}
		else if(pageSwitch == 5 && jksjid){
			window.open(basePath+"/project2/production/view?id="+id);
		}else if (pageSwitch == 6){
			window.open(basePath + "/produce/workpackage145/view?id=" + id);
		}
		else if (pageSwitch == 8) {
			this_.selectById(id, function(obj) {
				if (obj.zt != "7") {
					AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				window.open(basePath + "/produce/reservation/item/main?gdId="+ id + "&gdLx=145");
			});
		} else if (pageSwitch == 7) {
			window.open(basePath + "/project2/workcard/view/" + id);
		}
	},
	/** 打开修订弹框 */
	openWinXDClose : function(id, isRefresh, o) {
		o = o || window.event;
		if (o.stopPropagation) { // W3C阻止冒泡方法
			o.stopPropagation();
		} else {
			o.cancelBubble = true; // IE阻止冒泡方法
		}
		var this_ = this;
		this_.selectById(id,function(obj) {
		if (!(obj.zt == "9" || obj.zt == "10")) {
			AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!');
			this_.refreshPage();
			return;
		}
		if (obj.zt == 9) {
			Workorder145ZDCloseWin.show({
				viewObj : obj,
				isShowReplacementRecord : true,
				colOptionhead : true,
				callback : function(data) {// 回调函数
					if (data != null) {
						var message = '提交成功!';
						var url = basePath + "/produce/workorder145/doXDClose";
						this_.performAction(url,data,message,"workorder145_zdclose_Modal",isRefresh,o);
					}
				}
			});
		} else {
			Workorder145WGCloseWin.show({
					viewObj : obj,
					isShowReplacementRecord : true,
					colOptionhead : true,
					callback : function(data) {// 回调函数
						if (data != null) {
							var message = '提交成功!';
							var url = basePath + "/produce/workorder145/doXDClose";
							this_.performAction(url,data,message,"workorder145_wgclose_Modal",isRefresh,o);
						}
					}
				});
		}

	});
	},
	/** 根据ID查找数据 */
	selectById : function(id, obj) {// 通过id获取数据
		AjaxUtil.ajax({
			async : false,
			url : basePath + "/produce/workorder145/selectWOById",
			type : "post",
			data : {
				gdid : id
			},
			dataType : "json",
			success : function(data) {
				if (data != null) {
					if (typeof (obj) == "function") {
						obj(data);
					}
				}
			}
		});
	},
	/** 打开ATA章节号对话框* */
	openChapterWin : function() {
		var this_ = this;
		var zjh = $.trim($("#zjhid_search").val());
		FixChapterModal.show({
			parentWinId : '',
			selected : zjh,// 原值，在弹窗中默认勾选
			dprtcode : this_.dprtcode,// 机构代码
			callback : function(data) {// 回调函数
				if (data != null) {
					var zjhName = formatUndefine(data.zjh) + " " + formatUndefine(data.ywms);
					$("#zjhid_search").val(data.zjh);
					$("#zjhName_search").val(zjhName);
				} else {
					$("#zjhid_search").val('');
					$("#zjhName_search").val('');
				}
			}
		});
	},
	/** 后台数据请求 */
	performAction : function(url, param, message, winId, isRefresh, o) {
		var this_ = this;
		startWait($("#"+winId));
		// 提交数据
		AjaxUtil.ajax({
			url : url,
			contentType : "application/json;charset=utf-8",
			type : "post",
			data : JSON.stringify(param),
			dataType : "json",
			modal : $("#"+winId),
			success : function(data) {
				finishWait($("#"+winId));

				id = data;
				pageParam = this_.encodePageParam();
				AlertUtil.showMessage(message);

				$("#" + winId).modal("hide");
				if (isRefresh) {
					/* this_.refreshPage(); */
					this_.decodePageParam();
				}
				if (typeof (o) == "function") {
					o(true);
				}
			}
		});
	},
	refreshPage : function() {// 刷新页面
		this.goPage(this.pagination.page, this.pagination.sort,this.pagination.order);
	},
	/** 改变组织机构* */
	changeDprtcode : function() {
		var this_ = this;
		this_.loadFjList();
		this_.loadSearchFilterDatas();// 刷新搜索条件
	},
	/** 字段排序 */
	orderBy : function(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(
				function() { // 重置class
					$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
				});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#workorder145_pagination li[class='active']").text();
		this.goPage(currentPage, obj, orderStyle.split("_")[1]);
	},
	initWebuiPopover : function() {// 初始化
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view', 'body', function(obj) {
			return this_.getHistoryAttachmentList(obj);
		});
		$("#workorder145_table_div").scroll(function() {
			$('.attachment-view').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList : function(obj) {// 获取历史附件列表
		var jsonData = [ 
		                  {mainid : $(obj).attr('qtid'), type : '其它附件'}
				        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
				        ,{mainid :  $(obj).attr('gkfjid'), type : '工卡附件'}
				        ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	changefj : function() {
		this.goPage(1, "auto", "desc");
	},
	encodePageParam : function() {
		var this_ = this;
		var pageParam = {};
		pageParam.pagination = this_.pagination;
		return Base64.encode(JSON.stringify(pageParam));
	},
	decodePageParam : function() {
		var this_ = this;
		try {
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);

			var params = pageParamJson.params;
			if (pageParamJson.pagination) {
				this_.goPage(pageParamJson.pagination.page,pageParamJson.pagination.sort,pageParamJson.pagination.order);
			} else {
				this_.goPage(1, "auto", "desc");
			}
		} catch (e) {
			this_.goPage(1, "auto", "desc");
		}
	},
	exportExcel:function(){
		var param = this.getParams();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
		window.open(basePath+"/produce/workorder145/workorder145.xls?paramjson="+JSON.stringify(param));
	},
	print:function(id,gdlx,e){
		 if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
		var url=basePath+"/produce/workorder145/workorderPDF?id=" + encodeURIComponent(id)+"&gdlx="+gdlx+"&dprtcode="+encodeURIComponent($("#dprtcode_search").val());
	   	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url)+"&afterPrint="+1+"&id="+id+"&dprtcode="+$("#dprtcode_search").val()+"&ywlx=21"+"&basePath="+basePath,
					'PDF','width:50%;height:50%;top:100;left:100;');	
		}
};
function customResizeHeight(bodyHeight, tableHeight) {
	if ($(".displayContent").css("display") == "block") {
		// 完工反馈设置
		$(".displayContent:visible #attach_div .panel-body").removeAttr("style");
		$(".displayContent:visible #attach_div .panel-body").addClass("padding-top-0 padding-bottom-0");
	}
}