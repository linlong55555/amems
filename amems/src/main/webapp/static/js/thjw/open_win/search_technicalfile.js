TechnicalfileModal = {
	paginationId:'TechnicalfileList',
	id:'TechnicalfileModal',
	param: {
		selected:null,
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		id:null,
		parentid:null,//第一层modal
		clearJswj: true,
	},
	data:[],
	show : function(param){
		$("#TechnicalfileModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearJswj == false){
			$("#jswj_btn_clear").hide();
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
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
		   url:basePath+"/project2/airworthiness/queryAll",
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
						renderTo : "technicalfilePagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_technicalfile_search").val(),[4,7],"#technicalfileList tr td");
			   } else {
				  $("#technicalfileList").empty();
				  $("#technicalfilePagination").empty();
				  $("#technicalfileList").append("<tr><td colspan=\"9\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   //new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.keyword = $.trim($("#keyword_technicalfile_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.zt = 1;
		 paramsMap.id = this.param.id;
		 searchParam.paramsMap=paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if (index % 2 == 0) {
				htmlContent += "<tr onclick='TechnicalfileModal.checkRow(this)'>";
			} else {
				htmlContent += "<tr onclick='TechnicalfileModal.checkRow(this)'>";
			}
			if(TechnicalfileModal.param.selected && TechnicalfileModal.param.selected == row.id){
				checked = "checked";
			}
				htmlContent += "<td class='text-center' style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjlx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jswjlx))+"</td>";
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjly))+"'>"+StringUtil.escapeStr(formatUndefine(row.jswjly))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjbh))+"'>"+StringUtil.escapeStr(formatUndefine(row.jswjbh))+"</td>";
			    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.bb))+"'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.xzah))+"'>"+StringUtil.escapeStr(formatUndefine(row.xzah))+"</td>";
			    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jswjzt))+"'>"+StringUtil.escapeStr(formatUndefine(row.jswjzt))+"</td>"; 
			    htmlContent += "<td class='text-left' title='"+(row.scfj?StringUtil.escapeStr(formatUndefine(row.scfj.wbwjm +"."+ row.scfj.hzm)):'')+"'>" +
	    					   "<a href=\"javascript:downloadfile('"+(row.scfj?row.scfj.id:'#')+"')\">"+(row.scfj?StringUtil.escapeStr(formatUndefine(row.scfj.wbwjm +"."+ row.scfj.hzm)):'')+"</a></td>"; 
			    if(row.zj){
			    	htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ata))+" "+(row.zj?StringUtil.escapeStr(formatUndefine(row.zj.ywms)):'')+"'>"+StringUtil.escapeStr(formatUndefine(row.ata))+" "+(row.zj?StringUtil.escapeStr(formatUndefine(row.zj.ywms)):'')+"</td>"; 
			    }else{
			    	htmlContent += "<td class='text-left' ></td>"; 
			    }
			    htmlContent += "</tr>"
		    
		});
		$("#technicalfileList", $("#TechnicalfileModal")).empty();
		$("#technicalfileList", $("#TechnicalfileModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData : function(){
		$("#keyword_technicalfile_search").val('');
	},
	setData: function(){
		var $checkedRadio = $("#technicalfileList", $("#TechnicalfileModal")).find("input:checked");
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
	},
	clearJswj:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}	
	}
	
}

function appendjxfw(list){
	var htmlContent="";
	 if(list == null){
		 return htmlContent;
	 }
	 $.each(list,function(index,row){
		 if(index==0){
			 htmlContent += row.fjjx
		 }else{
			 htmlContent +=","+row.fjjx
		 }
	 });
	 return htmlContent;
}
