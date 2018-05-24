var pgdids=[];
var hcids=[];
var gdids=[];          //重新修改之前用的是工单编号  存工单id
var gznrids=[];
var jkx=[];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var oldPgdList =[];
var glgdgknum = 1;
$(".panel-heading").not(":first").click(function(){         //隐藏和显示
	$(this).next().slideToggle("fast");
});

var diczt=DicAndEnumUtil.getEnumName('workOrderStateEnum',$("#gdzt").val());  //翻译工单状态
$("#gdzt").val(diczt);

$(function(){
	Navigation(menuCode,"修改工单","Modifly W/O");
	
	$('#pgdh-dialog').dialog('close');                              //评估单号的模态框
	$('#hcxx-dialog').dialog('close');                              //航材库的模态框
	//数据字典
	DicAndEnumUtil.registerDic('4','zy',$("#dprtcode_parma").val());      //专业
	DicAndEnumUtil.registerDic('4','zy2',$("#dprtcode_parma").val());      //专业
	DicAndEnumUtil.registerDic('3','zxfl',$("#dprtcode_parma").val());    //执行分类
	DicAndEnumUtil.registerDic('19','gzzw',$("#dprtcode_parma").val());    //工作站位
	
	$("#zy").val($("#zy-wo").val());
	
	var zxflvalue=$("#zxfl-wo").val()==""?"FJ":$("#zxfl-wo").val();
	if(zxflvalue=="FJ"){
		$("#zxfl").val("飞机");
	}else if(zxflvalue=="FZJJ"){
		$("#zxfl").val("非装机件");
	}else{
		$("#zxfl").val("装机件");
	}
	
	var gzzwvalue=$("#gzzw-wo").val()==""?"-":$("#gzzw-wo").val();
	$("#gzzw").val(gzzwvalue);
	
	sumTotal();
    
	$("#zxdxtr input").attr("disabled",true);
	$("#zxdxtr select").attr("disabled",true);
	
	if($("#zt").val()==1||$("#zt").val()==2||$("#zt").val()==3){            //通过工单的状态判断页面修改的内容
		$("select").attr("disabled",true);
		$("input").attr("disabled",true);
		$("textarea").attr("disabled",true);
		$("a").attr("disabled",true);
		$("button").not("[name='pgdBut']").css("display","none");
		$("#jjjj [name='hideTd']").css("display","none");
		$("#fileuploaderBut").css("display","none");
	}
	
	var len=$("#Annunciatelist").length;                                                          //判断机型的是否可编辑
	if(parseInt(len)>0){ 
		$("#fjjx").attr("disabled",true);
		$("#gdlx").attr("disabled",true);
	}
	
    var len2=$("#glgdCardList tr").length;                                                 //当关联工单不为空是 不能修改执行对象
	if(len2>0){
		$("#zxfl").attr("disabled",true);
		$("#fjzch").attr("disabled",true);
		$("#bjh").attr("disabled",true);
		$("#bjxlh").attr("disabled",true);
	}
	//将已经存在的评估单存放到数组中去
	$("#Annunciatelist").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var pgdid =$("input[name='pgdid']").eq(index).val();   
		pgdids.push(pgdid);
		oldPgdList=pgdids;
	});
	//将已经存在的工单存放到数组中去
	$("#glgdCardList").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var xggdid =$("input[name='xggdid']").eq(index).val();   
		gdids.push(xggdid);
		gdids.push($("#id").val());
	});
	
	
	//将已经存在的航材存放到数组中去
	$("#CKlist").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var bjid =$("input[name='bjid']").eq(index).val();   
		hcids.push(bjid);
	});
	//将已经存在的航材存放到数组中去
	$("#list").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var gznrid =$("input[name='gznrid']").eq(index).val();   
		gznrids.push(gznrid);
	});

	if($("#gkid").val()==""){
		$("#jobcard_demo").hide();
	}else{
		$("#jobcard_demo").show();
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
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
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
	
	loadWorkGroup();  //加载工作组
	
	$("#list", $("#addtr")).find("tr").each(function(index){
		$(this).find("td[name='workId']").html(index+1);
		workId++;
	});
	$("#CKlist", $("#airMaterialTools")).find("tr").each(function(index){
		$(this).find("td[name='hcnum']").html(index+1);
		hcnum++;
	});
	$("#glgdCardList", $("#relatedWorkOrder")).find("tr").each(function(index){
		$(this).find("td[name='glgdgknum']").html(index+1);
		glgdgknum++;
	});
	
	var dprtcode=$("#dprtcode_parma").val();
	if($("#gddlx").val()==2&&$("#zt").val()!=1&&$("#zt").val()!=2&&$("#zt").val()!=3){
		var fjzch2=$("#zxdxfjzch").val();
		 if(fjzch2!=null&&fjzch2!=""){
			   $("#fjzch").append("<option value="+StringUtil.escapeStr(fjzch2)+">"+StringUtil.escapeStr(fjzch2)+"</option>");
		   }else{
			   $("#fjzch").attr("disabled",true);
		   }
		AjaxUtil.ajax({                                                           //根据飞机注册号取得机型
			type : 'post',
			url : basePath+"/productionplan/planeData/findJx?fjzch="+encodeURIComponent(fjzch2)+"&dprtcode="+dprtcode,
			dataType : 'json',
			success : function(data) {
				if(data!=null){
					$("#fjjx").val(data.fjjx);
					if($("#gdlx").val()!=1){
						changeZXFL(data.fjjx);
					}else{
						if(fjzch!=null&&fjzch!=""){
							$("#fjzch").append("<option value="+StringUtil.escapeStr(fjzch2)+">"+StringUtil.escapeStr(fjzch2)+"</option>");
						}
						
						$("#bjh").append("<option value="+StringUtil.escapeStr($("#zxdxbjh").val())+">"+StringUtil.escapeStr($("#zxdxbjh").val())+"</option>");
						$("#bjxlh").append("<option value="+StringUtil.escapeStr($("#zxdxbjxlh").val())+">"+StringUtil.escapeStr($("#zxdxbjxlh").val())+"</option>");
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
				}
			}
		});
	}else{
	   var gczlid=$("#gczlid").val();
	   var fjzch2=$("#zxdxfjzch").val();
	   if(fjzch2!=null&&fjzch2!=""){
		   $("#fjzch").append("<option value="+StringUtil.escapeStr(fjzch2)+">"+StringUtil.escapeStr(fjzch2)+"</option>");
	   }else{
		   $("#fjzch").attr("disabled",true);
	   }
	   AjaxUtil.ajax({                                                           //根据飞机注册号取得机型
			type : 'post',
			url : basePath+"/project/workorder/getJx?gczlid="+gczlid,
			dataType : 'json',
			success : function(data) {
				if(data!=null){
					$("#fjjx").val(data.fjjx);
				}
			}
		});
		
		$("#bjh").append("<option value="+StringUtil.escapeStr($("#zxdxbjh").val())+">"+StringUtil.escapeStr($("#zxdxbjh").val())+"</option>");
		$("#bjxlh").append("<option value="+StringUtil.escapeStr($("#zxdxbjxlh").val())+">"+StringUtil.escapeStr($("#zxdxbjxlh").val())+"</option>");
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
});
function ViewJobCard(){
	 var id= $("#temp_id").val();
	 var dprtcode= $("#temp_dprtcode").val();
	 window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode="+dprtcode);
}

//获取工作组
function loadWorkGroup(){
	var obj={};
	obj.dprtcode=$("#dprtcode_parma").val();
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
					   if(list.id==$("#gzzid").val()){
						  apps += "<option value='"+list.id+"' selected='selected'>"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
					   }else{
						  apps += "<option value='"+list.id+"'>"+StringUtil.escapeStr(list.gzzdm)+" "+StringUtil.escapeStr(list.gzzmc)+"</option>";
					   }
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

function changeBjxlh(){
	var fjzch = $("#fjzch").val();
	var bjxlh = $("#bjxlh").val();
	var bjh = $("#bjh").val().split("^")[0];
	if(fjzch != null && fjzch != ''){
		$.each(zjqdArr,function(i,obj){
			if(fjzch == obj.fjzch && bjh == obj.jh && bjxlh == obj.xlh){
			  $("#zjqdid").val(obj.id);
			}
		});
	}
}

//针对时控件和定检工单动态加载监控项
function loadJkxm(){
	var num=0;
    if($("#old_jkxmbhF").val()!=null&&$("#old_jkxmbhF").val()!=""){
    	num++;
    }
    if($("#old_jkxmbhS").val()!=null&&$("#old_jkxmbhS").val()!=""){
    	num++;
    }
    if($("#old_jkxmbhT").val()!=null&&$("#old_jkxmbhT").val()!=""){
    	num++;
    }
    if(num==3){
    	$("#th_1").css("display", "none");
		$("#th_2").css("display", "none");
		$("#th_3").css("display", "none");
		$("#th_4").css("display", "none");
    	$("#div_1").attr("colspan", 3);
    	
    	$("#value_1").text($("#old_jkxmbhF").val());
    	$("#wcrq").val($("#old_jkzF").val());
    	
    	var zwmc1=$("#value_1").html();
    	$("#value_4").text(transferEnglish(zwmc1));
    	
    	$("#value_2").text($("#old_jkxmbhS").val());
    	$("#jssj").val($("#old_jkzS").val());
    	
    	var zwmc2=$("#value_2").html();
    	$("#value_5").text(transferEnglish(zwmc2));
    	
    	$("#value_3").text($("#old_jkxmbhT").val());
    	$("#qlxh").val($("#old_jkzT").val());
    	
    	var zwmc3=$("#value_3").html();
    	$("#value_6").text(transferEnglish(zwmc3));
    	
    	$("#zxdxTb").removeAttr("style");
    	$("#zxdxDiv").removeAttr("style");
    	
    }else if(num==2){
    	$("#th_1").css("display", "none");
		$("#th_2").css("display", "none");
		$("#th_3").css("display", "none");
		$("#th_4").css("display", "none");
		$("#th_7").css("display", "none");
		$("#th_5").css("display", "none");
		
    	$("#div_1").attr("colspan", 2);
    	if($("#old_jkxmbhF").val()==null||$("#old_jkxmbhF").val()==""){
    		$("#value_1").text($("#old_jkxmbhS").val());
    		$("#wcrq").val($("#old_jkzS").val());
    		
    		var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    		
    		$("#value_2").text($("#old_jkxmbhT").val());
    		$("#jssj").val($("#old_jkzT").val());
    		
    		var zwmc2=$("#value_2").html();
        	$("#value_5").text(transferEnglish(zwmc2));
    		
    	}else if($("#old_jkxmbhT").val()==null||$("#old_jkxmbhT").val()==""){
    		$("#value_1").text($("#old_jkxmbhF").val());
    		$("#wcrq").val($("#old_jkzF").val());
    		
    		var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
        	
    		$("#value_2").text($("#old_jkxmbhS").val());
    		$("#jssj").val($("#old_jkzS").val());
    		
    		var zwmc2=$("#value_2").html();
        	$("#value_5").text(transferEnglish(zwmc2));
        	
    	}else{
    		$("#value_1").text($("#old_jkxmbhF").val());
    		$("#wcrq").val($("#old_jkzF").val());
    		$("#value_2").text($("#old_jkxmbhT").val());
    		$("#jssj").val($("#old_jkzT").val());
    	}
    	$("#zxdxTb").removeAttr("style");
    	$("#zxdxDiv").removeAttr("style");
    }else{
    	$("#th_1").css("display", "none");
		$("#th_2").css("display", "none");
		$("#th_3").css("display", "none");
		$("#th_4").css("display", "none");
		$("#th_7").css("display", "none");
		$("#th_5").css("display", "none");
		$("#th_8").css("display", "none");
		$("#th_6").css("display", "none");
		
    	$("#div_1").attr("colspan", 1);
    	
    	if($("#old_jkxmbhF").val()!=null&&$("#old_jkxmbhF").val()!=""){
    		$("#value_1").text($("#old_jkxmbhF").val());
        	$("#wcrq").val($("#old_jkzF").val());
        	var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    	}else if($("#old_jkxmbhT").val()!=null&&$("#old_jkxmbhT").val()!=""){
    		$("#value_1").text($("#old_jkxmbhT").val());
        	$("#wcrq").val($("#old_jkzT").val());
        	var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    	}else{
    		$("#value_1").text($("#old_jkxmbhS").val());
        	$("#wcrq").val($("#old_jkzS").val());
        	var zwmc1=$("#value_1").html();
        	$("#value_4").text(transferEnglish(zwmc1));
    	}
    	$("#zxdxTb").removeAttr("style");
    	$("#zxdxDiv").removeAttr("style");
    }
}
function transferEnglish(zwmc){
	var ywmc1="";
	switch(zwmc){
		case "日历":
		    ywmc1="Calendar";
			break;
		case "机身飞行时间":
		    ywmc1="Flight HRS.";
			break;
		case "搜索灯时间":
		    ywmc1="Search Time";
			break;
		case "绞车时间":
			ywmc1="Winch_Time";
			break;
		case "起落循环":
		    ywmc1="Flight CYCS.";
			break;
		case "绞车循环":
		    ywmc1="Winch Cycle";
			break;
		case "外吊挂循环":
			ywmc1="E/S CYCS.";
			break;
		case "特殊监控1":
			ywmc1="TS1";
			break;
		case "特殊监控2":
			ywmc1="TS2";
			break;
		case "N1":
			ywmc1="N1";
			break;
		case "N2":
			ywmc1="N2";
			break;
		case "N3":
			ywmc1="N3";
			break;
		case "N4":
			ywmc1="N4";
			break;
		case "N5":
			ywmc1="N5";
			break;
		case "N6":
			ywmc1="N6";
			break;
	}
	return ywmc1;
}

function hideDiv(){
	$("#demo2").css("display", "none");  
	$("#demo4").css("display", "none");  
	$("#demo5").css("display", "none");  
	$("#demo6").css("display", "none");
	$("#zxdxtr select").attr("disabled",true);
	$("#zxdxtr input").attr("disabled",true);
}

function judge(){
	if($("#zxfl").val()=="FZJJ"){
		$('#jssj').attr("disabled","true");
		$('#qlxh').attr("disabled","true");
		$('#jcsj').attr("disabled","true");
		$('#jcxh').attr("disabled","true");
	}
	
	if($("#gdlx").val()!=1&&$("#gddlx").val()!=3){
		var $wcrq=$('#wcrq');
		$wcrq.removeAttr("readonly");
		$('#wcrq').removeAttr("disabled","true");
		var wcrq=$wcrq.val();
		
		var $jssj=$('#jssj');
		$jssj.removeAttr("readonly");
		var jssj=$('#jssj').val();
		
		var $qlxh=$('#qlxh');
		$qlxh.removeAttr("readonly");
		var qlxh=$('#qlxh').val();
		
		var $jcsj=$('#jcsj');
		$jcsj.removeAttr("readonly");
		var jcsj=$('#jcsj').val();
		
		var $jcxh=$('#jcxh');
		$jcxh.removeAttr("readonly");
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
}
//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	judge();
};



//删除行
function lineRemove(pgdid){
	$('#tr1_'+pgdid).remove();
	pgdids.remove(pgdid);
	var len=$("#Annunciatelist tr").length;
    if(len==0){
    	$("#fjjx").attr("disabled",false);
    	$("#gdlx").attr("disabled",false);
    	
    	$("#fjzch").val("");
    	$("#bjh").val("");
    	$("#bjxlh").val("");
    	$("#zxdxtr input").val("");
    }
}
function lineRemovePgd(obj){
	var y=$(obj).parent().parent().find('input:first');     //获取当前行元素
	$(obj).parent().parent().remove();  
	pgdids.remove($(y).val());
	var len=$("#Annunciatelist tr").length;
    if(len==0){
    	$("#fjjx").attr("disabled",false);
    	$("#gdlx").attr("disabled",false);
    	
    	$("#fjzch").val("");
    	$("#bjh").val("");
    	$("#bjxlh").val("");
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

var id=1;
var workId=1; 
 function addTr(){
	   var htmlContent = '';
	   htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
	   htmlContent = htmlContent + "<td style='vertical-align:middle' class='hideTd'><button class='line6' onclick='lineRemoveGznr(this)'><i class='icon-minus color-blue cursor-pointer'></i></button></td>" ;
	   htmlContent = htmlContent + "<td style='vertical-align:middle' name='workId'>"+workId+"</td>";  
	   htmlContent = htmlContent + "<td><input type='hidden' class='form-control' value='"+id+"' name='gznrid'/><textarea maxlength='1300' type='text' placeholder='最大长度不超过1300' class='form-control' rows='3' name='gznr'></textarea></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input maxlength='6'class='form-control' type='text' name='gzz'/></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='isBj' type='checkbox'  style='width: 20px;height: 20px;' /></td>";  
	   htmlContent = htmlContent + "</tr>";  
	   id++;
	   workId++;
	   $("#list").append(htmlContent);
 }
 
 function lineRemoveGznr(obj) {                                                          //删除工作内容的当前行
	    $(obj).parent().parent().remove();   
	    workId -- ;
	    $("#list", $("#addtr")).find("tr").each(function(index){
			$(this).find("td[name='workId']").html(index+1);
		});
	}

 /**************************************航材工具信息加载start******************************/
//信息检索航材
function searchRevision(){
	goPage4(1,"auto","desc");
}

function changeHclx(){
	goPage4(1,"auto","desc");
}

function openHcxxList() {
	 goPage4(1,"auto","desc");
	 $("#alertModalHcxx").modal("show");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage4(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch4(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch4(pageNumber,sortType,sequence){
    var obj ={};
	obj.hcids=hcids;//已选择的航次信息id
	obj.hclxs =$.trim($("#hclx").val());
	obj.keyword = $.trim($("#keyword_search").val());
	obj.dprtcode = $.trim($("#dprtcode_parma").val());
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
			   appendContentHtml4(data.rows);
			   new Pagination({
					renderTo : "pagination4",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(a,b,c){
						AjaxGetDatasWithSearch4(a,c,b);
					}
				});
			   // 标记关键字
			   signByKeyword($("#keyword_search").val(),[2,3,4],"#Hcxxlist tr td");
		   } else {
			  $("#Hcxxlist").empty();
			  $("#pagination4").empty();
			  $("#Hcxxlist").append("<tr><td colspan=\"7\">暂无数据 No data.</td></tr>");
		   }
   },
 }); 
	
}
function appendContentHtml4(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
		   }
						   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='hcxx'onclick='chooesRow2(this)'><input type='hidden'  name='hcid' value='"+formatUndefine(row.id)+"'>" +
						    "<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.zwms)+"'>" +
					   		"<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(row.ywms)+"'>" +
					   		"<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(row.bjh)+"'>" +
					   		"<input type='hidden' name='kykcsl' value='"+formatUndefine(row.kykcsl==null?0:row.kykcsl)+"'>" +
					   		"<input type='hidden' name='hclx' value='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"'>" +
					   		"</td>";
				htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>"; 
				htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
				htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
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
				
				htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.jldw)+"</td>";  
				htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
				htmlContent = htmlContent + "</tr>";  
				});
				//$("#Pgdlist").empty();
				$("#Hcxxlist").html(htmlContent);
}
//保存航材信息
var xuhao=1;
var hcnum=1;
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
		
		var hclx2=hctype[hclx];
			
		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" name='wohchc' bgcolor=\"#fefefe\"  id='tr1_"+hcid+"'>";
		htmlContent = htmlContent + "<td><a href=javascript:lineRemove2('"+hcid+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle' name='hcnum'>"+hcnum+"</td>"; 
		htmlContent = htmlContent + "<td ><input  maxlength='100'  name='refJhly_1' type='text' class='form-control'></td>";  
		
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(ywms)+"'><input type='hidden'  name='ywms_1' value='"+StringUtil.escapeStr(ywms)+"'/>"+StringUtil.escapeStr(ywms)+"</td>"; 
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(zwms)+"' ><input type='hidden'  name='wohcid' value='"+xuhao+"'/><input type='hidden'  name='bjid' value='"+hcid+"'/><input type='hidden'  name='zwms_1' value='"+StringUtil.escapeStr(zwms)+"'/>"+StringUtil.escapeStr(zwms)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(bjh)+"'><input type='hidden'  name='bjh_1' value='"+StringUtil.escapeStr(bjh)+"'/>"+StringUtil.escapeStr(bjh)+"</td>";  
		htmlContent = htmlContent + "<td class='text-right' style='vertical-align:middle' >"+kycksl+"</td>";
		
		htmlContent += "<td class='text-right' style='vertical-align:middle' >";
		   if(dxtdsl != 0){
			   htmlContent += "<a href='javascript:void(0);' bjh='"+StringUtil.escapeStr(bjh)+"' onclick=\"viewTdkc(this)\">"+dxtdsl+"</a>";
		   }else{
			   htmlContent += dxtdsl;
		   }
		htmlContent += "</td>";
		
		htmlContent = htmlContent + "<td class='text-left' ><input   onkeyup='clearNoNum(this)' name='sl_1' type='text' class='form-control'></td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'><input type='hidden'  name='hclx_1' value='"+hclx2+"'/>"+hclx+"</td>";  
		htmlContent = htmlContent + "<td ><input name='bz_1' type='text'  maxlength='300' placeholder='最大长度不超过300' class='form-control'></td>";   
		htmlContent = htmlContent + "</tr>";
		hcids.push(hcid);
    	xuhao++;
    	hcnum++;
	 });
		//$("#Annunciatelist").empty();
	    $("#CKlist").append(htmlContent);
}

