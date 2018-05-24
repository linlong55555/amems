
$(document).ready(function(){
	
	

	validatorForm =  $('#inventoryform').bootstrapValidator({
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
	                        message: '出库日期不能为空'
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
	
	initStoreSelect1();
	$("#dprtcode2").on("change",initStoreSelect1);
	 
});

/**
 * 更改选中仓库
 */
function searchckh(){
	inventorygoPage(1,"auto","desc");//开始的加载默认的内容 
}

function initStoreSelect1(){
	
	var storeHtml = "<option value=\"\">显示全部All</option>";
	var cklbs=[0,4,5,6,7,8];
	var dprtcode = $("#dprtcode2").val();
	$.each(userStoreList, function(index, row){
		if($.inArray(row.cklb, cklbs)>=0 && dprtcode == row.dprtcode){
			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>";
		}
	});

	$("#ckh2").html(storeHtml);
}

var pagination1 = {};
/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam1(){
	var pageParam = {};
	var params = {};
	params.hclx = $.trim($("#hclx2").val());
	params.ckh = $.trim($("#ckh2").val());
	params.dprtcode = $.trim($("#dprtcode2").val());
	params.keyword = $.trim($("#inventorykeyword_search").val());

	
	pageParam.params = params;
	pageParam.pagination = pagination1;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam1(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#hclx2").val(params.hclx);
		$("#ckh2").val(params.ckh);
		$("#dprtcode2").val(params.dprtcode);
		$("#inventorykeyword_search").val(params.keyword);
		
		if(pageParamJson.pagination){
			inventorygoPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			inventorygoPage(1,"auto","desc");//开始的加载默认的内容 
		}
	}catch(e){
		inventorygoPage(1,"auto","desc");
	}
}

	 //带条件搜索的翻页
	 function inventoryAjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam1();
		pagination1 = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination1;
		obj.keyword = $.trim($("#inventorykeyword_search").val());
		obj.ckh = searchParam.ckh;
		obj.hclx = searchParam.hclx;
		obj.dprtcode = searchParam.dprtcode;
		if(id1 != ""){
			searchParam.id = id1;
			id1 = "";
		}
		
		startWait();
		AjaxUtil.ajax({
			   url:basePath+"/aerialmaterial/warehouse/warehouseList",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(obj),
			   success:function(data){
				    finishWait();
				    if(data.rows !=""){
				    	inventoryappendContentHtml(data.rows);
				    	 new Pagination({
								renderTo : "inventorypagination",
								data: data,
								sortColumn : sortType,
								orderType : sequence,
								goPage:function(a,b,c){
									inventoryAjaxGetDatasWithSearch(a,b,c);
								}
							}); 
						// 标记关键字
						   signByKeyword($("#inventorykeyword_search").val(),[3,4,5,6,9,11]);
						   
					   } else {
						  $("#inventorylist").empty();
						  $("#inventorypagination").empty();
						  $("#inventorylist").append("<tr><td class='text-center' colspan=\"17\">暂无数据 No data.</td></tr>");
					   }
				    new Sticky({tableId:'kcck'});
		      }
		    }); 
	 }
	 
	//信息检索
	function searchinventory(){
		inventorygoPage(1,"auto","desc");
		ids.splice(0,ids.length);
		 $("#changerepertoryid").val("");
	}
	 
	 //将搜索条件封装 
	 function gatherSearchParam1(){
		 var searchParam = {};
		 searchParam.ckh = $("#ckh2").val();
		 searchParam.dprtcode = $("#dprtcode2").val();
		 searchParam.hclx = $("#hclx2").val();
		 return searchParam;
	 }
		var ids=[];
	 function inventoryappendContentHtml(list){
		   var htmlContent = '';
		   var chid=$("#changerepertoryid").val();
		   var vhids="";
		   if(chid!=""){
			    vhids=chid.split(",");
				 for (var i = 0; i < vhids.length; i++) {
					 ids.push(vhids[i]);
				}
		   }
			 
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
					htmlContent += "<tr bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent += "<tr bgcolor=\"#fefefe\">";
				}
			
				 var style1="";
				 var style2="display: none;";
				 if(vhids!=""){
					 if($.inArray(row.id, vhids) >= 0){
						 style1 = "display: none;";
						 style2 = "";
					 }
				 }
				 
			   htmlContent = htmlContent + "<td class='fixed-column'  align='center'><input type='hidden' value='1' name='"+ row.id + "'>" +
		    	"<i name='outstockBtn' style='"+style1+"' class='icon-signout color-blue cursor-pointer' onClick=\"select('"+ row.id + "','"+index+"',this)\" title='出库 OutStock'></i>" +
		    	"<i name='backBtn' style='"+style2+"' class='icon-share-alt color-blue cursor-pointer' onClick=\"backStock('"+ row.id + "','"+index+"',this)\" title='撤销  BackStock'></i>"+"</td>"; 
			   htmlContent += "<td  class='fixed-column' style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
			   htmlContent = htmlContent + "<td class='fixed-column text-left' style='vertical-align: middle; '>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td  style='vertical-align: middle; ' class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			   htmlContent = htmlContent + "<td   style='vertical-align: middle; ' class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' >"+StringUtil.escapeStr(gljb[row.gljb])+"</td>";  
				htmlContent += "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
				htmlContent += "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
				htmlContent += "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.grn)+"</td>"; 
				htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; '  align='right' >"+formatUndefine(row.kcsl)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.ckmc)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.kwh)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(row.hjsm).substring(0,10)+"</td>";  
			   htmlContent = htmlContent + "<td style='vertical-align: middle; ' class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";  
			   htmlContent = htmlContent + "</tr>"; 
		   });
		   $("#inventorylist").empty();
		   $("#inventorylist").html(htmlContent);
		   $("#changerepertoryid").val("");
	
		   refreshPermission();
	 }
	 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function inventorygoPage(pageNumber,sortType,sequence){
		inventoryAjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}	
 
	//搜索条件显示与隐藏 
	function search2() {
		if ($("#divSearch2").css("display") == "none") {
			$("#divSearch2").css("display", "block");
			$("#icon3").removeClass("icon-caret-down");
			$("#icon3").addClass("icon-caret-up");
		} else {
			$("#divSearch2").css("display", "none");
			$("#icon3").removeClass("icon-caret-up");
			$("#icon3").addClass("icon-caret-down");
		}
	}	
	
	
	/**
	  * 出库验证
	  * @returns
	  */
	 function select(id,index){
		 
		 
		 $("#kcck i[name='outstockBtn']:eq("+index+")").hide();
		 $(".sticky-col i[name='outstockBtn']:eq("+index+")").hide();
		 $("#kcck i[name='backBtn']:eq("+index+")").show();
		 $(".sticky-col i[name='backBtn']:eq("+index+")").show();
		 
		 var chid=$("#changerepertoryid").val();
		var vhids=chid.split(",");
		 var falo=true;
		 for (var i = 0; i < vhids.length; i++) {
			if(vhids[i]==id){
				falo=false;
			}
		}
		 if(falo==false){
			 AlertUtil.showErrorMessage("该选项已选择!"); 
			 return;
		 }
			 ids.push(id);
	 }	
	 
	/**
	  * 撤销
	  * @returns
	  */
	 function backStock(id,index){
		 
		 $("#kcck i[name='outstockBtn']:eq("+index+")").show();
		 $(".sticky-col i[name='outstockBtn']:eq("+index+")").show();
		 $("#kcck i[name='backBtn']:eq("+index+")").hide();
		 $(".sticky-col i[name='backBtn']:eq("+index+")").hide();
		 for (var i = 0; i < ids.length; i++) {
			 if(ids[i]==id){
				 ids.splice(i,1);
			}
		 }
	 }	
	 
	/**
	  * 手动领用出库
	  * @returns
	  */
	 function goManual(){
		 if($("#changerepertoryid").val()!=null&&$("#changerepertoryid").val()!=""){
			 ids.push($("#changerepertoryid").val());
		 }
		 
		 if(ids==""){
			 AlertUtil.showErrorMessage("请选择需要操作的项!"); 
		 }else{
			 window.location =basePath+"/aerialmaterial/outstock/warehousemain?id="+$.trim(ids)+"&pageParam1="+encodePageParam1();
		 }
			
	 }	
	 
	 /**
	  * 借出出库
	  * @returns
	  */
	 function checkout(){
		 if($("#changerepertoryid").val()!=null&&$("#changerepertoryid").val()!=""){
			 ids.push($("#changerepertoryid").val());
		 }
		 if(ids==""){
			 AlertUtil.showErrorMessage("请选择需要操作的项!"); 
		 }else{
			 window.location =basePath+"/aerialmaterial/outstock/checkout/"+$.trim(ids)+"?pageParam1="+encodePageParam1();
		 }
			
	 }
	 
	 /**
	  * 归还出库
	  * @returns
	  */
	 function givebackout(){
		 if($("#changerepertoryid").val()!=null&&$("#changerepertoryid").val()!=""){
			 ids.push($("#changerepertoryid").val());
		 }
		 if(ids==""){
			 AlertUtil.showErrorMessage("请选择需要操作的项!"); 
		 }else{
			 window.location =basePath+"/aerialmaterial/outstock/givebackout/"+$.trim(ids)+"?pageParam1="+encodePageParam1();
		 }
			
	 }
	 
	 /**
	  * 报废出库
	  * @returns
	  */
	 function dumping(){
		
		 if($("#changerepertoryid").val()!=null&&$("#changerepertoryid").val()!=""){
			 ids.push($("#changerepertoryid").val());
		 }
		 if(ids==""){
			 AlertUtil.showErrorMessage("请选择需要操作的项!"); 
		 }else{
			 window.location =basePath+"/aerialmaterial/outstock/dumping/"+$.trim(ids)+"?pageParam1="+encodePageParam1();
		 }
			
	 }
	 
	 /**
	  * 其它出库
	  * @returns
	  */
	 function otherbackout(){
		 if($("#changerepertoryid").val()!=null&&$("#changerepertoryid").val()!=""){
			 ids.push($("#changerepertoryid").val());
		 }
		 if(ids==""){
			 AlertUtil.showErrorMessage("请选择需要操作的项!"); 
		 }else{
			 window.location =basePath+"/aerialmaterial/outstock/otherbackout/"+$.trim(ids)+"?pageParam1="+encodePageParam1();
		 }
			
	 }	 
	 
		//搜索条件重置
		function searchresetinventory(){
			$("#divSearch2").find("input").each(function() {
			$(this).attr("value", "");
		});

		$("#divSearch2").find("textarea").each(function(){
			$(this).val("");
		});
		
		 $("#divSearch2").find("select").each(function(){
				$(this).val("");
			});
		 var zzjgid=$('#zzjgid').val();
		 $("#inventorykeyword_search").val("");
		 $("#dprtcode2").val(zzjgid);
		 $("#dprtcode2").trigger("change");
		}
		//字段排序
		function orderByinventory(obj, _obj) {
			ids.splice(0,ids.length);
			 $("#changerepertoryid").val("");
			$obj = $("#kcck th[name='column_"+obj+"']");
			var orderStyle = $obj.attr("class");
			$("#kcck .sorting_desc").removeClass("sorting_desc").addClass("sorting");
			$("#kcck .sorting_asc").removeClass("sorting_asc").addClass("sorting");
			
			var orderType = "asc";
			if (orderStyle.indexOf ( "sorting_asc")>=0) {
				$obj.removeClass("sorting").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$obj.removeClass("sorting").addClass("sorting_asc");
				orderType = "asc";
			}
			var currentPage = $("#inventorypagination li[class='active']").text();
			inventorygoPage(currentPage,obj, orderType);
		}
		
