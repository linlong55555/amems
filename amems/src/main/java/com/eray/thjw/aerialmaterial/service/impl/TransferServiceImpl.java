package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.dao.TransferMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Transfer;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.TransferService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SaiBongEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;

@Service
public class TransferServiceImpl implements TransferService {
    
	@Resource
	private TransferMapper transferMapper;
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private MaterialHistoryService materialHistoryService;
	@Resource
	private StorageMapper storageMapper;
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	@Resource
	private SaibongUtilService saibongUtilService;     
    @Resource
	private MaterialRecService materialRecService;   //航材的REC
    
    @SuppressWarnings("unchecked")
	@Override
	public int insertSelective(Map<String, Object> map) {
		int count=0;
		String ykrid=(String)map.get("ykrid");          //取得移库人id
		String ykrq=(String)map.get("ykrq");          //取得移库日期
		String ykyy=(String)map.get("ykyy");			//取得移库的原因
		
		String czls = UUID.randomUUID().toString();  //日志的操作流水
		
		List<Map<String, Object>> detailList = (List<Map<String, Object>>)map.get("detail");
		 
		List<Transfer> transferlist=new ArrayList<Transfer>();        //存储移库的航材集合
		List <String> ids =new ArrayList<String>();                   //存储要移库的航材id集合
		
		if(null != detailList && !detailList.isEmpty()){
			for (Map<String, Object> detailMap : detailList) {
				String id = (String)detailMap.get("id");              //部件ID
				ids.add(id);
			}
		    List<Stock> stocklist=stockSerivce.queryChoseList(ids);   //查询出要移库的航材集合
		    
		    User user = ThreadVarUtil.getUser();
		    //通过采番获取移库编号
			String ykdh="";
			try {
				ykdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HCYK.getName());
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
			
		    for (Map<String, Object> detailMap : detailList) {
				double newkcsl=0;
				Storage storage=null;
		    	String mbsl=(String)detailMap.get("mbSl");                //取得对应的移库数量 
	    	    String id=(String)detailMap.get("id");
				String kwid= (String)detailMap.get("mbKwh");               //取得对应的库位id   
	//			String ykid=(String)detailMap.get("ykid");                 //判断移库赋值
				
				BigDecimal kcslNumber = new BigDecimal(mbsl).setScale(2);  //将移库数量进行类型转化
				if(null!=kwid&&!"".equals(kwid)){
					storage = storageMapper.selectByIdWithRel(kwid);
				}
		    	for (Stock oldstock : stocklist) {
		    		//当移库数量等于库存数量时
		    		if(oldstock.getId()!=null&&id.equals(oldstock.getId().toString())){
			    		if(kcslNumber.compareTo(oldstock.getKcsl())==0){  
				    			MaterialHistory mh=oldstock.toMaterialHistory();              //将移库航材对象转化成履历对象
								String mh_id=UUID.randomUUID().toString();
								mh.setId(mh_id);
								mh.setWhrid(ThreadVarUtil.getUser().getId());
								mh.setWhsj(new Date());
								//增加要移库的仓库记录到部件履历表中
								count += materialHistoryMapper.insertSelective(mh);        
								Transfer transfer=new Transfer();      
								transfer.setYsCklb(oldstock.getCklb());;
								transfer.setYsCkid(oldstock.getCkid());
								transfer.setYsCkh(oldstock.getCkh());
								transfer.setYsCkmc(oldstock.getCkmc());
								transfer.setYsKwid(oldstock.getKwid());
								transfer.setYsKwh(oldstock.getKwh());
								transfer.setYsSl(oldstock.getKcsl());
								
								//更新原库存记录，仓库信息重新赋值
								oldstock.setDprtcode(user.getJgdm());
								oldstock.setCkid(storage.getMainid());
								oldstock.setCklb(storage.getCklb());
								oldstock.setCkh(storage.getCkh());
								oldstock.setCkmc(storage.getStore().getCkmc());
								oldstock.setKwid(storage.getId());
								oldstock.setKwh(storage.getKwh());
								oldstock.setWhrid(ThreadVarUtil.getUser().getId());
								oldstock.setWhsj(new Date());
								count+=stockSerivce.updateStockKc(oldstock);               //为库存增加新的库存记录
								
								//增加库存的REC
								//commonRecService.write(oldstock.getId(), TableEnum.B_H_001, user.getId(),UpdateTypeEnum.SAVE);
								materialRecService.writeStockRec(oldstock.getId(), czls, oldstock.getId(), ykdh, StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.UPDATE, "",storage.getStore().getCkmc(),storage.getKwh(),mbsl);
								
								
								//增加履历表的目标记录数
								MaterialHistory newMaterialhistory =oldstock.toMaterialHistory();   //再次将将库航材对象转化成履历对象
								String newmh_id=UUID.randomUUID().toString();
								newMaterialhistory.setId(newmh_id);                                         //将移库信息赋值给履历对象
								newMaterialhistory.setKcid(oldstock.getId());
								newMaterialhistory.setCkid(storage.getMainid());
								newMaterialhistory.setCklb(storage.getCklb());
								newMaterialhistory.setCkh(storage.getCkh());
								newMaterialhistory.setCkmc(storage.getStore().getCkmc());
								newMaterialhistory.setKwid(storage.getId());
								newMaterialhistory.setKwh(storage.getKwh());
								newMaterialhistory.setKcsl(kcslNumber);
								newMaterialhistory.setWhrid(ThreadVarUtil.getUser().getId());
								newMaterialhistory.setWhsj(new Date());
								//将重新赋值后的履历对象新增到履历表中成为移库的目标记录
								count+= materialHistoryMapper.insertSelective(newMaterialhistory); 
								
							                    //实例化移库单对象
								transfer.setId(UUID.randomUUID().toString()); 
								transfer.setDprtcode(user.getJgdm());
								
								transfer.setYkdh(ykdh);                                    //给移库对象赋值（赋值原始仓库数量信息）
								transfer.setKcllidYs(mh_id);
								transfer.setBjid(oldstock.getBjid());
								transfer.setBjh(oldstock.getBjh());
								transfer.setPch(oldstock.getPch());
								transfer.setSn(oldstock.getSn());
								transfer.setZwms(oldstock.getZwms());
								transfer.setYwms(oldstock.getYwms());
				
								
								transfer.setZdbmid(user.getBmdm());
								transfer.setZdrid(user.getId());
								
								transfer.setMbCklb(storage.getCklb());
								transfer.setMbCkid(storage.getMainid());
								transfer.setMbCkh(storage.getCkh());
								transfer.setMbCkmc(storage.getStore().getCkmc());
								transfer.setMbKwid(storage.getId());
								transfer.setMbKwh(storage.getKwh());
								transfer.setMbSl(kcslNumber);
								transfer.setYkbmid(user.getBmdm());
								transfer.setYkrid(ykrid);
								transfer.setYkyy(ykyy);
								transfer.setKcllidMb(newmh_id);
								SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
								Date date=null;
								try {
									date = formatter.parse(ykrq);
									transfer.setYkrq(date);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								transferlist.add(transfer);
			    			
			    	     //当移库数量小于库存数量时	
			    		}else{
			    			if(id.equals(oldstock.getId().toString())){
			    				BigDecimal yssl=oldstock.getKcsl();    //记录原始库存数量
			    				
			    				//更新原始库存记录
			    				newkcsl= oldstock.getKcsl().subtract(kcslNumber).doubleValue();  //算出实际的库存量
			    				oldstock.setKcsl(new BigDecimal(newkcsl).setScale(2));
			    				oldstock.setWhrid(ThreadVarUtil.getUser().getId());
			    				oldstock.setWhsj(new Date());
		    		    		count+=stockSerivce.updateStockKc(oldstock);
		    		    		//增加库存的REC
		    		    		//commonRecService.write(oldstock.getId(), TableEnum.B_H_001, user.getId(),UpdateTypeEnum.UPDATE );
								materialRecService.writeStockRec(oldstock.getId(), czls, oldstock.getId(), ykdh, StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.UPDATE, "",storage.getStore().getCkmc(),storage.getKwh(),mbsl);
		    		    		
		    		    		//将移库航材对象转化成履历对象
		    		    		MaterialHistory mh=oldstock.toMaterialHistory();              
								String mh_id=UUID.randomUUID().toString();
								mh.setId(mh_id);
								mh.setKcsl(yssl);
								mh.setWhrid(ThreadVarUtil.getUser().getId());
			    				mh.setWhsj(new Date());
								//增加要移库的仓库记录到部件履历表中
								count += materialHistoryMapper.insertSelective(mh);  
								
								//增加新的新的库存记录，器仓库信息和数量重新赋值
								String newstock_id=UUID.randomUUID().toString();
								Transfer transfer=new Transfer();     
								transfer.setYsCklb(oldstock.getCklb());
								transfer.setYsCkid(oldstock.getCkid());
								transfer.setYsCkh(oldstock.getCkh());
								transfer.setYsCkmc(oldstock.getCkmc());
								transfer.setYsKwid(oldstock.getKwid());
								transfer.setYsKwh(oldstock.getKwh());
								transfer.setYsSl(yssl);
								
								oldstock.setId(newstock_id);
								oldstock.setDprtcode(user.getJgdm());
								oldstock.setCkid(storage.getMainid());
								oldstock.setCklb(storage.getCklb());
								oldstock.setCkh(storage.getCkh());
								oldstock.setCkmc(storage.getStore().getCkmc());
								oldstock.setKwid(storage.getId());
								oldstock.setKwh(storage.getKwh());
								
								oldstock.setKcsl(kcslNumber);
								oldstock.setWhrid(ThreadVarUtil.getUser().getId());
								oldstock.setWhsj(new Date());
								stockSerivce.insertSelective(oldstock);               //为库存增加新的库存记录
								
								//增加库存的REC
								//commonRecService.write(newstock_id, TableEnum.B_H_001, user.getId(),UpdateTypeEnum.SAVE);
								materialRecService.writeStockRec(newstock_id, czls, newstock_id, ykdh, StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.SAVE, "",storage.getStore().getCkmc(),storage.getKwh(),mbsl);
								
								//增加履历表的目标记录数
								MaterialHistory newMaterialhistory =oldstock.toMaterialHistory();   //再次将将库航材对象转化成履历对象
								String newmh_id=UUID.randomUUID().toString();
								newMaterialhistory.setId(newmh_id);                                         //将移库信息赋值给履历对象
								newMaterialhistory.setKcid(newstock_id);
								newMaterialhistory.setCkid(storage.getMainid());
								newMaterialhistory.setCklb(storage.getCklb());
								newMaterialhistory.setCkh(storage.getCkh());
								newMaterialhistory.setCkmc(storage.getStore().getCkmc());
								newMaterialhistory.setKwid(storage.getId());
								newMaterialhistory.setKwh(storage.getKwh());
								newMaterialhistory.setKcsl(kcslNumber);
								newMaterialhistory.setWhrid(ThreadVarUtil.getUser().getId());
								newMaterialhistory.setWhsj(new Date());
								//将重新赋值后的履历对象新增到履历表中成为移库的目标记录
								count+= materialHistoryMapper.insertSelective(newMaterialhistory); 
								
							                     //实例化移库单对象
								transfer.setId(UUID.randomUUID().toString()); 
								transfer.setDprtcode(user.getJgdm());
								
								transfer.setYkdh(ykdh);                                    //给移库对象赋值（赋值原始仓库数量信息）
								transfer.setKcllidYs(mh_id);
								transfer.setBjid(oldstock.getBjid());
								transfer.setBjh(oldstock.getBjh());
								transfer.setPch(oldstock.getPch());
								transfer.setSn(oldstock.getSn());
								transfer.setZwms(oldstock.getZwms());
								transfer.setYwms(oldstock.getYwms());
						
								transfer.setZdbmid(user.getBmdm());
								transfer.setZdrid(user.getId());
								
								transfer.setMbCklb(storage.getCklb());
								transfer.setMbCkid(storage.getMainid());
								transfer.setMbCkh(storage.getCkh());
								transfer.setMbCkmc(storage.getStore().getCkmc());
								transfer.setMbKwid(storage.getId());
								transfer.setMbKwh(storage.getKwh());
								transfer.setMbSl(kcslNumber);
								transfer.setYkbmid(user.getBmdm());
								transfer.setYkrid(ykrid);
								transfer.setYkyy(ykyy);
								transfer.setKcllidMb(newmh_id);
								SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
								Date date=null;
								try {
									date = formatter.parse(ykrq);
									transfer.setYkrq(date);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								transferlist.add(transfer);
			    			}
			    		}
		    		}else{
		    			count=-1;
		    		}
		    	}
		    	   
		    }
		    for (Transfer transfer : transferlist) {                         //遍历新增移库记录
			    if(null != transferlist && !transferlist.isEmpty()){
			    	count+=transferMapper.insertSelective(transfer);
			    }
			}
		}	
		return count;
	}

	@Override
	public List<Transfer> selectTransferList(Transfer record) {
		return transferMapper.selectTransferList(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		int count=0;
		String czls = UUID.randomUUID().toString();  //日志的操作流水
		Transfer transfer=transferMapper.selectByid(id);
		if(transfer.getZt().equals("1")){
			//查询出移库原始履历
			MaterialHistory oldMH=materialHistoryMapper.selectByPrimaryKey(transfer.getKcllidYs());
			//查询出移库目标履历
			MaterialHistory newMH=materialHistoryMapper.selectByPrimaryKey(transfer.getKcllidMb());
			
			//原始库存记录
			
			Stock stock=stockSerivce.queryById(oldMH.getKcid());
			//查询目标库存记录
			Stock newStock=stockSerivce.queryById(newMH.getKcid());
			
			if (stock==null){count=-1;	return count;}
			//记录原始库存量
			BigDecimal yskc=stock.getKcsl();
			
			//表示撤销全移库操作，更新库存的库位信息
			if(newMH.getKcsl().compareTo(oldMH.getKcsl())==0){
				if(null!=stock){
					//当目标移库的数量发生改变的时候
					if(newStock.getKcsl().compareTo(newMH.getKcsl())==0 && newStock.getCkid().equals(newMH.getCkid())){
						stock.setDprtcode(oldMH.getDprtcode());
						stock.setCkid(oldMH.getCkid());
						stock.setCklb(oldMH.getCklb());
						stock.setCkh(oldMH.getCkh());
						stock.setCkmc(oldMH.getCkmc());
						stock.setKwid(oldMH.getKwid());
						stock.setKwh(oldMH.getKwh());
						count+=stockSerivce.updateStockKc(stock);
						
						//增加库存的REC
						//commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(),UpdateTypeEnum.UPDATE );
						materialRecService.writeStockRec(stock.getId(), czls, stock.getId(), transfer.getYkdh(), StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.UPDATE, "撤销",oldMH.getCkmc(),oldMH.getKwh(),newMH.getKcsl());
					}else{
						count=-1;
					}
				}else{
					//当目标移库的数量发生改变的时候
					if(newStock.getKcsl().compareTo(newMH.getKcsl())==0){
						Stock newstock=oldMH.toStock();  
						String new_stockid=UUID.randomUUID().toString();
						stock.setId(new_stockid);
						stockSerivce.insertSelective(newstock);
						
						//增加库存的REC
						//commonRecService.write(new_stockid, TableEnum.B_H_001, user.getId(),UpdateTypeEnum.SAVE );
						materialRecService.writeStockRec(new_stockid, czls, new_stockid, transfer.getYkdh(), StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.SAVE, "撤销",oldMH.getCkmc(),oldMH.getKwh(),newMH.getKcsl());
					}else{
						count=-1;
					}
				}
			}else if(newMH.getKcsl().compareTo(oldMH.getKcsl())==-1){
				if(null!=stock&&null!=newStock){
					//当目标移库的数量发生改变的时候
					if(newStock.getKcsl().compareTo(newMH.getKcsl())==0&& newStock.getCkid().equals(newMH.getCkid())){
						BigDecimal yksl =  transfer.getMbSl().setScale(2);
						stock.setKcsl(yskc.add(yksl));
						count+=stockSerivce.updateStockKc(stock);
						
						//增加库存的REC
						//commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(),UpdateTypeEnum.UPDATE );
						materialRecService.writeStockRec(stock.getId(), czls, stock.getId(), transfer.getYkdh(), StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.UPDATE, "撤销",oldMH.getCkmc(),oldMH.getKwh(),newMH.getKcsl());
						
						
						materialRecService.writeStockRec(newStock.getId(), czls, newStock.getId(), transfer.getYkdh(), StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.DELETE, "撤销",oldMH.getCkmc(),oldMH.getKwh(),newMH.getKcsl());
						//commonRecService.write(newStock.getId(), TableEnum.B_H_001, user.getId(),UpdateTypeEnum.DELETE );
						stockSerivce.delete(newStock);
					}else{
						count=-1;
					}
				}else{
					//当目标移库的数量发生改变的时候
					if(newStock!=null&&newStock.getKcsl().compareTo(newMH.getKcsl())==0){
						Stock stock_new=newMH.toStock();  
						String id2=UUID.randomUUID().toString();
						stock_new.setId(id2);
						stockSerivce.insertSelective(stock_new);
						//commonRecService.write(id2, TableEnum.B_H_001, user.getId(),UpdateTypeEnum.SAVE );
						materialRecService.writeStockRec(id2, czls, id2, transfer.getYkdh(), StockRecBizTypeEnum.TYPE3, UpdateTypeEnum.SAVE, "撤销",oldMH.getCkmc(),oldMH.getKwh(),newMH.getKcsl());
					}else{
						count=-1;
					}
				}
				
			}else{
				count=-1;
			}
			transfer.setZt("11");                                //将移库状态更新为撤销
			count+=transferMapper.updateByPrimaryKey(transfer);
		}else{
			count=-2;
		}
		return count;
	}

	@Override
	public List<Transfer> selectByKey(String id) {
		return transferMapper.selectByKey(id);
	}
}
