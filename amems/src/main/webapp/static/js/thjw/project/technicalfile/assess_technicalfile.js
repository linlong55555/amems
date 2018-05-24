var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var no=0;
var zxdxFjzch="";
var xlhValue="";
var tr={};
//时间控件加载
$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});

$(document).ready(function(){
	initFixedWorkContent();
	
	 //MenuUtil.activeMenu("project:technicalfile:mainassess");
	//数据字典
	var dprtcode=$("#technicalFileDprtcode").val();
/*	DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);*/

	
	for(var j=0;j<document.getElementById("syx").options.length;j++) 
	{ 
		if(document.getElementById("syx").options[j].value==$('#syx1').val()){
			document.getElementById("syx").options[j].selected=true;
		}
	} 
	
	
/*	for(var j=0;j<document.getElementById("ly").options.length;j++) 
	{ 
		if(document.getElementById("ly").options[j].value==$('#ly1').val()){
			document.getElementById("ly").options[j].selected=true;
		}
	} 
	for(var j=0;j<document.getElementById("fl").options.length;j++) 
	{ 
		if(document.getElementById("fl").options[j].value==$('#fl1').val()){
			document.getElementById("fl").options[j].selected=true;
		}
	} 
	for(var j=0;j<document.getElementById("wjlx").options.length;j++) 
	{ 
		if(document.getElementById("wjlx").options[j].value==$('#wjlx1').val()){
			document.getElementById("wjlx").options[j].selected=true;
		}
	} */
	$("#qtMs").hide();
	if($('#syx1').val()==0){
		$("#xfwj").hide();
		$("#yxdxList").hide();
		$("#syxdxms").hide();
	}
	if($("#isJstg").val()==1){
		 $("#isJstg").attr("checked",true);
	}
	if($("#isJszl").val()==1){
		 $("#isJszl").attr("checked",true);
	}
	if($("#isWxfa").val()==1){
		 $("#isWxfa").attr("checked",true);
	}
	if($("#isXdmel").val()==1){
		$("#isXdmel").attr("checked",true);
	}
	if($("#isXdgk").val()==1){
		$("#isXdgk").attr("checked",true);
	}
	if($("#isGczl").val()==1){
		 $("#isGczl").attr("checked",true);
	}
	if($("#isFjgd").val()==1){
		 $("#isFjgd").attr("checked",true);
	}
	if($("#isFlxgd").val()==1){
		 $("#isFlxgd").attr("checked",true);
	}
	if($("#isZjfj").val()==1){
		 $("#isZjfj").attr("checked",true);
	}
	if($("#isQt").val()==1){
		 $("#isQt").attr("checked",true);
		 $("#qtMs").show();
	}
	
	changeXXX();
	fjzchOnchange();
	
	
	//上传
	$("#fileuploader").uploadFile({
		url:basePath+"/project/technicalfile/ajaxUploadFile",
		multiple:false,
		dragDrop:false,
		maxFileCount:1,
		formData: {"fileType":"technicalfile"},//参考FileType枚举（技术文件类型）
		fileName: "myfile",
		returnType: "json",
		removeAfterUpload:true,
		uploadStr:"上传",
		statusBarWidth:150,
		dragdropWidth:150,
		uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
		uploadButtonClass: "ajax-file-upload_ext",
		onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
        {
			var sm=data.attachments[0].fileName;
		$("#fileName").val(sm);	
		sm=sm.substring(0,sm.lastIndexOf ('.'));
		$("#sm").val(sm);
		$("#newFileName").val(data.attachments[0].newFileName);
		$("#relativePath").val(data.attachments[0].relativePath);
        },
	}); 
	
	
	/*var len = $("#rotatable tr" ).length;
	if(len<=0){
		addRotatableCol();
	}*/
	
});
function fjzchOnchange(){
	$("[name='fjzch']").off("change");
	$("[name='fjzch']").on('change',function(){
		var bjh=$(this).parent().parent().next().find('div input');
		var bjxlh=$(this).parent().parent().next().next().find('div input');
		bjh.val(""); 
		bjxlh.val(""); 
	
});
}

