var exportId ="";
	$(function(){
		// 初始化导航
		Navigation(menuCode,"","");
		// 初始化时间控件
		initDateWidget();
		decodePageParam();
		
	    // 初始化导入
	    importModal.init({
		    importUrl:"/quality/maintenancepersonnel/excelImport",
		    downloadUrl:"/common/templetDownfile/6",
			callback: function(data){
				// 刷新维修档案人员列表
				 goPage(1,"auto","desc");
				 $("#ImportModal").modal("hide");
			}
		});
	    
	    authHeight();
		$(window).resize(function() {
			 authHeight();
		});
		
		// 初始化日志
		initLog();
	});
	
	var pagination = {};
	
	var zzztMap = {
			1 : "在职",
			0 : "离职"
	}
	
	var wbbsMap = {
			1 : "内部",
			2 : "外部"
	}
	
	var xbMap = {
			1 : "男",
			2 : "女"
	}
	
	var jhMap = {
			1 : "已婚",
			0 : "未婚"
	}
	
	/**
	 * 初始化日志功能
	 */
	function initLog(){
		logModal.init({code:'B_Z_001'});
	}
	
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
	
	/**
	 * 刷新维修人员档案
	 */
	function refreshMaintenancePersonnel(){
		goPage(1,"auto","desc");
		$("#detail_div").hide();
		authHeight();
	}
	
	/**
	 * 搜索条件显示与隐藏 
	 */
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
		authHeight();
	}
	
	/**
	 * 搜索条件重置
	 */
	function searchreset(){
		$(".divSearch input:checkbox").attr("checked","checked");
		$(".divSearch input:not(:checkbox)").val("");
		$(".divSearch select").val("");
		$("#dprtcode_search").val(userJgdm);
	}
	
	/**
	 * 初始化时间控件
	 */
	function initDateWidget(){
		$('.date-picker').datepicker({
			 autoclose: true,
			 clearBtn:true
		});
	}
	
	/**
   	 * 跳转至新增页面
   	 */
   	function goToAddPage(){
   		window.location = basePath + "/quality/maintenancepersonnel/add?pageParam="+encodePageParam();
   	}
   	
   	/**
	 * 跳转到修改页面
	 * @param id
	 */
	function goToEditPage(id){
		window.location = basePath + "/quality/maintenancepersonnel/edit/"+id+"?&pageParam="+encodePageParam();
	}
	
	/**
	 * 删除
	 */
	function goToDeletePage(id, xm){
		AlertUtil.showConfirmMessage("是否确认删除姓名为"+xm+"的维修档案数据？",{callback:function(){
 			AjaxUtil.ajax({
  			   url:basePath+"/quality/maintenancepersonnel/delete",
  			   type: "post",
  			   contentType:"application/json;charset=utf-8",
  			   dataType:"json",
  			   data:JSON.stringify({
  				   id : id
  			   }),
  			   success:function(data){
  				   refreshMaintenancePersonnel();
  				   AlertUtil.showMessage("删除成功!");
  		       }
  			}); 
  		}});
	}
	
	/**
	 * 跳转到查看页面
	 * @param id
	 */
	function goToViewPage(id){
		window.open(basePath + "/quality/maintenancepersonnel/view/"+id);
	}
	
	function getparams(){
		var param = {};
		 param.keyword = $.trim($("#keyword_search").val());
		 param.dprtcode = $.trim($("#dprtcode_search").val());
		 
		 var wbbsList = [];
		 var zzztList = [];
		 var xbList = [];
		 var jhList = [];
		 var glList = [];
		 var slList = [];
		 $("[name=wbbs_search]:checkbox:checked").each(function(){
			 wbbsList.push($(this).val());
		 });
		 $("[name=zzzt_search]:checkbox:checked").each(function(){
			 zzztList.push($(this).val());
		 });
		 $("[name=xb_search]:checkbox:checked").each(function(){
			 xbList.push($(this).val());
		 });
		 $("[name=jh_search]:checkbox:checked").each(function(){
			 jhList.push($(this).val());
		 });
		 $("[name=gl_search]:checkbox:checked").each(function(){
			 glList.push($(this).val());
		 });
		 $("[name=sl_search]:checkbox:checked").each(function(){
			 slList.push($(this).val());
		 });
		 param.paramsMap = {
				 wbbsList : wbbsList,
				 zzztList : zzztList,
				 xbList : xbList,
				 jhList : jhList,
				 glList : glList,
				 slList : slList
		 };
		 return param;
	}
	
	//带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 
		 var param = getparams();
		
		 pagination = {
				 page:pageNumber,
				 sort:sortType,
				 order:sequence,
				 rows:20
		 };
		 param.pagination = pagination;
		 if(id != ''){
			 	param.id = id;
				id = '';
		 }
		 
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/quality/maintenancepersonnel/queryPage",
		   type: "post",
		   data:JSON.stringify(param),
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
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
				   // 替换null或undefined
				   FormatUtil.removeNullOrUndefined();
				   // 标记关键字
				   signByKeyword($.trim($("#keyword_search").val()), [2,3,6,13,14,15,16])
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td class='text-center' colspan=\"19\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'maintenancepersonnel_table'});
			   refreshPermission();
	      }
	    }); 
		
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   var post = $(row.businesses).map(function(){
					return StringUtil.escapeStr(this.dgmc)
				}).get().join(", ");
			   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"' onclick='showDetail(\""+row.id+"\",this)' style='cursor:pointer'>",
			                   "<td class='fixed-column' style='text-align: center'>",			            
			                   "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:maintenancepersonnel:edit' onClick='goToEditPage(\""+row.id+"\")' title='修改 Edit'></i>&nbsp;&nbsp;",
			                   "<i class='icon-remove color-blue cursor-pointer checkPermission' permissioncode='quality:maintenancepersonnel:delete' onClick='goToDeletePage(\""+row.id+"\",\""+row.xm+"\")' title='删除 Delete'></i>",
			                   "</td>",
			                   "<td name='content' class='fixed-column' style='text-align: left' title='"+StringUtil.escapeStr(row.rybh)+"'><a href='javascript:void(0);' onclick='goToViewPage(\""+row.id+"\")'  name='content'>"+StringUtil.escapeStr(row.rybh)+"</a></td>",
			                   "<td name='content' class='fixed-column' style='text-align: left' title='"+StringUtil.escapeStr(row.xm)+"'>"+StringUtil.escapeStr(row.xm)+"</td>",
			                   "<td name='content' style='text-align: center' title='"+zzztMap[row.zzzt]+"'>"+zzztMap[row.zzzt]+"</td>",
			                   "<td name='content' style='text-align: center' title='"+wbbsMap[row.wbbs]+"'>"+wbbsMap[row.wbbs]+"</td>",
