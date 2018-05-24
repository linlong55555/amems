var no1=0;
var pgdids=[];
var hcids=[];
var gdids=[];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var nox=0;
var glgdgknum = 1;

$(function(){
	$('#pgdh-dialog').dialog('close');                              //评估单号的模态框
	$('#hcxx-dialog').dialog('close');                              //航材库的模态框
	//数据字典
	DicAndEnumUtil.registerDic('4','zy',$.trim($("#zzjgid").val()));      //专业
	DicAndEnumUtil.registerDic('4','zy2',$.trim($("#zzjgid").val()));      //专业
	DicAndEnumUtil.registerDic('3','zxfl',$.trim($("#zzjgid").val()));    //执行分类
	DicAndEnumUtil.registerDic('19','gzzw',$.trim($("#zzjgid").val()));    //工作站位
	
	Navigation(menuCode,"新增EO工单","Add EO");
	if($("#zxfl").val()=='ZJJ'){
		$("#zxflname").val("飞机部件");
	}else if($("#zxfl").val()=='FZJJ'){
		$("#zxflname").val("非装机件");
	}else if($("#zxfl").val()=='FJ'){
		$("#zxflname").val("机身");
	}else{
		$("#zxflname").val("");
	}
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("#time").val((jhgsRs*jhgsXss).toFixed(2));
	}
	$('#btnSave').click(function() {
		saveNonroutine();
    });
	
	$(".panel-heading").not(":first").click(function(event){         //隐藏和显示
		if($(event.target).attr("id")!= "copy_wo"){
			$(this).next().slideToggle("fast");
		}
	});
	
	if($("#gkid").val()==""){
		$("#jobcard_demo").hide();
	}else{
		$("#jobcard_demo").show();
	}
	
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	if($("#gczlbh").val()!=null&&$("#gczlbh").val()!=""){
		initChapter();
		var fjzch2=$("#fjzch").val();
		var dprtcode=$.trim( $('#zzjgid').val());
		AjaxUtil.ajax({                                                           //根据飞机注册号查询飞机序号和左发序号
			type : 'post',
			url : basePath+"/productionplan/scheduledcheckitem/getXlh?fjzch="+encodeURIComponent(fjzch2)+"&dprtcode="+dprtcode,
			dataType : 'json',
			success : function(data) {
				if(data!=null){
					$("#fjxlh").val(data.fjXlh);
					$("#zfxh").val(data.zfXlh);
					$("#yfxh").val(data.yfXlh);
				}
			}
		});   
	}
	
	
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	zhut: {
                validators: {
                	notEmpty: {
                        message: '工单主题不能为空！'
                    },
                }
            },
        	xfgdyy: {
                validators: {
                }
            },
            bz: {
                validators: {
                }
            }
        }
    });
	var dprtcode = $.trim($("#zzjgid").val());
	var fjzch2=$("#fjzch").val();
	if(fjzch2!=null&&fjzch2!=""){
		AjaxUtil.ajax({                                                           //根据飞机注册号取得机型
			type : 'post',
			url : basePath+"/productionplan/planeData/findJx?fjzch="+encodeURIComponent(fjzch2)+"&dprtcode="+dprtcode,
			dataType : 'json',
			success : function(data) {
				if(data!=null){
					var planeRegOption = '';
					var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
					if(planeDatas != null && planeDatas.length >0){
						$.each(planeDatas,function(i,planeData){
							if(planeData.FJJX==data.fjjx){
								planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' selected='selected'>"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
							}else{
								planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
							}
						});
					}
					$("#fjjx").append(planeRegOption);
					
				}
			}
		});
		
	}else{
		var planeRegOption = '';
		var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
			});
		}
		if(planeRegOption == ''){
			planeRegOption = '<option value="">暂无飞机 No plane</option>';
		}
		$("#fjjx").append(planeRegOption);
	}
	loadWorkGroup();
});
function ViewJobCard(){
	 var id= $("#temp_id").val();
	 var dprtcode= $("#temp_dprtcode").val();
	 window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode="+dprtcode);
}

//获取工作组
function loadWorkGroup(){
	var obj={};
	obj.dprtcode=$.trim( $('#zzjgid').val());
	 AjaxUtil.ajax({
		   url:basePath+"/sys/workgroup/loadWorkGroup",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   if(data!=null){
				   var apps = '';
				   var list=data.wgList;
				   $.each(list,function(i,list){
			 			apps += "<option value='"+list.id+"' >"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
			 	   }); 
				   if(!data.wgMust){
					   $("#gzz").append("<option value=''>请选择</option>"+apps);
				   }else{
					   $("#gzz").append(apps);
				   }
			   }
	       },
	 }); 
}

//监控项目赋值
var  jkzF=$("#old_jkzF").val();
var  jkxmbhF=$("#old_jkxmbhF").val();
if(jkzF!=null && jkzF!=""){
	if(jkxmbhF=="calendar"){
		$("#wcrq").val(jkzF);
	}
	if(jkxmbhF=="fuselage_flight_time"){
		$("#jssj").val(jkzF);
	}
	if(jkxmbhF=="landing_gear_cycle"){
		$("#qlxh").val(jkzF);
	}
	if(jkxmbhF=="winch_time"){
		$("#jcsj").val(jkzF);
	}
	if(jkxmbhF=="winch_cycle"){
		$("#jcxh").val(jkzF);
	}
}

 var  jkzS=$("#old_jkzS").val();
 var  jkxmbhS=$("#old_jkxmbhS").val();	
if(jkzS!=null && jkzS!=""){
	if(jkxmbhS=="calendar"){
		$("#wcrq").val(jkzS);
	}
	if(jkxmbhS=="fuselage_flight_time"){
		$("#jssj").val(jkzS);
	}
	if(jkxmbhS=="landing_gear_cycle"){
		$("#qlxh").val(jkzS);
	}
	if(jkxmbhS=="winch_time"){
		$("#jcsj").val(jkzS);
	}
	if(jkxmbhS=="winch_cycle"){
		$("#jcxh").val(jkzS);
	}
}

 var   jkzT=$("#old_jkzT").val();
 var  jkxmbhT=$("#old_jkxmbhT").val();	
