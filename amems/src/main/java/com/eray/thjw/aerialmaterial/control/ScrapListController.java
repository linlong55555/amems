package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.dao.HasScrappedListMapper;
import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.aerialmaterial.po.ScrapList;
import com.eray.thjw.aerialmaterial.service.HasScrappedListService;
import com.eray.thjw.aerialmaterial.service.ScrapListService;
import com.eray.thjw.aerialmaterial.service.SecondmentService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.TransferService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 * 航材报废
 * @author meizhiliang
 */
@Controller
@RequestMapping("/aerialmaterial/scrap/")
public class ScrapListController extends BaseController {
	
	@Resource
	private SecondmentService secondmentService;
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private CommonRecService commonRecService;                   //REC表服务
	@Resource
	private ScrapListService scrapListService;
	@Resource
	private TransferService transferService;
	@Resource
	private HasScrappedListService hasScrappedListService;
	@Resource
	private HasScrappedListMapper hasScrappedListMapper;
	
	/** 新增 */
	private static final String FORWARD_TYPE_ADD = "1";
	
	/** 修改 */
	private static final String FORWARD_TYPE_EDIT = "2";
	
	/** 审核 */
	private static final String FORWARD_TYPE_AUDIT = "3";
	
	/** 审核 */
	private static final String FORWARD_TYPE_VIEW = "4";
	
