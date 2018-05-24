
$(function(){
});

//打开拆下件对话框
function searchDisassemblyComponentModal(btn) {
	$("#wz_tableView").val("");
	$("#hidden_disassemblyComponent").val($(btn).parent().parent().attr("id"));
	$("#disassemblyComponentModal").modal("show");
	goPageDisassemblyComponent(1,"auto","desc");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPageDisassemblyComponent(pageNumber,sortType,sequence){
	AjaxDisassemblyComponentSearch(pageNumber,sortType,sequence);
}	
//信息检索
function searchDisassemblyComponent(){
	goPageDisassemblyComponent(1,"auto","desc");
}

//带条件搜索的翻页
function AjaxDisassemblyComponentSearch(pageNumber,sortType,sequence){
	var searchParam = getDisassemblyComponentSearchParam();
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/productionplan/loadingList/queryTable",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendDisassemblyComponentContentHtml(data.rows);
			   if($("#forwordType").val() != 3){
				   new Pagination({
						renderTo : "disassemblyComponentPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(pageNumber,sortType,sequence){
							AjaxDisassemblyComponentSearch(pageNumber,sortType,sequence);
						}
					}); 
			   }
			   FormatUtil.removeNullOrUndefined();
			   // 标记关键字
			   signByKeyword($("#component_disassembly_search").val(),[2,3,4,5,6,7,8],"#disassemblyComponentList tr td");
		   } else {
			   $("#disassemblyComponentList").empty();
			   $("#disassemblyComponentPagination").empty();
			   $("#disassemblyComponentList").append("<tr><td colspan=\"12\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
//将搜索条件封装 
function getDisassemblyComponentSearchParam(){
	 var searchParam = {};
	 searchParam.keyword = $.trim($("#component_disassembly_search").val());
	 searchParam.fjzch = $.trim($("#fjzch").val());
	 searchParam.dprtcode = $.trim($("#dprtcode").val());
	 searchParam.zt = 1;
	 searchParam.sxzt = 1;
	 searchParam.notCj = 0;
	 searchParam.wz =  $.trim($("#wz_tableView").val());
	 searchParam.beginScrq =  $.trim($("#beginScrq_tableView").val());
	 searchParam.endScrq =  $.trim($("#endScrq_tableView").val());
	 searchParam.azjldh =  $.trim($("#azjldh_tableView").val());
	 searchParam.beginAzrq =  $.trim($("#beginAzrq_tableView").val());
	 searchParam.endAzrq =  $.trim($("#endAzrq_tableView").val());
	 searchParam.ccjldh =  $.trim($("#ccjldh_tableView").val());
	 searchParam.beginCcrq =  $.trim($("#beginCcrq_tableView").val());
	 searchParam.endCcrq =  $.trim($("#endCcrq_tableView").val());
	 searchParam.llklx =  $.trim($("#llklx_tableView").val());
	 searchParam.llkbh =  $.trim($("#llkbh_tableView").val());
	 searchParam.bz =  $.trim($("#bz_tableView").val());
	 searchParam.kzlx =  $.trim($("#kzlx_tableView").val());
	 searchParam.isDj =  $.trim($("#isDj_tableView").val());
	 var id = $("#hidden_disassemblyComponent").val();
	 var data = totalData.getDismountRecord(id).on;
	 if(data.jh){
		 searchParam.wz = data.wz;
		 $("#wz_tableView").val(data.wz);
		 $("#wz_tableView").attr("disabled","disabled");
		 searchParam.fjdid = data.fjdid;
	 }else{
		 $("#wz_tableView").removeAttr("disabled");
	 }
	 var notIds = [];
	 $(totalData.finishedWork).each(function(){
		 $(this.dismountRecord).each(function(){
			 if(this.cxZjqdid){
				 notIds.push(this.cxZjqdid);
			 }
		 });
	 });
	 searchParam.notIds = notIds;
	 var cxjid = totalData.getDismountRecord(id).cxZjqdid;
	 if(cxjid){
		 searchParam.paramsMap =  {
				 cxjid : cxjid
		 };
	 }
	 return searchParam;
}
	function appendDisassemblyComponentContentHtml(list){
		var htmlContent = '';
		$.each(list,function(index,row){
		var zjhms = $.trim((row.zjh || "") + "  "+ (row.zjhms || ""));
		var wzms = DicAndEnumUtil.getEnumName('planeComponentPositionEnum',row.wz);
		var id = $("#hidden_disassemblyComponent").val();
		var cxjid = totalData.getDismountRecord(id).cxZjqdid;
		var cxjldh = totalData.getDismountRecord(id).cxjldh;
		var cxBz = totalData.getDismountRecord(id).cxBz;
		htmlContent += ["<tr style='cursor:pointer' bgcolor='"+(index % 2 == 0 ? "#f9f9f9" : "#fefefe")+"'>",
		                   "<td class='text-center'>",
		                   "<input name='disassemblyComponent' type='radio' value='"+row.id+"' "+(row.id==cxjid?"checked='checked'":"")+">",
		                   "</td>",
		                   "<td style='text-align:center'>" + (row.cj <= 1 ? "*" : "") + "</td>",
		                   "<td title='"+StringUtil.escapeStr(zjhms)+"'>"+StringUtil.escapeStr(zjhms)+"</td>",
		                   "<td title='"+StringUtil.escapeStr(row.ywmc)+"'>"+StringUtil.escapeStr(row.ywmc)+"</td>",
		                   "<td title='"+StringUtil.escapeStr(row.zwmc)+"'>"+StringUtil.escapeStr(row.zwmc)+"</td>",
		                   "<td title='"+StringUtil.escapeStr(row.jh)+"'>"+StringUtil.escapeStr(row.jh)+"</td>",
		                   "<td title='"+StringUtil.escapeStr(row.xlh)+"'>"+StringUtil.escapeStr(row.xlh)+"</td>",
		                   "<td title='"+StringUtil.escapeStr(row.nbsbm)+"'>"+StringUtil.escapeStr(row.nbsbm)+"</td>",
		                   "<td title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>",
		                   "<td title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>",
		                   "<td title='"+row.zjsl+"'>"+row.zjsl+"</td>",
		                   "<td title='"+wzms+"'>"+wzms+"</td>",
		                   "<input type='hidden' name='cj' value='"+row.cj+"'>",
		                   "<input type='hidden' name='wz' value='"+row.wz+"'>",
		                   "<input type='hidden' name='fjdid' value='"+row.fjdid+"'>",
		                   "</tr>"
		                   ].join("");
	   $("#disassemblyComponentList").empty();
	   $("#disassemblyComponentList").html(htmlContent);
	   $("#disassemblyComponent_cxdh").val(cxjldh);
	   $("#disassemblyComponent_cxbz").val(cxBz);
	   if($("#forwordType").val() == 3){
		   $("#disassemblyComponentList input:radio:not([checked])").parent().parent().remove();
	   }
   });
		
}
	
	/**
	 * 绑定行选择事件
	 */
	$("#disassemblyComponentList > tr").live("click", function(){
		if($("#forwordType").val() != 3){
			   $(this).find("input[type='radio']").attr("checked",true);
		 }
	});
	
	 function moreDisassemblyComponent() {
		if ($("#divSearchDisassemblyComponent").css("display") == "none") {
			$("#divSearchDisassemblyComponent").css("display", "block");
			$("#iconDisassemblyComponent").removeClass("icon-caret-down");
			$("#iconDisassemblyComponent").addClass("icon-caret-up");
		} else {

			$("#divSearchDisassemblyComponent").css("display", "none");
			$("#iconDisassemblyComponent").removeClass("icon-caret-up");
			$("#iconDisassemblyComponent").addClass("icon-caret-down");
		}
	}
	 
	 //搜索条件重置
	function disassemblyComponentSearchreset(){
		$("#divSearchDisassemblyComponent").find("input").each(function() {
			$(this).attr("value", "");
		});
		
		$("#divSearchDisassemblyComponent").find("textarea").each(function(){
			$(this).val("");
		});
		 
		$("#divSearchDisassemblyComponent").find("select").each(function(){
			$(this).val("");
		});
		$("#component_disassembly_search").val("");
	 }

