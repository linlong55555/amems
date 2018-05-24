package com.eray.thjw.basic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.CoverPlateInformationMapper;
import com.eray.thjw.basic.po.CoverPlateInformation;
import com.eray.thjw.basic.service.CoverPlateInformationService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
/**
 * @author liub
 * @description 盖板Service
 */
@Service
public class CoverPlateInformationServiceImpl implements CoverPlateInformationService {

	@Resource
	private CoverPlateInformationMapper coverPlateInformationMapper;
	
	@Resource
	private PlaneModelDataService planeModelDataService;

	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * 根据飞机机型查询盖板多选下拉框
	 * @param coverPlateInformation
	 * @return List<CoverPlateInformation>
	 */
	@Override
	public List<CoverPlateInformation> queryMultiselectByFjjx(CoverPlateInformation coverPlateInformation) {
		return coverPlateInformationMapper.queryMultiselectByFjjx(coverPlateInformation);
	}
	
	/**
	 * @author liub
	 * @description 根据飞机机型查询盖板(弹窗)
	 * @param coverPlateInformation
	 * @return List<CoverPlateInformation>
	 */
	@Override
	public List<CoverPlateInformation> queryWinByFjjx(CoverPlateInformation coverPlateInformation) {
		return coverPlateInformationMapper.queryWinByFjjx(coverPlateInformation);
	}

	/**
	 * 
	 * @Description 经过查询得到盖板列表
	 * @CreateTime 2017年8月31日 上午11:07:37
	 * @CreateBy 李高升
	 * @param coverPlateInformation
	 * @return
	 */
	@Override
	public Map<String, Object> queryCoverPlateList(CoverPlateInformation coverPlateInformation) {
		//获取登录人，根据登录人信息筛选数据
		User user = ThreadVarUtil.getUser();//当前登陆人
		coverPlateInformation.getParamsMap().put("userId", user.getId());
		coverPlateInformation.getParamsMap().put("userType", user.getUserType());
		PageHelper.startPage(coverPlateInformation.getPagination());
		//获取id，查询当前编辑项并置第一列
		String id = coverPlateInformation.getId();
		coverPlateInformation.setId(null);
		List<CoverPlateInformation> list = coverPlateInformationMapper.queryCoverPlateListByKeyword(coverPlateInformation);
		
		
		if(((Page)list).getTotal() > 0){
			// 获取下达指令
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					CoverPlateInformation param = new CoverPlateInformation();
					param.setId(id);
					List<CoverPlateInformation> newRecordList = coverPlateInformationMapper.queryCoverPlateListByKeyword(param);
					// 获取下达指令
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, coverPlateInformation.getPagination());
		}else{
			List<CoverPlateInformation> newRecordList = new ArrayList<CoverPlateInformation>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				CoverPlateInformation param = new CoverPlateInformation();
				param.setId(id);
				newRecordList = coverPlateInformationMapper.queryCoverPlateListByKeyword(param);
				// 获取下达指令
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, coverPlateInformation.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, coverPlateInformation.getPagination());
		}
	}
	
	/**
	 * 
	 * @Description 添加盖板
	 * @CreateTime 2017年9月19日 上午11:07:37
	 * @CreateBy 胡才秋
	 * @param coverPlateInformation
	 * @return
	 */
	@Override
	public void addCoverPlate(CoverPlateInformation coverPlateInformation) throws BusinessException {
		
		if(coverPlateInformationMapper.getCount(coverPlateInformation)==0){
			coverPlateInformationMapper.insertSelective(coverPlateInformation);
		}else{
			throw new BusinessException("该盖板比那好已存在!");
		}
		
	}

	/**
	 * 
	 * @Description 添加或者修改盖板
	 * @CreateTime 2017年9月19日 上午11:07:37
	 * @CreateBy 胡才秋
	 * @param coverPlateInformation
	 * @return
	 */
	@Override
	public boolean saveorUpdate(CoverPlateInformation coverPlateInformation) throws BusinessException {
		//写入用户id与维护部门id
		User user = ThreadVarUtil.getUser();
		String id = user.getId();
		String userJgdm = user.getJgdm();
		String usertype = user.getUserType();
		String departmentId = user.getBmdm();
		String czls = UUID.randomUUID()+"";
		coverPlateInformation.setWhbmid(departmentId);
		coverPlateInformation.setWhrid(id);
		if(StringUtils.isEmpty(coverPlateInformation.getId())) {
			coverPlateInformation.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		}
		
		//操作权限验证
		List<String> fjzch = new ArrayList<String>();
		fjzch.add(coverPlateInformation.getFjjx());
		planeModelDataService.existsModel4Expt(id, usertype, coverPlateInformation.getDprtcode(), fjzch);
		
		
		//修改或添加盖板
		int count = 0;
		/**
		 *	若为插入，则赋予机构代码与状态信息 
		 */
		if(StringUtils.isEmpty(coverPlateInformation.getId())) {
			coverPlateInformation.setZt(EffectiveEnum.YES.getId());
			int count2 = coverPlateInformationMapper.getCount(coverPlateInformation);
			if(count2>0) {
				throw new BusinessException("该盖板编号已存在");
			}
		}

		if(!StringUtils.isEmpty(coverPlateInformation.getId())) {
			if(!userJgdm.equals(coverPlateInformation.getDprtcode())) {
				throw new BusinessException("只能更改本组织机构下的数据！");
			}
			if(coverPlateInformation.getZt()!=null && coverPlateInformation.getZt()==EffectiveEnum.NO.getId()) {
				count = coverPlateInformationMapper.updateByPrimaryKeySelective(coverPlateInformation);
				commonRecService.write(coverPlateInformation.getId(), TableEnum.D_021, id, czls, LogOperationEnum.DELETE,
						UpdateTypeEnum.DELETE, coverPlateInformation.getId());
			}else {
				count = coverPlateInformationMapper.updateByPrimaryKeySelective(coverPlateInformation);
				commonRecService.write(coverPlateInformation.getId(), TableEnum.D_021, id, czls, LogOperationEnum.EDIT,
						UpdateTypeEnum.UPDATE, coverPlateInformation.getId());
			}
		}else{
			String uuid = UUID.randomUUID()+"";
			coverPlateInformation.setId(uuid);
			count = coverPlateInformationMapper.insertSelective(coverPlateInformation);
			commonRecService.write(coverPlateInformation.getId(), TableEnum.D_021, id, czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, coverPlateInformation.getId());
		}
		return count>0;
	}

	/**
	 * 
	 * @Description 通过id查询盖板信息
	 * @CreateTime 2017年9月18日 上午10:08:37
	 * @CreateBy 胡才秋
	 * @param coverPlateInformation
	 * @return
	 */
	@Override
	public CoverPlateInformation findDataById(String id) throws BusinessException {
		return coverPlateInformationMapper.selectByPrimaryKey(id);
	}
}
