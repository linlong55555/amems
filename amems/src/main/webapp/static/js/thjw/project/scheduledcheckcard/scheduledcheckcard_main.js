var dg1;

//加载事件
$(function() {
	
  $("#grid").width(($(window).width() - 30));
  dg1 = datagrid1('grid');
  
  //查询时间
  $('#query').click(function() {
	//刷新
	reload1();
  });
});

//重新加载
function reload1() {
  dg1.datagrid('reload');
}

//显示提示信息
function showmsg(msg) {
  $.messager.alert("提示", msg, 'info');
}

//日期格式
function myformatter(date){
  var y = date.getFullYear();
  var m = date.getMonth()+1;
  var d = date.getDate();
  return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

//日期格式转换
function myparser(s){
  if (!s) return new Date();
  var ss = (s.split('-'));
  var y = parseInt(ss[0],10);
  var m = parseInt(ss[1],10);
  var d = parseInt(ss[2],10);
  if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
      return new Date(y,m-1,d);
  } else {
      return new Date();
  }
}

//显示数据表格
function datagrid1(id) {
  //表格
  return $('#' + id).datagrid(
  {
   title:'定检工卡列表',	//标题
   method:'get',		//请求方式（post或get）默认为get
   sortName:'REMAININGDAYS', //排序名称（剩余天数）
   sortOrder:'asc', 	//排序顺序（asc为升序，desc为降序）
   collapsible:false,	//可折叠的（true为可折叠，false为不可折叠）
   striped:true, 		//条纹（true为有条纹，false为无条纹）
   pagination:true, 	//分页（true为显示分页，false为不显示分页）
   rownumbers:true, 	//行号（true为显示行号，false为不显示行号）
   fitColumns:true,		//自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动（默认为false）
   singleSelect:true,	//只允许选中一行
   width:'100%',
   pageList:[10, 20, 30, 40, 50], 		//分页条数列表
   url:'page',	//发送请求的地址（默认为当前页地址）
   idField:'id', 		//id字段
   onBeforeLoad:function(param) {	
	   	 //请求参数
		 /*param['jszlh'] = $.trim($("#jszlh").val());				//修订通知书编号
		 param['pgdh'] = $.trim($("#pgdh").val());					//评估单号
		 param['zdr'] = $.trim($("#zdr").val());					//制单人
		 param['zdsj'] = $.trim($('#zdsj').datebox('getValue'));	//制单日期
		 param['tzslx'] = $.trim($("#tzslx").val());				//通知书类型value
		 param['tzslxText'] = $.trim($("#tzslx").find("option:selected").text());	//通知书类型text
		 param['jx'] = $.trim($("#jx").val());						//机型value
		 param['jxText'] = $.trim($("#jx").find("option:selected").text());			//机型text
		 param['xdzt'] = $.trim($("#xdzt").val());					//修订主题
		 param['xdr'] = $.trim($("#xdr").val());					//修订人
		 param['xdqx'] = $.trim($('#xdqx').datebox('getValue'));	//修订期限
		 param['xdnr'] = $.trim($("#xdnr").val());					//修订内容
		 param['zt'] = $.trim($("#zt").val());						//状态value
		 param['ztText'] = $.trim($("#zt").find("option:selected").text());			//状态Text
		 param['thresholdKey'] = "JSWJ";							//系统阀值设置key
*/		 param['pagination.sort'] = param.sort;						//排序列字段名
		 param['pagination.order'] = param.order;					//排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
		 param['pagination.page'] = param.page;						//页数
		 param["pagination.rows"] = param.rows;						//每页行数
   },
   onLoadSuccess:function(data) {						//加载成功回调事件
	  
   },
   onSelect:function(rowIndex, rowData) {				//单机体验事件
	  
   },
   rowStyler: function(index,rowData) {						//行样式
	 
   },
   columns:[[	 
   {
	field:'id',		 
	title:'定检工卡编号',		 
	width:160,			 
	align:'center'		 

   },
   {
	field:'gzzt',
	title:'工作主题',
	width:100,
	align:'center'
   },
   {
	field:'zdsj',
	title:'关联定检项目',
	width:100,
	align:'center'
   },
   {
	field:'tzslx',
	title:'通知书类型',
	width:100,
	align:'center',
	formatter:function(value,rowData,rowIndex) {
	  //单元格的格式化函数，需要三个参数：（value：字段的值，rowData：行的记录数据，rowIndex：行的索引）
	  if(rowData.tzslx == 1) {
		 return '维修方案';
	  }else if(rowData.tzslx == 2) {
		 return 'MEL';
	  }else if(rowData.tzslx == 3) {
		 return '工单';
	  }
	}
   },
   {
	field:'jx',
	title:'机型',
	width:80,
	align:'center'
   },
   {
	field:'xdzt',
	title:'修订主题',
	width:180,
	align:'center'
   },
   {
	field:'xdr',
	title:'修订人',
	width:100,
	align:'center'
   },
   {
	field:'xdqx',
	title:'修订期限',
	width:100,
	align:'center'
   },
   {
	field:"remainingDays",
	title:"剩余天数",
	width:80,
	align:'center',
	styler:function(value,rowData,rowIndex) {
	  if (rowData.remainingDays <= rowData.yjtsjb1) {										   		   //剩余天数小于等于预警天数级别1
		  return 'background-color:#FF0000;color:#000000;font-weight:bold;';
	  }else if (rowData.remainingDays > rowData.yjtsjb1 && rowData.remainingDays <= rowData.yjtsjb2) { //剩余天数大于预警天数级别1并且剩余天数小于等于预警天数级别2
		  return 'background-color:#FFFF00;color:#000000;font-weight:bold;';
	  }
	}
   },
   {
	field:'xdnr',
	title:'修订内容',
	width:220,
	align:'center'
   },
   {
	field:'zt',
	title:'状态',	
	width:100,
	align:'center',
	formatter:function(value,rowData,rowIndex) {
	  if(rowData.zt == 1) {
		 return '未提交';
	  }else if(rowData.zt == 2) {
		 return '已提交';
	  }else if (rowData.zt == 8) {
		 return '已作废';
	  }else if(rowData.zt == 9) {
		 return '指定结束';
	  }else if (rowData.zt == 10) {
		 return '已完成';
	  }
	}
   },
   {
	field:'operate',
	title:'操作',	
	width:180,
	align:'center',
	formatter:function(value,rowData,rowIndex) {
      //修改按钮
	  var modify = '';
	  if (rowData.zt == 1) {
		  modify = '<a href="javascript:void(0);" class="revisionNoticeBookOperate" onclick="modifyRevisionNoticeBook('+rowIndex+')">修改</a>';
	  }
	  
	  //查看按钮
	  var view = '<a href="javascript:void(0);" class="revisionNoticeBookOperate" onclick="viewRevisionNoticeBook('+rowIndex+')">查看</a>';
	  
	  //打印按钮
	  var print = '<a href="javascript:void(0);" class="revisionNoticeBookOperate" onclick="printRevisionNoticeBook('+rowIndex+')">打印</a>';
	  
	  //关闭按钮
	  var close = '';
	  
	  //指定结束按钮
	  var end = '';
	  if (rowData.zt == 2) {
		  close = '<a href="javascript:void(0);" class="revisionNoticeBookOperate"  onclick="closeRevisionNoticeBook('+rowIndex+')">关闭</a>';
		  end = '<a href="javascript:void(0);" class="revisionNoticeBookOperate"  onclick="endRevisionNoticeBook('+rowIndex+')">指定结束</a>';
	  }
	  
	  return modify + '&nbsp;' + view + '&nbsp;' + print + '&nbsp;' + close + '&nbsp;' + end;
	}
   },
   ]],
   toolbar:[			//工具栏
   {
	id:'btnAdd',
	text:'新增',
	iconCls:'icon-add',
	handler:function() {
	  if ($("#winAdd").html() == null || $("#winAdd").html() == "") {
		  $(document.body).append("<div id='winAdd'></div>");
	  }
		
	  
		
	  openWin("winAdd",
			  "新增修订通知书", "intoAddRevisionNoticeBook",
			  $(window).width() - 50,
			  $(window).height() - 50
			 );
	}
   }
  ]
  });
}

