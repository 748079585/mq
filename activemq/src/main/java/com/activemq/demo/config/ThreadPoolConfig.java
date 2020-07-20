package com.activemq.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置。 可配置多个线程池。 支持spring @Async 异步任务，如果使用特定线程池，e.g. @Async("bean name")。
 * Created on 2018/3/8 Created by wei .
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

	private static final String PREFIX_DEFAULT_THREAD = "defaultExecutor-";
	private static final String PREFIX_EVENT_PUBLISH_THREAD = "eventPublishExecutor-";

	/**
	 * 事件发布线程池
	 * 
	 * @return ThreadPoolTaskExecutor
	 */
	@Bean
	public ThreadPoolTaskExecutor eventPublishExecutor() {
		ThreadPoolTaskExecutor executor = getThreadPoolTaskExecutor(PREFIX_EVENT_PUBLISH_THREAD);
		return executor;
	}

	/**
	 * 全局默认线程池
	 * 
	 * @return ThreadPoolTaskExecutor
	 */
	@Bean
	public ThreadPoolTaskExecutor defaultExecutor() {
		ThreadPoolTaskExecutor executor = getThreadPoolTaskExecutor(PREFIX_DEFAULT_THREAD);
		return executor;
	}

	private ThreadPoolTaskExecutor getThreadPoolTaskExecutor(String threadNamePrefix) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(40);
		executor.setKeepAliveSeconds(300);
		executor.setAwaitTerminationSeconds(10);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.initialize();
		return executor;
	}

}
