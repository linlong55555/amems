$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	var dprtcode=$("#dprtcode_search").val();
	DicAndEnumUtil.registerDic('8','ly',dprtcode);
	
	//飞机注册号
	initfjzch(dprtcode);
	
	flighthistoryMain.reload();

	flighthistory_hover.init();
	//导入初始化
	/*initImport();*/

});
//搜索条件重置
function searchreset(){
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#dprtcode_search").val(userJgdm);
	// checked();
	 $("#fxrq").val("");
	 //飞机注册号
	 initfjzch(userJgdm);
	 multiSelectReset(zzjgid);
}
//组织机构改变事件
function dprtChange(){
	initfjzch($("#dprtcode_search").val());
}
//飞机搜索框
function initfjzch(dprtcode){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"#_#"+StringUtil.escapeStr(planeData.FDJSL)+"' " +
			                             "jx='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+
												StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+
							  "</option>";
		});
	}else{
		planeRegOption += "<option value='' >"+"暂无飞机"+"</option>";
	}
	$("#fjzch").html(planeRegOption); 
}
var flighthistoryMain={
		fdjsl:1,
		fjzch:'',
		colspan:34,
		inint:function(){
			//发动机表头还原为1个
			this.showfdjTh();
			//获取飞机注册号和发动机数量
			this.getFjzchAndfdjsl();
		},
		showfdjTh:function(){
			//发动机表头还原为1个
			this.hidefdjTh();
			//根据发动机数量显示发动机表头
			this.getfdjTh();
		},
		hidefdjTh:function(){
			for(var i=1;i<=4;i++){
				var children = $("#fdjTh"+i).attr("children");
				var childrenList = children.split(","); 
				$.each(childrenList,function(index,row){
					$("th[name='"+row+"']").hide();
					$("td[name='"+row+"']").hide();
				});
				$("#fdjTh"+i).hide();
			}
		},
		getfdjTh:function(){
			var count = this.fdjsl;
			var colspan = this.colspan;
			for(var i=1;i<=count;i++){
				var children = $("#fdjTh"+i).attr("children");
				var childrenList = children.split(","); 
				$.each(childrenList,function(index,row){
					$("th[name='"+row+"']").show();
					$("td[name='"+row+"']").show();
				});
				$("#fdjTh"+i).show();
				colspan += 3;
			}
			this.colspan = colspan;
		},
		getFjzchAndfdjsl:function(){
			var this_ = this;
			var str = $("#fjzch").val();
			 if(str){
				 var fjzchAndFdjsl = str.split("#_#"); 
				 this_.fjzch = fjzchAndFdjsl[0];
				 this_.fdjsl = fjzchAndFdjsl[1];
			 }
		},
		gatherSearchParam:function(){
			var searchParam={};
			 var fxrq=$("#fxrq").val();
			 var paramsMap={};
			 paramsMap.fjzch=this.fjzch;
			 paramsMap.dprtcode=$("#dprtcode_search").val();
			 paramsMap.keyword=$.trim($('#keyword_search').val());
			 paramsMap.hbh=$.trim($('#hbh').val());
			 paramsMap.jlbym=$.trim($('#jlbym').val());
			 paramsMap.zt=$.trim($('#zt').val());
			 paramsMap.fdjsl=this.fdjsl;
			 //评估期限
			 if(null != fxrq && "" != fxrq){
				 var fxrqBegin = fxrq.substring(0,10)+" 00:00:00";
				 var fxrqEnd = fxrq.substring(13,23)+" 23:59:59";
				 paramsMap.fxrqBegin = fxrqBegin;
				 paramsMap.fxrqEnd = fxrqEnd;
			 }
			 searchParam.paramsMap=paramsMap;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = pagination;
			 startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/produce/flighthistory/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					   finishWait();
			 			if(data.rows.length > 0){
			 			_this.appendContentHtml(data.rows);
			 			_this.appendXiaojiHtml(data.subtotal,data.rwlxList);
			 			//显示发动机
			 			_this.showfdjTh();
			 			//分页
			 			var page_ = new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						  /* signByKeyword($("#keyword_search").val(),[3,_this.colspan-1,_this.colspan]);*/
						   signByKeyword($("#keyword_search").val(),[4,5,6,7]);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#xiaoji").hide();
						$("#list").append("<tr><td class='text-center' colspan=\""+_this.colspan+"\">暂无数据 No data.</td></tr>");
						
					}
			 			/*new Sticky({tableId:'main_list_table'});*/
			 		}
			 });
		},
		reload:function(){
			TableUtil.resetTableSorting("main_list_table")
			this.inint();
			this.goPage(1,"auto","desc");
		},
		fxrwlxAppend : function(list){
			var htmlContent = '';
			if(list != null && list.length > 0 ){
				$.each(list,function(index,row){
					if(row.fxrwlx){
						htmlContent += StringUtil.escapeStr(row.fxrwlx) + " : " + TimeUtil.convertToHour(row.paramsMap.lj_fxsj, TimeUtil.Separator.COLON)+" ";
					}
				});
			}
			return htmlContent;
		},
		//小计拼接
		appendXiaojiHtml:function(row,list){
			var htmlContent = '';
				htmlContent += "<tr style='height:5px;'>";
				htmlContent += "<td colspan='"+flighthistoryMain.colspan+"' style='border-bottom:0px;'></td>";
				htmlContent += "</tr>";
				htmlContent += "<tr class='thead-total'>";
				htmlContent += "<th colspan='7' style='text-align:left !important;'> ";
				htmlContent += "<button  class='total-close' onclick='closeTotalTr(this)' > &times;</button>";
				htmlContent += "累计小计<small>(统计符合搜索条件的所有数据的累计值)</small></th>";
				htmlContent += "<th colspan='7' style='text-align:left !important;'> ";
				htmlContent += flighthistoryMain.fxrwlxAppend(list);
				htmlContent += "</th>";
				
				htmlContent += "<th class='text-center' >"+TimeUtil.convertToHour(row.paramsMap.lj_sysj, TimeUtil.Separator.COLON)+"</th>";
				htmlContent += "<th class='text-center' >"+TimeUtil.convertToHour(row.paramsMap.lj_fxsj, TimeUtil.Separator.COLON)+"</th>";
				htmlContent += "<th class='text-center' >"+row.paramsMap.lj_fxxh+"</th>";
				htmlContent += "<th ></th>";
				htmlContent += "<th class='text-center' name='foneBJ'></th>";
				htmlContent += "<th class='text-center' name='foneEH'>"+TimeUtil.convertToHour(row.paramsMap.lj_fdj1_sj, TimeUtil.Separator.COLON)+"</th>";
				htmlContent += "<th class='text-center' name='foneEC'>"+row.paramsMap.lj_fdj1_xh+"</th>";
				htmlContent += "<th class='text-center' name='fonehy'>"+row.paramsMap.lj_fdj1_hytjl+"</th>";
				
				htmlContent += "<th class='text-center' name='ftwoBJ'></th>";
				htmlContent += "<th class='text-center' name='ftwoEH'>"+TimeUtil.convertToHour(row.paramsMap.lj_fdj2_sj, TimeUtil.Separator.COLON)+"</th>";
				htmlContent += "<th class='text-center' name='ftwoEC'>"+row.paramsMap.lj_fdj2_xh+"</th>";
				htmlContent += "<th class='text-center' name='ftwohy'>"+row.paramsMap.lj_fdj2_hytjl+"</th>";
				
				htmlContent += "<th class='text-center' name='fthreeBJ'></th>";
				htmlContent += "<th class='text-center' name='fthreeEH'>"+TimeUtil.convertToHour(row.paramsMap.lj_fdj3_sj, TimeUtil.Separator.COLON)+"</th>";
				htmlContent += "<th class='text-center' name='fthreeEC'>"+row.paramsMap.lj_fdj3_xh+"</th>";
				htmlContent += "<th class='text-center' name='fthreehy'>"+row.paramsMap.lj_fdj3_hytjl+"</th>";
				
				htmlContent += "<th class='text-center' name='ffourBJ'></th>";
				htmlContent += "<th class='text-center' name='ffourEH'>"+TimeUtil.convertToHour(row.paramsMap.lj_fdj4_sj, TimeUtil.Separator.COLON)+"</th>";
				htmlContent += "<th class='text-center' name='ffourEC'>"+row.paramsMap.lj_fdj4_xh+"</th>";
				htmlContent += "<th class='text-center' name='ffourhy'>"+row.paramsMap.lj_fdj4_hytjl+"</th>";
				
				htmlContent += "<th class='text-center' name='apuBJ'></th>";
				htmlContent += "<th class='text-center'>"+TimeUtil.convertToHour(row.paramsMap.lj_apu_sj, TimeUtil.Separator.COLON)+"</th>";
				htmlContent += "<th class='text-center'>"+row.paramsMap.lj_apu_xh+"</th>";
				htmlContent += "<th class='text-center'>"+row.paramsMap.lj_apu_hytjl+"</th>";
				htmlContent += "<th colspan='12' ></th>";
				htmlContent += "</tr>";
				htmlContent += "<tr style='height:5px;'><td colspan='"+flighthistoryMain.colspan+"' style='border-top:0px;'></td></tr>";
				$("#xiaoji").show();
				$("#xiaoji").empty();
				$("#xiaoji").html(htmlContent);
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
				htmlContent += "<tr >";
			    htmlContent += "<td class='text-center' name='zt'>"+DicAndEnumUtil.getEnumName('flbStatusEnum',row.flightSheet.zt)+"</td>"; 
			    htmlContent += "<td class='text-center' name='fxrq'>"+StringUtil.escapeStr(row.flightSheet.fxrq?row.flightSheet.fxrq.substring(0,10):'')+"</td>"; 
			    htmlContent += "<td name='fjzch' class='text-center' title='"+StringUtil.escapeStr(row.flightSheet.fjzch)+"'>"+StringUtil.escapeStr(row.flightSheet.fjzch)+"</td>"; 
			    htmlContent += "<td name='jldh' class='text-center' title='"+StringUtil.escapeStr(row.flightSheet.fxjlbh)+"'>"+StringUtil.escapeStr(row.flightSheet.fxjlbh)+"</td>"; 
			    htmlContent += "<td name='hbh' class='text-center' title='"+StringUtil.escapeStr(row.hbh)+"'>"+StringUtil.escapeStr(row.hbh)+"</td>";
			    htmlContent += "<td name='jlbym' class='text-center' title='"+StringUtil.escapeStr(row.flightSheet.jlbym)+"'>"+StringUtil.escapeStr(row.flightSheet.jlbym)+"</td>"; 
			    htmlContent += "<td name='hc' class='text-center' title='"+StringUtil.escapeStr(row.hcms)+"'>"+StringUtil.escapeStr(row.hcms)+"</td>"; 
			    htmlContent += "<td name='fxrwlx' class='text-center' title='"+StringUtil.escapeStr(row.fxrwlx)+"'>"+StringUtil.escapeStr(row.fxrwlx)+"</td>";
			    htmlContent += "<td name='qfz' class='text-center' title='"+StringUtil.escapeStr(row.qfz)+"'>"+StringUtil.escapeStr(row.qfz)+"</td>";
			    htmlContent += "<td name='zlz' class='text-center' title='"+StringUtil.escapeStr(row.zlz)+"'>"+StringUtil.escapeStr(row.zlz)+"</td>";
			    
			    htmlContent += "<td name='kc' class='text-center' title='"+StringUtil.escapeStr(row.kcsj?row.kcsj.substring(11,16):'')+"'>"+StringUtil.escapeStr(row.kcsj?row.kcsj.substring(11,16):'')+"</td>"; 
			    htmlContent += "<td name='qf' class='text-center' title='"+StringUtil.escapeStr(row.qfsj?row.qfsj.substring(11,16):'')+"'>"+StringUtil.escapeStr(row.qfsj?row.qfsj.substring(11,16):'')+"</td>"; 
			    htmlContent += "<td name='ld' class='text-center' title='"+StringUtil.escapeStr(row.ldsj?row.ldsj.substring(11,16):'')+"'>"+StringUtil.escapeStr(row.ldsj?row.ldsj.substring(11,16):'')+"</td>";
			    htmlContent += "<td name='tc' class='text-center' title='"+StringUtil.escapeStr(row.tcsj?row.tcsj.substring(11,16):'')+"'>"+StringUtil.escapeStr(row.tcsj?row.tcsj.substring(11,16):'')+"</td>";
			    
			    htmlContent += "<td name='bcsy' class='text-center' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.sysjFz), TimeUtil.Separator.COLON)/*+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.sysjFz,2), TimeUtil.Separator.COLON)*/+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.sysjFz), TimeUtil.Separator.COLON)/*+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.sysjFz,2), TimeUtil.Separator.COLON)*/+"</td>"; 
			    htmlContent += "<td name='bcfc' class='text-center' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.fxsjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fxjslj,0), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.fxsjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fxjslj,0), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td name='ljfc' class='text-center' title='"+StringUtil.escapeStr(row.fxxh?row.fxxh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fxjslj,1)+"'>"+StringUtil.escapeStr(row.fxxh?row.fxxh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fxjslj,1)+"</td>"; 
			    htmlContent += "<td name='lxqlcs' class='text-center' title='"+StringUtil.escapeStr(row.lxqlcs)+"'>"+StringUtil.escapeStr(row.lxqlcs)+"</td>"; 
			   
			    var strKg = "";
			    if(flighthistoryMain.appendLj(row.paramsMap.fdj_1,0)){
			    	strKg = " ";
			    }
			    htmlContent += "<td name='foneBJ' class='text-center' title='"+flighthistoryMain.appendLj(row.paramsMap.fdj_1,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_1,1)+"'>"+flighthistoryMain.appendLj(row.paramsMap.fdj_1,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_1,1)+"</td>"; 
			    htmlContent += "<td name='foneEH' class='text-center' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f1SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_1,2), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f1SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_1,2), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td name='foneEC' class='text-center' title='"+StringUtil.escapeStr(row.f1Xh?row.f1Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_1,3)+"'>"+StringUtil.escapeStr(row.f1Xh?row.f1Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_1,3)+"</td>"; 
			    htmlContent += "<td name='fonehy' class='text-center' title='"+StringUtil.escapeStr(row.f1Hytjl)+"'>"+StringUtil.escapeStr(row.f1Hytjl)+"</td>"; 
			    
			     strKg = "";
			    if(flighthistoryMain.appendLj(row.paramsMap.fdj_2,0)){
			    	strKg = " ";
			    }
			    htmlContent += "<td name='ftwoBJ' class='text-center' title='"+flighthistoryMain.appendLj(row.paramsMap.fdj_2,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_2,1)+"'>"+flighthistoryMain.appendLj(row.paramsMap.fdj_2,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_2,1)+"</td>"; 
			    htmlContent += "<td name='ftwoEH' class='text-center' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f2SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_2,2), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f2SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_2,2), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td name='ftwoEC' class='text-center' title='"+StringUtil.escapeStr(row.f2Xh?row.f2Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_2,3)+"'>"+StringUtil.escapeStr(row.f2Xh?row.f2Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_2,3)+"</td>"; 
			    htmlContent += "<td name='ftwohy' class='text-center' title='"+StringUtil.escapeStr(row.f2Hytjl)+"'>"+StringUtil.escapeStr(row.f2Hytjl)+"</td>"; 
			    
			     strKg = "";
			    if(flighthistoryMain.appendLj(row.paramsMap.fdj_3,0)){
			    	strKg = " ";
			    }
			    htmlContent += "<td name='fthreeBJ' class='text-center' title='"+flighthistoryMain.appendLj(row.paramsMap.fdj_3,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_3,1)+"'>"+flighthistoryMain.appendLj(row.paramsMap.fdj_3,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_3,1)+"</td>"; 
			    htmlContent += "<td name='fthreeEH' class='text-center' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f3SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_3,2), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f3SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_3,2), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td name='fthreeEC' class='text-center' title='"+StringUtil.escapeStr(row.f3Xh?row.f3Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_3,3)+"'>"+StringUtil.escapeStr(row.f3Xh?row.f3Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_3,3)+"</td>"; 
			    htmlContent += "<td name='fthreehy' class='text-center' title='"+StringUtil.escapeStr(row.f3Hytjl)+"'>"+StringUtil.escapeStr(row.f3Hytjl)+"</td>"; 
			   
			     strKg = "";
			    if(flighthistoryMain.appendLj(row.paramsMap.fdj_4,0)){
			    	strKg = " ";
			    }
			    htmlContent += "<td name='ffourBJ' class='text-center' title='"+flighthistoryMain.appendLj(row.paramsMap.fdj_4,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_4,1)+"'>"+flighthistoryMain.appendLj(row.paramsMap.fdj_4,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.fdj_4,1)+"</td>"; 
			    htmlContent += "<td name='ffourEH' class='text-center' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f4SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_4,2), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.f4SjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.fdj_4,2), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td name='ffourEC' class='text-center' title='"+StringUtil.escapeStr(row.f4Xh?row.f4Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_4,3)+"'>"+StringUtil.escapeStr(row.f4Xh?row.f4Xh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.fdj_4,3)+"</td>"; 
			    htmlContent += "<td name='ffourhy' class='text-center' title='"+StringUtil.escapeStr(row.f4Hytjl)+"'>"+StringUtil.escapeStr(row.f4Hytjl)+"</td>"; 
			   
			     strKg = "";
			    if(flighthistoryMain.appendLj(row.paramsMap.apu,0)){
			    	strKg = " ";
			    }
			    htmlContent += "<td name='apuBJ' class='text-center' title='"+flighthistoryMain.appendLj(row.paramsMap.apu,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.apu,1)+"'>"+flighthistoryMain.appendLj(row.paramsMap.apu,0)+strKg+flighthistoryMain.appendLj(row.paramsMap.apu,1)+"</td>"; 
			    htmlContent += "<td name='apuh' class='text-center' title='"+TimeUtil.convertToHour(StringUtil.escapeStr(row.apuSjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.apu,2), TimeUtil.Separator.COLON)+"'>"+TimeUtil.convertToHour(StringUtil.escapeStr(row.apuSjFz), TimeUtil.Separator.COLON)+" / "+TimeUtil.convertToHour(flighthistoryMain.appendLj(row.paramsMap.apu,2), TimeUtil.Separator.COLON)+"</td>"; 
			    htmlContent += "<td name='apuc' class='text-center' title='"+StringUtil.escapeStr(row.apuXh?row.apuXh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.apu,3)+"'>"+StringUtil.escapeStr(row.apuXh?row.apuXh:0)+" / "+flighthistoryMain.appendLj(row.paramsMap.apu,3)+"</td>"; 
			    htmlContent += "<td name='apuhy' class='text-center' title='"+StringUtil.escapeStr(row.apuHytjl)+"'>"+StringUtil.escapeStr(row.apuHytjl)+"</td>"; 
			    
			    htmlContent += "<td name='idgtk' class='text-center' title='"+StringUtil.escapeStr(row.idgtksj)+"'>"+StringUtil.escapeStr(row.idgtksj)+"</td>"; 
			    htmlContent += "<td name='yyy' class='text-center' title='"+StringUtil.escapeStr(row.yyy)+"'>"+StringUtil.escapeStr(row.yyy)+"</td>"; 
			    htmlContent += "<td name='cyl' class='text-center' title='"+StringUtil.escapeStr(row.ryCyl)+"'>"+StringUtil.escapeStr(row.ryCyl)+"</td>"; 
			    htmlContent += "<td name='jyl' class='text-center' title='"+StringUtil.escapeStr(row.ryJyl)+"'>"+StringUtil.escapeStr(row.ryJyl)+"</td>"; 
			    htmlContent += "<td name='fxqzyl' class='text-center' title='"+StringUtil.escapeStr(row.ryCyl+row.ryJyl)+"'>"+StringUtil.escapeStr(row.ryCyl+row.ryJyl)+"</td>"; 
			    htmlContent += "<td name='fxhsyyl' class='text-center' title='"+StringUtil.escapeStr(row.rySyyl)+"'>"+StringUtil.escapeStr(row.rySyyl)+"</td>"; 
			    htmlContent += "<td name='xhyl' class='text-center' title='"+((parseFloat(row.ryCyl || 0)+parseFloat(row.ryJyl || 0))-parseFloat(row.rySyyl || 0)).toFixed(2)+"'>"+((parseFloat(row.ryCyl || 0)+parseFloat(row.ryJyl || 0))-parseFloat(row.rySyyl || 0)).toFixed(2)+"</td>"; 
			   /* htmlContent += "<td name='fxr' class='text-center' title='"+StringUtil.escapeStr(row.fxr)+"'>"+StringUtil.escapeStr(row.fxr)+"</td>"; */
			    htmlContent += "<td name='jc' class='text-center' title='"+StringUtil.escapeStr(row.jz)+"'>"+StringUtil.escapeStr(row.jz)+"</td>"; 
			    htmlContent += "<td name='zdrid' class='text-center' title='"+StringUtil.escapeStr(row.paramsMap.username+" "+row.paramsMap.realname)+"'>"+StringUtil.escapeStr(row.paramsMap.username+" "+row.paramsMap.realname)+"</td>"; 
			    htmlContent += "<td name='zdsj' class='text-center' title='"+StringUtil.escapeStr(row.flightSheet.zdsj)+"'>"+StringUtil.escapeStr(row.flightSheet.zdsj)+"</td>"; 
			    htmlContent += "<td name='dprtcode' class='text-center' title='"+StringUtil.escapeStr(row.paramsMap.dprtName)+"'>"+StringUtil.escapeStr(row.paramsMap.dprtName)+"</td>"; 
			    htmlContent += "</tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   //refreshPermission(); 
		 });
			flighthistory_hover.init();
		},
		// 拼接累计
		appendLj:function(obj,index){
			var str = '0';
			if(obj){
				var data = obj.split("#_#");
				if(index == 0 || index ==1){
					str =data[index]?data[index]:'';
				}else{
					str =data[index]?data[index]:'0';
				}
			}
			return str;
		},
		// 获取机型
		getACType : function(){
			return $.trim($("#fjzch option:selected").attr("jx"));
		},
		// 获取飞机注册号
		getFjzch : function(){
			return $.trim($("#fjzch").val());
		},
		// 获取组织机构
		getDprtcode : function(){
			return userJgdm;
		},
		//导入飞行履历：显示导入模态框
		showImportModal : function(){
			var this_ = this;
			if(StringUtil.escapeStr(this_.fjzch) == ""){
				AlertUtil.showMessage("请选择飞机注册号！");
			}else{
				 // 初始化导入
			    importModal.init({
				    importUrl:"/produce/flighthistory/fxll/excelImport?dprtcode="+this_.getDprtcode()+"&fjjx="+this_.getACType()+"&fjzch="+this_.getFjzch(),
				    downloadUrl:"/common/templetDownfile/12", //飞行履历
					callback: function(data){
						// 重新加载table数据
						this_.reload();
						$("#ImportModal").modal("hide");
					}
				});
				importModal.show();
			}
		}
};
function orderBy(obj){
	// 字段排序
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
			orderType = "asc";
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		flighthistoryMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}
//搜索条件显示与隐藏 
function search() {
	
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}

