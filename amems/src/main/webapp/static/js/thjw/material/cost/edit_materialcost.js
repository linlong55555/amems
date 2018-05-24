$(document).ready(function(){
	//数据字典
	Navigation(menuCode,"维护航材成本","Edit AirMaterial");
	
	DicAndEnumUtil.registerDic('1','jldw',$("#dprtcode").val());
	initChapter();
	
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	//生成多选下拉框动
	$('#jx').multiselect({
		buttonClass: 'btn btn-default',
        buttonWidth: 'auto',
        numberDisplayed:20,
	    includeSelectAllOption: true,
	    onChange:function(element,checked){
	    }
	});
	$("#jldw").val($("#jldw-hx").val());
	
	changeMaterialType();
	setReadonlyDisabled();
	
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	juescb: {
	        	validators: {
		            regexp: {
	                    regexp: /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/,
	                    message: '决算成本格式不正确'
	                },
	            }
            },
            jiescb: {
            	validators: {
		            regexp: {
	                    regexp: /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/,
	                    message: '结算成本格式不正确'
	                },
	            }
            },
            gscb: {
            	validators: {
		            regexp: {
	                    regexp: /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/,
	                    message: '估算成本格式不正确'
	                },
	            }
            
            }
        }
    });
	goPage(1,"id","desc");//开始的加载默认的内容 
	getFjjxData($("#id").val());
});

function getFjjxData(id){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/material/material/selectById",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(data != null){
				var jx = '';
				var fjjx = [];
				if(data.xh == '00000'){
					jx = '通用Currency';
				}else{
					if(data.xh == null || data.xh == ''){
						if(data.materialToPlaneModelList != null && data.materialToPlaneModelList.length > 0){
							$.each(data.materialToPlaneModelList,function(index,row){
								fjjx.push(row.fjjx);
							});
							jx = fjjx.join(",");
						}
					}
					
				}
				$("#jxdiv").val(jx);
			}
		}
	});
}

function changeMaterialType(){
	var type = $("#hclx").val();
	if(1 == type){
		$("#materialType").show();
	}else{
		$("#materialType").hide();
		$("#hclxEj").val("");
	}
}

//设置只读或失效
function setReadonlyDisabled(){
	$("select").attr("disabled","true");
	$("#isMel").attr("disabled","true");
	$("#bz").attr("readonly","readonly");
}

//初始化ATA章节号信息
function initChapter(){
 	var zjh = $.trim($("#zjh").val());
 	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixchapter/getFixChapterByZjh",
		type:"post",
		data:{zjh:zjh,dprtcode:$("#dprtcode").val()},
		dataType:"json",
		success:function(data){
			if(null != data){
				$("#zjhName").val(data.zjh+" "+data.zwms);
			}
		},
	});
}


function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		 AjaxUtil.ajax({
			 url:basePath+"/aerialmaterial/cost/queryCostListByPage",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,13]);
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"6\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
	      },
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var mainid=$("#id").val();
		 searchParam.mainid = mainid;
		 return searchParam;
	 }
	 
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\">";
			   }
			   htmlContent = htmlContent + "<td><a href=javascript:lineRemove2('"+row.id+"')><button class='line6'><i class='fa glyphicon glyphicon-minus cursor-pointer'></i></button></a></td>";  
			  // htmlContent += "<td><i class='icon-trash color-blue cursor-pointer' onclick=\"lineRemove2('"+row.id+"')\" title='删除  Delete'></td>";  
			   htmlContent += "<td class='text-right' style='vertical-align:middle'>"+formatUndefine(row.juescb)+"</td>"; 
			   htmlContent += "<td class='text-right' style='vertical-align:middle'>"+formatUndefine(row.jiescb)+"</td>"; 
			   htmlContent += "<td class='text-right' style='vertical-align:middle'>"+formatUndefine(row.gscb)+"</td>"; 
			   htmlContent += "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>"; 
			   htmlContent += "<td style='vertical-align:middle'>"+formatUndefine(row.whsj)+"</td>";
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		 
	 }
	 function addHcCost() {
	    $("#alertModalAdd").modal("show");
	    $('#juescb').val("");
	    $('#jiescb').val("");
	    $('#gscb').val("");
     } 
	 
	 function AddCost(){                                              //新增报价记录
		  $('#form').data('bootstrapValidator').validate();
			if(!$('#form').data('bootstrapValidator').isValid()){
				return false;
			}
			var mainid=$.trim( $('#id').val());
			var juescb=$.trim( $('#juescb').val());        
			var jiescb=$.trim( $('#jiescb').val());
			var gscb=$.trim( $('#gscb').val());
			if(juescb==""&&jiescb==""&&gscb==""){
				AlertUtil.showErrorMessage("航材报价中至少有一个不能为空！");
				return false;
			}
			var materialcost={};
			
			materialcost.mainid=mainid;
			materialcost.juescb=juescb;
			materialcost.jiescb=jiescb;
			materialcost.gscb=gscb;
			
			AjaxUtil.ajax({
				url:basePath+"/aerialmaterial/cost/add",
				type: "post",
				contentType:"application/json;charset=utf-8",
				data:JSON.stringify(materialcost),
				dataType:"json",
				success:function(data){
					if(data>0){
						goPage(1,"id","desc");//开始的加载默认的内容 
						AlertUtil.showMessage('保存成功!');
					}
				},
			});
	}
	    //删除航次报价记录
		function lineRemove2(id) {
			AlertUtil.showConfirmMessage("你确定要删除报价吗？",{callback: function(){
				AjaxUtil.ajax({
					url:basePath+"/aerialmaterial/cost/delete?id="+id,
					contentType:"application/json;charset=utf-8",
					type:"post",
					dataType:"json",
					success:function(data){
						if(data>0){
							goPage(1,"id","desc");//开始的加载默认的内容 
							AlertUtil.showMessage('删除成功!');
						}
					},
				});
			}});
		}
		//返回前页面
		 function back(){
			 var id=$.trim( $('#id').val());
		 	 window.location.href =basePath+"/aerialmaterial/cost/main?id="+id+"&pageParam="+pageParam;
		 }