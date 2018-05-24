var zt=[0,1,2,3,4,5,6,7,8,9,10];
var zts=["保存","提交","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束","完成"];
var no1=0;
var pgdids=[];
var hcids=[];
var gdbhs=[];
var XggkGdbhs=[];
var nox=0;
var hcxuhao=1;
var glgdgknum = 1;
var oldGznrList=[];
var oldHchcList=[];
var oldXggkList=[];
var hcxh=0;
var oldScwjList=[];
var xggdnum = 0;
$(function(){
	 initRevisionNoticeBook();
	$('#pgdh-dialog').dialog('close');                              //评估单号的模态框
	$('#hcxx-dialog').dialog('close');                              //航材库的模态框
	//数据字典
	DicAndEnumUtil.registerDic('4','zy');      //专业
	DicAndEnumUtil.registerDic('3','zxfl');    //执行分类
	DicAndEnumUtil.registerDic('19','gzzw');    //工作站位
	$('#btnSave').click(function() {
		saveNonroutine();
    });
	
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	//addRotatableCol();
	
	//加载工作组
	loadWorkGroup();
	
	/*$(".panel-heading").not(":first").click(function(event){         //隐藏和显示
		if($(event.target).attr("id")!= "copy_wo"){
			$(this).next().slideToggle("fast");
		}
	});*/
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	xfgdyy: {
                validators: {
                	stringLength: {
                        max: 300,
                        message: '长度最多不超过300个字符'
                    }
                }
            },
            bz: {
                validators: {
                    stringLength: {
                        max: 300,
                        message: '长度最多不超过300个字符'
                    }
                }
            },
            gkbh: {
                validators: {
                	notEmpty: {
                        message: '工卡编号不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            },
            jx: {
                validators: {
                	notEmpty: {
                        message: '机型不能为空'
                    }
                }
            },
           /* zhut: {
                validators: {
                	notEmpty: {
                        message: '主题不能为空'
                    }
                }
            },*/
            jhgsRs: {
                validators: {
                	notEmpty: {
                        message: '人数不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]*$/,
                        message: '人数只能输入整数'
                    }
                }
            },
            jhgsXss: {
                validators: {
                	notEmpty: {
                        message: '小时数不能为空'
                    },
                    regexp: {
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '小时数只能输入数字和小数点'
                    }
                }
            },
            bb: {
                validators: {
                    regexp: {
	                    regexp: /^[0-9]{1,10}(\.[0-9]{0,2})?$/,
	                    message: '只能输入10位整数和2位小数'
	                }
                }
            },
            gklx: {
                validators: {
                	notEmpty: {
                        message: '工卡类型不能为空'
                    }
                }
            }
        }
       
    });
	
    var dprtcode = $.trim($("#dprtcode").val());
	 var planeRegOption = '';
	 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 
	 if(planeDatas != null && planeDatas.length >0){
		 
	  	$.each(planeDatas,function(i,planeData){
	  	    planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  	});
	 }
	  /*if(planeRegOption == ''){
	  	planeRegOption = '<option value="">暂无飞机 No plane</option>';
	  }*/
	  $("#jx").append(planeRegOption);
	  
	  //decodePageParam();
	  var djid=$("#djnrid").val();
	  if(djid!=1){
		  jzdjxx(djid);
	  }
	  
	  if($("#gklx").val()!=null && $("#gklx").val()!=""){
		  gklxSelect();
	  }else{
		  //默认不显示非定检工卡的ATA章节号
		  $("#lxSelectzjh").hide();
	  }
	  
	//提交
	$('#submit').on('click',function(){
		var zt=1;
		woSave(zt);
	});
	
	//保存
	$('#edit').on('click',function(){
		var zt=0;
		woSave(zt);
	});
});
function initRevisionNoticeBook(){
	var jx=$("#jx").val();
	if(jx==""){
		jx="00000";
	}
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/revisionNoticeBook/queryAllBook",
		type:"post",
		data:{jx:jx,zt:3,tzslx:3,dprtcode:$("#dprtcode").val()},
		dataType:"json",
		success:function(data){
			//生成多选下拉框(飞机注册号)
			$('#xdtzsDiv').empty();
			$('#xdtzsDiv').html("<select multiple='multiple' id='xdtzsid' ></select>");
			//修订通知书，下拉框数据集
			var bookOption = "";
			$.each(data,function(i,book){
				bookOption += "<option value='"+book.id+"'>" + StringUtil.escapeStr(book.text) +"</option>";
			});
			$("#xdtzsid").empty();
			$("#xdtzsid").append(bookOption);
			
			//生成多选下拉框动
			$('#xdtzsid').multiselect({
				buttonClass: 'btn btn-default',
		        buttonWidth: 'auto',
		        numberDisplayed:2,
			    includeSelectAllOption: true,
			    onChange:function(element,checked){
			    }
			});
		}
	});
}
function openGlgd(){
		goPage3(1,"auto","desc");
		$("#alertModalGlgd").modal("show");
}

//移除一行
function removeCol(e){
	$(e.target).parent().parent().remove();
	resXh();
	no1--;
	var len = $("#rotatable").find("tr").length;
	if(len < 1){
		$("#rotatable").append("<tr><td  colspan='12'  class='text-center'>暂无数据 No data.</td></tr>");
	}
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
    workId--;
	$("#list", $("#addtr")).find("tr").each(function(index){
		$(this).find("td[name='workId']").html(index+1);
	});
}
var workId=1;                                                                     //增加工作内容
 function addTr(){
	   var htmlContent = '';
	   htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><button  onclick='del_tr(this)'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>" ;
	   htmlContent = htmlContent + "<td style='vertical-align:middle' name='workId'>"+workId+"</td>";  
	  /* htmlContent = htmlContent + "<td><input maxlength='300' class='form-control' type='text' name='gznr'/></td>"; */ 
	   htmlContent = htmlContent + "<td><textarea maxlength='1300' class='form-control' id='gznr' name='gznr' style='min-height:80px' ></textarea></td>";  
	   
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input maxlength='6' class='form-control' type='text' name='gzz'/></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='isBj' type='checkbox'  style='width: 20px;height: 20px;' /></td>";  
	   htmlContent = htmlContent + "</tr>";  
	   workId++;
	   $("#list").append(htmlContent);
 }



//打开航材库存表对话框

//信息检索航材
 function searchRevision2(){
 	goPage3(1,"auto","desc");
 }
 //航材耗材改变事件
 function changeHclx(){
	 goPage2(1,"auto","desc");
 }
 
//信息检索航材
function searchRevision(){
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
	obj.hclxs =$.trim($("#hclx").val());//航材类型
	obj.keyword = $.trim($("#keyword_search").val());
	obj.dprtcode=$.trim( $('#dprtcode').val()); 
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
			   var page_ = new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch2(a,b,c);
					}//,
				});
			   // 标记关键字
			   signByKeyword($("#keyword_search").val(),[2,3,4],"#Hcxxlist tr td");
		   } else {
			  $("#Hcxxlist").empty();
			  $("#pagination").empty();
			  $("#Hcxxlist").append("<tr><td colspan=\"7\">暂无数据 No data.</td></tr>");
		   }
   }
 }); 
	
}
var xuhao=1;
function appendContentHtml2(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)' >";
		   }
		      
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='hcxx' onclick='chooesRow2(this)' ><input type='hidden'  name='hcid' value="+formatUndefine(row.id)+">" +
		   																		"<input type='hidden' name='xuhao' value="+xuhao+">" +
		   																		"<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(formatUndefine(row.zwms))+"'>" +
																		   		"<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(formatUndefine(row.ywms))+"'>" +
																		   		"<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"'>" +
																		   		"<input type='hidden' name='kykcsl' value="+formatUndefine(row.kykcsl==null?0:row.kykcsl)+">" +
																		   		"<input type='hidden' name='hclx' value="+row.hclx+">" +
																		   		"</td>";
		   /*htmlContent = htmlContent + "<td class='text-center'>"+xuhao+"</td>";*/
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ywms))+"'>"+StringUtil.escapeStr(formatUndefine(row.ywms))+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zwms))+"'>"+StringUtil.escapeStr(formatUndefine(row.zwms))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.bjh))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.kykcsl==null?0:row.kykcsl)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.jldw)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
		   xuhao++;
	   });
	   //$("#Pgdlist").empty();
	   $("#Hcxxlist").html(htmlContent);
	 
}
function appendPaginationHtml2(data,sortType,sequence){
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
      + "<li onclick=\"goPage2(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
      + "<li onclick=\"goPage2("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   }
	   
	   for (var index = startpage;index <= endpage;index++){
		   if (index==currentPage){
			   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }else {
			   paginationContent = paginationContent + "<li onclick=\"goPage2("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }
	   }
	   if (currentPage ==pageCount){
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }else {
		   paginationContent = paginationContent + "<li onclick=\"goPage2("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li onclick=\"goPage2("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }
	   $("#pagination").empty();
	   $("#pagination").html(paginationContent);
}


