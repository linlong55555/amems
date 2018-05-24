$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
	})
	
	function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
    }

    function moreSearchDestory(){
    	if ($("#divSearchDestory").css("display") == "none") {
    		$("#divSearchDestory").css("display", "block");
    		App.resizeHeight();
    		$("#iconDestory").removeClass("icon-caret-down");
    		$("#iconDestory").addClass("icon-caret-up");
    	} else {
    		$("#divSearchDestory").css("display", "none");
    		App.resizeHeight();
    		$("#iconDestory").removeClass("icon-caret-up");
    		$("#iconDestory").addClass("icon-caret-down");
    	}
    }
	
	function customResizeHeight(bodyHeight, tableHeight){
		 var tabHead=$(".tab-only ul.nav-tabs ").outerHeight()||0;
		 var tabRowHeight=$(".tab-only .tab-content .row-height:visible").outerHeight()||0;
		 var paginationHeight=$(".tab-only .page-height:visible").outerHeight()||0;
		 var tabTableHeight=bodyHeight-tabHead-tabRowHeight-paginationHeight-20;
		 $(".tab-only .tab-content .tab-pane:visible").find("table").parent("div").attr("style","height:"+tabTableHeight+"px;overflow:auto;");	
	}
	
	var destroy_main = {
			id:'',
			bfdh:'',
			data:[],
			init:function(){
				this.inithcly();
				this.decodePageParam();
			},
			//初始化航材来源
			changedprtcode: function(){
				this.inithcly();
			},
			//初始化航材来源
			inithcly: function(){
				$("#destroy_hcly").empty();
				$("#destroy_hcly").html("<option value=''>显示全部</option>");
				DicAndEnumUtil.registerDic('85','destroy_hcly',$("#destroy_zzjg").val());
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
					url : basePath + "/material/destroy/airmaterial/getDestroyList",
					type : "post",
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					data : JSON.stringify(obj),
					success : function(data) {
						finishWait();
						if (data.total > 0) {
							this_.appendContentHtml(data.rows);
							this_.data = [];
							this_.data = data.rows;
							var page_ = new Pagination({
								renderTo : "destroy_pagination_list",
								data : data,
								sortColumn : sortType,
								orderType : sequence,
								extParams : {},
								goPage : function(a, b, c) {
									AjaxGetDatasWithSearch(a, b, c);
								}

							});
							// 标记关键字
							signByKeyword($("#destroy_keyword_search").val(), [ 3,4,5,6,9, 10 ],"#destoryList tr td");
						} else {
							$("#destoryList").empty();
							$("#destroy_pagination_list").empty();
							$("#destoryList").append("<tr class='text-center'><td colspan=\"14\">暂无数据 No data.</td></tr>");
						}
						new Sticky({
							tableId : 'destroy_list_table'
						});
					}
				});
			
			},
			gatherSearchParam : function() {
				var searchParam = {};
				searchParam.keyword = $.trim($("#destroy_keyword_search").val());
				searchParam.dprtcode = $("#destroy_zzjg").val();
				var hcly = $("#destroy_hcly").val();
				var paramsMap= {};
				var bfrq = $.trim($("#destroy_bfrq_search").val());
				if (null != bfrq && "" != bfrq) {
					var bfrqBegin = bfrq.substring(0, 10) + " 00:00:00";
					var bfrqEnd = bfrq.substring(13, 23) + " 23:59:59";
					paramsMap.bfrqBegin = bfrqBegin;
					paramsMap.bfrqEnd = bfrqEnd;
				}
				var xhsj = $("#xhrq_search").val();
				if (null != xhsj && "" != xhsj) {
					var xhsjBegin = xhsj.substring(0, 10) + " 00:00:00";
					var xhsjEnd = xhsj.substring(13, 23) + " 23:59:59";
					paramsMap.xhsjBegin = xhsjBegin;
					paramsMap.xhsjEnd = xhsjEnd;
				}
				paramsMap.hcly = hcly;
				paramsMap.tool = "1";
			    searchParam.paramsMap = paramsMap;	
				return searchParam;
			},
			searchreset:function(){
				$("#toDestroy_keyword_search").val("");
				$("#toDestroy_bfrq_search").val("");
				$("#toDestroy_hcly").val("");
				$("#toDestroy_zzjg").val(userJgdm);
				this.performSelectClear();
			},
			appendContentHtml:function(list) {
				var htmlContent = '';
				var this_ = this;
				$.each(list,function(index, row) {
					htmlContent += "<tr onclick='destroy_main.clickRow("+index+",this)'>";
					htmlContent += "<td class='text-center fixed-column'>";		
					htmlContent += "<input type='checkbox' name='checkrow' index='"+index+"' onclick=\"destroy_main.checkRow("+index+",this)\" />";
					htmlContent += "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.WZ)+"' >"+ StringUtil.escapeStr(row.WZ) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.BJH)+"'>"+ StringUtil.escapeStr(row.BJH) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"'>"+ StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.PCH)+"'>"+ StringUtil.escapeStr(row.PCH) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.SN)+"'>"+ StringUtil.escapeStr(row.SN) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.HCLY)+"'>"+ formatUndefine(row.HCLY) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"'>"+ StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.SM)+"'>"+ StringUtil.escapeStr(row.SM) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.BFDH)+"'><a href='#' onClick=\"destroy_main.viewApply('"+ row.BFID + "',event)\">"+ StringUtil.escapeStr(row.BFDH) + "</a></td>";
					htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.BFRQ)+"'>"+ StringUtil.escapeStr(row.BFRQ) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.SQR)+"'>"+ StringUtil.escapeStr(row.SQR) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.XHR)+"'>"+ StringUtil.escapeStr(row.XHR) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.XHSJ)+"'>"+ StringUtil.escapeStr(row.XHSJ) + "</td>";
					htmlContent += "</tr>";
				});
				$("#destoryList").empty();
				$("#destoryList").html(htmlContent);
				refreshPermission();
			},
			searchRevision:function() {
				this.goPage(1, "auto", "desc");
			},
			changeHcly:function(){
				this.searchRevision();
			},
			viewApply:function(id,e){
				e = e || window.event;  
			    if(e.stopPropagation) { //W3C阻止冒泡方法  
			        e.stopPropagation();  
			    } else {  
			        e.cancelBubble = true; //IE阻止冒泡方法  
			    }
				window.open(basePath + "/material/scrapped/apply/view?id=" + id);
			},
			encodePageParam:function() {
				var pageParam = {};
				var params = {};
				params.keyword = $.trim($("#destroy_keyword_search").val());
				params.zzjg = $.trim($("#destroy_zzjg").val());
				pageParam.params = params;
				pageParam.pagination = pagination;
				return Base64.encode(JSON.stringify(pageParam));
			},
			checkRow:function(index,this_){
				var flag = $(this_).is(":checked");
				if(flag){
					$(this_).removeAttr("checked");
				}else{
					$(this_).attr("checked",true);
				}
			},
			performSelectClear:function(){
				$("#destoryList :checkbox[name=checkrow]").attr("checked", false);
			},
			performSelectAll:function(){
				$("#destoryList :checkbox[name=checkrow]").attr("checked", true);
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
				var currentPage = $("#destroy_pagination_list li[class='active']").text();
				var o = obj.split("D_")[1];
				this_.goPage(currentPage, o, orderStyle.split("_")[1]);
			},
			clickRow:function(index,this_){
				var $checkbox1 = $("#destoryList :checkbox[name='checkrow']:eq("+index+")");
				var $checkbox2 = $(" .sticky-col :checkbox[name='checkrow']:eq("+index+")");
				var checked = $checkbox1.is(":checked");
				$checkbox1.attr("checked", !checked);
				$checkbox2.attr("checked", !checked);
				new Sticky({tableId:'destroy_list_table'}); //初始化表头浮动
			},
			revoke:function(){
				var idList = [];
				var this_ = this;
				$("#destoryList").find("tr input:checked").each(function(){
					var index = $(this).attr("index");
					var data = this_.data[index];
					idList.push(data.ID);
				});
				if(idList.length >0){
					this_.tip('确定撤销选中数据吗？',idList,basePath + "/material/destroy/airmaterial/revoke","撤销成功!");
				}else{
					AlertUtil.showMessage("请选中数据后再进行操作！");
					return false;
				}
			},

			tip:function(tipmessage,param,url,message){
				var this_ =this;
				AlertUtil.showConfirmMessage(tipmessage, {
					callback : function() {
							this_.doRequest(param, url, message);
					}
				});
			},
			doRequest:function(data,url,message){
				var this_ = this;
				AjaxUtil.ajax({
					url : url,
					type : "post",
					data : JSON.stringify(data),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					async : false,
					success : function(data) {
						pageParam=this_.encodePageParam();
						AlertUtil.showMessage(message, this_.decodePageParam());
						refreshPermission();
					}
				});
			},
			exportExcel:function(){
				var param = this.gatherSearchParam();
				param.pagination = {page:1,sort:"auto",order:"desc",rows:10000};
				window.open(basePath+"/material/destroy/airmaterial/export/2/ToolDestroyed.xls?paramjson="+JSON.stringify(param));
			}
	}