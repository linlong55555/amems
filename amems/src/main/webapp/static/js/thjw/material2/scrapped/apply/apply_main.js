$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		decodePageParam();
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
		
		//执行待办
	    if(todo_ywid){
	    	openEditWin(todo_ywid);
	    	todo_ywid = null;
	    }
		
	})
	
	function decodePageParam() {
		try {
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#keyword_search").val(params.keyword);
			$("#zzjg").val(params.zzjg);
			if (pageParamJson.pagination) {
				goPage(pageParamJson.pagination.page,
						pageParamJson.pagination.sort,
						pageParamJson.pagination.order);
			} else {
				goPage(1, "auto", "desc");
			}
		} catch (e) {
			goPage(1, "auto", "desc");
		}
	}
	function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
		var obj = {};
		obj = gatherSearchParam();
		if(!obj.paramsMap.show){
			$("#applyList").empty();
			$("#pagination_list").empty();
			$("#applyList").append("<tr class='text-center'><td colspan=\"9\">暂无数据 No data.</td></tr>");
			return false;
		}
		pagination = {
			page : pageNumber,
			sort : sortType,
			order : sequence,
			rows : 20
		};
		obj.pagination = pagination;
		if (id != "") {
			obj.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/material/scrapped/apply/getScrappedApplyList",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(obj),
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					appendContentHtml(data.rows);
					var page_ = new Pagination({
						renderTo : "pagination_list",
						data : data,
						sortColumn : sortType,
						orderType : sequence,
						extParams : {},
						goPage : function(a, b, c) {
							AjaxGetDatasWithSearch(a, b, c);
						}

					});
					// 标记关键字
					signByKeyword($("#keyword_search").val(), [ 3, 6 ],"#applyList tr td");
				} else {
					$("#applyList").empty();
					$("#pagination_list").empty();
					$("#applyList").append("<tr class='text-center'><td colspan=\"9\">暂无数据 No data.</td></tr>");
				}
				hideBottom();
				new Sticky({
					tableId : 'packing_list_table'
				});
			}
		});
	
	}
	function gatherSearchParam() {
		var searchParam = {};
		searchParam.keyword = $.trim($("#keyword_search").val());// 关键字查询
		searchParam.dprtcode = $.trim($("#zzjg").val());
		var paramsMap={};
		var bfrq = $.trim($("#bfrq_search").val());
		if (null != bfrq && "" != bfrq) {
			var bfrqBegin = bfrq.substring(0, 10) + " 00:00:00";
			var bfrqEnd = bfrq.substring(13, 23) + " 23:59:59";
			paramsMap.bfrqBegin = bfrqBegin;
			paramsMap.bfrqEnd = bfrqEnd;
		}
		var obj = document.getElementsByName("jy");
	    var check_val = [];
		for (var i = 0; i < obj.length; i++) {
			if(obj[i].checked){
				if(obj[i].value == 1){
	        		check_val.push(1);
	        		check_val.push(5);
	        	}
		        if(obj[i].value == 2){
	        		check_val.push(2);
	        	}
		        if(obj[i].value == 3){
		        	check_val.push(3);
		        	check_val.push(9);
		        	check_val.push(10);
		        }
			}
		}
	    if(check_val.length>0){
	    	paramsMap.ztList = check_val;	
	    	paramsMap.show = true;
	    }else{
	    	paramsMap.show = false;
	    }
		searchParam.paramsMap=paramsMap;
		return searchParam;
	}
	function encodePageParam() {
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword_search").val());
		params.zzjg = $.trim($("#zzjg").val());
		pageParam.params = params;
		pageParam.pagination = pagination;
		return Base64.encode(JSON.stringify(pageParam));
	}
	function appendContentHtml(list) {
		var htmlContent = '';
		$.each(list,function(index, row) {
			htmlContent += "<tr  id='"+row.id+"'  onclick=\"showHiddenContent(this)\">";
			htmlContent += "<td class='text-center fixed-column'>";	
			if(row.zt ==1 || row.zt == 5){
				htmlContent += "<i class='icon-edit color-blue cursor-pointer '  onClick=\"edit('"
						+ row.id + "',event)\" title='编辑 Edit'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='produce:workpackage:main:03' onClick=\"invalid('"
						+ row.id +"','" + row.zt + "',event)\" title='删除 Delete '></i>&nbsp;&nbsp;";
			} 
			if( row.zt == 2 || row.zt == 3){
				htmlContent += "<i class='iconnew-end color-blue cursor-pointer ' bh='"+row.bfdh+"'  onClick=\"endClose('"
						+ row.id + "',event)\" title='指定结束 End'></i>&nbsp;&nbsp;";
			}
			htmlContent += "</td>";
			htmlContent += "<td class='text-center' title='"+formatUndefine(DicAndEnumUtil.getEnumName('applyStatusEnum', row.zt))+"' >"+ formatUndefine(DicAndEnumUtil.getEnumName('applyStatusEnum', row.zt))+ "</td>";	
			htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.bfdh)+"' ><a href='#' onClick=\"viewApply('"
			+ row.id + "',event)\">"+ StringUtil.escapeStr(formatUndefine(row.bfdh))+ "</a></td>";
			htmlContent += "<td  class='text-center ' title='"+ StringUtil.escapeStr(formatUndefine(row.bfrq?row.bfrq.substring(0,10):""))+ "'>"
					+ StringUtil.escapeStr(formatUndefine(row.bfrq?row.bfrq.substring(0,10):""))+ "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.sqr?row.sqr.username:"")+" "+StringUtil.escapeStr(row.sqr?row.sqr.realname:"") + "' >"+ StringUtil.escapeStr(row.sqr?row.sqr.username:"")+" "+StringUtil.escapeStr(row.sqr?row.sqr.realname:"") + "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.sm) + "'>"+ StringUtil.escapeStr(row.sm)+ "</td>";		
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";		
			if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
				htmlContent += '<i qtid="'+row.id+'" class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';		
			}
			htmlContent += "</td>";
			htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.shr==null?"":row.shr.username)+" "+StringUtil.escapeStr(row.shr==null?"":row.shr.realname)+ "'>"+ StringUtil.escapeStr(row.shr==null?"":row.shr.username)+" "+StringUtil.escapeStr(row.shr==null?"":row.shr.realname)+ "</td>";
			htmlContent += "<td class='text-center'title='"+formatUndefine(row.spsj)+"'>"+ formatUndefine(row.spsj) + "</td>";
			htmlContent += "</tr>";
		});
		$("#applyList").empty();
		$("#applyList").html(htmlContent);
		initWebuiPopover();
		refreshPermission();
	}
	
	function initWebuiPopover(){//初始化
		WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
			return getHistoryAttachmentList(obj);
		});
		$("#packing_list_table_div").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	}
	function getHistoryAttachmentList(obj){//获取历史附件列表
		var jsonData = [
		   	         {mainid : $(obj).attr('qtid'), type : '附件'}
		   	    ];
		 return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	}
	
	// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	function goPage(pageNumber, sortType, sequence) {
		AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	}
	// 信息检索
	function searchRevision() {
		TableUtil.resetTableSorting("packing_list_table");
		goPage(1, "auto", "desc");
	}
	
	function searchreset(){
		$("#bfrq_search").val("");
		$("#keyword_search").val("");
		$("#zzjg").val(userJgdm);
		var zt = document.getElementsByName('jy');
		for (var i = 0; i < zt.length; i++) {
			if(zt[i].value=='1'){
				zt[i].checked = 'checked';
			}else{
				zt[i].checked = false;
			}	
		}
	}
	function orderBy(obj) {
		var this_ = this;
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
		var currentPage = $("#pagination_list li[class='active']").text();
		goPage(currentPage, obj, orderStyle.split("_")[1]);
	}
	
	function moreSearch(){
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
    }
	/**
	 *点击行选择 
	 */
	function showHiddenContent(this_){
	 	var id = $(this_).attr("id");
	 	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
	 	if($("#bottom_hidden_content").css("display")=='block'){
	 		$("#bottom_hidden_content").slideUp(100);
	 	}
	 	$("#bottom_hidden_content").slideDown(100);
	 	
		var isBottomShown = false;
		if($(".displayContent").is(":visible")){
			isBottomShown = true;
		}
	 	TableUtil.showDisplayContent();
	 	if($("#hideIcon").length==0){
	 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#pagination_list .fenye"));
		}
	 	loadDetail(id);
	}
	
	function applyOpen(){
		apply_open.init({
			dprtcode:userJgdm,//组织机构
			workpackageId:'',//id
			operation:true,//操作类型
			modalName:'新增报废登记',//模态框中文名称
			modalEname:'Add Discarded registration',//模态框英文名称
			callback:function(data){
			var message = data.zt ==1?"保存成功!":data.zt==2?"提交成功!":"";
			doRequest(data,message,basePath + "/material/scrapped/apply/add","apply_open_modal");
			}
		})
	}
	
	function edit(id,e){
		 e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
		    openEditWin(id);
	}
	
	function openEditWin(id){
		getDataById(id,"edit",function(obj){
			if(!(obj.zt == 1 || obj.zt == 5 )){
				AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
				return;
			}
			apply_open.init({
				dprtcode:obj.dprtcode,//组织机构
				id:obj.id,//id
				obj:obj,
				operation:true,//操作类型
				modalName:'编辑报废登记',//模态框中文名称
				modalEname:'Edit Discarded registration',//模态框英文名称
				callback:function(data){
				var message = data.zt ==1?"保存成功!":data.zt==2?"提交成功!":"";
				doRequest(data,message,basePath + "/material/scrapped/apply/edit","apply_open_modal");
				}
			})
		})
	}
	
	function doRequest(data,message,url,modal){
		startWait($("#"+modal));
		AjaxUtil.ajax({
			url : url,
			type : "post",
			data : JSON.stringify(data),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			async : false,
			modal : $("#"+modal),
			success : function(data) {
				id = data;				
				pageParam=encodePageParam();
				AlertUtil.showMessage(message, decodePageParam());
				$("#"+modal).modal("hide");
				finishWait($("#"+modal));
				refreshPermission();
			}
		});
	}
	
	function getDataById(id,option,obj){
		var this_ = this;
		var param={};
		param.id=id;
		var paramsMap={};
		paramsMap = {
				"option" : option 
			};
		param.paramsMap=paramsMap;
		AjaxUtil.ajax({
			url : basePath + "/material/scrapped/apply/getRecord",
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
	}
	function viewApply(id,e){
		e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	    window.open(basePath + "/material/scrapped/apply/view?id=" + id);
	}
	//删除
	function invalid(id,zt,e){
		e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	    getDataById(id,"del",function(obj){
			if(!(obj.zt == 1 || obj.zt == 5 )){
				AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
				return;
			}
		var param = {};
		param.id = id;
		AlertUtil.showConfirmMessage("确定要删除吗？", {
			callback : function() {
				AjaxUtil.ajax({
					url : basePath + "/material/scrapped/apply/delete",
					type : "post",
					async : false,
					contentType : "application/json;charset=utf-8",
					data : JSON.stringify(param),
					dataType : "json",
					success : function(data) {
						pageParam=encodePageParam();
						AlertUtil.showMessage('删除成功！', decodePageParam());
						refreshPermission();
					}
				});
			}
		});
	    })
	}
	
	function endClose(id,e){
		e = e || window.event;  
	    if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    }
	    getDataById(id,"close",function(obj){
			if(!(obj.zt == 2 || obj.zt == 3 )){
				AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
				return;
			}
			var pgdh=$(e).attr("bh"); //评估单号
			var gbyy=$(e).attr("zdjsyy"); //关闭原因
			var gbrq=$(e).attr("zdjsrq"); //关闭日期
			var gbrid=$(e).attr("zdjsr");//关闭人
			var zt=$(e).attr("zt"); //状态
			AssignEndModal.show({
				chinaHead:'报废单号',//单号中文名称
				englishHead:'Scrap No.',//单号英文名称
				edit:true,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
				titleName:"指定结束",
				titleEname:"End",
				jsdh:obj.bfdh,//指定结束单号
				jsr:gbrid,//指定结束人
				jssj:gbrq,//指定结束时间
				jsyy:gbyy,//指定结束原因
				zt:2,//状态
				showType:false,
				callback: function(data){//回调函数			
					var message = '关闭成功!';
					var param={};
					param.id = id;
					param.zdjsyy=data.gbyy;
					param.bfdh = obj.bfdh;
					AlertUtil.showConfirmMessage("确定要关闭吗？", {
						callback : function() {
							doRequest(param,"关闭成功!",basePath + "/material/scrapped/apply/doClose","AssignEndModal");
						}
					});
				}
			});
		
	    })
	}
	
	function loadDetail(id){
		var obj = {};
		obj.id = id;
		AjaxUtil.ajax({
			url : basePath + "/material/scrapped/apply/getScrappedDetailList",
			type : "post",
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			data : JSON.stringify(obj),
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					appendDetail(data.rows);
				} else {
					$("#bottom_hidden_content_tbody").append("<tr class='text-center'><td colspan=\"12\">暂无数据 No data.</td></tr>");
				}
			}
		});
	}
 
	function appendDetail(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr id='"+row.KCID+"'>";
			htmlContent +="<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";	
			htmlContent +="<td style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('destroyEnum',row.BFZT)+"'>"+DicAndEnumUtil.getEnumName('destroyEnum',row.BFZT)+"</td>";	
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BJH)+"\n"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.BJH)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"</p></td>";//部件名称
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.PCH)+"\n"+StringUtil.escapeStr(row.SN)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.PCH)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.SN)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.GG)+"\n"+StringUtil.escapeStr(row.XH)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.GG)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.XH)+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"\n"+StringUtil.escapeStr(formatUndefine(row.KWH))+"'><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.KWH))+"</p></td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"'>"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.CQBH)+"'>"+StringUtil.escapeStr(row.CQBH)+"</td>";//产权 
			var zcb = '';
			var zjz = '';
			if(formatUndefine(row.ZCB) != ''){
				zcb = formatUndefine(row.ZCB).toFixed(2)+" "+formatUndefine(row.BIZ);
			}
			if(formatUndefine(row.ZJZ) != ''){
				zjz = formatUndefine(row.ZJZ).toFixed(2)+" "+formatUndefine(row.JZBZ);
			}
		 
		    htmlContent += "<td style='text-align:left;vertical-align:middle;' name='kcjz' title='"+formatUndefine(zcb)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(zcb)+"</p></td>"; 
		    if(formatUndefine(row.HJSM) =='' && formatUndefine(row.SYTS)==''){
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"' >"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</td>";
			}else{
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"\n"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</p><p  class='margin-bottom-0 '>"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"</p></td>";
			}
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.XHR)+"\n"+StringUtil.escapeStr(row.XHSJ?row.XHSJ.substring(0,10):"")+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.XHR)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.XHSJ?row.XHSJ.substring(0,10):"")+"</p></td>"; 
			htmlContent += "</tr>";  
		    
		});
		$("#bottom_hidden_content_tbody").empty();
		$("#bottom_hidden_content_tbody").append(htmlContent);
	
	}
	
	function hideBottom(){
		$("#hideIcon").remove();
		TableUtil.hideDisplayContent();
	}