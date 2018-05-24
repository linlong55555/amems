
var id = "";
var pagination = {};

var alertMaterialFormId = 'alertMaterialForm';
var materialHeadChinaId = 'materialHeadChina';
var materialHeadEnglishId = 'materialHeadEnglish';
var jldw = '';
var ckh = '';
var kwh = '';
var gljb = '';
var hclx = '';
var hclxEj = '';
var planeModelOption = '';
var isProduction = false;

var subId = "subListView";

$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	goPage(1,"auto","desc");//开始的加载默认的内容 
	initInfo();
	refreshPermission();
	formValidator();
	//初始化日志功能
	logModal.init({code:'D_008'});
});

//form验证规则
function formValidator(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	bjh: {
                validators: {
                	notEmpty: {
                        message: '件号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            bzjh: {
                validators: {
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            jldw: {
                validators: {
                    notEmpty: {
                        message: '单位不能为空'
                    }
                }
            },
            cjjh: {
                validators: {
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            },
            zwms: {
                validators: {
                    /*notEmpty: {
                        message: '航材中文名称不能为空'
                    },*/
                    /*regexp: {
                        regexp: /^[\u4e00-\u9fa5]{0,}$/,
                        message: '只能输入中文'
                    },*/
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            },
            ywms: {
                validators: {
                	notEmpty: {
                        message: '英文名称不能为空'
                    },
                    /*regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },*/
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            },
            xingh: {
                validators: {
                	regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            }, 
            gg: {
                validators: {
                	regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
                        message: '不能输入中文且长度最多不超过50个字符'
                    }
                }
            }, 
            minKcl: {
                validators: {
                	stringLength: {
                        max: 10,
                        message: '长度最多不超过10个字符'
                    }
                }
            }, 
            maxKcl: {
                validators: {
                	stringLength: {
                        max: 10,
                        message: '长度最多不超过10个字符'
                    }
                }
            }            
        }
    });
}

//隐藏Modal时验证销毁重构
$("#"+alertMaterialFormId).on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     formValidator();
});

function initInfo(){
	//数据字典
	DicAndEnumUtil.registerDic('1','jldw',$("#dprtcode").val());
	changeStore();
	//初始化机型多选下拉框
	initMultiselect();
	$("#jx", $("#"+alertMaterialFormId)).html(planeModelOption);
	//生成多选下拉框动
	changeMaterialType();
	jldw = $.trim($("#jldw", $("#"+alertMaterialFormId)).val());
	ckh = $.trim($("#ckh", $("#"+alertMaterialFormId)).val());
	kwh = $.trim($("#kwh", $("#"+alertMaterialFormId)).val());
	gljb = $.trim($("#gljb", $("#"+alertMaterialFormId)).val());
	hclx = $.trim($("#hclx", $("#"+alertMaterialFormId)).val());
	hclxEj = $.trim($("#hclxEj", $("#"+alertMaterialFormId)).val());
}
//初始化机型多选下拉框
function initMultiselect(){
	//生成多选下拉框(机型)
	$("#jxdiv", $("#"+alertMaterialFormId)).empty();
	$("#jxdiv", $("#"+alertMaterialFormId)).html("<select multiple='multiple' id='jx' ></select>");
	$("#jx", $("#"+alertMaterialFormId)).append(planeModelOption);
	$("#jx", $("#"+alertMaterialFormId)).multiselect({
		buttonClass: 'btn btn-default',
//        buttonWidth: 'auto',
        buttonClass: 'btn btn-default',
	    buttonWidth: '100%',//auto
	    buttonContainer: '<div class="btn-group base-data-syjx"  style="width:100%;"/>',
        numberDisplayed:10,
	    includeSelectAllOption: true,
	    onChange:function(element,checked){
	    }
	});
}

function changeMaterialType(){
	var type = $("#hclx").val();
	if(1 == type){
		 $("#hclxEj", $("#"+alertMaterialFormId)).empty();
		var list = DicAndEnumUtil.getEnum("materialSecondTypeEnum");
		if(null != list && list.length>0){
			 var html = "";
			 $.each(list,function(i,data){
				 html += "<option value='"+data.id+"'>"+data.name+"</option>";
			 })
			 $("#hclxEj", $("#"+alertMaterialFormId)).append(html);
		 }
		var hclxEj = $("#hclxEj", $("#"+alertMaterialFormId)).val();
		if(null == hclxEj || '' == hclxEj){
			$("#hclxEj", $("#"+alertMaterialFormId)).val(14);
		}
		$("#materialType").show();
	}else if(2 == type){
		 $("#hclxEj", $("#"+alertMaterialFormId)).empty();
		 var list = DicAndEnumUtil.getEnum("materialToolSecondTypeEnum");
		 if(null != list && list.length>0){
			 var html = "";
			 $.each(list,function(i,data){
				 html += "<option value='"+data.id+"'>"+data.name+"</option>";
			 })
			 $("#hclxEj", $("#"+alertMaterialFormId)).append(html);
		 }
		 $("#materialType").show();
	}else{
		$("#materialType").hide();
	}
}

//仓库改变时,重新加载库位信息
function changeStore(){
 	var ckid = $.trim($("#ckh").val());
 	AjaxUtil.ajax({
		async: false,
		url:basePath+"/material/store/queryStorageListByStoreId",
		type:"post",
		data:{storeId:ckid},
		dataType:"json",
		success:function(data){
			buildStorage(data);	
		}
	});
}

//加载库位信息
function buildStorage(storageList){

 	$("#kwh").empty();
 	
 	var option = '<option value=""></option>';
 	for(var i = 0 ; i < storageList.length ; i++){
 		var storage = storageList[i];
 		option += '<option value="'+storage.id+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
 	}
 	$("#kwh").append(option);
}

//初始化仓库数据
function initStoreData(){
	AjaxUtil.ajax({
	   url:basePath+"/material/store/selectByDprtcode",
	   async: false,
	   type: "post",
	   dataType:"json",
	   data:{dprtcode:$("#dprtcode").val()},
	   success:function(data){
		   $("#ckh").empty();
		   var tempOption = '<option value=""></option>';
		   if(data != null && data.length >0){
			   $.each(data,function(i,store){
				   tempOption += "<option value='"+store.id+"' >"+StringUtil.escapeStr(store.ckh)+" " + StringUtil.escapeStr(store.ckmc)+"</option>";
			   });
		   }
		   $("#ckh").append(tempOption);
	   }
   }); 
}


//初始化飞机机型
function initPlaneModelData(){
	AjaxUtil.ajax({
	   url:basePath+"/project/planemodeldata/findAllType",
	   async: false,
	   type: "post",
	   dataType:"json",
	   data:{dprtcode:$("#dprtcode").val()},
	   success:function(data){
		   var tempOption = '';
		   tempOption += "<option value='00000' >通用Currency</option>";
		   if(data.length >0){
			   $.each(data,function(i,planeModel){
				 	tempOption += "<option value='"+StringUtil.escapeStr(planeModel)+"' >"+StringUtil.escapeStr(planeModel)+"</option>";
			   });
		   }
		   planeModelOption = tempOption;
	   }
   }); 
}

	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if(id != ""){
			searchParam.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
			 url:basePath+"/material/material/queryAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   
				 	 new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(pageNumber,sortType,sequence){
								AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
							}
						}); 
				   
				   
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,11,14,15,18,19],"#list tr td");
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"21\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'material_main_table'});
	      }
	    }); 
		
	 }
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#keyword_search").val());
		 var gljb = $.trim($("#gljb_search").val());
		 var sxkz = $.trim($("#sxkz_search").val());
		 var hclx = $.trim($("#hclx_search").val());
		 var bz = $.trim($("#bz_search").val());
		 searchParam.dprtcode = $.trim($("#dprtcode_search").val()); 
		 var whrq = $.trim($("#whrq_search").val()); 
		 searchParam.keyword = keyword;
		 if('' != gljb){
			 searchParam.gljb = gljb;
		 }
		 if('' != sxkz){
			 searchParam.sxkz = sxkz;
		 }
		 if('' != hclx){
			 searchParam.hclx = hclx;
		 }
		 if('' != bz){
			 searchParam.bz = bz;
		 }
		 if(null != whrq && "" != whrq){
			 var whrqBegin = whrq.substring(0,10)+" 00:00:00";
			 var whrqEnd = whrq.substring(13,23)+" 23:59:59";
			 paramsMap.whrqBegin = whrqBegin;
			 paramsMap.whrqEnd = whrqEnd;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent += "<tr bgcolor=\"#fefefe\">";
			   }
			      
			   htmlContent += "<td class='fixed-column text-center'>";
			   
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='material:material:main:02' onClick=\"edit('"+ row.id + "','" + row.dprtcode + "',false)\" title='维护基本信息 Maintenance'>&nbsp;&nbsp;</i>";
			  /* htmlContent += "<i class='icon-random color-blue cursor-pointer checkPermission' permissioncode='material:material:main:05' onClick=\"edit('"+ row.id + "','" + row.dprtcode + "',true)\" title='维护生产信息 Maintenance'>&nbsp;&nbsp;</i>";*/
			   
			   htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='material:material:main:03' onClick=\"del('"+ row.id + "')\" title='作废 Invalid'></i>";  
			   
			   htmlContent += "</td>";  
			   
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' class='text-left'>";
			   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id + "')\">"+StringUtil.escapeStr(row.bjh)+"</a>";
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.cjjh)+"' class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+"' class='text-left'>"+StringUtil.escapeStr(row.ywms)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' class='text-left'>"+StringUtil.escapeStr(row.zwms)+"</td>"; 
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' class='text-left'>"+StringUtil.escapeStr(row.jhly)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bzjh)+"' class='text-left'>"+StringUtil.escapeStr(row.bzjh)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.gse)+"' class='text-left'>"+StringUtil.escapeStr(row.gse)+"</td>";
			   var zzjbs=row.zzjbs=="1"?"是":"否";
			   htmlContent += "<td  title='"+StringUtil.escapeStr(zzjbs)+"' class='text-center'>"+StringUtil.escapeStr(zzjbs)+"</td>";
			   htmlContent += "<td title='"+SubstitutionUtil.formatTitle(row)+"' class='text-left'>" + SubstitutionUtil.formatContent(row) + "</td>";
			   if('00000' == formatUndefine(row.xh)){
				   htmlContent = htmlContent + "<td>通用Currency</td>";
			   }else{
				   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.xh)+"' class='text-left'>"+StringUtil.escapeStr(row.xh)+"</td>";
			   }
			   
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb) +"</td>";
			   htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('agingControlEnum',row.sxkz) +"</td>";
			   if(1 == row.isMel){
				   htmlContent += "<td class='text-center'>是</td>";
			   }else{
				   htmlContent += "<td class='text-center'>否</td>";
			   }
			   var zjh = '';
			   if(row.fixChapter != null){
				   zjh = StringUtil.escapeStr(row.fixChapter.zjh) + " " + StringUtil.escapeStr(row.fixChapter.ywms);
			   }
			   htmlContent += "<td title='"+zjh+"' class='text-left'>"+zjh+"</td>";
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx) +"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jldw) +"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
			   if(undefined != row.zdr){
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
			   }
			   else{
				   htmlContent += "<td class='text-left'>"+"</td>";
			   }
			   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.cjsj)+"</td>";
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
			   htmlContent += "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission();
	 }