//保存航材信息
function appendHcxx(){
	var htmlContent = ""; 
	$('input[name=hcxx]:checked').each(function(){  
		
		var hcid=$(this).next().val();
		var xuhao=$(this).next().next().val();
		var zwms=$(this).next().next().next().val();
		var ywms=$(this).next().next().next().next().val();
		var bjh=$(this).next().next().next().next().next().val();
		var kycksl=$(this).next().next().next().next().next().next().val();
		var hclx=$(this).next().next().next().next().next().next().next().val();
		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+hcid+"'>";
		htmlContent = htmlContent + "<td><a href=javascript:lineRemove2('"+hcid+"')><button><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></a>" +
									"<input type='hidden' name='bjid' value='"+hcid+"'>" +
									"<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(formatUndefine(zwms))+"'>" +
							   		"<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(formatUndefine(ywms))+"'>" +
							   		"<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(formatUndefine(bjh))+"'>" +
							   		"<input type='hidden' name='kycksl' value='"+formatUndefine(kycksl)+"'>" +
							   		"<input type='hidden' name='hclx' value="+hclx+">" +
							   		"</td>"; 
		htmlContent = htmlContent + "<td class='text-center' name='hcxuhao'>"+hcxuhao+"</td>";  
		htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='refJhly' type='text' class='form-control' maxlength='100' ></td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(zwms))+"'>"+StringUtil.escapeStr(zwms)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(ywms))+"'>"+StringUtil.escapeStr(ywms)+"</td>"; 
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(bjh))+"'>"+StringUtil.escapeStr(bjh)+"</td>";  
		htmlContent = htmlContent + "<td class='text-right' style='vertical-align:middle'>"+kycksl+"</td>";  
		htmlContent = htmlContent + "<td ><input maxlength='12'  onkeyup='clearNoNum(this)'  placeholder='输入数字...' name='sl' type='text' class='form-control'></td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' name=>"+DicAndEnumUtil.getEnumName('materialTypeEnum',hclx)+"</td>";  
		htmlContent = htmlContent + "<td ><input name='bz' type='text' class='form-control' maxlength='300' ></td>";   
		htmlContent = htmlContent + "</tr>";
		hcids.push(hcid);
		hcxuhao++;
	 });
		//$("#Annunciatelist").empty();
	    $("#CKlist").append(htmlContent);
}

//删除行
function lineRemove2(hcid){
	$('#tr1_'+hcid).remove();
	hcids.remove(hcid);
	hcxuhao--;
	$("#CKlist", $("#airMaterialTools")).find("tr").each(function(index){
		$(this).find("td[name='hcxuhao']").html(index+1);
	});
}




//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage3(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch3(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch3(pageNumber,sortType,sequence){
   var obj ={};
	var gdsbh="";
	for(var i=0;i<gdbhs.length;i++){
		gdsbh+= gdbhs[i]+",";
   	}
	gdsbh=gdsbh.substring(0,gdsbh.length-1);
	obj.gdsbh=gdsbh;                         //已选择的工单的工单编号

	obj.keyword = $.trim($("#keyword_search2").val());
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/project/womanagement/workCardList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml3(data.rows);
			   appendPaginationHtml3(data,sortType,sequence);
			   // 标记关键字
			   signByKeyword($("#keyword_search2").val(),[2],"#glgdCardlist2 tr td");
		   } else {
			  $("#glgdCardlist2").empty();
			  $("#pagination2").empty();
			  $("#glgdCardlist2").append("<tr><td colspan=\"11\">暂无数据 No data.</td></tr>");
		   }
   }
 }); 
	
}
function appendContentHtml3(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
		   } else {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
		   }
		   htmlContent = htmlContent + "<td><input type=\"radio\" name='glgd'><input type='hidden'  name='gdbh' value="+formatUndefine(row.gdbh)+">" ;
																		   if(row.gdlx==1||row.gdlx==2||row.gdlx==3){
																			   htmlContent = htmlContent + "<input type='hidden' name='gdlx' value="+DicAndEnumUtil.getEnumName('workOrderTypeEnum',4)+'-'+DicAndEnumUtil.getEnumName('workOrderTypeEnum',row.gdlx)+">";  
																			   }else{
																			  htmlContent = htmlContent + "<input type='hidden' name='gdlx' value="+DicAndEnumUtil.getEnumName('workOrderTypeEnum',row.gdlx)+''+">";  
																			   }
																		   htmlContent = htmlContent + "<input type='hidden' name='zy' value="+StringUtil.escapeStr(formatUndefine(row.zy))+">";
																		   htmlContent = htmlContent + "<input type='hidden' name='bz' value="+StringUtil.escapeStr(formatUndefine(row.bz))+"></td>";
		   htmlContent = htmlContent + "<td>"+formatUndefine(row.gdbh)+"</td>";
		   if(row.gdlx==1||row.gdlx==2||row.gdlx==3){
			   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('workOrderTypeEnum',4)+'-'+DicAndEnumUtil.getEnumName('workOrderTypeEnum',row.gdlx)+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('workOrderTypeEnum',row.gdlx)+"</td>";  
		   }
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.zy))+"</td>";  
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.bz))+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#glgdCardlist2").html(htmlContent);
	 
}
function appendPaginationHtml3(data,sortType,sequence){
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
      + "<li onclick=\"goPage3(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
      + "<li onclick=\"goPage3("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   }
	   
	   for (var index = startpage;index <= endpage;index++){
		   if (index==currentPage){
			   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }else {
			   paginationContent = paginationContent + "<li onclick=\"goPage3("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }
	   }
	   if (currentPage ==pageCount){
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }else {
		   paginationContent = paginationContent + "<li onclick=\"goPage3("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li onclick=\"goPage3("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }
	   $("#pagination2").empty();
	   $("#pagination2").html(paginationContent);
}

//保存关联工单信息
function appendGlgd(){
	
	var htmlContent = ""; 
	$('input[name=glgd]:checked').each(function(){  
		var gdbh=$(this).next().val();
		var gdlx=$(this).next().next().val();
		var bz=$(this).next().next().next().val();
		htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+gdbh+"'>";
		htmlContent = htmlContent + "<td><a href=javascript:lineRemove3('"+gdbh+"')><button><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></a></td>";  
		htmlContent = htmlContent + "<td name='gdbh'>"+StringUtil.escapeStr(gdbh)+"</td>";  
		htmlContent = htmlContent + "<td name='gdlx'>"+gdlx+"</td>";  
		htmlContent = htmlContent + "<td name='zy'>"+StringUtil.escapeStr(bz)+"</td>";  
		htmlContent = htmlContent + "</tr>";
		gdbhs.push(gdbh);
	 });
	    $("#glgdCardList").append(htmlContent);
}

//删除行
function lineRemove3(gdbh){
	$('#tr1_'+gdbh).remove();
	gdbhs.remove(gdbh);
}
//获取耗材工具信息参数
function getWOAirMaterialParam(){
	var fol=false;
	var folText="";
	var woairmat ={};
	var WOAirMaterial = [];
	
	var len = $("#CKlist").find("tr").length;
	if (len == 1) {
		if($("#CKlist").find("td").length ==1){
			return woairmat;
		};
	}
	
	if (len > 0){
		$("#CKlist").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var bjid =$("input[name='bjid']").eq(index).val(); 
			var zwmc =$("input[name='zwms']").eq(index).val(); 
			var ywmc =$("input[name='ywms']").eq(index).val(); 
			var jh =$("input[name='bjh']").eq(index).val();
			var sl =$("input[name='sl']").eq(index).val(); 
			var lx =$("input[name='hclx']").eq(index).val(); 
			var bz =$("input[name='bz']").eq(index).val(); 
			var refJhly =$("input[name='refJhly']").eq(index).val(); 
			if(sl==null || sl=="" || sl==0){
				fol=true;
				folText="航材耗材工具信息不能为空且大于0";
			}
			infos.bjid = bjid;
			infos.zwmc = zwmc;
			infos.ywmc = ywmc;
			infos.jh = jh;
			infos.sl = sl;
			infos.lx = lx;
			infos.bz  = bz;
			infos.refJhly  = refJhly;
			WOAirMaterial.push(infos);
		});
		
	}
	woairmat.WOAirMaterial=WOAirMaterial;
	woairmat.fol=fol;
	woairmat.folText=folText;
	
	return woairmat;
}


