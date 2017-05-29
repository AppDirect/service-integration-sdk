package com.appdirect.sdk.utils;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.MDC;

public class MdcExecutorService extends ForkJoinPool {

	private MdcExecutorService(int parallelism, ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler handler, boolean asyncMode) {

		super(parallelism, defaultForkJoinWorkerThreadFactory, handler, asyncMode);
	}

	public static MdcExecutorService newMdcWorkStealingPool() {
		return new MdcExecutorService(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
	}

	@Override
	public void execute(Runnable task) {
		super.execute(wrap(task, MDC.getCopyOfContextMap()));
	}

	private static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
		return () -> {
			if (context != null) {
				MDC.setContextMap(context);
			}
			try {
				runnable.run();
			} finally {
				MDC.clear();
			}
		};
	}
}
