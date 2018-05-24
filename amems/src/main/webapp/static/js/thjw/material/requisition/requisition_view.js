
var operateType = "view"; //页面操作类型，默认view
	//申请单状态
var	SQDZT = ["","保存","提交","","","","","","作废","指定结束","完成"];
//数值验证正则
var numberReg =  new RegExp("^[0-9]+(.[0-9]{2})?$");
	
var delIdArray = [];//删除的领用清单ID
	
$(document).ready(function(){
	Navigation(menuCode, "领用申请单", "Requisition Bill");
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				//调用主列表页查询
			}
		}
	});
	
	//初始化页面布局
	initController();
	changeOrganization();
	//加载数据
	loadData();
});


//改变组织机构时改变飞机注册号
function changeOrganization(){
	var dprtcode = $.trim($("#dprtcode").val());
	var planeRegOption = '<option value="00000" selected="selected" >通用Currency</option>';
	var planeData = acAndTypeUtil.getACRegList({DPRTCODE:formatUndefine(dprtcode),FJJX:null});
	if(planeData != null && planeData.length >0){
		$.each(planeData,function(i,planeData){
			if(dprtcode == planeData.DPRTCODE){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			}
		});
	}
	if(planeRegOption == ''){
		planeRegOption = '<option value="">暂无飞机 No plane</option>';
	}
	$("#fjzch").html(planeRegOption);
}
/**
 * 初始化页面权限控制
 */
function initController(){
	operateType = $("#operateType").val();
	if(operateType=="edit"){
		$("#pageTitle").html("领用申请单编辑");
		$("#btnGoAdd").show();
		$("#btnSave").show();
		 $("#fjzch").show();
		 $("#fjzchyc").hide();
		$("#btnSubmit").show();
		$("#detailTable tr th:eq(0)").show();
		$("#detailTable tr td:eq(0)").show();

		

		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		$(".downward", $("#detailTable")).removeClass("downward");
	}else {//operateType=="view"
		$("#pageTitle").html("领用申请单查看");
		 $("#fjzch").hide();
		 $("#fjzchyc").show();
		 $("#remark").hide();
		// $("#fjzchyc").val();
		$(":input").attr("readonly","readonly");
		$("#fjzch").attr("disabled","disabled");
		$("#selectUserBtn").hide();
		$("#sqrq").removeClass("date-picker");
		$(".view-input").show();
		$("#kcslTh").hide();
	}
}

/**
 * 加载数据
 */
function loadData(){
	startWait();
	var url = null;
	if(operateType == "edit"){
		url = basePath+"/aerialmaterial/requisition/view/saved";
	}else{
		url = basePath+"/aerialmaterial/requisition/view/id/"+$("#id").val();
	}
	AjaxUtil.ajax({
		url:url,
		type: "get",
		cache:false,
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		success:function(data){
			if(data){
				renderForm(data);
				if(data.detailList){
					appendContentHtml(data.detailList);
				}else{
					$detailTBody.empty();
					$detailTBody.append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
				}
			} else {
				var $detailTBody = $("#detailTBody");
				$detailTBody.empty();
				$detailTBody.append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
			}
			finishWait();
	    }
	}); 
}

/**
 * 填充FORM表单数据
 * @returns
 */
function renderForm(data){
	$("#id").val(data.id);
	$("#lysqdh").val(data.lysqdh);
	$("#sqrq").val(data.sqrq.substr(0,10));
	$("#fjzch").val(StringUtil.escapeStr(data.fjzch)==''?'00000':StringUtil.escapeStr(data.fjzch));
	$("#fjzchyc").val(StringUtil.escapeStr(data.fjzch) == '00000'?'通用Currency':StringUtil.escapeStr(data.fjzch)==''?'通用Currency':StringUtil.escapeStr(data.fjzch));
	if(operateType == "view"){
		$("#fjzch").html("<option>"+(StringUtil.escapeStr(data.fjzch) == '00000'?'通用Currency':StringUtil.escapeStr(data.fjzch))+"</option>");
	}
	$("#sqrid").val(data.sqrUser.id);
	$("#sqrmc").val(data.sqrUser.username +" "+ data.sqrUser.realname);
	$("#lyyy").val(data.lyyy);
	$("#zdr").val(data.zdrUser.username+" "+ data.zdrUser.realname);
	$("#zdsj").val(data.zdsj);
	$("#zt").val(SQDZT[data.zt]);
}

