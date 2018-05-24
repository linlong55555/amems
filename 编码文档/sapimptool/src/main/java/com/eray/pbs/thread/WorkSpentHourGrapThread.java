package com.eray.pbs.thread;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.service.WorkSpentHourGrapService;
import com.eray.util.framework.SpringContextHolder;

/**
 * spent hour内容抓取回写至工单信息表中 2017.01.10
 * @author ganqing
 *
 */
public class WorkSpentHourGrapThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(WorkSpentHourGrapThread.class);
	
	private Long sleepTime = 5400000L;  //毫秒 = 90分钟 = 90*60*1000
	
	private Long waitTime;
	
	@Override
	public void run() {
		
		WorkSpentHourGrapService workSpentHourGrapService = SpringContextHolder.getBean("workSpentHourGrapService");
		if (workSpentHourGrapService != null) {
			while (true) {
				waitTime = new Date().getTime();
				workSpentHourGrapService.wirteSpentHour();
				logger.info("workSpentHourGrap success,the Date {}", new Date());
				waitTime = new Date().getTime() - waitTime;
				if (sleepTime > waitTime) {
					try {
						Thread.sleep(sleepTime - waitTime);
					} catch (InterruptedException e) {
						logger.error("spent Thread is interrupted!");
					}
				}
			}
		} else {
			logger.error("workSpentHourGrapService instantiation fail");
		}
		
	}

}
