$(document).ready(function(){

});

function AjaxGetDatasWithSearch1(pageNumber,sortType,sequence){
	if(!isLoadMonitor){
		loadMonitorsettings();
	}
	var obj ={};
	var searchParam = gatherSearchParam1();
	obj.pagination = {sort:sortType,order:sequence};
	obj.fjzch = searchParam.fjzch;
	obj.dprtcode = searchParam.dprtcode;
	obj.keyword = $.trim($("#keyword_search1").val());
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/productionplan/timecontrollwareitem/queryTimeControllWareItemList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
	    if(data.rows!=undefined&&data.rows.length>0){
			   appendContentHtml1(data.rows);
			// 标记关键字
			   signByKeyword($("#keyword_search1").val(),[4,5,14,15,16,17,18]);
			   
		} else {
			  $("#list1").empty();
			  $("#pagination").empty();
			  $("#list1").append("<tr><td colspan=\"20\">暂无数据 No data.</td></tr>");
		}
	    new Sticky({tableId:'skj'});
      }
    }); 
	
 }

//将搜索条件封装 
function gatherSearchParam1(){
	 var searchParam = {};
	 searchParam.fjzch = $("#fjzch_search").val();
	 searchParam.dprtcode = $("#dprtcode").val();
	 return searchParam;
}

function appendContentHtml1(list){
	
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   var yc = row.yc.split(",");//逗号分割计划值
		   var jhcss="";
		   for (var i = 0; i < yc.length; i++) {
			   jhcss+=yc[i]+"<br>";
			}
		   jhcss=jhcss.substring(0,jhcss.length-1);
		   
		   var sy = row.sy.split(",");//逗号分割剩余值
		   var sys="";
		   for (var i = 0; i < sy.length; i++) {
			   sys+=sy[i]+"<br>";
			}
		   sys=sys.substring(0,sys.length-1);
		   
		   var fengzhi = row.fengzhi.split(",");//逗号分割剩余值
		   var fengzhis="";
		   for (var i = 0; i < fengzhi.length; i++) {
			   fengzhis+=fengzhi[i]+"<br>";
			}
		   fengzhis=fengzhis.substring(0,fengzhis.length-1);
		   
		   if(parseInt(row.syts)<=parseInt(yjtsJb1)){
			   htmlContent = htmlContent + "<tr bgcolor=\""+warningColor.level1+"\" >";
		   }else if(parseInt(row.syts)>parseInt(yjtsJb1)&&parseInt(row.syts)<=parseInt(yjtsJb2)){
			   htmlContent = htmlContent + "<tr bgcolor=\""+warningColor.level2+"\">";
		   }else{
			   htmlContent = htmlContent + "<tr >";
		   }
		   
		   htmlContent = htmlContent + "<td  class='fixed-column' style='vertical-align: middle;'>";
		   if(row.gdid==null||row.gdzt==8||row.gdzt==9||row.gdzt==4){
			   htmlContent = htmlContent + "<i class='icon-reorder color-blue cursor-pointer checkPermission' onClick=\"subgds('"
				+ row.id + "')\" permissioncode='productionplan:scheduledcheckitem:main:03' title='生成工单  Submit Work Order'></i>"; 
		   }
		   
		   if(row.gdid!=null&&row.gdzt!=8&&row.gdzt!=4&&row.gdzt!=9&&row.rwdh==null){
			   htmlContent = htmlContent + "<i class='icon-file-alt color-blue cursor-pointer checkPermission' onClick=\"subjh('"
				+ row.id + "')\" permissioncode='productionplan:scheduledcheckitem:main:04' title='提交计划  Submit Production'></i>"; 
		   }
		   
		   if(row.gdzt!=0&&row.gdzt!=1&&row.gdzt!=2&&row.gdzt!=4&&row.gdzt!=5&&row.gdzt!=6&&row.gdzt!=8&&row.gdzt!=9&&row.rwdh!=null&&row.gdid!=null){
		   }	
		 
		   htmlContent = htmlContent +  "&nbsp;&nbsp;<i class='icon-edit color-blue cursor-pointer checkPermission' jkbz='"+formatUndefine(StringUtil.escapeStr(row.jkbz) )+"' ywmc='"+formatUndefine(StringUtil.escapeStr(row.ywmc) )+"' xlh='"+formatUndefine(StringUtil.escapeStr(row.xlh) )+"' jh='"+formatUndefine(StringUtil.escapeStr(row.jh) )+"' onClick=\"edit1('"
		   + formatUndefine(row.id) + "',event)\" permissioncode='productionplan:scheduledcheckitem:main:05' title='编辑备注  Edit Remark'></i>"; 
		   htmlContent = htmlContent + "</td>"; 
		   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-center'>"+formatUndefine(cjs[row.cj])+"</td>"; 
		   htmlContent = htmlContent + "<td   style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.ywmc) )+"'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.ywmc)  )+"</span></td>";  
		   htmlContent = htmlContent + "<td  style='vertical-align: middle; ' class='text-left'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.xlh)  )+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-left'>"+ formatUndefine(jhcss) + "</td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+formatUndefine(fengzhis)+"</td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+formatUndefine(sys)+"</td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; text-align:right;' ><input name='syts' value='"+formatUndefine(row.syts)+"' type='hidden'/>"+formatUndefine(row.syts)+"</td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle;'  class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.jkbz)  )+"'>"+formatUndefine(StringUtil.escapeStr(row.jkbz))+"</td>"; 
		   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-left'>"+formatUndefine(zts[row.wz])+"</td>";
		   htmlContent = htmlContent + "<td style='vertical-align: middle;'  class='text-left'><a href='javascript:void(0);' onclick=\"viewDetailjd('"+row.gdid+"')\" >"+formatUndefine(StringUtil.escapeStr(row.gdbh) )+"</a></td>"; 
	       htmlContent = htmlContent + "<td style='vertical-align: middle;'  class='text-left'>"+formatUndefine(StringUtil.escapeStr(row.rwdh) )+"</td>"; 
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.jh))+"'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.jh))+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.zwmc) )+"'><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.zwmc) )+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; 'class='text-left' ><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.zjh+" "+StringUtil.escapeStr(row.zjhzwms)) )+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'><span name='keyword'>"+formatUndefine(row.tsn)+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'><span name='keyword'>"+formatUndefine(row.tso)+"</span></td>";  
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' >"+formatUndefine(bjlxs[row.kzlx])+"</td>"; 
		 
		   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+AccessDepartmentUtil.getDpartName(formatUndefine(StringUtil.escapeStr(row.dprtcode) ))+"</td>"; 
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#list1").empty();
	   $("#list1").html(htmlContent);
	   refreshPermission();
}

