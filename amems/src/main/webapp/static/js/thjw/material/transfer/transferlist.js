var gljb=["","无","批次号管理","序列号管理"];
var hclx=["其他","航材","设备","工具","危险品","低值易耗品"];
var zt=["","收货","正常","冻结","待报废"];
var hclb=["其他","待检库","报废库","待修库","危险品","工具","航材","设备","低值易耗品"];

var ids=[];
$(document).ready(function(){
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		var selectTab = $("#tablist").find("li.active").index();
		
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				if(selectTab == 0){
					$('#hctransfer').trigger('click');
				}else if(selectTab == 1){
					$('#hctransferHistory').trigger('click');
				}
			}
		}
	});
	
	 decodePageParam();//开始的加载默认的内容 
	 Navigation(menuCode);
	 initNav();
	 initStoreSelect();
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
		};
		refreshPermission();       //触发按钮权限监控
});
var pagination = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.hclx = $.trim($("#hclx").val());
	params.ckh = $.trim($("#ckh").val());
	params.dprtcode = $.trim($("#dprtcode").val());
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
		$("#keyword_search").val(params.keyword);
		$("#hclx").val(params.hclx);
		$("#ckh").val(params.ckh);
		$("#dprtcode").val(params.dprtcode);
		if(pageParamJson.pagination){
			goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			goPage(1,"auto","desc");
		}
	}catch(e){
		goPage(1,"auto","desc");
	}
}
function initStoreSelect(){
	var html = "<option value=\"\">显示全部All</option>";
	var dprtcode = $.trim($("#dprtcode").val());
	$.each(userStoreList, function(index, row){
		if(dprtcode == row.dprtcode){
			html += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	$("#ckh").html(html);
}

function changeOrg(){
	var html = "<option value=\"\">显示全部All</option>";
	var dprtcode = $.trim($("#dprtcode").val());
	$.each(userStoreList, function(index, row){
		if(dprtcode == row.dprtcode){
			html += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});
	$("#ckh").html(html);
}

/**
 * 初始化页面sheet功能
 */
function initNav(){
	$('.nav.nav-tabs > li > a').click(function(){
	    var href=$(this).attr('href');
	    $('#tablist a[href="'+href+'"]').tab('show');
	}); 
}

function viewCklist(){
	 goPage(1,"auto","desc");
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		 AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/stock/transferStockList",
			   //url:basePath+"/aerialmaterial/stock/stockList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   success:function(data){
				   
				    finishWait();
				    if(data.rows !=""){
				    	appendContentHtml(data.rows);
				    	 new Pagination({
								renderTo : "pagination",
								data: data,
								sortColumn : sortType,
								orderType : sequence,
								goPage:function(a,b,c){
									AjaxGetDatasWithSearch(a,b,c);
								}
							}); 
						// 标记关键字
						   signByKeyword($("#keyword_search").val(),[3,4,5,6],"#list tr td");
						   
					   } else {
						  $("#list").empty();
						  $("#pagination").empty();
						  $("#list").append("<tr><td class='text-center' colspan=\"17\">暂无数据 No data.</td></tr>");
					   }
				      new Sticky({tableId:'tansfer_list_table'});
		          },
		    }); 
	 }
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 searchParam.hclx = $.trim($("#hclx").val());
		 searchParam.ckh = $.trim($("#ckh").val());
		 searchParam.dprtcode = $.trim($("#dprtcode").val());
		 return searchParam;
	 }
	 
	 function chooesRow2(obj,id,index){
		var $checkbox1 = $("#tansfer_list_table :checkbox[name='kc_h']:eq("+index+")");
		var checked = $checkbox1.is(":checked");
		if(checked){
			$(obj).removeAttr("checked");
			ids.remove(id);
		}else{
			$(obj).attr("checked",true);
			ids.push(id);
		}
	 }
	 function chooesRow1(obj,id,index){
		var $checkbox1 = $("#tansfer_list_table :checkbox[name='kc_h']:eq("+index+")");
		var $checkbox2 = $(".sticky-col :checkbox[name='kc_h']:eq("+index+")");
		var checked = $checkbox1.is(":checked");
		$checkbox1.attr("checked", !checked);
		$checkbox2.attr("checked", !checked);
		if(checked){
			ids.remove(id);
		}else{
			ids.push(id);
		}
	 }
		//扩展数组方法：查找指定元素的下标  
		Array.prototype.indexOf = function(val) {  
		for (var i = 0; i < this.length; i++) {  
		    if (this[i] == val) return i;  
		}  
		return -1;  
		};  

		//扩展数组方法:删除指定元素  
		Array.prototype.remove = function(val) {  
		var index = this.indexOf(val);  
		while(index>-1){  
		    this.splice(index, 1);  
		    index = this.indexOf(val);  
		}  
		};
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick=\"chooesRow1(this,'"+row.id+"','"+index+"')\">";
			   Array.prototype.S=String.fromCharCode(2);
			   Array.prototype.in_array=function(e){
			       var r=new RegExp(this.S+e+this.S);
			       return (r.test(this.S+this.join(this.S)+this.S));
			   };
			   if(ids.in_array(row.id)){
				   htmlContent = htmlContent+ "<td class='fixed-column'><input type=\"checkbox\" value='"+row.id+"' onclick=\"chooesRow2(this,'"+row.id+"','"+index+"')\" name='kc_h' checked='checked' index='"+index+"' onchange=\"checkRow("+index+",this)\">";
			   }else{
				   htmlContent = htmlContent+ "<td class='fixed-column'><input type=\"checkbox\" value='"+row.id+"' onclick=\"chooesRow2(this,'"+row.id+"','"+index+"')\" name='kc_h' index='"+index+"' onchange=\"checkRow("+index+",this)\">";
			   }
			   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(gljb[row.gljb])+"</td>";  			  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.shzh)+"'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ckmc)+"'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.kwh)+"'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right' >"+formatUndefine(row.syts)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();       //触发按钮权限监控
		 
	 }
	 
	 function addTransfer(){
		 var len=ids.length;
		 if(len>0){
			 window.location.href =basePath+"/aerialmaterial/transfer/checkList?ids=" + ids+"&pageParam="+encodePageParam();
		 }else{
			 AlertUtil.showErrorMessage("请至少选择一个航材移库！");
			 return;
		 }
	 }
	 function checkRow(index,this_){
		$("#list :checkbox[name=checkrow]:eq("+index+")").attr("checked", $(this_).is(":checked"));
     }
	 
	 
	//字段排序
	function orderBy(obj) {
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "desc";
		} else {
			orderType = "asc";
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderType);
	}

	
		 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
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
	});
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
	 });
	 var zzjgid=$('#zzjgid').val();
	 $("#keyword_search").val("");
	 $("#dprtcode").val(zzjgid);
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
	
