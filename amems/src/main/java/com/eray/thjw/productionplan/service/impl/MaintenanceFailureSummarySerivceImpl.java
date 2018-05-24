package com.eray.thjw.productionplan.service.impl;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.MaintenanceFailureSummaryDetailMapper;
import com.eray.thjw.productionplan.dao.MaintenanceFailureSummaryMapper;
import com.eray.thjw.productionplan.po.MaintenanceFailureSummary;
import com.eray.thjw.productionplan.po.MaintenanceFailureSummaryDetail;
import com.eray.thjw.productionplan.service.MaintenanceFailureSummarySerivce;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.LuceneUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import enu.DelStatus;
import enu.LuceneEnum;
/**
 * @author liub
 * @description 维修故障总结service,用于业务逻辑处理
 * @develop date 2017.01.04
 */
@Service
public class MaintenanceFailureSummarySerivceImpl implements MaintenanceFailureSummarySerivce {
	
	/**
	 * @author liub
	 * @description 维修故障总结Mapper
	 * @develop date 2017.01.04
	 */
	@Resource
	private MaintenanceFailureSummaryMapper summaryMapper;
	
	/**
	 * @author liub
	 * @description 故障总结经过思路Mapper
	 * @develop date 2017.01.04
	 */
	@Resource
	private MaintenanceFailureSummaryDetailMapper summaryDetailMapper;
	
	/**
  	 * @author liub
  	 * @description 附件service
  	 * @develop date 2017.01.17
  	 */
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * @author liub
	 * @description 公共service
	 * @develop date 2017.01.06
	 */
	@Resource
	private CommonService commonService;

