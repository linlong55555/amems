	
	var planes;
	var message=[];
	$(document).ready(function(){
		 initPlanes();
		 changeOrganization(true);
		 Navigation(menuCode);
		 decodePageParam();
		 //回车搜索
		 $(document).keydown(function(event){
			 if(event.keyCode==13){
					if($("#keyword").is(":focus")){
						decodePageParam();      
					}
				}
		 });
		 
		 $('input.date-range-picker').daterangepicker().prev().on("click", function() {
				$(this).next().focus();
		 });
		 
		//初始化日志功能
		logModal.init({code:'B_S_006',diffCallback:function(data){
			var row = data.master.rows[0];
			// 主表的修改类型为新增，则去除上一版本
			if(row.REC_XGLX == 1){
				$("table[id$='_LAST']").parent().parent().parent().remove();
			}
		}});
		/*if(message.length<=0){
			$("#tz").hide();
		}else{
			initMessage();
		}*/
		 
	});
	
	var pagination  = {};
	
	/**
	  * 编码页面查询条件和分页
	  * @returns
	  */
	 function encodePageParam(){
	 	var pageParam = {};
	 	var params = {};
	 	params.keyword = $.trim($("#keyword").val());
	 	params.fxjldh = $.trim($("#fxjldh").val());
	 	params.fjzch = $.trim($("#fjzch").val());
	 	params.jlbym = $.trim($("#jlbym").val());
	 	var fxrq = $.trim($("#fxrq").val());
		if(fxrq != ''){
			 param.fxrqBegin = fxrq.substring(0,10);
			 param.fxrqEnd = fxrq.substring(13,23);
		}
	 	params.zt = $.trim($("#zt").val());
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
	 		$("#keyword").val(params.keyword);
	 		$("#fxjldh").val(params.fxjldh);
	 		$("#fjzch").val(params.fjzch);
	 		$("#jlbym").val(params.jlbym);
	 		if(params.fxrqBegin){
	 			$("#fxrq").val(params.fxrqBegin + " ~ " + params.fxrqEnd);
	 		}
	 		$("#zt").val(params.zt);
	 		$("#dprtcode").val(params.dprtcode);
	 		if(pageParamJson.pagination){
	 			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
	 		}else{
	 			searchFlightRecord();
	 		}
	 	}catch(e){
	 		searchFlightRecord();
	 	}
	 }
	
	 /**
	  * 状态map
	  */
	 var statusMap = {
			 1 : "保存",
			 2 : "提交",
			 8 : "作废",
			 9 : "关闭",
			 10 : "完成",
			 11 : "修改作废",
			 12 : "修订"
	 };
	
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 var param = {};
		 param.keyword = $.trim($("#keyword").val());
		 
		 var fxjldh = $.trim($("#fxjldh").val());
		 if(fxjldh != ''){
			 param.fxjldh = fxjldh;
		 }
		 
		 var fjzch = $.trim($("#fjzch").val());
		 if(fjzch != ''){
			 param.fjzch = fjzch;
		 }
		 
		 var jlbym = $.trim($("#jlbym").val());
		 if(jlbym != ''){
			 param.jlbym = jlbym;
		 }
		 
		 var fxrq = $.trim($("#fxrq").val());
		 if(fxrq != ''){
			 param.fxrqBegin = fxrq.substring(0,10);
			 param.fxrqEnd = fxrq.substring(13,23);
		 }
		 
		 var zt = $.trim($("#zt").val());
		 if(zt != ''){
			 param.zt = zt;
		 }
		 
		 var dprtcode = $.trim($("#dprtcode").val());
		 if(dprtcode != ''){
			 param.dprtcode = dprtcode;
		 }
		 
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
		   url:basePath+"/flightdata/flightrecordsheet/queryByPage",
		   type: "post",
		   data:JSON.stringify(param),
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   success:function(data){
		    finishWait();
		    $("#tz").hide();
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
				   if(message.length>0){
						initMessage();
						$("#tz").show();
				   }
				   // 替换null或undefined
				   FormatUtil.removeNullOrUndefined();
				   // 标记关键字
				   signByKeyword($.trim($("#keyword").val()), [2,4,7,9])
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"11\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'flight_record_sheet_table'});
			   refreshPermission();
	      }
	    }); 
		
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			  if(row.isXdtx == 1){
				   htmlContent += ["<tr bgcolor=\"#ff9c9c\">",
				                   buildEditBtn(row),
				                   "<td class='fixed-column'><a href=\"javascript:goToViewPage('"+row.id+"')\">"+StringUtil.escapeStr(row.fxjldh)+"</a></td>",
				                   "<td style='text-align: left'>"+StringUtil.escapeStr(row.fjzch)+"</td>",
				                   "<td>"+StringUtil.escapeStr(row.jlbym)+"</td>",
				                   "<td>"+(row.fxrq||"").substr(0, 10)+"</td>",
				                   "<td>"+statusMap[row.zt]+"</td>",
				                   "<td style='text-align: left'>"+row.zdr.displayName+"</td>",
				                   "<td>"+row.zdsj+"</td>",
				                   "<td style='text-align: left'>"+row.xdr.displayName+"</td>",
				                   "<td>"+row.xdsj+"</td>",
				                   "<td style='text-align: left'>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>",
				                   "</tr>"
				                   ].join("");
				   message.push(row);
			  }else{
				   htmlContent += ["<tr bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
				                   buildEditBtn(row),
				                   "<td class='fixed-column'><a href=\"javascript:goToViewPage('"+row.id+"')\">"+StringUtil.escapeStr(row.fxjldh)+"</a></td>",
				                   "<td style='text-align: left'>"+StringUtil.escapeStr(row.fjzch)+"</td>",
				                   "<td>"+StringUtil.escapeStr(row.jlbym)+"</td>",
				                   "<td>"+(row.fxrq||"").substr(0, 10)+"</td>",
				                   "<td>"+statusMap[row.zt]+"</td>",
				                   "<td style='text-align: left'>"+row.zdr.displayName+"</td>",
				                   "<td>"+row.zdsj+"</td>",
				                   "<td style='text-align: left'>"+row.xdr.displayName+"</td>",
				                   "<td>"+row.xdsj+"</td>",
				                   "<td style='text-align: left'>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>",
				                   "</tr>"
				                   ].join("");
			   }
		   });
		   message.reverse();
		   
		   if(message.length > 0){
			   AlertUtil.showErrorMessage("请依次提交飞行记录单号为"+$(message).map(function(){
				   return this.fxjldh;
			   }).get().join(",")+"的数据！");
		   }
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   
		   function buildEditBtn(row){
			   if(row.zt == 99){
				   return "<td class='fixed-column'></td>";
			   }
			   return "<td class='fixed-column'><i class='icon-edit color-blue cursor-pointer' onClick='goToEditPage(\"" + row.id + "\")' title='修改 Edit'></i></td>";
		   }
	 }
	 
	 /**
	  * 跳转到添加页面
	  * @param obj
	  * @param rid
	  */
	 function goToAddPage(){
		 if($("#fjzch option:selected").attr("issync") == 1){
			 window.location =basePath+"/flightdata/flightrecordsheet/add?pageParam="+encodePageParam()+"&fjzch="+encodeURIComponent($("#fjzch").val());
		 }else{
			 AlertUtil.showErrorMessage("请先初始化该飞机的装机清单数据，再添加飞行记录单！");
		 }
	 }
	 
	 /**
	  * 跳转到修改页面
	  * @param obj
	  * @param rid
	  */
	 function goToEditPage(id){
		 window.location =basePath+"/flightdata/flightrecordsheet/edit/"+id+"?&pageParam="+encodePageParam();
	 }
	 
	 /**
	  * 跳转到查看页面
	  * @param obj
	  * @param rid
	  */
	 function goToViewPage(id){
		 window.open(basePath + "/flightdata/flightrecordsheet/view/"+id);
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
		goPage(currentPage, orderType, sortField);
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
		
	 //搜索条件重置
	 function searchreset(){
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		 });
		
		  $("#divSearch").find("select").each(function(){
			 $(this).val("");
		 })
		 
		 $("#zt").val("-1");
		 $("#keyword").val("");
		 $("#dprtcode").val(userJgdm);
		 changeOrganization();
	 }
	 
	 /**
	  * 搜索飞行记录单
	  */
	 function searchFlightRecord(){
		 message=[];
		 goPage(1,"auto","desc");
	 }
		 
		 //回车事件控制
		 /*document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		}*/
	 
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
	 
	 //改变组织机构时改变飞机注册号
	 function changeOrganization(notFresh){
	 	var dprtcode = $.trim($("#dprtcode").val());
	 	var defaultPlane = $("#defaultPlane").val();
	 	var planeRegOption = '';
	 	if(planes != null && planes.length >0){
	 		$.each(planes,function(i,planeData){
	 			if(dprtcode == planeData.dprtcode){
	 				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.fjzch)+"' "
	 					+"issync='"+(planeData.isSync||0)+"' "
	 					+(defaultPlane ==planeData.fjzch?"selected='selected'":"")+">"+StringUtil.escapeStr(planeData.fjzch)+"</option>";
	 			}
	 		});
	 	}
	 	if(planeRegOption == ''){
	 		planeRegOption = '<option value="NO_PLANE">暂无飞机 No plane</option>';
	 	}
	 	$("#fjzch").html(planeRegOption);
	 	if(!notFresh){
	 		searchFlightRecord();
	 	}
	 }
	 
	 function initPlanes(){
		 AjaxUtil.ajax({
			   async: false,
			   url:basePath+"/productionplan/planeData/findAllWithFjjx",
			   type: "post",
			   success:function(data){
				   planes = data;
		      }
	    }); 
	 }
	 /**
	  * 初始化消息显示
	  * @returns
	  */
	 function initMessage(){
	 	
	 	//轮播
	 	var html = "";
	 /*	$.each(message, function(index, row){
	 		html += "<li>";
	 		html += "<a  onclick=\"goToEditPage('"+row.id+"');\" href='javascript:void(0);' title='"+StringUtil.title(row.fxjldh,30)+"'>"+StringUtil.subString(row.fxjldh,30)+"</a>";
	 		html += "</li>";
	 	});*/
	 	if(message.length>0){
	 		html += "<li>";
	 		html += "<a  onclick=\"goToEditPage('"+message[0].id+"');\" href='javascript:void(0);' title='"+StringUtil.title(message[0].fxjldh,30)+"'>"+StringUtil.subString(message[0].fxjldh,30)+"</a>";
	 		html += "</li>";
	 	}
	 	$("#messageUl").html(html);
	 	
	 	$("#messageDiv").BreakingNews({
	 		background		:"#FFF",
	 		title			:"<i id=\"messageBth\" title='通知公告 Notice'  class=\"info-label1 danger-bg\" >"+message.length+"</i>",
	 		/*title			:"<div id=\"messageBth\" class=\"col-xs-1 info-label1 danger-bg\" ></div>",*/
	 		
	 		titlecolor		:"#FFF",
	 		titlebgcolor	:"#FFF",
	 		linkcolor		:"#333",
	 		linkhovercolor		:"#428bca",
	 		fonttextsize		:16,
	 		isbold			:false,
	 		border			:"solid 0px #099",
	 		width			:"60%",
	 		timer			:3000,
	 		autoplay		:true,
	 		effect			:"slide",
	 		marginleft      :10
	 	});
	     
	 	var title =  "<div>待修改的飞行记录本"  + /*"<span class=\"badge margin-left-10\">"+message.length+"</span>"+*/
//	 	"<em onclick='moreMessage();' class='pull-right icon-external-link cursor-pointer'/>" +
	 	"</div>" ;
	 	$('#messageBth').webuiPopover({
	 		placement:'auto',
	 		container: document.body,
	 		style:'',
	 		padding: false,
	 		title : title,// 设置 弹出框 的标题
	 		trigger: 'click',
	 		type:'html',
	 		width:500,
	 		height:'auto',
	 		backdrop:false,
	 		content : generateMessageHtml()// 这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
	 	});
	 }
	 function generateMessageHtml(){
			var html = "<ul class='bd list-group margin-bottom-0'>";
			$.each(message, function(index, row){
				html += "<li class='list-group-item'>";
				if(row.jjd == 9){
					html += "<i class='icon-volume-up margin-right-5' style='color:red;'></i>";
				}else{
					html += "<i class='icon-volume-up margin-right-10'></i>";
				}
				html += "<span class='margin-right-10'><a onclick=\"goToEditPage('"+row.id+"');\" href='javascript:void(0);' title='"+StringUtil.escapeStr(StringUtil.title(row.fxjldh,30))+"'>"+StringUtil.escapeStr(StringUtil.subString(row.fxjldh,30))+"</a></span>";
				html += "</li>";
			});
			html += "</ul>"
			return html;
		}
	 $('#tghelp').webuiPopover({
		 placement:'auto',
	 		container: document.body,
	 		style:'',
	 		padding: false,
	 		trigger: 'click',
	 		type:'html',
	 		width:250,
	 		height:'auto',
	 		backdrop:false,
	 		content : "<div style='padding:10px;'>标记有颜色的数据为待修改的数据</div>"
	 	});
	 