function changeXXX(){
	//$(this).parent().parent().next().find('div select').attr('name')
	$("[name='zxdx']").off("change");
	$("[name='zxdx']").on('change',function(){
		var apps="";
		
		var bjh=$(this).parent().parent().next().next().find('div input');
		var bjxlh=$(this).parent().parent().next().next().next().find('div input');
		bjh.val("");
		bjxlh.val("");
		var fjzch=$(this).parent().parent().next().find('div select');
		var fjzchValue = fjzch.val();
	if($(this).val()=="ZJJ"){
	
			fjzch.attr({"disabled":false});
			AjaxUtil.ajax({
				type : 'post',
				cache : false,
				url : basePath+"/project/workorder/getPlaneDatas",
				data: { 'fjjx':$('#jx').val(),'dprtcode':$('#technicalFileDprtcode').val()},
				dataType : 'json',
				success : function(data) {
					fjzch.empty(); 
					
					for ( var i = 0; i < data.length; i++) {
						apps+="<option value=" + StringUtil.escapeStr(data[i].fjzch) + ">"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
					}
					
					fjzch.append("<option value=''>请选择</option>"+apps);
					fjzch.val(fjzchValue);
					$("[name='fjzch']").off("change");
					$("[name='fjzch']").on('change',function(){
						var bjh=$(this).parent().parent().next().find('div input');
						bjh.val(""); 
						var bjxlh=$(this).parent().parent().next().next().find('div input');
						bjxlh.val(""); 
					
						
					});
					
				}
			});
	}else if($(this).val()=="FJ"){
			fjzch.attr({"disabled":false});
			AjaxUtil.ajax({
				type : 'post',
				cache : false,
				url : basePath+"/project/workorder/getPlaneDatas",
				data: { 'fjjx':$('#jx').val(),'dprtcode':$('#technicalFileDprtcode').val()},
				dataType : 'json',
				success : function(data) {
					fjzch.empty(); 
					for ( var i = 0; i < data.length; i++) {
						apps+="<option value=" + StringUtil.escapeStr(data[i].fjzch) + ">"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
					}
					
					fjzch.append("<option value=''>请选择</option>"+apps);//
					fjzch.val(fjzchValue);
					$("[name='fjzch']").off("change");
					$("[name='fjzch']").on('change',function(){
						var bjh=$(this).parent().parent().next().find('div input');
						bjh.val(""); 
						var bjxlh=$(this).parent().parent().next().next().find('div input');
						bjxlh.val(""); 
					
						
					});
				}
			});
			
			
		
		}else if($(this).val()=="FZJJ"){
			fjzch.empty(); 
			fjzch.attr({"disabled":"disabled"});
		}

	});
}


function initFixedWorkContent(){
	var list1='';
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/technicalfile/queryListByDjxmbhid",
		type:"post",
		data:{djxmid:$("#id").val()},
		dataType:"json",
		success:function(data){
			
			 for (var i = 0; i < data.length; i++) {
				 list1+=data[i].id+',';
			 }
			$("#list1").val(list1);
			
			initTableCol(data);
			
		}
	});
}
//初始化表格
function initTableCol(data){
	
	
	//先移除暂无数据一行
	$("#rotatable").empty();
	
	if(JSON.stringify(data) == '[]'){
	//	$("#rotatable").append("<tr><td  colspan='12'>暂无数据 No data.</td></tr>");
		return;
	}
	
	
	$.each(data,function(i,workObj){
		 var fjzchOption="";
		 var bjhOption="";
		 var xlhOption="";
		 var zxflOption="";
		 //回显执行分类
		 if(DicAndEnumUtil.data.dicMap!=undefined ){
			 var dicMap = DicAndEnumUtil.data.dicMap!=undefined?DicAndEnumUtil.data.dicMap:{};
			 var items = dicMap[3]!=undefined?dicMap[3]:[];
			 var len = items.length;
			// $('#rotatable tr:last td select:first').empty(); 
			// var zxdx=$('#rotatable tr:last td select:first');
			
			 for(var i=0;i<len;i++){
					 if(items[i].id==workObj.zxfl){
						 zxflOption+="<option value="+items[i].id+" selected='selected'>"+StringUtil.escapeStr(items[i].name)+"</option>";
					 }else{
						 zxflOption+="<option value="+items[i].id+" >"+StringUtil.escapeStr(items[i].name)+"</option>";
					 }
			 }
			 zxflOption= "<option value=''>请选择</option>"+zxflOption;
		 }
		 if(workObj.zxfl!="FZJJ"){
			 //回显飞机注册号
			 AjaxUtil.ajax({
				 type : 'post',
				 cache : false,
				 url : basePath+"/project/workorder/getPlaneDatas",
				 data: { 'fjjx':$('#jx').val(),'dprtcode':$('#technicalFileDprtcode').val()},
				 dataType : 'json',
				 async:false,
				 success : function(data) {
					 for ( var i = 0; i < data.length; i++) {
						 if(data[i].fjzch==workObj.fjzch){
							 fjzchOption+="<option value=" + StringUtil.escapeStr(data[i].fjzch) + " selected='selected' >"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
						 }else{
							 fjzchOption+="<option value=" + StringUtil.escapeStr(data[i].fjzch) + ">"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
						 }
					 }
					 fjzchOption= "<option value=''>请选择</option>"+fjzchOption;
				 }
			 }); 
		 }
		
	//alert(JSON.stringify(workObj));
	 workObj.zxflOption = zxflOption;
	 workObj.fjzchOption = fjzchOption;
	/* work0bj.jhgsRs=workObj.jhgsRs;
	 work0bj.jhgsXss=workObj.jhgsXss;*/
	 
	 addRow(workObj);
	});
	
}