//查看详情
function viewDetailjd(gdid){
	window.open(basePath+"/project/workorder/intoEdit?id="+gdid + "&gddlx=2");
}

//编辑备注
function edit1(id,e){
	var jkbz = $(e.target).attr("jkbz");
	var ywmc = $(e.target).attr("ywmc");
	var xlh = $(e.target).attr("xlh");
	var jh = $(e.target).attr("jh");
	
	
	 $("#ywmc1").val(ywmc);
	 $("#xlh1").val(xlh);
	 $("#jh1").val(jh);
	 $("#jkid1").val(id);
	 $("#jkbz1").val(jkbz);
	 $("#alertModalview1").modal("show");
}

//确认编辑备注
function sbDown1() {
	 var jkid = $("#jkid1").val();
	 var jkbz = $.trim($("#jkbz1").val());
	
	 var reserve = {
			 id : jkid,
			 jkbz : jkbz
	 };
	 startWait();
	 AjaxUtil.ajax({
		 url:basePath + "/productionplan/timecontrollwareitem/saveJkbz",
		 contentType:"application/json;charset=utf-8",
		 type:"post",
		 async: false,
		 data:JSON.stringify(reserve),
		 dataType:"json",
		 success:function(data){
			 finishWait();
			 AlertUtil.showMessage('保存成功!');
			 $("#alertModalview1").modal("hide");
			 searchRevision1();
			 refreshPermission();
		 }
	 });
}


//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage1(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch1(pageNumber,sortType,sequence);
}

