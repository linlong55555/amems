package com.eray.pbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.server.DbServer;
import com.eray.pbs.thread.RevisonSpentTotalBackSAPThread;
import com.eray.pbs.util.Module;
import com.eray.util.framework.ThreadPoolManager;

/**
 * 按月回传revision的工时 2016.08.02
 * @author ganqing
 *
 */
public class RevisonSpentTotalBackSAPModule implements Module<Object> {
	private static final Logger logger = LoggerFactory.getLogger(RevisonSpentTotalBackSAPModule.class);

	@Override
	public void init(DbServer<Object> server) throws Exception {
		logger.info("{} is starting", this.getClass().getSimpleName());
		ThreadPoolManager.getThreadPoolExecutor().execute(new RevisonSpentTotalBackSAPThread());
		logger.info("{} is started", this.getClass().getSimpleName());
	}

	@Override
	public void start() throws Exception {
		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void destroy() {
		
	}

}
