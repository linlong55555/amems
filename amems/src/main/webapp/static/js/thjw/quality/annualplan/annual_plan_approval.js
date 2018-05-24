 $(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$("#change_record_pagination").css("display","none");
		$("#process_record_pagination").css("display","none");
		$("#process_record_table").removeClass("table-bordered");
		$("#change_record_table").removeClass("table-bordered");
		$("#year_search").datepicker({
	        language: "zh-CN",
	        todayHighlight: true,
	        format: 'yyyy',
	        autoclose: true,
	        startView: 'years',
	        maxViewMode:'years',
	        minViewMode:'years'
		});
		$(".operation_td").hide();
		annual_plan_approval_main.initList();
	});
 annual_plan_approval_main = {
			id : "annual_plan_approval_main",
			initList: function(){
				//加载待审核数据
				this.getDshsj();
				
			},
			getDshsj : function(dprtcode){
				var this_ = this;
				var searchParam = {};
				if(dprtcode){
					searchParam.dprtcode = dprtcode;
				}else{	
					searchParam.dprtcode = userJgdm;
				}
				searchParam.zt = 2;
				
				AjaxUtil.ajax({
					   url:basePath+"/quality/annualplan/queryAllByZt",
					   type: "post",
					   contentType:"application/json;charset=utf-8",
					   dataType:"json",
					   data:JSON.stringify(searchParam),
					   success:function(data){
				 			if(data != null && data.length >0){
				 				//拼接待批准数据
				 				this_.appendDshsj(data);
				 				//加载其他数据
				 				this_.getdata();
				 				
				 			}else{
				 				//拼接暂无数据
				 				this_.appendZwsj();
				 			}
					   }
				  }); 
			},
			getdata : function (){
				var this_ = this;
				//数据当前年度审核id
				var ndshId = $("input:radio[name='ndshId']:checked").val();
				var nf = $("input:radio[name='ndshId']:checked").next().val();
				var bbh = $("input:radio[name='ndshId']:checked").next().next().val();
				$("#year_search").val(nf);
				$("#bbh").val(bbh);
				
				//加载附件清单
				this_.getFj(ndshId);
				//加载流程记录
				process_record.load(ndshId);
				//加载更变记录
				change_record.load(nf);
				//年度审核计划
				course_list_table_div.decodePageParam(true); 
				/* 隐藏列 */
				$(".operation_td").css("display","none");
				//年度视图
				year_list_table_div.decodePageParam(); 		       
				
			},
			getFj : function (ndshId){
				var this_ = this;
				AjaxUtil.ajax({
					   url:basePath+"/quality/annualplan/queryFjByNdshId",
					   type: "post",
					   dataType:"json",
					   async : false,
					   data:{
						   id : ndshId
					   },
					   success:function(data){
						  var rows = data.rows;
				 			if(rows != null && rows.length >0){
				 				//拼接待批准数据
				 				this_.appendFj(rows);
				 				
				 			}else{
				 				//拼接暂无数据
				 				$("#fjList").empty();
								$("#fjList").append("<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>");
				 			}
					   }
				  }); 
			},
			appendFj : function (list){
				var str = "";
				$.each(list,function(index,row){
					str += "<tr>";
					str += "<td class='colwidth-5 text-center'>"+StringUtil.escapeStr(row.wbwjm)+"."+StringUtil.escapeStr(row.hzm)+"</td>";
					str += "<td class='text-center'>"+StringUtil.escapeStr(row.wjdx)+" KB"+"</td>";
					str += "</tr>";
				});
				 $("#fjList").empty();
				 $("#fjList").html(str);
			},
			getGbjl : function (ndshId){
				
			},
			appendZwsj : function (){
				$("#dshsj").empty();
				$("#dshsj").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
				$("#fjList").empty();
				$("#fjList").append("<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>");
				$("#process_record_list").empty();
				$("#process_record_list").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
				$("#change_record_list").empty();
				$("#change_record_list").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
				$("#course_list_table_div_list").empty();
				$("#course_list_table_div_list").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
				$("#year_list_table_div_list").empty();
				$("#year_list_table_div_list").append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
			},
			appendDshsj : function (list){
				var str = "";
				$.each(list,function(index,row){
					str += "<tr onclick = changeRadio(this)>";
					
					var checked = "";
					
					if((index == 0 && !todo_nf) || todo_nf == StringUtil.escapeStr(row.nf)){
						checked = "checked=checked";
					}
					
					str += "<td class='colwidth-5 text-center'><input value="+row.id+" type='radio' "+checked+" name='ndshId'  /><input type='hidden' value="+row.nf+" /><input type='hidden' value="+row.bbh+" /></td>";
					
					str += "<td class='colwidth-5 text-center'>"+StringUtil.escapeStr(row.nf)+"</td>";
					str += "<td class='text-left'>"+StringUtil.escapeStr(row.ndjhsm)+"</td>";
					str += "<td class='text-left'>R"+StringUtil.escapeStr(row.bbh)+"</td>";
					str += "</tr>";
				});
				 $("#dshsj").empty();
				 $("#dshsj").html(str);
			},
			
			
			/**
			 *  字段排序
			 */
			orderBy: function(obj){
			  var this_=this;
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
				var currentPage = $("#"+this_.id+"_pagination li[class='active']").text();
				this_.goPage(currentPage,obj,orderStyle.split("_")[1]);
			},
			/**
			 * 批准
			 */
			approval: function (){
				 var id =$("input:radio[name='ndshId']:checked").val();
				 if(id == null || id==""){
					 AlertUtil.showMessage("当前没有可批准的数据!");
					 return false;
				 }
				 
				 var this_ = this;
				 var str = "是否确定批准?";
				 AlertUtil.showConfirmMessage(str, {callback: function(){
					 	var message = "批准成功!";
						var url = basePath+"/quality/annualplan/approval";
						var obj ={};
						obj.id = id;
						obj.pfyj = $("#pfyj").val();
						this_.performAction(url, obj, message);
						$("#ndjhnums").text(0);    //数量标识
					}});
			},
			/**
			 * 批准驳回
			 */
			approvalBh: function (){
				var id =$("input:radio[name='ndshId']:checked").val();
				 if(id == null || id==""){
					 AlertUtil.showMessage("当前没有可批准驳回的数据!");
					 return false;
				 }
				 var this_ = this;
				 var str = "是否确定批准驳回?";
				 AlertUtil.showConfirmMessage(str, {callback: function(){
					 	var message = "批准驳回成功!";
						var url = basePath+"/quality/annualplan/approvalBh";
						var obj = {};
						obj.id = id;
						obj.pfyj = $("#pfyj").val();
						this_.performAction(url, obj, message);
						$("#ndjhnums").text(0);    //数量标识
					}});
			},
			/**
			 * 提交后台
			 */
		  performAction :function(url, obj, message){
					var this_ = this;
					startWait();
					// 提交数据
					AjaxUtil.ajax({
						contentType:"application/json;charset=utf-8",
						url:url,
						type:"post",
						data:JSON.stringify(obj),
						dataType:"json",
						success:function(data){
							finishWait();
							$("#pfyj").val("");
							AlertUtil.showMessage(message);
							annual_plan_approval_main.initList();
						}
					});
			},
			openzrdw : function(obj,jdbs){
				var this_ = this;
				var dprtname = $("#annual_plan_module_"+obj).val();
				var dprtcode = $("#annual_plan_module_"+obj+"id").val();
				departmentModal.show({
					dprtnameList : dprtname,// 部门名称
					dprtcodeList : dprtcode,// 部门id
					jdbs:jdbs,
					type : false,// 勾选类型true为checkbox,false为radio
					dprtcode : $("#dprtcode").val(),//
					callback : function(data) {// 回调函数
						$("#annual_plan_module_"+obj).val(data.dprtbm+" "+data.dprtname);
						$("#annual_plan_module_"+obj+"bh").val(data.dprtbm);
						$("#annual_plan_module_"+obj+"mc").val(data.dprtname);
						$("#annual_plan_module_"+obj+"id").val(data.dprtcode);
						if(obj != 'shdx_search'){
							$('#annual_plan_alert_Modal_from').data('bootstrapValidator')  
				 		      .updateStatus("annual_plan_module_"+obj+"", 'NOT_VALIDATED',null)  
				 		      .validateField("annual_plan_module_"+obj+""); 
						}
					}
				})
			},
			openUser : function(obj){
				var this_=this;
				var userList = [];
				var a = $.trim($("#annual_plan_module_"+obj+"id").val());
				var b = $.trim($("#annual_plan_module_"+obj).val());
				for (var i = 0; i < a.split(",").length; i++) {
					var p = {
						id : a.split(",")[i],
						displayName : b.split(",")[i]
					};
					userList.push(p);
				}
				if (a == "") {
					userList = [];
				}
				Personel_Tree_Multi_Modal.show({
					checkUserList:userList,//原值，在弹窗中回显
					dprtcode:$("#dprtcode").val(),//
					parentWinId : "xlhExistModal",
					callback: function(data){//回调函数
						var displayName = '';
						var mjsrid = '';
						if(data != null && data != ""){
							$.each(data, function(i, row){
								displayName += formatUndefine(row.displayName) + ",";
								mjsrid += formatUndefine(row.id) + ",";
							});
						}
						if(displayName != ''){
							displayName = displayName.substring(0,displayName.length - 1);
						}
						if(mjsrid != ''){
							mjsrid = mjsrid.substring(0,mjsrid.length - 1);
						}
						$("#annual_plan_module_"+obj).val(displayName);
						$("#annual_plan_module_"+obj+"id").val(mjsrid);
					}
				});
			}
		};
 
 
 
 
 
 
 
 
 
 
 
 
    //搜索条件显示与隐藏 
    function search() {
    	if ($("#divSearch").css("display") == "none") {
    		$("#divSearch").css("display", "block");
    		App.resizeHeight();
    		$("#icon").removeClass("icon-caret-down");
    		$("#icon").addClass("icon-caret-up");
    	} else {
    		$("#divSearch").css("display", "none");
    		App.resizeHeight();
    		$("#icon").removeClass("icon-caret-up");
    		$("#icon").addClass("icon-caret-down");
    	}
    }
    /* 新增对话框 */
    function openWinAdd(){
    	$("#annual_plan_alert_Modal").modal("show");
    }
    function addAttachment(){
    	$("#attachment_alert_Modal").modal("show");
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			attachHead:false,
		});//显示附件
    	$("#left_column").attr("class","col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0");
		$("#left_column").siblings("div").attr("class","col-sm-10 col-xs-9 padding-leftright-8");
		$("#attachments_list_edit .panel-body").css("padding-top","0px");
    	
    }
    function customResizeHeight(bodyHeight, tableHeight){
		var panelHeadingHeight = $('.panel-heading').outerHeight();
		 var rowHeight = $('.row-height:visible').outerHeight()||0;
		//隐藏的div
		if(panelHeadingHeight<35){
			bodyHeight = bodyHeight + panelHeadingHeight - 35;
        }
		var left_first_content=263
		
	    $(".left_first_content").attr('style', 'height:' + left_first_content+ 'px !important;padding-top:0px');
	  
	    var left_second_body=bodyHeight-218;
	    if(navigator.userAgent.indexOf("Chrome") > -1){
	    	left_second_body -= 9;
        }
	    $(".left_second_content").attr('style', 'height:'+left_second_body+'px !important;overflow-x: auto;margin-bottom:0px;border:1px solid #d5d5d5;');
	    
	    $("#maintenance_toggle_div").css("top",($("#left_div").outerHeight()/2)+"px");
	    if($(".tabcontent-main").length>=1){
       	 /*tab-row-heigt tab 中查询条件*/
        var tabRowHeight=$(".first-tab-style .tab-content .row-height:visible").outerHeight()||0;
       	 var tabHeight=bodyHeight-rowHeight-5;
       	 var paginationFirstHeight=$(".first-tab-style .page-height:visible").outerHeight()||0;
       	 var tabHead=$(".first-tab-style ul.nav-tabs ").outerHeight()||0;
       	 if(navigator.userAgent.indexOf("Chrome") > -1){
       		 tabHeight -= 10;
              }
       	 if($(".displayTabContent").is(":hidden")){
       	 var tabBody=tabHeight-tabHead;
       	 $(".first-tab-style .tab-content").attr("style","height:"+tabBody+"px;");
            var tabTableHeight=tabBody-paginationFirstHeight-tabRowHeight-20;
       	 $(".first-tab-style .tab-content .tab-pane:visible").find("table").parent("div").attr("style","height:"+tabTableHeight+"px;overflow:auto;");	
       	 }else{
    		 if($(".displayTabContent").length==0){
    			 var tabBody=tabHeight-tabHead;
            	 $(".first-tab-style .tab-content").attr("style","height:"+tabBody+"px;");
                 var tabTableHeight=tabBody-paginationFirstHeight-tabRowHeight-10;
            	 $(".first-tab-style .tab-content .tab-pane:visible").find("table").parent("div").attr("style","height:"+tabTableHeight+"px;overflow:auto;");	
         	}else{
         		 $tabDiv = $(".first-tab-style");
              	if($tabDiv.length > 0){
              		var cTabHeight = $tabDiv.attr("c-height");
              		var tempTabTableHight = $tabDiv*0.45;
              		if(cTabHeight){
              			
              			if(cTabHeight.indexOf("%") > 0){
              				cTabHeight = cTabHeight.replace("%","");
              				cTabHeight = cTabHeight/100;
              				tempTabTableHight = tabHeight * cTabHeight;
              			}else{
              				tempTabTableHight = cTabHeight;
              			}
              		} 
              		
         	 }
              var firstTabBody=tempTabTableHight-tabHead;
              $(".first-tab-style .tab-content").attr("style","height:"+firstTabBody+"px;");
              var firstTabTableHeight=firstTabBody-paginationFirstHeight-tabRowHeight-20;
           	  $(".first-tab-style .tab-content .tab-pane:visible").find("table").parent("div").attr("style","height:"+firstTabTableHeight+"px;overflow:auto;");
           	  var displayTabContent= tabHeight-tempTabTableHight-3;
           	  $(".displayTabContent").attr("style","height:"+displayTabContent+"px;");
           	  var paginationDisplay=$(".displayTabContent .page-height:visible").outerHeight()||0
           	  if($(".displayTabContent>ul").length>0 ){
           		 var displayContentHeader=$(".displayTabContent>ul").eq(0).outerHeight()||0;  
           		 var dispalyContentSearch=$(".displayTabContent .row-height:visible").eq(0).outerHeight()||0;
           		 var displayContent=displayTabContent-displayContentHeader;
           		 var displayContentTableHeight=displayContent-dispalyContentSearch-23-paginationDisplay;
           		  $(".displayTabContent .tab-content .tab-pane:visible").find("table").parent("div").attr("style","height:"+displayContentTableHeight+"px;overflow:auto;");
           	  }
         	}
    }
       }
	    
        // 浮动日志div高度
        $("#floatDiv").height($(".page-content:first>div").height()-13);
        try{
        	$("#logTableDiv").height($("#log_pagination").offset().top-$("#logTableDiv").offset().top - 5);
        }catch(e){}
	}
    
    function toggleAnnual(obj){
    var i = $(obj);
	if(i.hasClass("icon-caret-left")){
		i.removeClass("icon-caret-left").addClass("icon-caret-right");
		$("#left_div").hide();
		$("#right_div").removeClass("col-sm-9").addClass("col-sm-12");
	}else{
		i.removeClass("icon-caret-right").addClass("icon-caret-left");
		$("#left_div").show();
		$("#right_div").removeClass("col-sm-12").addClass("col-sm-9");
	}
	App.resizeHeight();
    }
    
    function changeRadio (obj){
    	$(obj).find("input:radio[name='ndshId']").attr('checked', 'checked');
    	annual_plan_approval_main.getdata();
    }
    function dprtChange(obj){
    	 $("#divSearch").find("input").each(function() {
     		$(this).attr("value", "");
     	});
    	annual_plan_approval_main.getDshsj($(obj).val());
    }
    function searchRevision (){
 	   if($("#dshsj").find("tr").eq(0).find("td").length > 1){
 		   annual_plan_approval_main.getdata();
 	   }
 	   
    }
  //搜索条件重置
    function searchreset(){
    	 $("#divSearch").find("input").each(function() {
    		$(this).attr("value", "");
    	});
    	 $("#keyword_search").val("");
    }