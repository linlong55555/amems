var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var no1=0;
var pgdids=[];
var nox=0;
var oldzxdxList=[];
$(function() {
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	/*initFixedWorkContent();*/
	/*addRotatableCol();*/
	
	//加载已选择的技术评估单
	selectChoosePgd();
	//初始化工作内容
	initFixedWorkContent();
	
	changeXXX();
	initChapter();
	//生成多选下拉框动
	$('#eolx').multiselect({
		buttonClass: 'btn btn-default',
	    buttonWidth: 'auto',
	    numberDisplayed:20,
	    includeSelectAllOption: true,
	    onChange:function(element,checked){
	    }
	});
	var engineeringEolx=$("#engineeringEolx").val();
	var Eolx=[];
	for(var i=0;i<engineeringEolx.length;i++){
		Eolx.push(engineeringEolx[i]);
	}
	$("#eolx").multiselect('select', Eolx);
	//回显总计
	amount();
	//初始化日志功能
	if($("#jg").val()=="1"){
		logModal.init({code:'B_G_009',id:$('#engineeringId').val()});
	}
});

function judge(id){
	nox=id;
	var $wcrq=$('#wcrq'+id);
	$wcrq.removeAttr("readonly");
	$('#wcrq'+id).removeAttr("disabled","true");
	var wcrq=$wcrq.val();
	
	var $jssj=$('#jssj'+id);
	$jssj.removeAttr("readonly");
	var jssj=$('#jssj'+id).val();
	
	var $qlxh=$('#qlxh'+id);
	$qlxh.removeAttr("readonly");
	var qlxh=$('#qlxh'+id).val();
	
	var $jcsj=$('#jcsj'+id);
	$jcsj.removeAttr("readonly");
	var jcsj=$('#jcsj'+id).val();
	
	var $jcxh=$('#jcxh'+id);
	$jcxh.removeAttr("readonly");
	var jcxh=$('#jcxh'+id).val();
	
	var num=0;
	if(wcrq!=null && wcrq!=""){
		num++;
	}
	if(jssj!=null && jssj!=""){
		num++;
	}
	if(qlxh!=null && qlxh!=""){
		num++;
	}
	if(jcsj!=null && jcsj!=""){
		num++;
	}
	if(jcxh!=null && jcxh!=""){
		num++;
	}
	if(num>=3){
		if(wcrq == ""){
			$wcrq.attr("readonly","true");
			$wcrq.attr("disabled","true");
		}
		if(jssj == ""){
			$jssj.attr("readOnly","true");
		}
		if(qlxh == ""){
			$qlxh.attr("readOnly","true");
		}
		if(jcsj == ""){
			$jcsj.attr("readOnly","true");
		}
		if(jcxh == ""){
			$jcxh.attr("readOnly","true");
		}
	}
	
}
//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	judge(nox);
};

function initFixedWorkContent(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/engineering/queryListBymainid",
		type:"post",
		data:{mainid:$("#engineeringId").val()},
		dataType:"json",
		success:function(data){
			initTableCol(data);
		}
	});
}
//初始化表格
function initTableCol(data){
	
	//$.extend(true, oldzxdxList, data);
	$.each(data,function(i,workObj){
		oldzxdxList.push(workObj.id);
		 var fjzchOption="";
		 var bjhOption="";
		 var xlhOption="";
		 var zxflOption="";
		 var bjname="";
		 var wcrq="";
		 var jssj="";
		 var qlxh="";
		 var jcsj="";
		 var jcxh="";
		 
		 //回显执行分类
		 if(DicAndEnumUtil.data.dicMap!=undefined ){
			 var dicMap = DicAndEnumUtil.data.dicMap!=undefined?DicAndEnumUtil.data.dicMap:{};
			 var items = dicMap[3]!=undefined?dicMap[3]:[];
			 var len = items.length;
			// $('#rotatable tr:last td select:first').empty(); 
			// var zxdx=$('#rotatable tr:last td select:first');
			 for(var i=0;i<len;i++){
					 if(items[i].id==workObj.zxfl){
						 zxflOption+="<option value="+items[i].id+" selected='selected'>"+items[i].name+"</option>";
					 }else{
						 zxflOption+="<option value="+items[i].id+" >"+items[i].name+"</option>";
					 }
			 }
		 }
		if(workObj.zxfl !="FZJJ"){
			var jx=$('#jx').val();
			bjhOption="";
			xlhOption="";
			 //回显飞机注册号
			AjaxUtil.ajax({
					type : 'post',
					cache : false,
					url : basePath+"/project/workorder/getPlaneDatas",
					data: { 'fjjx':$('#jx').val(),'dprtcode':$('#engineeringDprtcode').val()},
					dataType : 'json',
					async:false,
					success : function(data) {
						for ( var i = 0; i < data.length; i++) {
							 if(data[i].fjzch==workObj.fjzch){
								 fjzchOption+="<option value=" + data[i].fjzch + " selected='selected' >"+ data[i].fjzch + "</option>";
							 }else{
								 fjzchOption+="<option value=" + data[i].fjzch + ">"+ data[i].fjzch + "</option>";
							 }
						}
						 fjzchOption= "<option value=''>please choose</option>"+fjzchOption;
					}
			}); 
		}
			if(workObj.jkzF!=null && workObj.jkzF!=""){
				if(workObj.jkxmbhF=="calendar"){
					wcrq=workObj.jkzF;
				}
				if(workObj.jkxmbhF=="fuselage_flight_time"){
					jssj=workObj.jkzF;
				}
				if(workObj.jkxmbhF=="landing_gear_cycle"){
					qlxh=workObj.jkzF;
				}
				if(workObj.jkxmbhF=="winch_time"){
					jcsj=workObj.jkzF;
				}
				if(workObj.jkxmbhF=="winch_cycle"){
					jcxh=workObj.jkzF;
				}
			}
				
			if(workObj.jkzS!=null && workObj.jkzS!=""){
				if(workObj.jkxmbhS=="calendar"){
					wcrq=workObj.jkzS;
				}
				if(workObj.jkxmbhS=="fuselage_flight_time"){
					jssj=workObj.jkzS;
				}
				if(workObj.jkxmbhS=="landing_gear_cycle"){
					qlxh=workObj.jkzS;
				}
				if(workObj.jkxmbhS=="winch_time"){
					jcsj=workObj.jkzS;
				}
				if(workObj.jkxmbhS=="winch_cycle"){
					jcxh=workObj.jkzS;
				}
				
				
			}
			if(workObj.jkzT!=null && workObj.jkzT!=""){
				if(workObj.jkxmbhT=="calendar"){
					wcrq=workObj.jkzT;
				}
				if(workObj.jkxmbhT=="fuselage_flight_time"){
					jssj=workObj.jkzT;
				}
				if(workObj.jkxmbhT=="landing_gear_cycle"){
					qlxh=workObj.jkzT;
				}
				if(workObj.jkxmbhT=="winch_time"){
					jcsj=workObj.jkzT;
				}
				if(workObj.jkxmbhT=="winch_cycle"){
					jcxh=workObj.jkzT;
				}
			}
		 
	 workObj.zxflOption = zxflOption;
	 workObj.fjzchOption = fjzchOption;
	 workObj.bjname = bjname;
	 workObj.wcrq=wcrq;
	 workObj.jssj=jssj;
	 workObj.qlxh=qlxh;
	 workObj.jcsj=jcsj;
	 workObj.jcxh=jcxh;
	 //alert(JSON.stringify(workObj));
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
		};
	}
	
	//角色，下拉框数据集
	var roleOptionzt ="";
	    roleOptionzt ="<option value=''>please choose</option><option value='1'>有效</option><option value='0'>无效</option>";
	var sj="";
		sj="data-date-format='yyyy-mm-dd'";
	var obj = {};
	obj.sj=sj;
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
	obj.bjname = "";
	obj.wcrq="";
	obj.jssj="";
	obj.qlxh="";
	obj.jcsj="";
	obj.jcxh="";
	
	addRow(obj);
	/*if(DicAndEnumUtil.data.dicMap!=undefined ){
		 
		 var dicMap = DicAndEnumUtil.data.dicMap!=undefined?DicAndEnumUtil.data.dicMap:{};
		 var items = dicMap[3]!=undefined?dicMap[3]:[];
		 var len = items.length;
		
		 var zxdx=$('#zxdxList tr:last td select:first');
		 var appd="";
		 for(var i=0;i<len;i++){
				 appd+="<option value="+items[i].id+" >"+StringUtil.escapeStr(items[i].name)+"</option>";
		 }
		 zxdx.append("<option value=''>please choose</option>"+appd);
	 }*/
	changeXXX();
}



