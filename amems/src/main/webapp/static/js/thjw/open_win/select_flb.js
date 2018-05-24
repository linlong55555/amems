SelectFlbModal = {
		id : "SelectFlbModal",
	paginationId:'fixChapterPagination',
	param: {
		parentWinId : '',
		selected:null,
		clearZjh: true,
		fjzch : null,
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
		this.goPage(1,"auto","desc");
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
		   url:basePath+"/produce/flb/queryAllList",
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
				   signByKeyword($("#keyword_SelectFlb").val(),[2],"#SelectFlbList tr td");
			   } else {
				  $("#SelectFlbList").empty();
				  $("#fixChapterPagination").empty();
				  $("#SelectFlbList").append("<tr><td colspan=\"5\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_SelectFlb").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.fjzch = this.param.fjzch;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			htmlContent += "<tr onclick='SelectFlbModal.checkRow(this)'>";
			console.info(SelectFlbModal.param.selected);
			console.info(SelectFlbModal.param.selected);
			if(SelectFlbModal.param.selected && SelectFlbModal.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td  style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.fxjlbh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jlbym)+"</td>";  
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('flbStatusEnum',row.zt))+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
			htmlContent += "</tr>";  
		    
		});
		$("#SelectFlbList", $("#SelectFlbModal")).empty();
		$("#SelectFlbList", $("#SelectFlbModal")).html(htmlContent);
		 TableUtil.addTitle("#SelectFlbList tr td"); //加载td title
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData : function(){
		$("#keyword_SelectFlb").val('');
	},
	setData: function(){
		var $checkedRadio = $("#SelectFlbList", $("#SelectFlbModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0&&index!=undefined){			
				var chapter = this.data[index];
				chapter.gdbh = formatUndefine(chapter.gdbh);
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