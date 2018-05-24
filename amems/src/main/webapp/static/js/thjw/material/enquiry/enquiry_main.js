var pagination = {};
var enquiryIdList = [];
var reserverDetailId = '';
var supplierWinAddData = [];
var materialId = '';
var enquiryType = 1;
var orderNumber = 1;
var isValid = false;
var materialSl = 0;
var materialBjh = '';
var enquiryStatus = 1;
var selectIndex = -1;
var attachNumber = 0;
var delAttachements = [];
var uploadObj = null;
var id = "";
var dprtcode = '';
var excelId="";//存储要导出的id
var excelLx="";//存储要导出的类型
var excelSl=0;
var pageId = "enquiry_main";
var isDoropDown=false;
var numberValidator = {
		reg : new RegExp("^[0-9]{1,10}(\.[0-9]{0,2})?$"),
		message: "只能输入数值，保留两位小数"
	};

$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRevision();//调用主列表页查询
			}
		}
	});
	
	showorhideEnquiry();
	$("#enquiryList").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
	$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	goPage(1,"auto","desc");//开始的加载默认的内容 
	initUploadObj();
	refreshPermission();
	/*authHeightUtil.authHeightOne(pageId);
	 $(window).resize(function() {
		 authHeightUtil.authHeightOne(pageId);
	 });*/
});

