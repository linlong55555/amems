hc_list_view_modal = {
		id:"hc_list_view",
		param: {
			obj:[],
			isExportShow:true,
		},
		init:function(param){
			if(param){
				$.extend(this.param, param);
			}			
			$("#"+this.id+"_list").html("<tr class='text-center'><td colspan='11'>暂无数据 No data.</td></tr>");
			if(this.param.isExportShow){
				document.getElementById(this.id+"_export").style.display="block";
			}else{
				document.getElementById(this.id+"_export").style.display="none";
			}
			if(this.param.obj!=null && this.param.obj.length>0 && this.param.obj != undefined){
				this.appendHtml(this.param.obj);
			}			
		},
		appendHtml:function(list){
			var htmlContent="";			
			var this_ = this;
			if(list!=null){
				$.each(list,function(index,row){					
					htmlContent +="<tr id='"+row.id+"'>";
					htmlContent += "<td title='"+formatUndefine(DicAndEnumUtil.getEnumName('materialEnum', row.HCLX))+"' style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialEnum',row.HCLX) +"</td>"; 
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.JH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.JH)+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.XH)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.XH)+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.YWMS)+"" +StringUtil.escapeStr(row.ZWMS) +"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.YWMS)+"" +StringUtil.escapeStr(row.ZWMS)+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.XQSL)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.XQSL)+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.JLDW)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.JLDW)+"</a></td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.KCSL)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.KCSL)+"</td>";
				    htmlContent += "<td title='"+(StringUtil.escapeStr(row.KCSL)-StringUtil.escapeStr(row.DJSL))+"' style='text-align:right;vertical-align:middle;'>"+(StringUtil.escapeStr(row.KCSL)-StringUtil.escapeStr(row.DJSL))+"</td>";				   				 
				    htmlContent += "<td style='text-align:left;vertical-align:middle;'>";
				    var tdjxx =row.TDJXX;
					var str = tdjxx==null?"":tdjxx.split(",");
					for (var i = 0; i < str.length; i++) {
						if(i==str.length-1){
							htmlContent += "<span title='"+StringUtil.escapeStr(str[i].split("#_#"))+"'>"+str[i].split("#_#")+"</span>";
						}else{
							htmlContent += "<span title='"+StringUtil.escapeStr(str[i].split("#_#"))+"'>"+str[i].split("#_#")+"</span>"+"<br>";	
						}								
					}				
				    htmlContent += "</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.JHLY)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.JHLY)+"</td>";	   
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.BZSM)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.BZSM)+"</td>";	   
				   	htmlContent += "</tr>";
			   });
			   $("#"+this_.id+"_list").empty();
			   $("#"+this_.id+"_list").append(htmlContent);
			}
		},
};