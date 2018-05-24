$(document).ready(function(){
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
				}
		);
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
		$('#stockout_open_alert').on('shown.bs.modal', function () {
			modalLeftRightResize()
		})
		
	
		
	})
	function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		modalLeftRightResize();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		modalLeftRightResize();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
    }
    function modalLeftRightResize(){
    	/*if( $("#modalPanelLeft").css("display")=="none"){*/
    		var modalPanelBody = parseInt($("#modalPanelRight").parents(".modal-body").css("max-height"));
    		var searchContent=$("#modalPanelRight .searchContent").outerHeight()||0;
    		var tableHeight=modalPanelBody-searchContent-50;
    		$("#modalPanelRight").find("table").parent().css({"max-height":tableHeight+"px","overflow":"auto"});
    		$("#modalPanelLeft").css({"min-height":"300px"});
    	/*}else{
    		 $("#modalPanelLeft").css({"height":"auto"});
        	 $("#modalPanelLeft").find("table").parent().css({"height":"auto"}); 
        	var modalPanelRight = $("#modalPanelRight").outerHeight()||0;
    		 var modalPanelLeft = $("#modalPanelLeft").outerHeight()||0;
    		 var modalLeftRowHeight= $("#modalPanelLeft .row-height").outerHeight()||0;
    		 
    		 if(modalPanelRight>modalPanelLeft){
    			 $("#modalPanelLeft").css({"height":modalPanelRight+"px","min-height":"300px"})
    		 }else{
    			 $("#modalPanelLeft").css({"height":modalPanelRight+"px","min-height":"300px"}); 
    			 $("#modalPanelLeft").find("table").parent().css({"height":(modalPanelRight-modalLeftRowHeight-20)+"px"}); 
    		 }
    	}*/
    	
    }
	function customResizeHeight(bodyHeight, tableHeight){
		$("#stockout_body").removeAttr("style");
		var panelFooter=$("#stockout_body").siblings(".panel-footer").outerHeight()||0
		var scrollDiv=$("#scrollDiv").outerHeight()||0;
		var panelBody=bodyHeight-panelFooter-scrollDiv;
		$("#receipt_table").parent().css({"height":(panelBody)+"px","overflow":"auto"});
	}
	//添加出库明细
	function stockOutOpen(){
		//出物类型为其他
		if($("#cklx").val()==7){
			$("#modalPanelLeft").css("display","none");
			$("#modalPanelRight").removeAttr("class");
			$("#modalPanelRight").addClass("col-xs-12 padding-left-0 padding-right-0");
		}else{
			$("#modalPanelLeft").css("display","block");
			$("#modalPanelRight").removeAttr("class");
			$("#modalPanelRight").addClass("col-xs-9 padding-right-0");
		}
		$("#stockout_open_alert").modal("show"); 
	}
	