//保存提交
function woSave(zt){
	var xdtzsid = $('#xdtzsid').val();
	if(xdtzsid != null ){
		xdtzsid.remove("multiselect-all");
	}
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	//验证工卡编号是否唯一
	if(validationJobCardBH($("#gkbh").val())==false){
		AlertUtil.showErrorMessage("工卡编号已存在，请重新输入");
		return false;
	}
	if($("#zhut").val()==null || $("#zhut").val()==""){
		AlertUtil.showErrorMessage("主题不能为空");
		return false;
	}
	
	if($('#gldjxm').val()!=null && $('#gldjxm').val()!=""){
		if($('#djgznr').val()==null || $('#djgznr').val()==""){
			AlertUtil.showErrorMessage("请选择定检工作内容");
			return false;
		}
	}
	if($('#workgroupboolean').val()=="true"){
		if($("#gzgroup").val()==null ||$("#gzgroup").val()==""){
			AlertUtil.showErrorMessage("请选择工作组");
			return false;
		}
	}
	
	
	/*var len = $("#CKlist").find("tr").length;
	if(len<=0){
		AlertUtil.showErrorMessage("请先选择工单的航材耗材！ ");
		return false;
	}*/
	var wobase={};
	wobase.gdbh=$("#gkbh").val(); 		//工单编号  
	wobase.gklx=$("#gklx").val(); 		//工卡类型
	wobase.gzzId=$("#gzgroup").val(); 		//工作组id
	if(wobase.gklx==1){
		wobase.wxfabh=$("#wxfabh").val();   //维修方案编号              
		wobase.djbh=$("#djbh").val();		//定检编号
		wobase.nbxh=$("#djgznbid").val();	//内部序号
		wobase.zjh=$("#zjh").val();		//ATA章节号
	}else{
		wobase.zjh=$("#fdjgkZjh").val();		//ATA章节号
	}
	wobase.zy=$("#zy").val();			//专业
	wobase.jhgsRs=$("#jhgsRs").val();	//标准工时（人数）
	wobase.jhgsXss=$("#jhgsXss").val();	//标准工时（小时）
	wobase.jx=$("#jx").val();			//机型
	wobase.gzzt=$("#zhut").val();		//主题
	//wobase.cjgk=$("#cjgk").val();		//厂家工卡
	wobase.yxx=$("input:radio[name='yxx']:checked").val();			//有效性
	wobase.bz=$("#bz").val();			//备注
	wobase.cksc=$("#cksc").val();		//参考手册
	wobase.ckgk=$("#ckgk").val();		//参考工卡
	wobase.bb=$("#bb").val();			//版本
	wobase.gzzw=$("#gzzw").val();		//工作站位
	wobase.gzdd=$("#gzdd").val();		//工作地点
	wobase.bcwj=$("#bcwj").val();		//补充文件
	wobase.pc=$("#pc").val();		//频次
	wobase.zt=zt;
	wobase.xdtzsid=xdtzsid;
	startWait();
    wobase.woJobenclosureList=getWoJobenclosureParenm();       // 封装附件上传表的参数实体
    var woairmat=getWOAirMaterialParam();
    if(woairmat.fol){
    	AlertUtil.showErrorMessage(woairmat.folText);
    	finishWait();
    	return false;
    }
	wobase.woAirMaterialList=woairmat.WOAirMaterial;           //获取航材耗材的参数实体
	//alert(JSON.stringify(wobase.woAirMaterialList));
	wobase.woJobContentList=getWOJobContentParam();        //获取工作内容的参数实体
	wobase.nonwocardList=getXggkParam();                //获取相关工卡的参数实体
	AjaxUtil.ajax({
		url:basePath+"/project/jobCard/saveMainJobCard",	
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(wobase),
		dataType:"json",
		success:function(data){
			    finishWait();
				AlertUtil.showMessage('添加成功!','/project/jobCard/main?id='+data+'&pageParam='+pageParam);
		}
	});
	
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


//获取工单实体参数
function getNonroutineParem(){
	var Nonroutine=[];
	
	var gdlx=$.trim( $('#gdlx').val());                            //工单类型
	var zy=$.trim( $('#zy').val());                                   //专业
	var jhgs_rs=$.trim( $('#jhgsRs').val());                   // 计划工时人数
	var jhgs_xss=$.trim( $('#jhgsXss').val());                //计划工时小时数
	var xfgdyy=$.trim( $('#xfgdyy').val());                    //下发工单原因
	var bz=$.trim( $('#bz').val());                                   //备注
	zt=0;                                                                      //状态
	
	var infos={};
	infos.gdlx=gdlx;
	infos.zy=zy;
	infos.jhgs_rs=jhgs_rs;
	infos.jhgs_xss=jhgs_xss;
	infos.xfgdyy=xfgdyy;
	infos.bz=bz;
	infos.zt=zt;
	 Nonroutine.push(infos);
	 return Nonroutine;
}

function getWoJobenclosureParenm(){
	var woJobenclosure=[];
	  var responses = uploadObj.responses;
	  var len = uploadObj.responses.length;
	  if(len != undefined && len>0){
		  for(var i =0;i<len;i++){
			  woJobenclosure.push({
						'yswjm':responses[i].attachments[0].yswjm
						,'wbwjm':responses[i].attachments[0].wbwjm
						,'nbwjm':responses[i].attachments[0].nbwjm
						,'cflj':responses[i].attachments[0].cflj
						,'wjdx':responses[i].attachments[0].wjdx
					});
			}
	  }
	 return woJobenclosure;
}
		
//获取相关工卡参数
function getXggkParam(){
	var NonwocardList = [];
	
	var len = $("#appendlistXggk").find("tr").length;
	if (len == 1) {
		if($("#appendlistXggk").find("td").length ==1){
			return NonwocardList;
		};
	}
	
	if (len > 0){
		$("#appendlistXggk").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var xggdid =$("input[name='xggdid']").eq(index).val(); 
			var xgjcgdid =$("input[name='xgjcgdid']").eq(index).val(); 
			var xggdLx =$("input[name='xggd_lx']").eq(index).val(); 
			var xggdZlx =$("input[name='xggd_zlx']").eq(index).val(); 
			infos.xggdid = xggdid;
			infos.xgjcgdid = xgjcgdid;
			infos.xggdLx = xggdLx;
			infos.xggdZlx = xggdZlx;
			NonwocardList.push(infos);
		});
	}
	return NonwocardList;
}


function sumTotal(){
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	if((jhgsRs!=null ||jhgsRs!="")&&(jhgsXss!=null || jhgsXss!="")){
		$("#time").html((jhgsRs*jhgsXss).toFixed(2));
	}
}

var  zxfltype={
		 ZJJ:'装机件',
		FZJJ:'非装机件',
		 FJ:'机身',
};
var  wotype={
		 时控件工单:'1',
		 附件工单:'2',
		 排故工单:'3',
		 非例行工单:'4',
		 定检工单:'5',
		 EO工单:'6',
};
var  hctype={
		 危险品:'4',
		 工具:'5',
		 航材:'6',
		 设备:'7',
		 低值易耗品:'8',
};



//只能输入数字和小数
function clearNoNum(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     sumTotal();
}







//打开维修方案列表对话框
function openWxfa() {
	initRevisionNoticeBook();
	var gklx=$('#gklx').val();
	if(gklx==null || gklx==''){
		AlertUtil.showErrorMessage("请先选择工卡类型");
		$("#jx").val("");
		return
	}else if(gklx==1){
		goPageWxfa(1,"auto","desc");
	}else{
		 $('#xzwxfa').val("");
		 $('#wxfabh').val("");
		 $('#wxfabb').val("");
		 //清空定检项目
		 $('#gldjxm').val("");
		 $('#djxmid').val("");
		 $('#djbh').val("");
		 //清空定检工卡
		 $('#djgznrid').val("");
		 $('#djgznrckgk').val("");
		 $('#djgznr').val("");
	}
	
	
	 /*$("#alertModalWxfa").modal("show");*/
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageWxfa(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearchWxfa(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearchWxfa(pageNumber,sortType,sequence){
	
	
	var searchParam = gatherSearchParamWxfa();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	 AjaxUtil.ajax({
		 url:basePath+"/project/maintenance/queryAllPageDistincList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.rows !=null && data.rows!=""){
			   appendContentHtmlWxfa(data.rows);
			   
		   } else {
			     $('#xzwxfa').val("");
				 $('#wxfabh').val("");
				 $('#wxfabb').val("");
				 //清空定检项目
				 $('#gldjxm').val("");
				 $('#djxmid').val("");
				 $('#djbh').val("");
				 //清空定检工卡
				 $('#djgznrid').val("");
				 $('#djgznrckgk').val("");
				 $('#djgznr').val("");
				//厂家手册
				$('#cksc').val("");
				//厂家工卡
				$('#ckgk').val("");
				//ATA章节号加名称
				$('#zjhAndMc').val("");
				//ATA章节号
				$('#zjh').val("");
				//工作地点
				$('#gzdd').val("");
				//工作站位
				$('#gzzw').val("-");
		   }
      }
    }); 
	
}
//将搜索条件封装 
function gatherSearchParamWxfa(){
	 var searchParam = {};
	 var keyword = $.trim($("#keyword_search_wxfa").val());
	 var dprtcode = $.trim($("#dprtcode").val());
	 var jx = $.trim($("#jx").val());
	 searchParam.keyword = keyword;
	 searchParam.dprtcode = dprtcode;
	 searchParam.jx = jx;
	 
	 var jhSxrq = $.trim($("#jhSxrq_search").val());
	 var sjSxrq = $.trim($("#sjSxrq_search").val());
	 /*if(null != jhSxrq && "" != jhSxrq){
		 var jhSxrqBegin = jhSxrq.substring(0,4)+"-"+jhSxrq.substring(4,6)+"-"+jhSxrq.substring(6,8);
		 var jhSxrqEnd = jhSxrq.substring(11,15)+"-"+jhSxrq.substring(15,17)+"-"+jhSxrq.substring(17,19);
		 searchParam.jhSxrqBegin = jhSxrqBegin;
		 searchParam.jhSxrqEnd = jhSxrqEnd;
	 }
	 if(null != sjSxrq && "" != sjSxrq){
		 var sjSxrqBegin = sjSxrq.substring(0,4)+"-"+sjSxrq.substring(4,6)+"-"+sjSxrq.substring(6,8);
		 var sjSxrqEnd = sjSxrq.substring(11,15)+"-"+sjSxrq.substring(15,17)+"-"+sjSxrq.substring(17,19);
		 searchParam.sjSxrqBegin = sjSxrqBegin;
		 searchParam.sjSxrqEnd = sjSxrqEnd;
	 }*/
	 
	 return searchParam;
}
//维修方案
function searchWxfa(){
	goPageWxfa(1,"auto","desc");
}
function appendContentHtmlWxfa(row){
	if($('#jx').val()=="" || $('#jx').val()==null){
		$('#xzwxfa').val("");
		 $('#wxfabh').val("");
		 $('#wxfabb').val("");
		 //清空定检项目
		 $('#gldjxm').val("");
		 $('#djxmid').val("");
		 $('#djbh').val("");
		 //清空定检工卡
		 $('#djgznrid').val("");
		 $('#djgznrckgk').val("");
		 $('#djgznr').val("");
		//厂家手册
		$('#cksc').val("");
		//厂家工卡
		$('#ckgk').val("");
		//ATA章节号加名称
		$('#zjhAndMc').val("");
		//ATA章节号
		$('#zjh').val("");
		//工作地点
		$('#gzdd').val("");
		//工作站位
		$('#gzzw').val("-");
		 return
	}
		 $('#xzwxfa').val(row.wxfabh+" "+row.zwms);
		 $('#wxfabh').val(row.wxfabh);
		 $('#wxfabb').val(row.bb);
		 //清空定检项目
		 $('#gldjxm').val("");
		 $('#djxmid').val("");
		 $('#djbh').val("");
		 //清空定检工卡
		 $('#djgznrid').val("");
		 $('#djgznrckgk').val("");
		 $('#djgznr').val("");
		//厂家手册
		$('#cksc').val("");
		//厂家工卡
		$('#ckgk').val("");
		//ATA章节号加名称
		$('#zjhAndMc').val("");
		//ATA章节号
		$('#zjh').val("");
		//工作地点
		$('#gzdd').val("");
		//工作站位
		$('#gzzw').val("-");
		 

	 
	
	
	
	 
}
function appendPaginationHtmlWxfa(data,sortType,sequence){
	   
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
        + "<li onclick=\"goPageWxfa(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
        + "<li onclick=\"goPageWxfa("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   }
	   
	   for (var index = startpage;index <= endpage;index++){
		   if (index==currentPage){
			   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }else {
			   paginationContent = paginationContent + "<li onclick=\"goPageWxfa("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }
	   }
	   if (currentPage ==pageCount){
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }else {
		   paginationContent = paginationContent + "<li onclick=\"goPageWxfa("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li onclick=\"goPageWxfa("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }
	   $("#paginationWxfa").empty();
	   $("#paginationWxfa").html(paginationContent);
}


