$(function(){
	$('.datepicker').datepicker({
		 autoclose: true,
   });
	
	Navigation(menuCode,"报废审批","Approval Scrap");
	
	if($("#spsj").val()==null||$("#spsj").val()==""){
		TimeUtil.getCurrentDate("#spsj");                          //取得当前时间
	}
	$(".panel-heading").not(":first").click(function(){         //隐藏和显示
		$(this).next().slideToggle("fast");
	});
	
	var diczt=DicAndEnumUtil.getEnumName('contractStatusEnum',$("#zt").val());  //翻译报废状态
	$("#zt").val(diczt);
	$("#CKList").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var id =$("input[name='id']").eq(index).val(); 
		var haszt=$("input[name='hasScrappedzt']").eq(index).val();
		var selects = document.getElementsByName("zt_"+id); 
		if(haszt!=2){
		 for (var i=0; i<selects.length; i++){  
		        if (selects[i].value=="3") {  
		            selects[i].checked= true;  
		            break;  
		        }  
		    }  
	}
	if(haszt==2){
		 for (var i=0; i<selects.length; i++){  
		        if (selects[i].value=="2") {  
		            selects[i].checked= true;  
		            break;  
		        }  
		    }  
	}
		
	});
	if($("#sprid").val()==""||$("#sprid").val()==null){
		//将当前用户赋值给检验用户
		$("#spr").val($("#userspr").val());
		$("#sprid").val($("#usersprid").val());
	}
});


//打开调整列表对话框
function openUserList() {
	 var this_ = this;
		//调用用户选择弹窗
		userModal.show({
			clearUser: false,
			selected:$("#sprid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#sprid").val(formatUndefine(data.id));
				$("#spr").val(formatUndefine(data.username) + " "+ formatUndefine(data.realname));
				$("#sprbmid").val(data.bmdm);
			},
			localDepartment:true
	    });
}

// 审批提交
function submitSave() {
	var param={};
	param.id=$("#scrappedId").val();
	param.sprid=$.trim( $('#sprid').val());  
	param.spsj=$.trim( $('#spsj').val());  
	param.spyj=$.trim( $('#spyj').val());
	param.spbmid=$("#sprbmid").val();
	param.bflx=$("#bflx").val();
	param.zt=10;
	param.bfdh=$("#bfdh").val();
	var hasScrappedList=[];
	$("#CKList").find("tr").each(function(){
		var obj = {};
		var index=$(this).index(); //当前行
		obj.id =$("input[name='id']").eq(index).val(); 
		obj.kcllid =$("input[name='kcllid']").eq(index).val(); 
		obj.mainid =$("input[name='mainid']").eq(index).val(); 
		obj.zt =$('input:radio:checked').eq(index).val();
		obj.bz =$("[name='bz']").eq(index).val(); 
		hasScrappedList.push(obj);
	});
	param.hasScrappedList=hasScrappedList;
	 AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/scrap/submitScrap",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(param),
			dataType:"json",
			success:function(data){
				finishWait();
			    AlertUtil.showMessage('提交成功!','/aerialmaterial/scrap/main?id='+$("#scrappedId").val()+'&pageParam='+pageParam);
			},
		});
}
function back(){
	 window.location.href =basePath+"/aerialmaterial/scrap/main?id="+$('#scrappedId').val()+"&pageParam="+pageParam;
}

function save(){
	var param={};
	param.id=$("#scrappedId").val();
	param.sprid=$.trim( $('#sprid').val());  
	param.spsj=$.trim( $('#spsj').val());  
	param.spyj=$.trim( $('#spyj').val());
	param.spbmid=$("#sprbmid").val();
	param.zt=1;
	param.bfdh=$("#bfdh").val();
	var hasScrappedList=[];
	$("#CKList").find("tr").each(function(){
		var obj = {};
		var index=$(this).index(); //当前行
		obj.id =$("input[name='id']").eq(index).val(); 
		obj.kcllid =$("input[name='kcllid']").eq(index).val(); 
		obj.mainid =$("input[name='mainid']").eq(index).val(); 
		obj.zt =$('input:radio:checked').eq(index).val();
		obj.bz =$("[name='bz']").eq(index).val(); 
		hasScrappedList.push(obj);
	});
	param.hasScrappedList=hasScrappedList;
	 AjaxUtil.ajax({
			url:basePath+"/aerialmaterial/scrap/saveScrap",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(param),
			dataType:"json",
			success:function(data){
				finishWait();
			    AlertUtil.showMessage('保存成功!','/aerialmaterial/scrap/main?id='+$("#scrappedId").val()+'&pageParam='+pageParam);
			},
		});
}