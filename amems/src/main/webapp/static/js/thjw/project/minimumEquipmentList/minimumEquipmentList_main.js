var choseAllIds=[];	
var revisionNoticeBookitemData = [];
var pagination={};
	$(document).ready(function(){
			Navigation(menuCode);
			 decodePageParam();
		 	//goPage(1,"auto","desc");//开始的加载默认的内容 
			 
			//时间控件
				$('#wcrq').datepicker({
					 autoclose: true,
					 clearBtn:true
				}).on('hide', function(e) {
					  $('#form').data('bootstrapValidator')  
			        .updateStatus('wcrq', 'NOT_VALIDATED',null)  
			        .validateField('wcrq');  
				});
			//初始化日志功能
			//logModal.init({code:'B_G_005'});		
	});

	
	/**
	 * 查看修订通知书
	 * intoViewRevisionNoticeBook
	 * @param id
	 */
	function view(id,dprtcode) {
		window.open(basePath+"/project/revisionNoticeBook/intoViewRevisionNoticeBook?id="+ id+"&dprtcode="+dprtcode);
		
	}
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var paramsMap = {};
		annunciateitemData=[];
		paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination=pagination;
		obj.keyword = $.trim($("#keyword_search").val());//关键字查询
		 var xdqx = $.trim($("#xdqx").val());
		 if(null != xdqx && "" != xdqx){
			 var xdqxBegin = xdqx.substring(0,10)+" 00:00:00";
			 var xdqxEnd = xdqx.substring(13,23)+" 23:59:59";
			 paramsMap.xdqxBegin = xdqxBegin;
			 paramsMap.xdqxEnd = xdqxEnd;
		 }
		 obj.paramsMap=paramsMap;
		if(id != ""){
			obj.id = id;
			id = "";
		}
		//obj.id = '1'';//搜索条件
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/engineering/mel/main",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
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
						//controller: this_
					});
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[1,2,5,6,7,9,14]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"18\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
	 
	 
	 function appendContentHtml(list){
		 choseAllIds=[];
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   choseAllIds.push(row.id);
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" >";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\"  >";
			   }
			   htmlContent = htmlContent + "<td  class='text-center fixed-column'><a href=\"javascript:view('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.xddh))+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
			   htmlContent = htmlContent + "<td  class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.ckzl))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ckzl))+"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.xdzt))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.xdzt))+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.xdnr))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.xdnr))+"</td>";  
			   if(undefined != row.xdr_user){
				   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.xdr_user.displayName)+"</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine((row.xdsj).substring(0,10))+"</td>"; 
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.bz))+"</td>";  
			   if(row.oam != null && row.oam.length>0){
				   htmlContent = htmlContent + "<td title='' class='text-left'>"+formatScwj(row.oam)+"</td>";  
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'></td>";  
			   }
			   if(row.shr_user){
				   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.shr_user.displayName)+"</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.shsj)+"</td>"; 
			   if(row.pfr_user){
				   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.pfr_user.displayName)+"</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.pfsj)+"</td>"; 
			   if(row.zdr_user){
				   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.zdr_user.displayName)+"</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
			   htmlContent = htmlContent + "<td  class='text-center'>"+formatUndefine(row.zdsj)+"</td>"; 
			   if(row.bm_dprt){
				   htmlContent = htmlContent + "<td  class='text-left'>"+formatUndefine(row.bm_dprt.dprtname)+"</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
			   if(row.jg_dprt){
				   htmlContent = htmlContent + "<td  class='text-left'>"+formatUndefine(row.jg_dprt.dprtname)+"</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td  class='text-left'></td>";
			   }
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
	 }
	 
	 function formatScwj(list){
			var htmlContent = '';
				$.each(list,function(i,row){
					htmlContent = htmlContent
					+ '<a href="#" title="'+StringUtil.escapeStr(row.wbwjm)+'"  onclick="downloadfile(\''
					+ row.id + '\')" >'
					+ StringUtil.escapeStr(row.wbwjm) + '</a><br>';
				});
			return htmlContent;
		}
	//附件下载
	 function downloadfile(id){
	 	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
	 	//window.open(basePath + "/common/orderDownfile/" + id);
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
				//alert(choseAllIds.length);
				 for(var i=0;i<choseAllIds.length;i++){
					 //alert("a");
					 var flag = $("#"+choseAllIds[i]).is(":hidden");
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
					callback: function(){//回调函数
						if(idArr.length > 0){
							batchApproval(idArr,isApprovel);
						}
					}
				});
			}

			function batchApproval(idList,isApprovel){
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
						idList:idList
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
