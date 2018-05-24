
var wcFlag = true;
var orderNumber = 1;//序号
var fjjx = '';
$(function() {
	Navigation(menuCode,"编辑定检项目","Edit");
	getFjjxByWxfabh();
	setMonitorItem();
	initFixedWorkContent();
	sumTotal();
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	wxfabh: {
                validators: {
                    notEmpty: {
                        message: '维修方案编号不能为空'
                    },
                    regexp: {
                    	regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
                        message: '不能输入中文且长度最多不超过20个字符'
                    }
                }
            },
            bb: {
                validators: {
                    notEmpty: {
                        message: '版本不能为空'
                    },
                    stringLength: {
                        max: 12,
                        message: '长度最多不超过12个字符'
                    }
                }
            },
            zwms: {
                validators: {
                    notEmpty: {
                        message: '定检名称不能为空'
                    },
                    /*regexp: {
                        regexp: /^([\u4E00-\u9FA5]+|[a-zA-Z]+)$/,
                        message: '只能输入中文和英文'
                    },*/
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            },
            zyxs: {
                validators: {
                    notEmpty: {
                        message: '请选择重要系数'
                    }
                }
            },
            yxx: {
                validators: {
                    notEmpty: {
                        message: '请选择有效性'
                    }
                }
            },
            jhgsRs: {
                validators: {
                    callback: {
                    	message: '请输入人',
                    	callback: function(value,validator){
                    		if('' == value){
                    			return false;
                    		}
                    		return true;
                    	}
                    }
                }
            },
            jhgsXss: {
                validators: {
                    callback: {
                    	message: '请输入小时',
                    	callback: function(value,validator){
                    		if('' == value){
                    			return false;
                    		}
                    		return true;
                    	}
                    }
                }
            },
            pxh: {
                validators: {
                    notEmpty: {
                        message: '排序号不能为空'
                    },
                    stringLength: {
                        max: 12,
                        message: '长度最多不超过12个字符'
                    }
                }
            }          
        }
    });
	
});

function setMonitorItem(){
	AjaxUtil.ajax({
		url:basePath+"/project/monitoritem/queryListByDjxmid",
		type:"post",
		async: false,
		data:{djxmid:$("#id").val()},
		dataType:"json",
		success:function(data){
			returnView(data);
		}
	});
}

function initFixedWorkContent(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/fixedcheckworkcontent/queryListByDjxmbhid",
		type:"post",
		data:{djxmid:$("#id").val()},
		dataType:"json",
		success:function(data){
			initTableCol(data);
		}
	});
}

