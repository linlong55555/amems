
$(function(){
	/**
	 * 选择子部件 添加行选中事件
	 */
	$("#subcomponentList tr").live("click",function(event){
		 // 避免复选框重复选择
		 if($(event.target).attr("type") == "checkbox"){
			 return;
		 }
		 var checkbox = $(this).find(":checkbox");
         if(checkbox.attr("checked") == "checked"){
        	 checkbox.removeAttr("checked");
         } else {
        	 checkbox.attr("checked","checked");
         }
    });
	
	/**
	 * 自动带入航次主数据的数据
	 */
	$("#jh_zs").blur(function(){
		AjaxUtil.ajax({
			   url:basePath+"/material/material/selectByBjhAndDprt",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify({
				   bjh : $.trim($("#jh_zs").val()),
				   dprtcode : $.trim($("#dprtcode").val())
			   }),
			   success:function(data){
				   if(data){
					   $("#zwms_zs").val(data.zwms);
					   $("#ywms_zs").val(data.ywms);
					   $("#cjjh_zs").val(data.cjjh);
					   $("#zjh_zs").val(data.zjh);
					   if(data.fixChapter){
						   $("#zjh_show_zs").val($.trim((data.fixChapter.zjh||"")+"  "+ (data.fixChapter.zwms||"")));
					   }
				   }
		      }
	    }); 
	});
});