//拼接维修方案
function appendWxfa(){
	wxfabh=$('#WxfaRadio:checked').val();
	xzwxfa=$('#WxfaRadio:checked').next().val();
	$('#xzwxfa').val(wxfabh+" "+xzwxfa);
	$('#wxfabh').val(wxfabh);
	
	
	//清空定检项目
	$('#gldjxm').val("");
	$('#djxmid').val("");
	$('#djbh').val("");
	//清空定检工卡
	$('#djgznrid').val("");
	$('#djgznrckgk').val("");
	$('#djgznr').val("");
}


//定检项目
function searchDjxm(){
	goPageDjxm(1,"auto","desc");
}
//打开定检项目列表对话框
function openDjxm() {
	$("#keyword_search_Djxm").val("");
	var wxfabh=$('#wxfabh').val();
	var jx=$("#jx").val();
	if(jx==null || jx==""){
		AlertUtil.showErrorMessage("请先选择机型");
		return;
	}
	if(wxfabh==null || wxfabh==""){
		AlertUtil.showErrorMessage("该机型下可能没有维修方案,请重新选择机型");
		return;
	}
	 goPageDjxm(1,"auto","desc");
	 $("#alertModalDjxm").modal("show");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageDjxm(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearchDjxm(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearchDjxm(pageNumber,sortType,sequence){
	 
	 var searchParam = gatherSearchParamDjxm();
	 searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	 AjaxUtil.ajax({
		 url:basePath+"/project/fixedcheckitem/queryAllPageList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtmlDjxm(data.rows);
			   var page_ = new Pagination({
					renderTo : "paginationDjxm",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearchDjxm(a,b,c);
					}//,
					//controller: this_
				});
			   // 标记关键字
			   signByKeyword($("#keyword_search_Djxm").val(),[2,3,5,8],"#listDjxm tr td");
		   } else {
			  $("#listDjxm").empty();
			  $("#paginationDjxm").empty();
			  $("#listDjxm").append("<tr><td colspan=\"8\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
//将搜索条件封装 
function gatherSearchParamDjxm(){
	 var searchParam = {};
	 searchParam.keyword = $.trim($("#keyword_search_Djxm").val());
	 searchParam.wxfabh = $.trim($("#wxfabh").val());
	 searchParam.bb = $.trim($("#wxfabb").val());
	 searchParam.pxh = $.trim($("#pxh_search").val());
	 searchParam.dprtcode = $.trim($("#dprtcode").val());
	 searchParam.yxx = 1;
	 return searchParam;
}
function appendContentHtmlDjxm(list){
	   var yxx = '';
	   var htmlContent = '';
	   var djxmid=$('#djxmid').val();
	   var djbh=$('#djbh').val();
	   $.each(list,function(index,row){
			   
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)' >";
			   } else {
				   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)'>";
			   }
			   if(djbh==row.djbh){
				   htmlContent = htmlContent + "<td><input type='radio' name='DjxmRadio' id='DjxmRadio' checked='true' value="+formatUndefine(row.id)+"><input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zwms))+"'><input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.djbh))+"'></td>";
			   }else{
				   htmlContent = htmlContent + "<td><input type='radio' name='DjxmRadio' id='DjxmRadio' value="+formatUndefine(row.id)+"><input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zwms))+"'><input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.djbh))+"'></td>";
			   }
			   /*htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.pxh)+"</td>"; */ 
			   
			   htmlContent = htmlContent + "<td class='text-center'>";
			   htmlContent = htmlContent + StringUtil.escapeStr(formatUndefine(row.djbh));
			   htmlContent = htmlContent + "</td>";  
			   
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zwms))+"'>"+StringUtil.escapeStr(formatUndefine(row.zwms))+"</td>";  
			   /*htmlContent = htmlContent + "<td>"+DicAndEnumUtil.getEnumName('approveStatusEnum',row.spzt)+"</td>";*/
			   
			   if(row.yxx == 1){
				   yxx = '有效';	
			   }else{
				   yxx = '无效';
			   }
			   htmlContent = htmlContent + "<td class='text-center'>"+yxx+"</td>";  
			   htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>"; 
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jktj))+"'>"+StringUtil.escapeStr(formatUndefine(row.jktj))+"</td>";
			   htmlContent = htmlContent + "<td class='text-center' >"+indexOfTime(row.sxsj)+"</td>";
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"'>"+StringUtil.escapeStr(formatUndefine(row.bz))+"</td>";
			   /* htmlContent = htmlContent + "<td>"+formatUndefine(row.user.username)+" "+formatUndefine(row.user.realname)+"</td>";
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
		   htmlContent = htmlContent + "<td>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>";*/
			   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#listDjxm").empty();
	   $("#listDjxm").html(htmlContent);
	 
}
function appendPaginationHtmlDjxm(data,sortType,sequence){
	   
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
        + "<li onclick=\"goPageDjxm(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
        + "<li onclick=\"goPageDjxm("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   }
	   
	   for (var index = startpage;index <= endpage;index++){
		   if (index==currentPage){
			   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }else {
			   paginationContent = paginationContent + "<li onclick=\"goPageDjxm("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }
	   }
	   if (currentPage ==pageCount){
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }else {
		   paginationContent = paginationContent + "<li onclick=\"goPageDjxm("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li onclick=\"goPageDjxm("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }
	   $("#paginationDjxm").empty();
	   $("#paginationDjxm").html(paginationContent);
}
function appendDjxm(){
	djxmid=$('#DjxmRadio:checked').val();
	gldjxm=$('#DjxmRadio:checked').next().val();
	djbh=$('#DjxmRadio:checked').next().next().val();
	
	if(djxmid=="" || djxmid==null){
		$('#gldjxm').val("");
		$('#djxmid').val("");
		$('#djbh').val("");
		//清空定检工卡
		$('#djgznrid').val("");
		$('#djgznbid').val("");
		$('#djgznrckgk').val("");
		$('#djgznr').val("");
		//厂家手册
		$('#cksc').val("");
		//厂家工卡
		$('#ckgk').val("");
		//ATA章节号加名称
		$('#zjhAndMc').val("");
		//ATA章节号
		$('#zjh').val("");
		//工作地点
		$('#gzdd').val("");
		//工作站位
		$('#gzzw').val("-");
		return
	}
	
	$('#gldjxm').val(djbh+" "+gldjxm);
	$('#djxmid').val(djxmid);
	$('#djbh').val(djbh);
	//清空定检工卡
	$('#djgznrid').val("");
	$('#djgznbid').val("");
	$('#djgznrckgk').val("");
	$('#djgznr').val("");
	$('#zjh').val("");
	$('#zjhAndMc').val("");
	//厂家手册
	$('#cksc').val("");
	//厂家工卡
	$('#ckgk').val("");
	//ATA章节号加名称
	$('#zjhAndMc').val("");
	//工作站位
	$('#gzzw').val("-");
}



