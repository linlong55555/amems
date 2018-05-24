
TechnicalBulletin = {
	flag : true,
	id : "TechnicalBulletin",
	tbodyId : 'thchnical_bulletin_list',
	alertModalSendId : 'alertModalSend',
	sendTbodyId : 'SendList',
	sendPaginationId : 'send_pagination',
	selectId : '',
	param: {
		pgdid:'', //评估单id
		yjtsJb1:0,
		yjtsJb2:0,
		yjtsJb3:0
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.loadData(this.param.pgdid);
	},
	loadData : function(pgdid){
		var this_ = this;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project/annunciate/queryByPgdId",
			type:"post",
			data:{pgdid:pgdid},
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendTechnicalHtml(data);
				}else{
					$("#"+this_.tbodyId).empty();
					$("#"+this_.tbodyId).append("<tr class='text-center'><td colspan=\"12\">暂无数据 No data.</td></tr>");
				}
			}
		});
	},
	appendTechnicalHtml : function(list){
		var zt=[0,1,2,3,4,5,6,7,8,9,10];
		var zts=["保存","提交","已审核","已批准","中止(关闭)","审核驳回","批准驳回","","作废","指定结束","完成"];
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\""+this_.getWarningColor("#f9f9f9",row.syts,row.zt)+"\" >";
			} else {
				htmlContent += "<tr bgcolor=\""+this_.getWarningColor("#fefefe",row.syts,row.zt)+"\" >";
			}
			htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			htmlContent += "<td class='text-center'><a href=\"javascript:"+this_.id+".viewMainAnnunciate('"+row.id+"')\">"+formatUndefine(row.jstgh)+"</a></td>";   
			htmlContent += "<td class='text-left'>";
			htmlContent += this_.formatPgdn(row.pgjg, row.dprtcode,row.id);
			htmlContent += "</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ckzl)+"'>"+StringUtil.escapeStr(row.ckzl)+"</td>";  
			htmlContent += "<td class='text-center'><a href=\"javascript:"+this_.id+".selectSend('"+row.id+"')\">"+(row.tnum?row.tnum:0)+"<font color='black'>/"+formatUndefine(row.snum)+"</font></a></td>";  
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zhut)+"'>"+StringUtil.escapeStr(row.zhut)+"</td>";  
			if(row.zt==9){
				var zdjsr = StringUtil.escapeStr(row.jsr_user?row.jsr_user.displayName:'');
			   		htmlContent += "<td class='text-left'><a  djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.jstgh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+row.zdjsrq+"' href=\"javascript:"+this_.id+".shutDown(this,false)\">"+formatUndefine(zts[row.zt])+"</a></td>";  
			   	}else{
			   		htmlContent += "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
			   	}
			htmlContent += "<td class='text-center'>"+indexOfTime(row.tgqx)+"</td>";
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.zdr_user.displayName)+"</td>";  
			htmlContent += "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
			htmlContent += "</tr>";   
		});
		$("#"+this_.tbodyId).empty();
		$("#"+this_.tbodyId).html(htmlContent);
	},
	formatPgdn : function(pgjg,dprtcode,id){
		var htmlContent = '';
		var this_ = this;
		if(pgjg != null && pgjg != ""){
			var strs= new Array(); //定义一数组
			strs = pgjg.split(","); //字符分割
			strs.sort();
			$.each(strs,function(i,s){
				var pgdh = s.split("^")[0];
				var pgdhid = s.split("^")[1];
				if(i == 1){
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer "+id+"' onclick=CollapseOrExpandUtil.collapseOrExpand(this)></i><div class='bulletinDisplayFile' style='display:none'>";
				}
				htmlContent += "<a href=\"javascript:"+this_.id+".selectPgdBypgdh('"+pgdhid+"','"+dprtcode+"')\" >"+StringUtil.escapeStr(pgdh)+"</a>";
				if(i != 0 && i != strs.length - 1){
					htmlContent += "<br/>";
				}
				if(i == strs.length - 1){
					htmlContent += "</div>";
				}
			});
		}
		return htmlContent;
	},
	getWarningColor : function(bgcolor,syts,zt){
		if(!(zt == 0 || zt==5 || zt==6 || zt==1 || zt==2 || zt==3)){
			return bgcolor;
		}
		if(this.param.yjtsJb1 < Number(syts) && Number(syts) <= this.param.yjtsJb2){
			bgcolor = warningColor.level2;//黄色
		}
		if(Number(syts) <= this.param.yjtsJb1){
			bgcolor = warningColor.level1;//红色
		}
		return bgcolor;
	},
	shutDown : function(this_,isEdit){
		var id = $(this_).attr("djid");
		var sqdh = $(this_).attr("sqdh");
		var zdjsyy = $(this_).attr("zdjsyy");
		var zdjsr = $(this_).attr("zdjsr");
		var zdjsrq = $(this_).attr("zdjsrq");
		AssignEndModal.show({
	 		chinaHead:'维护提示编号',//单号中文名称
	 		englishHead:'Technical Bulletin No.',//单号英文名称
	 		edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
	 		jsdh:sqdh,//指定结束单号
	 		jsr:zdjsr,//指定结束人
	 		jssj:zdjsrq,//指定结束时间
	 		jsyy:zdjsyy,//指定结束原因
	 		callback: function(data){//回调函数
	 			
	 		}
	 	});
	},
	selectSend : function(id){
		this.selectId = id;
		$("#"+this.alertModalSendId).modal("show");
		this.goPage(1,"auto","desc");//开始的加载默认的内容 
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam ={};
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.mainid=this.selectId;
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
		   url:basePath+"/project/annunciate/selectSend",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.sendPaginationId,
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
				   }); 
			   } else {
				   $("#"+this_.sendTbodyId, $("#"+this_.alertModalSendId)).empty();
				   $("#"+this_.sendPaginationId, $("#"+this_.alertModalSendId)).empty();
				   $("#"+this_.sendTbodyId, $("#"+this_.alertModalSendId)).append("<tr><td colspan=\"9\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\">";
			} else {
				htmlContent += "<tr bgcolor=\"#fefefe\">";
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(row.use.displayName)+"' class='text-left'>"+formatUndefine(row.use.displayName)+"</td>"; 
			if(row.zt == 0){
				htmlContent += "<td>未接收</td>";   			   
			}else{
				htmlContent += "<td>已接收</td>";  		   
			}
			if(row.bm==null || row.bm==""){
				htmlContent += "<td class='text-left'></td>"; 
			}else{
				htmlContent += "<td title='"+StringUtil.escapeStr(row.bm.dprtname)+"' class='text-left'>"+formatUndefine(row.bm.dprtname)+"</td>"; 
			}
			htmlContent += "<td>"+formatUndefine(row.jssj)+"</td>";   
			htmlContent += "</tr>";   
		    
		});
		$("#"+this_.sendTbodyId, $("#"+this_.alertModalSendId)).empty();
		$("#"+this_.sendTbodyId, $("#"+this_.alertModalSendId)).html(htmlContent);
	},
	viewMainAnnunciate : function(id,dprtcode){
		window.open(basePath+"/project/annunciate/intoViewMainAnnunciate?id=" + id+"&dprtcode=" + dprtcode);
	},
	selectPgdBypgdh : function(id,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	}
}