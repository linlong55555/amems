
/**
 *初始化当前js
 */
$(function(){
	Navigation(menuCode, '', '', 'SC11-1', '刘兵', '2017-11-14', '刘兵', '2017-11-14');//加载导航栏
	post_main_panel_content.init();
	logModal.init({code:'B_Z_005'});//初始化日志功能
	
	//执行待办
    if(todo_ywid){
    	if(todo_jd == 3){
    		post_main_panel_content.openWinEvaluat(todo_ywid);
    	}
    	todo_ywid = null;
    }
	
});

var post_main_panel_content = {
		id:'post_main_panel_content',
		tableDivId : 'post_main_Table_top_div',
		tableId : 'post_main_Table',
		tbodyId : 'post_main_list',
		paginationId:'post_main_pagination',
		pagination : {},
		operationId : '',
		sqlxArr : ["","初次","延期","增加"],
		init : function(){
			this.initInfo();
		},
		initInfo : function(){
			var this_ = this;
			$("#zt_search", $("#"+this.id)).val(3);
			this_.load();
			refreshPermission();
			//回车事件控制
			document.onkeydown = function(event){
				e = event ? event :(window.event ? window.event : null); 
				if(e.keyCode==13){
					if($("#keyword_search", $("#"+this_.id)).is(":focus") 
							|| $("#keyword_search", $("#"+this_.id)).is(":focus")
					){
						this_.load();      
					}
				}
			};
		},
		//加载数据
		load : function(pageNumber, sortColumn, orderType){
			var this_ = this;
			this_.mainData = [];
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
			this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
			searchParam.pagination = this_.pagination;
			if(this_.operationId != ""){
				searchParam.id = this_.operationId;
				this_.operationId = "";
			}
			$.extend(searchParam, this_.getParams());
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/quality/post/application/queryAllPageList",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					finishWait();
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
						signByKeyword($("#keyword_search").val(),[3,4,5,7],"#"+this_.tbodyId+" tr td");
					} else {
						$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
						$("#"+this_.paginationId, $("#"+this_.id)).empty();
						$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"14\" class='text-center'>暂无数据 No data.</td></tr>");
					}
					new Sticky({tableId:this_.tableId});
		      }
		    }); 
		},	
		getParams : function(){//将搜索条件封装 
			var searchParam = {};
			var paramsMap = {};
			var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
			var zt = $.trim($("#zt_search").val(), $("#"+this.id));//状态
			searchParam.keyword = keyword; //关键字
			searchParam.dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());//组织机构
			if($("#yxq_search", $("#"+this.id)).is(":checked")){
				zt = '';
				paramsMap.isChecked = "checked";
			}
			searchParam.zt = zt;
			paramsMap.sqgw = $.trim($("#sqgw_search", $("#"+this.id)).val());//申请岗位
			paramsMap.sqrs=$.trim($("#sqr_search").val(), $("#"+this.id));//申请人
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		appendContentHtml: function(list){
			var this_ = this;
			var htmlContent = '';
			$.each(list,function(index,row){
			   
				htmlContent += "<tr>";
				htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'>";
				if(row.zt == 3){
					htmlContent += "<i class='spacing icon-tumblr-sign color-blue cursor-pointer checkPermission' permissioncode='quality:post:evaluation:main:01' onClick="+this_.id+".openWinEvaluat('"+ row.id + "') title='评估 Assessment'></i>";
				}
				if(row.zt == 4){
					htmlContent += "<i class='spacing icon-print color-blue cursor-pointer' onClick="+this_.id+".print('"+ row.id + "') title='打印 Print'></i>";
					htmlContent += "<i class='spacing icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:post:evaluation:main:01' onClick="+this_.id+".openWinEdit('"+ row.id + "') title='修改 Edit'></i>";
					if(row.pgjl == 1){
						htmlContent += "<i class='spacing icon-cogs color-blue cursor-pointer checkPermission' permissioncode='quality:post:evaluation:main:02' onClick="+this_.id+".updateEffectDate('"+ row.id + "') title='设置有效期 Setting'></i>";
						htmlContent += "<i class='spacing icon-upload-alt color-blue cursor-pointer checkPermission' permissioncode='quality:post:evaluation:main:03' onClick="+this_.id+".openAttachmentWinEdit('"+ row.id + "','"+ row.sqsfjid + "') title='上传授权书 Upload'></i>";
//						if(row.paramsMap.noyxq > 0){
//							bgcolor = warningColor.level1;//红色
//							htmlContent += "<i title='未填写授权有效日期' class='spacing iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' />";
//						}
					}
				}
			    htmlContent += "</td>";
			    
			    htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'>";
				
			    if(row.zt == 4 && row.pgjl == 1 && row.paramsMap.noyxq > 0){
					bgcolor = warningColor.level1;//红色
					htmlContent += "<i title='未填写授权有效日期' class='spacing iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' />";
				}
				
				htmlContent += "</td>";
			    
			    htmlContent += "<td class='fixed-column' title='"+StringUtil.escapeStr(row.sqsqdh)+"' style='text-align:left;vertical-align:middle;'>";
				htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".view('"+row.id+"')>"+StringUtil.escapeStr(row.sqsqdh)+"</a>";
				htmlContent += "</td>";
			    
			    htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.rybh:'')+"' style='text-align:left;vertical-align:middle;'>";
				htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".findWxdaView('"+row.sqrdaid+"')>"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.rybh:'')+"</a>";
				htmlContent += "</td>";
				
				htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.xm:'')+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.xm:'')+"</td>";
			    htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.xrzz:'')+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.xrzz:'')+"</td>";
			    htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.szdw:'')+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.szdw:'')+"</td>";
			    
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(this_.sqlxArr[row.sqlx])+"</td>";
			    htmlContent += "<td title='"+(StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgbh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgmc:''))+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgbh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgmc:'')+"</td>";
			    
			    if(row.zt==9){
			    	htmlContent += "<td style='text-align:left;vertical-align:middle;' >"; 
			    	htmlContent += "<a href='javascript:void(0);'  " 
			    		+ "sqsqdh='"+StringUtil.escapeStr(row.sqsqdh)+"'   zt='"+row.zt+"' gbyy='"+StringUtil.escapeStr(row.gbyy)+"'" 
			    		+ "gbczsj='"+StringUtil.escapeStr(row.gbrq)+"'   gbrid='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.gbr:'')+"' " 
			    		+"onclick=\""+this_.id+".zdjs('"+row.id+"', this , false)\">"+DicAndEnumUtil.getEnumName('postStatusEnum',row.zt)+"</a></td>";
			    	htmlContent += "</td>"; 
			    }else{
			    	htmlContent += "<td title='"+DicAndEnumUtil.getEnumName('postStatusEnum',row.zt)+"' style='text-align:left;vertical-align:middle;' >"; 
			    	htmlContent += DicAndEnumUtil.getEnumName('postStatusEnum',row.zt); 
			    	htmlContent += "</td>"; 
			    }
			    htmlContent += "<td title='"+this_.formatYxqTitle(row.postApplicationSQJXList)+"' style='text-align:left;vertical-align:middle;' >"+this_.formatYxqContent(row.postApplicationSQJXList)+"</td>";
			    htmlContent += "<td title='"+(StringUtil.escapeStr(row.whr?row.whr.username:'')+" "+StringUtil.escapeStr(row.whr?row.whr.realname:''))+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.whr?row.whr.username:'')+" "+StringUtil.escapeStr(row.whr?row.whr.realname:'')+"</td>";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
			    htmlContent += "<td title='"+StringUtil.escapeStr(row.dprt?row.dprt.dprtname:'')+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.dprt?row.dprt.dprtname:'')+"</td>"; 
			    htmlContent += "</tr>" ;
			    
			});
			$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
			$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
			refreshPermission();
		},
		/**
		 * 格式化有效期标题
		 */
		formatYxqTitle : function(list){
			var htmlContent = '';
			if(list != null && list != "" && list.length > 0 ){
				$.each(list,function(i,obj){
					
					htmlContent += StringUtil.escapeStr(obj.fjjx);
					if(formatUndefine(obj.yxqKs) != ''){
						htmlContent += " " + indexOfTime(obj.yxqKs) + " ";
					}
					if(formatUndefine(obj.yxqKs) != '' && formatUndefine(obj.yxqJs) != ''){
						htmlContent += "~"; 
					}
					if(formatUndefine(obj.yxqJs) != ''){
						htmlContent += " " + indexOfTime(obj.yxqJs);
					}
					if(i != list.length - 1){
						htmlContent += "\n";
					}
				});
			}
			return htmlContent;
		},
		/**
		 * 格式化有效期内容
		 */
		formatYxqContent : function(list){
			var htmlContent = '';
			if(list != null && list != "" && list.length > 0 ){
				$.each(list,function(i,obj){
					
					if(i == 1){
						htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=CollapseOrExpandUtil.collapseOrExpand(this,'post_main_Table')></i><div class='affectedDisplayFile' style='display:none'>";
					}
					htmlContent += StringUtil.escapeStr(obj.fjjx);
					if(formatUndefine(obj.yxqKs) != ''){
						htmlContent += " " + indexOfTime(obj.yxqKs) + " ";
					}
					if(formatUndefine(obj.yxqKs) != '' && formatUndefine(obj.yxqJs) != ''){
						htmlContent += "~"; 
					}
					if(formatUndefine(obj.yxqJs) != ''){
						htmlContent += " " + indexOfTime(obj.yxqJs);
					}
					if(i != 0 && i != list.length - 1){
						htmlContent += "</br>";
					}
					if(i == list.length - 1){
						htmlContent += "</div>";
					}
				});
			}
			return htmlContent;
		},
		/**
		 * 评估
		 */
		openWinEvaluat : function(id){
			var this_ = this;
			Post_Open_Modal.show({
				id:id,
				editParam:3,//3:评估,4:修改评估
				modalHeadCN : '岗位评估',
				modalHeadENG : 'Post',
				callback : function(data) {//回调函数
					Post_Open_Modal.close();//关闭弹窗
					AlertUtil.showMessage('评估成功!');
					this_.operationId = data;
					this_.refreshPage();
				}
			});
		},
		/**
		 * 修改评估
		 */
		openWinEdit : function(id){
			var this_ = this;
			Post_Open_Modal.show({
				id:id,
				editParam:4,//3:评估,4:修改评估
				modalHeadCN : '修改评估',
				modalHeadENG : 'Edit',
				callback : function(data) {//回调函数
					Post_Open_Modal.close();//关闭弹窗
					AlertUtil.showMessage('修改成功!');
					this_.operationId = data;
					this_.refreshPage();
				}
			});
		},
		/**
		 * 设置有效期
		 */
		updateEffectDate : function(id){
			var this_ = this;
			setting_effect_alert_Modal.show({
				id : id,
				callback: function(param){//回调函数
					param.id = id;
					var url = basePath + "/quality/post/evaluation/updateEffectDate";
					this_.performAction(url, param, false, '',function(obj){
						AlertUtil.showMessage('设置有效期成功!');
						setting_effect_alert_Modal.close();
						this_.refreshPage();
					});
				}
			});
		},
		/**
		 * 上传授权书
		 */
		openAttachmentWinEdit : function(id, sqsfjid){
			var this_ = this;
			open_win_attachments_list_edit.show({
				djid : sqsfjid,
		 		colOptionhead : true,
				fileHead : true,
				fileType:"17",
				callback: function(attachments){//回调函数
					var param = {};
					param.id = id;
					param.attachmentList = attachments;
					// 提交数据
					AjaxUtil.ajax({
						url:basePath + "/quality/post/evaluation/updateUpload",
						contentType:"application/json;charset=utf-8",
						type:"post",
						data:JSON.stringify(param),
						dataType:"json",
						success:function(data){
							AlertUtil.showMessage('上传授权书成功!');
							open_win_attachments_list_edit.close();
							this_.refreshPage();
						}
					});
				}
			});//显示附件
		},
		/**
		 * 查看指定结束
		 */
		zdjs : function(id, e, isEdit){
			var sqsqdh=$(e).attr("sqsqdh"); //单号
			var gbyy=$(e).attr("gbyy"); 	//完成原因
			var jsr=$(e).attr("gbrid");		//完成人
			var jssj=$(e).attr("gbczsj");	//完成日期
			var zt=$(e).attr("zt"); 		//状态
			ConfirmModal.show({
		 		chinaHead:'授权单号',//单号中文名称
		 		englishHead:'Post No.',//单号英文名称
		 		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		 		jsdh:sqsqdh,//保留单号
		 		jsr:jsr,//完成人
		 		jssj:jssj,//完成时间
		 		jsyy:gbyy,//完成原因
		 		zt:zt,//状态
		 		callback: function(data){//回调函数
		 			
		 		}
		 	});
		},
		/**
		 * 查看岗位申请
		 */
		view : function(id){
			window.open(basePath+"/quality/post/application/find/"+id);
		},
		/**
		 * 查看人员档案
		 */
		findWxdaView : function(id){
			window.open(basePath + "/quality/maintenancepersonnel/view/"+id);
		},
		more: function() {//查询条件更多 展开/收缩
			if ($("#divSearch", $("#"+this.id)).css("display") == "none") {
				$("#divSearch", $("#"+this.id)).css("display", "block");
				$("#icon", $("#"+this.id)).removeClass("icon-caret-down");
				$("#icon", $("#"+this.id)).addClass("icon-caret-up");
			} else {
				$("#divSearch", $("#"+this.id)).css("display", "none");
				$("#icon", $("#"+this.id)).removeClass("icon-caret-up");
				$("#icon", $("#"+this.id)).addClass("icon-caret-down");
			}
		},
		/**
		 * 刷新页面
		 */
		refreshPage : function(){
			//重置排序图标
			TableUtil.resetTableSorting(this.tableDivId);
			this.load(this.pagination.page, this.pagination.sort, this.pagination.order);
		},
		//搜索
		search: function(){
			//重置排序图标
			TableUtil.resetTableSorting(this.tableDivId);
			this.load();
		},
		orderBy : function(sortColumn, prefix){//字段排序
			var this_ = this;
			var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableDivId));
			var orderStyle = $obj.attr("class");
			$(".sorting_desc", $("#"+this_.tableDivId)).removeClass("sorting_desc").addClass("sorting");
			$(".sorting_asc", $("#"+this_.tableDivId)).removeClass("sorting_asc").addClass("sorting");
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
			this_.load(currentPage, sortColumn, orderType);
		},
		searchreset : function(){
			$("#keyword_search", $("#"+this.id)).val("");
			$("#sqgw_search", $("#"+this.id)).val("");
			$("#zt_search", $("#"+this.id)).val(3);
			$("#yxq_search", $("#"+this.id)).removeAttr("checked");
			
			$("#divSearch", $("#"+this.id)).find("input").each(function() {
				$(this).attr("value", "");
			});

			$("#divSearch", $("#"+this.id)).find("textarea").each(function(){
				$(this).val("");
			});

			$("#divSearch", $("#"+this.id)).find("select").each(function() {
				$(this).attr("value", "");
			});
			
			$("#dprtcode_search", $("#"+this.id)).val(userJgdm);
			
			this.search();
			
		},
		/**
		 * 执行请求
		 * url 请求路径
		 * param 请求参数
		 * async true异步，false同步
		 * modal 遮罩,防重复提交
		 * callback 回调函数
		 */
		performAction : function(url, param, async, modal, callback){
			var this_ = this;
			startWait(modal);
			AjaxUtil.ajax({
				url:url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				async: async,
				modal:modal,
				success:function(data){
					finishWait(modal);
					if(typeof(callback)=="function"){
						callback(data); 
					}
				}
			});
		},
		
		//打印
		print:function(id){
			var url=basePath+"/quality/post/application/print?id=" + id + "&dprtcode="+$("#dprtcode_search").val();
	    	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
					'PDF','width:50%;height:50%;top:100;left:100;');	
		},
}

