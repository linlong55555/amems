	var choseAllIds=[];
	 $(function(){
		 //开始的加载默认的内容 
		 decodePageParam();
		 //回车搜索
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#keyword_search").is(":focus")){
					searchPlane();      
				}
			}
		};
		
		Navigation(menuCode);
		 
	    // 初始化导入
	    importModal.init({
		    importUrl:"/productionplan/planeData/excelImport",
		    downloadUrl:"/common/templetDownfile/5",
			callback: function(data){
				// 刷新飞机信息
				 goPage(1,"auto","desc");
				 $("#ImportModal").modal("hide");
			}
		});
	    
	    //初始化日志功能
		logModal.init({code:'D_007'});
	 });
	 
	 var pagination  = {};
	 
	 
	 /**
	  * 编码页面查询条件和分页
	  * @returns
	  */
	 function encodePageParam(){
	 	var pageParam = {};
	 	var params = {};
	 	params.keyword = $.trim($("#keyword_search").val());
	 	params.jd = $.trim($("#jd_search").val());
	 	params.dprtcode = $.trim($("#dprtcode").val());
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
	 		$("#keyword_search").val(params.keyword);
	 		$("#dprtcode").val(params.dprtcode);
	 		refreshBase();
	 		$("#jd_search").val(params.jd);
	 		if(pageParamJson.pagination){
	 			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
	 		}else{
	 			goPage(1,"auto","desc");
	 		}
	 	}catch(e){
	 		refreshBase();
	 		goPage(1,"auto","desc");
	 	}
	 }
	 
	 /**
	  * 带条件搜索的翻页
	  */
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		var fjzch_ = $("#fjzch").val();
		var dprtcode = $("#dprtcode_hid").val();
		if(fjzch_ != ''){
			searchParam.fjzch = fjzch_;
			searchParam.dprtcode = dprtcode;
			$("#fjzch").val("");
			$("#dprtcode_hid").val("");
		}
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/planeData/page",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows, searchParam.keyword);
				   new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
						}
					}); 
				   FormatUtil.removeNullOrUndefined();
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,13]);
			   } else {
				  $("#planelist").empty();
				  $("#pagination").empty();
				  $("#planelist").append("<tr><td colspan=\"37\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'planeData_table'});
	      }
	    }); 
		
	 }

	 /**
	  * 封装搜索条件
	  * @returns
	  */
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 searchParam.jd = $.trim($("#jd_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode").val());
		 return searchParam;
	 }
	 
	 
	 /**
	  * 拼接table数据
	  * @param list
	  */
	 function appendContentHtml(list, keyword){
		  choseAllIds=[];
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   choseAllIds.push(index);
			   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"' id='"+index+"'>",
			                   "<td class='fixed-column'>",
			                   "<i class='icon-edit color-blue cursor-pointer'  fjzch='"+StringUtil.escapeStr(row.FJZCH) +"' onClick='goToEditPage(this, \""+row.DPRTCODE+"\")' title='修改 Edit'></i>&nbsp;&nbsp;",
			                   "<i class='icon-remove color-blue cursor-pointer'  fjzch='"+StringUtil.escapeStr(row.FJZCH) +"' onClick='deletePlane(this, \""+row.DPRTCODE+"\")' title='删除 Delete'></i>",
			                   "</td>",
			                   "<td name='content' style='text-align: left'>"+StringUtil.escapeStr(row.FJJX)+"</td>",
			                   "<td style='text-align: left'><a href='javascript:void(0);' fjzch='"+StringUtil.escapeStr(row.FJZCH) +"' onclick='goToViewPage(this, \""+row.DPRTCODE+"\")'  name='content'>"+StringUtil.escapeStr(row.FJZCH)+"</a></td>",
			                   "<td name='content' style='text-align: left'>"+StringUtil.escapeStr(row.XLH)+"</td>",
			                   "<td name='content' style='text-align: left'>"+StringUtil.escapeStr(row.BZM)+"</td>",
			                   "<td>"+(row.CCRQ||"").substr(0, 10)+"</td>",
			                   "<td class='text-left'>"+StringUtil.escapeStr(row.JDMS)+"</td>",
			                   
			                   "<td name='content' style='text-align: left'>"+sanzheng(index,row)+"</td>",
			                   /*"<td name='content' style='text-align: left'>"+youxaioqi(row)+"</td>",*/
			                   /*abbreviate(StringUtil.escapeStr(row.GJDJZH), 15),*/
			                  /* abbreviate(StringUtil.escapeStr(row.SHZH), 15),*/
			                  /* abbreviate(StringUtil.escapeStr(row.WXDTXKZH), 15),
			                   "<td>"+(row.DTZZJKRQ||"").substr(0, 10)+"</td>",*/
			                   "<td class='text-left'>"+StringUtil.escapeStr(row.JSGZJL)+"</td>",
			                   "<td class='text-left'>"+StringUtil.escapeStr(row.BZ)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.USERNAME||"")+" "+StringUtil.escapeStr(row.REALNAME||"")+"'>"+StringUtil.escapeStr(row.USERNAME||"")+" "+StringUtil.escapeStr(substring026(row.REALNAME||""))+"</td>",
			                   "<td>"+row.CJSJ+"</td>",
			                   "<td style='text-align: left'>"+AccessDepartmentUtil.getDpartName(row.DPRTCODE)+"</td>",
			                   "<td>"+(row.init_date_rq||"").substr(0, 10)+"</td>",
			                   "<td style='text-align: right'>"+row.init_time_jsfx+"</td>",
			                   "<td style='text-align: right'>"+row.init_time_ssd+"</td>",
			                   "<td style='text-align: right'>"+row.init_time_jc+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_qlj+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_jc+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_wdg+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_l_n1+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_l_n2+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_l_n3+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_l_n4+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_l_n5+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_l_n6+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_r_n1+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_r_n2+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_r_n3+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_r_n4+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_r_n5+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_r_n6+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_ts1+"</td>",
			                   "<td style='text-align: right'>"+row.init_loop_ts2+"</td>",
			                   "</tr>"
			                   ].join("");
		   });
		   $("#planelist").empty();
		   $("#planelist").html(htmlContent);
		 
	 }
	 
	 function sanzheng(index,row){
			 var pgdhs='';
			 var GJDJZH=row.GJDJZH;
			 var SHZH=row.SHZH;
			 var WXDTXKZH=row.WXDTXKZH;
			 var GJDJZJKRQ=(row.GJDJZJKRQ||"").substr(0, 10);
			 var SHZJKRQ=(row.SHZJKRQ||"").substr(0, 10);
			 var DTZZJKRQ=(row.DTZZJKRQ||"").substr(0, 10);
			 var orders=[];
			 var ordersDate=[];
			 var ordersName=[];
			 if(GJDJZJKRQ != ''){
				 orders.push(GJDJZH);
				 ordersDate.push(GJDJZJKRQ);
				 ordersName.push("国籍登记证号:");
			 }
			 if(SHZJKRQ != ''){
				 orders.push(SHZH);
				 ordersDate.push(SHZJKRQ);
				 ordersName.push("适航证号:");
			 }
			 if(DTZZJKRQ != ''){
				 orders.push(WXDTXKZH);
				 ordersDate.push(DTZZJKRQ);
				 ordersName.push("无线电台许可证号:");
			 }
			   var len = orders.length;
			   for(i=0;i<len;i++){
				    if (i == 1) {
					   pgdhs = pgdhs
								+ "　<i class='icon-caret-down cursor-pointer' id='"
								+ index
								+ "icon' onclick=vieworhideWorkContent('"
								+ index + "')></i><div id='"
								+ index
								+ "content' style='display:none;'>";
					}
				    pgdhs += ordersName[i]+"<span >"+StringUtil.escapeStr(orders[i])+"<span style='color:#0e79dc;font-weight:bold;'>&nbsp;/&nbsp;</span>"+ordersDate[i]+"</span>";
				    if (i != 0) {
					   pgdhs = pgdhs + "<br>";

					}
					if (i != 0 && i == orders.length - 1) {
						pgdhs = pgdhs + "</div>";
					}
			   }
		   
		   return pgdhs
	 }
	
	 function vieworhideWorkContentAll(){
		 var obj = $("th[name='th_fjsz']");
		 var flag = obj.hasClass("downward");
		 if(flag){
			obj.removeClass("downward").addClass("upward");
		 }else{
			obj.removeClass("upward").addClass("downward");
		 }
		 for(var i=0;i<choseAllIds.length;i++){
			 if(flag){
				 $("#"+choseAllIds[i]+'content').fadeIn(500);
				 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-down");
				 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-up");
			 }else{
				 $("#"+choseAllIds[i]+'content').hide();
				 $("#"+choseAllIds[i]+'icon').removeClass("icon-caret-up");
				 $("#"+choseAllIds[i]+'icon').addClass("icon-caret-down");
			 }
		 }
	 }
	
	 function vieworhideWorkContent(id) {
		
			var flag = $("#" + id+'content').is(":hidden");
		
			if (flag) {
				$("#" + id+'content').fadeIn(500);
				$("#" + id + 'icon').removeClass("icon-caret-down");
				$("#" + id + 'icon').addClass("icon-caret-up");
			} else {
				$("#" + id+'content').hide();
				$("#" + id + 'icon').removeClass("icon-caret-up");
				$("#" + id + 'icon').addClass("icon-caret-down");
			}

		}
	
	 /**
	  * 组装飞机初始化数据
	  * @param row
	  * @returns {String}
	  */
	 function assemblePlaneInitData(row){
		 var html = "";
		 if(row && row.initDatas){
			 $.each($("[id^='init_']"), function(i1,col){
				 var flag = false;
				 $.each(row.initDatas, function(i2,obj){
					 if(col.id == obj.initXmbh){
						 html += "<td>"+(obj.initValue||"")+"</td>";
						 flag = true;
					 }
				 });
				 if(!flag){
					 html += "<td></td>";
				 }
			 });
		 }
		 return html;
	 }
	 
	 
	 /**
	  * 跳转到新增页面
	  */
	function goToAddPage(){
		window.location.href =basePath+"/productionplan/planeData/add?pageParam="+encodePageParam();
	}
	
	/**
	 * 跳转到编辑页面
	 * @param fjzch
	 */
	function goToEditPage(btn,dprtcode_){
		window.location.href =basePath+"/productionplan/planeData/edit?fjzch="+encodeURIComponent($(btn).attr("fjzch"))+"&dprtcode="+dprtcode_+"&pageParam="+encodePageParam();
	}
	
	/**
	 * 跳转到查看页面
	 * @param fjzch
	 */
	function goToViewPage(btn,dprtcode_){
		window.open(basePath + "/productionplan/planeData/view?fjzch="+encodeURIComponent($(btn).attr("fjzch"))+"&dprtcode="+dprtcode_);
	}
	 
	  
	/**
	 * 字段排序（id以_order结尾的数据）
	 * @param obj
	 */
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
		goPage(currentPage, "d1." + obj, orderType);
	}
	
	/**
	 * 跳转至指定页数
	 * @param pageNumber	当前页码
	 * @param sortType	排序字段
	 * @param sequence	排序规则 
	 */
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	
	/**
	 * 飞机搜索
	 */
	function searchPlane(){
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
  	  * 显示导入模态框
  	  */
  	 function showImportModal(){
  		 importModal.show();
  	 }
  	 
  	 function abbreviate(content, maxLength){
  		if(content && content.length > maxLength){
			   remark="<td class='text-left' title='"+content+"'>"+content.substr(0,maxLength)+"...</td>"; 
		   }else{
			   remark="<td class='text-left'>"+content+"</td>";
		   }
  		return remark;
  	 }
  	 
  	/**
 	 * 刷新基地
 	 */
 	function refreshBase(isSearchPlane){
 		AjaxUtil.ajax({
 			   async: false,
 			   url:basePath+"/productionmessage/aircraftscheduling/queryBase",
 			   type: "post",
 			   contentType:"application/json;charset=utf-8",
 			   dataType:"json",
 			   data:JSON.stringify({
 				   dprtcode : $("#dprtcode").val()
 			   }),
 			   success:function(data){
 				   $("#jd_search").empty();
 				   if(data && data.length > 0){
 					  $("#jd_search").append('<option value="">显示全部All</option>');
 					   for(var i = 0; i < data.length; i++){
 						   $("#jd_search").append('<option value="'+data[i].id+'">'+StringUtil.escapeStr(data[i].jdms)+'</option>');
 					   }
 				   }else{
 					   $("#jd_search").append('<option value="">暂无基地</option>');
 				   }
 				   if(isSearchPlane){
 					   searchPlane();
 				   }
 		      }
 		    }); 
 	}
 	
 	/**
 	 * 删除飞机
 	 * @param btn
 	 * @param dprtcode_
 	 */
 	function deletePlane(btn,dprtcode_){
 		var fjzch_ = $(btn).attr("fjzch");
 		AlertUtil.showConfirmMessage("是否确认删除飞机"+fjzch_+"？",{callback:function(){
 			AjaxUtil.ajax({
  			   url:basePath+"/productionplan/planeData/delete",
  			   type: "post",
  			   contentType:"application/json;charset=utf-8",
  			   dataType:"json",
  			   data:JSON.stringify({
  				   fjzch : fjzch_,
  				   dprtcode : dprtcode_
  			   }),
  			   success:function(data){
  				   searchPlane();
  				   AlertUtil.showMessage("删除成功!");
  		       }
  			}); 
  		}});
 	}
	
 	function substring026(str){
 		if(str!=null&&str!=""){
 			if(str.length>6){
 				return str.substring(0,6)+"...";
 			}
 		}
 		return str;
 	}