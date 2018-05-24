$(document).ready(function(){
		 Navigation(menuCode);
		 	fjzchlist();
		 	refreshPermission();
			decodePageParam();
			logModal.init({
				code : 'B_S_019'
			});
			$('#hbrq').datepicker({
				autoclose : true,
				clearBtn : true
			});
			validation();
	 });

	function fjzchlist(){
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		var planeRegOption = '';
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			});
		}
		$("#fjzch").html(planeRegOption); 
	}
	
	var pagination = {};
	var mainpagination={};
	function encodePageParam() {
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword").val());
		params.dprtcode = $.trim($("#dprtcode").val());
		params.fjzch = $.trim($("#fjzch").val());
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
			$("#fjzch").val(params.fjzch);
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
		searchParam.keyword = $.trim($("#keyword").val());
		var dprtcode = $.trim($("#dprtcode").val());
		if (dprtcode != '') {
			searchParam.dprtcode = dprtcode;
		}

		var fjzch = $.trim($("#fjzch").val());
		if (fjzch != '') {
			searchParam.fjzch = fjzch;
		}
		pagination = {
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 10
		};
		mainpagination={
				page : pageNumber,
				sort : sequence,
				order : sortType,
				rows : 10	
		};
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/productionplan/planefaultmonitoring/getListPage",
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
					signByKeyword($.trim($("#keyword").val()),
							[ 3,4 ],"#fjgzjk_list tr td")
				} else {
					$("#fjgzjk_list").empty();
					$("#fjgzjk_pagination").empty();
					$("#fjgzjk_list").append(
							"<tr ><td  class='text-center'  colspan=\"11\">暂无数据 No data.</td></tr>");
				}
				new Sticky({
					tableId : 'fjgz_record_sheet_table'
				});
			}
		});

	}
 	
 	var chosesId=[];
 	function appendContentHtml(list){
 		chosesId=[];
		var htmlContent = '';
		$.each(list,function(index,row){
			  if (index % 2 == 0) {
				  if($("#manidinfo").val()==row.id){
					  htmlContent +="<tr id='"+row.id+"'  dprtId='"+row.dprtcode+"' fjzch='"+row.fjzch+"' onclick='showDcgzcl(this)' style=\"cursor:pointer\" class='bg_tr' >";
				  }else{					  
					  htmlContent +="<tr id='"+row.id+"'  dprtId='"+row.dprtcode+"' fjzch='"+row.fjzch+"' onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
				  }
			   } else {
				   if($("#manidinfo").val()==row.id){
						  htmlContent +="<tr id='"+row.id+"'  dprtId='"+row.dprtcode+"' fjzch='"+row.fjzch+"' onclick='showDcgzcl(this)' style=\"cursor:pointer\" class='bg_tr' >";
					  }else{
						  htmlContent +="<tr id='"+row.id+"'  dprtId='"+row.dprtcode+"' fjzch='"+row.fjzch+"' onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
					  }
			   }
			if(row.gbzt==1){
				htmlContent += "<td style=\"vertical-align: middle;\" class='text-center '><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='productionplan:planefaultmonitoring:main:02' title='修改 Edit' onclick='update(\""
					+ row.id+ "\")' ></i>&nbsp;&nbsp;<i class='icon-search color-blue cursor-pointer checkPermission' permissioncode='productionplan:planefaultmonitoring:main:03' title='查看  View' onClick='look(\""
					+ row.id+ "\")' ></i></td>";
			}else{
			htmlContent += "<td style=\"vertical-align: middle;\" class='text-center '><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='productionplan:planefaultmonitoring:main:02' title='修改 Edit' onclick='update(\""
						+ row.id+ "\")' ></i>&nbsp;&nbsp;<i class='icon-search color-blue cursor-pointer checkPermission' permissioncode='productionplan:planefaultmonitoring:main:03' title='查看  View' onClick='look(\""
						+ row.id+ "\")' ></i>&nbsp;&nbsp;<i class='icon-power-off color-blue cursor-pointer '  title='关闭  Close' onClick='addFjgzjk(\""
						+ row.id+ "\")' ></i></td>";
			}
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center ' title='"+StringUtil.escapeStr(row.fjzch)+"'>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.gzxx)+"'>"+StringUtil.escapeStr(row.gzxx)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-right' title='"+row.gzcs+"'>"+row.gzcs+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+(row.zjfsrq!=null?row.zjfsrq.substring(0,10):'')+"'>"+(row.zjfsrq!=null?row.zjfsrq.substring(0,10):'')+"</td>";
		    if(row.gbzt==1){
		    	htmlContent +="<td style=\"vertical-align: middle;\" ><a href='javascript:;' onClick='getFjgzjk(\""+ row.id+ "\")'>"+"关闭"+"</a></td>";
		    }else{
		    	htmlContent +="<td style=\"vertical-align: middle;\" >"+"未关闭"+"</td>";
		    }
		    
		    if (index % 2 == 0) {
		    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' >"+getAttacment(row.attachments,"#f9f9f9")+"</td>";
		    }else{
		    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' >"+getAttacment(row.attachments,"#fefefe")+"</td>";
		    }
		    
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zdrname)+"' >"+(StringUtil.escapeStr(row.zdrname)) +"</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' >"+row.zdsj+"</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' >"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
		    htmlContent += "</tr>";
		    $("#fjgzjk_list").empty();
		    $("#fjgzjk_list").html(htmlContent);
			 
	})
}
 	function downloadfile(id) {
 		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
 	} 
 	function add(){
 		window.location =basePath+"/productionplan/planefaultmonitoring/add?pageParam="+encodePageParam();
 		}
	function getAttacment(list,str){
		var attachment="";		
		if (list!=null) {
			for (var i = 0; i < list.length; i++) {
				chosesId.push(list[i].id);
				if (i == 1) {
					attachment = attachment
							+ "　<i class='icon-caret-down' id='"
							+ list[i].id
							+ "icon' onclick=switchfj('"
							+ list[i].id + "')></i><div id='"
							+ list[i].id
							+ "' style='display:none'>";
					}
				if(i==0){
					attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(list[i].wbwjm)+"' onclick=\"downloadfile('"+list[i].id+"')\" >"+substring0To7(StringUtil.escapeStr(list[i].wbwjm))+"</a>";
				}				
				if (i != 0) {
					attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(list[i].wbwjm)+"' onclick=\"downloadfile('"+list[i].id+"')\" >"+substring0To10(StringUtil.escapeStr(list[i].wbwjm))+"</a>";
					attachment += "<br>";

				}
				if (i != 0 && i == list.length - 1) {
					attachment += "</div>";
				}
 					}
 			}			
 			return attachment;
 		}
	 function substring0To7(str){
			if(str!="" && str!=null ){
				if(str.length>7){
					return str.substring(0,7)+"...";
				}
				return str;
			}
			return str;
		}
	 function substring0To10(str){
			if(str!="" && str!=null ){
				if(str.length>10){
					return str.substring(0,10)+"...";
				}
				return str;
			}
			return str;
		}
	 
	 function substring0To10(str){
			if(str!="" && str!=null ){
				if(str.length>=10){
					return str.substring(0,10)+"...";
				}
				return str;
			}
			return str;
		}
	
     function getFjgzjk(id){
    	 clear();
    	 $("#gbyymark").hide();
    	 $("#gb").show();
    	 $("#qd").hide();
    	 var param={};
    	 param.id=id;
    	 AjaxUtil.ajax({
    			url : basePath
    					+ "/productionplan/planefaultmonitoring/getgbyy",
    			type : "post",
    			data : JSON.stringify(param),
    			contentType : "application/json;charset=utf-8",
    			dataType : "json",
    			success : function(data) {
    				$("#gbrname").val(data.gbrname);
    				$("#gbyy").val(data.gbyy);
    				$("#gbsj").val(data.gbsj);
    				$("#gbyy").attr("readonly",true);
    				$("#alertModalFjgzjk").modal("show");
    			}
    		});   	
     }
     
     function addFjgzjk(id){
    	 clear();
    	 $("#gbyymark").show();
    	 $("#gbyyid").val(id);
    	 $("#gbrname").val($("#username").val()+" "+$("#realname").val());
    	 $("#gb").hide();
    	 $("#qd").show();
    	 $("#gbyy").attr("readonly",false);
    	 $("#alertModalFjgzjk").modal("show");
     }
     function clear(){
    	$("#gbyyid").val("");
    	$("#gbr").val("");
		$("#gbyy").val("");
		$("#gbsj").val("");
     }
     
     function searchFjgzRecord(){
    	 goPage(1, "desc", "auto");
    	 document.getElementById('dcgzcl_div').style.display = "none";
     }
     function zdjsOver(){
    	var param={};
    	param.gbrid=$("#gbrid").val();
    	param.gbyy=$("#gbyy").val();
    	param.id=$("#gbyyid").val();
    	if($.trim($("#gbyy").val())!=''){
    	 AjaxUtil.ajax({
 			url : basePath
 					+ "/productionplan/planefaultmonitoring/addgbyy",
 			type : "post",
 			data : JSON.stringify(param),
 			contentType : "application/json;charset=utf-8",
 			dataType : "json",
 			success : function(data) {
 				AlertUtil.showMessage('保存成功!',
 						'/productionplan/planefaultmonitoring/main?id='+$("#gbyyid").val()+'&pageParam=' + encodePageParam() );
 				$("#alertModalFjgzjk").hide();
 				refreshPermission();
 			}
 		});
    	}else{
    		AlertUtil.showMessage("关闭原因不能为空！");
    	}
     }
     

     function showDcgzcl(this_){
    	 var id = $(this_).attr("id");
    	 var fjzch = $(this_).attr("fjzch");
    	 var dprtcode=$(this_).attr("dprtId");
    	$("#manidinfo").val(id);
		 $("#fjzchid").val(fjzch);
		 $("#dprtId").val(dprtcode);
		 showInfo(1, "desc", "auto");
    	 if($("#dcgzcl_div").css("display")=='none'){  		 
    		
 //   		 $("#dcgzcl_div").slideUp(500);
    		 $("#dcgzcl_div").slideDown(500);
    	 }
    	 $("#"+id ).addClass('bg_tr').siblings().removeClass('bg_tr');
    	 $("#"+id ).addClass('bg_tr').siblings().find("td").removeClass('bg_tr');
    	 $("#"+id).find("td").addClass('bg_tr');
     }
     
     function update(id){
    	 window.location =basePath+"/productionplan/planefaultmonitoring/edit?id="+id+'&pageParam=' + encodePageParam();
     }
     
     function look(id){
    	 window.location =basePath+"/productionplan/planefaultmonitoring/look?id="+id+'&pageParam=' + encodePageParam();
     }
     
     $("#fjzch").change(function() {
    	 searchFjgzRecord();
    	});
     $("#dprtcode").change(function() {
    	 fjzchlist();
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
     
     function exportExcel(){
    	 window.open(basePath+"/productionplan/planefaultmonitoring/planeFaultMonitoringOutExcel?dprtcode="
    	 			+ $("#dprtcode").val() + "&keyword="
    	 			+ encodeURIComponent($.trim($("#keyword").val()))+"&fjzch="+$("#fjzch").val());
     }
     
     function vieworhideZjqkContent(){
    	 new Sticky({tableId:'fjgz_record_sheet_table'});
    	 var obj = $("th[name='fj']");
 		var flag = obj.hasClass("downward");
 		if(flag){
 			obj.removeClass("downward").addClass("upward");
 		}else{
 			obj.removeClass("upward").addClass("downward");
 		}
 		for(var i=0;i<chosesId.length;i++){
 			if(flag){				
 				$("#"+chosesId[i]).fadeIn(500);
 				$("#"+chosesId[i]+'icon').removeClass("icon-caret-down");
 				$("#"+chosesId[i]+'icon').addClass("icon-caret-up");
 			}else{
 				$("#"+chosesId[i]).hide();
 				$("#"+chosesId[i]+'icon').removeClass("icon-caret-up");
 				$("#"+chosesId[i]+'icon').addClass("icon-caret-down");
 			}
 		}
 	}
  	
  	function switchfj(id) {
  		new Sticky({tableId:'fjgz_record_sheet_table'});
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