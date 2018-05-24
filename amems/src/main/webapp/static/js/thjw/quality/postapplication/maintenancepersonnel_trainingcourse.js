Post_Open_Modal_Trainingcourse = {
	id : "Post_Open_Modal",
	//加载维修档案人员数据
	setData: function(wxrydaid,sqgwid,dprtcode,fjjx){
		
		var searchParam={};
		var paramsMap={};
	
		searchParam.id = wxrydaid;
		searchParam.sqgwid = sqgwid;
		searchParam.dprtcode = dprtcode;
		searchParam.paramsMap=paramsMap;
		if(fjjx != undefined && fjjx.length>0 ){
			paramsMap.fjjx=fjjx;
		}
		
		var this_=this;
		if(sqgwid!=''){
			startWait();
		   	 AjaxUtil.ajax({
			   	    url:basePath+"/quality/maintenancepersonnel/loadTrainingcourse",
			   	    type: "post",
			   	    contentType:"application/json;charset=utf-8",
			   	    dataType:"json",
			   	    data:JSON.stringify(searchParam
			   	    	/*id : wxrydaid,
			   	    	sqgwid : sqgwid,
			   	    	dprtcode : dprtcode*/
			   	    ),
			   		modal:$("#Post_Open_Modal"),
			   	    success:function(data){	   	    
			   	    	console.info(data);
			   	    	finishWait();
						// 回显表格数据
						this_.fillTableData(data);
			        }
		        });
		}else{
			$("#maintenance_trainingcourse_list").empty();
			$("#maintenance_trainingcourse_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		}
		
	},
	//加载维修档案人员数据
	setDataNofjjx: function(wxrydaid,sqgwid,dprtcode,id){
		console.info("&&",wxrydaid,sqgwid,dprtcode,id);
		var searchParam={};
		
		searchParam.id = wxrydaid;
		searchParam.sqgwid = sqgwid;
		searchParam.dprtcode = dprtcode;
		searchParam.sqid = id;
		var this_=this;
		if(sqgwid!=''){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/quality/maintenancepersonnel/loadTrainingcourseFjjx",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				modal:$("#Post_Open_Modal"),
				success:function(data){	   	    
					finishWait();
					// 回显表格数据
					this_.fillTableData(data);
				}
			});
		}else{
			$("#maintenance_trainingcourse_list").empty();
			$("#maintenance_trainingcourse_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		}
		
	},
	/**
	 * 初始化课程培训
	 */
	fillTableData : function(data){
		if(data.length>0){
			var htmlContent = '';
			$.each(data,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td class='text-center' >"+formatUndefine(row.SJ_KSRQ).substring(0,10)+"</td>"; 
				htmlContent += "<td class='text-center' >"+formatUndefine(row.SJ_JSRQ).substring(0,10)+"</td>"; 
				htmlContent += "<td title='"+formatUndefine(row.FJJX)+"' class='text-left' >"+formatUndefine(row.FJJX)+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.KCBM)+" "+StringUtil.escapeStr(row.KCMC)+"</td>"; 
			
				htmlContent += "</td>";
				htmlContent += "</tr>" ;
				$("#maintenance_trainingcourse_list").empty();
				$("#maintenance_trainingcourse_list").html(htmlContent);
			});
		}else{
			$("#maintenance_trainingcourse_list").empty();
			$("#maintenance_trainingcourse_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
		}
	}

};

