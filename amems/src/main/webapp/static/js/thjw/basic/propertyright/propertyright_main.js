var alertModalAdd='AddalertModal';
$(function(){
	$('#cqbm').on('change', function(e) {
		$('#form').data('bootstrapValidator')  
		.updateStatus('cqbm', 'NOT_VALIDATED',null)  
		.validateField('cqbm');   
	});
	Navigation(menuCode, '', '', 'SC-1-1', '孙霁', '2017-09-25', '孙霁', '2018-02-5');
	var dprtcode=$("#dprtcode_search").val();
	//加载飞机注册号
	queryfjzch(dprtcode);
	propertyrightMain.reload();
	//初始化日志功能
	logModal.init({code:'PropertyrightD_026'});
	//验证
	validation();
});
//验证
function validation(){
 $('#form').bootstrapValidator({
	        message: '数据不合法',
	        excluded:[":disabled"],  
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	cqbm: {
	                validators: {
	                	notEmpty: {
	                        message: '产权编码不能为空'
	                    }
	                }
	            },
	           fjzch: {
	                validators: {
	                    notEmpty: {
	                        message: '飞机注册号不能为空'
	                    }
	                }
	            }
	        }
	    });
}

//搜索条件重置
function searchreset(){
	$("#fjzch_search").val("");
	$("#gs_search").val("");
	 $("#keyword_search").val("");
	 $("#dprtcode_search").val(userJgdm);
	 queryfjzch(userJgdm);
};
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
function orderBy(obj){
	// 字段排序
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
		orderType = "asc";
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	propertyrightMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}
//切换组织机构
function dprtChange(){
	var dprtcode=$("#dprtcode_search").val();
	queryfjzch(dprtcode);
}
var propertyrightMain={
		pagination : {},
		gatherSearchParam:function(){
			var searchParam={};
			 if (id != "") {
				searchParam.id = id;
				id = "";
			 }
			 searchParam.fjzch=$("#fjzch_search").val();
			 searchParam.dprtcode=$("#dprtcode_search").val();
			 var paramsMap={};
			 paramsMap.keyword=$("#keyword_search").val();
			 paramsMap.gs=$("#gs_search").val();
			 searchParam.paramsMap=paramsMap;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 _this.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = _this.pagination;
			 startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/basic/propertyright/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					   finishWait();
			 			if(data.rows.length > 0){
						 
			 			_this.appendContentHtml(data.rows);
			 			//分页
			 			var page_ = new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[2]);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'quality_check_list_table'});
			 		}
			 });
		},
		reload:function(){
			TableUtil.resetTableSorting("quality_check_list_table");
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
					htmlContent += "<tr >";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='fixed-column '>";
		    	htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' " +
		    				   "permissioncode='aircraftinfo:main:02' onClick=\"edit('"+row.id+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
		    	htmlContent += "<i fjjx='"+StringUtil.escapeStr(row.id)+"'class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='aircraftinfo:main:03' " +
		    				   "onClick=\"invalid('"+row.id+"')\" title='删除 Delete'></i>&nbsp;&nbsp;";
			    htmlContent += "</td>";
			    /*htmlContent += "<td style='text-align:left;vertical-align:middle;'  title='"+StringUtil.escapeStr(row.cqbh)+"'><a href=\"javascript:view('"+row.id+"')\">"+StringUtil.escapeStr(row.cqbh)+"</a></td>";*/
			    htmlContent += "<td class='fixed-column ' style='text-align:left;vertical-align:middle;'  title='"+StringUtil.escapeStr(row.cqbh)+"'>"+StringUtil.escapeStr(row.cqbh)+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;'  title='"+StringUtil.escapeStr(row.fjzch)+"'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;'  title='"+StringUtil.escapeStr(row.gsmc)+"'>"+StringUtil.escapeStr(row.gsmc)+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;'  title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.whr?row.whr.displayName:'')+"'>"+StringUtil.escapeStr(row.whr?row.whr.displayName:'')+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.bm_dprt?row.bm_dprt.dprtname:'')+"'>"+StringUtil.escapeStr(row.bm_dprt?row.bm_dprt.dprtname:'')+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.jg_dprt?row.jg_dprt.dprtname:'')+"'>"+StringUtil.escapeStr(row.jg_dprt?row.jg_dprt.dprtname:'')+"</td>"; 
			    htmlContent += "</tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission(); 
		 });
		}
};

