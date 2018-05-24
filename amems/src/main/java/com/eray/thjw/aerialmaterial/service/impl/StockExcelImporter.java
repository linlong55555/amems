package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.basic.dao.PropertyrightMapper;
import com.eray.thjw.basic.po.Propertyright;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.material2.service.MaterialBatchInfoService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.system.dao.DictItemMapper;
import com.eray.thjw.system.po.DictItem;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.UpdateTypeEnum;
import enu.aerialmaterial.InstockTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.common.PartSnValidationEnum;

@Service("stockexcelimporter")
public class StockExcelImporter extends AbstractExcelImporter<Stock>{
	private static String SHOUGONG="手工制单";
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private MaterialBatchInfoService materialBatchInfoService;

	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private PropertyrightMapper propertyrightMapper;

	@Resource
	private StoreMapper storeMapper;
	@Resource
	private CommonService commonService;
	
	@Resource
	private StorageMapper storageMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private DictItemMapper dictItemMapper;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	List<Stock> stockList = new ArrayList<Stock>(); //部件+序列号唯一校验
	
	@Override
	public void validateParam(Map<Integer, List<Stock>> datasMap)
			throws ExcelImportException {
		//构建件号、序列号Map
		Map<String, List<Integer>> jhxlhMap = getJhxlhMap(datasMap);//文本中,件号序列号,那个几行存在了
		List<String> hclyList = getAllDic("85");	//航材来源
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<Stock> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			Stock ll;
			// 所有部件号
			List<String> bjhList = getAllBjh();
			// 所有仓库号
			List<String> ckhList = getAllCkh();
			//所有产权编号
			List<String> cqbhList = getAllCqbh();
			for (int i = 0; i < datas.size(); i++) {
				ll = datas.get(i);
				if(StringUtils.isBlank(ll.getBjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部件号不能为空");
				}
				if(ll.getKcsl() == null){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，在库数量不能为空");
				}
				
				if(StringUtils.isBlank(ll.getCkh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，仓库号不能为空");
				}
				if(StringUtils.isBlank(ll.getKwh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库位号不能为空");
				}
				// 日期类型验证
				if(ll.getRksj()!=null && ll.getRksj().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，入库时间格式不正确");
				}
				if( ll.getScrq()!=null && ll.getScrq().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，生产日期格式不正确");
				}
				if(ll.getHjsm()!=null && ll.getHjsm().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，货架寿命格式不正确");
				}
				if( ll.getSpqx()!=null &&ll.getSpqx().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，索赔期限格式不正确");
				}
				// 非中文验证
				if(!StringUtils.isBlank(ll.getBjh()) && Utils.Str.isChinese(ll.getBjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部件号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getCkh()) && Utils.Str.isChinese(ll.getCkh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，仓库号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getKwh()) && Utils.Str.isChinese(ll.getKwh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库位号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getXh()) &&Utils.Str.isChinese(ll.getXh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，型号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getGg()) &&Utils.Str.isChinese(ll.getGg())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，规格不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getGrn()) &&Utils.Str.isChinese(ll.getGrn())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，GRN号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getPch()) &&Utils.Str.isChinese(ll.getPch())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，批次号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getSn()) &&Utils.Str.isChinese(ll.getSn())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getHtbhCg()) &&Utils.Str.isChinese(ll.getHtbhCg())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，采购合同号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getHtbhSx()) &&Utils.Str.isChinese(ll.getHtbhSx())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，送修合同号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getTddh()) &&Utils.Str.isChinese(ll.getTddh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，提订单号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getXkzh()) &&Utils.Str.isChinese(ll.getXkzh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，许可证号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getShzh()) &&Utils.Str.isChinese(ll.getShzh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，适航证号不能含有中文");
				}
				// 长度验证
				if(Utils.Str.getLength(ll.getBiz()) > 15){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，币种的最大长度为15");
				}
				// 长度验证
				if(Utils.Str.getLength(ll.getCkh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，仓库号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getKwh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库位号的最大长度为50");
				}
				// 长度验证
				if(Utils.Str.getLength(ll.getCqid()) > 150){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，产权编号的最大长度为150");
				}
				// 长度验证
				if(Utils.Str.getLength(ll.getQczt()) > 15){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，器材状态的最大长度为15");
				}
				if(Utils.Str.getLength(ll.getBjh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部件号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getXh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，型号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getGg()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，规格的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getGrn()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，GRN号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getPch()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，批次号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getSn()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getHtbhCg()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，采购合同编号的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getHtbhSx()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，送修合同编号的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getTddh()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，提订单号的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getXkzh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，许可证号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getShzh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，适航证号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getShzwz()) > 30){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，适航证位置的最大长度为30");
				}
				if(Utils.Str.getLength(ll.getBz()) > 330){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，备注的最大长度为330");
				}
				if(Utils.Str.getLength(ll.getCfyq()) > 150){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，存放要求的最大长度为150");
				}
				if(Utils.Str.getLength(ll.getZzcs()) > 15){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，制造厂商的最大长度为15");
				}
				if(Utils.Str.getLength(ll.getHcly()) > 15){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，来源的最大长度为15");
				}
				
				if(!StringUtils.isBlank(ll.getSn())){
					if(ll.getKcsl() == null){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库存数量不能为空!");
					}else{
						if(new BigDecimal("0.00").compareTo(ll.getKcsl()) != 0 && new BigDecimal("1.00").compareTo(ll.getKcsl()) != 0){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号不为空时,库存数量只能为1或0！");
						}
					}
				}
				
				BigDecimal bd1=new BigDecimal("99999999.99");
			    BigDecimal bd2=new BigDecimal("0");
			    
			    Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式  
			    
				if(ll.getKcsl()!=null){
					  Matcher match=pattern.matcher( ll.getKcsl().toString()); 
					if(ll.getKcsl().compareTo(BIGDECIMAL_TYPE_ERROR) == 0 || match.matches()==false){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库存数量格式不正确");
					}else if(ll.getKcsl().compareTo(bd1) == 1 || ll.getKcsl().compareTo(bd2)== -1){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库存数量的数值为0-99999999.99");
					}
				}
			
				if(ll.getKccb()!=null){
					 Matcher match=pattern.matcher( ll.getKccb().toString()); 
					if(ll.getKccb().compareTo(BIGDECIMAL_TYPE_ERROR) == 0 || match.matches()==false){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库存成本格式不正确");
					}else if(ll.getKccb().compareTo(bd1) == 1 || ll.getKccb().compareTo(bd2) == -1){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库存成本的数值为0-99999999.99");
					}
				
				}
				if(ll.getJz()!=null){
					 Matcher match=pattern.matcher( ll.getJz().toString()); 
					if(ll.getJz().compareTo(BIGDECIMAL_TYPE_ERROR) == 0 || match.matches()==false){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，器材价值格式不正确");
					}else if(ll.getJz().compareTo(bd1) == 1 || ll.getJz().compareTo(bd2)== -1){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，器材价值的数值为0-99999999.99");
					}
					
				}
				// 部件号验证
				if( !bjhList.contains(ll.getBjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部件号不存在");
				}
				
				// 仓库号验证
				if(!ckhList.contains(ll.getCkh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，仓库号不存在");
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库位号不存在");
				}else{
					// 所有库位号
					List<String> kwhList = getAllKwh(ll.getCkh());
					// 库位号验证
					if(!kwhList.contains(ll.getKwh())){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，库位号不存在或没有权限");
					}
				}
				
				// 产权编号验证
				if( !StringUtils.isBlank(ll.getCqid()) && !cqbhList.contains(ll.getCqid())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，产权编号不存在");
				}
				
				
				//件号序列号唯一性验证
				if(!StringUtils.isBlank(ll.getSn())){
					String key = (ll.getBjh()==null?"":ll.getBjh())+"_"+ll.getSn();
					if(jhxlhMap.get(key) != null && jhxlhMap.get(key).size() > 1){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，件号+序列号，在本文档"+jhxlhMap.get(key).toString()+"行已存在");
					}
				}
				
				//航材来源验证
				if(StringUtils.isNotBlank(ll.getHcly()) && !hclyList.contains(ll.getHcly())){
					addErrorMessage("第"+(sheetIndex+1)+"行，来源在数据字典中不存在");
				}
			}
			
			//start: 调用岳彬彬，校验方法，分批次查询，提高查询效率
			Map<Integer, List<Stock>> groupMap = new HashMap<Integer, List<Stock>>();//用map存储,分组后的数据
			int groupNum = 1; //分批次查询
			int stepNum = SysContext.BATCH_PROCESS_SIZE; //每批次200条
			for(int startIndex = 0; null != stockList && startIndex < stockList.size();startIndex += stepNum){
				int endIndex = stockList.size() > (startIndex+stepNum)?(startIndex+stepNum):stockList.size();
				List<Stock> newList = stockList.subList(startIndex,endIndex);
				groupMap.put(groupNum, newList);
			    groupNum++;
			}
			
			StringBuffer tipMessage = new StringBuffer("");
			for (Iterator iter = groupMap.entrySet().iterator(); iter.hasNext();)  
			{  
				Entry entry = (Entry) iter.next();  
				List<Stock> subList = (List<Stock>) entry.getValue();  
				try {
					stockSerivce.getCount4ValidationBjAndXlh(subList, PartSnValidationEnum.OTHER.getId());
				} catch (Exception e) {
					tipMessage.append(e.getMessage());
				}
			}
			
			if(null != tipMessage && tipMessage.toString().length() > 0){
				throw new ExcelImportException(tipMessage.toString());
			}
			//end: 调用岳彬彬，校验方法，分批次查询，提高查询效率
			
		}
	}
	