function save(){
	
	$('#form').data('bootstrapValidator').validate();
	if(!$('#form').data('bootstrapValidator').isValid()){
		 AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	}
	var wxfabh = $.trim($("#wxfabh").val());
	var bb = $.trim($("#bb").val());
	var zwms = $.trim($("#zwms").val());
	var zyxs = $.trim($("#zyxs").val());
	var yxx = $.trim($("#yxx").val());
	var jhgsRs = $.trim($("#jhgsRs").val());
	var jhgsXss = $.trim($("#jhgsXss").val());
	var pxh = $.trim($("#pxh").val());
	var bz = $.trim($("#bz").val());
	var dprtcode = $.trim($("#dprtcode").val());
	
	if(wxfabh == "" || wxfabh == null){
		AlertUtil.showErrorMessage("维修方案编号不能为空!");
		return false;
	}
	
	if(zwms == "" || zwms == null){
		AlertUtil.showErrorMessage("定检名称不能为空!");
		return false;
	}
	
	if(bb == ""|| bb == null){
		AlertUtil.showErrorMessage("版本号不能为空!");
		return false;
	}
	
	if(jhgsRs == "" || jhgsRs == null){
		AlertUtil.showErrorMessage("人数不能为空!");
		return false;
	}
	
	if(jhgsXss == "" || jhgsXss == null){
		AlertUtil.showErrorMessage("小时不能为空!");
		return false;
	}
	
	if(pxh == "" || pxh == null){
		AlertUtil.showErrorMessage("排序不能为空!");
		return false;
	}
	
	var regu = /^[0-9]+$/;
	if (!regu.test(jhgsRs)) {
        AlertUtil.showErrorMessage("人数只能输入数字！");
        return false;
    }
	
	var regu = /^[0-9]+\.?[0-9]{0,2}$/;
	if (!regu.test(jhgsXss)) {
        AlertUtil.showErrorMessage("小时只能输入数字,并保留两位小数！");
        return false;
    }
	
	var regu = /^[0-9]+$/;
	if (!regu.test(pxh)) {
        AlertUtil.showErrorMessage("排序号只能输入数字！");
        return false;
    }
	
	var fixedCheckItem = {
			id : $.trim($("#id").val()),
			wxfabh : wxfabh,
			bb : bb,
			zwms : zwms,
			zyxs : zyxs,
			yxx : yxx,
			jhgsRs : jhgsRs,
			jhgsXss : jhgsXss,
			bz : bz,
			dprtcode : dprtcode,
			pxh : pxh,
		};

	//获取工作内容
	fixedCheckItem.fixedCheckWorkContentList = getWorkContentParam();
	if(!wcFlag){
		wcFlag = true;
		return false;
	}
	//获取监控项目参数
	var monitorItemList = getMonitorItemParam();//获取监控项目参数
	if(monitorItemList == null || monitorItemList.length == 0){
		AlertUtil.showErrorMessage("请设置监控项!");
		return false;
	}
	if(!monitorItemList){
		return false;
	}
	fixedCheckItem.monitorItemList = monitorItemList;
	
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/project/fixedcheckitem/edit",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('保存成功!','/project/fixedcheckitem/main?id='+$.trim($("#id").val())+'&wxfabh='+encodeURIComponent(wxfabh)+"&pageParam="+pageParam);
		}
	});
}

//初始化表格
function initTableCol(data){
	
	//先移除暂无数据一行
	$("#rotatable").empty();
	
	if(JSON.stringify(data) == '[]'){
		$("#rotatable").append("<tr style='height:35px;'><td colspan='18' class='text-align:center;vertical-align:middle;'>暂无数据 No data.</td></tr>");
		return;
	}
	$.each(data,function(i,workObj){
		
		var display = "";
		var option = "";
		var zjhStr = "";
		
		if(workObj.zt == 1){
			if(workObj.isUpd != null){
				option = '<i class="icon-circle pull-center"></i>';
			}
		}else{
			//display = "display:none";
			option = "删除";
		}
		var fixChapter = workObj.fixChapter;
		if(null != fixChapter && '' != fixChapter){
			zjhStr = formatUndefine(fixChapter.zjh) + " " + formatUndefine(fixChapter.zwms);
		}
		
		var fjzchStr = '';
		if('00000' == StringUtil.escapeStr(workObj.fjzch)){
			fjzchStr = "通用Currency";
	    }else{
	    	var fixedCheckWorkPlaneList = workObj.fixedCheckWorkPlaneList;
			$.each(fixedCheckWorkPlaneList,function(i,s){
				if(s.fjzch != null && s.fjzch != '' && s.fjzch != 'null'){
					fjzchStr += s.fjzch+",";
				}
			});
			if(fjzchStr != ''){
				fjzchStr = StringUtil.escapeStr(fjzchStr);
				fjzchStr = fjzchStr.substring(0,fjzchStr.length - 1);
			}
			workObj.fjzch = fjzchStr;
	    }
		
		var gzlxStr = '';
		if(workObj.gzlx != null && workObj.gzlx != ""){
			var gzlxArr = workObj.gzlx.split(",");
			for(var i = 0 ; i < gzlxArr.length ; i++){
				if('multiselect-all' != gzlxArr[i]){
					gzlxStr += gzlxArr[i]+",";
				}
			}
		}
		if(gzlxStr != ''){
			gzlxStr = gzlxStr.substring(0,gzlxStr.length-1);
		}
		
		workObj.orderNumber = orderNumber++;
		workObj.display = display;
		workObj.option = option;
		workObj.zjhStr = zjhStr;
		workObj.fjzchStr = fjzchStr;
		workObj.gzlxStr = gzlxStr;
		addRow(workObj,"rotatable",true);
	});
}