/**
 * 拼接表格内容
 * @param list
 * @returns
 */
var choses=[];
function appendContentHtml(list){
	choses=[];
	var htmlContent = '';
	$.each(list, function(index,row){
		choses.push(row.id);
		htmlContent += "<tr style=\"cursor:pointer;\"  >";
		
		if(operateType == "edit"){
			htmlContent += "<td class='text-center' style='vertical-align: middle;'><div>";
			htmlContent += "<i class='icon-trash color-blue cursor-pointer' onClick=\"del('"+ row.id + "','"+ index + "', event);\" title='删除 Delete'></i>&nbsp;&nbsp;";
			htmlContent += "</div></td>";
		}
		
		htmlContent += "<td style='vertical-align: middle;' align='center'>"+formatUndefine(index+1)+"</td>";  
		htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hCMainData.bjh)+"' align='left'>"+StringUtil.escapeStr(row.hCMainData.bjh)+"</td>";  
		htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hCMainData.ywms)+"' align='left'>"+StringUtil.escapeStr(row.hCMainData.ywms)+"</td>";  
		htmlContent += "<td class='tag-set ' style='vertical-align: middle; 'title='"+StringUtil.escapeStr(row.hCMainData.zwms)+"' align='left'>"+StringUtil.escapeStr(row.hCMainData.zwms)+"</td>";  
		htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.hCMainData.cjjh)+"' align='left'>"+StringUtil.escapeStr(row.hCMainData.cjjh)+"</td>"; 
		htmlContent += "<td style='vertical-align: middle; '>"+DicAndEnumUtil.getEnumName("materialTypeEnum",formatUndefine(row.hCMainData.hclx))+"</td>";  
		
		if(formatUndefine(row.stock.sn) == ""){
			htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.stock.pch)+"' align='left'>"+StringUtil.escapeStr(row.stock.pch)+"</td>"; 
		}else{
			htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.stock.sn)+"'  align='left'>"+StringUtil.escapeStr(row.stock.sn)+"</td>"; 
		}
		
		htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' title='"+StringUtil.escapeStr(row.stock.shzh)+"' align='left'>"+StringUtil.escapeStr(row.stock.shzh)+"</td>";  
		htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.stock.ckmc)+"</td>";  
		htmlContent += "<td class='tag-set ' style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.stock.kwh)+"</td>";  
		
		
		if(operateType == "edit"){
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(row.stock.kcsl)+"</td>"; 
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.hCMainData.jldw)+"</td>";  
		}
		
		if(operateType == "edit"){
			htmlContent += "<td style='vertical-align: middle;'><input type=\"text\" style='IME-MODE:disabled;' name='sqlysl' class='form-control' rowid='"+row.id+"' kcValue='"+formatUndefine(row.stock.kcsl)+"' oldValue='"+formatUndefine(row.sqlysl)+"' onkeyup='onkeyup4Num(this)' value=\""+formatUndefine(row.sqlysl)+"\"/></td>";  
			
			var workBtn = work_order_util.getWorkBtn();
			
			if(workBtn != ''){
				htmlContent += "<td style='vertical-align: middle; text-overflow: hidden ! important; display:block; white-space: nowrap; '>";
			}else{
				htmlContent += "<td style='vertical-align: middle; text-overflow: hidden ! important; display:block; white-space: nowrap; '>";
			}
			htmlContent += "<input style='display:none'  type=\"text\" name=\"glgd\"  data-role=\"tagsinput\" readonly='readonly'/>";
			htmlContent += workBtn;
			htmlContent += "<input type='hidden' name='workType' />";
			htmlContent += "</td>";  
			
		}else{
			htmlContent += "<td style='vertical-align: middle; ' align='left'>"+StringUtil.escapeStr(row.hCMainData.jldw)+"</td>";  
			htmlContent += "<td style='vertical-align: middle; ' align='right'>"+formatUndefine(row.sqlysl)+"</td>";  
			htmlContent += "<td style='vertical-align: middle;  text-overflow: hidden ! important;'>";
			if(row.workOrderList){
				 var list=row.workOrderList;
				   for(var i = 0; i <list.length; i++){					   
					    if (i == 1) {
					    	htmlContent = htmlContent
									+ "　<i class='icon-caret-down' id='"
									+ row.id
									+ "icon' onclick=switchThqk('"
									+ row.id + "')></i><div id='"
									+ row.id
									+ "' style='display:none'>";
						}
					    var gdlx = list[i].gdlx;
					    if(gdlx == 135){
					    	htmlContent +="<a href='"+basePath+"/produce/workorder/woView?gdid="+list[i].gdid+"' >"+StringUtil.escapeStr(list[i].gdbh)+"</a>";
					    }else{
					    	htmlContent +="<a href='"+basePath+"/produce/workorder145/woView?gdid="+list[i].gdid+"' >"+StringUtil.escapeStr(list[i].gdbh)+"</a>";
					    }
						if (i != 0) {
							htmlContent += "<br>";

						}
						if (i != 0 && i == list.length - 1) {
							htmlContent += "</div>";
						}
				   }
				
				
				
				
			}
			htmlContent += 	"</td>";  
		}
		htmlContent += "</tr>";  
	});
	var $detailTBody = $("#detailTBody");
	$detailTBody.empty();
	$detailTBody.html(htmlContent);
	work_order_util.renderGlgd(list);
}

