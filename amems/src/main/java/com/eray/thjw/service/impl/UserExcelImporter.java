package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.common.util.UserUtil;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.system.dao.LoginMapper;
import com.eray.thjw.system.po.Login;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

@Service("userExcelImporter")
public class UserExcelImporter extends AbstractExcelImporter<User> {
	
	@Autowired
	private UserMapper userMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private DepartmentMapper departmentMapper;
	
	@Resource
	private LoginMapper loginMapper;
	@Resource
	private DepartmentService departmentService;
	
	/**
	 * 
	 * @Description 根据父id找所有部门
	 * @CreateTime 2017年11月30日 下午2:30:57
	 * @CreateBy 林龙
	 * @param jgdm
	 * @return
	 */
	private List<String> getAllBmjg(String jgdm) {
		try {
			List<String> resultList = new ArrayList<String>();
			List<Department> jgdmlist = departmentMapper.findAllparentId(jgdm);
			for (Department department: jgdmlist) {
				resultList.add(department.getDprtcode());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Description 查询所有账号
	 * @CreateTime 2017年11月30日 下午2:50:19
	 * @CreateBy 林龙
	 * @return
	 */
	private List<String> getAlltlogin() {
		try {
			List<String> resultList = new ArrayList<String>();
			List<Login> loginlist = loginMapper.findAllLogin();
			for (Login login: loginlist) {
				resultList.add(login.getUsername());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public void validateParam(Map<Integer, List<User>> datasMap) throws ExcelImportException {
		
		// 所有账号
		List<String> tloginList = getAlltlogin();
		// 所有用户
	//	List<String> tuserList = getAlltltuser();
		
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的数据
			List<User> datas = datasMap.get(sheetIndex);
		
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			
			if(StringUtils.isBlank(datas.get(0).getJgdm())){
				throw new ExcelImportException("无法获取机构代码,请选择组织机构!");
			}
			
			// 根据组织机构所有部门机构
			List<String> bmjgList = getAllBmjg(datas.get(0).getJgdm());
			
			String temp = "";
			User user ;
			for (int i = 0; i < datas.size(); i++) {
				user = datas.get(i);
				
				if(StringUtils.isBlank(user.getUsername())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，用户代码不能为空!");
				}
				if(StringUtils.isBlank(user.getRealname())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，姓名不能为空!");
				}
				if(StringUtils.isBlank(user.getBmdm())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部门机构不能为空!");
				}
				
				// 长度验证
				if(Utils.Str.getLength(user.getUsername()) > 30){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，用户代码的最大长度为30!");
				}
				if(Utils.Str.getLength(user.getRealname()) > 100){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，姓名的最大长度为100!");
				}
				if(Utils.Str.getLength(user.getDrzhid()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，登录账号的最大长度为50!");
				}
				if(Utils.Str.getLength(user.getPhone()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，固定电脑的最大长度为20!");
				}
				if(Utils.Str.getLength(user.getCellphone()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，手机号的最大长度为20!");
				}
				if(user.getSex() != null &&  user.getSex() != 1 &&  user.getSex() != 2){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，性别只能为1或者2!");
				}
				if(Utils.Str.getLength(user.getBmdm()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部门机构的最大长度为50!");
				}
				
				// 格式验证
				if(!StringUtils.isBlank(user.getUsername()) && Utils.Str.isChinese(user.getUsername())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，用户代码不能含有中文!");
				}
				if(!StringUtils.isBlank(user.getDrzhid()) && Utils.Str.isChinese(user.getDrzhid())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，登陆账号不能含有中文!");
				}
				if(!StringUtils.isBlank(user.getPhone()) && Utils.Str.isChinese(user.getPhone())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，固定电话不能含有中文!");
				}
				if(!StringUtils.isBlank(user.getCellphone()) && Utils.Str.isChinese(user.getCellphone())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，手机不能含有中文!");
				}
				if(!StringUtils.isBlank(user.getBmdm()) && Utils.Str.isChinese(user.getBmdm())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部门机构不能含有中文!");
				}
				
				//重复验证
				temp = user.getUsername();
			    for (int j = i + 1; j < datas.size(); j++) {
				   User user1 =(User) datas.get(j);
					if(user1 != null && temp != null){
						if (temp.equals(user1.getUsername())){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，用户代码不能重复!");
						}
					}
	            }
				
				//登录账号查找t_login表
				if(!StringUtils.isBlank(user.getDrzhid()) && tloginList.contains(user.getDrzhid())){
					//登录账号查找t_user表
					List<User> userNew=userMapper.selectByDrzhName(user.getDrzhid());
					for (User user2 : userNew) {
						if(!user2.getUsername().equals(user.getUsername())){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，登陆账号已被其他用户使用!");
						}
					}
				}
				
				if(!StringUtils.isBlank(user.getBmdm()) && !bmjgList.contains(user.getBmdm())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部门机构不存在!");
				}
				
			}
			
		}
	}
	

	@Override
	public int writeToDB(Map<Integer, List<User>> datas) throws ExcelImportException {
		List<User> list = new ArrayList<User>();
		list.addAll(datas.get(0));
		// 所有账号
		
		List<String> tloginList = getAlltlogin();
		for (User user : list) {
			Login login=new Login();
			//登录账号查找t_login表
			if(!StringUtils.isBlank(user.getDrzhid())){
				if(!tloginList.contains(user.getDrzhid()) ){//不存在
					String loginuuid = UUID.randomUUID().toString();
					login=new Login();
					login.setId(loginuuid); 
					login.setUsername(user.getDrzhid());
					login.setPassword(UserUtil.MD5(GlobalConstants.getString(GlobalConstants.DEFAULT_PASSWORD_KEY)));
					login.setState(1);
					login.setJgdm(user.getJgdm());
					loginMapper.insertSelective(login);
				}else{//存在
					//登录账号查找t_user表
					List<User> userNew=userMapper.selectByDrzhName(user.getDrzhid());
					if(userNew.size()==0){
						Login login1=loginMapper.selectByPrimaryName(user.getDrzhid());
						login.setId(login1.getId());
					}else{//修改账号表组织机构
						Login login1=loginMapper.selectByPrimaryName(user.getDrzhid());
						login.setId(login1.getId());
						login.setJgdm(user.getJgdm());
						loginMapper.updateByPrimaryKeySelective(login);
					}
				}
			}
			user.setState(1);
			Department department=	departmentMapper.selectDepartmentByDprtCodeParentId(user.getBmdm(),user.getJgdm());
			user.setBmdm(department.getId());
			
			//根据当前选择的部门id查询从当前部门往上的所有父数据（包含自己）
			if(department.getId()!=null&&!department.getId().equals("")){
				List<Department> departmentList=departmentService.selectDepartmentPid(department.getId());
				for (Department department1 : departmentList) {
					if(department1.getJdbs()==1){
						user.setJddm(department1.getId());//用户选择部门后往部门上级递归查找基地标识为是的 部门ID存入该字段
						break;
					}
				}
			}
			
			int num=userMapper.queryCount(user);
			if(num>0){
				user.setDrzhid(login.getId());
				userMapper.updateByPrimaryKeySelectiveImport(user);
			}else{
				String uuid = UUID.randomUUID().toString();
				user.setId(uuid);
				user.setDrzhid(login.getId());
				userMapper.insertSelective(user);
			}
		}
		
		return 1;
	}

	@Override
	public Map<Integer, List<User>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList) throws ExcelImportException {
		// 结果集
		Map<Integer, List<User>> resultMap = new TreeMap<Integer, List<User>>();
		
		String dprtcode = this.getParam("dprtcode").toString();
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			// sheet对应页签
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			List<User> list = new ArrayList<User>();
			User user;
			Map<Integer, String> bean;
			for (int i = 0; i < mapList.size(); i++) {
				bean = mapList.get(i);
				user = new User();
				/*
				 * 读取excel值
				 */
				user.setUsername(bean.get(0)); 	//用户代码
				user.setRealname(bean.get(1));	//姓名
				user.setDrzhid(bean.get(2));	//登录账号
				user.setPhone(bean.get(3));		//固定电话
				user.setCellphone(bean.get(4));
				if (bean.get(5) != null ) {
					user.setSex(convertToInteger(bean.get(5)));	//性别
				}else{
					user.setSex(1); //默认男性
				}
				user.setBmdm(bean.get(6));		//部门机构
				user.setJgdm(dprtcode);			//组织机构
				list.add(user);
			}
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}

}