//向table新增一行
function addRotatableCol(){
	
	//先移除暂无数据一行
	var len = $("#rotatable").length;
	if (len == 1) {
		if($("#rotatable").find("td").length == 1){
			$("#rotatable").empty();
		}
	}
	
	//角色，下拉框数据集
	var roleOptionzt ="";
	    roleOptionzt ="<option value=''>请选择</option><option value='1'>有效</option><option value='0'>无效</option>";
	
	var obj = {};
	obj.id = "";
	obj.nbxh = "";
	obj.zjh = "";
	obj.wz = "";
	obj.scms = "";
	obj.cksc = "";
	obj.ckgk = "";
	obj.isBj = 0;
	obj.isMi = 0;
	obj.bz  = "";
	obj.ztOptionzt  = roleOptionzt;
	
	addRow(obj);
	changeXXX();
/*	 if(DicAndEnumUtil.data.dicMap!=undefined ){
		 var dicMap = DicAndEnumUtil.data.dicMap!=undefined?DicAndEnumUtil.data.dicMap:{};
		 var items = dicMap[3]!=undefined?dicMap[3]:[];
		 var len = items.length;
		 var zxdx=$('#rotatable tr:last td select:first');
		 var appd="";
	
		 for(var i=0;i<len;i++){
				 appd+="<option value="+items[i].id+" >"+StringUtil.escapeStr(items[i].name)+"</option>";
			 
		 }
		 zxdx.append("<option value=''>请选择</option>"+appd);
	 }*/
}
function a(){
	alert("s");
}
//向table新增一行
function addRow(obj){
	no++;
	var tr = "";
		
		tr += "<tr>";
		
		tr += "<td>";
			tr += "<button class='line6' onclick='removeCol(event)'><i class='icon-minus color-blue cursor-pointer' ></i></button>";
			/*tr += "<a href=javascript:'removeCol(event);  ><button><i class='icon-minus color-blue cursor-pointer'></i></button></a>";*/
		tr += "</td>";
		
		tr += "<td><input id='syxid' name='syxid' value='"+obj.id+"' type='hidden'/>";
		tr += "	<div>";
		tr += "		<select class='form-control' id='zxdx' name='zxdx' >";
		tr +="<option value='' >please choose</option>";
		if(obj.zxfl=="FJ"){
			tr +="<option value='FJ' selected='selected' >机身</option>";
		}else{
			tr +="<option value='FJ'>机身</option>";
		}
		if(obj.zxfl=="ZJJ"){
			tr +="<option value='ZJJ' selected='selected' >飞机部件</option>";
		}else{
			tr +="<option value='ZJJ' >飞机部件</option>";
		}
		if(obj.zxfl=="FZJJ"){
			tr +="<option value='FZJJ' selected='selected' >非装机件</option>";
		}else{
			tr +="<option value='FZJJ' >非装机件</option>";
		}
		tr += "		</select>";
		tr += "	</div>";
		tr += "</td>";
		if(obj.zxfl=="FZJJ"){
			tr += "<td>";
			tr += "	<div>";
			tr += "		<select class='form-control' name='fjzch' disabled='disabled'>";
			tr += "		</select>";
			tr += "	</div>";
			tr += "</td>";
		}else{
			tr += "<td>";
			tr += "	<div>";
			tr += "		<select class='form-control' name='fjzch'>";
			tr += obj.fjzchOption;
			tr += "		</select>";
			tr += "	</div>";
			tr += "</td>";
		}
		
		tr += "<td>";
		tr += "	<div class='input-group'>";
		tr +="<span class='input-group-btn'><button class='btn btn-primary' onclick='chooseFl(this)'><i class='icon-search' ></i></button></span>";
		tr += "<input disabled='disabled' type='text' class='form-control'  id='bjh' name='bjh'  value="+StringUtil.escapeStr(formatUndefine(obj.bjh))+">"
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div> ";
		tr += "<input disabled='disabled' type='text' class='form-control'  id='xlh' name='xlh' value="+StringUtil.escapeStr(formatUndefine(obj.bjxlh))+">"
		tr += "<input type='hidden' class='form-control'  id='zjqdid' name='zjqdid' value="+formatUndefine(obj.zjqdid)+" >"
		tr += "	</div>";
		tr += "</td>";
		
		
		if(obj.jhgsXss==undefined){
			tr += "<td>";
			tr += "	 <div class='col-xs-3 padding-left-8 padding-right-0' >";
			tr += "<input maxlength='9'  type='text' class='form-control'  id='jhgsRs' name='jhgsRs"+no+"' onkeyup='clearNoNum1(this,"+no+")' value="+formatUndefine(obj.jhgsRs)+">"
			tr += "	</div>";
			tr += " <div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsRs> 人　x　</div>";
			tr += "<div class='col-xs-3 padding-left-0 padding-right-0' >";
			tr += "<input maxlength='9'  type='text' class='form-control' id='jhgsXss' name='jhgsXss"+no+"' onkeyup='clearNoNum(this,"+no+")' value="+formatUndefine(obj.jhgsXss)+">";
			tr += "</div> ";
			tr += "<div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsXss>时　=";
			tr += "<span id='time' name='time"+no+"'></span>";
			tr += "</div>";
			tr += "</td>";
			tr += "</tr>";
		}else{
		tr += "<td>";
		tr += "	 <div class='col-xs-3 padding-left-8 padding-right-0' >";
		tr += "<input maxlength='9'  type='text' class='form-control'  id='jhgsRs' name='jhgsRs"+no+"' onkeyup='clearNoNum1(this,"+no+")' value="+obj.jhgsRs+">"
		tr += "	</div>";
		tr += " <div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsRs> 人　x　</div>";
		tr += "<div class='col-xs-3 padding-left-0 padding-right-0' >";
		tr += "<input maxlength='9'  type='text' class='form-control' id='jhgsXss' name='jhgsXss"+no+"' onkeyup='clearNoNum(this,"+no+")' value="+obj.jhgsXss+">";
		tr += "</div> ";
		tr += "<div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsXss>时　=";
		tr += "<span id='time' name='time"+no+"'>"+(obj.jhgsRs*obj.jhgsXss).toFixed(2)+"</span>";
		tr += "</div>";
		tr += "</td>";
		}
		tr += "</tr>";
		 /*<div class="col-xs-2 padding-left-8 padding-right-0" >
			<input maxlength="9"  type="text" class="form-control "  id="jhgsRs" placeholder='请输入人数...' onkeyup='clearNoNum(this)'>
		</div>
		 <div class="pull-left padding-left-0 padding-right-0" style="padding:6px 0; height:34px; line-height:24px;" > 人  <i class="xy">X</i></div>
		<div class="col-xs-2 padding-left-0 padding-right-0" >
		    <input maxlength="9"  type="text" class="form-control" id="jhgsXss" placeholder='请输入小时数...' onkeyup='clearNoNum(this)'>					    
	     </div> 
	     <div class="pull-left padding-left-0 padding-right-0" style="padding:6px 0; height:34px; line-height:24px;" > 小时<i class="dy">=</i></div>
       <div class="col-xs-2 padding-left-8 padding-right-0" >
		    <input maxlength="9"  type="text" class="form-control "  id="time" readOnly="true">
	     </div> */
		
		
		
	$("#rotatable").append(tr);
	
	//$('#rotatable tr:last td select:first').append("<option value="+3+" >"+'aa'+"</option>")
	

}
function chooseFl(e){
		tr=$(e).parent().parent().parent().parent();
	var zxdx=tr.find('select[name=zxdx]').val();
	var bjh=tr.find('input[name=bjh]');
	var xlh=tr.find('input[name=xlh]');
	 xlhValue=xlh.val();
	if(zxdx=="FZJJ"){
		zxdxFjzch="";
		//非装机件方法的调用
		openZjj();
		
	}else if(zxdx=="FJ"){
		AlertUtil.showErrorMessage("选择机身时，无需选择序列号");
	}else if(zxdx=="ZJJ"){
		var fjzch=tr.find('select[name=fjzch]').val();
			zxdxFjzch=fjzch
		if(fjzch=="" || fjzch==null){
			AlertUtil.showErrorMessage("请先选择飞机注册号");
		}else{
			//装机件方法的调用
			openZjj();
		}
	}else{
		AlertUtil.showErrorMessage("请先选择执行对象");
	}
}
//装机件方法的调用
function openZjj(){
	goPageBjh(1,"auto","desc");
	$("#bjhModal").modal("show");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageBjh(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearchBjh(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearchBjh(pageNumber,sortType,sequence){
	var obj={};
		obj.dprtcode=$("#technicalFileDprtcode").val();
		obj.keyword=$.trim($("#keyword_search_alert").val());
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	var fjzch=zxdxFjzch;
	var url="";
	if(fjzch!=null && fjzch!=""){
		url=basePath+"/project/workorder/getLoadingList";
		ord="jh"
		obj.fjzch=fjzch;
		obj.wz = $.trim($("#wz").val());
		searchBjh(obj,url,ord);
		$("#bjh_pagination").empty();
	}else{
		url=basePath+"/project/workorder/getSpareStore";
		ord="bjh"
		searchBjh(obj,url,ord);
		$("#jh_pagination").empty();
	}
}
function searchBjh(obj,url,ord){
	startWait();
	 AjaxUtil.ajax({
	   url:url,
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtmlBjh(data.rows);
			   if(ord=="bjh"){
				   $("td[name='wzstyle']").each(function(){
					   $(this).css("display","none");
				   })
				   $("th[name='wzstyle']").eq(0).css("display","none");
				   $("#wzsele").css("display","none");
			   }else{
				   $("th[name='wzstyle']").eq(0).css("display","table-cell");
				   $("#wzsele").css("display","block");
			   }
			   new Pagination({
					renderTo : ord+"_pagination",
					data: data,
					sortColumn : ord,
					orderType : "desc",
					goPage:function(a,b,c){
						AjaxGetDatasWithSearchBjh(a,b,c);
					}
				});
			// 标记关键字
			   signByKeyword($("#keyword_search_alert").val(),[2,3,4,5,6,7,8]);
		   } else {
			  $("#userlist").empty();
			  $("#bjh_pagination").empty();
			  $("#jh_pagination").empty();
			   if(ord=="bjh"){
				   $("td[name='wzstyle']").each(function(){
					   $(this).css("display","none");
				   })
				   $("th[name='wzstyle']").eq(0).css("display","none");
				   $("#wzsele").css("display","none");
				   $("#userlist").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
			    }else{
			    	$("th[name='wzstyle']").eq(0).css("display","table-cell");
					$("#wzsele").css("display","block");
			    	$("#userlist").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
			    }
		   }
	    },	   
	});	
}

function appendContentHtmlBjh(list){
	   var htmlContent = '';
	   var sn=xlhValue;
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
		   }
		   if(sn==row.xlh){
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" name='pgd' checked='checked' onclick='chooesRow2(this)' ><input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.jh))+">" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.xlh))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.id))+"'></td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" name='pgd'  onclick='chooesRow2(this)' ><input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.jh))+">" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.xlh))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.id)+"'></td>";
		   }
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zjhms)+"'>"+StringUtil.escapeStr(row.zjhms)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.jh)+"'>"+StringUtil.escapeStr(row.jh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwmc)+"'>"+StringUtil.escapeStr(row.zwmc)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywmc)+"'>"+StringUtil.escapeStr(row.ywmc)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			htmlContent = htmlContent + "<td name='wzstyle' class='text-left' title='"+DicAndEnumUtil.getEnumName('partsPositionEnum',row.wz)+"'>"+DicAndEnumUtil.getEnumName('partsPositionEnum',row.wz)+"</td>";
			htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#userlist").html(htmlContent);
	 
}
//保存部件号和序列号
function getBjhAndXlh(){
	$('input[name=pgd]:checked').each(function(){   
		var jh=$(this).next().val();
		var xlh=$(this).next().next().val();
		var zwmc=$(this).next().next().next().val();
		var ywmc=$(this).next().next().next().next().val();
		var id=$(this).next().next().next().next().next().val();
		
		tr.find('input[name=bjh]').val(jh);
		tr.find('input[name=xlh]').val(xlh);
		var zxdx=tr.find('select[name=zxdx]').val();
		if(zxdx=="ZJJ"){
			tr.find('input[name=zjqdid]').val(id);
		}else{
			tr.find('input[name=zjqdid]').val("");
		}
		//$("#zjqdid").val(id);
	});
}

