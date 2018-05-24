$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		transfer_main.init();
	});
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

	//移库操作
	function transferOpen(){
		var dprtcode = $("#dprtcode_search").val();
		var storeIds = [];
		//获取选择的数据id
		$("#transfer_main_tbody").find("tr input:checked").each(function(){
			storeIds.push($(this).val());
		});
		if(storeIds == null || storeIds.length <= 0 ){
			AlertUtil.showErrorMessage("请选择至少一条数据");
			return false;
		}
		transfer_open.init({
			storeIds:storeIds,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				
			}
		});
	}
	
	//切换组织机构
	function dprtChange(){
		//获取仓库
		transfer_main.initCk();//仓库
		transfer_main.initKw();//库位
	}
	//删除一行
	function deletRow(obj){
		$(obj).parent().parent("tr").remove();
	}
	
	var transfer_main = {
			id:'transfer_main',
			tableDivId : 'transfer_main_top_div',
			tableId : 'transfer_main_table',
			tbodyId : 'transfer_main_tbody',
			paginationId:'transfer_main_Pagination',
			operationId : '',
			pagination : {},
			currentDate : '',
			param: {
				fjzch : '',
				dprtcode:userJgdm//默认登录人当前机构代码
			},
			init : function(param){
				var this_ = this;
				//航材类型下拉
				hclxSelect = '<option value="" selected="true">显示全部All</option>';
				if($("#hclxType").val() == 1){
					hclxSelect += '<option value="1" >航材</option>';
					hclxSelect += '<option value="4" >化工品</option>';
					hclxSelect += '<option value="5" >低值易耗品</option>';
					hclxSelect += '<option value="6" >松散件</option>';
					hclxSelect += '<option value="0" >其他</option>';
				}else{
					hclxSelect += '<option value="21" >工具</option>';
					hclxSelect += '<option value="22" >设备</option>';
				}
				//获取仓库
				this_.initCk();
				this_.initqczt();
				//获取库位
				//this_.changeKw();
				$("#wllb_search").empty();
				$("#wllb_search").append(hclxSelect);
				if(param){
					$.extend(this.param, param);
				}
				//重置排序图标
				TableUtil.resetTableSorting(this.tableDivId);
				this.load(1,"auto","desc");
			},
			//初始化器材状态
			initqczt: function(){
				var this_ = this;
				$("#qczt_search").html("<option value=''>显示全部All</option>");
				DicAndEnumUtil.registerDic('86',"qczt_search",$("#dprtcode_search").val());
			},
			//搜索条件重置
			searchreset : function(){
				var this_ = this;
				 $("#divSearch", $("#"+this_.id)).find("input").each(function() {
					$(this).attr("value", "");
				});
				
				 $("#divSearch", $("#"+this_.id)).find("textarea").each(function(){
					 $(this).val("");
				 });
				 $("#divSearch", $("#"+this_.id)).find("select").each(function(){
						$(this).val("");
					});
				 $("#keyword_search", $("#"+this_.id)).val("");
				 $("#dprtcode_search", $("#"+this_.id)).val(userJgdm);
				 $("#shelf_ck", $("#"+this_.id)).val("");
				 $("#shelf_kw", $("#"+this_.id)).val("");
				 
			},
			initCk : function(){
				var dprtcode = $("#dprtcode_search").val();
				var storeHtml = "<option value=''>显示全部 All</option>";
				$.each(userStoreList, function(index, row){
					if(dprtcode == row.dprtcode){
			 			storeHtml += "<option value=\""+row.id +"\" ckh=\""+StringUtil.escapeStr(row.ckh)+"\" ckmc=\""+StringUtil.escapeStr(row.ckmc)+"\" cklb=\""+StringUtil.escapeStr(row.cklb)+"\">"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+"</option>"
					}
				});
				$("#shelf_ck").html(storeHtml);
				
			},
			initKw : function(){
				var this_ = this;
				var option = "<option value=''>显示全部 All</option>";
				var ckid = $("#shelf_ck").val();
				$.each(userStoreList, function(index, row){
					if(row.id == ckid){
						for(var i = 0 ; i < row.storageList.length ; i++){
					 		var storage = row.storageList[i];
					 		option += '<option value="'+StringUtil.escapeStr(storage.kwh)+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
					 	}
					}
				});
				$("#shelf_kw").html(option);
				$("#shelf_kw").selectpicker("refresh");
				
				
			},
			shelf_ck : function(){
				var option ="";
				$.each(userStoreList, function(index, row){
					if(row.id  == ckid){
						for(var i = 0 ; i < row.storageList.length ; i++){
					 		var storage = row.storageList[i];
				 			option += '<option value="'+storage.id +'" kwh="'+StringUtil.escapeStr(storage.kwh)+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
					 	}
					}
				});
				return option;
			},
			/**
			 * 加载数据
			 */
			load : function(pageNumber, sortColumn, orderType){
				var this_ = this;
				this_.mainData = [];
				if(typeof(pageNumber) == "undefined"){
					pageNumber = 1;
				}
				if(typeof(sortColumn) == "undefined"){
					sortColumn = "auto";
				} 
				if(typeof(orderType) == "undefined"){
					orderType = "desc";
				} 
				
				var searchParam ={};
				this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
				searchParam.pagination = this_.pagination;
				$.extend(searchParam, this_.getParams());
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/material/store/queryAllYk",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify(searchParam),
					success:function(data){
						this_.planUsageList = [];
						finishWait();
						if(data.total >0){
							this_.mainData = data.rows;
							this_.appendContentHtml(data.rows);
						   new Pagination({
								renderTo : this_.paginationId,
								data: data,
								sortColumn : sortColumn,
								orderType : orderType,
								controller: this_
							}); 
							// 标记关键字
							signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[3,4,5,6],"#"+this_.tbodyId+" tr td");
						} else {
							this_.setNoData();
						}
						new Sticky({tableId:this_.tableId});
			      }
			    }); 
					
			},	
			/**
			 * 将搜索条件封装 
			 */
			getParams : function(){
				var this_ = this;
				var searchParam = {};
				var paramsMap = {};
				var keyword = $.trim($("#keyword_search" ,$("#"+this_.id)).val());
				var cqid = $.trim($("#cqid_search" ,$("#"+this_.id)).val());
				var wllb = $.trim($("#wllb_search" ,$("#"+this_.id)).val());
				var qczt = $.trim($("#qczt_search",$("#"+this_.id)).val());
				var dprtcode = $.trim($("#dprtcode_search",$("#"+this_.id)).val());
				var ckid = $.trim($("#shelf_ck",$("#"+this_.id)).val());
				var kwh = $.trim($("#shelf_kw",$("#"+this_.id)).val());
				if(wllb == "" || wllb == null){
					if($("#hclxType").val() ==1){
						paramsMap.hclx = [0,1,4,5,6];
					}else{
						paramsMap.hclx = [2,3];
					}
				}else{
					if(wllb == 21 || wllb == 22){
						paramsMap.hclx = [2,3];
						paramsMap.hclx_ej = wllb;
					}else{
						paramsMap.hclx = [wllb];
					}
				}
				searchParam.dprtcode = dprtcode;
				paramsMap.keyword = keyword;
				searchParam.cqid = cqid;
				searchParam.ckid = ckid;
				searchParam.kwh = kwh;
				searchParam.qczt = qczt;
				searchParam.paramsMap = paramsMap;
				return searchParam;
			},
			/**
			 * 拼接列表
			 */
			appendContentHtml: function(list){
				var this_ = this;
				var htmlContent = '';
				$.each(list,function(index,row){
				   
					var paramsMap = row.paramsMap?row.paramsMap:{};
					htmlContent += "<tr onclick=\"transfer_main.showHiddenContent("+index+",this)\">";
					htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input value='"+row.id+"' type='checkbox' name='checkrow' index="+index+"  onclick=\"SelectUtil.checkRow(this,'selectAllId')\"  /></td>";
					htmlContent += "<td class='text-center'>"+ (index+1)+ "</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ckmc)+" "+StringUtil.escapeStr(row.kwh)+"'>"+StringUtil.escapeStr(row.ckmc)+" "+StringUtil.escapeStr(row.kwh)+"</td>";
					if(row.djsl && row.djsl > 0){
						htmlContent += "<td class='text-right' title='"+(parseInt(row.kcsl)-parseInt(row.djsl))+"/"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"'>"+(parseInt(row.kcsl)-parseInt(row.djsl))+"/"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>";
					}else{
						htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"'>"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>";
					}
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.cqbh) +" "+ StringUtil.escapeStr(paramsMap.fjzch) +" "+ StringUtil.escapeStr(paramsMap.gsmc)+"'>"+StringUtil.escapeStr(paramsMap.cqbh)+"</td>";
//					htmlContent += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('stockStatusEnum',row.zt)+"'>"+DicAndEnumUtil.getEnumName('stockStatusEnum',row.zt)+"</td>";
					htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.cfyq)+"'>"+StringUtil.escapeStr(row.cfyq)+"</td>";
					if(paramsMap.hclx ==2 || paramsMap.hclx ==3){
						if(row.hclxEj ==21){
							htmlContent += "<td class='text-left' title='工具'>工具</td>";
						}else if(row.hclxEj ==22){
							htmlContent += "<td class='text-left' title='设备'>设备</td>";
						}else{
							htmlContent += "<td class='text-left'></td>";
						}
					}else{
						htmlContent += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('materialType2Enum',paramsMap.hclx)+"'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',paramsMap.hclx)+"</td>";
					}
					htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.qczt)+"'>"+StringUtil.escapeStr(row.qczt)+"</td>";
					htmlContent += "</tr>"; 
				    
				});
				$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
				$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
				refreshPermission();
			},
			/**
			 * 点击行选择 
			 */
			showHiddenContent: function(index,this_){
				var $checkbox1 = $("#transfer_main_tbody :checkbox[name='checkrow']:eq("+index+")");
				var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
				var checked = $checkbox1.is(":checked");
				$checkbox1.attr("checked", !checked);
				$checkbox2.attr("checked", !checked);
			},
			/**
			 * 清空数据
			 */
			setNoData : function(){
				var this_ = this;
				$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
				$("#"+this_.paginationId, $("#"+this_.id)).empty();
				$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"12\" class='text-center'>暂无数据 No data.</td></tr>");
			},
			/**
			 * 排序
			 */
			orderBy : function(sortColumn, prefix){//字段排序
				var this_ = this;
				var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableDivId));
				var orderStyle = $obj.attr("class");
				$(".sorting_desc", $("#"+this_.tableDivId)).removeClass("sorting_desc").addClass("sorting");
				$(".sorting_asc", $("#"+this_.tableDivId)).removeClass("sorting_asc").addClass("sorting");
				var orderType = "asc";
				if (orderStyle.indexOf ("sorting_asc")>=0) {
					$obj.removeClass("sorting").addClass("sorting_desc");
					orderType = "desc";
				} else {
					$obj.removeClass("sorting").addClass("sorting_asc");
					orderType = "asc";
				}
				var currentPage = $("#"+this_.paginationId+" li[class='active']", $("#"+this_.id)).text();
				if(currentPage == ""){currentPage = 1;}
				/*if(prefix != null && prefix != '' && typeof prefix != undefined){
					sortColumn = prefix+"."+sortColumn;
				}*/
				this_.load(currentPage, sortColumn, orderType);
			},
			/**
			 * 刷新页面
			 */
			refreshPage : function(){//刷新页面
				$("#"+this.tableDivId).prop('scrollTop','0');
				//重置排序图标
				TableUtil.resetTableSorting(this.tableDivId);
				this.load(this.pagination.page, this.pagination.sort, this.pagination.order);
			},
			/**
			 * 搜索
			 */
			search : function(){
				$("#"+this.tableDivId).prop('scrollTop','0');
				//重置排序图标
				TableUtil.resetTableSorting(this.tableDivId);
				this.load(1,"auto","desc");
			},
			openList : function(){
				var this_ = this;
				var dprtcode = $.trim($("#dprtcode_search", $("#"+this_.id)).val());
				var cqId = $("#cqid_search", $("#"+this_.id)).val();
				cqModal.show({
					selected:cqId,//原值，在弹窗中默认勾选
					dprtcode:dprtcode,
					callback: function(data){//回调函数
						var cqText = '';
						var cqid = '';
						if(data != null ){
							cqText = data.cqbh;
							cqid = data.id;
						}
						$("#cqText_search", $("#"+this_.id)).val(cqText);
						$("#cqid_search", $("#"+this_.id)).val(cqid);
					}
				});
			},
			moreSearch : function(){
				var this_ = this;
				if ($("#divSearch", $("#"+this_.id)).css("display") == "none") {
					$("#divSearch", $("#"+this_.id)).css("display", "block");
					App.resizeHeight();
					$("#icon", $("#"+this_.id)).removeClass("icon-caret-down");
					$("#icon", $("#"+this_.id)).addClass("icon-caret-up");
				} else {
					$("#divSearch", $("#"+this_.id)).css("display", "none");
					App.resizeHeight();
					$("#icon", $("#"+this_.id)).removeClass("icon-caret-up");
					$("#icon", $("#"+this_.id)).addClass("icon-caret-down");
				}
			},
			//搜索条件重置
			searchreset : function(){
				var this_ = this;
				 $("#divSearch", $("#"+this_.id)).find("input").each(function() {
					$(this).attr("value", "");
				});
				
				 $("#divSearch", $("#"+this_.id)).find("textarea").each(function(){
					 $(this).val("");
				 });
				 $("#divSearch", $("#"+this_.id)).find("select").each(function(){
					 $(this).val("");
				 });
				 $("#keyword_search", $("#"+this_.id)).val("");
				 $("#dprtcode_search", $("#"+this_.id)).val(userJgdm);
				 $("#shelf_ck", $("#"+this_.id)).val("");
				 $("#shelf_kw", $("#"+this_.id)).val("");
					transfer_main.initKw();//库位
			},
			/**
			 * 导出
			 */
			exportExcel : function(){
				var param = this.getParams();
				param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
				param.keyword = encodeURIComponent(param.keyword);
				window.open(basePath+"/material/stock/material/outfield.xls?paramjson="+JSON.stringify(param));
			}
		}
	function selectworkcard(e,index,id,name,obj){
		 e = e || window.event;  
		    if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
		    var $checkbox1 = $("#" + id + " :checkbox[name='" + name + "']:eq("
	 				+ index + ")");
	 		var $checkbox2 = $(".sticky-col :checkbox[name='" + name + "']:eq("
	 				+ index + ")");
		    if(!$(obj).parents("table").hasClass("sticky-col")){
		    	var checked = $checkbox1.is(":checked");
		 		$checkbox1.attr("checked", checked);
		 		$checkbox2.attr("checked", checked);	
		    }else{
		    	var checked = $checkbox2.is(":checked");
		 		$checkbox1.attr("checked", checked);
		 		$checkbox2.attr("checked", checked);
		    }
		   
	}