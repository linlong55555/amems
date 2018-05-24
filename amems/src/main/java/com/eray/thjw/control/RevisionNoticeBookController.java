package com.eray.thjw.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.RevisionNoticeBook;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.RevisionNoticeBookService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import enu.RevisionNoticeBookZtEnum;
import enu.ThresholdEnum;
import enu.ordersource.OrderSourceEnum;

/**
 * @author pingxiaojun
 * @description 修订通知书 Controller类
 * @develop date 2016.08.15
 */
@Controller
@RequestMapping("/project/revisionNoticeBook")
public class RevisionNoticeBookController extends BaseController {

	// 修订通知书Service
	@Autowired
	private RevisionNoticeBookService revisionNoticeBookService;

	// 用户Service
	@Autowired
	private UserService userService;

	// 下达指令来源Service
	@Autowired
	private OrderSourceService orderSourceService;

	@Autowired
	private PlaneModelDataService planeModelDataService;
	@Autowired
	private MonitorsettingsService monitorsettingsService;

	/**
	 * @author pingxiaojun
	 * @description 跳转至修订维修方案界面
	 * @return 修订通知书的路径
	 */
	@Privilege(code = "project:revisionnoticebook:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project/revisionNoticeBook/revisionNoticeBookMain", responseParamMap);
	}
	/**
	 * @author pingxiaojun
	 * @description 跳转至修订工卡界面
	 * @return 修订通知书的路径
	 */
	@Privilege(code = "project:revisionnoticebook:card_main")
	@RequestMapping(value = "card_main", method = RequestMethod.GET)
	public ModelAndView cardList(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project/revisionNoticeBook/revisionNoticeBook_card_main", responseParamMap);
	}
	/**
	 * @author pingxiaojun
	 * @description 跳转至修订mel界面
	 * @return 修订通知书的路径
	 */
	@Privilege(code = "project:revisionnoticebook:mel_main")
	@RequestMapping(value = "mel_main", method = RequestMethod.GET)
	public ModelAndView melList(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project/revisionNoticeBook/revisionNoticeBook_mel_main", responseParamMap);
	}

	/**
	 * @author pingxiaojun
	 * @description 查询修订通知书列表
	 * @param requestParamMap
	 * @return 修订通知书列表的数据
	 */
	@RequestMapping(value = "queryRevisionNoticeBookList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRevisionNoticeBookList(@RequestParam Map requestParamMap,
			Pagination pagination) throws BusinessException {
		// 响应参数Map
		Map<String, Object> responseParamMap = revisionNoticeBookService.queryAllRevisionNoticeBook(requestParamMap, pagination);
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GDXD.getName(),String.valueOf(requestParamMap.get("dprtcode")));
		responseParamMap.put("monitorsettings", monitorsettings);
		return responseParamMap;
	}

	/**
	 * @author pingxiaojun
	 * @description 初始化新增修订通知书界面
	 * @param cfkey
	 *            采番key
	 * @develop date 2016.08.17
	 * @return 新增修订通知书的路径
	 */
	@Privilege(code="project:revisionnoticebook:add") 
	@RequestMapping("intoAddRevisionNoticeBook")
	public ModelAndView intoAddRevisionNoticeBook() throws Exception {

		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		Date date = new Date();
		responseParamMap.put("zdrq", date);
		responseParamMap.put("xdqx", date);
		//获取登入user
		User user=ThreadVarUtil.getUser();
		List<User> xdrList = userService.getByDprtcode(user.getJgdm());
		responseParamMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());
		responseParamMap.put("xdrList", xdrList);
		responseParamMap.put("defaultDate", new Date());

		return new ModelAndView("project/revisionNoticeBook/add_revisionNoticeBook", responseParamMap);
	}
	/**
	 * @author pingxiaojun
	 * @description 初始化新增修订通知书界面
	 * @param cfkey
	 *            采番key
	 * @develop date 2016.08.17
	 * @return 新增修订通知书的路径
	 */
	//@Privilege(code="project:revisionnoticebook:mel_add") 
	@RequestMapping("intoAddRevisionNoticeBook_mel")
	public ModelAndView intoAddRevisionNoticeBook_mel() throws Exception {
		
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		Date date = new Date();
		responseParamMap.put("zdrq", date);
		responseParamMap.put("xdqx", date);
		//获取登入user
		User user=ThreadVarUtil.getUser();
		List<User> xdrList = userService.getByDprtcode(user.getJgdm());
		responseParamMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());
		responseParamMap.put("xdrList", xdrList);
		responseParamMap.put("defaultDate", new Date());
		
		return new ModelAndView("project/revisionNoticeBook/revisionNoticeBook_mel_add", responseParamMap);
	}
	/**
	 * @author pingxiaojun
	 * @description 初始化新增修订通知书界面
	 * @param cfkey
	 *            采番key
	 * @develop date 2016.08.17
	 * @return 新增修订通知书的路径
	 */
	//@Privilege(code="project:revisionnoticebook:card_add") 
	@RequestMapping("intoAddRevisionNoticeBook_card")
	public ModelAndView intoAddRevisionNoticeBook_card() throws Exception {
		
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		Date date = new Date();
		responseParamMap.put("zdrq", date);
		responseParamMap.put("xdqx", date);
		//获取登入user
		User user=ThreadVarUtil.getUser();
		List<User> xdrList = userService.getByDprtcode(user.getJgdm());
		responseParamMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());
		responseParamMap.put("xdrList", xdrList);
		responseParamMap.put("defaultDate", new Date());
		
		return new ModelAndView("project/revisionNoticeBook/revisionNoticeBook_card_add", responseParamMap);
	}

	/**
	 * @author pingxiaojun
	 * @description 新增修订通知书信息
	 * @param requestParamMap
	 *            请求参数Map
	 * @develop date 2016.08.17
	 * @throws Exception
	 *             新增修订通知书信息出现的异常
	 * @return 新增修订通知书信息的处理结果
	 */
	 //@Privilege(code="project:revisionnoticebook:add") 
	@RequestMapping(value = "addRevisionNoticeBook", method = RequestMethod.POST)
	@ResponseBody
	public Object addRevisionNoticeBook(@RequestBody Map<String, Object> requestParamMap) throws Exception {
		// 响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();

		if (null != requestParamMap  && requestParamMap.size() > 0) {
			// 判断修订期限是否为日期格式
			String xdqx = (String) requestParamMap.get("xdqx");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String currentServerTime = sdf.format(new Date());

			// 判断修订期限是否小于当前日期
			if (Utils.DT.compare(Utils.DT.toDate(xdqx), Utils.DT.toDate(currentServerTime)) == -1) {
				responseParamMap.put("state", "Failure");
				responseParamMap.put("message", "修订期限不能小于当前日期");
				return responseParamMap;
			}
			responseParamMap = revisionNoticeBookService.saveRevisionNoticeBook(requestParamMap);
		} else {
			responseParamMap.put("state", "Failure");
			responseParamMap.put("message", "修订通知书信息不能为空");
		}

		return responseParamMap;
	}

	/**
	 * @author pingxiaojun
	 * @description 查询修订通知书信息
	 * @param id
	 *            主键id
	 * @develop date 2016.08.18
	 * @return 修订通知书信息的数据
	 */
	@RequestMapping(value = "queryRevisionNoticeBook", method = RequestMethod.POST)
	@ResponseBody
	public Object queryRevisionNoticeBook(String id) throws BusinessException {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		try {
			RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.queryByPrimaryKey(id);
			responseParamMap.put("revisionNoticeBook", revisionNoticeBook);
		} catch (Exception e) {
			throw new BusinessException("查看修订通知书失败");
		}
		return responseParamMap;
	}

	/**
	 * @author pingxiaojun
	 * @description 初始化修改修订通知书界面
	 * @param id
	 *            主键id
	 * @develop date 2016.08.18
	 * @return 修改修订通知书的路径
	 * @throws BusinessException 
	 */
	@RequestMapping("intoRevisionNoticeBook")
	public ModelAndView intoRevisionNoticeBook(@RequestParam String id,@RequestParam String dprtcode, HttpServletRequest req) throws BusinessException {
		// 响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();

		// 根据主键id查询修订通知书信息
		RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.queryByPrimaryKey(id);
		if(revisionNoticeBook==null){
			throw new BusinessException("查询失败");
		}
		responseParamMap.put("revisionNoticeBook", revisionNoticeBook);
		// 修订人列表
		List<User> xdrList = userService.getByDprtcode(dprtcode);
		responseParamMap.put("xdrList", xdrList);
		// 根据指令id查询下达指令来源信息
		List<Map<String, Object>> orderSourceList = orderSourceService.queryOrderSourceListByZlid(id);
		if (orderSourceList != null && orderSourceList.size() > 0) {
			StringBuffer pgdidSbf = new StringBuffer();
			StringBuffer pgdhSbf = new StringBuffer();
			List<TechnicalFile> pgdhList = new ArrayList<TechnicalFile>();

			for (Map<String, Object> orderSource : orderSourceList) {
				pgdidSbf.append(orderSource.get("PGDID")).append(",");
				pgdhSbf.append(orderSource.get("PGDH")).append(",");

				// 评估单信息
				TechnicalFile technicalFile = new TechnicalFile();
				technicalFile.setId(String.valueOf(orderSource.get("PGDID")));
				technicalFile.setPgdh(String.valueOf(orderSource.get("PGDH")));
				technicalFile.setShzltgh(String.valueOf(orderSource.get("SHZLTGH")));
				pgdhList.add(technicalFile);
			}

			// 评估单id
			responseParamMap.put("pgdids", pgdidSbf.substring(0, pgdidSbf.length() - 1));
			// 评估单号
			responseParamMap.put("pgdhs", pgdhSbf.substring(0, pgdhSbf.length() - 1));
			// 评估单号列表
			responseParamMap.put("pgdhList", pgdhList);
		}

		responseParamMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());

		// 根据状态判断是否为提交状态或者保存状态
		if (revisionNoticeBook.getZt() == OrderSourceEnum.NOT_EVALUATED.getId()
				|| revisionNoticeBook.getZt() == OrderSourceEnum.REVIEW_THE_REJECTED.getId()
				|| revisionNoticeBook.getZt() == OrderSourceEnum.APPROVE_TURN_.getId()) {
			return new ModelAndView("project/revisionNoticeBook/modify_revisionNoticeBook", responseParamMap);
		} else {
			return new ModelAndView("project/revisionNoticeBook/edit_revisionNoticeBook", responseParamMap);
		}
	}

	/**
	 * @author pingxiaojun
	 * @description 修改修订通知书信息
	 * @param requestParamMap
	 *            请求参数Map
	 * @develop date 2016.08.18
	 * @throws Exception
	 *             修改修订通知书信息出现的异常
	 * @return 修改修订通知书信息的处理结果
	 */
	@Privilege(code="project:revisionnoticebook:edit,project:revisionnoticebook:card_edit,project:revisionnoticebook:mel_edit") 
	@RequestMapping(value = "editRevisionNoticeBook", method = RequestMethod.POST)
	@ResponseBody
	public Object editRevisionNoticeBook(@RequestBody Map<String, Object> requestParamMap) throws Exception {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		if (null != requestParamMap  && requestParamMap.size() > 0) {
			// 判断修订期限是否为日期格式
			String xdqx = (String) requestParamMap.get("xdqx");
			if (!Utils.Str.isDate(xdqx)) {
				throw new BusinessException("修订期限格式不正确，请输入yyyy-MM-dd的格式");
			}
			try {
				// 查询修订通知书信息
				RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService
						.queryByPrimaryKey(String.valueOf(requestParamMap.get("id")));
				if (revisionNoticeBook == null) {
					throw new BusinessException("该修订通知书信息不存在");
				}
				int[] i={ OrderSourceEnum.NOT_EVALUATED.getId()
						, OrderSourceEnum.EVALUATED.getId()
						, OrderSourceEnum.CHECKED.getId()
						, OrderSourceEnum.APPROVE.getId()
						, OrderSourceEnum.REVIEW_THE_REJECTED.getId()
						, OrderSourceEnum.APPROVE_TURN_.getId()};
				responseParamMap = revisionNoticeBookService.modifyRevisionNoticeBook(requestParamMap,i);
			} catch (Exception e) {
				throw new BusinessException("保存修订通知书失败");
			}
		} else {
			throw new BusinessException("修订通知书信息不能为空");
		}
		
		return responseParamMap;
	}
	/**
	 * @author pingxiaojun
	 * @description 修改修订通知书信息
	 * @param requestParamMap
	 *            请求参数Map
	 * @develop date 2016.08.18
	 * @throws Exception
	 *             修改修订通知书信息出现的异常
	 * @return 修改修订通知书信息的处理结果
	 */
	//@Privilege(code="project:revisionnoticebook:edit") 
	@RequestMapping(value = "modifyRevisionNoticeBook", method = RequestMethod.POST)
	@ResponseBody
	public Object modifyRevisionNoticeBook(@RequestBody Map<String, Object> requestParamMap) throws Exception {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		try {
			int[] i={OrderSourceEnum.NOT_EVALUATED.getId()
					,OrderSourceEnum.REVIEW_THE_REJECTED.getId()
					,OrderSourceEnum.APPROVE_TURN_.getId()};
			responseParamMap = revisionNoticeBookService.modifyRevisionNoticeBook(requestParamMap,i);
		} catch (Exception e) {
			throw new BusinessException("保存修订通知书失败");
		}
		return responseParamMap;
	}

	/**
	 * @author pingxiaojun
	 * @description 初始化查看修订通知书界面
	 * @param id 
	 *            修订通知书主键id
	 * @return 查看修订通知书的路径
	 */
	/* @Privilege(code="project:revisionnoticebook:view") */
	@RequestMapping("intoViewRevisionNoticeBook")
	public ModelAndView intoViewRevisionNoticeBook(@RequestParam String id,@RequestParam String dprtcode, HttpServletRequest req, Model model) throws Exception {

		Map<String, Object> responseParamMap = new HashMap<String, Object>();

		// 根据主键id查询修订通知书信息
		RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.queryByPrimaryKey(id);
		// 修订人列表
		List<User> xdrList = userService.getByDprtcode(dprtcode);
		responseParamMap.put("xdrList", xdrList);

		// 根据指令id查询下达指令来源信息
		List<Map<String, Object>> orderSourceList = orderSourceService.queryOrderSourceListByZlid(id);
		if (orderSourceList != null && orderSourceList.size() > 0) {
			StringBuffer pgdidSbf = new StringBuffer();
			StringBuffer pgdhSbf = new StringBuffer();
			List<TechnicalFile> pgdhList = new ArrayList<TechnicalFile>();

			for (Map<String, Object> orderSource : orderSourceList) {
				pgdidSbf.append(orderSource.get("PGDID")).append(",");
				pgdhSbf.append(orderSource.get("PGDH")).append(",");

				// 评估单信息
				TechnicalFile technicalFile = new TechnicalFile();
				technicalFile.setId(String.valueOf(orderSource.get("PGDID")));
				technicalFile.setPgdh(String.valueOf(orderSource.get("PGDH")));
				technicalFile.setShzltgh(String.valueOf(orderSource.get("SHZLTGH")));
				pgdhList.add(technicalFile);
			}

			// 评估单id
			responseParamMap.put("pgdids", pgdidSbf.substring(0, pgdidSbf.length() - 1));
			// 评估单号
			responseParamMap.put("pgdhs", pgdhSbf.substring(0, pgdhSbf.length() - 1));
			// 评估单号列表
			responseParamMap.put("pgdhList", pgdhList);
		}  
		responseParamMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());
		responseParamMap.put("revisionNoticeBook", revisionNoticeBook);
		return new ModelAndView("/project/revisionNoticeBook/view_revisionNoticeBook", responseParamMap);
	}

	/**
	 * @author pingxiaojun
	 * @description 作废修订通知书
	 * @param id
	 *            主键id
	 * @param zt
	 *            状态
	 * @throws Exception
	 *             作废修订通知书出现的异常
	 * @return 作废修订通知书的处理结果
	 */
	 @Privilege(code="project:revisionnoticebook:main:01,project:revisionnoticebook:main:card_01,project:revisionnoticebook:main:mel_01") 
	@RequestMapping(value = "deleteRevisionNoticeBook", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteRevisionNoticeBook(String id, Integer zt) throws Exception {
		// 响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		// 根据主键id查询修订通知书信息
		RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.queryByPrimaryKey(id);
		if(revisionNoticeBook==null){
			throw new BusinessException("查询失败");
		}
		// 关闭修订通知书
		revisionNoticeBook.setZt(zt);
		responseParamMap=revisionNoticeBookService.deleteRevisionNoticeBook(revisionNoticeBook);
		return responseParamMap;
	}

	/**
	 * @author pingxiaojun
	 * @description 初始化指定结束修订通知书界面
	 * @param id
	 *            主键id
	 * @return 指定结束修订通知书的路径
	 */
	@Privilege(code="project:revisionnoticebook:end,project:revisionnoticebook:card_end,project:revisionnoticebook:mel_end") 
	@RequestMapping("intoEndRevisionNoticeBook")
	public ModelAndView intoEndRevisionNoticeBook(String id) throws Exception {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		// 根据主键id查询修订通知书信息
		RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.queryByPrimaryKey(id);
		responseParamMap.put("revisionNoticeBook", revisionNoticeBook);
		return new ModelAndView("project/revisionNoticeBook/end_revisionNoticeBook", responseParamMap);
	}

	/**
	 * @author pingxiaojun
	 * @description 指定结束修订通知书
	 * @param requestParamMap
	 *            请求参数
	 * @throws Exception
	 *             指定结束修订通知书出现的异常
	 * @return 指定结束修订通知书的处理结果
	 */
	@Privilege(code="project:revisionnoticebook:end,project:revisionnoticebook:card_end,project:revisionnoticebook:mel_end") 
	@RequestMapping(value = "endRevisionNoticeBook", method = RequestMethod.POST)
	@ResponseBody
	public Object endRevisionNoticeBook(@RequestBody Map<String, Object> requestParamMap) throws BusinessException {
		try {
			// 获取登入user
			User user = ThreadVarUtil.getUser();
			// 响应参数Map
			Map<String, Object> responseParamMap = new HashMap<String, Object>();
				// 根据主键id查询修订通知书信息
				RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService
						.queryByPrimaryKey(String.valueOf(requestParamMap.get("id")));
				// 状态（1：保存、2：提交、2：已审核、3：已批准、4：中止（关闭）、5：审核驳回、6：审批驳回、8：作废、9：关闭、10：完成）
				revisionNoticeBook.setZt(RevisionNoticeBookZtEnum.CLOSE.getId());
				// 指定结束人id
				revisionNoticeBook.setZdjsrid(user.getId());
				// 指定结束日期
				revisionNoticeBook.setZdjsrq(new Date());
				// 指定结束原因
				revisionNoticeBook.setZdjsyy(String.valueOf(requestParamMap.get("zdjsyy")));
				responseParamMap=revisionNoticeBookService.endRevisionNoticeBook(revisionNoticeBook);
			return responseParamMap;
		} catch (Exception e) {
			throw new BusinessException("修订通知书指定结束失败");
		}

	}

	/**
	 * @author pingxiaojun
	 * @description 关闭修订通知书
	 * @param requestParamMap
	 * @throws Exception
	 * @return 指定结束修订通知书的处理结果
	 */
	 @Privilege(code="project:revisionnoticebook:cancel,project:revisionnoticebook:card_cancel,project:revisionnoticebook:mel_cancel") 
	@ResponseBody
	@RequestMapping(value = "closeRevisionNoticeBook", method = RequestMethod.POST)
	public Object closeRevisionNoticeBook(@RequestBody Map<String, Object> requestParamMap) throws BusinessException {
		try {
			// 获取登入user
			User user = ThreadVarUtil.getUser();
			// 响应参数Map
			Map<String, Object> responseParamMap = new HashMap<String, Object>();
				// 根据主键id查询修订通知书信息
				RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService
						.queryByPrimaryKey(String.valueOf(requestParamMap.get("id")));
				// 状态（1：保存、2：提交、2：已审核、3：已批准、4：中止（关闭）、5：审核驳回、6：审批驳回、8：作废、9：关闭、10：完成）
				revisionNoticeBook.setZt(RevisionNoticeBookZtEnum.FINISH.getId());
				// 指定结束人id
				revisionNoticeBook.setZdjsrid(user.getId());
				// 指定结束日期
				revisionNoticeBook.setZdjsrq(new Date());
				// 指定结束原因
				revisionNoticeBook.setZdjsyy(String.valueOf(requestParamMap.get("zdjsyy")));
				// 完成日期
				revisionNoticeBook.setWcrq(Utils.DT.toDate((String) requestParamMap.get("wcrq")));
				responseParamMap=revisionNoticeBookService.closeRevisionNoticeBook(revisionNoticeBook);
			return responseParamMap;
		} catch (Exception e) {
			throw new BusinessException("修订通知书指定结束失败");
		}

	}

	/**
	 * @author liub
	 * @description 查询修订通知书
	 * @param request,model
	 * @return List<Map<String, Object>>
	 */
	// @Privilege(code="project:revisionnoticebook:alllist")
	@ResponseBody
	@RequestMapping(value = "queryAllBook", method = RequestMethod.POST)
	public List<Map<String, Object>> queryAllBook(HttpServletRequest request, RevisionNoticeBook revisionNoticeBook)
			throws BusinessException {
		List<Map<String, Object>> tzsList = null;
		try {
			tzsList = revisionNoticeBookService.findAll(revisionNoticeBook);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败");
		}
		return tzsList;
	}

	/**
	 * @author sunji
	 * @description 检查修改权限
	 * @param model,id
	 * @return 页面视图
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request, @RequestParam String id) {
		return revisionNoticeBookService.checkUpdMt(SessionUtil.getUserFromSession(request), id);
	}

	/**
	 * @author sunji
	 * @description 初始化修订通知书（审核）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 *
	 */
	
	@Privilege(code="project:revisionnoticebook:main:02,project:revisionnoticebook:main:card_02,project:revisionnoticebook:main:mel_02")
	@RequestMapping("intoShenheMainRevisionNoticeBook")
	public ModelAndView intoShenheMainRevisionNoticeBook(Model model, @RequestParam String id, @RequestParam String dprtcode) throws BusinessException {
		// 响应参数Map
		Map<String, Object> responseParamMap = new HashMap<String, Object>();

		// 根据主键id查询修订通知书信息
		RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.queryByPrimaryKey(id);
		if(revisionNoticeBook==null){
			throw new BusinessException("查询失败");
		}
		// 修订人列表
		List<User> xdrList = userService.getByDprtcode(dprtcode);
		responseParamMap.put("xdrList", xdrList);

		// 根据指令id查询下达指令来源信息
		List<Map<String, Object>> orderSourceList = orderSourceService.queryOrderSourceListByZlid(id);
		if (orderSourceList != null && orderSourceList.size() > 0) {
			StringBuffer pgdidSbf = new StringBuffer();
			StringBuffer pgdhSbf = new StringBuffer();
			List<TechnicalFile> pgdhList = new ArrayList<TechnicalFile>();

			for (Map<String, Object> orderSource : orderSourceList) {
				pgdidSbf.append(orderSource.get("PGDID")).append(",");
				pgdhSbf.append(orderSource.get("PGDH")).append(",");

				// 评估单信息
				TechnicalFile technicalFile = new TechnicalFile();
				technicalFile.setId(String.valueOf(orderSource.get("PGDID")));
				technicalFile.setPgdh(String.valueOf(orderSource.get("PGDH")));
				technicalFile.setShzltgh(String.valueOf(orderSource.get("SHZLTGH")));
				pgdhList.add(technicalFile);
			}

			// 评估单id
			responseParamMap.put("pgdids", pgdidSbf.substring(0, pgdidSbf.length() - 1));
			// 评估单号
			responseParamMap.put("pgdhs", pgdhSbf.substring(0, pgdhSbf.length() - 1));
			// 评估单号列表
			responseParamMap.put("pgdhList", pgdhList);
		}
		responseParamMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());

		responseParamMap.put("revisionNoticeBook", revisionNoticeBook);
		return new ModelAndView("project/revisionNoticeBook/shenhe_revisionNoticeBook", responseParamMap);
	}

	/**
	 * @author sunji
	 * @description 初始化修订通知书（批复）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 *
	 */
	@Privilege(code="project:revisionnoticebook:main:03,project:revisionnoticebook:main:card_03,project:revisionnoticebook:main:mel_03")
	@RequestMapping("intoPifuMainRevisionNoticeBook")
	public ModelAndView intoPifuMainRevisionNoticeBook(Model model, @RequestParam String id, @RequestParam String dprtcode) throws BusinessException {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();

		// 根据主键id查询修订通知书信息
		RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.queryByPrimaryKey(id);
		if(revisionNoticeBook==null){
			throw new BusinessException("查询失败");
		}
		// 修订人列表
		List<User> xdrList = userService.getByDprtcode(dprtcode);
		responseParamMap.put("xdrList", xdrList);

		// 根据指令id查询下达指令来源信息
		List<Map<String, Object>> orderSourceList = orderSourceService.queryOrderSourceListByZlid(id);
		if (orderSourceList != null && orderSourceList.size() > 0) {
			StringBuffer pgdidSbf = new StringBuffer();
			StringBuffer pgdhSbf = new StringBuffer();
			List<TechnicalFile> pgdhList = new ArrayList<TechnicalFile>();

			for (Map<String, Object> orderSource : orderSourceList) {
				pgdidSbf.append(orderSource.get("PGDID")).append(",");
				pgdhSbf.append(orderSource.get("PGDH")).append(",");

				// 评估单信息
				TechnicalFile technicalFile = new TechnicalFile();
				technicalFile.setId(String.valueOf(orderSource.get("PGDID")));
				technicalFile.setPgdh(String.valueOf(orderSource.get("PGDH")));
				technicalFile.setShzltgh(String.valueOf(orderSource.get("SHZLTGH")));
				pgdhList.add(technicalFile);
			}

			// 评估单id
			responseParamMap.put("pgdids", pgdidSbf.substring(0, pgdidSbf.length() - 1));
			// 评估单号
			responseParamMap.put("pgdhs", pgdhSbf.substring(0, pgdhSbf.length() - 1));
			// 评估单号列表
			responseParamMap.put("pgdhList", pgdhList);
		}
		responseParamMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());
		responseParamMap.put("revisionNoticeBook", revisionNoticeBook);
		return new ModelAndView("project/revisionNoticeBook/pifu_revisionNoticeBook", responseParamMap);
	}

	/**
	 * @author sunji
	 * @description 修改保存状态的信息（审核提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@Privilege(code="project:revisionnoticebook:main:02,project:revisionnoticebook:main:card_02,project:revisionnoticebook:main:mel_02")
	@ResponseBody
	@RequestMapping(value = "submitshenheMainRevisionNoticeBook", method = RequestMethod.POST)
	public Object submitshenheMainRevisionNoticeBook(@RequestBody RevisionNoticeBook revisionNoticeBook,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			revisionNoticeBookService.updateshenheRevisionNoticeBook(revisionNoticeBook);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return resultMap;
	}

	/**
	 * @author sunji
	 * @description 修改保存状态的信息（批复提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException
	 * @develop date 2016.08.18
	 */
	 
	@Privilege(code="project:revisionnoticebook:main:03,project:revisionnoticebook:main:card_03,project:revisionnoticebook:main:mel_03")
	@ResponseBody
	@RequestMapping(value = "submitpifuMainRevisionNoticeBook", method = RequestMethod.POST)
	public Object submitpifuMainRevisionNoticeBook(@RequestBody RevisionNoticeBook revisionNoticeBook,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			revisionNoticeBookService.updatepifuRevisionNoticeBook(revisionNoticeBook);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return resultMap;
	}



	@RequestMapping(value = "/export/{type}/{id}")
	public String export(@PathVariable String id, @PathVariable String type, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			RevisionNoticeBook revisionNoticeBook = revisionNoticeBookService.selectByPrimaryKey(id);
			model.addAttribute("id", id);
			String path = "http://".concat(request.getServerName()).concat(":")
					.concat(String.valueOf(request.getServerPort()))
					.concat(request.getContextPath())
					.concat("/static/images/report");
			model.addAttribute("images_path", path.concat("/south_logo.jpg"));
			model.addAttribute("jxzlh", revisionNoticeBook.getJszlh());
			model.addAttribute("xdr",
					revisionNoticeBook.getXdr() != null ? revisionNoticeBook.getXdr().getUsername() : "");
			model.addAttribute("bb", revisionNoticeBook.getBb());
			model.addAttribute("xdzt", revisionNoticeBook.getXdzt());
			model.addAttribute("jx", revisionNoticeBook.getJx());
			model.addAttribute("xdqx", revisionNoticeBook.getXdqx() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, revisionNoticeBook.getXdqx()));
			model.addAttribute("xdnr", revisionNoticeBook.getXdnr());
			model.addAttribute("wcrq", revisionNoticeBook.getWcrq() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, revisionNoticeBook.getWcrq()));
			model.addAttribute("zdjsr",
					revisionNoticeBook.getZdjs_user() != null ? revisionNoticeBook.getZdjs_user().getUsername() : "");
			model.addAttribute("zdjsrq", revisionNoticeBook.getZdjsrq() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, revisionNoticeBook.getZdjsrq()));

