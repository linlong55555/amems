package com.eray.pbs.thread;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.service.ShiftInOutService;
import com.eray.pbs.service.SyncKqDataDoorService;
import com.eray.util.framework.SpringContextHolder;
/**
 * 同步数据线程 2016.07.21
 * @author ganqing
 *
 */
public class SyncKqDataDoorThread implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(SyncKqDataDoorThread.class);
	
	private Long sleepTime = 1200000L;  //毫秒 = 20分钟 = 20*60*1000
	private Long waitTime;
	private SyncKqDataDoorService syncKqDataDoorService; //数据同步
	private ShiftInOutService shiftInOutService; //排班
	
	
	@Override
	public void run() {
		syncKqDataDoorService = SpringContextHolder.getBean("syncKqDataDoorService");
		shiftInOutService = SpringContextHolder.getBean("shiftInOutService");
		while (true) {
			if (syncKqDataDoorService == null) {
				logger.error("syncKqDataDoorService is null.");
			}
			if (shiftInOutService == null) {
				logger.error("shiftInOutService is null.");
			}
			
			waitTime = new Date().getTime();
			syncKqDataDoorService.addSyncKqDataDoorVO(); //同步数据
			logger.info("in&out start sysn datas.");
			Set<String> yyyyMMddSet = shiftInOutService.handDatas(); //处理数据程序
			logger.info("handle datas over.");
			shiftInOutService.modifyShiftInAndOutData(yyyyMMddSet); //对插入的数据进行一次调整2016.09.26
			logger.info("modifyShiftInAndOutData over.");
			waitTime = new Date().getTime() - waitTime;
			if (sleepTime > waitTime) {
				logger.info("sleep time:" + (sleepTime - waitTime));
				try {
					Thread.sleep(sleepTime - waitTime);
				} catch (InterruptedException e) {
					logger.error("SyncKqDataDoorThread is interrupted!");
				}
			}
		}
		
	}

}
