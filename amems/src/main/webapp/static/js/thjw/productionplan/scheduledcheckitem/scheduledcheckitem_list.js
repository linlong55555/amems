var zt=[0,1,2,3,4,5];
var zts=["机身","1#左发","2#右发","绞车","搜索灯","外吊挂"];
var cjs=["*","*",""];
var bulx=[1,2];
var bjlxs=["","时控件","时寿件"];
var oldStoreIds = '';
var roleId = '';
 
$(document).ready(function(){
	Navigation(menuCode);
	changeOrganization();
	//$('#dprtcode').trigger('change');
	
	$('#pre_scheduling_block').hide();
	$('#jzrqBlock').hide();
	 
	 goPage(1,"auto","desc");//开始的加载默认的内容 
	 initNav();
	 refreshPermission();
	 //zhuchao
	 $('.datepicker').datepicker({
			autoclose : true,
			clearBtn:true
	}).next().on("click", function() {
		$(this).prev().focus();
	});
	 
	 //监控模式-可以提交计划，否则不可以
	 $('input[name=jkfs]:radio').on('click',function(){
		 if($(this).val()=='PRE_SCHEDULING') {
			 $('#jsid').hide();
			 $("#sssss").hide();
			 
			 $('#jzrqBlock').show();
			 $('#pre_scheduling_block').show();
		 }
		 else {
			  
			  //监控后清除预排
			  $("#ypPanel #list").empty();
			  $("#ypPanel #pagination").empty();
			  new Sticky({tableId:'ypPanel'});
			  
			 $('#sssss').show();
			 $('#jsid').show();
			 
			 $('#pre_scheduling_block').hide();
			 $('#jzrqBlock').hide();
			 $("#jzrq").val('');
		 }
	 });
	 
	 
	 $('#dprtcode').on('change',function(){
		 	 
		 	var dprtcode = $.trim($("#dprtcode").val());
			var planeRegOption = '';
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:formatUndefine(dprtcode),FJJX:null});
			if(planeDatas != null && planeDatas.length >0){
				$.each(planeDatas,function(i,planeData){
					if(dprtcode == planeData.DPRTCODE){
						planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
					}
				});
				planeRegOption += '<option value="">非装机件 </option>';
			}
			if(planeRegOption == ''){
				planeRegOption = '<option value="">暂无飞机 No plane</option>';
			}
			$("#fjzch_search").html(planeRegOption);
			$("#fjzch_search").trigger("change");
	 });
	 
	 $('#fjzch_search').on('change',function(){
		 $('.nav.nav-tabs > li.active a') .trigger('click');
	 });
	 
	 
});

//改变组织机构时改变飞机注册号
function changeOrganization(){
	
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption = '';
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:formatUndefine(dprtcode),FJJX:null});
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			if(dprtcode == planeData.DPRTCODE){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			}
		});
		planeRegOption += '<option value="">非装机件 </option>';
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}
	$("#fjzch_search").html(planeRegOption);
}



/**
 * 初始化页面sheet功能
 */
function initNav(){
	$('.nav.nav-tabs > li > a').click(function(){
	    var href=$(this).attr('href');
	    $('#tablist a[href="'+href+'"]').tab('show');
	    if(href=="#planeLoadingList"){
	    	 goPage(1,"auto","desc");//开始的加载默认的内容 
	    	var value  = $('input[name="jkfs"]:checked').val();
	    	if(value=="PRE_SCHEDULING"){
	    		 $('#sssss').hide();
	    	}
	    }else if(href=="#timeMonitor"){
	    	goPage1(1,"auto","desc");//开始的加载默认的内容 
	    }else{
	    	  goPage2(1,"auto","desc");//开始的加载默认的内容 
	    }
	}); 
}


var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var isLoadMonitor = false;
//初始化系统阀值
function initMonitorsettings(){
	isLoadMonitor = false;
}