/**
 * 打开修改工作内容窗口
 * @returns
 */
function openWorkContentWinEdit(node){
	var obj = {};
	var id = $.trim($(node).find("input[name='hiddenid']").val());
	obj.zjh = $.trim($(node).find("input[name='zjh']").val());
	obj.xmly = $.trim($(node).find("span[name='xmly']").text());
	obj.zjhStr = $.trim($(node).find("span[name='zjhStr']").text());
	obj.wz = $.trim($(node).find("span[name='wz']").text());
	obj.scmsZw = $.trim($(node).find("span[name='scmsZw']").text());
	obj.scmsYw = $.trim($(node).find("span[name='scmsYw']").text());
	obj.cksc = $.trim($(node).find("span[name='cksc']").text());
	obj.ckgk = $.trim($(node).find("span[name='ckgk']").text());
	obj.jclx = $.trim($(node).find("input[name='jclx']").val());
	obj.gzzw = $.trim($(node).find("input[name='gzzw']").val());
	obj.isBj = $(node).find("input[name='isBj']").val();
	obj.isMi = $(node).find("input[name='isMi']").val();
	obj.gzlx = $(node).find("input[name='gzlx']").val();
	obj.fjzch = $(node).find("input[name='fjzch']").val();
	obj.bz = $.trim($(node).find("span[name='bz']").text());
	obj.zt = $(node).find("input[name='zt']").val();
	WorkContentModal.show({
		fjjx : fjjx,
		viewObj:obj,//对象，在弹窗中回显
		dprtcode:$("#dprtcode").val(),
		callback: function(data){//回调函数
			if(data != null){
				var zt = data.zt;
				if(zt == 1){
					//$(node).find(".wc").show();
					if(id != null && id != ""){
						$(node).find(".gk").show();
					}
				}else{
					$(node).find(".gk").hide();
					//$(node).find(".wc").hide();
				}
				$(node).find("span[name='zjhStr']").text(data.zjhStr);
				$(node).find("span[name='zjhStr']").parent().attr("title",data.zjhStr);
				$(node).find("input[name='zjh']").val(data.zjh);
				$(node).find("span[name='xmly']").text(data.xmly);
				$(node).find("span[name='xmly']").parent().attr("title",data.xmly);
				$(node).find("span[name='wz']").text(data.wz);
				$(node).find("span[name='wz']").parent().attr("title",data.wz);
				$(node).find("span[name='scmsZw']").text(data.scmsZw);
				$(node).find("span[name='scmsZw']").parent().attr("title",data.scmsZw);
				$(node).find("span[name='scmsYw']").text(data.scmsYw);
				$(node).find("span[name='scmsYw']").parent().attr("title",data.scmsYw);
				$(node).find("span[name='cksc']").text(data.cksc);
				$(node).find("span[name='cksc']").parent().attr("title",data.cksc);
				$(node).find("span[name='ckgk']").text(data.ckgk);
				$(node).find("span[name='ckgk']").parent().attr("title",data.ckgk);
				$(node).find("input[name='jclx']").val(data.jclx);
				$(node).find("input[name='gzzw']").val(data.gzzw);
				$(node).find("span[name='jclx']").text(data.jclx);
				$(node).find("span[name='gzzw']").text(data.gzzw);
				$(node).find("span[name='jclx']").parent().attr("title",data.jclx);
				$(node).find("span[name='gzzw']").parent().attr("title",data.gzzw);
				
				$(node).find("span[name='zt']").text(data.zt == 1?"有效":"无效");
				$(node).find("input[name='zt']").val(zt);
				
				$(node).find("span[name='isBj']").text(data.isBj == 1?"是":"否");
				$(node).find("input[name='isBj']").val(data.isBj);
				
				$(node).find("span[name='isMi']").text(data.isMi == 1?"是":"否");
				$(node).find("input[name='isMi']").val(data.isMi);
				
				$(node).find("span[name='gzlxStr']").text(data.gzlxStr);
				$(node).find("span[name='gzlxStr']").parent().attr("title",data.gzlxStr);
				$(node).find("input[name='gzlx']").val(data.gzlx);
				
				$(node).find("span[name='fjzchStr']").text(data.fjzchStr);
				$(node).find("span[name='fjzchStr']").parent().attr("title",data.fjzchStr);
				$(node).find("input[name='fjzch']").val(data.fjzch);
				
				$(node).find("span[name='bz']").text(data.bz);
				$(node).find("span[name='bz']").parent().attr("title",data.bz);
			}
		}
	})
}