//等效替代
SubstitutionUtil = {
	id : "SubstitutionUtil",
	viewId : "alertModalView",
	flag : true,
	formatTitle : function(row){
		var list = row.substitutionList;
		var htmlContent = '';
		if(list != null && list != "" && list.length > 0 ){
			$.each(list,function(i,obj){
				var tdjh = '';
				if(obj.knxbs == 2 && obj.bjh != row.bjh){
					tdjh = StringUtil.escapeStr(obj.bjh);
				}else{
					tdjh = StringUtil.escapeStr(obj.tdjh);
				}
				htmlContent += tdjh;
				if(i != list.length - 1){
					htmlContent += ",";
				}
			});
		}
		return htmlContent;
	},
	formatContent : function(row){
		var strs = row.substitutionList
		var this_ = this;
		var htmlContent = '';
		if(strs != null && strs.length > 0){
			$.each(strs,function(i,obj){
				if(i == 1){
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=CollapseOrExpandUtil.collapseOrExpand(this,'material_main_table')></i><div class='affectedDisplayFile' style='display:none'>";
				}
				var tdjh = '';
				if(obj.knxbs == 2 && obj.bjh != row.bjh){
					tdjh = StringUtil.escapeStr(obj.bjh);
				}else{
					tdjh = StringUtil.escapeStr(obj.tdjh);
				}
				htmlContent += "<a href='#' onclick='"+this_.id+".viewSubstitution(\""+obj.id+"\")'>"+tdjh+"</a>";
				if(i != 0 && i != strs.length - 1){
					htmlContent += "<br/>";
				}
				if(i == strs.length - 1){
					htmlContent += "</div>";
				}
			});
		}
		return htmlContent;
	},
	viewSubstitution : function(id){
		open_win_equivalent_substitution.show({
			id : id,
			isParamId : true
		});
	}
}

