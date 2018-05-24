	
    var resultmap = {};
	$(document).ready(function(){
		Navigation(menuCode);
		 //goPage(1,"auto","");//开始的加载默认的内容 
		$('#fjzch').on('change',function(){
			 goPage(1,"auto","");
		 });
		 
		 $('#dprtcode').on('change',function(){
			    var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
			 	var planeRegOption = '';
				if(planeDatas != null && planeDatas.length >0){
					$.each(planeDatas,function(i,planeData){
						planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
					});
				}
				planeRegOption += '<option value="">非装机件</option>';
				$("#fjzch").html(planeRegOption); 
				$("#fjzch").trigger("change");
		 });
		 
		
		 $('#rwlx').on('change',function(){
			 var cur = $(this).children('option:selected').val();
			 if(cur !='2'){
				 $('#rwzlx').find("option[value='']").attr("selected",true); 
			 }
		 });
		 $('#dprtcode').trigger("change");
		 
		//初始化日志功能
		 logModal.init({code:'B_S_009_PLAN',extparam:{IN_ZTS:[9,10]}});
		 
		 $("#wcrq").daterangepicker().prev().on("click", function() {
				$(this).next().focus();
			});
		 $("#dysj").daterangepicker().prev().on("click", function() {
				$(this).next().focus();
			});
	});

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sortField){
		 var param = {};
		 param.paramsMap = {};
		 param.keyword = $.trim($("#keyword").val());
		 var fjzch = $.trim($("#fjzch").val());
		 if(fjzch != ''){
			 param.fjzch = fjzch;
		 }
		 else
		 {
			 param.isLoadedParts = '0'
		 }
		 
		 param.rwlx = $.trim($("#rwlx").val());
		 param.rwzlx = $.trim($("#rwzlx").val());
		 param.dprtcode = $.trim($("#dprtcode").val());
		 param.zt=$.trim($("#zt").val());
		 var wcrq=$.trim($("#wcrq").val());
		 if(wcrq!=''){
			 param.paramsMap.wcrqStart=wcrq.substring(0,10)+" 00:00:00";
			 param.paramsMap.wcrqEnd=wcrq.substring(13,23)+" 23:59:59";
		 }
		 var dysj=$.trim($("#dysj").val());
		 if(dysj!=''){
			 param.paramsMap.dyrqStart=dysj.substring(0,10)+" 00:00:00";
			 param.paramsMap.dyrqEnd=dysj.substring(13,23)+" 23:59:59";
		 }
		 param['pagination.sort'] = sortField;						//排序列字段名
		 param['pagination.order'] = sortType;					//排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
		 param['pagination.page'] = pageNumber;						//页数
		 param["pagination.rows"] = 20;		
		 
			 
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/plantask/queryHistoryList",
		   type: "post",
		   data:param,
		   async: false,
		   cache:false,
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortField,
						orderType : sortType,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,c,b);
						}//,
						//controller: this_
					});
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td style=\"vertical-align: middle; \" colspan=\"23\">暂无数据 No data.</td></tr>");
			   }
			   refreshPermission();
	      } 
	    }); 
		
		
		new Sticky({tableId:'plantaskhistory_list_table'});
		
	 }
	 var choses=[];
	 function appendContentHtml(list){
		 	choses=[];
		   var htmlContent = '';
		   resultmap = {};
		   $.each(list,function(index,row){
			   resultmap[row.id] = row;
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr   bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr   bgcolor=\"#fefefe\">";
			   }
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-center'>"+formatUndefine(index+1)+"</td>"; 
			   var gddlxForOrder = formatUndefine(row.rwlx)=='1'?'4':row.rwlx;
			   var apath = basePath
						+ "/project/workorder/Looked?id="
						+ row.xggdid + "&gddlx=" + gddlxForOrder + "";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'><a class='cursor-pointer' onClick=\"viewTask('"
								+ apath+ "')\" href='#'  >"+row.rwdh+"</a></td>";  
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.title(StringUtil.escapeStr(row.rwxx))+"' > "
				+ StringUtil.subString(StringUtil.escapeStr(row.rwxx),20)
				+ "</td>";
			   
			   htmlContent = htmlContent
				+ "<td style=\"vertical-align: middle; \" class='text-left'>"
				+ DicAndEnumUtil.getEnumName('planTaskType',
						row.rwlx)
				+ ''
				+ (row.rwzlx=='0'?'':'-'+DicAndEnumUtil.getEnumName('planTaskSubType',
						row.rwzlx)) + "</td>";
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.fjzch) )+"</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.fxjldh) )+"</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.jlbym)  )+"</td>";
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.bjh) )+"</td>";  
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.xlh) )+"</td>";  
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.dyrstr) )+"</td>";  
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-center'>"+formatUndefine((row.dysj||'').substring(0,10))+"</td>";  
			   
			    
			   
			   if(row.zt!=9){
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+DicAndEnumUtil.getEnumName('planTaskState',formatUndefine(row.zt))+"</td>";
			   }else{
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>";
				   htmlContent = htmlContent +"<a href=\"javascript:ZdjsReson('"+ row.id+"')\">指定结束</a><br>";
				   htmlContent = htmlContent + "</td>";
			   }
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(row.zdjsrid)+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-center'>"+formatUndefine((row.zdjsrq||'').substring(0,10))  +"</td>";  
			  
			     
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left' title='"+StringUtil.escapeStr(row.gzxx) + "'>"+formatUndefine(StringUtil.escapeStr(row.gzxx)  )+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left' title='"+StringUtil.escapeStr(row.clcs) + "'>"+formatUndefine(StringUtil.escapeStr(row.clcs)  )+"</td>"; 
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine( row.jh )+"</td>";  
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(row.sj )+"</td>";
			   var jhgsStr="";
			   if(formatUndefine(row.jhgsRs)!="" && formatUndefine(row.jhgsXs)!=""){
				   var jhgs=(row.jhgsRs)*(row.jhgsXs);
				   jhgsStr = jhgsStr + formatUndefine(row.jhgsRs)+"人";  
				   jhgsStr = jhgsStr + " x "+formatUndefine(row.jhgsXs)+"时 =";
				   jhgsStr = jhgsStr + formatUndefine(jhgs)+"时";
			   }
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left'>"+formatUndefine(jhgsStr)+"</td>";  
			   var gsStr = '';
			   if(formatUndefine(row.sjgsRs)!="" && formatUndefine(row.sjgsXs)!=""){
				   gsStr = gsStr + formatUndefine(row.sjgsRs)+"人";  
				   gsStr = gsStr + " x "+formatUndefine(row.sjgsXs)+"时 =";
				   gsStr = gsStr + formatUndefine(row.sjgs)+"时";
			   }
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" class='text-left' title=\""+gsStr+"\">"
			   htmlContent = htmlContent + gsStr;
			   htmlContent = htmlContent +"</td>";
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \" title='"+formatUndefine(StringUtil.escapeStr(row.bz)  )+"' class='text-left'>"
				+formatUndefine(StringUtil.escapeStr(row.bz))+ "</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \"  class='text-left'>"
			   
				+formatUndefine(getAttacment(row.attachments))+ "</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle; \"  class='text-left'>"
				+formatUndefine(row.dprtname)+ "</td>";
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		  
		   var keyword = $.trim($("#keyword").val());
		   signByKeyword(keyword, [2,3,5,7,8,9,13,20],"#list tr td")
		 
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
		goPage(currentPage,orderType,obj);
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
	function search(){
		goPage(1,"auto","");
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
		 $('#dprtcode').trigger('change');
		 
		 $("#keyword").val("");
		 
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
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}
	}
		 
	function viewTask(url) {
		//window.open(url,'任务单、工单查看','height="'+window.screen.availHeight+'", width="'+window.screen.availWidth+'", top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
		window.open(url);
	}
	
	//查看指定结束原因
	function ZdjsReson(id){
		console.info(id)
		var gdbh = resultmap[id].rwdh ;
		var zdjsyy = resultmap[id].zdjsyy ;
		var zdjsrq = resultmap[id].zdjsrq ;
		var zdjsr = resultmap[id].zdjsrid ;
		 $('#alertModalZdjsReson').modal('show');
		 $("#gdbh2").val(gdbh);
		 $("#zdjsr").val(zdjsr);
		 
	     $("#zdjsyy2").val(zdjsyy);
	     $("#zdjsrq").val(zdjsrq);
	     
	     //加载附件
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
								 
								var trHtml = '';
								for (var i = 0; i < len; i++) {
									trHtml = trHtml
											+ '<tr  bgcolor="#f9f9f9" id=\''
											+ attachments[i].uuid + '\' key=\''
											+ attachments[i].id + '\' >';
									trHtml = trHtml + '<td><div>';
									 
									trHtml = trHtml
											+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
											+ attachments[i].uuid
											+ '\')" title="删除附件 Delete attachment">  ';
									
									trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-20\" title=\"'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\">'
									+'<a href="#" onclick="downloadfile(\''
									+ attachments[i].id
									+ '\')"  >' + StringUtil.escapeStr(attachments[i].wbwjm)
									+ '</a></td>';

									trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-10\">'
											+ attachments[i].wjdxStr + '</td>';
									trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-left colwidth-13\">'
											+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
									trHtml = trHtml + '<td style=\"vertical-align: middle;\"  class=\"text-center colwidth-13\">' + attachments[i].czsj
											+ '</td>';
									trHtml = trHtml
											+ '<input type="hidden" name="relativePath" value=\''
											+ attachments[i].relativePath + '\'/>';
									trHtml = trHtml + '</tr>';
									
								}
								$('#filelist').html(trHtml);
								$('#filelist').parent().show();
							}
							else{
								$('#filelist').parent().hide();
							}
						}
					}
				});
	 }
	
	/**
	 * 导出excel
	 */
	function historyOutExcel() {
		var fjzch = $("#fjzch").val();
		var keyword = $.trim($("#keyword").val());
		var rwlx=$("#rwlx").val();
		var rwzlx=$("#rwzlx").val();
		var zt=$("#zt").val();
		var wcrq=$("#wcrq").val();
		var dysj=$("#dysj").val();
		var dprtcode=$("#dprtcode").val();
		var wcrqStart="";
		var wcrqEnd="";
		 if(wcrq!=''){
			 wcrqStart=wcrq.substring(0,10);
			 wcrqEnd=wcrq.substring(13,23);
		 }		
		 var dyrqStart="";
		 var dyrqEnd="";
		 if(dysj!=''){
			 dyrqStart=dysj.substring(0.10);
			 dyrqEnd=dysj.substring(13,23);
		 }
		window.open(basePath+"/productionplan/plantask/historyOut.xls?fjzch="+encodeURIComponent(fjzch)+"&rwlx="+rwlx+"&keyword="+encodeURIComponent(keyword)+"&rwzlx="+rwzlx
		 		+"&zt="+zt+"&wcrqStart="+wcrqStart+"&wcrqEnd="+wcrqEnd+"&dprtcode="+dprtcode+"&dyrqStart="+dyrqStart+"&dyrqEnd="+dyrqEnd);		
 
	}
	
	/**
	 * 下载附件
	 */
	function downloadfile(id) {
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
	
	function getAttacment(list){
		var attachment='';
		if(list!=null){
				if (list.length>0) {
					var attachments = list;
					var len = list.length;
					if (len > 0) {
						for (var i = 0; i < len; i++) {					
						choses.push(attachments[i].id);						
						if (i == 1) {
							attachment = attachment
									+ "　<i class='icon-caret-down' id='"
									+ attachments[i].id
									+ "icon' onclick=switchFj('"
									+ attachments[i].id + "')></i><div id='"
									+ attachments[i].id
									+ "' style='display:none'>";
							}
						if(i==0){
							attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+StringUtil.escapeStr(attachments[i].wbwjm)+"</a>";
						}
						if (i != 0) {
							attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+StringUtil.escapeStr(attachments[i].wbwjm)+"</a>";
							attachment += "<br>";

						}
						if (i != 0 && i == len - 1) {
							attachment += "</div>";
						}
					}
					 }
				}
		}
		return attachment;
	}
	 
	function vieworhideFj(){
		new Sticky({tableId:'repairRecord_check_list_table'});
		var obj = $("th[name='fj']");
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
	
	function switchFj(id) {
		new Sticky({tableId:'repairRecord_check_list_table'});
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
	
	