var dg1;
var dg2;
$(function() {
	DicUtil.registerDics(            /* 注册字典*/
			[
			 {dicId:13,domId:'zt'}
			 ]
	);
	
	
	$("#dg1").width(($(window).width() - 30));
	dg1 = datagrid1('dg1');
	$('#btnQuery1').click(function() { // 查询在做刷新
		reload1();
	});
	
	/**   权限控制 begin   */
	//PriUtil.registerPris();
	//PriUtil.registerPris([{id:'btnAdd',code:'sys_person_add'},{id:'btnEdit',code:'person_button_edit'}]);
	/**   权限控制 end   */
	
	DicUtil.registerDics([{dicId:1,domId:'sex'},{dicId:2,domId:'type'}]);
	
});

function showmsg(msg) {
	$.messager.alert("提示", msg, 'info');
}

function reload1() {
	dg1.datagrid('reload');
}

function datagrid1(id) {
	return $('#' + id)
			.datagrid(
					{
						title : '技术指令列表',
						method : 'post',
						sortName : 'id', // 排序的列
						sortOrder : 'desc', // 倒序
						collapsible : false,// 可折叠
						striped : true, // 奇偶行颜色不同
						pagination : true, // 显示分页
						rownumbers : true, // 显示行号
						pageList : [ 10, 20, 30, 50 ],
						url : 'queryInstructionList',
						idField : 'id', // 主键字段
						onBeforeLoad : function(param) {
							 
							var jstgh = $("#jstgh").val(); // 
							var pgdh = $("#pgdh").val(); // 
							var jx = $("#jx").val(); // 
							var zt = $("#zt").combobox('getValue'); // 
							
							if (jstgh != null && jstgh != "") {// 
								param['jstgh'] = jstgh;
							}
							if (pgdh != null && pgdh != "") {// 
								param['pgdh'] = pgdh;
							}
							if (zt != null && zt != "") {// 
								param['zt'] = zt;
							}
							if (jx != null && jx != "") {// 
								param['jx'] = jx;
							}
							param['pagination.sort'] = param.sort;
							param['pagination.order'] = param.order;
							param['pagination.page'] = param.page;
							param["pagination.rows"] = param.rows;
						},
						onLoadSuccess : function(data) {
							if (data.rows != null && data.rows != '') {
								dg1.datagrid('selectRow', 0);
							}
						},
						onSelect : function(rowIndex, rowData) {// 单机体验馆事件
						},
						checked : function(rowData){
							alert(rowData);
						},
						columns : [ [
								{
									field : 'jszlh',
									title : '维护提示编号'
								},
								{
									field : 'orderSourceList',
									title : '关联评估单号',
									formatter : function(value, row, index) {
										if(row.orderSourceList!=null)
										//alert(row.orderSourceList.pgdid);
									var str="";
										for(var i=0;i<row.orderSourceList.length;i++){
											str+="<a href='javascript:selectSend("+row.orderSourceList[i].pgdid+");'>"+row.orderSourceList[i].pgdh+"</a><br>"
											
											//alert(row.orderSourceList[i].pgdid);
										}
										return str;
									}
								},
								{
									field : 'realname',
									title : '制单人'
								},
								{
									field : 'zdsj',
									title : '制单日期'
								},
								{
									field : 'ckzl',
									title : '参考资料'
								},
								{
									field : 'zhut',
									title : '主题'
								},
								{
									field : 'zt',
									title : '状态',
									formatter : function(value, row, index) {
										return value == 1 ? '已提交':'未提交';
									}
								},
								{
									field : 'dprtcode',
									title : '组织机构代码'
								}] ],
						fitColumns : false,
						singleSelect : true,
						toolbar : [
								{
									id : 'btnAdd',
									text : '新增',
									iconCls : 'icon-add',
									handler : function() {
										if ($("#winadd").html() == null
												|| $("#winadd").html() == "") {
											$(document.body).append(
													"<div id='winadd'></div>");
										}
										openWin("winadd", "新增技术指令",
												"intoAddInstruction", $(window)
														.width() - 50,
												$(window).height() - 50);
									}
								},
								'-',
								{
									id : 'btnEdit',
									text : '修改',
									iconCls : 'icon-edit',
									handler : function() {
										updateMainInstruction();
									}
								},
								'-',{
									id : 'btnShenpi',
									text : '审核',
									iconCls : 'icon-edit',
									handler : function() {
										shenheMainAnnunciate();
										
									}
								},
								'-',{
									id : 'btnPifu',
									text : '批复',
									iconCls : 'icon-edit',
									handler : function() {
										pifuMainAnnunciate();
										
									}
								},
								'-',{
									id : 'btnView',
									text : '查看',
									iconCls : 'icon-view',
									handler : function() {
										viewMainInstruction();
										
									}
								},
								{
									id : 'btnClubDel',
									text : '关闭',
									iconCls : 'icon-remove',
									handler : function() {
										var row = dg1.datagrid("getSelected");
										
										// 删除时先获取选择行
										var rows = dg1
												.datagrid("getSelections");
										// 选择要删除的行
										if (rows.length > 0) {
											$.messager
													.confirm(
															"提示",
															"你确定要关闭吗?",
															function(r) {
																if(rows[0].zt!=0){
																	$.messager
																	.alert(
																			'提示',
																			'只能作废已提交状态的维护提示',
																			'info');
																	dg1
																	.datagrid('reload');
																	return false;
																}
																if (r) {
																	var advTypes = [];
																	for ( var i = 0; i < rows.length; i++) {
																		advTypes
																				.push(rows[i].typeCode);
																	}
																	
																	// 将选择到的行存入数组并用,分隔转换成字符串，
																	// 本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id
																	// alert(ids.join(','));
																	$
																			.ajax({
																				type : "post",
																				url : "offInstruction",
																				dataType : "json",
																				data : {
																					'id' : row.id
																				},
																				success : function(
																						data) {
																					if (data.state == "success") {
																						showmsg("操作成功!");
																						reload1();
																					} else {
																						$.messager.alert("提示", data.message,"question");
																					}
																					dg1
																							.datagrid('reload');
																				}
																			});
																}
															});
										} else {
											$.messager.alert("提示", "请选择要关闭的行",
													"error");
										}
									}

								},
								'-',
								{
									id : 'btnClubDel',
									text : '作废',
									iconCls : 'icon-remove',
									handler : function() {
										var row = dg1.datagrid("getSelected");
										
										// 删除时先获取选择行
										var rows = dg1
												.datagrid("getSelections");
										// 选择要删除的行
										if (rows.length > 0) {
											
											$.messager
													.confirm(
															"提示",
															"你确定要作废吗?",
															function(r) {
																if(rows[0].zt!=0){
																	$.messager
																	.alert(
																			'提示',
																			'只能作废未提交状态的维护提示',
																			'info');
																	dg1
																	.datagrid('reload');
																	return false;
																}
																if (r) {
																	var advTypes = [];
																	for ( var i = 0; i < rows.length; i++) {
																		advTypes
																				.push(rows[i].typeCode);
																	}
																	
																	// 将选择到的行存入数组并用,分隔转换成字符串，
																	// 本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id
																	// alert(ids.join(','));
																	$
																			.ajax({
																				type : "post",
																				url : "deleteInstruction",
																				dataType : "json",
																				data : {
																					'id' : row.id
																				},
																				success : function(
																						data) {
																					if (data.state == "success") {
																						showmsg("操作成功!");
																						reload1();
																					} else {
																						$.messager.alert("提示", data.message,"question");
																					}
																					dg1
																							.datagrid('reload');
																				}
																			});
																}
															});
										} else {
											$.messager.alert("提示", "请选择要作废的行",
													"error");
										}
									}
								} ]
					// 只允许选择一行 单选属性
					});
}

