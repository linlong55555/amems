package com.eray.thjw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.OilpriceMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Oilprice;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.OilpriceService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class OilpriceServiceImpl implements OilpriceService{

	@Autowired
	private OilpriceMapper oilpriceMapper;
	@Autowired
	private CommonRecService commonRecService;
	/**
	 * @author sunji
	 * @description 油品查询
	 * @develop date 2016.08.15
	 */
	public List<Oilprice> selectByOil(String dprtcode) {
		return oilpriceMapper.selectByDprtcode(dprtcode);
	}
	/**
	 * @author sunji
	 * @description 油品价格查询
	 * @develop date 2016.08.15
	 */
	public List<Oilprice> selectByYpgg(Oilprice oilprice) {
		return oilpriceMapper.selectByYpgg(oilprice);
	}

	/**
	 * @author sunji
	 * @description 添加油品价格
	 * @develop date 2016.08.15
	 */
	public void save(Oilprice oilprice) {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		oilprice.setId(uuid.toString());
		oilprice.setWhsj(new Date());
		oilprice.setWhrid(user.getId());
		oilprice.setWhbmid(user.getBmdm());
		oilprice.setDprtcode(user.getJgdm());
		oilprice.setZt(1);
		oilpriceMapper.insertSelective(oilprice);
		commonRecService.write(oilprice.getId(), TableEnum.B_J_004, user.getId(), czls, oilprice.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,oilprice.getId());
	}
	/**
	 * @author sunji
	 * @description 修改油品价格
	 * @develop date 2016.08.15
	 */
	public void update(Oilprice oilprice) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		int []i={1};
		boolean b=this.verification(oilprice.getId(), i);
		if(b){
			throw new BusinessException("该油品价格已删除，请刷新后再进行操作");
		}
		
		oilprice.setWhsj(new Date());
		oilprice.setWhrid(user.getId());
		oilprice.setWhbmid(user.getBmdm());
		oilpriceMapper.updateByPrimaryKeySelective(oilprice);
		commonRecService.write(oilprice.getId(), TableEnum.B_J_004, user.getId(), czls, oilprice.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,oilprice.getId());
	}
	/**
	 * @author sunji
	 * @description 油品价格作废
	 * @develop date 2016.08.15
	 */
	 public void invalid(Oilprice oilprice) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int []i={1};
		boolean b=this.verification(oilprice.getId(), i);
		if(b){
			throw new BusinessException("该油品价格已删除，请刷新后再进行操作");
		}
	 	oilprice.setZt(0);
		oilpriceMapper.updateByPrimaryKeySelective(oilprice);
		commonRecService.write(oilprice.getId(), TableEnum.B_J_004, user.getId(), czls, oilprice.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,oilprice.getId());
		}
	 
		public boolean verification(String id, int [] i){
			boolean b=true;
			Oilprice oilprice=oilpriceMapper.selectByPrimaryKey(id);
			for (int a = 0; a < i.length; a++) {
				
				if(oilprice.getZt()==i[a]){
					b=false;
				}
			}
			
			return b;
		}
}
