var choseAllIds=[];	
var revisionNoticeBookitemData = [];
/*var yjtsJb1 = $("#yjtsJb1").val();
var yjtsJb2 = $("#yjtsJb2").val();
var yjtsJb3 = $("#yjtsJb3").val();*/
var pagination={};
	$(document).ready(function(){
			Navigation(menuCode);
			 decodePageParam();
		 	//goPage(1,"auto","desc");//开始的加载默认的内容 
			//DicAndEnumUtil.registerEnum('revisionNoticeBookTypeEnum','tzslx');
			 
			//时间控件
				$('#wcrq').datepicker({
					 autoclose: true,
					 clearBtn:true
				}).on('hide', function(e) {
					  $('#form').data('bootstrapValidator')  
			        .updateStatus('wcrq', 'NOT_VALIDATED',null)  
			        .validateField('wcrq');  
			  });
			 /**
			 * 添加修订通知书
			 * @param id
			 */
			 $('#add').on('click',function(){
				 window.location.href =  basePath+"/project/revisionNoticeBook/intoAddRevisionNoticeBook_card?pageParam="+encodePageParam();
				 
			 });
			//初始化日志功能
			logModal.init({code:'B_G_005'});		
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
	
	/**
	 * 查看修订通知书
	 * intoViewRevisionNoticeBook
	 * @param id
	 */
	function view(id,dprtcode) {
		window.open(basePath+"/project/revisionNoticeBook/intoViewRevisionNoticeBook?id="+ id+"&dprtcode="+dprtcode);
		
	}
	
	
	/**
	 * 编辑修订通知书
	 * @param id
	 */
	function edit(id,zt,dprtcode) {
		
		 if(zt==0 || zt==1 || zt==5 || zt==6 || zt==2 || zt==3){
			 if(checkUpdMt(id)){	
				 window.location.href =  basePath+"/project/revisionNoticeBook/intoRevisionNoticeBook?id="+ id+"&dprtcode="+ dprtcode+"&pageParam="+encodePageParam();
			 }
		 }else{
			 AlertUtil.showErrorMessage('只有保存,提交,审核驳回,批准驳回下的状态才能进行操作!');
		 }
		
	}
	//验证是否能进行修改
	 function checkUpdMt(id){
	 	var flag = false;
	 	AjaxUtil.ajax({
	 		url:basePath + "/project/revisionNoticeBook/checkUpdMt",
	 		type:"post",
	 		async: false,
	 		data:{
	 			'id' : id
	 		},
	 		dataType:"json",
	 		success:function(data){
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
	 * 作废修定通知书
	 * @param id
	 */
	function deleteRevisionNoticeBook(id,zt) {
		if(checkUpdMt(id)){
			AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
				 if(zt==0 || zt==5 || zt==6){
					 AjaxUtil.ajax({
						 type:"post",
						 url:basePath+"/project/revisionNoticeBook/deleteRevisionNoticeBook",
						 dataType:"json",
						 data:{'id':id,'zt':8},
						 success:function(data) {
							 if(data.state=="success"){
								 AlertUtil.showErrorMessage('作废成功');
								 refreshPage();
							 }else{
								 AlertUtil.showErrorMessage(data.message);
							 }
						 }
					 });
					 
				 }else{
					 AlertUtil.showErrorMessage('只能作废未提交,审核驳回,批准驳回状态的修订通知书!');
				 }
			 }});
		}
		
	}
	
	/**
	 * 结束修定通知书
	 * @param id
	 */
/*	function end(id,zlbh,zt) {
		 if(checkUpdMt(id)){
			 if(zt==3){
				 $("#zlbh").val(zlbh);
				 $("#zlid").val(id);
				 $("#alertModalZdjs").modal("show");
			 }else{
				 AlertUtil.showErrorMessage('该状态的修订通知书能指定结束!');
			 }
		 }
		
		
			window.location.href =  basePath+"/project/revisionNoticeBook/intoEndRevisionNoticeBook?id="+ id;
	}*/
	//指定结束
	 function shutDown(id,obj,zdjsrq,isEdit){
		var sqdh=$(obj).attr("jszlh");
		var zdjsyy=$(obj).attr("zdjsyy");
		var zdjsr=$(obj).attr("zdjsr");
		AssignEndModal.show({
			chinaHead:'修订通知书编号',//单号中文名称
			englishHead:'A/N No.',//单号英文名称
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
					zdjsOver(obj);
				}
			}
		});
	}
	function zdjsOver(obj){
	    	  AjaxUtil.ajax({
				  url:'endRevisionNoticeBook',						//发送请求的地址（默认为当前页地址）
				  type:'post',										//请求方式（post或get）默认为get
				  data:JSON.stringify(obj),							//发送到服务器的数据
				  contentType:"application/json;charset=utf-8", 	//发送到服务器的数据内容编码类型
				  dataType:'json',									//预期服务器返回的数据类型
				  cache:false,										//缓存（true有缓存，false无缓存）
				  async: false,
				  success:function(data) {							//请求成功后调用的回调函数
					  if(data.state=="success"){
							 AlertUtil.showErrorMessage("指定结束成功");
							 $("#AssignEndModal").modal("hide");
							 id=obj.id;
							 refreshPage();
						 }else{
							  AlertUtil.showErrorMessage(data.message);
						 }
					 
				  }
		  }); 
	}
	function CloseOver(){
		 //参数组装
		  var obj = {};
		  obj.id = $.trim($("#zlid2").val());				//主键id
	      obj.zdjsrid = $.trim($("#userId").val());		//指定结束人id
	      obj.zdjsyy =  $.trim($("#zdjsyy2").val());								//指定结束原因
 	  //判断指定结束原因
 	  var wcrq = $.trim($("#wcrq").val());
 	  	  obj.wcrq =  $.trim($("#wcrq").val());								//指定结束原因
 	  if (wcrq == "") {
		      AlertUtil.showErrorMessage("完成日期不能为空");
 		  return false;
 	  }
 	  
	    	  AjaxUtil.ajax({
				  url:'closeRevisionNoticeBook',						//发送请求的地址（默认为当前页地址）
				  type:'post',										//请求方式（post或get）默认为get
				  data:JSON.stringify(obj),							//发送到服务器的数据
				  contentType:"application/json;charset=utf-8", 	//发送到服务器的数据内容编码类型
				  dataType:'json',									//预期服务器返回的数据类型
				  cache:false,										//缓存（true有缓存，false无缓存）
				  async: false,
				  success:function(data) {							//请求成功后调用的回调函数
					  if(data.state=="success"){ 
							 AlertUtil.showErrorMessage("关闭成功");
							 id=obj.id;
				 			 refreshPage();
						 }else{
							  AlertUtil.showErrorMessage(data.message);
						 }
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
		 params.zzjg=$.trim($("#zzjg").val());
		 params.xdqx=$.trim($("#xdqx").val());
		 params.tzslx=$.trim($("#tzslx").val());
		 pageParam.params=params;
		 pageParam.pagination=pagination;
		 return Base64.encode(JSON.stringify(pageParam));
	 }	
	
	 
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		 var param = {};
		 revisionNoticeBookitemData=[];
		 param.jszlh = $.trim($("#jszlh").val());
		 param.pgdh = $.trim($("#pgdh").val());
		 param.zdrrealname = $.trim($("#zdr").val());
		 param.keyword = $.trim($("#keyword_search").val());//关键字查询
		 param.zt = $.trim($("#zt").val());
		 param.jszt = $.trim($("#jszt").val());
		 param.dprtcode = $.trim($("#zzjg").val());
		 param.tzslx = $.trim($("#tzslx").val());
		 var xdqx = $.trim($("#xdqx").val());
		 if(null != xdqx && "" != xdqx){
			 var xdqxBegin = xdqx.substring(0,10)+" 00:00:00";
			 var xdqxEnd = xdqx.substring(13,23)+" 23:59:59";
			 param.xdqxBegin = xdqxBegin;
			 param.xdqxEnd = xdqxEnd;
		 }
		 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		 param['sort'] = sortType;						//排序列字段名
		 param['order'] = sequence;					//排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
		 param['page'] = pageNumber;						//页数
		 param['rows'] = 20;
		 param['userType'] = $("#userType").val();
		 param['userId'] = $("#userId").val();
		 
		if(id != ""){
			param.id = id;
			id = "";
		}
		
		startWait();
		 AjaxUtil.ajax({
		   url:basePath+"/project/revisionNoticeBook/queryRevisionNoticeBookList",
		   type: "post",
		   data:param,
		   async: false,
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows,data.monitorsettings);
				   revisionNoticeBookitemData=data.rows;
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sequence,
						orderType : sortType,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,c,b);
						}
					});
				   signByKeyword($("#keyword_search").val(),[3,4,5,6,7,10,13]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"15\">暂无数据 No data.</td></tr>");
			   }
			   refreshPermission();
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
	 
	 function appendContentHtml(list,monitorsettings){
		 choseAllIds=[];
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   choseAllIds.push(row.id);
			   if (index % 2 == 0) {
				   htmlContent += "<tr bgcolor=\""+getWarningColor("#f9f9f9",row.remainingDays,row.zt,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2)+"\"  onclick='clickRow("+index+",this)' >";
				   
			   } else {
				   htmlContent += "<tr bgcolor=\""+getWarningColor("#fefefe",row.remainingDays,row.zt,monitorsettings.yjtsJb1,monitorsettings.yjtsJb2)+"\"  onclick='clickRow("+index+",this)' >";
			   }
			   htmlContent += "<td class='text-center fixed-column' ><input type='checkbox' index='"+index+"' name='checkrow' index='"+index+"' onclick=\"checkRow("+index+",this)\" /></td>";
			   
			   htmlContent = htmlContent + "<td class='text-center fixed-column'><i class='icon-print color-blue cursor-pointer '  onClick=\"printRevisionNoticeBook('"+ row.id + "')\" title='打印 Print'></i>&nbsp;&nbsp;";
			      
			   if(row.zt==0|| row.zt==1 || row.zt==2 || row.zt==3 || row.zt==5 || row.zt==6){
				   htmlContent = htmlContent +"<i class='icon-edit  color-blue cursor-pointer checkPermission' permissioncode='project:revisionnoticebook:edit,project:revisionnoticebook:card_edit,project:revisionnoticebook:mel_edit' onClick=\"edit('"+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='修改 Edit'>" +
				   		"</i>&nbsp;&nbsp;";
			   }
			   if(row.zt==0 ||row.zt==5 ||row.zt==6){
				   htmlContent = htmlContent +"<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project:revisionnoticebook:main:01,project:revisionnoticebook:main:card_01,project:revisionnoticebook:main:mel_01' onClick=\"deleteRevisionNoticeBook('"+ row.id + "','"+row.zt+"')\" title='作废 Invalid '></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==3){
				   htmlContent = htmlContent +"<i class='icon-remove-sign color-blue cursor-pointer checkPermission' permissioncode='project:revisionnoticebook:end,project:revisionnoticebook:card_end,project:revisionnoticebook:mel_end' jszlh='"+row.jszlh+"' zdjsyy='' onClick=\"shutDown('"+row.id+"', this,'',true)\" title='指定结束 End'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==1){
				   htmlContent = htmlContent +"<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project:revisionnoticebook:main:02,project:revisionnoticebook:main:card_02,project:revisionnoticebook:main:mel_02' onClick=\"audit('"+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='审核 Review'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==2){
				   htmlContent = htmlContent +"<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project:revisionnoticebook:main:03,project:revisionnoticebook:main:card_03,project:revisionnoticebook:main:mel_03' onClick=\"reply('"+ row.id + "','"+row.zt+"','"+row.dprtcode+"')\" title='批准 Approve'></i>&nbsp;&nbsp;";
			   }
			   if(row.zt==1||row.zt==2||row.zt==3){
				   htmlContent = htmlContent +"<i class='icon-power-off color-blue cursor-pointer checkPermission' permissioncode='project:revisionnoticebook:cancel,project:revisionnoticebook:card_cancel,project:revisionnoticebook:mel_cancel'  onClick=\"closeRevisionNoticeBook('"+ row.id + "','"+row.jszlh+"',"+row.zt+")\" title='关闭 Close'></i>&nbsp;&nbsp;";
			   }
			   
			   htmlContent = htmlContent + "</td>";
			   
			   var pgdhs = "";
			   var orders = row.orderSources;
			   //orders.sort();
			   if(orders!=undefined && orders.length>0)
			   {
				   var len = orders.length;
				   for(i=0;i<len;i++){
					   if (i == 1) {
						   pgdhs = pgdhs
									+ "　<i class='icon-caret-down' id='"
									+ row.id
									+ "icon' onclick=vieworhideWorkContent('"
									+ row.id + "')></i><div id='"
									+ row.id
									+ "' style='display:none'>";
						}
						   pgdhs += "<a href=\"javascript:viewPgdh('"+orders[i].pgdid+"','"+row.dprtcode+"')\">"+orders[i].pgdh+"</a>";
						   if (i != 0) {
							   pgdhs = pgdhs + "<br>";

							}
							if (i != 0 && i == orders.length - 1) {
								pgdhs = pgdhs + "</div>";
							}
						   
				   }
			   }
			   htmlContent = htmlContent + "<td  class='text-center fixed-column'><a href=\"javascript:view('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.jszlh))+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left'>"+formatUndefine(pgdhs)+"</td>";  
			    
			  /* htmlContent = htmlContent + "<td  class='text-left'>"+DicAndEnumUtil.getEnumName('revisionNoticeBookTypeEnum',row.tzslx)+"</td>";  */
			   htmlContent = htmlContent + "<td  class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.xdzt))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.xdzt))+"</td>";
			   
			   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.xdr.displayName)+"'>"+StringUtil.escapeStr(row.xdr.displayName)+"</td>";  
			   
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.xdqx)+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-right'>"+formatUndefine(row.remainingDays)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.xdnr))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.xdnr))+"</td>";
			   if(row.zt==9){
				   var zdjsr = row.zdjs_user?row.zdjs_user.displayName:'';
				   htmlContent = htmlContent + "<td class='text-left'><a href='javascript:void(0);' "+
				   "jszlh='"+StringUtil.escapeStr(row.jszlh)+"' "+
				   "zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' "+
				   "zdjsr='"+StringUtil.escapeStr(zdjsr)+"' "+
				   " onclick=\"shutDown('"+row.id+"',this,'"+row.zdjsrq+"',false)\">"+DicAndEnumUtil.getEnumName('revisionNoticeBookZtEnum',row.zt)+"</a></td>";
				   
			   }else{
				   htmlContent = htmlContent + "<td  class='text-left'>"+DicAndEnumUtil.getEnumName('revisionNoticeBookZtEnum',row.zt) +"</td>";     
				   }
			   if(row.jszt==0){
				   htmlContent = htmlContent + "<td  class='text-center'>未接收</td>";     
			   }else{
				   htmlContent = htmlContent + "<td  class='text-center'>已接收</td>";     
			   }
			   
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
	 function vieworhideWorkContent(id) {
		 new Sticky({tableId:'quality_check_list_table'});
			var flag = $("#" + id).is(":hidden");
			if (flag) {
				$("#" + id).fadeIn(500);
				$("#" + id + 'icon').removeClass("icon-caret-down");
				$("#" + id + 'icon').addClass("icon-caret-up");
			} else {
				$("#" + id).hide();
				$("#" + id + 'icon').removeClass("icon-caret-up");
				$("#" + id + 'icon').addClass("icon-caret-down");
			}

		}
	//查看指定结束界面
	 function viewreason(jstgh,reason,jssj){
	 		$('#zlbh1').val(jstgh);
	 		$('#zdjsyy1').val(reason);
	 		$('#zdjssj1').val(jssj);
	 		$("#alertModalview").modal("show");
	 	 } 
	
	 
	 //查看revision对应的工卡列表
	 function goToLinkPage(obj,rid){
		 obj.stopPropagation(); //屏蔽父元素的click事件
		 window.location =basePath+"/main/work/listpage?rid="+rid;
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
		 var zzjgid=$("#zzjgid").val();
		 $("#zzjg").val(zzjgid);
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
		 	function audit(id,zt,dprtcode){
		 			if(zt!=1){
		 				AlertUtil.showErrorMessage('只能对已提交状态的数据进行审核!');
		 				return
		 			}
		 			window.location.href =basePath+"/project/revisionNoticeBook/intoShenheMainRevisionNoticeBook?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
		 	}
		 	//批复
		 	function reply(id,zt,dprtcode){
		 			if(zt!=2){
		 				AlertUtil.showErrorMessage('只能对已审核状态的数据进行批准!');
		 				return
		 			}
		 			window.location.href =basePath+"/project/revisionNoticeBook/intoPifuMainRevisionNoticeBook?id=" + id+"&dprtcode=" + dprtcode+"&pageParam="+encodePageParam();
		 	} 
		 	/**
			 *关闭
			 * @param id
			 */
			function closeRevisionNoticeBook(id,zlbh,zt) {
				 if(zt==1|| zt==2 || zt==3){
					 $("#zlbh2").val(zlbh);
					 $("#zlid2").val(id);
					 $("#alertModalClose").modal("show");
				 }else{
					 AlertUtil.showErrorMessage('该状态的修订通知书不能关闭!');
				 }
				
				
					/*window.location.href =  basePath+"/project/revisionNoticeBook/intoEndRevisionNoticeBook?id="+ id;*/
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
			//获取预警颜色
			function getWarningColor(bgcolor,syts,zt,yjtsJb1,yjtsJb2){
				if(!(zt == 0 || zt==5 || zt==6 || zt==1 || zt==2 || zt==3)){
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
	/*		function clickRow(index,this_){
				
				var $checkbox = $("#list :checkbox[name='checkrow']:eq("+index+")");
				$(".sticky-col").find(":checkbox[name='checkrow']:eq("+index+")").attr("checked", !$checkbox.is(":checked"));
				$checkbox.attr("checked", !$checkbox.is(":checked"));
			}
			function checkRow(index,this_){
				$("#list :checkbox[name=checkrow]:eq("+index+")").attr("checked", $(this_).is(":checked"));
			}*/
			
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

			//打开批量审核批准对话框
			function batchApproveWin(isApprovel){
				var idArr = [];
				var approvalArr = [];
				var approvalNotArr = [];
				var zt = isApprovel?2:1;
				$("#list").find("tr input:checked").each(function(){
					var index = $(this).attr("index");
					var revisionNoticeBook = revisionNoticeBookitemData[index];
					if(revisionNoticeBook.zt == zt ){
						idArr.push(revisionNoticeBook.id);
						approvalArr.push(revisionNoticeBook.jszlh);
			   	 	}else{
			   	 		approvalNotArr.push(revisionNoticeBook.jszlh);
			   	 	}
					
				});
				
				BatchApprovelModal.show({
					approvalArr:approvalArr,//审核可操作的数据
					approvalNotArr:approvalNotArr,//审核不可操作的数据
					isApprovel:isApprovel,//判断审核还是批准
					callback: function(data){//回调函数
						if(idArr.length > 0){
							batchApproval(idArr,data,isApprovel);
						}
					}
				});
			}

			function batchApproval(idList,data,isApprovel){
				var url = basePath+"/project/revisionNoticeBook/updateBatchAudit";
				var message = '批量审核成功!';
				if(isApprovel){
					url = basePath+"/project/revisionNoticeBook/updateBatchApprove";
					message = '批量批准成功!';
				}
				startWait();
				// 提交数据
				AjaxUtil.ajax({
					url:url,
					type:"post",
					data:{
						idList:idList,yj:data.yj
					},
					dataType:"json",
					success:function(message){
						finishWait();
						AlertUtil.showMessage(message);
						refreshPage();
					}
				});
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
					$("#zzjg").val(params.zzjg);
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
			function add(){
				window.location = basePath+"/project/revisionNoticeBook/intoAddRevisionNoticeBook_card?pageParam="+encodePageParam();
			}
			/**
			 * 打印
			 * @param id
			 */
			function printRevisionNoticeBook(id){
				var url=basePath+"/project/revisionNoticeBook/export/pdf/"+id;
				window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
						'PDF','width:50%;height:50%;top:100;left:100;');
			}
			//全选
			function performSelectAll(){
				$(":checkbox[name=checkrow]").attr("checked", true);
			}
			//取消全选
			function performSelectClear(){
				$(":checkbox[name=checkrow]").attr("checked", false);
			}