//按钮选择
function chooesRow2(obj){
	if(obj.type=='radio'){
		var flag = $(obj).is(":checked");
		if(!flag){
			$(obj).attr("checked",true);
		}
	}else{
		var flag = $(obj).is(":checked");
		if(flag){
			$(obj).removeAttr("checked");
		}else{
			$(obj).attr("checked",true);
		}
	}
}
//单选行选
function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='radio']");
	var flag = obj.is(":checked");
	if(!flag){
		obj.attr("checked",true);
	}
}

//移除一行
function removeCol(e){
	$(e.target).parents("tr").remove();
	var len = $("#rotatable").find("tr").length;
	if(len < 1){
		//$("#rotatable").append("<tr><td  colspan='12'>暂无数据 No data.</td></tr>");
	}
	changeXXX();
}

//保存评估单
function btnSave(){
	
	var list='';
	var  isjstg=0;
	var  isjszl=0;
	var  iswxfa=0;
	var  isxdmel=0;
	var  isxdgk=0;
	var  isgczl=0;
	var  isfjgd=0;
	var  isflxgd=0;
	var  iszjfj=0;
	var  isqt=0;
	var  syx=$('#syx').val();
	
	if(syx==1){
	if($("#isJstg").attr("checked")){
		 isjstg=1;
	}
	if($("#isJszl").attr("checked")){
		isjszl=1;
	}
	if($("#isWxfa").attr("checked")){
		iswxfa=1;
	}
	if($("#isXdmel").attr("checked")){
		isxdmel=1;
	}
	if($("#isXdgk").attr("checked")){
		isxdgk=1;
	}
	if($("#isGczl").attr("checked")){
		isgczl=1;
	}
	if($("#isFjgd").attr("checked")){
		isfjgd=1;
	}
	if($("#isFlxgd").attr("checked")){
		isflxgd=1;
	}
	if($("#isZjfj").attr("checked")){
		iszjfj=1;
	}
	if($("#isQt").attr("checked")){
		isqt=1;
	}
	var syxdx=$.trim($('#syxdx').val());
	
	if($.trim($('#syx').val())==0){
		  isjstg=0;
		  isjszl=0;
		  iswxfa=0;
		  isxdmel=0;
		  isxdgk=0;
		  isxdmel=0;
		  isxdgk=0;
		  isgczl=0;
		  isfjgd=0;
		  isflxgd=0;
		  iszjfj=0;
		  isqt=0;
		  $('#qtMs').val("");
	}
	
	

	
	var workContentParam = [];
	var len = $("#rotatable").find("tr").length;
/*	if (len == 1) {
		if($("#rotatable").find("td").length ==1){
			return workContentParam;
		}
	}*/
	var fola=true;
	if (len > 0){
		
		
		$("#rotatable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var syxid =$("input[name='syxid']").eq(index).val(); //当前行，隐藏id的值
			var zxfl =$("select[name='zxdx']").eq(index).val(); //执行对象
			var fjzch =$("select[name='fjzch']").eq(index).val(); //飞机注册号
			var bjh =$("input[name='bjh']").eq(index).val(); //部件号
			var bjxlh =$("input[name='xlh']").eq(index).val(); //序列号
			var zjqdid =$("input[name='zjqdid']").eq(index).val(); //装机清单id
			var jhgsRs =$("input[id='jhgsRs']").eq(index).val(); //人数
			var jhgsXss =$("input[id='jhgsXss']").eq(index).val(); //小时数
			var folt=true;
			if(zxfl==""||zxfl==null){
				AlertUtil.showErrorMessage("执行对象不能为空");
				fola= false;
				folt=false;
				return false;
			}
			if(zxfl=="FJ"){
				if(fjzch==""||fjzch==null){
					AlertUtil.showErrorMessage("飞机注册号不能为空");
					fola= false;
					folt=false;
					return false;
				}
			}
			
			if(zxfl=="ZJJ" || zxfl=="FZJJ"){
				if(bjh==""||bjh==null){
					AlertUtil.showErrorMessage("选择非装机件或飞机部件时，请选择部件号");
					fola= false;
					folt=false;
					return false;
				}
			}
			if(isNaN(jhgsRs)){
				AlertUtil.showErrorMessage("计划工时只能输入数字");
				fola= false;
				folt=false;
				return false;
			}
			if(isNaN(jhgsXss)){
				AlertUtil.showErrorMessage("计划工时只能输入数字");
				fola= false;
				folt=false;
				return false;
			}
		
			if(folt==false){
				return false;
			}
			list+=syxid+',';
			infos.id  = syxid;
			infos.zxfl  = zxfl;
			infos.fjzch  = fjzch;
			infos.bjh  = bjh;
			infos.bjxlh  = bjxlh;
			infos.zjqdid=zjqdid;
			infos.jhgsRs  = jhgsRs;
			infos.jhgsXss  = jhgsXss;
			workContentParam.push(infos);
		});
		
	}
	if(fola==false){
		return false;
	}
	
	}else{
		var workContentParam=[];
		var syxdx="";
	}
	var fixedCheckItem = {
			id : $.trim( $('#id').val()),//id
			
			fbrq : $.trim( $('#fbrq').val()),//发布日期。yyyymmdd
			dj : $.trim( $('#dj').val()),//等级
			jjcd : $.trim( $('#jjcd').val()),//紧急程度
			wczl : $.trim( $('#wczl').val()),//完成种类：1强制、2建议、3选做
			xgwj : $.trim( $('#xgwj').val()),//相关文件
			gbtj : $.trim( $('#gbtj').val()),//关闭条件
			isMfhc : $.trim( $("input:radio[name='isMfhc']:checked").val()),//是否免费航材：0否、1是
			isZbhc : $.trim( $("input:radio[name='isZbhc']:checked").val()),//是否自备航材：0否、1是
			isTsgj : $.trim( $("input:radio[name='isTsgj']:checked").val()),//是否需特殊工具：0否、1是
			isYxzlph : $.trim( $("input:radio[name='isYxzlph']:checked").val()),//是否影响重量平衡：0否、1是
			fwxjnr : $.trim( $('#fwxjnr').val()),//服务信件内容
			
			
			wjzt :   $.trim($('#wjzt').val()),//文件主题
			sjgz :  $.trim($('#sjgz').val()),// 涉及改装
			wjzy :  $.trim($('#wjzy').val()),// 文件摘要
			syx :  $.trim($('#syx').val()),//适用性
			isJstg :  isjstg,//技术通告
			isJszl : isjszl,// 技术指令
			isWxfa : iswxfa,// 维修方案
			isXdmel : isxdmel,// MEL
			isXdgk : isxdgk,// 工单通知书 
			isGczl: isgczl,//工程指令
			isFjgd : isfjgd,//附加工单
			isFlxgd : isflxgd,//非例行工单
			isZjfj : iszjfj,//转交飞机
			isQt : isqt,//其他
			zt:0,//状态
			pgyj : $.trim($('#pgyj').val()),//机型工程师意见
			qtMs : $.trim($('#qtMs').val()),//其他说明
			syxdx : $.trim($('#syxdx').val()),//受影响对象
			list1:$("#list1").val(),//改变前的集合
			list:list,//改变后的集合
		};
	
	fixedCheckItem.affected = workContentParam;
	startWait();
	
	//对比是否有重复
	var fol=contrast(workContentParam);
	if(fol){
		 AlertUtil.showConfirmMessage("飞机注册号或件号,序列号存在重复数据,是否继续", {callback: function(){
			 saveHT(fixedCheckItem);
		 }});
		 
	}else{
		saveHT(fixedCheckItem);
	}
}
function saveHT(fixedCheckItem){
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/project/technicalfile/modifyModifyFileAssess",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		success:function(data){
				finishWait();//结束Loading
			if (data.state == "Success") {
				AlertUtil.showMessage('保存成功!','/project/technicalfile/mainassess?id='+fixedCheckItem.id+'&pageParam='+pageParam);
			} else {
				AlertUtil.showErrorMessage(data.text);
			}
		}
	});
}
//提交评估单
function btnSave1(){
	var list='';
	var  isjstg=0;
	var  isjszl=0;
	var  iswxfa=0;
	var  isxdmel=0;
	var  isxdgk=0;
	var  isgczl=0;
	var  isfjgd=0;
	var  isflxgd=0;
	var  iszjfj=0;
	var  isqt=0;
	var syx=$('#syx').val();
		
	if(syx==1){
		
	if($("#isJstg").attr("checked")){
		 isjstg=1;
	}
	if($("#isJszl").attr("checked")){
		isjszl=1;
	}
	if($("#isWxfa").attr("checked")){
		iswxfa=1;
	}
	if($("#isXdmel").attr("checked")){
		isxdmel=1;
	}
	if($("#isXdgk").attr("checked")){
		isxdgk=1;
	}
	if($("#isGczl").attr("checked")){
		isgczl=1;
	}
	if($("#isFjgd").attr("checked")){
		isfjgd=1;
	}
	if($("#isFlxgd").attr("checked")){
		isflxgd=1;
	}
	if($("#isZjfj").attr("checked")){
		iszjfj=1;
	}
	if($("#isQt").attr("checked")){
		isqt=1;
	}
	var syxdx=$.trim($('#syxdx').val());
	
	if($.trim($('#syx').val())==0){
		  isjstg=0;
		  isjszl=0;
		  iswxfa=0;
		  isxdmel=0;
		  isxdgk=0;
		  isgczl=0;
		  isfjgd=0;
		  isflxgd=0;
		  iszjfj=0;
		  isqt=0;
		  $('#qtMs').val("");
	}
	


	
	var workContentParam = [];
	
	var len = $("#rotatable").find("tr").length;
	/*if (len == 1) {
		if($("#rotatable").find("td").length ==1){
			return workContentParam;
		}
	}*/
	var fola=true;
	if (len > 0){
		
		
		$("#rotatable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var syxid =$("input[name='syxid']").eq(index).val(); //当前行，隐藏id的值
			var zxfl =$("select[name='zxdx']").eq(index).val(); //执行对象
			var fjzch =$("select[name='fjzch']").eq(index).val(); //飞机注册号
			var bjh =$("input[name='bjh']").eq(index).val(); //部件号
			var bjxlh =$("input[name='xlh']").eq(index).val(); //序列号
			var zjqdid =$("input[name='zjqdid']").eq(index).val(); //装机清单id
			var jhgsRs =$("input[id='jhgsRs']").eq(index).val(); //人数
			var jhgsXss =$("input[id='jhgsXss']").eq(index).val(); //小时数
			var folt=true;
			if(zxfl==""||zxfl==null){
				AlertUtil.showErrorMessage("执行对象不能为空");
				fola= false;
				folt=false;
				return false;
			}
			if(zxfl=="FJ"){
				if(fjzch==""||fjzch==null){
					AlertUtil.showErrorMessage("飞机注册号不能为空");
					fola= false;
					folt=false;
					return false;
				}
			}
			
			if(zxfl=="ZJJ" || zxfl=="FZJJ"){
				if(bjh==""||bjh==null){
					AlertUtil.showErrorMessage("选择非装机件或飞机部件时，请选择部件号");
					fola= false;
					folt=false;
					return false;
				}
			}
		
			if(folt==false){
				return false;
			}
			list+=syxid+',';
			infos.id  = syxid;
			infos.zxfl  = zxfl;
			infos.fjzch  = fjzch;
			infos.bjh  = bjh;
			infos.bjxlh  = bjxlh;
			infos.zjqdid=zjqdid;
			infos.jhgsRs  = jhgsRs;
			infos.jhgsXss  = jhgsXss;
			workContentParam.push(infos);
		});
		
	}
	if(fola==false){
		return false;
	}
	}else{
		var workContentParam = [];
		var syxdx="";
	}
	var fixedCheckItem = {
			id : $.trim( $('#id').val()),//id
			
			fbrq : $.trim( $('#fbrq').val()),//发布日期。yyyymmdd
			dj : $.trim( $('#dj').val()),//等级
			jjcd : $.trim( $('#jjcd').val()),//紧急程度
			wczl : $.trim( $('#wczl').val()),//完成种类：1强制、2建议、3选做
			xgwj : $.trim( $('#xgwj').val()),//相关文件
			gbtj : $.trim( $('#gbtj').val()),//关闭条件
			isMfhc : $.trim( $("input:radio[name='isMfhc']:checked").val()),//是否免费航材：0否、1是
			isZbhc : $.trim( $("input:radio[name='isZbhc']:checked").val()),//是否自备航材：0否、1是
			isTsgj : $.trim( $("input:radio[name='isTsgj']:checked").val()),//是否需特殊工具：0否、1是
			isYxzlph : $.trim( $("input:radio[name='isYxzlph']:checked").val()),//是否影响重量平衡：0否、1是
			fwxjnr : $.trim( $('#fwxjnr').val()),//服务信件内容
			
			
			
			wjzt :   $.trim($('#wjzt').val()),//文件主题
			sjgz :  $.trim($('#sjgz').val()),// 涉及改装
			wjzy :  $.trim($('#wjzy').val()),// 文件摘要
			syx :  $.trim($('#syx').val()),//适用性
			isJstg :  isjstg,//技术通告
			isJszl : isjszl,// 技术指令
			isWxfa : iswxfa,// 维修方案
			isXdmel : isxdmel,// MEL
			isXdgk : isxdgk,// 工单通知书 
			isGczl: isgczl,//工程指令
			isFjgd : isfjgd,//附加工单
			isFlxgd : isflxgd,//非例行工单
			isZjfj : iszjfj,//转交飞机
			isQt : isqt,//其他
			zt:1,//状态
			pgyj : $.trim($('#pgyj').val()),//机型工程师意见
			qtMs : $.trim($('#qtMs').val()),//其他说明
			syxdx : syxdx,//受影响对象
			list1:$("#list1").val(),//改变前的集合
			list:list,//改变后的集合
		};
	
	
	fixedCheckItem.affected = workContentParam;
	
	startWait();
	//对比是否有重复
	var fol=contrast(workContentParam);
	if(fol){
		 AlertUtil.showConfirmMessage("飞机注册号或件号,序列号存在重复数据,是否继续", {callback: function(){
			 submitHT(fixedCheckItem);
		 }});
		 
	}else{
		submitHT(fixedCheckItem);
	}

}