//			return "redirect:/report/".concat(type).concat("/").concat(revisionNoticeBook.getDprtcode())
//					.concat("/RevisionNoticeBook");
			return outReport(type, revisionNoticeBook.getDprtcode(), "RevisionNoticeBook", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	/**
	 * 修改接收状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "updateJszt", method = RequestMethod.POST)
	public Map<String, Object> updateJszt(@RequestBody RevisionNoticeBook revisionNoticeBook,HttpServletRequest request) throws Exception{
		Map <String, Object > resultMap =new HashMap<String, Object>();
		try {
			revisionNoticeBookService.updateJszt(revisionNoticeBook);
		} catch (Exception e) {
			throw new BusinessException("数据修改失败",e);
		}
		return resultMap;
	}

	/**
	 * @author sunji
	 * @description 批量审核
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 */
	@Privilege(code="project:revisionnoticebook:main:02,project:revisionnoticebook:main:card_02,project:revisionnoticebook:main:mel_02")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		
		try {
			return revisionNoticeBookService.updateBatchAudit(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量审核失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 批量批准
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 */
	@Privilege(code="project:revisionnoticebook:main:03,project:revisionnoticebook:main:card_03,project:revisionnoticebook:main:mel_03")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return revisionNoticeBookService.updateBatchApprove(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量批准失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据评估单id、通知书类型查询修订通知书数据
	 * @param pgdid,tzslx
	 * @return List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "queryByPgdId", method = RequestMethod.POST)
	public List<Map<String, Object>> queryByPgdId(String pgdid,Integer tzslx)throws BusinessException {
		List<Map<String, Object>> list = null;
		try {
			list = revisionNoticeBookService.queryByPgdId(pgdid,tzslx);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
		return list;
	}
}
