var alertMaterialFormId = 'alertMaterialForm';
var materialHeadChinaId = 'materialHeadChina';
var materialHeadEnglishId = 'materialHeadEnglish';
var planeModelOption = '';
var subId = "subListView";
$(document).ready(function(){
	Navigation(menuCode,"查看部件主数据","View");//初始化导航
	var id= $("#materialId").val();
	openMaterialView(id);
});

//打开查看航材航材弹出框
function openMaterialView(id){
	$("#jldw", $("#"+alertMaterialFormId)).empty();
	$("input:radio[name='sxkz']", $("#"+alertMaterialFormId)).removeAttr("checked");
	selectById(id, function(obj){
		$("#dprtcode").val(obj.dprtcode);
		initStoreData(function(){
			DicAndEnumUtil.registerDic('1','jldw',obj.dprtcode);
			$("#"+materialHeadChinaId, $("#"+alertMaterialFormId)).html('查看部件');
			$("#"+materialHeadEnglishId, $("#"+alertMaterialFormId)).html('View');
			setMaterialData(obj);//设置航材表单数据
			setView();//设置只读/失效
			var jx = obj.xh;
			if(obj.xh == '00000'){
				jx = '通用Currency';
			}else{
				jx = obj.xh?obj.xh.join(","):'';
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
//设置只读/失效/隐藏
function setView(){	
	$("#bzjh",$("#"+alertMaterialFormId)).attr("disabled","true");
	$("#gse",$("#"+alertMaterialFormId)).attr("disabled","true");
	$("input[name='zzjbs']",$("#"+alertMaterialFormId)).attr("disabled","true");
	$("#jhly", $("#"+alertMaterialFormId)).attr("disabled","true");
	$("#bjh", $("#"+alertMaterialFormId)).attr("disabled","true");
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

//设置航材表单数据
function setMaterialData(obj){
	$("#id", $("#"+alertMaterialFormId)).val(obj.id);
	$("#bjh", $("#"+alertMaterialFormId)).val(obj.bjh);
	$("#jhly", $("#"+alertMaterialFormId)).val(obj.jhly);
	$("#bzjh",$("#"+alertMaterialFormId)).val(obj.bzjh);
	$("#gse",$("#"+alertMaterialFormId)).val(obj.gse);
	if(obj.zzjbs=="1"){
		$("#zzjbs",$("#"+alertMaterialFormId)).attr("checked","true");
	}
	$("#jldw", $("#"+alertMaterialFormId)).val(obj.jldw);
	$("#cjjh", $("#"+alertMaterialFormId)).val(obj.cjjh);
	$("#zjh", $("#"+alertMaterialFormId)).val(obj.zjh);
	var zjhName = '';
	if(obj.fixChapter != null){
		zjhName = formatUndefine(obj.fixChapter.zjh) + " " + formatUndefine(obj.fixChapter.ywms);
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
	$("#zzcs", $("#"+alertMaterialFormId)).val(obj.zzcs);
	$("input:radio[name='sxkz'][value='"+obj.sxkz+"']", $("#"+alertMaterialFormId)).attr("checked",true); 
	if(1 == obj.hclx || 2== obj.hclx){
		$("#hclxEjDiv").show(hclxEjDiv);
	}else{
		$("#hclxEjDiv").hide(hclxEjDiv);
	}
	changeMaterialType();
	$("#hclxEj", $("#"+alertMaterialFormId)).val(obj.hclxEj);
	
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
        buttonWidth: '100%',//auto
	    buttonContainer: '<div class="btn-group" style="min-width:100%;" />',
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

//初始化仓库数据
function initStoreData(obj){
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
		   if(typeof(obj)=="function"){
				obj(); 
		   }
	   }
   }); 
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