//信息检索
function searchRevision1(){
	goPage1(1,"auto","desc");
}

//搜索条件显示与隐藏 
function search1() {
	if ($("#divSearch1").css("display") == "none") {
		$("#divSearch1").css("display", "block");
		$("#icon1").removeClass("icon-caret-down");
		$("#icon1").addClass("icon-caret-up");
	} else {
		$("#divSearch1").css("display", "none");
		$("#icon1").removeClass("icon-caret-up");
		$("#icon1").addClass("icon-caret-down");
	}
}

/**
 * 更改选中的飞机
 */
function changeSelectedPlane(){
	 goPage1(1,"auto","desc");//开始的加载默认的内容 
}

//时控件监控：生成工单
function subgds(id){
	//验证定检是否可以提交计划
	 if(checkdg(id)){
	var timeControllWareItem = {};
	timeControllWareItem.id = id;
	/*
	 * bug:3182 
	 * author:hanwu
	 * date:20170624
	 */
	timeControllWareItem.fjzch = $("#fjzch_search").val();
	timeControllWareItem.dprtcode = $("#dprtcode").val();
			
	AlertUtil.showConfirmMessage("您确定要生成工单吗？",{callback: function(){
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/productionplan/timecontrollwareitem/subgd",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(timeControllWareItem),
		dataType:"json",
		success:function(data){
				finishWait();//结束Loading
			if (data.state == "success") {
				AlertUtil.showMessage('生成工单成功!');
				goPage1(1,"auto","desc");//开始的加载默认的内容 
				
				/*$("#alertModal").modal("show");
					$('#alertModal').on('hidden.bs.modal', function (e) {
						window.location = basePath+"/productionplan/scheduledcheckitem/main?t=" + new Date().getTime()+"#timeMonitor";
					});*/
				
			} else {
				AlertUtil.showErrorMessage("提交失败!");
			}
		}
	});
				}});
	 }
 }

//时控件监控：提交计划
function subjh(id){
	//验证定检是否可以提交计划
	 if(checkskj(id)){
			var timeControllWareItem = {};
			timeControllWareItem.id = id;
			AlertUtil.showConfirmMessage("您确定要提交计划吗？",{callback: function(){
			startWait();
			// 提交数据
			AjaxUtil.ajax({
				url : basePath+"/productionplan/timecontrollwareitem/subjh",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(timeControllWareItem),
				dataType:"json",
				success:function(data){
						finishWait();//结束Loading
					if (data.state == "success") {
						
						AlertUtil.showMessage('提交计划成功!');
						goPage1(1,"auto","desc");//开始的加载默认的内容 
						
					/*	$("#alertModal").modal("show");
							$('#alertModal').on('hidden.bs.modal', function (e) {
								window.location = basePath+"/productionplan/scheduledcheckitem/main?t=" + new Date().getTime()+"#timeMonitor";
						});
						$("#alertModalBody").html("提交计划成功!");*/
					} else {
						AlertUtil.showErrorMessage("提交失败!");
					}
				}
			});
		 
			}});
	 }
	 
 }

//时控件是否可以提交工单
function checkdg(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath+"/productionplan/timecontrollwareitem/checkdg",
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


//时控件是否可以提交计划
function checkskj(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath+"/productionplan/timecontrollwareitem/checkUpdMt",
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
function orderBy1(obj, _obj) {
	$obj = $("#skj th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#skj .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#skj .sorting_asc").removeClass("sorting_asc").addClass("sorting");
	
	var order = "asc";
	if (orderStyle.indexOf ("sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		order = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		order = "asc";
	}
	var currentPage = $("#pagination1 li[class='active']").text();
	goPage1(currentPage,obj, order);
}
/**
 * 导出excel
 */
function timeOutExcel() {
	
	var searchParam = gatherSearchParam1();
	var fjzch = searchParam.fjzch;
	var dprtcode = searchParam.dprtcode;
	var keyword = $.trim($("#keyword_search1").val());
	var lx=3;
	window.open(basePath+"/productionplan/scheduledcheckitem/scheduledcheckitem.xls?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+dprtcode+"&keyword="+encodeURIComponent(keyword)+"&lx="+lx);

}