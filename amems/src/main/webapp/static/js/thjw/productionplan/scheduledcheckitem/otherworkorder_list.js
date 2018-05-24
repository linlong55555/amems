$(document).ready(function(){
});

function AjaxGetDatasWithSearch2(pageNumber,sortType,sequence){
	if(!isLoadMonitor){
		loadMonitorsettings();
	}
	var obj ={};
	var searchParam = gatherSearchParam2();
	obj.fjzch = searchParam.fjzch;
	obj.dprtcode = searchParam.dprtcode;
	obj.keyword = $.trim($("#keyword_search2").val());
	obj.pagination = {sort:sortType,order:sequence};
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/productionplan/otherworkorder/queryOtherWorkOrdertemList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.rows.length>0){
			   appendContentHtml2(data.rows);
			// 标记关键字
			   signByKeyword($("#keyword_search2").val(),[3,5,10,12,13]);
			   
		   } else {
			  $("#list2").empty();
			  $("#pagination").empty();
			  $("#list2").append("<tr><td colspan=\"14\">暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'qtgd'});
     }
   }); 
	
}


//将搜索条件封装 
function gatherSearchParam2(){
	 var searchParam = {};
	 searchParam.fjzch = $("#fjzch_search").val();
	 searchParam.dprtcode = $("#dprtcode").val();
	 return searchParam;
}

function appendContentHtml2(list){
	
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   var jihua = row.jihua.split(",");//逗号分割计划值
		   var jihuas="";
		   for (var i = 0; i < jihua.length; i++) {
			   jihuas+=jihua[i]+"<br>";
			}
		   jihuas=jihuas.substring(0,jihuas.length-1);
		   
		   var shengyu = row.shengyu.split(",");//逗号分割剩余值
		   var shengyus="";
		   for (var i = 0; i < shengyu.length; i++) {
			   shengyus+=shengyu[i]+"<br>";
			}
		   shengyus=shengyus.substring(0,shengyus.length-1);
		  
		   if(parseInt(row.shengyut)<=parseInt(yjtsJb1)){
			   htmlContent = htmlContent + "<tr bgcolor=\""+warningColor.level1+"\">";
		   }else if(parseInt(row.shengyut)>parseInt(yjtsJb1)&&parseInt(row.shengyut)<=parseInt(yjtsJb2)){
			   htmlContent = htmlContent + "<tr bgcolor=\""+warningColor.level2+"\">";
		   }else{
			   htmlContent = htmlContent + "<tr >";
		   }
		   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align: middle; ' >";
		   
		   if(row.rwid==null){
			   htmlContent = htmlContent + "<i class='icon-file-alt color-blue cursor-pointer checkPermission' permissioncode='productionplan:scheduledcheckitem:main:06'  onClick=\"qtgdtj('"+ StringUtil.escapeStr(row.gdbh)  + "')\" title='提交计划  Submit Production'></i>&nbsp;&nbsp;";
		   }
		   if(row.rwid!=null){
			   htmlContent = htmlContent + ""; 
		   }
		   htmlContent = htmlContent +  "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='productionplan:scheduledcheckitem:main:07'  gdbh='"+formatUndefine(StringUtil.escapeStr(row.gdbh) )+"' gdlx='"+formatUndefine(row.gdlx)+"' jkbz='"+formatUndefine(StringUtil.escapeStr(row.jkbz))+"' onClick=\"edit2('"
		   + formatUndefine(row.gdid) + "','"+formatUndefine(row.gddl)+"','"+formatUndefine(StringUtil.escapeStr(row.gdbh) )+"',event)\" title='编辑备注  Edit Remark'></i>"; 
		   
		   htmlContent = htmlContent +"</td>"; 
		   htmlContent += "<td   style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		  
		   htmlContent = htmlContent + "<td   style='vertical-align: middle; ' class='text-left'><a  href='javascript:void(0);' onclick=\"viewDetailother('"+row.gdid+"','"+row.gddlx+"')\"><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.gdbh))+"</span></a></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; 'class='text-left' >"+formatUndefine(row.gdlx)+"</td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.bjxlh) )+"</span></td>"; 
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+formatUndefine(jihuas)+"</td>";//计划值
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+formatUndefine(shengyus)+"</td>";//剩余
			   htmlContent = htmlContent + "<td style='vertical-align: middle; text-align:right;'>"+row.shengyut+"</td>";//剩余天数
		   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.rwdh))+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.jkbz) )+"'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.jkbz))+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.fjzch) )+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.bjh))+"'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.bjh) )+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; 'class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.gdms) )+"'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.gdms))+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+AccessDepartmentUtil.getDpartName(formatUndefine(StringUtil.escapeStr(row.dprtcode) ))+"</td>"; 
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#list2").empty();
	   $("#list2").html(htmlContent);
	   refreshPermission();
}

//查看详情
function viewDetailother(gdid,gdzl){

	window.open(basePath+"/project/workorder/Looked?id="+gdid + "&gddlx="+gdzl);
}

