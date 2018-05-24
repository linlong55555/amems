
Amendmentnotice = {
	flag : true,
	id : "Amendmentnotice",
	tbodyId : 'revision_notice_book_list',
	param: {
		pgdid:'', //评估单id
		tzslx:'',//通知书类型
		titleHead:'修订通知书/Amendment Notice',
		yjtsJb1:0,
		yjtsJb2:0,
		yjtsJb3:0
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		$("#titleNoticeHead").html(this.param.titleHead);
		this.loadData();
	},
	loadData : function(){
		var pgdid = this.param.pgdid;
		var tzslx = this.param.tzslx;
		var this_ = this;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project/revisionNoticeBook/queryByPgdId",
			type:"post",
			data:{pgdid:pgdid,tzslx:tzslx},
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendTechnicalHtml(data);
				}else{
					$("#"+this_.tbodyId).empty();
					$("#"+this_.tbodyId).append("<tr class='text-center'><td colspan=\"15\">暂无数据 No data.</td></tr>");
				}
			}
		});
	},
	appendTechnicalHtml : function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\""+this_.getWarningColor("#f9f9f9",row.remainingDays,row.zt)+"\" >";
			} else {
				htmlContent += "<tr bgcolor=\""+this_.getWarningColor("#fefefe",row.remainingDays,row.zt)+"\" >";
			}
			htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			htmlContent += "<td class='text-center fixed-column'><a href=\"javascript:"+this_.id+".viewMain('"+row.id+"','"+row.dprtcode+"')\">"+formatUndefine(row.jszlh)+"</td>";  
			htmlContent += "<td class='text-left'>"+this_.formatPgdn(row.orderSources, row.dprtcode)+"</td>";  
			htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('revisionNoticeBookTypeEnum',row.tzslx)+"</td>";  
		   	htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jx)+"</td>";  
		   	htmlContent += "<td title='"+StringUtil.escapeStr(row.xdzt)+"'class='text-left'>"+StringUtil.escapeStr(row.xdzt)+"</td>";
		   	htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.xdr.displayName)+"</td>";  
		   	htmlContent += "<td class='text-center'>"+formatUndefine(row.xdqx)+"</td>";  
		   	htmlContent += "<td class='text-right'>"+formatUndefine(row.remainingDays)+"</td>";  
		   	htmlContent += "<td title='"+StringUtil.escapeStr(row.xdnr)+"'class='text-left'>"+StringUtil.escapeStr(row.xdnr)+"</td>";
		   	if(row.zt==9){
		   		var zdjsr = StringUtil.escapeStr(row.zdjs_user?StringUtil.escapeStr(row.zdjs_user.displayName):'');
		   		htmlContent += "<td class='text-left'><a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.jszlh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+row.zdjsrq+"' onclick=\""+this_.id+".shutDown(this,false)\">"+DicAndEnumUtil.getEnumName('revisionNoticeBookZtEnum',row.zt)+"</a></td>";  
		   	}else{
		   		htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('revisionNoticeBookZtEnum',row.zt) +"</td>";     
			   	}
		   	if(row.jszt==0){
		   		htmlContent += "<td class='text-center'>未接收</td>";     
		   	}else{
		   		htmlContent += "<td class='text-center'>已接收</td>";     
		   	}
		   	if(undefined != row.zdr){
		   		htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
		   	}
		   	else{
		   		htmlContent += "<td class='text-left'></td>";
		   	}
		   	htmlContent += "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
		   	htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.dprtname)+"</td>";
		   	htmlContent += "</tr>";   
		});
		$("#"+this_.tbodyId).empty();
		$("#"+this_.tbodyId).html(htmlContent);
	},
	formatPgdn : function(strs,dprtcode){
		var htmlContent = '';
		var this_ = this;
		if(strs != null && strs.length > 0){
			strs.sort();
			$.each(strs,function(i,s){
				if(i == 1){
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick="+this_.id+".vieworhidePgdh(this)></i><div class='noticeDisplayFile' style='display:none'>";
				}
				htmlContent += "<a href=\"javascript:"+this_.id+".selectPgdBypgdh('"+s.pgdid+"','"+dprtcode+"')\" >"+StringUtil.escapeStr(s.pgdh)+"</a>";
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
	vieworhidePgdh : function(obj){
		var flag = $(obj).next().is(":hidden");
		if (flag) {
			$(obj).next().fadeIn(500);
			$(obj).removeClass("icon-caret-down");
			$(obj).addClass("icon-caret-up");
		} else {
			$(obj).next().hide();
			$(obj).removeClass("icon-caret-up");
			$(obj).addClass("icon-caret-down");
		}
	},
	vieworhideContentAll : function(){
		if (this.flag) {
			$(".noticeDisplayFile").each(function(){
				$(this).fadeIn(500);
				$(this).prev().removeClass("icon-caret-down");
				$(this).prev().addClass("icon-caret-up");
			});
			this.flag = false;
		} else {
			$(".noticeDisplayFile").each(function(){
				$(this).hide();
				$(this).prev().removeClass("icon-caret-up");
				$(this).prev().addClass("icon-caret-down");
			});
			this.flag = true;
		}
	},
	shutDown : function(this_,isEdit){
		var id = $(this_).attr("djid");
		var sqdh = $(this_).attr("sqdh");
		var zdjsyy = $(this_).attr("zdjsyy");
		var zdjsr = $(this_).attr("zdjsr");
		var zdjsrq = $(this_).attr("zdjsrq");
		AssignEndModal.show({
			chinaHead:'修订通知书编号',//单号中文名称
			englishHead:'A/N No.',//单号英文名称
			edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
			jsdh:sqdh,//指定结束单号
			jsr:zdjsr,//指定结束人
			jssj:zdjsrq,//指定结束时间
			jsyy:zdjsyy,//指定结束原因
			callback: function(data){//回调函数
			}
		});
	},
	viewMain : function(id,dprtcode){
		window.open(basePath+"/project/revisionNoticeBook/intoViewRevisionNoticeBook?id="+ id+"&dprtcode="+dprtcode);
	},
	selectPgdBypgdh : function(pgdid,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(pgdid)+"/"+$.trim(dprtcode)+"");
	}
}