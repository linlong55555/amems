//打开评估单列表对话框
function openBjhList() {
	if($("#zxfl").val()=="ZJJ"){
		if(fjzch==""||fjzch==null){
			AlertUtil.showErrorMessage("请选择飞机注册号！");
		}
	}
	$("#wz").val("");
	goPageBjh(1,"jh","desc");
	$("#bjhModal").modal("show");
}
function searchRevision_alert(){
	goPageBjh(1,"jh","desc");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageBjh(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearchBjh(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearchBjh(pageNumber,sortType,sequence){
	var obj={};
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	obj.keyword = $.trim($("#keyword_search_alert").val());
	if($("#zxfl").val()!="FZJJ"){
		obj.wz = $.trim($("#wz").val());
	}
	obj.dprtcode=$.trim( $('#zzjgid').val()); 
	var fjzch=$("#fjzch").val();
	if($("#zxfl").val()=="ZJJ"){
		if(fjzch!=""&&fjzch!=null){
			obj.fjzch=fjzch;
		}
		searchBjh(obj);
	}
    if($("#zxfl").val()=="FZJJ"){
    	searchBjh2(obj);
	}
}
//执行对象是飞机部件时
function searchBjh(obj){
	var flag="ZJJ";
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/workorder/getLoadingList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtmlBjh(data.rows,flag);
			   new Pagination({
					renderTo : "bjh_pagination",
					data: data,
					sortColumn : "jh",
					orderType : "desc",
					goPage:function(a,b,c){
						AjaxGetDatasWithSearchBjh(a,b,c);
					}
				});
			    // 标记关键字
				signByKeyword($("#keyword_search_alert").val(),[2,3,4,5,6,7,8],"#userlist tr td");
		   } else {
			   if(flag!="ZJJ"){
				  $("#demo1_div").hide(); 
				  $("#demo2_div").hide(); 
			   }else{
				   $("#demo1_div").show(); 
				   $("#demo2_div").show(); 
			   }
			  $("#userlist").empty();
			  $("#bjh_pagination").empty();
			  $("#userlist").append("<tr class='text-center'><td colspan=\"8\">暂无数据 No data.</td></tr>");
		   }
	    },	   
	});		   
}

//执行对象是非装机件时
function searchBjh2(obj){
	var flag="FZJJ";
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/workorder/getSpareStore",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtmlBjh(data.rows,flag);
			   new Pagination({
					renderTo : "bjh_pagination",
					data: data,
					sortColumn : "bjh",
					orderType : "desc",
					goPage:function(a,b,c){
						AjaxGetDatasWithSearchBjh(a,b,c);
					}
				});
			    // 标记关键字
				signByKeyword($("#keyword_search_alert").val(),[2,3,4,5,6,7,8],"#userlist tr td");
		   } else {
			   if(flag!="ZJJ"){
				  $("#demo1_div").hide(); 
				  $("#demo2_div").hide(); 
			   }else{
				   $("#demo1_div").show(); 
				   $("#demo2_div").show(); 
			   }
			  $("#userlist").empty();
			  $("#bjh_pagination").empty();
			  $("#userlist").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
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
function appendContentHtmlBjh(list,flag){
	   if(flag!="ZJJ"){
		  $("#demo1_div").hide(); 
		  $("#demo2_div").hide(); 
	   }else{
		   $("#demo1_div").show(); 
		   $("#demo2_div").show(); 
	   }
	   var htmlContent = '';
	   var sn=$("#bjxlh_demo").val();
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
		   }
		   if(sn==row.xlh){
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" name='pgd' checked='checked' onclick='chooesRow2(this)' ><input type='hidden' value="+StringUtil.escapeStr(row.jh)+">" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.xlh)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.zwmc)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.ywmc)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.id)+"'></td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" name='pgd'  onclick='chooesRow2(this)' ><input type='hidden' value="+StringUtil.escapeStr(row.jh)+">" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.xlh)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.zwmc)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.ywmc)+"'>" +
			   "<input type='hidden' value='"+StringUtil.escapeStr(row.id)+"'></td>";
		   }
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zjhms)+"'>"+StringUtil.escapeStr(row.zjhms)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.jh)+"'>"+StringUtil.escapeStr(row.jh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.ywmc)+"'>"+StringUtil.escapeStr(row.ywmc)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.zwmc)+"'>"+StringUtil.escapeStr(row.zwmc)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			if(flag=="ZJJ"){
				htmlContent = htmlContent + "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('partsPositionEnum',row.wz)+"'>"+DicAndEnumUtil.getEnumName('partsPositionEnum',row.wz)+"</td>";  
			}
			htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#userlist").html(htmlContent);
}

//保存部件号和序列号
function getBjhAndXlh(){
	$('input[name=pgd]:checked').each(function(){   
		var jh=$(this).next().val();
		var xlh=$(this).next().next().val();
		var zwmc=$(this).next().next().next().val();
		var ywmc=$(this).next().next().next().next().val();
		var id=$(this).next().next().next().next().next().val();
		$("#bjh").val(jh);
		$("#bjxlh").val(xlh);
		$("#bjxlh_demo").val(xlh);   //用于回显
		$("#bjmc").val(ywmc+' '+zwmc);
		$("#zjqdid").val(id);
	});
}
//改变位置
function changeWz(){
	goPageBjh(1,"jh","desc");
}



