chosen_stock_alert = {
	id : "chosen_stock_alert",
	paginationId : "chosen_stock_alert_pagination",
	param: {
		parentWinId : '',
		selected:null,
		
		id : null,
		bs : null, //1。需求。2合同 
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data:[],
	show : function(param){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
	},
	appendContentHtmlStock: function(row){
		var obj=row;
		paramsMap=row.paramsMap;
		var this_=this;
		var htmlContent = '';
			htmlContent += "<tr id='"+row.id+stockout_open_alert.mxid+"1"+"'>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";//部件名称
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+" "+StringUtil.escapeStr(row.kwh)+"</td>"; 
			htmlContent += "<td class='text-right' >"+formatUndefine(row.kcsl-row.djsl)+"/"+formatUndefine(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>"; 
			if(formatUndefine(row.hjsm) =='' && formatUndefine(row.syts)==''){
				htmlContent += "<td class='text-center'></td>";
			}else{
				htmlContent += "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"/"+formatUndefine(row.syts)+"</td>";
			}
			var kccb = StringUtil.escapeStr(row.kccb);
			if(kccb == ''){
				htmlContent += "<td ></td>"; 
			}else{
				kccb = kccb.toFixed(2);
				htmlContent += "<td class='text-right' >"+kccb+" "+formatUndefine(row.biz)+"</td>"; 
			}
			var jz = formatUndefine(row.jz);
			if(jz == ''){
				htmlContent += "<td ></td>"; 
			}else{
				jz = jz.toFixed(2);
				htmlContent += "<td class='text-right' >"+jz+" "+formatUndefine(row.jzbz)+"</td>"; 
			}
		    htmlContent += "<td class='text-left' >"+formatUndefine(row.paramsMap?row.paramsMap.cqbh:'')+"</td>";//产权 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.grn)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('stockStatusEnum',row.zt)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.hcly)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.rksj).substring(0,10)+"</td>"; //上架日期
		    htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.xh)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.gg)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.zzcs)+"</td>"; 
			htmlContent += "</tr>";  
		$("#"+this_.id+"_stocklist").append(htmlContent);
		TableUtil.addTitle("#"+this_.id+"_stocklist tr td"); //加载td title
		 
//		paramsMap.mxid=stockout_open_alert.mxid;
//		console.info(stockout_open_alert.mxid,"明细");
//		obj.mxid=stockout_open_alert.mxid;
//		console.info(obj,"明细");
		stockout_open_alert.backData[row.id+stockout_open_alert.mxid]=obj;
	},
	remove: function(id){
		var	idList=stockout_open_alert.idList;
		var idnewlist=[];
		for(var i=0;i<idList.length;i++){
			if(idList[i] != id){
				idnewlist.push(idList[i]);
			}
		}
		stockout_open_alert.idList=idnewlist;
		this.AjaxGetDatasWithStock(1,"auto","desc");//加载已存在库存
		
		$("#chosenstock").text("已选择（"+stockout_open_alert.idList.length+"）");
	
	}
}