//查看替代部件详情
function viewTdkc(this_){
	var bjh = $(this_).attr("bjh");
	Work_Material_View_Modal.show({
		bjh : bjh,//
		dprtcode : $.trim( $('#zzjgid').val())
	});
}

/**************************************航材工具信息加载end******************************/
//删除行
function lineRemove2(hcid){
	$('#tr1_'+hcid).remove();
	hcids.remove(hcid);
	hcnum--;
	$("#CKlist", $("#airMaterialTools")).find("tr").each(function(index){
		$(this).find("td[name='hcnum']").html(index+1);
	});
}
function lineRemoveHc(obj){
	var y=$(obj).parent().parent().find("input[name='bjid']");     //获取当前行元素
	$(obj).parent().parent().remove();   
	hcids.remove($(y).val());
	hcnum--;
	$("#CKlist", $("#airMaterialTools")).find("tr").each(function(index){
		$(this).find("td[name='hcnum']").html(index+1);
	});
}

/**************************************相关工单start******************************/
//信息检索关联工单
function searchRevision3(){
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
		return false;
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
    var obj ={};
	obj.gdids=gdids;   //已选择的工单的工单编号
	obj.gddlx =$.trim($("#gddlx2").val());
	var zy=$.trim($("#zy2").val());
	if(zy!="all"){
		obj.zy=zy;
	}
	obj.keyword = $.trim($("#keyword_search2").val());
	obj.dprtcode = $.trim($("#dprtcode_parma").val());
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
					renderTo : "pagination3",
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
			  $("#pagination3").empty();
			  $("#glgdCardlist2").append("<tr><td colspan=\"5\">暂无数据 No data.</td></tr>");
		   }
       },
 }); 
	
}
function appendContentHtml3(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
		   }
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='wo_glgd' onclick='chooesRow2(this)'><input type='hidden'  name='gdbh' value='"+StringUtil.escapeStr(row.gdbh)+"'>" ;
																		   if(row.gddlx==2){
																			   htmlContent = htmlContent + "<input type='hidden' name='gdlx' value='"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',row.gdlx)+"'>";  
																			   }else{
																			  htmlContent = htmlContent + "<input type='hidden' name='gdlx' value='"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+''+"'>";  
																			   }
																		   htmlContent = htmlContent + "<input type='hidden' name='zy' value='"+formatUndefine(row.zy)+"'>";
																		   htmlContent = htmlContent + "<input type='hidden' name='zhut' value='"+StringUtil.escapeStr(row.zhut)+"'>";
																		   htmlContent = htmlContent + "<input type='hidden' name='id' value='"+formatUndefine(row.id)+"'>";
																		   htmlContent = htmlContent + "<input type='hidden' name='gdjcid' value='"+formatUndefine(row.gdjcid)+"'></td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.gdbh)+"</td>";
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
var xggdid=1;
function appendGlgd(){
	
	var htmlContent = ""; 
	$('input[name=wo_glgd]:checked').each(function(){  
		var gdbh=$(this).next().val();
		var gddlxAndgdlx=$(this).next().next().val();
		var gddlx=gddlxAndgdlx.split("-")[0];
		var gdlx=gddlxAndgdlx.split("-")[1]==null?"":gddlxAndgdlx.split("-")[1];
		var zy=$(this).next().next().next().val();
    	var zhut=$(this).next().next().next().next().val();
		var id=$(this).next().next().next().next().next().val();
		var gdjcid=$(this).next().next().next().next().next().next().val();
		
		var gdlx2 = "";
		
		var gddlx2=wotype1[gddlx];
		if(gdlx!="" && gdlx!=null){
			gdlx2=wotype2[gdlx];
		}
		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+id+"'>";
		htmlContent = htmlContent + "<td><a href=javascript:lineRemove3('"+id+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
		htmlContent = htmlContent + "<td name='glgdgknum'>"+glgdgknum+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' name='gdbh'><input type='hidden' name='id' value="+xggdid+" />"+gdbh+"</td>";  
		htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='xggdlx'  value="+gddlx2+" /><input type='hidden' name='xggdzlx' value='"+gdlx2+"' /><input type='hidden' name='xggdid' value="+id+" /><input type='hidden' name='xggdjcid' value="+gdjcid+" />"+gddlxAndgdlx+"</td>";  
		htmlContent = htmlContent + "<td name='zy'>"+StringUtil.escapeStr(zy)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(zhut)+"'>"+StringUtil.escapeStr(zhut)+"</td>";
		htmlContent = htmlContent + "</tr>";
		gdids.push(id);
		xggdid++;
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
/**************************************相关工单end******************************/
//删除行
function lineRemove3(id){
	$('#tr1_'+id).remove();
	gdids.remove(id);
   var len=$("#glgdCardList tr").length;
	if(len==0&&$("#gdlx").val()!=1){
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
function lineRemoveXggd(obj){
	var y=$(obj).parent().parent().find('input:first');     //获取当前行元素
	$(obj).parent().parent().remove(); 
	gdids.remove($(y).val());
   var len=$("#glgdCardList tr").length;
	if(len==0&&$("#gdlx").val()!=1){
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


/**************************************执行对象信息加载start******************************/
//打开执行对象对话框
function openZxdx() {
	 goPage2(1,"auto","desc");
	 var mainid=$("#gczlid").val();
		if(mainid==null||mainid==""){
			AlertUtil.showErrorMessage("请先选择工程指令！");
			return;
		}else{
	         $("#alertModalZxdx").modal("show");
		}
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage2(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch2(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch2(pageNumber,sortType,sequence){
	var obj ={};
	var mainid=$("#gczlid").val();
		obj.mainid=mainid;
		obj.dprtcode = $.trim($("#dprtcode_parma").val());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/project/engineering/queryDetailEnginer",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				finishWait();
				if(data.rows.length>0){
					appendContentHtml2(data.rows);
				} else {
					$("#Zxdxbody").empty();
					$("#Zxdxbody").append("<tr><td colspan=\"5\">暂无数据 No data.</td></tr>");
				}
			},
		}); 
}
function appendContentHtml2(list){
	   var htmlContent = '';
	   var gczlzxdxid =$("#gczlzxdxid").val();
	   $.each(list,function(index,row){
		   
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)'>";
		   }
		   if(gczlzxdxid==row.id){
			   htmlContent = htmlContent + "<td><input type=\"radio\" name='chose' checked='true' onclick='chooesRow2(this)'><input type='hidden' value='"+formatUndefine(row.id)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.zxfl)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.fjzch)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.bjh)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.bjxlh)+"'>" +
			   
			   "<input type='hidden' value='"+formatUndefine(row.jkxmbhF)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkflbhF)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkzF)+"'>" +
			   
			   "<input type='hidden' value='"+formatUndefine(row.jkxmbhS)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkflbhS)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkzS)+"'>" +
			   
			   "<input type='hidden' value='"+formatUndefine(row.jkxmbhT)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkflbhT)+"'>";
			   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkzT)+"'>";
			   htmlContent += "<input type='hidden' name='zjqdid' value='"+formatUndefine(row.zjqdid)+"'>";
			   htmlContent += "<input type='hidden' name='bjms' value='"+StringUtil.escapeStr(row.bjms)+"'>";
			   htmlContent += "</td>";
		   }else{
			   htmlContent = htmlContent + "<td><input type=\"radio\" name='chose' onclick='chooesRow2(this)'><input type='hidden' value='"+formatUndefine(row.id)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.zxfl)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.fjzch)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.bjh)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.bjxlh)+"'>" +
			   
			   "<input type='hidden' value='"+formatUndefine(row.jkxmbhF)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkflbhF)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkzF)+"'>" +
			   
			   "<input type='hidden' value='"+formatUndefine(row.jkxmbhS)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkflbhS)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkzS)+"'>" +
			   
			   "<input type='hidden' value='"+formatUndefine(row.jkxmbhT)+"'>" +
			   "<input type='hidden' value='"+formatUndefine(row.jkflbhT)+"'>";
			   htmlContent += "<input type='hidden' value='"+formatUndefine(row.jkzT)+"'>";
			   htmlContent += "<input type='hidden' name='zjqdid' value='"+formatUndefine(row.zjqdid)+"'>";
			   htmlContent += "<input type='hidden' name='bjms' value='"+StringUtil.escapeStr(row.bjms)+"'>";
			   htmlContent += "<input type='hidden' name='jhgsrs' value='"+formatUndefine(row.jhgsRs)+"'>";
			   htmlContent += "<input type='hidden' name='jhgsxss' value='"+formatUndefine(row.jhgsXss)+"'>";
			   htmlContent += "</td>";
		   }
		   htmlContent = htmlContent + "<td>"+formatUndefine(zxfltype[row.zxfl])+"</td>";  
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.bjh)+"</td>";  
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.bjxlh)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#Zxdxbody").html(htmlContent);
	 
}