//设置高度
function authHeight(){
    //头部高度
    var headerHeight = $('.header').outerHeight();
    
    var headerDownHeight=0;
    if(isDoropDown){
    	headerDownHeight = $('.header-down').outerHeight();	
    }else{
    	headerDownHeight=25;
    	 if(navigator.userAgent.indexOf("Chrome") > -1){
    		 headerDownHeight=15;
         }
    	
    }
    
//    var headerDownHeight = $('.header-down').outerHeight();
    //window高度
    var windowHeight = $(window).height()
    //尾部的高度
    var footerHeight = $('.footer').outerHeight()||0;
    //分页的高度
    var paginationHeight = $('.page-height:visible').outerHeight()||0;
    //panelheader的高度
    var panelHeadingHeight = $('.panel-heading').outerHeight();
    //调整高度
    var adjustHeight = $("#adjustHeight").val()||0;
    //搜索框的高度
    var searchContent=$(".searchContent").outerHeight()||0;
    //body的高度
    
   //情景1：table-tab
    //修改1：在页面div class='page-content'上加class='page-content table-tab-type'
    //修改2：给上方表格的父div 添加class='table-tab-type_table'
    //修改3：给tab中table的父div 添加class='tab-second-height'
    
    
    if($(".table-tab-type").length>0){
    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
    	//表格的高度
        var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
      //谷歌的兼容性问题
        if(navigator.userAgent.indexOf("Chrome") > -1){
        	tableHeight -= 10;
        }
        //隐藏的div是否显示
        if($("#tabDiv").css("display")=='block'){
        	//表格的高度
        	var actualTableHeight= tableHeight*0.5;
        	//表格的高度
        	$(".table-tab-type_table").attr('style', 'height:' + actualTableHeight+ 'px !important;overflow-x: auto;');
        	// tab header 的高度
        	var table_tab=$("#tabDiv .nav-tabs").outerHeight()||0;
        	//选中的提示信息
        	var selectCourse=$("#selectCourse").outerHeight()||0;
        	//表格的高度
        	var actualTableOuterHeight=$(".table-tab-type_table").outerHeight()||0;
        	// tabcontent
        	var tabcontent=tableHeight-actualTableOuterHeight-table_tab-selectCourse-10;
        	//如果下方的tab是引用课件信息。
        	if($(".tab-second-height").length>0){
        		//是否存在课件上传
        		var fileHeadheigth=$("#fileHead").outerHeight()||0;
        		//下方tab的高度
        		var tab_second_height =tabcontent-fileHeadheigth-20;
        		
        		//给下方tab中table 的父div 的高度进行赋值
        		$(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
        	}
        	
        	if($(".tab-table-search-height").length>0){
        		//是否存在课件上传
        		var fileHeadheigth=$(".tab-search-content").outerHeight()||0;
        		//下方tab的高度
        		var tab_second_height =tabcontent-fileHeadheigth-20;
        		
        		//给下方tab中table 的父div 的高度进行赋值
        		$(".tab-table-search-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;')
        	}
        	
        }else{
        	//给表格的父div的高度进行赋值
        	 $(".table-tab-type_table").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
        }
        
    }
  //情景2：table-table
    
  //情景3：tab-table
    

    //情景4:tab-tab 类型
    //修改1：在页面div class='page-content'上加class='page-content tab-tab-type'
    //修改2：在页面第一个ul的class='nav-tabs'的父div 上加class='tab-tab-type_parentdiv'
    //修改3：在第一个Tab的表格的父div 上添加class='tab-first-height'（有几个tab就得添加几次）
    //修改4：在第二个Tab的表格的父div 上添加class='tab-second-height'（有几个tab就得添加几次）
    /*if($(".tab-tab-type").length>0){
    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
     	//表格的高度
         var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
        //谷歌的兼容性问题
         if(navigator.userAgent.indexOf("Chrome") > -1){
         	tableHeight -= 10;
         }
         //first tab header
         var  tab_tab_type_parentdiv_header=$(".tab-tab-type_parentdiv .nav-tabs").outerHeight()||0
         //#fileDiv 不显示的情况下 first tab 的高度
         var tabheight=tableHeight-tab_tab_type_parentdiv_header-10;
         //隐藏的div是否显示
         if($("#fileDiv").css("display")=='block'){
        	 //#fileDiv显示情况下first tab中table的高度
        	 $(".tab-first-height").attr('style', 'height:' + tabheight*0.5+ 'px !important;overflow-x: auto;');
        	 //提示信息的高度
        	 var selectCourse=$("#selectRow").outerHeight()||0;
        	 //第二个tab header 的高度
        	 var secondheader=$("#fileDiv .nav-tabs").outerHeight()||0;
        	 //第一个tab的高度
        	 var tab_tab_type_parentdiv=$(".tab-tab-type_parentdiv").outerHeight()||0;
        	 //第二个tab中table的高度
        	 var tab_second_height=tableHeight-(tab_tab_type_parentdiv-paginationHeight)-secondheader-selectCourse-10;
        	//给第二个tab中table的高度赋值
        	 $(".tab-second-height").attr('style', 'height:' + tab_second_height+ 'px !important;overflow-x: auto;') 
         }else{
        	//#fileDiv 不显示情况下first tab中table的高度
        	 $(".tab-first-height").attr('style', 'height:' + tabheight+ 'px !important;overflow-x: auto;');
         }
    }  */
    
}


function changeType(){
	goPage(1,"auto","desc");//开始的加载默认的内容 
}
	 //带条件搜索的翻页
	function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
//		authHeightUtil.authHeightOne(pageId);
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		searchParam.id = id;
		url = basePath+"/aerialmaterial/enquiry/queryAllPageList";
		startWait();
		AjaxUtil.ajax({
			 url:url,
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
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
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[3,4,5,6,7,8,14],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"16\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var djlx = $.trim($("#djlx_search").val());
		 var xjzt = $.trim($("#xjzt_search").val());
		 var sqrname = $.trim($("#sqrname_search").val());
		 var sqrq = $.trim($("#sqrq_search").val()); 
		 var yqqx = $.trim($("#yqqx_search").val()); 
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val());
		 searchParam.keyword = keyword;
		 searchParam.djlx = djlx;
		 if('' != xjzt){
			 searchParam.xjzt = xjzt;
		 }
		 if('' != sqrname){
			 paramsMap.sqrname = sqrname;
		 }
		 if(null != sqrq && "" != sqrq){
			 var sqrqBegin = sqrq.substring(0,10);
			 var sqrqEnd = sqrq.substring(13,23);
			 paramsMap.sqrqBegin = sqrqBegin;
			 paramsMap.sqrqEnd = sqrqEnd;
		 }
		 if(null != yqqx && "" != yqqx){
			 var yqqxBegin = yqqx.substring(0,10);
			 var yqqxEnd = yqqx.substring(13,23);
			 paramsMap.yqqxBegin = yqqxBegin;
			 paramsMap.yqqxEnd = yqqxEnd;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		 
		 $("#selectRow").html('');
		 $("#enquiryList").empty();
		 $("#enquiryList").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   var sl = row.SL;
			   if(sl == null || sl == 'undefined'){
					sl = row.SQSL;
			   }
			   if(reserverDetailId == row.ID){
				   loadSupplierPrice(row,sl,index);
			   }
			   /*if(selectIndex == index){
				   selectSupplierPrice(row.ID,row.BJH,row.DJLX,row.BJID,sl,row.XJZT,index);
			   }*/
			   
			   if (index % 2 == 0) {
				   htmlContent += "<tr id='"+row.ID+"' sl="+sl+" index="+index+" xjzt="+row.XJZT+" dprtId='"+row.DPRTCODE+"' bjid='"+row.BJID+"' bjh='"+StringUtil.escapeStr(row.BJH)+"' xjlx='"+row.DJLX+"' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick=selectSupplierPrice(this)>";
			   } else {
				   htmlContent += "<tr id='"+row.ID+"' sl="+sl+" index="+index+" xjzt="+row.XJZT+" dprtId='"+row.DPRTCODE+"' bjid='"+row.BJID+"' bjh='"+StringUtil.escapeStr(row.BJH)+"' xjlx='"+row.DJLX+"' style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=selectSupplierPrice(this)>";
			   }
			   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			   htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('formTypeEnum',row.DJLX) +"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.SQDH)+"' class='text-center'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.MAINID+"','"+row.DJLX+"')\">"+StringUtil.escapeStr(row.SQDH)+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.BJH)+"' class='text-left'>"+StringUtil.escapeStr(row.BJH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.YWMS)+"' class='text-left'>"+StringUtil.escapeStr(row.YWMS)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ZWMS)+"' class='text-left'>"+StringUtil.escapeStr(row.ZWMS)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.CJJH)+"' class='text-left'>"+StringUtil.escapeStr(row.CJJH)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.SN)+"' class='text-left'>"+StringUtil.escapeStr(row.SN)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.sl)+"' class='text-right'>"+StringUtil.escapeStr(sl)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.JLDW) +"</td>";
			   htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('enquiryStatusEnum',row.XJZT) +"</td>";
			   htmlContent += "<td class='text-center'>"+indexOfTime(formatUndefine(row.YQQX))+"</td>";
			   var zjmin = 0;
			   var zjmax = 0;
			   var zjvalue = 0;
			   if(formatUndefine(row.BJCLFMIN) != ''){
				   zjmin = StringUtil.escapeStr(row.BJCLFMIN*sl).toFixed(2);
			   }
			   if(StringUtil.escapeStr(row.BJCLFMAX) != ''){
				   zjmax = StringUtil.escapeStr(row.BJCLFMAX*sl).toFixed(2);
			   }
			   zjvalue = zjmin+"~"+zjmax;
			   if(zjmin == zjmax){
				   zjvalue = zjmin;
			   }
			   htmlContent += "<td title='"+zjvalue+"' class='text-right'>"+zjvalue+"</td>";
			   htmlContent += "<td title='"+(StringUtil.escapeStr(row.SQRUSERNAME)+" "+StringUtil.escapeStr(row.SQRREALNAME))+"' class='text-left'>"+formatUndefine(row.SQRUSERNAME)+" "+formatUndefine(row.SQRREALNAME)+"</td>";
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.SQSJ)+"</td>";
			   htmlContent += "<td>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.DPRTCODE))+"</td>";
			   htmlContent += "</tr>";  
			   
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   if(reserverDetailId != ""){
			   $("#"+reserverDetailId).addClass('bg_tr').siblings().removeClass('bg_tr');
		   }
		   id = "";
	 }
	 
