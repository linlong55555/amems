var pagination = {};
var alertModalAdd='AddalertModal';
$(function(){
	changeList();	 
	goPage(1,"auto","desc");
	//验证
	validation();
	//初始化日志功能
	logModal.init({code:'B_G2_01101'});
});
 
//验证
 function validation(){
  $('#form').bootstrapValidator({
 	        message: '数据不合法',
 	        feedbackIcons: {
 	            //valid: 'glyphicon glyphicon-ok',
 	            invalid: 'glyphicon glyphicon-remove',
 	            validating: 'glyphicon glyphicon-refresh'
 	        },
 	       fields: {
 	        	dlbh: {
 	                validators: {
 	                	notEmpty: {
 	                        message: '编号不能为空'
 	                    },
 	                    regexp: {
 	                        regexp: /^[\x00-\xff]*$/,
 	                        message: '编号不能输入中文及中文符号'
 	                    }
 	                }
 	            },
 	          	xc: {
 	          	  	validators: {
 	            		regexp: {
 	            			regexp: /^([1-9]|([1-9]\d+))$|^0$|^$/,
 	            			message: '项次必须是空值或者非负整数'
 	            		},
 	            	}
 	            }
 	        }
 	    });
 }
 
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
 function goPage(pageNumber,sortType,sequence){
 	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
 }
 
//查询条件
 function gatherSearchParam (){
	 var searchParam={};
	 searchParam.dprtcode=$("#dprtcode").val();
	 searchParam.jx=$("#fjjx").val();
	 searchParam.keyword=$("#keyword_search").val();
	 return searchParam;
 }
 
// 带条件搜索的翻页
 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	    var searchParam = gatherSearchParam();
			pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		    searchParam.pagination = pagination;
	 AjaxUtil.ajax({
		   url:basePath+"/project/maintenanceclass/queryAll",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
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
			// 标记关键字
			});
 			signByKeyword($("#keyword_search").val(),[3,5,6]);
 			}else{
	 			$("#maintenanceClass_list").empty();
				$("#pagination").empty();
				$("#maintenanceClass_list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
 			}
 		
 		}
     }); 	
 }
