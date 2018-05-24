var delAttachements = [];
var mrbs = [];
	mrbs[0]="初始";
	mrbs[1]="默认";
var mc='';
$(document).ready(
		function() {
			Navigation(menuCode);
			decodePageParam();
			validation();
			refreshPermission();
			// goPage(1, "desc", "auto");// 开始的加载默认的内容
			$(document).keydown(function(event) {
				if (event.keyCode == 13) {
					if ($("#keyword_search").is(":focus")) {
						searchRecord();
					}
				}
			});
			
		});
var pagination = {};
/**
 * 编码页面查询条件和分页
 * 
 * @returns
 */
function encodePageParam() {
	var pageParam = {};
	var params = {};
	params.keyword = $.trim($("#keyword_search").val());
	params.dprtcode = $.trim($("#dprtcode").val());
	pageParam.params = params;
	pageParam.pagination = pagination;
	return Base64.encode(JSON.stringify(pageParam));
}

/**
 * 解码页面查询条件和分页 并加载数据
 * 
 * @returns
 */
function decodePageParam() {
	try {
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#dprtcode").val(params.dprtcode);
		if (pageParamJson.pagination) {
			goPage(pageParamJson.pagination.page,
					pageParamJson.pagination.order,
					pageParamJson.pagination.sort);
		} else {
			goPage(1, "desc", "auto");
		}
	} catch (e) {
		goPage(1, "desc", "auto");
	}
}

function goPage(pageNumber, sortType, sortField) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sortField);
}

function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var searchParam = {};
	searchParam.keyword = $.trim($("#keyword_search").val());
	var dprtcode = $.trim($("#dprtcode").val());
	if (dprtcode != '') {
		searchParam.dprtcode = dprtcode;
	}
	pagination = {page:pageNumber,sort:sequence,order:sortType,rows:20};
	searchParam.pagination = pagination;
	if(id != ""){
		searchParam.id = id;
		id = "";
	}
	startWait();
	AjaxUtil.ajax({
		url : basePath + "/sys/workgroup/getListPage",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtml(data.rows);
				new Pagination({
					renderTo : "pagination",
					data : data,
					sortColumn : sortType,
					orderType : sequence,
					goPage : function(a, b, c) {
						AjaxGetDatasWithSearch(a, b, c);
					}
				});
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
				signByKeyword($.trim($("#keyword_search").val()),
						[ 2,3,4],"#list tr td")
			} else {
				$("#list").empty();
				$("#pagination").empty();
				$("#list").append(
						"<tr><td colspan=\"9\">暂无数据 No data.</td></tr>");
			}
			/*new Sticky({
				tableId : 'WorkGroup_check_list_table'
			});*/
		}
	});

}
function appendContentHtml(list) {
	var htmlContent = '';
	$
			.each(
					list,
					function(index, row) {
						htmlContent += [
								"<tr>",
										"<td class='text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:workgroup:main:02' onClick='update(\""
										+ row.id
										+ "\")' title='修改 Edit'></i></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='sys:workgroup:main:03'  onClick=\"Delete('"
										+ row.id
										+ "')\" title='作废  Invalid'></i></td>",
								"<td  class='text-left' title='"+ StringUtil.escapeStr(row.gzzdm) +"' ><a href='javascript:void(0);'onClick='look(\""
										+ row.id + "\")' >"
										+  StringUtil.escapeStr(row.gzzdm)
										+ "</td>",
								"<td class=' text-left' title='"+ StringUtil.escapeStr(row.gzzmc) +"'  >"
										+ StringUtil.escapeStr(row.gzzmc)
										+ "</td>",
								"<td class='text-left' title='"+ StringUtil.escapeStr(row.remark) +"'>"
										+ StringUtil.escapeStr(row.remark)+"</td>",
								"<td class='text-left' title='"+ StringUtil.escapeStr(mrbs[row.mrbs]) +"'>"
										+ StringUtil.escapeStr(mrbs[row.mrbs])+"</td>",
								"<td class='text-left' title='"+StringUtil.escapeStr(row.whr.displayName)+"'>" + StringUtil.escapeStr(row.whr.displayName) + "</td>",
								"<td class='text-center'>" + formatUndefine(row.whsj) + "</td>",
								"<td class='text-left'>"+ StringUtil.escapeStr(row.wh_department==null?"":row.wh_department.dprtname) + "</td>",
								"<td class='text-left'>"+StringUtil.escapeStr(row.name)+ "</td>", 
								"</tr>" ].join("");
					});
	$("#list").empty();
	$("#list").html(htmlContent);
}
function searchRecord() {
	goPage(1, "desc", "auto");
}