var flighthistory_hover = {
		
		id : "main_list_table",
		
		// 初始化
		init : function(){
			this.initHover();
		},
			
		// 选择列高亮
		initHover : function(){
			var this_ = this;
			
			$("#"+this_.id+" td,th").hover(
					
					function(){
						/*alert(00);*/
						var columeName = $(this).attr("name");
						$("#"+this_.id+" td[name='"+columeName+"'],#"+this_.id+" th[name='"+columeName+"'],#"+this_.id+" th[children*='"+columeName+"']").addClass("warning");
					},
					function(){
						/*alert(01);*/
		     		  $("#"+this_.id+" td,th").removeClass("warning");
					}
		     );
		},
		
	};
function closeTotalTr(obj){
	
	$(obj).parent("th").parent("tr").hide();
	$("#totalIcon").show();
	$(obj).parent("th").parent("tr").parent("thead").find("tr:visible").css("background","#fff7f1");
}

function showTotalTr(){
	$(".thead-total").show();
	$("#totalIcon").hide();
	$(".thead-total").parent("thead").find("tr").not(".thead-total").css("background","");
}






/**
 * 显示导入模态框
 */
/*function showImportModal(){
	 importModal.show();
}*/
/**
 * 初始化导入
 */
/*function initImport(){
	$("#ImportBody").prepend(['<div class="row padding-bottom-5">',
		     					'<label class="col-lg-2 text-right padding-left-0 padding-right-0">',
									'<div class="font-size-12 line-height-18">',
										'<span style="color: red"></span>飞机注册号',
									'</div>',
									'<div class="font-size-9 line-height-18">A/C REG</div>',
								'</label>',
								'<div class="col-lg-7 padding-left-8 padding-right-0" >',
									'<select class="form-control" id="fjzch_import">'+$("#fjzch_search").html()+'</select>',
								'</div>',
								'<input type="text" id="ignore" style="display:none; value="0">',
							 '</div>'].join(""));
	// 初始化导入
   importModal.init({
	    importUrl:"/produce/flighthistory/excelImport",
	    formData:{
	    	fjzch : function(){
	    		return $("#fjzch_import").val();
	    	}
	    },
	    downloadUrl:"/common/templetDownfile/12",
		callback: function(data){
			// 刷新装机清单数据
			changeSelectedPlane();
			$("#ignore").val(0);
		},
		beforeImport: function(){
			// 验证飞机是否已经初始化
			var issync = $("#fjzch_import option:selected").attr("issync");
			var ignore = $("#ignore").val();
			if(issync == 0 || ignore == 1){
				return true;
			}else{
				AlertUtil.showConfirmMessage("该飞机数据已初始化成功，是否需要继续导入",{callback:function(option){
					$("#ignore").val(1);
					$("#ImportBody form input").change();
				}});
				return false;
			}
		}
	})
}*/
//导出
function exportExcel(){
	var searchParam = flighthistoryMain.gatherSearchParam();
		window.open(basePath+"/produce/flighthistory/flighthistory.xls?paramsMap[dprtcode]="
				+ encodeURIComponent(searchParam.paramsMap.dprtcode)+
				"&paramsMap[fjzch]="+encodeURIComponent(searchParam.paramsMap.fjzch)+
				"&paramsMap[keyword]="+ encodeURIComponent(searchParam.paramsMap.keyword) + 
				"&paramsMap[hbh]=" + encodeURIComponent(searchParam.paramsMap.hbh)+
				"&paramsMap[jlbym]="+ encodeURIComponent(searchParam.paramsMap.jlbym) + 
				"&paramsMap[fdjsl]="+ encodeURIComponent(searchParam.paramsMap.fdjsl) + 
				"&paramsMap[zt]=" + encodeURIComponent(searchParam.paramsMap.zt));
}