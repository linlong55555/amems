package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.QualityCheckMapper;
import com.eray.thjw.aerialmaterial.po.QualityCheck;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.QualityCheckService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;

@Service
public class QualityCheckServiceImpl implements QualityCheckService {
     
	@Resource
	private QualityCheckMapper qualityCheckMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private AttachmentMapper attachmentMapper;
    @Resource
 	private CommonRecService commonRecService;
    @Resource
    private StockSerivce stockService;
    @Resource
	private SaibongUtilService saibongUtilService;           //REC
    @Resource
    private MaterialRecService materialRecService;   //航材的REC
	
    
	@Override
	public List<QualityCheck> selectQualityCheckList(QualityCheck record) {
		return qualityCheckMapper.selectQualityCheckList(record);
	}

	@Override
	public QualityCheck selectByPrimaryKey(String id) {
		return qualityCheckMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(QualityCheck qualitycheck) {
		int count =0;
		QualityCheck qc_temp=selectByPrimaryKey(qualitycheck.getId());
		if(qc_temp.getZt().intValue()==1){      //判断状态是否改变
			String czls = UUID.randomUUID().toString();  //日志的操作流水
			User user = ThreadVarUtil.getUser();
			qualitycheck.setZdrid(user.getId());
			qualitycheck.setZdbmid(user.getBmdm());
			qualitycheck.setCzls(czls);               //日志的操作流水
			//通过采番获取移库编号
			String jydh;
			try {
				jydh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HCJY.getName());
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
			if(qualitycheck.getJydh()==null||"".equals(qualitycheck.getJydh())){
				qualitycheck.setJydh(jydh);
			}
			if(qualitycheck.getZt()==1){                   //当检验的时候是保存状态
				count= qualityCheckMapper.updateByPrimaryKeySelective(qualitycheck);               //更新检验单
			}else{
				count= qualityCheckMapper.updateByPrimaryKeySelective(qualitycheck);               //更新库存
				Stock st=new Stock();
				st.setId(qualitycheck.getKcid());
				st.setHcly(qualitycheck.getHcly());
				st.setXkzh(qualitycheck.getXkzh());
				st.setShzh(qualitycheck.getShzh());
				st.setShzwz(qualitycheck.getShzwz());
				st.setHjsm(qualitycheck.getHjsm());
				st.setTsn(qualitycheck.getTsn());
				st.setTso(qualitycheck.getTso());
				st.setCfyq(qualitycheck.getCfyq());
				st.setBz(qualitycheck.getBz());
				st.setJydid(qualitycheck.getId());
				st.setZt(qualitycheck.getZt());
				count+=stockService.updateByPrimaryKeySelective(st);       //更新库存表
				
				//增加库存的REC
				//commonRecService.write(user.getId(), TableEnum.B_H_001, user.getId(),UpdateTypeEnum.UPDATE );
				materialRecService.writeStockRec(qualitycheck.getKcid(), czls, qualitycheck.getId(), qualitycheck.getJydh(), StockRecBizTypeEnum.TYPE5, UpdateTypeEnum.UPDATE, "",qualitycheck.getJydh());
				
				//更新合同提订数量
				count+=qualityCheckMapper.updateHtNum(qualitycheck);
			}
			List<Attachment> attList=qualitycheck.getAttachmentList();
			for (Attachment attachment : attList){
				if(attachment.getId().equals("")){
					UUID uuid = UUID.randomUUID();
					String id2 = uuid.toString();
					attachment.setId(id2);
					attachment.setMainid(qualitycheck.getId());
					attachment.setCzbmid(user.getBmdm());;
					attachment.setCzrid(user.getId());
					count=attachmentMapper.insertSelective(attachment);
					if(qualitycheck.getZt()==1){
						commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(),qualitycheck.getCzls() ,LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,qualitycheck.getId());
					}else{
						commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(),qualitycheck.getCzls() ,LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.SAVE,qualitycheck.getId());
					}
				}
			}
			List<String> ids=qualitycheck.getDel_ids();
			for (String id : ids) {
				count+= attachmentService.updateByPrimaryKey(id);
				if(qualitycheck.getZt()==1){
					commonRecService.write(id, TableEnum.D_011, user.getId(),qualitycheck.getCzls() ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,qualitycheck.getId());
				}else{
					commonRecService.write(id, TableEnum.D_011, user.getId(),qualitycheck.getCzls() ,LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.UPDATE,qualitycheck.getId());
				}
			}
		}else{
			count=-1;
		}
		return count;
	}
	@Override
	public void save(QualityCheck qualityCheck) {
		qualityCheckMapper.insertSelective(qualityCheck);
	}
}
