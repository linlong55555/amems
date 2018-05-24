
$(function(){
	// 借用归还初始化
	borrowReturn.init();
});
  
/**
 * 借用归还
 */
var borrowReturn = {
	/**
	 * 初始化
	 */
	init : function(){
		
		var this_ = this;
		
		// 初始化导航
		this_.initNavigation();
		
		// 初始化控件
		this_.initWidget();
		
		// 初始化事件
		this_.initEvent();
		
		// 初始化数据字典和枚举
		this_.initDicAndEnum();
		
		// 重置数据
		this_.resetData();
		
		// 选中搜索输入框
		this_.focusSearchInput();
	},
	
	/**
	 * 初始化导航
	 */
	initNavigation : function(){
		Navigation(menuCode,"","");
	},
	
	/**
	 * 初始化控件
	 */
	initWidget : function(){
		$("#borrowOrReturn").bootstrapSwitch();
	},
	
	/**
	 * 初始化事件
	 */
	initEvent : function(){
		var this_ = this;
		
		// 切换借用/归还
		$('#borrowOrReturn').on('switchChange.bootstrapSwitch', function(event, state) {
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
			  $("#borrow_return_tool_list").html('<tr class="no-data"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>');
			  $("#borrow_return_user_list").html('<tr class="no-data"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>');
		 });
		
		// 快捷键
		$(document).keypress(function(e){
			// Enter
			if(!e.ctrlKey && e.keyCode == 13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					this_.search();; //调用主列表页查询
				}
		    }
			// Ctrl + Enter
			else if(e.ctrlKey && e.which == 13){
				this_.submitData();
			}
		});
	},
	
	/**
	 * 初始化数据字典和枚举
	 */
	initDicAndEnum : function(){
		
	},
	
	/**
	 * 行选中-radio
	 */
	selectOneRow : function(obj){
		$(obj).find("input:radio").attr("checked",'true');
    	$(obj).parent("tbody").find("tr").removeClass("bg_tr");
    	$(obj).addClass("bg_tr");
	},
	
	/**
	 * 行选中-checkbox
	 */
	checkOneRow : function(obj, event){
		if ($(event.target).attr("type") == "checkbox") {
			return;
		}
		var $checkbox = $(obj).find("input[type='checkbox']");
		$checkbox.attr("checked", !$checkbox.is(":checked"));
	},
	
	/**
	 * 关闭消息提示
	 */
	closeAlertMessage : function(obj){
		$("#alert_msg_div").css("display","none");
    	App.resizeHeight();
	},
	
	/**
	 * 切换长期借用
	 */
	toggleLongBorrow : function(obj){
		if($(obj).prop("checked")){
    		$("#longReturn").css("visibility","visible");
    	}else{
    		$("#longReturn").css("visibility","hidden");
    	}
	},
	
	/**
	 * 查询
	 */
	search : function(){
		
		var this_ = this;
		var keyword = $.trim($("#keyword_tool").val());
		if(!keyword){
			AlertUtil.showErrorMessage("请输入待借用的工具编号！");
			return false;
		}
		// 关闭消息框
		this_.closeAlertMessage();
		
		// 关键字为json格式（扫码）
		if(this_.isJson(keyword)){
			
			var obj = JSON.parse(keyword);
			
			// 人员
			if(obj.DATATYPE == "1-ry"){
				// 添加人员
				this_.loadUser(obj);
			}
			// 工具
			else if(obj.DATATYPE == "2-kc"){
				// 添加工具
				this_.loadTool(obj);
			}
		}
		// 关键字为非json格式（手工输入关键字）
		else{
			// 根据关键字添加用户/工具
			this_.loadByKeyword(keyword);
		}
	},
	
	/**
	 * 添加人员
	 */
	loadUser : function(obj){
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
			url: basePath+"/material/toolborrowreturn/loaduser",
			type: "post",
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				id : obj.ID,
				type : this_.getType()
			}),
			success : function(data){
				finishWait();
				this_.appendUserToHtml(data);
				this_.focusSearchInput();
		    },
		    error : function(data) {
				finishWait();
				this_.alertErrorMessage(data.responseText);
				this_.focusSearchInput();
			},
		}); 
	},
	
	/**
	 * 拼接用户
	 */
	appendUserToHtml : function(obj){
		// 相同数据只存在一条
		if($("#borrow_return_user_list>#"+obj.id).length >= 1){
			return false;
		}
		var isChecked = $("#borrow_return_user_list>:not(.no-data)").length == 0;
		console.log(obj);
		var html = ["<tr id='"+StringUtil.escapeStr(obj.id)+"' onclick='borrowReturn.selectOneRow(this)'>",
			            "<td style='vertical-align: middle;' class='text-center'>",
			            	"<button class='line6 line6-btn' onclick='borrowReturn.deleteUser(this)' type='button'>",
			            		"<i class='icon-minus cursor-pointer color-blue cursor-pointer'></i>",
		            		"</button>",
			            "</td>",
		            	"<td class='text-center' style='vertical-align:middle;'>",
		            		"<input type='radio' name='chooseCheckbox' "+(isChecked?"checked=checked":"")+"/>",
	            		"</td>",
	            		"<td>",
	            			"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.username)+"'>"+StringUtil.escapeStr(obj.username)+"</p>",
	            			"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.realname)+"'>"+StringUtil.escapeStr(obj.realname)+"</p>",
            			"</td>",
            			"<td>",
            				"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.paramsMap?obj.paramsMap.dprtcode:obj.dprtbh)+"'>"+StringUtil.escapeStr(obj.paramsMap?obj.paramsMap.dprtcode:obj.dprtbh)+"</p>",
            				"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.paramsMap?obj.paramsMap.dprtname:obj.dprtname)+"'>"+StringUtil.escapeStr(obj.paramsMap?obj.paramsMap.dprtname:obj.dprtname)+"</p>",
        				"</td>",
        				"<td class='text-center' style='vertical-align:middle;'>",
        					obj.sex == 2 ? "女" : "男",
    					"</td>",
    					"<input name='zrrmc' type='hidden' value='"+StringUtil.escapeStr(obj.username)+" "+StringUtil.escapeStr(obj.realname)+"'>",
		            "</tr>"].join("");
		$("#borrow_return_user_list>.no-data").remove();
		$("#borrow_return_user_list").append(html);
	},
	
	/**
	 * 添加工具
	 */
	loadTool : function(obj){
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
			url: basePath+"/material/toolborrowreturn/loadtool",
			type: "post",
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				id : obj.ID,
				type : this_.getType(),
				bjh : obj.DATALIST ? obj.DATALIST[0].BJH : ""
			}),
			success : function(data){
				finishWait();
				this_.appendToolToHtml(data, true);
				this_.focusSearchInput();
		    },
		    error : function(data) {
		    	finishWait();
				this_.alertErrorMessage(data.responseText);
				this_.focusSearchInput();
			},
		}); 
	},
	
	/**
	 * 拼接工具
	 */
	appendToolToHtml : function(obj, isChecked){
		// 相同数据只存在一条
		if($("#borrow_return_tool_list>#"+obj.id).length >= 1){
			return false;
		}
		var html = ["<tr id='"+StringUtil.escapeStr(obj.id)+"' onclick='borrowReturn.checkOneRow(this, event)'>",
		            "<td style='vertical-align: middle;' class='text-center'>",
		            	"<button class='line6 line6-btn' onclick='borrowReturn.deleteTool(this)' type='button'>",
		            		"<i class='icon-minus cursor-pointer color-blue cursor-pointer'></i>",
	            		"</button>",
		            "</td>",
		            "<td style='text-align:center;vertical-align:middle;'>",
		            	"<input name='borrow_return_tool_list_checkbox' value='1' type='checkbox' "+(isChecked?"checked=checked":"")+" />",
		            "</td>",
		            "<td>",
		            	"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.bjh)+"'>"+StringUtil.escapeStr(obj.bjh)+"</p>",
		            	"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.sn)+"'>"+StringUtil.escapeStr(obj.sn)+"</p>",
		            "</td>",
		            "<td>",
		            	"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.xh)+"'>"+StringUtil.escapeStr(obj.xh)+"</p>",
		            	"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.gg)+"'>"+StringUtil.escapeStr(obj.gg)+"</p>",
		            "</td>",
		            "<td>",
		            	"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.ywms)+"'>"+StringUtil.escapeStr(obj.ywms)+"</p>",
		            	"<p class='borrow-p' title='"+StringUtil.escapeStr(obj.zwms)+"'>"+StringUtil.escapeStr(obj.zwms)+"</p>",
		            "</td>",
		            "<td>",
			            "<p class='borrow-p' title='"+StringUtil.escapeStr(obj.ckh)+" "+StringUtil.escapeStr(obj.ckmc)+"'>"+StringUtil.escapeStr(obj.ckh)+" "+StringUtil.escapeStr(obj.ckmc)+"</p>",
			            "<p class='borrow-p' title='"+StringUtil.escapeStr(obj.kwh)+"'>"+StringUtil.escapeStr(obj.kwh)+"</p>",
		            "</td>",
		            "<input type='hidden' name='kcsl' value='"+StringUtil.escapeStr(obj.kcsl)+"' />",
	            "</tr>"].join("");
		$("#borrow_return_tool_list>.no-data").remove();
		$("#borrow_return_tool_list").append(html);
	},
	
	/**
	 * 根据关键字添加人员/工具
	 */
	loadByKeyword : function(keyword){
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
			url: basePath+"/material/toolborrowreturn/loadbykeyword",
			type: "post",
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				keyword : keyword,
				type : this_.getType(),
				dprtcode : userJgdm
			}),
			success : function(data){
				finishWait();
				this_.appendUserAndToolListToHtml(data);
				this_.focusSearchInput();
		    },
		    error : function(data) {
		    	finishWait();
				this_.alertErrorMessage(data.responseText);
				this_.focusSearchInput();
			},
		}); 
	},
	
	/**
	 * 拼接用户和工具集合
	 */
	appendUserAndToolListToHtml : function(obj){
		var this_ = this;
		
		obj.users && $.each(obj.users, function(i, user){
			this_.appendUserToHtml(user);
		});
		
		obj.stocks && $.each(obj.stocks, function(i, stock){
			this_.appendToolToHtml(stock, obj.stocks.length == 1);
		});
	},
	
	/**
	 * 字符串是否json格式
	 */
	isJson : function(str){
        try {
        	var obj=JSON.parse(str);
            return typeof obj == "object" && obj;
        } catch(e) {
        	return false;
        }
	},
	
	/**
	 * 获取工具/设备借归类型
	 */
	getType : function(){
		return $('#borrowOrReturn').bootstrapSwitch('state') ? "10" : "20";
	},
	
	/**
	 * 弹出消息提示框
	 */
	alertErrorMessage : function(msg){
		var button = "<button type='button' onclick='borrowReturn.closeAlertMessage(this)' class='close'>&times;</button>";
		$("#alert_msg_div").html(button + msg);
    	$("#alert_msg_div").removeClass("alert-success").addClass("alert-danger").show();
    	App.resizeHeight();
	},
	
	/**
	 * 弹出消息提示框
	 */
	alertSuccessMessage : function(msg){
		var button = "<button type='button' onclick='borrowReturn.closeAlertMessage(this)' class='close'>&times;</button>";
		$("#alert_msg_div").html(button + msg);
    	$("#alert_msg_div").removeClass("alert-danger").addClass("alert-success").show();
    	App.resizeHeight();
	},
	
	/**
	 * 查询后方法
	 */
	focusSearchInput : function(){
		$("#keyword_tool").val("").focus();
	},
	
	/**
	 * 获取数据
	 */
	getData : function(){
		
		var this_ = this;
		
		var list = [];
		var $checkedRadio = $("#borrow_return_user_list").find("input:checked");
		var $tr = $checkedRadio.parents("tr");
		var jyZrrid = $tr.attr("id");
		var jyZrrmc = $tr.find("[name='zrrmc']").val();
		
		var cqjybs = $("#longCheckbox").is(":checked") ? 1 : 0;
		var jyBz = "";
		if(cqjybs == 1){
			jyBz = $.trim($("#longReturn").val());
		}
		
		$("#borrow_return_tool_list>:not(.no-data)").each(function(i, tool){
			var $tr = $(tool);
			if($tr.find("input:checkbox").is(":checked")){
				var obj = {};
				obj.id = $tr.attr("id");
				obj.jyZrrid = jyZrrid;
				obj.jyZrrmc = jyZrrmc;
				obj.jySl = $tr.find("[name='kcsl']").val();
				obj.jyBz = jyBz;
				obj.cqjybs = cqjybs;
				obj.type = this_.getType();
				list.push(obj);
			}
		});
		
		return list;
	},
	
	/**
	 * 验证数据
	 */
	validateData : function(list){
		
		var this_ = this;
		this_.closeAlertMessage();
		
		var result = {};
		//1.通过  2.不通过  3.确认提示
		result.type = 1;
		
		if(list.length == 0){
			result.type = 2;
			result.msg = "请至少添加选中一个工具！";
			return result;
		}
		
		if($("#borrow_return_user_list").find("input:checked").length == 0){
			result.type = 2;
			result.msg = "请添加一个人员！";
			return result;
		}
		
		if($("#borrow_return_tool_list input:checkbox:not(:checked)").length > 0){
			result.type = 3;
			result.msg = "有工具未勾选，是否继续？";
		}
		return result;
	},
	
	/**
	 * 提交数据
	 */
	submitData : function(){
		var this_ = this;
		var obj = this_.getData();
		var result = this_.validateData(obj);
		if(result.type == 1){
			doSubmit();
		}else if(result.type == 2){
			this_.alertErrorMessage(result.msg);
			return false;
		}else if(result.type == 3){
			AlertUtil.showConfirmMessage(result.msg, {callback: function(){
				doSubmit();
			}});
		}
		
		function doSubmit(){
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/material/toolborrowreturn/submit",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					finishWait();
					this_.resetData();
					this_.focusSearchInput();
					this_.alertSuccessMessage("提交成功！");
				}
			});
		}
	},
	
	/**
	 * 重置数据
	 */
	resetData : function(){
		$("#keyword_tool,#longReturn").val("");
		$('#borrowOrReturn').bootstrapSwitch('state',true);
		$("#longCheckbox").removeAttr("checked");
		$("#longReturn").css("visibility","hidden");
		$("#borrow_return_tool_list").html('<tr class="no-data"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>');
		$("#borrow_return_user_list").html('<tr class="no-data"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>');
	},
	
	/**
	 * 选择用户
	 */
	chooseUser : function(){
		var this_ = this;
		var $checkedRadio = $("#borrow_return_user_list").find("input:checked");
		var $tr = $checkedRadio.parents("tr");
		var jyZrrid = $tr.attr("id");
		var jyZrrmc = $tr.find("[name='zrrmc']").val();
		Personel_Tree_Multi_Modal.show({
			checkUserList:[{id:jyZrrid,displayName:jyZrrmc}],//原值，在弹窗中回显
			dprtcode:userJgdm,
			multi:false,
			callback: function(data){//回调函数
				data && $.each(data, function(i, user){
					this_.appendUserToHtml(user);
				});
			}
		});
	},
	
	/**
	 * 删除用户
	 */
	deleteUser : function(obj){
		
		var $tr = $(obj).parents("tr");
		var isChecked = $tr.find("input:checked").length == 1;
		$tr.remove();
		if($("#borrow_return_user_list>tr").length == 0){
			$("#borrow_return_user_list").html('<tr class="no-data"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>');
		}
		
		if(isChecked && $("#borrow_return_user_list>tr:not(.no-data)").length >= 1){
			$("#borrow_return_user_list>tr:first input:radio").attr("checked","checked");
		}
	},
	
	/**
	 * 选择工具
	 */
	chooseTool : function(){
		var this_ = this;
		stock_open_alert.show({
			type : 2,
			multi : true,
			hclxList : [2, 3],
	 		modalHeadCN : '选择库存',
			modalHeadENG : 'Stock',
			dprtcode:userJgdm,//机构代码
			callback : function(data) {//回调函数
				data && $.each(data, function(i, tool){
					this_.appendToolToHtml(tool, true);
				});
			}
		});
	},
	
	/**
	 * 删除工具
	 */
	deleteTool : function(obj){
		$(obj).parents("tr").remove();
		if($("#borrow_return_tool_list>tr").length == 0){
			$("#borrow_return_tool_list").html('<tr class="no-data"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>');
		}
	},
};



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