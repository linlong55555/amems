package com.eray.thjw.oil.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.oil.dao.OilMapper;
import com.eray.thjw.oil.service.OilService;
import com.eray.thjw.po.Oil;
import com.eray.thjw.po.Oilprice;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
/**
 * @author sunji
 * @description 油品规格 ServiceImpl类
 * @develop
 */
@Service
public class OilServiceImpl implements OilService{

	@Autowired
	private OilMapper oilMapper;
	@Autowired
	private CommonRecService commonRecService;
	/**
	 * 根据条件查询油品规格
	 */
	public List<Oil> queryAll(Oil oil) throws BusinessException {
		return oilMapper.queryAll(oil);
	}
	/**
	 * @author sunji
	 * @description 添加油品规格
	 */
	public void save(Oil oil) {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		oil.setId(uuid.toString());
		oil.setWhsj(new Date());
		oil.setWhrid(user.getId());
		oil.setWhbmid(user.getBmdm());
		oil.setDprtcode(user.getJgdm());
		oil.setZt(1);
		oilMapper.insertSelective(oil);
		commonRecService.write(oil.getId(), TableEnum.B_J_005, user.getId(), czls, oil.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,oil.getId());
	}
	/**
	 * @author sunji
	 * @description 修改油品规格
	 */
	public void update(Oil oil) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		int []i={1};
		boolean b=this.verification(oil.getId(), i);
		if(b){
			throw new BusinessException("该油品规格已删除，请刷新后再进行操作");
		}
		oil.setWhsj(new Date());
		oil.setWhrid(user.getId());
		oil.setWhbmid(user.getBmdm());
		oilMapper.updateByPrimaryKeySelective(oil);
		commonRecService.write(oil.getId(), TableEnum.B_J_005, user.getId(), czls, oil.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,oil.getId());
		
	}
	/**
	 * @author sunji
	 * @description 油品规格作废
	 */
	public void invalid(Oil oil) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int []i={1};
		boolean b=this.verification(oil.getId(), i);
		if(b){
			throw new BusinessException("该油品规格已删除，请刷新后再进行操作");
		}
		oil.setZt(0);
		oilMapper.updateByPrimaryKeySelective(oil);
		commonRecService.write(oil.getId(), TableEnum.B_J_005, user.getId(), czls, oil.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,oil.getId());
	}
	public boolean verification(String id, int [] i){
		boolean b=true;
		Oil oil=oilMapper.selectByPrimaryKey(id);
		for (int a = 0; a < i.length; a++) {
			
			if(oil.getZt()==i[a]){
				b=false;
			}
		}
		
		return b;
	}
	 /**
	  * @author sunji
	  * @description 验证唯一
	  */
	public int validationYpgg(Oil oil) throws BusinessException {
		return oilMapper.validationYpgg(oil);
	}
	 /**
	  * @author sunji
	  * @description 根据组织机构查询所油品规格
	  */
	public List<Oil> queryByDprtcode(String dprtcode) throws BusinessException {
		return oilMapper.queryByDprtcode(dprtcode);
	}
}
