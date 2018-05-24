
	$(function(){
		Navigation(menuCode, '', '', 'GC-8-1', '韩武', '2017-10-12', '韩武', '2017-10-12');//加载导航栏
		flb.init();
	});
	
	var flb = {
		
		tableId : "flb_table",
		
		tbodyId : "flb_table_list",
		
		paginationId : "flb_table_pagination",
		
		// 待提交的飞行记录本
		messages : [],
			
		// 页面初始化
		init : function(){
			
			
			var this_ = this;
			
			// 初始化飞机注册号
			this_.initAcReg();
			// 初始化控件
			this_.initWidget();
			// 加载数据
			this_.reload();
			// 初始化飞行数据
			this_.initFlighRecord();
			// 初始化日志
			this_.initLog();
		},
		
		// 初始化控件
		initWidget : function(){
			
			var this_ = this;
			
			// 初始化日期控件
			$('.date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
			$('.date-range-picker').daterangepicker().prev().on("click", function() {
				$(this).next().focus();
			});
			
		},
		
		// 初始化飞行数据
		initFlighRecord : function(){
			var this_ = this;
			$("#bottom_hidden_content [name='fxrwlx']>select").replaceWith('<input class="form-control input-sm" disabled="disabled" type="text">');
			this_.record = new FlbRecord({
				parentId : "bottom_hidden_content",
				ope_type : 1,
				win_type : "main",
				dprtcode:this_.getDprtcode(),
				callback : function(){
					$("#bottom_hidden_content [name='flb_detail_content']>div").removeAttr("style");
					$("#bottom_hidden_content [name='flb_detail_des']").remove();
					$("#bottom_hidden_content [name='flb_detail_content']")
						.removeClass("col-lg-11").removeClass("padding-leftright-8")
						.addClass("col-lg-12").addClass("padding-left-0").addClass("padding-right-0");
					$("#bottom_hidden_content input").attr("disabled","disabled");
					$("#bottom_hidden_content select").attr("disabled","disabled");
					$("#bottom_hidden_content button").remove();
					$("#bottom_hidden_content .readonly-style").removeClass("readonly-style");
					$("#bottom_hidden_content [name='jz_btn']").remove();
					$("#bottom_hidden_content th:first>div").removeClass("pull-left");
				}
			});
		},
		
		// 初始化飞机注册号
		initAcReg : function(id,dprtcode){
			var this_ = this;
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode||this_.getDprtcode()});
		 	var planeRegOption = '';
		 	if(planeDatas != null && planeDatas.length >0){
		 		$.each(planeDatas, function(i, planeData){
	 				planeRegOption 
	 					+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' " +
	 							"jx='"+StringUtil.escapeStr(planeData.FJJX)+"' "+
	 							"xlh='"+StringUtil.escapeStr(planeData.XLH)+"' "+
	 							"fdjsl='"+StringUtil.escapeStr(planeData.FDJSL)+"'>"
	 					+ StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH) 
	 					+ "</option>";
		 		});
		 	}else{
		 		planeRegOption = '<option value="NO_PLANE">暂无飞机 No plane</option>';
		 	}
		 	if(id){
		 		$("#"+id).html(planeRegOption);
		 	}else{
		 		$("#fjzch").html(planeRegOption);
		 	}
		},
		
		// 改变组织机构
		changeDprtcode : function(){
			this.initAcReg();
			this.reload();
		},
		
		// 获取组织机构
		getDprtcode : function(){
			return $.trim($("#dprtcode").val());
		},
		
		// 获取飞机注册号
		getFjzch : function(){
			return $.trim($("#fjzch").val());
		},
		
		// 获取发动机数量
		getEngCount : function(){
			return parseInt($("#fjzch option:selected").attr("fdjsl"));
		},
		
		// 获取关键字
		getKeyword : function(){
			return $.trim($("#keyword").val());
		},
		
		// 初始化消息提示
		initMessage : function(){
			var this_ = this;
		 	var html = "";
		 	$.each(this_.messages, function(index, row){
				html += "<li>";
				html += "<a  onclick=\"goToEditPage('"+row.id+"');\" href='javascript:void(0);' title='"+StringUtil.title(row.fxjlbh,30)+"'>请提交飞行记录本："+StringUtil.subString(row.fxjlbh,30)+"</a>";
		 		html += "</li>";
			});
		 	$("#messageUl").html(html);
		 	
		 	$("#messageDiv").BreakingNews({
		 		background		:"#FFF",
		 		title			:"",
		 		titlecolor		:"#FFF",
		 		titlebgcolor	:"#FFF",
		 		linkcolor		:"#333",
		 		linkhovercolor		:"#428bca",
		 		fonttextsize		:14,
		 		isbold			:false,
		 		border			:"solid 0px #099",
		 		width			:"250px",
		 		timer			:3000,
		 		autoplay		:true,
		 		effect			:"slide",
		 		marginleft      :10
		 	});
		     
		 	var title =  "<div>待修改的飞行记录本<span class=\"badge margin-left-10\">"+this_.messages.length+"</span></div>" ;
		 	$('#tghelp').webuiPopover('destroy');
		 	$('#tghelp').webuiPopover({
		 		placement:'auto',
		 		container: document.body,
		 		style:'',
		 		padding: false,
		 		title : title,// 设置 弹出框 的标题
		 		trigger: 'click',
		 		type:'html',
		 		width:500,
		 		height:'auto',
		 		backdrop:false,
		 		content : this_.generateMessageHtml(this_.messages)
		 	});
		 	
		 	if(this_.messages.length > 0){
		 		$("#tghelp").show();
		 	}else{
		 		$("#tghelp").hide();
		 	}
		},
		
		// 拼接消息内容
		generateMessageHtml : function(messages){
			var html = "<ul class='bd list-group margin-bottom-0'>";
			$.each(messages, function(index, row){
				html += "<li class='list-group-item'>";
				if(row.jjd == 9){
					html += "<i class='icon-volume-up margin-right-5' style='color:red;'></i>";
				}else{
					html += "<i class='icon-volume-up margin-right-10'></i>";
				}
				html += "<span class='margin-right-10'><a onclick=\"goToEditPage('"+row.id+"');\" href='javascript:void(0);' title='"+StringUtil.escapeStr(StringUtil.title(row.fxjlbh,30))+"'>"+StringUtil.escapeStr(StringUtil.subString(row.fxjlbh,30))+"</a></span>";
				html += "</li>";
			});
			html += "</ul>"
			return html;
		},
		
		// table重新加载
		reload:function(){
			// 重置排序图标
			TableUtil.resetTableSorting(this.tableId);
			this.goPage(1,"auto","desc");
		},
		
		// table跳转页面
		goPage:function(pageNumber, sortColumn, orderType){
			this.AjaxGetDatasWithSearch(pageNumber, sortColumn, orderType);
		},
		
		// 获取table数据
		AjaxGetDatasWithSearch:function(pageNumber, sortColumn, orderType){
			
			 var this_ = this;
			 var param = this_.gatherSearchParam();
			 param.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
			 startWait();
			 AjaxUtil.ajax({
				type: "post",
				url: basePath+"/produce/flb/queryAllList",
		 		contentType:"application/json;charset=utf-8",
		 		dataType:"json",
		 		data:JSON.stringify(param),
		 		success:function(data){
		 			finishWait();
		 			// 拼接表格
		 			this_.appendContentHtml(data);
		 			// 拼接分页
		 			this_.appendPaginationHtml(data, pageNumber, sortColumn, orderType);
		 			// 表格添加title
		 			TableUtil.addTitle("#" + this_.tbodyId + " tr td");
					// 标记关键字
				    signByKeyword(this_.getKeyword(), [4, 5], "#" + this_.tbodyId + " tr td");
				    // 移除
	 				FormatUtil.removeNullOrUndefined();
	 				// 初始化消息提示
	 				this_.initMessage();	
	 				// 隐藏飞行记录
	 				this_.hideBottom();
	 				// 刷新权限
	 				refreshPermission();
	 				// 浮动表头
				    new Sticky({tableId:this_.tableId});
		 		}
		     });
		},
		
		// 获取查询参数
		gatherSearchParam : function(){
			
			var this_ = this;
			var searchParam = {};
			searchParam.fjzch = this_.getFjzch();
			searchParam.dprtcode = this_.getDprtcode();
			
			var paramsMap = {};
			paramsMap.keyword = this_.getKeyword();
			var fxrq = $("#fxrq").val();
			if(fxrq != ''){
				paramsMap.fxrqBegin = fxrq.substring(0,10);
				paramsMap.fxrqEnd = fxrq.substring(13,23);
			}
			var ztList = [];
			$("[name=zt]:checkbox:checked").each(function(){
				ztList.push($(this).val());
			});
			paramsMap.ztList = ztList;
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		
		// 拼接表格
		appendContentHtml:function(data){
			var this_ = this;
			var htmlContent = '';
			this_.messages = [];
			if(data.total > 0){
				$.each(data.rows,function(index,row){
					if(row.isXdtx == 1 && (row.zt == 2 || row.zt == 12)){
						this_.messages.push(row);
					}
					htmlContent +="<tr name='"+row.id+"'>";
					htmlContent +="<td class='text-center fixed-column'>";
					if(row.zt == 1 || row.zt == 2 || row.zt == 12){
						htmlContent +="<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='produce:flb:main:02,produce:flb:main:03,produce:flb:main:04,produce:flb:main:05' onclick='flb_detail.show(\""+row.id+"\")' title='修改 Edit'></i>";
					}
//					if(row.zt == 1){
//						htmlContent +="&nbsp;&nbsp;<i class='icon-trash color-blue cursor-pointer' onclick='flb.del(\""+row.id+"\")' title='删除 Delete'></i>";
//					}
					htmlContent +="</td>";
					
					htmlContent +="<td>"+StringUtil.escapeStr(row.fjzch)+"</td>"
					htmlContent +="<td class='text-center'>"+(row.fxrq||"").substr(0, 10)+"</td>"
					htmlContent +="<td class='text-center'><a href='javascript:void(0);' onclick='flb.view(\""+row.id+"\")'>"+StringUtil.escapeStr(row.fxjlbh)+"</a></td>";
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.jlbym)+"</td>";
					htmlContent +="<td class='text-center'>"+DicAndEnumUtil.getEnumName('flbStatusEnum', row.zt)+"</td>"
					htmlContent +="<td>"+StringUtil.escapeStr(row.zdr ? row.zdr.displayName : '')+"</td>";
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.zdsj)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.xdr ? row.xdr.displayName : '')+"</td>";
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.xdsj)+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr class='no-data'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>";
			}
			$("#" + this_.tbodyId).html(htmlContent);
			$("#" + this_.tbodyId + ">tr:not(.no-data)").on("click", function(event){
				this_.showFlighRecord(event.target, this);
			});
		},
		
		// 拼接分页
		appendPaginationHtml : function(data, pageNumber, sortColumn, orderType){
			var this_ = this;
			if(data.total > 0){
				new Pagination({
					renderTo : this_.paginationId,
					data : data,
					sortColumn : sortColumn,
					orderType : orderType,
					extParams : {},
					goPage : function(a, b, c) {
						this_.AjaxGetDatasWithSearch(a, b, c);
					}
				});
			}else{
				$("#"+this_.paginationId).empty();
			}
		},
		
		// 查询更多
		more : function(){
			if ($("#divSearch").css("display") == "none") {
				$("#divSearch").css("display", "block");
				$("#icon").removeClass("icon-caret-down");
				$("#icon").addClass("icon-caret-up");
			} else {
				$("#divSearch").css("display", "none");
				$("#icon").removeClass("icon-caret-up");
				$("#icon").addClass("icon-caret-down");
			}
		},
		
		// 展示飞行记录
		showFlighRecord : function(e, tr){
			var this_ = this;
			
			// 点击按钮则不展示
			if($(e).is("i") || $(e).is("a")){
				return false;
			}
			var id = $(tr).attr("name");
			
			var isBottomShown = false;
			if($(".displayContent").is(":visible")){
				isBottomShown = true;
			}
			
		 	TableUtil.showDisplayContent();
		 	if($("#hideIcon").length==0){
		 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="flb.hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#flb_table_pagination .fenye"));
			}
		 	
			if(!isBottomShown){
				TableUtil.makeTargetRowVisible($(tr), $("#flb_table_top_div"));
			}
		 	
		 	$("[name="+id+"]").addClass('bg_tr').siblings().removeClass('bg_tr');
		 	new Sticky({tableId:this_.tableId});
		 	
		 	this_.loadFlightData(id);
		},
		
		// 隐藏飞行记录
		hideBottom : function(){
			$("#hideIcon").remove();
			TableUtil.hideDisplayContent();
		},
		
		// 加载飞行数据
		loadFlightData : function(id){
			startWait();
			var this_ = this;
			AjaxUtil.ajax({
				url: basePath+"/produce/flb/detail",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					id : id,
				}),
				success:function(data){
					finishWait();
					this_.record.fillData(data.flightSheetVoyageList);
					
					//根据状态显示按钮
					this_.hiddenBtn();
					if(data.zt == 1){
						$(".btn-save").show();
						$(".btn-submit").show();
						$(".btn-delete").show();
					}else if(data.zt == 2 || data.zt == 12){
						$(".btn-revision").show();
						$(".btn-scrap").show();
					}
			    }
			}); 
		},
		
		// 新增飞行记录单
		add : function(){
			flb_detail.show();
		},
		
		// 默认隐藏所有按钮
		hiddenBtn : function(){
			$(".btn-save").hide();
			$(".btn-submit").hide();
			$(".btn-delete").hide();
			$(".btn-scrap").hide();
			$(".btn-revision").hide();
		},
		
		// 查看飞行记录单
		view : function(id){
			window.open(basePath + "/produce/flb/view?id=" + id);
		},
		
		// 获取当前日期
		getNowFormatDate : function() {
		    var date = new Date();
		    var seperator1 = "-";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
		    return currentdate;
		},
		searchreset:function(){
			var this_ = this;		
			$("#keyword").val("");
			$("#fxrq").val("");		
			$("#dprtcode").val(userJgdm);
			this_.initAcReg();
			var zt = document.getElementsByName("zt");
			for (var i = 0; i < zt.length; i++) {
				if(zt[i].value!=9){
					zt[i].checked='checked';
				}else{
					zt[i].checked=false;
				}
			}
		},
		
		// 字段排序
		orderBy : function(sortColumn, prefix){
			var this_ = this;
			var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableId));
			var orderStyle = $obj.attr("class");
			$(".sorting_desc", $("#"+this_.tableId)).removeClass("sorting_desc").addClass("sorting");
			$(".sorting_asc", $("#"+this_.tableId)).removeClass("sorting_asc").addClass("sorting");
			var orderType = "asc";
			if (orderStyle.indexOf ("sorting_asc")>=0) {
				$obj.removeClass("sorting").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$obj.removeClass("sorting").addClass("sorting_asc");
				orderType = "asc";
			}
			var currentPage = $("#"+this_.paginationId+" li[class='active']", $("#"+this_.id)).text();
			if(currentPage == ""){currentPage = 1;}
			if(prefix != null && prefix != '' && typeof prefix != undefined){
				sortColumn = prefix+"."+sortColumn;
			}
			this_.goPage(currentPage, sortColumn, orderType);
		},
		
		// 初始化日志
		initLog : function(){
			logModal.init({code:'B_S2_006'});
		},
		
	};
	
	// 自定义页面高度
	function customResizeHeight(bodyHeight, tableHeight){
		
	}
	
	