if(jkzT!=null && jkzT!=""){
	if(jkxmbhT=="calendar"){
		$("#wcrq").val(jkzT);
	}
	if(jkxmbhT=="fuselage_flight_time"){
		$("#jssj").val(jkzT);
	}
	if(jkxmbhT=="landing_gear_cycle"){
		$("#qlxh").val(jkzT);
	}
	if(jkxmbhT=="winch_time"){
		$("#jcsj").val(jkzT);
	}
	if(jkxmbhT=="winch_cycle"){
		$("#jcxh").val(jkzT);
	}
}
function changeJx(){
	$("#gczlbh").val("");
	$("#gczlid").val("");
	$("#zxdxDiv input").val("");
	$("#zjh").val("");
	$("#zjhandname").val("");
	$("#zhut").val("");
	$("#zjqdid").val("");
}
//初始化ATA章节号信息
function initChapter(){
 	var zjh = $.trim($("#zjh").val());
 	var dprtcode = $.trim($("#dprtcode").val());
 	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixchapter/getFixChapterByZjh",
		type:"post",
		data:{"zjh":zjh,"dprtcode":dprtcode},
		dataType:"json",
		success:function(data){
			if(null != data){
				$("#zjhandname").val(StringUtil.escapeStr(data.zjh)+" "+StringUtil.escapeStr(data.zwms));
			}
		},
	});
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

function del_tr(obj) {                                                          //删除工作内容的当前行
    $(obj).parent().parent().remove(); 
    workId -- ;
    $("#listContent", $("#addtr")).find("tr").each(function(index){
		$(this).find("td[name='workId']").html(index+1);
	});
}
var workId=1;                                                                     //增加工作内容
 function addTr(){
	   var htmlContent = '';
	   htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><button class='line6' onclick='del_tr(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button></td>" ;
	   htmlContent = htmlContent + "<td style='vertical-align:middle' name='workId'>"+workId+"</td>";  
	   htmlContent = htmlContent + "<td><textarea rows='3' maxlength='1300' placeholder='最大长度不超过1300' class='form-control' type='text' name='gznr'></textarea></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input maxlength='6' class='form-control' type='text' name='gzz'/></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='isBj' type='checkbox'  style='width: 20px;height: 20px;' /></td>";  
	   htmlContent = htmlContent + "</tr>";  
	   workId++;
	   $("#listContent").append(htmlContent);
 }


//信息检索航材
 function searchRevision(){
 	goPage2(1,"auto","desc");
 }
 function changeLx(){
	goPage2(1,"auto","desc");
 }
 function openHcxxList() {
 	 goPage2(1,"auto","desc");
 	 $("#alertModalHcxx").modal("show");
 }
 //跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
 function goPage2(pageNumber,sortType,sequence){
 	AjaxGetDatasWithSearch2(pageNumber,sortType,sequence);
 }
 //带条件搜索的翻页
 function AjaxGetDatasWithSearch2(pageNumber,sortType,sequence){
    var obj ={};
 	obj.hcids=hcids;//已选择的航次信息id
 	obj.hclxs =$.trim($("#hclx").val());
 	obj.keyword = $.trim($("#keyword_search").val());
 	obj.dprtcode=$.trim( $('#zzjgid').val());
 	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
 	startWait();
 	AjaxUtil.ajax({
 	   url:basePath+"/project/workorder/selectHcxxList",
 	   type: "post",
 	   contentType:"application/json;charset=utf-8",
 	   dataType:"json",
 	   data:JSON.stringify(obj),
 	   success:function(data){
 	    finishWait();
 		   if(data.total >0){
 			   appendContentHtml2(data.rows);
 			  new Pagination({
					renderTo : "pagination3",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(a,b,c){
						AjaxGetDatasWithSearch2(a,c,b);
					}
				});
 			   // 标记关键字
 			   signByKeyword($("#keyword_search").val(),[2,3,4],"#Hcxxlist tr td");
 		   } else {
 			  $("#Hcxxlist").empty();
 			  $("#pagination3").empty();
 			  $("#Hcxxlist").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
 		   }
    },
  }); 
 }
 function appendContentHtml2(list){
 	   var htmlContent = '';
 	   $.each(list,function(index,row){
 		   if (index % 2 == 0) {
 			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)'>";
 		   } else {
 			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)' >";
 		   }
 		      
 		   htmlContent = htmlContent + "<td class='text-center'><input type=\"checkbox\" onclick='chooesRow2(this)' name='hcxx'><input type='hidden'  name='hcid' value='"+formatUndefine(row.id)+"'>" +
 		   																		"<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.zwms)+"'>" +
 																		   		"<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(row.ywms)+"'>" +
 																		   		"<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(row.bjh)+"'>" +
 																		   		"<input type='hidden' name='kykcsl' value='"+formatUndefine(row.kykcsl==null?0:row.kykcsl)+"'>" +
 																		   		"<input type='hidden' name='hclx' value='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"'>" +
 																		   		"</td>";
 		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>"; 
 		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
 		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
 		   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.kykcsl==null?0:row.kykcsl)+"</td>";  
 		  
 		  htmlContent += "<td class='text-right'>";
			var dxtdsl = formatUndefine(row.paramsMap.dxtdsl);
			if(dxtdsl == '' && row.paramsMap.tdjczs > 0){
				dxtdsl = 0;
				htmlContent += dxtdsl;
			}else{
				htmlContent += "<a href='javascript:void(0);' bjh='"+StringUtil.escapeStr(row.bjh)+"' onclick=\"viewTdkc(this)\">"+dxtdsl+"</a>";
			}
			htmlContent += "<input type='hidden' name='dxtdsl' value='"+dxtdsl+"'>" ;
			htmlContent += "</td>";
 		   
 		   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.jldw)+"</td>";  
 		   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
 		   htmlContent = htmlContent + "</tr>";  
 	   });
 	   $("#Hcxxlist").html(htmlContent);
 }

 //保存航材信息
 var xuhao=1;
 function appendHcxx(){
 	var htmlContent = ""; 
 	$('input[name=hcxx]:checked').each(function(){  
 		var hcid=$(this).next().val();
 		var zwms=$(this).next().next().val();
 		var ywms=$(this).next().next().next().val();
 		var bjh=$(this).next().next().next().next().val();
 		var kycksl=$(this).next().next().next().next().next().val();
 		var hclx=$(this).next().next().next().next().next().next().val();
 		var dxtdsl = $(this).parent().parent().find("input[name='dxtdsl']").val();
 		
 		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" name='wohchc' bgcolor=\"#fefefe\"  id='tr1_"+hcid+"'>";
 		htmlContent = htmlContent + "<td><a href=javascript:lineRemove2('"+hcid+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
 		htmlContent = htmlContent + "<td style='vertical-align:middle' name='xuhao'>"+xuhao+"</td>"; 
 		
 		htmlContent = htmlContent + "<td ><input  maxlength='100' name='refJhly_1' type='text' class='form-control'></td>";  
 		
 		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(ywms)+"'><input type='hidden'  name='ywms_1' value='"+StringUtil.escapeStr(ywms)+"'/>"+StringUtil.escapeStr(ywms)+"</td>"; 
 		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(zwms)+"'><input type='hidden'  name='id_1' value='"+hcid+"'/><input type='hidden'  name='zwms_1' value='"+StringUtil.escapeStr(zwms)+"'/>"+StringUtil.escapeStr(zwms)+"</td>";  
 		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(bjh)+"'><input type='hidden'  name='bjh_1' value='"+StringUtil.escapeStr(bjh)+"'/>"+StringUtil.escapeStr(bjh)+"</td>";  
 		htmlContent = htmlContent + "<td class='text-right' style='vertical-align:middle'><input type='hidden'  name='kycksl_1' value='"+kycksl+"'/>"+kycksl+"</td>";  
 		
 		htmlContent += "<td class='text-right' style='vertical-align:middle' >";
		   if(dxtdsl != 0){
			   htmlContent += "<a href='javascript:void(0);' bjh='"+StringUtil.escapeStr(bjh)+"' onclick=\"viewTdkc(this)\">"+dxtdsl+"</a>";
		   }else{
			   htmlContent += dxtdsl;
		   }
		htmlContent += "</td>";
 		
 		htmlContent = htmlContent + "<td ><input   onkeyup='clearNoNum(this)'   maxlength='10' name='sl_1' type='text' class='form-control'></td>";  
 		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'><input type='hidden'  name='hclx_1' value='"+hclx+"'/>"+hclx+"</td>";  
 		htmlContent = htmlContent + "<td ><input name='bz_1' type='text'  maxlength='300' placeholder='最大长度不超过300' class='form-control'></td>";   
 		htmlContent = htmlContent + "</tr>";
 		hcids.push(hcid);
 		xuhao++;
 	 });
 		//$("#Annunciatelist").empty();
 	    $("#CKlist").append(htmlContent);
 }

 //删除行
 function lineRemove2(hcid){
 	$('#tr1_'+hcid).remove();
 	hcids.remove(hcid);
 	xuhao --;
	$("#CKlist", $("#airMaterialTools")).find("tr").each(function(index){
		$(this).find("td[name='xuhao']").html(index+1);
	});
 }
 