/**
 * 工单
 */
var work_order_util = {
		id : 'work_order_util',
		oldFjzch : null,//飞机注册号
		currRow : {},//当前行
		/**
		 * 渲染相关工单
		 * @returns
		 */
		renderGlgd : function(list){
			$.each($("input[name='glgd']"),function(index){
				var elt = $(this);
				elt.tagsinput({
				  itemValue: 'gdid',
				  itemText: 'gdbh',
				  maxTags: 3
				});
				var workType = 135;
				if(list[index] && list[index].workOrderList){
					$.each(list[index].workOrderList, function(i, row){
						elt.tagsinput('add', row);
						workType = row.gdlx;
					})
				}
				elt.parent().find("input[name='workType']").val(workType);
			});
			$(".bootstrap-tagsinput input").attr("readonly", true);
			this.initWebuiPopover();
		},
		/**
		 * 清空Tagsinput
		 */
		clearWorkOrder : function($obj){
			$obj.tagsinput('removeAll');
		},
		getWorkBtn : function(){
			var btn = "";
			if(checkBtnPermissions('aerialmaterial:requisition:main:04') && !checkBtnPermissions('aerialmaterial:requisition:main:05')){
				btn += "<i class='icon-search color-blue cursor-pointer' onclick='"+this.id+".openWorkOrder(135,this);'/>";
			}else if(!checkBtnPermissions('aerialmaterial:requisition:main:04') && checkBtnPermissions('aerialmaterial:requisition:main:05')){
				btn += "<i class='icon-search color-blue cursor-pointer' onclick='"+this.id+".openWorkOrder(145,this);'/>";
			}else if(checkBtnPermissions('aerialmaterial:requisition:main:04') && checkBtnPermissions('aerialmaterial:requisition:main:05')){
				btn += "<i class='operation-work-btn icon-search color-blue cursor-pointer' />";
			}
			return btn;
		},
		openWorkOrder : function(type, obj){
			this.currRow = $(obj).parent();
			this.selectWorkOrder(type);
		},
		initWebuiPopover : function(){
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('operation-work-btn','body',function(obj){
				return this_.getWorkTyleBtns(obj);
			}, 50);
		},
		getWorkTyleBtns : function(obj){//获取工单类型按钮组
			var this_ = this;
			this_.currRow = $(obj).parent();
			var str = "<div class='button-group-new' style='text-align:center;vertical-align:middle;'>";
			str += "<p style='background:white;' class='margin-bottom-0'>";
			str += "<a href='javascript:void(0);' onclick='"+this_.id+".selectWorkOrder(135)' style='padding-left:0px;'>135</a>";
			str += "</p>";
			str += "<p style='background:white;' class='margin-bottom-0'>";
			str += "<a href='javascript:void(0);' onclick='"+this_.id+".selectWorkOrder(145)' style='padding-left:0px;'>145</a>";
			str += "</p>";
			str += "</div>";
			return str;
		},
		selectWorkOrder : function(type){
			var this_ = this;
			var fjzch = $("#fjzch").val();
			var workTypeObj = this_.currRow.find("input[name='workType']");
			var workOrderObj = this_.currRow.find("input[name='glgd']");
			var workType = workTypeObj.val();
			if(fjzch == "00000"){
				fjzch = null;
			}
			var isReload = false;
			if(this_.oldFjzch == null || this_.oldFjzch !== fjzch){
				isReload = true;
			}
			this_.oldFjzch = fjzch;
			open_win_work_order.show({
				dprtcode:userJgdm,
				gdzt:[2,7],
				fjzch: fjzch,
				userId : userId,
				userType : userType,
				selected : workOrderObj.val(),
				type : type,//工单类型
				callback: function(data){
					if(data != null && data.length > 0){
						if(data.length>1){
							$(".bootstrap-tagsinput").css("width","95%");
						}
						if(workType != type){
							this_.clearWorkOrder(workOrderObj);//清空
							workTypeObj.val(type);
						}
						$.each(data,function(index,row){
							row.gdlx = type;
							workOrderObj.tagsinput('add', row);
						});
					}
				}
			}, isReload);
		}
}

