var pgdids=[];
var hcids=[];
var gdids=[];   
//重新修改之前用的是工单编号  存工单id
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var jkx=[];
var zjqdArr = [];
$(function(){
	$('#pgdh-dialog').dialog('close');                              //评估单号的模态框
	$('#hcxx-dialog').dialog('close');                              //航材库的模态框
	//数据字典
	DicAndEnumUtil.registerDic('4','zy',$.trim($("#zzjgid").val()));      //专业
	DicAndEnumUtil.registerDic('4','zy2',$.trim($("#zzjgid").val()));      //专业
	DicAndEnumUtil.registerDic('19','gzzw',$.trim($("#zzjgid").val()));    //工作站位
	Navigation(menuCode,"新增非例行工单","Add Non-Routine W/O");
	
	$('#btnSave').click(function() {
		saveNonroutine();
    });
	
	$(".panel-heading").not(":first").click(function(event){         //隐藏和显示
		if($(event.target).attr("id")!= "copy_wo"){
			$(this).next().slideToggle("fast");
		}
	});
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
    });
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	wjzt: {
                validators: {
                	notEmpty: {
                        message: '工单主题不能为空'
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
 	loadWorkGroup();
 	
 	if($("#gkid").val()==""){
		$("#jobcard_demo").hide();
	}else{
		$("#jobcard_demo").show();
		$("#zy").val(StringUtil.escapeStr($("#temp_zy").val()));
		$("#zjhandname").val(StringUtil.escapeStr($("#temp_zjh").val())+" "+StringUtil.escapeStr($("#temp_zjhZwms").val()));
		$("#gzz").val(StringUtil.escapeStr($("#temp_gzz").val()));
		$("#gzzw").val(StringUtil.escapeStr($("#temp_gzzw").val()));
		$("#zjh").val(StringUtil.escapeStr($("#temp_zjh").val()));
	}
 	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("#time").val((jhgsRs*jhgsXss).toFixed(2));
	}
 	
});
function ViewJobCard(){
	 var id= $("#temp_id").val();
	 var dprtcode= $("#temp_dprtcode").val();
	 window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode="+dprtcode);
}


var dprtcode=$.trim( $('#zzjgid').val()); 
//获取工作组
function loadWorkGroup(){
	var obj={};
	obj.dprtcode=dprtcode;
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
function changeLx(){                    //工单类型改变清除已选择的评估单
	$("#Annunciatelist").empty();
	pgdids.splice(0,pgdids.length);          //清空数组
}

function judge(){
	if($("#zxfl").val()=="FZJJ"){
		return
	}
	var $wcrq=$('#wcrq');
	$wcrq.removeAttr("readonly");
	$('#wcrq').removeAttr("disabled","true");
	var wcrq=$wcrq.val();
	
	var $jssj=$('#jssj');
	$jssj.removeAttr("disabled");
	var jssj=$('#jssj').val();
	
	var $qlxh=$('#qlxh');
	$qlxh.removeAttr("disabled");
	var qlxh=$('#qlxh').val();
	
	var $jcsj=$('#jcsj');
	$jcsj.removeAttr("disabled");
	var jcsj=$('#jcsj').val();
	
	var $jcxh=$('#jcxh');
	$jcxh.removeAttr("disabled");
	var jcxh=$('#jcxh').val();
	
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
			$wcrq.attr("disabled","true");
			$wcrq.attr("disabled","true");
		}
		if(jssj == ""){
			$jssj.attr("disabled","true");
		}
		if(qlxh == ""){
			$qlxh.attr("disabled","true");
		}
		if(jcsj == ""){
			$jcsj.attr("disabled","true");
		}
		if(jcxh == ""){
			$jcxh.attr("disabled","true");
		}
	}
}
//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	judge();
};