//查看评估单号操作
function viewPgdh(rowIndex,pgdid) {
	if(typeof(pgdid) == undefined || pgdid == ''){
		$.messager.alert("提示", "没有可查看的评估单号","error");
	}
	else{
		$('#revisionNoticeBookList').datagrid('selectRow',rowIndex);
		
		if ($("#btnSends").html() == null || $("#btnSends").html() == "") {
			$(document.body).append("<div id='btnSends'></div>");
		}
		openWin("btnSends", "查看",
				basePath+"/project/technicalfile/findAssessFileUpload?id="
						+ pgdid,
				$(window).width() - 400,
				$(window).height() - 0);
	}
	
}

//修改操作
function modifyRevisionNoticeBook(rowIndex) {
  $('#revisionNoticeBookList').datagrid('selectRow',rowIndex);
  
  //获取选中的一行
  var row = dg1.datagrid('getSelected');
  
  if (row == "") {
	  $.messager.alert("提示", "没有可查看的修订通知书","error");
  }else if (row.id != null && row.id != undefined) {
	  AjaxUtil.ajax({
		  url:'queryRevisionNoticeBook',					//发送请求的地址（默认为当前页地址）
		  type:'post',										//请求方式（post或get）默认为get
		  data:{'id':row.id},								//发送到服务器的数据
		  dataType:'json',									//预期服务器返回的数据类型
		  success:function(data) {							//请求成功后调用的回调函数
			if (data.revisionNoticeBook == undefined) {
				$.messager.alert("提示", "没有可修改的修订通知书","error");
			}else {
				if ($("#winModify").html() == null || $("#winModify").html() == "") {
					$(document.body).append("<div id='winModify'></div>");
				}
				
				openWin("winModify",
						"修改修订通知书","intoRevisionNoticeBook?id="+row.id,
						$(window).width() - 50,
						$(window).height() - 50
					   );
			}
		  }
	  });
  } else {
	  $.messager.alert("提示", "请选择要查看的修订通知书", "error");
  }
}

