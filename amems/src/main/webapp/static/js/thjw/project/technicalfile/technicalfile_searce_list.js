var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束"];
var ids='';
var choseAllIds=[];
var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
$(document).ready(function(){
		 goPage(1,"auto","desc");// 开始的加载默认的内容
		 // 菜单设置 自己设置
			var dprtcode=$("#zzjg").val();
			DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
			DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
			DicAndEnumUtil.registerDic('8','ly',dprtcode);
			//导航
			Navigation(menuCode);
			//初始化日志功能
			logModal.init({code:'B_G_001'});
			
			fjjxjz();
			  
			  
	});
	function fjjxjz(){
		var dprtcode = $.trim($("#zzjg").val());
		 var planeRegOption = '';
		 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
		 if(planeDatas != null && planeDatas.length >0){
		  	$.each(planeDatas,function(i,planeData){
		  	    planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
		  	});
		 }
		  /*if(planeRegOption == ''){
		  	planeRegOption = '<option value="">暂无飞机 No plane</option>';
		  }*/
		 var htmlplan='<option value="">显示全部</option>';
		  $("#jx").empty();
		  $("#jx").append(htmlplan);
		  $("#jx").append(planeRegOption);
	}
	 // 带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
		obj.keyword=$.trim($('#keyword_search').val());
		obj.jx = searchParam.jx;
		obj.fl = searchParam.fl;
		obj.wjlx = searchParam.wjlx;
		obj.ly = searchParam.ly;
		obj.zt = searchParam.zt;
		obj.pgrid=searchParam.pgrid;
		obj.zdrid=searchParam.zdrid;
		obj.dprtcode=searchParam.dprtcode;
		
		var sxrq=searchParam.sxrq;
		var pgqx=searchParam.pgqx;
		
		 if(null != sxrq && "" != sxrq){
			 var sxrqBegin = sxrq.substring(0,10)+" 00:00:00";
			 var sxrqEnd = sxrq.substring(13,23)+" 23:59:59";
			 paramsMap.sxrqBegin = sxrqBegin;
			 paramsMap.sxrqEnd = sxrqEnd;
		 }
		 if(null != pgqx && "" != pgqx){
			 var pgqxBegin = pgqx.substring(0,10)+" 00:00:00";
			 var pgqxEnd = pgqx.substring(13,23)+" 23:59:59";
			 paramsMap.pgqxBegin = pgqxBegin;
			 paramsMap.pgqxEnd = pgqxEnd;
		 }
		 obj.paramsMap=paramsMap;
		// obj.yjjb = $("#yjjb").val();
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/project/technicalfile/queryTechnicalFileListUpload",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   yjtsJb1 =data.monitorsettings.yjtsJb1;
				   yjtsJb2 =data.monitorsettings.yjtsJb2;
				   yjtsJb3 =data.monitorsettings.yjtsJb3;
				   appendContentHtml(data.rows);
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}//,
				   });
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,7,8,17,18]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td class='text-center' colspan=\"20\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
	 // 将搜索条件封装
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.jx = $("#jx").val();
		 searchParam.fl = $("#fl").val();
		 searchParam.wjlx = $("#wjlx").val();
		 searchParam.ly = $("#ly").val();
		 searchParam.zt = $("#zt").val();
		 searchParam.pgrid=$("#jxgcs").val();
		 searchParam.dprtcode=$("#zzjg").val();
		 searchParam.pgqx=$("#pgqx").val();
		 searchParam.sxrq=$("#sxrq").val();
		 return searchParam;
	 }
	 
	 function appendPaginationHtml(data,sortType,sequence){
			   
			   var viewpagecount = 10;// 每页显示12个分页便签
			   var param = data.rows; // 页面数据
			   var total = data.total;// 总数据量
			   var size =  data.pageable.rows;// 每页显示的条目数
			   var pageCount = total % size==0? parseInt(total/size):parseInt(total/size)+1;// 总的页数
			   var currentPage = 1; // 记录当前页码
			   var subpagination ="";
			   
			   $('#paginationSummary #total').html(total);
			   $('#paginationSummary #pageCount').html(pageCount);
			   $('#paginationSummary #size').html(size);
			   for (var i=0;i<pageCount;i++){
				   if (data.pageable.page == (i+1)) {
				  	 currentPage = i + 1;
				  	 break;
				   }
			   }
			   
			   var startpage = currentPage - (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1: viewpagecount / 2);// 起始页
			   var endpage = currentPage + viewpagecount / 2; // 结束页
			   if (startpage < 1) {
					startpage = 1;
					if (pageCount >= viewpagecount){
						endpage = viewpagecount;
					}else{
						endpage = pageCount;
					}
				}
			   
			   if (endpage > pageCount) {
					endpage = pageCount;
					if ((endpage - viewpagecount) > 0){
						startpage = endpage - viewpagecount + 1;
					}else{
						startpage = 1;
					}
				}
			   var paginationContent = "";
			   if (currentPage==1) { // 当前为第一页时,不能向前翻页
				   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
				   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;</a></li>";
			   } else {
				   paginationContent = paginationContent 
	               + "<li onclick=\"goPage(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
				   paginationContent = paginationContent 
	               + "<li onclick=\"goPage("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
			   }
			   
			   for (var index = startpage;index <= endpage;index++){
				   if (index==currentPage){
					   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
				   }else {
					   paginationContent = paginationContent + "<li onclick=\"goPage("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
				   }
			   }
			   if (currentPage ==pageCount){
				   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
				   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
			   }else {
				   paginationContent = paginationContent + "<li onclick=\"goPage("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
				   paginationContent = paginationContent + "<li onclick=\"goPage("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
			   }
			   $("#pagination").empty();
			   $("#pagination").html(paginationContent);
		 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   choseAllIds=[];
		   $.each(list,function(index,row){
			   choseAllIds.push(row.id);
			   if (index % 2 == 0) {
				   htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.syts,row.zt)+"\" >";
				   
			   } else {
				   htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.syts,row.zt)+"\" >";
			   }
			      
			   /*htmlContent = htmlContent + "<td class='text-center'>"+ "<i class='icon-edit color-blue cursor-pointer' onClick=\"edit('"
				+ row.id + "')\" title='修改'></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer' onClick=\"Delete('"
				+ row.id + "')\" title='作废 '></i>&nbsp;&nbsp;<i class='icon-remove-sign color-blue cursor-pointer' onClick=\"AssignStore('"
				+ row.id + "',"+row.zt+",'"+row.pgdh+"')\" title='指定结束 '></i>"+"</td>";*/
			   htmlContent = htmlContent
				+ "<td class='text-center fixed-column'>"
				+ "<i class='icon-print color-blue cursor-pointer '  onClick=\"printTechnicalApproval('"
				+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;</td>";
			   htmlContent = htmlContent + "<td class='text-center fixed-column'><a href=\"javascript:find('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"</a></td>";   
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ly))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.fl))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjlx))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"'>"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+indexOfTime(row.sxrq)+"</td>";
			   htmlContent = htmlContent + "<td class='text-center'>"+indexOfTime(row.pgqx)+"</td>";
			   if(row.zt==3 || row.zt==4 || row.zt==9){
				   htmlContent = htmlContent + "<td class='text-center'>√</td>";  
			   }else{
				   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.syts))+"</td>";  
			   }
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.pgr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.pgr_user.displayName))+"</td>"; 
			   if(row.zt==9){
				   var zdjsr = row.zdjs_user?row.zdjs_user.displayName:'';
				   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
				   "pgdh='"+StringUtil.escapeStr(row.pgdh)+"' "+
				   "zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' "+
				   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
				   " onclick=\"shutDown('"+row.id+"',this,'"+row.zdjsrq+"',false)\">"+formatUndefine(zts[row.zt])+"</a></td>";
				  /*
					 * htmlContent = htmlContent + "<td class='text-left'><a
					 * onclick='viewreason(\""+row.pgdh+"\",\""+row.zdjsyy+"\")'>"+formatUndefine(zts[row.zt])+"</a></td>";
					 */
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
			   }
			   htmlContent = htmlContent + "<td class='text-left'>"; 
			   if(row.zlbh!=null && row.zlbh!=""){
				   var strs= new Array(); // 定义一数组
				   strs=row.zlbh.split(",&sun1"); // 字符分割
				   for (i=0;i<strs.length ;i++ ){
					   var bh=strs[i].split("(")[0];
					   
					   if(i==1){
						   htmlContent = htmlContent +"　<i class='icon-caret-down' id='"+row.id+"icon' onclick=vieworhideWorkContent('"+row.id+"')></i><div id='"+row.id+"' style='display:none'>"; 
					   }
					   
					htmlContent = htmlContent +"<a href='javascript:void(0);' bh='"+StringUtil.escapeStr(bh)+"' dprtcode='"+row.dprtcode+"'onclick=\"selectByzlbh(this)\">"+StringUtil.escapeStr(strs[i])+"</a>";
					if(i!=0){
						htmlContent = htmlContent +"<br>";
						
					}
					if(i!=0 && i==strs.length-1)  {
						htmlContent = htmlContent +"</div>"; 
					}
						
					   
				   } 
			   }
			   htmlContent = htmlContent + "</td>"; 
			   
			   var nbwjm=row.technicalUpload.nbwjm;
			   var hzm="";
			   if(nbwjm!=null && nbwjm!=""){
				   var str= new Array(); // 定义一数组
				   str=nbwjm.split("."); // 字符分割
				   hzm=str[1];
			   }
			  
				var wj=row.technicalUpload.wbwjm+"."+hzm
				htmlContent = htmlContent
						+ "<td class='text-left'><a href=\"javascript:downfile('"+row.technicalUpload.id+"')\">"
						+ StringUtil.escapeStr(formatUndefine(wj))+ "</a></td>";
				htmlContent = htmlContent
						+ "<td title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"' class='text-left'>"
						+StringUtil.escapeStr(formatUndefine(row.bz))+ "</td>";
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.zdr_user.displayName))+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";  
			   
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
	 }	
	 function vieworhideWorkContent(id){
		 new Sticky({tableId:'quality_check_list_table'});
		 var flag = $("#"+id).is(":hidden");
		 if(flag){
			 $("#"+id).fadeIn(500);
			 $("#"+id+'icon').removeClass("icon-caret-down");
			 $("#"+id+'icon').addClass("icon-caret-up");
		 }else{
			 $("#"+id).hide();
			 $("#"+id+'icon').removeClass("icon-caret-up");
			 $("#"+id+'icon').addClass("icon-caret-down");
		 }
		 
	 }
	 function vieworhideWorkContentAll(){
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
		 new Sticky({tableId:'quality_check_list_table'});
	 }
	 
	 // 修改技术文件上传
	 function edit(id){
		 if(checkUpdMt1(id)){
		 window.location =basePath+"/project/technicalfile/initModifyFileUpload/"+$.trim(id)+"";
		 }
	 }
	 
	 // 指令指定
		function AssignStore(id,zt,pgdh){
			 if(checkUpdMt(id)){
				ids=id;
				$('#pgdh').val(pgdh);
				$("#alertModalStore").modal("show");
			 }
		}
		
		// 查看指定结束界面
		function viewreason(pgdh,reason){
				$('#pgddh').val(pgdh);
				$('#reasonfor').val(reason);
				$("#alertviewreason").modal("show");
			 }
		
		// 指定结束保存
		function saveRoleToStore(selectNode){
			var zdjsyy=$.trim($('#zdjsyy').val());
			if(zdjsyy==null || zdjsyy==""){
				AlertUtil.showErrorMessage('结束原因不能为空');
				return 
			}
			
			
			var fixedCheckItem = {
					"id" :ids,// id
					"zdjsyy" : zdjsyy// 指定结束原因
				};
			startWait();
			// 提交数据
			AjaxUtil.ajax({
				url : basePath+"/project/technicalfile/modifyModifyFileStop",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(fixedCheckItem),
				dataType:"json",
				success:function(data){
						finishWait();// 结束Loading
					if (data.state == "Success") {
						// $("#alertModalStore").modal("close");
						AlertUtil.showErrorMessage("指定结束成功!");
						goPage(1,"auto","desc");
					} else {
						AlertUtil.showErrorMessage(data.text);
					}
				}
			});


		}
	 
	// 检查技术文件指定结束
	 function checkUpdMt(id){
	 	var flag = false;
	 	AjaxUtil.ajax({
	 		url:basePath+"/project/technicalfile/checkUpdMt",
	 		type:"post",
	 		async: false,
	 		data:{
	 			'id' : id
	 		},
	 		dataType:"json",
	 		success:function(data){
	 			finishWait();// 结束Loading
	 			if (data.state == "success") {
	 				flag = true;
	 			} else {
	 				AlertUtil.showErrorMessage(data.message);
	 			}
	 		}
	 	});
	 	return flag;
	 }
	 // 检查技术文件可以修改
	 function checkUpdMt1(id){
		 var flag = false;
		 AjaxUtil.ajax({
			 url:basePath+"/project/technicalfile/checkUpdMt1",
			 type:"post",
			 async: false,
			 data:{
				 'id' : id
			 },
			 dataType:"json",
			 success:function(data){
				 finishWait();// 结束Loading
				 if (data.state == "success") {
					 flag = true;
				 } else {
					 AlertUtil.showErrorMessage(data.message);
				 }
			 }
		 });
		 return flag;
	 }
	 
	 // 作废技术文件
	 function Delete(id){
		 var messager=confirm("你确定要作废吗?");
			if(messager){
				startWait();// 开始Loading
				AjaxUtil.ajax({
								type : "post",
								url : basePath+"/project/technicalfile/removeModifyFileUpload/"+$.trim(id)+"",
								dataType : "json",
								success : function(data) {
									finishWait();// 结束Loading
								 if (data.state=="Success"){
									 	goPage(1,"auto","desc");
									 	AlertUtil.showErrorMessage("作废成功!");
									}else{
										AlertUtil.showErrorMessage(data.message);
									}
								
								}
							});
			   }
	 }
	 
	 // 查看
	 function find(id,dprtcode){
		 window.open(basePath+"/project/technicalfile/findTechnicalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	 }
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
	 	var currentPage = $("#pagination li[class='active']").text();
	 	goPage(currentPage,obj,orderStyle.split("_")[1]);
	 }
	
		 // 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
		 // 信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
		
	 // 搜索条件重置
	 function searchreset(){
		 var zzjgid=$('#zzjgid').val();
		 var yjts=$('#yjts').val();
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
		 $("#zzjg").val(zzjgid);
		 $("#yjjb").val(yjts);
		 changeContent();
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
		 
		 // 回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		}
		 
		// 只能输入数字和小数
		 function clearNoNum(obj){
		      // 先把非数字的都替换掉，除了数字和.
		      obj.value = obj.value.replace(/[^\d.]/g,"");
		      // 必须保证第一个为数字而不是.
		      obj.value = obj.value.replace(/^\./g,"");
		      // 保证只有出现一个.而没有多个.
		      obj.value = obj.value.replace(/\.{2,}/g,".");
		      // 保证.只出现一次，而不能出现两次以上
		      obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		 }
		 
		 
		 
		 // 改变机型工程师和制单人 列表内容
		 function changeContent(){
				var dprtcode=$("#zzjg").val();
				$("#fl").empty();
				$("#fl").append("<option value='' >显示全部</option>");
				$("#wjlx").empty();
				$("#wjlx").append("<option value='' >显示全部</option>");
				$("#ly").empty();
				$("#ly").append("<option value='' >显示全部</option>");
				DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
				DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
				DicAndEnumUtil.registerDic('8','ly',dprtcode);
			 var obj={};
			 obj.jgdm=$('#zzjg').val();
			 AjaxUtil.ajax({
			 		url:basePath+"/project/technicalfile/changeContent",
			 		type:"post",
			 		async: false,
			 		contentType:"application/json;charset=utf-8",
			 		data:JSON.stringify(obj),
			 		dataType:"json",
			 		success:function(data){
			 			finishWait();// 结束Loading
			 			$('#zdrid').empty();
			 			 $('#jxgcs').empty();
			 			appendChangeContent(data.userToRole);
			 			
			 		}
			 	});
			 fjjxjz();
		 }
	// 拼接机型工程师和制单人 列表内容
		 function appendChangeContent(data){
			 var appendChangeContent='';
			 appendChangeContent=appendChangeContent+"<option value=''>显示全部</option>";
			 $.each(data,function(index,row){
				 appendChangeContent=appendChangeContent+"<option value='"+row.id+"'>"+StringUtil.escapeStr(row.displayName)+"</option>";
			 });
			 $('#zdrid').html(appendChangeContent);
			 $('#jxgcs').html(appendChangeContent);
			 
		 }
	// 查看指 令页面
		 function selectByzlbh(obj){
			 var zlbh=$(obj).attr("bh");
			 var dprtcode=$(obj).attr("dprtcode");
			 window.open(basePath+"/project/technicalfile/selectByzlbh?zlbh="+encodeURIComponent(zlbh)+"&dprtcode="+encodeURIComponent(dprtcode)+"");
		 }
		 /**
		  * 导出
		  */
		 function technicalFileUploadOutExcel() {
			 window.open(basePath+"/project/technicalfile/technicalFileOutExcel.xls?jx=" + encodeURIComponent($("#jx").val()) + "&fl="
			 			+ encodeURIComponent($("#fl").val())+"&wjlx="+$("#wjlx").val() + "&zt=" + $("#zt").val() + "&pgrid="
			 			+ encodeURIComponent($("#jxgcs").val()) + "&ly=" + encodeURIComponent($("#ly").val()) + "&dprtcode="
			 			+ $("#zzjg").val()  + "&keyword="
			 			+ encodeURIComponent($.trim($("#keyword_search").val())));
		 }

//附件下载
function downfile(id){
	//window.open(basePath + "/project/technicalfile/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.B_G_00101)
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
//指定结束
function shutDown(id,obj,zdjsrq,isEdit){
	var sqdh=$(obj).attr("pgdh");
	var zdjsyy=$(obj).attr("zdjsyy");
	var zdjsr=$(obj).attr("zdjsr");
	
	AssignEndModal.show({
		chinaHead:'评估单号',//单号中文名称
		englishHead:'Assessment NO.',//单号英文名称
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
				saveRoleToStore(obj);
			}
		}
	});
}
	
	// 指定结束保存
	function saveRoleToStore(fixedCheckItem){
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url : basePath+"/project/technicalfile/modifyModifyFileStop",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
					finishWait();// 结束Loading
				if (data.state == "Success") {
					// $("#alertModalStore").modal("close");
					AlertUtil.showErrorMessage("指定结束成功!");
					id=fixedCheckItem.id;
					refreshPage();
					$("#AssignEndModal").modal("hide");
				} else {
					AlertUtil.showErrorMessage(data.text);
				}
			}
		});


	}

// 检查技术文件指定结束
function checkUpdMt(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath+"/project/technicalfile/checkUpdMt",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			finishWait();// 结束Loading
			if (data.state == "success") {
				flag = true;
			} else {
				AlertUtil.showErrorMessage(data.message);
			}
		}
	});
	return flag;
}

/**
 * 打印
 * @param id
 */
function printTechnicalApproval(id){
	var url=basePath+"/project/technicalfile/export/pdf/"+id;
	window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
			'PDF','width:50%;height:50%;top:100;left:100;');
}