//打开定检工作内容列表对话框
function openDjgznr() {
	var djxmid=$('#djxmid').val();
	if(djxmid==null || djxmid==""){
		AlertUtil.showErrorMessage("请先选择定检项目");
		return;
	}
	AjaxGetDatasWithSearchDjgznr(djxmid);
	 $("#alertModalDjgznr").modal("show");
}
//拼接工作内容列表
function AjaxGetDatasWithSearchDjgznr(djxmid){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixedcheckworkcontent/queryListByDjxmbhid",
		type:"post",
		data:{djxmid:djxmid},
		dataType:"json",
		success:function(data){
			if(data.length>0){
				appendPaginationHtmlDjgznr(data);
				
			}else{
				 $("#listDjgznr").empty();
				 $("#listDjgznr").append("<tr><td colspan=\"8\">暂无数据 No data.</td></tr>");
			}
		}
	});
}
//拼接定检工作内容
function appendPaginationHtmlDjgznr(list){
	var djgznbid=$('#djgznbid').val();
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if(row.zt==1){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)' >";
			   } else {
				   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow3(this)' >";
			   }
			   if(row.nbxh==djgznbid){
				   htmlContent = htmlContent + "<td><input type='radio' name='DjgznrRadio' id='DjgznrRadio' checked='true' value='"+formatUndefine(row.id)+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.nbxh))+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ckgk))+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.cksc))+"'>" ;
				   									if(row.fixChapter==null || row.fixChapter==""){
				   										htmlContent = htmlContent + "<input type='hidden' value='' >" +
				   																	"<input type='hidden' value='' >" ;
				   									}else{
				   										htmlContent = htmlContent + "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.fixChapter.zwms))+"'>" +
				   																	"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.fixChapter.zjh))+"'>" ;
				   									}
				   		htmlContent = htmlContent + "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.wz))+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.gzzw))+"'>"+
													"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.scmsYw))+"'>"+
													"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.scmsZw))+"'></td>";
			   }else{
				   htmlContent = htmlContent + "<td><input type='radio' name='DjgznrRadio' id='DjgznrRadio' value='"+formatUndefine(row.id)+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.nbxh))+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ckgk))+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.cksc))+"'>" ;
										   
										   if(row.fixChapter==null || row.fixChapter==""){
													htmlContent = htmlContent + "<input type='hidden' value=''>" +
																				"<input type='hidden' value=''>";
												}else{
													htmlContent = htmlContent + "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.fixChapter.zwms))+"'>" +
																				"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.fixChapter.zjh))+"'>" ;
												}
						htmlContent = htmlContent + "<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.wz))+"'>" +
				   									"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.gzzw))+"'>"+
													"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.scmsYw))+"'>"+
													"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.scmsZw))+"'></td>";
			   }
			   
			   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.nbxh))+"</td>";  
			   if(row.fixChapter==null || row.fixChapter==""){
				   htmlContent = htmlContent + "<td class='text-left'></td>";  
			   } else{
				   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.fixChapter.zjh))+" "+StringUtil.escapeStr(formatUndefine(row.fixChapter.zwms))+"'>"+StringUtil.escapeStr(formatUndefine(row.fixChapter.zjh))+" "+StringUtil.escapeStr(formatUndefine(row.fixChapter.zwms))+"</td>";  
			   }
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.scmsYw))+"'>"+StringUtil.escapeStr(formatUndefine(row.scmsYw))+"</td>";
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.scmsZw))+"'>"+StringUtil.escapeStr(formatUndefine(row.scmsZw))+"</td>";
			   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.jclx)+"</td>";
			  /* if(row.fjzch=="00000"){
				   htmlContent = htmlContent + "<td class='text-left'>通用Currency</td>";
			   }else{
				   htmlContent = htmlContent + "<td class='text-left'>row.fixChapter</td>";
			   }*/
			   
			   if(row.isBj == 1){
				   htmlContent = htmlContent + "<td class='text-center'>是</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td class='text-center'>否</td>"; 
			   }
			   if(row.isMi == 1){
				   htmlContent = htmlContent + "<td class='text-center'>是</td>"; 
			   }else{
				   htmlContent = htmlContent + "<td class='text-center'>否</td>"; 
			   }
			   /* htmlContent = htmlContent + "<td>"+formatUndefine(row.user.username)+" "+formatUndefine(row.user.realname)+"</td>";
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
		   htmlContent = htmlContent + "<td>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>";*/
			   htmlContent = htmlContent + "</tr>";  
			   
		   }
		    
	   });
	   $("#listDjgznr").empty();
	   $("#listDjgznr").html(htmlContent);
}
function appendDjgznr(){
	djgznrid=$('#DjgznrRadio:checked').val();
	djgznbid=$('#DjgznrRadio:checked').next().val();
	djgznrckgk=$('#DjgznrRadio:checked').next().next().val();
	cksc=$('#DjgznrRadio:checked').next().next().next().val();
	zjhmc=$('#DjgznrRadio:checked').next().next().next().next().val();
	zjh=$('#DjgznrRadio:checked').next().next().next().next().next().val();
	gzdd=$('#DjgznrRadio:checked').next().next().next().next().next().next().val();
	gzzw=$('#DjgznrRadio:checked').next().next().next().next().next().next().next().val();
	scmsYw=$('#DjgznrRadio:checked').next().next().next().next().next().next().next().next().val();
	scmsZw=$('#DjgznrRadio:checked').next().next().next().next().next().next().next().next().next().val();
	
	if(djgznrid==null || djgznrid==""){
		//定检工作内容id
		$('#djgznrid').val("");
		//定检工作内部id
		$('#djgznbid').val("");
		//页面回显
		$('#djgznr').val("");
		//参考工卡
		$('#djgznrckgk').val("");
		//厂家手册
		$('#cksc').val("");
		//厂家工卡
		$('#ckgk').val("");
		//ATA章节号加名称
		$('#zjhAndMc').val("");
		//ATA章节号
		$('#zjh').val("");
		//工作地点
		$('#gzdd').val("");
		//工作站位
		$('#gzzw').val("-");
		return
	}
	//定检工作内容id
	$('#djgznrid').val(djgznrid);
	//定检工作内部id
	$('#djgznbid').val(djgznbid);
	//页面回显
	$('#djgznr').val("第"+djgznbid+"条");
	//参考工卡
	$('#djgznrckgk').val(djgznrckgk);
	//厂家手册
	$('#cksc').val(cksc);
	//厂家工卡
	$('#ckgk').val(djgznrckgk);
	//ATA章节号加名称
	$('#zjhAndMc').val(zjh+" "+zjhmc);
	//ATA章节号
	$('#zjh').val(zjh);
	//工作地点
	$('#gzdd').val(gzdd);
	//工作站位
	$('#gzzw').val(gzzw);
	//主题
	$('#zhut').val(scmsYw+" "+scmsZw);
	
	
}
//相关工卡
function searchXggk(){
	goPageXggk(1,"auto","desc");
}
//打开相关工卡列表
function openXggk(){
	var jx=$('#jx').val();
	if(jx==null || jx==""){
		AlertUtil.showErrorMessage("请先选择机型");
		return;
	}
	goPageXggk(1,"auto","desc");
	$("#alertModalXggk").modal("show");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageXggk(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearchXggk(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearchXggk(pageNumber,sortType,sequence){
	var obj ={};
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	obj.jx=$('#jx').val();
	obj.dprtcode=$('#dprtcode').val();
	obj.keyword = $.trim($("#keyword_search_xggk").val());
	obj.ids=XggkGdbhs;//已选择的评估单id
	//obj.id = '1'';//搜索条件
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/workorder/queryRelatedJobCardAll",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtmlXggk(data.rows);
			   var page_ = new Pagination({
					renderTo : "paginationXggk",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearchXggk(a,b,c);
					}//,
					//controller: this_
				});
			// 标记关键字
			   signByKeyword($("#keyword_search_xggk").val(),[2],"#listXggk tr td");
		   } else {
			  $("#listXggk").empty();
			  $("#paginationXggk").empty();
			  $("#listXggk").append("<tr><td colspan=\"6\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
function chooesRow2(obj){
	chooesRow($(obj));
}

function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='checkbox']");
	chooesRow(obj);
}
//选中一行(单选)
function chooesRow3(objradio){
	$(objradio).find("input[type='radio']").attr("checked",true);
}

//行选
function chooesRow(obj){
	var flag = obj.is(":checked");
	if(flag){
		obj.removeAttr("checked");
	}else{
		obj.attr("checked",true);
	}
	
}

function appendContentHtmlXggk(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)' >";
		   }
		      
		   htmlContent = htmlContent + "<td><input type='checkbox' onclick='chooesRow2(this)' name='XggkCheckbox' id='XggkCheckbox"+row.gdbh+"' value="+StringUtil.escapeStr(formatUndefine(row.gdbh))+" >" +
									   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zhut))+"'>" +
									   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.zy))+"'>" +
									   		"<input type='hidden' value="+formatUndefine(row.id)+">" +
									   		"<input type='hidden' value="+formatUndefine(row.gdjcid)+">" +
									   		"<input type='hidden' value="+formatUndefine(row.gdlx)+">" +
									   		"<input type='hidden' value="+formatUndefine(row.gdzlx)+"></td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.gdbh))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.zy))+"</td>";  
		   if(row.gdlx==1){
			   htmlContent = htmlContent + "<td class='text-left'>例行工单</td>";  			   
		   }else if(row.gdlx==2){
			   htmlContent = htmlContent + "<td class='text-left'>非例行工单</td>";  			   
		   }else if(row.gdlx==3){
			   htmlContent = htmlContent + "<td class='text-left'>EO工单</td>";  			   
		   }else if(row.gdlx==5){
			   htmlContent = htmlContent + "<td class='text-left'>定检工卡</td>";  			   
		   }else{
			   htmlContent = htmlContent + "<td></td>";  			   
		   }
		   
		   if(row.gdzlx==0){
			   htmlContent = htmlContent + "<td></td>";  			   
		   }else if(row.gdzlx==1){
			   htmlContent = htmlContent + "<td class='text-left'>时控件工单</td>";  			   
		   }else if(row.gdzlx==2){
			   htmlContent = htmlContent + "<td class='text-left'>附加工单</td>";  			   
		   }else if(row.gdzlx==3){
			   htmlContent = htmlContent + "<td class='text-left'>排故工单</td>";  			   
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'></td>";  			   
			   
		   }
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zhut))+"'>"+StringUtil.escapeStr(formatUndefine(row.zhut))+"</td>";
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#listXggk").empty();
	   $("#listXggk").html(htmlContent);
	   
	  /* //判断是否已选择，
	   for(var i=0;i<XggkGdbhs.length;i++){
		   $("#XggkCheckbox"+XggkGdbhs[i]).attr("checked", "checked");
		   //alert($("#XggkCheckbox"+XggkGdbhs[i]).val());
	   };*/
	 
}

