/**
 * 航材历史合同价格
 */
open_win_contract_cost_his = {
	id:'open_win_contract_cost_his',
	loaded: false,//是否已加载
	data:[],
	pageSize: 10,
	param: {
		bjid:'',//部件ID
		bjh:'',
		multi:false, //是否多选 默认单选
		selected:null, //已经选择的
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(!this.loaded || isReload === true){
			this.initParam();
			this.load();
		}
	},
	//初始化参数
	initParam: function(){
		
	},
	//加载数据
	load : 	function(pageNumber, sortColumn, orderType){
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		
		var obj ={};
		obj['pagination.page']=pageNumber;
		obj['pagination.sort']=sortColumn;
		obj['pagination.order']=orderType;
		obj['pagination.rows']=this.pageSize;
		obj.paramsMap = {bjid:this.param.bjid, bjh:this.param.bjh};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/aerialmaterial/contract/list/costHistory",
			type: "post",
			dataType:"json",
			data:obj,
			success:function(data){
				if(data.rows && data.rows.length > 0){
					this_.data = data.rows;
					this_.appendContentHtml(data.rows);
					this_.appendPaginationHtml(data, sortColumn, orderType);						   
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"16\">暂无数据 No data.</td></tr>");
				}
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},		
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='"+this_.id+".rowonclick(event);' >";
			   } else {
				   htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.id+".rowonclick(event);' >";
			   }
			   htmlContent += "<td class='text-center'>"; 
			   if(this_.param.multi){
				   htmlContent += "<input type='checkbox' name='"+this_.id+"_list_radio' value='"+index+"'>"; 
			   }else{
				   htmlContent += "<input type='radio' name='"+this_.id+"_list_radio' value='"+index+"'>"; 
			   }
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-center'>"+(index + 1)+"</td>";
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.contract.htqdrq).substr(0, 10)+"</td>";
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.contract.htlsh)+"</td>";
			   htmlContent += "<td>"+StringUtil.escapeStr(row.contract.gysmc)+"</td>";
			   htmlContent += "<td class='text-right'>"+formatUndefine(row.htClf)+"</td>";  
			   htmlContent += "</tr>";
		   });
	   $("#"+this_.id+"_list").html(htmlContent);
	},
	//拼接分页
	appendPaginationHtml : function(data, sortColumn, orderType){
		var viewpagecount = 10;//每页显示12个分页便签
		var param = data.rows; //页面数据
		var total = data.total;//总数据量
		var size =  data.pageable.rows;//每页显示的条目数
		var pageCount = total % size==0? parseInt(total/size):parseInt(total/size)+1;//总的页数 
		var currentPage = 1; //记录当前页码 
		var subpagination ="";
	   
		for (var i=0;i<pageCount;i++){
			if (data.pageable.page == (i+1)) {
				currentPage = i + 1;
				break;
			}
		}
		var startpage = currentPage - (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1: viewpagecount / 2);//起始页
		var endpage = currentPage + viewpagecount / 2; //结束页 
		if (startpage < 1) {
			startpage = 1;
			if (pageCount >= viewpagecount){
				endpage = viewpagecount;
			}else{
				endpage = pageCount;
			}
		}
	   
		if (endpage > pageCount) {
			endpage = pageCount;
			if ((endpage - viewpagecount) > 0){
				startpage = endpage - viewpagecount + 1;
			}else{
				startpage = 1;
			}
		}
		var paginationContent = "";
		if (currentPage==1) { //当前为第一页时,不能向前翻页
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;</a></li>";
		} else {
		   paginationContent = paginationContent 
		   + "<li onclick=\""+this.id+".load(1,'"+sortColumn+"','"+orderType+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
		   + "<li onclick=\""+this.id+".load("+(currentPage-1)+",'"+sortColumn+"','"+orderType+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
		}
	   
		for (var index = startpage;index <= endpage;index++){
			if (index==currentPage){
				paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
			}else {
				paginationContent = paginationContent + "<li onclick=\""+this.id+".load("+index+",'"+sortColumn+"','"+orderType+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
			}
		}
		if (currentPage ==pageCount){
			paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
			paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
		}else {
			paginationContent = paginationContent + "<li onclick=\""+this.id+".load("+(currentPage+1)+",'"+sortColumn+"','"+orderType+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
			paginationContent = paginationContent + "<li onclick=\""+this.id+".load("+pageCount+",'"+sortColumn+"','"+orderType+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
		}
		$("#"+this.id+"_pagination").empty();
		$("#"+this.id+"_pagination").html(paginationContent);
	},
	//确认
	rowonclick: function(e){
		if(!this.param.multi){
			$(e.target).parent().find("input[type='radio']").attr("checked",true);
		}
	},
	save: function(){
		var $checkedRadio = $("#"+this.id+"_list").find("input:checked");
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(this.data[$checkedRadio.attr("value")]);
		}
		$("#"+this.id).modal("hide");
	},
	close: function(){
		$("#"+this.id).modal("hide");
	}
	
};