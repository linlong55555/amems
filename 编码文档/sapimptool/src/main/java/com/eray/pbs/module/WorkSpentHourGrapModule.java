package com.eray.pbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.server.DbServer;
import com.eray.pbs.thread.WorkSpentHourGrapThread;
import com.eray.pbs.util.Module;
import com.eray.util.framework.ThreadPoolManager;

/**
 * 抓取spent hour回写至工单信息表 2017.01.10
 * @author ganqing
 *
 */
public class WorkSpentHourGrapModule implements Module<Object> {

	private Logger logger = LoggerFactory.getLogger(WorkSpentHourGrapModule.class);
	
	@Override
	public void init(DbServer<Object> server) throws Exception {
		
	}

	@Override
	public void start() throws Exception {
		logger.info("{} is starting", this.getClass().getSimpleName());
		ThreadPoolManager.getThreadPoolExecutor().execute(new WorkSpentHourGrapThread());
		logger.info("{} is started", this.getClass().getSimpleName());
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void destroy() {
		
	}

}