function changeJx(){
	$("#zxfl").val("");
	$("#fjzch").val("");
	$("#fjxlh").val("");
	$("#zfxh").val("");
	$("#yfxh").val("");
	$("#bjh").val("");
	$("#bjxlh").val("");
	$("#bjmc").val("");
	$("#wcrq").val("");
	$("#jssj").val("");
	$("#qlxh").val("");
	$("#jcsj").val("");
	$("#jcxh").val("");
}
function changeDate(){
	var num=0;
    if($("#jssj").val()!=null&&$("#jssj").val()!=""){
    	num++;
    }
    if($("#qlxh").val()!=null&&$("#qlxh").val()!=""){
    	num++;
    }
    if($("#jcsj").val()!=null&&$("#jcsj").val()!=""){
    	num++;
    }
    if($("#jcxh").val()!=null&&$("#jcxh").val()!=""){
    	num++;
    }
    if(num>2){
    	$("#wcrq").val("");
    	$("#wcrq").attr("disabled",true);
    }
}

function changeZXFL(){
	$("#zjqdid").val('');
	$("#bjxlh_demo").val('');
	var fjzchValue = $("#fjzch").val();
	if($("#zxfl").val()=="ZJJ"){
		$("#searchBut").css("display", ""); 
		$("#fjzch").attr("disabled",false);
		$('#jssj').attr("disabled",false);
		$('#qlxh').attr("disabled",false);
		$('#jcsj').attr("disabled",false);
		$('#jcxh').attr("disabled",false);
		$("#nofjjx").remove();
		
		var fjjx=$("#fjjx").val();
		
		//$("#fjxlh").val("");
		//$("#zfxh").val("");
//		$("#yfxh").val("");
		
		$("#bjh").val("");
		$("#bjxlh").val("");
		$("#bjmc").val("");
		
		
		var dprtcode = $.trim($("#zzjgid").val());
	 	var apps = '';
	 	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode,FJJX:fjjx});
	 	$("#fjzch").empty();
	 	if(planeDatas != null && planeDatas.length >0){
	 		$.each(planeDatas,function(i,planeData){
	 			apps += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
	 		});
	 	}
	 	$("#fjzch").append("<option value=''>请选择</option>"+apps);
	 	$("#fjzch").val(fjzchValue);
			
		$("[name='fjzch']").off("change");
		$("[name='fjzch']").on('change',function(){
			$("#zjqdid").val('');
			$("#bjh").val('');
			$("#bjxlh").val('');
			$("#bjmc").val(""); 
			var fjzch=$("#fjzch").val();
			
			AjaxUtil.ajax({                                                           //根据飞机注册号查询飞机序号和左发序号
				type : 'post',
				url : basePath+"/productionplan/scheduledcheckitem/getXlh?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+dprtcode,
				dataType : 'json',
				success : function(data) {
					if(data!=null){
						$("#fjxlh").val(data.fjXlh);
						$("#zfxh").val(data.zfXlh);
						$("#yfxh").val(data.yfXlh);
					}
				}
			});
			$("#searchBut").css("display", ""); 
		});
	}else if($("#zxfl").val()=="FJ"){
		    $("#searchBut").css("display", "none"); 
			$("#fjzch").attr("disabled",false);
			$('#jssj').attr("disabled",false);
			$('#qlxh').attr("disabled",false);
			$('#jcsj').attr("disabled",false);
			$('#jcxh').attr("disabled",false);
			$("#nofjjx").remove();
			
			var fjjx=$("#fjjx").val();
			
			$("#fjzch").empty();
//			$("#fjxlh").val("");
//			$("#zfxh").val("");
//			$("#yfxh").val("");
			$("#bjh").val("");
			$("#bjxlh").val("");
			$("#bjmc").val("");
			$("#bjh").attr("disabled",true);
			$("#bjxlh").attr("disabled",true);
			$("#bjmc").attr("disabled",true);
			
			//根据机型和飞机的设置权限 过滤
			var dprtcode = $.trim($("#zzjgid").val());
		 	var apps = '';
		 	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode,FJJX:fjjx});
		 	$("#fjzch").empty();
		 	if(planeDatas != null && planeDatas.length >0){
		 		$.each(planeDatas,function(i,planeData){
		 			apps += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
		 		});
		 	}
		 	$("#fjzch").append("<option value=''>请选择</option>"+apps);
		 	$("#fjzch").val(fjzchValue);
			$("[name='fjzch']").off("change");
			$("[name='fjzch']").on('change',function(){
				$("#zjqdid").val('');
				var fjzch=$("#fjzch").val();
				AjaxUtil.ajax({                                                           //根据飞机注册号查询飞机序号和左发序号
					type : 'post',
					url : basePath+"/productionplan/scheduledcheckitem/getXlh?fjzch="+encodeURIComponent(fjzch)+"&dprtcode="+dprtcode,
					dataType : 'json',
					success : function(data) {
						if(data!=null){
							$("#fjxlh").val(data.fjXlh);
							$("#zfxh").val(data.zfXlh);
							$("#yfxh").val(data.yfXlh);
						}
					}
				});
			});
	}else if($("#zxfl").val()=="FZJJ"){
		$("#nofjjx").remove();
		$('#wcrq').attr("disabled",false);
		$('#jssj').attr("disabled",true);
		$('#qlxh').attr("disabled",true);
		$('#jcsj').attr("disabled",true);
		$('#jcxh').attr("disabled",true);
		$('#jssj').val("");
		$('#qlxh').val("");
		$('#jcsj').val("");
		$('#jcxh').val("");
		$("#bjh").attr("disabled",false);
		$("#bjxlh").attr("disabled",false);
		$("#bjmc").attr("disabled",false);
		$("#fjjx").append("<option id='nofjjx' value='无' selected='selected'>无</option>");
		$("#fjzch").empty();
		$("#fjzch").append("<option value=''>请选择</option>");
		
		$("#fjxlh").val("");
		$("#zfxh").val("");
		$("#yfxh").val("");
		
		$("#fjzch").attr("disabled",true);
		$("#fjxlh").attr("disabled",true);
		$("#zfxh").attr("disabled",true);
		$("#yfxh").attr("disabled",true);
		
		$("#bjh").val("");
		$("#bjxlh").val("");
		$("#bjmc").val("");
		$("#searchBut").css("display", ""); 
	}
}