//打开新增航材弹出框
function openMaterialAdd(){
	$("#"+subId,$("#"+alertMaterialFormId)).hide();
	//数据字典
	$("#jldw", $("#"+alertMaterialFormId)).empty();
	DicAndEnumUtil.registerDic('1','jldw',userJgdm);
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).removeAttr("checked");
	$("input[name='zzjbs'][value='0']",$("#"+alertMaterialFormId)).attr("checked","true");
	$("#dprtcode").val(userJgdm);
	initPlaneModelData();
	initStoreData();
	if(checkBtnPermissions("material:material:main:05")){
		setAddAndEdit();
	}else{
		setAdd();//设置可编辑
		$("#ckh", $("#"+alertMaterialFormId)).text('');
		$("#kwh", $("#"+alertMaterialFormId)).text('');
		
	}
	$("#"+materialHeadChinaId, $("#"+alertMaterialFormId)).html('新增部件');
	$("#"+materialHeadEnglishId, $("#"+alertMaterialFormId)).html('Add');
	var obj = {};
	obj.jldw = jldw;
	obj.ckh = ckh;
	obj.kwh = kwh;
	obj.hcjz = '';
	obj.gljb = gljb;
	obj.hclx = hclx;
	obj.hclxEj = hclxEj;
	obj.isMel = 1;
	obj.sxkz = 0;
	obj.xh = '00000';
	setMaterialData(obj);//设置航材表单数据
	$("#"+alertMaterialFormId).modal("show");
	
	AttachmengsList.show({
 		djid:'',
 		colOptionhead : true,
		fileHead : true,
		fileType:"part"
	});//显示附件
	
	//隐藏出现异常的感叹号
	AlertUtil.hideModalFooterMessage();
}

