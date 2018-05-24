

	var pagination = {};
	var dataList = {};
	
	/**
	 * 编码页面查询条件和分页
	 * @returns
	 */
	function encodePageParam(){
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword").val());
		params.dprtcode = $.trim($("#dprtcode").val());
		params.fjzch = $.trim($("#fjzch").val());
		params.isM = $.trim($("#isM").val());
		params.mel = $.trim($("#mel").val());
		params.zt = $.trim($("#zt").val());
		
		 var gbrq = $.trim($("#gbrq").val());
		 if(gbrq != ''){
			 params.gbrq = $.trim($("#gbrq").val());
		 }
		 var sqrq = $.trim($("#sqrq").val());
		 if(sqrq != ''){
			 params.sqrq = $.trim($("#sqrq").val());
		 }
		 var dqrq = $.trim($("#dqrq").val());
		 if(dqrq != ''){
			 params.dqrq = $.trim($("#dqrq").val());
		 }
		
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
			$("#keyword").val(params.keyword);
			$("#dprtcode").val(params.dprtcode);
			$("#fjzch").val(params.fjzch);
			$("#isM").val(params.isM);
			$("#mel").val(params.mel);
			$("#zt").val(params.zt);
			
			 if(params.gbrq!=undefined){
				 $("#gbrq").val(params.gbrq);
			 }
			 if(params.sqrq!=undefined){
				 $("#sqrq").val(params.sqrq);
			 }
			 if(params.dqrq!=undefined){
				 $("#dqrq").val(params.dqrq);
			 }
			 
			 $('#dprtcode').trigger('change');
			if(pageParamJson.pagination){
				goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				goPage(1,"auto","desc");
			}
		}catch(e){
			goPage(1,"auto","desc");
		}
	}
	
	function refreshPage(){
		goPage(pagination.page, pagination.sort, pagination.order);
	}


	$(document).ready(function(){
		Navigation(menuCode)
		$('#dprtcode').on('change',function(){
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		 	var planeRegOption = '<option value="">显示全部 All</option>';
			if(planeDatas != null && planeDatas.length >0){
				$.each(planeDatas,function(i,planeData){
					planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
				});
			}
		 
			$("#fjzch").html(planeRegOption);
			
			$("#fjzch").trigger("change");
		});
		
		$('#dprtcode').trigger('change');
	 
		 $("#fjzch").on('change',function(){
			 window.search();
			 
		 });
	    
		decodePageParam()
		
		 $('input.date-range-picker').daterangepicker({
			 autoUpdateInput: false,
			 autoApply: true,
			 locale : {  
                 applyLabel : '确定',  
                 cancelLabel : '清除',  
                 fromLabel : '起始时间',  
                 toLabel : '结束时间',  
                 customRangeLabel : '自定义',  
                 daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                 monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                         '七月', '八月', '九月', '十月', '十一月', '十二月' ],  
                 firstDay : 1  
             }  
		 }).prev().on("click", function() {
				$(this).next().focus();
		 });
		 
		 $('input.date-range-picker').on('cancel.daterangepicker', function(ev, picker) {
             $(this).val('');
         });
		 
		 validationEnd();
		 
		//初始化日志功能
		logModal.init({code:'B_S_013'});
	});

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber, sortType, sortField) {
		var param = gatherSearchParam();
		pagination = {
			page : pageNumber,
			sort : sortType,
			order : sortField,
			rows : 20
		};
		param.pagination = pagination;
		if (id != "") {
			param.id = id;
			id = "";
		}
//		param.paramsMap = {userType:userType} ;
		param.paramsMap.userType = userType ;
	
		startWait();
		AjaxUtil.ajax({
			cache:false,
			url : basePath + "/flightdata/legacytrouble/queryList",
			contentType : "application/json;charset=utf-8",
			type : "post",
			dataType : "json",
			data : JSON.stringify(param),
			async : false,
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					appendContentHtml(data.rows);
					var page_ = new Pagination({
						renderTo : "pagination",
						data : data,
						sortColumn : sortField,
						orderType : sortType,
						extParams : {},
						goPage : function(a, b, c) {
							AjaxGetDatasWithSearch(a, c, b);
						}// ,
					// controller: this_
					});
					new Sticky({tableId : 'legacytrouble_list_table'});
					
				} else {
					var tablefir = $("#legacytrouble_list_table").next();
					var tablesec = tablefir.next();
					var tablethi = tablesec.next();
					tablefir.remove();
					tablesec.remove();
					tablethi.remove();
					
				//	new Sticky({tableId : 'legacytrouble_list_table'});
					$("#list").empty();
					$("#pagination").empty();
					$("#list")
							.append(
									"<tr><td style=\"vertical-align: middle;\"  colspan=\"20\">暂无数据 No data.</td></tr>");
				}
				refreshPermission();
			}
		});
	
	}

	function gatherSearchParam() {
		var param = {};
		 param.keyword = $.trim($("#keyword").val());
		 param.dprtcode = $.trim($("#dprtcode").val());
		 param.fjzch = $.trim($("#fjzch").val());
		 param.isM = $.trim($("#isM").val());
		 param.mel = $.trim($("#mel").val());
		 param.zt = $.trim($("#zt").val());
		 
		/* var gbrq = $.trim($("#gbrq").val());
		 if(gbrq != ''){
			 param.gbrqStart = gbrq.substring(0,10)+" 00:00:00";
			 param.gbrqEnd = gbrq.substring(13,23)+" 23:59:59";
		 }
		 
		 var sqrq = $.trim($("#sqrq").val());
		 if(sqrq != ''){
			 param.sqrqStart = sqrq.substring(0,10)+" 00:00:00";
			 param.sqrqEnd = sqrq.substring(13,23)+" 23:59:59";
		 }
		 
		 var dqrq = $.trim($("#dqrq").val());
		 if(dqrq != ''){
			 param.dqrqStart = dqrq.substring(0,10)+" 00:00:00";
			 param.dqrqEnd = dqrq.substring(13,23)+" 23:59:59";
		 }*/
		 
		 param.paramsMap = {};
		  var gbrq = $.trim($("#gbrq").val());
		 if(gbrq != ''){
			 param.paramsMap.gbrqStart = gbrq.substring(0,10)+" 00:00:00";
			 param.paramsMap.gbrqEnd = gbrq.substring(13,23)+" 23:59:59";
		 }
		 
		 var sqrq = $.trim($("#sqrq").val());
		 if(sqrq != ''){
			 param.paramsMap.sqrqStart = sqrq.substring(0,10)+" 00:00:00";
			 param.paramsMap.sqrqEnd = sqrq.substring(13,23)+" 23:59:59";
		 }
		 
		 var dqrq = $.trim($("#dqrq").val());
		 if(dqrq != ''){
			 param.paramsMap.dqrqStart = dqrq.substring(0,10)+" 00:00:00";
			 param.paramsMap.dqrqEnd = dqrq.substring(13,23)+" 23:59:59";
		 }
		 
		 
		return param;
	}
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   dataList[row.id] = row;
			   
			   var bgcolor = '';
			   if(row.bgcolor!=null && row.bgcolor!='null' ){
				   bgcolor = row.bgcolor;
			   }
			   else{
				   if (index % 2 == 0) {
					   bgcolor = "#f9f9f9";
				   } else {
					   bgcolor = "#fefefe";
				   } 
			   }
			  
			   htmlContent = htmlContent + "<tr bgcolor=\""+bgcolor+"\">";
			   htmlContent = htmlContent + "<td  style=\"vertical-align: middle;\"   class='text-center fixed-column '>"
			   if (row.canEdit) {
				   htmlContent = htmlContent + "<i class='icon-edit  color-blue cursor-pointer checkPermission ' permissioncode='flightdata:legacytrouble:edit,flightdata:legacytrouble:manage1:02' onClick=\"edit('"+ row.id + "')\" title='编辑 Edit'></i>&nbsp;&nbsp;"
			   }
			   if (row.canCancel) {
				   htmlContent = htmlContent + "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='flightdata:legacytrouble:cancel,flightdata:legacytrouble:manage1:03' onClick=\"cancel('"+ row.id + "')\" title='作废 Invalid'></i>&nbsp;&nbsp;"
			   }
			   
			   if (row.canEnd) {
				   htmlContent = htmlContent + "<i class='fa glyphicon glyphicon-remove color-blue cursor-pointer checkPermission' permissioncode='flightdata:legacytrouble:end,flightdata:legacytrouble:manage1:06' onClick=\"end('"+ row.id + "')\" title='关闭 Close'></i>&nbsp;&nbsp;"
			   }
			   if (row.canResave) {
				   htmlContent = htmlContent + "<i class='icon-envelope color-blue cursor-pointer checkPermission' permissioncode='flightdata:legacytrouble:resave,flightdata:legacytrouble:manage1:04' onClick=\"resave('"+ row.id + "')\" title='再次保留 Again Retain'></i>&nbsp;&nbsp;"
			   }
			   if (row.canGenerateOrder) {
				   htmlContent = htmlContent + "<i class='icon-reorder color-blue cursor-pointer checkPermission' permissioncode='flightdata:legacytrouble:generateorder,flightdata:legacytrouble:manage1:05' onClick=\"generateOrder('"+ row.id +"','"+StringUtil.escapeStr(row.gzbldh)+ "')\" title='生成工单 Generate Work Order'></i>&nbsp;&nbsp;"
			   }
			   
			   htmlContent = htmlContent +"</td>"; 
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left   '  ><a href='#' onClick=\"view('"+ row.id + "')\">"+formatUndefine(StringUtil.escapeStr(row.gzbldh))+"</a></td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  '>"+formatUndefine(StringUtil.escapeStr(row.fjzch))+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'   title='"+formatUndefine(StringUtil.escapeStr(row.zjh)) +"'>"+formatUndefine(StringUtil.escapeStr(row.zjh))+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  '>"+formatUndefine(StringUtil.escapeStr(row.fxjldh))+'/'+formatUndefine(StringUtil.escapeStr(row.jlbym))+"</td>"; 
			    
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  title='"+formatUndefine(StringUtil.escapeStr(row.gzms) )+"' class='text-left colwidth-20'>"
				+formatUndefine(StringUtil.escapeStr(row.gzms) )+ "</td>";
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  '>"+formatUndefine(row.mel)+"</td>";
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center  '>"+(formatUndefine(row.isM)=='1'?'是':'否')+"</td>"; 
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  'title='"+StringUtil.escapeStr(row.scBlrxx)+"'>"+formatUndefine(StringUtil.escapeStr(row.scBlrxx) )+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center  '>"+formatUndefine((row.scSqrq||'').substring(0,10))+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center  '>"+formatUndefine((row.scDqrq||'').substring(0,10))+"</td>";
			 
			   //处理工单信息
			   var gdxxHtml = '';
			   if( row.gdxxs !=null && row.gdxxs.length>0 ){
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  '>"+formatWorkCard(StringUtil.escapeStr(row.gdxxs) ) +"</td>";
			   }
			   else{
				   var gddlxForOrder = formatUndefine(row.rwlx)=='1'?'4':row.rwlx;
					var apath = basePath
							+ "/project/workorder/Looked?id="
							+ row.xggdid + "&gddlx=" + gddlxForOrder + "";
					
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left'>"+formatUndefine(row.gdxx)+"</td>"; 
			   }
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  ' title='"+StringUtil.escapeStr(row.zcBlrxx)+"'>"+StringUtil.escapeStr(row.zcBlrxx)+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center '>"+ formatUndefine((row.zcSqrq||'').substring(0,10)) +"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center  '>"+ formatUndefine((row.zcDqrq||'').substring(0,10))+"</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  ' title='"+StringUtil.escapeStr(row.gbrxx)+"'>"+StringUtil.escapeStr(row.gbrxx)+"</td>"; 
			  
			   var ztStr = DicAndEnumUtil.getEnumName('legacyTroubleStatusEnum',formatUndefine(row.zt));
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  '>"
			   +((formatUndefine(row.zt) == 9)?'<a href="#" onclick=endDetail(\''+row.id+'\') >关闭</a>':ztStr )
			   +"</td>";
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  ' title='"+StringUtil.escapeStr(row.whr.displayName)+"'>"+ StringUtil.escapeStr(row.whr.displayName)+"</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-center  '>"+ formatUndefine((row.whsj)) +"</td>";
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  '>"+ formatUndefine(row.dprtName)+"</td>";
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#legacytrouble_list_table #list").empty();
		   $("#legacytrouble_list_table #list").html(htmlContent);
		  
		   var keyword = $.trim($("#keyword").val());
		  // signByKeyword(keyword, [2,3,4,5,6,7,8,12,15,17])
		 
	 }
	 
	 //查看revision对应的工卡列表
	 function goToLinkPage(obj,rid){
		 obj.stopPropagation(); //屏蔽父元素的click事件
		 window.location =basePath+"/main/work/listpage?rid="+rid;
	 }
	  
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "asc";
		} else {
			orderType = "desc";
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderType);
		
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
	 *信息检索
	 */
	function search(){
		goPage(1,"auto","desc");
	}
		
	 //搜索条件重置
	 function searchreset(){
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch").find("textarea").each(function(){
			 $(this).val("");
		 })
		 
		  $("#divSearch").find("select").each(function(){
			 $(this).val("");
		 })
		  
		 $('#dprtcode').val($('#user_jgdm').val());
		 var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		 	var planeRegOption = '<option value="">显示全部 All</option>';
			if(planeDatas != null && planeDatas.length >0){
				$.each(planeDatas,function(i,planeData){
					planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
				});
			}
		 $("#fjzch").html(planeRegOption);
		 $('#keyword').val('');
		 
	 }
	 
	//搜索条件显示与隐藏 
	function more() {
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
	 
	 
	 //进编辑页面
	 function edit(id){
		 window.location =basePath+"/flightdata/legacytrouble/edit?id="+$.trim(id)+'&pageParam='+encodePageParam();
	 }
	 
	 function add(){
		 window.location =basePath+"/flightdata/legacytrouble/add?pageParam="+encodePageParam();
	 }
	 
	 //进编辑页面
	 function view(id){
		 //window.location =basePath+"/flightdata/legacytrouble/view?id="+$.trim(id)+'&pageParam='+encodePageParam();
		 window.open(basePath+"/flightdata/legacytrouble/view?id="+$.trim(id)+'&pageParam='+encodePageParam());
	 }
	 
	 //作废
	 function cancel(id){
		 AlertUtil.showConfirmMessage("是否继续执行该操作",{
				 option:{id:id}
				 ,callback:function(option){
					 AjaxUtil.ajax({
						   url:basePath+"/flightdata/legacytrouble/cancel",
						   type: "post",
						   cache:false,
						   data:{id:option.id},
						   async: false,
						   success:function(data){ 
							   AlertUtil.showMessage("故障保留单作废成功");
							   search();
						   } 
					    });  
				 }
		 });
		 
	 }
	 
	 //再次保留
	 function resave(id){
		 window.location =basePath+"/flightdata/legacytrouble/resave?id="+$.trim(id)+'&pageParam='+encodePageParam();
	 }
	 
	 //关闭/指定结束
	 function end(id){
		 
		 var row = dataList[id];
		 $("#endform #id").val(id);
		 $("#endform #gbsm").val('') 
		 $("#endform #gzbldh").val(formatUndefine(row.gzbldh)) ;
		 
		 $("#endform #gbrid").removeAttr("disabled");
		 $("#endform #gbsm").removeAttr("disabled");
		 $("#endform #gbrq").hide();
		 $("#endform #gbrqBlock").hide();
		 
		 $("#endModal #endbtn").show();
		 $("#endModal").find("span").show();
		 $("#endModal").modal("show");
	 }
	 
	 /**
	  * 查看关闭原因
	  * @param id
	  */
	 function endDetail(id){
		 var row = dataList[id];
		 $("#endform #gbsm").val(formatUndefine(row.gbsm)) ;
		 $("#endform #gzbldh").val(formatUndefine(row.gzbldh)) ;
		 $("#endform #gbrq").val(formatUndefine(row.gbrq))
		 
		 $("#endform #gbrid").attr("disabled","true");
		 $("#endform #gbsm").attr("disabled","true");
		 $("#endform #gbrq").show();
		 $("#endform #gbrqBlock").show();
		 $("#endModal #endbtn").hide();
		 $("#endModal").find("span").hide();
		 $("#endModal").modal("show");
		 
	 }
	 
	 $("#endbtn").on('click',function(){
		  $('#endform').data('bootstrapValidator').validate();
		  if(!$('#endform').data('bootstrapValidator').isValid()){
			return false;
		  }	
		  var data = {
			'id':$("#endform #id").val()
			,'gbsm':$.trim($('#gbsm').val())
			,'gbrid':$.trim($('#gbrid').val())
		  }
		  var body  = window;
		  
			// 提交数据
		  AjaxUtil.ajax({
				url:basePath+"/flightdata/legacytrouble/end",
				type:"post",
				data:data,
				dataType:"json",
				cache:false,
				success:function(data){
					$("#endModal").modal("hide");
					AlertUtil.showMessageCallBack({
				          message:"故障保留单关闭成功"
				          ,option:{}
						 ,callback:function(){
							 search();
						 }
					}); 
				}
			});

	 })
	 
	 //生成工单
	 function generateOrder(id,gzbldh){
		 $("#gdgzbldh").val(gzbldh);
		 $("#orderform #id").val(id);
		 $("#orderModal").modal("show");
	 }
	 
	 $("#generateWorkOrderBtn").on('click',function(){
		  var data = {
			'id':$("#orderform #id").val()
			,'gdlx':$.trim($('#gdlx').val())
		  }
		  var body  = window;
			// 提交数据
		  AjaxUtil.ajax({
				url:basePath+"/flightdata/legacytrouble/generateOrder",
				type:"post",
				data:data,
				dataType:"json",
				cache:false,
				success:function(data){
					AlertUtil.showMessageCallBack({
				          message:"生成工单成功"
				          ,option:{}
						 ,callback:function(){
							 search();
						 }
					}); 
				}
			});

	 })
	 
	  function formatWorkCard(gdxxArr){
		var card = '';
   		$.each(gdxxArr,function(i,s){
   			if(i == 1){
   				card += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=vieworhideWorkCard(this)></i><div style='display:none'>";
   			}
   			
   			var gddlxForOrder = formatUndefine(this.gdlx)=='1'?'4':this.gdlx;
			var apath = basePath
					+ "/project/workorder/Looked?id="
					+ this.id + "&gddlx=" + gddlxForOrder + "";
			
   			card += "<a href='#' onClick=\"viewTask('"
								+ apath+ "')\">"
				   + DicAndEnumUtil.getEnumName('planTaskType',formatUndefine(this.gdlx))
				   + (this.gdlx2== null?'':(this.gdlx2=='0'?'':'-'+DicAndEnumUtil.getEnumName('planTaskSubType',formatUndefine(this.gdlx2))))
				   +' '
				   +formatUndefine(this.gdbh)+'-'
				   + DicAndEnumUtil.getEnumName('workOrderStateEnum',formatUndefine(this.zt))+' '
				   +"</a>";
   			
   			if(i != 0 && i != gdxxArr.length - 1){
   				card += "<br/>";
   			}
   			if(i == gdxxArr.length - 1){
   				card += "</div>";
   			}
   		});
	   	
	   	return card;
     }
		 
	//定检工卡显示或隐藏
	 function vieworhideWorkCard(obj) {
		 
	 	var flag = $(obj).next().is(":hidden");
	 	if (flag) {
	 		$(obj).next().fadeIn(500);
	 		$(obj).removeClass("icon-caret-down");
	 		$(obj).addClass("icon-caret-up");
	 		
	 	} else {
	 		$(obj).next().hide();
	 		$(obj).removeClass("icon-caret-up");
	 		$(obj).addClass("icon-caret-down");
	 	}
	 	new Sticky({tableId : 'legacytrouble_list_table'});
	 }
	 
	 function viewTask(url) {
			window.open(url);
	}
	 
	 function validationEnd(){ 
		 $('#endform').bootstrapValidator({
		        message: '数据不合法',
		        feedbackIcons: {
		            //valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		            gbsm: {
		                validators: {
		                	notEmpty: {
		                        message: '关闭原因不能为空'
		                    }
				            ,stringLength: {
				                max: 300,
				                message: '关闭原因不能超出300个字符'
				            }
		                }
		            } 
			        ,gbrid: {
		                validators: {
		                	notEmpty: {
		                        message: '关闭人不能为空'
		                    }
		                }
		            }
		        
		        
		        }
		    });
	 }
	 
	//隐藏Modal时验证销毁重构
	 $("#endModal").on("hidden.bs.modal", function() {
	 	 $("#endform").data('bootstrapValidator').destroy();
	      $('#endform').data('bootstrapValidator', null);
	      validationEnd();
	 });
	 
	 /**
		 * 导出excel
		 */
		function legacyTroubleOutExcel() {
			var keyword = $.trim($("#keyword").val());
			var dprtcode = $.trim($("#dprtcode").val());
			var fjzch = $.trim($("#fjzch").val());
			var isM = $.trim($("#isM").val());
			var mel = $.trim($("#mel").val());
			var zt = $.trim($("#zt").val());		 
			var gbrq = $.trim($("#gbrq").val());
			var sqrq = $.trim($("#sqrq").val());
			var dqrq = $.trim($("#dqrq").val());
			var gbrqStart="";
			var gbrqEnd="";
			if(gbrq != ''){
				 gbrqStart = gbrq.substring(0,10);
				 gbrqEnd = gbrq.substring(13,23);
			 }
			var sqrqStart="";
			var sqrqEnd="";
			 if(sqrq != ''){
				 sqrqStart = sqrq.substring(0,10);
				 sqrqEnd = sqrq.substring(13,23);
			 }
			var dqrqStart="";
			var dqrqEnd="";
			 if(dqrq != ''){
				 dqrqStart = dqrq.substring(0,10);
				 dqrqEnd = dqrq.substring(13,23);
			 }
			window.open(basePath+"/flightdata/legacytrouble/legacyTroubleoutExcel.xls?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+encodeURIComponent(dprtcode)+"&keyword="+encodeURIComponent(keyword)+"&isM="+encodeURIComponent(isM)+"&mel="+encodeURIComponent(mel)+"&zt="+encodeURIComponent(zt)
			+"&gbrqStart="+encodeURIComponent(gbrqStart)+"&gbrqEnd="+encodeURIComponent(gbrqEnd)+"&sqrqStart="+encodeURIComponent(sqrqStart)+"&sqrqEnd="+encodeURIComponent(sqrqEnd)+"&dqrqStart="+encodeURIComponent(dqrqStart)+"&dqrqEnd="+encodeURIComponent(dqrqEnd));
		}
	
	