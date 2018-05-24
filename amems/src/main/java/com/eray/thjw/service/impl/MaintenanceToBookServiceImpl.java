package com.eray.thjw.service.impl;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.MaintenanceToBookMapper;
import com.eray.thjw.po.MaintenanceToBook;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.MaintenanceToBookService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 维修方案修订通知书service,用于业务逻辑处理
 * @develop date 2016.08.30
 */
@Service
public class MaintenanceToBookServiceImpl implements MaintenanceToBookService {
	
	/**
	 * @author liub
	 * @description 维修方案Mapper
	 * @develop date 2016.08.15
	 */
	@Autowired
	private MaintenanceToBookMapper maintenanceToBookMapper;
	
	/**
	 * @author liub
	 * @description 历史数据公共service
	 * @develop date 2016.09.26
	 */
	@Autowired
	private CommonRecService commonRecService;

	/**
	 * @author liub
	 * @description 保存维修方案修订通知书信息
	 * @param maintenance
	 * @return 
	 * @develop date 2016.08.30
	 */
	@Override
	public void saveMaintenanceToBook(String wxfaid ,String xdtzsidStr,String czls, String zdid) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		if(null != xdtzsidStr && !"".equals(xdtzsidStr)){
			String[] xdtzsidArr = xdtzsidStr.split(",");
			List<String> columnValueList = new ArrayList<String>();
			for (String xdtzsid : xdtzsidArr) {
				UUID uuid = UUID.randomUUID();//获取随机的uuid
				String id = uuid.toString();
				MaintenanceToBook toBook = new MaintenanceToBook();
				toBook.setId(id);
				toBook.setMainid(wxfaid);
				toBook.setXdtzsid(xdtzsid);
				toBook.setZt(DelStatus.TAKE_EFFECT.getId());
				toBook.setDprtcode(user.getJgdm());
				toBook.setWhrid(user.getId());
				toBook.setWhbmid(user.getBmdm());
				maintenanceToBookMapper.insertSelective(toBook);
			//commonRecService.write(id, TableEnum.B_G_005, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//保存修订通知书日志
				columnValueList.add(id);
			}
			
			commonRecService.write("id", columnValueList, TableEnum.B_G_01101, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//保存维修方案修订通知书历史记录信息
		}
	}
}