function selectCity(val) {
	if (val == "") {
		$("#code").hide();
		$("#code").html("<option value=''></option>");
	} else {
		AjaxUtil.ajax({
			type : 'post',
			cache : false,
			url : 'select_city.do',
			data : {
				code : val
			},
			dataType : 'json',
			success : function(data) {
				if (data.code == 1) {
					$("#code").show();
					var cityList = data.cityList;
					$("#code").html("");
					for ( var i = 0; i < cityList.length; i++) {
						$("#code").append(
								"<option value=" + cityList[i].code + ">"
										+ cityList[i].name + "</option>");
					}
				} else {
					if (data.code == 9) {
						$.messager.alert('提示', data.text, 'info');
					}
				}
			}
		});

	}
}

function changeState(val) {
	$.messager.confirm("提示", "你确定要修改吗?", function(r) {
		if (r) {
			var typeCode = $(val).attr("typeCode");
			var typeState = $(val).attr("typeState");
			
			if(typeState==0){
				typeState=1;
			}else{
				typeState=0;
			}
			
			AjaxUtil.ajax({
				type : "post",
				url : "update_adv_status.do",
				dataType : "json",
				data : {
					'typeCode' : typeCode,
					'typeState' : typeState
				},
				success : function(data) {
					$.messager.alert('提示', data.text, 'info');
					dg1.datagrid('reload');
				}
			});
		}
	});
}
//技术通告修改
function updateMainInstruction(){
	var row = dg1.datagrid("getSelected");
	if (row) {
		if(checkUpdIt(row.id)){	
			intoEditMainInstruction(row.id);
		}
	} else {
		$.messager.alert("提示", "请选择要修改的行","question");
	}
}
//技术指令审核
function shenheMainAnnunciate(){
	var row = dg1.datagrid("getSelected");
	if (row) {
		if(row.zt==1){	
			intoShenheMainInstruction(row.id);
		}else{
			$.messager.alert("提示", "只能审核已提交状态的技术指令","question");
		}
	} else {
		$.messager.alert("提示", "请选择要修改的行","question");
	}
}
//技术指令批复
function pifuMainAnnunciate(){
	var row = dg1.datagrid("getSelected");
	if (row) {
		if(row.zt==2){	
			intoPifuMainInstruction(row.id);
		}else{
			$.messager.alert("提示", "只能批复已审核状态的技术指令","question");
		}
	} else {
		$.messager.alert("提示", "请选择要批复的行","question");
	}
}
//查看技术指令
function viewMainInstruction(){
	var row = dg1.datagrid("getSelected");
	if (row) {
			intoViewMainInstruction(row.id);
	} else {
		$.messager.alert("提示", "请选择要查看的行","question");
	}
}
//验证是否能进行修改
function checkUpdIt(id){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath + "/project/instruction/checkUpdIt",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			if (data.state == "success") {
				flag = true;
			} else {
				$.messager.alert("提示", data.message,"question");
			}
		}
	});
	return flag;
}
//进入到审核页面
function intoShenheMainInstruction(id){

	if ($("#winshenhe").html() == null || $("#winshenhe").html() == "") {
		$(document.body).append("<div id='winshenhe'></div>");
	}
	var row = dg1.datagrid("getSelected");
	if (row) {
		openWin("winshenhe", 
				"审核维护提示",
				basePath + "/project/instruction/intoShenheMainInstruction?id=" + id,
				$(window).width() - 50,
				$(window).height() - 50);
	} else {
		$.messager.alert("提示", "请选择要审核的行","question");
	}
}
//进入到批复页面
function intoPifuMainInstruction(id){

	if ($("#winpifu").html() == null || $("#winpifu").html() == "") {
		$(document.body).append("<div id='winpifu'></div>");
	}
	var row = dg1.datagrid("getSelected");
	if (row) {
		openWin("winpifu", 
				"批复维护提示",
				basePath + "/project/instruction/intoPifuMainInstruction?id=" + id,
				$(window).width() - 50,
				$(window).height() - 50);
	} else {
		$.messager.alert("提示", "请选择要批复的行","question");
	}
}


