$(document).ready(function(){
		Navigation(menuCode);
	 	refreshPermission();
		decodePageParam();
//			logModal.init({
//				code : 'B_S_019'
//			});
		$('#hbrq').datepicker({
			autoclose : true,
			clearBtn : true
		});
		validation();
		businessHeight("business_main_pageContent");
		$(window).resize(function(){
			businessHeight('business_main_pageContent');
		});
		
		//导入
		initImport();
			
});
	
//验证
function validation(){
	$('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	dgbh: {
                validators: {
                	notEmpty: {
                        message: '岗位编号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            dgmc: {
                validators: {
                    notEmpty: {
                        message: '岗位名称不能为空'
                    }
                }
            }
        }
    });
}

	var pagination = {};
	var mainpagination={};
	function encodePageParam() {
		var pageParam = {};
		var params = {};
		params.keyword = $.trim($("#keyword").val());
		params.dprtcode = $.trim($("#dprtcode").val());
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

		pagination = {
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 20
		};
		mainpagination={
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 20	
		}
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			url : basePath + "/training/business/businessList",
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
					signByKeyword($.trim($("#keyword").val()),[ 2,3,4 ],"#fjgzjk_list tr td")
				} else {
					$("#fjgzjk_list").empty();
					$("#fjgzjk_pagination").empty();
					$("#fjgzjk_list").append("<tr ><td  class='text-center'  colspan=\"8\">暂无数据 No data.</td></tr>");
				}
				new Sticky({
					tableId : 'fjgz_record_sheet_table'
				});
			}
		});

	}
 
 	function appendContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			  if (index % 2 == 0) {
				  if($("#manidinfo").val()==row.id){
					  htmlContent +="<tr gid='"+row.id+"'   onclick='showDcgzcl(this)' style=\"cursor:pointer\" class='bg_tr'>";
				  }else{					  
					  htmlContent +="<tr gid='"+row.id+"'   onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
				  }
				  
			   } else {
				   if($("#manidinfo").val()==row.id){
					   		htmlContent +="<tr gid='"+row.id+"'  onclick='showDcgzcl(this)' style=\"cursor:pointer\" class='bg_tr'>";
					  }else{					  
						  	htmlContent +="<tr gid='"+row.id+"'  onclick='showDcgzcl(this)' style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
					  }
				  
			   }
		
			htmlContent += "<td  class='text-center fixed-column'>"+
					"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='training:business:manage:02' title='修改 Edit' onclick='update(this)'" +
						" gid='"+StringUtil.escapeStr(row.id)+"' dgbh='"+StringUtil.escapeStr(row.dgbh)+"' " +
						" dgmc='"+StringUtil.escapeStr(row.dgmc)+"' bz='"+StringUtil.escapeStr(row.bz)+"'></i>&nbsp;&nbsp;" +
			/* htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project:annunciate:main:02' onClick=\"edit('"
				+ row.id + "',"+row.zt+",'"+row.dprtcode+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";*/
			
			
					"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission'  permissioncode='training:business:manage:03' title='作废  Invalid' onClick='invalid(\""
						+ row.id+ "\")' ></i></td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left ' title='"+StringUtil.escapeStr(row.dgbh)+"'>"+StringUtil.escapeStr(row.dgbh)+"</td>";  
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.dgmc)+"'>"+StringUtil.escapeStr(row.dgmc)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+getmat(row.businessToMaintenancePersonnels)+"'>"+getmat(row.businessToMaintenancePersonnels)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zdr.username)+"'>"+StringUtil.escapeStr(row.zdr.username)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>";
		    htmlContent += "<td  style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.dprt.dprtname)+"'>"+StringUtil.escapeStr(row.dprt.dprtname)+"</td>";
		    htmlContent += "</tr>";
		    $("#fjgzjk_list").empty();
		    $("#fjgzjk_list").html(htmlContent);
			 
		    //刷新按钮
		    refreshPermission();
	})
}
 	function listQc(xmList){
 		var list = [];
 		$.each(xmList,function(index,oldList){
 			var fl = true;
 			$.each(list,function(index,newList){
 				if(newList == oldList){
 					fl = false;
 				}
 			});
 			if(fl){
 				list.push(oldList);
 			}
 		});
 		return list;
 	}
 	function getmat(list){
 		var str='';
 		var xmList = [];
 		if(list!=null){
 			$.each(list,function(index,row){
 				xmList.push(row.maintenancePersonnel.xm);
 			});
 			if(xmList.length > 0){
 				xmList = listQc(xmList);
 				$.each(xmList,function(index,row){
 	 				if(index==0){
 	 					str +=row;
 	 				}else{
 	 					str +=","+row;
 	 				}
 	 			});
 			}
 			
 			
 		}
 		return str;
 	}
 	//新增初始化
 	function add(){
 		 validation();
 		 
	     $("#alertModaladdupdate").find("input").each(function() {
			$(this).attr("value", "");
		 });
		 $("#alertModaladdupdate").find("textarea").each(function(){
			 $(this).val("");
		 });
		 
		 $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);
		 $("#accredit").html("新增岗位");
		 $("#accreditrec").html("Add Business");
		 
	 	 $("#alertModaladdupdate").modal("show");
 	}
 	
 	//修改
	 function update(obj){
		 validation();
		 var id = $(obj).attr("gid");
		 var dgbh = $(obj).attr("dgbh");
		 var dgmc = $(obj).attr("dgmc");
		 var bz = $(obj).attr("bz");
		 
		 
		 $("#businessid").val(id);
		 $("#bz").val(bz);
		 $("#dgbh").val(dgbh);
		 $("#olddgbh").val(dgbh);
		 $("#dgmc").val(dgmc);
		 $("#accredit").html("修改岗位");
		 $("#accreditrec").html("Update Business");
		/* $("#alertModaladdupdate input").attr("disabled",false);
		 $("#alertModaladdupdate select").attr("disabled",false);
		 $("#alertModaladdupdate textarea").attr("disabled",false);*/
	 	 $("#alertModaladdupdate").modal("show");
	 }	
 	
	 //保存工作记录
	 function saveUpdate(){
		 $('#form1').data('bootstrapValidator').validate();
		  if(!$('#form1').data('bootstrapValidator').isValid()){
			  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
			return false;
		  }			 
		  
		 var obj ={};
		 var id = $("#businessid").val();
		 var url ="";
		 if(id==""){
			 url = basePath+"/training/business/save";//新增
		 }else{
			 url = basePath+"/training/business/edit";//修改9 
			 obj.id = id;
			 obj.dprtcode = $("#dprtcode").val();
		 }
		 var dgbh = $("#dgbh").val();
	 	 var dgmc = $("#dgmc").val();
	 	 var bz = $("#bz").val();
		 obj.dgmc = dgmc;
		 obj.dgbh = dgbh;
		 obj.bz = bz;
		 submitAjax(obj,url);
	 	 }
	 
	 function submitAjax(obj,url){
		 startWait($("#alertModaladdupdate"));
	   	 AjaxUtil.ajax({
	 		url:url,
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#alertModaladdupdate"),
	 		success:function(data){
	 			finishWait($("#alertModaladdupdate"));
	 			AlertUtil.showMessage('操作成功!');
	 			$("#alertModaladdupdate").modal("hide");
	 			decodePageParam();
	 			refreshPermission();
	 		}
	   	   });
	 }
	 
	function getAttacment(list){
		var attachment="";		
		if (list!=null) {
			for (var i = 0; i < list.length; i++) {
 					attachment = attachment
 						+ '<a href="#" title="'+StringUtil.escapeStr(list[i].wbwjm)+'"  onclick="downloadfile(\''
 						+ list[i].id + '\')" >'
 						+ StringUtil.escapeStr(list[i].wbwjm) + '</a><br>';
 					}
 			}			
 			return attachment;
 		}

     function searchFjgzRecord(){
    	 goPage(1, "desc", "auto");
    	 $('#dcgzcl_div').css("display","none");
    	 businessHeight('business_main_pageContent');
    	 $("#manidinfo").val("");
     }
     
     function showDcgzcl(this_){
    	// 下方div是否显示
			var isBottomShown = false;
			if($("#dcgzcl_div").is(":visible")){
				isBottomShown = true;
			}
    	 var id = $(this_).attr("gid");
    	 var dprtcode=$(this_).attr("dprtId");
    	$("#manidinfo").val(id);
		 $("#dprtId").val(dprtcode);
		 showInfo(1, "desc", "auto");
    	 if($("#dcgzcl_div").css("display")=='none'){  		 
    		
    		 $("#dcgzcl_div").css("display","block");
    	 }
    	 $(this_).addClass('bg_tr').siblings().removeClass('bg_tr');
    	 businessHeight('business_main_pageContent');
    	 if(!isBottomShown){
    		 TableUtil.makeTargetRowVisible($(this_), $("#tableId"));
    	 }
    	 var ztContentHtml="";
    	
     }
     
     
     $("#dprtcode").change(function() {
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
 	//隐藏Modal时验证销毁重构
	 $("#alertModaladdupdate").on("hidden.bs.modal", function() {
	 	 $("#form1").data('bootstrapValidator').destroy();
	      $('#form1').data('bootstrapValidator', null);
	      validation();
	 });
	 
	 function invalid(id){
		 AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
				var obj={};
				obj.id=id;
				url = basePath+"/training/business/invalid";//作废
				submitAjax(obj,url);
				hideBottom();
			 }});
	 }
	 	//设置高度
	 	function businessHeight(id){
	 	    //头部高度
	 	    var headerHeight = $('.header').outerHeight();
	 	    var headerDownHeight = $('.header-down').outerHeight();
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
	 	    var searchContent=$("#"+id+" .searchContent").outerHeight()||0;
	 	    //body的高度
	 	   
	 	    
	 	 
	 	  //情景2：table-table
	 	    if($("#"+id+".table-table-type").length>0){
	 	    	 var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
	 	      	//表格的高度
	 	          var tableHeight = bodyHeight - searchContent - paginationHeight -20 - adjustHeight;
	 	         //谷歌的兼容性问题
	 	          if(navigator.userAgent.indexOf("Chrome") > -1){
	 	          	tableHeight -= 10;
	 	          }
	 	        //隐藏的div是否显示
	 	          if($("#"+id+" .bottom_hidden_content").css("display")=='block'){
	 	        	  $("#"+id+" .table-height").attr('style', 'height:' + tableHeight*0.45+ 'px !important;overflow-x: auto;');
	 	        	
	 	        		  /*$(".table-height").attr('style', 'height:190px !important;overflow-x: auto;');  */
	 	        	 
	 	        	  
	 	        	  var table_height=parseInt($("#"+id+" .table-height").css("height"));
	 	        	  var bottom_height=tableHeight-(table_height);
	 	        	  var bottompanelheader=$("#"+id+" .bottom_hidden_content .panel-heading ").outerHeight()||0;
	 	        	  var bottomHiddenButton=$("#"+id+" .bottom-hidden-button").outerHeight()||0;
	 	        	  var bottomHiddenPagination=$("#"+id+" #fjgzjkInfo_pagination").outerHeight()||0
	 	        	  var bottom_hidden_table_content=bottom_height-bottompanelheader-bottomHiddenButton-bottomHiddenPagination-26;
	 	        	  $("#"+id+" .bottom-hidden-table-content").css("height",bottom_hidden_table_content+"px")
	 	          }else{
	 	        	$("#"+id+" .table-height").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
	 	          }
	 	    }
	 	    
	 	 
	 	}
	 	
		/**
		 * 初始化导入
		 */
		function initImport(){
			// 初始化导入
		   importModal.init({
			    importUrl:"/training/business/excelImport",
			    downloadUrl:"/common/templetDownfile/18",
				callback: function(data){
					decodePageParam();
					$("#ImportModal").modal("hide");
				},
			});
		}
		/**
		 * 显示导入模态框
		 */
		function showImportModal(){
			 importModal.show();
		}
		
		function exportExcel(){
			var keyword = $.trim($("#keyword").val());
			var dprtcode = $("#dprtcode").val();
			window.open(basePath+"/training/business/Post.xls?dprtcode="
		 				+ dprtcode + "&keyword="+ encodeURIComponent(keyword));
			
		}
		
		//回车事件控制
		document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchFjgzRecord(); //调用主列表页查询
				}
			}
		};
		
		function hideBottom(){
			$("#dcgzcl_div").css("display","none");
			App.resizeHeight();
		}