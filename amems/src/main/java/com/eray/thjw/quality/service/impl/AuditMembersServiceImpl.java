	package com.eray.thjw.quality.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.quality.dao.AuditMembersMapper;
import com.eray.thjw.quality.po.AuditMembers;
import com.eray.thjw.quality.service.AuditMembersService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.UserService;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.quality.BusinessTypeEnum;
import enu.quality.RoleEnum;
/**
 * 
 * @Description 审核成员serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("auditMembersService")
public class AuditMembersServiceImpl implements AuditMembersService  {

	@Resource
	private AuditMembersMapper auditMembersMapper;
	@Resource
	private UserService userService;
	@Resource
	private CommonService commonService;

	/**
	 * 
	 * @Description 编辑审核成员
	 * @CreateTime 2018年1月8日 下午4:02:30
	 * @CreateBy 林龙
	 * @param auditMembers
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateSelective(List<AuditMembers> auditMemberslist, String uuid,Integer ywlx,String dprtcode,User user) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		List<AuditMembers> insertList = null;//新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工种工时
		//根据业务id、业务类型、机构代码获取数据库已经存在的数据
		List<AuditMembers> list = auditMembersMapper.queryByYwidAndYwlxAndDrpt(uuid, ywlx, dprtcode);
		//map集合,key:id,WorkContent
		Map<String, AuditMembers> map = new HashMap<String, AuditMembers>();
		//将数据库已存在的器材/工具放入workContentMap
		for (AuditMembers auditMembers : list) {
			map.put(auditMembers.getId(), auditMembers);
		}
		if(null != auditMemberslist && auditMemberslist.size() > 0){
			insertList = new ArrayList<AuditMembers>();
			for (AuditMembers auditMembers : auditMemberslist) {
				//判断id是否为空,是否存在于数据库,不为空且存在:修改,反之:新增
				if(null != auditMembers.getId() && null != map.get(auditMembers.getId())){
					User newuser=userService.selectByPrimaryKey(auditMembers.getCyid());
					if(null != newuser){
						auditMembers.setCybh(newuser.getUsername());	//成员编号
						auditMembers.setCymc(newuser.getRealname());	//成员名称
						auditMembers.setBmid(newuser.getDepartment() == null?"":newuser.getDepartment().getId());
						auditMembers.setBmbh(newuser.getDepartment() == null?"":newuser.getDepartment().getDprtcode());
						auditMembers.setBmmc(newuser.getDepartment() == null?"":newuser.getDepartment().getDprtname());
					}
					auditMembers.setWhbmid(user.getBmdm());	        //部门id
					auditMembers.setWhrid(user.getId());		    //用户id
					auditMembers.setWhsj(currentDate);				//当前时间
					
					idMap.put(auditMembers.getId(), auditMembers.getId());
					//修改
					auditMembersMapper.updateByPrimaryKeySelective(auditMembers);
					
				}else{
					insertList.add(auditMembers);
				}
			}
			//保存
			inertSelective(insertList,uuid,ywlx, dprtcode,user);
		}
		for (AuditMembers auditMembers : list){
			//如果数据库id不在页面,那么删除
			if(null == idMap.get(auditMembers.getId())){
				columnValueList.add(auditMembers.getId());
			}
		}
		if(columnValueList.size() > 0){
			//批量删除
			auditMembersMapper.delete4Batch(columnValueList);
		}
		
		
		
	}
	
	/**
	 * 
	 * @Description 新增编辑审核成员
	 * @CreateTime 2018年1月8日 下午4:02:30
	 * @CreateBy 林龙
	 * @param auditMembers
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void inertSelective(List<AuditMembers> auditMemberslist, String uuid,Integer ywlx,String dprtcode,User user) throws BusinessException {
		
		Date currentDate = commonService.getSysdate();//当前时间
		for (AuditMembers auditMembers : auditMemberslist) {
			String newuuid = UUID.randomUUID().toString();	//uuid-主单id
			User newuser=userService.selectByPrimaryKey(auditMembers.getCyid());
			if(null != newuser){
				auditMembers.setCybh(newuser.getUsername());	//成员编号
				auditMembers.setCymc(newuser.getRealname());	//成员名称
				auditMembers.setBmid(newuser.getDepartment() == null?"":newuser.getDepartment().getId());
				auditMembers.setBmbh(newuser.getDepartment() == null?"":newuser.getDepartment().getDprtcode());
				auditMembers.setBmmc(newuser.getDepartment() == null?"":newuser.getDepartment().getDprtname());
			}
			auditMembers.setId(newuuid);					//id
			auditMembers.setDprtcode(user.getJgdm());		//组织机构
			auditMembers.setZt(EffectiveEnum.YES.getId());	//状态为有效
			auditMembers.setYwid(uuid);						//业务id
			auditMembers.setYwlx(BusinessTypeEnum.SHNDJH.getId()); //业务类型
			auditMembers.setJs(RoleEnum.CY.getId());	    //角色
			auditMembers.setWhbmid(user.getBmdm());	        //部门id
			auditMembers.setWhrid(user.getId());		    //用户id
			auditMembers.setWhsj(currentDate);				//当前时间
			auditMembersMapper.insertSelective(auditMembers);	
		}
	}

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2018-1-10 下午2:40:53
	 * @CreateBy 孙霁
	 * @param auditMembersList
	 * @param ywid
	 * @param ywlx
	 * @param user
	 * @throws BusinessException
	 */
	@Override
	public void insert(List <AuditMembers> auditMembersList, String ywid, Integer ywlx,
			User user) throws BusinessException {
		for (AuditMembers auditMembers : auditMembersList) {
			auditMembers.setId(UUID.randomUUID().toString());//id
			auditMembers.setDprtcode(user.getJgdm());		//组织机构
			auditMembers.setZt(EffectiveEnum.YES.getId());	//状态为有效
			auditMembers.setYwid(ywid);						//业务id
			auditMembers.setYwlx(ywlx); 					//业务类型
			auditMembers.setWhsj(new Date());				//当前时间
			auditMembersMapper.insertSelective(auditMembers);	
		}
	}

	/**
	 * 
	 * @Description 根据ywid 获取审核成员信息
	 * @CreateTime 2018-1-11 上午11:17:37
	 * @CreateBy 孙霁
	 * @param mainid
	 * @throws BusinessException
	 */
	@Override
	public List<AuditMembers> selectByYwid(String ywid)
			throws BusinessException {
		return auditMembersMapper.selectByYwid(ywid);
	}

	/**
	 * 
	 * @Description 根据ywid 删除审核成员信息
	 * @CreateTime 2018-1-11 上午11:17:37
	 * @CreateBy 孙霁
	 * @param mainid
	 * @throws BusinessException
	 */
	@Override
	public void delete(String ywid) throws BusinessException {
		auditMembersMapper.deleteByYwid(ywid);
	}
	
		
}