function setModelData(){
	var zjh = $("#list").find("input:checked").next().val();
	var zwmc = $.trim($("#list").find("input:checked").parent().next().next().html());
	if(null != zjh){
		$("#zjh").val(zjh);
		$("#zjhName").val(zjh+" "+zwmc);
	};
}

//打开评估单列表对话框
function openPgd() {
	var jx=($("#fjjx  option:selected").val());
	if(jx!=""&&jx!=null){
		goPage6(1,"auto","desc");
		$("#alertModalPgd").modal("show");
	}else{
		 AlertUtil.showErrorMessage("请先选择机型！");	
	}
}
//信息检索评估单
function searchRevision_pgd(){
	goPage6(1,"auto","desc");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage6(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch6(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch6(pageNumber,sortType,sequence){
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
	obj.pgdsId=pgdsId;           //已选择的评估单id
	
	var jx=($("#fjjx  option:selected").val());
	
	obj.jx=jx;           //将飞机机型设置为检索参数
	if($("#gdlx").val()==2){
		obj.isFjgd=1;	               //排故工单复选为1
	}else{
		obj.isFlxgd=1;	            //排故工单复选为1
	}
	obj.keyword = $.trim($("#keyword_search_pgd").val());
	obj.dprtcode=$.trim( $('#zzjgid').val()); 
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
			   appendContentHtml6(data.rows);
			   new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(a,b,c){
						AjaxGetDatasWithSearch6(a,c,b);
					}
				});
			// 标记关键字
			   signByKeyword($("#keyword_search_pgd").val(),[2,3,8],"#Pgdlist tr td");
		   } else {
			  $("#Pgdlist").empty();
			  $("#pagination").empty();
			  $("#Pgdlist").append("<tr class='text-center'><td colspan=\"12\">暂无数据 No data.</td></tr>");
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
function appendContentHtml6(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)'>";
		   }
		      
		   htmlContent = htmlContent + "<td class='text-center'><input type=\"checkbox\" name='pgd_1' onclick='chooesRow2(this)' ><input type='hidden' value="+formatUndefine(row.id)+">" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(row.pgdh)+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(row.shzltgh)+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(row.wjzt)+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(row.ly)+"'>" +
																		   		"<input type='hidden' value='"+formatUndefine(row.sxrq)+"'>" +
																		   		"<input type='hidden' value='"+formatUndefine(row.pgqx)+"'>" +
																		   		"<input type='hidden' value='"+formatUndefine(row.zt)+"'>"+
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(row.pgr_user.username)+"'>"+
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(row.pgr_user.realname)+"'>"+
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(row.jx)+"'></td>";
								htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.shzltgh)+"' class='text-left'>"+StringUtil.escapeStr(row.shzltgh)+"</td>";  
								htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.pgdh)+"' class='text-center'>"+StringUtil.escapeStr(row.pgdh)+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.ly)+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.fl)+"</td>";  
								htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.bb)+"</td>";  
								htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.wjzt)+"' class='text-left'>"+StringUtil.escapeStr(row.wjzt)+"</td>";  
								htmlContent = htmlContent + "<td>"+(formatUndefine(row.sxrq).substring(0,10))+"</td>"; 
								htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(row.pgr_user.displayName)+"</td>";  
								htmlContent = htmlContent + "<td>"+(formatUndefine(row.pgqx).substring(0,10))+"</td>";  
								htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td></tr>";  
		    
	   });
	   $("#Pgdlist").html(htmlContent);
	 
}

