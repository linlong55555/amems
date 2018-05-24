$(document).ready(function(){
	     Navigation(menuCode,'','','DICT','梅志亮','2016-08-01','李高升','2017-08-17');
		 goPage(1,"auto","desc");//开始的加载默认的内容 
		 //菜单设置  自己设置
		 
		 var zzjgid=$('#zzjgid').val();
		 $("#account_dprtcode").val(zzjgid);
		 
		 validation1();
		 validation2();
		 
//		 calcLineHeight();
		 
});
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
   AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
//信息检索
function changeOrganization(){
	goPage(1,"auto","desc");
}

 var lxid="";
 var bj="";
 var sc="";
 var dprtcode="";
 function select_type_dic(obj){
	$(obj).addClass("bg_tr");
	$("#list tr").not(obj).removeClass("bg_tr");
	 var lxmc=$(obj).find("input[name='lxmc']").val();
	 var dicdprtcode=$(obj).find("input[name='dprtcode']").val();
	 var diclxid=$(obj).find("input[name='lxid']").val();
	 var dicbj=$(obj).find("input[name='is_bj']").val();
	 var dicxz=$(obj).find("input[name='is_xz']").val();
	 var dicsc=$(obj).find("input[name='is_sc']").val();
	 $("#lxmc").html(lxmc);
	 if(dicxz==0){
		 $("#addbutton").hide();
	 }else{
		 $("#addbutton").show();
	 }
	 lxid=diclxid;
	 bj=dicbj;
	 sc=dicsc;
	 dprtcode=dicdprtcode;
	 var temp_dprtcode=$.trim($("#account_dprtcode").val());
	 if(temp_dprtcode=="-1"){
		 $("#sysbutton").hide();
	 }else{
		 $("#sysbutton").show(); 
	 }
	 searchRevision();
 }
 //带条件搜索的翻页
 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence,searchParam){
	 var searchParam = gatherSearchParam();
	 startWait();
	 AjaxUtil.ajax({
		   url:basePath+"/sys/dict/getNewDicListByDprtcode",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.rows!=null&&data.rows!=""){
				   appendContentHtml(data.rows);
			   } else {
				  $("#list").empty();
				  $("#list").append("<li class='list-group-item'>暂无数据</li>");
			   }
	      },
	    }); 
 }
 //将搜索条件封装 
 function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.dprtcode = $.trim($("#account_dprtcode").val());
	 return searchParam;
 }
 function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   htmlContent = htmlContent + "<tr onclick='select_type_dic(this)'>";
		  htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.lxmc)+"'>" +
		  		"<input type='hidden' name='lxmc' value='"+StringUtil.escapeStr(row.lxmc)+"'>" +
		  		"<input type='hidden' name='lxid' value='"+StringUtil.escapeStr(row.lxid)+"'>" +
  				"<input type='hidden' name='is_bj' value='"+StringUtil.escapeStr(row.isBj)+"'>" +
				"<input type='hidden' name='is_xz' value='"+StringUtil.escapeStr(row.isXz)+"'>" +
				"<input type='hidden' name='is_sc' value='"+StringUtil.escapeStr(row.isSc)+"'>" +
				"<input type='hidden' name='is_mc' value='"+StringUtil.escapeStr(row.isMc)+"'>" +
				"<input type='hidden' name='dprtcode' value='"+StringUtil.escapeStr(row.dprtcode)+"'>" +
				"<input type='hidden' name='is_sz' value='"+StringUtil.escapeStr(row.isSz)+"'>"+StringUtil.escapeStr(row.lxmc)+"</td><td  class='text-right' title='"+StringUtil.escapeStr(row.xlms)+"'>"+StringUtil.escapeStr(row.xlms)+"</td></tr>";  
	   });
	   $("#list").empty();
	   $("#list").html(htmlContent);
		new Sticky({
			tableId : 'listTbale'
		});
	   $("#list li:first-child").click();   //加载初始化
 }
 
 
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
 function goPage2(pageNumber,sortType,sequence){
    AjaxGetDatasWithSearch2(pageNumber,sortType,sequence);
 }	
 //带条件搜索的翻页
 function AjaxGetDatasWithSearch2(pageNumber,sortType,sequence,searchParam){
	 var searchParam = gatherSearchParam2();
	 searchParam.pagination = {page:1,sort:sortType,order:sequence,rows:100000};
	 startWait();
	 AjaxUtil.ajax({
		   url:basePath+"/sys/dict/getDictItemList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.rows!=null&&data.rows!=""){
				   appendContentHtml2(data.rows);
				   // 标记关键字
				   signByKeyword($("#dickeyword_search").val(),[2,3],"#dicList tr td");
			   } else {
				  $("#dicList").empty();
				  $("#dicList").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
			   }
	      },
	    }); 
 }
 //将搜索条件封装 
 function gatherSearchParam2(){
	 var searchParam = {};
	 searchParam.keyword = $.trim($("#dickeyword_search").val());
	 searchParam.lxid=lxid;
	 searchParam.dprtcode=$.trim($("#account_dprtcode").val());
	 return searchParam;
 }
 function appendContentHtml2(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr name='one_line'  bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr name='one_line'  bgcolor=\"#fefefe\">";
		   }
		   htmlContent = htmlContent + "<td class='text-center'><i name='edit' class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:dict:main:02' sz='"+StringUtil.escapeStr(row.sz)+"' mc='"+StringUtil.escapeStr(row.mc)+"' xc='"+StringUtil.escapeStr(row.xc)+"'"	+ 
		   "' tid='"+StringUtil.escapeStr(row.id)+"'"+
		   "' onClick=edit('"+ row.id + "',this) title='修改 Edit'></i>&nbsp;&nbsp;" +
			"<i  name='remove' class='icon-trash  color-blue cursor-pointer checkPermission' permissioncode='sys:dict:main:03' sz='"+StringUtil.escapeStr(row.sz)+"' onClick=del('"+ row.id +"',this) title='删除 Delete'></i></td>"; 
		  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.sz)+"'>"+StringUtil.escapeStr(row.sz)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.mc)+"'>"+StringUtil.escapeStr(row.mc)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'  title='"+StringUtil.escapeStr(row.xc)+"'>"+StringUtil.escapeStr(row.xc)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.displayname)+"'>"+StringUtil.escapeStr(row.displayname)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(row.whsj)+"'>"+formatUndefine(row.whsj)+"</td>";  
		   htmlContent = htmlContent + "</tr>"; 
	   });
	   $("#dicList").empty();
	   $("#dicList").html(htmlContent);
	   refreshPermission();       //触发按钮权限监控
	   $("#dicList").find("tr[name='one_line']").each(function(){           //循环遍历判断是否有操作权限
		   if(bj==0){
		        $("td [name='edit']").hide(); 
		   }
		   if(sc==0){
			   $("td [name='remove']").hide(); 
		   }
	   });
 }	
 //信息检索
 function searchRevision(){
 	goPage2(1,"XC","ASC");
 }

 function  validation1(){
		$("#xc").live("keyup", function(){
			backspace($(this),/^([1-9]|([1-9]\d+))$|^0$|^$/);
			 $('#form').data('bootstrapValidator')  
		     .updateStatus('xc', 'NOT_VALIDATED',null)  
		     .validateField('xc');  
		});
 	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	sz: {
                validators: {
                    notEmpty: {
                        message: '值不能为空'
                    },
                }
            },
            xc: {
            	validators: {
            		regexp: {
            			regexp: /^([1-9]|([1-9]\d+))$|^0$|^$/,
            			message: '项次必须是空值或者非负整数'
            		},
            	}
            },
        }
    });
 }
 function  validation2(){
	 $("#xc2").live("keyup", function(){
		 backspace($(this),/^([1-9]|([1-9]\d+))$|^0$|^$/);
		 $('#form2').data('bootstrapValidator')  
	     .updateStatus('xc2', 'NOT_VALIDATED',null)  
	     .validateField('xc2');  
	 });
	 validatorForm =  $('#form2').bootstrapValidator({
		 message: '数据不合法',
		 feedbackIcons: {
			 invalid: 'glyphicon glyphicon-remove',
			 validating: 'glyphicon glyphicon-refresh'
		 },
		 fields: {
			 sz2: {
				 validators: {
					 notEmpty: {
						 message: '值不能为空'
					 },
				 }
			 },
			 xc2: {
				 validators: {
					 regexp: {
						 regexp: /^([1-9]|([1-9]\d+))$|^0$|^$/,
						 message: '项次必须是空值或者非负整数'
					 },
				 }
			 },
		 }
	 });
 }
 
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
	
 function add(){
 	$('#ModalAddDicItem').on('shown.bs.modal', function() {
		AlertUtil.hideModalFooterMessage();
	});
 	$("#ModalAddDicItem").modal("show");
 	$("#sz").val("");
 	$("#mc").val("");
 	$("#xc").val("");	 	
 };
 

 function saveDicItem() {
    startWait($("#ModalAddDicItem"));	
    $('#form').data('bootstrapValidator').validate();
    if(!$('#form').data('bootstrapValidator').isValid()){
 	   finishWait($("#ModalAddDicItem"));
 	   return false;
    }
 	
 	var obj={};
    obj.sz=$('#sz').val();      
    obj.mc=$('#mc').val();  
    obj.xc=$('#xc').val();  
    obj.lxid=lxid;
    obj.dprtcode=$.trim($("#account_dprtcode").val());
	AjaxUtil.ajax({
		type : 'post',
		url : basePath+'/sys/dict/add',                                      
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType : 'json',
		success : function(massge) {
		    finishWait($("#ModalAddDicItem"));
			if (massge == "success") {
				AlertUtil.showMessage("增加成功!");
				$("#ModalAddDicItem").modal("hide");
				searchRevision();
			}else{
				AlertUtil.showModalFooterMessage(massge);
			}
		}
	});
 }
 //修改字典明细
 function edit(id,obj){
	 $('#ModalEditDicItem').on('shown.bs.modal', function() {
			AlertUtil.hideModalFooterMessage();
		});
 	 $("#ModalEditDicItem").modal("show");	 
     $("#sz2").val($(obj).attr("sz"));
     $('#sz2').attr("readonly","readonly");     
     $("#mc2").val($(obj).attr("mc"));
     $("#xc2").val($(obj).attr("xc"));
     $("#temp_sz").val($(obj).attr("sz"));
     $("#temp_mc").val($(obj).attr("mc"));
     $("#temp_xc").val($(obj).attr("xc"));
     $("#id").val(id);
     $("#tid").val($(obj).attr("tid"));
 }