function appendXggk(){
	var htmlContent = ""; 
	//清空
	//XggkGdbhs =[];
	$('input[name=XggkCheckbox]:checked').each(function(){   
		
		var gdbh=$(this).val();
		var gddlxAndgdlx=$.trim($(this).parent().next().next().next().html());
		var zgg = $.trim($(this).parent().next().next().next().next().html());
		if(null != zgg && '' != zgg){
			gddlxAndgdlx += "-" + zgg;
		}
		var zhut=$(this).next().val();
		var zy=$(this).next().next().val();
		var xggdid=$(this).next().next().next().val();
		var xgjcgdid=$(this).next().next().next().next().val();
		var xggd_lx=$(this).next().next().next().next().next().val();
		var xggd_zlx=$(this).next().next().next().next().next().next().val();
		//alert(wjzt);
		htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+xggdid+"'>";
		htmlContent = htmlContent + "<td><button  onclick=\"gdbhRemove('"+xggdid+"')\"><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button> </td>";  
		htmlContent = htmlContent + "<td name='glgdgknum'>"+glgdgknum+"</td>";
		htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='xggdid' value="+xggdid+"><input type='hidden' name='xggd_lx' value="+xggd_lx+"><input type='hidden' name='xggd_zlx' value="+xggd_zlx+"><input type='hidden' name='xgjcgdid' value="+xgjcgdid+">"+StringUtil.escapeStr(gdbh)+"</td>";
		
		htmlContent = htmlContent + "<td class='text-left'>"+gddlxAndgdlx+"</td>";  
		
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(zhut)+"'>"+StringUtil.escapeStr(zhut)+"</td>";
		htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(zy)+"</td>";  
		htmlContent = htmlContent + "</tr>";
		//pgdids.push(pgdId);
		XggkGdbhs.push(xggdid);
		glgdgknum++;
		  });
	    $("#appendlistXggk").append(htmlContent);
}
//删除行
function gdbhRemove(xggdid){
	$('#tr1_'+xggdid).remove();
	XggkGdbhs.remove(xggdid);
	glgdgknum--;
	$("#appendlistXggk", $("#relatedWorkOrder")).find("tr").each(function(index){
		$(this).find("td[name='glgdgknum']").html(index+1);
	});
}
//验证工卡编号是否唯一
function validationJobCardBH(gkbh){
	var oldgdbh=$('#oldgkbh').val();
	var dprtcode=$('#dprtcode').val();
	var falot=true;
	 AjaxUtil.ajax({
	   url:basePath+"/project/jobCard/validationJobCardBH",
	   type:"post",
		async: false,
		data:{
			'gdbh' : gkbh,
			'oldgdbh': oldgdbh,
			'dprtcode': dprtcode
		},
		dataType:"json",
	   success:function(data){
		   if(data.total>0){
			   falot = false;
			   return falot;
		   }
		   return falot;
     }
   }); 
	 return falot;
}

//上传
var uploadObj = $("#fileuploader").uploadFile({
	url:basePath+"/common/ajaxUploadFile",
	multiple:true,
	dragDrop:false,
	showStatusAfterSuccess: false,
	showDelete: false,
	formData: {
		"fileType":"workorder"
		,"wbwjm" : function(){return $('#wbwjm').val()}
		},//参考FileType枚举（技术文件类型）
	fileName: "myfile",
	returnType: "json",
	removeAfterUpload:true,
	showStatusAfterSuccess : false,
	showStatusAfterError: false,
	uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
	uploadButtonClass: "ajax-file-upload_ext",
	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
	{
		var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
		 trHtml = trHtml+'<td><div>';
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="Delete 删除">  ';
		 trHtml = trHtml+'</div></td>';
		 //trHtml = trHtml+'<td>'+data.attachments[0].yswjm+'</td>';
		 //trHtml = trHtml+'<td>'+data.attachments[0].nbwjm+'</td>';
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
		  
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wjdxStr)+'</td>';
		 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].user.displayName)+'</td>';
		 trHtml = trHtml+"<td class='text-center'>"+data.attachments[0].czsj+'</td>';
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
	if(uuid!=null && uuid!=""){
		$('#'+uuid).remove();
		}
	
}

function back(){
	window.location.href =basePath+"/project/jobCard/main?pageParam="+pageParam;
}

//只能输入数字和小数,保留两位
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
    	 if(Number(_value) > 9999999999.99){
    		return validateMax(_value.substr(0, _value.length-1));
    	 }
    	 return _value;
    }
}

/*$(".panel-heading").not(":first").click(function(){         //隐藏和显示
	$(this).next().slideToggle("fast");
});*/


//获取工作组
function loadWorkGroup(){
	var obj={};
	obj.dprtcode=$("#dprtcode").val();
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
			 			apps += "<option value='"+list.id+"' >"+list.gzzdm+" "+list.gzzmc+"</option>";
			 	   }); 
				   if(!data.wgMust){
					   $("#gzgroup").append("<option value=''>please choose</option>"+apps);
				   }else{
					   $("#gzgroup").append(apps);
				   }
			   }
	       },
	 }); 
}

function gklxSelect(){
	var gklx=$("#gklx").val();
	if(gklx==1){
		$("#lxSelect").show();
		$("#lxSelectzjh").hide();
	}else if(gklx==2){
		$("#lxSelect").hide();
		$("#lxSelectzjh").show();
	}else{
		$("#jx").val("");
	}
	
}