//保存评估单
function appendPgd(){
	var htmlContent = ""; 
	$('input[name=pgd_1]:checked').each(function(){   
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
		var jx=$(this).next().next().next().next().next().next().next().next().next().next().next().val();
		
		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+pgdId+"'>";           
		htmlContent = htmlContent + "<td style='vertical-align:middle'><a href=javascript:lineRemove('"+pgdId+"')><button class='line6'><i class='icon-minus cursor-pointer'></i></button></a></td>";
		htmlContent = htmlContent + "<td style='vertical-align:middle'><input type='hidden' name='PgdIdAndPgdh' value="+pgdId+","+pgdh+"><input type='hidden' name='Ckzl' value="+ckzl+">"+StringUtil.escapeStr(pgdh)+"</td>";
		htmlContent = htmlContent + "<td class='text-left' title='"+ly+"'style='vertical-align:middle'>"+ly+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(ckzl)+"'style='vertical-align:middle'>"+StringUtil.escapeStr(ckzl)+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'>"+sxrq.substring(0,10)+"</td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(username)+" "+StringUtil.escapeStr(realname)+"</td>";
		htmlContent = htmlContent + "<td style='vertical-align:middle'>"+(pgqx.substring(0,10))+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+zts[pgzt]+"</td>";
		htmlContent = htmlContent + "</tr>";
		pgdids.push(pgdId);
		
		if($('#zhut').val()=="" || $('#zhut').val()==null){
			$('#zhut').val(wjzt);
		}
		$('#pgjx').val(jx);                        //将评估单的机型放在隐藏域中
		});
	    $("#Annunciatelist").append(htmlContent);
	    if($("#Annunciatelist tr").length>0){
	    	$('#fjjx').attr("disabled",true);
	    }
}

//删除行
function lineRemove(pgdid){
	$('#tr1_'+pgdid).remove();
	pgdids.remove(pgdid);
	var len=$("#Annunciatelist tr").length;
    if(len==0){
    	$("#fjjx").attr("disabled",false);
    	$("#pgjx").val("无");
    	$("#zxdxtr select").val("");
    	$("#zxdxtr input").val("");
    }
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
	   htmlContent = htmlContent + "<td><textarea rows='3' maxlength='1300' placeholder='最大长度不超过1300' class='form-control' type='text' name='gznr'/></textarea></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input maxlength='6' class='form-control' type='text' name='gzz'/></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='isBj' type='checkbox' style='width: 20px;height: 20px;' /></td>";  
	   htmlContent = htmlContent + "</tr>";  
	   workId++;
	   $("#listContent").append(htmlContent);
 }