//打开修改航材航材弹出框
function openMaterialEdit(id,dprtcode){
	$("#"+subId,$("#"+alertMaterialFormId)).hide();
	//隐藏出现异常的感叹号
	AlertUtil.hideModalFooterMessage();
	//数据字典
	$("#jldw", $("#"+alertMaterialFormId)).empty();
	DicAndEnumUtil.registerDic('1','jldw',dprtcode);
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).removeAttr("checked");
	$("input[name='zzjbs'][value='0']",$("#"+alertMaterialFormId)).attr("checked","true");

	isProduction = checkBtnPermissions("material:material:main:05");
	$("#dprtcode").val(dprtcode);
	initPlaneModelData();
	initStoreData();
	selectById(id,function(obj){
		$("#"+materialHeadChinaId, $("#"+alertMaterialFormId)).html('修改部件');
		$("#"+materialHeadEnglishId, $("#"+alertMaterialFormId)).html('Edit');
		setMaterialData(obj);//设置航材表单数据
		if(isProduction){
			setAddAndEdit();
			/*var jx = obj.xh;
			if(obj.xh == '00000'){
				jx = '通用Currency';
			}else{
				jx = obj.xh.join(",");
			}*/
			/*$("#jxdiv", $("#"+alertMaterialFormId)).empty();
			var jxInput = '<input type="text" id="jxInput" name="jxInput" class="form-control" readonly/>';
			$("#jxdiv", $("#"+alertMaterialFormId)).html(jxInput);
			$("#jxInput", $("#"+alertMaterialFormId)).val(jx);
			checkUptById(id,function(data){
				if(data != null && data != ''){
					$("#gljb", $("#"+alertMaterialFormId)).attr("disabled","true");
					$("#hclx", $("#"+alertMaterialFormId)).attr("disabled","true");
				}
			})*/
		}else{
			setAdd();//设置可编辑
			if(obj.ckh == null || obj.ckh == ''){
				$("#ckh", $("#"+alertMaterialFormId)).text(formatUndefine(obj.ckh));
				$("#kwh", $("#"+alertMaterialFormId)).text(formatUndefine(obj.kwh));
			}
		}
		
		$("#bjh", $("#"+alertMaterialFormId)).attr("readonly","true");
		$("#"+alertMaterialFormId).modal("show");
		AttachmengsList.show({
 			djid:id,
 			colOptionhead : true,
 			fileHead : true,
 			fileType:"part"
 		});//显示附件
		selectSubByBjhAndDprt(obj.bjh,obj.dprtcode);
	});
	$("#gljb", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#hclx", $("#"+alertMaterialFormId)).attr("disabled","true");
	
}

