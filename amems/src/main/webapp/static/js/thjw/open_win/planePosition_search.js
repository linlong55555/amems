PositionModal = {
	paginationId:'PositionPagination',
	id:'PositionModal',
	viewPositionId:'um_selectPosition',
	param: {
		selected:null,
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		modalHeadCN : '飞机站位列表',
		modalHeadENG : 'A/C Type Position List',
		id:null,
		jx:null,
		lx:3,
		parentid:null,//第一层modal
	},
	data:[],
	show : function(param){
		var this_ = this;
		$("#PositionModal").modal("show");
		this_.initMulti();
		$("#keyword_Position_search").val("");
		if(param){
			$.extend(this.param, param);
		}
		this.initModelHead();
		this_.clearData();
		this_.goPage(1,"auto","desc");
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	TheEcho : function (){
		
		
		
		
		var this_ = this;
		if(this_.param.selected != null && this_.param.selected.length > 0 ){
			var list = this_.param.selected;
			$.each(list,function(index,row){
				this_.addTaginput(row);
				$("#"+ row.id).attr("checked",true);
			});
			this_.param.selected = null;
		}else{
			var list = $("#"+this.viewPositionId).tagsinput('items');
			$.each(list,function(index,row){
				$("#"+ row.id).attr("checked",true);
			});
		}
		
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},
	initMulti : function(){
		var this_= this;
		$("#"+this_.viewPositionId).tagsinput({
		   itemValue: 'id' 
		  ,itemText: 'sz'
		  ,maxTags: 1000
		});
		
		$('#'+this_.viewPositionId).on('itemRemoved', function(event) {
			if(event.item != null && event.item != undefined &&  event.item != 'undefined'){
				this_.romoveTagInput(event.item.id);
			}
		});
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
		   url:basePath+"/basic/station/selectByJx",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total != null &&  data.total > 0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : "PositionPagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
					    goPage: function(a,b,c){
					    	this_.AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   //回显选中
				   this_.TheEcho();
				   // 标记关键字
				   signByKeyword($("#keyword_Position_search").val(),[2,3],"#PositionList tr td");
			   } else {
				  $("#PositionList").empty();
				  $("#PositionPagination").empty();
				  $("#PositionList").append("<tr><td colspan=\"3\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_Position_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.jx = this.param.jx;
		 searchParam.lx = this.param.lx;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if (index % 2 == 0) {
				htmlContent += "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='PositionModal.clickRow("+index+",this)'>";
			} else {
				htmlContent += "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='PositionModal.clickRow("+index+",this)'>";
			}
				htmlContent += "<td class='text-center' style='text-align:center;vertical-align:middle;'>" +
						"<input type='checkbox' id='"+row.id+"' name='checkrow' index='"+index+"' "+checked+" onclick=\"PositionModal.checkRow("+index+",this)\" /></td>";  
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.sz))+"'>"+StringUtil.escapeStr(formatUndefine(row.sz))+"</td>";
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ms))+"'>"+StringUtil.escapeStr(formatUndefine(row.ms))+"</td>";
			    htmlContent += "</tr>"
		    
		});
		$("#PositionList").empty();
		$("#PositionList").html(htmlContent);
	},
	search: function(){
		var this_ = this;
		//this_.param.selected = [];
		//this_.clearData();
		this_.goPage(1,"auto","desc");
	},
	checkRow: function(index,this_){
		var flag = $(this_).is(":checked");
		if(flag){
			$(this_).removeAttr("checked");
			this.romoveTagInput(this.data[index].id);
		}else{
			$(this_).attr("checked",true);
			this.addTaginput(this.data[index]);
		}
	},
	clickRow: function(index,this_){
		var $checkbox1 = $("#PositionList :checkbox[name='checkrow']:eq("+index+")");
		var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
		var checked = $checkbox1.is(":checked");
		$checkbox1.attr("checked", !checked);
		$checkbox2.attr("checked", !checked);
		
		if(checked){
			this.romoveTagInput(this.data[index].id);
		}else{
			this.addTaginput(this.data[index]);
		}
	},
	clearData : function(){
		$("#"+this.viewPositionId).tagsinput('removeAll');
	},
	addTaginput : function(row){
		$("#"+this.viewPositionId).tagsinput('add', row);
	},
	romoveTagInput : function(id){
		$("#"+this.viewPositionId).tagsinput('remove', id);
		$("#"+id).attr("checked",false);
	},
	setData: function(){
		var data = $("#"+this.viewPositionId).tagsinput('items');
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	},
	performSelectAll:function(){
		var this_=this;
		$(":checkbox[name=checkrow]").attr("checked", true);
		var list=this_.data
		$.each(list,function(index,row){
			this_.addTaginput(row);
		});
	},
	performSelectClear:function(){
		var this_=this;
		$(":checkbox[name=checkrow]").attr("checked", false);
		var list=this_.data
		$.each(list,function(index,row){
			this_.romoveTagInput(row.id);
		});
	}
	
}
