var dataList = {};
	
	$(function(){
		// 初始化导航
		Navigation(menuCode,"","");
		
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					refreshScrapData();//调用主列表页查询
				}
			}
		});
		
		// 开始的加载默认的内容 
		goPage(1,"auto","desc");
		// 初始化时间控件
		initDateWidget();
		authHeight();
		$(window).resize(function() {
			 authHeight();
		});
	});
	
	var pagination = {};
	/**
	 * 编码页面查询条件和分页
	 * @returns
	 */
	function encodePageParam(){
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword_search").val());
		params.bflx = $.trim($("#bflx").val());
		params.zt = $("#zt").val();
		params.dprtcode = $("#dprtcode").val();
		pageParam.params = params;
		pageParam.pagination = pagination;
		return Base64.encode(JSON.stringify(pageParam));
	}

	/**
	 * 解码页面查询条件和分页 并加载数据
	 * @returns
	 */
	function decodePageParam(){
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#dprtcode").val(params.dprtcode);
			$("#keyword_search").val(params.keyword);
		    $("#bflx").val(params.bflx);
		    $("#zt").val(params.zt);
			
			if(pageParamJson.pagination){
				goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				goPage(1,"auto","desc");
			}
		}catch(e){
			goPage(1,"auto","desc");
		}
	}

	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		$("#scrapLoadingList_div,#selectData").hide();
		authHeight();
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.pagination = pagination;
		searchParam.id = id;
		url = basePath+"/aerialmaterial/scrap/queryapplypage";
		startWait();
		AjaxUtil.ajax({
			 url:url,
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[3,4,6,8],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"13\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'scrap_table'});
	      }
	    }); 
		
	 }
	
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {
				 paramsMap : {
					 keyword : $.trim($("#keyword_search").val()),
					 bflx : $.trim($("#bflx").val()),
					 zt : $.trim($("#zt").val()),
					 dprtcode : $.trim($("#dprtcode").val()),
					 scrapPage : 'audit'
				 }
		 };
		 var sqsj = $("#sqrq_search").val();
		 if(null != sqsj && "" != sqsj){
			 var sqsjBegin = sqsj.substring(0,10);
			 var sqsjEnd = sqsj.substring(13,23);
			 searchParam.paramsMap.sqsjBegin = sqsjBegin;
			 searchParam.paramsMap.sqsjEnd = sqsjEnd;
		 }
		 var spsj = $("#sprq_search").val();
		 if(null != spsj && "" != spsj){
			 var spsjBegin = spsj.substring(0,10);
			 var spsjEnd = spsj.substring(13,23);
			 searchParam.paramsMap.spsjBegin = spsjBegin;
			 searchParam.paramsMap.spsjEnd = spsjEnd;
		 }
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 
		 var htmlContent = '';
		 $.each(list,function(index,row){
			 dataList[row.id] = row;
			   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"' onclick='showScrapLoadingList(\""+row.id+"\",\""+row.bfdh+"\",this)' style='cursor:pointer'>",
			                   "<td class='fixed-column' style='text-align: center'>",
			                   (row.zt==2?"<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:scrap:audit:audit' onClick='goToExecuteAuditPage(\""+row.id+"\")' title='审核 Review'></i>&nbsp;&nbsp;":""),
			                   (row.zt==2?"<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:scrap:audit:close' onClick='closed(\""+row.id+"\",\""+row.bfdh+"\")' title='指定结束 Close'></i>":""),
			                   (row.zt==3?"<i class='icon-reply-all color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:scrap:audit:revoke' onClick='revoke(\""+row.id+"\",\""+row.bfdh+"\")' title='撤销 Revoke'></i>":""),
			                   "</td>",
			                   "<td name='content' style='text-align: center' title='"+DicAndEnumUtil.getEnumName('stockTypeEnum',row.bflx)+"'>"+DicAndEnumUtil.getEnumName('stockTypeEnum',row.bflx)+"</td>",
			                   "<td name='content' style='text-align: center' title='"+StringUtil.escapeStr(row.bfdh)+"'><a href='javascript:void(0);' onclick='goToViewPage(\""+row.id+"\")' name='content'>"+StringUtil.escapeStr(row.bfdh)+"</a></td>",
			                   "<td name='content' style='text-align: left' title='"+StringUtil.escapeStr(row.bfyy)+"'>"+StringUtil.escapeStr(row.bfyy)+"</td>",
			                   ((row.zt==9)?"<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('scrapStatusEnum',row.zt)+"'><a href='#' onClick='endDetail(\""+row.id+"\")'>"+DicAndEnumUtil.getEnumName('scrapStatusEnum',row.zt)+"</a></td>"
					                    :"<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('scrapStatusEnum',row.zt)+"'>"+DicAndEnumUtil.getEnumName('scrapStatusEnum',row.zt)+"</td>"),	
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.bfr==null?"":row.bfr.displayName)+"'>"+StringUtil.escapeStr(row.bfr==null?"":row.bfr.displayName)+"</td>",
			                   "<td style='text-align: center' title='"+(row.bfsj||'')+"'>"+(row.bfsj||'')+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.spr==null?"":row.spr.displayName)+"'>"+StringUtil.escapeStr(row.spr==null?"":row.spr.displayName)+"</td>",
			                   "<td style='text-align: center' title='"+(row.spsj||'')+"'>"+(row.spsj||'')+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.spyj)+"'>"+StringUtil.escapeStr(row.spyj)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.zdr==null?"":row.zdr.displayName)+"'>"+StringUtil.escapeStr(row.zdr==null?"":row.zdr.displayName)+"</td>",
			                   "<td style='text-align: center' title='"+(row.zdsj||'')+"'>"+(row.zdsj||'')+"</td>",
			                   "<td style='text-align: left' title='"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"'>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>",
			                   "</tr>"
			                   ].join("");
		 });
		 $("#list").empty();
		 $("#list").html(htmlContent);
		 refreshPermission();
	 }
	 
	
	/**
	 * 加载附件
	 * @param id
	 */
	function loadAttachments(id) {
		AjaxUtil.ajax({
					url : basePath + "/common/loadAttachments",
					type : "post",
					data : {
						mainid : id
					},
					success : function(data) {
						if (data.success) {
							var attachments = data.attachments;
							var len = data.attachments.length;
							if (len > 0) {
								$('#profileList>tr').remove();
								var trHtml = '';
								for (var i = 0; i < len; i++) {
									trHtml = trHtml
											+ '<tr bgcolor="#f9f9f9" id=\''
											+ attachments[i].uuid + '\' key=\''
											+ attachments[i].id + '\' >';
									trHtml = trHtml + '<td class="text-center">' +(i+1) + '</td>';
									trHtml = trHtml
											+ '<td class="text-left"> <a class="cursor-pointer" onclick="downloadfile(\''
											+ attachments[i].id + '\')" >'
											+ StringUtil.escapeStr(attachments[i].wbwjm) + '</a></td>';
									trHtml = trHtml + '<td class="text-left">'
											+ attachments[i].wjdxStr + ' </td>';
									trHtml = trHtml + '<td class="text-left">'
											+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
									trHtml = trHtml + '<td>' + attachments[i].czsj
											+ '</td>';
									trHtml = trHtml
											+ '<input type="hidden" name="relativePath" value=\''
											+ attachments[i].relativePath + '\'/>';
									trHtml = trHtml + '</tr>';
								}
								$('#profileList').append(trHtml);
							}else{
								$('#profileList').html('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
							}
						}
					}
				});
	}

function downloadfile(id){
	//window.open(basePath + "/common/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}


	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		if(obj == "sqr"){
			obj = "c.username";
		}
		if(obj == "spr"){
			obj = "b.username";
		}
		if(obj == "whr"){
			obj = "b.username";
		}
		goPage(currentPage,obj,orderStyle.split("_")[1]);
	}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
		
	//搜索条件重置
	function searchreset(){
		var dprtId = $.trim($("#dprtId").val());
		$("#keyword_search").val("");
		
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
		$("#dprtcode").val(dprtId);
		$("#bflx").val("");
		$("#zt").val("");
	}
 
	//搜索条件显示与隐藏 
	function search() {
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}
	}
	
	/**
	 * 刷新报废数据
	 */
	function refreshScrapData(){
		goPage(1,"auto","desc");
	}
	
	/**
	 * 初始化时间控件
	 */
	function initDateWidget(){
		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",function() {
			$(this).next().focus();
		});
	}
	
	/**
	 * 显示报废部件清单
	 * @param id
	 */
	function showScrapLoadingList(id,bfdh,tr){
		if(getEvent().target.tagName == "I"){
			return;
		}
		$("#list>tr").removeClass("bg_tr");
		$(tr).addClass("bg_tr");
		$("#current_id").val(id);
		$("#scrapLoadingList_div,#selectData").show();
		$("#selectData").html("已选择报废单"+bfdh);
		// 加载附件
		loadAttachments(id);
		getScrapLoadingList();
		authHeight();
	}
	
	function getEvent(){
	    if(window.event){
	        return window.event;
	    }
	    var f = arguments.callee.caller;
	    do{
	        var e = f.arguments[0];
	        if(e && (e.constructor === Event || e.constructor===MouseEvent || e.constructor===KeyboardEvent)){
	             return e;
	        }
	    }while(f=f.caller);
	}
	
	/**
	 * 获取报废部件清单
	 */
	function getScrapLoadingList(){
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/scrap/queryscrapdetail",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify({
			   id : $("#current_id").val()
		   }),
		   success:function(data){
		    finishWait();
		    var htmlContent = '';
		    if(data.length > 0){
		    	$.each(data,function(index,row){
					   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.hCMainData.bjh)+"'>"+StringUtil.escapeStr(row.hCMainData.bjh)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.hCMainData.ywms)+"'>"+StringUtil.escapeStr(row.hCMainData.ywms)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.hCMainData.zwms)+"'>"+StringUtil.escapeStr(row.hCMainData.zwms)+"</td>",
					                   "<td style='text-align: right' title='"+(row.bfsl||0)+"'>"+(row.bfsl||0)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.bfck)+"'>"+StringUtil.escapeStr(row.bfck)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.bfkw)+"'>"+StringUtil.escapeStr(row.bfkw)+"</td>",
					                   "<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('materialPriceEnum',row.hCMainData.hcjz)+"'>"+DicAndEnumUtil.getEnumName('materialPriceEnum',row.hCMainData.hcjz)+"</td>",
					                   "<td style='text-align: right' title='"+StringUtil.escapeStr(row.kccb)+"'>"+StringUtil.escapeStr(row.kccb)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.materialHistory.ckmc)+"'>"+StringUtil.escapeStr(row.materialHistory.ckmc)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.yskw)+"'>"+StringUtil.escapeStr(row.yskw)+"</td>",
					                   "<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.hCMainData.gljb)+"'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.hCMainData.gljb)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>",
					                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>",
					                   "<td style='text-align: center' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hCMainData.hclx)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hCMainData.hclx)+"</td>",					                   
					               /*    "<td style='text-align: right' title='"+(row.kcsl||0)+"'>"+(row.kcsl||0)+"</td>",
					                   "<td style='text-align: right' title='"+(row.kysl||0)+"'>"+(row.kysl||0)+"</td>",*/
					                   "</tr>"
					                   ].join("");
				 });
		    }else{
		    	htmlContent = "<tr><td colspan=\"15\" class='text-center'>暂无数据 No data.</td></tr>";
		    }
			$("#scrapList").empty();
			$("#scrapList").html(htmlContent);
	      }
	    }); 
	 }
	
	/**
	 * 跳转到新增页面
	 */
	function goToAddPage(){
		window.location = basePath+"/aerialmaterial/scrap/add?pageParam="+encodePageParam();
	}
	
	/**
	 * 跳转到审核页面
	 */
	function goToExecuteAuditPage(id){
		window.location = basePath+"/aerialmaterial/scrap/executeaudit/"+id+"?pageParam="+encodePageParam();
	}
	
	/**
	 * 跳转到查看页面
	 * @param id
	 */
	function goToViewPage(id){
		window.open(basePath + "/aerialmaterial/scrap/view/"+id);
	}
	
	/**
	 * 指定结束
	 */
	function closed(id, bfdh){
		AssignEndModal.show({
			chinaHead:'报废单号',//单号中文名称
			englishHead:'Scrap No.',//单号英文名称
			edit:true,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
			jsdh:bfdh,//指定结束单号'
			showType:false,
			callback: function(data){//回调函数
				if(null != data && data != ''){
					startWait();
					$("#AssignEndModal").modal("hide");
					AjaxUtil.ajax({
						url:basePath+"/aerialmaterial/scrap/close",
						type:"post",
						data:JSON.stringify({
							id : id,
							zdjsyy : data.gbyy
						}),
						dataType:"json",
						contentType: "application/json;charset=utf-8",
						success:function(id){
							finishWait();
							AlertUtil.showMessage("指定结束成功!");
							refreshScrapData();
						}
					});
				}
			}
		});
	}
	
	/**
	 * 撤销
	 */
	function revoke(id, bfdh){
		AlertUtil.showConfirmMessage("是否确认撤销报废单号为"+bfdh+"数据？",{callback:function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/scrap/revoke",
				type:"post",
				data:JSON.stringify({
					id : id
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(id){
					finishWait();
					AlertUtil.showMessage("撤销成功!");
					refreshScrapData();
				}
			});
		}});
	}

	function endDetail(id){
		var row = dataList[id];
		AssignEndModal.show({
			chinaHead:'报废单号',//单号中文名称
			englishHead:'Scrap No.',//单号英文名称
			edit:false,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
			jsdh:row.bfdh,//指定结束单号			
			jsr:StringUtil.escapeStr(row.jsr.displayName),//指定结束人
			jssj:row.jssj,//指定结束时间
			jsyy:row.jsyy,//指定结束原因
			
		});
	}
	
	//设置高度
	function authHeight(){
	    //头部高度
	    var headerHeight = $('.header').outerHeight();
	    var headerDownHeight = $('.header-down').outerHeight();
	    //window高度
	    var windowHeight = $(window).height()
	    //尾部的高度
	    var footerHeight = $('.footer').outerHeight()||0;
	    //分页的高度
	    var paginationHeight = $('.page-height:visible').outerHeight()||0;
	    //panelheader的高度
	    var panelHeadingHeight = $('.panel-heading').outerHeight();
	    //调整高度
	    var adjustHeight = $("#adjustHeight").val()||0;
	    //搜索框的高度
	    var searchContent=$(".searchContent").outerHeight()||0;
	    //body的高度
	   
	    
	   //情景1：table-tab
	    //修改1：在页面div class='page-content'上加class='page-content table-tab-type'
	    //修改2：给上方表格的父div 添加class='table-tab-type_table'
	    //修改3：给tab中table的父div 添加class='tab-second-height'
	    
	    
	    if($(".table-tab-type").length>0){
	    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
	    	//表格的高度
	        var tableHeight = bodyHeight - searchContent - paginationHeight  - 70 - adjustHeight;
	      //谷歌的兼容性问题
	        if(navigator.userAgent.indexOf("Chrome") > -1){
	        	tableHeight -= 10;
	        }
	        //隐藏的div是否显示
	        if($("#scrapLoadingList_div").css("display")=='block'){
	        	//表格的高度
	        	var actualTableHeight= tableHeight*0.5;
	        	//表格的高度
	        	$(".table-tab-type_table").attr('style', 'height:' + actualTableHeight+ 'px !important;overflow-x: auto;');
	        	// tab header 的高度
	        	var table_tab=$("#scrapLoadingList_div .nav-tabs").outerHeight()||0;
	        	//选中的提示信息
	        	var selectCourse=$("#selectCourse").outerHeight()||0;
	        	//表格的高度
	        	var actualTableOuterHeight=$(".table-tab-type_table").outerHeight()||0;
	        	// tabcontent
	        	var tabcontent=tableHeight-actualTableOuterHeight-table_tab-selectCourse-20;
	        	//如果下方的tab是引用课件信息。
	        	if($(".tab-second-height").length>0){
	        		//是否存在课件上传
	        		var fileHeadheigth=$("#fileHead").outerHeight()||0;
	        		//下方tab的高度
	        		var tab_second_height =tabcontent-fileHeadheigth-20;
	        		//给下方tab中table 的父div 的高度进行赋值
	        		$(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
	        	}
	        	
	        }else{
	        	//给表格的父div的高度进行赋值
	        	 $(".table-tab-type_table").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
	        }
	        
	    }
	}