//向table新增一行
function addRow(obj){
		no1++;
	var no=no1;
	
	var tr = "";
		
		tr += "<tr>";
		
		/*tr += "<td>";
			tr += "&nbsp;&nbsp;<button onclick='removeCol(event)'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button>";
		tr += "</td>";*/
		
		tr += "<td><input id='noum' name='noum' value='"+no+"' type='hidden'/>";
		tr +=  no;
		tr += "<input id='syxid' name='syxid' value='"+obj.id+"' type='hidden'/></td>";
		
		tr += "<td>";
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
		tr += "</td>";
		
		
		tr += "<td>";
		
		tr += "<input id='syxid' name='syxid' class='form-control' value='"+StringUtil.escapeStr(formatUndefine(obj.fjzch))+"' type='text' disabled='disabled' />";
	/*	tr += "		<select class='form-control' name='fjzch' disabled='disabled'>";
		tr += obj.fjzchOption;
		tr += "		</select>";*/
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control' name='zt' readonly='readonly' onmouseover='this.title=this.value' value='"+StringUtil.escapeStr(formatUndefine(obj.bjms))+"'>";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='input-group'> ";
		tr += "<input readonly='readonly' type='text' class='form-control'  onmouseover='this.title=this.value' id='bjh' name='bjh' style='width:150px;' value='"+StringUtil.escapeStr(formatUndefine(obj.bjh))+"'>"
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "<input readonly='readonly' type='text' class='form-control'  onmouseover='this.title=this.value' id='bjxlh' name='bjxlh' style='width:150px;' value='"+StringUtil.escapeStr(formatUndefine(obj.bjxlh))+"'>"
		tr += "<input type='hidden' class='form-control'  id='zjqdid' name='zjqdid' value="+formatUndefine(obj.zjqdid)+">"
		tr += "	</div>";
		tr += "</td>";
		
		
		if (obj.scgd != null && obj.scgd != "") {
		tr += "<td class='text-left'>";
			var strs = new Array(); // 定义一数组
			strs = obj.scgd.split(","); // 字符分割
			for (i = 0; i < strs.length; i++) {
				
				var gdidAndgdbh = new Array(); // 定义一数组
				gdidAndgdbh = strs[i].split("^");
				var gdid=gdidAndgdbh[0];
				var gdbh=gdidAndgdbh[1];

				if (i == 1) {
					tr = tr
							+ "　<i class='icon-caret-down' id='"
							+ gdid
							+ "icon' onclick=vieworhideWorkContent('"
							+ gdid + "')></i><div id='"
							+ gdid
							+ "' style='display:none'>";
				}

				tr = tr
						+ "<a href=\"javascript:selectByEoid('"
						+ gdid + "')\">" + gdbh + "</a>";
				if (i != 0) {
					tr = tr + "<br>";

				}
				if (i != 0 && i == strs.length - 1) {
					tr = tr + "</div>";
				}

			}
		tr += "</td>";
		}else{
			tr += "<td>";
			tr += "</td>";
		}
		if($('#engineeringZt').val()!=4 && $('#engineeringZt').val()!=8 && $('#engineeringZt').val()!=9 && $('#engineeringZt').val()!=10){
			tr += "<td class='text-center'>";
			tr += "<a href=\"javascript:addEogd('"+ obj.id + "','"+obj.mainid+"')\">添加</a>";
			tr += "</td>";
		}else{
			tr += "<td class='text-center'>";
			tr += "</td>";
		}
		
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input disabled='disabled' type='text' class='form-control datepicker' name='wcrq"+no+"' id='wcrq"+no+"' data-date-format='yyyy-mm-dd'  maxlength='10'  value="+obj.wcrq+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='jssj"+no+"' id='jssj"+no+"' disabled='disabled' value="+obj.jssj+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='qlxh"+no+"' id='qlxh"+no+"' disabled='disabled' value="+obj.qlxh+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='jcsj"+no+"' id='jcsj"+no+"' disabled='disabled' value="+obj.jcsj+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='jcxh"+no+"' id='jcxh"+no+"' disabled='disabled' value="+obj.jcxh+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	 <div class='col-xs-3 padding-left-8 padding-right-0' >";
		tr += "<input maxlength='9'  type='text' class='form-control'  id='jhgsRs"+no+"' name='jhgsRs"+no+"' onkeyup='clearNoNum(this,"+no+")' disabled='disabled' value="+formatUndefine(obj.jhgsRs)+" >"
		tr += "	</div>";
		tr += " <div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsRs> 人x</div>";
		tr += "<div class='col-xs-3 padding-left-8 padding-right-0' >";
		tr += "<input maxlength='9'  type='text' class='form-control' id='jhgsXss"+no+"' name='jhgsXss"+no+"' onkeyup='clearNoNum(this,"+no+")' disabled='disabled' value="+formatUndefine(obj.jhgsXss)+" >";
		tr += "</div> ";
		tr += "<div class='pull-left padding-left-0 padding-right-0' style='padding:6px 0; height:34px; line-height:24px;' value=obj.jhgsXss>时=</div>";
		tr += "<div class='col-xs-3 padding-left-8 padding-right-0' >";
		tr += "<input maxlength='9'  type='text' class='form-control '  id='time' name='time"+no+"' readOnly='true' value="+formatUndefine(obj.jhgsXss*obj.jhgsRs)+">";
		tr += "</div>";
		tr += "</td>";
		
		tr += "</tr>";
	
	$("#zxdxList").append(tr);
	
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	
	
	
}

