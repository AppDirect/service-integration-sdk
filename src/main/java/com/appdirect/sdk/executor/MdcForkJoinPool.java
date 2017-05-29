package com.appdirect.sdk.executor;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.MDC;

public class MdcForkJoinPool extends ForkJoinPool {

	private MdcForkJoinPool(int parallelism, ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler handler, boolean asyncMode) {

		super(parallelism, defaultForkJoinWorkerThreadFactory, handler, asyncMode);
	}

	public static MdcForkJoinPool newMdcWorkStealingPool() {
		return new MdcForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
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
