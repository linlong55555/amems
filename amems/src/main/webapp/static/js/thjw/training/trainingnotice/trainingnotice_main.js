var state = [];
state[1] = "参训";
state[2] = "讲师";
var fxbs = [];
fxbs[1] = "初训";
fxbs[2] = "复训";

$(document).ready(function(){
		 Navigation(menuCode);
		 	refreshPermission();
			decodePageParam();
			$('#hbrq').datepicker({
				autoclose : true,
				clearBtn : true
			});
	 });

	var pagination = {};
	
	function encodePageParam() {
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword").val());
		params.dprtcode = $.trim($("#dprtcode").val());
		pageParam.params = params;
		pageParam.pagination = pagination;
		return Base64.encode(JSON.stringify(pageParam));
	}	
 	function decodePageParam() {
		try {
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#keyword").val(params.keyword);
			$("#dprtcode").val(params.dprtcode);
			if (pageParamJson.pagination) {
				goPage(pageParamJson.pagination.page,
						pageParamJson.pagination.order,
						pageParamJson.pagination.sort);
			} else {
				goPage(1, "desc", "auto");
			}
		} catch (e) {
			goPage(1, "desc", "auto");
		}
	}				
 	function goPage(pageNumber, sortType, sortField) {
		AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
	}
 
 	function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
		var searchParam = {};
		pagination = {page:pageNumber,sort:sequence,order:sortType,rows:20};
		searchParam.keyword = $.trim($("#keyword").val());
		
		var dprtcode = $.trim($("#dprtcode").val());
		
		if (dprtcode != '') {searchParam.dprtcode = dprtcode;}
		
		searchParam.jsid = userId;
		 var pxDate_search = $.trim($("#pxDate_search").val());
			
		 if(null != pxDate_search && "" != pxDate_search){
			var pxDateBegin= pxDate_search.substring(0,4)+"-"+pxDate_search.substring(5,7)+"-"+pxDate_search.substring(8,10)+" 00:00:00";
			var pxDateEnd= pxDate_search.substring(12,17)+"-"+pxDate_search.substring(18,20)+"-"+pxDate_search.substring(21,23)+" 23:59:59";
			
			 searchParam.paramsMap ={ pxDateBegin:pxDateBegin,pxDateEnd:pxDateEnd};
		 }
		
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/training/trainingnotice/trainingnoticeList",
			type : "post",
			data : JSON.stringify(searchParam),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					appendContentHtml(data.rows);
					new Pagination({
						renderTo : "fjgzjk_pagination",
						data : data,
						sortColumn : sortType,
						orderType : sequence,
						goPage : function(a, b, c) {
							AjaxGetDatasWithSearch(a, b, c);
						}
					});
					// 替换null或undefined
					FormatUtil.removeNullOrUndefined();
					// 标记关键字
					signByKeyword($.trim($("#keyword").val()),[2,3,5,10],"#fjgzjk_list tr td");
				} else {
					$("#fjgzjk_list").empty();
					$("#fjgzjk_pagination").empty();
					$("#fjgzjk_list").append("<tr ><td  class='text-center'  colspan=\"17\">暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId : 'fjgz_record_sheet_table'});
			}
		});

	}
 	
 	function appendContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			
			  if (index % 2 == 0) {
				  htmlContent +="<tr id='"+row.id+"'   onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent +="<tr id='"+row.id+"'  onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#fefefe\">";;
			   }
			
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center ' title='"+StringUtil.escapeStr(state[row.paramsMap?row.paramsMap.tzlx:''])+"'>"+StringUtil.escapeStr(state[row.paramsMap?row.paramsMap.tzlx:''])+"</td>";  
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.course.kcbh)+"'><a href='javascript:void(0);' onclick=\"findto('"+row.id+"')\">"+StringUtil.escapeStr(row.course.kcbh)+"</a></td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.course.kcmc)+"'>"+StringUtil.escapeStr(row.course.kcmc)+"</td>";
		    
		    var pxrqq=(formatUndefine(row.jhKsrq).substring(0,10)+" "+formatUndefine(row.jhKssj));
		    var pxrqh=(formatUndefine(row.jhJsrq).substring(0,10)+" "+formatUndefine(row.jhJssj));
		    var pxrq=pxrqq==" "?pxrqh:pxrqq+(pxrqh==" "?"":"~"+pxrqh);
		    
		    var sjrqq=formatUndefine(row.sjKsrq).substring(0,10)+" "+formatUndefine(row.sjKssj);
		    var sjrqh=formatUndefine(row.sjJsrq).substring(0,10)+" "+formatUndefine(row.sjJssj);
		    var sjrq=sjrqq==" "?sjrqh:sjrqq+(sjrqh==" "?"":"~"+sjrqh);
		    if(sjrq!=''){
		    	htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+pxrq+" "+sjrq+"'>"+pxrq+" "+sjrq+"</td>";
		    }else{
		    	htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+pxrq+"'>"+pxrq+"</td>";
		    }
		    
		    
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.kcdd)+"'>"+StringUtil.escapeStr(row.kcdd)+"</td>";
		    
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pxlb)+"'>"+StringUtil.escapeStr(row.pxlb)+"</td>";
		    
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pxjg)+"'>"+StringUtil.escapeStr(row.pxjg)+"</td>";
		    
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zrr)+"'>"+StringUtil.escapeStr(row.zrr)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pxdx)+"'>"+StringUtil.escapeStr(row.pxdx)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.jsxm)+"'>"+StringUtil.escapeStr(row.jsxm)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(fxbs[row.fxbs])+"'>"+StringUtil.escapeStr(fxbs[row.fxbs])+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pxxs)+"'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.ksxs)+"'>"+StringUtil.escapeStr(row.ksxs)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.ks)+"'>"+StringUtil.escapeStr(row.ks)+"</td>";
		    htmlContent += "<td class='text-center'>";
		    console.info(row.course.id);
			   if(row.course != null&& row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
				   htmlContent += "<i class='icon-file color-blue cursor-pointer' onClick=\"openCoursewareView('"+ row.course.id + "')\" title='课件 Courseware'></i>";
			   }
		   htmlContent += "</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' ></td>";
		    htmlContent += "</tr>";
		    $("#fjgzjk_list").empty();
		    $("#fjgzjk_list").html(htmlContent);
			 
	})
}

 	//弹出查看培训计划界面
 	function findto(id){
 		window.open(basePath+"/training/trainingnotice/find/"+$.trim(id));
 	}
 	
 	
 	function openCoursewareView(id){
 		open_win_attachments_list_edit.show({
 			attachHead : true,
 			chinaHead : '课件信息',
 			englishHead : 'Courseware Information',
 			chinaFileHead : '课件名',
 			englishFileHead : 'Courseware',
 			chinaColFileTitle : '课件名',
 			englishColFileTitle : 'Courseware',
 			djid:id,
 	 		colOptionhead : false,
 			fileHead : false,
 			fileType:"course",
 			callback: function(attachments){//回调函数
 			}
 		});//显示附件
 	}
 	
     function searchFjgzRecord(){
    	 goPage(1, "desc", "auto");
    	 TableUtil.resetTableSorting("fjgz_record_sheet_table");
     }
     
     function showDcgzcl(this_){
    	 var id = $(this_).attr("id");
    	 var dprtcode=$(this_).attr("dprtId");
    	$("#manidinfo").val(id);
		 $("#dprtId").val(dprtcode);
		 goPage(1, "desc", "auto");
    	 if($("#dcgzcl_div").css("display")=='none'){  		 
    		
    		 $("#dcgzcl_div").slideDown(500);
    	 }
    	 $("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
     }
     
     $("#dprtcode").change(function() {
    	 searchFjgzRecord();
	});
     
     function orderBy(obj) {
    		var orderStyle = $("#" + obj + "_order").attr("class");
    		$("th[id$=_order]").each(
    				function() { // 重置class
    					$(this).removeClass("sorting_desc").removeClass("sorting_asc")
    							.addClass("sorting");
    				});
    		$("#" + obj + "_" + "order").removeClass("sorting");
    		var orderType = "asc";
    		if (orderStyle.indexOf("sorting_asc") >= 0) {
    			$("#" + obj + "_" + "order").addClass("sorting_desc");
    			orderType = "asc";
    		} else {
    			orderType = "desc";
    			$("#" + obj + "_" + "order").addClass("sorting_asc");
    		}
    		orderStyle = $("#" + obj + "_order").attr("class");
    		var currentPage = $("#fjgzjk_pagination li[class='active']").text();
    		goPage(currentPage, orderStyle.split("_")[1], obj);
    	}
 	//隐藏Modal时验证销毁重构
	 $("#alertModaladdupdate").on("hidden.bs.modal", function() {
	 	 $("#form1").data('bootstrapValidator').destroy();
	      $('#form1').data('bootstrapValidator', null);
	      validation();
	 });
	 
	 
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