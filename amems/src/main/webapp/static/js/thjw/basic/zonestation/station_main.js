
//页面初始化
$(function(){

	//导航
	Navigation(menuCode, '', '', 'STATION', '李高升', '2017-08-19', '李高升', '2017-08-19');

	changeList();	 
	goPage(1,"auto","desc");
	// 验证
	validation();
	chineseReject();
	
	initFjjxOption();
	// 初始化日志功能
	logModal.init({code:'D_023_STATION'});
	//导入初始化
	initImport();

});
 
 
 
	/**
	 * 不满足正则则回退
	 * @param obj
	 * @param reg
	 */
function backspace(obj, reg){
		var value = obj.val();
		while(!(reg.test(value)) && value.length > 0){
			value = value.substr(0, value.length-1);
	    }
		obj.val(value);
}
// 验证,禁止输入中文
function chineseReject(){
	$("#zone2").live("keyup", function(){
		 backspace($(this),/^[\x00-\xff]*$/);
		 $('#form').data('bootstrapValidator')  
		 .updateStatus('zone2', 'NOT_VALIDATED',null)  
		 .validateField('zone2');  
	});
}

// 验证
function validation(){
	  $('#form').bootstrapValidator({
	 	        message: '数据不合法',
	 	        feedbackIcons: {
	 	            // valid: 'glyphicon glyphicon-ok',
	 	            invalid: 'glyphicon glyphicon-remove',
	 	            validating: 'glyphicon glyphicon-refresh'
	 	        },
	 	       fields: {
	 	        	zone2: {
	 	                validators: {
	 	                	notEmpty: {
	 	                        message: '飞机站位不能为空'
	 	                    },
	 	                    regexp: {
	 	                        regexp: /^[\x00-\xff]*$/,
	 	                        message: '飞机站位不能输入中文'
	 	                    }
	 	                }
	 	            }
	 	        }
	 	    });
	 }
 
// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
 function goPage(pageNumber,sortType,sequence){
 	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
 }
 
// 查询条件
 function gatherSearchParam (){
	 var searchParam={};
	 searchParam.dprtcode=$("#dprtcode").val();
	 searchParam.jx=$("#fjjx").val();
	 searchParam.keyword=$("#keyword_search").val().trim();
	 return searchParam;
 }
 
 // 切换组织机构时机型查询
 function changeList(){
	 
	 var dprtcode = $.trim($("#dprtcode").val());
	 var planeRegOption = '';
	 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 if( planeDatas!=null&&planeDatas.length >0){
	  	$.each(planeDatas,function(i,planeData){
	  	    planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  	});
	 }else{
		 planeRegOption +="<option value=''>暂无飞机机型No data</option>";
	 }
	  $("#fjjx").empty();
	  $("#fjjx").append(planeRegOption);
	  search();
	 
	 
 }
 
