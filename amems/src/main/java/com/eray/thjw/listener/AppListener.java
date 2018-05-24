package com.eray.thjw.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Service;

import com.eray.thjw.util.LuceneUtil;

@Service("appListener")
public class AppListener implements ApplicationListener<ApplicationEvent> {
	// 调用ApplicationContext.publishEvent方法时会触发执行该方法
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// 如果容器关闭时，触发
		if (event instanceof ContextClosedEvent) {
			LuceneUtil.closeAllWriter();
		}
		// 容器刷新时候触发
//		if (event instanceof ContextRefreshedEvent) {
//			ContextRefreshedEvent cre = (ContextRefreshedEvent) event;
//		}
		// 容器启动的时候触发
//		if (event instanceof ContextStartedEvent) {
//			ContextStartedEvent cse = (ContextStartedEvent) event;
//		}
		// 容器停止时候触发
		if (event instanceof ContextStoppedEvent) {
			LuceneUtil.closeAllWriter();
		}
	}

}
