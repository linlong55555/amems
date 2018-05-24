$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		audit_main.init();
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
	})
	
	function applyOpen(){
		$("#apply_open_alert").modal("show");
	}
	
var audit_main = {
		id:'',
		bfdh:'',
		init:function(){
			this.decodePageParam();
		},
		decodePageParam:function(){
			var this_ = this;
			try {
				var decodeStr = Base64.decode(pageParam);
				var pageParamJson = JSON.parse(decodeStr);
				var params = pageParamJson.params;			
				if (pageParamJson.pagination) {
					this_.goPage(pageParamJson.pagination.page,
							pageParamJson.pagination.sort,
							pageParamJson.pagination.order);
				} else {
					this_.goPage(1, "auto", "desc");
				}
			} catch (e) {
				this_.goPage(1, "auto", "desc");
			}
		},
		goPage:function(pageNumber, sortType, sequence){
			this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
		},
		AjaxGetDatasWithSearch : function(pageNumber, sortType, sequence) {
			var obj = {};
			var this_ = this;
			obj = this_.gatherSearchParam();
			pagination = {
				page : pageNumber,
				sort : sortType,
				order : sequence,
				rows : 20
			};
			obj.pagination = pagination;
			if (id != "") {
				obj.id = id;
				id = "";
			}
			startWait();
			AjaxUtil.ajax({
				url : basePath + "/material/scrapped/audit/getScrappedApplyList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						this_.appendContentHtml(data.rows);
						$("#audit_main_reject").show();
						$("#audit_main_pass").show();
					} else {
						$("#auditList").empty();
						$("#auditList").append("<tr class='text-center'><td colspan=\"9\">暂无数据 No data.</td></tr>");
						$("#detailList").empty();
						$("#pagination_list").empty();
						$("#detailList").append("<tr class='text-center'><td colspan=\"9\">暂无数据 No data.</td></tr>");
						$("#processList").empty();
						$("#processList").append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
						$("#attachmentList").empty();
						$("#attachmentList").html("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
						$("#audit_main_reject").hide();
						$("#audit_main_pass").hide();
					}
					new Sticky({
						tableId : 'packing_list_table'
					});
				}
			});
		
		},
		gatherSearchParam : function() {
			var searchParam = {};
			var zt =2 ;
		    searchParam.zt = zt;	
			return searchParam;
		},
		appendContentHtml:function(list) {
			var htmlContent = '';
			var this_ = this;
			$.each(list,function(index, row) {
				htmlContent += "<tr bfdh='"+row.bfdh+"' dprtcode='"+row.dprtcode+"' id='"+row.id+"' onclick=\"audit_main.showHiddenContent(this)\">";
				htmlContent += "<td style='vertical-align:middle;' class='text-center colwidth-3'>";
				if(index == 0){
					htmlContent += "<input type='radio' name='checkRadio' checked='checked' /></td>"
						this_.showHiddenContent(this);
				}else{
					htmlContent += "<input type='radio' name='checkRadio' /></td>"
				}
				htmlContent += "<td class='colwidth-9 text-left'><p class='margin-bottom-0 double-p-style' title='"+(row.bfrq?row.bfrq.substring(0,10):"")+"'>"+(row.bfrq?row.bfrq.substring(0,10):"")+"</p>";
				htmlContent += "<p class='margin-bottom-0 double-p-style' title='"+StringUtil.escapeStr(row.sqr?(row.sqr.username+" "+row.sqr.realname):"")+"'>"+StringUtil.escapeStr(row.sqr?(row.sqr.username+" "+row.sqr.realname):"")+"</p></td>";
				htmlContent += "<td class='colwidth-15'><p class='margin-bottom-0 text-right double-p-style' title='"+StringUtil.escapeStr(row.bfdh)+"' >"+StringUtil.escapeStr(row.bfdh)+"</p>";
				htmlContent += "<p class='margin-bottom-0 text-right double-p-style' title='"+StringUtil.escapeStr(row.sm)+"'>"+StringUtil.escapeStr(row.sm)+"</p></td>";
				htmlContent += "</tr>";
			});
			$("#auditList").empty();
			$("#auditList").html(htmlContent);
			refreshPermission();
		},
		showHiddenContent :function(e){
			var this_ = this ;
			$("#keyword_search").val("");
			$(e).find("input[type='radio']").prop("checked",true);
		 	var id = $(e).attr("id");
		 	var bfdh = $(e).attr("bfdh");
		 	this_.id = id ;
		 	this_.bfdh = bfdh;
		 	var dprtcode = $(e).attr("dprtcode");
			this_.goPageDetail(1, "auto", "desc");//加载报废部件信息
			this_.loadAttachment(id);//加载附件
			this_.loadProcess(id,dprtcode);//加载流程信息
		},
		goPageDetail:function(pageNumber, sortType, sequence){
			this.loadDetail(pageNumber, sortType, sequence);
		},
		loadDetail:function(pageNumber, sortType, sequence){
			var obj = {};
			var this_  = this;
			obj.id = this_.id;
			pagination = {
				page : pageNumber,
				sort : sortType,
				order : sequence,
				rows : 10
			};
			obj.pagination = pagination;
			obj.keyword = $.trim($("#keyword_search").val());
			AjaxUtil.ajax({
				url : basePath + "/material/scrapped/apply/getScrappedDetailList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						this_.appendDetail(data.rows);
						var page_ = new Pagination({
							renderTo : "pagination_list",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPageDetail : function(a, b, c) {
								loadDetail(a, b, c);
							}

						});
						signByKeyword($.trim($("#keyword_search").val()), [ 3,4,5 ],"#detailList tr td");
					} else {
						$("#detailList").empty();
						$("#pagination_list").empty();
						$("#detailList").append("<tr class='text-center'><td colspan=\"9\">暂无数据 No data.</td></tr>");
					}
				}
			});
		},		
		appendDetail :function(list){
			var htmlContent = '';
			$.each(list,function(index,row){
				htmlContent += "<tr id='"+row.KCID+"'>";
				htmlContent +=" <td style='vertical-align:middle;' class='text-center'>"+(index+1)+"</td>";
				htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.HCLX)+"</td>";
				htmlContent += "<td  style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BJH)+"\n"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.BJH)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"</p></td>";//部件名称
				htmlContent += "<td style='vertical-align:middle;' title='"+StringUtil.escapeStr(row.PCH)+"\n"+StringUtil.escapeStr(row.SN)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.PCH)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.SN)+"</p></td>"; 
				htmlContent += "<td style='vertical-align:middle;' title='"+StringUtil.escapeStr(row.GG)+"\n"+StringUtil.escapeStr(row.XH)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.GG)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.XH)+"</p></td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"\n"+StringUtil.escapeStr(formatUndefine(row.KWH))+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.CKH))+" "+StringUtil.escapeStr(formatUndefine(row.CKMC))+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(formatUndefine(row.KWH))+"</p></td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"'>"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"</td>"; 	
				var zcb = '';
				var zjz = '';
				if(formatUndefine(row.ZCB) != ''){
					zcb = formatUndefine(row.ZCB).toFixed(2)+" "+formatUndefine(row.BIZ);
				}
				if(formatUndefine(row.ZJZ) != ''){
					zjz = formatUndefine(row.ZJZ).toFixed(2)+" "+formatUndefine(row.JZBZ);
				}
			 
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' name='kcjz' title='"+formatUndefine(zcb)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(zcb)+"</p></td>";   
			    if(formatUndefine(row.HJSM) =='' && formatUndefine(row.SYTS)==''){
					htmlContent += "<td style='vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"' >"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</td>";
				}else{
					htmlContent += "<td style='vertical-align:middle;' title='"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"\n"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(row.RKRQ?row.RKRQ.substring(0,10):"")+"</p><p  class='margin-bottom-0 '>"+formatUndefine(row.HJSM).substring(0,10)+"/"+formatUndefine(row.SYTS)+"</p></td>";
				}
				htmlContent += "</tr>";  
			    
			});
			$("#detailList").empty();
			$("#detailList").append(htmlContent);
		},
		loadAttachment:function(id){
			var this_ =this;
			var obj = {};
			obj.manid = id;
			var mainidList = [];
			mainidList.push(id);
			AjaxUtil.ajax({
				url:basePath + "/common/loadPlaneDataAttachmentsByMainIds",
				type:"post",
				async : false,
				data:{
					idList:mainidList
				},
				dataType:"json",
				success:function(data){
					if(data.success){
						this_.appendAttachmentContentHtml(data);
					}
				}
			});
		},
		 loadProcess:function(id,dprtcode){
			var id = id;
			var dprtcode = dprtcode;
			var data ={};
			data.mainid = id;
			data.dprtcode = dprtcode;
			var this_ = this;
			AjaxUtil.ajax({
				url : basePath + "/quality/processrecord/list",
				type : "post",
				data : JSON.stringify(data),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				async : false,
				success : function(data) {
					if(data.length>0){
						this_.appendProcessContentHtml(data);
					}else{
						$("#processList").empty();
						$("#processList").append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
					}
				}
			});
		},
		appendProcessContentHtml : function(list){
			var htmlContent = '';
			var this_ = this;
	    	$.each(list,function(index, row) {
		    	htmlContent += "<tr>";		
				htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.czrmc)+"' >"+ StringUtil.escapeStr(formatUndefine(row.czrmc))+ "</td>";		
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.czsj)+"'>"+ StringUtil.escapeStr(row.czsj) + "</td>";
				htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.czsm)+ "'>" + StringUtil.escapeStr(row.czsm)+ "</td>";
				htmlContent += "</tr>";
	    	});
	    	$("#processList").empty();
	    	$("#processList").html(htmlContent);
	    },
		 appendAttachmentContentHtml:function(data){
			var this_ = this;
			var content = '';
			var list = data.attachments;
			if(list.length>0){
				$.each(data.attachments,function(i,row){
					var fj = StringUtil.escapeStr(row.wbwjm);
					if(StringUtil.escapeStr(row.hzm) != ''){
						fj += "."+StringUtil.escapeStr(row.hzm);
					}
					content += "<tr>";
					content += '<td title="'+fj+'" class="text-left">';
					content += "<a href='javascript:void(0);' onclick=audit_main.downloadfile('"+row.id+"') >"+fj+"</a>";
					content += '</td>';
					content += '<td>'+row.wjdxStr+'</td>';
					content += "</tr>"; 
				})
				$("#attachmentList").empty();
				$("#attachmentList").html(content);
			}else{
				$("#attachmentList").empty();
				$("#attachmentList").html("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
			}
		},
		downloadfile:function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		},
		searchRevision:function() {
			this.goPageDetail(1, "auto", "desc");
		},
		 reject:function(){
			 var this_= this;
			audit_modal.show({
				id:this.id,
				bfdh:this.bfdh,
				titleName:"审核驳回",
				titleEname:"Reject",
				type:false,
				callback:function(data){
					var obj = data;
					obj.zt = 5;
					this_.doRequest(obj,"审核驳回成功!",basePath + "/material/scrapped/audit/audit","audit_modal");
				}
			})
		},
		pass:function(){
			var this_ = this;
			audit_modal.show({
				id:this_.id,
				bfdh:this_.bfdh,
				titleName:"审核通过",
				titleEname:"Pass",
				type:true,
				callback:function(data){
					var obj = data;
					obj.zt = 3;
					this_.doRequest(obj,"审核通过成功!",basePath + "/material/scrapped/audit/audit","audit_modal");
				}
			})
		},
		doRequest:function(data,message,url,modal){
			var this_ = this;
			startWait($("#"+modal));
			AjaxUtil.ajax({
				url : url,
				type : "post",
				data : JSON.stringify(data),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				async : false,
				modal : $("#"+modal),
				success : function(data) {
					id = data;				
					pageParam=this_.encodePageParam();
					AlertUtil.showMessage(message, this_.decodePageParam());
					$("#"+modal).modal("hide");
					finishWait($("#"+modal));
					refreshPermission();
				}
			});
		},
		 encodePageParam:function() {
			var pageParam = {};
			var params = {};
			params.keyword = $.trim($("#keyword_search").val());
			params.zzjg = $.trim($("#zzjg").val());
			pageParam.params = params;
			pageParam.pagination = pagination;
			return Base64.encode(JSON.stringify(pageParam));
		},
		orderBy:function(obj) {
			var this_ = this;
			var orderStyle = $("#" + obj + "_order").attr("class");
			$("th[id$=_order]").each(
					function() { // 重置class
						$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
					});
			$("#" + obj + "_" + "order").removeClass("sorting");
			if (orderStyle.indexOf("sorting_asc") >= 0) {
				$("#" + obj + "_" + "order").addClass("sorting_desc");
			} else {
				$("#" + obj + "_" + "order").addClass("sorting_asc");
			}
			orderStyle = $("#" + obj + "_order").attr("class");
			var currentPage = $("#pagination_list li[class='active']").text();
			this_.goPageDetail(currentPage, obj, orderStyle.split("_")[1]);
		}
		
}
	//删除一行
	
	 function removeTr(obj){
		 $(obj).parent().parent().remove();
	 }
	 
	 function customResizeHeight(bodyHeight, tableHeight){
		 var panelHeadingHeight = $('.panel-heading').outerHeight();
		 if(panelHeadingHeight<35){
			 bodyHeight = bodyHeight + panelHeadingHeight - 35;
		 }
		 var leftFirstBody=(bodyHeight)*0.4;
		 $(".left_first_body").attr('style', 'height:' + leftFirstBody+ 'px !important;overflow-x: auto;');
		 var leftFirstContent=$(".left_first_content").outerHeight();
		 var rightHeight=$("#right_div").outerHeight();
		 var leftSecondContent=rightHeight-leftFirstContent-20;
		
		 $(".left_second_content").attr('style', 'height:' + leftSecondContent+ 'px !important;overflow: auto;border:1px solid #d5d5d5;');
		 $(".toggle-btn-style").css("top",rightHeight/2+"px");
		}
	 function toggleBtnHandle(obj){
		 var i = $(obj);
			if(i.hasClass("icon-caret-left")){
				i.removeClass("icon-caret-left").addClass("icon-caret-right");
				$("#left_div").hide();
				$("#right_div").removeClass("col-sm-9").addClass("col-sm-12");
			}else{
				i.removeClass("icon-caret-right").addClass("icon-caret-left");
				$("#left_div").show();
				$("#right_div").removeClass("col-sm-12").addClass("col-sm-9");
			}
	 }
	 
	