//			                   "<td name='content' style='text-align: right' title='"+StringUtil.escapeStr(row.cxfz)+"'>"+StringUtil.escapeStr(row.cxfz)+"</td>",
			                   "<td name='content' style='text-align: left' title='"+StringUtil.escapeStr(row.szdw)+"'>"+StringUtil.escapeStr(row.szdw)+"</td>",
			                   "<td style='text-align: left' title='"+post+"'>"+post+"</td>",
			                   "<td name='content' style='text-align: center' title='"+xbMap[row.xb]+"'>"+xbMap[row.xb]+"</td>",
			                   "<td style='text-align: center' title='"+(row.sr||'').substr(0,10)+"'>"+(row.sr||'').substr(0,10)+"</td>",
			                   "<td style='text-align: center' title='"+(row.cjgzrq||'').substr(0,10)+"'>"+(row.cjgzrq||'').substr(0,10)+"</td>",
			                   "<td style='text-align: center' title='"+(row.rzrq||'').substr(0,10)+"'>"+(row.rzrq||'').substr(0,10)+"</td>",
			                   "<td style='text-align: center' title='"+StringUtil.escapeStr(row.zzmm)+"'>"+StringUtil.escapeStr(row.zzmm)+"</td>",
			                   "<td style='text-align: center' title='"+StringUtil.escapeStr(row.yzbm)+"'>"+StringUtil.escapeStr(row.yzbm)+"</td>",
			                   "<td style='text-align: center' title='"+row.lxdh+"'>"+row.lxdh+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.yxdz)+"'>"+StringUtil.escapeStr(row.yxdz)+"</td>",
			                   "<td name='content' style='text-align: center' title='"+jhMap[row.isJh]+"'>"+jhMap[row.isJh]+"</td>",
