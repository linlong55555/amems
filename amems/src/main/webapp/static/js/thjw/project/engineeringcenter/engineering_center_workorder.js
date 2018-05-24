
WorkOrder = {
	flag : true,
	id : "WorkOrder",
	tbodyId : 'work_order_list',
	param: {
		searchParam:{} ,//查询参数
		url: ''
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.loadData();
	},
	loadData : function(){
		var this_ = this;
		AjaxUtil.ajax({
			async: false,
			url:this_.param.url,
			type:"post",
			data:this_.param.searchParam,
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendTechnicalHtml(data);
				}else{
					$("#"+this_.tbodyId).empty();
					$("#"+this_.tbodyId).append("<tr class='text-center'><td colspan=\"16\">暂无数据 No data.</td></tr>");
				}
			}
		});
	},
	appendTechnicalHtml : function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
			} else {
				htmlContent += "<tr bgcolor=\"#fefefe\" >";
			}
			htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
			var gdlxName = '';
			if(row.gddlx==2){
				gdlxName= DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx)+'-'+DicAndEnumUtil.getEnumName('minWorkOrderTypeEnum',row.gdlx);  
			}else{
				gdlxName= DicAndEnumUtil.getEnumName('maxWorkOrderTypeEnum',row.gddlx);  
			}
			htmlContent += "<td class='text-left'>"+gdlxName+"</td>";
			htmlContent += "<td><a title='"+StringUtil.escapeStr(row.gdbh)+"' href=\"javascript:"+this_.id+".looked('"+ row.id + "',"+row.gddlx+")\">"+StringUtil.escapeStr(row.gdbh)+"</a></td>";  
			htmlContent += "<td class='text-left'>";
			htmlContent += this_.formatPgdn(row.pgdid, row.dprtcode);
			htmlContent += "</td>";
			if(row.zxfl=="FZJJ"){
				htmlContent += "<td class='text-left'>非装机件</td>";  
			}else if(row.zxfl=="ZJJ"){
				htmlContent += "<td class='text-left'>飞机部件</td>"; 
			}else{
				htmlContent += "<td class='text-left'>机身</td>";  
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(row.fjzch)+"' class='text-left'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjxlh)+"'>"+StringUtil.escapeStr(row.bjxlh)+"</td>";
			htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.zhuanye)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zjh)+' '+StringUtil.escapeStr(row.zwms)+"'>"+formatUndefine(row.zjh)+' '+formatUndefine(row.zwms)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jdms)+"'>"+StringUtil.escapeStr(row.jdms)+"</td>";
			if(row.zt!=9){
				htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('workOrderStateEnum',row.zt)+"</td>";
			}else{
				htmlContent += "<td class='text-left'>";
				htmlContent +="<a gdbh='"+StringUtil.escapeStr(row.gdbh)+"' zdjsyy='"+StringUtil.escapeStr(row.zdjsyy)+"' zdjsr='"+StringUtil.escapeStr(row.displayname)+"' zdjsrq='"+row.zdjsrq+"' href=\"javascript:"+this_.id+".shutDown(this)\">指定结束</a><br>"; 
				htmlContent += "</td>";
			}
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.displaygzz)+"'>"+StringUtil.escapeStr(row.displaygzz)+"</td>";
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>";
			htmlContent += "<td class='text-center'>"+formatUndefine(row.zdsj)+"</td>";
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.dprtname)+"</td>";
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
				var pgdh = s.split("@")[0];
				var pgdhid = s.split("@")[1];
				if(i == 1){
					htmlContent += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick="+this_.id+".vieworhidePgdh(this)></i><div class='workOrderDisplayFile' style='display:none'>";
				}
				htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".selectPgdBypgdh('"+pgdhid+"','"+dprtcode+"') >"+pgdh+"</a>";
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
			$(".workOrderDisplayFile").each(function(){
				$(this).fadeIn(500);
				$(this).prev().removeClass("icon-caret-down");
				$(this).prev().addClass("icon-caret-up");
			});
			this.flag = false;
		} else {
			$(".workOrderDisplayFile").each(function(){
				$(this).hide();
				$(this).prev().removeClass("icon-caret-up");
				$(this).prev().addClass("icon-caret-down");
			});
			this.flag = true;
		}
	},
	shutDown : function(this_){
		var gdbh = $(this_).attr("gdbh");
		var zdjsyy = $(this_).attr("zdjsyy");
		var zdjsr = $(this_).attr("zdjsr");
		var zdjsrq = $(this_).attr("zdjsrq");
		AssignEndModal.show({
	 		chinaHead:'工单编号',//单号中文名称
	 		englishHead:'W/O No.',//单号英文名称
	 		edit:false,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
	 		jsdh:gdbh,//指定结束单号
	 		jsr:zdjsr,//指定结束人
	 		jssj:zdjsrq,//指定结束时间
	 		jsyy:zdjsyy,//指定结束原因
	 		callback: function(data){//回调函数
	 		}
	 	});
	},
	looked : function(id,gddlx){
		 window.open(basePath+"/project/workorder/LookedWo?id=" + id+"&gddlx="+gddlx);
	},
	selectPgdBypgdh : function(id,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	}
}