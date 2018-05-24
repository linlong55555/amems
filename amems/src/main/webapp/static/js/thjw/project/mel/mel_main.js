var choseAllIds=[];	
var revisionNoticeBookitemData = [];

var pagination={};
$(document).ready(function(){
	
	Navigation(menuCode, '', '', 'GC-8-1', '刘兵', '2017-08-01', '刘兵', '2017-08-01');
	decodePageParam();
	refreshPermission();
	decodePageParam();
	//初始化日志功能
	logModal.init({code:'B_G_019'});		
});

    /**
     * 查看评估单号操作
     */
	function viewPgdh(pgdid,dprtcode) {
		if(typeof(pgdid) == undefined || pgdid == ''){
			$.messager.alert("提示", "没有可查看的评估单号","error");
		}
		else{
			window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(pgdid)+"/"+$.trim(dprtcode)+"");
		}
	}
	
	function view(id,dprtcode) {
		window.open(basePath+"/project/mel/view?id="+ id+"&dprtcode="+dprtcode);
		
	}
	
	
	/**
	 * 编辑
	 * @param id
	 */
	function edit(id,dprtcode) {
		window.location.href = basePath+"/project/mel/edit?id="+ id+"&dprtcode="+ dprtcode+"&pageParam="+encodePageParam();
	}
	/**
	 * 作废
	 * @param id
	 */
	function deleteMel(id) {
		AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
			
				 AjaxUtil.ajax({
					 type:"post",
					 url:basePath+"/project/mel/cancel",
					 dataType:"json",
					 data:{'id':id},
					 success:function(data) {
						AlertUtil.showMessage('作废成功!');
						refreshPage();
					 }
				 });
				 
		 }});
		
	}
	
	//指定结束
	function shutDown(this_,isEdit){
		var id = $(this_).attr("djid");
		var sqdh = $(this_).attr("sqdh");
		var zdjsyy = $(this_).attr("zdjsyy");
		var zdjsr = $(this_).attr("zdjsr");
		var zdjsrq = $(this_).attr("zdjsrq");
		AssignEndModal.show({
			chinaHead:'文件编号',//单号中文名称
			englishHead:'File No.',//单号英文名称
			edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
			jsdh:sqdh,//指定结束单号
			jsr:zdjsr,//指定结束人
			jssj:zdjsrq,//指定结束时间
			jsyy:zdjsyy,//指定结束原因
			callback: function(data){//回调函数
				if(null != data && data != ''){
					var obj = {
							id : id,
							zdjsrid : $("#userId").val(),
							zdjsyy : data
					};
					sbShutDown(obj);
				}
			}
		});
	}

	//确认指定结束
	function sbShutDown(plan) {
		
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/project/mel/shutDown",
			contentType:"application/json;charset=utf-8",
			type:"post",
			async: false,
			data:JSON.stringify(plan),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('指定结束成功!');
				$("#AssignEndModal").modal("hide");
				id = plan.id;
				refreshPage();
			}
		});
	}
	
	/**
	 * 打印修定通知书
	 * @param id
	 */
	function print(id) {
		alert('未处理')
	}
	 function encodePageParam(){
		 var pageParam={};
		 var params={};
		 params.keyword=$.trim($("#keyword_search").val());
		 params.jszlh=$.trim($("#jszlh").val());
		 params.pgdh=$.trim($("#pgdh").val());
		 params.zdr=$.trim($("#zdr").val());
		 params.zt=$.trim($("#zt").val());
		 params.jszt=$.trim($("#jszt").val());
		 params.dprtcode_search=$.trim($("#dprtcode_search").val());
		 params.xdqx=$.trim($("#xdqx").val());
		 params.tzslx=$.trim($("#tzslx").val());
		 pageParam.params=params;
		 pageParam.pagination=pagination;
		 return Base64.encode(JSON.stringify(pageParam));
	 }	
	 
  //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			 url:basePath+"/project/mel/queryAllPageList",
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
				   signByKeyword($("#keyword_search").val(),[2,5,12,13],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"18\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'main_list_table'});
	      }
	    }); 
		
	 }
    
