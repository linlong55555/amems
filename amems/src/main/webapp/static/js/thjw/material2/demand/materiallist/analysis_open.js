/**
 * 需求统计分析
 */ 
var analysis_open_alert = {
		id:'analysis_open_alert',
		tbodyId:'analysis_open_alert_Tbody',
		rowCount : 0,
		param: {
			idList : [],//需求清单id集合
			changeCss:false,
			dprtcode:userJgdm,//默认登录人当前机构代码
			callback: function(){//回调函数
				
			}
		},
		show : function(param){
			$("#"+this.id).modal("show");
			AlertUtil.hideModalFooterMessage();
			if(param){
				$.extend(this.param, param);
			}
			this.initInfo();
		},
		initInfo : function(){
			this.rowCount = 0;
			if(this.param.changeCss){
				$("#analysis_open_alert_table_div", $("#"+this.id)).removeClass("padding-right-8").addClass("padding-right-0");
			}
			this.initfjzch();
			this.clearSearch();
			// 先移除暂无数据一行
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			if(this.param.idList.length > 0){
				this.initDataList();
			}else{
				this.setNoData();
			}
		},
		clearSearch : function(){
			$("#"+this.id+"_keyword_search", $("#"+this.id)).val("");
			$('input:checkbox[name=isWarning_search]', $("#"+this.id)).each(function(){
				$(this).attr("checked", "checked");
			});
		},
		initfjzch : function(){
			var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:this.param.dprtcode});
			var planeRegOption = "";
			if(planeDatas != null && planeDatas.length >0){
				planeRegOption += "<option value='' >"+"显示全部 All"+"</option>";
				$.each(planeDatas,function(i,planeData){
					planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"'>"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
				});
				planeRegOption += "<option value='-' >N/A</option>";
			}else{
				planeRegOption += "<option value='-1' >"+"暂无飞机"+"</option>";
			}
			$("#a_fjzch", $("#"+this.id)).html(planeRegOption); 
		},
		initDataList : function(){
			var this_ = this;
			var searchParam = {};
			$.extend(searchParam, this.getParams());
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/material/demand/queryAnalysisList",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(searchParam),
				dataType:"json",
				success:function(data){
					if(data != null && data.length > 0){
						this_.appendHtml(data);
					    // 标记关键字
						signByKeyword($("#"+this_.id+"_keyword_search", $("#"+this_.id)).val(),[3,4],"#"+this_.tbodyId+" tr td");
					}else{
						this_.setNoData();
					}
				}
			});
		},
		getParams : function(){

			var searchParam = {};
			var paramsMap = {};
			var keyword = $.trim($("#"+this.id+"_keyword_search", $("#"+this.id)).val());
			if($('input:checkbox[name=isWarning_search]:checked', $("#"+this.id)).length == 1){
				paramsMap.isWarning = $('input:checkbox[name=isWarning_search]:checked', $("#"+this.id)).val(); 
			}
			if('' != keyword){
				paramsMap.keyword = keyword;
			}
			var fjzch = $("#a_fjzch", $("#"+this.id)).val();
			if(fjzch != ''){
				paramsMap.fjzch = fjzch == '-'?'':fjzch;
			}
			paramsMap.dprtcode = this.param.dprtcode;
				
			searchParam.idList = this.param.idList;
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		appendHtml : function(list){
			var this_ = this;
			var fjArr = [];
			var bjArr = [];
			var htmlContent = '';
			$.each(list,function(index,row){
				
				var paramsMap = row.paramsMap?row.paramsMap:{};
				var isWarning = (paramsMap.isWarning == 1?"是":"否");
				var warningStyle = (paramsMap.isWarning == 1?"color: red":"");
				var kysl = paramsMap.kcsl - paramsMap.djsl;
				var dw = StringUtil.escapeStr(row.dw);
				
				htmlContent += "<tr id='"+row.id+"' >";
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";
			    htmlContent += "<td title='' style='text-align:center;vertical-align:middle;'>"+isWarning+"</td>";
			    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(paramsMap.fjzch))+"'>"+StringUtil.escapeStr(formatUndefine(paramsMap.fjzch))+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.bjh))+"</td>";
			    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(paramsMap.zywms))+"'>"+StringUtil.escapeStr(formatUndefine(paramsMap.zywms))+"</td>";
			    htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(formatUndefine(row.xqsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.xqsl))+dw+"</td>";
			    
			    htmlContent += "<td jh='"+StringUtil.escapeStr(row.bjh)+"' title='"+StringUtil.escapeStr(paramsMap.kcsl)+"' onclick="+this_.id+".openStorageDetailWin(this) style='text-align:right;vertical-align:middle;cursor:pointer;'>";
				htmlContent += "<a jh='"+StringUtil.escapeStr(row.bjh)+"' style='"+warningStyle+"' href='javascript:void(0);' onclick="+this_.id+".openStorageDetailWin(this)>"+StringUtil.escapeStr(paramsMap.kcsl)+dw+"</a>";
				htmlContent += "</td>";
			    
