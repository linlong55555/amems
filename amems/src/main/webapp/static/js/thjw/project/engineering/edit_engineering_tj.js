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
	$('#zhut').on('change', function(e) {
		$('#form').data('bootstrapValidator')  
		.updateStatus('zhut', 'NOT_VALIDATED',null)  
		.validateField('zhut');  
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
	
	//飞机注册号的改变事件
	fjzchOnchange();
	//部件号的改变事件
	bjhOnchange();
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
            jx: {
                validators: {
                	notEmpty: {
                        message: '机型不能为空'
                    }
                }
            },
            bb: {
                validators: {
                	regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            },
            zhut: {
                validators: {
                	notEmpty: {
                        message: '主题不能为空'
                    }
                }
            },
            xggzh: {
                validators: {
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            }
            	            
        }
    });
	
	
    var dprtcode = $.trim($("#dprtcode").val());
    var jx=$("#engineeringFjjx").val();
	 var planeRegOption = '';
	 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 if(planeDatas != null && planeDatas.length >0){
	  	$.each(planeDatas,function(i,planeData){
	  		if(jx==planeData.FJJX){
	  			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' selected='selected' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  		}else{
	  			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"'>"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  		}
	  	});
	 }
	  /*if(planeRegOption == ''){
	  	planeRegOption = '<option value="">暂无飞机 No plane</option>';
	  }*/
	  $("#jx").append(planeRegOption);
});

function bjhOnchange(){
	$("[name='bjh']").off("change");
	$("[name='bjh']").on('change',function(){
	var appen="";
	var bjxlh=$(this).parent().next().find('select');
	var fjzch=$(this).parent().prev().find('select').val();
	var bjmc=$(this).parent().next().next().find('input');
	bjmc.attr("disabled",false);
	bjxlh.attr("disabled",false);
	var value=$(this).val();
	var Jh=value.split("^")[0];
	var bjm=value.split("^")[1];
	if(bjm=='null'){
		bjm="";
	}
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
					appen+="<option value=" + data[i].xlh + ">"+ data[i].xlh + "</option>";
				}
				
				bjxlh.append("<option value=''>please choose</option>"+appen);//
				
			}
		});
	
});
}

function fjzchOnchange(){
	$("[name='fjzch']").off("change");
	$("[name='fjzch']").on('change',function(){
		var appe="";
		var bjh=$(this).parent().next().find('select');
		bjh.attr("disabled",false);
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
					appe+="<option value=" + StringUtil.escapeStr(data[i].jh)+"^"+StringUtil.escapeStr(data[i].zwmc) + ">"+ StringUtil.escapeStr(data[i].jh) +"-"+StringUtil.escapeStr(data[i].ywmc)+"</option>";
					}
				}
				
				bjh.append("<option value=''>please choose</option>"+appe);//

				$("[name='bjh']").off("change");
				$("[name='bjh']").on('change',function(){
				var appen="";
				var bjxlh=$(this).parent().next().find('select');
				var bjmc=$(this).parent().next().next().find('input');
				bjmc.attr("disabled",false);
				bjxlh.attr("disabled",false);
				var value=$(this).val();
				var Jh=value.split("^")[0];
				var bjm=value.split("^")[1];
				if(bjm=='null'){
					bjm="";
				}
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
								appen+="<option value=" + StringUtil.escapeStr(data[i].xlh) + ">"+ StringUtil.escapeStr(data[i].xlh) + "</option>";
							}
							
							bjxlh.append("<option value=''>please choose</option>"+appen);//
							
						}
					});
				
			});
				
				
			}
		});
	
	});
}


