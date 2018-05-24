package com.eray.thjw.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.po.SaibongGz;
import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.system.dao.SaiBongGzMapper;
import com.eray.thjw.system.service.SaiBongGzService;

/**
 * 采番管理
 * @author xu.yong
 *
 */
@Service(value="saiBongGzService")
public class SaiBongGzServiceImpl implements SaiBongGzService{

	@Resource
	private SaiBongGzMapper saiBongGzMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private DeptInfoMapper deptInfoMapper;

	
	/** 日期类型错误 */
	protected final static Date DATE_TYPE_ERROR = new Date();
	
	@Override
	public List<SaibongGz>  queryAllList(SaibongGz saibongGz)
			throws BusinessException {
		return saiBongGzMapper.queryAllList(saibongGz);
	}
	
	/**
	 * String转Date
	 * @param str
	 * @param rowIndex
	 * @param des
	 * @return
	 */
	protected Date convertToDate(String str){
		return convertToDate(str, null);
	}
	
	/**
	 * String转Date
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected Date convertToDate(String str, Date defaultValue){
		if(StringUtils.isBlank(str)){
			return defaultValue;
		}
		SimpleDateFormat format = new SimpleDateFormat(str);
		try {
			return format.parse(str);
		} catch (ParseException e) {
			return DATE_TYPE_ERROR;
		}
	}

	@Override
	public void update(SaibongGz saibongGz) throws BusinessException {
		
			preview(saibongGz);
			SaibongGz saibongGzs=saiBongGzMapper.queryKey(saibongGz.getCfkey(),saibongGz.getDprtcode());
			
			if(saibongGzs==null){
				saiBongGzMapper.insertSelective(saibongGz);
			}else{
				saiBongGzMapper.updateByPrimaryKeySelective(saibongGz);
			}
	}
	
	/**
	 * 验证
	 * @param saibongGz
	 * @return
	 * @throws BusinessException
	 */
	private  String preview(SaibongGz saibongGz)throws BusinessException{
		
		String tvalue=null;
		//验证时间格式
		try {
			tvalue= saiBongGzMapper.selectPreviewTime(saibongGz);
		} catch (Exception e) {
			throw new BusinessException("日期格式不正确，请重新输入",e);
		}
		
		//验证采番长度
		StringBuffer str=new StringBuffer();
		str.append(saibongGz.getGgdwz());
		str.append(saibongGz.getTrqgs());
		str.append(saibongGz.getFgf());
		
		if((str.length()+saibongGz.getLlshcd())>14){
			throw new BusinessException("采番长度不能大于14，请重新输入");
		}
		
		return tvalue;
	}

	@Override
	public String previewSaibong(SaibongGz saibongGz) throws BusinessException {
		String previewStr=null;
		String tvalue=preview(saibongGz);
			
			//流水号长度
			int dqlsh = 1;
			//分隔符
			String fgf = saibongGz.getFgf();
			//流水号补0
			String dqlshStr = format(dqlsh, saibongGz.getLlshcd().intValue(), saibongGz.getLtbgz());
			
			String[] rule = new String[3];
			try{
				rule[saibongGz.getGcfzcsx()-1] = saibongGz.getGgdwz();
				rule[saibongGz.getTcfzcsx()-1] = tvalue;
				rule[saibongGz.getLcfzcsx()-1] = dqlshStr;
				
				//根据动态文字位置插入动态文字
				String weight = "动态文字";
				int dtwzwz = 3;
				if( null != saibongGz.getDtwzwz()){
					dtwzwz = saibongGz.getDtwzwz();
					rule[dtwzwz -1] =  StringUtils.isNotBlank(rule[dtwzwz -1])?(weight+fgf+rule[dtwzwz -1]):weight;
				}
				
				
			}catch(Exception e){
				throw new BusinessException("采番异常，采蕃规则位置项配置错误", e);
			}
			
			previewStr = appendSaibong(rule, fgf);
		
		return previewStr;
	}
	
	/**
	 * 流水号格式化
	 * @param num 流水号 
	 * @param length 格式化长度
	 * @param type 补0方式，1前补 2后补，其它不补
	 * @return
	 */
	private String format(int num, int length, Integer type){
		if(type == null){
			return String.valueOf(num);
		}
		if(type.intValue() == 1){//前补0
			return fix(num, length) + num;
		}else if(type.intValue() == 2){//后补0
			return num + fix(num, length);
		}else{
			return String.valueOf(num);
		}
	}
	
	/**
	 * 计算补0
	 * @param num 流水号
	 * @param length 格式化长度
	 * @return
	 */
	private String fix(int num, int length){
		StringBuffer fix = new StringBuffer();
		for (int i = 0; i < length-String.valueOf(num).length(); i++) {
			fix.append(0);
		}
		return fix.toString();
	}
	
	
	/**
	 * 拼装番号
	 * @param rule 番号组成
	 * @param fgf 分隔符
	 * @return
	 */
	private String appendSaibong(String[] rule, String fgf){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rule.length; i++) {
			if(StringUtils.isNotBlank(rule[i])){
				if(i != 0 && sb.length() > 0){
					sb.append(fgf);
				}
				sb.append(rule[i]);
			}
		}
		return sb.toString();
	}
	
	public int getLength4Lscd(String dprtcode,String key) throws BusinessException{
		SaibongGz saibongGzs=saiBongGzMapper.queryKey(key,dprtcode);
		if(null == saibongGzs){
			saibongGzs = saiBongGzMapper.queryKey(key,"-1");
		}
		int length = saibongGzs.getLlshcd();
		return (int) Math.pow(10,length);
	}
}
