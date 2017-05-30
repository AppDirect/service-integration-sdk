package com.appdirect.sdk.executor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.MDC;

@RunWith(MockitoJUnitRunner.class)
public class MdcExecutorTest {

	@Test
	public void MdcContextShouldPropagateToUnderlyingThread() throws Exception {
		//Given
		MdcExecutor mdcExecutor = new MdcExecutor(new ForkJoinPool());
		Map<String, String> mdcContext = aDummyMdcContext();
		CustomRunnable task = new CustomRunnable();

		//When
		setMdcContext(mdcContext);
		mdcExecutor.submit(task).get();

		//Then
		assertThat(task.getLocalMdcContext()).isEqualTo(mdcContext);
	}

	private void setMdcContext(Map<String, String> stringStringMap) {
		MDC.setContextMap(stringStringMap);
	}

	private Map<String, String> aDummyMdcContext() {
		Map<String, String> mdcContext = new HashMap<>();

		mdcContext.put("key1", "val1");
		mdcContext.put("key2", "val2");

		return mdcContext;
	}

	class CustomRunnable implements Runnable {

		private Map<String, String> localMdcContext;

		@Override
		public void run() {
			this.localMdcContext = MDC.getCopyOfContextMap();
		}

		private Map<String, String> getLocalMdcContext() {
			return localMdcContext;
		}
	}
}
