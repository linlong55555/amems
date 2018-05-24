package com.eray.component.saibong.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.dao.SaibongUtilMapper;
import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.po.SaibongGz;
import com.eray.component.saibong.po.SaibongXx;
import com.eray.component.saibong.po.SaibongXxTwo;
import com.eray.component.saibong.service.SaibongUtilService;

@Service
public class SaibongUtilServiceImpl implements SaibongUtilService {

	@Resource
	private SaibongUtilMapper saibongUtilMapper;
	
	/**
	 * 生成采蕃号
	 * @param dprtcode 组织机构代码
	 * @param key 采蕃Key
	 * @return
	 * @throws SaibongException 
	 */
	public String generate(String dprtcode, String key) throws SaibongException{
		
		if(StringUtils.isBlank(dprtcode) || StringUtils.isBlank(key)) {
			throw new SaibongException("采番异常，参数错误");
		}
		
		//获取当前采番(含当前流水号)
		SaibongGz saibongGz = this.saibongUtilMapper.selectSaibongXx(dprtcode, key);
		
		if(saibongGz == null){
			throw new SaibongException("采番异常，无采蕃规则");
		}
		if(saibongGz.getLlshcd() == null || saibongGz.getLlshcd().intValue() == 0){
			throw new SaibongException("采番异常，采蕃规则流水号长度项配置错误");
		}
		
		//生成采番
		if(saibongGz.getSaibongXx() != null && StringUtils.isNotBlank(saibongGz.getSaibongXx().getCfkey())){
			//规则下已存在采番，递增采番
			SaibongXx saibongXx = saibongGz.getSaibongXx();
			saibongXx.setGvalue(saibongGz.getGgdwz());
			saibongXx.setTvalue(saibongGz.getTvalue());
			
			//当前流水号
			int dqlsh = 1;
			if(StringUtils.isBlank(saibongGz.getTvalue()) || saibongGz.getTvalue().equals(saibongXx.getFssjStr())){//当前数据的发生时间与采番规则当前时间相同，继续采番
				dqlsh = saibongXx.getDqlsh() + 1;
			}
			
			//采番长度超出，重置
			if(String.valueOf(dqlsh).length() > saibongGz.getLlshcd().intValue()){
				dqlsh = 1;
			}
			//分隔符
			String fgf = saibongGz.getFgf();
			if(fgf == null){
				fgf = "";
			}
			
			//流水号补0
			String dqlshStr = format(dqlsh, saibongGz.getLlshcd().intValue(), saibongGz.getLtbgz());
			
			String[] rule = new String[3];
			try{
				rule[saibongGz.getGcfzcsx()-1] = saibongGz.getGgdwz();
				rule[saibongGz.getTcfzcsx()-1] = saibongXx.getTvalue();
				rule[saibongGz.getLcfzcsx()-1] = dqlshStr;
			}catch(Exception e){
				throw new SaibongException("采番异常，采蕃规则位置项配置错误", e);
			}
			
			String dqfh = appendSaibong(rule, fgf);
			//更新采蕃
			saibongXx.setDqfh(dqfh);
			saibongXx.setDqlsh(dqlsh);
			saibongXx.setLvalue(dqlshStr);
			saibongXx.setFssj(saibongGz.getTsysdate());
			this.saibongUtilMapper.updateSaibongXx(saibongXx);
			return dqfh;
		}else{//规则下未进行采番，获取初始化采番

			SaibongXx saibongXx = new SaibongXx();
			saibongXx.setDprtcode(dprtcode);
			saibongXx.setCfkey(key);
			saibongXx.setGvalue(saibongGz.getGgdwz());
			saibongXx.setTvalue(saibongGz.getTvalue());
			
			//当前流水号
			int dqlsh = 1;
			
			//分隔符
			String fgf = saibongGz.getFgf();
			if(fgf == null){
				fgf = "";
			}
			
			//流水号补0
			String dqlshStr = format(dqlsh, saibongGz.getLlshcd().intValue(), saibongGz.getLtbgz());
			
			String[] rule = new String[3];
			try{
				rule[saibongGz.getGcfzcsx()-1] = saibongGz.getGgdwz();
				rule[saibongGz.getTcfzcsx()-1] = saibongXx.getTvalue();
				rule[saibongGz.getLcfzcsx()-1] = dqlshStr;
			}catch(Exception e){
				throw new SaibongException("采番异常，采蕃规则位置项配置错误", e);
			}
			
			String dqfh = appendSaibong(rule, fgf);
			//更新采蕃
			saibongXx.setDqfh(dqfh);
			saibongXx.setDqlsh(dqlsh);
			saibongXx.setLvalue(dqlshStr);
			saibongXx.setFssj(saibongGz.getTsysdate());
			this.saibongUtilMapper.insertSaibongXx(saibongXx);
			return dqfh;
		}
	}
	