//保存执行对象
function appendZxdx(){
	$('input[name=chose]:checked').each(function(){   
		/*$("#th_bjmc").css("display", "block");
		$("#td_bjmc").css("display", "block");*/
		var id=$(this).next().val();
		var zxfl=$(this).next().next().val();
		var fjzch=$(this).next().next().next().val();
		var bjh=$(this).next().next().next().next().val();
		var bjhxlh=$(this).next().next().next().next().next().val();

		var jkxmbhF=$(this).next().next().next().next().next().next().val();
	//	var jkflbhF=$(this).next().next().next().next().next().next().next().val();
		var jkzF=$(this).next().next().next().next().next().next().next().next().val();
		
		var jkxmbhS=$(this).next().next().next().next().next().next().next().next().next().val();
	//	var jkflbhS=$(this).next().next().next().next().next().next().next().next().next().next().val();
		var jkzS=$(this).next().next().next().next().next().next().next().next().next().next().next().val();
		
		var jkxmbhT=$(this).next().next().next().next().next().next().next().next().next().next().next().next().val();
	//	var jkflbhT=$(this).next().next().next().next().next().next().next().next().next().next().next().next().next().val();
		var jkzT=$(this).next().next().next().next().next().next().next().next().next().next().next().next().next().next().val();
		
		var bjms=$(this).siblings("input[name='bjms']").val();
		var zjqdid=$(this).siblings("input[name='zjqdid']").val();
		
		var jhgsrs=$(this).siblings("input[name='jhgsrs']").val();
		var jhgsxss=$(this).siblings("input[name='jhgsxss']").val();
		$("#jhgsRs").val(jhgsrs);
		$("#jhgsXss").val(jhgsxss);
		$("#time").val(jhgsxss*jhgsrs);
		
		
		$("#zxdxtr select").val("");
		$("#zxdxtr input").val("");
		$("#zjqdid").val("");
		$("#gczlzxdxid").val(id);
		$("#zlmxid").val(id);
		
		$("#zxfl").append("<option value="+zxfl+" selected='selected'>"+zxfltype[zxfl]+"</option>");
		if(fjzch!=null&&fjzch!=""){
			$("#fjzch").append("<option value="+fjzch+" selected='selected'>"+fjzch+"</option>");
		}
		
		$("#bjh").append("<option value="+bjh+" selected='selected'>"+bjh+"</option>");
		$("#bjxlh").append("<option value="+bjhxlh+" selected='selected'>"+bjhxlh+"</option>");
		$("#bjmc").val(bjms);
		$("#zjqdid").val(zjqdid);
		var dprtcode=$("#dprtcode_parma").val();
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
/**************************************执行对象信息加载end******************************/


function chooesRow2(obj){
	chooesRowPGD($(obj));
}

function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='checkbox']");
	chooesRowPGD(obj);
}
function chooesRow3(objradio){
	var obj = $(objradio).find("input[type='radio']");
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



/**************************************工程指令信息加载strat******************************/
//检索 工程指令
function searchRevision5(){
	goPage5(1,"auto","desc");
}

function openGczl(){
	goPage5(1,"auto","desc");
	$("#alertModalGczl").modal("show");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage5(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch5(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch5(pageNumber,sortType,sequence){
   var obj ={};
    obj.fjjx = $.trim($("#fjjx").val());
	obj.keyword = $.trim($("#keyword_search3").val());
	obj.dprtcode = $.trim($("#dprtcode_parma").val());
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
			   appendContentHtml5(data.rows);
			   new Pagination({
					renderTo : "pagination5",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(a,b,c){
						AjaxGetDatasWithSearch5(a,b,c);
					}
				});
			   // 标记关键字
			   signByKeyword($("#keyword_search3").val(),[2],"#Gczlbody tr td");
		   } else {
			  $("#Gczlbody").empty();
			  $("#pagination4").empty();
			  $("#Gczlbody").append("<tr><td colspan=\"7\">暂无数据 No data.</td></tr>");
		   }
   },
 }); 
}
function appendContentHtml5(list){
	   var htmlContent = '';
	   var gczlid=$('#gczlid').val();
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)'>";
		   }
		   if(gczlid==row.id){
			   htmlContent = htmlContent + "<td><input type=\"radio\" name='zlid' checked='true' onclick='chooesRow2(this)'>" +
			   "<input type='hidden'  name='id' value='"+formatUndefine(row.id)+"'>" +
			   "<input type='hidden'  name='gczlbh' value='"+StringUtil.escapeStr(row.gczlbh)+"'>" +
			//   "<input type='hidden'  name='gczlbh' value='"+formatUndefine(row.gczlzxdxid)+"'>" +
			   "<input type='hidden' name='zhut' value='"+StringUtil.escapeStr(row.zhut)+"'>"+
			   "<input type='hidden' name='fjjx' value='"+StringUtil.escapeStr(row.fjjx)+"'></td>";
		   }else{
			   htmlContent = htmlContent + "<td><input type=\"radio\" name='zlid' onclick='chooesRow2(this)'>" +
			   "<input type='hidden'  name='id' value='"+formatUndefine(row.id)+"'>" +
			   "<input type='hidden'  name='gczlbh' value='"+StringUtil.escapeStr(row.gczlbh)+"'>" +
		   //    "<input type='hidden'  name='gczlbh' value='"+formatUndefine(row.gczlzxdxid)+"'>" +
			   "<input type='hidden' name='zhut' value='"+StringUtil.escapeStr(row.zhut)+"'>"+
			   "<input type='hidden' name='fjjx' value='"+StringUtil.escapeStr(row.fjjx)+"'></td>";
		   }
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.gczlbh)+"'>"+StringUtil.escapeStr(row.gczlbh)+"</td>";  
		   htmlContent = htmlContent + "<td  class='text-left' title='"+StringUtil.escapeStr(row.ckzl)+"'>"+StringUtil.escapeStr(row.ckzl)+"</td>";
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.zwms)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.xggzh)+"' class='text-left'>"+StringUtil.escapeStr(row.xggzh)+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zhut)+"' class='text-left'>"+StringUtil.escapeStr(row.zhut)+"</td>"; 
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
		var zhut=$(this).next().next().next().val();
		var fjjx=$(this).next().next().next().next().val();
		$("#gczlbh").val(gczlbh);                             //将选中的值赋给jsp中的工程指令编号
		$("#gczlid").val(id);
		$("#wjzt").val(zhut);
		$("#fjjx").val(fjjx);
		
		$("#zxdxtr select").val("");
		$("#zxdxtr input").val("");
	 });
}
/**************************************工程指令信息加载end******************************/
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
		AlertUtil.showErrorMessage("执行对象不能为空！");
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

	var id=$.trim( $('#id').val());                       
	var gddlx=$.trim( $('#gddlx').val());                               //工单大类型
	
	var gdjcid=$.trim( $('#gdjcid').val());                               //工单基础id
	var zdrid=$.trim( $('#zdrid').val());                               //制单人id
	var gdbh=$.trim( $('#gdbh').val());                               //制单人id
	var zy=$.trim( $('#zy').val());                                      //专业
	var jd=$.trim( $('#jd').val());                                      //专业
	var zjh=$.trim( $('#zjh').val());                                   //ATA章节号
	var jhgsRs=$.trim( $('#jhgsRs').val());                     // 计划工时人数
	var jhgsXss=$.trim( $('#jhgsXss').val());                //计划工时小时数
	var xfgdyy=$.trim( $('#xfgdyy').val());                     //下发工单原因
	var zhut=$.trim( $('#wjzt').val());        
	var bz=$.trim( $('#bz').val());                                            //备注
	var gzzw=$.trim( $('#gzzw').val());  
	var gzz=$.trim( $('#gzz').val()); 
	var gczlid=$.trim( $('#gczlid').val());        
	var gczlbh=$.trim( $('#gczlbh').val());        
	var gczlzxdxid=$.trim( $('#gczlzxdxid').val());        

	var workorder={};
		workorder.id=id;
		workorder.gdbh=gdbh;
		workorder.gddlx=gddlx;
		workorder.gdjcid=gdjcid;
		workorder.zdrid=zdrid;
		workorder.zy=zy;
		workorder.jd=jd;
		workorder.zjh=zjh;
		workorder.jhgsRs=jhgsRs;
		workorder.jhgsXss=jhgsXss;
		workorder.xfgdyy=xfgdyy;
		
	
		workorder.bz=bz;
		workorder.gzzw=gzzw;
		workorder.gzzid=gzz;
		workorder.zhut=zhut;
		workorder.zt=zt;
		
		workorder.gczlid=gczlid;
		workorder.gczlbh=gczlbh;
		workorder.gczlzxdxid=gczlzxdxid;
		
	
	var baseworkorder={};
	
	baseworkorder.wOActionObj=zxdxList(),                                                //封装执行对象的参数实体
    baseworkorder.woJobenclosure=getWoJobenclosureParenm(),        // 封装附件上传表的参数实体
	baseworkorder.woAirMaterial=getWOAirMaterialParam(),                 //获取航材耗材的参数实体
    baseworkorder.woJobContent=getWOJobContentParam(),                //获取工作内容的参数实体
	baseworkorder.nonwocardList=getNonwocardListParam(),                //获取相关工卡的参数实体

	workorder.baseWorkOrder=baseworkorder;
	
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/project/workorder/Edit",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(workorder),
		dataType:"json",
		success:function(data){
			if(data>0){
				finishWait();
				if(zt==0){
					AlertUtil.showMessage('保存成功!','/project/workorder/main?id='+id+'&pageParam='+pageParam);
				}else{
					AlertUtil.showMessage('提交成功!','/project/workorder/main?id='+id+'&pageParam='+pageParam);
				}
			}else{
				finishWait();
				AlertUtil.showMessage('该工单已更新，请刷新后再进行操作!','/project/workorder/main?id='+id+'&pageParam='+pageParam);
			}
		},
	});
	
}