//隐藏Modal时验证销毁重构
 $("#AddEditAlertModal").on("hidden.bs.modal", function() {
 	 $("#form").data('bootstrapValidator').destroy();
      $('#form').data('bootstrapValidator', null);
      validation();
 });

 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){

	    var searchParam = gatherSearchParam();
			pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		    searchParam.pagination = pagination;
		    startWait();
		AjaxUtil.ajax({
		   url:basePath+"/basic/station/getZoneStationList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   modal:$("#AddEditAlertModal"),
		   success:function(data){
		   finishWait();
		   if(data.rows!=null&&data.rows!=""){
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
			    FormatUtil.removeNullOrUndefined();
	 			signByKeyword($("#keyword_search").val(),[3,4],"#zoneStation_list tr td");
		   } else {
			   $("#zoneStation_list").empty();
				$("#pagination").empty();
				$("#zoneStation_list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
		   }
 		
 		}
     }); 	
 }
/* 表格拼接 */
 function appendContentHtml(list){
	 
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr  bgcolor=\"#f9f9f9\" >";
			} else {
				htmlContent += "<tr  bgcolor=\"#fefefe\">";
			}
			 htmlContent = htmlContent + "<td class='text-center'><i name='edit' class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='basic:station:main:edit' jx='"+StringUtil.escapeStr(row.jx)+"' ms='"+StringUtil.escapeStr(row.ms)+"' zone='"+StringUtil.escapeStr(row.sz)+"'"	+ 
			   "  objId='"+StringUtil.escapeStr(row.id)+"'"+
			   "  dprtcode='"+StringUtil.escapeStr(row.dprtcode)+"'"+
			   "  dprtname='"+StringUtil.escapeStr(row.dprtname)+"'"+
			   "  onClick=edit(this) title='修改 Edit' permissioncode='basic:station:main:edit'></i>&nbsp;&nbsp;" +
				"<i  name='remove' class='icon-trash  color-blue cursor-pointer checkPermission' permissioncode='basic:station:main:del'  onClick=del('"+ row.id +"') title='删除Delete'></i></td>"; 
			
		    htmlContent += "</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.sz))+"'>"+StringUtil.escapeStr(formatUndefine(row.sz))+"</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ms))+"'>"+StringUtil.escapeStr(formatUndefine(row.ms))+"</td>";
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.displayname))+"'>"+StringUtil.escapeStr(formatUndefine(row.displayname))+"</td>";
		    /* htmlContent += "<td class='text-left' title='"+(row.dw_dprt?StringUtil.escapeStr(formatUndefine(row.dw_dprt.dprtname)):'')+"'>"+(row.dw_dprt?StringUtil.escapeStr(formatUndefine(row.dw_dprt.dprtname)):'')+"</td>"; */
		    htmlContent += "<td class='text-center' title='"+(row.whsj?StringUtil.escapeStr(formatUndefine(row.whsj)):'')+"'>"+(row.whsj?StringUtil.escapeStr(formatUndefine(row.whsj)):'')+"</td>"; 
		    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"'>"+StringUtil.escapeStr(formatUndefine(row.dprtname))+"</td>";

		    htmlContent += "</tr>" 	
	   $("#zoneStation_list").empty();
	   $("#zoneStation_list").html(htmlContent);
	   refreshPermission(); 
	 })
}
 
 // 新增
 function add(){
	  // 清空弹窗
	  modalEmpty();
	  if(document.getElementById("zone2").readOnly){
	    	 $("#zone2").removeAttr("readonly");  
	     }
	 $("#hideOrShow").show();
	 $("#modalName").html("新增飞机站位");
	 $("#modalEname").html("Add");
	 // 标记添加操作
	 $("#commitButton").attr("name","add");		
	 
	 var fjjx_choose =$("#fjjx").find("option:selected").val();
	 var dprtcode_choose =$("#dprtcode").find("option:selected").val();
	 if(dprtcode_choose=""||dprtcode_choose=="undefined"||dprtcode_choose==undefined||fjjx_choose==null||dprtcode_choose!=userJgdm){		 
		 AlertUtil.showMessage("只能新增本组织机构下的数据！");
		 return ;
	 }
	 if(fjjx_choose==""||fjjx_choose=="undefined"||fjjx_choose==undefined||fjjx_choose==null){
		 AlertUtil.showMessage("机型不能为空！");
		 return ;
	 }
	 
	 
	 //初始化弹出下拉框
	 $("#fjjx2").val(fjjx_choose);
	 
	 $("#zone2").val("");
	 $("#ms2").val("");

	 $('#form').data('bootstrapValidator').validate();
	 $("#AddEditAlertModal").modal("show");
 }
 //要修改的id
 var objId;
 // 修改
 function edit(obj){
	 // 清空弹窗
	 modalEmpty();
	 $("#hideOrShow").hide();
	 $("#modalName").html("修改飞机站位");
	 $("#modalEname").html("Edit");
	 // 标记修改操作
	 $("#commitButton").attr("name","edit");
	 var id = $(obj).attr('objid');
	startWait();
	var searchParam = {id:id};
	AjaxUtil.ajax({
		   url:basePath+"/basic/station/getZoneStationList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		   finishWait();
		   if(data.rows!=null&&data.rows!=""){
			   var e = data.rows[0];
			   $("#zone2").val(e.sz);
			   $("#fjjx2").val(e.jx);
			   $("#ms2").val(e.ms);
			   objId = e.id;
		   }
		
		}
	 }); 
	 
	 
	 

