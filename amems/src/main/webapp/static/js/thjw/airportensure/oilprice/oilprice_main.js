var alertModalAdd='alertModalAdd';
$(document).ready(function(){
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRevision();//调用主列表页查询
			}
		}
	});
	
	var zzjgid=$('#zzjgid').val();
	$("#dprtcode").val(zzjgid);

	//加载油品
	loadingOil();
	//拼接暂无一行
	appendNoData();
	
	refreshPermission();
	//时间控件
	$('#ksrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	}).on('hide', function(e) {
		  $('#form').data('bootstrapValidator')  
        .updateStatus('ksrq', 'NOT_VALIDATED',null)  
        .validateField('ksrq');  
  });
	//验证
	validation();
	 $('#oilList>tr').click(function() {
			$(this)
	        .addClass('sel')
	        .siblings().removeClass('sel');
		});
	 
		//初始化日志功能
		logModal.init({code:'B_J_004'});
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
	            ksrq: {
	                validators: {
	                	notEmpty: {
	                        message: '开始日期不能为空'
	                    }
	                }
	            },
	            ypjg: {
	                validators: {
	                    notEmpty: {
	                        message: '价格不能为空'
	                    },
	                    regexp: {
		                    regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
		                    message: '只能输入10位整数和2位小数'
		                }
	                }
	            }
	        }
	    });
}