//打开装上件对话框
function showSubcomponentModal(btn) {
	$("#subcomponentModal").modal("show");
	$("#chooseList").empty();
	var id = $("#hidden_mountComponent").val();
	var data = totalData.getDismountRecord(id);
	var children = data.on.children || [];
	var childrenIds = [];
	for(var i = 0; i < children.length; i++){
		childrenIds.push(children[i].zbjZjqdid);
	}
	// 获取已获取部件数据
	if(childrenIds.length > 0){
		AjaxUtil.ajax({
			url:basePath+"/productionplan/loadingList/queryList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				ids : childrenIds
			}),
			success:function(data){
				finishWait();
				if(data.length >0){
					$.each(data,function(index,row){
						$("#chooseList").append(["<tr style='cursor:pointer' bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
						                         "<td><i class='icon-arrow-up' onclick='goUp(this)' style='cursor:pointer' ></i></td>",
						                         "<td style='text-align:left'>"+$.trim((row.zjh || "") + "  "+ (row.zjhms || ""))+"</td>",
						                         "<td style='text-align:left'>"+row.ywmc+"</td>",
						                         "<td style='text-align:left'>"+row.zwmc+"</td>",
						                         "<td style='text-align:left'>"+row.jh+"</td>",
						                         "<td style='text-align:left'>"+row.xlh+"</td>",
						                         "<td style='text-align:left'>"+row.cjjh+"</td>",
						                         "<td style='text-align:right'>"+row.zjsl+"</td>",
						                         "<td>"+DicAndEnumUtil.getEnumName('planeComponentPositionEnum',row.wz)+"</td>",
						                         "<input type='hidden' name='id' value='"+row.id+"'>",
						                         "</tr>"
						                         ].join(""));
					});
					FormatUtil.removeNullOrUndefined();
				} else {
					$("#chooseList").append("<tr><td colspan=\"11\" class='text-center non-choice'>暂无数据 No data.</td></tr>");
				}
			}
		}); 
	}
	goPageSubcomponent(1,"auto","desc");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageSubcomponent(pageNumber,sortType,sequence){
	AjaxSubComponentSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchSubcomponent(){
	goPageSubcomponent(1,"auto","desc");
}

//带条件搜索的翻页
function AjaxSubComponentSearch(pageNumber,sortType,sequence){
	var searchParam = getSubcomponentSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/productionplan/loadingList/queryEffectiveNoChildList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendSubcomponentContentHtml(data.rows);
			   new Pagination({
					renderTo : "subcomponentPagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(pageNumber,sortType,sequence){
						AjaxSubComponentSearch(pageNumber,sortType,sequence);
					}
				});
			   FormatUtil.removeNullOrUndefined();
			   // 标记关键字
			   signByKeyword($("#subcomponent_search").val(),[2,3,4,5,6,7],"#subcomponentList tr td");
		   } else {
			   $("#subcomponentList").empty();
			   $("#subcomponentPagination").empty();
			   $("#subcomponentList").append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
//将搜索条件封装 
function getSubcomponentSearchParam(){
	 var searchParam = {};
	 var id = $("#hidden_mountComponent").val();
	 var on = totalData.getDismountRecord(id).on;
	 // 过滤当前装上件
	 if(on.zsZjqdid){
		 searchParam.notId = on.zsZjqdid;
	 }
	 // 过滤此次飞机记录单拆下的部件
	 var notIds = [];
	 $(totalData.finishedWork).each(function(){
		 $(this.dismountRecord).each(function(){
			   if(this.cxZjqdid){
				   notIds.push(this.cxZjqdid);
			   }
		 });
	 });
	 searchParam.notIds = notIds;
	 searchParam.keyword = $.trim($("#subcomponent_search").val());
	 searchParam.fjzch = $.trim($("#fjzch").val());
	 searchParam.zt = 1;
	 searchParam.sxzt = 1;
	 searchParam.wz =  $.trim($("#wz_main").val());
	 searchParam.azjldh =  $.trim($("#azjldh_main").val());
	 searchParam.ccjldh =  $.trim($("#ccjldh_main").val());
	 searchParam.llklx =  $.trim($("#llklx_main").val());
	 searchParam.llkbh =  $.trim($("#llkbh_main").val());
	 searchParam.bz =  $.trim($("#bz_main").val());
	 searchParam.kzlx =  $.trim($("#kzlx_main").val());
	 searchParam.isDj =  $.trim($("#isDj_main").val());
	 searchParam.dprtcode =  $.trim($("#dprtcode").val());
	 var azrq = $.trim($("#azrq_main").val());
	 if(azrq != ''){
		 searchParam.beginAzrq = azrq.substring(0,10);
		 searchParam.endAzrq = azrq.substring(13,23);
	 }
	 var scrq = $.trim($("#scrq_main").val());
	 if(scrq != ''){
		 searchParam.beginScrq = scrq.substring(0,10);
		 searchParam.endScrq = scrq.substring(13,23);
	 }
	 var ccrq = $.trim($("#ccrq_main").val());
	 if(ccrq != ''){
		 searchParam.beginCcrq = ccrq.substring(0,10);
		 searchParam.endCcrq = ccrq.substring(13,23);
	 }
	 return searchParam;
}

	function appendSubcomponentContentHtml(list){
		
		// 获取已有子节点
		var id = $("#hidden_mountComponent").val();
		var data = totalData.getDismountRecord(id);
		
		var chooseList = [];
		$("#chooseList>tr>input[name='id']").each(function(){
	    	var input = $(this);
	    	chooseList.push(input.val());
		});
		$("#subcomponentList").empty();
		$.each(list,function(index,row){
			$("#subcomponentList").append(["<tr style='cursor:pointer' bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
					                         "<td>"+($.inArray(row.id, chooseList) == -1 ? "<i class='icon-arrow-down' onclick='goDown(this)' style='cursor:pointer' ></i>":"")+"</td>",
					                         "<td style='text-align:left' title='"+StringUtil.escapeStr($.trim((row.zjh || "") + "  "+ (row.zjhms || "")))+"'>"+StringUtil.escapeStr($.trim((row.zjh || "") + "  "+ (row.zjhms || "")))+"</td>",
					                         "<td style='text-align:left' title='"+StringUtil.escapeStr(row.ywmc)+"'>"+StringUtil.escapeStr(row.ywmc)+"</td>",
					                         "<td style='text-align:left' title='"+StringUtil.escapeStr(row.zwmc)+"'>"+StringUtil.escapeStr(row.zwmc)+"</td>",
					                         "<td style='text-align:left' title='"+StringUtil.escapeStr(row.jh)+"'>"+StringUtil.escapeStr(row.jh)+"</td>",
					                         "<td style='text-align:left' title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>",
					                         "<td style='text-align:left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>",
					                         "<td style='text-align:right' title='"+row.zjsl+"'>"+row.zjsl+"</td>",
					                         "<td>"+DicAndEnumUtil.getEnumName('planeComponentPositionEnum',row.wz)+"</td>",
					                         "<input type='hidden' name='id' value='"+row.id+"'>",
					                         "</tr>"
					                         ].join(""));
	   });
	}
	
	function moreSubcomponent() {
		if ($("#divSearchSubcomponent").css("display") == "none") {
			$("#divSearchSubcomponent").css("display", "block");
			$("#iconSubcomponent").removeClass("icon-caret-down");
			$("#iconSubcomponent").addClass("icon-caret-up");
		} else {

			$("#divSearchSubcomponent").css("display", "none");
			$("#iconSubcomponent").removeClass("icon-caret-up");
			$("#iconSubcomponent").addClass("icon-caret-down");
		}
	}
	 
	 //搜索条件重置
	function subcomponentSearchreset(){
		$("#divsearchSubcomponent").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("#divsearchSubcomponent").find("textarea").each(function(){
			$(this).val("");
		});
		 
		$("#divsearchSubcomponent").find("select").each(function(){
			$(this).val("");
		});
		$("#subcomponent_search").val("");
	 }
	
	/**
	 * 选择子部件
	 */
	function chooseSubcomponent(){
		var id = $("#hidden_mountComponent").val();
		var data = totalData.getDismountRecord(id);
		// 所有子部件
		var children = [];
		$("#chooseList>tr>input[name='id']").each(function(){
	    	var input = $(this);
	    	var tds = input.parent().find("td");
			// 添加数据到对象中
			children.push({
				zbjZjqdid : input.val(),
				jh : $(tds[4]).text(),
				xlh : $(tds[5]).text(),
				zwmc : $(tds[3]).text(),
				ywmc : $(tds[2]).text(),
				zjh_show : $(tds[1]).text()
			});
		});
		data.on.children = children;
		$("#childenList_show").empty();
		var inputs = $("#chooseList>tr>input[name='id']");
		if(inputs.length > 0){
			inputs.each(function(i, obj){
				var tds = $(obj).parent().find("td");
				var jh = StringUtil.escapeStr($(tds[4]).text());
				var xlh = StringUtil.escapeStr($(tds[5]).text());
				var ywmc = StringUtil.escapeStr($(tds[2]).text());
				var zwmc = StringUtil.escapeStr($(tds[3]).text());
				var zjh_show = StringUtil.escapeStr($(tds[1]).text());
				var displayName = zjh_show + " " + jh + " " + ywmc + " " + zwmc + " " +  xlh;
				$("#childenList_show").append('<input style="border:none;border-bottom:#ccc solid 1px;height:24px;width : 100%;margin-top:10px;" id="" type="text" readonly="readonly" value="'+displayName+'">');
			});
		}else{
			$("#childenList_show").append('<input style="border:none;border-bottom:#ccc solid 1px;height:24px;width : 100%;margin-top:10px;" id="" type="text" readonly="readonly">');
		}
		$("#chooseList").empty();
		$("#subcomponentModal").modal("hide");
	}
	
	/**
	 * 选择子部件
	 * @param btn
	 */
	function goDown(btn){
		var tds = $(btn).parent().parent().find("td");
		var id = $(btn).parent().parent().find("input[name='id']").val();
		var count = $("#chooseList").children().length;
		$("#chooseList td.non-choice").remove();
	    $("#chooseList").append(["<tr bgcolor='"+(count % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
               "<td><i class='icon-arrow-up' onclick='goUp(this)' style='cursor:pointer' ></i></td>",
               "<td style='text-align:left' title='"+StringUtil.escapeStr($(tds[1]).text())+"'>"+StringUtil.escapeStr($(tds[1]).text())+"</td>",
               "<td style='text-align:left' title='"+StringUtil.escapeStr($(tds[2]).text())+"'>"+StringUtil.escapeStr($(tds[2]).text())+"</td>",
               "<td style='text-align:left' title='"+StringUtil.escapeStr($(tds[3]).text())+"'>"+StringUtil.escapeStr($(tds[3]).text())+"</td>",
               "<td style='text-align:left' title='"+StringUtil.escapeStr($(tds[4]).text())+"'>"+StringUtil.escapeStr($(tds[4]).text())+"</td>",
               "<td style='text-align:left' title='"+StringUtil.escapeStr($(tds[5]).text())+"'>"+StringUtil.escapeStr($(tds[5]).text())+"</td>",
               "<td style='text-align:left' title='"+StringUtil.escapeStr($(tds[6]).text())+"'>"+StringUtil.escapeStr($(tds[6]).text())+"</td>",
               "<td style='text-align:right' title='"+$(tds[7]).text()+"'>"+$(tds[7]).text()+"</td>",
               "<td >"+$(tds[8]).text()+"</td>",
               "<input type='hidden' name='id' value='"+id+"'>",
               "</tr>"
               ].join(""));
		$(btn).remove();
	}
	
	/**
	 * 还原子部件
	 * @param btn
	 */
	function goUp(btn){
		var id = $(btn).parent().parent().find("input[name='id']").val();
		var input = $("#subcomponentList>tr>input[value='"+id+"']");
		if(input){
			input.parent().find("td:first").append("<i class='icon-arrow-down' onclick='goDown(this)' style='cursor:pointer' ></i>");
		}
		$(btn).parent().parent().remove();
	}
	
	//打开装上件对话框
	function searchMountComponentModal(btn) {
		if(!$("#fxrq").val()){
			AlertUtil.showErrorMessage("请先填写飞行日期！");
			return false;
		}
		$("#hidden_mountComponent").val($(btn).parent().parent().attr("id"));
		$("#mountComponentModal").modal("show");
		resetMountComponentData();
		$("#wz_zs").val("1");
		if($("#forwordType").val() != 3){
			$("#wz_zs").removeAttr("disabled");
			$("#jh_zs").removeAttr("disabled");
			$("#xlh_zs").removeAttr("disabled");
		}
    	$('#mountComponentModal').on('shown.bs.modal', function (e) {
			$("#loadingList_tree").height($("#componentForm").height()-121);
		});
    	// 继承拆下件的位置和父节点
    	var id = $("#hidden_mountComponent").val();
    	var dismountRecord = totalData.getDismountRecord(id);
    	var cxZjqdid = dismountRecord.cxZjqdid;
		var cxWz = dismountRecord.cxWz;
		var cxFjdid = dismountRecord.cxFjdid;
		var cxCj = dismountRecord.cxCj;
		if(cxZjqdid){
			$("#wz_zs").val(cxWz);
		}
		refreshStatus(cxCj||1);
    	initMountComponentValidate();
    	fillMountComponentData();
		refreshTree(cxFjdid);
	}
	
	/**
	 * 回填装上件信息
	 */
	function fillMountComponentData(){
		var id = $("#hidden_mountComponent").val();
		var data = totalData.getDismountRecord(id).on;
		$("#azrq_zs").attr("disabled","disabled");
		$("#azrq_zs").val($("#fxrq").val());
		if(data.jh){
			$("#fjzch_zs").val(data.fjzch);
         	$("#fjjx_zs").val(data.fjjx);
         	$("#jh_zs").val(data.jh);
         	$("#xlh_zs").val(data.xlh);
         	$("#pch_zs").val(data.pch);
         	$("#zjh_zs").val(data.zjh);
         	$("#zjh_show_zs").val(data.zjh_show);
         	$("#cjjh_zs").val(data.cjjh);
         	$("#zwms_zs").val(data.zwmc);
         	$("#ywms_zs").val(data.ywmc);
         	$("#bjgzjl_zs").val(data.bjgzjl);
         	$("#zjsl_zs").val(data.zjsl);
     		$("#zjsl_max").val(data.zjslMax);
         	$("#scrq_zs").val((data.scrq||"").substr(0, 10));
         	$("#xkzh_zs").val(data.xkzh);
         	$("#shzh_zs").val(data.shzh);
         	$("#llkbh_zs").val(data.llkbh);
			$("#bz_zs").val(data.bz);
         	$("#azjldh_zs").val(data.azjldh);
         	$("#ccrq_zs").val((data.ccrq||"").substr(0, 10));
         	$("#ccjldh_zs").val(data.ccjldh);
         	$("#nbsbm_zs").val(data.nbsbm);
         	$("#bz_zs").val(data.bz);
         	$("#wz_zs").val(data.wz);
         	$("#parent_zs").val(data.fjdid);
         	if(data.parent){
         		$("#parent_show_zs").val(data.parent.displayName);
         		$("#parent_show_zs").attr("title", data.parent.displayName);
         	}
         	if(data.children){
         		if(data.children.length > 0){
         			$("#childenList_show").empty();
         			$(data.children).each(function(){
         				var displayName = this.zjh_show + " " + this.jh + " " + this.ywmc + " " + this.zwmc + " " +  this.xlh;
        				$("#childenList_show").append('<input style="border:none;border-bottom:#ccc solid 1px;height:24px;width : 100%;margin-top:10px;" id="" type="text" readonly="readonly" value="'+displayName+'">');
             		});
         		}
         	}
         	
         	$("#llklx_zs").val(data.llklx||"1");
     		$("#kzlx_zs").val(data.kzlx||"1");
     		$("#isDj_zs").val(data.isDj != 0 ? (data.isDj||"1") : "0");
     		$("#hidden_hcId").val(data.id);
     		$("#tso_zs").val(data.tso);
         	$("#tsn_zs").val(data.tsn);
		}
		var zt = $("#frsZt").val();
		if(data.jh && data.xlh && zt && zt > 1 && data.isNew != 1){
			$("#jh_zs").attr("disabled","disabled");
			$("#xlh_zs").attr("disabled","disabled");
		}
		if(data.fjdid && data.fjdid != '0'){
			$("#wz_zs").attr("disabled","disabled");
		}
		limitCount();
	}
	
	/**
	 * 重置装上件表单
	 */
	function resetMountComponentData(){
		$("#componentForm :input").not(":button, :submit, :reset, :checkbox, :radio, select").val("");
		$("#llklx_zs").val("1");
		$("#kzlx_zs").val("1");
		$("#isDj_zs").val("1");
		$("#zjsl_zs").val("1");
		$("#zjsl_max").val("");
		$("#component_mount_search").val("");
		$("#fjzch_zs").val($("#fjzch").val());
    	$("#fjjx_zs").val($("#fjzch option:selected").attr("fjjx"));
    	$("#hidden_hcId").val("");
    	
    	$("#childenList_show").html('<input style="border:none;border-bottom:#ccc solid 1px;height:24px;width : 100%;margin-top:10px;" type="text" readonly="readonly">');
    	$("#main_parent_btn_zs").removeAttr("disabled");
	}
	
	/**
	 * 初始化树
	 * @param node 默认选择的节点id
	 * @param hasValue 右侧面板是否有值
	 */
	function initTree(fjdidParam, expandAll){
		var id = $("#hidden_mountComponent").val();
		var data = totalData.getDismountRecord(id).on;
		AjaxUtil.ajax({
			   url:basePath+"/productionplan/loadingList/queryEffectiveTree", 
			   type: "post",
			   dataType:"json",
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify({
				   fjzch:$.trim($("#fjzch").val()),
				   zt:1,
				   notCj:2,
				   keyword:$.trim($("#keyword_search").val()),
				   dprtcode:$.trim($("#dprtcode").val()),
				   notId:data.zsZjqdid
			   }),
			   success:function(list){
				   var setting = {
						   view: {
							   showIcon: false,
							   selectedMulti: false,
							   nameIsHTML: false
						   },
						   check: {enable: false,autoCheckTrigger: true}, 
						   data: {
							   key: {
									name: "displayName"
							   },
							   simpleData: {
								   enable: true,
								   idKey: "id",
								   pIdKey: "pId",
								   rootPId: null
							   }
						   },
						   callback: {
							   beforeClick: beforeClick
						   }
				   };
				   $(totalData.finishedWork).each(function(){
					   $(this.dismountRecord).each(function(){
						   // 加上此次飞行记录单新增的一级件
						   var on = this.on;
						   if(on.jh && on.cj == 1 && !containsLoadingList(list, on) && on.zsZjqdid != data.zsZjqdid){
							   var jh = on.jh || "";
							   var xlh = on.xlh || "";
							   var ywmc = on.ywmc || "";
							   var zwmc = on.zwmc || "";
							   var zjh_show = on.zjh_show || "";
							   displayName = zjh_show + " " + jh + " " + ywmc + " " + zwmc + " " +  xlh;
							   list.push({
								   jh : on.jh,
								   xlh : on.xlh,
								   ywmc : on.ywmc,
								   zwmc : on.zwmc,
								   cj : 1,
								   wz : on.wz,
								   id : on.zsZjqdid,
								   fjzch : on.fjzch,
								   fjjx : on.fjjx,
								   fjdid : on.fjdid,
								   displayName : displayName
							   });
						   }
						   // 减去此次飞机记录单拆下的一级件
						   if(this.cxCj == 1){
							   for(var i=0; i < list.length; i++){
								   if(list[i].id == this.cxZjqdid){
								        list.splice(i,1);
								        i--;
								    }
								}
						   }
					   });
				   });
				   
				   var treeObj = $.fn.zTree.init($("#loadingList_tree"), setting, list);
				   var nodes = treeObj.transformToArray(treeObj.getNodes());
				   if(expandAll){
					   treeObj.expandAll(true); 
				   }else{
					   treeObj.expandNode(nodes[0]);
				   }
				   $.each(nodes, function(index, node){
					   node.title = StringUtil.escapeStr(node.name);  
					   treeObj.updateNode(node); 
				   });
				   // 模拟点击节点
				   if(fjdidParam){
					   var node = treeObj.getNodeByParam("id", fjdidParam, null);
					   if(node){
						   $("#parent_zs").val(node.id);
						   $("#parent_show_zs").val(node.displayName);
						   treeObj.selectNode(node);
					   }
				   } else if(data.jh){
					   var fjdid = data.fjdid;
					   var node = treeObj.getNodeByParam("id", fjdid, null);
					   $("#parent_zs").val(node.id);
					   $("#parent_show_zs").val(node.displayName);
					   treeObj.selectNode(node);
				   }
		      }
		    }); 
	}
	
	/**
	 * 判断list中是否包含这个装机清单数据
	 * @param list
	 * @param ll
	 * @returns {Boolean}
	 */
	function containsLoadingList(list, ll){
		var result = false;
		$(list).each(function(){
			if(this.jh == ll.jh && this.xlh == ll.xlh){
				result = true;
			}
		});
		return result;
	}
	
	function beforeClick(treeId, treeNode, clickFlag){
		var id = $("#hidden_mountComponent").val();
    	var dismountRecord = totalData.getDismountRecord(id);
    	if(dismountRecord.cxFjdid){
    		return false;
    	}
    	if($("#forwordType").val() == 3){
    		return false;
    	}
    	if(treeNode.id == '0' 
		 	|| treeNode.id == 'js'
	 		|| treeNode.id == 'zf'
 			|| treeNode.id == 'yf'
			|| treeNode.id == 'ssd'
			|| treeNode.id == 'wdg'
			|| treeNode.id == 'jc'){
    		return false;
    	}
    	return true;
	}
	
	function refreshStatus(cj){
		var id = $("#hidden_mountComponent").val();
    	var dismountRecord = totalData.getDismountRecord(id);
    	var cxZjqdid = dismountRecord.cxZjqdid;
    	
		if(cj == 1){
			if($("#forwordType").val() != 3){
				$("#showSubcomponentBtn").removeAttr("disabled");
			}
		}else if(cj == 2){
			$("#showSubcomponentBtn").attr("disabled","disabled");
		}
		
		if(cxZjqdid){
			$("#main_parent_btn_zs").attr("disabled","disabled");
		}else if($("#forwordType").val() != 3){
			$("#main_parent_btn_zs").removeAttr("disabled");
		}
		
		if(cj == 1 && !cxZjqdid){
			if($("#forwordType").val() != 3){
				$("#wz_zs").removeAttr("disabled");
			}
		}else{
			$("#wz_zs").attr("disabled","disabled");
		}
	}
	
	/**
	 * 刷新整个树
	 * @param id 默认选中的id
	 */
	function refreshTree(fjdid, expandAll){
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
		if(treeObj){
			treeObj.destroy();
		}
		initTree(fjdid, expandAll);
	}
	
	/**
	 * 查询外场库存
	 */
	function searchOutStock(){
		if(!$.trim($("#component_mount_search").val())){
			AlertUtil.showErrorMessage("请先输入关键字！");
			return false;
		}
		var notIds = [];
		for(var i = 0; i < totalData.finishedWork.length; i++){
			var dismountRecord = totalData.finishedWork[i].dismountRecord;
			for(var j = 0; j < dismountRecord.length; j++){
				if(dismountRecord[j].zsWckcid){
					notIds.push(dismountRecord[j].zsWckcid);
				}
			}
		}
		var keywords = $("#component_mount_search").val().split(" ");
		var searchParam = {
				dprtcode : $("#dprtcode").val(),
				notIds : notIds,
				paramsMap : {
					keywords : keywords
				}
		};
		AjaxUtil.ajax({
	 		   url:basePath+"/aerialmaterial/outfieldstock/queryMountComponent",
	 		   type: "post",
	 		   contentType:"application/json;charset=utf-8",
	 		   dataType:"json",
	 		   data:JSON.stringify(searchParam),
	 		   success:function(data){
	 			  $('#component_mount_search').typeahead('destroy');
	 			  if(data.length == 1){
	 				  setMountComponentByOutStock(data[0]);
	 			  }else {
	 				  refreshAutoComplete(data);
	 			  }
	 	      }
	 	}); 
	}
	
	/**
	 * 根据外场库存补充装上件数据
	 * @param item
	 */
	function setMountComponentByOutStock(item){
		resetMountComponentData();
    	$("#fjzch_zs").val($("#fjzch").val());
    	$("#fjjx_zs").val($("#fjzch option:selected").attr("fjjx"));
    	$("#jh_zs").val(item.bjh);
    	$("#xlh_zs").val(item.sn);
    	$("#pch_zs").val(item.pch);
    	if(item.hcMainData.fixChapter){
    		$("#zjh_zs").val(item.hcMainData.zjh);
    		$("#zjh_show_zs").val($.trim((item.hcMainData.fixChapter.zjh||"")+"  "+ (item.hcMainData.fixChapter.zwms||"")));
    	}
    	$("#cjjh_zs").val(item.hcMainData.cjjh);
    	$("#zwms_zs").val(item.hcMainData.zwms);
    	$("#ywms_zs").val(item.hcMainData.ywms);
    	$("#bjgzjl_zs").val(item.bjgzjl);
    	$("#zjsl_zs").val(item.kcsl);
    	$("#zjsl_max").val(item.kcsl);
    	$("#azrq_zs").val($("#fxrq").val());
    	$("#scrq_zs").val((item.scrq||"").substr(0, 10));
    	$("#xkzh_zs").val(item.xkzh);
    	$("#shzh_zs").val(item.shzh);
    	$("#llkbh_zs").val(item.llkbh);
    	$("#llklx_zs").val(item.llklx||'1');
		$("#kzlx_zs").val(item.kzlx||'1');
		$("#isDj_zs").val(item.isDj != 0 ? (item.isDj||"1") : "0");
		$("#hidden_hcId").val(item.id);
		$("#tso_zs").val(item.tso);
    	$("#tsn_zs").val(item.tsn);
    	limitCount();
		$("#componentForm").data('bootstrapValidator').resetForm(false);
		
		var id = $("#hidden_mountComponent").val();
    	var dismountRecord = totalData.getDismountRecord(id);
    	var cxZjqdid = dismountRecord.cxZjqdid;
		var cxWz = dismountRecord.cxWz;
		var cxFjdid = dismountRecord.cxFjdid;
		var cxCj = dismountRecord.cxCj;
		if(cxZjqdid){
			 $("#wz_zs").val(cxWz);
			 var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
			 var node = treeObj.getNodeByParam("id", cxFjdid, null);
			 if(node){
				 $("#parent_zs").val(node.id);
				 $("#parent_show_zs").val(node.displayName);
				 treeObj.selectNode(node);
			 }
		}
		refreshStatus(cxCj||1);
	}
	
	
	/**
	 * 初始化输入框自动完成控件
	 */
	function refreshAutoComplete(data){
	    $('#component_mount_search').typeahead({
			displayText : function(item){
				return item.displayName;
			},
		    source: function (query, process) {
		    	process(data);
		    },

            highlighter: function (item) {
                return "&nbsp;&nbsp;" + item + "&nbsp;&nbsp;";
            },

            updater: function (item) {
            	setMountComponentByOutStock(item);
                return item;
            }
		});
	    $('#component_mount_search').typeahead('lookup');
		$('#component_mount_search').focus();
	}
	
	
	/**
	 * 设置装上件信息
	 */
	function setMountComponent(){
		//获取父节点
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
		var node = treeObj.getSelectedNodes()[0];
		
		$('#componentForm').data('bootstrapValidator').validate();
		  if(!$('#componentForm').data('bootstrapValidator').isValid()){
			return false;
		}
		
		if(!node){
			AlertUtil.showErrorMessage("请选择父节点！");
			return false;
		}
		
		var id = $("#hidden_mountComponent").val();
		var targetRow = $("#"+id);
		
		// 获取该装上件所在的航段紧接着的一个航次
		var finishedWorkId = id.split("_")[0];
		var hd = $("#"+finishedWorkId+" select[name='hd']").val();
		var hcNum = (parseInt(hd)+1)/2;
		var afterVoyage = false;
		if(hcNum >= 4){
			afterVoyage = true;
			hcNum = 4;
		}
		var data = totalData.getDismountRecord(id);
		// 验证一级节点重复
		if(node.cj == 0){
			var zf = $("#hc"+hcNum+">td[name='f1sj']>small[name='xlh']").html();
			var yf = $("#hc"+hcNum+">td[name='f2sj']>small[name='xlh']").html();
			var jc = $("#hc"+hcNum+">td[name='jcsj']>small[name='xlh']").html();
			var ssd = $("#hc"+hcNum+">td[name='ssdsj']>small[name='xlh']").html();
			var dg = $("#hc"+hcNum+">td[name='dgxh']>small[name='xlh']").html();
			var wz = $("#wz_zs").val();
			var xlh = $("#xlh_zs").val();
			// 航后则需重新统计航后的序列号
			if(afterVoyage){
				// 获取所有完成工作
				var finishedWork = $("#finishedWorkList>tr[name='finishedWork']");
				finishedWork.each(function(i, work){
					var hd = $(work).find("select[name='hd']").val();
					if(hd == 99){
						// 循环拆解记录
						$("#finishedWorkList>tr>td>table>tbody>tr[id^='"+$(work).attr("id")+"']").each(function(i2, cjjl_){
							var cjjl = $(cjjl_);
							var dismountRecord = totalData.getDismountRecord(cjjl.attr("id"));
							var cxWz = dismountRecord.cxWz;
							var zsWz = dismountRecord.on.wz;
							var cj_cjjl = dismountRecord.cxCj || dismountRecord.on.cj || '';
							var wz_cjjl = cxWz || zsWz || '';
							if(cj_cjjl == 1 && wz_cjjl == wz){
								if(cxWz){
									if(wz == 1){
										zf = '无';
									}
									if(wz == 2){
										yf = '无';
									}
									if(wz == 3){
										jc = '无';
									}
									if(wz == 4){
										ssd = '无';
									}
									if(wz == 5){
										dg = '无';
									}
								}
								if(zsWz){
									if(wz == 1){
										zf = cjjl.find("td:eq(10)").html()||'无';
									}
									if(wz == 2){
										yf = cjjl.find("td:eq(10)").html()||'无';
									}
									if(wz == 3){
										jc = cjjl.find("td:eq(10)").html()||'无';
									}
									if(wz == 4){
										ssd = cjjl.find("td:eq(10)").html()||'无';
									}
									if(wz == 5){
										dg = cjjl.find("td:eq(10)").html()||'无';
									}
								}
							}
						});
					}
				});
			}
			if(wz == 1 && zf != '无' && zf != xlh){
				if(data.on.cj != 1 || data.on.wz != 1){
					AlertUtil.showErrorMessage("1级左发最多只能存在一个！");
					return false;
				}
			}
			if(wz == 2 && yf != '无' && yf != xlh){
				if(data.on.cj != 1 || data.on.wz != 2){
					AlertUtil.showErrorMessage("1级右发最多只能存在一个！");
					return false;
				}
			}
			if(wz == 3 && jc != '无' && jc != xlh){
				if(data.on.cj != 1 || data.on.wz != 3){
					AlertUtil.showErrorMessage("1级绞车最多只能存在一个！");
					return false;	
				}
			}
			if(wz == 4 && ssd != '无' && ssd != xlh){
				if(data.on.cj != 1 || data.on.wz != 4){
					AlertUtil.showErrorMessage("1级搜索灯最多只能存在一个！");
					return false;
				}
			}
			if(wz == 5 && dg != '无' && dg != xlh){
				if(data.on.cj != 1 || data.on.wz != 5){
					AlertUtil.showErrorMessage("1级外吊挂最多只能存在一个！");
					return false;
				}
			}
		}
		
		// 生成装机清单id
		if(!data.on.zsZjqdid){
			data.on.zsZjqdid = Math.uuid().toLowerCase();
			data.on.isNew = 1;
		}
		
		// 验证件号、序列号重复
		var result = true;
		if($("#xlh_zs").val()){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/productionplan/loadingList/queryByParam",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					jh : $("#jh_zs").val(),
					xlh : $("#xlh_zs").val(),
					dprtcode : $("#dprtcode").val(),
					notId : data.on.zsZjqdid,
					zt : 1
					
				}),
				success:function(list){
					if(list.length == 1){
						if(list[0].zt == 1){
							AlertUtil.showErrorMessage("件号为："+list[0].jh+"，序列号为："+list[0].xlh+"，状态为：装上的部件，已存在！");
							result =  false;
						}
					}else if(list.length > 1){
						AlertUtil.showErrorMessage("件号为："+list[0].jh+"，序列号为："+list[0].xlh+"的装上部件存在多个！");
						result = false;
					}
				}
			});
			if(!result){
				return false;
			}
		}
		// 验证件号、批次号、内部识别码重复
		else if($("#nbsbm_zs").val()){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/productionplan/loadingList/queryByParam",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					jh : $("#jh_zs").val(),
					pch : $("#pch_zs").val(),
					nbsbm : $("#nbsbm_zs").val(),
					dprtcode : $("#dprtcode").val(),
					zt : 1,
					notId : data.on.zsZjqdid
				}),
				success:function(list){
					if(list.length >= 1){
						AlertUtil.showErrorMessage("件号为："+list[0].jh+ (list[0].pch?"，批次号为："+list[0].pch:"") 
								+"，内部识别码为："+list[0].nbsbm+"，状态为：装上的部件，已存在！");
						result =  false;
					}
				}
			});
			if(!result){
				return false;
			}
		}
		
		// 验证部件在库存中存在，弱验证
		var existFlag;
		if(data.on.isNew == 1){
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/flightdata/flightrecordsheet/isExistInStock",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					jh : $("#jh_zs").val(),
					xlh : $("#xlh_zs").val(),
					dprtcode : $("#dprtcode").val()
				}),
				success:function(data){
					existFlag = data;
				}
			});
		}
		
		if(existFlag == 1){
			AlertUtil.showErrorMessage("该部件在库存中，请先完成出库！");
			return false;
		}
		
		if(existFlag == 2){
			AlertUtil.showConfirmMessage("该部件在外场航材不存在，若继续，则视同为新增，是否继续？", {callback: function(){
				setData();
			}});
		}else{
			setData();
		}
		
		
		function setData(){
			// 图标
			targetRow.find("td:eq(7)>i.icon-plus").removeClass("icon-plus").addClass("icon-edit");
			// 件号
			targetRow.find("td:eq(8)").html(StringUtil.escapeStr($("#jh_zs").val()));
			targetRow.find("td:eq(8)").attr("title", $("#jh_zs").val());
			// 名称
			targetRow.find("td:eq(9)").html(StringUtil.escapeStr($("#ywms_zs").val()));
			targetRow.find("td:eq(9)").attr("title", $("#ywms_zs").val());
			// 序列号
			targetRow.find("td:eq(10)").html(StringUtil.escapeStr($("#xlh_zs").val()));
			targetRow.find("td:eq(10)").attr("title", $("#xlh_zs").val());
			// 安装位置
			targetRow.find("td:eq(11)").html(DicAndEnumUtil.getEnumName('planeComponentPositionEnum',$("#wz_zs").val()));
			targetRow.find("td:eq(11)").attr("title", DicAndEnumUtil.getEnumName('planeComponentPositionEnum',$("#wz_zs").val()));
			// 数量
			targetRow.find("td:eq(12)").html($("#zjsl_zs").val());
			targetRow.find("td:eq(12)").attr("title", $("#zjsl_zs").val());
			// 父节点
			targetRow.find("td:eq(13)").html($("#parent_show_zs").val());
			targetRow.find("td:eq(13)").attr("title", $("#parent_show_zs").val());
			
			var beforeCj = data.on.cj;
			var beforeWz = data.on.wz;
			data.on.fjzch = $("#fjzch").val();
			data.on.fjjx = $("#fjjx_zs").val();
			data.on.xlh = $("#xlh_zs").val();
			data.on.pch = $("#pch_zs").val();
			data.on.jh = $("#jh_zs").val();
			data.on.zjh = $("#zjh_zs").val();
			data.on.zjh_show = $("#zjh_show_zs").val();
			data.on.cjjh = $("#cjjh_zs").val();
			data.on.zwmc = $("#zwms_zs").val();
			data.on.ywmc = $("#ywms_zs").val();
			data.on.bjgzjl = $("#bjgzjl_zs").val();
			data.on.zjsl = $("#zjsl_zs").val();
			data.on.zjslMax = $("#zjsl_max").val();
			data.on.scrq = ($("#scrq_zs").val()||"").substr(0, 10);
			data.on.xkzh = $("#xkzh_zs").val();
			data.on.shzh = $("#shzh_zs").val();
			data.on.llkbh = $("#llkbh_zs").val();
			data.on.llklx = $("#llklx_zs").val();
			data.on.kzlx = $("#kzlx_zs").val();
			data.on.isDj = $("#isDj_zs").val();
			data.on.bz = $("#bz_zs").val();
			data.on.azrq = ($("#azrq_zs").val()||"").substr(0, 10);
			data.on.azjldh = $("#azjldh_zs").val();
			data.on.ccrq = ($("#ccrq_zs").val()||"").substr(0, 10);
			data.on.ccjldh = $("#ccjldh_zs").val();
			data.on.nbsbm = $("#nbsbm_zs").val();
			data.on.wz = $("#wz_zs").val();
			data.on.tso = $("#tso_zs").val();
			data.on.tsn = $("#tsn_zs").val();
			data.on.fjdid = node.id;
			data.on.parent = {
					displayName : $("#parent_show_zs").val()
			}
			data.on.cj = node.cj+1;
			data.zsIs = 1;
			data.zsWckcid = $("#hidden_hcId").val();
			var timeMonitorSettings = data.timeMonitor.settings;
			var fixedMonitors = data.fixedMonitor;
			
			// 同步时控件的件号、序列号
			if(timeMonitorSettings && timeMonitorSettings.length > 0){
				$(timeMonitorSettings).each(function(){
					this.jh = data.on.jh;
					this.xlh = data.on.xlh;
				});
			}
			// 同步定检件的件号、序列号
			if(fixedMonitors && fixedMonitors.length > 0){
				$(fixedMonitors).each(function(){
					this.jh = data.on.jh;
					this.xlh = data.on.xlh;
					$(this.monitorItemList).each(function(){
						this.jh = data.on.jh;
						this.xlh = data.on.xlh;
					});
				});
			}
			// 同步时控件、定检件监控设置
			if(data.on.kzlx == 3){
				data.timeMonitor = {};
				targetRow.find("td:eq(14)").html('<a href="javascript:void(0);" class="cursor-pointer" style="color:red" onclick="showTimeMonitorModal(this)"><u>待配置</u></a>');
			}
			if(data.on.isDj ==  0){
				data.fixedMonitor = [];
				targetRow.find("td:eq(15)").html('<a href="javascript:void(0);" class="cursor-pointer" style="color:red" onclick="showFixedMonitorModal(this)"><u>待配置</u></a>');
			}
			$("#mountComponentModal").modal("hide");
			
			// 刷新飞行前数据
			if(data.on.cj == 1){
				calcCurrentLevelOneComponent(data.on.wz);
			}
			if(beforeCj == 1){
				calcCurrentLevelOneComponent(beforeWz);
			}
		}
	}
	
	function reValidateXlh(){
		$('#componentForm').data('bootstrapValidator')  
        .updateStatus("xlh_zs", 'NOT_VALIDATED',null)  
        .validateField("xlh_zs");
	 }
	
	/**
	 * 初始化验证
	 */
	function initMountComponentValidate(){
		if($("#componentForm").data('bootstrapValidator')){
			$("#componentForm").data('bootstrapValidator').destroy();
			$('#componentForm').data('bootstrapValidator', null);
		}
		 var validatorForm =  $('#componentForm').bootstrapValidator({
			 	excluded: [':disabled'],
		        message: '数据不合法',
		        feedbackIcons: {
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	jh_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    },
		                    notEmpty: {
		                        message: '件号不能为空'
		                    }
		                }
		            },
		            zjh_zs: {
		                validators: {
		                    notEmpty: {
		                        message: 'ATA章节号不能为空'
		                    }
		                },
	                	callback: {
	                        message: '时控件和定检件的序列号为必输项',
	                        callback: function(value, validator) {
	                			var zjh = $("#zjh").val();
	                			if(zjh){
	                				return false;
	                			}
	                            return true;
	                        }
	                    }
		            },
		            cjjh_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            nbsbm_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            zjsl_zs: {
		            	validators: {
		                	regexp: {
		                        regexp: /^([1-9]|([1-9]\d+))$/,
		                        message: '只能输入大于1的正整数'
		                    },
		                    callback: {
		                        message: '装机数量不可大于库存数量',
		                        callback: function(value, validator) {
		                			var max = $("#zjsl_max").val();
		                			if(max && value && parseInt(value) > parseInt(max)){
		                				return false;
		                			}
		                            return true;
		                        }
		                    }
		                }
		            },
		            xlh_zs: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    },
		                	callback: {
		                        message: '时控件和定检件的序列号为必输项',
		                        callback: function(value, validator) {
		                        	var kzlx = $("#kzlx_zs").val();
		                			var isDj = $("#isDj_zs").val();
		                			var xlh = $("#xlh_zs").val();
		                			if(!xlh && (kzlx == 1 || kzlx == 2 || isDj == 1)){
		                				return false;
		                			}
		                            return true;
		                        }
		                    }
		                }
		            },
		            /*ywmc_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	*/
		            xkzh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	
		            shzh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },	
		            pch_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            llkbh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            azjldh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            ccjldh_zs: {
		                validators: {
		                    regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            parent_zs: {
		                validators: {
		                    notEmpty: {
		                        message: '父节点不能为空'
		                    }
		                }
		            },
		        }
		    });
	}
	
	/**
	 * 删除装上件信息
	 */
	function deleteMountComponent(btn){
		var tr = $(btn).parent().parent();
		tr.find("td:gt(6)>i.icon-edit").removeClass("icon-edit").addClass("icon-plus");
		tr.find("td:gt(7):lt(6)").empty();
		tr.find("td:gt(7):lt(6)").attr("title","");
		tr.find("td:eq(14)").html('<a href="javascript:void(0);" class="cursor-pointer" style="color:red" onclick="showTimeMonitorModal(this)"><u>待配置</u></a>');
		tr.find("td:eq(15)").html('<a href="javascript:void(0);" class="cursor-pointer" style="color:red" onclick="showFixedMonitorModal(this)"><u>待配置</u></a>');
		 // 加入到数据集合中
		var data = totalData.getDismountRecord(tr.attr("id"));
		var wz = data.on.wz;
		data.on = {};
		data.timeMonitor = {};
		data.fixedMonitor = [];
		data.zsIs = 0;
		data.zsWckcid = '';
		if(wz){
			calcCurrentLevelOneComponent(wz);
		}
	}
	
	//打开ATA章节号对话框
	function openChapterWin(){
		var zjh = $.trim($("#zjh_zs").val());
		FixChapterModal.show({
			selected:zjh,//原值，在弹窗中默认勾选
			callback: function(data){//回调函数
				var wczjh = '';
				var wczjhName = '';
				if(data != null){
					wczjh = data.zjh;
					wczjhName = data.zwms;
					wczjhName = wczjh + " " + wczjhName;
				}
				$("#zjh_zs").val(wczjh);
				$("#zjh_show_zs").val(wczjhName);
				$("#componentForm").data('bootstrapValidator').resetForm(false); 
			}
		})
	}
	
	/**
	  * 弹出选择父节点modal
	  */
	 function openChooseParent(){
		 $("#chooseParent").modal("show");
	 }
	 
	 /**
	  * 选择父节点事件
	  * @param event
	  * @param treeId
	  * @param treeNode
	  */
	function chooseParent(){
		var treeObj = $.fn.zTree.getZTreeObj("loadingList_tree");
		var treeNode = treeObj.getSelectedNodes()[0];
		$("#parent_zs").val(treeNode.id);
		$("#parent_show_zs").val(treeNode.displayName);
		$("#parent_show_zs").attr("title", treeNode.displayName);
		$("#componentForm").data('bootstrapValidator').resetForm(false); 
		if(treeNode.cj != 0){
			$("#wz_zs").val(treeNode.wz);
			$("#childenList_show").html('<input style="border:none;border-bottom:#ccc solid 1px;height:24px;width : 100%;margin-top:10px;" type="text" readonly="readonly">');
		}
		refreshStatus(parseInt(treeNode.cj)+1);
	}
	