//加载系统阀值
function loadMonitorsettings() {
	AjaxUtil.ajax({
		url:basePath + "/productionplan/scheduledcheckitem/getByKeyDprtcode",
		type:"post",
		async: false,
		data:{
			dprtcode : $("#dprtcode").val()
		},
		dataType:"json",
		success:function(data){
			if(data != null && data.monitorsettings != null){
				yjtsJb1 = data.monitorsettings.yjtsJb1;
				yjtsJb2 = data.monitorsettings.yjtsJb2;
				yjtsJb3 = data.monitorsettings.yjtsJb3;
			}
			isLoadMonitor = true;
		}
	});
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		if(!isLoadMonitor){
			loadMonitorsettings();
		}
		 
		var obj ={};
		
		var searchParam = gatherSearchParam();
		obj.fjzch = searchParam.fjzch;
		obj.dprtcode = searchParam.dprtcode;
		obj.keyword = $.trim($("#keyword_search").val());
		 
		
		jkfs = $.trim($('input[name=jkfs]:radio:checked').val());
		 //预排
		if(jkfs == 'PRE_SCHEDULING'){
			
			//无飞机注册号，不能预排
			var fjzch = $.trim($('#fjzch_search').val());
			if(fjzch == ''){
				AlertUtil.showErrorMessage("非装机件无法预排计划！");
				return false;
			}
			
			
			pagination = {
					page : pageNumber,
					sort : sortType,
					order : sortField,
					rows : 20
				};
			obj.pagination = pagination;
				
			var click4Btn = $('#click4Btn').val()
			obj.jzrq = $("#jzrq").val();
			if((obj.jzrq == undefined || obj.jzrq =='')) {
				if(click4Btn =='1'){
					AlertUtil.showErrorMessage("预排模式下，截至日期不能为空");
				}
				
				return false;
			}
			else {
				
				  
			    $("#pre_scheduling_block").show();
				$("#sssss").hide();
				var sortField = sequence;
				startWait();

				AjaxUtil.ajax({
					   url:basePath+"/productionplan/scheduledcheckitem/recursivelyScheduledCheckItems",
					   type: "post",
					   dataType:"json",
					   contentType : "application/json;charset=utf-8",
					   async : false,
					   data : JSON.stringify(obj),
					   success:function(data){
					       finishWait();
					       if(data.rows.length>0){
					    	   appendContentHtmlYp(data.rows);
					    	   var page_ = new Pagination({
									renderTo : "yp_pagination",
									data : data,
									sortColumn : sortField,
									orderType : sortType,
									extParams : {},
									goPage : function(a, b, c) {
										AjaxGetDatasWithSearch(a, c, b);
									}// ,
								// controller: this_
								});
					    	   
							   // 标记关键字
							   signByKeyword($.trim($("#keyword_search").val()),[2,3,4,5,7,8]);
							   
						   } else {
							  $("#ypPanel #list").empty();
							  $("#ypPanel #pagination").empty();
							  $("#ypPanel #list").append("<tr style='vertical-align: middle;text-align:center;'><td colspan=\"11\">暂无数据 No data.</td></tr>");
						   }
					       
					       new Sticky({tableId:'ypPanel'});
				      } 
				    }); 
				$('#click4Btn').val('');
			}
			
		} 
		else {
			$("#sssss").show();
			$("#pre_scheduling_block").hide();
			 
			 //监控
			obj.pagination = {sort:sortType,order:sequence};
			startWait();
			AjaxUtil.ajax({
				   url:basePath+"/productionplan/scheduledcheckitem/queryScheduledCheckItemList",
				   type: "post",
				   dataType:"json",
				   async : false,
				   contentType:"application/json;charset=utf-8",
				   data:JSON.stringify(obj),
				   success:function(data){
				    finishWait();
				    if(data.rows!=undefined&&data.rows.length>0){
				    	
						   appendContentHtml(data.rows);
							// 标记关键字
					       signByKeyword($("#keyword_search").val(),[4,5,6,13,14,15]);
						   
				   } else {
					  $("#jkPanel #list").empty();
					  $("#jkPanel #pagination").empty();
					  $("#jkPanel #list").append("<tr style='vertical-align: middle;text-align:center;'><td colspan=\"16\">暂无数据 No data.</td></tr>");
				   }
				    new Sticky({tableId:'jkPanel'});
			      }
			    }); 
		}
		
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.fjzch = $("#fjzch_search").val();
		 searchParam.dprtcode = $("#dprtcode").val();
		 return searchParam;
	 }

	 /**
	  * 监控列表
	  * @param list
	  * @param parentDom
	  */
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			
			   var jhcs = row.jhc.split(",");//逗号分割计划值
			   var jhcss="";
			   for (var i = 0; i < jhcs.length; i++) {
				   jhcss+=jhcs[i]+"<br>";
				}
			   jhcss=jhcss.substring(0,jhcss.length-1);
			   
			   var syss = row.sy.split(",");//逗号分割剩余值
			   var sys="";
			   for (var i = 0; i < syss.length; i++) {
				   sys+=syss[i]+"<br>";
				}
			   sys=sys.substring(0,sys.length-1);
			  
			   if(parseInt(row.syts)<=parseInt(yjtsJb1)){
				   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\"   ;  bgcolor=\""+warningColor.level1+"\">";
			   }else if(parseInt(row.syts)>parseInt(yjtsJb1)&&parseInt(row.syts)<=parseInt(yjtsJb2)){
				   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\"  ;   bgcolor=\""+warningColor.level2+"\">";
			   }else{
				   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\"  ;  >";
			   }
			  
			   htmlContent += "<td class='fixed-column'  style='vertical-align: middle; '><input name='djxmid' value='"+row.djxmid+"' type='hidden'/><input name='fjzch' value='"+formatUndefine(row.fjzch)+"' type='hidden'/><input name='id' type='checkbox' value='"+row.id+"' onchange=\"checkbox_change('"+index+"',this);\" /></td>"; 
			   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align: middle;'>"+ "<i class='icon-edit color-blue cursor-pointer checkPermission' jkbz='"+formatUndefine(StringUtil.escapeStr(row.jkbz))+"' ywmc='"+formatUndefine(StringUtil.escapeStr(row.ywmc))+"' xlh='"+formatUndefine(StringUtil.escapeStr(row.xlh))+"' jh='"+formatUndefine(StringUtil.escapeStr(row.jh) )+"' djbh='"+formatUndefine(StringUtil.escapeStr(row.djbh) )+"'  zwms='"+formatUndefine(StringUtil.escapeStr(row.zwms) )+"' onClick=\"edit('"
				+ formatUndefine(row.id) + "',event)\" permissioncode='productionplan:scheduledcheckitem:main:02' title='编辑备注  Edit Remark'></i></td>"; 
			   htmlContent += "<td  style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td  style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.zwms))+"'><input name='zwms' value='"+StringUtil.escapeStr(row.zwms) +"' type='hidden'/><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.zwms)  )+"</span></td>";  
			   htmlContent = htmlContent + "<td  style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.ywmc))+"'><input name='ywmc' value='"+StringUtil.escapeStr(row.ywmc)+"' type='hidden'/><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.ywmc)  )+"</span></td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'><input name='xlh' value='"+StringUtil.escapeStr(row.xlh)+"' type='hidden'/><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.xlh))+"</span></td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle;'  class='text-left'><input name='jhcss' value='"+jhcss+"' type='hidden'/>"+ formatUndefine(jhcss) + "</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'><input name='sys' value='"+sys+"' type='hidden'/>"+formatUndefine(sys)+"</td>";  
				 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; text-align:right;' ><input name='syts' value='"+formatUndefine(row.syts)+"' type='hidden'/>"+formatUndefine(row.syts)+"</td>";  
			   
			   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-left'  title='"+formatUndefine(StringUtil.escapeStr(row.jkbz))+"'><input name='jkbz' value='"+StringUtil.escapeStr(row.jkbz)+"' type='hidden'/>"+formatUndefine(StringUtil.escapeStr(row.jkbz))+"</td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-left'><input name='wz' value='"+zts[row.wz]+"' type='hidden'/>"+formatUndefine(zts[row.wz])+"</td>"; 
			   
			   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-left' ><input name='jhrw' value='"+StringUtil.escapeStr(row.jhrw)+"' type='hidden'/><input name='dprtcode' value='"+StringUtil.escapeStr(row.dprtcode) +"' type='hidden'/><a href='javascript:void(0);' onclick=\"viewDetail('"+row.xggdid+"')\">"+formatUndefine(StringUtil.escapeStr(row.jhrw))+"</a></td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' ><input name='bjlx' value='"+row.bjlx+"' type='hidden'/><input name='djbh' value='"+StringUtil.escapeStr(row.djbh)+"' type='hidden'/><a href='javascript:void(0);' onclick=\"viewDetaildj('"+StringUtil.escapeStr(row.djbh)+"','"+StringUtil.escapeStr(row.dprtcode)+"')\"><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.djbh))+"</span></a></td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; 'class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.jh))+"'><input name='jh' value='"+StringUtil.escapeStr(row.jh)+"' type='hidden'/><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.jh))+"</span></td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.zwmc))+"'><input name='zwmc' value='"+StringUtil.escapeStr(row.zwmc)+"' type='hidden'/><span name='keyword'>"+formatUndefine(StringUtil.escapeStr(row.zwmc))+"</span></td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle;' class='text-left'>"+AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode))+"</td>"; 
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#jkPanel #list").empty();
		   $("#jkPanel #list").html(htmlContent);
		   refreshPermission();
	 }

	//编辑备注
	 function edit(id,e){
		 var jkbz = $(e.target).attr("jkbz");
		 var ywmc = $(e.target).attr("ywmc");
		 var xlh = $(e.target).attr("xlh");
		 var jh = $(e.target).attr("jh");
		 var djbh = $(e.target).attr("djbh");
		 var zwms = $(e.target).attr("zwms");
		 $("#jkid").val(id);
		 $("#jkbz").val(jkbz);
		 $("#ywmc").val(ywmc);
		 $("#xlh").val(xlh);
		 $("#jh").val(jh);
		 $("#djbh").val(djbh);
		 $("#zwms").val(zwms);
		 $("#alertModalview").modal("show");
	 }
	 
	 //确认编辑备注
	 function sbDown() {
		 var jkid = $("#jkid").val();
		 var jkbz =$.trim($("#jkbz").val());
		
		 var reserve = {
				 id : jkid,
				 jkbz : jkbz
		 };
		 startWait();
		 AjaxUtil.ajax({
			 url:basePath + "/productionplan/scheduledcheckitem/saveJkbz",
			 contentType:"application/json;charset=utf-8",
			 type:"post",
			 async: false,
			 data:JSON.stringify(reserve),
			 dataType:"json",
			 success:function(data){
				 finishWait();
				 AlertUtil.showMessage('保存成功!');
				 $("#alertModalview").modal("hide");
				 searchRevision();
				 refreshPermission();
			 }
		 });
	 }
	 
	//查看详情
	 function viewDetail(xggdid){
	 	window.open(basePath+"/project/checklist/Looked?id="+ xggdid);
	 }
	 //查看定检项目
	 function viewDetaildj(djbh,dprtCode){
		 window.open(basePath+"/project/fixedcheckitem/selectByZxbsAndBh?djbh="+ encodeURIComponent(djbh)+"&dprtCode="+dprtCode);
	 }
	 
	 
	 /**
	  * 预排列表
	  * @param list
	  * @param parentDom
	  */
	 function appendContentHtmlYp(list){
		   var htmlContent = ''; 
		   $.each(list,function(index,row){
			   var jhcss="";
			   var jhcssTip="";
			   
			   jhcss = formatUndefine(StringUtil.escapeStr(row.jkxmbhF)) +':'+formatUndefine(StringUtil.escapeStr(row.jkzF));
			   jhcss = jhcss+"<br>"+ formatUndefine(StringUtil.escapeStr(row.jkxmbhS)) +':'+formatUndefine(StringUtil.escapeStr(row.jkzS));
			   jhcss = jhcss+"<br>"+ formatUndefine(StringUtil.escapeStr(row.jkxmbhT)) +':'+formatUndefine(StringUtil.escapeStr(row.jkzT));
			   
			   jhcssTip = formatUndefine(StringUtil.escapeStr(row.jkxmbhF)) +':'+formatUndefine(StringUtil.escapeStr(row.jkzF));
			   jhcssTip = jhcssTip+" "+ formatUndefine(StringUtil.escapeStr(row.jkxmbhS)) +':'+formatUndefine(StringUtil.escapeStr(row.jkzS));
			   jhcssTip = jhcssTip+" "+ formatUndefine(StringUtil.escapeStr(row.jkxmbhT)) +':'+formatUndefine(StringUtil.escapeStr(row.jkzT));
			   
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\"  ;  >";
			   htmlContent += "<td style='vertical-align: middle;' class='fixed-column' align='center'  >"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='fixed-column' style='vertical-align: middle; ' align='center'  title='"+formatUndefine(StringUtil.escapeStr(row.djbh))+"' >"
			   +"<a href='javascript:void(0);' onclick=\"viewDetaildj('"+StringUtil.escapeStr(row.djbh)+"','"+row.dprtcode+"')\">"
			   +formatUndefine(row.djbh)+"</a>"
			   +"</td>";  
			   htmlContent = htmlContent + "<td  class='fixed-column' style='vertical-align: middle; ' align='left'  title='"+formatUndefine(StringUtil.escapeStr(row.djzwmc))+"'>"+formatUndefine(StringUtil.escapeStr(row.djzwmc))+"</td>"; 
			   htmlContent = htmlContent + "<td  style='vertical-align: middle; ' align='left'  title='"+formatUndefine(StringUtil.escapeStr(row.bjywms))+"'>"+formatUndefine(StringUtil.escapeStr(row.bjywms))+"</td>";
			   htmlContent = htmlContent + "<td  style='vertical-align: middle; ' align='left' title='"+formatUndefine(row.bjxlh)+"'>"+formatUndefine(row.bjxlh)+"</td>";
			   htmlContent = htmlContent + "<td  style='vertical-align: middle;' align='center' class='fixed-column' title='"+formatUndefine((row.zxrq||'').substring(0,10))+"' >"+formatUndefine((row.zxrq||'').substring(0,10))+"</td>";
			   
			   htmlContent = htmlContent + "<td style='vertical-align: middle;' align='left'    title='"+formatUndefine(jhcssTip)+"'>"+ formatUndefine(jhcss) + "</td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' align='left'  title='"+formatUndefine(row.bjh)+"'>"+formatUndefine(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' align='left'  title='"+formatUndefine(StringUtil.escapeStr(row.bjzwms))+"'>"+formatUndefine(StringUtil.escapeStr(row.bjzwms))+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle;'  class='text-left' title='"+AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode))+"'>"+AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode))+"</td>"; 
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#ypPanel #list").empty();
		   $("#ypPanel #list").html(htmlContent);
		   refreshPermission();
	 }
	 
	  
	//字段排序
	function orderBy(obj, _obj) {
		$obj = $("#jkPanel th[name='column_"+obj+"']");
		var orderStyle = $obj.attr("class");
		$("#jkPanel .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#jkPanel .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
		var orderType = "asc";
		if (orderStyle.indexOf ( "sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj, orderType);
	}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	
	//信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
	

	function searchFromButton(){
		$('#click4Btn').val(1);
		goPage(1,"auto","desc");
		$('#click4Btn').val('');
	}
	
	//搜索条件重置
	function searchreset(){	
		 $("#divSearch").find("input").each(function() {
				$(this).attr("value", "");
			});
			
			 $("#divSearch").find("textarea").each(function(){
				 $(this).val("");
			 });
			 
			  $("#divSearch").find("select").each(function(){
				 $(this).val("");
			 });
			 
			// $('#fjzch_search').attr("value", "");
			 $('#keyword_search').attr("value", "");
			 
			 var zzjgid=$('#zzjgid').val();
			 $("#dprtcode").val(zzjgid);
			 
			 var SelectArr = $("#fjzch_search_sel select");
			  SelectArr[0].options[0].selected = true; 
			  $("#jzrq").val('');
			  //changeOrganization();
			  $('#dprtcode').trigger('change');
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
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		}
		 
	/**
	 * 更改选中的飞机
	 */
	function changeSelectedPlane(){
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 goPage1(1,"auto","desc");//开始的加载默认的内容 
		 goPage2(1,"auto","desc");//开始的加载默认的内容 
	}
		 
	/**
	 * 提交计划
	 */
	function sub(){
		startWait();
		var ids = '';
		$("#list").find("tr td input[type='checkbox']:checked").each(function(){
			ids += $(this).val()+",";
		});
		
		ids = getstr(ids);
		if(ids==""){
			finishWait();
			AlertUtil.showErrorMessage("请勾选需要提交的项");
			return;
		}
		
		//验证定检是否可以提交计划
		 if(checkUpdMt(ids)){
		AlertUtil.showConfirmMessage("您确定要提交计划吗？",{callback: function(){
				startWait();
				// 提交数据
				AjaxUtil.ajax({
					url : basePath+"/productionplan/scheduledcheckitem/subScheduledcheckitem",
					type:"post",
					data:{'ids' : ids},
					success:function(data){
							finishWait();//结束Loading
						if (data.state == "success") {
							
							AlertUtil.showMessage('生成计划成功!');
							goPage(1,"auto","desc");//开始的加载默认的内容 
						} else {
							AlertUtil.showErrorMessage("提交失败!");
						}
					}
				});
			 
					}});
				
		 }else{
			 finishWait();
		 }
		
	}
	
	//验证定检是否可以提交计划
	 function checkUpdMt(ids){
	 	var flag = false;
	 	AjaxUtil.ajax({
	 		url:basePath+"/productionplan/scheduledcheckitem/checkUpdMt",
	 		type:"post",
	 		async: false,
	 		data:{
	 			'ids' : ids
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

	 //点击监控模式
	/* function jiankong(){
		 $("#pre_scheduling").hide();
		 $('#sssss').show();
	 }*/
	 
	 //点击预排模式
	/* function yupai(){
		 $("#sssss").hide();
		 $("#pre_scheduling").show();
	 }*/
	 
	//全选
	 function checkAll(){
			$("[name=id]:checkbox").each(function() { 
				 $(this).attr("checked", 'checked'); 
			 });
			 
		}
	//全不选
	 function notCheckAll(){
			 $("[name=id]:checkbox").each(function() { 
				 $(this).removeAttr("checked"); 
			 });
		}
	function checkbox_change(index, _this){
		$("#jkPanel :checkbox[name=id]:eq("+index+")").attr("checked", $(_this).is(":checked"));
		
	}

	/**
	 * 导出excel
	 */
	function Excel() {
		var jkfs = $.trim($('input[name=jkfs]:radio:checked').val());
		var searchParam = gatherSearchParam();
		var fjzch = searchParam.fjzch;
		var dprtcode = searchParam.dprtcode;
		var keyword = $.trim($("#keyword_search").val());
		if(jkfs=="PRE_SCHEDULING"){
			var jzrq= $("#jzrq").val();
			if(jzrq == undefined || jzrq =='') {
				AlertUtil.showErrorMessage("预排模式下，截至日期不能为空");
			}else{
				var lx=2;
				window.open(basePath+"/productionplan/scheduledcheckitem/scheduledcheckitem.xls?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+encodeURIComponent(dprtcode)+"&keyword="+encodeURIComponent(keyword)+"&jzrq="+jzrq+"&lx="+lx);
			}	
		}else{
			var lx=1;
			window.open(basePath+"/productionplan/scheduledcheckitem/scheduledcheckitem.xls?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+dprtcode+"&keyword="+encodeURIComponent(keyword)+"&lx="+lx);
		}
	}