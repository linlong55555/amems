package com.eray.thjw.quality.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.CommonMapper;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.dao.WorkRequireMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.project2.dao.TodoMapper;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.quality.dao.MaintenancePersonnelMapper;
import com.eray.thjw.quality.dao.PostApplicationMapper;
import com.eray.thjw.quality.dao.PostApplicationPXPGMapper;
import com.eray.thjw.quality.dao.PostApplicationRYZZPGMapper;
import com.eray.thjw.quality.dao.PostApplicationSQJXMapper;
import com.eray.thjw.quality.po.PersonnelToPost;
import com.eray.thjw.quality.po.PostApplication;
import com.eray.thjw.quality.po.PostApplicationPXPG;
import com.eray.thjw.quality.po.PostApplicationSQJX;
import com.eray.thjw.quality.service.PersonnelToPostService;
import com.eray.thjw.quality.service.PostApplicationPXPGService;
import com.eray.thjw.quality.service.PostApplicationRYZZPGService;
import com.eray.thjw.quality.service.PostApplicationSQJXService;
import com.eray.thjw.quality.service.PostApplicationService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.BusinessMapper;
import com.eray.thjw.training.dao.BusinessToMaintenancePersonnelMapper;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.training.service.BusinessToMaintenancePersonnelService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.ConclusionEnum;
import enu.common.EffectiveEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoEnum;
import enu.produce.UndoStatusEnum;
import enu.quality.PostStatusEnum;
/**
 * 
 * @Description 岗位授权serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("postApplicationService")
public class PostApplicationServiceImpl implements PostApplicationService  {

	@Resource
	private PostApplicationMapper postApplicationMapper;
	
	@Resource
	private CommonMapper commonMapper;
	
	@Resource
	private PostApplicationPXPGMapper postApplicationPXPGMapper;
	
	@Resource
	private PostApplicationRYZZPGMapper postApplicationRYZZPGMapper;
	
	@Resource
	private WorkRequireMapper workRequireMapper;
	
	@Resource
	private BusinessToMaintenancePersonnelMapper businessToMaintenancePersonnelMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private PersonnelToPostService personnelToPostService;
	
	@Resource
	private PostApplicationPXPGService applicationPXPGService;
	
	@Resource
	private PostApplicationRYZZPGService applicationRYZZPGService;
	
	@Resource
	private PostApplicationSQJXService postApplicationSQJXService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private BusinessToMaintenancePersonnelService businessToMaintenancePersonnelService;
	
	@Resource
	private MaintenancePersonnelMapper maintenancePersonnelMapper;
	
	@Resource
	private BusinessMapper businessMapper;
	
	@Resource
	private TodorsService todorsService;
	
	@Resource
	private PostApplicationSQJXMapper postApplicationSQJXMapper;
	

	/**
	 * @Description 岗位授权分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param PostApplication 岗位授权
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(PostApplication postApplication)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = postApplication.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		postApplication.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		postApplication.getParamsMap().put("userId", user.getId());
		postApplication.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(postApplication.getPagination());
		List<PostApplication> list = postApplicationMapper.queryAllPageList(postApplication);
		if(((Page)list).getTotal() > 0){
			// 获取现任职务
			getOrders(list);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					PostApplication param = new PostApplication();
					param.setId(id);
					List<PostApplication> newRecordList = postApplicationMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			setSQJXList(list);
			return PageUtil.pack4PageHelper(list, postApplication.getPagination());
		}else{
			List<PostApplication> newRecordList = new ArrayList<PostApplication>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				PostApplication param = new PostApplication();
				param.setId(id);
				newRecordList = postApplicationMapper.queryAllPageList(param);
				// 获取现任职务
				getOrders(list);
				if(newRecordList != null && newRecordList.size() == 1){
					setSQJXList(newRecordList);
					return PageUtil.pack(1, newRecordList, postApplication.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, postApplication.getPagination());
		}
	}
	
	/**
	 * @Description 设置申请机型
	 * @CreateTime 2018-1-31 上午11:12:22
	 * @CreateBy 刘兵
	 * @param postApplicationList
	 */
	private void setSQJXList(List<PostApplication> postApplicationList){
		List<String> mainidList = new ArrayList<String>();
		for (PostApplication postApplication : postApplicationList) {
				mainidList.add(postApplication.getId());
		}
		if(0 != mainidList.size()){
			List<PostApplicationSQJX> postApplicationSQJXList = postApplicationSQJXService.queryByMainidList(mainidList);
			Map<String, List<PostApplicationSQJX>> map = new HashMap<String, List<PostApplicationSQJX>>();
			for (PostApplicationSQJX postApplicationSQJX : postApplicationSQJXList){
				List<PostApplicationSQJX> pSQJXList = map.get(postApplicationSQJX.getMainid());
				if(null == pSQJXList){
					pSQJXList = new ArrayList<PostApplicationSQJX>();
					map.put(postApplicationSQJX.getMainid(), pSQJXList);
				}
				pSQJXList.add(postApplicationSQJX);
			}
			for (PostApplication postApplication : postApplicationList) {
				postApplication.setPostApplicationSQJXList(map.get(postApplication.getId()));
				mainidList.add(postApplication.getId());
			}
		}
	}
	
	/**
	 * @Description 获取岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param mainid 父id
	 */
	@Override
	public List<PostApplicationSQJX> querySQJXByMainid(String mainid) {
		return postApplicationSQJXService.queryByMainid(mainid);
	}
	
	/**
	 * 
	 * @Description 获取现任职务
	 * @CreateTime 2017年9月1日 上午9:26:50
	 * @CreateBy 林龙
	 * @param bulletinList
	 */
	private void getOrders(List<PostApplication> postApplicationList) {
		List<String> pgdidList = new ArrayList<String>();// 评估单id集合
		for (PostApplication postApplication : postApplicationList) {
			pgdidList.add(postApplication.getSqrdaid());
		}
		if (pgdidList.size() > 0) {
			List<PersonnelToPost> isList = personnelToPostService.selectMainidList(pgdidList);
			if (isList.size() > 0) {
				for (PostApplication postApplication: postApplicationList) {
					String  gwzw="";
					int i=1;
					for (PersonnelToPost personnelToPost : isList) {
						if (postApplication.getSqrdaid().equals(personnelToPost.getMainid())) {
							Date  maxks =personnelToPost.getKsrq(); //最大开始日期
							Date  maxjs =personnelToPost.getJsrq(); //最大结束日期
							if(i==1){
								gwzw=personnelToPost.getGwzw();
								i=2;
							}
							if (personnelToPost.getKsrq().getTime() > maxks.getTime() &&  personnelToPost.getJsrq().getTime() > maxjs.getTime()) {
								gwzw=personnelToPost.getGwzw();
								i=1;
							}else{
								if(i==2){
									continue;
								}
							}
						}
					}
					postApplication.getParamsMap().put("xrzz", gwzw);
				}
			}
		}
	}
	/**
	 * @Description 保存/提交 岗位授权
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 岗位授权
	 * @return 岗位授权id
	 * @throws BusinessException
	 */
	@Override
	public String save(PostApplication postApplication) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//新增岗位授权 
		insertSelective(postApplication,uuid,czls,user);
		
		//岗位申请提交写待办
		if(PostStatusEnum.Submit.getId().equals(postApplication.getZt())){
			String sqrXm=maintenancePersonnelMapper.selectByPrimaryKey(postApplication.getSqrdaid()).getXm();
			String gwmc=businessMapper.selectByPrimaryKey(postApplication.getSqgwid()).getDgmc();
			StringBuilder sm=new StringBuilder(" ");
			sm.append("请审批").append(sqrXm).append("申请的").append(gwmc).append("岗位");
			List<PostApplicationSQJX>  jxList=postApplication.getPostApplicationSQJXList();
			StringBuilder jx=new StringBuilder(" ");
			if(jxList!=null){
				for(PostApplicationSQJX postApplicationSQJX:jxList){
					jx.append(postApplicationSQJX.getFjjx()).append(",");
				}	
				jx.deleteCharAt(jx.length()-1);
				if(!" ".equals(jx.toString())){
					sm.append(" 机型(").append(jx.toString()).append(")");
				}				
			}			    			 
			 todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), postApplication.getId(), postApplication.getSqsqdh(),
					 null, null, UndoEnum.GWSQ.getId(), NodeEnum.NODE2.getId(), sm.toString(),
					 null, postApplication.getShrid(), "quality:post:review:main", postApplication.getId(), null);
			 
			 todorsService.updateToDo(postApplication.getId(), NodeEnum.NODE5.getId(), UndoStatusEnum.DCL.getId(), user.getId());
			  
		}
		
		return uuid;
	}
	
	/**
	 * @Description 新增岗位授权
	 * @CreateTime 2017年9月27日 下午1:43:30
	 * @CreateBy 林龙
	 * @param postApplication
	 * @param uuid 岗位授权id
	 * @param czls 流水号
	 * @param user 当前登录人
	 */
	private void insertSelective(PostApplication postApplication, String uuid,String czls,User user)throws BusinessException {

		postApplication.setSqrid(user.getId());			//申请人id
		//当状态为提交时排除掉自身的申请单，申请人id、申请岗位、存在状态（1保存、2提交、3已审核、5审核驳回）的申请单，则报错，不能重复申请
		if(postApplication.getZt()==PostStatusEnum.Submit.getId()){
			valid4SavebusinessToMain(postApplication);
		}
		String sqsqdh=setBldh(postApplication,user); 	//获取最新岗位授权单号
		postApplication.setId(uuid);                    //id
		postApplication.setDprtcode(user.getJgdm());    //组织机构
		postApplication.setWhrid(user.getId());		    //用户id
		postApplication.setWhbmid(user.getBmdm());	    //部门id
		postApplication.setSqbmid(user.getBmdm());		//申请部门id
	
		postApplication.setSqsqdh(sqsqdh);				//申请授权单号
		//新增岗位授权
		postApplicationMapper.insertSelective(postApplication);
		//添加新增日志
		commonRecService.write(uuid, TableEnum.B_Z_005,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid);
		//添加新增机型
		postApplicationSQJXService.save(postApplication.getPostApplicationSQJXList(), postApplication.getId());
		
	}
	
	/**
	 * @Description 根据岗位授权单对象获取最新岗位授权单编号
	 * @CreateTime 2017年9月27日 下午1:48:51
	 * @CreateBy 林龙
	 * @param postApplication  岗位授权对象
	 * @param user 当前用户
	 * @return
	 * @throws BusinessException 
	 */
	private String setBldh(PostApplication postApplication,User user) throws BusinessException {
		StringBuffer bldhNew = new StringBuffer();//岗位授权编号
		PostApplication fai = new PostApplication(); //new 岗位授权
		boolean b = true;
		while(b){
			String sqsqdh;
			try {
				//采番获得授权单号
				sqsqdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.SQSQD.getName()); 
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			bldhNew.append(sqsqdh);
			fai.setSqsqdh(sqsqdh);
			fai.setDprtcode(user.getJgdm());
			int i = postApplicationMapper.queryCount(fai); 
			if(i <= 0){
				b = false;
			}
		}
		return bldhNew.toString();
	}

	/**
	 * @Description 根据岗位授权id查询岗位授权信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param PostApplication 岗位授权
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public PostApplication getInfoById(PostApplication postApplication)throws BusinessException {
		return postApplicationMapper.getInfoById(postApplication);
	}
	
	/**
	 * @Description 修改保存岗位授权
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return 岗位授权id
	 * @throws BusinessException
	 */
	@Override
	public String update(PostApplication postApplication) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//修改岗位授权
		updateByPrimaryKeySelective(postApplication,czls,user);
		
		//岗位申请提交写待办
		if(PostStatusEnum.Submit.getId().equals(postApplication.getZt())){
			StringBuilder  builder=new StringBuilder(" ");
			 String sqrXm=maintenancePersonnelMapper.selectByPrimaryKey(postApplication.getSqrdaid()).getXm();
			 String gwmc=businessMapper.selectByPrimaryKey(postApplication.getSqgwid()).getDgmc();
			 StringBuilder sm=new StringBuilder(" ");
				sm.append("请审批 ").append(sqrXm).append("申请的").append(gwmc).append("岗位");
			List<PostApplicationSQJX>  jxList=postApplication.getPostApplicationSQJXList();
			if(jxList!=null){
				for(PostApplicationSQJX postApplicationSQJX:jxList){
					builder.append(postApplicationSQJX.getFjjx()).append(",");
				}
				 builder.deleteCharAt(builder.length()-1);
				 if(!" ".equals(builder.toString())){
					 sm.append(" 机型(").append(builder.toString()).append(")"); 
				 }				 
			}					 
			 todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), postApplication.getId(), postApplication.getSqsqdh(),
					 null, null, UndoEnum.GWSQ.getId(), NodeEnum.NODE2.getId(), sm.toString(),
					 null, postApplication.getShrid(), "quality:post:review:main", postApplication.getId(), null);
			 
			 todorsService.updateToDo(postApplication.getId(), NodeEnum.NODE5.getId(), UndoStatusEnum.DCL.getId(), user.getId());
			  
		}
		
		return postApplication.getId();
	}

	/**
	 * @Description 修改岗位授权
	 * @CreateTime 2017年9月27日 下午4:40:24
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @param czls 流水号
	 */
	private void updateByPrimaryKeySelective(PostApplication postApplication,String czls,User user)throws BusinessException {
		
		// 验证单据状态是否已变更
		validation4CurrentZt(postApplication.getId(), Integer.valueOf((String) postApplication.getParamsMap().get("currentZt")));
		
		//当状态为提交时排除掉自身的申请单，申请人id、申请岗位、存在状态（1保存、2提交、3已审核、5审核驳回）的申请单，则报错，不能重复申请
		if(postApplication.getZt()==PostStatusEnum.Submit.getId()){
			valid4SavebusinessToMain(postApplication);
		}
		
		postApplication.setWhrid(user.getBmdm()); //维护部门
		postApplication.setWhrid(user.getId());   //维护人
		postApplicationMapper.updateByPrimaryKeySelective(postApplication);
		//添加修改日志
		commonRecService.write(postApplication.getId(), TableEnum.B_Z_005,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,postApplication.getId());
		//添加修改机型
		postApplicationSQJXService.update(postApplication.getPostApplicationSQJXList(), postApplication.getId());
	}

	/**
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZt(String id, Integer zt)throws BusinessException {
		int bzt = postApplicationMapper.getCurrentZt4Validation(id);
		if (bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}


	/**
	 * @Description 修改审核人
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateShr(PostApplication postApplication)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		postApplication.setShsj(null);
		postApplication.setShyj("");
		postApplication.setShjl(null);
		//修改岗位授权
		updateByPrimaryKeySelective(postApplication,czls,user);
		
		//修改待办办理人id
		todorsService.updateBlrByDbid(postApplication.getId(), NodeEnum.NODE5.getId(),UndoStatusEnum.DCL.getId(),postApplication.getShrid());
		
		return postApplication.getId();
	}
	
	/**
	 * @Description 删除
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 */
	@Override
	public void delete(PostApplication postApplication) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		// 验证单据状态是否已变更
		validation4CurrentZt(postApplication.getId(), Integer.valueOf((String) postApplication.getParamsMap().get("currentZt")));
		
		PostApplication postApplicationNew=postApplicationMapper.selectByPrimaryKey(postApplication.getId());
		BusinessToMaintenancePersonnel businessToMaintenancePersonnel=new BusinessToMaintenancePersonnel();
		businessToMaintenancePersonnel.setWxrydaid(postApplicationNew.getSqrdaid());
		businessToMaintenancePersonnel.setGwid(postApplicationNew.getSqgwid());
		businessToMaintenancePersonnel.setZt(0);
		businessToMaintenancePersonnelService.deleteByPrimaryBt(businessToMaintenancePersonnel);
		
		//添加新增日志
		commonRecService.write(postApplication.getId(), TableEnum.B_Z_005,user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,postApplication.getId());
		
		postApplicationPXPGMapper.deleteByPrimaryMainid(postApplication.getId());
		
		postApplicationRYZZPGMapper.deleteByPrimaryMainid(postApplication.getId());
		
		//删除岗位授权
		postApplicationMapper.deleteByPrimaryKey(postApplication.getId());
		
		//删除待办
		todorsService.deleteToDo(postApplication.getId(), null, null);
	}

	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年11月14日 下午1:57:13
	 * @CreateBy 林龙
	 * @param postApplication
	 * @throws BusinessException
	 */
	@Override
	public void updategConfirm(PostApplication postApplication)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) postApplication.getParamsMap().get("currentZt"));
		// 验证单据状态是否已变更
		validation4CurrentZt(postApplication.getId(), iCurrentZt);
		
		postApplication.setZt(PostStatusEnum.CLOSE.getId()); 	//状态为指定结束
		postApplication.setGbrid(user.getId());
		postApplication.setGbrq(commonMapper.getSysdate());		//当前日期
		
		//指定结束
		postApplicationMapper.updateByPrimaryKeySelective(postApplication);
		
		//添加新增日志
		commonRecService.write(postApplication.getId(), TableEnum.B_Z_005,user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,postApplication.getId());
		
		PostApplication postApplicationNew=postApplicationMapper.selectByPrimaryKey(postApplication.getId());
		BusinessToMaintenancePersonnel businessToMaintenancePersonnel=new BusinessToMaintenancePersonnel();
		businessToMaintenancePersonnel.setWxrydaid(postApplicationNew.getSqrdaid());
		businessToMaintenancePersonnel.setGwid(postApplicationNew.getSqgwid());
		businessToMaintenancePersonnel.setZt(0);
		businessToMaintenancePersonnelService.deleteByPrimaryBt(businessToMaintenancePersonnel);
		
		//指定结束删除待办
		todorsService.deleteToDo(postApplication.getId(), null, null);
	}

	/**
	 * 
	 * @Description 审核通过
	 * @CreateTime 2017年11月14日 下午3:57:07
	 * @CreateBy 林龙
	 * @param postApplication
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updatePassed(PostApplication postApplication)throws BusinessException {
		// 验证单据状态是否已变更
		this.validation4CurrentZt(postApplication.getId(), Integer.valueOf((String) postApplication.getParamsMap().get("currentZt")));
		
		String czls = UUID.randomUUID().toString();							//流水号
		User user = ThreadVarUtil.getUser();								//当前登陆人
		postApplication.setZt(PostStatusEnum.AUDIT.getId()); 				//状态为审核驳回
		postApplication.setShjl(ConclusionEnum.AUDITED.getId());  			//结论：审核驳回
		postApplication.setShsj(commonMapper.getSysdate());					//当前日期
		//修改
		postApplicationMapper.updateByPrimaryKeySelective(postApplication);
		//添加审核日志
		commonRecService.write(postApplication.getId(), TableEnum.B_Z_005,user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,postApplication.getId());
		
		//写入待办	
		StringBuilder  builder=new StringBuilder(" ");
		StringBuilder sm=new StringBuilder(" ");
		String sqrXm=maintenancePersonnelMapper.selectByPrimaryKey(postApplication.getSqrdaid()).getXm();
		String gwmc=businessMapper.selectByPrimaryKey(postApplication.getSqgwid()).getDgmc();
		sm.append("请评估").append(sqrXm).append("申请的").append(gwmc).append("岗位");
		List<PostApplicationSQJX>  jxList=postApplicationSQJXMapper.queryByMainid(postApplication.getId());
		if(jxList!=null){
			for(PostApplicationSQJX postApplicationSQJX:jxList){
				builder.append(postApplicationSQJX.getFjjx()==null?"":postApplicationSQJX.getFjjx()).append(",");
			}
			builder.deleteCharAt(builder.length()-1);
			if(!" ".equals(builder.toString())){				
				sm.append(" 机型(").append(builder.toString()).append(")");
			}
		}	
		todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), postApplication.getId(), postApplication.getSqsqdh(),
				 null, null, UndoEnum.GWSQ.getId(), NodeEnum.NODE3.getId(), sm.toString(),
				 null, null, "quality:post:evaluation:main:01", postApplication.getId(), null);				
		todorsService.updateToDo(postApplication.getId(), NodeEnum.NODE2.getId(), UndoStatusEnum.DCL.getId(), user.getId());
		
		return postApplication.getId();
	}

	/**
	 * 
	 * @Description 审核驳回
	 * @CreateTime 2017年11月14日 下午3:57:19
	 * @CreateBy 林龙
	 * @param postApplication
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateTurnDown(PostApplication postApplication)throws BusinessException {
		// 验证单据状态是否已变更
		this.validation4CurrentZt(postApplication.getId(), Integer.valueOf((String) postApplication.getParamsMap().get("currentZt")));
		
		String czls = UUID.randomUUID().toString();							//流水号
		User user = ThreadVarUtil.getUser();								//当前登陆人
		postApplication.setZt(PostStatusEnum.AUDITDOWN.getId()); 			//状态为审核驳回
		postApplication.setShjl(ConclusionEnum.AUDITEDREJECTED.getId());  	//结论：审核驳回
		postApplication.setShsj(commonMapper.getSysdate());					//当前日期
		//修改
		postApplicationMapper.updateByPrimaryKeySelective(postApplication);
		//添加审核日志
		commonRecService.write(postApplication.getId(), TableEnum.B_Z_005,user.getId(), czls, LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,postApplication.getId());
		
		
		//审核驳回写待办		
		StringBuilder  builder=new StringBuilder(" ");
		StringBuilder sm=new StringBuilder(" ");
		String gwmc=businessMapper.selectByPrimaryKey(postApplication.getSqgwid()).getDgmc();
		String sqrid=postApplicationMapper.getInfoById(postApplication).getSqrid();
		sm.append("申请的").append(gwmc).append("岗位");
		List<PostApplicationSQJX>  jxList=postApplicationSQJXMapper.queryByMainid(postApplication.getId());
		if(jxList!=null){		
			for(PostApplicationSQJX item:jxList){
			builder.append((item.getFjjx()==null?"":item.getFjjx())).append(",");
			}
			builder.deleteCharAt(builder.length()-1);
			if(!" ".equals(builder.toString())){
				sm.append(" 机型(").append(builder.toString()).append(")");
			}			
		}
		sm.append("已被驳回,请重新提交");
		todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), postApplication.getId(), postApplication.getSqsqdh(),
				 null, null, UndoEnum.GWSQ.getId(), NodeEnum.NODE5.getId(), sm.toString(),
				 null, sqrid, "quality:post:application:main", postApplication.getId(), null);
		
		todorsService.updateToDo(postApplication.getId(),NodeEnum.NODE2.getId() , UndoStatusEnum.DCL.getId(), user.getId());
		
		return postApplication.getId();
		
		
	}
	
	/**
	 * @Description 根据mainid查询岗位授权-培训评估
	 * @CreateTime 2017-11-17 下午3:03:24
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @return 岗位授权-培训评估集合
	 */
	@Override
	public List<PostApplicationPXPG> queryPostApplicationPXPGByMainId(String mainid){
		return postApplicationPXPGMapper.queryByMainId(mainid);
	}
	

	/**
	 * @Description 查询人员资质评估
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param workRequire 岗位要求
	 * @return List<WorkRequire> 岗位要求集合
	 */
	@Override
	public List<WorkRequire> queryWorkRequireEval(WorkRequire workRequire){
		return workRequireMapper.queryWorkRequireEval(workRequire);
	}
	
	/**
	 * @Description 岗位评估
	 * @CreateTime 2017-11-16 下午6:06:21
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	@Override
	public String updateEvaluation(PostApplication postApplication) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		PostApplication pa = postApplicationMapper.selectByPrimaryKey(postApplication.getId());
		if(null == pa){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		valid4CurrentZt(new Integer[]{
				PostStatusEnum.AUDIT.getId(),
				PostStatusEnum.APPROVAL.getId(),
				}, pa.getZt(), "该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		postApplication.setZt(PostStatusEnum.APPROVAL.getId());
		if(null != postApplication.getAttachmentList() && postApplication.getAttachmentList().size() > 0){
			if(StringUtils.isBlank(postApplication.getSqsfjid())){
				postApplication.setSqsfjid(UUID.randomUUID().toString());
			}
		}
		postApplication.setPgrid(user.getId());
		postApplication.setPgbmid(user.getJgdm());
		//岗位评估
		postApplicationMapper.updateEvaluation(postApplication);
		//添加日志
		commonRecService.write(postApplication.getId(), TableEnum.B_Z_005, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.APPROVE, postApplication.getId());
		
		//编辑岗位授权-培训评估
		applicationPXPGService.update(postApplication.getApplicationPXPGList(), postApplication.getId());
		//编辑岗位授权-人员资质评估
		applicationRYZZPGService.update(postApplication.getPostApplicationRYZZPGList(), postApplication.getId());
		//编辑附件信息
		attachmentService.eidtAttachment(postApplication.getAttachmentList(), postApplication.getSqsfjid(), czls, postApplication.getId(), pa.getDprtcode(), LogOperationEnum.YIPIZHUN_WO);
		//编辑岗位-人员关系
		if(EffectiveEnum.YES.getId() == postApplication.getPgjl()){
			pa.setYxqKs(postApplication.getYxqKs());
			pa.setYxqJs(postApplication.getYxqJs());
			pa.setPostApplicationSQJXList(postApplication.getPostApplicationSQJXList());
			updateBusinessToMain(pa);
			postApplicationSQJXService.update(postApplication.getPostApplicationSQJXList(), pa.getId());
		}else{
			if(null != postApplication.getParamsMap().get("operationType") && PostStatusEnum.AUDIT.getId() == (Integer)postApplication.getParamsMap().get("operationType")){
				deleteBusinessToMain(pa);
			}
		}
		
		
		//写待办
	     todorsService.updateToDo(postApplication.getId(), NodeEnum.NODE3.getId(), UndoStatusEnum.DCL.getId(), user.getId());

		return postApplication.getId();
	}
	
	/**
	 * @Description 设置有效期
	 * @CreateTime 2017-11-17 上午11:18:56
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	@Override
	public String updateEffectDate(PostApplication postApplication) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		PostApplication pa = postApplicationMapper.selectByPrimaryKey(postApplication.getId());
		if(null == pa){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		//岗位评估
		postApplicationMapper.updateByPrimaryKeySelective(postApplication);
		//添加日志
		commonRecService.write(pa.getId(), TableEnum.B_Z_005, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, postApplication.getId());
		
		//编辑岗位-人员关系
		pa.setYxqKs(postApplication.getYxqKs());
		pa.setYxqJs(postApplication.getYxqJs());
		pa.setPostApplicationSQJXList(postApplication.getPostApplicationSQJXList());
		updateBusinessToMain(pa);
		postApplicationSQJXService.update(postApplication.getPostApplicationSQJXList(), pa.getId());
		return postApplication.getId();
	}
	
	
	/**
	 * @Description 上传授权书
	 * @CreateTime 2017-11-16 下午6:03:54
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	@Override
	public String updateUpload(PostApplication postApplication) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		PostApplication pa = postApplicationMapper.selectByPrimaryKey(postApplication.getId());
		if(null == pa){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		if(StringUtils.isBlank(pa.getSqsfjid())){
			pa.setSqsfjid(UUID.randomUUID().toString());
			postApplication.setSqsfjid(pa.getSqsfjid());
		}
		//岗位评估
		postApplicationMapper.updateByPrimaryKeySelective(postApplication);
		//添加日志
		commonRecService.write(pa.getId(), TableEnum.B_Z_005, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, postApplication.getId());
		//编辑附件信息
		attachmentService.eidtAttachment(postApplication.getAttachmentList(), pa.getSqsfjid(), czls, pa.getId(), pa.getDprtcode(), LogOperationEnum.EDIT);
		
		//编辑岗位-人员关系
//		updateBusinessToMain(pa);
		return postApplication.getId();
	}
	
	/**
	 * @Description 验证表单重复提交,新增岗位人员关系
	 * @CreateTime 2018-3-19 下午1:08:35
	 * @CreateBy 刘兵
	 * @param pa 岗位授权
	 * @throws BusinessException
	 */
	private void valid4SavebusinessToMain(PostApplication pa) throws BusinessException{
		PostApplication postApplicationOld = new PostApplication();
		postApplicationOld.setSqrdaid(pa.getSqrdaid());
		postApplicationOld.setSqgwid(pa.getSqgwid());
		postApplicationOld.setId(pa.getId());
		List<String> fjjxList = postApplicationMapper.selectFjjxByPostApplication(postApplicationOld);
		List<PostApplicationSQJX> postApplicationSQJXList = pa.getPostApplicationSQJXList();
		if(null != postApplicationSQJXList && postApplicationSQJXList.size() > 0){
			for (PostApplicationSQJX postApplicationSQJX : postApplicationSQJXList) {
				if(fjjxList.contains(StringUtils.isBlank(postApplicationSQJX.getFjjx())?"-":postApplicationSQJX.getFjjx())){
					if(StringUtils.isBlank(postApplicationSQJX.getFjjx())){
						throw new BusinessException("该数据已申请，请重新填写数据!");
					}else{
						throw new BusinessException("机型"+postApplicationSQJX.getFjjx()+"已申请，请重新填写数据!");
					}
				}
			}
		}
		User user = ThreadVarUtil.getUser();//当前登陆人
		Map<String, BusinessToMaintenancePersonnel> map = new HashMap<String, BusinessToMaintenancePersonnel>();
		List<BusinessToMaintenancePersonnel> oldList = businessToMaintenancePersonnelMapper.queryByGwidAndWxdaid(pa.getSqrdaid(), pa.getSqgwid());
		for (BusinessToMaintenancePersonnel businessToMaintenancePersonnel : oldList) {
			map.put(StringUtils.isBlank(businessToMaintenancePersonnel.getFjjx())?"-":businessToMaintenancePersonnel.getFjjx(), businessToMaintenancePersonnel);
		}
		for (PostApplicationSQJX postApplicationSQJX : postApplicationSQJXList) {
			BusinessToMaintenancePersonnel businessToMaintenancePersonnel = map.get(StringUtils.isBlank(postApplicationSQJX.getFjjx())?"-":postApplicationSQJX.getFjjx());
			if(null == businessToMaintenancePersonnel){
				businessToMaintenancePersonnel = new BusinessToMaintenancePersonnel();
				String uuidbt = UUID.randomUUID().toString();	//uuid-主单id
				businessToMaintenancePersonnel.setId(uuidbt);
				businessToMaintenancePersonnel.setWxrydaid(pa.getSqrdaid());
				businessToMaintenancePersonnel.setGwid(pa.getSqgwid());
				businessToMaintenancePersonnel.setFjjx(postApplicationSQJX.getFjjx());
				businessToMaintenancePersonnel.setLb(postApplicationSQJX.getLb());
				businessToMaintenancePersonnel.setZt(0);
				businessToMaintenancePersonnel.setWhbmid(user.getBmdm());
				businessToMaintenancePersonnel.setWhrid(user.getId());
				businessToMaintenancePersonnelMapper.insertSelective(businessToMaintenancePersonnel);
			}
		}
	}
	
	/**
	 * @Description 删除 岗位-人员关系
	 * @CreateTime 2017-11-20 下午4:19:22
	 * @CreateBy 刘兵
	 * @param pa 岗位授权
	 * @throws BusinessException
	 */
	private void deleteBusinessToMain(PostApplication pa) throws BusinessException{
		BusinessToMaintenancePersonnel businessToMaintenancePersonnel = new BusinessToMaintenancePersonnel();
		businessToMaintenancePersonnel.setWxrydaid(pa.getSqrdaid());
		businessToMaintenancePersonnel.setGwid(pa.getSqgwid());
		businessToMaintenancePersonnel.setZt(EffectiveEnum.NO.getId());
		businessToMaintenancePersonnelService.deleteByPrimaryBt(businessToMaintenancePersonnel);
	}
	
	/**
	 * @Description 编辑 岗位-人员关系
	 * @CreateTime 2017-11-17 下午8:40:46
	 * @CreateBy 刘兵
	 * @param pa 岗位授权
	 * @throws BusinessException
	 */
	private void updateBusinessToMain(PostApplication pa) throws BusinessException{
		List<PostApplicationSQJX> postApplicationSQJXList = pa.getPostApplicationSQJXList();
		if(null != postApplicationSQJXList && postApplicationSQJXList.size() > 0){
			User user = ThreadVarUtil.getUser();//当前登陆人
			Map<String, BusinessToMaintenancePersonnel> map = new HashMap<String, BusinessToMaintenancePersonnel>();
			Map<String, String> jxMap = new HashMap<String, String>();
			List<BusinessToMaintenancePersonnel> oldList = businessToMaintenancePersonnelMapper.queryByGwidAndWxdaid(pa.getSqrdaid(), pa.getSqgwid());
			for (BusinessToMaintenancePersonnel businessToMaintenancePersonnel : oldList) {
				map.put(StringUtils.isBlank(businessToMaintenancePersonnel.getFjjx())?"-":businessToMaintenancePersonnel.getFjjx(), businessToMaintenancePersonnel);
			}
			for (PostApplicationSQJX postApplicationSQJX : postApplicationSQJXList) {
				BusinessToMaintenancePersonnel businessToMaintenancePersonnel = map.get(StringUtils.isBlank(postApplicationSQJX.getFjjx())?"-":postApplicationSQJX.getFjjx());
				if(null != businessToMaintenancePersonnel){
					jxMap.put(StringUtils.isBlank(postApplicationSQJX.getFjjx())?"-":postApplicationSQJX.getFjjx(), StringUtils.isBlank(postApplicationSQJX.getFjjx())?"-":postApplicationSQJX.getFjjx());
					businessToMaintenancePersonnel.setKsrq(postApplicationSQJX.getYxqKs());
					businessToMaintenancePersonnel.setJzrq(postApplicationSQJX.getYxqJs());
					businessToMaintenancePersonnelMapper.updateById(businessToMaintenancePersonnel);
				}else{
					businessToMaintenancePersonnel = new BusinessToMaintenancePersonnel();
					businessToMaintenancePersonnel.setId(UUID.randomUUID().toString());
					businessToMaintenancePersonnel.setWxrydaid(pa.getSqrdaid());
					businessToMaintenancePersonnel.setGwid(pa.getSqgwid());
					businessToMaintenancePersonnel.setKsrq(postApplicationSQJX.getYxqKs());
					businessToMaintenancePersonnel.setJzrq(postApplicationSQJX.getYxqJs());
					businessToMaintenancePersonnel.setZt(EffectiveEnum.YES.getId());
					businessToMaintenancePersonnel.setWhrid(user.getId());
					businessToMaintenancePersonnel.setWhbmid(user.getBmdm());
					businessToMaintenancePersonnel.setFjjx(postApplicationSQJX.getFjjx());
					businessToMaintenancePersonnel.setLb(postApplicationSQJX.getLb());
					businessToMaintenancePersonnelService.insertSelective(businessToMaintenancePersonnel);
				}
			}
			for (BusinessToMaintenancePersonnel businessToMaintenancePersonnel : oldList){
				if(null == jxMap.get(StringUtils.isBlank(businessToMaintenancePersonnel.getFjjx())?"-":businessToMaintenancePersonnel.getFjjx())){
					businessToMaintenancePersonnelMapper.deleteByPrimaryKey(businessToMaintenancePersonnel.getId());
				}
			}
		}
	}
	
	/**
	 * @Description 验证表单重复提交,当前状态是否存在于数组中,不存在:抛出异常message
	 * @CreateTime 2017-11-16 下午6:06:21
	 * @CreateBy 刘兵
	 * @param ztArr 可操作状态数组
	 * @param currentZt 当前状态
	 * @param message 异常信息
	 * @throws BusinessException
	 */
	private void valid4CurrentZt(Integer[] ztArr, Integer currentZt,String message) throws BusinessException{
		if(currentZt != null && !ArrayUtils.contains(ztArr,currentZt)){
			throw new BusinessException(message);
		}
	}
     
}