	private Map<String, List<Integer>> getJhxlhMap(Map<Integer, List<Stock>> sheetList) {
		stockList.clear();
		Map<String, List<Integer>> jhxlhMap = new HashMap<String, List<Integer>>();
		String dprtcode = ThreadVarUtil.getUser().getJgdm();
		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			//工作表对应的的库存数据
			List<Stock> datas = sheetList.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			//循环校验每一行数据
			Stock bean;
			for (int rowIndex = 0; null != datas && rowIndex < datas.size(); rowIndex++) {
				bean = datas.get(rowIndex);
				if(!StringUtils.isBlank(bean.getSn())){
					
					String jh = bean.getBjh()==null?"":bean.getBjh();
					if(Utils.Str.getLength(bean.getSn()) > 50 || Utils.Str.getLength(bean.getBjh()) > 50){
						continue;
					}
					
					String key = jh+"_"+bean.getSn();

					List<Integer> list = new ArrayList<Integer>();
					if(jhxlhMap.get(key) != null){
						list = jhxlhMap.get(key);
					}else{
						Stock stock = new Stock();
						stock.setBjh(bean.getBjh()==null?"":bean.getBjh());
						stock.setSn(bean.getSn());
						stock.setDprtcode(dprtcode);
						stockList.add(stock);
					}
					list.add(rowIndex+3);
					jhxlhMap.put(key, list);
				}
				
			}
		}
		return jhxlhMap;
	}
	
	/**
	 * @Description 获取数据字典
	 * @CreateTime 2018年5月18日 上午10:13:14
	 * @CreateBy 韩武
	 * @param lxid
	 * @return
	 */
	private List<String> getAllDic(String lxid){
		List<String> resultList = new ArrayList<String>();
		List<DictItem> dictList = dictItemMapper.selectByLxidAndDprtcode(lxid, ThreadVarUtil.getUser().getJgdm());
		for (DictItem dictItem : dictList) {
			resultList.add(dictItem.getSz());
		}
		return resultList;
	}
	
	/**
	 * 
	 * @Description 获取所有产权编号
	 * @CreateTime 2018年5月14日 上午10:02:54
	 * @CreateBy 林龙
	 * @return
	 */
	private List<String> getAllCqbh() {
		try {
			List<String> resultList = new ArrayList<String>();
			List<Propertyright> propertyrightlist = propertyrightMapper.selectAllByDprtcode(ThreadVarUtil.getUser().getJgdm());
			for (Propertyright propertyright : propertyrightlist) {
				resultList.add(propertyright.getCqbh());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有部件号
	 * @return
	 */
	private List<String> getAllBjh(){
		try {
			List<String> resultList = new ArrayList<String>();
			List<HCMainData> hCMainDatas = hCMainDataMapper.selectAllByDprtcode(ThreadVarUtil.getUser().getJgdm());
			for (HCMainData hCMainData : hCMainDatas) {
				resultList.add(hCMainData.getBjh());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取所有仓库号
	 * @return
	 */
	private List<String> getAllCkh(){
		try {
			List<String> resultList = new ArrayList<String>();
			List<Store> stores = storeMapper.selectByUserIdJgdm(ThreadVarUtil.getUser().getJgdm(),ThreadVarUtil.getUser().getId());
			for (Store store : stores) {
				resultList.add(store.getCkh());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据仓库号获取所有库位号
	 * @return
	 */
	private List<String> getAllKwh(String storeCode){
		List<String> resultList = new ArrayList<String>();
		try {
			List<Storage> storages = storageMapper.queryStorageListByStoreCode(storeCode,ThreadVarUtil.getUser().getJgdm());
			for (Storage storage : storages) {
				resultList.add(storage.getKwh());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	@Override
	public int writeToDB(Map<Integer, List<Stock>> datasMap)
			throws ExcelImportException {
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<Stock> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			//人员档案
			if(sheetIndex==0){
				for (int i = 0; i < datas.size(); i++) {
					Stock stock =datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					//根据仓库号和组织机构查询仓库信息
					Store store=storeMapper.selectByPrimaryCkh(stock.getCkh(),ThreadVarUtil.getUser().getJgdm());
					//根据仓库号和库位号查询库位信息
					Storage storage=storageMapper.selectByPrimarykwh(stock.getCkh(),stock.getKwh(),ThreadVarUtil.getUser().getJgdm());
					//根据部件号和组织机构查询所有部件
					HCMainData hCMainData=hCMainDataMapper.selectByPrimaryHCMainData(stock.getBjh(),ThreadVarUtil.getUser().getJgdm());
					//根据产权编号和组织机构查询产权编号
					if(!StringUtils.isBlank(stock.getCqid())){
						Propertyright propertyright=propertyrightMapper.selectByPrimaryHCMainData(stock.getCqid(),ThreadVarUtil.getUser().getJgdm());
						stock.setCqid(propertyright.getId());
					}
					
					stock.setCklb(store.getCklb());
					stock.setCkid(store.getId());
					stock.setCkmc(store.getCkmc());
					stock.setKwid(storage.getId());
					stock.setKwh(storage.getKwh());
					stock.setBjid(hCMainData.getId());
					stock.setZwms(hCMainData.getZwms());
					stock.setYwms(hCMainData.getYwms());
					stock.setRksj(commonService.getSysdate());
					stock.setJldw(hCMainData.getJldw());
					if(ThreadVarUtil.getUser().getBmdm()==null){
						stock.setRkbmid("");
					}else{
						stock.setRkbmid(ThreadVarUtil.getUser().getBmdm());
					}
					stock.setRkrid(ThreadVarUtil.getUser().getId());
					stock.setZt(2);
					stock.setXh(hCMainData.getXh());
					stock.setGg(hCMainData.getGg());
					stock.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					
					List<Stock> num=stockMapper.queryCountnums(stock);
					//更新库存成本
					materialBatchInfoService.insertOrUpdate(stock.getDprtcode(), stock.getBjh(), stock.getSn(), stock.getPch(), stock.getKccb(), stock.getBiz(), stock.getJz(), stock.getJzbz());
					if(num.size() > 0){
						
						stockMapper.updateByPrimaryKeySelectives(num.get(0));
						materialRecService.writeStockRec(stock.getId(), czls.toString(), num.get(0).getId(), "", StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.UPDATE,InstockTypeEnum.TYPE0.getName(),"","",SHOUGONG,stock.getKcsl());
					}else{
						// 写入日志
						String id = UUID.randomUUID().toString();
						stock.setId(id);
						stockMapper.insertSelective(stock);
						materialRecService.writeStockRec(stock.getId(), czls.toString(), id, "", StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE,InstockTypeEnum.TYPE0.getName(),"","",SHOUGONG,stock.getKcsl());
					}
				}
			}
		}
		return 1;
	}


	@Override
	public Map<Integer, List<Stock>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		// 结果集
		Map<Integer, List<Stock>> resultMap = new TreeMap<Integer, List<Stock>>();
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			// sheet对应库存
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			List<Stock> list = new ArrayList<Stock>();
			Stock ll;
			Map<Integer, String> bean;
			for (int i = 0; i < mapList.size(); i++) {
				bean = mapList.get(i);
				ll = new Stock();
				/*
				 * 读取excel值
				 */
				ll.setBjh(bean.get(0));
				ll.setSn(bean.get(1));
				ll.setPch(bean.get(2));
				
				if(!StringUtils.isBlank(bean.get(3))){
					ll.setKcsl(convertToBigDecimal(bean.get(3)));
				}
				if(!StringUtils.isBlank(bean.get(4))){
					ll.setKccb(convertToBigDecimal(bean.get(4)));
				}
				if(!StringUtils.isBlank(bean.get(5))){
					ll.setJz(convertToBigDecimal(bean.get(5)));
				}
				
				ll.setJzbz(bean.get(6));
				ll.setBiz(bean.get(6));
				
				ll.setCkh(bean.get(7));
				ll.setKwh(bean.get(8));
				ll.setCqid(bean.get(9));
				ll.setQczt(bean.get(10));
				
				ll.setHcly(bean.get(11));
				ll.setXh(bean.get(12)); //型号
				ll.setGg(bean.get(13));	//规格
				ll.setGrn(bean.get(14));	//GRN
				ll.setShzh(bean.get(15));
				ll.setShzwz(bean.get(16));
				ll.setXkzh(bean.get(17));
				
				ll.setRksj(convertToDates(bean.get(18)));
				ll.setScrq(convertToDate(bean.get(19)));
				ll.setHjsm(convertToDate(bean.get(20)));
				ll.setSpqx(convertToDate(bean.get(21)));
				ll.setCfyq(bean.get(22));
				ll.setZzcs(bean.get(23));
				ll.setTddh(bean.get(24));
				ll.setHtbhCg(bean.get(25));
				ll.setHtbhSx(bean.get(26));
				ll.setBz(bean.get(27));
				
				list.add(ll);
			}
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}

}