//进入修改界面
function intoEditMainInstruction(id){

	if ($("#winedit").html() == null || $("#winedit").html() == "") {
		$(document.body).append("<div id='winedit'></div>");
	}
	var row = dg1.datagrid("getSelected");
	if (row) {
		openWin("winedit", 
				"修改技术指令",
				basePath + "/project/instruction/intoEditMainInstruction?id=" + id,
				$(window).width() - 50,
				$(window).height() - 50);
	} else {
		$.messager.alert("提示", "请选择要修改的行","question");
	}
}
//进入查询界面
function intoViewMainInstruction(id){

	if ($("#winview").html() == null || $("#winview").html() == "") {
		$(document.body).append("<div id='winview'></div>");
	}
	var row = dg1.datagrid("getSelected");
	if (row) {
		openWin("winview", 
				"查看技术指令",
				basePath + "/project/instruction/intoViewMainInstruction?id=" + id,
				$(window).width() - 50,
				$(window).height() - 50);
	} else {
		$.messager.alert("提示", "请选择要查看的行","question");
	}
}
//查看
function selectSend(id){
	var row = dg1.datagrid("getSelected");
	if ($("#btnSends").html() == null || $("#btnSends").html() == "") {
		$(document.body).append("<div id='btnSends'></div>");
	}
	
	openWin("btnSends", "查看",
			basePath + "/project/technicalfile/findApprovalFileUpload?id="
					+id,
			$(window).width() - 400,
			$(window).height() - 0);
	
}



