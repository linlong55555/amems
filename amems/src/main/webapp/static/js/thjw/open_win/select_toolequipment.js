selecttoolequipmentModal = {
	id : "selecttoolequipmentModal",
	paginationId:'selecttoolequipmentPagination',
	param: {
		parentWinId : '',
		selected:null,
		clearZjh: true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data:[],
	show : function(param){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearZjh == false){
			$("#zjh_btn_clear").hide();
		}
		this.clearData();
		this.goPage(1,"auto","asc");
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_fix_chapter_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/stock/queryAllPageToolsList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_fix_chapter_search").val(),[2,3,4],"#selecttoolequipmentList tr td");
			   } else {
				  $("#selecttoolequipmentList").empty();
				  $("#selecttoolequipmentPagination").empty();
				  $("#selecttoolequipmentList").append("<tr><td colspan=\"11\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	
	appendContentHtml: function(list){
		var this_=this;
		
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if (index % 2 == 0) {
				htmlContent += "<tr onclick='selecttoolequipmentModal.checkRow(this)'>";
			} else {
				htmlContent += "<tr onclick='selecttoolequipmentModal.checkRow(this)'>";
			}
			if(this_.param.selected && this_.param.selected == row.sn){
				checked = "checked";
			}else{
				checked = "";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zwms)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sn)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.pch)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',StringUtil.escapeStr(row.hclx))+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gg)+"</td>";
			htmlContent += "<td  style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.kcsl)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jldw)+"</td>";
			htmlContent += "</tr>";  
		    TableUtil.addTitle("#selecttoolequipmentList tr td"); //加载td title
		});
		$("#selecttoolequipmentList", $("#selecttoolequipmentModal")).empty();
		$("#selecttoolequipmentList", $("#selecttoolequipmentModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData : function(){
		$("#keyword_fix_chapter_search").val('');
	},
	setData: function(){
		var $checkedRadio = $("#selecttoolequipmentList", $("#selecttoolequipmentModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0&&index!=undefined){			
				var chapter = this.data[index];
				chapter.sn = formatUndefine(chapter.sn);
				chapter.bjh = formatUndefine(chapter.bjh);
				chapter.ywms = formatUndefine(chapter.ywms);
				chapter.zwms = formatUndefine(chapter.zwms);
				chapter.pch = formatUndefine(chapter.pch);
				chapter.xh = formatUndefine(chapter.xh);
				chapter.gg = formatUndefine(chapter.gg);
				chapter.kcsl = formatUndefine(chapter.kcsl);
				chapter.jldw = formatUndefine(chapter.jldw);
				this.param.callback(chapter);
			}else{
				
			}
		}
		this.close();
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	clearZjh:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		this.close();
	}
}