//查看替代部件详情
 function viewTdkc(this_){
 	var bjh = $(this_).attr("bjh");
 	Work_Material_View_Modal.show({
 		bjh : bjh,//
 		dprtcode : $.trim( $('#zzjgid').val())
 	});
 }

//信息检索关联工单
function searchRevision2(){
	goPage3(1,"auto","desc");
}
function changeGddlx(){
	goPage3(1,"auto","desc");
}
function changeZy(){
	goPage3(1,"auto","desc");
}
function openGlgd() {
	if($("#zxfl").val()==""){
		AlertUtil.showErrorMessage("请先执行工单的对象！");
		return;
	}else{
		$("#gddlx2").val("");
		$("#zy2").val("all");
		$("#alertModalGlgd").modal("show");
		goPage3(1,"auto","desc");
	}
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage3(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch3(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch3(pageNumber,sortType,sequence){
	var obj={};
	obj.gdids=gdids;   //已选择的工单的工单编号
	obj.gddlx =$.trim($("#gddlx2").val());
	var zy=$.trim($("#zy2").val());
	if(zy!="all"){
		obj.zy=zy;
	}
	obj.keyword = $.trim($("#keyword_search2").val());
	obj.dprtcode=$.trim( $('#zzjgid').val());
	obj.jx=$("#fjjx").val();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/workorder/workCardList",
		type: "post",
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success:function(data){
			finishWait();
			if(data.total >0){
				appendContentHtml3(data.rows);
				new Pagination({
					renderTo : "pagination2",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(a,b,c){
						AjaxGetDatasWithSearch3(a,b,c);
					}
				});
				// 标记关键字
				signByKeyword($("#keyword_search2").val(),[2,5],"#glgdCardlist2 tr td");
			} else {
				$("#glgdCardlist2").empty();
				$("#pagination2").empty();
				$("#glgdCardlist2").append("<tr class='text-center'><td colspan=\"5\">暂无数据 No data.</td></tr>");
			}
		},
	}); 
}
function appendContentHtml3(list){
	var htmlContent = '';
	$.each(list,function(index,row){
		if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)'>";
		   }
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='wo_glgd' onclick='chooesRow2(this)'><input type='hidden'  name='gdbh' value='"+StringUtil.escapeStr(row.gdbh)+"'>" ;
					   if(row.gddlx==2){
						   htmlContent = htmlContent + "<input type='hidden' name='gdlx' value='"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',row.gdlx)+"'>";  
					   }else{
					       htmlContent = htmlContent + "<input type='hidden' name='gdlx' value='"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+"'>";  
					   }
					   htmlContent = htmlContent + "<input type='hidden' name='zy' value='"+formatUndefine(row.zy)+"'>";
					   htmlContent = htmlContent + "<input type='hidden' name='zhut' value='"+StringUtil.escapeStr(row.zhut)+"'>";
					   htmlContent = htmlContent + "<input type='hidden' name='id' value='"+formatUndefine(row.id)+"'>";
					   htmlContent = htmlContent + "<input type='hidden' name='gdjcid' value='"+formatUndefine(row.gdjcid)+"'></td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.gdbh)+"</td>";
		   if(row.gddlx==2){
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',row.gdlx)+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+"</td>";  
		   }
		   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.zy)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zhut)+"'>"+StringUtil.escapeStr(row.zhut)+"</td>";
		   htmlContent = htmlContent + "</tr>";  
	});
	$("#glgdCardlist2").html(htmlContent);
	
}