function changeXXX(){
	//$(this).parent().parent().next().find('div select').attr('name')
	$("[name='zxdx']").off("change");
	$("[name='zxdx']").on('change',function(){
		var apps="";
		var no5=0
	if($(this).val()=="ZJJ"){
		var fjzch=$(this).parent().next().find('select');
			AjaxUtil.ajax({
				type : 'post',
				cache : false,
				url : basePath+"/project/workorder/getPlaneDatas",
				dataType : 'json',
				success : function(data) {
					
					fjzch.empty(); 
					for ( var i = 0; i < data.length; i++) {
						
						apps+="<option value=" + data[i].fjzch + ">"+ data[i].fjzch + "</option>";
					}
					
					fjzch.append("<option value=''>please choose</option>"+apps);//
					
					$("[name='fjzch']").off("change");
					$("[name='fjzch']").on('change',function(){
						var appe="";
						var bjh=$(this).parent().next().find('select');
						
						var fjzch=$(this).val();
						AjaxUtil.ajax({
							type : 'post',
							cache : false,
							url : basePath+"/project/workorder/getLoadingList?fjzch="+$(this).val(),
							dataType : 'json',
							success : function(data) {
								bjh.empty(); 
								var str = '';
								for ( var i = 0; i < data.length; i++) {
									if(str.indexOf(data[i].jh) == -1){
										str += data[i].jh+",";
									appe+="<option value=" + data[i].jh+"-"+data[i].zwmc + ">"+ data[i].jh +"-"+data[i].zwmc+"</option>";
									}
								}
								
								bjh.append("<option value=''>please choose</option>"+appe);//

								$("[name='bjh']").off("change");
								$("[name='bjh']").on('change',function(){
								var appen="";
								var bjxlh=$(this).parent().next().find('select');
								var value=$(this).val();
								var Jh=value.split("-")[0];
								var bjm=value.split("-")[1];
								$(this).parent().next().next().find('input').val(bjm);
								
								
								
									AjaxUtil.ajax({
										type : 'post',
										cache : false,
										url : basePath+"/project/workorder/getLoadingList",
										dataType : 'json',
									   data: { 'fjzch':fjzch,'jh':Jh},
										success : function(data) {
											bjxlh.empty(); 
											
											for ( var i = 0; i < data.length; i++) {
												appen+="<option value='" + StringUtil.escapeStr(data[i].xlh) + "'>"+ StringUtil.escapeStr(data[i].xlh) + "</option>";
											}
											
											bjxlh.append("<option value=''>please choose</option>"+appen);//
											
										}
									});
								
							});
								
								
							}
						});
					
					});
				}
			});
	}else if($(this).val()=="FJ"){
			var fjzch=$(this).parent().next().find('select');
			fjzch.empty();
			var bjh=$(this).parent().next().next().find('select');
			bjh.empty();
			var bjxlh=$(this).parent().next().next().next().find('select');
			bjxlh.empty();
			AjaxUtil.ajax({
				type : 'post',
				cache : false,
				url : basePath+"/project/workorder/getPlaneDatas",
				dataType : 'json',
				success : function(data) {
					fjzch.empty(); 
					for ( var i = 0; i < data.length; i++) {
						apps+="<option value='" + StringUtil.escapeStr(data[i].fjzch) + "'>"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
					}
					fjzch.append("<option value=''>please choose</option>"+apps);
			
					/*$("[name='fjzch']").off("change");
					$("[name='fjzch']").on('change',function(){
					
						var bjh=$(this).parent().next().find('select');
						bjh.empty(); 
						var bjxlh=$(this).parent().next().next().find('select');
						bjxlh.empty(); 
						
					});*/
				}
			});
		
	}else if($(this).val()=="FZJJ"){
		var fjzch=$(this).parent().next().find('select');
		fjzch.empty();
		var bjh=$(this).parent().next().next().find('select');
		bjh.empty();
		var obj1={};
		AjaxUtil.ajax({
			type : 'post',
			cache : false,
			url : basePath+"/project/workorder/getSpareStore",
			data: JSON.stringify(obj1),
			contentType:"application/json;charset=utf-8",
			dataType : 'json',
			success : function(data) {
				var str="";
				for ( var i = 0; i < data.length; i++) {
					if(str.indexOf(data[i].bjh) == -1){
						str += data[i].bjh+",";
						apps+="<option value='"+ StringUtil.escapeStr(data[i].bjh)+"^"+StringUtil.escapeStr(data[i].zwms) + "'>"+ StringUtil.escapeStr(data[i].bjh) +"-"+StringUtil.escapeStr(data[i].ywms)+"</option>";
					}
				}  
				
				bjh.append("<option value=''>please choose</option>"+apps);//
		
				$("[name='bjh']").off("change");
				$("[name='bjh']").on('change',function(){
					var appen="";
					var bjxlh=$(this).parent().next().find('select');
					var value=$(this).val();
					var bjh=value.split("-")[0];
					var zwms=value.split("-")[1];
					$(this).parent().next().next().find('input').val(zwms);
					var obj={'bjh':bjh};
					AjaxUtil.ajax({
						type : 'post',
						cache : false,
						url : basePath+"/project/workorder/getSpareStore",
						contentType:"application/json;charset=utf-8",
						dataType : 'json',
					   data: JSON.stringify(obj),
						success : function(data) {
							bjxlh.empty(); 
							for ( var i = 0; i < data.length; i++) {
								appen+="<option value='" + StringUtil.escapeStr(data[i].sn) + "'>"+ StringUtil.escapeStr(data[i].sn) + "</option>";
							}
							
							bjxlh.append("<option value=''>please choose</option>"+appen);//
							
						}
					});
					
					
					
				});
			}
		});
		
	}

	});
}