function loadSupplierPrice(row,sl,index){
	var id = row.ID;
	selectIndex = index;
	reserverDetailId = id;
	materialId = row.BJID;
	enquiryType = row.DJLX;
	materialSl = sl;
	materialBjh = StringUtil.escapeStr(row.BJH);
	enquiryStatus = row.XJZT*1;
	dprtcode = row.DPRTCODE;
	var str = '';
	str = '已选择部件号'+StringUtil.escapeStr(row.BJH);
	$("#selectRow").html(str);
	showorhideEnquiry();
	loadEnquiry(id);
	attachNumber = 0;
	initUploadObj();
	loadAttachements(id);
	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
}
	 
function selectSupplierPrice(this_){
	var id = $(this_).attr("id");
	var bjh = $(this_).attr("bjh");
	excelId=id;//用于excel导出
	selectIndex = $(this_).attr("index");
	reserverDetailId = id;
	materialId = $(this_).attr("bjid");
	enquiryType = $(this_).attr("xjlx");
	excelLx=enquiryType;//用于excel导出
	materialSl = $(this_).attr("sl");
	excelSl=materialSl;//用于excel导出
	materialBjh = bjh;
	enquiryStatus = $(this_).attr("xjzt")*1;
	dprtcode = $(this_).attr("dprtId");
	var str = '';
	str = '已选择部件号'+bjh;
	$("#selectRow").html(str);
	showorhideEnquiry();
	loadEnquiry(id);
	attachNumber = 0;
	initUploadObj();
	loadAttachements(id);
	$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
	$("#tabDiv").css("display","none");
	$("#tabDiv").css("display","block");
//	authHeightUtil.authHeightOne(pageId);
}

//加载附件信息begin*************************************************************************************************************

/**
 * 实例化附件上传组件
 */ 
