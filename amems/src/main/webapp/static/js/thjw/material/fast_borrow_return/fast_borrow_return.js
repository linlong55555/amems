$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$("#customDiv").html('<input id="MAC_LIMIT" name="MAC_LIMIT"  type="checkbox" checked="checked" data-on-text="借用" data-off-text="归还" data-on-color="success" data-off-color="warning" />');	
		$("#MAC_LIMIT").bootstrapSwitch();
		//借用归还切换事件
		$('input[name="MAC_LIMIT"]').on('switchChange.bootstrapSwitch', function(event, state) {
			  if(!state){
				  $("#borrowed_tool_header .chinese_title").text("待归还工具");
				  $("#borrowed_tool_header .english_title").text("Restitution tools");
				  $("#borrower_header .chinese_title").text("归还人");
				  $("#borrower_header .english_title").text("Restitution");
				  $("#keyword_tool").attr("placeholder","请输入待归还工具编号");
				  $("#fastBorrowReturnBodyHeight").find(".row-height").css("display","none");
				  App.resizeHeight();
			  }else{
				  $("#borrowed_tool_header .chinese_title").text("待借用工具");
				  $("#borrowed_tool_header .english_title").text("Tools to be borrowed"); 
				  $("#borrower_header .chinese_title").text("借用人");
				  $("#borrower_header .english_title").text("Borrower");
				  $("#keyword_tool").attr("placeholder","请输入待借用工具编号");
				  $("#fastBorrowReturnBodyHeight").find(".row-height").css("display","block");
				  $("#longCheckbox").prop("checked",false);
				  $("#longReturn").css("visibility","hidden");
				  App.resizeHeight();
			  }
			});
		//快捷键方式
		$(document).keypress(function(e) {
			if (e.ctrlKey && e.which == 13)
			alert("点击确定按钮的操作")
		})
	});
    
    function enterTool(e){
    	if(e.keyCode){
    		alert("匹配查询");
    	}
    }
    
    function customResizeHeight(bodyHeight, tableHeight){
        var footConfirmHeight = $(".footConfirm:visible").outerHeight()||0;
    	var rowHeight = $('.row-height:visible').outerHeight()||0;
    	$("#fastBorrowReturnBodyHeight").removeAttr("style");
    	$("#fastBorrowReturnBodyHeight").css("height",(bodyHeight-footConfirmHeight-rowHeight-8)+"px");
    	var fastBorrowReturnBodyHeight=(bodyHeight-footConfirmHeight-rowHeight-8);
    	for(var i=0;i<$("#fastBorrowReturnBodyHeight .childContent").length;i++){
    		var header=$("#fastBorrowReturnBodyHeight .childContent").eq(i).find(".panel-heading").outerHeight()||0;
    		var rowHeightChild=$("#fastBorrowReturnBodyHeight .childContent").eq(i).find('.row-height:visible').outerHeight()||0
    		$("#fastBorrowReturnBodyHeight .childContent").eq(i).find(".panel-body").find("table").parent("div").css({"height":(fastBorrowReturnBodyHeight-header-30-rowHeightChild)+"px","overflow":"auto"})
    	}
    	
    	
    }
    //关闭提示框
    function closeAlert(obj){
    	$(obj).parent('.alert').css("display","none");
    	App.resizeHeight();
    }
    //行选中
    function selectOneRow(obj){
    	$(obj).find("td").eq(0).find("input").attr("checked",'true');
    	$(obj).parent("tbody").find("tr").removeClass("bg_tr");
    	$(obj).addClass("bg_tr");
    }
    //长期借用
    function checkboxSelect(obj){
    	if($(obj).prop("checked")){
    		$("#longReturn").css("visibility","visible");
    	}else{
    		$("#longReturn").css("visibility","hidden");
    	}
    	
    }