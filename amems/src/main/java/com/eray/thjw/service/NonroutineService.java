package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.Nonroutine;
import com.eray.thjw.productionplan.po.TimeControllWareItem;

public interface NonroutineService {
       
	   int insertSelective(List<Nonroutine> nonroutine,String uuid,String gdbh,String jcid)throws RuntimeException;                                 //增加非例行工单
	   
	   void save(TimeControllWareItem timeControllWareItem)throws Exception ;//保存时控件工单
	   
	   int updateByPrimaryKey(Nonroutine record);           //   审核工单
	 
	   int updateByPrimaryKey2(Nonroutine record);           //   审批工单 
	   
	   int updateByPrimaryKey3(Nonroutine record);           //   指定结束
	   
	   int updateByPrimaryKey4(Nonroutine record);           //   作废工单
	   
	   void updateByPrimaryKeySelective(Nonroutine nonroutine);
		/**
		 * @author ll
		 * @description 根据工单id查询工单信息
		 * @param id
		 * @return FixedCheckItem
		 * @develop date 2016.08.24
		 */
		public Nonroutine selectByPrimaryKey(Nonroutine nonroutine);        //根据非例行工单实体查出工单信息 
		
		public Nonroutine selectByEOWork(Nonroutine nonroutine);        //根据EO工单实体查出工单信息 
		
		public Nonroutine selectByDingJianWork(Nonroutine nonroutine);        //根据定检工单实体查出工单信息 
}