/**
 * 打开新增工作内容窗口
 * @returns
 */
function openWorkContentWinAdd(nodeId){
	WorkContentModal.show({
		fjjx : fjjx,
		viewObj:null,//对象，在弹窗中回显
		dprtcode:$("#dprtcode").val(),
		callback: function(data){//回调函数
			if(data != null){
				
				if(!isExistBodyData(nodeId)){
					$("#"+nodeId).empty();
				}
				
				var option = "";
				option += "<button class='line6' onclick='removeCol(this,\"rotatable\")'>";
				option += "<i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
				option += "</button>";
				
				//data.display = data.zt==1?"":"display:none";
				data.display = "";
				data.option = option;
				data.orderNumber = orderNumber++;
				data.id = "";
				data.nbxh = "";
				data.djgk = "";
				addRow(data,nodeId,false);
			}
		}
	})
}

//获取工作内容参数
function getWorkContentParam(){
	var workContentParam = [];
	
	var len = $("#rotatable").find("tr").length;
	if (len == 1) {
		if($("#rotatable").find("td").length ==1){
			/*wcFlag = false;
			AlertUtil.showErrorMessage("请选择工作内容!");*/
			return workContentParam;
		}
	}
	
	if (len > 0){
		$("#rotatable").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var hiddenid =$("input[name='hiddenid']").eq(index).val(); //当前行，隐藏id的值
			var zjh =$("input[name='zjh']").eq(index).val(); //当前行，ATA章节号
			var xmly =$.trim($("span[name='xmly']").eq(index).text());
			var gzlxStr =$.trim($("span[name='gzlxStr']").eq(index).text());
			var gzlx =$.trim($("input[name='gzlx']").eq(index).val());
			var wz =$.trim($("span[name='wz']").eq(index).text());
			var scmsZw =$.trim($("span[name='scmsZw']").eq(index).text());
			var scmsYw =$.trim($("span[name='scmsYw']").eq(index).text());
			var jclx =$.trim($("input[name='jclx']").eq(index).val());
			var fjzch =$.trim($("input[name='fjzch']").eq(index).val());
			var gzzw =$.trim($("input[name='gzzw']").eq(index).val());
			var cksc =$.trim($("span[name='cksc']").eq(index).text());
			var ckgk =$.trim($("span[name='ckgk']").eq(index).text());
			var isBj =$.trim($("input[name='isBj']").eq(index).val());
			var isMi =$.trim($("input[name='isMi']").eq(index).val());
			var bz =$.trim($("span[name='bz']").eq(index).text());
			var zt =$.trim($("input[name='zt']").eq(index).val());

			infos.id = hiddenid;
			var fjzchValue = '';
			var zchArr = [];
			if(fjzch == '00000'){
				fjzchValue = fjzch;
			}else{
				var fjzchArr = fjzch.split(",");
				for(var i = 0 ; i < fjzchArr.length ; i++){
					var fixedCheckWorkPlane = {};
					fixedCheckWorkPlane.fjzch = fjzchArr[i];
					zchArr.push(fixedCheckWorkPlane);
				}
			}
			if(fjzchValue == '' && zchArr.length > 0){
				fjzchValue = '-';
			}
			infos.zjh = zjh;
			infos.xmly = xmly;
			infos.gzlx = gzlx;
			infos.wz = wz;
			infos.scmsZw = scmsZw;
			infos.scmsYw = scmsYw;
			infos.jclx = jclx;
			infos.fjzch = fjzchValue;
			infos.gzzw = gzzw;
			infos.cksc = cksc;
			infos.ckgk = ckgk;
			infos.isBj = isBj;
			infos.isMi = isMi;
			infos.bz  = bz;
			infos.zt  = zt;
			infos.fixedCheckWorkPlaneList = zchArr;
			workContentParam.push(infos);
		});
	}

	return workContentParam;
}

