
var xh = 1;//序号

$(function() {
	Navigation(menuCode,"查看仓库信息","View");//初始化导航
	$("#rotatable").append("<tr><td  colspan='3' class='text-center'>暂无数据 No data.</td></tr>");
	initStorage();
	
	setReadonlyDisabled();
	loodingBase($("#jdid").val());
	
});
function loodingBase(jd){
	var dprtcode = userJgdm;
	AjaxUtil.ajax({
		   url:basePath+"/sys/department/queryDepartmentByJd", 
		   type: "post",
		   dataType:"json",
		   data:{
			   'id':dprtcode
		   },
		   success:function(data){
			   if(data != null && data.length > 0){
				   var htmlappend = '<option value=""></option>';
				   $.each(data,function(index,row){
					   if(row.id == jd){
						   htmlappend += "<option selected value='"+row.id+"'>"+row.dprtname+"</option>";
					   }else{
						   htmlappend += "<option value='"+row.id+"'>"+row.dprtname+"</option>";
					   }
				   });
				   
				   $("#jd").html(htmlappend);
			   }
		   }
	 });
}
//设置只读或失效
function setReadonlyDisabled(){
	$("input").attr("readonly","readonly");
	$("select[name='cklb']").attr("disabled","true");
	$("#bz").attr("readonly","readonly");
}
//初始化仓库库位
function initStorage(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/material/store/queryStorageListByStoreId",
		type:"post",
		data:{storeId:$("#id").val()},
		dataType:"json",
		success:function(data){
			initTableCol(data);
		}
	});
}

//初始化仓库库位
function initTableCol(data){
	
	//先移除暂无数据一行
	$("#rotatable").empty();
	
	if(JSON.stringify(data) == '[]'){
		$("#rotatable").append("<tr><td  colspan='3' class='text-center'>暂无数据 No data.</td></tr>");
		return;
	}
	$.each(data,function(i,obj){
		obj.xh = xh++;
		addRow(obj);
	});
}

//向table新增一行
function addRow(obj){
	var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "	<span name='xh'>"+obj.xh+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.kwh)+"' style='text-align:left;vertical-align:middle;'>";
		tr += StringUtil.escapeStr(obj.kwh);
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.bz)+"' style='text-align:left;vertical-align:middle;'>";
		tr += StringUtil.escapeStr(obj.bz);
		tr += "</td>";
		
		tr += "</tr>";
	
	$("#rotatable").append(tr);
}

//返回前页面
function back(){
	 window.location.href =basePath+"/material/store/main?pageParam="+pageParam;
}

