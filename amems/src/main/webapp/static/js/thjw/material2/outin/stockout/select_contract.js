var xgfs=[];	
	xgfs[1]='航材供应商';
	xgfs[2]='外委供应商';
	xgfs[3]='客户';
SelectSourceorderModal = {
		id : "SelectSourceorderModal",
	paginationId:'SelectSourceorderPagination',
	param: {
		parentWinId : '',
		selected:null,
		htlx : null,
		zt : null,
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
		var this_ = this;
		var url = "/material/outin/contractlist"; 	
		var searchParam = this.gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.pagination = pagination;
		startWait();
		AjaxUtil.ajax({
			 url:basePath+url,
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
						extParams:{},
						goPage: function(a,b,c){
							this_.AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_SelectSourceorder").val(),[2],"#SelectSourceorderList tr td");
			   } else {
				  $("#SelectSourceorderList").empty();
				  $("#SelectSourceorderPagination").empty();
				  $("#SelectSourceorderList").append("<tr><td colspan=\"7\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.htlx = this.param.htlx;
		 var paramsMap={};
		 paramsMap.ztList=this.param.zt;
		 paramsMap.keyword=$.trim($("#keyword_SelectSourceorder").val());
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			htmlContent += "<tr onclick='SelectSourceorderModal.checkRow(this)'>";
			if(SelectSourceorderModal.param.selected && SelectSourceorderModal.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hth)+"</td>";
			htmlContent += "<td  style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.htrq).substring(0,10)+"</td>";  
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.biz)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jffs)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(xgfs[row.xgflx])+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xgfms)+"</td>";
			htmlContent += "</tr>";  
		    
		});
		$("#SelectSourceorderList", $("#SelectSourceorderModal")).empty();
		$("#SelectSourceorderList", $("#SelectSourceorderModal")).html(htmlContent);
		 TableUtil.addTitle("#SelectSourceorderList tr td"); //加载td title
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData : function(){
		$("#keyword_SelectSourceorder").val('');
	},
	setData: function(){
		var $checkedRadio = $("#SelectSourceorderList", $("#SelectSourceorderModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0&&index!=undefined){			
				var chapter = this.data[index];
				chapter.lyid = formatUndefine(chapter.id);
				chapter.lybh = formatUndefine(chapter.hth);
				chapter.lydw = formatUndefine(chapter.xgfms);
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