function judge(obj,id,status){
	if(status==2){
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d.]/g,"");
		//必须保证第一个为数字而不是.
		obj.value = obj.value.replace(/^\./g,"");
		//保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{2,}/g,".");
		//保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		
		strs=obj.value.split("."); //字符分割
		if(strs.length>1){
			if(strs[1]>99){
				obj.value=strs[0]+'.'+strs[1].substr(0, 2);
			}
		}
	}
	
    if(status==1){
    	
    	//先把非数字的都替换掉，除了数字
        obj.value = obj.value.replace(/[^\d.:]/g,"");
    	
    	if(obj.value.indexOf(".") != -1){
    		if(obj.value.indexOf(":") != -1){
    			obj.value = obj.value.substring(0,obj.value.length -1);
    		}else{
    			clearNoNumTwoDate(obj);
    		}
    	}
    	if(obj.value.indexOf(":") != -1){
    		if(obj.value.indexOf(".") != -1){
    			obj.value = obj.value.substring(0,obj.value.length -1);
    		}else{
    			clearNoNumTwoColon(obj);
    		}
    	}
    	
    	if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
      		 if(obj.value.substring(1,2) != "." && obj.value.substring(1,2) != ":"){
      			obj.value = 0;
      		 }
      	 }
   
    }
    
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
/*document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	judge(1,nox);
};*/

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
						 zxflOption+="<option value="+items[i].id+" selected='selected'>"+StringUtil.escapeStr(items[i].name)+"</option>";
					 }else{
						 zxflOption+="<option value="+items[i].id+" >"+StringUtil.escapeStr(items[i].name)+"</option>";
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
								 fjzchOption+="<option value='" + StringUtil.escapeStr(data[i].fjzch) + "' selected='selected' >"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
							 }else{
								 fjzchOption+="<option value='" + StringUtil.escapeStr(data[i].fjzch) + "'>"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
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
	obj.id="1";
	
	addRow(obj);
	/*if(DicAndEnumUtil.data.dicMap!=undefined ){
		 
		 var dicMap = DicAndEnumUtil.data.dicMap!=undefined?DicAndEnumUtil.data.dicMap:{};
		 var items = dicMap[3]!=undefined?dicMap[3]:[];
		 var len = items.length;
		
		 var zxdx=$('#zxdxList tr:last td select:first');
		 var appd="";
		 for(var i=0;i<len;i++){
				 appd+="<option value="+items[i].id+" >"+items[i].name+"</option>";
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
		tr += "		<select class='form-control' name='fjzch' disabled='disabled'>";
		tr += obj.fjzchOption;
		tr += "		</select>";
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
							+ gdid + "')\">" + StringUtil.escapeStr(gdbh) + "</a>";
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
		tr += "		<input disabled='disabled' type='text' class='form-control datepicker' name='wcrq"+no+"' id='wcrq"+no+"' data-date-format='yyyy-mm-dd'   maxlength='10'  value="+obj.wcrq+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='jssj"+no+"' id='jssj"+no+"' disabled='disabled' value="+StringUtil.escapeStr(obj.jssj)+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='qlxh"+no+"' id='qlxh"+no+"' disabled='disabled' value="+StringUtil.escapeStr(obj.qlxh)+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='jcsj"+no+"' id='jcsj"+no+"' disabled='disabled' value="+StringUtil.escapeStr(obj.jcsj)+" >";
		tr += "	</div>";
		tr += "</td>";
		
		tr += "<td>";
		tr += "	<div class='col-xs-12 padding-left-8 padding-right-0'>";
		tr += "		<input class='form-control date-picker' name='jcxh"+no+"' id='jcxh"+no+"' disabled='disabled' value="+StringUtil.escapeStr(obj.jcxh)+" >";
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
		tr += "<input maxlength='9'  type='text' class='form-control '  id='time' name='time"+no+"' readOnly='true' value="+formatUndefine(obj.jhgsXss*obj.jhgsRs).toFixed(2)+">";
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
		
	/* if($("#Annunciatelist tr").length<=0){
		 AlertUtil.showErrorMessage("请先选择评估单");
		 $(this).val("");
		 return
	    }*/
		if($('#jx').val()=="" || $('#jx').val()==null){
			 AlertUtil.showErrorMessage("请先选择机型");
			 $(this).val("");
			 return
		}
		
	if($(this).val()=="ZJJ"){
		var fjzch=$(this).parent().next().find('select');
		/*if($('#jx').val()==null ||$('#jx').val()==""){
			AlertUtil.showErrorMessage("请先选择机型");
			$(this).val()=="";
			return;
		}*/
		
			AjaxUtil.ajax({
				type : 'post',
				cache : false,
				url : basePath+"/project/workorder/getPlaneDatas",
				dataType : 'json',
				data: { 'fjjx':$('#jx').val()},
				success : function(data) {
					fjzch.attr("disabled",false);
					fjzch.empty(); 
					for ( var i = 0; i < data.length; i++) {
						
						apps+="<option value=" + data[i].fjzch + ">"+ data[i].fjzch + "</option>";
					}
					
					fjzch.append("<option value=''>please choose</option>"+apps);//
					
					$("[name='fjzch']").off("change");
					$("[name='fjzch']").on('change',function(){
						var appe="";
						var bjh=$(this).parent().next().find('select');
						bjh.attr("disabled",false);
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
									appe+="<option value='" + StringUtil.escapeStr(data[i].jh)+"^"+StringUtil.escapeStr(data[i].zwmc) + "'>"+ StringUtil.escapeStr(data[i].jh) +"-"+StringUtil.escapeStr(formatUndefine(data[i].ywmc))+"</option>";
									}
								}
								
								bjh.append("<option value=''>please choose</option>"+appe);//

								$("[name='bjh']").off("change");
								$("[name='bjh']").on('change',function(){
								var appen="";
								var bjxlh=$(this).parent().next().find('select');
								var bjmc=$(this).parent().next().next().find('input');
								bjmc.attr("disabled",false);
								bjxlh.attr("disabled",false);
								var value=$(this).val();
								var Jh=value.split("^")[0];
								var bjm=value.split("^")[1];
								
								if(bjm=='null'){
									bjm="";
								}
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
			fjzch.attr("disabled",false);
			fjzch.empty();
			var bjh=$(this).parent().next().next().find('select');
			bjh.attr("disabled",true);
			bjh.empty();
			var bjxlh=$(this).parent().next().next().next().find('select');
			bjxlh.attr("disabled",true);
			bjxlh.empty();
			var bjm=$(this).parent().next().next().next().next().find('input');
			bjm.attr("disabled",true);
			bjm.val("");
			AjaxUtil.ajax({
				type : 'post',
				cache : false,
				url : basePath+"/project/workorder/getPlaneDatas",
				data: { 'fjjx':$('#jx').val()},
				dataType : 'json',
				success : function(data) {
					fjzch.empty(); 
					for ( var i = 0; i < data.length; i++) {
						apps+="<option value='" + StringUtil.escapeStr(data[i].fjzch) + "'>"+ StringUtil.escapeStr(data[i].fjzch) + "</option>";
					}
					fjzch.append("<option value=''>please choose</option>"+apps);
			
					$("[name='fjzch']").off("change");
					$("[name='fjzch']").on('change',function(){
					
						var bjh=$(this).parent().next().find('select');
						bjh.empty(); 
						var bjxlh=$(this).parent().next().next().find('select');
						bjxlh.empty(); 
						
					});
				}
			});
		
	}else if($(this).val()=="FZJJ"){
		var fjzch=$(this).parent().next().find('select');
		fjzch.attr("disabled",true);
		fjzch.empty();
		var bjh=$(this).parent().next().next().find('select');
		bjh.empty();
		bjh.attr("disabled",false);
		var xlh=$(this).parent().next().next().next().find('select');
		xlh.empty();
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
					bjxlh.attr("disabled",false);
					var bjmc=$(this).parent().next().next().find('input');
					bjmc.attr("disabled",false);
					var value=$(this).val();
					var bjh=value.split("^")[0];
					var zwms=value.split("^")[1];
					$(this).parent().next().next().find('input').val(formatUndefine(zwms));
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
	obj.dprtcode=$("#dprtcode").val();
	obj.keyword=$.trim($('#keyword_search').val());//关键字
	//var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/technicalfile/selectPgdList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
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
				// 标记关键字
				signByKeyword($("#keyword_search").val(), [ 2, 3, 8 ],"#Pgdlist tr td");
		   } else {
			  $("#Pgdlist").empty();
			  $("#pagination").empty();
			  $("#Pgdlist").append("<tr><td colspan=\"12\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
function chooesRow2(obj){
	chooesRowPGD($(obj));
}

function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='checkbox']");
	chooesRowPGD(obj);
}

//行选
function chooesRowPGD(obj){
	var flag = obj.is(":checked");
	if(flag){
		obj.removeAttr("checked");
	}else{
		obj.attr("checked",true);
	}
	
}
function appendContentHtml(list){
	 var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#fefefe\" onclick='chooesRow1(this)' >";
		   }
		      
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='pgd' onclick='chooesRow2(this)' ><input type='hidden' value="+formatUndefine(row.id)+">" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ly))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.sxrq))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.pgqx))+"'>" +
																		   		"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.zt))+">"+
																					"<input type='hidden' value="+StringUtil.escapeStr(row.pgr_user.username)+">"+
																					"<input type='hidden' value="+StringUtil.escapeStr(row.pgr_user.realname)+"></td>";
								htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"</td>";  
								htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ly))+"</td>";  
								htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.fl))+"</td>";  
								htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
								htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"</td>";  
								htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.sxrq).substring(0,10))+"</td>"; 
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.pgr_user.displayName))+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.pgqx).substring(0,10))+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td></tr>";  
		    
	   });
	   //$("#Pgdlist").empty();
	   $("#Pgdlist").html(htmlContent);
	 
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
		var username=$(this).next().next().next().next().next().next().next().next().next().val();
		var realname=$(this).next().next().next().next().next().next().next().next().next().next().val();
		//alert(wjzt);
		htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+pgdId+"'>";
		htmlContent = htmlContent + "<td><button class='line6' onclick=\"lineRemove('"+pgdId+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'><input type='hidden' name='PgdIdAndPgdh' value="+pgdId+","+pgdh+"><input type='hidden' name='Ckzl' value='"+ckzl+"'><a href=\"javascript:viewMainTechnicalfile('"+pgdId+"','"+dprtcode+"')\">"+pgdh+"</a></td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(ly)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(ckzl)+"'>"+StringUtil.escapeStr(ckzl)+"</td>";  
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+StringUtil.escapeStr(sxrq.substring(0,10))+"</td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(username)+" "+StringUtil.escapeStr(realname)+"</td>";
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+StringUtil.escapeStr(pgqx.substring(0,10))+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+zts[pgzt]+"</td>";
		htmlContent = htmlContent + "</tr>";
		pgdids.push(pgdId);
		
		if($('#zhut').val()=="" || $('#zhut').val()==null){
			$('#zhut').val(wjzt);
			
			$('#zhut').change();
		}
		  });
		//$("#Annunciatelist").empty();
	    $("#Annunciatelist").append(htmlContent);
	  /* if($("#Annunciatelist tr").length>0){
	    	$('#jx').attr("disabled",true);
	    }*/
}