//编辑备注
function edit2(id,gddl,gdbh,e){
	var jkbz = $(e.target).attr("jkbz");
	var gdbh = $(e.target).attr("gdbh");
	var gdlx = $(e.target).attr("gdlx");
	
	
	 $("#gdbh").val(gdbh);
	 $("#gdlx").val(gdlx);
	 
	 $("#jkid2").val(id);
	 $("#jkbz2").val(jkbz);
	 $("#gddl").val(gddl);
	 $("#gdbh").val(gdbh);
	 $("#alertModalview2").modal("show");
}

//确认编辑备注
function sbDown2() {
	 var jkid = $("#jkid2").val();
	 var jkbz = $.trim($("#jkbz2").val());
	 var gddl = $("#gddl").val();
	 var gdbh = $("#gdbh").val();
	
	 var reserve = {
			 id : jkid,
			 jkbz : jkbz,
			 gddl : gddl,
			 gdbh : gdbh
	 };
	 
	 startWait();
	 AjaxUtil.ajax({
		 url:basePath + "/productionplan/otherworkorder/saveJkbz",
		 contentType:"application/json;charset=utf-8",
		 type:"post",
		 async: false,
		 data:JSON.stringify(reserve),
		 dataType:"json",
		 success:function(data){
			 finishWait();
			 AlertUtil.showMessage('保存成功!');
			 $("#alertModalview2").modal("hide");
			 searchRevision2();
			 refreshPermission();
		 }
	 });
}



//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage2(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch2(pageNumber,sortType,sequence);
}	


//信息检索
function searchRevision2(){
	goPage2(1,"auto","desc");
}

//搜索条件显示与隐藏 
function search2() {
	if ($("#divSearch2").css("display") == "none") {
		$("#divSearch2").css("display", "block");
		$("#icon2").removeClass("icon-caret-down");
		$("#icon2").addClass("icon-caret-up");
	} else {
		$("#divSearch2").css("display", "none");
		$("#icon2").removeClass("icon-caret-up");
		$("#icon2").addClass("icon-caret-down");
	}
}

/**
 * 更改选中的飞机
 */
function changeSelectedPlane2(){
	 goPage2(1,"auto","desc");//开始的加载默认的内容 
}

//其他工单监控：提交计划
function qtgdtj(id){
	//验证其他工单是否可以提交计划
			 if(checkqt(id)){
					var timeControllWareItem = {};
					timeControllWareItem.id = id;
					AlertUtil.showConfirmMessage("您确定要提交计划吗？",{callback: function(){
					startWait();
					// 提交数据
					AjaxUtil.ajax({
						url : basePath+"/productionplan/otherworkorder/subjh",
						contentType:"application/json;charset=utf-8",
						type:"post",
						data:JSON.stringify(timeControllWareItem),
						dataType:"json",
						success:function(data){
								finishWait();//结束Loading
							if (data.state == "success") {
								
								AlertUtil.showMessage('生成计划成功!');
								goPage2(1,"auto","desc");//开始的加载默认的内容 
								
							/*	$("#alertModal").modal("show");
									$('#alertModal').on('hidden.bs.modal', function (e) {
										window.location = basePath+"/productionplan/scheduledcheckitem/main?t=" + new Date().getTime()+"#fixedMonitor";
									});
									
								$("#alertModalBody").html("提交计划成功!");		*/
							} else {
								AlertUtil.showErrorMessage("提交失败!");
							}
						}
					});
				 
					}});
			 }
			 
		 }



//其他工单是否可以提交计划
 function checkqt(id){
 	var flag = false;
 	AjaxUtil.ajax({
 		url:basePath+"/productionplan/otherworkorder/checkUpdMt",
 		type:"post",
 		async: false,
 		data:{
 			'id' : id
 		},
 		dataType:"json",
 		success:function(data){
 			finishWait();//结束Loading
 			if (data.state == "success") {
 				flag = true;
 			} else {
 				AlertUtil.showErrorMessage(data.message);
 			}
 		}
 	});
 	return flag;
 }
 
 
	//字段排序
	function orderBy2(obj, _obj) {
		$obj = $("#qtgd th[name='column_"+obj+"']");
		var orderStyle = $obj.attr("class");
		$("#qtgd .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#qtgd .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
		var order = "asc";
		if (orderStyle.indexOf ("sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			order = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			order = "asc";
		}
		var currentPage = $("#pagination2 li[class='active']").text();
		goPage2(currentPage,obj, order);
	}
	
	/**
	 * 导出excel
	 */
	function otherOutExcel() {
		
		var searchParam = gatherSearchParam2();
		var fjzch = searchParam.fjzch;
		var dprtcode = searchParam.dprtcode;
		var keyword = $.trim($("#keyword_search2").val());
		var lx=4;
		window.open(basePath+"/productionplan/scheduledcheckitem/scheduledcheckitem.xls?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+encodeURIComponent(dprtcode)+"&keyword="+encodeURIComponent(keyword)+"&lx="+lx);

	}