//通过维修方案编号获取机型
function getFjjxByWxfabh(){
	var wxfabh = $.trim($("#wxfabh").val());
	AjaxUtil.ajax({
 		url:basePath+"/project/maintenance/getFjjxByWxfabh",
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{wxfabh:wxfabh,dprtcode:$("#dprtcode").val()},
 		success:function(data){
	    	if(data != null && data != ''){
	    		fjjx = data;
	    	}
 		}
	}); 
}

//向table新增一行
function addRow(obj,nodeId,flag){

	var tr = "";
	
		tr += "<tr style='cursor:pointer;height:35px;' ondblclick=openWorkContentWinEdit(this)>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += obj.option;
		tr += "</td>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += "<span name='orderNumber'>"+obj.orderNumber+"</span>";
		tr += "<input name='nbxh' type='hidden' value='"+StringUtil.escapeStr(obj.nbxh)+"' class='form-control' readonly />";
		tr += "<input type='hidden' class='form-control' name='hiddenid' value='"+obj.id+"'/>";
		tr += "<input type='hidden' class='form-control' name='jclx' value='"+StringUtil.escapeStr(obj.jclx)+"'/>";
		tr += "<input type='hidden' class='form-control' name='gzzw' value='"+StringUtil.escapeStr(obj.gzzw)+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.zjhStr)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='zjhStr'>"+StringUtil.escapeStr(obj.zjhStr)+"</span>";
		tr += "<input type='hidden' class='form-control' name='zjh' value='"+StringUtil.escapeStr(obj.zjh)+"'/>";
		tr += "</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.xmly)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='xmly'>"+StringUtil.escapeStr(obj.xmly)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.gzlxStr)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='gzlxStr'>"+StringUtil.escapeStr(obj.gzlxStr)+"</span>";
		tr += "<input type='hidden' class='form-control' name='gzlx' value='"+StringUtil.escapeStr(obj.gzlx)+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.wz)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='wz'>"+StringUtil.escapeStr(obj.wz)+"</span>";
		tr += "</td>";
		tr += "<td title='"+StringUtil.escapeStr(obj.scmsZw)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='scmsZw'>"+StringUtil.escapeStr(obj.scmsZw)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.scmsYw)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='scmsYw'>"+StringUtil.escapeStr(obj.scmsYw)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.jclx)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='jclx'>"+StringUtil.escapeStr(obj.jclx)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.fjzchStr)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='fjzchStr'>"+StringUtil.escapeStr(obj.fjzchStr)+"</span>";
		tr += "<input type='hidden' class='form-control' name='fjzch' value='"+StringUtil.escapeStr(obj.fjzch)+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.gzzw)+"' class='wc' style='text-align:right;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='gzzw'>"+StringUtil.escapeStr(obj.gzzw)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.cksc)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='cksc'>"+StringUtil.escapeStr(obj.cksc)+"</span>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.ckgk)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='ckgk'>"+StringUtil.escapeStr(obj.ckgk)+"</span>";
		tr += "</td>";
		
		tr += "<td class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		if(flag){
			tr += formatWorkCard(obj.djgkList,obj.zt);
		}
		tr += "</td>";
		
		tr += "<td class='wc' style='text-align:center;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='isBj'>"+(obj.isBj == 1?"是":"否")+"</span>";
		tr += "<input type='hidden' class='form-control' name='isBj' value='"+StringUtil.escapeStr(obj.isBj)+"'/>";
		tr += "</td>";
		
		tr += "<td class='wc' style='text-align:center;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='isMi'>"+(obj.isMi == 1?"是":"否")+"</span>";
		tr += "<input type='hidden' class='form-control' name='isMi' value='"+StringUtil.escapeStr(obj.isMi)+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.bz)+"' class='wc' style='text-align:left;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='bz'>"+StringUtil.escapeStr(obj.bz)+"</span>";
		tr += "</td>";
		
		tr += "<td class='wc' style='text-align:center;vertical-align:middle;"+obj.display+"'>";
		tr += "<span name='zt'>"+(obj.zt == 1?"有效":"无效")+"</span>";
		tr += "<input type='hidden' class='form-control' name='zt' value='"+StringUtil.escapeStr(obj.zt)+"'/>";
		tr += "</td>";
		
		tr += "</tr>";
	
	$("#"+nodeId).append(tr);
}

