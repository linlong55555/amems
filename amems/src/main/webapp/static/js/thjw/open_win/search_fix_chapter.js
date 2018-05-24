FixChapterModal = {
		id : "FixChapterModal",
	paginationId:'fixChapterPagination',
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
//		ModalUtil.showSearchModal(this.param.parentWinId,this.id,this.paginationId);
		this.clearData();
		this.goPage(1,"zjh","asc");
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:100000};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			 url:basePath+"/project/fixchapter/selectFixChapter",
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
				   signByKeyword($("#keyword_fix_chapter_search").val(),[2,3,4],"#fixChapterList tr td");
			   } else {
				  $("#fixChapterList").empty();
				  $("#fixChapterPagination").empty();
				  $("#fixChapterList").append("<tr><td colspan=\"4\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_fix_chapter_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if (index % 2 == 0) {
				htmlContent += "<tr onclick='FixChapterModal.checkRow(this)'>";
			} else {
				htmlContent += "<tr onclick='FixChapterModal.checkRow(this)'>";
			}
			if(FixChapterModal.param.selected && FixChapterModal.param.selected == row.zjh){
				checked = "checked";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zjh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zwms)+"</td>";
			htmlContent += "</tr>";  
		    
		});
		$("#fixChapterList", $("#FixChapterModal")).empty();
		$("#fixChapterList", $("#FixChapterModal")).html(htmlContent);
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
		var $checkedRadio = $("#fixChapterList", $("#FixChapterModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0&&index!=undefined){			
				var chapter = this.data[index];
				chapter.zwms = formatUndefine(chapter.zwms);
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