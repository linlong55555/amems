$(function(){
	Navigation(menuCode, '查看物料履历', 'View', 'SC-1-2 ', '孙霁', '2017-09-25', '孙霁', '2017-09-25');//加载导航栏
	$('.page-content input').attr('disabled',true);
	$('.page-content textarea').attr('disabled',true);
	componenthistory.reload();
});

var componenthistory={
		gatherSearchParam:function(){
			var searchParam={};
			var paramsMap = {};
			try{
				var decodeStr = Base64.decode(pageParam);
				var pageParamJson = JSON.parse(decodeStr);
				var params = pageParamJson.params;
				 paramsMap.jh = params.bjh;
				 paramsMap.xlh = params.xlh;
				 paramsMap.dprtcode = params.dprtcode;
			}catch(e){
				
			}
			 searchParam.paramsMap = paramsMap;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 var searchParam=_this.gatherSearchParam();
			 AjaxUtil.ajax({
				 url:basePath+"/produce/materialhistory/queryAllBj",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
			 			if(data.row || data.rows.length > 0){
			 				_this.appendInput(data.row);
			 				_this.appendContentHtml(data.rows);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
						
					}
			 	}
			 });
		},
		reload:function(){
			this.goPage(1,"auto","desc");
		},
		//input填充
		appendInput:function(row){
			$("#jh").val(StringUtil.escapeStr(row.paramsMap.bjh));
			$("#xlh").val(StringUtil.escapeStr(row.paramsMap.xlh));
			$("#xh").val(StringUtil.escapeStr(row.paramsMap.bjh_xh));
			$("#cjjh").val(StringUtil.escapeStr(row.paramsMap.cjjh));
			$("#mc").val(StringUtil.escapeStr(row.paramsMap.bjh_ywmc)+" "+StringUtil.escapeStr(row.paramsMap.bjh_zwmc));
			$("#ata").val(StringUtil.escapeStr(row.paramsMap.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjh_ywmc));
			$("#llkh").val(StringUtil.escapeStr(row.paramsMap.llkbh));
			$("#ckrq").val(StringUtil.escapeStr(row.paramsMap.chucrq?row.paramsMap.chucrq.substring(0,10):''));
			$("#gzjl").val(StringUtil.escapeStr(row.paramsMap.bjgzjl));
			$("#cal").val(StringUtil.escapeStr(row.paramsMap.cal?parseInt(row.paramsMap.cal):''));
			$("#tsn").val(TimeUtil.convertToHour(StringUtil.escapeStr(row.paramsMap.tsn), TimeUtil.Separator.COLON));
			$("#tso").val(TimeUtil.convertToHour(StringUtil.escapeStr(row.paramsMap.tso), TimeUtil.Separator.COLON));
			$("#csn").val(StringUtil.escapeStr(row.paramsMap.csn));
			$("#cso").val(StringUtil.escapeStr(row.paramsMap.cso));
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>"; 
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.czrq?row.paramsMap.czrq.substring(0,10):"")+"'>"+StringUtil.escapeStr(row.paramsMap.czrq?row.paramsMap.czrq.substring(0,10):"")+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('materialHistorySubtypeEnum',row.paramsMap.czlx)+"'>"+DicAndEnumUtil.getEnumName('materialHistorySubtypeEnum',row.paramsMap.czlx)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.ywdjbh)+"'>";
			    if(row.paramsMap.ywdjid != null && row.paramsMap.ywdjid != ''){
			    	htmlContent += "<a href=\"javascript:viewywdj('"+row.paramsMap.ywdjid+"','"+row.paramsMap.czlx+"')\">"+StringUtil.escapeStr(row.paramsMap.ywdjbh)+"</a>";
			    }else{
			    	htmlContent += StringUtil.escapeStr(row.paramsMap.ywdjbh);
			    }
			    htmlContent += "</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.ck_fjzch)+"'>"+StringUtil.escapeStr(row.paramsMap.ck_fjzch)+"</td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.kw_zjwz)+"'>"+StringUtil.escapeStr(row.paramsMap.kw_zjwz)+"</td>"; 
			    
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr($.trim(row.paramsMap.sjbj))+"'><a href=\"javascript:viewsjbj('"+row.paramsMap.sjbj+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(row.paramsMap.sjbj)+"</a></td>"; 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.czz)+"'>"+StringUtil.escapeStr(row.paramsMap.czz)+"</td>"; 
			    htmlContent += _this.formatLastData(StringUtil.escapeStr(row.paramsMap.bjyysj),1,row.paramsMap.czlx);
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.czyy)+"'>"+StringUtil.escapeStr(row.paramsMap.czyy)+"</td>"; 
			    htmlContent += _this.formatLastData(StringUtil.escapeStr(row.paramsMap.bjyysj),2,row.paramsMap.czlx);
			    htmlContent += "</tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission(); 
		 });
		},
		formatLastData : function(data,i,lx){
			var str = "";
			if(data == null || data == "" || (i == 2 && lx ==1)){
				str += "<td></td>";
				return str;
			}
			var list = data.split(",");
			var value = '';
			$.each(list,function(index,row){
				var tdArr = row.split("#_#");
				
				var dw = MonitorUnitUtil.getMonitorUnit(tdArr[0]);
				if(tdArr[0] == '1_10'){
					dw ='D';
				}
				if(dw == 'FH' || dw == 'EH' || dw == 'APUH'){
					value += (tdArr[i]?TimeUtil.convertToHour(tdArr[i], TimeUtil.Separator.COLON):0)+dw+"</br>";
				}else{
					if(dw =='D' && isNaN(tdArr[i])){
						value += (tdArr[i]?tdArr[i]:0)+"</br>";
					}else{
						value += (tdArr[i]?tdArr[i]:0)+dw+"</br>";
					}
				}
			});
			str += "<td title='"+value.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+value+"</td>";
			return str;
		}
};

function viewywdj(id,czlx){
	if(czlx == 1 || czlx == 2){//1装上、2拆下
		window.open(basePath+"/produce/workorder/woView?gdid="+id);
	}else if(czlx == 5){//5报废
		window.open(basePath+"/aerialmaterial/scrap/view/"+id);
	}else if(czlx == 10 || czlx == 11 || czlx == 12 || czlx == 13 || czlx == 14){//10入库、11采购入库、12送修入库、13借用入库、14归还入库
		window.open(basePath+"/material/outin/receipt?id="+id+"&type=view");
	}else if(czlx == 20 || czlx == 21 || czlx == 22 || czlx == 23 || czlx == 24|| czlx == 25|| czlx == 26
			|| czlx == 27|| czlx == 28|| czlx == 29){//20出库 21领用出库 22送修出库 23归还出库 24借调出库
		window.open(basePath+"/material/outin/viewOut?id="+id);
	}
}
function viewsjbj(str,dprtcode){
	if(str){
		var obj = str.split(" ");
		window.open(basePath+"/produce/materialhistory/view?pageParam=" + encodePageParam(obj[0],obj[1],dprtcode));
	}
}
function encodePageParam(bjh,xlh,dprtcode){
	var pageParam = {};
	var params = {};
	params.bjh = bjh;
	params.xlh = xlh;
	params.dprtcode = dprtcode;
	pageParam.params = params; 
	return Base64.encode(JSON.stringify(pageParam));
}
function refresh(){
	componenthistory.reload();
}