//			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.dabh)+"'>"+StringUtil.escapeStr(row.dabh)+"</td>",
			                   "<td style='text-align: left' title='"+StringUtil.escapeStr(row.zdr.displayName)+"'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>",
			                   "<td style='text-align: center' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>",
			                   "<td style='text-align: left' title='"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"'>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>",
			                   "</tr>"
			                   ].join("");
		   });
		   
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
	 
	//字段排序
	function orderBy(sortField) {
		var sortColum = $("#" + sortField + "_order");
		var orderStyle = sortColum.attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		sortColum.removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			sortColum.addClass("sorting_desc");
			orderType = "desc";
		} else {
			sortColum.addClass("sorting_asc");
			orderType = "asc";
		}
		orderStyle = $("#" + sortField + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		currentPage = currentPage==''?1:currentPage;
		goPage(currentPage, sortField, orderType);
	}
	
     /**
      * 跳转至指定页数:三个参数依次为:当前页码,排序规则 排序字段,
      * @param pageNumber
      * @param sortType
      * @param sortField
      */ 
	 function goPage(pageNumber,sortType,sortField){
		AjaxGetDatasWithSearch(pageNumber,sortType,sortField);
	 }	
	 
	 
	 /**
	  * 显示维修方案详情
	  * @param id
	  */
	 function showDetail(id,tr){
		if(getEvent().target.tagName == "I"){
			return;
		}
		// 下方div是否显示
		var isBottomShown = false;
		if($("#detail_div").is(":visible")){
			isBottomShown = true;
		}
		$("#list>tr").removeClass("bg_tr");
		$(tr).addClass("bg_tr");
		$("#current_id").val(id);
		$("#personnelId").val(id);
		$("#detail_div").show();
		$("#rybhmark").hide();
		$("#xmmark").hide();
		$("#cjgzrqmark").hide();
		$("#rzrqmark").hide();
		$("#lzrqmark").hide();
		$("div[name='personnelInfo']").hide();
		loadDetail(id);
		authHeight();
		if($("#hideIcon").length == 0){
			$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"></div>').insertAfter($(".fenye"));
		}
		// 选中行可见
		if(!isBottomShown){
			TableUtil.makeTargetRowVisible($(tr), $(".table-tab-type_table"));
		}
		new Sticky({tableId:'maintenancepersonnel_table'});
		exportId = id;
	 }
	 
	 /**
	  * 隐藏下方div
	  */
	 function hideBottom(){
		 $("#detail_div").hide();
		 authHeight();
		 $("#hideIcon").remove();
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
	  * 加载维修人员档案详情数据
	  * @param id
	  */
	 function loadDetail(id){
		 $("#basic_div input").attr("disabled","disabled");
		 $("#basic_div select").attr("disabled","disabled");
		 $("#basic_div textarea").attr("disabled","disabled");
		 startWait();
	   	 AjaxUtil.ajax({
	   	    url:basePath+"/quality/maintenancepersonnel/loaddetail",
	   	    type: "post",
	   	    contentType:"application/json;charset=utf-8",
	   	    dataType:"json",
	   	    data:JSON.stringify({
	   		    id : $("#current_id").val()
	   	    }),
	   	    success:function(data){
	   	    	finishWait();
	   	    	$("#personnelId").val(data.id);
				$("#rybh").val(data.rybh);
				$("#wxryid").val(data.wxryid);
				$("#xm").val(data.xm);
				$("#szdw").val(data.szdw);
				$("[name=xb][value='"+data.xb+"']").attr("checked","checked"); 
				$("input[name='wbbs'][value='"+data.wbbs+"']").attr('checked',true);
				$("#sr").val((data.sr||"").substr(0,10));
				$("#jg").val(data.jg);
				$("#zzmm").val(data.zzmm);
				$("#xl").val(data.xl);
				$("#sfz").val(data.sfz);
				$("#yzbm").val(data.yzbm);
				$("#lxdh").val(data.lxdh);
				$("#yxdz").val(data.yxdz);
				$("#dz").val(data.dz),
				$("[name=is_jh][value='"+data.isJh+"']").attr("checked","checked"); 
				$("#jtcy").val(data.jtcy);
				$("#cjgzrq").val((data.cjgzrq||"").substr(0,10));
				$("#rzrq").val((data.rzrq||"").substr(0,10));
				$("#rzqxx").val(data.rzqxx);
				$("[name=zzzt][value='"+data.zzzt+"']").attr("checked","checked"); 
				$("#lzrq").val((data.lzrq||"").substr(0,10));
				$("#bz").val(data.bz);
				$("#sr").datepicker("update");
				$("#cjgzrq").datepicker("update");
				$("#rzrq").datepicker("update");
				$("#lzrq").datepicker("update");
				$("#zpdzD").val(data.zpdzD);
				$("#zpdzX").val(data.zpdzD);
				if(data.zpdzD!=null && data.zpdzD!=''){
					var str = data.zpdzD.replaceAll(/\\/g,"&");
					var url = basePath +"/training/faculty/preview/"+str+"/1";
					$(".avatar-view img").attr('src',url);
				}else{
					$(".avatar-view img").attr('src', basePath +'/static/images/maintenanceTest.png');
				}
				
				$("#xm_div").html('<input class="form-control" disabled="disabled" type="text" value="'+(data.xm||"")+'">');
				
				if(data.zzzt == "0"){
					$("#lzrqDiv").show();
				}else{
					$("#lzrqDiv").hide();
					$("#lzrq").val("");
				}
				personnel.updateData(data);
				// 回显表格数据
				fillTableData(data);
				loadBasicAttachments();
	        }
         }); 
	 }
	 
	   /**
   	  * 显示导入模态框
   	  */
   	 function showImportModal(){
   		 importModal.show();
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
 	    var searchContent= $(".searchContent:visible").outerHeight()|| -10;
 	    //body的高度
 	   
 	    
 	   //情景1：table-tab
 	    //修改1：在页面div class='page-content'上加class='page-content table-tab-type'
 	    //修改2：给上方表格的父div 添加class='table-tab-type_table'
 	    //修改3：给tab中table的父div 添加class='tab-second-height'
 	    
 	    
 	    if($(".table-tab-type").length>0){
 	    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
 	    	//表格的高度
 	        var tableHeight = bodyHeight - searchContent - paginationHeight  - 60 - adjustHeight;
 	      //谷歌的兼容性问题
 	        if(navigator.userAgent.indexOf("Chrome") > -1){
 	        	tableHeight -= 10;
 	        }
 	        //隐藏的div是否显示
 	        if($("#detail_div").css("display")=='block'){
 	        	//表格的高度
 	        	var actualTableHeight= tableHeight*0.4;
 	        	//表格的高度
 	        	$(".table-tab-type_table").attr('style', 'height:' + actualTableHeight+ 'px !important;overflow-x: auto;');
 	        	// tab header 的高度
 	        	var table_tab=$("#detail_div .nav-tabs").outerHeight()||0;
 	        	//表格的高度
 	        	var actualTableOuterHeight=$(".table-tab-type_table").outerHeight()||0;
 	        	// tabcontent
 	        	var tabcontent=tableHeight-actualTableOuterHeight-table_tab-40;
 	        	//如果下方的tab是引用课件信息。
 	        	if($(".tab-second-height").length>0){
 	        		//是否存在课件上传
 	        		var fileHeadheigth=$("#fileHead").outerHeight()||0;
 	        		//下方tab的高度
 	        		var tab_second_height =tabcontent-fileHeadheigth+10;
 	        		//给下方tab中table 的父div 的高度进行赋值
 	        		$(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
 	        	}
 	        	
 	        }else{
 	        	//给表格的父div的高度进行赋值
 	        	 $(".table-tab-type_table").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
 	        }
 	        
 	    }
 	}
 	
 	function exportExcel(){
 		var id = exportId;
 		if(id){
 			var dprtcode = $("#dprtcode_search").val();
 			window.open(basePath+"/quality/maintenancepersonnel/personnelArchives.xls?dprtcode="+ dprtcode + "&id="+ encodeURIComponent(id));
 		}else{
 			AlertUtil.showMessage("请先选择一个维修人员档案!");
 		}	
 	}
 	
 	function exportExcelList(){
 		var param = getparams();
 		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		window.open(basePath+"/quality/maintenancepersonnel/maintenancepersonnel.xls?paramjson="+JSON.stringify(param));
 	}