//返回前页面
function back(){
	 window.location.href =basePath+"/project/workorder/main?pageParam="+pageParam;
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
function transferChinese(zwmc){
	var ywmc1="";
	var ywmc2="";
	switch(zwmc){
		case "日历":
    	    ywmc1="calendar";
    	    ywmc2="calendar";
    		break;
		case "机身飞行时间":
    	    ywmc1="flight_time";
    	    ywmc2="fuselage_flight_time";
    		break;
		case "搜索灯时间":
    	    ywmc1="flight_time";
    	    ywmc2="search_light_time";
    		break;
		case "绞车时间":
			ywmc1="flight_time";
			ywmc2="winch_time";
			break;
		case "起落循环":
    	    ywmc1="loop";
    	    ywmc2="landing_gear_cycle";
    		break;
		case "绞车循环":
    	    ywmc1="loop";
    	    ywmc2="winch_cycle";
    		break;
		case "外吊挂循环":
			ywmc1="loop";
			ywmc2="ext_suspension_loop";
			break;
		case "特殊监控1":
			ywmc1="loop";
			ywmc2="special_first";
			break;
		case "特殊监控2":
			ywmc1="loop";
			ywmc2="special_second";
			break;
		case "N1":
			ywmc1="N1";
			ywmc2="N1";
			break;
		case "N2":
			ywmc1="N2";
			ywmc2="N2";
			break;
		case "N3":
			ywmc1="N3";
			ywmc2="N3";
			break;
		case "N4":
			ywmc1="N4";
			ywmc2="N4";
			break;
		case "N5":
			ywmc1="N5";
			ywmc2="N5";
			break;
		case "N6":
			ywmc1="N6";
			ywmc2="N6";
			break;
	}
	return ywmc1+"+"+ywmc2;
}

//获取执行对象
function zxdxList(){
	var WOActionObj={};
	
	    var zxfl=$("#zxfl").val();
		var fjzch=$("#fjzch").val();      
		var bjh=$("#bjh").val()==null?"":$("#bjh").val().split("^")[0];
		var bjxlh=$("#bjxlh").val();
		var zxdxid=$("#zxdxid").val();
		
		var zjqdid=$("#zjqdid").val();
		var zjqdid_temp=$("#zjqdid_temp").val();
		if(zxfl=="ZJJ"){
			if(zjqdid==null||zjqdid==""){
				WOActionObj.zjqdid=zjqdid_temp;
			}else{
				WOActionObj.zjqdid=zjqdid;
			}
		}
		
		WOActionObj.id=zxdxid;
		WOActionObj.zxfl=zxfl;
		WOActionObj.fjzch=fjzch;
		WOActionObj.bjh=bjh;
		WOActionObj.bjxlh=bjxlh;
		
		var wcrq=$("#wcrq").val();
		var jssj=$("#jssj").val();
		var qlxh=$("#qlxh").val();
		var jcsj=$("#jcsj").val();
		var jcxh=$("#jcxh").val();
		if($("#gddlx").val()==1||$("#gdlx").val()==1){
			if(wcrq!=null && wcrq!=""){
				var value=transferChinese($("#value_1").text());
				WOActionObj.jkxmbhF=value.split("+")[1]; 
				WOActionObj.jkflbhF=value.split("+")[0];
				WOActionObj.jkzF=wcrq;
			}
			if(jssj!=null && jssj!=""){
				var value=transferChinese($("#value_2").text());
				WOActionObj.jkxmbhS=value.split("+")[1]; 
				WOActionObj.jkflbhS=value.split("+")[0]; 
				WOActionObj.jkzS=jssj;
			}
			if(qlxh!=null && qlxh!=""){
				var value=transferChinese($("#value_3").text());
				WOActionObj.jkxmbhT=value.split("+")[1]; 
				WOActionObj.jkflbhT=value.split("+")[0]; 
				WOActionObj.jkzT=qlxh;
			}
		}else{
			var str=[wcrq,jssj,qlxh,jcsj,jcxh];
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
			
			var id =$("input[name='wohcid']").eq(index).val();
			var refJhly =$("input[name='refJhly_1']").eq(index).val(); 
			var bjid =$("input[name='bjid']").eq(index).val(); 
			var zwmc =$("input[name='zwms_1']").eq(index).val(); 
			var ywmc =$("input[name='ywms_1']").eq(index).val(); 
			var jh =$("input[name='bjh_1']").eq(index).val();
			var sl =$("input[name='sl_1']").eq(index).val(); 
			var lx =$("input[name='hclx_1']").eq(index).val(); 
			var bz =$("input[name='bz_1']").eq(index).val(); 
			
			infos.id=id;
			infos.refJhly=refJhly;
			infos.bjid=bjid;
			infos.zwmc = zwmc;
			infos.ywmc = ywmc;
			infos.jh = jh;
			infos.sl = sl;
			infos.lx = lx;
			infos.bz  = bz;
			WOAirMaterial.push(infos);
		});
	}
	return WOAirMaterial;
}

