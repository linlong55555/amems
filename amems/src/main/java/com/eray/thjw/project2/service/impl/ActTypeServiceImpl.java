package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.project2.service.ActTypeService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 
 * @Description 机型实现Impl
 * @CreateTime 2017年8月14日 上午10:31:26
 * @CreateBy 林龙
 */
@Service("actTypeService")
public class ActTypeServiceImpl implements ActTypeService{

	@Resource
	private ActTypeMapper actTypeMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 机型分页列表查询
	 * @CreateTime 2017年8月14日 上午10:27:56
	 * @CreateBy 林龙
	 * @param actType
	 * @return 分页数据
	 */
	@Override
	public Map<String, Object> queryAllPageList(ActType actType){
		//用户刚编辑过的记录机型
		String fjjx = actType.getFjjx();
		//清除查询条件中的机型，保证列表查询结果集的正确性
		actType.setFjjx(fjjx);
		PageHelper.startPage(actType.getPagination());
		List<ActType> list = actTypeMapper.queryAllPageList(actType);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(fjjx)){	//判断fjjx是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, fjjx)){
					ActType param = new ActType();
					param.setFjjx(fjjx);
					List<ActType> newRecordList = actTypeMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, actType.getPagination());
		}else{
			List<ActType> newRecordList = new ArrayList<ActType>(1);
			if(StringUtils.isNotBlank(fjjx)){	//判断fjjx是否为空，不为空则表示需要查询最近编辑的业务记录
				ActType param = new ActType();
				param.setFjjx(fjjx);
				newRecordList = actTypeMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, actType.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, actType.getPagination());
		}
	}

	/**
	 * @Description 验证机型唯一性（根据组织机构和机型）
	 * @CreateTime 2017年8月14日 下午3:38:19
	 * @CreateBy 林龙
	 * @param actType
	 * @throws BusinessException
	 */
	private void validationAcType(ActType actType)throws BusinessException{
		
		int iNum = actTypeMapper.selectActTypeCount(actType);
		
		if(iNum > 0){
			throw new BusinessException("当前机型已存在!");
		}
	}
	
	/**
	 * @Description 机型保存
	 * @CreateTime 2017年8月14日 上午10:28:52
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	@Override
	public String save(ActType actType) throws BusinessException{
		
		User user=ThreadVarUtil.getUser();//当前登陆人
		actType.setDprtcode(user.getJgdm()); //当前登陆机构代码
		//验证机型唯一性
		this.validationAcType(actType);
		
		actType.setZt(EffectiveEnum.YES.getId());//状态为有效
		actType.setBmid(user.getBmdm());//当前登陆人部门id
		actType.setCjrid(user.getId());//当前登陆人id
		
		//新增实现
		actTypeMapper.insertSelective(actType);
		
		//拼接机型和组织机构参数
		StringBuffer strfjjxDprt=new StringBuffer();
		strfjjxDprt.append(" b.fjjx = '");
		strfjjxDprt.append(actType.getFjjx().replaceAll("'", "''"));
		strfjjxDprt.append("' and b.dprtcode = '");
		strfjjxDprt.append(actType.getDprtcode());
		strfjjxDprt.append("'");
		
		//添加新增日志
		commonRecService.writeByWhere(strfjjxDprt.toString(), TableEnum.D_004, ThreadVarUtil.getUser().getId(), UUID.randomUUID().toString(), 
						 LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,actType.getFjjx().concat(",").concat(actType.getDprtcode()));
		return actType.getFjjx();
	}

	/**
	 * @Description 机型修改
	 * @CreateTime 2017年8月14日 上午10:29:01
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	@Override
	public String update(ActType actType) throws BusinessException{
		User user=ThreadVarUtil.getUser();//当前登陆人
		
		//修改实现
		actType.setCjrid(user.getId());
		actType.setBmid(user.getBmdm());
		actTypeMapper.updateByPrimaryKeySelective(actType);
		
		//拼接机型和组织机构参数
		StringBuffer strfjjxDprt=new StringBuffer();
		strfjjxDprt.append(" b.fjjx = '");
		strfjjxDprt.append(actType.getFjjx().replaceAll("'", "''"));
		strfjjxDprt.append("' and b.dprtcode = '");
		strfjjxDprt.append(actType.getDprtcode());
		strfjjxDprt.append("'");
		
		//添加修改日志
		commonRecService.writeByWhere(strfjjxDprt.toString(), TableEnum.D_004, ThreadVarUtil.getUser().getId(), UUID.randomUUID().toString(),
						 LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, actType.getFjjx().concat(",").concat(actType.getDprtcode()));
		return actType.getFjjx();
	}


	
	/**
	 * @Description 注销机型
	 * @CreateTime 2017年8月14日 下午6:14:01
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	@Override
	public String updatenote(ActType actType) throws BusinessException {
		
		//验证
		this.validationAc(actType);
		
		//注销实现
		actTypeMapper.updateByPrimaryKey(actType);
		
		//拼接机型和组织机构参数
		StringBuffer strfjjxDprt = new StringBuffer();
		strfjjxDprt.append(" b.fjjx = '");
		strfjjxDprt.append(actType.getFjjx().replaceAll("'", "''"));
		strfjjxDprt.append("' and b.dprtcode = '");
		strfjjxDprt.append(actType.getDprtcode());
		strfjjxDprt.append("'");
		
		//添加注销日志
		commonRecService.writeByWhere(strfjjxDprt.toString(), TableEnum.D_004, ThreadVarUtil.getUser().getId(), UUID.randomUUID().toString(),
						 LogOperationEnum.WRITEOFF, UpdateTypeEnum.UPDATE,actType.getFjjx().concat(",").concat(actType.getDprtcode()));
		return actType.getFjjx();
	}
	
	/**
	 * @Description 验证机型有没有被引用
	 * @CreateTime 2017年8月14日 下午3:38:19
	 * @CreateBy 林龙
	 * @param actType
	 * @throws BusinessException
	 */
	private void validationAc(ActType actType)throws BusinessException{
		
		int iNum = actTypeMapper.selectFjzchCount(actType);//
		
		if(iNum > 0){
			throw new BusinessException("当前机型已被引用，不可注销!");
		}
	}

	/**
	 * @Description 启用机型
	 * @CreateTime 2017年8月14日 下午6:14:01
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	@Override
	public String updateEnabled(ActType actType) throws BusinessException {
		
		//验证
		this.validationAc(actType);
		
		//启用实现
		actTypeMapper.updateByPrimaryKey(actType);
		
		//拼接机型和组织机构参数
		StringBuffer strfjjxDprt = new StringBuffer();
		strfjjxDprt.append(" b.fjjx = '");
		strfjjxDprt.append(actType.getFjjx().replaceAll("'", "''"));
		strfjjxDprt.append("' and b.dprtcode = '");
		strfjjxDprt.append(actType.getDprtcode());
		strfjjxDprt.append("'");
		
		//添加启用日志
		commonRecService.writeByWhere(strfjjxDprt.toString(), TableEnum.D_004, ThreadVarUtil.getUser().getId(), UUID.randomUUID().toString(),
						 LogOperationEnum.ENABLED, UpdateTypeEnum.UPDATE,actType.getFjjx().concat(",").concat(actType.getDprtcode()));
		return actType.getFjjx();
	}

	/**
	 * 
	 * @Description 根据组织机构查询机型
	 * @CreateTime 2017年11月8日 上午11:40:52
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	@Override
	public List<ActType> findByDprtcode(String dprtcode) {
		return actTypeMapper.findByDprtcode(dprtcode);
	}
}