	/**
	 * 生成采蕃号（带加权值）
	 * @param dprtcode 组织机构代码
	 * @param key 采蕃Key
	 * @param weight 加权值
	 * @return
	 * @throws SaibongException 
	 */
	public String generate(String dprtcode, String key, String weight) throws SaibongException{
		if(StringUtils.isBlank(dprtcode) || StringUtils.isBlank(key) || StringUtils.isBlank(weight)) {
			throw new SaibongException("采番异常，参数错误");
		}
		
		//获取key及当前采蕃
		//获取当前采番(含当前流水号)
		SaibongGz saibongGz = this.saibongUtilMapper.selectSaibongXxTwo(dprtcode, key, weight);
		
		if(saibongGz == null){
			throw new SaibongException("采番异常，无采蕃规则");
		}
		if(saibongGz.getLlshcd() == null || saibongGz.getLlshcd().intValue() == 0){
			throw new SaibongException("采番异常，采蕃规则流水号长度项配置错误");
		}
		
		//生成采番
		if(saibongGz.getSaibongXxTwo() != null && StringUtils.isNotBlank(saibongGz.getSaibongXxTwo().getCfkey())){
			//规则下已存在采番，递增采番
			SaibongXxTwo saibongXxTwo = saibongGz.getSaibongXxTwo();
			saibongXxTwo.setGvalue(saibongGz.getGgdwz());
			saibongXxTwo.setTvalue(saibongGz.getTvalue());
			
			//当前流水号
			int dqlsh = 1;
			if(StringUtils.isBlank(saibongGz.getTvalue()) || saibongGz.getTvalue().equals(saibongXxTwo.getFssjStr())){//当前数据的发生时间与采番规则当前时间相同，继续采番
				dqlsh = saibongXxTwo.getDqlsh() + 1;
			}
			
			//采番长度超出，重置
			if(String.valueOf(dqlsh).length() > saibongGz.getLlshcd().intValue()){
				dqlsh = 1;
			}
			//分隔符
			String fgf = saibongGz.getFgf();
			if(fgf == null){
				fgf = "";
			}
			
			//流水号补0
			String dqlshStr = format(dqlsh, saibongGz.getLlshcd().intValue(), saibongGz.getLtbgz());
			
			String[] rule = new String[3];
			try{
				rule[saibongGz.getGcfzcsx()-1] = saibongGz.getGgdwz();
				rule[saibongGz.getTcfzcsx()-1] = saibongXxTwo.getTvalue();
				rule[saibongGz.getLcfzcsx()-1] = dqlshStr;
				
			    //根据动态文字的位置将文本插入到正确的位置。
				int dtwzwz = 3;
				if(saibongGz.getDtwzwz() != null){
					dtwzwz = saibongGz.getDtwzwz();
				}
				rule[dtwzwz -1] =  StringUtils.isNotBlank(rule[dtwzwz -1])?(weight+fgf+rule[dtwzwz -1]):weight;

				
			}catch(Exception e){
				throw new SaibongException("采番异常，采蕃规则位置项配置错误", e);
			}
			
			String dqfh = appendSaibong(rule, fgf);
			//更新采蕃
			saibongXxTwo.setDqfh(dqfh);
			saibongXxTwo.setDqlsh(dqlsh);
			saibongXxTwo.setLvalue(dqlshStr);
			saibongXxTwo.setFssj(saibongGz.getTsysdate());
			this.saibongUtilMapper.updateSaibongXxTwo(saibongXxTwo);
			return dqfh;
		}else{//规则下未进行采番，获取初始化采番

			SaibongXxTwo saibongXxTwo = new SaibongXxTwo();
			saibongXxTwo.setDprtcode(dprtcode);
			saibongXxTwo.setCfkey(key);
			saibongXxTwo.setDjh(weight);
			saibongXxTwo.setGvalue(saibongGz.getGgdwz());
			saibongXxTwo.setTvalue(saibongGz.getTvalue());
			
			//当前流水号
			int dqlsh = 1;
			
			//分隔符
			String fgf = saibongGz.getFgf();
			if(fgf == null){
				fgf = "";
			}
			
			//流水号补0
			String dqlshStr = format(dqlsh, saibongGz.getLlshcd().intValue(), saibongGz.getLtbgz());
			
			String[] rule = new String[3];
			try{
				rule[saibongGz.getGcfzcsx()-1] = saibongGz.getGgdwz();
				rule[saibongGz.getTcfzcsx()-1] = saibongXxTwo.getTvalue();
				rule[saibongGz.getLcfzcsx()-1] = dqlshStr;
				//根据动态文字的位置将文本插入到正确的位置。
				 
				int dtwzwz = 3;
				if(saibongGz.getDtwzwz() != null){
					dtwzwz = saibongGz.getDtwzwz();
				}
				
				rule[dtwzwz -1] =  StringUtils.isNotBlank(rule[dtwzwz -1])?(weight+fgf+rule[dtwzwz -1]):weight;
				
			}catch(Exception e){
				throw new SaibongException("采番异常，采蕃规则位置项配置错误", e);
			}
			
			String dqfh = appendSaibong(rule, fgf);
			//更新采蕃
			saibongXxTwo.setDqfh(dqfh);
			saibongXxTwo.setDqlsh(dqlsh);
			saibongXxTwo.setLvalue(dqlshStr);
			saibongXxTwo.setFssj(saibongGz.getTsysdate());
			this.saibongUtilMapper.insertSaibongXxTwo(saibongXxTwo);
			return dqfh;
		}
		
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
	
}
