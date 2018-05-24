package com.eray.thjw.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.common.util.UserUtil;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.system.dao.LoginMapper;
import com.eray.thjw.system.po.Login;
import com.eray.thjw.system.service.AccountService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.common.EnableEnum;

/**
 * 账号管理
 * @author xu.yong
 *
 */
@Service(value="accountService")
public class AccountServiceImpl implements AccountService{

	@Resource
	private LoginMapper loginMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private DeptInfoMapper deptInfoMapper;
	
	/**
	 * 分页查询账号信息
	 * @param baseEntity
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryPageList(BaseEntity baseEntity) throws BusinessException{
		
		
		String id = (String)baseEntity.getParamsMap().get("id");
		
		baseEntity.getParamsMap().remove("id");
		
		User sessionUser = ThreadVarUtil.getUser();
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(sessionUser.getOrgcode())){
			baseEntity.getParamsMap().put("userId", sessionUser.getId());
		}
		PageHelper.startPage(baseEntity.getPagination());
		List<Login> list = this.loginMapper.selectPage(baseEntity);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					BaseEntity entity1 = new BaseEntity();
					Map<String, Object> paramsMap = new HashMap<String, Object>(1);
					paramsMap.put("id", id);
					entity1.setParamsMap(paramsMap);
					List<Login> newRecordList = this.loginMapper.selectPage(entity1);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
			
		}else{
			List<Login> newRecordList = new ArrayList<Login>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				BaseEntity entity1 = new BaseEntity();
				Map<String, Object> paramsMap = new HashMap<String, Object>(1);
				paramsMap.put("id", id);
				entity1.setParamsMap(paramsMap);
				newRecordList = this.loginMapper.selectPage(entity1);
				if(newRecordList == null || newRecordList.size() != 1){
					throw new BusinessException("查询异常");
				}
				return PageUtil.pack(1, newRecordList, baseEntity.getPagination());
			}
			return PageUtil.pack(0, newRecordList, baseEntity.getPagination());
		}
		
	}
	
	/**
	 * 分页查询未绑定的账号
	 * @param baseEntity
	 * @return
	 */
	public Map<String, Object> queryUnboundPageList(BaseEntity baseEntity){
		PageHelper.startPage(baseEntity.getPagination());
		List<Login> list = this.loginMapper.selectUnboundPage(baseEntity);
		return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
	}
	
	/**
	 * 保存账号
	 * @param login
	 * @throws BusinessException 
	 */
	public void saveAccount(Login login) throws BusinessException{
		
		//验证账号是否重复
		int count = this.loginMapper.selectCountByUserName(login.getUsername());
		if(count > 0){
			throw new BusinessException("当前账号已存在");
		}
		
		if(StringUtils.isNotBlank(login.getJgdm())){
			validateAccountMax(login.getJgdm());
		}
		
		//保存账号
		login.setId(UUID.randomUUID().toString());
		login.setPassword(UserUtil.MD5(GlobalConstants.getString(GlobalConstants.DEFAULT_PASSWORD_KEY)));
		login.setState(EnableEnum.ENABLED.getId());
		this.loginMapper.insertSelective(login);
	}
	
	
	/**
	 * 启用/禁用 账号
	 * 修改账号状态
	 * @param id
	 * @param state
	 */
	public void updateAccountState(String id, Integer state){
		Login login = new Login();
		login.setId(id);
		login.setState(state);
		this.loginMapper.updateByPrimaryKeySelective(login);
	}
	
	/**
	 * 重置账号密码
	 * @param id
	 */
	public void updateAccountPassword4Reset(String id){
		Login login = new Login();
		login.setId(id);
		login.setPassword(UserUtil.MD5(GlobalConstants.getString(GlobalConstants.DEFAULT_PASSWORD_KEY)));
		this.loginMapper.updateByPrimaryKeySelective(login);
	}
	
	/**
	 * 重置账号密码
	 * @param id
	 * @param password 加密过的密码
	 */
	public void updateAccountPassword(String id, String password){
		Login login = new Login();
		login.setId(id);
		login.setPassword(password);
		this.loginMapper.updateByPrimaryKeySelective(login);
	}
	
	/**
	 * 删除账号
	 * @param id
	 * @throws BusinessException 
	 */
	public void deleteAccount(String id) throws BusinessException{
		//判断账号是否被绑定
		List<User> list = this.userMapper.selectByDrzhid(id);
		if(list != null && !list.isEmpty()){
			User user = list.get(0);
			throw new BusinessException("该账号已与用户["+user.getRealname()+"]绑定，请先解除绑定关系");
		}
		this.loginMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Login selectByPrimaryKey(String drzhid) {
		return loginMapper.selectByPrimaryKey(drzhid);
	}

	@Override
	public void updateByPrimaryKey(Login login) throws BusinessException {
		
		if(StringUtils.isNotBlank(login.getJgdm())){
			Login oldLogin = loginMapper.selectByPrimaryKey(login.getId());
			if(!login.getJgdm().equals(oldLogin.getJgdm())){
				validateAccountMax(login.getJgdm());
			}
		}
		loginMapper.updateByPrimaryKey(login);
	}
	
	private void validateAccountMax(String jgdm) throws BusinessException{
		//验证账号注册数量 
		int count1 = this.loginMapper.queryCountByJgdm(jgdm);
		//查询账号注册最大数量
		DeptInfo accounts = deptInfoMapper.selectCounts(jgdm);
		if(accounts!=null){
			if(accounts.getZczh_max()!=null && count1 >=accounts.getZczh_max() && accounts.getZczh_max()!=0){
				throw new BusinessException("已超过当前组织机构最大账号注册数量");
			}
		}
	}

	@Override
	public Login selectByPrimaryName(String accountName) {
		return loginMapper.selectByPrimaryName(accountName);
	}
	
}
