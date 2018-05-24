package com.eray.thjw.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.OrderSourceMapper;
import com.eray.thjw.dao.RevisionNoticeBookLogMapper;
import com.eray.thjw.dao.RevisionNoticeBookMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.RevisionNoticeBook;
import com.eray.thjw.po.RevisionNoticeBookLog;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.RevisionNoticeBookService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.ordersource.OrderSourceEnum;

/**
 * @author pingxiaojun
 * @description 修订通知书 ServiceImpl类
 * @develop date 2016.08.15
 */
@Service
public class RevisionNoticeBookServiceImpl implements RevisionNoticeBookService {
	
	//修订通知书Dao
	@Autowired
	private RevisionNoticeBookMapper revisionNoticeBookMapper;
	
	//修订通知书日志Dao
	@Autowired
	private RevisionNoticeBookLogMapper revisionNoticeBookLogMapper;
	
	//下达指令来源Dao
	@Autowired
	private OrderSourceMapper orderSourceMapper;
	
	@Autowired
	private SaibongUtilService saibongUtilService;     
	//添加历史数据的实现
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private OrderAttachmentService orderAttachmentService;
	@Autowired
	private PlaneModelDataService planeModelDataService;

	/**
	 * 查询修订通知书列表并且分页的实现方法
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryRevisionNoticeBookListByPage(Map<String, Object> requestParamMap) {

		List<Map<String, Object>> list = revisionNoticeBookMapper.selectRevisionNoticeBookListByPage(requestParamMap);
        Map<String, Map<String, Object>> map = new HashMap<String, Map<String,Object>>();
		if (list!=null && !list.isEmpty()) {
			Map<String, Object> param = new HashMap<String, Object>();
			List<String> ids = new ArrayList<String>();
			for (Map<String, Object> row : list) {
				if (!ids.contains(row.get("id").toString())) {
					row.put("orderSources", new ArrayList<OrderSource>());
					ids.add(row.get("id").toString());
					map.put(row.get("id").toString(), row);
				}
			}
			param.put("ids", ids);
			List<OrderSource> orderSources = orderSourceMapper.selectOrderSourceList(param);
			
			if (orderSources!=null && !orderSources.isEmpty()) {
				Map<String, Object> row = null;
				for (OrderSource orderSource : orderSources) {
					if (map.containsKey(orderSource.getZlid())) {
						row = ((Map<String, Object>)map.get(orderSource.getZlid()));
						((List<OrderSource>)row.get("orderSources")).add(orderSource);
					}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 保存修订通知书信息的实现方法
	 */
	@Override
	public Map<String, Object> saveRevisionNoticeBook(Map<String, Object> requestParamMap) throws Exception {
		//响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		String czls=UUID.randomUUID().toString();
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		//提示文字
		String successText = "";
		String failureText = "";
		
		//判断状态（1：保存，2：提交）
		if ((Integer)requestParamMap.get("zt") != null) {
			if ((Integer)requestParamMap.get("zt") == 0) {
				successText = "保存成功";
				failureText = "保存失败";
			}else {
				successText = "提交成功";
				failureText = "提交失败";
			}
		}else {
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", "状态不能为空");
			return responseParamMap;
		}
		try {
			/** 第1步：插入修订通知书信息至数据库 **/
			//获取采番号              
			if(requestParamMap.get("tzslx").toString().equals("2")){
				requestParamMap.put("jszlh", saibongUtilService.generate(user.getJgdm(), SaiBongEnum.XDMEL.getName()));
			}else if(requestParamMap.get("tzslx").toString().equals("3")){
				requestParamMap.put("jszlh", saibongUtilService.generate(user.getJgdm(), SaiBongEnum.XDGK.getName()));
			}else{
				requestParamMap.put("jszlh", saibongUtilService.generate(user.getJgdm(), SaiBongEnum.XDTZ.getName()));
			}
			RevisionNoticeBook revisionNoticeBook = this.assemblyRevisionNoticeBookParam(requestParamMap, "insert");
			revisionNoticeBook.setZdrid(user.getId());
			revisionNoticeBook.setZdbmid(user.getBmdm());
			revisionNoticeBook.setDprtcode(user.getJgdm());
			revisionNoticeBook.setJszt(0);
			if(revisionNoticeBook.getCkzl()!=null && !"".equals(revisionNoticeBook.getCkzl())){
				if(revisionNoticeBook.getCkzl().length()>500){
					revisionNoticeBook.setCkzl(revisionNoticeBook.getCkzl().substring(0,500));
				}
			}
			responseParamMap.put("id", revisionNoticeBook.getId());
			//检查是否有飞机权限存在
			List<String> jxList=new ArrayList<String>();
			jxList.add(revisionNoticeBook.getJx());
			boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),user.getJgdm(), jxList);
			if(!fola){
				throw new BusinessException("机型权限已变更,请刷新后再进行操作");
			}
			int revisionNoticeBookAffectedRows = revisionNoticeBookMapper.insertSelective(revisionNoticeBook);
			