//保存关联工单信息
function appendGlgd(){
	
	var htmlContent = ""; 
	$('input[name=wo_glgd]:checked').each(function(){  
		var gdbh=$(this).next().val();
		var gdlx=$(this).next().next().val();
		var zy=$(this).next().next().next().val();
		var zhut=$(this).next().next().next().next().val();
		var id=$(this).next().next().next().next().next().val();
		var gdjcid=$(this).next().next().next().next().next().next().val();
		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" name='glgd' bgcolor=\"#fefefe\" id='tr1_"+gdbh+"'>";
		htmlContent = htmlContent + "<td style='vertical-align:middle'><a href=javascript:lineRemove3('"+gdbh+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'name='glgdgknum'>"+glgdgknum+"</td>";
		htmlContent = htmlContent + "<td style='vertical-align:middle'class='text-left'><input type='hidden' name='gdbh' value="+StringUtil.escapeStr(gdbh)+">"+StringUtil.escapeStr(gdbh)+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'class='text-left'><input type='hidden' name='gdlx' value="+gdlx+"><input type='hidden' name='id' value="+id+"><input type='hidden' name='gdjcid' value="+gdjcid+">"+gdlx+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'class='text-center'><input type='hidden' name='zy' value="+zy+">"+StringUtil.escapeStr(zy)+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'class='text-left' title='"+StringUtil.escapeStr(zhut)+"'><input type='hidden' name='zhut' value="+StringUtil.escapeStr(zhut)+">"+StringUtil.escapeStr(zhut)+"</td>";
		htmlContent = htmlContent + "</tr>";
		gdids.push(id);
		glgdgknum++;
	});
	$("#glgdCardList").append(htmlContent);
	var len=$("#glgdCardList tr").length;
	if(len>0){
		$("#zxfl").attr("disabled",true);
		$("#fjzch").attr("disabled",true);
		$("#bjh").attr("disabled",true);
		$("#bjxlh").attr("disabled",true);
	}
}
//删除行
function lineRemove3(gdbh){
	$('#tr1_'+gdbh).remove();
	gdids.remove(gdbh);
	glgdgknum--;
	$("#glgdCardList", $("#relatedWorkOrder")).find("tr").each(function(index){
		$(this).find("td[name='glgdgknum']").html(index+1);
	});
}



//打开执行对象对话框
function openZxdx() {
	 goPage(1,"auto","desc");
	 var mainid=$("#gczlid").val();
		if(mainid==null||mainid==""){
			AlertUtil.showErrorMessage("请先选择工程指令！");
			return;
		}else{
	         $("#alertModalZxdx").modal("show");
		}
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var mainid=$("#gczlid").val();
		obj.mainid=mainid;
		obj.dprtcode=$.trim( $('#zzjgid').val());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/project/engineering/queryDetailEnginer",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				finishWait();
				if(data.rows!=null){
					appendContentHtml(data.rows);
				} else {
					$("#Zxdxbody").empty();
					$("#Zxdxbody").append("<tr class='text-center'><td colspan=\"11\">暂无数据 No data.</td></tr>");
				}
			},
		}); 
}
function appendContentHtml(list){
	   var htmlContent = '';
	   var detailEngineeringId=$("#gczlzxdxid").val();
	   $.each(list,function(index,row){
		   
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
		   }
		   htmlContent = htmlContent + "<td>";
		   
		   if(detailEngineeringId==row.id){
			   htmlContent = htmlContent + "<input type=\"radio\" onclick='chooesRow2(this)' checked='true' name='chose'><input type='hidden' value='"+formatUndefine(row.id)+"'>";
		   }else{
			   htmlContent = htmlContent + "<input type=\"radio\" onclick='chooesRow2(this)' name='chose'><input type='hidden' value='"+formatUndefine(row.id)+"'>";
		   }	   
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.zxfl)+"'>";
		   htmlContent += "<input type='hidden' value='"+StringUtil.escapeStr(row.fjzch)+"'>";
		   htmlContent += "<input type='hidden' value='"+StringUtil.escapeStr(row.bjh)+"'>";
		   htmlContent += "<input type='hidden' value='"+StringUtil.escapeStr(row.bjxlh)+"'>";
			   
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkxmbhF)+"'>";
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkflbhF)+"'>";
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkzF)+"'>";
			   
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkxmbhS)+"'>";
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkflbhS)+"'>";
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkzS)+"'>";
			   
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkxmbhT)+"'>";
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkflbhT)+"'>";
		   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkzT)+"'>";
		 
		   htmlContent += "<input type='hidden' name='zjqdid' value='"+formatUndefine(row.zjqdid)+"'>";
		   htmlContent += "<input type='hidden' name='bjms' value='"+StringUtil.escapeStr(row.bjms)+"'>";
		   htmlContent += "<input type='hidden' name='jhgsrs' value='"+formatUndefine(row.jhgsRs)+"'>";
		   htmlContent += "<input type='hidden' name='jhgsxss' value='"+formatUndefine(row.jhgsXss)+"'>";
		   
		   htmlContent += "</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zxfltype[row.zxfl])+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.fjzch)+"'>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bjxlh)+"'>"+StringUtil.escapeStr(row.bjxlh)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#Zxdxbody").html(htmlContent);
	 
}