function editDictItem(){
	if(
			$.trim($("#temp_sz").val())== $.trim($("#sz2").val())&&
			$.trim($("#temp_mc").val())== $.trim($("#mc2").val())&&
			$.trim($("#temp_xc").val())== $.trim($("#xc2").val())
			
	){
		AlertUtil.showMessage("修改成功!");
		$("#ModalEditDicItem").modal("hide");
		searchRevision();
	}else{		
		$('#form2').data('bootstrapValidator').validate();
		if(!$('#form2').data('bootstrapValidator').isValid()){
			finishWait($("#ModalEditDicItem"));
			return false;
		}
		var obj={};
		obj.sz=$('#sz2').val();      
		obj.mc=$('#mc2').val();  
		obj.xc=$('#xc2').val();  
		obj.id=$('#tid').val(); 
		obj.lxid=lxid;
		obj.zdrrealname=$("#temp_sz").val();
		obj.dprtcode=$.trim($("#account_dprtcode").val());
		AjaxUtil.ajax({
			type : 'post',
			url:basePath+"/sys/dict/Edit", 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			modal:$("#ModalEditDicItem"),
			data:JSON.stringify(obj),		
			success : function(massge) {
				finishWait($("#ModalEditDicItem"));
				if (massge == "success") {
					AlertUtil.showMessage("修改成功!");
					$("#ModalEditDicItem").modal("hide");
					searchRevision();
				}else{
					AlertUtil.showModalFooterMessage(massge);
				}
			}
		});
	}
}
//删除字典项明细
function del(id,obj){
	var dictItem={};
	dictItem.id=id;  
	dictItem.lxid=lxid;
	dictItem.sz=$(obj).attr("sz");
	dictItem.dprtcode=$.trim($("#account_dprtcode").val());
	 AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
		AjaxUtil.ajax({
			type : "post",
			url : basePath+"/sys/dict/Delete",
			contentType:"application/json;charset=utf-8",
			dataType : "json",
			data:JSON.stringify(dictItem),	
			success : function(massge) {
				finishWait();
			    if (massge == "success") {
					AlertUtil.showMessage("删除成功!");
					searchRevision();
				}
			}
		});
    }});
} 
//同步字典
function restoreDefault(){
    var dprtcode=$.trim($("#account_dprtcode").val());
	AjaxUtil.ajax({
		type : "post",
		url : basePath+"/sys/dict/Synch",
		dataType : "json",
		data:{"lxid":lxid,"dprtcode":dprtcode},
	    async: false,
	    cache:false,
		success : function(massge) {
			finishWait();
		    if (massge == "success") {
				AlertUtil.showMessage("恢复默认成功!");
				searchRevision();
			}
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
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc") >=0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "asc";
	} else {
		orderType = "desc";
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage2(currentPage,obj,orderType);
}

function calcLineHeight(){
	// 头高度
    var headerHeight = $('.header').outerHeight();
    // 头部菜单高度
    var headerDownHeight = $('.header-down').outerHeight();
    // 屏幕高度
    var windowHeight = $(window).height();
    // 底部高度
    var footerHeight = $('.footer').outerHeight()||0;
    // 面板标题高度
    var panelHeadingHeight = $('.panel-heading').outerHeight();
    // 版权高度
    var copyrightHeight = $("#copyright:visible").outerHeight()||0;
	var height = windowHeight - headerHeight - headerDownHeight - footerHeight - panelHeadingHeight - copyrightHeight;
	$("#seperateLine").height(height + 8);
	
	$("#list").height(height - 65);
}

//自定义页面高度
function customResizeHeight(bodyHeight, tableHeight){
	var divHeight = $("#list").parent().find("div").first().outerHeight()||0;
	var urlHeight = tableHeight - divHeight;
	$("#listTbale").parent().attr('style','height: '+urlHeight+'px; overflow: auto');
}

//回车事件控制
document.onkeydown = function(event) {
	e = event ? event : (window.event ? window.event : null);
	if (e.keyCode == 13) {
		$('#sjzdSearch').trigger('click');
	}
};