//移除一行
function removeCol(e){
	$(e.target).parent().parent().remove();
	/*var len = $("#zxdxList").find("tr").length;
	if(len < 1){
		$("#zxdxList").append("<tr><td  colspan='12'>暂无数据 No data.</td></tr>");
	}*/
}

//打开评估单列表对话框
function openPgd() {
	 goPage(1,"auto","desc");
	 $("#alertModalPgd").modal("show");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var zlid=$('zlid').val();
	if (zlid != null && zlid != "") {
		obj.zlid = zlid;					//指令id
	   	}
	var pgdsId="";
	for(var i=0;i<pgdids.length;i++){
   		//alert(pgdids[i]);
   		pgdsId+= pgdids[i]+",";
   	}
	pgdsId=pgdsId.substring(0,pgdsId.length-1);
	obj.pgdsId=pgdsId;//已选择的评估单id
	obj.isGczl=1;	//技术通告复选为1
	obj.jx=$('#jx').val();//机型
	//var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/engineering/selectPgdList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml(data.rows);
			   appendPaginationHtml(data,sortType,sequence);
		   } else {
			  $("#Pgdlist").empty();
			  $("#pagination").empty();
			  $("#Pgdlist").append("<tr><td colspan=\"11\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
function appendContentHtml(list){
	var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
		   }
		      
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='pgd'><input type='hidden' value="+formatUndefine(row.id)+">" +
																				"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.pgdh))+">" +
																				"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.shzltgh))+">" +
																				"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.wjzt))+">" +
																				"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.ly))+">" +
																				"<input type='hidden' value="+formatUndefine(row.sxrq)+">" +
																				"<input type='hidden' value="+formatUndefine(row.pgqx)+">" +
																				"<input type='hidden' value="+formatUndefine(row.zt)+"></td>";
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"</td>";  
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"</td>";  
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.ly))+"</td>";  
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.fl))+"</td>";  
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"</td>";  
			htmlContent = htmlContent + "<td>"+(formatUndefine(row.sxrq).substring(0,10))+"</td>";  
			htmlContent = htmlContent + "<td>"+(formatUndefine(row.pgqx).substring(0,10))+"</td>";  
			htmlContent = htmlContent + "<td>"+formatUndefine(zts[row.zt])+"</td>";  
			htmlContent = htmlContent + "</tr>";  
		    
	   });
	   //$("#Pgdlist").empty();
	   $("#Pgdlist").html(htmlContent);
	 
}
function appendPaginationHtml(data,sortType,sequence){
	   var viewpagecount = 10;//每页显示12个分页便签
	   var param = data.rows; //页面数据
	   var total = data.total;//总数据量
	   var size =  data.pageable.rows;//每页显示的条目数
	   var pageCount = total % size==0? parseInt(total/size):parseInt(total/size)+1;//总的页数 
	   var currentPage = 1; //记录当前页码 
	   var subpagination ="";
	   
	   for (var i=0;i<pageCount;i++){
		   if (data.pageable.page == (i+1)) {
		  	 currentPage = i + 1;
		  	 break;
		   }
	   }
	   
	   var startpage = currentPage - (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1: viewpagecount / 2);//起始页
	   var endpage = currentPage + viewpagecount / 2; //结束页 
	   if (startpage < 1) {
			startpage = 1;
			if (pageCount >= viewpagecount){
				endpage = viewpagecount;
			}else{
				endpage = pageCount;
			}
		}
	   
	   if (endpage > pageCount) {
			endpage = pageCount;
			if ((endpage - viewpagecount) > 0){
				startpage = endpage - viewpagecount + 1;
			}else{
				startpage = 1;
			}
		}
	   var paginationContent = "";
	   if (currentPage==1) { //当前为第一页时,不能向前翻页
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   } else {
		   paginationContent = paginationContent 
        + "<li onclick=\"goPage(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
        + "<li onclick=\"goPage("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   }
	   
	   for (var index = startpage;index <= endpage;index++){
		   if (index==currentPage){
			   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }else {
			   paginationContent = paginationContent + "<li onclick=\"goPage("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }
	   }
	   if (currentPage ==pageCount){
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }else {
		   paginationContent = paginationContent + "<li onclick=\"goPage("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li onclick=\"goPage("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }
	   $("#pagination").empty();
	   $("#pagination").html(paginationContent);
}