//信息检索航材
function searchRevision(){
	goPage2(1,"auto","desc");
}
function changeHclx(){
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
	obj.hcids=hcids;           //已选择的航次信息id
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
			  $("#Hcxxlist").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
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
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)'>";
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
			   htmlContent += "<a href='javascript:void(0);' bjh='"+StringUtil.escapeStr(row.bjh)+"' onclick=\"viewTdkc(this)\">"+(dxtdsl)+"</a>";
		   }
		   htmlContent += "<input type='hidden' name='dxtdsl' value='"+dxtdsl+"'>" ;
		   htmlContent += "</td>";
		   
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.jldw)+"</td>";  
		   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
	   });
	   //$("#Pgdlist").empty();
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
		
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'title='"+StringUtil.escapeStr(ywms)+"'><input type='hidden'  name='ywms_1' value='"+StringUtil.escapeStr(ywms)+"'/>"+StringUtil.escapeStr(ywms)+"</td>"; 
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(zwms)+"'><input type='hidden'  name='id_1' value='"+hcid+"'/><input type='hidden'  name='zwms_1' value='"+StringUtil.escapeStr(zwms)+"'/>"+StringUtil.escapeStr(zwms)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(bjh)+"' ><input type='hidden'  name='bjh_1' value='"+StringUtil.escapeStr(bjh)+"'/>"+StringUtil.escapeStr(bjh)+"</td>";  
		htmlContent = htmlContent + "<td class='text-right' style='vertical-align:middle'><input type='hidden'  name='kycksl_1' value='"+kycksl+"'/>"+kycksl+"</td>";  
		
		htmlContent += "<td class='text-right' style='vertical-align:middle' >";
		   if(dxtdsl != 0){
			   htmlContent += "<a href='javascript:void(0);' bjh='"+StringUtil.escapeStr(bjh)+"' onclick=\"viewTdkc(this)\">"+dxtdsl+"</a>";
		   }else{
			   htmlContent += dxtdsl;
		   }
		   htmlContent += "</td>";
		
		htmlContent = htmlContent + "<td ><input onkeyup='clearNoNum(this)'  placeholder=''  maxlength='10'  name='sl_1' type='text' class='form-control'></td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'><input type='hidden'  name='hclx_1' value='"+hclx+"'/>"+hclx+"</td>";  
		htmlContent = htmlContent + "<td ><input name='bz_1' type='text'  maxlength='300' placeholder='最大长度不超过300' class='form-control'></td>";   
		htmlContent = htmlContent + "</tr>";
		hcids.push(hcid);
		xuhao++;
	 });
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
		AlertUtil.showErrorMessage("请先选择工单的执行对象！");
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
	AjaxUtil.ajax({
	   url:basePath+"/project/workorder/workCardList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
	    console.info(data);
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
			  $("#glgdCardlist2").append("<tr><td colspan=\"5\">暂无数据 No data.</td></tr>");
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
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.zy)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zhut)+"'>"+StringUtil.escapeStr(row.zhut)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#glgdCardlist2").html(htmlContent);
	 
}

