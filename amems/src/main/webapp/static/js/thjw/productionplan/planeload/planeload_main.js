 $(function(){
		 Navigation(menuCode);
		//加载飞机注册号
		 jzfjzch();
			decodePageParam();
			//初始化日志功能
			logModal.init({code:'B_S_018'});
	 });
	 //加载飞机注册号
 function jzfjzch(){
	 var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
	 	var planeRegOption = "<option value='' >显示全部</option>";
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			});
		}
		$("#fjzch").html(planeRegOption); 
 }

 var pagination = {};
 function encodePageParam() {
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword_search").val());
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
			$("#keyword_search").val(params.keyword);
			$("#dprtcode").val(params.dprtcode);
			$("#fjzch").val(params.fjzch);
			if (pageParamJson.pagination) {
				goPage(pageParamJson.pagination.page,
						pageParamJson.pagination.sort,
						pageParamJson.pagination.order);
			} else {
				goPage(1,"auto","desc");
			}
		} catch (e) {
			goPage(1,"auto","desc");
		}
	}				
 function goPage(pageNumber, sortType, sortField) {
		AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
	}
 function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
		var searchParam = {};
		searchParam.keyword = $.trim($("#keyword_search").val());
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
			sort :sortType,
			order :sequence,
			rows : 20
		};
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/productionplan/planeload/getListPage",
			type : "post",
			data : JSON.stringify(searchParam),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				finishWait();
				if (data.total > 0) {
					appendContentHtml(data.rows);
					new Pagination({
						renderTo : "pagination",
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
					signByKeyword($.trim($("#keyword_search").val()),
							[ 3,16 ],"#fjzdList>tr>td")
				} else {
					$("#fjzdList").empty();
					$("#pagination").empty();
					$("#fjzdList").append(
							"<tr><td colspan=\"26\" class='text-center'>暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId : 'fjzd_list_table'});
			}
		});

	}
 var choseAllIds=[];
 var choses=[];					 
 function appendContentHtml(list) {
	 choses=[];
	 choseAllIds=[];
		var htmlContent = '';
			$.each(list,function(index,row){
				
				if (index % 2 == 0) {
							htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt)+"\" id='"+row.id+"'  >";
						   
						} else {
							htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt)+"\" id='"+row.id+"'>";
						}
						htmlContent += "<td style='width:60px' class='fixed-column text-center'><i class='icon-edit color-blue cursor-pointer' title='修改 Edit'onClick='update(\""+ row.id+ "\")' />&nbsp;&nbsp;" +
																			"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer' title='删除  Delete'onClick='del(\""+ row.id+ "\")' />&nbsp;&nbsp;"+
																			"<i class='icon-search color-blue cursor-pointer' title='查看  View'onClick='view(\""+ row.id+ "\")' />";
					    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.fjzch))+"'>"+StringUtil.escapeStr(formatUndefine(row.fjzch))+"</td>";  
					    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ms))+"'>"+StringUtil.escapeStr(substring0To15(formatUndefine(row.ms)))+"</td>";
					    htmlContent += "<td class='text-center' title='"+(row.zxrq!=null?row.zxrq.substring(0,10):"")+"'>"+(row.zxrq!=null?row.zxrq.substring(0,10):"")+"</td>";
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_x1))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_x1))+"</td>";
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_y1))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_y1))+"</td>";
					    
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_z1))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_z1))+"</td>";  
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_x2))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_x2))+"</td>";
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_y2))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_y2))+"</td>";
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_z2))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_z2))+"</td>";
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_x3))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_x3))+"</td>";  
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_y3))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_y3))+"</td>";
					    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.zxyzd_z3))+"'>"+StringUtil.escapeStr(formatUndefine(row.zxyzd_z3))+"</td>";
					    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.wjzd))+"'>"+StringUtil.escapeStr(formatUndefine(row.wjzd))+"</td>";
					    htmlContent += "<td style='padding:0px;' colspan='3' >"+formatPgdn(row.planeLoadInfolist)+"</td>";  
					    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.gx))+"'>"+StringUtil.escapeStr(substring0To15(formatUndefine(row.gx)))+"</td>";
					    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fjzl_sz))+"'>"+StringUtil.escapeStr(row.fjzl_sz==null?"":(formatUndefine(row.fjzl_sz))+" "+formatUndefine(row.fjzl_dw))+"</td>";
					    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zxzx_sz))+"'>"+StringUtil.escapeStr(row.zxzx_sz==null?"":(formatUndefine(row.zxzx_sz))+" "+formatUndefine(row.zxzx_dw))+"</td>";
					    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.hxzx_sz))+"'>"+StringUtil.escapeStr(row.hxzx_sz==null?"":(formatUndefine(row.hxzx_sz))+" "+formatUndefine(row.hxzx_dw))+"</td>";
					    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"'>"+StringUtil.escapeStr(substring0To15(formatUndefine(row.bz)))+"</td>";
					    htmlContent += "<td class='text-left' >"+getAttacment(row.id)+"</td>";
					    if(row.zdr_user){
					    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+"'>"+StringUtil.escapeStr(substring0To9(formatUndefine(row.zdr_user.username)))+" "+StringUtil.escapeStr(substring0To3(formatUndefine(row.zdr_user.realname)))+"</td>";
					    }else{
					    	htmlContent += "<td class='text-left' ></td>";
					    }
					    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(formatUndefine(row.zdsj))+"</td>";
					    if(row.jg_dprt){
					    	htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname))+"</td>";
					    }else{
					    	htmlContent += "<td class='text-left' ></td>";
					    }
					    htmlContent += "</tr>"
				})				
				$("#fjzdList").empty();
				$("#fjzdList").html(htmlContent);
				refreshPermission(); 
}
function orderBy(obj){
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
	var currentPage = $("#pagination li[class='active']").text();
			  	goPage(currentPage,obj,orderStyle.split("_")[1]);
			 }
			
			//获取预警颜色
			 function getWarningColor(bgcolor,syts,zt){
			 	if(!(zt == 0 || zt==5 || zt==6)){
			 		return bgcolor;
			 	}
			 	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
			 		bgcolor = warningColor.level2;//黄色
			 	}
			 	if(Number(syts) <= yjtsJb1){
			 		bgcolor = warningColor.level1;//红色
			 	}
			 	return bgcolor;
			 }
			 
 function add(){
	 window.location =basePath+"/productionplan/planeload/add?pageParam="+encodePageParam();
 }

 
