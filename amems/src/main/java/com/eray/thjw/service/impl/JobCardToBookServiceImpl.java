package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.JobCardToBookMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.JobCardToBook;
import com.eray.thjw.po.MaintenanceToBook;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.JobCardToBookService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
/**
 * @author sunji
 * @description 工卡通知书service,用于业务逻辑处理
 */
@Service
public class JobCardToBookServiceImpl implements JobCardToBookService{

	/**
	 * @author liub
	 * @description 维修方案Mapper
	 * @develop date 2016.08.15
	 */
	@Autowired
	private JobCardToBookMapper jobCardToBookMapper;
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
	public void saveJobCardToBook(String gkid ,List<String> xdtzsids,String czls, String zdid) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
			for (String xdtzsid : xdtzsids) {
				UUID uuid = UUID.randomUUID();//获取随机的uuid
				String id = uuid.toString();
				JobCardToBook toBook = new JobCardToBook();
				toBook.setId(id);
				toBook.setMainid(gkid);
				toBook.setXdtzsid(xdtzsid);
				toBook.setZt(DelStatus.TAKE_EFFECT.getId());
				toBook.setDprtcode(user.getJgdm());
				toBook.setWhrid(user.getId());
				toBook.setWhbmid(user.getBmdm());
				jobCardToBookMapper.insertSelective(toBook);
			//commonRecService.write(id, TableEnum.B_G_005, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//保存修订通知书日志
			}
			
			commonRecService.write("id", xdtzsids, TableEnum.B_G_01302, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//保存维修方案修订通知书历史记录信息
	}

	/**
	 * @author sunji
	 * @description 根据mainid查询数据
	 * @param jobCardToBook
	 * @return List<JobCardToBook>
	 */
	public List<JobCardToBook> queryAllByMainid(String mainid)
			throws BusinessException {
		return jobCardToBookMapper.queryAllByMainid(mainid);
	}

	/**
	 * @author sunji
	 * @description 修改工卡修订通知书信息
	 * @param maintenance
	 * @return 
	 */
	public void updateJobCardToBook(String gkid, List<String> xdtzsids,
			String czls, String zdid) throws BusinessException {
		
		//获取登入user
		User user=ThreadVarUtil.getUser();
		List<JobCardToBook> jobCardToBookList=this.queryAllByMainid(gkid);
		List<String> oldbookList=new ArrayList<String>();
		for (JobCardToBook jobCardToBook : jobCardToBookList) {
			if(xdtzsids==null || !xdtzsids.contains(jobCardToBook.getId())){
				jobCardToBookMapper.deleteByPrimaryKey(jobCardToBook.getId());
				commonRecService.write(jobCardToBook.getId(), TableEnum.D_01701, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.UPDATE,gkid);
			}
			oldbookList.add(jobCardToBook.getId());
		}
		
		if(xdtzsids!=null){
			for (String xdtzsid : xdtzsids) {
				if(!oldbookList.contains(xdtzsid)){
					UUID uuid = UUID.randomUUID();//获取随机的uuid
					String id = uuid.toString();
					JobCardToBook toBook = new JobCardToBook();
					toBook.setId(id);
					toBook.setMainid(gkid);
					toBook.setXdtzsid(xdtzsid);
					toBook.setZt(DelStatus.TAKE_EFFECT.getId());
					toBook.setDprtcode(user.getJgdm());
					toBook.setWhrid(user.getId());
					toBook.setWhbmid(user.getBmdm());
					jobCardToBookMapper.insertSelective(toBook);
					commonRecService.write(toBook.getId(), TableEnum.D_01701, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.UPDATE,gkid);
				}
				
			}
		}
		
		
	}

}
