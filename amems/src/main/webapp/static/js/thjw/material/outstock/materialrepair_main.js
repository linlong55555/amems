
$(document).ready(function(){
	
	

	validatorForm =  $('#sendaform').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        		username1: {
	                validators: {
	                	notEmpty: {
	                        message: '出库人不能为空'
	                    }
	                }
	            },
	            ckrq1: {
	                validators: {
	                	notEmpty: {
	                        message: '出库日期不能为空'
	                    }
	                }
	            },
	            bz1: {
	                validators: {
	                	 stringLength: {
	                         max: 300,
	                         message: '长度最多不超过个300字符'
	                     }
	                }
	            }
        	}
    });
	
	
	initStoreSelects();
	 $("#dprtcode1").on("change",initStoreSelects);
});

function initStoreSelects(){
	
	var html = "<option value=\"\">显示全部All</option>";
	var dprtcode = $("#dprtcode1").val();
	$.each(userStoreList, function(index, row){
		if(row.cklb==3 && dprtcode == row.dprtcode){
			html += "<option value=\""+row.ckh+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	$("#ckh1").html(html);
}

var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
//	params.sqrname = $.trim($("#sqrname1").val());
	params.ckh = $.trim($("#ckh1").val());
	params.dprtcode = $.trim($("#dprtcode1").val());
	params.keyword = $.trim($("#sendakeyword_search").val());
	params.flightdate = $("#flightDate_search1").val();

	
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
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
//		$("#sqrname1").val(params.sqr);
		$("#ckh1").val(params.ckh);
		$("#dprtcode1").val(params.dprtcode);
		$("#sendakeyword_search").val(params.keyword);
	    $("#flightDate_search1").val(params.flightdate);
		
		if(pageParamJson.pagination){
			sendagoPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			sendagoPage(1,"auto","desc");
		}
	}catch(e){
		sendagoPage(1,"auto","desc");
	}
}

	 //带条件搜索的翻页
	 function sendaAjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword =  $.trim($("#sendakeyword_search").val());
		
		obj.fxDateBegin = searchParam.fxDateBegin;
		obj.fxDateEnd = searchParam.fxDateEnd;
		obj.ckh = searchParam.ckh;
//		obj.sqrname = searchParam.sqrname;
		obj.dprtcode = searchParam.dprtcode;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/materialrepair/repairList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				   
				    finishWait();
				    if(data.rows !=""){
				    	sendaappendContentHtml(data.rows);
						   
				    	 new Pagination({
								renderTo : "sendapagination",
								data: data,
								sortColumn : sequence,
								orderType : sortType,
								goPage:function(a,b,c){
									sendaAjaxGetDatasWithSearch(a,c,b);
								}
							}); 
				    	
						// 标记关键字
					   signByKeyword($("#sendakeyword_search").val(),[3,5,7,8,9,10,12]);
						   
					   } else {
					
						  $("#sendalist").empty();
						  $("#sendapagination").empty();
						  $("#sendalist").append("<tr><td class='text-center' colspan=\"17\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'sxck'});
		      }
		    }); 
	 }
	 
	 function sendaappendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			
			   if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent += "<tr bgcolor=\"#fefefe\">";
				}
			   
			   htmlContent = htmlContent + "<td class=' text-center' ><label><input  name='ids' type='radio' value='"+row.id+"'  /></label></td>";  
			   htmlContent += "<td  style='vertical-align: middle;'  align='center'><input type='hidden' id='"+row.id+"'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class=' text-center' ><input type='hidden'  name='id' value='"+row.id+"'><a href='javascript:void(0);' onclick=\"viewDetail('"+row.sxid+"')\"><span name='keyword'>"+StringUtil.escapeStr(row.sqdh)+"</span></a></td>";  
			   htmlContent = htmlContent + "<td class='text-center' ><input type='hidden'  name='sqrid' value='"+row.sqrid+"'><a href='javascript:void(0);' onclick=\"viewDetailsx('"+row.htid+"')\">"+StringUtil.escapeStr(row.htlsh)+"</a> </td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.sqr)+"'>"+StringUtil.escapeStr(row.sqr)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.sqsj).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.grn)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.xh)+"'>"+StringUtil.escapeStr(row.xh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.shzh)+"'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right'>1</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#sendalist").empty();
		   $("#sendalist").html(htmlContent);
	       refreshPermission();
	 }
	 
		//查看送修申请单
	 function viewDetail(id){
	 	window.open(basePath+"/aerialmaterial/repair/view?id=" + id+"&t=" + new Date().getTime());
	 }
	 
	 //查看合同
	 function viewDetailsx(id){
		 window.open(basePath+"/aerialmaterial/contract/view?id=" + id+"&t=" + new Date().getTime());
	 }
	//信息检索
	function searchsenda(){
		sendagoPage(1,"auto","desc");
	}
	
	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function sendagoPage(pageNumber,sortType,sequence){
		sendaAjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 
		 searchParam.sqrname = $("#sqrname1").val();
		 searchParam.ckh = $("#ckh1").val();
		 searchParam.dprtcode = $("#dprtcode1").val();
		 var flightdate = $("#flightDate_search1").val();
		 if(null != flightdate && "" != flightdate){
			 searchParam.fxDateBegin = flightdate.substring(0,4)+"-"+flightdate.substring(5,7)+"-"+flightdate.substring(8,10);
			 searchParam.fxDateEnd = flightdate.substring(12,17)+"-"+flightdate.substring(18,20)+"-"+flightdate.substring(21,23);
		 }
		 
		 return searchParam;
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
	  * 选择用户
	  * @returns
	  */
	 function selectUser1(){
	 	userModal.show({
	 		selected:$("#userid1").val(),//原值，在弹窗中默认勾选
	 		dprtcode:$("#dprtcode1").val(),
	 		callback: function(data){//回调函数
	 			$("#userid1").val(formatUndefine(data.id));
	 			$("#username1").val(StringUtil.escapeStr(data.username)+" "+StringUtil.escapeStr(data.realname));
	 			$("#ckbmid1").val(StringUtil.escapeStr(data.bmdm));
	 			
	 			  $('#sendaform').data('bootstrapValidator')  
	 		      .updateStatus('username1', 'NOT_VALIDATED',null)  
	 		      .validateField('username1');  
	 		}
	 	});
	 }	
	 
	 /**
	  * 送修出库
	  * @returns
	  */
	 function putoutstorage1(){
		 
		 $('#sendaform').data('bootstrapValidator').validate();
		 
		  if(!$('#sendaform').data('bootstrapValidator').isValid()){
			return false;
		  }	
		  
		  var ids = [];
		  $("#sendalist").find("td input[type='radio']:checked").each(function(){
				ids.push( $(this).val());
			});
		  console.info(ids);
			if(ids==""){
				AlertUtil.showErrorMessage("请选择需要操作的项");
				return;
			}
			
			if(ids.length>1){
				AlertUtil.showErrorMessage("只能选择一个项");
				return;
			}
		  
			 AlertUtil.showConfirmMessage("您确定要出库吗？",{callback: function(){
				
				var fixedCheckItem = {
						
						
						id :$.trim($("#sendalist").find("td input[type='radio']:checked").val()),//送修单id
						cukid :$.trim($('#userid1').val()),//出库人id
						ckbmid :$.trim($('#sendackbmid').val()),//出库人部门
						cksj :$.trim($('#ckrq1').val()),//出库日期
						bz :$.trim($('#bz1').val()),//备注
					};
				
			
				startWait();
					
				// 提交数据
				AjaxUtil.ajax({
					url : basePath+"/aerialmaterial/materialrepair/sendOutbound",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(fixedCheckItem),
					dataType:"json",
					success:function(data){
							finishWait();//结束Loading
						if (data.state == "success") {
							$("#alertModal").modal("show");
								$('#alertModal').on('hidden.bs.modal', function (e) {
									window.location = basePath+"/aerialmaterial/outstock/main?pageParam="+encodePageParam()+"&t=" + new Date().getTime()+"#senda";
								});
								$("#alertModalBody").html("出库成功!");
								refreshPermission();
						} else {
							AlertUtil.showErrorMessage(data.message);
						}
					}
				});
				
			 }});
	 }	 

		
		//搜索条件重置
		function searchresetsenda(){
			$("#divSearch1").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch1").find("textarea").each(function(){
			$(this).val("");
		});
		
		 $("#divSearch1").find("select").each(function(){
				$(this).val("");
			});
		
		 var zzjgid=$('#zzjgid').val();
		 $("#sendakeyword_search").val("");
		 $("#dprtcode1").val(zzjgid);
		 $("#dprtcode1").trigger("change");
		}

		//字段排序
		function orderBysenda(obj, _obj) {
			$obj = $("#sxck th[name='column_"+obj+"']");
			var orderStyle = $obj.attr("class");
			$("#sxck .sorting_desc").removeClass("sorting_desc").addClass("sorting");
			$("#sxck .sorting_asc").removeClass("sorting_asc").addClass("sorting");
			
			var orderType = "asc";
			if (orderStyle.indexOf ( "sorting_asc")>=0) {
				$obj.removeClass("sorting").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$obj.removeClass("sorting").addClass("sorting_asc");
				orderType = "asc";
			}
			var currentPage = $("#sendapagination li[class='active']").text();
			sendagoPage(currentPage,obj, orderType);
		}

		//全选
		 function checkAll(){
				$("[name=ids]:checkbox").each(function() { 
					 $(this).attr("checked", 'checked'); 
				 });
				 
			}
		//全不选
		 function notCheckAll(){
				 $("[name=ids]:checkbox").each(function() { 
					 $(this).removeAttr("checked"); 
				 });
			}

	function checkbox_change(index, _this){
		$(this).attr("checked", $(_this).is(":checked"));
		
	}
		