	/**
	 * @author 梅志亮  报废清单列表
	 * @time 2016-10-31
	 */
	@Privilege(code = "aerialmaterial:scrap:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String getIndex() {
		return "material/scrap/scraplist";
	}
	/**
	 * @author 梅志亮  报废清单列表
	 * @time 2016-11-22
	 */
	@SuppressWarnings("rawtypes")
	@Privilege(code = "aerialmaterial:scrap:main")
	@ResponseBody
	@RequestMapping(value = "queryScrapList", method = RequestMethod.POST)
	public Map<String, Object> queryQualityCheckList(
		@RequestBody ScrapList scraplist, HttpServletRequest request,
		Model model)throws BusinessException {
		String id = scraplist.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		scraplist.setId(null);
		
		PageHelper.startPage(scraplist.getPagination());
		List<ScrapList> list = scrapListService.selectScrapList(scraplist);
		if(((Page)list).getTotal() > 0){
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ScrapList scraplist1 = new ScrapList();
					scraplist1.setId(id);
					List<ScrapList> newRecordList = scrapListService.selectScrapList(scraplist1);;
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, scraplist.getPagination());
		}else{
			List<ScrapList> newRecordList = new ArrayList<ScrapList>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ScrapList scraplist1 = new ScrapList();
				scraplist1.setId(id);
				newRecordList = scrapListService.selectScrapList(scraplist1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, scraplist.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, scraplist.getPagination());
		}
	}
	
	
	@Privilege(code = "aerialmaterial:scrap:main:01")
	@RequestMapping(value = "intoAudit", method = RequestMethod.GET)
	public ModelAndView intoAudit(String id) {
		Map<String, Object> model =new HashMap<String, Object>();
		model.put("scrappedList", scrapListService.selectByPrimaryKey(id));
		model.put("hasScrappedList", hasScrappedListService.selectHasScrappedList(id));
		return new ModelAndView("material/scrap/audit_scrap",model);
	}
	/**
	 * @author 梅志亮
	 * @throws Exception 
	 * @description 更新报废航材
	 * @develop date 2016.11.24
	 */
	@ResponseBody
	@RequestMapping(value = "saveScrap", method = RequestMethod.POST)
	public void updateScrap(@RequestBody ScrapList scraplist,HttpServletRequest request) throws Exception {
		scrapListService.updateByPrimaryKeySelective(scraplist);
	} 
	
	@ResponseBody
	@RequestMapping(value = "submitScrap", method = RequestMethod.POST)
	public void submitScrap(@RequestBody ScrapList scraplist,HttpServletRequest request) throws Exception {
		scrapListService.updateByPrimaryKeySelective(scraplist);
	} 
	/**
     * @author 梅志亮  查看航材报废
	 * @time 2016-11-24
	 */
	@RequestMapping(value="view",method=RequestMethod.GET)
	public ModelAndView  View(String id){
		Map<String, Object> model =new HashMap<String, Object>();
		model.put("scraplist", scrapListService.selectByPrimaryKey(id));
		model.put("hasScrappedList", hasScrappedListMapper.selectHasScrappedList(id));
		return new ModelAndView("material/scrap/view_scrap",model);
	}
	
	/**
	 * 跳转到报废申请页面
	 * @return
	 */
	@Privilege(code="aerialmaterial:scrap:apply")
	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public String apply(){
		return "material/scrap/scrap_apply_main";
	}
	
	/**
	 * 跳转到新增报废申请页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_ADD);
			return new ModelAndView("material/scrap/scrap_apply_modify", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至新增报废申请页面!",e);
		}
	}
	
	/**
	 * 跳转到修改报废申请页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_EDIT);
			model.put("scrap", scrapListService.selectByPrimaryKey(id));
			return new ModelAndView("material/scrap/scrap_apply_modify", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至修改报废申请页面!",e);
		}
	}
	
	
	/**
	 * 跳转到查看报废申请页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_VIEW);
			model.put("scrap", scrapListService.selectByPrimaryKey(id));
			return new ModelAndView("material/scrap/scrap_apply_modify", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至查看报废申请页面!",e);
		}
	}
	
	/**
	 * 查询-报废申请
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryapplypage",method={RequestMethod.POST})
	public Map<String, Object> queryapplypage(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			return scrapListService.queryapplypage(scraplist);
		} catch (Exception e) {
			 throw new BusinessException("查询报废申请单失败!", e);
		}
	}
	
	/**
	 * 查询报废单详情
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryscrapdetail",method={RequestMethod.POST})
	public List<HasScrappedList> queryscrapdetail(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			return scrapListService.queryscrapdetail(scraplist);
		} catch (Exception e) {
			 throw new BusinessException("查询报废单详情失败!", e);
		}
	}
	
	/**
	 * 保存报废单
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/save",method={RequestMethod.POST})
	public String save(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			return scrapListService.doSave(scraplist);
		} catch (Exception e) {
			 throw new BusinessException("保存报废单失败!", e);
		}
	}
	
	/**
	 * 提交报废单
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/submit",method={RequestMethod.POST})
	public String submit(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			return scrapListService.doSubmit(scraplist);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			 throw new BusinessException("提交报废单失败!", e);
		}
	}
	
	/**
	 * 作废
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="aerialmaterial:scrap:apply:scrap")
	@RequestMapping(value="/scrap",method={RequestMethod.POST})
	public void scrap(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			scrapListService.doScrap(scraplist);
		} catch (Exception e) {
			 throw new BusinessException("作废报废单失败!", e);
		}
	}
	
	/**
	 * 指定结束
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/close",method={RequestMethod.POST})
	public void close(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			scrapListService.doClose(scraplist);
		} catch (Exception e) {
			 throw new BusinessException("指定结束报废单失败!", e);
		}
	}
	
	/**
	 * 撤销
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="aerialmaterial:scrap:audit:revoke")
	@RequestMapping(value="/revoke",method={RequestMethod.POST})
	public void revoke(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			scrapListService.doRevoke(scraplist);
		} catch (Exception e) {
			 throw new BusinessException("指定结束报废单失败!", e);
		}
	}
	
	/**
	 * 跳转到报废申请页面
	 * @return
	 */
	@Privilege(code="aerialmaterial:scrap:audit")
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public String audit(){
		return "material/scrap/scrap_audit_main";
	}
	
	/**
	 * 跳转到报废审核页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/executeaudit/{id}", method = RequestMethod.GET)
	public ModelAndView execute(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_AUDIT);
			model.put("scrap", scrapListService.selectByPrimaryKey(id));
			return new ModelAndView("material/scrap/scrap_apply_modify", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至跳转到报废审核页面失败!",e);
		}
	}
	
	/**
	 * 审核通过报废单
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="aerialmaterial:scrap:audit:audit")
	@RequestMapping(value="/auditApprove",method={RequestMethod.POST})
	public String auditApprove(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			return scrapListService.doAuditApprove(scraplist);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			 throw new BusinessException("审核通过报废单失败!", e);
		}
	}
	
	/**
	 * 审核驳回报废单
	 * @param scraplist
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="aerialmaterial:scrap:audit:audit")
	@RequestMapping(value="/auditReject",method={RequestMethod.POST})
	public String auditReject(@RequestBody ScrapList scraplist) throws BusinessException{
		try {
			return scrapListService.doAuditReject(scraplist);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			 throw new BusinessException("审核驳回报废单失败!", e);
		}
	}
}