//打开查看航材航材弹出框
function openMaterialView(id,dprtcode){
	$("#jldw", $("#"+alertMaterialFormId)).empty();
	DicAndEnumUtil.registerDic('1','jldw',dprtcode);
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).removeAttr("checked");
	$("#dprtcode").val(dprtcode);
	initStoreData();
	selectById(id,function(obj){
		$("#"+materialHeadChinaId, $("#"+alertMaterialFormId)).html('查看部件');
		$("#"+materialHeadEnglishId, $("#"+alertMaterialFormId)).html('View');
		setMaterialData(obj);//设置航材表单数据
		$("#"+alertMaterialFormId).modal("show");
		setView();//设置只读/失效
		var jx = obj.xh;
		if(obj.xh == '00000'){
			jx = '通用Currency';
		}else{
			jx = obj.xh.join(",");
		}
		$("#jxdiv", $("#"+alertMaterialFormId)).empty();
		var jxInput = '<input type="text" id="jxInput" name="jxInput" class="form-control" readonly/>';
		$("#jxdiv", $("#"+alertMaterialFormId)).html(jxInput);
		$("#jxInput", $("#"+alertMaterialFormId)).val(jx);
		
		if(obj.ckh == null || obj.ckh == ''){
			$("#ckh", $("#"+alertMaterialFormId)).text(formatUndefine(obj.ckh));
			$("#kwh", $("#"+alertMaterialFormId)).text(formatUndefine(obj.kwh));
		}
		AttachmengsList.show({
 			djid:id,
 			colOptionhead : false,
 			fileHead : false,
 			fileType:"part"
 		});//显示附件
		selectSubByBjhAndDprt(obj.bjh,obj.dprtcode);
	});
}

//通过id获取航材数据
function selectById(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/material/material/selectById",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(data != null){
				if(data.materialToStore != null){
					data.msId = data.materialToStore.id;
					data.ckh = data.materialToStore.ckh;
					data.kwh = data.materialToStore.kwh;
				}
				if(data.xh == null || data.xh == '' || data.xh == '-'){
					var fjjx = [];
					if(data.materialToPlaneModelList != null && data.materialToPlaneModelList.length > 0){
						$.each(data.materialToPlaneModelList,function(index,row){
							fjjx.push(row.fjjx);
						});
					}
					data.xh = fjjx;
				}
				if(typeof(obj)=="function"){
					obj(data); 
				}
			}
		}
	});
}

//通过部件号、机构代码获取等效替代部件信息
function selectSubByBjhAndDprt(bjh,dprtcode){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/basic/substitution/selectSubByBjhAndDprt",
		type:"post",
		data:{bjh:bjh,dprtcode:dprtcode},
		dataType:"json",
		success:function(data){
			if(data != null && data.length > 0){
				var htmlContent = '';
				$.each(data,function(index,row){
					if (index % 2 == 0) {
					   htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
					} else {
					   htmlContent += "<tr bgcolor=\"#fefefe\"  >";
					}
					var knxbs = (row.knxbs == 1?"否":"是");
					var sujxbs = formatSub(row.applicabilityList,row.jxbs,1);
					var sugkbs = formatSub(row.applicabilityList,row.gkbs,2);
					htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";	
					htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.bjh)+"</td>";
					htmlContent += "<td title='"+StringUtil.escapeStr(row.ms)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.ms)+"</td>";
					htmlContent += "<td title='"+StringUtil.escapeStr(row.tdjh)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.tdjh)+"</td>";
					htmlContent += "<td title='"+StringUtil.escapeStr(row.tdjms)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.tdjms)+"</td>";
					htmlContent += "<td title='"+StringUtil.escapeStr(sujxbs)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(sujxbs)+"</td>";
					htmlContent += "<td title='"+StringUtil.escapeStr(sugkbs)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(sugkbs)+"</td>";
					htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+knxbs+"</td>";
					htmlContent += "</tr>";  
					    
				});
				$("#subListViewTable",$("#"+subId)).empty();
				$("#subListViewTable",$("#"+subId)).html(htmlContent);
				$("#"+subId,$("#"+alertMaterialFormId)).show();
			}
		}
	});
}
//list列表、ty通用、type类型
function formatSub(list,ty,type){
	var v = (ty == '00000'?"通用":'');
	if(ty != '00000' && list != null){
		$.each(list,function(index,row){
			if(row.syxlx == type){
				v += StringUtil.escapeStr(row.sydx) + ",";
			}
		});
		if(v != ''){
			v = v.substring(0,v.length - 1);
		}
	}
	return v;
}

//检查航材类型是否可以修改
function checkUptById(id,obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/material/material/checkUptById",
		type:"post",
		data:{id:id},
		dataType:"json",
		success:function(data){
			if(typeof(obj)=="function"){
				obj(data); 
			}
		}
	});
}



