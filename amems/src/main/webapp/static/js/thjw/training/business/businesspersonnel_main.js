var delAttachements = [];	
function showInfo(pageNumber, sortType, sequence){
	var searchParam={};
	searchParam.gwid=$("#manidinfo").val();
	searchParam.dprtcode=$("#dprtId").val();
	pagination = {
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 20
		};
	AjaxUtil.ajax({
		url : basePath + "/training/business/getInfoList",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			checkPersonList=[];
			baseSj = {};
			if (data.total > 0) {
				appendContentHtmlInfo(data.rows);
				new Pagination({
					renderTo : "fjgzjkInfo_pagination",
					data : data,
					sortColumn : sortType,
					orderType : sequence,
					goPage : function(a, b, c) {
						showInfo(a, b, c);
					}
				});
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
			} else {
				$("#dcgzcl_list").empty();
				$("#fjgzjkInfo_pagination").empty();
				$("#dcgzcl_list").append(
						"<tr><td colspan=\"8\">暂无数据 No data.</td></tr>");
			}
			new Sticky({
				tableId : 'flight_record_sheet_table'
			});
		}
	});
}
					
	function appendContentHtmlInfo(list){
			var htmlContent = '';
			
			$.each(list,function(index,row){
				var param={
						id:row.wxrydaid,
						displayName:row.maintenancePersonnel.rybh+" "+row.maintenancePersonnel.xm
				};
				checkPersonList.push(param);
				baseSj.fjjx = row.fjjx;
				baseSj.lb = row.lb;
				baseSj.ksrq = row.ksrq;
				baseSj.jzrq = row.jzrq;
				
				htmlContent += "<tr >";
				htmlContent += "<td style=\"vertical-align: middle;\" class='text-center fixed-column'><i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='training:business:manage:05' title='作废  Invalid' onClick='deleteInfo(\""
					+ row.id+ "\")' ></i></td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.maintenancePersonnel.rybh)+"'><a href=\"javascript:view('"+row.maintenancePersonnel.id+"')\">"+StringUtil.escapeStr(row.maintenancePersonnel.rybh)+"</a></td>";  
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.maintenancePersonnel.xm)+"'>"+StringUtil.escapeStr(row.maintenancePersonnel.xm)+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.lb)+"'>"+StringUtil.escapeStr(row.lb)+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.ksrq?row.ksrq.substring(0,10):'')+"'>"+StringUtil.escapeStr(row.ksrq?row.ksrq.substring(0,10):'')+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.jzrq?row.jzrq.substring(0,10):'')+"'>"+StringUtil.escapeStr(row.jzrq?row.jzrq.substring(0,10):'')+"</td>";
			    if(row.maintenancePersonnel.wbbs=="2"){
			    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title="+(StringUtil.escapeStr(row.maintenancePersonnel.szdw)?StringUtil.escapeStr(row.maintenancePersonnel.szdw):"外部人员")+">"+(StringUtil.escapeStr(row.maintenancePersonnel.szdw)?StringUtil.escapeStr(row.maintenancePersonnel.szdw):"外部人员")+"</td>";
			    }else{
			    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.maintenancePersonnel.szdw)+"'>"+StringUtil.escapeStr(row.maintenancePersonnel.szdw)+"</td>";
			    }
			    htmlContent += "</tr>"
			    $("#dcgzcl_list").empty();
			    $("#dcgzcl_list").html(htmlContent);
		});
			refreshPermission(); 
	}
		
	function view(id){
		window.open(basePath+"/quality/maintenancepersonnel/view/" + id);
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
 	var baseSj = {};
 	//打开调整列表对话框
 	function openPersonelWin() {
 		checkPersonList =[];
 		baseSj = {};
 		var this_ = this;
 		Personel_Tree_Multi_Modal.show({
 			clearUser:false,
 			checkUserList:checkPersonList,//原值，在弹窗中回显
 			baseSj:baseSj,//基础数据，在弹窗中回显
 			dprtcode:$("#dprtcode").val(),//
 			callback: function(data,obj){//回调函数
 				var personName = '';
 				if(data != null && data != ""){
 					var ids=[];
 					var message="";
 					$.each(data, function(i, row){
 							ids.push(row.id);
 					});
 					if(ids.length>0){
 							submitUser(ids,obj);
 					}
 				}
 			}
 		})
 	}
 	function submitUser(ids,data){
 		var obj={};
 		obj.ids=ids;
 		obj.gwid=$("#manidinfo").val();
 		obj.dprtcode=$("#dprtcode").val();
 		obj.fjjx = data.fjjx;
 		obj.lb = data.lb;
 		obj.ksrq = data.ksrq;
 		obj.jzrq = data.jzrq;
 		
 	// 提交数据
 		AjaxUtil.ajax({
 			url:basePath+"/training/business/saveUser",//添加人员岗位关系表
 			type: "post",
 			contentType:"application/json;charset=utf-8",
 			data:JSON.stringify(obj),
 			dataType:"json",
 			success:function(data){
 				pagination = mainpagination;
 				pageParam=encodePageParam();
 				 AlertUtil.showErrorMessage("添加成功");
 				showInfo(1, "desc", "auto");
 				decodePageParam();
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
						 pagination = mainpagination;
						 pageParam=encodePageParam();
						 AlertUtil.showErrorMessage("作废成功");
						 showInfo(1, "desc", "auto");
			 			 decodePageParam();
					 }
				 });
				 
			 
	 }});
 	}
 	
 	