//保存评估单
function appendPgd(){
	var dprtcode=$("#jgdm").val();
	var htmlContent = ""; 
	$('input[name=pgd]:checked').each(function(){   
		var pgdId=$(this).next().val();
		var pgdh=$(this).next().next().val();
		var ckzl=$(this).next().next().next().val();
		var wjzt=$(this).next().next().next().next().val();
		var ly=$(this).next().next().next().next().next().val();
		var sxrq=$(this).next().next().next().next().next().next().val();
		var pgqx=$(this).next().next().next().next().next().next().next().val();
		var pgzt=$(this).next().next().next().next().next().next().next().next().val();
		//alert(wjzt);
		htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+pgdId+"'>";
		htmlContent = htmlContent + "<td><a href=\"javascript:lineRemove('"+pgdId+"')\">删除</a></td>";
		htmlContent = htmlContent + "<td><input type='hidden' name='PgdIdAndPgdh' value='"+pgdId+","+StringUtil.escapeStr(pgdh)+"'><input type='hidden' name='Ckzl' value='"+StringUtil.escapeStr(ckzl)+"'><a href=\"javascript:viewMainTechnicalfile('"+pgdId+"','"+dprtcode+"')\">"+StringUtil.escapeStr(pgdh)+"</a></td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(ly)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(ckzl)+"'>"+StringUtil.escapeStr(ckzl)+"</td>";  
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(sxrq.substring(0,10))+"</td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(username)+" "+StringUtil.escapeStr(realname)+"</td>";
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(pgqx.substring(0,10))+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+zts[pgzt]+"</td>";
		htmlContent = htmlContent + "</tr>";
		pgdids.push(pgdId);
		
		if($('#zhut').val()=="" || $('#zhut').val()==null){
			$('#zhut').val(wjzt);
		}
		  });
		//$("#Annunciatelist").empty();
	    $("#Annunciatelist1").append(htmlContent);
}

//删除行
function lineRemove(pgdid){
	$('#tr1_'+pgdid).remove();
	pgdids.remove(pgdid);
}
//扩展数组方法：查找指定元素的下标  
Array.prototype.indexOf = function(val) {  
for (var i = 0; i < this.length; i++) {  
    if (this[i] == val) return i;  
}  
return -1;  
};  

//扩展数组方法:删除指定元素  
Array.prototype.remove = function(val) {  
var index = this.indexOf(val);  
while(index>-1){  
    this.splice(index, 1);  
    index = this.indexOf(val);  
}  
};
//提交
function submit(){
	
	obj = {
			id: $('#engineeringId').val(),
			gczlbh: $('#engineeringGczlbh').val(),
			orderSourceList:getPgdIdAndPgdh()
	};
	
	//alert(obj.detailEngineeringList);
	
	AjaxUtil.ajax({
		url:basePath+"/project/engineering/updateMainEngineering",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
				AlertUtil.showMessage('提交成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
		}
	});
	
}



//保存
function save(){
	/*var len = $("#Annunciatelist").find("tr").length;
	if(len<=0){
		alert("请先选择评估单 ");
		return false;
	}
	*/
	obj = {
			zhut:$.trim( $('#zhut').val()),
			xggzh:$.trim( $('#xggzh').val()),
			jhGs:$.trim( $('#jhGs').val()),
			isCfzxsx:$.trim( $('#isCfzxsx').val()),
			isZlphyx:$.trim( $('#isZlphyx').val()),
			bb:$.trim( $('#bb').val()),
			txyj:$.trim( $('#txyj').val()),
			bz:$.trim( $('#bz').val()),
			zt:0,
			detailEngineeringList:zxdxList(),
			ckzl:getShzlh(),
			orderSourceList:getPgdIdAndPgdh()
			
	};
	
	if(obj.detailEngineeringList.folg==false){
		return ;
	}
	//alert(obj.detailEngineeringList);
	
	AjaxUtil.ajax({
		url:basePath+"/project/engineering/saveMainEngineering",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			
				AlertUtil.showMessage('保存成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
		}
	});
	
}
//获取选择后的所有评估单id
function getPgdIdAndPgdh(){
	var orderSourceList=[];
	$("#Annunciatelist1").find("tr input[name='PgdIdAndPgdh']").each(function(){
		var value=$(this).val();
		var pgdid=value.split(",")[0];
		var pgdh=value.split(",")[1];
		 var ordersour={};
		ordersour.pgdid=pgdid;
		ordersour.pgdh=pgdh;
		orderSourceList.push(ordersour);
	});
	return orderSourceList;
}
//获取所有的适航指令号并拼接
function getShzlh(){
	var ckzl="";
	$("#Annunciatelist").find("tr input[name='Ckzl']").each(function(){
		//alert($(this).val());
		//alert(1);
		ckzl+=$(this).val()+",";
	});
	//alert(ckzl);
	ckzl=ckzl.substring(0,ckzl.length-1);
	//alert(ckzl);
	
	return ckzl;
}