function initUploadObj(){

  uploadObj = $("#fileuploader")
		.uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showStatusAfterError: false,
					showDelete : false,
					maxFileCount : 100,
					formData : {
						"fileType" : "legacytrouble",
						"wbwjm" : function() {
							return $('#wbwjm').val()
						}
					}, 
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
					uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
					uploadButtonClass:"ajax-file-upload_ext",
					statusBarWidth : 150,
					dragdropWidth : 150,
					onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
					{
						if (1 == $("#profileList").find("tr").length && $("#profileList").find("td").length ==1) {
							$("#profileList").empty();
						}
						attachNumber++;
						var trHtml = '<tr bgcolor="#f9f9f9" id=\''+ data.attachments[0].uuid + '\'>';
						trHtml += '<td><div>';
						trHtml += '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+ data.attachments[0].uuid + '\')" title="删除 Delete">  ';
						trHtml += '</div></td>';
						
						trHtml += '<td style="text-align:center;vertical-align:middle;">';
						trHtml += 	  "<span name='orderNumber'>"+attachNumber+"</span>";
						trHtml += '</td>';
						
						trHtml += '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" class="text-left">';
						trHtml += StringUtil.escapeStr(data.attachments[0].wbwjm);
						trHtml += '</td>';
						
						trHtml += '<td class="text-left">' + data.attachments[0].wjdxStr + ' </td>';
						trHtml += '<td title="'+StringUtil.escapeStr(data.attachments[0].user.displayName)+'" class="text-left">' + StringUtil.escapeStr(data.attachments[0].user.displayName) + '</td>';
						trHtml += '<td>' + data.attachments[0].czsj + '</td>';
						trHtml += '<input type="hidden" name="relativePath" value=\'' + StringUtil.escapeStr(data.attachments[0].relativePath) + '\'/>';

						trHtml += '</tr>';
						$("#profileList").append(trHtml);
					}
					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('#wbwjm').val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
				            	AlertUtil.showMessageCallBack({
									message : "文件说明不能包含如下特殊字符 < > / \\ | : \" * ?",
									option : 'wbwjm',
									callback : function(option){
										$("#"+option).focus();
									}
								});
				            	$('.ajax-file-upload-container').html("");
				            	uploadObj.selectedFiles -= 1;
								return false;
				            } 
				            else{
				            	return true; 
				            }
						}else{
							return true;
						}
					}
				});


}	
function loadAttachements(djid){
	AjaxUtil.ajax({
		url : basePath + "/common/loadAttachments",
		type : "post",
		async : false,
		data : {
			mainid : djid
		},
		success : function(data) {
			if (data.success) {
				var attachments = data.attachments;
				var len = data.attachments.length;
				$("#profileList").empty();
				if (len > 0) {
					var trHtml = '';
					
					for (var i = 0; i < len; i++) {
						attachNumber++;
						trHtml += '<tr bgcolor="#f9f9f9" name="'+djid+'" id=\''+ attachments[i].uuid + '\' key=\''+ attachments[i].id + '\' >';
						
						trHtml += '<td><div>';
						trHtml += '<i class="icon-trash color-blue cursor-pointer checkPermission" permissioncode="aerialmaterial:enquiry:main:02" onclick="delfile(\''+ data.attachments[0].uuid + '\')" title="删除 Delete">  ';
						trHtml += '</div></td>';
						
						trHtml += '<td style="text-align:center;vertical-align:middle;">';
						trHtml += 	  "<span name='orderNumber'>"+attachNumber+"</span>";
						trHtml += '</td>';
						
						trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].wbwjm)+'" class="text-left">';
						trHtml += "<a href='javascript:void(0);' onclick=\"downloadfile('"+ attachments[i].id+"')\">"+StringUtil.escapeStr(attachments[i].wbwjm)+"</a>";
						trHtml += '</td>';
						
						trHtml += '<td class="text-left">' + attachments[i].wjdxStr + ' </td>';
						trHtml += '<td title="'+StringUtil.escapeStr(attachments[i].czrname)+'" class="text-left">'+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
						trHtml += '<td style="text-align:center;vertical-align:middle;">' + attachments[i].czsj+ '</td>';
						trHtml += '<input type="hidden" name="relativePath" value=\''+ attachments[i].relativePath + '\'/>';
						trHtml += '</tr>';
					}
					$("#profileList").append(trHtml);
					refreshPermission();
				}else{
					$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
				}
			}
		}
	});
}

function downloadfile(id){
	//window.open(basePath + "/common/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function delfile(uuid){
	var key = $('#' + uuid).attr('key');
	if (key == undefined || key == null || key == '') {
		var responses = uploadObj.responses;
		var len = uploadObj.responses.length;
		var fileArray = [];
		var waitDelArray = [];
		if (len > 0) {
			for (var i = 0; i < len; i++) {
				if (responses[i].attachments[0].uuid != uuid) {
					fileArray.push(responses[i]);
				}
			}
			uploadObj.responses = fileArray;
			uploadObj.selectedFiles -= 1;
			$('#' + uuid).remove();
		}

	} else {
		$('#' + uuid).remove();
		delAttachements.push({
			id : key
		});
	}
	attachNumber--;
	if($("#profileList").find("tr").length == 0){
		$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
	var len = $("#profileList").find("tr").length;
	if (len >= 1){
		$("#profileList").find("tr").each(function(index){
			$(this).find("span[name='orderNumber']").html(index+1);
		});
	}else{
		$("#profileList").append("<tr style='height:35px;'><td colspan='6'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	}
}

function getAttachments(){
	var attachments = [];
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	if (len != undefined && len > 0) {
		for (var i = 0; i < len; i++) {
			attachments.push({
				'yswjm' : responses[i].attachments[0].yswjm,
				'wbwjm' : responses[i].attachments[0].wbwjm,
				'nbwjm' : responses[i].attachments[0].nbwjm,
				'wjdx' : responses[i].attachments[0].wjdx,
				'cflj' : responses[i].attachments[0].cflj,
				'id' : responses[i].attachments[0].id,
				'operate' : responses[i].attachments[0].operate
			});
		}
	}

	var dellen = delAttachements.length;
	if (dellen != undefined && dellen > 0) {
		for (var i = 0; i < dellen; i++) {
			attachments.push({
				'id' : delAttachements[i].id,
				'operate' : 'DEL'
			});
		}
	}
	return attachments;
}


//加载附件信息end*************************************************************************************************************

function showorhideEnquiry(){
	if(enquiryType == 1){
		$("#enquiryHead tr th:eq(3)").show();
		$("#enquiryHead tr th:eq(4)").hide();
		$("#enquiryHead tr th:eq(5)").hide();
		$("#enquiryHead tr th:eq(6)").hide();
	}else{
		$("#enquiryHead tr th:eq(3)").hide();
		$("#enquiryHead tr th:eq(4)").show();
		$("#enquiryHead tr th:eq(5)").show();
		$("#enquiryHead tr th:eq(6)").show();
	}
}
	 
//查看详情
function viewDetail(id,type){
	if(type == 1){
		window.open(basePath+"/aerialmaterial/reserve/view?id=" + id+"&t=" + new Date().getTime());
	}else{
		window.open(basePath+"/aerialmaterial/repair/view?id=" + id+"&t=" + new Date().getTime());
	}
}

function save(){
	var url = basePath+"/aerialmaterial/enquiry/save";	
	var enquiry = {
			mainid : reserverDetailId,
			dprtcode : dprtcode,
			  djlx : enquiryType
		};
	var paramsMap = {};
	paramsMap.bjid = materialId;
	paramsMap.bjh = materialBjh;
	paramsMap.xjzt = enquiryStatus;
	
	enquiry.paramsMap = paramsMap;
	enquiry.enquiryIdList = enquiryIdList;
	enquiry.enquiryList = getEnquiryParam();
	enquiry.attachments = getAttachments();
	
	if(!isValid){
		return false;
	}
	
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(enquiry),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('保存成功');
			id = reserverDetailId;
			refreshPage();
		}
	});
}

