var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var no=0;
//时间控件加载
$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});

$(document).ready(function(){
	
	initFixedWorkContent();
	
	//数据字典
	var dprtcode=$("#technicalFileDprtcode").val();
/*	DicAndEnumUtil.registerDic('7', 'fl',dprtcode);
	DicAndEnumUtil.registerDic('16','wjlx',dprtcode);
	DicAndEnumUtil.registerDic('8','ly',dprtcode);*/
	//导航
	//Navigation(menuCode,"技术文件评估","Evaluation");

	
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
	} */
	
	//初始化日志功能
	logModal.init({code:'B_G_001',id:$('#technicalFileId').val()});
	
});


function changeXXX(){
	//$(this).parent().parent().next().find('div select').attr('name')
	$("[name='zxdx']").off("change");
	$("[name='zxdx']").on('change',function(){
		var apps="";
		
	if($(this).val()=="ZJJ"){
	
		var fjzch=$(this).parent().parent().next().find('div select');
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
				}
			});
	}else if($(this).val()=="FJ"){
			var fjzch=$(this).parent().parent().next().find('div select');
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
			var fjzch=$(this).parent().parent().next().find('div select');
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
		
	 workObj.zxflOption = zxflOption;
	 workObj.fjzchOption = fjzchOption;
	 
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
//向table新增一行
function addRow(obj){
	no++;
	var tr = "";
		
		tr += "<tr>";
		
		/*tr += "<td>";
			tr += "<button class='line6' onclick='removeCol(event)'><i class='icon-minus color-blue cursor-pointer' ></i></button>";
			tr += "<a href=javascript:'removeCol(event);  ><button><i class='icon-minus color-blue cursor-pointer'></i></button></a>";
		tr += "</td>";*/
		
		tr += "<td><input id='syxid' name='syxid' value='"+obj.id+"' type='hidden'/>";
		tr += "	<div>";
		tr += "		<select class='form-control' id='zxdx' name='zxdx' disabled='disabled'>";
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
			tr += "		<select class='form-control' name='fjzch' disabled='disabled'>";
			tr += obj.fjzchOption;
			tr += "		</select>";
			tr += "	</div>";
			tr += "</td>";
		}
		
		tr += "<td>";
		tr += "	<div>";
		tr += "<input disabled='disabled' type='text' class='form-control'  id='bjh' name='bjh' value="+StringUtil.escapeStr(formatUndefine(obj.bjh))+">"
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
			tr += "<input maxlength='9'  type='text' class='form-control'  id='jhgsRs' name='jhgsRs"+no+"' onkeyup='clearNoNum(this,"+no+")' disabled='disabled' value="+formatUndefine(obj.jhgsRs)+">"
			tr += "	</div>";
			tr += " <div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsRs> 人　x　</div>";
			tr += "<div class='col-xs-3 padding-left-0 padding-right-0' >";
			tr += "<input maxlength='9'  type='text' class='form-control' id='jhgsXss' name='jhgsXss"+no+"' onkeyup='clearNoNum(this,"+no+")' disabled='disabled' value="+formatUndefine(obj.jhgsXss)+">";
			tr += "</div> ";
			tr += "<div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsXss>时　=";
			tr += "<span id='time' name='time"+no+"'></span>";
			tr += "</div>";
			tr += "</td>";
			tr += "</tr>";
		}else{
		tr += "<td>";
		tr += "	 <div class='col-xs-3 padding-left-8 padding-right-0' >";
		tr += "<input maxlength='9'  type='text' class='form-control'  id='jhgsRs' name='jhgsRs"+no+"' onkeyup='clearNoNum(this,"+no+")' disabled='disabled' value="+obj.jhgsRs+">"
		tr += "	</div>";
		tr += " <div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsRs> 人　x　</div>";
		tr += "<div class='col-xs-3 padding-left-0 padding-right-0' >";
		tr += "<input maxlength='9'  type='text' class='form-control' id='jhgsXss' name='jhgsXss"+no+"' onkeyup='clearNoNum(this,"+no+")' disabled='disabled' value="+obj.jhgsXss+">";
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

//移除一行
function removeCol(e){
	$(e.target).parent().parent().remove();
	var len = $("#rotatable").find("tr").length;
	if(len < 1){
		//$("#rotatable").append("<tr><td  colspan='12'>暂无数据 No data.</td></tr>");
	}
	changeXXX();
}

//通过评估单
function btnSave(){
	
	var fixedCheckItem = {
			id : $.trim( $('#id').val()),//id
			shyj : $.trim($('#shyj').val())//审核意见
		};
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/project/technicalfile/modifyModifyFileAudit",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		success:function(data){
				finishWait();//结束Loading
			if (data.state == "Success") {
				AlertUtil.showMessage('审核通过成功!','/project/technicalfile/mainaudit');
			} else {
				AlertUtil.showErrorMessage(data.text);
			}
		}
	});
	}

//驳回评估单
function btnSave1(){
	var fixedCheckItem = {
			id : $.trim( $('#id').val()),//id
			shyj : $.trim($('#shyj').val())//审核意见
		};
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/project/technicalfile/modifyModifyFileAuditBh",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		success:function(data){
			finishWait();//结束Loading
			if (data.state == "Success") {
				AlertUtil.showMessage('审核驳回成功!','/project/technicalfile/mainaudit');
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
function sumTotal(no){
	var jhgsRs = $("[name='jhgsRs"+no+"'] ").val();
	var jhgsXss = $("[name='jhgsXss"+no+"'] ").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("[name='time"+no+"'] ").val((jhgsRs*jhgsXss).toFixed(2));
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