	/**
	 * @author liub
	 * @description 增加维修故障总结
	 * @param summary
	 * @develop date 2017.01.04
	 */
	@Override
	public String add(MaintenanceFailureSummary summary){
		User user = ThreadVarUtil.getUser();
		//新增维修故障总结
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		summary.setId(id);
		summary.setZt(DelStatus.TAKE_EFFECT.getId());
		summary.setZdrid(user.getId());
		summary.setZddwid(user.getBmdm());
		summary.setDprtcode(user.getJgdm());
		summaryMapper.insertSelective(summary);
		summary.setSummaryDetailJson("");
		summary.setAttachmentsJson("");
		StringBuffer jgslstr = new StringBuffer();
		//新增故障总结经过思路
		if(null != summary.getSummaryDetailList() && summary.getSummaryDetailList().size() > 0){
			for (MaintenanceFailureSummaryDetail summaryDetail : summary.getSummaryDetailList()) {
				String detailId = UUID.randomUUID().toString();
				summaryDetail.setId(detailId);
				summaryDetail.setMainid(id);
				summaryDetail.setZt(DelStatus.TAKE_EFFECT.getId());
				summaryDetail.setWhrid(user.getId());
				summaryDetail.setWhdwid(user.getBmdm());
				summaryDetailMapper.insertSelective(summaryDetail);
				jgslstr.append(summaryDetail.getPgjg());
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
			summary.setSummaryDetailJson(gson.toJson(summary.getSummaryDetailList()));
		}
		summary.setJgslstr(jgslstr.toString());
		if(null != summary.getAttachments() && summary.getAttachments().size() > 0){
			attachmentService.eidtAttachment(summary.getAttachments(), summary.getId(),summary.getDprtcode());//编辑附件
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();  
			summary.setAttachmentsJson(gson.toJson(summary.getAttachments()));
		}
		LuceneUtil.addDocument(getDocument(summary),LuceneEnum.MAINTENANCE_RECORD);//新增故障总结到索引库
		return id;
	}
	
	/**
	 * @author liub
	 * @description 修改维修故障总结
	 * @param summary
	 * @develop date 2017.01.04
	 */
	@Override
	public void edit(MaintenanceFailureSummary summary){
		User user = ThreadVarUtil.getUser();
		summary.setZdrid(user.getId());
		summaryMapper.updateByPrimaryKeySelective(summary);
		summary.setSummaryDetailJson("");
		summary.setAttachmentsJson("");
		List<String> oldDetailIdList = summary.getDetailIdList();
		List<String> newDetailIdList = new ArrayList<String>();
		//新增故障总结经过思路
		StringBuffer jgslstr = new StringBuffer();
		if(null != summary.getSummaryDetailList() && summary.getSummaryDetailList().size() > 0){
			for(MaintenanceFailureSummaryDetail summaryDetail : summary.getSummaryDetailList()) {
				newDetailIdList.add(summaryDetail.getId());
				if(null != summaryDetail.getId() && !"".equals(summaryDetail.getId())){
					summaryDetail.setWhrid(user.getId());
					summaryDetailMapper.updateByPrimaryKeySelective(summaryDetail);
				}else{
					String detailId = UUID.randomUUID().toString();
					summaryDetail.setId(detailId);
					summaryDetail.setMainid(summary.getId());
					summaryDetail.setZt(DelStatus.TAKE_EFFECT.getId());
					summaryDetail.setWhrid(user.getId());
					summaryDetail.setWhdwid(user.getBmdm());
					summaryDetailMapper.insertSelective(summaryDetail);
				}
				jgslstr.append(summaryDetail.getPgjg());
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
			summary.setSummaryDetailJson(gson.toJson(summary.getSummaryDetailList()));
		}
		summary.setJgslstr(jgslstr.toString());
		//删除故障总结经过思路
		for (String oldDetailIdId : oldDetailIdList) {
			if(!newDetailIdList.contains(oldDetailIdId)){
				MaintenanceFailureSummaryDetail summaryDetail = new MaintenanceFailureSummaryDetail();
				summaryDetail.setId(oldDetailIdId);
				summaryDetail.setWhrid(user.getId());
				summaryDetail.setZt(DelStatus.LOSE_EFFECT.getId());
				summaryDetailMapper.updateByPrimaryKeySelective(summaryDetail);
			}
		}
		attachmentService.eidtAttachment(summary.getAttachments(), summary.getId(),summary.getDprtcode());//编辑附件
		Attachment attachment = new Attachment();
		attachment.setMainid(summary.getId());
		List<Attachment> attachmentList = attachmentService.queryListAttachments(attachment);//查询附件
		if(null != attachmentList && attachmentList.size() > 0){
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
			summary.setAttachmentsJson(gson.toJson(attachmentList));
		}
		LuceneUtil.updateDocument(getDocument(summary),LuceneEnum.MAINTENANCE_RECORD);//更新
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 * @develop date 2017.01.04
	 */
	@Override
	public void deleteSummary(String id){
		MaintenanceFailureSummary summary = new MaintenanceFailureSummary();
		summary.setId(id);
		summary.setZt(DelStatus.LOSE_EFFECT.getId());
		summaryMapper.updateByPrimaryKeySelective(summary);
		LuceneUtil.deleteDocument(id,LuceneEnum.MAINTENANCE_RECORD);
	}
	
	/**
	 * @author liub
	 * @description  根据条件分页查询维修故障总结列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.04
	 */
    @Override
	public List<Map<String, Object>> queryAllPageList(BaseEntity baseEntity){
		return summaryMapper.queryAllPageList(baseEntity);
	}

    /**
	 * @author liub
	 * @description 根据航材id查询维修故障总结及对应详情信息
	 * @param id
	 * @return MaintenanceFailureSummary
	 * @develop date 2017.01.04
	 */
    @Override
	public MaintenanceFailureSummary selectById(String id){
		return summaryMapper.selectById(id);
	}
    
    /**
	 * @author liub
	 * @description  根据条件分页查询维修故障总结列表(lucene)
	 * @param baseEntity,dprtList
	 * @return Map<String,Object>
	 * @develop date 2017.01.06
	 */
	@Override
	public Map<String,Object> queryLucenePageList(BaseEntity baseEntity, List<String> dprtList){
    	TopDocs topDocs = null;
    	Query query = null;
    	String keyword = null;
    	Pagination pagination = null; 
    	String[] fields = null;
    	List<Map<String, Object>> list = null;
    	Map<String, String> paramMap = null;
		try {
			fields = new String[]{"gjc", "gzms", "jyzj", "zy", "jgslstr", "fjjx", "zjhstr", "fjzch"};
			User user = ThreadVarUtil.getUser();
			keyword = String.valueOf(baseEntity.getParamsMap().get("keyword"));
			paramMap = new HashMap<String, String>();
			pagination = baseEntity.getPagination();
			paramMap.put("keyword", keyword);
			paramMap.put("sortType", pagination.getSort());
			paramMap.put("dprtcode", user.getJgdm());
			paramMap.put("zdrid", user.getId());
			topDocs = LuceneUtil.query(fields,dprtList,paramMap, pagination.getPage(), pagination.getRows(),"asc".equals(pagination.getOrder())?false:true,LuceneEnum.MAINTENANCE_RECORD);
			if(null == topDocs){
				return PageUtil.pack(0, new ArrayList<Map<String, Object>>(), pagination);
			}
			int count = topDocs.totalHits;
			if(count == 0){
				return PageUtil.pack(count, new ArrayList<Map<String, Object>>(), pagination);
			}
			list = new ArrayList<Map<String,Object>>();
			query = LuceneUtil.getKeyworkQuery(fields, keyword);
			for(int i = (pagination.getPage() - 1) * pagination.getRows() ; i < topDocs.scoreDocs.length ; i++){
				Map<String, Object> map = new HashMap<String, Object>();
				ScoreDoc scoreDoc = topDocs.scoreDocs[i];
				int docId = scoreDoc.doc;
				map.put("score", scoreDoc.score);//当前文档的积分
				map.put("docId", scoreDoc.score);//当前文档的逻辑编号
				// 通过逻辑编号去查询真正的document文档
				Document doc = LuceneUtil.getIndexSearcher(LuceneEnum.MAINTENANCE_RECORD).doc(docId);
				map.put("id", doc.get("id"));
				map.put("dprtcode", doc.get("dprtcode"));
				map.put("kjfw", doc.get("kjfw"));
				map.put("bjh", doc.get("bjh"));
				map.put("gzmsTitle", doc.get("gzms"));
				map.put("jyzjTitle", doc.get("jyzj"));
				map.put("gzms", LuceneUtil.setHighlighter(query, 10000, doc.get("gzms")));
				map.put("gjc", LuceneUtil.setHighlighter(query, 10000, doc.get("gjc")));
				map.put("gzmsStr", LuceneUtil.setHighlighter(query, 150, doc.get("gzms")));
				map.put("jyzjStr", LuceneUtil.setHighlighter(query, 70, doc.get("jyzj")));
				map.put("jyzj", LuceneUtil.setHighlighter(query, 10000, doc.get("jyzj")));
				map.put("fjjx", LuceneUtil.setHighlighter(query, 10000, doc.get("fjjx")));
				map.put("fjzch", LuceneUtil.setHighlighter(query, 10000, doc.get("fjzch")));
				map.put("jd", doc.get("jd"));
				map.put("zjh", doc.get("zjh"));
				map.put("zy", doc.get("zy"));
				map.put("zddwid", doc.get("zddwid"));
				map.put("zdrid", doc.get("zdrid"));
				map.put("zdsj", DateTools.stringToDate(doc.get("zdsj")));
				map.put("jdstr", doc.get("jdstr"));
				map.put("zjhstr", LuceneUtil.setHighlighter(query, 10000, doc.get("zjhstr")));
				map.put("zystr", LuceneUtil.setHighlighter(query, 10000, doc.get("zystr")));
				map.put("displayName", doc.get("displayName"));
				if(null != doc.get("attachmentsJson") && !"".equals(doc.get("attachmentsJson"))){
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();  
					map.put("attachmentList", gson.fromJson(doc.get("attachmentsJson"), List.class));
				}
				if(null != doc.get("summaryDetailJson") && !"".equals(doc.get("summaryDetailJson"))){
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); 
					//List<Map<String, Object>> summaryDetailList = gson.fromJson(doc.get("summaryDetailJson"), List.class);
					//Collections.sort(summaryDetailList, new DateOrderCompare());//排序调用方法
					map.put("detailList", setHighlighter(gson.fromJson(doc.get("summaryDetailJson"), List.class),query));
				}
				list.add(map);
			}
			return PageUtil.pack(count, list, pagination);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    
    /**
     * @author liub
     * @description 设置档案详情高亮
     * @param list,query
     * @return List<Map<String, Object>>
     * @develop date 2017.02.09
 	*/
	private List<Map<String, Object>> setHighlighter(List<Map<String, Object>> list,Query query){
		for (Map<String, Object> map : list) {
			map.put("pgjg", LuceneUtil.setHighlighter(query, 10000, String.valueOf(map.get("pgjg"))));
		}
   		return list;
    }
    
    /**
     * @author liub
     * @description 获取document
     * @param summary
     * @return Document
     * @develop date 2017.01.06
 	*/
	private Document getDocument(MaintenanceFailureSummary summary){
       	Document doc = null;
       	Map<String, Object> paramsMap = null;
   		try {
   			doc = new Document();
   			doc.add(new Field("id",summary.getId(),Field.Store.YES,Field.Index.NOT_ANALYZED));
   			doc.add(new Field("dprtcode",ThreadVarUtil.getUser().getJgdm(),Field.Store.YES,Field.Index.NOT_ANALYZED));
   			doc.add(new Field("kjfw",String.valueOf(summary.getKjfw()),TextField.TYPE_STORED));
   			doc.add(new Field("gjc", summary.getGjc(), TextField.TYPE_STORED));
   			doc.add(new Field("bjh", summary.getBjh(), TextField.TYPE_STORED));
   			doc.add(new Field("gzms", summary.getGzms(), TextField.TYPE_STORED));
   			doc.add(new Field("jyzj", summary.getJyzj(), TextField.TYPE_STORED));
   			doc.add(new Field("fjjx", summary.getFjjx(), TextField.TYPE_STORED));
   			doc.add(new Field("fjzch", summary.getFjzch(), TextField.TYPE_STORED));
   			doc.add(new Field("jd", summary.getJd(), TextField.TYPE_STORED));
   			doc.add(new Field("zjh", summary.getZjh(), TextField.TYPE_STORED));
   			doc.add(new Field("zy", summary.getZy(), TextField.TYPE_STORED));
   			String bmId = "";
   			if(null != ThreadVarUtil.getUser().getBmdm()){
   				bmId = ThreadVarUtil.getUser().getBmdm();
   			}
   			doc.add(new Field("zddwid", bmId, TextField.TYPE_STORED));
   			doc.add(new Field("zdrid",ThreadVarUtil.getUser().getId(),Field.Store.YES,Field.Index.NOT_ANALYZED));
   			doc.add(new Field("displayName", ThreadVarUtil.getUser().getDisplayName(), TextField.TYPE_STORED));
   			doc.add(new Field("zdsj", DateTools.dateToString(commonService.getSysdate(),  DateTools.Resolution.SECOND), TextField.TYPE_STORED));
   			paramsMap = summary.getParamsMap();
   			if(null != paramsMap){
   				doc.add(new Field("jdstr", String.valueOf(paramsMap.get("jdstr")), TextField.TYPE_STORED));
   	   			doc.add(new Field("zjhstr", String.valueOf(paramsMap.get("zjhstr")), TextField.TYPE_STORED));
   	   			doc.add(new Field("zystr", String.valueOf(paramsMap.get("zystr")), TextField.TYPE_STORED));
   			}
   			doc.add(new Field("jgslstr", summary.getJgslstr(), TextField.TYPE_STORED));
   			doc.add(new Field("attachmentsJson", summary.getAttachmentsJson(), TextField.TYPE_STORED));
   			doc.add(new Field("summaryDetailJson", summary.getSummaryDetailJson(), TextField.TYPE_STORED));
   		} catch (Exception e) {
   			throw new RuntimeException(e);
   		}
   		return doc;
    }
    
}

/**
 * @author liub
 * @description  整个详情排序
 * @develop date 2016.08.11
 */
class DateOrderCompare implements Comparator<Map<String, Object>> {

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		if(null == o1.get("clsj") || null == o2.get("clsj")){
			return 0;
		}
		String s1= (String)o1.get("clsj");
		String s2 = (String)o2.get("clsj");
		return s1.compareTo(s2);
	}
}
 