function copyJobCard(){
	goPage(1,"auto","desc");//开始的加载默认的内容 
	$("#alertModalDjgk").modal("show");
}
function searchRevisionDjgk(){
	goPage(1,"auto","desc");//检索
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	jobCarditemData=[];
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	paramsMap={userType:$("#userType").val(),userId:$("#userId").val()};
	obj.paramsMap=paramsMap;
	obj.pagination = pagination;
	obj.keyword=$("#keyword_djgk").val();
	obj.dprtcode=$("#dprtcode").val();
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/jobCard/queryJobCardList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   jobCarditemData=data.rows;
			   appendContentHtml(data.rows);
			   
			   var page_ = new Pagination({
					renderTo : "paginationDJGK",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}//,
				});
			// 标记关键字
			   signByKeyword($("#keyword_djgk").val(),[2,3,8,9,11,13,"#JobCardlistlist"]);
		   } else {
			  $("#JobCardlistlist").empty();
			  $("#mpagination").empty();
			  $("#JobCardlistlist").append("<tr><td colspan=\"15\">暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'quality_check_list_table'});
     }
   }); 
	
}

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" onclick='chooesRow3(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" onclick='chooesRow3(this)' >";
		   } 
		   htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input type='radio' name='gkRadio' id='gkRadio'  value='"+row.id+"' /><input type='hidden' value='"+row.jx+"' /></td>";
		   
		   htmlContent = htmlContent +"<td class='text-left fixed-column' title='"+StringUtil.escapeStr(formatUndefine(row.gdbh))+"'><a href=\"javascript:viewMainJobCard('"+row.id+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(formatUndefine(row.gdbh))+"</a></td>"; 
		   htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.djbh))+"' >"+StringUtil.escapeStr(formatUndefine(row.djbh))+"</td>";
		  if(row.gklx==1){
			  htmlContent = htmlContent + "<td class='text-center'>定检工卡</td>"; 
		  }else if(row.gklx==2){
			  htmlContent = htmlContent + "<td class='text-center'>非定检工卡</td>"; 
		  }else{
			  htmlContent = htmlContent + "<td class='text-center'></td>"; 
		  }
		  if(row.gzz==null){
			  htmlContent = htmlContent + "<td class='text-center'></td>"; 
		  }else{
			  htmlContent = htmlContent + "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.gzz.gzzmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.gzz.gzzmc))+"</td>"; 
		  } 
		   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.zy))+"</td>"; 
		   if(row.jhgsRs!=null && row.jhgsRs!="" && row.jhgsXss!=null && row.jhgsXss!=""){
			   var jhvalue=row.jhgsRs+"人x"+row.jhgsXss+"时="+(row.jhgsRs*row.jhgsXss).toFixed(2)+"时";
			   
			   htmlContent = htmlContent + "<td class='text-left' title='"+jhvalue+"'>"+jhvalue+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'></td>"; 
		   }
		  /* htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.jhgsRs)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-right'>"+formatUndefine(row.jhgsXss)+"</td>";  */
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.gzzt)+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.gzzt))+"</td>";
		   if(row.yxx==1){
			   htmlContent = htmlContent + "<td class='text-center'>有效</td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'>无效</td>";
		   }
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.bz))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.bz))+"</td>";
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
		   htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.zdr_user.displayName)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+AccessDepartmentUtil.getDpartName(formatUndefine(row.dprtcode))+"</td>";  
		   htmlContent = htmlContent + "</tr>";   
		    
	   });
	   $("#JobCardlistlist").empty();
	   $("#JobCardlistlist").html(htmlContent);
	   refreshPermission();
}
function copyGK(){
	var djgkid=$('#gkRadio:checked').val();
	if(djgkid==null || djgkid==""){
		$("#alertModalDjgk").hide();
		return false;
	}
/*	var jx=$('#gkRadio:checked').next().val();
	var dprtcode=$("#dprtcode").val();*/
	//根据定检工卡id查询该数据
	selectJobCard(djgkid);
	
}
//根据定检工卡id查询该数据
function selectJobCard(djgkid){
	AjaxUtil.ajax({
 		url:basePath + "/project/jobCard/selectJobCardByid",
 		type:"post",
 		data:{
 			'id' : djgkid
 		},
 		dataType:"json",
 		success:function(data){
	   if(data.jobCard!=null){
		   pasteJobCard(data.jobCard);
	   }
   }
 });
}
//粘贴工卡
function  pasteJobCard(jobCard){
	if(jobCard.gklx==1){
		$("#lxSelect").show();
		$("#lxSelectzjh").hide();
		$("#wxfabh").val(formatUndefine(jobCard.wxfabh));
		$("#djxmid").val(formatUndefine(jobCard.djxmid));
		$("#djbh").val(formatUndefine(jobCard.djbh));
		$("#djgznbid").val(formatUndefine(jobCard.nbxh));
		$("#wxfabb").val(formatUndefine(jobCard.wxfabb));
		$("#xzwxfa").val(formatUndefine(jobCard.wxfabh)+" "+formatUndefine(jobCard.wxfazwms));
		$("#gldjxm").val(formatUndefine(jobCard.djbh)+" "+formatUndefine(jobCard.djxmzwms));
		if(jobCard.nbxh=="" || jobCard.nbxh==null){
			$("#djgznr").val("");
		}else{
			$("#djgznr").val("第"+jobCard.nbxh+"条");
		}
		$("#zjhAndMc").val(formatUndefine(jobCard.zjh)+" "+formatUndefine(jobCard.zjhZwms));
		$("#zjh").val(formatUndefine(jobCard.zjh));
	}else{
		$("#lxSelect").hide();
		$("#lxSelectzjh").show();
		$("#fdjgkZjhName").val(formatUndefine(jobCard.zjh)+" "+formatUndefine(jobCard.zjhZwms));
		$("#fdjgkZjh").val(formatUndefine(jobCard.zjh));
		$("#wxfabh").val("");
		$("#djxmid").val("");
		$("#djbh").val("");
		$("#djgznbid").val("");
		$("#wxfabb").val("");
		$("#xzwxfa").val("");
		$("#gldjxm").val("");
		$("#djgznr").val("");
		$("#zjhAndMc").val("");
		$("#zjh").val("");
	}
	
	
	$("#gkbh").val(formatUndefine(jobCard.gdbh));
	$("#bb").val(formatUndefine(jobCard.bb));
	$("#zy").val(formatUndefine(jobCard.zy));
	$("#pc").val(formatUndefine(jobCard.pc));
	$("#gklx").val(formatUndefine(jobCard.gklx));
	$("#gzgroup").val(formatUndefine(jobCard.gzzId));
	$("#jx").val(formatUndefine(jobCard.jx));

	$("#cksc").val(formatUndefine(jobCard.cksc));
	$("#ckgk").val(formatUndefine(jobCard.ckgk));
	if(jobCard.yxx==1){
		$("input[type=radio][name=yxx][value=1]").attr("checked",true);
	}else{
		$("input[type=radio][name=yxx][value=0]").attr("checked",true);
		
	}
	
	$("#gzdd").val(formatUndefine(jobCard.gzdd));
	$("#gzzw").val(formatUndefine(jobCard.gzzw));
	$("#jhgsRs").val(formatUndefine(jobCard.jhgsRs));
	$("#jhgsXss").val(formatUndefine(jobCard.jhgsXss));
	sumTotal();
	$("#zhut").val(formatUndefine(jobCard.gzzt));
	$("#bcwj").val(formatUndefine(jobCard.bcwj));
	$("#bz").val(formatUndefine(jobCard.bz));
	
	$("#gdjcid").val(formatUndefine(jobCard.gdjcid));
	$("#jobCardDprtcode").val(formatUndefine(jobCard.jobCardDprtcode));
	//回显该工卡的工作内容
	workId=1;
	gznrHx();
	//回显该工卡的相关工卡
	glgdgknum=1;
	xggkHx();
	//回显该工卡的航材耗材
	hcxuhao=1;
	hchcgjHx();
}
//回显工作内容
function gznrHx(){
	var gdjcid=$('#gdjcid').val();
	var obj={
			mainid : gdjcid
	};
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedGznrList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   //oldGznrList=data.rows;
			   appendSelecedGznr(data.rows);
	   }
	 });
}
//拼接工作内容

function appendSelecedGznr(list){
	var htmlContent = '';
  var htmlContent = ""; 
  $.each(list,function(index,row){
	  oldGznrList.push(row.id);	
	  var check="";
	  if(row.isBj==1){
		  check="checked";
	  };
	   htmlContent = htmlContent + "<tr  name='one_line' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
	   htmlContent = htmlContent + "<td><button  onclick='del_tr(this)'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>" ;
	   htmlContent = htmlContent + "<td style='vertical-align:middle' name='workId'>"+workId+"</td>";  
	   /*htmlContent = htmlContent + "<td><input maxlength='300' class='form-control' type='text' name='gznr' value='"+formatUndefine(row.gznr)+"' /></td>";*/
	   htmlContent = htmlContent + "<td><textarea maxlength='300' class='form-control' name='gznr' style='height:40px'>"+StringUtil.escapeStr(formatUndefine(row.gznr))+"</textarea></td>";  
	   htmlContent = htmlContent + "<td><input maxlength='6' class='form-control' type='text' name='gzz' value='"+StringUtil.escapeStr(formatUndefine(row.gzz))+"' ></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='gznrId' type='hidden' value="+row.id+" />";
	   if(row.isBj==1){
		   htmlContent = htmlContent + "<input name='isBj' type='checkbox'  class='form-control' checked="+check+" /></td>";  
	   }else{
		   htmlContent = htmlContent + "<input name='isBj' type='checkbox'  class='form-control' /></td>";  
		   
	   }
	   htmlContent = htmlContent + "</tr>";  
	   workId++;
	 });
		$("#list").empty();
	    $("#list").append(htmlContent);
}
//回显该工卡的航材耗材
function hchcgjHx(){
	var gdjcid=$('#gdjcid').val();
	var obj={
			mainid : gdjcid
	};
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedHcList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   //oldHchcList=data.rows;
			   appendSelecedHc(data.rows);
	   }
	 }); 
}
function appendSelecedHc(list){
	 var htmlContent = '';
	   var htmlContent = ""; 
	   $.each(list,function(index,row){
		   hcxh++;
		   
		   oldHchcList.push(row.id);
			htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+row.bjid+"'>";
			htmlContent = htmlContent + "<td><a href=javascript:lineRemove2('"+row.bjid+"')><button><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></a>" +
									"<input type='hidden' name='bjid' value='"+formatUndefine(row.bjid)+"'>" +
									"<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"'>" +
							   		"<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"'>" +
							   		"<input type='hidden' name='bjh' value='"+StringUtil.escapeStr(formatUndefine(row.jh))+"'>" +
							   		"<input type='hidden' name='kycksl' value="+formatUndefine(row.sl)+">" +
							   		"<input type='hidden' name='hcxxId' value="+formatUndefine(row.id)+">" +
							   		"<input type='hidden' name='hclx' value="+formatUndefine(row.lx)+">" +
							   		"</td>";
			htmlContent = htmlContent + "<td class='text-center' name='hcxuhao'>"+hcxuhao+"</td>"; 
			htmlContent = htmlContent + "<td style='vertical-align:middle'><input name='refJhly' type='text' class='form-control' maxlength='100' value='"+StringUtil.escapeStr(formatUndefine(row.refJhly))+"'></td>"; 
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.zwmc))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.ywmc))+"</td>"; 
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(formatUndefine(row.jh))+"'>"+StringUtil.escapeStr(formatUndefine(row.jh))+"</td>";  
			htmlContent = htmlContent + "<td class='text-right' style='vertical-align:middle'>"+StringUtil.escapeStr(formatUndefine(row.kykcsl))+"</td>";  
			htmlContent = htmlContent + "<td ><input   onkeyup='clearNoNum(this)'  placeholder='输入数字...' name='sl' type='text' class='form-control' value='"+formatUndefine(row.sl)+"'></td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.lx)+"</td>";  
			htmlContent = htmlContent + "<td ><input name='bz' type='text' class='form-control' value='"+StringUtil.escapeStr(formatUndefine(row.bz))+"'></td>";   
			htmlContent = htmlContent + "</tr>";
			hcids.push(row.bjid);
			hcxuhao++;
		 });
			$("#CKlist").empty();
		    $("#CKlist").append(htmlContent);
}
//回显相关工卡
function xggkHx(){
	var mainid=$('#gdjcid').val();
	var dprtcode=$('#jobCardDprtcode').val();
	var jx=$('#jx').val();
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedXggkList",
		   type: "post",
		   dataType:"json",
		   data:{
			   'mainid' : mainid,
			   'jx' : jx,
			   'dprtcode':dprtcode
		   },
		   success:function(data){
			   //oldXggkList=data.rows;
			   appendSelecedXggk(data.rows);
	   }
	 });
}