function getAttacment(id){
		var html="";
		AjaxUtil
			.ajax({
				url : basePath + "/common/loadAttachments",
				type : "post",
				async : false,
				cache:false,
				data : {
					mainid : id
					},
				success : function(data) {
					if (data.success) {
						var attachments = data.attachments;
						var len = data.attachments.length;
						if (len > 0) {
							for (var i = 0; i < len; i++) {
							choses.push(attachments[i].id);
							console.info(choses);
							if (i == 1) {
						    	html = html
										+ "　<i class='icon-caret-down' id='"
										+ attachments[i].id
										+ "icon' onclick=switchZjqk('"
										+ attachments[i].id + "')></i><div id='"
										+ attachments[i].id
										+ "' style='display:none'>";
								}
							html +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+substring0To9(StringUtil.escapeStr(attachments[i].wbwjm))+"</a>";
							if (i != 0) {
								html += "<br>";

							}
							if (i != 0 && i == len - 1) {
								html += "</div>";
							}
							}
						}
					 }
					}
				});
		return html;
		}

function switchZjqk(id) {
	new Sticky({tableId:'fjzd_list_table'});
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
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}


function formatPgdn(list){
	var htmlContent = '';
			if(list!=null){
				$.each(list,function(i,row){
					choseAllIds.push(row.id);
					
					 if (i == 1) {
						 htmlContent = htmlContent
									+ "　<i class='icon-caret-down' id='"
									+ row.id
									+ "icon' onclick=vieworhidePgdh('"
									+ row.id + "')></i><div id='"
									+ row.id
									+ "' style='display:none'>";
						}

					 htmlContent += "<div><div class='text-left border-left-0 pull-left' title='"+StringUtil.escapeStr(row.bjmc)+"'  style='width:230px;height:30px;'>"+StringUtil.escapeStr(substring0To15(row.bjmc))+"</div>";
					    htmlContent += "<div class='text-left pull-left margin-left-5' title='"+StringUtil.escapeStr(row.sxm)+"'  style='width:140px;height:30px;'>"+StringUtil.escapeStr(substring0To9(StringUtil.escapeStr(row.sxm)))+"</div>"; 
						htmlContent += "<div class='text-right pull-left' title='"+StringUtil.escapeStr(row.zdz)+"' style='width:59px;height:30px;'>"+StringUtil.escapeStr(row.zdz)+"</div>";
						if (i != 0) {
							htmlContent += "<br>";

						}
						if (i != 0 && i == list.length - 1) {
							htmlContent += "</div>";
						}
				});
			}
		
	return htmlContent;
}

function substring0To9(str){
	if(str!="" && str!=null ){
		if(str.length>=9){
			return str.substring(0,9)+"...";
		}
		return str;
	}
	return str;
}
function substring0To3(str){
	if(str!="" && str!=null ){
		if(str.length>=3){
			return str.substring(0,3)+"...";
		}
		return str;
	}
	return str;
}
function substring0To15(str){
	if(str!="" && str!=null ){
		if(str.length>=15){
			return str.substring(0,15)+"...";
		}
		return str;
	}
	return str;
}
function vieworhidePgdh(id){
	new Sticky({tableId:'fjzd_list_table'});
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

function searchFjzd(){
	goPage(1,  "auto","desc");
}

function update(id){
	 window.location =basePath+"/productionplan/planeload/edit?id="+id+"&pageParam="+encodePageParam();
}
function del(id,mainid){
	 AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
				 AjaxUtil.ajax({
					 url:basePath + "/productionplan/planeload/delete",
					 type:"post",
					 async: false,
					 data:{
						 'id' : id
					 },
					 success:function(data){
						 if(data.state=="success"){
							 goPage();
							 AlertUtil.showErrorMessage(data.message);
						 }else{
							 AlertUtil.showErrorMessage(data.message);
						 }
					 }
				 });
	 }});
}

function view(id){
	 window.location =basePath+"/productionplan/planeload/view?id="+id+"&pageParam="+encodePageParam();
}

function vieworhideAttachment(){
	new Sticky({tableId:'fjzd_list_table'});
	 for(var i=0;i<choses.length;i++){
		 var flag = $("#"+choses[i]).is(":hidden");
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

function vieworhideContentAll(){
	new Sticky({tableId:'fjzd_list_table'});
	var obj = $("th[name='th_return']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	 for(var i=0;i<choseAllIds.length;i++){
		 //var flag = $("#"+choseAllIds[i]).is(":hidden");
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
	 for(var i=0;i<choses.length;i++){
		 //var flag = $("#"+choseAllIds[i]).is(":hidden");
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