//获取询价参数
function getEnquiryParam(){

	isValid = true;
	var enquiryParam = [];
	
	var len = $("#enquiryList").find("tr").length;
	if (len == 1) {
		if($("#enquiryList").find("td").length ==1){
			isValid = false;
			AlertUtil.showErrorMessage("请选择报价!");
			return enquiryParam;
		}
	}
	if (len > 0){
		var gysmcArr = [];
		$("#enquiryList").find("tr").each(function(){
			var this_ = this;
			var infos ={};
			var index=$(this_).index(); //当前行
			var hiddenid =$.trim($("input[name='hiddenid']").eq(index).val());
			var gysid =$.trim($("input[name='gysid']").eq(index).val());
			var gysbm =$.trim($("input[name='gysbm']").eq(index).val());
			var gysmc =$.trim($("input[name='gysmc']").eq(index).val());
			var bjClf =$.trim($("input[name='bjClf']").eq(index).val());
			var bjGsf =$.trim($("input[name='bjGsf']").eq(index).val());
			var bjQtf =$.trim($("input[name='bjQtf']").eq(index).val());
			var yjdhrq =$.trim($("input[name='yjdhrq']").eq(index).val());
			
			if(null == bjClf || "" == bjClf || bjClf <= 0){
				isValid = false;
				var message = '';
				$("input[name='bjClf']").eq(index).focus();
				if(enquiryType == 1){
					message = '请输入单价!';
				}else{
					message = '请输入材料费用!';
				}
				AlertUtil.showErrorMessage(message);
				return false;
			}
			if(null == bjGsf || "" == bjGsf || bjGsf <= 0){
				bjGsf = 0;
			}
			if(null == bjQtf || "" == bjQtf || bjQtf <= 0){
				bjQtf = 0;
			}
			if(null == yjdhrq || "" == yjdhrq){
				isValid = false;
				/*$("input[name='yjdhrq']").eq(index).focus();*/
				AlertUtil.showErrorMessage("请输入预计到货日期!");
				return false;
			}
			var regu = /^[0-9]+\.?[0-9]{0,2}$/;
			
			if(!numberValidator.reg.test(bjClf)){  
				if(enquiryType == 1){
					AlertUtil.showMessage("单价"+numberValidator.message);
				}else{
					AlertUtil.showMessage("材料费"+numberValidator.message);
				}
				isValid = false; 
				return false;
			} 
			
			if(enquiryType != 1){
				if(bjGsf != null && bjGsf != '' && bjGsf != 0){
					if(!numberValidator.reg.test(bjGsf)){  
						AlertUtil.showMessage("工时费"+numberValidator.message);
						isValid = false; 
						return false;
					} 
				}
				if(bjQtf != null && bjQtf != '' && bjQtf != 0){
					if(!numberValidator.reg.test(bjQtf)){  
						AlertUtil.showMessage("其它费"+numberValidator.message);
						isValid = false; 
						return false;
					} 
				}
			}
			if(gysid == null || gysid == ''){
				gysmc = $.trim($(this_).find("input[name='egysmc']").val());
			}
			if(isValid && gysmcArr.indexOf(gysmc) != -1){
				isValid = false;
				AlertUtil.showMessageCallBack({
					message : '对不起,供应商名称不能重复！',
					callback : function(){
						$(this_).find("input[name='egysmc']").focus();
					}
				});
		        return false;
			}
			gysmcArr.push(gysmc);
			infos.id = hiddenid;
			infos.mainid = reserverDetailId;
			infos.gysid = gysid;
			infos.gysbm = gysbm;
			infos.gysmc = gysmc;
			infos.bjClf = bjClf;
			infos.bjGsf = bjGsf;
			infos.bjQtf = bjQtf;
			infos.yjdhrq = yjdhrq;
			infos.djlx = enquiryType;
			enquiryParam.push(infos);
		});
	}
	return enquiryParam;
}

