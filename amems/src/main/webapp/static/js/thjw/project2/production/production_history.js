	
	var productionHistory = {
			// 获取历史版本列表
			getHistoryData : function(id, bb, func){
				var this_ = this;
				AjaxUtil.ajax({
					url:basePath+"/project2/production/versionlist",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify({
						id : id,
						bb : bb
					}),
					dataType:"json",
					success:function(data){
						if(typeof(func)=="function"){
							func(data); 
						}
					}
				});
			},
			// 拼接历史版本html
			buildHistoryHtml : function(data){
				var this_ = this;
				var html = '<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 padding-bottom-0 padding-top-0 margin-bottom-0" style="overflow-x: auto;margin:0px;padding-bottom:8px;width:100%;">';
				html += '<table class="table-thin table-set text-center webui-popover-table" style="border:0px;" width="100%">';
				html += ['<thead>',
								'<tr>',
									'<th class="colwidth-15">',
									   '<div class="font-size-12 line-height-12">生产指令编号</div>',
							           '<div class="font-size-9 line-height-12">Production Order No.</div>',
								   	'</th>',
								   	'<th class="colwidth-5">',
									   '<div class="font-size-12 line-height-12">版本</div>',
							           '<div class="font-size-9 line-height-12">Rev.</div>',
								   	'</th>',
								   	'<th class="colwidth-9">',
									   '<div class="font-size-12 line-height-12">生效日期</div>',
							           '<div class="font-size-9 line-height-12">Date</div>',
								   	'</th>',
								'</tr>',
							'</thead>'].join('');
				html += '<tbody id="history_list">';
				if(data && data.length > 0){
					$.each(data, function(i, row){
						
						html += ['<tr>',
									'<td>',
										'<a href="javascript:void(0);" onclick="productionHistory.view(\''+row.id+'\')">'+StringUtil.escapeStr(row.zlbh)+'</a>',
									'</td>',
									'<td><a href="javascript:void(0);" onclick="productionHistory.view(\''+row.id+'\')">R'+StringUtil.escapeStr(row.bb)+'</a></td>',
									'<td>'+(row.sxsj||"").substr(0,10)+'</td>',
								'</tr>'].join('');
					});
				}
				html += '</tbody>';
				html += '</table>';
				html += '</div>';
				html += '<div class="clearfix"></div>';
				return html;
			},
			view:function(id){
				window.open(basePath + "/project2/production/view?id=" + id);
			}
	}
	
