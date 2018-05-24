package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.JX_BJMapper;
import com.eray.thjw.po.JX_BJ;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.JX_BJService;
import com.eray.thjw.util.ThreadVarUtil;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class JX_BJServiceImpl implements JX_BJService {
    
	@Resource
	private JX_BJMapper jX_BJMapper;
	
	@Resource
	private CommonRecService commonRecService;
	@Override        
	public int insertSelective(PlaneModelData record) throws RuntimeException { 
		
		List<JX_BJ> JX_BJList=record.getjX_BJList();
		User user = ThreadVarUtil.getUser();
		int count=0;
		if(JX_BJList!=null && !JX_BJList.isEmpty()){
			List<String> list=new ArrayList<String>(); 
			for (JX_BJ jx_BJ : JX_BJList) {
				jx_BJ.setDprtcode(user.getJgdm());
				jx_BJ.setWhbmid(user.getBmdm());
				jx_BJ.setWhrid(user.getId());
				list.add(jx_BJ.getFjjx());
			    count=jX_BJMapper.insertSelective(jx_BJ);
			}
			commonRecService.writeByWhere("fjjx='"+record.getFjjx().replaceAll("'", "''")+"' and b.dprtcode = '"+user.getJgdm()+"'" , TableEnum.D_00401, user.getId(),record.getCzls(),record.getLogOperationEnum() , UpdateTypeEnum.SAVE,record.getZbid());
	    }
		return count;
	}
	@Override
	public List<JX_BJ> selectByPrimaryKey(JX_BJ record) throws RuntimeException {
		return jX_BJMapper.selectByPrimaryKey(record);
	}

	@Override
	public void deleteJX_BJ(String fjjx) throws RuntimeException {
		 jX_BJMapper.deleteJX_BJ(fjjx);
	}

}
