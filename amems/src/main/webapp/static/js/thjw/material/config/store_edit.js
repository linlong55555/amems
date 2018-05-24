var xh = 1;// 序号
var oldStorageIdArr = [];// 已拥有的库位id数组
var flag = true;

$(function() {
	Navigation(menuCode, "修改仓库信息", "Edit");// 初始化导航
	$("#rotatable").append("<tr><td  colspan='4' class='text-center'>暂无数据 No data.</td></tr>");
	initStorage();

	loodingBase($("#jdid").val());
	// 初始化导入
	importModal.init({
		importUrl : "/material/store/excelImport?id=" + $("#id").val(),
		downloadUrl : "/common/templetDownfile/4",
		callback : function(data) {
		xh=1;//重置序号
		initStorage();
		}
	})

	validatorForm = $('#form').bootstrapValidator({
		message : '数据不合法',
		feedbackIcons : {
			// valid: 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {

			ckh : {
				validators : {
					notEmpty : {
						message : '仓库编号不能为空'
					},
					regexp : {
						regexp : /^[^\u4e00-\u9fa5]{0,50}$/,
						message : '不能输入中文且长度最多不超过50个字符'
					}
				}
			},
			ckmc : {
				validators : {
					notEmpty : {
						message : '仓库名称不能为空'
					},
					/*
					 * regexp: { regexp: /^([\u4E00-\u9FA5]+|[a-zA-Z]+)$/,
					 * message: '只能输入中文和英文' },
					 */
					stringLength : {
						max : 100,
						message : '长度最多不超过100个字符'
					}
				}
			},
			cklb : {
				validators : {
					notEmpty : {
						message : '仓库类别不能为空'
					}
				}
			}/*,
			ckdz : {
				validators : {
					stringLength : {
						max : 100,
						message : '长度最多不超过100个字符'
					}
				}
			}*/
		}
	});

});

function loodingBase(jd){
	var dprtcode = userJgdm;
	AjaxUtil.ajax({
		   url:basePath+"/sys/department/queryDepartmentByJd", 
		   type: "post",
		   dataType:"json",
		   data:{
			   'id':dprtcode
		   },
		   success:function(data){
			   if(data != null && data.length > 0){
				   var htmlappend = '<option value="">请选择</option>';
				   $.each(data,function(index,row){
					   if(row.id == jd){
						   htmlappend += "<option selected value='"+row.id+"'>"+row.dprtname+"</option>";
					   }else{
						   htmlappend += "<option value='"+row.id+"'>"+row.dprtname+"</option>";
					   }
				   });
				   
				   $("#jd").html(htmlappend);
			   }
		   }
	 });
}

function initStorage() {
	AjaxUtil.ajax({
		async : false,
		url : basePath + "/material/store/queryStorageListByStoreId",
		type : "post",
		data : {
			storeId : $("#id").val()
		},
		dataType : "json",
		success : function(data) {
			initTableCol(data);
		}
	});
}

// 初始化仓库库位
function initTableCol(data) {

	// 先移除暂无数据一行
	$("#rotatable").empty();

	if (JSON.stringify(data) == '[]') {
		$("#rotatable").append("<tr><td  colspan='4' class='text-center'>暂无数据 No data.</td></tr>");
		return;
	}
	$.each(data, function(i, obj) {
		obj.xh = xh++;
		oldStorageIdArr.push(obj.id);
		addRow(obj);
	});
}

function save() {

	$('#form').data('bootstrapValidator').validate();
	if (!$('#form').data('bootstrapValidator').isValid()) {
		AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}

	var id = $.trim($("#id").val());
	var ckh = $.trim($("#ckh").val());
	var ckmc = $.trim($("#ckmc").val());
	var cklb = $.trim($("#cklb").val());
	var kgyid = $.trim($("#kgyid").val());
	var ckdz = $.trim($("#ckdz").val());
	var jd = $.trim($("#jd").val());
	var bz = $.trim($("#bz").val());

	var store = {
		id : id,
		ckh : ckh,
		ckmc : ckmc,
		cklb : cklb,
		kgyid : kgyid,
		ckdz : ckdz,
		jd : jd,
		bz : bz,
		dprtcode : $.trim($("#dprtcode").val())
	};
	store.storageIdList = oldStorageIdArr;
	store.storageList = getStorageParam();

	if (!flag) {
		return false;
	}

	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url : basePath + "/material/store/edit",
		contentType : "application/json;charset=utf-8",
		type : "post",
		data : JSON.stringify(store),
		dataType : "json",
		success : function(data) {
			finishWait();
			AlertUtil.showMessage('保存成功!', '/material/store/main?id='+id+'&pageParam='+pageParam);
		}
	});
}