//加载油品
function loadingOil(){
	 var dprtcode=$('#dprtcode').val();
	 AjaxUtil.ajax({
				url:basePath+"/airportensure/oilprice/selectByOil",
				   type: "post",
				   async: false,
				   dataType:"json",
				   data:{
					   'dprtcode' : dprtcode
				   },
				   success:function(data){
				   if(data.OilList !=""){
				    	appendContentHtmlOil(data.OilList);
					   } else {
						  $("#oilList").empty();
						  $("#oilList").append("<tr><td class='text-center' colspan=\"2\">暂无数据 No data.</td></tr>");
						  $("#pagination").empty();
						  $("#oilpriceList").empty();
						  $("#oilpriceList").html("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
						  $("#ypggid").val("");
						  $("#szName").empty();
						  $("#sz").val("");
					   }
		   }
		 });
}

function appendContentHtmlOil(list){
	 var htmlContent = '';
	   $.each(list,function(index,row){
		   var choId='cho'+row.id;
		   if (index == 0) {
			   if($("#sz").val()=="" || $("#sz").val()==null){
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" ypggid='"+StringUtil.escapeStr(row.id)+"' mc='"+StringUtil.escapeStr(row.ypgg)+"' sz='"+StringUtil.escapeStr(row.ypgg)+"' onclick=\"chooseOil(this,'"+choId+"')\" class='sel' id="+choId+" >";
				   $("#szName").html(StringUtil.escapeStr(row.ypgg));
				   $("#sz").val(row.ypgg);
				   $("#ypmc").val(row.ypgg);
				   $("#ypggid").val(row.id);
				   goPage(1,"auto","desc");
			   }else{
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" ypggid='"+StringUtil.escapeStr(row.id)+"' mc='"+StringUtil.escapeStr(row.ypgg)+"' sz='"+StringUtil.escapeStr(row.ypgg)+"' onclick=\"chooseOil(this,'"+choId+"')\" id="+choId+" >";
				   
			   }
		   }else{
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" ypggid='"+StringUtil.escapeStr(row.id)+"' mc='"+StringUtil.escapeStr(row.ypgg)+"' sz='"+StringUtil.escapeStr(row.ypgg)+"'  onclick=\"chooseOil(this,'"+choId+"')\" id="+choId+" >";
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ypgg)+"'>"+StringUtil.escapeStr(row.ypgg)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.ypjg)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#oilList").empty();
	   $("#oilList").html(htmlContent);
}

function appendNoData(){
	$("#oilpriceList").html("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
	$("#pagination").empty();
}
var objOil="";
//查看油品价格
function chooseOil(obj,e){
	
	objOil=e;
	var mc=$(obj).attr("mc");
	var sz=$(obj).attr("sz");
	var ypggid=$(obj).attr("ypggid");
	if(sz==null || sz==""){
		sz=$("#sz").val();
	}
	if(mc==null || mc==""){
		mc=$("#ypmc").val();
	}
	if(ypggid==null || ypggid==""){
		ypggid=$("#ypggid").val();
	}
	//回显到表头
	$("#szName").html(StringUtil.escapeStr(mc));
	$("#sz").val(sz);
	$("#ypmc").val(mc);
	$("#ypggid").val(ypggid);
	goPage(1,"auto","desc");
	//行变色
	$("#"+objOil).addClass('sel').siblings().removeClass('sel');
	
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.ypggid=$("#ypggid").val();
	obj.dprtcode=$("#dprtcode").val();
	obj.keyword=$.trim($("#keyword_search").val());
	AjaxUtil.ajax({
		url:basePath+"/airportensure/oilprice/selectByYpgg",
		   type: "post",
		   dataType:"json",
		   contentType:"application/json;charset=utf-8",
		   data:JSON.stringify(obj),
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
						//controller: this_
					});
			   }else{
				   appendNoData();
			   }
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[3,4]);
				   new Sticky({tableId:'quality_check_list_table'});
   }
 });
}
function appendContentHtml(list){
	  var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
		   }
		   htmlContent = htmlContent + "<td class='text-center'>";
			   htmlContent = htmlContent +"<i class='icon-edit color-blue cursor-pointer checkPermission' " +
			   "id='"+formatUndefine(row.id)+"' ksrq='"+(formatUndefine(row.ksrq).substring(0,10))+"' ypjg='"+formatUndefine(row.ypjg)+"' "+	
			   "permissioncode='airportensure:oilprice:main:02' onClick=\"edit(this)\" title='修改 Edit'></i>&nbsp;&nbsp;";
			   htmlContent = htmlContent +"<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='airportensure:oilprice:main:03' onClick=\"invalid('"
				+ row.id + "')\" title='作废 Invalid '></i>&nbsp;&nbsp;";
		   htmlContent = htmlContent + "</td>";
		   htmlContent = htmlContent + "<td class='text-center'>"+(formatUndefine(row.ksrq).substring(0,10))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.ypjg)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.whr.displayName)+"'>"+StringUtil.escapeStr(substring0To20(row.whr.displayName))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.whsj)+"</td>";  
		   htmlContent = htmlContent + "</tr>";
		    
	   });
	   $("#oilpriceList").empty();
	   $("#oilpriceList").html(htmlContent);
	   refreshPermission();
}
function add(){
	var ypggid=$("#ypggid").val();
	if(ypggid=="" || ypggid==null){
		AlertUtil.showErrorMessage('请先选择油品!');
		return false;
	}
	//初始化最新检验日期
	TimeUtil.getCurrentDate(function (data){
		$("#ksrq").val(data);
	});
	$("#ksrq").datepicker('setStartDate','');
	//时间控件
	$('#ksrq').datepicker({
		autoclose: true,
		clearBtn:false
	}).on('hide', function(e) {
		$('#form').data('bootstrapValidator')  
		.updateStatus('ksrq', 'NOT_VALIDATED',null)  
		.validateField('ksrq');  
	});
	validation();
	
	//清空
	$('#oilpriceId').val("");
	$('#ksrq').val("");
	$('#ypjg').val("");
	$('#ypjg').change();
	$('#ksrq').change();
	
	var sz=$('#sz').val();
	var ypmc=$('#ypmc').val();
		if(sz!=null && sz!=""){
			$('#ypName').val(ypmc);
			$("#alertModalAdd").modal("show");
		}else{
			AlertUtil.showErrorMessage('请先选择油品!');
		}
	
}
function edit(obj){
	validation();
	//清空
	$('#oilpriceId').val("");
	$('#ksrq').val("");
	$('#ypjg').val("");
	 var id = $(obj).attr("id");
	 var ksrq = $(obj).attr("ksrq");
	 var ypjg = $(obj).attr("ypjg");
	 $('#oilpriceId').val(id);
	 $('#ksrq').val(ksrq);
	 $('#ypjg').val(ypjg);
	 
	 var sz=$('#sz').val();
	 var ypmc=$('#ypmc').val();
		if(sz!=null && sz!=""){
			$('#ypName').val(ypmc);
			$("#alertModalAdd").modal("show");
		}else{
			AlertUtil.showErrorMessage('请先选择油品!');
		}
}
//保存，修改
function saveUpdate(){
	var ypgg=$('#sz').val();
	var ypggid=$('#ypggid').val();
	var ksrq=$('#ksrq').val();
	var ypjg=$('#ypjg').val();
	var id=$('#oilpriceId').val();
	var obj={};
	obj.id=id;
	obj.ypgg=ypgg;
	obj.ksrq=ksrq;
	obj.ypjg=ypjg;
	obj.ypggid=ypggid;
	//验证
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	  var url='';
	if(id!=null && id!=""){
		url = basePath+"/airportensure/oilprice/updateOilprice";//修改
	}else{
		url = basePath+"/airportensure/oilprice/saveOilprice";//新增
		
	}
	submitAjax(obj,url);
}
//作废
function invalid(id){
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		var obj={};
		obj.id=id;
		url = basePath+"/airportensure/oilprice/invalidOilprice";//作废
		submitAjax(obj,url);
	 }});
	
}
//提交ajax
function submitAjax(obj,url){
	startWait();
	AjaxUtil.ajax({
 		url:url,
 		contentType:"application/json;charset=utf-8",
 		type:"post",
 		async: false,
 		data:JSON.stringify(obj),
 		dataType:"json",
 		success:function(data){
 			finishWait();
 			$("#alertModalAdd").modal("hide");
 			//加载油品
 			loadingOil();
 			//alert($("#sz").val());
 			//goPage(1,"auto","desc");//开始的加载默认的内容 
 			chooseOil(this,objOil);
 			AlertUtil.showMessage('操作成功!');
 			 
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
// 信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}
//隐藏Modal时验证销毁重构
$("#"+alertModalAdd).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     validation();
});
//根据组织机构的改变获取油品规格
function ypggChange() {
	$("#sz").val("");
	loadingOil();
}

function substring0To20(str){
	if(str.length>=20){
		return str.substring(0,20)+"...";
	}
	return str;
}