function submitHT(fixedCheckItem){
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/project/technicalfile/modifyModifyFileAssesssub",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		success:function(data){
				finishWait();//结束Loading
			if (data.state == "Success") {
				AlertUtil.showMessage('提交成功!','/project/technicalfile/mainassess?id='+fixedCheckItem.id+'&pageParam='+pageParam);
			} else {
				AlertUtil.showErrorMessage(data.text);
			}
		}
	});
}

function showOrHides(val) {
	if($("#isQt").attr("checked")){
		$("#qtMs").show();
	}else{
		$("#qtMs").hide();
	}
	
}

function showOrHide(val) {
	if (val == "0") {
		$("#xfwj").hide();
		$("#yxdxList").hide();
		$("#syxdxms").hide();
		
	} else {
		$("#xfwj").show();
		$("#yxdxList").show();
		$("#syxdxms").show();
	}
}
//只能输入数字和小数
function clearNoNum(obj,no){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     sumTotal(no);
}
//只能输入数字
function clearNoNum1(obj,no){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d]/g,'');
     sumTotal(no);
}
function sumTotal(no){
	var jhgsRs = $("[name='jhgsRs"+no+"'] ").val();
	var jhgsXss = $("[name='jhgsXss"+no+"'] ").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("[name='time"+no+"'] ").html((jhgsRs*jhgsXss).toFixed(2));
	}
}
//附件下载
function downfile(id){
	//window.open(basePath + "/project/technicalfile/downfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.B_G_00101)
}
function back(){
	window.location.href =basePath+"/project/technicalfile/mainassess?pageParam="+pageParam;
}
function contrast(zxddList){
	var fol=false;
	var len=zxddList.length;
	for(var i=0;i<len;i++){
		for(var a=i+1;a<len;a++){
			if(zxddList[i].fjzch==zxddList[a].fjzch && zxddList[i].zxfl!="FZJJ"  && zxddList[a].zxfl!="FZJJ"){
				if(zxddList[i].zxfl==zxddList[a].zxfl ){
					if(zxddList[i].zxfl=="ZJJ"){
						if(zxddList[i].bjh==zxddList[a].bjh){
							if(zxddList[i].bjxlh==zxddList[a].bjxlh){
								fol=true;
							}
						}
					}else{
						fol=true;
					}
					
				}
			}
			if(zxddList[i].bjh==zxddList[a].bjh && zxddList[i].bjh!=null && zxddList[a].bjh!=null && zxddList[i].bjh!="" && zxddList[a].bjh!=""){
				if(zxddList[i].bjxlh==zxddList[a].bjxlh){
					fol=true;
				}
			}
		}
	}
	return fol;
}