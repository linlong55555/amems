var fjjx = "${param.fjjx}";
var pageParam = '${param.pageParam}';
var pagination = {};

$(document).ready(function(){
	Navigation(menuCode, '', '', 'gc_006001', '林龙', '2017-08-03', '林龙', '2017-08-03');//导航
	AddalertModal.validation(); 	//初始化验证
	decodePageParam();				//解码页面查询条件和分页 并加载数据
	refreshPermission();			//权限初始化
	logModal.init({code:'D_004'});	//初始化日志功能
});

/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
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

/**
 * 编码页面查询条件和分页
 * @returns
 */
function encodePageParam(){
	var pageParam = {};
	var params = {};
	params.dprtcode = $.trim($("#dprtcode").val());     //组织机构
	params.keyword = $.trim($("#keyword_search").val());//关键字
	
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
 */
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	

/**
 * 分页查询，参数（当前页码,排序字段,排序规则）
 */
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	//var searchParam = gatherSearchParam(); //获取查询条件
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		obj.pagination = pagination;
		obj.keyword =  $.trim($("#keyword_search").val());//关键字
		obj.dprtcode = $("#dprtcode").val(); //组织机构
	/*	if(fjjx != ""){
			obj.fjjx = fjjx;
			fjjx = "";
		}*/
	startWait(); //开始加载
	AjaxUtil.ajax({
		   url:basePath+"/project2/actype/queryAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			    finishWait(); //结束加载
			    if(data.rows != ""){
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
					
			       signByKeyword($("#keyword_search").val(),[2,3]);// 标记关键字
				 } else {
					  $("#actypeList").empty();
					  $("#pagination").empty();
					  $("#actypeList").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
				 }
			    new Sticky({tableId:'fjjyd'});//配置浮动
	      }
	}); 
}

/**
 *将查询条件封装 
 */
function gatherSearchParam(){
	 var searchParam = {};
	
	 
	 return searchParam;
}

/**
 * 加载列表数据
 */
function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   htmlContent += "<tr   fjjx='jx"+row.fjjx+"' onclick=\"showHiddenContent(this)\">";
		   htmlContent += "<td  class='fixed-column text-center'>";
		 
		   
		   if(row.zt==0){
			   htmlContent += "<i class='icon-unlock-alt color-blue cursor-pointer checkPermission' permissioncode='project2:actype:main:03' " 
				   +  "fjjx='"+StringUtil.escapeStr(row.fjjx)+"' onClick=\"enabled(event)\" title='启用 Enabled'></i>" ;
		   }
		   
		   if(row.zt==1){
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:actype:main:02' " 
		   		   +  "fjjx='"+StringUtil.escapeStr(row.fjjx)+"' bz='"+StringUtil.escapeStr(row.bz)+"' onClick=\"AddalertModal.inItUpdate(event)\" title='修改 Edit'></i>&nbsp;&nbsp;" ;
			   
			   htmlContent += "<i class='icon-lock color-blue cursor-pointer checkPermission' permissioncode='project2:actype:main:04' " 
				   +  "fjjx='"+StringUtil.escapeStr(row.fjjx)+"' onClick=\"note(event)\" title='注销 Log Off'></i>" ;
		   }
		   
		   htmlContent += "</td>";
		   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"'>"+StringUtil.escapeStr(row.fjjx)+"</td>";  
		   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";  
		   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.whr.id)+"'>"+StringUtil.escapeStr(row.whr.id)+"</td>";  
		   htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.cjsj)+"'>"+StringUtil.escapeStr(row.cjsj)+"</td>";  
		   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dprt.id)+"'>"+StringUtil.escapeStr(row.dprt.id)+"</td>";
		   htmlContent += "</tr>";  
	   });
	   $("#actypeList").empty();
	   $("#actypeList").html(htmlContent);
	   refreshPermission(); //初始化按钮权限
}

/**
 * 启用
 */
function enabled(e) {
	var obj = {};
	var fjjx = $(e.target).attr("fjjx");
	var dprtcode=$("#dprtcode").val();
		obj.dprtcode = dprtcode;//组织机构
		obj.fjjx = fjjx;//飞机机型
		obj.zt = 1;//状态为0
		AlertUtil.showConfirmMessage("您确定要启用吗？",{callback: function(){
		startWait(); //开始加载
		AjaxUtil.ajax({
			url:basePath + "/project2/actype/enabled",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
			success:function(data){
				finishWait();//结束加载
				AlertUtil.showMessage('启用成功!');
				decodePageParam();
			}
		});
	}});
}

/**
 * 注销
 */
function note(e) {
	var obj = {};
	var fjjx = $(e.target).attr("fjjx");
	var dprtcode=$("#dprtcode").val();
		obj.dprtcode = dprtcode;//组织机构
		obj.fjjx = fjjx;//飞机机型
		obj.zt = 0;//状态为0
		AlertUtil.showConfirmMessage("您确定要注销吗？",{callback: function(){
		startWait(); //开始加载
		AjaxUtil.ajax({
			url:basePath + "/project2/actype/note",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
			success:function(data){
				finishWait();//结束加载
				AlertUtil.showMessage('注销成功!');
				decodePageParam();
			}
		});
	}});
}

/**
 * 点击行选
 */
function showHiddenContent(this_){
 	var fjjx = $(this_).attr("fjjx");
 	
 	$("#"+fjjx).addClass('bg_tr').siblings().removeClass('bg_tr');
}	

/**
 * 字段排序 参数（，）
 */
function orderBy(obj, _obj) {
	$obj = $("#fjjxid th[name='column_"+obj+"']");
	var orderStyle = $obj.attr("class");
	$("#fjjxid .sorting_desc").removeClass("sorting_desc").addClass("sorting");
	$("#fjjxid .sorting_asc").removeClass("sorting_asc").addClass("sorting");
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

/**
 * 搜索
 */
function search(){
	goPage(1,"auto","desc");
	TableUtil.resetTableSorting("fjjxid");
}
