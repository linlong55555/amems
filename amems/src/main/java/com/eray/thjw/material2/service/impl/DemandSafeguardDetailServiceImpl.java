package com.eray.thjw.material2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.DemandSafeguardDetailMapper;
import com.eray.thjw.material2.po.DemandSafeguardDetail;
import com.eray.thjw.material2.service.DemandSafeguardDetailService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.material2.DemandSafeguardStatusEnum;

/** 
 * @Description 
 * @CreateTime 2018-2-26 下午3:20:10
 * @CreateBy 孙霁	
 */
@Service
public class DemandSafeguardDetailServiceImpl implements DemandSafeguardDetailService{

	@Resource
	private DemandSafeguardDetailMapper demandSafeguardDetailMapper;
	
	
	/**
	 * 
	 * @Description 根据查询条件查询需求清单
	 * @CreateTime 2018-2-27 下午3:10:33
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAll(
			DemandSafeguardDetail demandSafeguardDetail)
			throws BusinessException {
		PageHelper.startPage(demandSafeguardDetail.getPagination());
		List<DemandSafeguardDetail> list = demandSafeguardDetailMapper.queryAll(demandSafeguardDetail);
		return PageUtil.pack4PageHelper(list, demandSafeguardDetail.getPagination());
	}
	
	/**
	 * @Description 查询需求统计分析
	 * @CreateTime 2018-4-3 下午5:10:16
	 * @CreateBy 刘兵
	 * @param demandSafeguardDetail
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<DemandSafeguardDetail> queryAnalysisList(DemandSafeguardDetail demandSafeguardDetail) throws BusinessException {
		return demandSafeguardDetailMapper.queryAnalysisList(demandSafeguardDetail);
	}
	
	/**
	 * 
	 * @Description 批量处理
	 * @CreateTime 2018-2-28 下午5:05:41
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @throws BusinessException
	 */
	@Override
	public void updateBatch(DemandSafeguardDetail demandSafeguardDetail)
			throws BusinessException {
		User user = ThreadVarUtil.getUser();
		demandSafeguardDetail.setWhrid(user.getId());
		demandSafeguardDetail.setWhbmid(user.getBmdm());
		demandSafeguardDetail.setWhsj(new Date());
		demandSafeguardDetailMapper.updateBatch(demandSafeguardDetail);
	}
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2018-3-1 上午10:39:06
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @throws BusinessException
	 */
	@Override
	public  List<DemandSafeguardDetail> getDemandSafeguardDetailList(
			DemandSafeguardDetail demandSafeguardDetail)
			throws BusinessException {
		if(demandSafeguardDetail.getBzztList() != null && demandSafeguardDetail.getBzztList().size() > 0 && demandSafeguardDetail.getBzztList().get(0) != "" ){
			demandSafeguardDetail.getParamsMap().put("bzztList", demandSafeguardDetail.getBzztList());
		}
		if(demandSafeguardDetail.getWllbList() != null && demandSafeguardDetail.getWllbList().size() > 0 && demandSafeguardDetail.getWllbList().get(0) != "" ){
			demandSafeguardDetail.getParamsMap().put("wllbList", demandSafeguardDetail.getWllbList());
		}
		List<DemandSafeguardDetail> list=demandSafeguardDetailMapper.queryAll(demandSafeguardDetail);
		for (DemandSafeguardDetail dsd : list) {
			dsd.getParamsMap().put("ztText", DemandSafeguardStatusEnum.getName(dsd.getXqbs()));
			if(dsd.getParamsMap() != null && dsd.getParamsMap().get("jjcd") != null){
				System.out.println(Integer.valueOf(dsd.getParamsMap().get("jjcd").toString()));
				if(Integer.valueOf(dsd.getParamsMap().get("jjcd").toString()) == 1){
					dsd.getParamsMap().put("jjcdText","一般" );
				}else if(Integer.valueOf(dsd.getParamsMap().get("jjcd").toString()) == 9){
					dsd.getParamsMap().put("jjcdText","紧急" );
				}
			}else{
				dsd.getParamsMap().put("jjcdText","" );
				
			}
		}
		return list;
	}

}