//保存执行对象
function appendZxdx(){
	$('input[name=chose]:checked').each(function(index){   
		var id=$(this).next().val();
		var zxfl=$(this).next().next().val();
		var fjzch=$(this).next().next().next().val();
		var bjh=$(this).next().next().next().next().val();
		var bjhxlh=$(this).next().next().next().next().next().val();

		var jkxmbhF=$(this).next().next().next().next().next().next().val();
		//var jkflbhF=$(this).next().next().next().next().next().next().next().val();
		var jkzF=$(this).next().next().next().next().next().next().next().next().val();
		
		var jkxmbhS=$(this).next().next().next().next().next().next().next().next().next().val();
		//var jkflbhS=$(this).next().next().next().next().next().next().next().next().next().next().val();
		var jkzS=$(this).next().next().next().next().next().next().next().next().next().next().next().val();
		
		var jkxmbhT=$(this).next().next().next().next().next().next().next().next().next().next().next().next().val();
		//var jkflbhT=$(this).next().next().next().next().next().next().next().next().next().next().next().next().next().val();
		var jkzT=$(this).next().next().next().next().next().next().next().next().next().next().next().next().next().next().val();
		
		//var bjms=$(this).next().next().next().next().next().next().next().next().next().next().next().next().next().next().next().val();
		var bjms=$(this).siblings("input[name='bjms']").val();
		var zjqdid=$(this).siblings("input[name='zjqdid']").val();
		
		var jhgsrs=$(this).siblings("input[name='jhgsrs']").val();
		var jhgsxss=$(this).siblings("input[name='jhgsxss']").val();
		
		$("#zxdxDiv input").val("");
		$("#gczlzxdxid").val(id);
		$("#zjqdid").val(zjqdid);
		$("#zlmxid").val(id);
		$("#zxfl").val(zxfl);
		if(zxfl=="ZJJ"){
			$("#zxflname").val("飞机部件");
		}else if(zxfl=="FJ"){
			$("#zxflname").val("机身");
		}else{
			$("#zxflname").val("非装机件");
		}
		$("#jhgsRs").val(jhgsrs);
		$("#jhgsXss").val(jhgsxss);
		$("#time").val(jhgsxss*jhgsrs);
		
		$("#fjzch").val(fjzch);
		$("#bjh").val(bjh);
		$("#bjxlh").val(bjhxlh);
		$("#bjmc").val(bjms);
		
		AjaxUtil.ajax({                                                           //根据飞机注册号查询飞机序号和左发序号
			type : 'post',
			url : basePath+"/productionplan/scheduledcheckitem/getXlh?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+$.trim($("#zzjgid").val()),
			dataType : 'json',
			success : function(data) {
				if(data!=null){
					$("#fjxlh").val(data.fjXlh);
					$("#zfxh").val(data.zfXlh);
					$("#yfxh").val(data.yfXlh);
				}
			}
		});
		
		if(jkzF!=null && jkzF!=""){
			if(jkxmbhF=="calendar"){
				$("#wcrq").val(jkzF);
			}
			if(jkxmbhF=="fuselage_flight_time"){
				$("#jssj").val(jkzF);
			}
			if(jkxmbhF=="landing_gear_cycle"){
				$("#qlxh").val(jkzF);
			}
			if(jkxmbhF=="winch_time"){
				$("#jcsj").val(jkzF);
			}
			if(jkxmbhF=="winch_cycle"){
				$("#jcxh").val(jkzF);
			}
		}
		
		if(jkzS!=null && jkzS!=""){
			if(jkxmbhS=="calendar"){
				$("#wcrq").val(jkzS);
			}
			if(jkxmbhS=="fuselage_flight_time"){
				$("#jssj").val(jkzS);
			}
			if(jkxmbhS=="landing_gear_cycle"){
				$("#qlxh").val(jkzS);
			}
			if(jkxmbhS=="winch_time"){
				$("#jcsj").val(jkzS);
			}
			if(jkxmbhS=="winch_cycle"){
				$("#jcxh").val(jkzS);
			}
		}
		
		if(jkzT!=null && jkzT!=""){
			if(jkxmbhT=="calendar"){
				$("#wcrq").val(jkzT);
			}
			if(jkxmbhT=="fuselage_flight_time"){
				$("#jssj").val(jkzT);
			}
			if(jkxmbhT=="landing_gear_cycle"){
				$("#qlxh").val(jkzT);
			}
			if(jkxmbhT=="winch_time"){
				$("#jcsj").val(jkzT);
			}
			if(jkxmbhT=="winch_cycle"){
				$("#jcxh").val(jkzT);
			}
		}
	});
}


//检索 工程指令
function searchRevision3(){
	goPage4(1,"auto","desc");
}

function openGczl(){
	goPage4(1,"auto","desc");
	$("#alertModalGczl").modal("show");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage4(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch4(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch4(pageNumber,sortType,sequence){
   var obj ={};
   
    obj.fjjx = $.trim($("#fjjx").val());
	obj.keyword = $.trim($("#keyword_search3").val());
	obj.dprtcode=$.trim( $('#zzjgid').val());
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/project/engineering/queryEnginer",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml4(data.rows);
			   new Pagination({
					renderTo : "pagination4",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(a,b,c){
						AjaxGetDatasWithSearch4(a,b,c);
					}
				});
			   // 标记关键字
			   signByKeyword($("#keyword_search3").val(),[2],"#Gczlbody tr td");
		   } else {
			  $("#Gczlbody").empty();
			  $("#pagination4").empty();
			  $("#Gczlbody").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
		   }
   },
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
//多选行选
function chooesRow3(objcheckbox){
	var obj = $(objcheckbox).find("input[type='checkbox']");
	var flag = obj.is(":checked");
	if(flag){
		obj.removeAttr("checked");
	}else{
		obj.attr("checked",true);
	}
}
function appendContentHtml4(list){
	   var htmlContent = '';
	   var engineeringId=$("#gczlid").val();
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
		   }
		   if(engineeringId==row.id){
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" onclick='chooesRow2(this)' checked='true'  name='zlid'>" +
                                           "<input type='hidden'  name='id' value='"+formatUndefine(row.id)+"'>" +
											"<input type='hidden'  name='gczlbh' value='"+StringUtil.escapeStr(row.gczlbh)+"'>" +
											//"<input type='hidden'  name='gczlbh' value="+formatUndefine(row.gczlzxdxid)+">" +
											"<input type='hidden' name='zhut' value='"+StringUtil.escapeStr(row.zhut)+"'>"+
											"<input type='hidden' name='zjh' value='"+StringUtil.escapeStr(row.zjh)+"'>"+
											"<input type='hidden' name='fjjx' value='"+StringUtil.escapeStr(row.fjjx)+"'>"+
											"<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.zwms)+"'></td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" onclick='chooesRow2(this)'  name='zlid'>" +
						                  "<input type='hidden'  name='id' value='"+formatUndefine(row.id)+"'>" +
											"<input type='hidden'  name='gczlbh' value='"+StringUtil.escapeStr(row.gczlbh)+"'>" +
											//"<input type='hidden'  name='gczlbh' value="+formatUndefine(row.gczlzxdxid)+">" +
											"<input type='hidden' name='zhut' value='"+StringUtil.escapeStr(row.zhut)+"'>"+
											"<input type='hidden' name='zjh' value='"+StringUtil.escapeStr(row.zjh)+"'>"+
											"<input type='hidden' name='fjjx' value='"+StringUtil.escapeStr(row.fjjx)+"'>"+
											"<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.zwms)+"'></td>";
		   }
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.gczlbh)+"'>"+StringUtil.escapeStr(row.gczlbh)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ckzl)+"'>"+StringUtil.escapeStr(row.ckzl)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.zwms)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.xggzh)+"'>"+StringUtil.escapeStr(row.xggzh)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zhut)+"'>"+StringUtil.escapeStr(row.zhut)+"</td>";
		   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.bb)+"</td>"; 
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#Gczlbody").html(htmlContent);
	 
}