//执行对象
function zxdxList(){
	var zxdxx=[];
	zxdxx.folg=true;
	$("#zxdxList").find("tr").each(function(){
		var zxdx ={};
		var index=$(this).index(); //当前行
		var zxfl =$("select[name='zxdx']").eq(index).val(); //执行对象
		var zjqdid =$("input[name='syxid']").eq(index).val(); //当前行，隐藏id的值
		var fjzch =$("select[name='fjzch']").eq(index).val(); //飞机注册号
		var bjh =$("select[name='bjh']").eq(index).val(); //部件号
		var bjxlh =$("select[name='bjxlh']").eq(index).val(); //序列号
		var xh=$("input[name='noum']").eq(index).val();//当行序号
		if(zxfl=="fjbj"){
			zxdx.zjqdid=zjqdid;
		}
		
		zxdx.zxfl=zxfl;
		zxdx.fjzch=fjzch;
		zxdx.bjh=bjh;
		zxdx.bjxlh=bjxlh;
		zxdx.xh=xh;
		
		var wcrq =$("#wcrq"+xh).val(); //完成日期
		var jssj  =$("#jssj"+xh).val(); //机身时间
		var qlxh =$("#qlxh"+xh).val(); //起落循环 
		var jcsj =$("#jcsj"+xh).val(); //绞车时间 
		var jcxh =$("#jcxh"+xh).val(); //绞车循环 
		
		if(zxfl==null || zxfl==""){
			alert("请选择执行分类");
			zxdx.folg=false;
			return false;
		}
		if(zxfl=="fjbj"){
			//var syxid =$("input[name='syxid']").eq(index).val(); //当前行，隐藏id的值
			if(fjzch==null || fjzch==""){
				alert("请选择飞机注册号");
				zxdx.folg=false;
				return false;
			}else{
				if(bjh==null || bjh==""){
					alert("请选择部件");
					zxdx.folg=false;
					return false;
				}else{
					if(bjxlh==null || bjxlh==""){
						alert("请选择部件序列号");
						zxdx.folg=false;
						return false;
					}
				}
			}
			
		}
		if(zxfl=="fzjj"){
			if(bjh==null || bjh==""){
				alert("请选择部件");
				zxdx.folg=false;
				return false;
			}else{
				if(bjxlh==null || bjxlh==""){
					alert("请选择部件序列号");
					zxdx.folg=false;
					return false;
				}
			}
		}
		
		var str=[wcrq,jssj,qlxh,jcsj,jcxh];
		//alert(str.length);
		var stx=[];
		for(var i=0;i<str.length;i++){
			if(str[i]!=null && str[i]!=""){
				stx.push(str[i]);
			}
		}
		//alert(stx);
		for(var i=1;i<=stx.length;i++){
			if(1==i){
				if(wcrq!=null && wcrq!=""){
					zxdx.jkflbhF="calendar";
					zxdx.jkxmbhF="calendar";
					zxdx.jkzF=wcrq;
					wcrq="";
					continue;
				}
				if(jssj!=null && jssj!=""){
					zxdx.jkflbhF="flight_time";
					zxdx.jkxmbhF="fuselage_flight_time";
					zxdx.jkzF=jssj;
					jssj="";
					continue;
				}
				if(qlxh!=null && qlxh!=""){
					zxdx.jkflbhF="loop";
					zxdx.jkxmbhF="landing_gear_cycle";
					zxdx.jkzF=qlxh;
					qlxh="";
					continue;
				}
				if(jcsj!=null && jcsj!=""){
					zxdx.jkflbhF="flight_time";
					zxdx.jkxmbhF="winch_time";
					zxdx.jkzF=jcsj;
					jcsj="";
					continue;
				}
				if(jcxh!=null && jcxh!=""){
					zxdx.jkflbhF="loop";
					zxdx.jkxmbhF="winch_cycle";
					zxdx.jkzF=jcxh;
					jcxh="";
					continue;
				}
				
			}
			if(2==i){
				if(wcrq!=null && wcrq!=""){
					zxdx.jkflbhS="calendar";
					zxdx.jkxmbhS="calendar";
					zxdx.jkzS=wcrq;
					wcrq="";
					continue;
				}
				if(jssj!=null && jssj!=""){
					zxdx.jkflbhS="flight_time";
					zxdx.jkxmbhS="fuselage_flight_time";
					zxdx.jkzS=jssj;
					jssj="";
					continue;
				}
				if(qlxh!=null && qlxh!=""){
					zxdx.jkflbhS="loop";
					zxdx.jkxmbhS="landing_gear_cycle";
					zxdx.jkzS=qlxh;
					qlxh="";
					continue;
				}
				if(jcsj!=null && jcsj!=""){
					zxdx.jkflbhS="flight_time";
					zxdx.jkxmbhS="winch_time";
					zxdx.jkzS=jcsj;
					jcsj="";
					continue;
				}
				if(jcxh!=null && jcxh!=""){
					zxdx.jkflbhS="loop";
					zxdx.jkxmbhS="winch_cycle";
					zxdx.jkzS=jcxh;
					jcxh="";
					continue;
				}
				
			}
			if(3==i){
				if(wcrq!=null && wcrq!=""){
					zxdx.jkflbhT="calendar";
					zxdx.jkxmbhT="calendar";
					zxdx.jkzT=wcrq;
					wcrq="";
					continue;
				}
				if(jssj!=null && jssj!=""){
					zxdx.jkflbhT="flight_time";
					zxdx.jkxmbhT="fuselage_flight_time";
					zxdx.jkzT=jssj;
					jssj="";
					continue;
				}
				if(qlxh!=null && qlxh!=""){
					zxdx.jkflbhT="loop";
					zxdx.jkxmbhT="landing_gear_cycle";
					zxdx.jkzT=qlxh;
					qlxh="";
					continue;
				}
				if(jcsj!=null && jcsj!=""){
					zxdx.jkflbhT="flight_time";
					zxdx.jkxmbhT="winch_time";
					zxdx.jkzT=jcsj;
					jcsj="";
					continue;
				}
				if(jcxh!=null && jcxh!=""){
					zxdx.jkflbhT="loop";
					zxdx.jkxmbhT="winch_cycle";
					zxdx.jkzT=jcxh;
					jcxh="";
					continue;
				}
				
			}
		}
		//AlertUtil.showErrorMessage(JSON.stringify(zxdx));
		zxdxx.push(zxdx);
		//AlertUtil.showErrorMessage(JSON.stringify(zxdxx));
	});
	return zxdxx;
}
//加载已选择的关联评估单
function selectChoosePgd(){
	var id=$('#engineeringId').val();
	//alert(id);
	AjaxUtil.ajax({
		url:basePath + "/project/engineering/selectChoosePgd",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			//拼接内容
			appendSelectChoosePgd(data);
		}
	});
}
//拼接已选择的评估单
function appendSelectChoosePgd(dataList){
	var param = dataList.tchnicalFileList; //页面数据
	var htmlContent = "";
	$.each(param,function(index,data){
		pgdids.push(data.id); 
		//alert(data.id);
		htmlContent += "<tr bgcolor=\"#f9f9f9\" id='tr1_"+data.id+"'>";
		/*htmlContent += "<td align='center'><a href=\"javascript:lineRemove('"+data.id+"')\"><i class='icon-minus cursor-pointer' ></i></a></td>";*/
		//htmlContent = htmlContent + "<td><button class='line6' onclick=\"lineRemove('"+data.id+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
		htmlContent += "<td class='text-center' style='vertical-align:middle'>" +
				"<input type='hidden' name='PgdIdAndPgdh' value="+data.id+","+StringUtil.escapeStr(data.pgdh)+"><input type='hidden' name='Ckzl' value='"+StringUtil.escapeStr(data.shzltgh)+"'>"+
				"<a href=\"javascript:viewMainTechnicalfile('"+data.id+"','"+data.dprtcode+"')\">"
				+data.pgdh+"</a></td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(data.ly)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle' title='"+data.shzltgh+"' >"+StringUtil.escapeStr(data.shzltgh)+"</td>";
		htmlContent = htmlContent +"<td class='text-center' style='vertical-align:middle'>"+indexOfTime(data.sxrq)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(data.pgr_user.displayName)+"</td>";
		htmlContent = htmlContent +"<td class='text-center' style='vertical-align:middle'>"+indexOfTime(data.pgqx)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+zts[data.zt]+"</td>";
		htmlContent = htmlContent + "</tr>";
	});
	
	$("#Annunciatelist").empty();
	$("#Annunciatelist").html(htmlContent);
}
//审核（同意）
function agreedMain(){
	obj={
			'id':$('#engineeringId').val(),
			'shyj':$('#shyj').val(),
			'zt':2
	}
	AlertUtil.showConfirmMessage("您确定要审核通过吗？",{callback: function(){
		submitMainEngineering(obj);
	}});
	
}
//审核（驳回）
function rejectedMain(){
	if($('#shyj').val()=="" || $('#shyj').val()==null){
		AlertUtil.showErrorMessage("审核意见不能为空");
		return 
	}
	obj={
			'id':$('#engineeringId').val(),
			'shyj':$('#shyj').val(),
			'zt':5
	}
	AlertUtil.showConfirmMessage("您确定要审核驳回吗？",{callback: function(){
		submitMainEngineering(obj);
	}});
	
}
//批准（同意）
function agreedMain1(){
	obj={
			'id':$('#engineeringId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':3
	}
	AlertUtil.showConfirmMessage("您确定要批准通过吗？",{callback: function(){
		submitMain(obj);
	}});
	
}
//批准（驳回）
function rejectedMain1(){
	if($('#pfyj').val()=="" || $('#pfyj').val()==null){
		AlertUtil.showErrorMessage("批准意见不能为空");
		return 
	}
	obj={
			'id':$('#engineeringId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':6
	}
	
	AlertUtil.showConfirmMessage("您确定要批准驳回吗？",{callback: function(){
		submitMain(obj);
	}});
}
//批准（中止）
function suspendMain(){
	if($('#pfyj').val()=="" || $('#pfyj').val()==null){
		AlertUtil.showErrorMessage("批准意见不能为空");
		return 
	}
	obj={
			'id':$('#engineeringId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':4
	}
	
	AlertUtil.showConfirmMessage("您确定要中止(关闭)吗？",{callback: function(){
		submitMain(obj);
	}});
}
//审核提交
function submitMainEngineering(obj){
	//alert(JSON.stringify(obj));
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/engineering/submitshenheMainEngineering",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==2){
				AlertUtil.showMessage('审核成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==5){
				AlertUtil.showMessage('驳回成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
			}else{
				AlertUtil.showMessage('操作成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
				
			}
			
		}
	});

}
//提交
function submitMain(obj){
	//alert(JSON.stringify(obj));
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/engineering/submitpifuMainEngineering",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==3){
				AlertUtil.showMessage('批准成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==6){
				AlertUtil.showMessage('驳回成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==4){
				AlertUtil.showMessage('中止成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
			}else{
				AlertUtil.showMessage('操作成功!','/project/engineering/main?id='+data+'&pageParam='+pageParam);
			}
		}
	});

}
//初始化ATA章节号信息
function initChapter(){
 	var zjh = $.trim($("#zjh").val());
 	var dprtcode = $.trim($("#engineeringDprtcode").val()); 	
 	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixchapter/getFixChapterByZjh",
		type:"post",
		data:{zjh:zjh,dprtcode:dprtcode},
		dataType:"json",
		success:function(data){
			if(null != data){
				$("#zjhName").val(formatUndefine(data.zjh)+" "+formatUndefine(data.zwms));
			}
		}
	});
}
//------------------------------------ATA章节号---------------------
//打开调整列表对话框
function openList() {
	$("#alertModalList").modal("show");
	goPageZjh(1,"auto","desc");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageZjh(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearchZjh(pageNumber,sortType,sequence);
}	
//信息检索
function searchRevisionZjh(){
	goPageZjh(1,"auto","desc");
}
//带条件搜索的翻页
function AjaxGetDatasWithSearchZjh(pageNumber,sortType,sequence){
	var searchParamZjh = gatherSearchParamZjh();
	searchParamZjh.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/fixchapter/selectFixChapter",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParamZjh),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtmlZjh(data.rows);
			   appendPaginationHtmlZjh(data,sortType,sequence);
			   // 标记关键字
			   signByKeyword($("#keyword_search_zjh").val(),[2,3]);
		   } else {
			  $("#listZjh").empty();
			  $("#paginationZjh").empty();
			  $("#listZjh").append("<tr><td colspan=\"11\">暂无数据 No data.</td></tr>");
		   }
   }
 }); 
	
}
//将搜索条件封装 
function gatherSearchParamZjh(){
	 var searchParamZjh = {};
	 searchParamZjh.keyword = $.trim($("#keyword_search_zjh").val());
	 return searchParamZjh;
}