//选择一行
function selectSupplier(id){

	//先移除暂无数据一行
	var len = $("#enquiryList").length;
	if (len == 1) {
		if($("#enquiryList").find("td").length == 1){
			$("#enquiryList").empty();
		}
	}
	var isValid = true;
	if (len > 0){
		$("#enquiryList").find("tr").each(function(){
			var index=$(this).index(); //当前行
			var gysid =$.trim($("input[name='gysid']").eq(index).val());
			if(id == gysid){
				isValid = false;
			}
		});
	}
	if(isValid){
		var row = getSelectRow(id);
		row.gysid = id;
		row.enquiryId = "";
		row.orderNumber = orderNumber++;
		row.yjdhrq = '';
		addRow(row);
	}
}
//新增报价
function setData(){
	
	if($("#supplierWinAdd").find("tr input:checked").length > 0){
		//先移除暂无数据一行
		var len = $("#enquiryList").length;
		if (len == 1) {
			if($("#enquiryList").find("td").length == 1){
				$("#enquiryList").empty();
			}
		}
	}
	
	$("#supplierWinAdd").find("tr input:checked").each(function(){
		var index = $(this).attr("index");	
		var row = supplierWinAddData[index];
		row.gysid = row.ID;
		row.enquiryId = "";
		row.orderNumber = orderNumber++;
		row.yjdhrq = '';
		addRow(row);
	});
}
//手工新增报价
function ManuallyAdd(){
	//先移除暂无数据一行
	var len = $("#enquiryList").length;
	if (len == 1) {
		if($("#enquiryList").find("td").length == 1){
			$("#enquiryList").empty();
		}
	}
	
	var row = {};
	row.gysid = "";
	row.enquiryId = "";
	row.orderNumber = orderNumber++;
	row.yjdhrq = '';
	addRow(row);
}

//加载询价信息
function loadEnquiry(id){
	orderNumber = 1;
	$("#enquiryList").empty();
	if(enquiryType == 1){
		$("#enquiryList").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
	}else{
		$("#enquiryList").append("<tr><td  colspan='9' class='text-center'>暂无数据 No data.</td></tr>");
	}
	startWait();
	AjaxUtil.ajax({
	url:basePath + "/aerialmaterial/enquiry/queryEnquiryListByMainId",
	type:"post",
	async: false,
	data:{
		mainid:id
	},
	dataType:"json",
	success:function(data){
		finishWait();
		enquiryIdList = [];
		if(data.length >0){
			initTableRow(data);
		    }
		}
	});
}
//初始化询价列表
function initTableRow(data){
	//先移除暂无数据一行
	var len = $("#enquiryList").length;
	if (len == 1) {
		if($("#enquiryList").find("td").length == 1){
			$("#enquiryList").empty();
		}
	}
	$.each(data,function(index,row){
		enquiryIdList.push(row.id);
		row.GYSBM = row.gysbm;
		row.GYSMC = row.gysmc;
		row.enquiryId = row.id;
		row.HZZK = row.hzzk;
		row.GYSDJ = row.gysdj;
		row.BJ_CLF = row.bjClf;
		row.BJ_GSF = row.bjGsf;
		row.BJ_QTF = row.bjQtf;
		row.GYSMC = row.gysmc;
		row.orderNumber = orderNumber++;
		addRow(row);
	});
	
}

//向table新增一行
function addRow(row){
	var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		
		tr += "<button class='line6 checkPermission' permissioncode='aerialmaterial:enquiry:main:01' onclick='removeCol(this)'>";
	    tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
	    tr += "</button>";
		
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+row.orderNumber+"</span>";
		tr += 	  "<input type='hidden' name='gysid' value='"+row.gysid+"'/>";
		tr += 	  "<input type='hidden' name='gysbm' value='"+StringUtil.escapeStr(row.GYSBM)+"'/>";
		tr += 	  "<input type='hidden' name='gysmc' value='"+StringUtil.escapeStr(row.GYSMC)+"'/>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+row.enquiryId+"'/>";
		tr += "</td>";
		if(row.gysid != null && row.gysid != ''){
			tr +=  "<td title='"+StringUtil.escapeStr(row.GYSMC)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GYSMC)+"</td>";
		}else{
			tr +=  "<td style='text-align:left;vertical-align:middle;'>";
			tr += "<input type='text' name='egysmc' value='"+StringUtil.escapeStr(row.GYSMC)+"' class='form-control' maxlength='100' />";
			tr +=  "</td>";
		}
		
		var zj = '';
		if(enquiryType == 1){
			tr += "<td style='text-align:left;vertical-align:middle;'>";
			tr += "<input name='bjClf' type='text' value='"+StringUtil.escapeStr(row.BJ_CLF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			if(StringUtil.escapeStr(row.BJ_CLF) != ''){
				zj = StringUtil.escapeStr(row.BJ_CLF) * materialSl;
			}
		}else{
			tr += "<td style='text-align:left;vertical-align:middle;'>";
			tr += "<input name='bjClf' type='text' value='"+StringUtil.escapeStr(row.BJ_CLF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			tr += "<td style='text-align:left;vertical-align:middle;'>";
			tr += "<input name='bjGsf' type='text' value='"+StringUtil.escapeStr(row.BJ_GSF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			tr += "<td style='text-align:left;vertical-align:middle;'>";
			tr += "<input name='bjQtf' type='text' value='"+StringUtil.escapeStr(row.BJ_QTF)+"' class='form-control' placeholder='' onkeyup='clearNoNumTwo(this)' />";
			tr += "</td>";
			zj = StringUtil.escapeStr(row.BJ_CLF) + StringUtil.escapeStr(row.BJ_GSF) + StringUtil.escapeStr(row.BJ_QTF);
		}
		if(zj == ''){
			zj = 0;
		}
		zj = zj.toFixed(2);
		tr +=  "<td title='"+zj+"' name='zj' style='text-align:right;vertical-align:middle;'>"+zj+"</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		if(row.yjdhrq != ''){
			tr += "		<input type='text' class='form-control datepicker' name='yjdhrq' value="+row.yjdhrq+" data-date-format='yyyy-mm-dd' placeholder='请选择日期'  maxlength='10' />";
		}else{
			tr += "		<input type='text' class='form-control datepicker' name='yjdhrq' data-date-format='yyyy-mm-dd' placeholder='请选择日期'  maxlength='10' />";
		}
		tr += "	</div>";
		tr += "</td>";
		tr +=  "<td title='"+StringUtil.escapeStr(row.xjr?row.xjr.displayName:'')+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xjr?row.xjr.displayName:'')+"</td>";
		/*tr += "<td>";
		tr += 	"<div class=' col-sm-9  col-xs-8 padding-left-8 padding-right-0'>" ;
		tr += 		"<input name='zjh' type='text' value='"+row.zjh+"' title='"+row.zjh+"' class='form-control'  style='width: 120px;'/>" ;
		tr += 	"</div>" ;
		
		tr += "  <div class='col-sm-1 col-xs-1 padding-left-20' style='margin-left: 0px;''>" ;
		tr += 	"<a data-toggle='modal' onclick=openWinChapterList(this); class='cursor-pointer'>" ;
		tr += 			"<i class='icon-search font-size-20 padding-top-10'></i>" ;
		tr += 		"</a>" ;
		tr += "  </div>";
		tr += "</td>";*/
		tr += "</tr>";
	
	$("#enquiryList").append(tr);
	
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	refreshPermission();
}