//保存工程指令信息
function appendGczl(){
	$('input[name=zlid]:checked').each(function(){  
		
		var id=$(this).next().val();
		var gczlbh=$(this).next().next().val();
	//	var gczlzxdxid=$(this).next().next().next().val();
		var zhut=$(this).next().next().next().val();
		var zjh=$(this).next().next().next().next().val();
		var fjjx=$(this).next().next().next().next().next().val();
		var zwms=$(this).next().next().next().next().next().next().val();
		
		var zjhandzwms=zjh+" "+zwms;
		$("#gczlbh").val(gczlbh);                             //将选中的值赋给jsp中的工程指令编号
		$("#gczlid").val(id);
		$("#zhut").val(zhut);
	//	$("#gczlzxdxid").val(gczlzxdxid);
		$("#zjhandname").val(zjhandzwms);
		$("#zjh").val(zjh);
		$("#fjjx").val(fjjx);
		
		$("#zxdxDiv input").val("");
	 });
}



//保存提交

function submitSave(){
	var zt=1;
	validateInsert(zt);
}
function woSave(){
	var zt=0;
	validateInsert(zt);
}
function validateInsert(zt){
	$("#CKlist input[name='sl_1']").each(function(){
		var input = $(this);
		input.css("border-color","");
	});
	
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	
	if($("#jhgsXss").val()==null||$("#jhgsXss").val()==""||$("#jhgsRs").val()==null||$("#jhgsRs").val()==""){
		AlertUtil.showErrorMessage("工单标准工时不能为空！");
		return false;
	}
	 if($("#zxfl").val()=="ZJJ"){
		   if($("#fjzch").val()==""||$("#bjh").val()==""||$("#bjxlh").val()==""){
			   AlertUtil.showErrorMessage("请填写完整执行对象信息！");
			   return false;
		   }
	 }else if($("#zxfl").val()=="FZJJ"){
		   if($("#bjh").val()==""||$("#bjxlh").val()==""){
			   AlertUtil.showErrorMessage("请填写完整执行对象信息！");
			   return false;
		   }
	 }else{
		   if($("#fjzch").val()==""){
			   AlertUtil.showErrorMessage("请填写完整执行对象信息！");
			   return false;
		   }
	 }
	 if($("#CKlist").find("tr").length>0){
			var flag=true;
			$("#CKlist>tr").each(function(){
				var index=$(this).index(); //当前行
				var sl =$("input[name='sl_1']").eq(index).val();
				if(sl==null||sl==""||sl==0){
					AlertUtil.showErrorMessage("航材工具使用数量不能为空且不为零！");
					$("input[name='sl_1']").eq(index).css("border-color","#a94442");
					flag=false;
					return false;
				}
			});
			if(!flag){
				return;
			}
		}
	 
	var gddlx=$.trim( $('#gddlx').val());                            //工单类型
	var zy=$.trim( $('#zy').val());                                   //专业
	var jhgsRs=$.trim( $('#jhgsRs').val());                   // 计划工时人数
	var jhgsXss=$.trim( $('#jhgsXss').val());                //计划工时小时数
	var xfgdyy=$.trim( $('#xfgdyy').val());                    //下发工单原因
	var bz=$.trim( $('#bz').val());                                   //备注
	var zhut=$.trim( $('#zhut').val());  
	var jd=$.trim( $('#jd').val());
	var zjh=$.trim( $('#zjh').val());
	
	var gczlid=$.trim( $('#gczlid').val());        
	var gczlbh=$.trim( $('#gczlbh').val());        
	var gczlzxdxid=$.trim( $('#gczlzxdxid').val());
	var gzzw=$.trim( $('#gzzw').val());
	var gzz=$.trim( $('#gzz').val());
	var djgkid=$.trim( $('#djgkid').val());
	
	var workorder={};
	
	workorder.gddlx=gddlx;
	workorder.zy=zy;
	workorder.jhgsRs=jhgsRs;
	workorder.jhgsXss=jhgsXss;
	workorder.xfgdyy=xfgdyy;
	workorder.bz=bz;
	workorder.zhut=zhut;
	workorder.zt=zt;
	workorder.zjh=zjh;
	workorder.jd=jd;
	workorder.gzzw=gzzw;
	workorder.gczlid=gczlid;
	workorder.gczlbh=gczlbh;
	workorder.gczlzxdxid=gczlzxdxid;
	workorder.gzzid=gzz;
	workorder.djgkid=djgkid;
	
	var baseworkorder={};
	
	baseworkorder.wOActionObj=zxdxList(),                                                //封装执行对象的参数实体
    baseworkorder.woJobenclosure=getWoJobenclosureParenm(),        // 封装附件上传表的参数实体
	baseworkorder.woAirMaterial=getWOAirMaterialParam(),                 //获取航材耗材的参数实体
	baseworkorder.woJobContent=getWOJobContentParam(),                //获取工作内容的参数实体
	baseworkorder.nonwocardList=getNonwocardListParam(),                //获取相关工卡的参数实体

	workorder.baseWorkOrder=baseworkorder;
	
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/workorder/add",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(workorder),
		dataType:"json",
		success:function(data){
			if(data.length>0){
				finishWait();
				if(zt==0){
					AlertUtil.showMessage('保存成功!','/project/workorder/main?id='+data+'&pageParam='+pageParam);
				}else{
					AlertUtil.showMessage('提交成功!','/project/workorder/main?id='+data+'&pageParam='+pageParam);
				}
			}
		},
	});
}
//返回前页面
function back(){
	 window.location.href =basePath+"/project/workorder/main?pageParam="+pageParam;
}
//获取执行对象
function zxdxList(){
	var WOActionObj={};
		
	    var zxfl=$("#zxfl").val();
		var fjzch=$("#fjzch").val();      
		var bjh=$("#bjh").val();
		var bjxlh=$("#bjxlh").val();
		var zjqdid=$("#zjqdid").val();
		
		
		WOActionObj.zxfl=zxfl;
		WOActionObj.fjzch=fjzch;
		WOActionObj.bjh=bjh;
		WOActionObj.bjxlh=bjxlh;
		WOActionObj.zjqdid=zjqdid;
		
		var wcrq=$("#wcrq").val();
		var jssj=$("#jssj").val();
		var qlxh=$("#qlxh").val();
		var jcsj=$("#jcsj").val();
		var jcxh=$("#jcxh").val();
		
		var str=[wcrq,jssj,qlxh,jcsj,jcxh];
		//alert(str.length);
		var stx=[];
		for(var i=0;i<str.length;i++){
			if(str[i]!=null && str[i]!=""){
				stx.push(str[i]);
			}
		}
		for(var i=1;i<=stx.length;i++){
			if(1==i){
				if(wcrq!=null && wcrq!=""){
					WOActionObj.jkflbhF="calendar";
					WOActionObj.jkxmbhF="calendar";
					WOActionObj.jkzF=wcrq;
					wcrq="";
					continue;
				}
				if(jssj!=null && jssj!=""){
					WOActionObj.jkflbhF="flight_time";
					WOActionObj.jkxmbhF="fuselage_flight_time";
					WOActionObj.jkzF=jssj;
					jssj="";
					continue;
				}
				if(qlxh!=null && qlxh!=""){
					WOActionObj.jkflbhF="loop";
					WOActionObj.jkxmbhF="landing_gear_cycle";
					WOActionObj.jkzF=qlxh;
					qlxh="";
					continue;
				}
				if(jcsj!=null && jcsj!=""){
					WOActionObj.jkflbhF="flight_time";
					WOActionObj.jkxmbhF="winch_time";
					WOActionObj.jkzF=jcsj;
					jcsj="";
					continue;
				}
				if(jcxh!=null && jcxh!=""){
					WOActionObj.jkflbhF="loop";
					WOActionObj.jkxmbhF="winch_cycle";
					WOActionObj.jkzF=jcxh;
					jcxh="";
					continue;
				}
				
			}
			if(2==i){
				if(wcrq!=null && wcrq!=""){
					WOActionObj.jkflbhS="calendar";
					WOActionObj.jkxmbhS="calendar";
					WOActionObj.jkzS=wcrq;
					wcrq="";
					continue;
				}
				if(jssj!=null && jssj!=""){
					WOActionObj.jkflbhS="flight_time";
					WOActionObj.jkxmbhS="fuselage_flight_time";
					WOActionObj.jkzS=jssj;
					jssj="";
					continue;
				}
				if(qlxh!=null && qlxh!=""){
					WOActionObj.jkflbhS="loop";
					WOActionObj.jkxmbhS="landing_gear_cycle";
					WOActionObj.jkzS=qlxh;
					qlxh="";
					continue;
				}
				if(jcsj!=null && jcsj!=""){
					WOActionObj.jkflbhS="flight_time";
					WOActionObj.jkxmbhS="winch_time";
					WOActionObj.jkzS=jcsj;
					jcsj="";
					continue;
				}
				if(jcxh!=null && jcxh!=""){
					WOActionObj.jkflbhS="loop";
					WOActionObj.jkxmbhS="winch_cycle";
					WOActionObj.jkzS=jcxh;
					jcxh="";
					continue;
				}
				
			}
			if(3==i){
				if(wcrq!=null && wcrq!=""){
					WOActionObj.jkflbhT="calendar";
					WOActionObj.jkxmbhT="calendar";
					WOActionObj.jkzT=wcrq;
					wcrq="";
					continue;
				}
				if(jssj!=null && jssj!=""){
					WOActionObj.jkflbhT="flight_time";
					WOActionObj.jkxmbhT="fuselage_flight_time";
					WOActionObj.jkzT=jssj;
					jssj="";
					continue;
				}
				if(qlxh!=null && qlxh!=""){
					WOActionObj.jkflbhT="loop";
					WOActionObj.jkxmbhT="landing_gear_cycle";
					WOActionObj.jkzT=qlxh;
					qlxh="";
					continue;
				}
				if(jcsj!=null && jcsj!=""){
					WOActionObj.jkflbhT="flight_time";
					WOActionObj.jkxmbhT="winch_time";
					WOActionObj.jkzT=jcsj;
					jcsj="";
					continue;
				}
				if(jcxh!=null && jcxh!=""){
					WOActionObj.jkflbhT="loop";
					WOActionObj.jkxmbhT="winch_cycle";
					WOActionObj.jkzT=jcxh;
					jcxh="";
					continue;
				}
			}
		}
		return WOActionObj;
}