//设置航材表单数据
function setMaterialData(obj){
	$("#id", $("#"+alertMaterialFormId)).val(obj.id);
	$("#jhly", $("#"+alertMaterialFormId)).val(obj.jhly);
	$("#bzjh",$("#"+alertMaterialFormId)).val(obj.bzjh);
	$("#gse",$("#"+alertMaterialFormId)).val(obj.gse);
	$("#zzcs",$("#"+alertMaterialFormId)).val(obj.zzcs);
	if(obj.zzjbs=="1"){
		$("#zzjbs",$("#"+alertMaterialFormId)).attr("checked","true");
	}
	
	$("#bjh", $("#"+alertMaterialFormId)).val(obj.bjh);
	$("#jldw", $("#"+alertMaterialFormId)).val(obj.jldw);
	$("#cjjh", $("#"+alertMaterialFormId)).val(obj.cjjh);
	$("#zjh", $("#"+alertMaterialFormId)).val(obj.zjh);
	var zjhName = '';
	if(obj.fixChapter != null){
		zjhName = formatUndefine(obj.fixChapter.zjh) + " " + formatUndefine(obj.fixChapter.zwms);
	}
	$("#zjhName", $("#"+alertMaterialFormId)).val(zjhName);
	$("#zjhName_text", $("#"+alertMaterialFormId)).val(zjhName);
	$("#zwms", $("#"+alertMaterialFormId)).val(obj.zwms);
	$("#ywms", $("#"+alertMaterialFormId)).val(obj.ywms);
	$("#xingh", $("#"+alertMaterialFormId)).val(obj.xingh);
	$("#gg", $("#"+alertMaterialFormId)).val(obj.gg);
	if(obj.isMel == 1){
		$("#isMel", $("#"+alertMaterialFormId)).attr("checked","true");
	}else{
		$("#isMel", $("#"+alertMaterialFormId)).removeAttr("checked");
	}
	initMultiselect();//初始化机型多选下拉框
	$("#jx", $("#"+alertMaterialFormId)).multiselect('select', obj.xh);
	$("#bz", $("#"+alertMaterialFormId)).val(obj.bz);
	$("#msId", $("#"+alertMaterialFormId)).val(obj.msId);
	$("#ckh", $("#"+alertMaterialFormId)).val(obj.ckh);
	changeStore();
	$("#kwh", $("#"+alertMaterialFormId)).val(obj.kwh);
	$("#hcjz", $("#"+alertMaterialFormId)).val(obj.hcjz);
	$("#gljb", $("#"+alertMaterialFormId)).val(obj.gljb);
	$("#minKcl", $("#"+alertMaterialFormId)).val(obj.minKcl);
	$("#maxKcl", $("#"+alertMaterialFormId)).val(obj.maxKcl);
	$("#hclx", $("#"+alertMaterialFormId)).val(obj.hclx);
	$("input:radio[name='sxkz'][value='"+obj.sxkz+"']", $("#"+alertMaterialFormId)).attr("checked",true); 
	changeMaterialType();
	$("#hclxEj", $("#"+alertMaterialFormId)).val(obj.hclxEj);
	
}
//设置只读/失效/隐藏
function setView(){
	$("#bjh", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#jldw", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#cjjh", $("#"+alertMaterialFormId)).attr("readonly","true");
	$("#zjhbtn", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#zjhbtn", $("#"+alertMaterialFormId)).hide();
	$("#zwms", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#ywms", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#xingh", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#gg", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#isMel", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#bz", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#ckh", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#kwh", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#hcjz", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#gljb", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#minKcl", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#maxKcl", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#hclx", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).attr("disabled","true"); 
	$("#hclxEj", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#materialSave", $("#"+alertMaterialFormId)).hide();
	$(".identifying", $("#"+alertMaterialFormId)).hide();
}

//设置只读/失效/隐藏
function setAdd(){
	$("#jhly", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#bjh", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#jldw", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#cjjh", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#zjhbtn", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#zjhbtn", $("#"+alertMaterialFormId)).show();
	$("#zwms", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#ywms", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#xingh", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#gg", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#isMel", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#bz", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#ckh", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#kwh", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#hcjz", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#gljb", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#minKcl", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#maxKcl", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#hclx", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).attr("disabled","true"); 
	$("#hclxEj", $("#"+alertMaterialFormId)).attr("disabled","true");
	$(".identifying", $("#"+alertMaterialFormId)).show();
	$("#materialSave", $("#"+alertMaterialFormId)).show();
	
	$('#ata_click').show();
	$('#zjhName_text').hide();
}

//设置只读/失效/隐藏
function setEdit(){
	$("#jhly", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#bjh", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#jldw", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#cjjh", $("#"+alertMaterialFormId)).attr("readonly","true");
	$("#zjhbtn", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#zjhbtn", $("#"+alertMaterialFormId)).hide();
	$("#zwms", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#ywms", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#xingh", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#gg", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#isMel", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#bz", $("#"+alertMaterialFormId)).attr("readonly","readonly");
	$("#ckh", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#kwh", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#hcjz", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#gljb", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#minKcl", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#maxKcl", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#hclx", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).removeAttr("disabled"); 
	$("#hclxEj", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#materialSave", $("#"+alertMaterialFormId)).show();
	$(".identifying", $("#"+alertMaterialFormId)).show();
	
	$('#ata_click').hide();
	$('#zjhName_text').show();
}
function setAddAndEdit(){
	$("#jhly", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#bjh", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#jldw", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#cjjh", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#zjhbtn", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#zjhbtn", $("#"+alertMaterialFormId)).show();
	$("#zwms", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#ywms", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#xingh", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#gg", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#isMel", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#bz", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#ckh", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#kwh", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#hcjz", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#gljb", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#minKcl", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#maxKcl", $("#"+alertMaterialFormId)).removeAttr("readonly");
	$("#hclx", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).removeAttr("disabled"); 
	$("#hclxEj", $("#"+alertMaterialFormId)).removeAttr("disabled");
	$("#materialSave", $("#"+alertMaterialFormId)).show();
	$(".identifying", $("#"+alertMaterialFormId)).show();
	$('#ata_click').show();
	$('#zjhName_text').hide();
}

//打开ATA章节号对话框
function openChapterWin(){
	var zjh = $.trim($("#zjh").val());
	var dprtcode = $("#dprtcode").val();
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,//机构代码
		callback: function(data){//回调函数			
				var zjh=data.zjh==null?"":data.zjh;
				var name=data.zwms==null?"":StringUtil.escapeStr(data.zwms);
				$("#zjh").val(zjh);
				$("#zjhName").val(zjh+" "+name);			
		}
	})
}
	 
//修改航材
function edit(id,dprtcode){
	openMaterialEdit(id,dprtcode);
}

//查看详情
function viewDetail(id){
	window.open(basePath+"/material/material/view?id="+id);
}

//保存或修改航材
function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
		return false;
	}
	
	var jhly = $.trim($("#jhly").val());
	//标准件号
	var bzjh=$.trim($("#bzjh").val());
	//gse
	var gse=$.trim($("#gse").val());
	//自制件
	var zzjbs=$.trim($("input[name='zzjbs']:checked").val());
	//制造厂商
	var zzcs = $.trim($("#zzcs").val());
	
	var bjh = $.trim($("#bjh").val());
	var jldw = $.trim($("#jldw").val());
	var cjjh = $.trim($("#cjjh").val());
	var zjh = $.trim($("#zjh").val());
	var zwms = $.trim($("#zwms").val());
	var ywms = $.trim($("#ywms").val());
	var xingh = $.trim($("#xingh").val());
	var gg = $.trim($("#gg").val());
	var isMel = 0;
	var bz = $.trim($("#bz").val());
	var ckh = $.trim($("#ckh").val());
	var kwh = $.trim($("#kwh").val());
	var hcjz = $.trim($("#hcjz").val());
	var gljb = $.trim($("#gljb").val());
	var sxkz = $("input:radio[name='sxkz']:checked").val(); 
	var hclx = $.trim($("#hclx").val());
	var hclxEj = $.trim($("#hclxEj").val());
	var minKcl = $.trim($("#minKcl").val());
	var maxKcl = $.trim($("#maxKcl").val());
	
	if(minKcl != null && minKcl != ''){
		var regu = /^[0-9]+\.?[0-9]{0,2}$/;
		if (!regu.test(minKcl)) {
			AlertUtil.showModalFooterMessage("最低库存量只能输入数字,并保留两位小数!");
	        return false;
	    }
	}
	
	if(maxKcl != null && maxKcl != ''){
		var regu = /^[0-9]+\.?[0-9]{0,2}$/;
		if (!regu.test(maxKcl)) {
	        AlertUtil.showModalFooterMessage("最高库存量只能输入数字,并保留两位小数！");
	        return false;
	    }
	}
	
	if(zwms != '' && zwms.indexOf("`") > 0){
		AlertUtil.showModalFooterMessage("中文名称不能输入`符号!");
		return false;
	}
	
	if(ywms != '' && ywms.indexOf("`") > 0){
		AlertUtil.showModalFooterMessage("英文名称不能输入`符号!");
		return false;
	}
	
	
	var xh = '';
	var jxArr = [];
	var jxAll = $('#jx').val();
	if(jxAll != null){
		for(var i = 0 ; i < jxAll.length ; i++){
			if("00000" == jxAll[i]){
				xh = '00000';
				break;
			}
			if('multiselect-all' != jxAll[i]){
				jxArr.push(jxAll[i]);
			}
		}
	}
	if(xh == '' && jxArr.length > 0){
		xh = '-';
	}
	if($("#isMel").is(":checked")){
		isMel = 1;
	}
	if(Number(minKcl) > Number(maxKcl)){
		AlertUtil.showModalFooterMessage("最高库存量不能小于最低库存量!");
		return false;
	}
	
	if(1 != hclx && 2 != hclx){
		hclxEj = '';
	}
	
	var materialToStore = {
			id : $("#msId").val(),
			ckh : ckh,
			kwh : kwh
	};
	
	var material = {
			id : $.trim($("#id").val()),
			jhly : jhly,
			bzjh:bzjh,
			gse:gse,
			zzcs:zzcs,
			zzjbs:zzjbs,
			bjh : bjh,
			jldw : jldw,
			cjjh : cjjh,
			zjh : zjh,
			zwms : zwms,
			ywms : ywms,
			xingh : xingh,
			gg : gg,
			isMel : isMel,
			bz : bz,
			hcjz : hcjz,
			gljb : gljb,
			sxkz : sxkz,
			hclx : hclx,
			hclxEj : hclxEj,
			minKcl : minKcl,
			maxKcl : maxKcl,
			dprtcode : $("#dprtcode").val()
		};
	material.xh = xh;
	material.jxList = jxArr;
	material.materialToStore = materialToStore;
	material.attachments = AttachmengsList.getAttachments();
	
	var url = basePath+"/material/material/edit";
	if($("#id").val() == null || $("#id").val() == ''){
		url = basePath+"/material/material/add";
	}else{
		material.isProduction = "true";
			
	}
	
	startWait($("#alertMaterialForm"));
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(material),
		dataType:"json",
		modal:$("#alertMaterialForm"),
		success:function(data){
			finishWait($("#alertMaterialForm"));
			AlertUtil.showMessage('保存成功!');
			id = data;
			refreshPage();
			$("#"+alertMaterialFormId).modal("hide");
		}
	});

}

//作废
function del(id) {
	
	AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
		startWait();
		AjaxUtil.ajax({
			url:basePath + "/material/material/cancel",
			type:"post",
			async: false,
			data:{
				'id' : id
			},
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('作废成功!');
				refreshPage();
			}
		});
	}});
}

//改变类型时查询部件信息
function changeType(){
	goPage(1,"auto","desc");
}

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchRevision(){
	//重置排序图标
	TableUtil.resetTableSorting("material_main_table_top_div");
	goPage(1,"auto","desc");
}

//刷新页面
function refreshPage(){
	goPage(pagination.page, pagination.sort, pagination.order);
}
		
//搜索条件重置
function searchreset(){
	var dprtId = $.trim($("#dprtId").val());
	
	$("#keyword_search").val("");
	
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function(){
		$(this).val("");
	});

	$("#divSearch").find("select").each(function() {
		$(this).attr("value", "");
	});
	
	$("#dprtcode_search").val(dprtId);
	$("#hclx_search").val("");
}
 
//搜索条件显示与隐藏 
function search() {
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}
		
$('.date-picker').datepicker({
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker().prev().on("click",
		function() {
			$(this).next().focus();
		});
 $('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',

	includeSelectAllOption : true
}); 
		 	
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
}
		 
//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
		if(formatUndefine(winId) != ""){
			$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
		}else{
			searchRevision(); //调用主列表页查询
		}
	}
};

/**
 * 显示导入模态框
 */
function showImportModal() {
	importModal.init({
		importUrl : "/material/material/excelImport?dprtcode="+$("#dprtcode_search").val(),
		downloadUrl : "/common/templetDownfile/7",
		callback : function(data) {
			// 刷新装机清单数据
			goPage(1,"auto","desc");
		}
	});
	importModal.show();
}	

function exportExcel(){
	var keyword = $.trim($("#keyword_search").val());
	var dprtcode = $("#dprtcode_search").val();
	var gljb = $("#gljb_search").val();
	var hclx = $("#hclx_search").val();
	var sxkz = $("#sxkz_search").val();
	var whrq = $.trim($("#whrq_search").val());
	window.open(basePath+"/material/material/partMainData.xls?dprtcode="+ dprtcode + "&keyword="+ encodeURIComponent(keyword) +"&hclx=" +hclx
	+ "&gljb="+gljb+"&sxkz="+sxkz+"&whrq="+whrq);
}