//将搜索条件封装 
function gatherSearchParam(){
	var searchParam = {};
	var paramsMap = {};
	var keyword = $.trim($("#keyword_search").val());
	var zt = $.trim($("#zt_search").val());
	searchParam.keyword = keyword;
	searchParam.dprtcode = $.trim($("#dprtcode_search").val());
	searchParam.zt = zt;
	paramsMap.userId = $.trim($("#userId").val());
	paramsMap.userType = $.trim($("#userType").val());
	searchParam.paramsMap = paramsMap;
	return searchParam;
}
	 
	 function appendContentHtml(list){
		 choseAllIds=[];
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent += "<tr bgcolor=\"#fefefe\">";
			   }
			   
			   htmlContent += "<td class='text-center fixed-column'>";
			      
			   if(row.zt==0 || row.zt==5 || row.zt==6 ){
				   htmlContent += "<i class='icon-edit  color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:02' onClick=\"edit('"+ row.id + "','"+row.dprtcode+"')\" title='修改 Edit'>" +
				   		"&nbsp;&nbsp;</i>";
				   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:04' onClick=\"deleteMel('"+ row.id + "')\" title='作废 Invalid '></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==3){
				   htmlContent += "<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:07' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(formatUndefine(row.ggdbh))+"' zdjsyy='' zdjsr='' zdjsrq='' onClick=\"shutDown(this,true)\" title='指定结束 End'></i>";
			   }
			   if(row.zt==1){
				   htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:05' onClick=\"audit('"+ row.id + "','"+row.dprtcode+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==2){
				   htmlContent += "<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:mel:main:06' onClick=\"reply('"+ row.id + "','"+row.dprtcode+"')\" title='批准 Approve'></i>&nbsp;&nbsp;";
			   }
			   
			   htmlContent += "</td>";
			   
			   htmlContent += "<td  class='text-left fixed-column'><a href=\"javascript:view('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.ggdbh))+"</td>";  
			   htmlContent += "<td title='"+AffectedUtil.formatTitle(row.orderSourceList)+"' class='text-left'>" + AffectedUtil.formatContent(row.orderSourceList) + "</td>";
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";  
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.xmh)+"</td>";  
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.ssbf)+"</td>";  
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.sszj)+"</td>";
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.zy)+"</td>";  
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.xgqBb)+"</td>";  
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.xghBb)+"</td>";  
			   var xgbj = '';
			   if(row.xgbj != null){
				   xgbj = row.xgbj.replace("A","新增").replace("R","修订").replace("D","删除");
			   }
			   htmlContent += "<td  class='text-left'>"+xgbj+"</td>";
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.xdnr)+"</td>";
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.xgyy)+"</td>";  
			   htmlContent += "<td  class='text-left'>"+StringUtil.escapeStr(row.xdqx)+"</td>";  
			   htmlContent += "<td class='text-left'>";
			   if(row.zt == 9){
				   var zdjsr = row.jsr?StringUtil.escapeStr(row.jsr.displayName):'';
				   htmlContent += "<a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(formatUndefine(row.ggdbh))+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+formatUndefine(row.zdjsrq)+"' onclick=\"shutDown(this,false)\">指定结束</a>";
			   }else{
				   htmlContent += DicAndEnumUtil.getEnumName('revisionNoticeBookZtEnum',row.zt);
			   }
			   htmlContent += "</td>";
			   
			   if(undefined != row.zdr){
				   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.zdr.displayName)+"'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
			   }
			   else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
				   
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
			   
			   htmlContent = htmlContent + "<td  class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";
			   
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
	 }
	 
//新增
function add(){
	window.location = basePath+"/project/mel/add?pageParam="+encodePageParam();
}