//保存关联工单信息
var glgdgknum = 1;
function appendGlgd(){
	var htmlContent = ""; 
	$('input[name=wo_glgd]:checked').each(function(){ 
		var gdbh=$(this).next().val();
		var gdlx=$(this).next().next().val();
		var zy=$(this).next().next().next().val();
		var zhut=$(this).next().next().next().next().val();
		var id=$(this).next().next().next().next().next().val();
		var gdjcid=$(this).next().next().next().next().next().next().val();
		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" name='glgd' bgcolor=\"#fefefe\" id='tr1_"+id+"'>";
		htmlContent = htmlContent + "<td style='vertical-align:middle'><a href=javascript:lineRemove3('"+id+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle' name='glgdgknum'>"+glgdgknum+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left'><input type='hidden' name='gdbh' value="+StringUtil.escapeStr(gdbh)+">"+StringUtil.escapeStr(gdbh)+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'class='text-left'><input type='hidden' name='gdlx' value="+gdlx+"><input type='hidden' name='id' value="+id+"><input type='hidden' name='gdjcid' value="+gdjcid+">"+gdlx+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'><input type='hidden' name='zy' value="+zy+">"+StringUtil.escapeStr(zy)+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle' class='text-left' title='"+StringUtil.escapeStr(zhut)+"'>"+StringUtil.escapeStr(zhut)+"</td>";   
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
function lineRemove3(id){
	$('#tr1_'+id).remove();
	gdids.remove(id);
   var len=$("#glgdCardList tr").length;
	if(len==0){
		$("#zxfl").attr("disabled",false);
		$("#fjzch").attr("disabled",false);
		$("#bjh").attr("disabled",false);
		$("#bjxlh").attr("disabled",false);
	}
	glgdgknum--;
	$("#glgdCardList", $("#relatedWorkOrder")).find("tr").each(function(index){
		$(this).find("td[name='glgdgknum']").html(index+1);
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
	if($("#zxfl").val()==null||$("#zxfl").val()==""){
		 AlertUtil.showErrorMessage("请选择执行对象！");
		   return false;
	}else if($("#zxfl").val()=="ZJJ"){
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
    var num=0;
    if($("#wcrq").val()!=null&&$("#wcrq").val()!=""){
    	num++;
    }
    if($("#jssj").val()!=null&&$("#jssj").val()!=""){
    	num++;
    }
    if($("#qlxh").val()!=null&&$("#qlxh").val()!=""){
    	num++;
    }
    if($("#jcsj").val()!=null&&$("#jcsj").val()!=""){
    	num++;
    }
    if($("#jcxh").val()!=null&&$("#jcxh").val()!=""){
    	num++;
    }
	if(num<=0){
		 AlertUtil.showErrorMessage("至少填写一项监控项！");
		 return false;
	}else if(num>3){
		AlertUtil.showErrorMessage("至多填写三项监控项！");
		return false;
	}
	if($("#Annunciatelist tr").length>0){
		if($("#fjjx").val()!=$("#pgjx").val()){
			AlertUtil.showErrorMessage("工单的飞机机型和评估单机型不匹配，请修改！");
			return false;
		}
	}
	var gdlx=$.trim( $('#gdlx').val());                            //工单类型
	var gddlx=$.trim( $('#gddlx').val());                            //工单类型
	var zy=$.trim( $('#zy').val());                                   //专业
	var jhgsRs=$.trim( $('#jhgsRs').val());                   // 计划工时人数
	var jhgsXss=$.trim( $('#jhgsXss').val());                //计划工时小时数
	var xfgdyy=$.trim( $('#xfgdyy').val());                    //下发工单原因
	var bz=$.trim( $('#bz').val());                                   //备注
	var zhut=$.trim( $('#zhut').val());        
	var jd=$.trim( $('#jd').val());
	var zjh=$.trim( $('#zjh').val());
	var glxx=$.trim( $('#glxx').val());
	var isXyjszy=$("input:radio[name='isXyjszy']:checked").val();  
	var tcsj=$.trim( $('#tcsj').val());
	var gzzw=$.trim( $('#gzzw').val());
	var gzz=$.trim( $('#gzz').val());
	var djgkid=$.trim( $('#djgkid').val());
	
	var workorder={};
	workorder.gdlx=gdlx;
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
	workorder.glxx=glxx;
	workorder.isXyjszy=isXyjszy;
	workorder.tcsj=tcsj;
	workorder.gzzw=gzzw;
	workorder.gzzid=gzz;
	workorder.djgkid=djgkid;
	
	var baseworkorder={};
	
	baseworkorder.wOActionObj=zxdxList(),                                                //封装执行对象的参数实体
    baseworkorder.woJobenclosure=getWoJobenclosureParenm(),        // 封装附件上传表的参数实体
	baseworkorder.orderSourceList=getPgdIdAndPgdh(),                           //封装指令来源参数实体
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
		ordersour.zlxl=4;
		orderSourceList.push(ordersour);
	});
	return orderSourceList;
}
//获取所有的适航指令号并拼接
function getShzlh(){
	var ckzl="";
	$("#Annunciatelist").find("tr input[name='Ckzl']").each(function(){
		ckzl+=$(this).val()+",";
	});
	ckzl=ckzl.substring(0,ckzl.length-1);
	return ckzl;
}


//获取执行对象
function zxdxList(){
	
	var WOActionObj={};
		
	    var zxfl=$("#zxfl").val();
		var fjzch=$("#fjzch").val();      
		var bjh=$("#bjh").val()==null?"":$("#bjh").val().split("^")[0];
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
			return;
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
			var tr=$(this);
			var bjid =$("input[name='id_1']").eq(index).val(); 
			var refJhly =$("input[name='refJhly_1']").eq(index).val(); 
			var zwmc =$("input[name='zwms_1']").eq(index).val(); 
			var ywmc =$("input[name='ywms_1']").eq(index).val(); 
			var jh =$("input[name='bjh_1']").eq(index).val();
			var sl =$("input[name='sl_1']").eq(index).val(); 
			var lx =tr.find("input[name='hclx_1']").val(); 
			var bz =$("input[name='bz_1']").eq(index).val(); 
			
			infos.bjid=bjid;
			infos.refJhly = refJhly;
			infos.zwmc = zwmc;
			infos.ywmc = ywmc;
			infos.jh = jh;
			infos.sl = sl;
			if(!hctype[lx]){
				infos.lx=lx;
			}else{
				infos.lx = hctype[lx];
			}
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
		 ZJJ:'装机件',
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
var uploadObj = $("#fileuploader").uploadFile(
		{
			url : basePath + "/common/ajaxUploadFile",
			multiple : true,
			dragDrop : false,
			showStatusAfterSuccess : false,
			showDelete : false,
			maxFileCount : 100,
			formData : {
				"fileType" : "workorder",
				"wbwjm" : function() {
					return $('#wbwjm').val();
				}
			},
			fileName : "myfile",
			returnType : "json",
			removeAfterUpload : true,
			uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
			uploadButtonClass: "ajax-file-upload_ext",
			onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
			{
				var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
				 trHtml = trHtml+'<td><div>';
				 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
				 trHtml = trHtml+'</div></td>';
				 trHtml = trHtml+'<td class="text-left" title=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'><input type="hidden" name="hzm" value=\''+data.attachments[0].nbwjm.split('.')[1]+'\'/>'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
				 
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

function yanzhen(obj,status){
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
}



//只能输入数字和小数,保留两位,小数后不能超过59
function clearNoNumTwoDate(obj){
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
   	 var value = obj.value;
   	 if(str[1].length == 2){
   		 if(str[1] > 59){
       		 value = str[0] +".59";
       	 }
   	 }
   	 if(str[1].length > 2){
   		 value = str[0] +"." + str[1].substring(0,2);
   	 }
   	 obj.value = value;
    }
}

//只能输入数字和冒号,保留两位,冒号后不能超过59
function clearNoNumTwoColon(obj){
	 //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d:]/g,"");
    //必须保证第一个为数字而不是:
    obj.value = obj.value.replace(/^\:/g,"");
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\:{2,}/g,":");
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(":","$#$").replace(/\:/g,"").replace("$#$",":");
    
    var str = obj.value.split(":");
    if(str.length > 1){
   	 var value = obj.value;
   	 if(str[1].length == 2){
   		 if(str[1] > 59){
       		 value = str[0] +":59";
       	 }
   	 }
   	 if(str[1].length > 2){
   		 value = str[0] +":" + str[1].substring(0,2);
   	 }
   	 obj.value = value;
    }
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
