	
	
	var shlxMap = {
			1 : "采购",
			2 : "送修",
			3 : "借入",
			4 : "借出归还",
			99 : "其它",
	};
	var ztMap = {
			1 : "保存",
			2 : "提交",
			8 : "作废",
			9 : "关闭",
			10 : "完成",
			11 : "撤销"
	};
	var jyjgMap = {
			1 : "合格",
			2 : "不合格",
			3 : "让步接收"
	}
	
	$(document).ready(function(){
		Navigation(menuCode);
		refreshPermission();
		decodePageParam();
		$('input[name=date-range-picker]').daterangepicker().prev().on("click", function() {
			$(this).next().focus();
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
		params.dprtcode = $.trim($("#dprtcode").val());
		params.keyword = $.trim($("#keyword_search").val());
		params.flightdate = $("#flightDate_search").val();
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
		    $("#flightDate_search").val(params.flightdate);
			
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
		var searchParam = gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/receipt/queryPage",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   success:function(data){
				    finishWait();
				    if(data.total>0){
				    	appendContentHtml(data.rows);
				    	
				    	 new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(a,b,c){
								AjaxGetDatasWithSearch(a,b,c);
							}
						});  
				    	
				       
				    	FormatUtil.removeNullOrUndefined();
						// 标记关键字
				        signByKeyword($("#keyword_search").val(),[3,5,9,10]);
						   
					   } else {
					
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'receipt_main_table'});
		      }
		    }); 
		 
		
	 }
	 var choseAllIds=[];
	 var choses=[];
	 function appendContentHtml(list){
		 choseAllIds=[];
		 choses=[];
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   choseAllIds.push(row.id);
			   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
			                   "<td class='fixed-column' style='text-align: center'>",
			                   (row.zt==1?"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:receipt:edit' onClick='goToEditPage(\""+row.id+"\")' title='修改 Edit'></i>&nbsp;&nbsp;":""),
			                   (row.zt==1?"<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:receipt:scrap' onClick='scrap(\""+row.id+"\",\""+row.shdh+"\")' title='作废 Scrap'></i>":""),
			                   (row.zt==2?"<i class='icon-reply-all color-blue cursor-pointer checkPermission' permissioncode='aerialmaterial:receipt:revoke' onClick='revoke(\""+row.id+"\",\""+row.shdh+"\")' title='撤销 Revoke'></i>":""),
			                   "</td>",
			                   "<td name='content' style='text-align: center' title='"+(index+1)+"'>"+(index+1)+"</td>",
			                   "<td style='text-align: center' title='"+StringUtil.escapeStr(row.shdh)+"'><a href='javascript:void(0);' onclick='goToViewPage(\""+row.id+"\")' name='content'>"+StringUtil.escapeStr(row.shdh)+"</a></td>",
			                   "<td name='content' style='text-align: left' title='"+shlxMap[row.shlx]+"'>"+shlxMap[row.shlx]+"</td>",
			                   "<td name='content' style='text-align: left' title='"+StringUtil.escapeStr(row.xgdjbh)+"'>"+StringUtil.escapeStr(row.xgdjbh)+"</td>",
			                   "<td name='content' style='text-align: left' title='"+StringUtil.escapeStr(row.fhdw)+"'>"+StringUtil.escapeStr(row.fhdw)+"</td>",
			                   "<td style='text-align: center' title='"+ztMap[row.zt]+"'>"+ztMap[row.zt]+"</td>",
			                   "<td style='text-align: left'>"+buildZjqk(row.inspections)+"</td>",
			                   "<td style='text-align: left' >"+buildThqk(row.returnedPurchaseList)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.shr.displayName)+"'>"+StringUtil.escapeStr(row.shr.displayName)+"</td>",
			                   "<td style='text-align: center' title='"+(row.shrq||'').substr(0,10)+"'>"+(row.shrq||'').substr(0,10)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.zdr.displayName)+"'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>",
			                   "<td style='text-align: center' title='"+row.zdsj+"'>"+row.zdsj+"</td>",
			                   "<td style='text-align: left' title='"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"'>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>",
			                   "</tr>"
			                   ].join("");
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
		   
		   function buildZjqk(inspections){
			   if(inspections == null || inspections.length == 0){
				   return "";
			   }
			   var html = "";
			   for(var i = 0; i <inspections.length; i++){
				   choses.push(inspections[i].id);
				    if (i == 1) {
				    	html = html
								+ "　<i class='icon-caret-down' id='"
								+ inspections[i].id
								+ "icon' onclick=switchZjqk('"
								+ inspections[i].id + "')></i><div id='"
								+ inspections[i].id
								+ "' style='display:none'>";
					}

				    html += "<a href='javascript:void(0);' onclick=\"ToJydView('"+inspections[i].id+"')\" >"+StringUtil.escapeStr(inspections[i].jydh+"("+jyjgMap[inspections[i].jyjg]+")")+"</a>";
					if (i != 0) {
						html += "<br>";

					}
					if (i != 0 && i == inspections.length - 1) {
						html += "</div>";
					}
			   }
			   return html;
		   }
		   var thdhList=[];
		   function buildThqk(list){
			   if(list == null || list.length == 0){
				   return "";
			   }
			   var html = "";
			   thdhList=[];
			   for(var i = 0; i <list.length; i++){
				   if($.inArray(list[i].thdh, thdhList)!=-1){
					   
				   }else{
					   thdhList.push(list[i].thdh);
				    if (i == 1) {
				    	html = html
								+ "　<i class='icon-caret-down' id='"
								+ list[i].shdid
								+ "icon' onclick=switchThqk('"
								+ list[i].shdid + "')></i><div id='"
								+ list[i].shdid
								+ "' style='display:none'>";
					}

				    html +="<a href='javascript:void(0);' onclick=\"ToThdView('"+list[i].id+"')\" >"+StringUtil.escapeStr(list[i].thdh)+"</a>";
					if (i != 0) {
						html += "<br>";

					}
					if (i != 0 && i == list.length - 1) {
						html += "</div>";
					}
				   }
			   }
			   return html;
		   }
		   
	 }

		//字段排序
		function orderBy(obj) {
			var orderStyle = $("#" + obj + "_order").attr("class");
			$("th[id$=_order]").each(function() { //重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
			});
			$("#" + obj + "_" + "order").removeClass("sorting");
			var orderType = "asc";
			if (orderStyle.indexOf("sorting_asc") >= 0) {
				$("#" + obj + "_" + "order").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$("#" + obj + "_" + "order").addClass("sorting_asc");
				orderType = "asc";
			}
			orderStyle = $("#" + obj + "_order").attr("class");
			var currentPage = $("#pagination li[class='active']").text();
			goPage(currentPage,obj, orderType);
		}
	 
	  
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	
	//信息检索
	function searchReceipt(){
		goPage(1,"auto","desc");
	}
		
	//搜索条件重置
	function searchreset(){
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
	
		$("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	
	 	$("#keyword_search").val("");
	 	$("#dprtcode").val(userJgdm);
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
   	 * 封装搜索条件
   	 * @returns
   	 */
   	function gatherSearchParam(){
   		 var searchParam = {};
   		 searchParam.keyword = $.trim($("#keyword_search").val());
   		 searchParam.shlx = $.trim($("#shlx").val());
   		 searchParam.zt = $.trim($("#zt").val());
   		 searchParam.dprtcode = $.trim($("#dprtcode").val());
   		 var shrq = $.trim($("#shrq").val());
   		 if(shrq != ''){
   			searchParam.shrqBegin = shrq.substring(0,10);
   			searchParam.shrqEnd = shrq.substring(13,23);
   		 }
   		 return searchParam;
   	}
   	
   	/**
   	 * 跳转至新增页面
   	 */
   	function goToAddPage(){
   		window.location = basePath+"/aerialmaterial/receipt/add?pageParam="+encodePageParam();
   	}
   	
   	/**
	 * 跳转到修改页面
	 * @param id
	 */
	function goToEditPage(id){
	 window.location = basePath+"/aerialmaterial/receipt/edit/"+id+"?&pageParam="+encodePageParam();
	}
	
	/**
	 * 跳转到查看页面
	 * @param id
	 */
	function goToViewPage(id){
	 window.open(basePath + "/aerialmaterial/receipt/view/"+id);
	}
	
	/**
	 * 收缩/展开质检情况
	 * @param id
	 */
	function switchZjqk(id) {
		new Sticky({tableId:'receipt_main_table'});
		if ($("#" + id).is(":hidden")) {
			$("#" + id).fadeIn(500);
			$("#" + id + 'icon').removeClass("icon-caret-down");
			$("#" + id + 'icon').addClass("icon-caret-up");
		} else {
			$("#" + id).hide();
			$("#" + id + 'icon').removeClass("icon-caret-up");
			$("#" + id + 'icon').addClass("icon-caret-down");
		}
	}
	
	function switchThqk(id) {
		new Sticky({tableId:'receipt_main_table'});
		if ($("#" + id).is(":hidden")) {
			$("#" + id).fadeIn(500);
			$("#" + id + 'icon').removeClass("icon-caret-down");
			$("#" + id + 'icon').addClass("icon-caret-up");
		} else {
			$("#" + id).hide();
			$("#" + id + 'icon').removeClass("icon-caret-up");
			$("#" + id + 'icon').addClass("icon-caret-down");
		}
	}
	
	/**
	 * 作废
	 */
	function scrap(id, shdh){
		AlertUtil.showConfirmMessage("是否确认作废收货单号为"+shdh+"数据？",{callback:function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/receipt/scrap",
				type:"post",
				data:JSON.stringify({
					id : id
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(id){
					finishWait();
					AlertUtil.showMessage("作废成功!");
					searchReceipt();
				}
			});
		}});
	}
	
	/**
	 * 撤销
	 */
	function revoke(id, shdh){
		AlertUtil.showConfirmMessage("是否确认撤销收货单号为"+shdh+"数据？",{callback:function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/receipt/revoke",
				type:"post",
				data:JSON.stringify({
					id : id
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(id){
					finishWait();
					AlertUtil.showMessage("撤销成功!");
					searchReceipt();
				}
			});
		}});
	}
		 
	function ToThdView(id){
		window.open( basePath+"/aerialmaterial/returnedpurchase/view?id=" + id);
	}
	
	function ToJydView(id){
		window.open( basePath+"/aerialmaterial/inspection/Looked?id=" + id);
	}
	function vieworhideWorkContentAll(){
		new Sticky({tableId:'receipt_main_table'});
		var obj = $("th[name='th_return']");
		var flag = obj.hasClass("downward");
		if(flag){
			obj.removeClass("downward").addClass("upward");
		}else{
			obj.removeClass("upward").addClass("downward");
		}
		 for(var i=0;i<choseAllIds.length;i++){
			 if(flag){				
				 $("#"+choseAllIds[i]).fadeIn(500);
				 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
				 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
			 }else{
				 $("#"+choseAllIds[i]).hide();
				 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
				 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
			 }
		 }
	 }
	
	function vieworhideZjqkContentAll(){
		new Sticky({tableId:'receipt_main_table'});
		var obj = $("th[name='th_zjqk']");
		var flag = obj.hasClass("downward");
		if(flag){
			obj.removeClass("downward").addClass("upward");
		}else{
			obj.removeClass("upward").addClass("downward");
		}
		for(var i=0;i<choses.length;i++){
			if(flag){				
				$("#"+choses[i]).fadeIn(500);
				$("#"+choses[i]+'icon').removeClass("icon-caret-down");
				$("#"+choses[i]+'icon').addClass("icon-caret-up");
			}else{
				$("#"+choses[i]).hide();
				$("#"+choses[i]+'icon').removeClass("icon-caret-up");
				$("#"+choses[i]+'icon').addClass("icon-caret-down");
			}
		}
	}
	
	//回车事件控制
	document.onkeydown = function(event){
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchReceipt(); //调用主列表页查询
			}
		}
	};