function appendPaginationHtmlZjh(data,sortType,sequence){
		   
		   var viewpagecount = 10;//每页显示12个分页便签
		   var total = data.total;//总数据量
		   var size =  data.pageable.rows;//每页显示的条目数
		   var pageCount = total % size==0? parseInt(total/size):parseInt(total/size)+1;//总的页数 
		   var currentPage = 1; //记录当前页码 
		   
		   for (var i=0;i<pageCount;i++){
			   if (data.pageable.page == (i+1)) {
			  	 currentPage = i + 1;
			  	 break;
			   }
		   }
		   
		   var startpage = currentPage - (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1: viewpagecount / 2);//起始页
		   var endpage = currentPage + viewpagecount / 2; //结束页 
		   if (startpage < 1) {
				startpage = 1;
				if (pageCount >= viewpagecount){
					endpage = viewpagecount;
				}else{
					endpage = pageCount;
				}
			}
		   
		   if (endpage > pageCount) {
				endpage = pageCount;
				if ((endpage - viewpagecount) > 0){
					startpage = endpage - viewpagecount + 1;
				}else{
					startpage = 1;
				}
			}
		   var paginationContent = "";
		   if (currentPage==1) { //当前为第一页时,不能向前翻页
			   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
			   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;</a></li>";
		   } else {
			   paginationContent = paginationContent 
            + "<li onclick=\"goPageZjh(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
			   paginationContent = paginationContent 
            + "<li onclick=\"goPageZjh("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
		   }
		   
		   for (var index = startpage;index <= endpage;index++){
			   if (index==currentPage){
				   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
			   }else {
				   paginationContent = paginationContent + "<li onclick=\"goPageZjh("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
			   }
		   }
		   if (currentPage ==pageCount){
			   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
			   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
		   }else {
			   paginationContent = paginationContent + "<li onclick=\"goPageZjh("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
			   paginationContent = paginationContent + "<li onclick=\"goPageZjh("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
		   }
		   $("#paginationZjh").empty();
		   $("#paginationZjh").html(paginationContent);
	 }