//移除一行
function removeCol(obj){
	$(obj).parent().parent().remove();
	resXh();
	orderNumber--;
	var len = $("#enquiryList").find("tr").length;
	if(len < 1){
		if(enquiryType == 1){
			$("#enquiryList").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
		}else{
			$("#enquiryList").append("<tr><td  colspan='9' class='text-center'>暂无数据 No data.</td></tr>");
		}
	}
}

function resXh(){
	
	var len = $("#enquiryList").find("tr").length;
	if (len == 1) {
		if($("#enquiryList").find("td").length ==1){
			return false;
		}
	}
	var orderNumber = 1;
	if (len > 0){
		$("#enquiryList").find("tr").each(function(){
			var index=$(this).index(); //当前行
			$("span[name='orderNumber']").eq(index).html(orderNumber++);
		});
	}
}

	  
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass().addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle == "sorting_asc") {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderStyle.split("_")[1]);
	}
	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
	//信息检索
	function searchRevision(){
		selectIndex = -1;
		goPage(1,"auto","desc");
	}
		
	//搜索条件重置
	function searchreset(){
		var dprtId = $.trim($("#dprtId").val());
		
		$("#keyword_search").val("");
		$("#djlx_search").val("");
		
		$("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("#divSearch").find("select").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch").find("textarea").each(function(){
			$(this).val("");
		});
		$("#dprtcode_search").val(dprtId);
		
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
//		authHeightUtil.authHeightOne(pageId);
	}
	
	//航材搜索条件显示与隐藏 
	function search2() {
		if ($("#divSearch2").css("display") == "none") {
			$("#divSearch2").css("display", "block");
			$("#icon2").removeClass("fa-chevron-down");
			$("#icon2").addClass("fa-chevron-up");
		} else {

			$("#divSearch2").css("display", "none");
			$("#icon2").removeClass("fa-chevron-up");
			$("#icon2").addClass("fa-chevron-down");
		}
	}
	//打开报价列表对话框
	function openSupplierWinAdd() {
		if(materialId == '' || enquiryType == ''){
			AlertUtil.showErrorMessage("请选择需要报价的部件!");
			return false;
		}
		$("#alertModalSupplierWinAdd").modal("show");
		showorhideSupplier();
		goPageSupplierWinAdd(1,"auto","desc");  
	}
	
	//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPageSupplierWinAdd(pageNumber,sortType,sequence){
		AjaxSupplierWinAddSearch(pageNumber,sortType,sequence);
	}	
	//信息检索
	function searchSupplierWinAdd(){
		goPageSupplierWinAdd(1,"auto","desc");
	}
	
	//刷新页面
	function refreshPage(){
		goPage(pagination.page, pagination.sort, pagination.order);
	}

	//带条件搜索的翻页
	function AjaxSupplierWinAddSearch(pageNumber,sortType,sequence){
		var searchParam = getSupplierWinAddSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/aerialmaterialfirm/queryWinAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   SelectUtil.selectNode('selectAllId','supplierWinAdd');
			   finishWait();
			   if(data.total >0){
				   appendSupplierWinAddContentHtml(data.rows);
				   new Pagination({
						renderTo : "supplierWinAddPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxSupplierWinAddSearch(pageNumber,sortType,sequence);
						}
					});
				   supplierWinAddData = data.rows;
				   // 标记关键字
				   signByKeyword($("#keyword_supplier_search").val(),[3,4],"#supplierWinAdd tr td");
			   } else {
				   supplierWinAddData = [];
				   $("#supplierWinAdd").empty();
				   $("#supplierWinAddPagination").empty();
				   if(enquiryType == 1){
						$("#supplierWinAdd").append("<tr><td  colspan='7' class='text-center'>暂无数据 No data.</td></tr>");
					}else{
						$("#supplierWinAdd").append("<tr><td  colspan='9' class='text-center'>暂无数据 No data.</td></tr>");
					}
			   }
	     }
	   }); 
	}
	//将搜索条件封装 
	function getSupplierWinAddSearchParam(){
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_supplier_search").val());
		paramsMap.bjid = materialId;
		//searchParam.gyslb = enquiryType.toString();
		searchParam.gyslb = enquiryType;
		searchParam.dprtcode = dprtcode;
		
		var existsIdList = [];
		$("#enquiryList").find("tr").each(function(){
			var index=$(this).index(); //当前行
			var gysid =$.trim($("input[name='gysid']").eq(index).val());
			existsIdList.push(gysid);
		});
		
		if(existsIdList != '' && existsIdList.length > 0){
			 paramsMap.idList = existsIdList;
		}
		 
		if('' != keyword){
			searchParam.keyword = keyword;
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	}

	function appendSupplierWinAddContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='clickRow(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='clickRow(this)' >";
		   }
		   
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' index='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','supplierWinAdd')\" /></td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.GYSMC)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GYSMC)+"</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.GYSBM)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.GYSBM)+"</td>";
		   if(enquiryType == 1){
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.BJ_CLF)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.BJ_CLF)+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.BJ_CLF)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.BJ_CLF)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.BJ_GSF)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.BJ_GSF)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.BJ_QTF)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.BJ_QTF)+"</td>";  
		   }
		   htmlContent = htmlContent + "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.YXQ_KS)+"</td>";
		   htmlContent = htmlContent + "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.YXQ_JS)+"</td>";
	   });
	   $("#supplierWinAdd").empty();
	   $("#supplierWinAdd").html(htmlContent);
	   SelectUtil.selectNode('selectAllId','supplierWinAdd');
	}