//	 $("#fjjx2").val($(obj).attr("jx"));
//	 $("#zone2").val($(obj).attr("zone"));
//	 $("#ms2").val($(obj).attr("ms"));
//	 objId=$(obj).attr("objId");
	 $("#zone2").attr("readonly","readonly");
	 $('#form').data('bootstrapValidator').validate();
	 $("#AddEditAlertModal").modal("show");
 }
 
 
 // 清空
 function modalEmpty(){
	 // 隐藏出现异常的感叹号
	 AlertUtil.hideModalFooterMessage();
 }
 
 // operation=add增加操作；operation=edit，修改成功！
 var operation;
 // 保存
 function saveUpdate(){
	// 验证
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showModalFooterMessage("请根据提示输入正确的信息");
		return false;
	  }
	 var obj={};
	 var operation=$("#commitButton").attr("name");
	 	 
	 obj.ms=$("#ms2").val();

	 if(operation=="add"){
		 var url=basePath+"/basic/station/add";
		 obj.dprtcode=$("#dprtcode").find("option:selected").val();
		 obj.jx=$("#fjjx2").val();
		 if (!String.prototype.trim){			 
/*			 ---------------------------------------
			  * 清除字符串两端空格，包含换行符、制表符
			  *---------------------------------------*/
			 String.prototype.trim = function () { 
			  return this.replace(/(^[\s\n\t]+|[\s\n\t]+$)/g, "");
			 }			  
			}

		 obj.sz=$("#zone2").val().trim();
		 submit(obj,url,operation);
	 }else if(operation=="edit"){
		 var url=basePath+"/basic/station/edit";
		 obj.id=objId;
		 submit(obj,url,operation);
	 }
	 
 }
 // 提交
 function submit(obj,url,operation){
	 startWait($("#AddEditAlertModal"));
	 AjaxUtil.ajax({
			url:url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			dataType:"json",
			modal:$("#AddEditAlertModal"),
			success:function(massge){
				finishWait($("#AddEditAlertModal"));
					$("#AddEditAlertModal").modal("hide");
					if(operation=="add"){
					AlertUtil.showMessage("增加成功！");
					}else if(operation=="edit"){
						AlertUtil.showMessage("修改成功！");
					}					
					goPage(1,"auto","desc");				 
			}
		});
 }
	// 字段排序
 function orderBy(obj) {
 	var orderStyle = $("#" + obj + "_order").attr("class");
 	$("th[id$=_order]").each(function() { // 重置class
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
 // 信息检索
 function search(){
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
 	goPage(1,"auto","desc");
 }

 /**
  * 逻辑删除
  */
 function del (id){
	 AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		 AjaxUtil.ajax({
			 url:basePath+"/basic/station/delete/"+id,
			 type:"post",
			 contentType:"application/json;charset=utf-8",
			 dataType:"json",
			 success:function(massge){
					 AlertUtil.showMessage("删除成功!");
					 goPage(1,"auto","desc");
			 }
		 });
	 }});
 }
/* 飞机机型初始化*/
	function initFjjxOption(){
		$("#fjjx").empty();
		var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : userJgdm});
		if(typeList.length > 0){
			for(var i = 0; i < typeList.length; i++){
				$("#fjjx").append("<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>");
			}
		}else{
			$("#fjjx").append("<option value=''>暂无飞机机型No data</option>");
		}
	}
	
	/* 飞机机型初始化方法*/	
	function initFjjxOptionFn(e){
		if(e == "" || e==null) {
			 e = userJgdm;
		}
		var typeList = acAndTypeUtil.getACTypeList({DPRTCODE : e});
		var html = "";
		if(typeList.length > 0){
			for(var i = 0; i < typeList.length; i++){
				html += "<option value='"+StringUtil.escapeStr(typeList[i].FJJX)+"'>"+StringUtil.escapeStr(typeList[i].FJJX)+"</option>";
			}
		}else{
			html += "<option value='-1'>暂无飞机机型No data</option>";
		}
		return html;
	}	
	
	/**
	 * 初始化导入
	 */
	function initImport(){
		// 初始化导入
	   importModal.init({
		    importUrl:"/basic/station/excelImport",
		    downloadUrl:"/common/templetDownfile/15",
			callback: function(data){
				goPage(1,"auto","desc");
				$("#ImportModal").modal("hide");
			}
		})
	}
	/**
	 * 显示导入模态框
	 */
	function showImportModal(){
		 importModal.show();
	}
	//导出
	function exportExcel(){
		var searchParam = gatherSearchParam();
			window.open(basePath+"/basic/station/station.xls?dprtcode="
					+ encodeURIComponent(searchParam.dprtcode)+ "&lx=3" + "&keyword="
					+ encodeURIComponent(searchParam.keyword) + "&jx=" + encodeURIComponent(searchParam.jx))
	}
	
	//回车事件控制
	document.onkeydown = function(event){
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				search(); //调用主列表页查询
			}
		}
	};