//移除一行
function removeCol(e,nodeId){
	$(e).parent().parent().remove();
	orderNumber--;
	
	var len = $("#"+nodeId).find("tr").length;
	if (len >= 1){
		$("#"+nodeId).find("tr").each(function(){
			var index=$(this).index(); //当前行
			$("span[name='orderNumber']").eq(index).html(index+1);
		});
	}else{
		$("#"+nodeId).append("<tr style='height:35px;'><td colspan='18' class='text-align:center;vertical-align:middle;'>暂无数据 No data.</td></tr>");
	}
}

//判断是否存在数据
function isExistBodyData(nodeId){
	var len = $("#"+nodeId).find("tr").length;
	if (len == 0) {
		return false;
	}
	if (len == 1) {
		if($("#"+nodeId).find("td").length ==1){
			return false;
		}
	}
	return true;
}

function sumTotal(){
	var totle = 0;
	var jhgsRs = $("#jhgsRs").val();
	var jhgsXss = $("#jhgsXss").val();
	totle = jhgsRs*jhgsXss;
	if(totle == ''){
		totle = 0;
	}
	totle = totle.toFixed(2);
	$("#bzgs").val(totle);
	
}

//只能输入数字
function clearNoNumber(obj){
     //先把非数字的都替换掉，除了数字
     obj.value = obj.value.replace(/[^\d]/g,"");
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		obj.value = 0;
  	 }
     sumTotal();
}

//只能输入数字和小数,保留两位
function clearNoNumTwo(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     var str = obj.value.split(".");
     if(str.length > 1){
    	 if(str[1].length > 2){
    		 obj.value = str[0] +"." + str[1].substring(0,2);
    	 }
     }
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		 if(obj.value.substring(1,2) != "."){
  			obj.value = 0;
  		 }
  	 }
     sumTotal();
}

//只能输入数字和小数
function clearNoNum(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     sumTotal();
}

function formatWorkCard(djgkList,zt){
	var card = '';
	if(null != djgkList && djgkList.length > 0){
		$.each(djgkList,function(i,s){
			if(i == 1){
				card += "&nbsp;&nbsp;<i class='icon-caret-down cursor-pointer' onclick=vieworhideWorkCard(this)></i><div style='display:none'>";
			}
			card += "<a href='#' onclick=viewWorkCard('"+s.id+"') >"+StringUtil.escapeStr(s.gdbh)+"</a>"+" "+DicAndEnumUtil.getEnumName('jobCardStatusEnum',s.zt);
			if(i != 0 && i != djgkList.length - 1){
				card += "<br/>";
			}
			if(i == djgkList.length - 1){
				card += "</div>";
			}
		});
	}
	var display = "display:none";
	if(zt == 1){
		display = "";
	}
	card += "&nbsp;&nbsp;<i style='"+display+"' class='icon-plus color-blue cursor-pointer gk' onclick=addWorkCard(this) title='新增 Add' ></i>";
	return card;
}

//定检工卡显示或隐藏
function vieworhideWorkCard(obj) {
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

}

function addWorkCard(e){
	var id = $.trim($(e).parents("tr").find("input[name='hiddenid']").val());
	window.open(basePath+"/project/jobCard/intoAddJobCard?id="+id);
}

function viewWorkCard(id){
	window.open(basePath+"/project/jobCard/intoViewMainJobCard?id=" + id+"&dprtCode=" + $("#dprtcode").val()+"&t=" + new Date().getTime());
}

//返回前页面
function back(){
	 window.location.href =basePath+"/project/fixedcheckitem/main?wxfabh="+encodeURIComponent($.trim($("#wxfabh").val()))+"&pageParam="+pageParam;
}