var taskhistoryRecordMain={
		id : 'taskhistoryRecordMain',
		AjaxGetDatasWithSearch:function(gdid, gdbh, id){
			 var _this = this;
			 if(id != null && id != '' && typeof id != undefined){
				_this.id = id;
			 }
			 AjaxUtil.ajax({
				 url:basePath+"/produce/taskhistory/queryAllRecordByGdid",
				   type: "post",
				   dataType:"json",
				   data:{
					   'gdid':gdid
				   },
				   success:function(data){
			 			if(data.rows.length > 0){
			 			_this.appendContentHtml(data.rows,gdbh,gdid);
			 		}else{
			 			$("#taskhistory_chjjl_list", $("#"+_this.id)).empty();
						$("#taskhistory_chjjl_list", $("#"+_this.id)).append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
					}
			 	}
			 });
		},
		// 表格拼接
		appendContentHtml:function(list,gdbh,gdid){
			var _this = this;
			var htmlContent = '';
			$.each(list,function(index,row){
				    htmlContent += "<tr>";
				    htmlContent += "<td class='text-center'>"+(index+1)+"</td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(gdbh)+"'><a href=\"javascript:view('"+gdid+"')\">"+StringUtil.escapeStr(gdbh)+"</a></td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.cx_bjh)+"'>"+StringUtil.escapeStr(row.paramsMap.cx_bjh)+"</td>";
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.cx_xlh)+"'>"+StringUtil.escapeStr(row.paramsMap.cx_xlh)+"</td>";
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.cx_zwms)+" "+StringUtil.escapeStr(row.paramsMap.cx_ywms)+"'>"+StringUtil.escapeStr(row.paramsMap.cx_zwms)+" "+StringUtil.escapeStr(row.paramsMap.cx_ywms)+"</td>"; 
				    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.zsSj?row.zsSj.substring(0,16):'')+"'>"+StringUtil.escapeStr(row.zsSj?row.zsSj.substring(0,16):'')+"</td>";
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.zs_bjh)+"'>"+StringUtil.escapeStr(row.paramsMap.zs_bjh)+"</td>";
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.zs_xlh)+"'>"+StringUtil.escapeStr(row.paramsMap.zs_xlh)+"</td>";
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.zs_zwms)+" "+StringUtil.escapeStr(row.paramsMap.zs_ywms)+"'>"+StringUtil.escapeStr(row.paramsMap.zs_zwms)+" "+StringUtil.escapeStr(row.paramsMap.zs_ywms)+"</td>";
			    	htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.cxSj?row.cxSj.substring(0,16):'')+"'>"+StringUtil.escapeStr(row.cxSj?row.cxSj.substring(0,16):'')+"</td>"; 
				    if(row.zsBz){
				    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zsBz)+"'>"+StringUtil.escapeStr(row.zsBz)+"</td>"; 
				    }else{
				    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.cxBz)+"'>"+StringUtil.escapeStr(row.cxBz)+"</td>"; 
				    }
				    htmlContent += "</tr>" ;
		   $("#taskhistory_chjjl_list", $("#"+_this.id)).empty();
		   $("#taskhistory_chjjl_list", $("#"+_this.id)).html(htmlContent);
		 });
		}
}
//跳转到工单查看
function view(gdid){
	window.open( basePath+"/produce/workorder/woView?gdid="+gdid);
}