function saveUpdate(b){
	//验证
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
		return false;
	}
	var obj={};
	var propertyrightId=$("#propertyrightId").val();
	var cqbm=$("#cqbm").val();
	var fjzch=$("#fjzch").val();
	var gsid=$("#gsId").val();
	var bz=$("#bz").val();
	var gsmc=$("#gsText").val();
	var propertyrightDprtcode=$("#propertyrightDprtcode").val();
	if(fjzch == "-"){
		fjzch ="";
	}
	obj.fjzch = fjzch;
	obj.gsid = gsid;
	obj.bz = bz;
	obj.cqbh = cqbm;
	obj.gsmc = gsmc;
	obj.dprtcode = propertyrightDprtcode;
	startWait();
	if(propertyrightId !="" && propertyrightId != null){
		var url=basePath+"/basic/propertyright/edit";
		obj.id=propertyrightId;
	}else{
		var url=basePath+"/basic/propertyright/add";
	}
	AjaxUtil.ajax({
		url:url,
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			 AlertUtil.showErrorMessage("操作成功！");
			 if(b){
				 $("#AddalertModal").modal("hide");
			 }else{
				 $("#AddalertModal").modal("hide");
				 $("#AddalertModal").modal("show");
			 }
			 id = data;
			 propertyrightMain.goPage(propertyrightMain.pagination.page, propertyrightMain.pagination.sort, propertyrightMain.pagination.order);
			 finishWait();
			 modalEmpty();
		}
	});
}
function add(){
	//$("#AddalertModal").modal("show");
	ModalUtil.showModal(alertModalAdd);
	$("#modalName").html("新增");
	$("#modalEname").html("Add");
	
	validation();
	//清空弹窗
	modalEmpty();
	//加载飞机注册号
	var dprtcode=$("#dprtcode_search").val();
	queryfjzch(dprtcode);
}

function queryfjzch(dprtcode){
	AjaxUtil.ajax({
		 url:basePath+"/aircraftinfo/getByDprtcode",
		   type: "post",
		   dataType:"json",
		   data:{
			   dprtcode:dprtcode
		   },
		   success:function(data){
			   var fjzch_search = "<option value=''>显示全部 All</option>";
			   var str = "<option value=''>请选择</option>";
			   $.each(data,function(index,row){
				   str += "<option value="+row.fjzch+">"+row.fjzch+"</option>"; 
				   fjzch_search += "<option value="+row.fjzch+">"+row.fjzch+"</option>"; 
			   });
			   str += "<option value='-'>N/A</option>"; 
			   fjzch_search += "<option value='-'>N/A</option>"; 
			   $("#fjzch").empty();
			   $("#fjzch").append(str);
			   $("#fjzch_search").empty();
			   $("#fjzch_search").append(fjzch_search);
	 	   }
	 });
}

function edit(id){
	 AjaxUtil.ajax({
		 url:basePath+"/basic/propertyright/selectById",
		 type:"post",
		 async: false,
		 data:{
			 'id' : id
		 },
		 dataType:"json",
		 success:function(data){
			 finishWait();
			 setObj(data);
			 ModalUtil.showModal(alertModalAdd);
			$("#modalName").html("修改");
			$("#modalEname").html("Edit");
		 }
	 });
}
function setObj(data){
	//隐藏出现异常的感叹号
	AlertUtil.hideModalFooterMessage();
	var obj = data.propertyright;
	$("#cqbm").val(obj.cqbh);
	$("#propertyrightId").val(obj.id);
	$("#whrText").val(obj.whr?obj.whr.displayName:'');
	if(!obj.fjzch){
		$("#fjzch").val("-");
	}else{
		$("#fjzch").val(obj.fjzch);
	}
	$("#gsText").val(obj.gsmc);
	$("#gsId").val(obj.gsid);
	$("#gsbm").val(obj.customer?obj.customer.khbm:'');
	$("#bz").val(obj.bz);
	$("#propertyrightDprtcode").val(obj.dprtcode);
}
function modalEmpty(){
	//隐藏出现异常的感叹号
	AlertUtil.hideModalFooterMessage();
	$("#cqbm").val("");
	$("#propertyrightId").val("");
	$("#whrText").val(currentUser.displayName);
	$("#fjzch").val("");
	$("#gsText").val("");
	$("#gsId").val("");
	$("#gsbm").val("");
	$("#bz").val("");
	$("#propertyrightDprtcode").val("");
}

function openCustomer(){
	customer_modal.init({
		dprtcode:$("#dprtcode_search").val(),
		selected:$("#gsId").val(),
		callback:function(data){
			$("#gsId").val(StringUtil.escapeStr(data.id));
			$("#gsbm").val(StringUtil.escapeStr(data.khbm));
			$("#gsText").val(StringUtil.escapeStr(data.khbm)+" "+StringUtil.escapeStr(data.khmc));
			fjzchChange();
		}
	});
}
//隐藏Modal时验证销毁重构
$("#"+alertModalAdd).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     validation();
});
//作废
function invalid(id){
	AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
		startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/basic/propertyright/invalid",
				 type:"post",
				 async: false,
				 data:{
					 'id' : id
				 },
				 dataType:"json",
				 success:function(data){
					 finishWait();
					 AlertUtil.showErrorMessage("删除成功！");
					 propertyrightMain.goPage(1,"auto","desc");
				 }
			 });
			 
		 
	}});
}

//飞机注册号改变事件
function fjzchChange (){
	var fjzch = $("#fjzch").val();
	var gsbm = $("#gsbm").val();
	if(fjzch != "" && fjzch !="-"){
		$("#cqbm").val(fjzch);
		$('#cqbm').change();
		if(gsbm != ""){
			$("#cqbm").val(fjzch+"-"+gsbm);
			$('#cqbm').change();
		}
	}else{
		if(gsbm != ""){
			$("#cqbm").val(gsbm);
			$('#cqbm').change();
		}else{
			$("#cqbm").val("");
			$('#cqbm').change();
		}
	}
	
	
	
}
