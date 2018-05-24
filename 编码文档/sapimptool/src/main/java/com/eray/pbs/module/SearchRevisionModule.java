package com.eray.pbs.module;

import com.eray.pbs.server.DbServer;
import com.eray.pbs.thread.SearchRevisionCustomer;
import com.eray.pbs.util.Module;
import com.eray.util.framework.ThreadPoolManager;

public class SearchRevisionModule implements Module<Object>
{

	@Override
    public void init(DbServer<Object> server) throws Exception
    {
    }

	@Override
    public void start() throws Exception
    {
		ThreadPoolManager.getThreadPoolExecutor().execute(new SearchRevisionCustomer());
    }

	@Override
    public void stop()
    {
    }

	@Override
    public void destroy()
    {
    }

}