			//受影响的行数大于0表示为成功，否则表示为失败
			if (revisionNoticeBookAffectedRows > 0) {
				/** 第2步：插入修订通知书信息日志至数据库 **/
				if(revisionNoticeBook.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()){
					commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,revisionNoticeBook.getId());
				}else{
					commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.SAVE,revisionNoticeBook.getId());
				}
				
				/** 第3步：插入下达指令来源信息至数据库 **/
				if(null!=requestParamMap.get("orderSourceList") &&!"".equals(requestParamMap.get("orderSourceList"))){
					List <Map<String, String>>orderSourceListMap=(List<Map<String, String>>) requestParamMap.get("orderSourceList");
					List<OrderSource> orderSourceList = new ArrayList<OrderSource>(); 
					for (Map<String, String> map : orderSourceListMap) {
						OrderSource orderSource = new OrderSource();
						orderSource.setPgdh(map.get("pgdh"));
						orderSource.setPgdid(map.get("pgdid"));
						//创建插入对象
						orderSource.setZlxl(3);
						orderSource.setYwzt("");
						orderSource.setZlid(revisionNoticeBook.getId());			 //指令id		
						orderSource.setZlbh(revisionNoticeBook.getJszlh());		 //指令编号
						orderSource.setDprtcode(user.getJgdm());//机构代码
						orderSourceList.add(orderSource);
						
					}
					orderSourceMapper.insertOrderSource(orderSourceList);
				}
				
				/** 第4步：插入附件上传 **/
				if(requestParamMap.get("orderAttachmentList")!=null ){
					List <Map<String, String>>orderAttachmentList=(List<Map<String, String>>) requestParamMap.get("orderAttachmentList");
					for (Map<String, String> map : orderAttachmentList) {
						OrderAttachment orderAttachment =new OrderAttachment();
						String id = UUID.randomUUID().toString();//获取随机的uuid
						orderAttachment.setYswjm((String)map.get("yswjm"));
						orderAttachment.setWbwjm((String)map.get("wbwjm"));
						orderAttachment.setNbwjm((String)map.get("nbwjm"));
						orderAttachment.setCflj((String)map.get("cflj"));
						orderAttachment.setWjdx(new BigDecimal(map.get("wjdx").toString()));
						orderAttachment.setId(id);
						orderAttachment.setMainid(revisionNoticeBook.getId());
						orderAttachment.setCzbmid(user.getBmdm());
						orderAttachment.setCzsj(new Date());
						orderAttachment.setCzrid(user.getId());
						orderAttachment.setDprtcode(user.getJgdm());
						orderAttachment.setYxzt(1);
						orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
						orderAttachmentService.insertSelective(orderAttachment);
						//添加历史数据表
						commonRecService.write(id, TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,revisionNoticeBook.getId());
						
					}
					
					
					
					
					
				}
				//响应处理状态
				responseParamMap.put("state", "success");
				responseParamMap.put("message", successText);
				
			}else {
				responseParamMap.put("state", "Failure");
				responseParamMap.put("message", failureText);
			}
		} catch (Exception e) {
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", failureText);
		}
		
		return responseParamMap;
	}

	/**
	 * 根据主键id查询修订通知书信息的实现方法
	 */
	@Override
	public RevisionNoticeBook queryByPrimaryKey(String id)throws RuntimeException {
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> ids = new ArrayList<String>();
		RevisionNoticeBook revisionNoticeBook = revisionNoticeBookMapper.selectByPrimaryKey(id);
		System.out.println(revisionNoticeBook.toString());
		ids.add(revisionNoticeBook.getJszlh());
		param.put("ids", ids);
	
		List<OrderSource> orderSources = orderSourceMapper.selectOrderSourceList(param);
		if (orderSources!=null && !orderSources.isEmpty()) {
			revisionNoticeBook.setOrderSources(orderSources);
		}
		return revisionNoticeBook;
	}

	/**
	 * 修改修订通知书信息的实现方法
	 */
	@Override
	public Map<String, Object> modifyRevisionNoticeBook(Map<String, Object> requestParamMap, int [] i) throws Exception {
		//响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		//提示文字
		String successText = "";
		String failureText = "";
		
		
		boolean b=this.verification(String.valueOf(requestParamMap.get("id")), i);
		if(b){
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", "该修订通知书已更新，请刷新后再进行操作");
			return responseParamMap;
		}
		
		try {
			/** 第1步：更新修订通知书信息至数据库 **/
			RevisionNoticeBook revisionNoticeBook = this.assemblyRevisionNoticeBookParam(requestParamMap, "update");
			int revisionNoticeBookAffectedRows = revisionNoticeBookMapper.updateByPrimaryKeySelective(revisionNoticeBook);
			
			//受影响的行数大于0表示为成功，否则表示为失败
			if (revisionNoticeBookAffectedRows > 0) {
				/** 第2步：插入修订通知书信息日志至数据库 **/
				if(revisionNoticeBook.getZt()==null || revisionNoticeBook.getZt()==1){
					commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
				}else{
					commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
				}
				
				/** 第3步：删除下达指令来源信息 **/
				orderSourceMapper.deleteOrderSourceByZlid(revisionNoticeBook.getId());
				/** 第4步：插入下达指令来源信息至数据库 **/
				if(requestParamMap.get("orderSourceList")!=null &&requestParamMap.get("orderSourceList")!=""){
					List <Map<String, String>>orderSourceListMap=(List<Map<String, String>>) requestParamMap.get("orderSourceList");
					List<OrderSource> orderSourceList = new ArrayList<OrderSource>(); 
					for (Map<String, String> map : orderSourceListMap) {
						OrderSource orderSource = new OrderSource();
						orderSource.setPgdh(map.get("pgdh"));
						orderSource.setPgdid(map.get("pgdid"));
						//创建插入对象
						orderSource.setZlxl(3);
						orderSource.setYwzt("");
						orderSource.setZlid(revisionNoticeBook.getId());			 //指令id		
						orderSource.setZlbh(revisionNoticeBook.getJszlh());		 //指令编号
						orderSource.setDprtcode(revisionNoticeBook.getDprtcode());//机构代码
						orderSourceList.add(orderSource);
						
					}
					orderSourceMapper.insertOrderSource(orderSourceList);
				}
				/** 第5步：插入附件上传数据至数据库 **/
				if(requestParamMap.get("orderAttachmentList")!=null &&requestParamMap.get("oldOrderAttachmentIds")!=""){
					//对附件表进行操作
					//（1）获取旧数据id
					List <String> oldGdfjIds=(List<String>) requestParamMap.get("oldOrderAttachmentIds");
					//（2）获取新数据id
					List <String> newGdfjIds=new ArrayList<String>();
					//（3）获取新数据list集合
					List <Map<String, String>> orderAttachmentList=(List<Map<String, String>>) requestParamMap.get("orderAttachmentList");
					//（4）遍历新数据集合，并把id放入newXggkIds
					for (Map<String, String> map : orderAttachmentList) {
						newGdfjIds.add(map.get("id"));
						if(String.valueOf(map.get("id")).equals("1")){
							String id = UUID.randomUUID().toString();//获取随机的uuid
							OrderAttachment orderAttachment =new OrderAttachment();
							orderAttachment.setYswjm((String)map.get("yswjm"));
							orderAttachment.setWbwjm((String)map.get("wbwjm"));
							orderAttachment.setNbwjm((String)map.get("nbwjm"));
							orderAttachment.setCflj((String)map.get("cflj"));
							orderAttachment.setWjdx(new BigDecimal((map.get("wjdx").toString())));
							orderAttachment.setId(id);
							orderAttachment.setMainid(revisionNoticeBook.getId());
							orderAttachment.setCzbmid(user.getBmdm());
							orderAttachment.setCzsj(new Date());
							orderAttachment.setCzrid(user.getId());
							orderAttachment.setDprtcode(revisionNoticeBook.getDprtcode());
							orderAttachment.setYxzt(1);
							orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
							orderAttachmentService.insertSelective(orderAttachment);
							//添加历史数据表
							commonRecService.write(id, TableEnum.D_011, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,revisionNoticeBook.getId());
						}
					}
					//遍历旧数据id
					for (String oldGdfjId : oldGdfjIds) {
						if(newGdfjIds.contains(oldGdfjId)){
							for (Map<String, String> map : orderAttachmentList) {
								if(oldGdfjId.equals(String.valueOf(map.get("id")))){
									/*OrderAttachment orderAttachment =new OrderAttachment();
									orderAttachment.setId((String)map.get("id"));
									orderAttachment.setYswjm((String)map.get("yswjm"));
									orderAttachment.setWbwjm((String)map.get("wbwjm"));
									orderAttachment.setNbwjm((String)map.get("nbwjm"));
									orderAttachment.setCflj((String)map.get("cflj"));
									orderAttachment.setWjdx(new BigDecimal(map.get("wjdx").toString()));
									orderAttachmentService.updateByPrimaryKeySelective(orderAttachment);
									//添加历史数据表
									commonRecService.write(orderAttachment.getId(), TableEnum.D_011, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());*/
								}
							}
						}else{
							//添加历史数据表
							commonRecService.write(oldGdfjId, TableEnum.D_011, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,revisionNoticeBook.getId());
							orderAttachmentService.deleteByPrimaryKey(oldGdfjId);
						}
					}
					
					}
				
				
				
				
				//响应处理状态
				responseParamMap.put("state", "success");
				responseParamMap.put("message", successText);
			}else {
				responseParamMap.put("state", "Failure");
				responseParamMap.put("message", failureText);
			}
		} catch (Exception e) {
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", failureText);
			
		}
		
		return responseParamMap;
	}
	
	/**
	 * 作废修订通知书的实现方法
	 */
	@Override
	public Map<String, Object> deleteRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook) throws Exception {
		//响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		String czls=UUID.randomUUID().toString();
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		try {
			int []i={0,5,6};
			boolean b=this.verification(revisionNoticeBook.getId(), i);
			if(b){
				responseParamMap.put("state", "error");
				responseParamMap.put("message", "该修订通知书已更新，请刷新后再进行操作");
				return responseParamMap;
			}
			
			/** 第1步：更新修订通知书状态至数据库 **/
			int revisionNoticeBookAffectedRows = revisionNoticeBookMapper.updateByPrimaryKeySelective(revisionNoticeBook);
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,revisionNoticeBook.getId());
			//受影响的行数大于0表示为成功，否则表示为失败
			if (revisionNoticeBookAffectedRows > 0) {
				responseParamMap.put("state", "success");
				responseParamMap.put("message", "关闭成功");
			}else {
				responseParamMap.put("state", "Failure");
				responseParamMap.put("message", "关闭失败");
			}
		} catch (Exception e) {
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", "关闭失败");
			
		}
		
		return responseParamMap;
	}
	
	/**
	 * 指定结束修订通知书的实现方法
	 */
	@Override
	public Map<String, Object> endRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook)throws BusinessException {
		//响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		String czls=UUID.randomUUID().toString();
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		try {
			int []i={3};
			boolean b=this.verification(revisionNoticeBook.getId(), i);
			if(b){
				responseParamMap.put("state", "error");
				responseParamMap.put("message", "该修订通知书已更新，请刷新后再进行操作");
				return responseParamMap;
			}
			
			/** 第1步：更新修订通知书状态至数据库 **/
			int revisionNoticeBookAffectedRows = revisionNoticeBookMapper.updateByPrimaryKeySelective(revisionNoticeBook);
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().GUANBI, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
			//受影响的行数大于0表示为成功，否则表示为失败
			if (revisionNoticeBookAffectedRows > 0) {
				responseParamMap.put("state", "success");
				responseParamMap.put("message", "提交成功");
			}else {
				responseParamMap.put("state", "Failure");
				responseParamMap.put("message", "操作失败");
			}
		} catch (Exception e) {
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", "操作失败");
			
		}
		
		return responseParamMap;
	}
	/**
	 * 关闭修订通知书的实现方法
	 */
	@Override
	public Map<String, Object> closeRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook)throws BusinessException {
		//响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		String czls=UUID.randomUUID().toString();
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		try {
			int []i={1,2,3};
			boolean b=this.verification(revisionNoticeBook.getId(), i);
			if(b){
				responseParamMap.put("state", "error");
				responseParamMap.put("message", "该修订通知书已更新，请刷新后再进行操作");
				return responseParamMap;
			}
			
			/** 第1步：更新修订通知书状态至数据库 **/
			int revisionNoticeBookAffectedRows = revisionNoticeBookMapper.updateByPrimaryKeySelective(revisionNoticeBook);
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().WANCHEN, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
			//受影响的行数大于0表示为成功，否则表示为失败
			if (revisionNoticeBookAffectedRows > 0) {
				responseParamMap.put("state", "success");
				responseParamMap.put("message", "提交成功");
			}else {
				responseParamMap.put("state", "Failure");
				responseParamMap.put("message", "操作失败");
			}
		} catch (Exception e) {
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", "操作失败");
			
		}
		
		return responseParamMap;
	}
	
	/**
	 * 组装修订通知书参数
	 * @param requestParamMap 请求参数Map
	 * @param action 指令
	 * @return RevisionNoticeBook 修订通知书信息
	 */
	private RevisionNoticeBook assemblyRevisionNoticeBookParam(Map<String, Object> requestParamMap, String action) {
		RevisionNoticeBook revisionNoticeBook = new RevisionNoticeBook();
		
		//判断请求参数Map是否为空
		if (requestParamMap != null ) {
			if (!Utils.Str.isEmpty(action)) {
				if ("insert".equals(action)) {
					//通知书类型（1：维修方案，2：MEL，3：工单）
					if (requestParamMap.get("tzslx") != null) {
						revisionNoticeBook.setTzslx(Integer.valueOf(requestParamMap.get("tzslx").toString()));
					}
					
					revisionNoticeBook.setId(String.valueOf(UUID.randomUUID()));				//主键id
					revisionNoticeBook.setZdsj(new Date());										//制单时间
				}else if ("update".equals(action)) {
					revisionNoticeBook.setId(String.valueOf(requestParamMap.get("id")));		//主键id
				}
			}
			
			//技术指令号
			if (!Utils.Str.isEmpty(String.valueOf(requestParamMap.get("jszlh")))) {
				revisionNoticeBook.setJszlh(String.valueOf(requestParamMap.get("jszlh"))); 			
			}
			
			//机型
			if (!Utils.Str.isEmpty(String.valueOf(requestParamMap.get("jx")))) {
				revisionNoticeBook.setJx(String.valueOf(requestParamMap.get("jx")));				
			}
			
			//参考资料
			if (!Utils.Str.isEmpty(String.valueOf(requestParamMap.get("ckzl")))) {
				if(String.valueOf(requestParamMap.get("ckzl"))!=null && !"".equals(String.valueOf(requestParamMap.get("ckzl")))){
					if(String.valueOf(requestParamMap.get("ckzl")).length()>500){
						revisionNoticeBook.setCkzl(String.valueOf(requestParamMap.get("ckzl")).substring(0,500));
					}else{
						revisionNoticeBook.setCkzl(String.valueOf(requestParamMap.get("ckzl"))); 			
					}
				}
			}
			
			//修订主题
			if (requestParamMap.get("xdzt")!=null) {
				revisionNoticeBook.setXdzt(String.valueOf(requestParamMap.get("xdzt")));			
			}
			
			//修订内容
			if (requestParamMap.get("xdnr")!=null) {
				revisionNoticeBook.setXdnr(String.valueOf(requestParamMap.get("xdnr")));			
			}
			
			//修订人id
			if (!Utils.Str.isEmpty(String.valueOf(requestParamMap.get("xdrid")))) {
				revisionNoticeBook.setXdrid(String.valueOf(requestParamMap.get("xdrid")));			
			}
			
			//修订期限
			if (Utils.DT.toDate((String)requestParamMap.get("xdqx")) != null) {
				revisionNoticeBook.setXdqx(Utils.DT.toDate((String)requestParamMap.get("xdqx")));	
			}
			
			//制单人部门id
			if (!Utils.Str.isEmpty(String.valueOf(requestParamMap.get("zdbmid")))) {
				revisionNoticeBook.setZdbmid(String.valueOf(requestParamMap.get("zdbmid")));		
			}
			
			//制单人id
			if (!Utils.Str.isEmpty(String.valueOf(requestParamMap.get("zdrid")))) {
				revisionNoticeBook.setZdrid(String.valueOf(requestParamMap.get("zdrid")));		
			}
			
			//备注
			if (requestParamMap.get("bz")!=null ) {
				revisionNoticeBook.setBz(String.valueOf(requestParamMap.get("bz")));		
			}
			//版本
			if (requestParamMap.get("bb")!=null ) {
				revisionNoticeBook.setBb(String.valueOf(requestParamMap.get("bb")));		
			}
			
			//状态（1：保存，2：提交，9：关闭）
			if (Integer.valueOf(requestParamMap.get("zt").toString()) != null) {
				revisionNoticeBook.setZt(Integer.valueOf(requestParamMap.get("zt").toString()));						
			}
			
			//打印状态（0：未打印，1：已打印）
			revisionNoticeBook.setDyzt(0);
			
			//接收状态（0：未打印，1：已打印）
			revisionNoticeBook.setJszt(0);
			
			//机构代码
			if (!Utils.Str.isEmpty(String.valueOf(requestParamMap.get("dprtcode")))) {
				revisionNoticeBook.setDprtcode(String.valueOf(requestParamMap.get("dprtcode")));
			}
		}
		
		return revisionNoticeBook;
	}
			
	/**
	 * 组装修订通知书日志参数
	 * @param revisionNoticeBook 修订通知书信息
	 * @param recordType 记录类型
	 * @return RevisionNoticeBook 修订通知书日志信息
	 */
	private RevisionNoticeBookLog assemblyRevisionNoticeBookLogParam(RevisionNoticeBook revisionNoticeBook, Integer recordType) {
		RevisionNoticeBookLog revisionNoticeBookLog = new RevisionNoticeBookLog();
		
		//判断请求参数Map是否为空
		if (revisionNoticeBook != null) {
			revisionNoticeBookLog.setId(String.valueOf(UUID.randomUUID()));		//主键id
			revisionNoticeBookLog.setJszlh(revisionNoticeBook.getJszlh()); 		//技术指令号
			revisionNoticeBookLog.setTzslx(revisionNoticeBook.getTzslx());		//通知书类型（1：维修方案、2：MEL、3：工单）
			revisionNoticeBookLog.setJx(revisionNoticeBook.getJx());			//机型
			revisionNoticeBookLog.setCkzl(revisionNoticeBook.getCkzl()); 		//参考资料
			revisionNoticeBookLog.setXdzt(revisionNoticeBook.getXdzt());		//修订主题
			revisionNoticeBookLog.setXdnr(revisionNoticeBook.getXdnr());		//修订内容
			revisionNoticeBookLog.setXdrid(revisionNoticeBook.getXdrid());		//修订人id
			revisionNoticeBookLog.setXdqx(revisionNoticeBook.getXdqx());		//修订期限
			revisionNoticeBookLog.setZdbmid(revisionNoticeBook.getZdbmid());	//制单人部门id
			revisionNoticeBookLog.setZdrid(revisionNoticeBook.getZdrid());		//制单人id
			revisionNoticeBookLog.setZdsj(revisionNoticeBook.getZdsj());		//制单时间
			revisionNoticeBookLog.setZt(revisionNoticeBook.getZt());			//状态（1：保存，2：提交，9：关闭）
			revisionNoticeBookLog.setDyzt(revisionNoticeBook.getDyzt());		//打印状态（0：未打印，1：已打印）
			revisionNoticeBookLog.setDprtcode(revisionNoticeBook.getDprtcode());//机构代码
			revisionNoticeBookLog.setRecXglx(recordType);						//修改类型（1：新增、2：修改、3：删除）
			revisionNoticeBookLog.setRecCzrid(revisionNoticeBook.getZdrid());	//操作人id
			revisionNoticeBookLog.setRecCzsj(new Date());						//操作时间
		}
		
		return revisionNoticeBookLog;
	}
	/**
	 * @author liub
	 * @description 查询所有修订通知书
	 * @param revisionNoticeBook
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.30
	 */
	public List<Map<String, Object>> findAll(RevisionNoticeBook revisionNoticeBook) throws RuntimeException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<RevisionNoticeBook> findAll = revisionNoticeBookMapper.findAll(revisionNoticeBook);
		for (RevisionNoticeBook book : findAll) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", book.getId());
			map.put("text", book.getJszlh());
			list.add(map);
		}	
		return list;
	}

	/**
	 * @author sunji
	 * @description 检查技术通告修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(User user, String id) {
		//根据id查询该技术通告
		RevisionNoticeBook revisionNoticeBook=revisionNoticeBookMapper.selectByPrimaryKey(id);
		Map<String, Object> returnMap = validatePri(user, revisionNoticeBook);
		//验证维修方案操作权限
		if (!"success".equals(returnMap.get("state"))) {	
			return returnMap;
		}
		return returnMap;
	}
	/**
	 * @author sunji
	 * @description 验证技术通告操作权限
	 * @param user，id
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	private Map<String, Object> validatePri(User user ,RevisionNoticeBook revisionNoticeBook) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		//验证用户是否存在
		if(null == user){
			returnMap.put("state", "error");
			returnMap.put("message", "登录超时,请先登录!");
			return returnMap;
		}
		
		//验证技术通告是否存在
		if(null == revisionNoticeBook){
			returnMap.put("state", "error");
			returnMap.put("message", "维护提示不存在!");
			return returnMap;
		}
		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public void updateshenheRevisionNoticeBook(
			RevisionNoticeBook revisionNoticeBook) throws BusinessException {
		//获取登入人id
		String czls=UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		
		int i[]={OrderSourceEnum.EVALUATED.getId()};
		boolean b=this.verification(revisionNoticeBook.getId(), i);
		if(b){
			throw new BusinessException("该修订通知书已更新，请刷新后再进行操作");
		}
		
		revisionNoticeBook.setShsj(new Date());
		revisionNoticeBook.setShrid(user.getId());
		revisionNoticeBook.setShbmid(user.getBmdm());
		revisionNoticeBookMapper.updateByPrimaryKeySelective(revisionNoticeBook);
		//添加历史数据表
		if(revisionNoticeBook.getZt()==OrderSourceEnum.CHECKED.getId()){
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
		}else if(revisionNoticeBook.getZt()==OrderSourceEnum.REVIEW_THE_REJECTED.getId()){
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
			
		}
		
	}

	@Override
	public void updatepifuRevisionNoticeBook(
			RevisionNoticeBook revisionNoticeBook) throws BusinessException {
		//获取登入人id
		String czls=UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		
		int i[]={OrderSourceEnum.CHECKED.getId()};
		boolean b=this.verification(revisionNoticeBook.getId(), i);
		if(b){
			throw new BusinessException("该修订通知书已更新，请刷新后再进行操作");
		}
		revisionNoticeBook.setPfsj(new Date());
		revisionNoticeBook.setPfrid(user.getId());
		revisionNoticeBook.setPfbmid(user.getBmdm());
		revisionNoticeBookMapper.updateByPrimaryKeySelective(revisionNoticeBook);
		//添加历史数据表
		if(revisionNoticeBook.getZt()==OrderSourceEnum.APPROVE.getId()){
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().YIPIZHUN_WO, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
		}else if(revisionNoticeBook.getZt()==OrderSourceEnum.APPROVE_TURN_.getId()){
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
		}else if(revisionNoticeBook.getZt()==OrderSourceEnum.SUSPEND.getId()){
			commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
		}
		RevisionNoticeBook rnb=revisionNoticeBookMapper.selectByPrimaryKey(revisionNoticeBook.getId());
		
	}

	@Override
	public List<RevisionNoticeBook> queryAll(Map<String, Object > map) {
		return revisionNoticeBookMapper.queryAll(map);
	}
	
	@Override
	public RevisionNoticeBook selectByPrimaryKey(String id) {

		return revisionNoticeBookMapper.selectByPrimaryKey(id);
	}

	/**
	 * 修改接收状态
	 * @param id
	 * @return
	 */
	public void updateJszt(RevisionNoticeBook revisionNoticeBook) {
		String czls=UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		revisionNoticeBookMapper.updateByPrimaryKeySelective(revisionNoticeBook);
		//添加历史数据表
		commonRecService.write(revisionNoticeBook.getId(), TableEnum.B_G_005, user.getId(), czls, revisionNoticeBook.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,revisionNoticeBook.getId());
	}
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList
	 * @develop date 2016.12.28
	 */
	@Override
	public String updateBatchAudit(List<String> idList,String yj){
		
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<RevisionNoticeBook> fItemList = revisionNoticeBookMapper.queryByIdList(idList);
		for (RevisionNoticeBook item : fItemList) {
			if(OrderSourceEnum.EVALUATED.getId() == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("修订通知书【").append(item.getJszlh()).append("】审核通过!<br>");
			}else{
				buffer.append("修订通知书【").append(item.getJszlh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		if(null != auditIdList && auditIdList.size() > 0){	
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("pfyj", yj);
			paramMap.put("zt", OrderSourceEnum.CHECKED.getId());
			revisionNoticeBookMapper.updateBatchAudit(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_005, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
			return buffer.toString();
	}
	
	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList
	 * @develop date 2016.12.28
	 */
	@Override
	public String updateBatchApprove(List<String> idList,String yj){
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<RevisionNoticeBook> fItemList = revisionNoticeBookMapper.queryByIdList(idList);
		
		for (RevisionNoticeBook item : fItemList) {
			if(2 == item.getZt() ){
				auditIdList.add(item.getId());
				buffer.append("修订通知书【").append(item.getJszlh()).append("】批准通过!<br>");
			}else{
				buffer.append("修订通知书【").append(item.getJszlh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		if(null != auditIdList && auditIdList.size() > 0){	
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pfrid", user.getId());
			paramMap.put("pfbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("pfyj", yj);
			paramMap.put("zt", 3);
			revisionNoticeBookMapper.updateBatchApprove(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_005, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
		return buffer.toString();
	}
	
	/**
	 * @author liub
	 * @description 通过评估单id、通知书类型查询修订通知书
	 * @param pgdid,tzslx
	 * @return List<Map<String, Object>> 修订通知书列表
	 * @develop date 2017.03.15
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryByPgdId(String pgdid,Integer tzslx){
		
		List<Map<String, Object>> list = revisionNoticeBookMapper.queryByPgdId(pgdid,tzslx);
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String,Object>>();
		if (list!=null && !list.isEmpty()) {
			Map<String, Object> param = new HashMap<String, Object>();
			List<String> ids = new ArrayList<String>();
			for (Map<String, Object> row : list) {
				if (!ids.contains(row.get("id").toString())) {
					row.put("orderSources", new ArrayList<OrderSource>());
					ids.add(row.get("id").toString());
					map.put(row.get("id").toString(), row);
				}
			}
			param.put("ids", ids);
			List<OrderSource> orderSources = orderSourceMapper.selectOrderSourceList(param);
			
			if (orderSources!=null && !orderSources.isEmpty()) {
				Map<String, Object> row = null;
				for (OrderSource orderSource : orderSources) {
					if (map.containsKey(orderSource.getZlid())) {
						row = ((Map<String, Object>)map.get(orderSource.getZlid()));
						((List<OrderSource>)row.get("orderSources")).add(orderSource);
					}
				}
			}
		}
		return list;
	}
	
	public boolean verification(String id, int [] i){
		User user = ThreadVarUtil.getUser();
		boolean b=true;
		RevisionNoticeBook revisionNoticeBook=revisionNoticeBookMapper.selectByPrimaryKey(id);
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(revisionNoticeBook.getJx());
		boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),revisionNoticeBook.getDprtcode(), jxList);
		if(!fola){
			b=false;
		}
		for (int a = 0; a < i.length; a++) {
			
			if(revisionNoticeBook.getZt()==i[a]){
				b=false;
			}
		}
		
		return b;
	}
	/**
	 * sunji
	 * 查询列表
	 * @param revisionNoticeBook
	 * @return map
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllRevisionNoticeBook(
			 Map<String, Object> requestParamMap, Pagination pagination) throws BusinessException {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		String id=(String) requestParamMap.get("id");
		try {
		requestParamMap.put("id","");
			if (null != requestParamMap  && requestParamMap.size() > 0) {
				requestParamMap.put("start", pagination.getStart());
				requestParamMap.put("end", pagination.getEnd());
				requestParamMap.put("sort", pagination.getSort());
				requestParamMap.put("order", pagination.getOrder());
				
				PageHelper.startPage(pagination);
				// 查询修订通知书列表并且分页
				List<Map<String, Object>> list = this.queryRevisionNoticeBookListByPage(requestParamMap);
				responseParamMap.put("rows", list);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecordMap(list, "id", id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Map<String, Object> newRecord = new HashMap<String, Object>();
						newRecord.put("id", id);
						Pagination page = new Pagination();
						newRecord.put("start", page.getStart());
						newRecord.put("end", page.getEnd());
						newRecord.put("sort", page.getSort());
						newRecord.put("order", page.getOrder());
						newRecord.put("userId", requestParamMap.get("userId"));
						List<Map<String, Object>> newRecordList = this.queryRevisionNoticeBookListByPage(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}

					}
				}
				// 分页参数
				return PageUtil.pack4PageHelper(list, pagination);
				
			}else{
				List<Map<String, Object>> newRecordList = new ArrayList<Map<String, Object>>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Map<String, Object> newRecord = new HashMap<String, Object>();
					newRecord.put("id", id);
					newRecord.put("start", pagination.getStart());
					newRecord.put("end", pagination.getEnd());
					newRecord.put("sort", pagination.getSort());
					newRecord.put("order", pagination.getOrder());
					newRecord.put("userId", requestParamMap.get("userId"));
					pagination.setPage(1);
					newRecordList = this.queryRevisionNoticeBookListByPage(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						
						return PageUtil.pack(1, newRecordList, pagination);
					}

				}
				return PageUtil.pack(0, newRecordList, pagination);
			}

			}
		} catch (Exception e) {
			throw new BusinessException("查询修订通知书列表失败",e);
		}
		return responseParamMap;
	}

}
