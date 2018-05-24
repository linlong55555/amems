package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.AffectedMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Affected;
import com.eray.thjw.po.User;
import com.eray.thjw.service.AffectedService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
@Service
public class AffectedServiceImpl implements AffectedService {

	@Autowired
	private AffectedMapper affectedMapper;
	@Autowired
	private PlaneModelDataService planeModelDataService;
	
	
	/**
	 * @author liub
	 * @description 日志service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	@Override
	public void save(List<Affected> affected,String id,String list,String list1,String czls,String technicalfileId) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		List<String> list3 = new ArrayList<String>(); //修改之前list1
		List<String> list2 = new ArrayList<String>(); //修改之后的list
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		for (Affected affec : affected) {
			if(!"".equals(affec.getFjzch()) && affec.getFjzch()!=null){
				if (!jxList.contains(affec.getFjzch())){
					jxList.add(affec.getFjzch());
				} 
			}
		}
		
		if(jxList.size()>0){
			boolean b=planeModelDataService.existsAircraft(user.getId(),user.getUserType(),user.getJgdm(), jxList);
			if(!b){
				throw new BusinessException("机型权限已变更,请刷新后再进行操作");
			}
		}
		String[] menu=new String[0];
		String[] index =new String[0];
		
		if(!list1.equals("")){
			 menu = list1.split(",");//修改之前分配的菜单id
		}
		if(!list.equals("")){
			index = list.split(",");//修改之后的菜单id
		}
		
		for (int i = 0; i < index.length; i++) {
			list2.add(index[i]);
		}
		for (int i = 0; i < menu.length; i++) {
			list3.add(menu[i]);
		}
		
		//删除
		for(String newMenuId : list3){
			System.out.println("list1="+list1);
			if(!list2.contains(newMenuId)){
				Affected affectedList=new Affected();
				affectedList.setZt(0);
				affectedList.setId(newMenuId);
				affectedMapper.update(affectedList);             
				commonRecService.write(affectedList.getId(), TableEnum.B_G_00102, user.getId(),czls,affectedList.getLogOperationEnum().ZUOFEI,UpdateTypeEnum.UPDATE,technicalfileId);//插入日志
			}
		}
		
		
		for (Affected affectedList : affected) {
			
			if( affectedList.getId()!=null && !affectedList.getId().equals("")){
				
				affectedList.setMainid(id);
				if(affectedList.getBjh()==null){
					affectedList.setBjh("");
				}
				if(affectedList.getBjxlh()==null){
					affectedList.setBjxlh("");
				}
				if(affectedList.getZjqdid()==null){
					affectedList.setZjqdid("");
				}
				if(affectedList.getFjzch()==null){
					affectedList.setFjzch("");
				}
				if(affectedList.getJhgsRs()==null){
					affectedList.setJhgsRs("0");
				}
				if(affectedList.getJhgsXss()==null){
					affectedList.setJhgsXss("0");
				}
				affectedList.setWhrid(user.getId());
				affectedList.setWhsj("1");
				affectedList.setWhdwid(user.getBmdm());
				affectedList.setDprtCode(user.getJgdm());
				affectedMapper.update(affectedList);
				commonRecService.write(affectedList.getId(), TableEnum.B_G_00102, user.getId(),czls,affectedList.getLogOperationEnum().EDIT,UpdateTypeEnum.UPDATE,technicalfileId);//插入日志
			}else{
				//新增
				int num = 0;
				num++;
				UUID uuid = UUID.randomUUID();
				String ids = uuid.toString();	
				affectedList.setId(ids);
				affectedList.setMainid(id);
				if(affectedList.getBjh()==null){
					affectedList.setBjh("");
				}
				if(affectedList.getBjxlh()==null){
					affectedList.setBjxlh("");
				}
				if(affectedList.getZjqdid()==null){
					affectedList.setZjqdid("");
				}
				if(affectedList.getFjzch()==null){
					affectedList.setFjzch("");
				}
				
				if(affectedList.getJhgsRs()==null){
					affectedList.setJhgsRs("0");
				}
				
				if(affectedList.getJhgsXss()==null){
					affectedList.setJhgsXss("0");
				}
				
				affectedList.setWhrid(user.getId());
				affectedList.setWhsj("1");
				affectedList.setZt(1);
				affectedList.setWhdwid(user.getBmdm());
				affectedList.setDprtCode(user.getJgdm());
				affectedMapper.save(affectedList);
				commonRecService.write(affectedList.getId(), TableEnum.B_G_00102, user.getId(),czls,affectedList.getLogOperationEnum().SAVE_WO,UpdateTypeEnum.SAVE,technicalfileId);//插入日志
			}
		}
		
	}

	
	/**
	 * @author sunji
	 * @description 根据评估单id工作内容数据
	 * @param djxmid
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryListByDjxmbhid(String djxmid)
			throws BusinessException {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		List<Affected> wList = affectedMapper.queryListByDjxmbhid(djxmid);
		
		for (Affected w : wList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", w.getId());
			map.put("zjqdid", w.getZjqdid());
			map.put("zxfl", w.getZxfl());
			map.put("fjzch", w.getFjzch());
			map.put("bjh", w.getBjh());
			map.put("bjxlh", w.getBjxlh());
			map.put("jhgsRs", w.getJhgsRs());
			map.put("jhgsXss", w.getJhgsXss());
			w.CompareColumn(w);
			map.put("isUpd", w.getIsUpd());
			
			list.add(map);
		}
		return list;
	}
	/**
	 * @author sunji
	 * @description 根据多个评估单id工作内容数据
	 * @param techncialfileids
	 * @return List<Map<String, Object>>
	 */
	public List<Affected> queryListByTechnicalfileids(
			List<String> techncialfileIds) throws BusinessException {
		List<Affected> list = affectedMapper.queryListByTechnicalfileids(techncialfileIds);
		List<Affected> returnlist=new ArrayList<Affected>();
		for (Affected affected : list) {
			formatAffected(affected, returnlist);
		}
		
		return returnlist;
	}
	
	
	/**
	 * @author sunji
	 * @description 比较字符串
	 * @param s1,s2
	 * @return boolean
	 */
	private void formatAffected(Affected affected, List<Affected> returnlist){
		boolean exist = false;
		
		for (Affected returnAffected : returnlist) {
			if(cp(affected.getBjh(),returnAffected.getBjh())
					/*&& cp(affected.getJhgsRs(),returnAffected.getJhgsRs())
					&& cp(affected.getJhgsXss(),returnAffected.getJhgsXss())*/
					&& cp(affected.getBjxlh(),returnAffected.getBjxlh())
					&& cp(affected.getZjqdid(),returnAffected.getZjqdid())
					&& cp(affected.getZxfl(),returnAffected.getZxfl())
					){
				exist = true;
				break;
			}
		}
		if(!exist){
			returnlist.add(affected);
		}
	}
	
	/**
	 * @author sunji
	 * @description 比较字符串
	 * @param s1,s2
	 * @return boolean
	 */
	private boolean cp(String s1, String s2){
		s1 = s1==null?"":s1;
		s2 = s2==null?"":s2;
		return s1.equals(s2);
	}
	/**
	 * @author liub
	 * @description 根据多个评估单id查询执行对象数据
	 * @param pgdIds
	 * @return List<Affected>
	 */
	@Override
	public List<Affected> queryListByPgdIds(List<String> pgdIds)throws BusinessException{
		return affectedMapper.queryListByPgdIds(pgdIds);
	}

}