//点击行	
function clickRow(e){
	SelectUtil.checkRow($(e).find("input[type='checkbox']"),'selectAllId','supplierWinAdd');
}

	function getSelectRow(id){
		var rowData = {};
		$.each(supplierWinAddData,function(index,row){
			if(id == row.ID){
				rowData = row;
			}
		});
		return rowData;
	}
	
function showorhideSupplier(){
	if(enquiryType == 1){
		$("#supplierHead tr th:eq(4)").show();
		$("#supplierHead tr th:eq(5)").hide();
		$("#supplierHead tr th:eq(6)").hide();
		$("#supplierHead tr th:eq(7)").hide();
	}else{
		$("#supplierHead tr th:eq(4)").hide();
		$("#supplierHead tr th:eq(5)").show();
		$("#supplierHead tr th:eq(6)").show();
		$("#supplierHead tr th:eq(7)").show();
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
		 
		function setTotalPrice(obj){
			var totalPrice = '';
			var bjClf = $(obj).parent().parent().find("input[name='bjClf']").val();
			if(enquiryType == 1){
				totalPrice = bjClf*materialSl;
				
			}else{
				var bjGsf = $(obj).parent().parent().find("input[name='bjGsf']").val();
				var bjQtf = $(obj).parent().parent().find("input[name='bjQtf']").val();
				totalPrice = bjClf*1 + bjGsf*1 + bjQtf*1;
			}
			totalPrice = totalPrice.toFixed(2);
			$(obj).parent().parent().find("td[name='zj']").html(totalPrice);
			$(obj).parent().parent().find("td[name='zj']").attr("title",totalPrice);
		}

		
		//只能输入数字和小数,保留两位
		function clearNoNumTwo(obj){
		     //先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d.]/g,"");
		     //必须保证第一个为数字而不是.
		     obj.value = obj.value.replace(/^\./g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\.{2,}/g,".");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		     
		     var str = obj.value.split(".");
		     if(str.length > 1){
		    	 if(str[1].length > 2){
		    		 obj.value = str[0] +"." + str[1].substring(0,2);
		    	 }
		     }
		     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
		  		 if(obj.value.substring(1,2) != "."){
		  			obj.value = 0;
		  		 }
		  	 }
		     obj.value = validateMax(obj.value);
		     function validateMax(_value){
		    	 if(Number(_value) > 9999999999.99){
		    		return validateMax(_value.substr(0, _value.length-1));
		    	 }
		    	 return _value;
		    }
		     setTotalPrice(obj);
		}
		 
		//只能输入数字和小数
		function clearNoNum(obj){
		     //先把非数字的都替换掉，除了数字和.
		     obj.value = obj.value.replace(/[^\d.]/g,"");
		     //必须保证第一个为数字而不是.
		     obj.value = obj.value.replace(/^\./g,"");
		     //保证只有出现一个.而没有多个.
		     obj.value = obj.value.replace(/\.{2,}/g,".");
		     //保证.只出现一次，而不能出现两次以上
		     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		}
		
		//导出Excel
		function enquiryOutExcel(){		
			 window.open(basePath+"/aerialmaterial/enquiry/enquiryOutExcel?djlx=" + encodeURIComponent(excelLx)
					 +"&id="+encodeURIComponent(excelId)+"&dprtcode="+encodeURIComponent($("#dprtcode_search").val())
					 +"&sl="+excelSl);
		 	}
	