/**
 * 保存
 * @returns
 */
function save(){

	var data = {};
	//数据修改清单
	var modifyList = [];
	var isValid = true;
	
	var len = $("#detailTBody").find("tr").length;
	if (len > 0){
	$("input:text[name='sqlysl']").each(function(){
		
		if($.trim($(this).val()) == ""){
			AlertUtil.showMessage("请填写领用数");
			isValid = false;
		}
		if(!numberReg.test($.trim($(this).val())) || Number($.trim($(this).val())) <= 0){  
			AlertUtil.showMessage("请正确填写领用数");
			isValid = false; 
		} 
		if($(this).attr("kcValue") == ""){
			AlertUtil.showMessage("航材库存不足");
			isValid = false;
		}
		if(Number($(this).attr("kcValue")) < Number($.trim($(this).val()))){
			AlertUtil.showMessage("航材库存不足");
			isValid = false;
		}
		if($(this).attr("oldValue") != $(this).val()){
			modifyList.push({id: $(this).attr("rowid"), sqlysl: $(this).val(), workOrderList: $(this).parent().next().find("input[name='glgd']").tagsinput('items')});
		}else{
			modifyList.push({id: $(this).attr("rowid"), workOrderList: $(this).parent().next().find("input[name='glgd']").tagsinput('items')});
		}
	});
	
	}else{
		AlertUtil.showErrorMessage("请选择需要领用的领用清单");
		return false;
	}
	if(!isValid){
		return false;
	}
	
	data.id = $("#id").val();
	data.fjzch = $("#fjzch").val();
	data.sqrid = $("#sqrid").val();
	data.sqbmid = $("#sqbmid").val();
	data.sqrq = $("#sqrq").val();
	data.lyyy = $("#lyyy").val();
	if($.trim($("#lyyy").val())==''){
		AlertUtil.showMessage("领用原因不能为空!");
		return false;
	}
	data.modifyList = modifyList;
	//删除清单
	data.idList = delIdArray;
	
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/requisition/save",
		type: "post",
		contentType:'application/json;charset=utf-8',
		dataType:"json",
		data: JSON.stringify(data),
		success:function(data){
			delIdArray = [];
			AlertUtil.showMessage("保存成功");
			finishWait();
	    }
	}); 
}

/**
 * 提交
 * @returns
 */
