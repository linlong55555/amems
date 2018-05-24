alertModalZl = {
	id:'alertModalZl',
	tbodyId:'zlhlist',
	paginationId:'zlhpagination',
	isLoad:false,
	param: {
		checkUserList:null,
		callback:function(){}
	},
	data:[],
	show : function(param){
		//初始化时加载数据
		if(!this.isLoad){
			this.initMulti();
			this.isLoad = true;
		}
		this.clearData();//清空数据
		if(param){
			this.param.checkUserList=param;
			$.extend(true,this.param, param);
		}
		//回显数据
		if(this.param.checkUserList){
			this.returnViewData(this.param.checkUserList);
		}
		$("#"+this.id+" .bootstrap-tagsinput").css("width","100%");
		$("#"+this.id).modal("show");
		
		this.goPage(1,"auto","desc");
	},
	initMulti : function(){
		$("#"+this.id+"_selectzlh").tagsinput({
		  itemValue: 'id',
		  itemText: 'gdbh'
		});
	},
	clearData : function(){
		$("#"+this.id+"_selectzlh").tagsinput('removeAll');
		$("#keyword_search1").val("");
	},
	returnViewData : function(checkUserList){
		var this_ = this;
		$.each(checkUserList, function(i, row){
			$("#"+this_.id+"_selectzlh").tagsinput('add', row);
		});
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var this_ = this;
		var searchParam = this_.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/produce/fault/monitoring/queryWorkorderList",
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
				   signByKeyword($.trim($("#keyword_search1").val()),[2],"#"+this_.tbodyId+" tr td");
			   } else {
				   this_.data = [];
				   $("#"+this_.tbodyId, $("#"+this_.id)).empty();
				   $("#"+this_.paginationId, $("#"+this_.id)).empty();
				   $("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan=\"8\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search1").val());
		 searchParam.dprtcode=$("#dprtId").val();
		 searchParam.fjzch=$("#fjzchid").val();
		 return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='"+this_.id+".checkRow("+index+")'>";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.id+".checkRow("+index+")'>";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='hidden' id='id' />"+(index+1)+"</td>";	
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.gdbh)+"</td>"; 
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workorderTypeEnum',row.gdlx))+"</td>"; 
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.gdbt)+"</td>"; 
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.lyrwh)+"</td>"; 
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.zdrq).substring(0,10)+"</td>"; 
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(row.jhJsrq).substring(0,10)+"</td>"; 
		   htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt))+"</td>"; 
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#"+this_.tbodyId, $("#"+this_.id)).empty();
	   $("#"+this_.tbodyId, $("#"+this_.id)).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(index){
		$("#"+this.id+"_selectzlh").tagsinput('add', this.data[index]);
	},
	setData: function(){
		
		var data = $("#"+this.id+"_selectzlh").tagsinput('items');
		if(data == null || data == ""){
			AlertUtil.showErrorMessage("请选择指令号");
			return false;
		}
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	}
}