//			    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.paramsMap.kcsl))+"</td>";

			    htmlContent += "<td style='text-align:left;vertical-align:middle;'>";
				
				var tdjxx = paramsMap.tdjxx;
				var str = tdjxx==null?"":tdjxx.split(",");
				for (var i = 0; i < str.length; i++) {
					var dataArr = str[i].split("#_#")
					var bjh = dataArr[0];
					var sl = dataArr[1]?dataArr[1]:0;
					var ms = dataArr[3];
					var tdjtTitle = StringUtil.escapeStr(bjh) + " : " + sl;
					if(StringUtil.escapeStr(ms) != ''){
						tdjtTitle += "," + StringUtil.escapeStr(ms);
					}
					var tdj = "<a bjh='"+StringUtil.escapeStr(row.bjh)+"' tdjh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick="+this_.id+".openSubstitutionWin(this)>"+StringUtil.escapeStr(bjh)+"</a>";
					tdj += " : ";
					tdj += "<a jh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick="+this_.id+".openStorageDetailWin(this)>"+sl+"</a>";
					if(StringUtil.escapeStr(ms) != ''){
						tdj += " , " + StringUtil.escapeStr(ms);
					}
					if(i==str.length-1){
						htmlContent += "<span title='"+tdjtTitle+"'>"+tdj+"</span>";
					}else{
						htmlContent += "<span title='"+tdjtTitle+"'>"+tdj+"</span>"+"<br>";	
					}								
				}			
				
			    htmlContent += "</td>";
			    
			    htmlContent += "</tr>" ;
			    if(formatUndefine(row.paramsMap.fjzch) != '' && fjArr.indexOf(formatUndefine(row.paramsMap.fjzch)) == -1){
			    	fjArr.push(formatUndefine(row.paramsMap.fjzch));
				}
			    if(bjArr.indexOf(formatUndefine(row.bjh)) == -1){
			    	bjArr.push(formatUndefine(row.bjh));
				}
			});
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			$("#"+this.tbodyId, $("#"+this.id)).html(htmlContent);
			this_.setFxNum(list.length, fjArr.length, bjArr.length);
		},
		/**
		 * 设置暂无数据
		 */
		setNoData : function(){
			this.setFxNum(0, 0, 0);
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		},
		setFxNum : function(ybs, fjs, bjs){
			$("#a_ybs", $("#"+this.id)).html(ybs);
			$("#a_fjs", $("#"+this.id)).html(fjs);
			$("#a_bjs", $("#"+this.id)).html(bjs);
		},
		/**
		 * 查看替代件信息
		 */
		openSubstitutionWin : function(obj){
			var bjh = $(obj).attr("bjh");
			var tdjh = $(obj).attr("tdjh");
			var dprtcode = this.param.dprtcode;
			open_win_equivalent_substitution.show({
				bjh : bjh,
				tdjh : tdjh,
				isParamId : false,
				dprtcode:dprtcode
			});
		},
		/**
		 * 打开库存分布详情对话框
		 */
		openStorageDetailWin : function(obj){
			var this_ = this;
			var bjh = $(obj).attr("jh");
			var ckidList = [];
			//打开库存分布详情对活框
			open_win_inventory_distribution_details.show({
				bjh : bjh,
				ckidList:ckidList,
				dprtcode:this_.param.dprtcode
			});
		},
		/**
		 * 搜索
		 */
		search : function(){
			$("#"+this.id).prop('scrollTop','0');
			this.initDataList();
		}
 }
