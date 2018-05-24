function showInfo(id,scrs,kcbh,zt,ycrs){
	AjaxUtil.ajax({
		url : basePath + "/training/planperson/queryByPxjhscId",
		type : "post",
		data:{pxjhid:$("#manidinfo").val()},
		dataType:"json",
		success : function(data) {
			finishWait();
			checkPersonList=[];
			var str = '';
			var sjcy="";
			if(zt==1){
				scrs="";
			}else{
				sjcy=" "+'实际参与人数 : '+scrs+"人";
			}
			str = '已选课程编号 : '+formatUndefine(kcbh)+" 应参与人数 : "+formatUndefine(ycrs)+"人"+sjcy;
			$("#selectCourse").text(str);
			if (data.length>0) {
				
				appendContentHtmlInfo(data,zt);
	
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
			} else {
				$("#dcgzcl_list").empty();
				$("#dcgzcl_list").append(
						"<tr><td colspan=\"12\">暂无数据 No data.</td></tr>");
			}
			new Sticky({
				tableId : 'flight_record_sheet_table'
			});
		}
	});
}
					
	function appendContentHtmlInfo(list,zt){
		var htmlContent = '';
		
		$.each(list,function(index,row){
			htmlContent += "<tr >";
		    htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.rybh)+"'><a href='javascript:void(0);' onclick='goToViewPage(\""+row.paramsMap.mid+"\")'  name='content'>"+StringUtil.escapeStr(row.paramsMap.rybh)+"</a></td>";  
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.xm)+"'>"+StringUtil.escapeStr(row.xm)+"</td>";  
		    if(row.paramsMap.wbbs==2){
		    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='外部人员'>外部人员</td>";  
		    }else{
		    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.gzdw)+"'>"+StringUtil.escapeStr(row.gzdw)+"</td>";  
		    }
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.isYc== 1?"是":"否")+"'>"+StringUtil.escapeStr(row.isYc== 1?"是":"否")+"</td>";  
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.isSc== 1?"是":"否")+"'>"+StringUtil.escapeStr(row.isSc== 1?"是":"否")+"</td>";  
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.cql)+"'>"+StringUtil.escapeStr(row.cql)+"</td>";  
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-right' title='"+StringUtil.escapeStr(row.cj)+"'>"+StringUtil.escapeStr(row.cj)+"</td>";  
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.khjg== null?"":(row.khjg==1?"通过":"不通过"))+"'>"+StringUtil.escapeStr(row.khjg== null?"":(row.khjg==1?"通过":"不通过"))+"</td>";  
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zs)+"'>"+StringUtil.escapeStr(row.zs)+"</td>";  
		    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+formatUndefine(row.xcpxrq).substring(0,10)+"'>"+formatUndefine(row.xcpxrq).substring(0,10)+"</td>";  
		   if(zt=='10'){
			   htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";
		   }else{
			   htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' ></td>";
		   }
		    htmlContent += "</tr>"
				 
		});
		$("#dcgzcl_list").empty();
		$("#dcgzcl_list").html(htmlContent);
	}
		
	/**
	 * 跳转到查看页面
	 * @param id
	 */
	function goToViewPage(id){
		window.open(basePath + "/quality/maintenancepersonnel/view/"+id);
	}
	
 	//新增初始化
 	function addDcgz(){
 		 validation();
 		 
	     $("#alertModalDcgz").find("input").each(function() {
			$(this).attr("value", "");
		 });
		 $("#alertModalDcgz").find("textarea").each(function(){
			 $(this).val("");
		 });
		 
		 $("#alertModalDcgz input").attr("disabled",false);
		 $("#alertModalDcgz select").attr("disabled",false);
		 $("#alertModalDcgz textarea").attr("disabled",false);
		 $("#accredit1").html("新增人员");
		 $("#accreditrec1").html("Add Business");
		 
	 	 $("#alertModalDcgz").modal("show");
 	}
 	
 	var checkPersonList = [];//储存选中的人员
 	//打开调整列表对话框
 	function openPersonelWin() {
 		
 		var this_ = this;
 		Personel_Tree_Multi_Modal.show({
 			checkUserList:'',//原值，在弹窗中回显
 			dprtcode:$("#dprtcode").val(),//
 			callback: function(data){//回调函数
 				var personName = '';
 				if(data != null && data != ""){
 					var ids=[];
 					$.each(data, function(i, row){
 						var b=true;
 						$.each(checkPersonList, function(i, person){
 							if(person.id==row.id){
 								b=false;
 							}
 						});
 						if(b){
 							ids.push(row.id);
 						}
 					});
 					if(ids.length>0){
 						submitUser(ids);
 					}
 				}
 			}
 		})
 	}
 	function submitUser(ids){
 		var obj={};
 		obj.ids=ids;
 		obj.gwid=$("#manidinfo").val();
 		obj.dprtcode=$("#dprtcode").val();
 	// 提交数据
 		AjaxUtil.ajax({
 			url:basePath+"/training/business/saveUser",//添加人员岗位关系表
 			type: "post",
 			contentType:"application/json;charset=utf-8",
 			data:JSON.stringify(obj),
 			dataType:"json",
 			success:function(data){
 				 AlertUtil.showErrorMessage("添加成功");
 				showInfo();
 				 
 			}
 		});
 		
 	}
 	function deleteInfo(id){
 		 AlertUtil.showConfirmMessage("确定要作废吗？", {callback: function(){
				 AjaxUtil.ajax({
					 url:basePath+"/training/business/deleteInfo",//添加人员岗位关系表
					 type:"post",
					 async: false,
					 data:{
						 'id' : id
					 },
					 dataType:"json",
					 success:function(data){
						 AlertUtil.showErrorMessage("作废成功");
						 showInfo();
					 }
				 });
				 
			 
	 }});
 	}
 	
 	