function more() {
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
/**
 * 新增按钮
 */
function add() {
	clearData();
	validation();
	$("#addModal span").show();
	$("#addModal input").attr("disabled", false);
	$("#addModal textarea").attr("disabled", false);
	$("#save").show();
	$("#addModal").modal("show");
}


/**
 * 保存信息(新增、修改)
 */
function save() {
	startWait($("#addModal"));
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  finishWait($("#addModal"));
		return false;
	  }	
	var param = {};
	param.gzzdm =$.trim($("#gzzdm").val());
	param.gzzmc =$.trim($("#gzzmc").val());
	param.remark = $.trim($("#remark").val());
	param.whbmid = $("#whbmid").val();
	param.whrid = $("#whrid").val();
	param.id = $("#id").val();
	param.mrbs = $("input:radio[name='mrbs']:checked").val();
	if($("#id").val()==""){
		param.dprtcode=$("#zzjg").val();
	}else{
		param.dprtcode = $("#dpt").val();	
	}
	AjaxUtil.ajax({
		url : basePath
				+ "/sys/workgroup/checkWorkGroup",
		type : "post",
		data : JSON.stringify(param),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		modal:$("#addModal"),
		success : function(data) {
			if((data==1&&mc==$.trim($("#gzzdm").val()))){
				AjaxUtil.ajax({
					url : basePath
							+ "/sys/workgroup/addWorkGroup",
					type : "post",
					data : JSON.stringify(param),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					modal:$("#addModal"),
					success : function(data) {
						$("#addModal").modal("hide");
						 	AlertUtil.showMessage('操作成功!','/sys/workgroup/main?id='+data+'&pageParam='+encodePageParam());
						 	clearData();
						 	finishWait();
						 	refreshPermission();
						 	mc='';
					}
				});
				
			}
			if(data==0){
				AjaxUtil.ajax({
					url : basePath
							+ "/sys/workgroup/addWorkGroup",
					type : "post",
					data : JSON.stringify(param),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					modal:$("#addModal"),
					success : function(data) {
						$("#addModal").modal("hide");
						 	AlertUtil.showMessage('操作成功!','/sys/workgroup/main?id='+data+'&pageParam='+encodePageParam());
						 	clearData();
						 	finishWait();
						 	refreshPermission();
						 	mc='';
					}
				});
			}
			else{
				if($("#id").val()==""){
					AlertUtil.showMessage('该工作组代码已存在!');	
				}
				finishWait($("#addModal"));
			}
		}
		
	});	
	
	} 

/**
 * 删除
 * @param id
 */
function Delete(id) {
	var param = {};
	param.id = id;
	param.whbmid = $("#whbmid").val();
 	param.whrid = $("#whrid").val();
	AlertUtil.showConfirmMessage("您确定要作废吗？",{callback: function(){
		AjaxUtil.ajax({
			url : basePath + "/sys/workgroup/deleteWorkGroup",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
			success : function(data) {
				AlertUtil.showMessage('操作成功!','/sys/workgroup/main?pageParam='+encodePageParam());
				refreshPermission();
			}
		});
	}});
}
/**
 * 修改
 * 
 * @param id
 */
function update(id) {
	clearData();
	$("#addModal span").show();
	$("#addModal input").attr("disabled", false);
	$("#addModal textarea").attr("disabled", false);
	$("#save").show();
	var param = {};
	param.id = id;
	AjaxUtil.ajax({
		url : basePath + "/sys/workgroup/edit",
		type : "post",
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		data : JSON.stringify(param),
		success : function(data) {
			$("#gzzdm").val(data.gzzdm);
			$("#gzzmc").val(data.gzzmc);
			$("#remark").val(data.remark);
			$("#id").val(data.id);
			$("#dpt").val(data.dprtcode);
			$("#addModal").modal("show");
			mc=data.gzzdm;
			
			
			$("input:radio[name='mrbs'][value='"+data.mrbs+"']").attr("checked",true); 
			
		
		}
	});
}


// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(
			function() { // 重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc")
						.addClass("sorting");
			});
	$("#" + obj + "_" + "order").removeClass("sorting");
	var orderType = "asc";
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
		orderType = "asc";
	} else {
		orderType = "desc";
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage, orderStyle.split("_")[1], obj);
}

	//隐藏Modal时验证销毁重构
	 $("#addModal").on("hidden.bs.modal", function() {
	 	 $("#form").data('bootstrapValidator').destroy();
	      $('#form').data('bootstrapValidator', null);
	      validation();
	 });
	 function validation(){
	 validatorForm = $("#form").bootstrapValidator({
			message : '数据不合法',
			feedbackIcons : {
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				gzzdm : {
					validators : {
						notEmpty : {
							message : '工作组代码不能为空'
						},
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	                        message: '不能输入中文'
	                    }
					}
				},
				gzzmc : {
					validators : {
						notEmpty : {
							message : '工作组名称不能为空'
						}
					}
				}
			}
		});
	 }
	 
	 $("#dprtcode").change(function(){
		 searchRecord();
	 });
	
	 //清空数据
	 function clearData(){
		 $("#gzzdm").val("");
		 $("#gzzmc").val("");
		 $("#remark").val("");
		 $("#id").val("");
		 $("#dpt").val("");
		 $("input:radio[name='mrbs'][value='0']").attr("checked",true); 
	 }
	 
	 function searchWorkGroup(){
		 goPage(1, "desc", "auto");
	 }
	/**
	 * 查看
	 * @param id
	 */ 
	 function look(id){
		 clearData();
			var param = {};
			param.id = id;
			AjaxUtil.ajax({
				url : basePath + "/sys/workgroup/edit",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(param),
				success : function(data) {
					$("#gzzdm").val(data.gzzdm);
					$("#gzzmc").val(data.gzzmc);
					$("#remark").val(data.remark);
					$("#id").val(data.id);
					$("#dpt").val(data.dprtcode);
					$("#addModal").modal("show");
					$("input:radio[name='mrbs'][value='"+data.mrbs+"']").attr("checked",true); 
				}
			});
			$("#addModal input").attr("disabled", true);
			$("#addModal span").hide();
			$("#addModal textarea").attr("disabled", true);
			$("#save").hide();
	 }

	function closeModal(){
		$("#addModal").modal("hide");
		mc='';
		clearData();
	}
	$('#addModal').on('hide.bs.modal', function (e) {
		mc='';
		clearData();
	});
	
	//回车事件控制
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			$('#workGroupMainSearch').trigger('click');
		}
	};