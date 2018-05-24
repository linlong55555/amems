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
		toDestroy_main.init();
		$("#destroyli a").on("shown.bs.tab",function(){
			$("#toDestroy_keyword_search").val("");
			toDestroy_main.init();
		})
		$("#revoke a").on("shown.bs.tab",function(){
			$("#destroy_keyword_search").val("");
			destroy_main.init();
		})
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
	
	var toDestroy_main = {
			id:'',
			bfdh:'',
			data:[],
			init:function(){
				this.decodePageParam();
				this.inithcly();
			},
			//初始化航材来源
			inithcly: function(){
				$("#toDestroy_hcly").empty();
				$("#toDestroy_hcly").html("<option value=''>显示全部</option>");
				DicAndEnumUtil.registerDic('85','toDestroy_hcly',$("#toDestroy_zzjg").val());
			},
			//初始化航材来源
			changedprtcode: function(){
				this.inithcly();
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
					url : basePath + "/material/destroy/airmaterial/getToDestroyList",
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
								renderTo : "pagination_list",
								data : data,
								sortColumn : sortType,
								orderType : sequence,
								extParams : {},
								goPage : function(a, b, c) {
									AjaxGetDatasWithSearch(a, b, c);
								}

							});
							// 标记关键字
							signByKeyword($("#toDestroy_keyword_search").val(), [3, 4,5,6,9, 10 ],"#toDestroyList tr td");
						} else {
							$("#toDestroyList").empty();
							$("#pagination_list").empty();
							$("#toDestroyList").append("<tr class='text-center'><td colspan=\"12\">暂无数据 No data.</td></tr>");
						}
						new Sticky({
							tableId : 'packing_list_table'
						});
					}
				});
			
			},
			gatherSearchParam : function() {
				var searchParam = {};
				searchParam.keyword = $.trim($("#toDestroy_keyword_search").val());
				searchParam.dprtcode = $("#toDestroy_zzjg").val();
				var hcly = $("#toDestroy_hcly").val();
				var paramsMap= {};
				var bfrq = $.trim($("#toDestroy_bfrq_search").val());
				if (null != bfrq && "" != bfrq) {
					var bfrqBegin = bfrq.substring(0, 10) + " 00:00:00";
					var bfrqEnd = bfrq.substring(13, 23) + " 23:59:59";
					paramsMap.bfrqBegin = bfrqBegin;
					paramsMap.bfrqEnd = bfrqEnd;
				}
				paramsMap.hcly = hcly;
				paramsMap.tool = "1";
			    searchParam.paramsMap = paramsMap;	
				return searchParam;
			},
			appendContentHtml:function(list) {
				var htmlContent = '';
				var this_ = this;
				$.each(list,function(index, row) {
					htmlContent += "<tr onclick='toDestroy_main.clickRow("+index+",this)'>";
					htmlContent += "<td class='text-center fixed-column'>";		
					htmlContent += "<input type='checkbox' name='checkrow' index='"+index+"' onclick=\"toDestroy_main.checkRow("+index+",this)\" />";
					htmlContent += "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.WZ)+"' >"+ StringUtil.escapeStr(row.WZ) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.BJH)+"'>"+ StringUtil.escapeStr(row.BJH) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"'>"+ StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.PCH)+"'>"+ StringUtil.escapeStr(row.PCH) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.SN)+"'>"+ StringUtil.escapeStr(row.SN) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.HCLY)+"'>"+ formatUndefine(row.HCLY) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW)+"'>"+ StringUtil.escapeStr(row.BFSL)+StringUtil.escapeStr(row.JLDW) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.SM)+"'>"+ StringUtil.escapeStr(row.SM) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.BFDH)+"'><a href='#' onClick=\"toDestroy_main.viewApply('"+ row.BFID + "',event)\">"+ StringUtil.escapeStr(row.BFDH) + "</a></td>";
					htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.BFRQ)+"'>"+ StringUtil.escapeStr(row.BFRQ) + "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.SQR)+"'>"+ StringUtil.escapeStr(row.SQR) + "</td>";
					htmlContent += "</tr>";
				});
				$("#toDestroyList").empty();
				$("#toDestroyList").html(htmlContent);
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
			searchreset:function(){
				$("#toDestroy_keyword_search").val("");
				$("#toDestroy_bfrq_search").val("");
				$("#toDestroy_hcly").val("");
				$("#toDestroy_zzjg").val(userJgdm);
				this.performSelectClear();
			},
			encodePageParam:function() {
				var pageParam = {};
				var params = {};
				params.keyword = $.trim($("#toDestroy_keyword_search").val());
				params.zzjg = $.trim($("#toDestroy_zzjg").val());
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
				this_.goPage(currentPage, obj, orderStyle.split("_")[1]);
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
				$("#toDestroyList :checkbox[name=checkrow]").attr("checked", false);
			},
			performSelectAll:function(){
				$("#toDestroyList :checkbox[name=checkrow]").attr("checked", true);
			},
			clickRow:function(index,this_){
				var $checkbox1 = $("#toDestroyList :checkbox[name='checkrow']:eq("+index+")");
				var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
				var checked = $checkbox1.is(":checked");
				$checkbox1.attr("checked", !checked);
				$checkbox2.attr("checked", !checked);
				new Sticky({tableId:'packing_list_table'}); //初始化表头浮动
			},
			destroy:function(){
				var idList = [];
				var this_ = this;
				$("#toDestroyList").find("tr input:checked").each(function(){
					var index = $(this).attr("index");
					var data = this_.data[index];
					idList.push(data.ID);
				});
				if(idList.length >0){
					this_.tip('确定销毁选中数据吗？',idList,basePath + "/material/destroy/airmaterial/destroy","销毁成功!");
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
				window.open(basePath+"/material/destroy/airmaterial/export/1/ToolDestroy.xls?paramjson="+JSON.stringify(param));
			}
	}