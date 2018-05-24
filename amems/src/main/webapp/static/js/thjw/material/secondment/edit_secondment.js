$(document).ready(function(){
	Navigation(menuCode,"修改借调对象","Edit Seconded");
	//数据字典
	/*DicAndEnumUtil.registerDic('21','qtfxd');*/
	var zzjg=$('#zzjgId').val();
	$("#qtfxd option[value='"+zzjg+"']").remove(); 
	//选择其他飞行队的隐藏或显示
	$("#fxd").hide();
	if($("#jddxlx").val()==1){
		$("#fxd").show();
		jddxbh=$("#jddxbh").val();
		$("#qtfxd").val(jddxbh);
		$("#jddxms").attr("disabled", 'false');
	}
	//过滤已选择的飞行队
	filterAerocade(zzjg);
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            jddxms: {
                validators: {
                	notEmpty: {
                        message: '借调对象描述不为空！'
                    },
                }
            },
            bz: {
                validators: {
                }
            }
        }
    });
});

function saveSecondment() {
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		return false;
	  }
	 var id = $.trim($("#id").val());
	 var jddxlx = $.trim($("#jddxlx").val());
	 var jddxms = $.trim($("#jddxms").val());
	 var bz = $.trim($("#bz").val());
	 var jddxbh = $.trim($("#jddxbh").val());
	
	 var obj={};
	 obj.id=id;
	 obj.jddxlx=jddxlx;                
	 obj.jddxms=jddxms;
	 obj.jddxbh=jddxbh;
	 obj.bz= bz;
	 if(jddxlx==1){
		 if($("#qtfxd").val()==""){
			 AlertUtil.showErrorMessage("请选择飞行队！");
			 return false;
		 }
	  }
	 AjaxUtil.ajax({
		type : 'post',
		url : basePath+'/aerialmaterial/secondment/Edit',                                                             //验证ATA章节号是否重复
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType : 'json',
		success : function(data) {
			if(data !=0) {
				AlertUtil.showMessage("保存成功",'/aerialmaterial/secondment/main?id='+id+'&pageParam='+pageParam);
			} 
		}
	 });
	}
//返回前页面
function back(){
	 window.location.href =basePath+"/aerialmaterial/secondment/main?pageParam="+pageParam;
}
function jddxlxChange(){
	if($("#jddxlx").val()==1){
		$("#fxd").show();
	}else{
		$("#fxd").hide();
		$("#jddxms").val("");
		$("#jddxms").removeAttr("disabled");
		$("#jddxbh").val("");
		$("#qtfxd").val("");
	}
}
function qtfxdChange(){
	var qtfxdValue=$("#qtfxd").val();
	var qtfxd=$("#qtfxd").find("option:selected").text();
	$("#jddxbh").val(qtfxdValue);
	if(qtfxdValue!=""){
		$("#jddxms").val(qtfxd);
		$("#jddxms").attr("disabled", 'false');
	}else{
		$("#jddxms").removeAttr("disabled");
		
	}
}
function filterAerocade(dprtcode){
	AjaxUtil.ajax({
		type : 'post',
		url : basePath+'/aerialmaterial/secondment/filterAerocade?dprtcode='+dprtcode,                                                             //验证ATA章节号是否重复
		contentType:"application/json;charset=utf-8",
		data : "",
		dataType : 'json',
		success : function(data) {
			if(data.jddxbhList.length>0){
				
				for(var i=0;i<data.jddxbhList.length;i++){
					//获取当前已选飞行队编号
					var jddxbh =$("#jddxbh").val();
					if(jddxbh!=data.jddxbhList[i]){
						$("#qtfxd option[value='"+data.jddxbhList[i]+"']").remove(); 
					}
					
				}
			}
			}
		});
}