//获取工作内容参数
function getWOJobContentParam(){
	var WOJobContent = [];
	
	var len = $("#list").find("tr").length;
	if (len == 1) {
		if($("#list").find("td").length ==1){
			return WOJobContent;
		}
	}
	
	if (len > 0){
		$("#list").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			
			var gznrid =$("input[name='gznrid']").eq(index).val();
			var gznr =$("textarea[name='gznr']").eq(index).val(); 
			var gzz =$("input[name='gzz']").eq(index).val(); 
			var isBj =$("input[name='isBj']").eq(index).is(":checked"); //当前行，必检
			
			infos.id=gznrid;
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
			var index=$(this).index(); //当前行
			
			var fjid =$("input[name='fjid']").eq(index).val(); 
			var yswjm =$("input[name='yswjm']").eq(index).val(); 
			var nbwjm =$("input[name='nbwjm']").eq(index).val(); 
			var wbwjm =$("input[name='wbwjm']").eq(index).val(); 
			var cflj =$("input[name='cflj']").eq(index).val(); 
			var wjdx =$("input[name='wjdx']").eq(index).val();
			var hzm =$("input[name='hzm']").eq(index).val();
			
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
			var index=$(this).index(); //当前行
			var id =$("input[name='id']").eq(index).val(); 
			var xggdlx =$("input[name='xggdlx']").eq(index).val(); 
			var xggdzlx =$("input[name='xggdzlx']").eq(index).val(); 
			var xggdid =$("input[name='xggdid']").eq(index).val(); 
			var xggdjcid =$("input[name='xggdjcid']").eq(index).val(); 
			
			infos.id=  id;
			infos.xggdLx = xggdlx;
			infos.xggdZlx = xggdzlx;
			infos.xggdid=xggdid;
			infos.xgjcgdid=xggdjcid;
			
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
		非例行:'2',
		定检工单:'1',
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
var fileid=1;
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
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除附件 Delete">  ';
		 trHtml = trHtml+'</div></td>';
		 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'"><input type="hidden" name="fjid" value=\''+fileid+'\'/><input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'><input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'>'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
		 trHtml = trHtml+'<td class="text-left"><input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'><input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(data.attachments[0].cflj)+'\'>'+data.attachments[0].wjdxStr+' </td>';
		 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].user.username)+" "+StringUtil.escapeStr(data.attachments[0].user.realname)+'"><input type="hidden" name="wjdx" value=\''+data.attachments[0].wjdx+'\'><input type="hidden" name="hzm" value=\''+data.attachments[0].nbwjm.split(".")[1]+'\'>'+StringUtil.escapeStr(data.attachments[0].user.username)+" "+StringUtil.escapeStr(data.attachments[0].user.realname)+'</td>';
		 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
		 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
		 
		 trHtml = trHtml+'</tr>';	 
		 $('#filelist').append(trHtml);
		 fileid++;
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
function lineRemovefile(obj){
	$(obj).parent().parent().remove();   
}
//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#zjh").val());
	var dprtcode=$.trim( $('#dprtcode_parma').val()); 
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

$('.datepicker').datepicker({
	autoclose: true,
	clearBtn:true
});

/**
 * 下载附件
 */
function downloadfile(id) {
	PreViewUtil.handle(id, PreViewUtil.Table.B_G_00603);
}
