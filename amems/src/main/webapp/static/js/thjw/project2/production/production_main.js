
	$(function(){
		production.init();
		
		//执行待办
	    if(todo_ywid){
	    	if(todo_jd == 5){
	    		production.openEditPage(todo_ywid);
	    	}else if(todo_jd == 2){
	    		production.openAuditPage(todo_ywid);
	    	}else if(todo_jd == 3){
	    		production.openApprovePage(todo_ywid);
	    	}else{
	    		production.openAddPage();
	    	}
	    	todo_ywid = null;
	    }
		
	});
	
	var production = {
		
		tableId : "production_table",
		
		tbodyId : "production_table_list",
		
		paginationId : "production_table_pagination",
		
		// 页面初始化
		init : function(){
			
			var this_ = this;
			
			// 初始化导航栏
			this_.initNavigation();
			// 初始化飞机注册号
			this_.initAcReg();
			// 初始化控件
			this_.initWidget();
			// 重置搜索条件
			this_.searchreset();
			// 加载数据
			this_.reload();
		},
		
		/**
		 * 初始化导航栏
		 */
		initNavigation : function(){
			Navigation(menuCode, '', '');
		},
		
		/**
		 * 初始化控件
		 */
		initWidget : function(){
			
		},
		
		/**
		 * 初始化飞机注册号
		 */
		initAcReg : function(){
			var this_ = this;
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		 	var planeRegOption = "<option value='' selected='selected'>显示全部All</option>";
		 	if(planeDatas != null && planeDatas.length >0){
		 		$.each(planeDatas, function(i, planeData){
	 				planeRegOption 
	 					+= "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'>"
	 					+ StringUtil.escapeStr(planeData.FJZCH)+ " " + StringUtil.escapeStr(planeData.XLH) 
	 					+ "</option>";
		 		});
		 	}
	 		$("#fjzch").html(planeRegOption);
		},
		
		/**
		 * table重新加载
		 */
		reload:function(){
			// 重置排序图标
			TableUtil.resetTableSorting(this.tableId);
			this.goPage(1,"auto","desc");
		},
		
		/**
		 * table跳转页面
		 */
		goPage:function(pageNumber, sortColumn, orderType){
			this.AjaxGetDatasWithSearch(pageNumber, sortColumn, orderType);
		},
		
		/**
		 * 获取table数据
		 */
		AjaxGetDatasWithSearch:function(pageNumber, sortColumn, orderType){
			
			 var this_ = this;
			 var param = this_.gatherSearchParam();
			 param.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
			 startWait();
			 AjaxUtil.ajax({
				type: "post",
				url: basePath+"/project2/production/pagelist",
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
				    signByKeyword($.trim($("#keyword").val()), [4, 7, 8, 9], "#" + this_.tbodyId + " tr td");
				    // 移除
	 				FormatUtil.removeNullOrUndefined();
	 				// 隐藏流程记录和历史版本
	 				TableUtil.hideDisplayContent();
	 				// 刷新权限
	 				refreshPermission();
		 		}
		     });
		},
		
		/**
		 * 获取查询参数
		 */
		gatherSearchParam : function(){
			
			var this_ = this;
			var searchParam = {};
			searchParam.dprtcode = $("#dprtcode").val();
			searchParam.bBbid = '-';
			
			var paramsMap = {};
			paramsMap.fjzch = $("#fjzch").val();
			paramsMap.keyword = $.trim($("#keyword").val());
			var ztList = [];
			$("[name=zt]:checkbox:checked").each(function(){
				ztList = ztList.concat($(this).val().split(","));
			});
			if($("#wglgk").is(":checked")){
				searchParam.gkbh = '-';
			}
			paramsMap.ztList = ztList;
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		
		/**
		 * 拼接表格
		 */
		appendContentHtml:function(data){
			var this_ = this;
			var htmlContent = '';
			if(data.total > 0){
				$.each(data.rows,function(index,row){
					htmlContent +="<tr name='"+row.id+"'>";
					htmlContent +="<td class='text-center fixed-column'>";
					if(row.zt == 1 || row.zt == 5 || row.zt == 6){
						htmlContent +="<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:production:main:02' onclick='production.openEditPage(\""+row.id+"\")' title='修改 Edit'></i>&nbsp;&nbsp;";
						htmlContent +="<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:production:main:03' onclick='production.deleteData(\""+row.id+"\")' title='删除 Delete'></i>";
					}
					if(row.zt == 2){
						htmlContent +="<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project2:production:main:06' onclick='production.openAuditPage(\""+row.id+"\")' title='审核 Review'></i>";
					}
					if(row.zt == 3){
						htmlContent +="<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project2:production:main:07' onclick='production.openApprovePage(\""+row.id+"\")' title='审批 Approve'></i>";
					}
					if(row.zt == 7){
						htmlContent +="<i class='icon-pencil color-blue cursor-pointer checkPermission' permissioncode='project2:production:main:04' onclick='production.openRevisionPage(\""+row.id+"\")' title='改版 Revision'></i>&nbsp;&nbsp;";
						htmlContent +="<i class='icon-off color-blue cursor-pointer checkPermission' permissioncode='project2:production:main:05' onclick='production.closeData(\""+row.id+"\")' title='关闭 Close'></i>";
					}
					if(row.zt == 9){
						htmlContent +="<i class='icon-ok color-blue cursor-pointer checkPermission' permissioncode='project2:production:main:08' onclick='production.openData(\""+row.id+"\")' title='启用 Open'></i>";
					}
					htmlContent +="</td>";
					
					htmlContent +="<td>"+StringUtil.escapeStr(row.jx)+"</td>"
					htmlContent +="<td>"+StringUtil.escapeStr(row.syx)+"</td>"
					htmlContent +="<td>"+StringUtil.escapeStr(row.zlms)+"</td>";
					htmlContent +="<td class='text-center'>"+DicAndEnumUtil.getEnumName('productionOrderStatusEnum', row.zt)+"</td>"
					htmlContent +="<td><a href='javascript:void(0);' onclick='production.viewProductionOrder(\""+row.id+"\")'>R"+StringUtil.escapeStr(row.bb)+"</a></td>";
					htmlContent +="<td class='text-center'>";
					htmlContent +="<a href='javascript:void(0);' onclick='production.viewWorkCard(\""+row.gkid+"\")'>"+StringUtil.escapeStr(row.gkbh)+"</a>";
					if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) 
							|| (row.workCard && row.workCard.gkfjid != null && row.workCard.gkfjid != "")
							|| (row.workCard && row.workCard.gznrfjid != null && row.workCard.gznrfjid != "")){
						row.workCard = row.workCard || {};
						htmlContent += '&nbsp;<i qtid="'+row.workCard.id+'" gkfjid="'+row.workCard.gkfjid+'" gznrfjid="'+row.workCard.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
					}
					htmlContent +="</td>";
					htmlContent +="<td class='text-center'><a href='javascript:void(0);' onclick='production.viewProductionOrder(\""+row.id+"\")'>"+StringUtil.escapeStr(row.zlbh)+"</a></td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.fixChapter ? row.fixChapter.displayName : '')+"</td>";
					htmlContent +="<td>"+StringUtil.escapeStr(row.whr ? row.whr.displayName : '')+"</td>";
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>";
					htmlContent +="<td class='text-center'>"+StringUtil.escapeStr(row.sxsj)+"</td>";
					htmlContent +="</tr>"
				});
			}else{
				htmlContent = "<tr class='no-data'><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>";
			}
			$("#" + this_.tbodyId).html(htmlContent);
			this_.initWebuiPopover();
			$("#" + this_.tbodyId + ">tr:not(.no-data)").on("click", function(event){
				this_.showBottomData(event.target, this);
			});
		},
		
		/**
		 * 初始化
		 */
		initWebuiPopover : function(){
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#production_table_top_div").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		},
		
		/**
		 * 获取历史附件列表
		 */
		getHistoryAttachmentList : function(obj){
			var jsonData = [
		         {mainid : $(obj).attr('qtid'), type : '其它附件'}
		        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
		        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
		    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},
		
		/**
		 * 拼接分页
		 */
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
		
		/**
		 * 查询更多
		 */
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
		
		/**
		 * 显示底部数据
		 */
		showBottomData : function(e, tr){
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
		 	production_process_record.loadData(id, $("#production_process_record_tab"));
		 	production_history_version.loadData(id, $("#production_history_version_tab"));
		 	
			if(!isBottomShown){
				TableUtil.makeTargetRowVisible($(tr), $("#production_table_top_div"));
			}
		 	
		 	$("[name="+id+"]").addClass('bg_tr').siblings().removeClass('bg_tr');
		},
		
		/**
		 * 打开新增页面
		 */
		openAddPage : function(){
			production_detail_modal.show({
				id : null,
				type : "add",
				dprtcode : $("#dprtcode").val()
			});
		},
		
		/**
		 * 打开修改页面
		 */
		openEditPage : function(id){
			production_detail_modal.show({
				id : id,
				type : "edit",
				dprtcode : $("#dprtcode").val()
			});
		},
		
		/**
		 * 打开审核页面
		 */
		openAuditPage : function(id){
			production_detail_modal.show({
				id : id,
				type : "audit",
				dprtcode : $("#dprtcode").val()
			});
		},
		
		/**
		 * 打开审批页面
		 */
		openApprovePage : function(id){
			production_detail_modal.show({
				id : id,
				type : "approve",
				dprtcode : $("#dprtcode").val()
			});
		},
		
		/**
		 * 打开改版页面
		 */
		openRevisionPage : function(id){
			production_detail_modal.show({
				id : id,
				type : "revision",
				dprtcode : $("#dprtcode").val()
			});
		},
		
		/**
		 * 删除数据
		 */
		deleteData : function(id){
			AlertUtil.showConfirmMessage("是否确认删除该数据？",{callback:function(){
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/project2/production/delete",
					type:"post",
					data:{
						id : id,
					},
					dataType:"json",
					success:function(data){
						finishWait();
						AlertUtil.showMessage("删除成功!");
						production.reload();
					}
				});
			}});
		},
		
		/**
		 * 启用数据
		 */
		openData : function(id){
			AlertUtil.showConfirmMessage("是否确认启用该数据？",{callback:function(){
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/project2/production/open",
					type:"post",
					data:{
						id : id,
					},
					dataType:"json",
					success:function(data){
						finishWait();
						AlertUtil.showMessage("启用成功!");
						production.reload();
					}
				});
			}});
		},
		
		/**
		 * 关闭数据
		 */
		closeData : function(id){
			AlertUtil.showConfirmMessage("是否确认关闭该数据？",{callback:function(){
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/project2/production/close",
					type:"post",
					data:{
						id : id,
					},
					dataType:"json",
					success:function(data){
						finishWait();
						AlertUtil.showMessage("关闭成功!");
						production.reload();
					}
				});
			}});
		},
		
		/**
		 * 查看工卡
		 */
		viewWorkCard : function(id){
			window.open(basePath + "/project2/workcard/view/" + id);
		},
		
		/**
		 * 查看生产指令
		 */
		viewProductionOrder : function(id){
			window.open(basePath + "/project2/production/view?id=" + id);
		},
		
		/**
		 * 重置搜索条件
		 */
		searchreset:function(){
			var this_ = this;		
			$("#keyword").val("");
			$("#dprtcode").val(userJgdm);
			this_.initAcReg();
			$("[name=zt]:checkbox").each(function(){
				var $obj = $(this);
				if($obj.val() != 9){
					$obj.prop("checked", "checked");
				}else{
					$obj.prop("checked", false);
				}
			});
			$("#wglgk").prop("checked", false);
		},
		
		/**
		 * 字段排序
		 */
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
		
	};
	
	/**
	 * 自定义页面高度
	 * @param bodyHeight
	 * @param tableHeight
	 */
	function customResizeHeight(bodyHeight, tableHeight){
		
	}
	
	