/* 表格拼接*/
 function appendContentHtml(list){
	 
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr>";
		    htmlContent += "<td class='text-center'>";
	    	htmlContent += "<i class='icon-edit color-blue cursor-pointer '  onClick=\"edit('"+row.id+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		    htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer ' onClick=\"invalid('"
		    				+ row.id + "')\" title='删除 Invalid '></i>&nbsp;&nbsp;";
		    htmlContent += "</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dlbh))+"'>"+StringUtil.escapeStr(formatUndefine(row.dlbh))+"</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xc))+"'>"+StringUtil.escapeStr(formatUndefine(row.xc))+"</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dlywms))+"'>"+StringUtil.escapeStr(formatUndefine(row.dlywms))+"</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dlzwms))+"'>"+StringUtil.escapeStr(formatUndefine(row.dlzwms))+"</td>";
		    htmlContent += "<td class='text-left' title='"+(row.whr?StringUtil.escapeStr(formatUndefine(row.whr.displayName)):'')+"'>"+(row.whr?StringUtil.escapeStr(formatUndefine(row.whr.displayName)):'')+"</td>"; 
		    /*htmlContent += "<td class='text-left' title='"+(row.dw_dprt?StringUtil.escapeStr(formatUndefine(row.dw_dprt.dprtname)):'')+"'>"+(row.dw_dprt?StringUtil.escapeStr(formatUndefine(row.dw_dprt.dprtname)):'')+"</td>"; */
		    htmlContent += "<td class='text-center' title='"+(row.whsj?StringUtil.escapeStr(formatUndefine(row.whsj)):'')+"'>"+(row.whsj?StringUtil.escapeStr(formatUndefine(row.whsj)):'')+"</td>"; 
		    htmlContent += "<td class='text-left' title='"+(row.jg_dprt?StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname)):'')+"'>"+(row.jg_dprt?StringUtil.escapeStr(formatUndefine(row.jg_dprt.dprtname)):'')+"</td>"; 
		    htmlContent += "</tr>" 	
	   $("#maintenanceClass_list").empty();
	   $("#maintenanceClass_list").html(htmlContent);
	   refreshPermission(); 
	 })
}
 
 //新增
 function add(){
	 //清空弹窗
	 modalEmpty();
	 var userDprtcode=$("#dprtId").val();
	 var dprtcode=$("#dprtcode").val();
	 var jx= $("#fjjx").val();
	 $("#jx").val(jx);
	 if(jx == null || jx == ''){
		 AlertUtil.showErrorMessage("没有机型时不能添加数据！"); 
		 return;
	 }
	 if(userDprtcode != dprtcode){
		 AlertUtil.showErrorMessage("只能新增本组织机构下的数据！"); 
	 }else{
		 $("#AddalertModal").modal("show");
		 $("#modalName").html("新增项目大类");
		 $("#modalEname").html("Add");
		 validation();
		
	 }
 }
 function edit(id){
	 AjaxUtil.ajax({
		 url:basePath+"/project/maintenanceclass/selectById",
		 type:"post",
		 async: false,
		 data:{
			 'id' : id
		 },
		 dataType:"json",
		 success:function(data){
			 finishWait();
			 setObj(data);
		 }
	 });
}
 //修改
 function setObj(data){
	 validation();
	 $("#AddalertModal").modal("show");
	 $("#modalName").html("修改项目大类");
	 $("#modalEname").html("Edit");
	 if(data.maintenanceclass == null){
		 AlertUtil.showErrorMessage("该数据不存在");
		 return false;
	 }
	 var obj= data.maintenanceclass;
	 //清空弹窗
	 modalEmpty();
	 $("#jx").val(obj.jx);
	 $("#dlbh").val(obj.dlbh);
	 $("#xc").val(obj.xc);
	 $("#oldDlbh").val(obj.dlbh);
	 $("#dlid").val(obj.id);
	 $("#dlzwms").val(obj.dlzwms);
	 $("#dlywms").val(obj.dlywms);
 }
 
 //清空
 function modalEmpty(){
	 //隐藏出现异常的感叹号
	 AlertUtil.hideModalFooterMessage();
	 $("#jx").val("");
	 $("#oldDlbh").val("");
	 $("#dlbh").val("");
	 $("#xc").val("");
	 $("#dlid").val("");
	 $("#dlzwms").val("");
	 $("#dlywms").val("");
 }
 //保存
 function saveUpdate(){
	//验证
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
		return false;
	  }
	 var obj={};
	 var jx=$("#fjjx").val();
	 var dlbh=$("#dlbh").val();
	 var xc=$("#xc").val();
	 var oldDlbh=$("#oldDlbh").val();
	 var dlid=$("#dlid").val();
	 var dlzwms=$("#dlzwms").val();
	 var dlywms=$("#dlywms").val();
	 
	 obj.jx=jx;
	 obj.dlbh=dlbh;
	 obj.xc=xc;
	 
	 obj.dlzwms=dlzwms;
	 obj.dlywms=dlywms;
	 if(dlid !=null && dlid !=""){
		 var url=basePath+"/project/maintenanceclass/edit";
		 obj.id=dlid;
		 var paramsMap={
				 dlbh:oldDlbh
		 }
		 var dprtcode=$("#dprtcode").val();
		 obj.dprtcode=dprtcode;
		 obj.paramsMap=paramsMap;
		 submit(obj,url);
	 }else{
		 var url=basePath+"/project/maintenanceclass/add";
		 submit(obj,url);
	 }
	 
 }
 //提交
 function submit(obj,url){
	 startWait();
	 AjaxUtil.ajax({
			url:url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				 AlertUtil.showErrorMessage("操作成功！");
				 $("#AddalertModal").modal("hide");
				 finishWait();
				 goPage(pagination.page,pagination.sort,pagination.order);
			}
		});
 }
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
 	var currentPage = $("#pagination li[class='active']").text();
 	goPage(currentPage,obj,orderStyle.split("_")[1]);
 }
 //信息检索
 function searchRevision(){
	TableUtil.resetTableSorting("quality_check_list_table");
 	goPage(1,"auto","desc");
 }
 //切换组织机构时机型查询
 function changeList(){
	 
	 var dprtcode = $.trim($("#dprtcode").val());
	 var planeRegOption = '';
	 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 if(planeDatas != null && planeDatas.length >0){
	  	$.each(planeDatas,function(i,planeData){
	  	    planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  	});
	 }
	  $("#fjjx").empty();
	  $("#fjjx").append(planeRegOption);
	  goPage(1,"auto","desc");
	 
/*	 var dprtcode=$("#dprtcode").val();
	 
	 AjaxUtil.ajax({
		 url:basePath + "/project/planemodeldata/findAllType",
		 type:"post",
		 async: false,
		 data:{
			 'dprtcode' : dprtcode
		 },
		 dataType:"json",
		 success:function(data){
			 var htmlContent='';
			 $.each(data,function(index,row){
				 htmlContent += "<option value="+StringUtil.escapeStr(formatUndefine(row))+">"+StringUtil.escapeStr(formatUndefine(row))+"</option>";
				 
			 });
			  $("#fjjx").empty();
			  $("#fjjx").html(htmlContent);
			  searchRevision();
		 }
	 });*/
	 
	 
 }
 
 function invalid (id){
	 AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		 startWait();
		 AjaxUtil.ajax({
			 url:basePath+"/project/maintenanceclass/delete",
			 type:"post",
			 async: false,
			 data:{
				 'id' : id
			 },
			 dataType:"json",
			 success:function(data){
				 	finishWait();
					 AlertUtil.showErrorMessage("删除成功");
					 /*goPage(pagination.page,pagination.sort,pagination.order);*/
					 goPage(1,"auto","desc");
			 }
		 });
	 }});
 }
 
//隐藏Modal时验证销毁重构
 $("#"+alertModalAdd).on("hidden.bs.modal", function() {
 	 $("#form").data('bootstrapValidator').destroy();
      $('#form').data('bootstrapValidator', null);
      validation();
 });
 
 function showImportModal(){
	 importModal.init({
		importUrl : "/project/maintenanceclass/excelImport?dprtcode="+$("#dprtcode").val(),
		downloadUrl : "/common/templetDownfile/13",
		callback : function(data) {
			goPage(1,"auto","desc");

		}
	});

	importModal.show();
 }