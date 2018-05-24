
TechnicalOrder = {
	flag : true,
	id : "TechnicalOrder",
	tbodyId : 'thchnical_order_list',
	param: {
		pgdid:'' //评估单id
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
			url:basePath+"/project/instruction/queryByPgdId",
			type:"post",
			data:{pgdid:pgdid},
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendTechnicalHtml(data);
				}else{
					$("#"+this_.tbodyId).empty();
					$("#"+this_.tbodyId).append("<tr class='text-center'><td colspan=\"10\">暂无数据 No data.</td></tr>");
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
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
			} else {
				htmlContent += "<tr bgcolor=\"#fefefe\" >";
			}
			htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			htmlContent += "<td class='text-center'><a href=\"javascript:"+this_.id+".viewMainInstruction('"+row.id+"','"+row.dprtcode+"')\">"+formatUndefine(row.jszlh)+"</a></td>";   
			htmlContent += "<td class='text-left'>";
			htmlContent += this_.formatPgdn(row.pgjg, row.dprtcode);
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.ckzl)+"' class='text-left'>"+StringUtil.escapeStr(row.ckzl)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zhut)+"' class='text-left'>"+row.zhut+"</td>";
			if(row.zt==9){
				var zdjsr = StringUtil.escapeStr(row.zdjsr_user)?StringUtil.escapeStr(row.zdjsr_user.displayName):'';
				htmlContent += "<td class='text-left'><a href='javascript:void(0);' djid='"+row.id+"' sqdh='"+StringUtil.escapeStr(row.jszlh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+zdjsr+"' zdjsrq='"+row.zdjsrq+"' onclick=\""+this_.id+".shutDown(this,false)\">"+formatUndefine(zts[row.zt])+"</a></td>";
			}else{
				htmlContent += "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td>";    
			}
			if(row.jszt==0){
				htmlContent += "<td class='text-center'>未接收</td>";  
			}else{
				htmlContent += "<td class='text-center'>已接收</td>";  
			}
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.zdr_user.displayName)+"</td>"; 
			htmlContent += "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";  
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
			htmlContent += "</tr>"; 
		});
		$("#"+this_.tbodyId).empty();
		$("#"+this_.tbodyId).html(htmlContent);
	},
	formatPgdn : function(pgjg,dprtcode){
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
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick="+this_.id+".vieworhidePgdh(this)></i><div class='orderDisplayFile' style='display:none'>";
				}
				htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".selectPgdBypgdh('"+pgdhid+"','"+dprtcode+"') >"+StringUtil.escapeStr(pgdh)+"</a>";
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
			$(".orderDisplayFile").each(function(){
				$(this).fadeIn(500);
				$(this).prev().removeClass("icon-caret-down");
				$(this).prev().addClass("icon-caret-up");
			});
			this.flag = false;
		} else {
			$(".orderDisplayFile").each(function(){
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
			chinaHead:'技术指令编号',//单号中文名称
			englishHead:'Technical Order No.',//单号英文名称
			edit:isEdit,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
			jsdh:sqdh,//指定结束单号
			jsr:zdjsr,//指定结束人
			jssj:zdjsrq,//指定结束时间
			jsyy:zdjsyy,//指定结束原因
			callback: function(data){//回调函数
			}
		});
	},
	viewMainInstruction : function(id,dprtcode){
		window.open(basePath+"/project/instruction/intoViewMainInstruction?id=" + id+"&dprtcode="+dprtcode);
	},
	selectPgdBypgdh : function(id,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	}
}