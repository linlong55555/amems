$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	certificateMain.reload();
});

var certificateMain={
		pagination : {},
		gatherSearchParam:function(){
			var searchParam={};
			 searchParam.dprtcode=$("#dprtcode_search").val();
			 searchParam.keyword=$.trim($('#keyword_search').val());
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 _this.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = _this.pagination;
			 AjaxUtil.ajax({
				   url:basePath+"/aerialmaterial/certificate/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
			 			if(data.rows.length > 0){
			 			_this.appendContentHtml(data.rows);
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
						   signByKeyword($("#keyword_search").val(),[2,4,5,6,7]);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#list").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'quality_check_list_table'});
			 		}
			 });
		},
		reload:function(){
			TableUtil.resetTableSorting("main_list_table");
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			$.each(list,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td class='text-center fixed-column'>";
				htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' " +
		    	"permissioncode='aerialmaterial:certificate:main:01' onClick=\"edit('"+row.bjh+"','"+(row.paramsMap?(row.paramsMap.xlh?row.paramsMap.xlh:''):'')+"','"+row.dprtcode+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";
				htmlContent += "</td>";
			    htmlContent += "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>"; 
			    htmlContent += "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.xingh)+"'>"+StringUtil.escapeStr(row.xingh)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap?StringUtil.escapeStr(row.paramsMap.xlh):'')+"'>"+StringUtil.escapeStr(row.paramsMap?StringUtil.escapeStr(row.paramsMap.xlh):'')+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap?StringUtil.escapeStr(row.paramsMap.pch):'')+"'>"+StringUtil.escapeStr(row.paramsMap?StringUtil.escapeStr(row.paramsMap.pch):'')+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jhly)+"'>"+StringUtil.escapeStr(row.jhly)+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"'>"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"</td>";
			    htmlContent += "<td title='证书列表 Certificate List' style='text-align:center;vertical-align:middle;'>";
				if(row.paramsMap.certificatecount && row.paramsMap.certificatecount > 0){
					htmlContent += '<i pch="'+StringUtil.escapeStr(row.paramsMap?row.paramsMap.pch:'')+'" bjh="'+StringUtil.escapeStr(row.bjh)+'" xlh="'+StringUtil.escapeStr(row.paramsMap?row.paramsMap.xlh:'')+'" dprtcode="'+StringUtil.escapeStr(row.dprtcode)+'" class="certificate-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
			    htmlContent += "</td></tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission(); 
		 });
		_this.initWebuiPopover();
		},
		initWebuiPopover: function(){//初始化
			var this_=this;
			WebuiPopoverUtil.initWebuiPopover('certificate-view','body',function(obj){
				return this_.getHistoryCertificateList(obj);
			},700);
			$("#structrucRepair_table").scroll(function(){
				$('.certificate-view').webuiPopover('hide');
			});
		},
		getHistoryCertificateList: function(obj){//获取历史附件列表
			var xlh = $(obj).attr('xlh');
			var pch = $(obj).attr('pch');
			if(xlh){
				pch = "-";
			}else{
				xlh = "-";
				pch = pch || "-";
			}
			var jsonData ={jh : $(obj).attr('bjh'),xlh : xlh, pch : pch,dprtcode : $(obj).attr('dprtcode') };
		    
			return history_certificate_alert_Modal.getHistoryCertificateList(jsonData);
		},
		
		
		//打开证书信息窗口
		 openEOStationWin:function(obj){
			var bjh = $(obj).attr('bjh');
			var xlh = $(obj).attr('xlh');
			var dprtcode = $(obj).attr('dprtcode');
			var this_ = this;
			history_certificate_alert_Modal.show({
				dprtcode:dprtcode,//默认登录人当前机构代码
				jh :bjh,
				xlh :xlh,
				callback : function(data) {//回调函数
					positionPublicList = [];
					fjzwList = data; 
					var str = '';
					if (data != null && data.length > 0) {
						$.each(data, function(index, row) {
							positionPublicList.push(row);
							str += formatUndefine(row.sz) + ",";
						});
					}
					if (str != '') {
						str = str.substring(0, str.length - 1);
					}
					$("#efjzw_public").html(str);
				}
			});
		}
};

function dprtChange (){
	certificateMain.reload();
}

function edit(bjh,xlh,dprtcode){
	hCMainData = {
			'bjh':bjh,
			'dprtcode':dprtcode
	}
	hCMainData.paramsMap = {
			'xlh':xlh
	};
	selectById(hCMainData,function(obj){
		CertificateModal.show({
				modalHeadChina : '编辑证书',
				modalHeadEnglish : 'Edit',
				viewObj:obj,
				dprtcode:dprtcode,//机构代码
				callback : function(data) {//回调函数
				if (data != null) {
					var message = '保存成功!';
					var url = basePath+"/aerialmaterial/certificate/updateCertificate";
					performAction(url, data, message);
				}
			}
		});
	});
}

function performAction(url, param, message){
	var this_ = this;
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage(message);
			$("#CertificateModal").modal("hide");
			certificateMain.goPage(certificateMain.pagination.page, certificateMain.pagination.sort, certificateMain.pagination.order);
		}
	});
}
function selectById(hCMainData,obj){
	AjaxUtil.ajax({
		async: false,
		 url:basePath+"/aerialmaterial/certificate/selectByBjhAndXlh",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(hCMainData),
		dataType:"json",
		success:function(data){
			if(data != null){
				if(typeof(obj)=="function"){
					obj(data.row); 
				}
			}
		}
	});
}
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
		certificateMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}