//获取耗材工具信息参数
function getWOAirMaterialParam(){
	var WOAirMaterial = [];
	
	var len = $("#CKlist").find("tr").length;
	if (len == 1) {
		if($("#CKlist").find("td").length ==1){
			return WOAirMaterial;
		};
	}
	
	if (len > 0){
		$("#CKlist").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			
			var bjid =$("input[name='id_1']").eq(index).val(); 
			var refJhly =$("input[name='refJhly_1']").eq(index).val(); 
			var zwmc =$("input[name='zwms_1']").eq(index).val(); 
			var ywmc =$("input[name='ywms_1']").eq(index).val(); 
			var jh =$("input[name='bjh_1']").eq(index).val();
			var sl =$("input[name='sl_1']").eq(index).val(); 
			var lx =$("input[name='hclx_1']").eq(index).val(); 
			var bz =$("input[name='bz_1']").eq(index).val(); 
			
			
			infos.bjid=bjid;
			infos.refJhly = refJhly;
			infos.zwmc = zwmc;
			infos.ywmc = ywmc;
			infos.jh = jh;
			infos.sl = sl;
			infos.lx = hctype[lx];
			infos.bz  = bz;
			WOAirMaterial.push(infos);
		});
	}
	return WOAirMaterial;
}

//获取工作内容参数
function getWOJobContentParam(){
	var WOJobContent = [];
	
	var len = $("#listContent").find("tr").length;
	if (len == 1) {
		if($("#listContent").find("td").length ==1){
			return WOJobContent;
		}
	}
	
	if (len > 0){
		$("#listContent").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var gznr =$("textarea[name='gznr']").eq(index).val(); //当前行，ATA章节号
			var gzz =$("input[name='gzz']").eq(index).val(); //当前行，位置
			var isBj =$("input[name='isBj']").eq(index).is(":checked"); //当前行，必检
			
			infos.gznr = gznr;
			infos.gzz = gzz;
			infos.isBj = isBj?1:0;
			WOJobContent.push(infos);
		});
	}

	return WOJobContent;
}
function getWoJobenclosureParenm(){
	var woJobenclosure = [];
		var len = $("#filelist").find("tr").length;
		if (len == 1) {
			if($("#filelist").find("td").length ==1){
				return woJobenclosure;
			};
		}
		if (len > 0){
			$("#filelist").find("tr").each(function(){
				var infos ={};
				var tr = $(this);
				var fjid =tr.find("input[name='fjid']").val(); 
				var yswjm =tr.find("input[name='yswjm']").val(); 
				var nbwjm =tr.find("input[name='nbwjm']").val(); 
				var wbwjm =tr.find("input[name='wbwjm']").val(); 
				var cflj =tr.find("input[name='cflj']").val(); 
				var wjdx =tr.find("input[name='wjdx']").val();
				var hzm =tr.find("input[name='hzm']").val();
				
				infos.id=fjid;
				infos.yswjm = yswjm;
				infos.nbwjm = nbwjm;
				infos.wbwjm = wbwjm;
				infos.cflj = cflj;
				infos.wjdx = wjdx;
				infos.hzm = hzm;
				woJobenclosure.push(infos);
			});
		}
		return woJobenclosure;
	}
		
