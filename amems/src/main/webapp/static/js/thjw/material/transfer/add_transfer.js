$(function(){
	Navigation(menuCode,"新增移库记录","Add Transfer");
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		var selectTab = $("#tablist").find("li.active").index();
		
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				//调用主列表页查询
			}
		}
	});
	
	$('.datepicker').datepicker({
		 autoclose: true,
		 clearBtn:true
   });
	
	if($("#ykrq").val()==null||$("#ykrq").val()==""){
		TimeUtil.getCurrentDate("#ykrq");                          //取得检验日期的当前时间
	}
	
	$(".panel-heading").not(":first").click(function(event){         //隐藏和显示
		if($(event.target).attr("id")!= "copy_wo"){
			$(this).next().slideToggle("fast");
		}
	});
	
	renderSelect();
});




//打开用户列表对话框
function openUserList() {
	 var this_ = this;
		//调用用户选择弹窗
		userModal.show({
			selected:$("#ykrid").val(),//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				$("#ykrid").val(formatUndefine(data.userId));
				$("#ykr").val(StringUtil.escapeStr(data.username) + " "+ StringUtil.escapeStr(data.realname));
			},
			localDepartment:true
		});
}


//渲染下拉单数据
function renderSelect(){
		var optionHtml = "";
		var dprtcode = userJgdm;
		var cklbs=[0,2,3,4,5,6,7,8];
		$.each(userStoreList, function(index, row){
			
				optionHtml += "<option value='"+row.id+"'>"+StringUtil.escapeStr(row.ckmc)+"</option>";
			
		});
		var $select = $("select[name='mbCkmc']:empty");
		$select.html(optionHtml);
		
		$select.change(function(){
			var storeId = $(this).val();
			for (var i = 0; i < userStoreList.length; i++) {
				if(userStoreList[i].id == storeId){
					var kwOptionHtml = "";
					var storageList = userStoreList[i].storageList;
					for (var j = 0; j < storageList.length; j++) {
					    kwOptionHtml += "<option value='"+storageList[j].id+"'>"+StringUtil.escapeStr(storageList[j].kwh)+"</option>";
					}
					var $selectKwh = $(this).parent().next().find("select[name='mbKwh']");
					if(kwOptionHtml==''||kwOptionHtml==null){
						kwOptionHtml += "<option value=''>无</option>";
					}
					$selectKwh.html(kwOptionHtml);
				}
				
			}		
		});
		$.each($select, function(){
			$(this).trigger("change");
		});
}
function delTr(obj) {                                                          //删除工作内容的当前行
    $(obj).parent().parent().remove();     
}


// 移库提交
function submitSave() {
	$("#CKList input[name='mbSl']").each(function(){
		var input = $(this);
		input.css("border-color","");
	});
	var flag=true;
	$("#CKList").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var kwid =$("input[name='kwid']").eq(index).val();
		var ckid =$("input[name='ckid']").eq(index).val();
		var mbkwid=$("select[name='mbKwh']").eq(index).val();
		var mbckid=$("select[name='mbCkmc']").eq(index).val();
		if(ckid==mbckid&&kwid==mbkwid){
			flag=false;
		}
	});
	if(!flag){
		AlertUtil.showMessage("不能移到同一个仓库的同一个库位!");
		return;
	}
	
	var data={};
	var ykrid=$.trim( $('#ykrid').val());  
	var ykrq=$.trim( $('#ykrq').val());  
	var ykyy=$.trim( $('#ykyy').val());
	
	data.ykrid=ykrid;
	data.ykrq=ykrq;
	data.ykyy=ykyy;
	var fola=true;
	var detail = [];
	$("#CKList").find("tr").each(function(){
		var stock = {};
		var index=$(this).index(); //当前行
		var id =$("input[name='id']").eq(index).val(); 
		var ykid =$("input[name='ykid']").eq(index).val(); 
		var mbCkmc =$("select [name='mbCkmc']").eq(index).val(); 
		var mbKwh =$("select[name='mbKwh']").eq(index).val(); 
		var mbSl =$("input[name='kcsl']").eq(index).val(); 
		var kykcsl =$("input[name='kykcsl']").eq(index).val(); 
		
		var folt=true;
		if(mbSl == "" || mbSl == null || mbSl == 0){
			AlertUtil.showMessage("移库数量不能为空或者0!");
			fola= false;
			folt=false;
			return false;
		}
		
		if(parseInt(mbSl)>parseInt(kykcsl)){
			AlertUtil.showMessage("移库数不能大于库存数!");
			fola= false;
			folt=false;
			return false;
		}
		
		if(folt==false){
			return false;
		}
		stock.id = id;
		stock.ykid = ykid;
		stock.mbCkmc=mbCkmc;
		stock.mbKwh=mbKwh;
		stock.mbSl=mbSl;
		detail.push(stock);
	});
	if(fola==false){
		return false;
	}
	data.detail=detail;
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/transfer/addTransfer",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(data),
		dataType:"json",
		success:function(data){
			if(data>0){
			    AlertUtil.showMessage('提交成功!','/aerialmaterial/transfer/main');
			}else{
				finishWait();
				AlertUtil.showMessage('该航材已经改变,请刷新后再进行操作!','/aerialmaterial/transfer/main');
			}
		},
	});
}
//返回前页面
function back(){
	 window.location.href =basePath+"/aerialmaterial/transfer/main?pageParam="+pageParam;
}


//只能输入正整数,且保留两位小数
function clearNoNum(obj){
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
