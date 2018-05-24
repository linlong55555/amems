
WorkContentUtil = {
	id:'WorkContentUtil',
	tbodyId:'work_content_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	param: {
		djid:null,//关联单据id
		ywlx:1,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
		colOptionhead : true,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	isValid : true,//验证是否通过,isValid:true表示验证通过,isValid:false表示验证未通过
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.rowCount = 0;
		this.initViewOrHide();
		if(this.param.djid){
			this.initDataList();
		}else{
			this.setNoData();
		}
	},
	initViewOrHide : function(){
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("."+this.colOptionheadClass, $("#"+this.id)).show();
		}else{
			$("."+this.colOptionheadClass, $("#"+this.id)).hide();
		}
	},
	initDataList : function(){
		var this_ = this;
		var searchParam = {};
		searchParam.ywid = this.param.djid;
		searchParam.ywlx = this.param.ywlx;
		searchParam.dprtcode = this.param.dprtcode;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/workcontent/queryAllList",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(searchParam),
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendHtml(data);
				}else{
					this_.setNoData();
				}
			}
		});
	},
	appendHtml : function(list){
		var this_ = this;
		// 先移除暂无数据一行
		$("#"+this_.tbodyId, $("#"+this_.id)).empty();
		$.each(list, function(i, row) {
			this_.rowCount++;
			this_.addRow(row);
		});
	},
	add : function(){
		var this_ = this;
		//先移除暂无数据一行
		var len = $("#"+this_.tbodyId, $("#"+this_.id)).length;
		if (len == 1) {
			if($("#"+this_.tbodyId,$("#"+this_.id)).find("td").length == 1){
				$("#"+this_.tbodyId, $("#"+this_.id)).empty();
			}
		}
		var row = {};
		this_.rowCount++;
		row.id = '';
		this_.addRow(row);
	},
	addRow : function(obj){
		var this_ = this;
		var tr = "";
		tr += "<tr>";
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:center;vertical-align:middle;'>";
			tr += "<button class='line6 line6-btn' onclick='"+this_.id+".removeRow(this)'>";
		    tr += "    <i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		    tr += "</button>";
			tr += "</td>";
		}
		
		var gznr = StringUtil.escapeStr(obj.gznr);
		var gzz = StringUtil.escapeStr(obj.gzz);
		var jcz = StringUtil.escapeStr(obj.jcz);
		var gznrTitle = StringUtil.escapeStr(obj.gznr);
		var gzzTitle = StringUtil.escapeStr(obj.gzz);
		var jczTitle = StringUtil.escapeStr(obj.jcz);
		if(this_.param.colOptionhead){
			gznrTitle = '';
			gzzTitle = '';
			jczTitle = '';
			gznr = "<textarea  onkeyup='"+this_.id+".changeGznr(this)' style='resize:none;height:115px;white-space:normal;' class='form-control' name='gznr' maxlength='1000'>"+StringUtil.escapeStr(obj.gznr)+"</textarea>";
			gzz = "<input class='form-control' name='gzz' type='text' value='"+StringUtil.escapeStr(obj.gzz)+"' maxlength='16' />";
			jcz = "<input class='form-control' name='jcz' type='text' value='"+StringUtil.escapeStr(obj.jcz)+"' maxlength='16' />";
		}
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.rowCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+gznrTitle+"' style='text-align:left;vertical-align:middle;'>";
		tr += gznr;
		tr += "</td>";
		
		tr += "<td title='"+gzzTitle+"' style='text-align:left;vertical-align:middle;'>";
		tr += gzz;
		tr += "</td>";
		
		tr += "<td title='"+jczTitle+"' style='text-align:left;vertical-align:middle;'>";
		tr += jcz;
		tr += "</td>";
		
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
	},
	removeRow : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='hiddenid']").val();
		if ('' != id && null != id) {
			
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				$(obj).parent().parent().remove();
				this_.resRowCount();
				this_.rowCount--;
			}});
			
		} else {
			$(obj).parent().parent().remove();
			this_.resRowCount();
			this_.rowCount--;
		}
	},
	resRowCount : function(){
		var this_ = this;
		var len = $("#"+this_.tbodyId,$("#"+this_.id)).find("tr").length;
		if (len >= 1){
			$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(index){
				$(this).find("span[name='orderNumber']").html(index+1);
			});
		}else{
			this_.setNoData();
		}
	},
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='5' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='4' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	changeGznr : function(obj){
		$(obj).removeClass("border-color-red");
		/*if($(obj).val() == null || $(obj).val() == ''){
			$(obj).addClass("border-color-red");
		}*/
	},
	getData : function(isBj){
		var this_ = this;
		this_.isValid = true;
		var param = [];
		var len = $("#"+this.tbodyId,$("#"+this.id)).find("tr").length;
		if (len == 1) {
			if($("#"+this.tbodyId,$("#"+this.id)).find("td").length ==1){
				return param;
			}
		}
		if (len > 0){
			var xc = 0;
			$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
				var infos ={};
				var index=$(this).index(); //当前行
				var hiddenid =$(this).find("input[name='hiddenid']").val(); //当前行，隐藏id的值
				var gznr = $(this).find("textarea[name='gznr']").val();
				var gzz = $(this).find("input[name='gzz']").val();
				var jcz = $(this).find("input[name='jcz']").val();
				
				if(null == gznr || "" == gznr){
					AlertUtil.showModalFooterMessage("请输入工作内容!");
					$(this).find("textarea[name='gznr']").focus();
					$(this).find("textarea[name='gznr']").addClass("border-color-red");
					this_.isValid = false; 
					return false;
				}
				
				xc++;
				infos.id = hiddenid;
				infos.gznr = gznr;
				infos.gzz  = gzz;
				infos.jcz  = jcz;
				infos.isBj  = isBj;
				infos.xc = xc;
				param.push(infos);
			});
		}
		return param;
	}
}