AffectedUtil = {
		flag : true,
		formatTitle : function(list){
			var htmlContent = '';
			if(list != null && list != "" && list.length > 0 ){
				$.each(list,function(i,obj){
					htmlContent += StringUtil.escapeStr(obj.pgdh);
					if(i != list.length - 1){
						htmlContent += ",";
					}
				});
			}
			return htmlContent;
		},
		formatContent : function(strs){
			var htmlContent = '';
			if(strs != null && strs.length > 0){
				$.each(strs,function(i,obj){
					if(i == 1){
						htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=CollapseOrExpandUtil.collapseOrExpand(this,'main_list_table')></i><div class='melDisplayFile' style='display:none'>";
					}
					htmlContent += "<a href='javascript:void(0);' onclick=AffectedUtil.view('"+obj.pgdid+"','"+obj.dprtcode+"') >"+StringUtil.escapeStr(obj.pgdh)+"</a>";
					if(i != 0 && i != strs.length - 1){
						htmlContent += "<br/>";
					}
					if(i == strs.length - 1){
						htmlContent += "</div>";
					}
				});
			}
			return htmlContent;
		},
		view : function(id,dprtcode){
			window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
		}
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
		 //信息检索
	function searchRevision(){
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
		 $("#keyword_search").val("");
		 var drpt=$("#drpt").val();
		 $("#dprtcode_search").val(drpt);
		 $("#yjjb").val(yjts);
		 changeContent();
		 
	 }
	//拼接机型工程师和制单人 列表内容
	 function appendChangeContent(data){
	 	 var appendChangeContent='';
	 	 appendChangeContent=appendChangeContent+"<option value=''>显示全部</option>";
	 	 $.each(data,function(index,row){
	 		 appendChangeContent=appendChangeContent+"<option value='"+row.id+"'>"+row.displayName+"</option>";
	 	 });
	 	 $('#zdrid').html(appendChangeContent);
	 	 $('#jxgcs').html(appendChangeContent);
	 	 
	 }
	 
	 //查看工单详细信息
	 function detailPage(obj,rid){
		 obj.stopPropagation(); 
		 window.location =basePath+"/main/revision/"+$.trim(rid)+"/revisionaction/detail?t="+new Date().getTime();
	 }
	 
	//搜索条件显示与隐藏 
	function more() {
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			$("#icon").removeClass("icon-chevron-down");
			$("#icon").addClass("icon-chevron-up");
		} else {

			$("#divSearch").css("display", "none");
			$("#icon").removeClass("icon-chevron-up");
			$("#icon").addClass("icon-chevron-down");
		}
	}
		
		$('.datepicker').datepicker({
			autoclose : true,
			clearBtn:true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				});
		
		
		 $('#example27').multiselect({
			buttonClass : 'btn btn-default',
			buttonWidth : 'auto',

			includeSelectAllOption : true
		}); 
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
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
		 	
		 	$('.date-picker').datepicker({
		 		autoclose : true
		 	}).next().on("click", function() {
		 		$(this).prev().focus();
		 	});
		 	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
		 			function() {
		 				$(this).next().focus();
		 			});
		 	 $('#example27').multiselect({
		 		buttonClass : 'btn btn-default',
		 		buttonWidth : 'auto',

		 		includeSelectAllOption : true
		 	}); 
		 	 
		 	 
		 	//字段排序
		 	function orderBy(obj) {
		 		var orderStyle = $("#" + obj + "_order").attr("class");
		 		$("th[id$=_order]").each(function() { //重置class
		 			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		 		});
		 		$("#" + obj + "_" + "order").removeClass("sorting");
		 		if (orderStyle.indexOf("sorting_asc")>=0) {
		 			$("#" + obj + "_" + "order").addClass("sorting_desc");
		 		} else {
		 			$("#" + obj + "_" + "order").addClass("sorting_asc");
		 		}
		 		orderStyle = $("#" + obj + "_order").attr("class");
		 		var currentPage = $("#pagination li[class='active']").text()||1;
		 		goPage(currentPage,obj,orderStyle.split("_")[1]);
		 	}
		 	//审核
		 	function audit(id,dprtcode){
		 		window.location.href =basePath+"/project/mel/audit?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
		 	}
		 	//批复
		 	function reply(id,dprtcode){
		 		window.location.href =basePath+"/project/mel/approve?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
		 	} 
			//只能输入数字和小数
			function clearNoNum(obj) {
				// 先把非数字的都替换掉，除了数字和.
				obj.value = obj.value.replace(/[^\d.]/g, "");
				// 必须保证第一个为数字而不是.
				obj.value = obj.value.replace(/^\./g, "");
				// 保证只有出现一个.而没有多个.
				obj.value = obj.value.replace(/\.{2,}/g, ".");
				// 保证.只出现一次，而不能出现两次以上
				obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
						".");
			}
			
			 function clickRow(index,this_){
					var $checkbox1 = $("#list :checkbox[name='checkrow']:eq("+index+")");
					var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
					var checked = $checkbox1.is(":checked");
					$checkbox1.attr("checked", !checked);
					$checkbox2.attr("checked", !checked);
				}

				function checkRow(index,this_){
					var flag = $(this_).is(":checked");
					if(flag){
						$(this_).removeAttr("checked");
					}else{
						$(this_).attr("checked",true);
					}
				}

			//刷新页面
			function refreshPage(){
				goPage(pagination.page, pagination.sort, pagination.order);
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
					$("#jszlh").val(params.jszlh);
					$("#pgdh").val(params.pgdh);
					$("#zdr").val(params.zdr);
					$("#zt").val(params.zt);
					$("#jszt").val(params.jszt);
					$("#dprtcode_search").val(params.dprtcode_search);
					$("#tzslx").val(params.tzslx);
					$("#xdqx").val(params.xdqx);
					$("#tzslx").val(params.tzslx);
					if(pageParamJson.pagination){
						goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
					}else{
						goPage(1,"auto","desc");
					}
				}catch(e){
					goPage(1,"auto","desc");
				}
			}
