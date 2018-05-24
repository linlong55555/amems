package com.eray.thjw.produce.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.ThresholdAirMapper;
import com.eray.thjw.produce.po.ThresholdAir;
import com.eray.thjw.produce.service.ThresholdAirService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.project2.MonitorProjectEnum;
/**
 * @Description 飞机基本信息impl
 * @CreateTime 2017年9月19日 上午11:15:50
 * @CreateBy 林龙
 */
@Service("thresholdAirService")
public class ThresholdAirServiceImpl implements ThresholdAirService  {

	@Resource
	private ThresholdAirMapper thresholdAirMapper;

	/**
	 * @Description 根据查询系统阀值设置-飞机数据
	 * @CreateTime 2017年9月19日 下午3:20:35
	 * @CreateBy 林龙
	 * @param thresholdAir 
	 * @return  系统阀值设置-飞机数据list集合
	 * @throws BusinessException
	 */
	@Override
	public List<ThresholdAir> queryMonitorSettingByfjzch(ThresholdAir thresholdAir) throws BusinessException {
		return thresholdAirMapper.queryMonitorSettingByfjzch(thresholdAir);
	}
	
	/**
	 * @Description 批量保存监控设置
	 * @CreateTime 2017年9月19日 下午5:09:51
	 * @CreateBy 林龙
	 * @param thresholdAir
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String save(ThresholdAir thresholdAir) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		thresholdAir.setWhbmid(user.getBmdm());
		thresholdAir.setWhrid(user.getId());
		
		save(thresholdAir,"CAL",MonitorProjectEnum.CAL.getCode(),"1D",user);	//日历
		save(thresholdAir,"FH",MonitorProjectEnum.FH.getCode(),"2T",user);		//飞行小时
		save(thresholdAir,"EH",MonitorProjectEnum.EH.getCode(),"2T",user);		//发动机小时
		save(thresholdAir,"APUH",MonitorProjectEnum.APUH.getCode(),"2T",user);	//APU小时
		save(thresholdAir,"FC",MonitorProjectEnum.FC.getCode(),"3C",user);		//飞行循环
		save(thresholdAir,"EC",MonitorProjectEnum.EC.getCode(),"3C",user);		//发动机循环
		save(thresholdAir,"APUC",MonitorProjectEnum.APUC.getCode(),"3C",user);	//APU循环

		return thresholdAir.getFjzch();
	}

	/**
	 * 
	 * @Description 操作监控设置-新增或者修改
	 * @CreateTime 2017年9月20日 上午9:26:13
	 * @CreateBy 林龙
	 * @param thresholdAir 监控设置
	 * @param parameter  参数
	 * @param jklbh 监控类编号
	 * @param jkflbh 监控分类编号
	 * @param user 用户
	 */
	private void save(ThresholdAir thresholdAir,String parameter,String jklbh,String jkflbh,User user) {
		if(thresholdAir.getParamsMap().get(parameter)!=null){ 
			Integer parameterLowerCase;
			//当阀值不为字符串空时
			if(!thresholdAir.getParamsMap().get(parameter).toString().equals("")){
				 parameterLowerCase=Integer.valueOf(thresholdAir.getParamsMap().get(parameter).toString());
			}else{
				parameterLowerCase=null;
			}
			
			thresholdAir.setFz(parameterLowerCase);	//阀值
			thresholdAir.setJklbh(jklbh); 			// 监控类编号
			thresholdAir.setJkflbh(jkflbh);			//监控分类编号
			
			//查询监控设置数据
			ThresholdAir thresholdAirOld=thresholdAirMapper.selectByPrimaryKey(thresholdAir);
			
			if(thresholdAirOld==null){
				thresholdAir.setDprtcode(user.getJgdm());
				//新增监控设置
				thresholdAirMapper.insertSelective(thresholdAir);
			}else{
				//修改监控设置
				thresholdAirMapper.updateByPrimaryKeySelective(thresholdAir);
			}
		}
	}
}