function submit(){

	var data = {};
	//数据修改清单
	var modifyList = [];
	var isValid = true;
	
	var len = $("#detailTBody").find("tr").length;
	if (len > 0){
	
	$("input:text[name='sqlysl']").each(function(){
		if($.trim($(this).val()) == ""){
			AlertUtil.showMessage("请填写领用数");
			isValid = false;
		}
		if(!numberReg.test($.trim($(this).val())) || Number($.trim($(this).val())) <= 0){  
			AlertUtil.showMessage("请正确填写领用数");
			isValid = false; 
		} 
		if($(this).attr("kcValue") == ""){
			AlertUtil.showMessage("航材库存不足");
			isValid = false;
		}
		if(Number($(this).attr("kcValue")) < Number($.trim($(this).val()))){
			AlertUtil.showMessage("航材库存不足");
			isValid = false;
		}
		
		if($(this).attr("oldValue") != $(this).val()){
			modifyList.push({id: $(this).attr("rowid"), sqlysl: $(this).val(), workOrderList: $(this).parent().next().find("input[name='glgd']").tagsinput('items')});
		}else{
			modifyList.push({id: $(this).attr("rowid"), workOrderList: $(this).parent().next().find("input[name='glgd']").tagsinput('items')});
		}
	});
	}else{
		AlertUtil.showErrorMessage("请选择需要领用的领用清单");
		return false;
	}
	if(!isValid){
		return false;
	}
	
	
	data.id = $("#id").val();
	data.fjzch = $("#fjzch").val();
	data.sqrid = $("#sqrid").val();
	data.sqbmid = $("#sqbmid").val();
	data.sqrq = $("#sqrq").val();
	data.lyyy = $.trim($("#lyyy").val());
	
	data.modifyList = modifyList;
	
	if($.trim($("#lyyy").val())==''){
		AlertUtil.showMessage("领用原因不能为空!");
		return false;
	}
	
	//删除清单
	data.idList = delIdArray;
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/requisition/submit",
		type: "post",
		contentType:'application/json;charset=utf-8',
		dataType:"json",
		data: JSON.stringify(data),
		success:function(data){
			finishWait();
			AlertUtil.showMessage("提交成功", '/aerialmaterial/requisition/main?pageParam='+pageParam);
	    }
	}); 
}

function del(id, index, e){
	AlertUtil.showConfirmMessage("确定删除此行吗",{
		callback: function(){
			$(e.target).parent().parent().parent().remove();
			delIdArray.push(id);
			
			$("#detailTBody tr").each(function(index, row){
				$(this).find("td:eq(1)").text(index+1);
			});
		}
	})
}

/**
 * 选择用户
 * @returns
 */
function selectUser(){
	userModal.show({
		selected:$("#sqrid").val(),//原值，在弹窗中默认勾选
		callback: function(data){//回调函数
			$("#sqrid").val(formatUndefine(data.id));
			$("#sqrmc").val(formatUndefine(data.username)+" "+formatUndefine(data.realname));
			$("#sqbmid").val(formatUndefine(data.bmdm));
		}
	})
}

function pageback(){
	if(operateType == "view"){
		window.location.href =basePath+"/aerialmaterial/requisition/main?tabId=history&pageParam="+pageParam;
	}else{
		window.location.href =basePath+"/aerialmaterial/requisition/main?tabId=main&pageParam="+pageParam;
	}
}
		

//只能输入数字和小数,保留两位
function onkeyup4Num(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     var str = obj.value.split(".");
     if(str.length > 1){
    	 if(str[1].length > 2){
    		 obj.value = str[0] +"." + str[1].substring(0,2);
    	 }
     }
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		 if(obj.value.substring(1,2) != "."){
  			obj.value = 0;
  		 }
  	 }
     obj.value = validateMax(obj.value);
     function validateMax(_value){
    	 if(Number(_value) > 99999999.99){
    		return validateMax(_value.substr(0, _value.length-1));
    	 }
    	 return _value;
    }
}

function vieworhideZjqkContentAll(){
	for(var i=0;i<choses.length;i++){
		var flag = $("#"+choses[i]).is(":hidden");
		if(flag){				
			$("#"+choses[i]).fadeIn(500);
			$("#"+choses[i]+'icon').removeClass("icon-caret-down");
			$("#"+choses[i]+'icon').addClass("icon-caret-up");
		}else{
			$("#"+choses[i]).hide();
			$("#"+choses[i]+'icon').removeClass("icon-caret-up");
			$("#"+choses[i]+'icon').addClass("icon-caret-down");
		}
	}
}
function switchThqk(id) {
	if ($("#" + id).is(":hidden")) {
		$("#" + id).fadeIn(500);
		$("#" + id + 'icon').removeClass("icon-caret-down");
		$("#" + id + 'icon').addClass("icon-caret-up");
	} else {
		$("#" + id).hide();
		$("#" + id + 'icon').removeClass("icon-caret-up");
		$("#" + id + 'icon').addClass("icon-caret-down");
	}
}