//获取相关工卡参数
function getNonwocardListParam(){
	var NonwocardList = [];
	
	var len = $("#glgdCardList").find("tr").length;
	if (len == 1) {
		if($("#glgdCardList").find("td").length ==1){
			return NonwocardList;
		};
	}
	
	if (len > 0){
		$("#glgdCardList").find("tr").each(function(){
			var infos ={};
			var tr=$(this);
			var gdlx =tr.find("input[name='gdlx']").val(); 
			var xggdid =tr.find("input[name='id']").val(); 
			var xgjcgdid =tr.find("input[name='gdjcid']").val(); 
			
			var	gdlxYj="";
			var	gdlxEj="";
			if(gdlx.indexOf("-")>0){
				gdlxYj= gdlx.split("-")[0];
			    gdlxEj=gdlx.split("-")[1];
				infos.xggdLx = wotype1[gdlxYj];
				infos.xggdZlx= wotype2[gdlxEj];
			}else{
				infos.xggdLx = wotype1[gdlx];
			}
			infos.xggdid=xggdid;
			infos.xgjcgdid=xgjcgdid;
			NonwocardList.push(infos);
		});
	}
	return NonwocardList;
}

var  zxfltype={
		 ZJJ:'飞机部件',
		FZJJ:'非装机件',
		 FJ:'机身',
};
var  wotype1={
		 定检工单:'1',
		 非例行:'2',
		 EO工单:'3',
};
var  wotype2={
		 时控件工单:'1',
		 附加工单:'2',
		 排故工单:'3',
};
var  hctype={
		 航材:'1',
		 设备:'2',
		 工具:'3',
		 危险品:'4',
		 低值易耗品:'5',
		 其它:'0',
};

//上传
var uploadObj = $("#fileuploader").uploadFile({
	url:basePath+"/common/ajaxUploadFile",
	multiple:true,
	dragDrop:false,
	showStatusAfterSuccess: false,
	showDelete: false,
	
	maxFileCount:100,
	formData: {
		"fileType":"workorder"
		,"wbwjm" : function(){return $('#wbwjm').val();}
		},//参考FileType枚举（技术文件类型）
	fileName: "myfile",
	returnType: "json",
	removeAfterUpload:true,
	uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
	uploadButtonClass: "ajax-file-upload_ext",
	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
	{
		var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
		trHtml = trHtml+'<td><div>';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
		 trHtml = trHtml+'</div></td>';
		 trHtml = trHtml+'<td class="text-left"><input type="hidden" name="hzm" value=\''+data.attachments[0].nbwjm.split('.')[1]+'\'/>'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
		 
		 trHtml = trHtml+'<td class="text-left"><input type="hidden" name="fjid" value=\''+data.attachments[0].uuid+'\'/><input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'/><input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'/>'+data.attachments[0].wjdxStr+' </td>';
		 trHtml = trHtml+'<td class="text-left"><input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'/><input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(data.attachments[0].cflj)+'\'/><input type="hidden" name="wjdx" value=\''+data.attachments[0].wjdx+'\'/>'+StringUtil.escapeStr(data.attachments[0].user.username)+" "+StringUtil.escapeStr(data.attachments[0].user.realname)+'</td>';
		 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
		 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
		 
		 trHtml = trHtml+'</tr>'; 
		 $('#filelist').append(trHtml);
	}
		//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
		,onSubmit : function (files, xhr) {
			var wbwjm  = $.trim($('#wbwjm').val());
			if(wbwjm.length>0){
				if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
	            	$('#wbwjm').focus();
	            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
	            	
	            	$('.ajax-file-upload-container').html("");
					uploadObj.selectedFiles -= 1;
					return false;
	            } 
	            else{
	            	return true; 
	            }
			}else{
				return true;
			}
		}
}); 

/**
* 删除附件
* @param newFileName
*/
function delfile(uuid) {
	var  responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	var fileArray = [];
	var waitDelArray = [];
	if(len > 0 ) {
		for(var i =0;i<len;i++){
			if(responses[i].attachments[0].uuid != uuid){
				fileArray.push(responses[i]);
			}
		}
		uploadObj.responses = fileArray;
		uploadObj.selectedFiles -= 1;
		$('#'+uuid).remove();
	}
}
//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#zjh").val());
	var dprtcode=$.trim( $('#zzjgid').val());
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		callback: function(data){//回调函数
			var wczjh = '';
			var wczjhName = '';
			if(data != null){
				wczjh = data.zjh;
				wczjhName = data.zwms;
				wczjhName = wczjh + " " + wczjhName;
			}
			$("#zjh").val(wczjh);
			$("#zjhandname").val(wczjhName);
		}
	});
}

function sumTotal(){
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("#time").val((jhgsRs*jhgsXss).toFixed(2));
	}
}
//只能输入正整数 验证人数
function clearNoNum(obj){
  obj.value=obj.value.replace(/\D/g,'');
  sumTotal();
}

//小时的验证只能输入数字和小数,保留两位  验证小时数
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
    sumTotal();
}