// 获取库位参数
function getStorageParam() {
	flag = true;
	var storageParam = [];
	var message = '';

	var len = $("#rotatable").find("tr").length;
	if (len == 1) {
		if ($("#rotatable").find("td").length == 1) {
			return storageParam;
		}
	}

	if (len > 0) {
		var kwhArr = [];
		$("#rotatable").find("tr").each(function() {
			var infos = {};
			var index = $(this).index(); // 当前行
			var hiddenid = $("input[name='hiddenid']").eq(index).val();
			var kwh = $("input[name='kwh']").eq(index).val();
			var bz = $("input[name='bz']").eq(index).val();
			if (null == kwh || "" == kwh) {
				flag = false;
				message = '请输入库位号!';
			}
			var reg = /^[^\u4e00-\u9fa5]{0,50}$/;
			if (flag && !reg.test(kwh)) {
				flag = false;
				message = '对不起，库位号不能输入中文且长度最多不超过50个字符!';
			}
			if (flag && kwhArr.indexOf(kwh) != -1) {
				flag = false;
				message = '对不起，库位号不能重复!';
			}
			if(!flag){
				AlertUtil.showMessageCallBack({
					message : message,
					callback : function(){
						$("input[name='kwh']").eq(index).focus();
					}
				});
				return false;
			}
			kwhArr.push(kwh);
			infos.id = hiddenid;
			infos.kwh = kwh;
			infos.bz = bz;
			storageParam.push(infos);
		});
	}

	return storageParam;
}

// 打开调整列表对话框
function openUserList() {
	var dprtcode = $("#dprtcode").val();
	userModal.show({
		selected : $("#kgyid").val(),// 原值，在弹窗中默认勾选
		dprtcode:dprtcode,//机构代码
		callback : function(data) {// 回调函数
			if (data != null) {
				var str = StringUtil.escapeStr(data.username) + " " + StringUtil.escapeStr(data.realname);
				$("#kgyid").val(formatUndefine(data.id));
				$("#kgyn").val(str);
			}
		}
	})
}

// 向table新增一行
function addRotatableCol() {

	// 先移除暂无数据一行
	var len = $("#rotatable").length;
	if (len == 1) {
		if ($("#rotatable").find("td").length == 1) {
			$("#rotatable").empty();
		}
	}

	var obj = {};
	obj.id = "";
	obj.xh = xh++;
	obj.kwh = "";
	obj.bz = "";

	addRow(obj);
}

// 向table新增一行
function addRow(obj) {

	var tr = "";
	tr += "<tr>";

	tr += "<td style='text-align:center;vertical-align:middle;'>";
	tr += "<button class='line6' onclick='removeCol(this)'>";
	tr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
	tr += "</button>";
	tr += "		<input type='hidden' class='form-control' name='hiddenid' value='"
			+ obj.id + "'/>";
	tr += "</td>";

	tr += "<td style='text-align:center;vertical-align:middle;'>";
	tr += "	<span name='xh'>" + obj.xh + "</span>";
	tr += "</td>";

	tr += "<td>";
	tr += "		<input name='kwh' type='text' title='不能输入中文且长度最多不超过50个字符' value='"
			+ StringUtil.escapeStr(obj.kwh)
			+ "' class='form-control' maxlength='50' />";
	tr += "</td>";

	tr += "<td>";
	tr += "		<input name='bz' type='text' title='长度最多不超过300个字符' value='"
			+ StringUtil.escapeStr(obj.bz)
			+ "' class='form-control' maxlength='300' />";
	tr += "</td>";

	tr += "</tr>";

	$("#rotatable").append(tr);
}

// 移除一行
function removeCol(obj) {
	var id = $(obj).next().val();
	if ('' != id && null != id) {
		
		AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
			
			$(obj).parent().parent().remove();
			resXh();
			xh--;
			var len = $("#rotatable").find("tr").length;
			if (len < 1) {
				$("#rotatable").append("<tr><td  colspan='4'  class='text-center'>暂无数据 No data.</td></tr>");
			}
			
		}});
		
	} else {
		$(obj).parent().parent().remove();
		resXh();
		xh--;
		var len = $("#rotatable").find("tr").length;
		if (len < 1) {
			$("#rotatable").append("<tr><td  colspan='4'  class='text-center'>暂无数据 No data.</td></tr>");
		}
	}
}

function resXh() {

	var len = $("#rotatable").find("tr").length;
	if (len == 1) {
		if ($("#rotatable").find("td").length == 1) {
			return false;
		}
	}
	var xh = 1;
	if (len > 0) {
		$("#rotatable").find("tr").each(function() {
			var index = $(this).index(); // 当前行
			$("span[name='xh']").eq(index).html(xh++);
		});
	}
}
//返回前页面
function back(){
	 window.location.href =basePath+"/material/store/main?pageParam="+pageParam;
}

/**
 * 显示导入模态框
 */
function showImportModal() {
	importModal.show();
}
