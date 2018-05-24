/**
 * 流程信息
 */
introduce_process_info_class = {
	id:'introduce_process_info_class',
	/**
	 * 获取流程信息的值
	 */
	getData:function(){
		var data={};
		data.shenhe=$("#"+this.id+"_shenhe").val(); //审核意见
		data.shenpi=$("#"+this.id+"_shenpi").val(); //批准意见
		return data;
	}
};