function appendContentHtmlZjh(list){
		var zjh = $.trim($("#zjh").val());
		var htmlContent = '';
		$.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='chooesRow(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow(this)'>";
		   }
		   if(zjh == row.zjh){
			   htmlContent = htmlContent + "<td class='text-center'><input type='radio' name='chapter' checked='true' /><input type='hidden' class='form-control' name='hiddenid' value='"+row.zjh+"'/></td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'><input type='radio' name='chapter' /><input type='hidden' class='form-control' name='hiddenid' value='"+row.zjh+"'/></td>";
		   }
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zjh)+"</td>";  
		   htmlContent = htmlContent +"<td>"+formatUndefine(row.zwms)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#listZjh").empty();
	   $("#listZjh").html(htmlContent);
	 
}

//选中一行
function chooesRow(objradio){
	$(objradio).find("input[type='radio']").attr("checked",true);
}

function setModelData(){
	var zjh = $("#listZjh").find("input:checked").next().val();
	var zwmc = $.trim($("#listZjh").find("input:checked").parent().next().next().html());
	if(null != zjh){
		$("#zjh").val(zjh);
		$("#zjhName").val(zjh+" "+zwmc);
	}
}
//------------------------------------ATA章节号---------------------
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
		$("[name='time"+no+"'] ").val(jhgsRs*jhgsXss);
	}
	amount();
	
}
/*function intoGznrAndPgd(){
	no1=0;
	$('#zxdxList').empty();
	$('#Annunciatelist').empty();
	addRotatableCol();
}*/
function amount(){
	var zj=0;
	$('input[id=time]').each(function(){
		zj=zj+parseFloat($(this).val());
	});
	$('#zj').val(zj);
}
function viewMainTechnicalfile(id,dprtcode){
	window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
}
function vieworhideWorkContent(id) {
	var flag = $("#" + id).is(":hidden");
	if (flag) {
		$("#" + id).fadeIn(500);
		$("#" + id + 'icon').removeClass("icon-caret-down");
		$("#" + id + 'icon').addClass("icon-caret-up");
	} else {
		$("#" + id).hide();
		$("#" + id + 'icon').removeClass("icon-caret-up");
		$("#" + id + 'icon').addClass("icon-caret-down");
	}

}
function selectByEoid (id){
	 window.open(basePath+"/project/workorder/LookedWo?id=" + id+"&gddlx="+3);
}
function addEogd(gczlzxdxid,gczlid){
	window.open(basePath+"/project/workorder/intoEO?gczlid=" + gczlid+"&gczlzxdxid="+gczlzxdxid);
}
function back(){
	window.location.href =basePath+"/project/engineering/main?pageParam="+pageParam;
}