function sumTotal(){
	var totle = 0;
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	totle = jhgsRs*jhgsXss;
	if(totle == ''){
		totle = 0;
	}
	totle = totle.toFixed(2);
	$("#bzgs").val(totle);
	
}
//拼接相关工卡
function appendSelecedXggk(list){
	//alert(JSON.stringify(list));
	
	   var htmlContent = ""; 
		 $.each(list,function(index,row){ 
			 oldXggkList.push(row.id);
			htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+row.xggdid+"'>";
			htmlContent = htmlContent + "<td><button  onclick=\"gdbhRemove('"+row.xggdid+"')\"><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button> </td>";  
			
			htmlContent = htmlContent + "<td class='text-center' name='glgdgknum'>"+glgdgknum+"</td>";
			
			htmlContent = htmlContent + "<td class='text-left'><input type='hidden' name='xggdid' value="+row.xggdid+"><input type='hidden' name='xggd_lx' value="+row.xggdLx+"><input type='hidden' name='xggd_zlx' value="+row.xggdZlx+"><input type='hidden' name='xgjcgdid' value="+row.xgjcgdid+"><input type='hidden' name='id' value="+row.id+">";
			if(row.xggdLx == 5){
				htmlContent = htmlContent +"<a href=\"javascript:viewMainJobCard('"+row.xggdid+"','"+row.dprtcode+"')\">"+formatUndefine(row.gdbh)+"</a>"; 
			}else{
				htmlContent = htmlContent + "<a href=\"javascript:Looked('"+ row.xggdid + "',"+row.xggdLx+")\">"+formatUndefine(row.gdbh)+"</a>";  
			}
			
			htmlContent = htmlContent +"</td>";
			
			if(row.xggdLx==2){
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.xggdLx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',row.xggdZlx)+"</td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left'>"+DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.xggdLx)+"</td>";  
		   }
			
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zhut))+"'>"+StringUtil.escapeStr(formatUndefine(row.zhut))+"</td>";
			htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.zy))+"</td>";  
			htmlContent = htmlContent + "</tr>";
			//pgdids.push(pgdId);
			XggkGdbhs.push(row.xggdid);
			glgdgknum++;
			  });
			$("#appendlistXggk").empty();
		    $("#appendlistXggk").append(htmlContent);
		    //判断是否已选择，
			   for(var i=0;i<XggkGdbhs.length;i++){
				   $("#XggkCheckbox"+XggkGdbhs[i]).attr("checked", "checked");
				   //alert($("#XggkCheckbox"+XggkGdbhs[i]).val());
			   };
	
}
/*//回显上传附件
function scwj(){
	var mainid=$('#gdjcid').val();
		AjaxUtil.ajax({
		   url:basePath+"/project/workorder/selectedScwjList",
		   type: "post",
		   dataType:"json",
		   data:{
			   'mainid' : mainid
		   },
		   success:function(data){
			   //oldScwjList=data.rows;
			   appendSelecedScwj(data.rows);
	   }
	 });
}
//拼接上传附件
function appendSelecedScwj(list){
	//alert(JSON.stringify(list));
	var htmlContent="";
	 $.each(list,function(index,row){ 
		 oldScwjList.push(row.id);
		 htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
		 htmlContent = htmlContent+'<td><div>';
//	 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 htmlContent = htmlContent+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+row.id+ '\')" title="Delete 删除">  ';
		 htmlContent = htmlContent+'</div></td>';
		 htmlContent = htmlContent+'<td>'+row.yswjm+'</td>';
		 htmlContent = htmlContent+'<td>'+row.nbwjm+'</td>';
		 htmlContent = htmlContent+"<td class='text-left'><a href=\"javascript:downloadfile('"+row.id+"')\">"+formatUndefine(row.wbwjm)+"</td>";
		 htmlContent = htmlContent+"<td class='text-left'>"+formatUndefine(row.wjdxStr)+'</td>';
		 htmlContent = htmlContent+"<td class='text-left'>"+formatUndefine(row.czr_user.displayName)+'</td>';
		 htmlContent = htmlContent+"<td class='text-center'>"+formatUndefine(row.czsj)+'</td>';
		 htmlContent = htmlContent+'<td>'+row.cflj+'</td>';
		 	 htmlContent = htmlContent+'<input type="hidden" name="fjid" value=\''+row.id+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="nbwjm" value=\''+row.nbwjm+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="yswjm" value=\''+row.yswjm+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wjdx" value=\''+row.wjdx+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wbwjm1" value=\''+row.wbwjm+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="cflj" value=\''+row.cflj+'\'/>';
		 
		 htmlContent = htmlContent+'</tr>';
	 });
	 $("#filelist").empty();
	 $('#filelist').append(htmlContent);
	
}*/
//查看跳转
function viewMainJobCard(id,dprtCode){
	window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode=" + dprtCode+"&pageParam="+encodePageParam());
}
function encodePageParam(){
	 var pageParam={};
	 var params={};
	 pageParam.params=params;
	 pageParam.pagination=pagination;
	 return Base64.encode(JSON.stringify(pageParam));
}
//加载定检信息
function jzdjxx(id){
	 AjaxUtil.ajax({
		 url:basePath + "/project/jobCard/selectBydjgznrid",
		 type:"post",
		 async: false,
		 data:{
			 'id' : id
		 },
		 dataType:"json",
		 success:function(data){
			jzxx(data.jobCard);
		 }
	 });
}
/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function jzxx(params){
	try{
		$("#wxfabh").val(formatUndefine(params.wxfabh));
		$("#djxmid").val(formatUndefine(params.djxmid));
		$("#djbh").val(formatUndefine(params.djbh));
		$("#djgznrid").val(formatUndefine(params.djgznrid));
		$("#djgznbid").val(formatUndefine(params.djgznrid));
		$("#wxfabb").val(formatUndefine(params.wxfabb));
		$("#zjhAndMc").val(formatUndefine(params.zjh)+" "+formatUndefine(params.zjhZwms));
		$("#zjh").val(formatUndefine(params.zjh));
		$("#cksc").val(formatUndefine(params.cksc));
		$("#ckgk").val(formatUndefine(params.ckgk));
		$("#gzdd").val(formatUndefine(params.gzdd));
		$("#gzzw").val(formatUndefine(params.gzzw));
		$("#zhut").val(formatUndefine(params.scmsYw)+" "+formatUndefine(params.scmsZw));
		
		$("#jx").val(formatUndefine(params.jx));
		$("#xzwxfa").val(formatUndefine(params.wxfabh)+" "+formatUndefine(params.wxfamc));
		$("#gldjxm") .val(formatUndefine(params.djbh)+" "+formatUndefine(params.djxmmc));
		$("#djgznbid").val(formatUndefine(params.nbxh));
		$("#djgznr").val("第"+params.nbxh+"条");
		$("#gklx").val("1");
	}catch(e){
		//AlertUtil.showErrorMessage("加载数据失败!");
	}
}
//选择非定检工卡时，选择ATA章节号
//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#fdjgkZjh").val());
	var dprtcode = $.trim($("#dprtcode").val());
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		callback: function(data){//回调函数
			var wczjh = '';
			var wczjhName = '';
			if(data != null){
				wczjh = data.zjh;
				wczjhName = data.zwms;
				wczjhName = formatUndefine(wczjh) + " " + formatUndefine(wczjhName);
			}
			$("#fdjgkZjh").val(formatUndefine(wczjh));
			$("#fdjgkZjhName").val(formatUndefine(wczjhName));
		}
	})
}

//只能输入数字和小数,保留两位
function clearNoNumTwo(obj){
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
     sumTotal();
}
//只能输入数字和小数
function clearNoNum(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     sumTotal();
}

//验证版本只能输入数字和小数,不能超过最大版本
function vilidateBb(obj){
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
    
    if(obj.value >= Number($("#rwxfabb").val())){
    	obj.value = $("#rwxfabb").val();
    }
     
}