//查看操作
function viewRevisionNoticeBook(rowIndex) {
  $('#revisionNoticeBookList').datagrid('selectRow',rowIndex);
  
  //获取选中的一行
  var row = dg1.datagrid("getSelected");
  
  if (row == "") {
	  $.messager.alert("提示", "没有可查看的修订通知书","error");
  }else if (row.id != null && row.id != undefined) {
	  AjaxUtil.ajax({
		  url:'queryRevisionNoticeBook',					//发送请求的地址（默认为当前页地址）
		  type:'post',										//请求方式（post或get）默认为get
		  data:{'id':row.id},								//发送到服务器的数据
		  dataType:'json',									//预期服务器返回的数据类型
		  success:function(data) {							//请求成功后调用的回调函数
			if (data.revisionNoticeBook == undefined) {
				$.messager.alert("提示", "没有可修改的修订通知书","error");
			}else {
			    if ($("#winView").html() == null || $("#winView").html() == "") {
				    $(document.body).append("<div id='winView'></div>");
			    }
				
				openWin("winView",
						"查看修订通知书","intoViewRevisionNoticeBook?id="+row.id,
						$(window).width() - 50,
						$(window).height() - 50
					   );
			}
		  }
	  });
  } else {
	  $.messager.alert("提示", "请选择要查看的修订通知书", "error");
  }
}
 
//打印操作
function printRevisionNoticeBook(rowIndex) {
  $('#revisionNoticeBookList').datagrid('selectRow',rowIndex);
	
  //获取选中的一行
  var row = dg1.datagrid("getSelected");
  
  if (row == "") {
	  $.messager.alert("提示", "没有可打印的修订通知书","error");
  }else if (row.id != null && row.id != undefined) {
	  $.messager.alert("提示", "该操作还未完善","error");
  }else {
	  $.messager.alert("提示", "请选择要打印的修订通知书", "error");
  }
}

//关闭操作
function closeRevisionNoticeBook(rowIndex) {
  $('#revisionNoticeBookList').datagrid('selectRow',rowIndex);
	
  //获取选中的一行
  var row = dg1.datagrid("getSelected");

  if (row == "") {
	  $.messager.alert("提示", "没有可关闭的修订通知书","error");
  }else if (row.id != null && row.id != undefined) {
	  AjaxUtil.ajax({
		  url:'queryRevisionNoticeBook',					//发送请求的地址（默认为当前页地址）
		  type:'post',										//请求方式（post或get）默认为get
		  data:{'id':row.id},								//发送到服务器的数据
		  dataType:'json',									//预期服务器返回的数据类型
		  success:function(data) {							//请求成功后调用的回调函数
			if (data.revisionNoticeBook == undefined) {
				$.messager.alert("提示", "没有可关闭的修订通知书","error");
			}else {
			    $.messager.confirm("提示","你确定要关闭吗?", function(confirm)
			    {
				 if (confirm) {
					 AjaxUtil.ajax({
						 type:"post",
						 url:"closeRevisionNoticeBook",
						 dataType:"json",
						 data:{'id':row.id,'zt':9},
						 success:function(data) {
						   $.messager.alert('提示',data.text,'info');
						   dg1.datagrid('reload');
						 }
					  });
				 }
				});
			}
		  }
	  });
  } else {
	  $.messager.alert("提示", "请选择要关闭的修订通知书", "error");
  }
}

//指定结束操作
function endRevisionNoticeBook(rowIndex) {
  $('#revisionNoticeBookList').datagrid('selectRow',rowIndex);
	
  //获取选中的一行
  var row = dg1.datagrid("getSelected");

  if (row == "") {
	  $.messager.alert("提示", "没有可指定结束的修订通知书","error");
  }else if (row.id != null && row.id != undefined) {
	  AjaxUtil.ajax({
		  url:'queryRevisionNoticeBook',					//发送请求的地址（默认为当前页地址）
		  type:'post',										//请求方式（post或get）默认为get
		  data:{'id':row.id},								//发送到服务器的数据
		  dataType:'json',									//预期服务器返回的数据类型
		  success:function(data) {							//请求成功后调用的回调函数
			if (data.revisionNoticeBook == undefined) {
				$.messager.alert("提示", "没有可指定结束的修订通知书","error");
			}else {
			    if ($("#winEnd").html() == null || $("#winEnd").html() == "") {
				    $(document.body).append("<div id='winEnd'></div>");
			    }
				
				openWin("winEnd",
						"指定结束修订通知书","intoEndRevisionNoticeBook?id=" + row.id,
						$(window).width() - 50,
						$(window).height() - 50
					   );
			}
		  }
	  });
  } else {
	  $.messager.alert("提示", "请选择要指定结束的修订通知书", "error");
  }
}