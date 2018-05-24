package com.eray.thjw.material2.control;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.aerialmaterial.dao.StoreInnerSearchMapper;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.ToolBorrowRecord;
import com.eray.thjw.aerialmaterial.service.StoreInnerSearchServcie;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.FrozenHistory;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import enu.MaterialToolSecondTypeEnum;
import enu.MaterialTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author 裴秀
 * @description 库内查询
 */
@Controller
@RequestMapping("material/stock/material")
public class MaterialInsideController extends BaseController{
	
@Resource	
private StoreInnerSearchServcie storeInnerSearchServcie;

@Resource
private StoreInnerSearchMapper storeInnerSearchMapper;

@Resource
private DeptInfoService deptInfoService;
	/**
	 * @Description 库内查询
     * @CreateTime 2018年03月08日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:stock:material:inside")
	@RequestMapping(value = "inside", method = RequestMethod.GET)
	public ModelAndView inside(Map<String, Object> model)throws BusinessException {
		model.put("isTool", false);
	    return new ModelAndView("/material2/stockmaterial/inside/inside_main",model);
	
	}
	/**
	 * @Description 库存明细账
     * @CreateTime 2018年03月08日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:stock:material:detail")
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/stockmaterial/detail/detail_main",model);
	
	}
	
	/**
	 * @Description 库内查询列表
	 * @CreateBy 刘邓
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryInnerList", method = RequestMethod.POST)
	public Map<String, Object> queryInnerList(@RequestBody Stock stock)throws BusinessException {
             return storeInnerSearchServcie.queryInnerStorePageList(stock);
	}
	
	/**
	 * @Description 根据库存id查询库存详情
	 * @param kcid
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryStockById", method = RequestMethod.POST)
	public Map<String,Object> queryStockById(@RequestParam String kcid) throws BusinessException{
		if (StringUtils.isBlank(kcid))
			throw new BusinessException("参数为空");
		Map<String, Object> map = new HashMap<String, Object>();
		Stock stock = storeInnerSearchServcie.queryKcByKcid(kcid);
		List<StockHistory> list = storeInnerSearchServcie.queryStoreHistoryByKcid(kcid);
		map.put("stock", stock);
		map.put("stockHistory", list);
		if (stock.gethCMainData() != null&& MaterialTypeEnum.APPOINTED.getId() == stock.gethCMainData().getHclx()) {// 如果是工具
			List<ToolBorrowRecord> listToolsRecords = storeInnerSearchServcie
					.getListBykcid(stock.getId());
			map.put("borrowRecords", listToolsRecords);
		}
		return map;
	}
		
	/**
	 * @Description 库内查询编辑页面保存
	 * @CreateBy 刘邓
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody Stock stock) throws BusinessException  {
		if (StringUtils.isBlank(stock.getId()))
			throw new BusinessException("参数为空");  
			try {
				storeInnerSearchServcie.save(stock);
			} catch (BusinessException e) {
				super.getLogger().error("库存保存失败",e);
				throw new BusinessException(e.getMessage());
			}
	
	}
	
	/**
	 * @Description 根据库存id查询冻结履历信息
	 * @param frozenHistory
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryFrozenHistoryBykcid", method = RequestMethod.POST)
	public Map<String,Object> queryFrozenHistoryBykcid(@RequestBody FrozenHistory frozenHistory) throws BusinessException{
			try {
				return storeInnerSearchServcie.queryFrozenHistoryBykcid(frozenHistory);
			} catch (BusinessException e) {
				  super.getLogger().error("查询冻结履历失败", e);
				  throw new BusinessException("查询冻结履历失败");
			}
	}
	
	/**
	 * @Description 根据库存id查询库存详情
	 * @param model
	 * @param kcid
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Map<String, Object> model,@RequestParam String kcid,@RequestParam String isTool) throws BusinessException {
		model.put("kcid", kcid);
		model.put("isTool", isTool);
		return new ModelAndView("/material2/stockmaterial/detail/kc_detail",model);
	}
	
	/**
	 * @Description 根据库存id查询工具借用归还记录
	 * @param toolBorrowRecord
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "viewToolHistory", method = RequestMethod.POST)
	public Map<String,Object> queryToolHistory(@RequestBody ToolBorrowRecord toolBorrowRecord) throws BusinessException{
		    try {
				return storeInnerSearchServcie.getListAllBykcid(toolBorrowRecord);
			} catch (BusinessException e) {
				  super.getLogger().error("查询工具借还履历失败", e);
				  throw new BusinessException("查询工具借还履历失败");
			}
		
	}
	
	
	
	
	/**
	 * @Description 航材库内查询导出
	 * @CreateTime 2018年03月19日 下午1:44:17
	 * @CreateBy 刘邓
	 * @param record
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "{type}/{name}")
	public String export(@PathVariable String type,@PathVariable String name,String json, Model model) throws BusinessException {
		try {
			StringBuilder str = new StringBuilder();
			Stock record = JSON.parseObject(json, Stock.class);
			List<Stock> list=storeInnerSearchMapper.queryAll(record);
			Map<String, Object> paramsMap;
			for (Stock stock : list) {
				str.delete(0, str.length());
				if(stock.getParamsMap() == null){
					paramsMap = new HashMap<String, Object>();
				}else{
					paramsMap = stock.getParamsMap();
				}
				if(stock.getDjsl() != null && stock.getDjsl().compareTo(new BigDecimal(0))>0){
					str.append((stock.getKcsl() != null ?stock.getKcsl().stripTrailingZeros().
							subtract(stock.getDjsl() != null?stock.getDjsl().stripTrailingZeros():new BigDecimal(0)).
							stripTrailingZeros().toPlainString():"0"));
					str.append("/").append((stock.getKcsl()!= null ?stock.getKcsl().
							stripTrailingZeros().toPlainString():"0")).append(stock.getJldw() != null ?stock.getJldw():"");
				}else{
					str.append((stock.getKcsl()!= null ?stock.getKcsl().
							stripTrailingZeros().toPlainString():"0")).append(stock.getJldw() != null ?stock.getJldw():"");
				}
				paramsMap.put("kcsl", str.toString());
				if(stock.getZt() != null ){
					paramsMap.put("ztText",StockStatusEnum.getName(stock.getZt()));
				}else{
					paramsMap.put("ztText","其他");
				}
				if(stock.gethCMainData() != null && stock.gethCMainData().getHclx() != null){
					if(stock.gethCMainData().getHclx() == MaterialTypeEnum.APPOINTED.getId()){
						paramsMap.put("hclxText",MaterialToolSecondTypeEnum.getName(stock.gethCMainData().getHclxEj()!=null?stock.gethCMainData().getHclxEj():0));
					}else{
						paramsMap.put("hclxText",MaterialTypeEnum.getName(stock.gethCMainData().getHclx()));
					}
				}
				if(StringUtils.isNotBlank(stock.getTsn())){
					//分钟转小时
					stock.setTsn(StringAndDate_Util.convertToHour(stock.getTsn()));
				}
                if(StringUtils.isNotBlank(stock.getTso())){
					//分钟转小时
                	stock.setTso(StringAndDate_Util.convertToHour(stock.getTso()));
				}
			}
			
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			if("1".equals(type)){
				return outReport("xls", "common", "stock", model);
			}else{
				return outReport("xls", "common", "insideut", model);
			}
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	@RequestMapping(value = "viewHtml", method = RequestMethod.GET)
	public ModelAndView viewHtml(String id ,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		Stock stock = storeInnerSearchServcie.queryKcByKcid(id);
		if(null != stock && StringUtils.isBlank(stock.getUuiddm())){
			storeInnerSearchServcie.updateUuiddm4Print(stock.getId());
			 stock = storeInnerSearchServcie.queryKcByKcid(id);
		}
		User user = ThreadVarUtil.getUser();
		DeptInfo DeptInfo = deptInfoService.selectById(user.getJgdm());
		String imagePath = "zx.jpg";
		String type = "135";
		if(null != DeptInfo){
			if("145".equals(DeptInfo.getDeptType())){
				imagePath = "hx.jpg";
				type = "145";
			}
		}
		String path = "/static/images/report";
		model.put("stock", stock);
		model.put("type", type);
		model.put("images_path", path.concat(File.separator).concat(imagePath));
		return new ModelAndView("/common/print/view",model);
	}
	/**
	 * 
	 * @Description 生成二维码
	 * @CreateTime 2018年3月21日 下午4:10:32
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param width
	 * @param height
	 * @param response
	 */
	@RequestMapping(value="/getErWeiCode",method={RequestMethod.POST,RequestMethod.GET} )
    public void getErWeiCode(String id,int width, int height,HttpServletResponse response){
		
        String json = new Gson().toJson(storeInnerSearchServcie.getData4Print(id));
        if(json!=null&&!"".equals(json)){	
            ServletOutputStream stream=null;
            try {
                stream=response.getOutputStream();
                QRCodeWriter writer=new QRCodeWriter();
                Hashtable<EncodeHintType, Object> hintMap = new Hashtable<EncodeHintType, Object>(); 
    		    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // 矫错级别
    		    hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
                BitMatrix m=writer.encode(json, BarcodeFormat.QR_CODE, height,width,hintMap);
                //去白边
                int[] rec = m.getEnclosingRectangle();  
                int resWidth = rec[2] + 1;  
                int resHeight = rec[3] + 1;  
              
                BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);  
                resMatrix.clear();  
                for (int i = 0; i < resWidth; i++) {  
                    for (int j = 0; j < resHeight; j++) {  
                        if (m.get(i + rec[0], j + rec[1]))  
                            resMatrix.set(i, j);  
                    }  
                }  
                MatrixToImageWriter.writeToStream(resMatrix, "png", stream);
            } catch (Exception e) {             
                e.printStackTrace();
            }finally{
                if(stream!=null){
                    try {
                        stream.flush();
                        stream.close();
                    } catch (IOException e) {             
                        e.printStackTrace();
                    }
                    
                }
            }
        }
    }
	/**
	 * 
	 * @Description 生成条形码
	 * @CreateTime 2018年3月21日 下午4:11:13
	 * @CreateBy 岳彬彬
	 * @param contents
	 * @param response
	 */
	@RequestMapping(value="/encodeBarCode",method={RequestMethod.POST,RequestMethod.GET} )
	public void encodeBarCode(String contents, HttpServletResponse response) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, 108);
        ServletOutputStream stream=null;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.CODE_128, codeWidth, 40, null);
            stream=response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png",stream);
        }  catch (Exception e) {             
            e.printStackTrace();
        }finally{
            if(stream!=null){
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {             
                    e.printStackTrace();
                }
                
            }
        }
    }
	
	/**
	 * Description 查询未维护数据
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryNoMaintenanceData", method = RequestMethod.POST)
	public Map<String, Object> queryNoMaintenanceData(@RequestBody Stock stock) throws BusinessException{
		try {
			return storeInnerSearchServcie.queryNoMaintenanceData(stock);
		} catch (BusinessException e) {
			 super.getLogger().error("未维护数据查询失败",e.getMessage());
			 throw new BusinessException("未维护数据查询失败");
		} catch(Exception e){
			super.getLogger().error("未维护数据查询失败",e.getMessage());
			throw new BusinessException("未维护数据查询失败");
		}
	}
	
	
	/**
	 * @Description 未维护数据导出
	 * @CreateTime 2018年03月19日 下午1:44:17
	 * @CreateBy 刘邓
	 * @param record
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "NoMaintenanceInfo.xls")
	public String exportNoMaintenanceInfo(String json, Model model) throws BusinessException {
		try {
			Stock record = JSON.parseObject(json, Stock.class);
			List<Stock> list=storeInnerSearchMapper.queryNoMaintenanceData(record);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "NoMaintenance", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	
	/**
	 * @Description 校验库存数据是否存在
	 * @param toolBorrowRecord
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "validate4Exist", method = RequestMethod.POST)
	public Stock validate4Exist(@RequestParam String kcid) throws BusinessException{
		Stock stock = storeInnerSearchServcie.queryKcByKcid(kcid);
		   return stock;
	}
	
	
}