//删除行
function lineRemove(pgdid){
	$('#tr1_'+pgdid).remove();
	pgdids.remove(pgdid);
	/*if($("#Annunciatelist tr").length<=0){
    	$('#jx').attr("disabled",false);
    }*/
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
	/*var len = $("#Annunciatelist").find("tr").length;
	if(len<=0){
		AlertUtil.showErrorMessage("请先选择评估单");
		return false;
	}*/
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
		var isZlphyx=$("input:radio[name='isZlphyx']:checked").val(); 
		var isCfzxsx=$("input:radio[name='isCfzxsx']:checked").val(); 
		var isYxfjdqfzsj=$("input:radio[name='isYxfjdqfzsj']:checked").val(); 
		var isYxcbw=$("input:radio[name='isYxcbw']:checked").val(); 
		var isSp=$("input:radio[name='isSp']:checked").val(); 
		var isXytsqc=$("input:radio[name='isXytsqc']:checked").val(); 
		var isXytsgjsb=$("input:radio[name='isXytsgjsb']:checked").val(); 
		var isXybfqcbhtzd=$("input:radio[name='isXybfqcbhtzd']:checked").val(); 
	  
	obj = {
			id: $('#engineeringId').val(),
			gczlbh: $('#engineeringGczlbh').val(),
			fjjx:$.trim( $('#jx').val()),
			zjh:$.trim( $('#zjh').val()),
			eolx:getEolx(),
			cj:$.trim( $('#cj').val()),
			zhut:$.trim( $('#zhut').val()),
			xggzh:$.trim( $('#xggzh').val()),
			bb:$.trim( $('#bb').val()),
			txyj:$.trim( $('#txyj').val()),
			bbly:$.trim( $('#bbly').val()),
			
			isCfzxsx:$.trim(isCfzxsx),
			isZlphyx:$.trim(isZlphyx),
			isYxfjdqfzsj:$.trim(isYxfjdqfzsj),
			isYxcbw:$.trim(isYxcbw),
			isSp:$.trim(isSp),
			isXytsqc:$.trim(isXytsqc),
			isXytsgjsb:$.trim(isXytsgjsb),
			isXybfqcbhtzd:$.trim(isXybfqcbhtzd),
			
			syxcbw:$.trim( $('#syxcbw').val()),
			tcsj:$.trim( $('#tcsj').val()),
			dprtcode:$.trim( $('#engineeringDprtcode').val()),
			zt:1,
			detailEngineeringList:zxdxList(),
			ckzl:getShzlh(),
			orderSourceList:getPgdIdAndPgdh(),
			olddetailEngineeringListId:oldzxdxList
	};
	
	//alert(JSON.stringify(obj.olddetailEngineeringList));
	
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
		AlertUtil.showErrorMessage("请先选择评估单");
		return false;
	}*/
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
		var isZlphyx=$("input:radio[name='isZlphyx']:checked").val(); 
		var isCfzxsx=$("input:radio[name='isCfzxsx']:checked").val(); 
		var isYxfjdqfzsj=$("input:radio[name='isYxfjdqfzsj']:checked").val(); 
		var isYxcbw=$("input:radio[name='isYxcbw']:checked").val(); 
		var isSp=$("input:radio[name='isSp']:checked").val(); 
		var isXytsqc=$("input:radio[name='isXytsqc']:checked").val(); 
		var isXytsgjsb=$("input:radio[name='isXytsgjsb']:checked").val(); 
		var isXybfqcbhtzd=$("input:radio[name='isXybfqcbhtzd']:checked").val(); 
	obj = {
			id: $('#engineeringId').val(),
			gczlbh: $('#engineeringGczlbh').val(),
			fjjx:$.trim( $('#jx').val()),
			zjh:$.trim( $('#zjh').val()),
			eolx:getEolx(),
			cj:$.trim( $('#cj').val()),
			zhut:$.trim( $('#zhut').val()),
			xggzh:$.trim( $('#xggzh').val()),
			bb:$.trim( $('#bb').val()),
			txyj:$.trim( $('#txyj').val()),
			bbly:$.trim( $('#bbly').val()),
			
			isCfzxsx:$.trim(isCfzxsx),
			isZlphyx:$.trim(isZlphyx),
			isYxfjdqfzsj:$.trim(isYxfjdqfzsj),
			isYxcbw:$.trim(isYxcbw),
			isSp:$.trim(isSp),
			isXytsqc:$.trim(isXytsqc),
			isXytsgjsb:$.trim(isXytsgjsb),
			isXybfqcbhtzd:$.trim(isXybfqcbhtzd),
			
			syxcbw:$.trim( $('#syxcbw').val()),
			tcsj:$.trim( $('#tcsj').val()),
			dprtcode:$.trim( $('#engineeringDprtcode').val()),
			zt:0,
			detailEngineeringList:zxdxList(),
			ckzl:getShzlh(),
			orderSourceList:getPgdIdAndPgdh(),
			olddetailEngineeringListId:oldzxdxList
			
	};
	
	if(obj.detailEngineeringList.folg==false){
		return ;
	}
	//alert(obj.detailEngineeringList);
	
	AjaxUtil.ajax({
		url:basePath+"/project/engineering/updateMainEngineering",
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
	$("#Annunciatelist").find("tr input[name='PgdIdAndPgdh']").each(function(){
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
		var id =$("input[name='syxid']").eq(index).val(); //当前行，隐藏id的值
		var fjzch =$("select[name='fjzch']").eq(index).val(); //飞机注册号
		var bjh =$("select[name='bjh']").eq(index).val(); //部件号
		if(bjh!=null && bjh!=""){
			if(bjh.indexOf("^")>-1){
				bjh=bjh.split("^")[0];
			}
		}
		
		var bjxlh =$("select[name='bjxlh']").eq(index).val(); //序列号
		var xh=$("input[name='noum']").eq(index).val();//当行序号
		if(zxfl=="fjbj"){
			zxdx.zjqdid=zjqdid;
		}
		zxdx.id=id;
		zxdx.zxfl=zxfl;
		zxdx.fjzch=fjzch;
		zxdx.bjh=bjh;
		zxdx.bjxlh=bjxlh;
		zxdx.xh=xh;
		zxdx.jhgsRs=$("#jhgsRs"+xh).val();//计划工时 人数
		zxdx.jhgsXss=$("#jhgsXss"+xh).val();//计划工时 小时数
		var wcrq =$("#wcrq"+xh).val(); //完成日期
		var jssj  =$("#jssj"+xh).val(); //机身时间
		var qlxh =$("#qlxh"+xh).val(); //起落循环 
		var jcsj =$("#jcsj"+xh).val(); //绞车时间 
		var jcxh =$("#jcxh"+xh).val(); //绞车循环 
		
		if(zxfl==null || zxfl==""){
			alert("请选择执行分类");
			zxdxx.folg=false;
			return false;
		}
		if(zxfl=="fjbj"){
			//var syxid =$("input[name='syxid']").eq(index).val(); //当前行，隐藏id的值
			if(fjzch==null || fjzch==""){
				alert("请选择飞机注册号");
				zxdxx.folg=false;
				return false;
			}else{
				if(bjh==null || bjh==""){
					alert("请选择部件");
					zxdxx.folg=false;
					return false;
				}else{
					if(bjxlh==null || bjxlh==""){
						alert("请选择部件序列号");
						zxdxx.folg=false;
						return false;
					}
				}
			}
			
		}
		if(zxfl=="fzjj"){
			if(bjh==null || bjh==""){
				alert("请选择部件");
				zxdxx.folg=false;
				return false;
			}else{
				if(bjxlh==null || bjxlh==""){
					alert("请选择部件序列号");
					zxdxx.folg=false;
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
		htmlContent = htmlContent + "<td style='vertical-align:middle'><button class='line6' onclick=\"lineRemove('"+data.id+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
		htmlContent += "<td align='center' style='vertical-align:middle'>" +
				"<input type='hidden' name='PgdIdAndPgdh' value='"+data.id+","+StringUtil.escapeStr(data.pgdh)+"'><input type='hidden' name='Ckzl' value='"+StringUtil.escapeStr(data.shzltgh)+"'>"+
				"<a href=\"javascript:viewMainTechnicalfile('"+data.id+"','"+data.dprtcode+"')\">"
				+StringUtil.escapeStr(data.pgdh)+"</a></td>";
		htmlContent = htmlContent +"<td align='left' style='vertical-align:middle'>"+StringUtil.escapeStr(data.ly)+"</td>";
		htmlContent = htmlContent +"<td align='left' title='"+StringUtil.escapeStr(data.shzltgh)+"' style='vertical-align:middle' >"+StringUtil.escapeStr(data.shzltgh)+"</td>";
		htmlContent = htmlContent +"<td align='center' style='vertical-align:middle'>"+indexOfTime(data.sxrq)+"</td>";
		htmlContent = htmlContent +"<td align='left' style='vertical-align:middle'>"+StringUtil.escapeStr(data.pgr_user.displayName)+"</td>";
		htmlContent = htmlContent +"<td align='center' style='vertical-align:middle'>"+indexOfTime(data.pgqx)+"</td>";
		htmlContent = htmlContent +"<td align='left' style='vertical-align:middle'>"+zts[data.zt]+"</td>";
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
	submitMainEngineering(obj);
}
//审核（驳回）
function rejectedMain(){
	obj={
			'id':$('#engineeringId').val(),
			'shyj':$('#shyj').val(),
			'zt':5
	}
	submitMainEngineering(obj);
}
//批准（同意）
function agreedMain1(){
	obj={
			'id':$('#engineeringId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':3
	}
	submitMain(obj);
}
//批准（驳回）
function rejectedMain1(){
	obj={
			'id':$('#engineeringId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':6
	}
	submitMain(obj);
}
//批准（中止）
function suspendMain(){
	obj={
			'id':$('#engineeringId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':4
	}
	submitMain(obj);
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
			   
			   	 new Pagination({
						renderTo : "paginationZjh",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}
					});  
			   
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
			   htmlContent = htmlContent + "<td class='text-center'><input type='radio' name='chapter' checked='true' /><input type='hidden' class='form-control' name='hiddenid' value='"+StringUtil.escapeStr(row.zjh)+"'/></td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'><input type='radio' name='chapter' /><input type='hidden' class='form-control' name='hiddenid' value='"+StringUtil.escapeStr(row.zjh)+"'/></td>";
		   }
		   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.zjh))+"</td>";  
		   htmlContent = htmlContent +"<td>"+StringUtil.escapeStr(formatUndefine(row.zwms))+"</td>";  
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
		$("[name='time"+no+"'] ").val((jhgsRs*jhgsXss).toFixed(2));
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
function getEolx(){
	var eolxList = $('#eolx').val();
	var eolx="";
	if(eolxList != null){
		for(var i = 0 ; i < eolxList.length ; i++){
			if('multiselect-all' != eolxList[i]){
				eolx=eolx+eolxList[i];
			}
		}
	}
	return eolx;
}
function viewMainTechnicalfile(id,dprtcode){
	window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
}
function selectByEoid (id){
	 window.open(basePath+"/project/workorder/LookedWo?id=" + id+"&gddlx="+3);
}
function addEogd(gczlzxdxid,gczlid){
	window.open(basePath+"/project/workorder/intoEO?gczlid=" + gczlid+"&gczlzxdxid="+gczlzxdxid);
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
function back(){
	window.location.href =basePath+"/project/engineering/main?pageParam="+pageParam;
}
//选择评估单
function searchRevision(){
	
	goPage(1,"auto","desc");
}