package com.appdirect.sdk.executor;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.MDC;

@RunWith(MockitoJUnitRunner.class)
public class MdcExecutorTest {

	@Spy
	private ExecutorService underlying = new ForkJoinPool();
	@InjectMocks
	private MdcExecutor mdcExecutor = new MdcExecutor(underlying);

	private Map<String, String> mdcContext = aDummyMdcContext();

	@Test
	public void shouldDelegateShutdownToUnderlying() throws Exception {
		//When
		mdcExecutor.shutdown();

		//Then
		verify(underlying).shutdown();
	}

	@Test
	public void shouldDelegateShutdownNowToUnderlying() throws Exception {
		//When
		mdcExecutor.shutdownNow();

		//Then
		verify(underlying).shutdownNow();
	}

	@Test
	public void shouldDelegateIsShutdownToUnderlying() throws Exception {
		//When
		boolean shutdown = mdcExecutor.isShutdown();

		//Then
		verify(underlying).isShutdown();
		assertThat(shutdown).isEqualTo(underlying.isShutdown());
	}

	@Test
	public void shouldDelegateIsTerminatedToUnderlying() throws Exception {
		//When
		boolean terminated = mdcExecutor.isTerminated();

		//Then
		verify(underlying).isTerminated();
		assertThat(terminated).isEqualTo(underlying.isTerminated());
	}

	@Test
	public void shouldDelegateAwaitingTerminationToUnderlying() throws Exception {
		//Given
		long timeout = 1L;
		TimeUnit timeUnit = TimeUnit.NANOSECONDS;

		//When
		mdcExecutor.awaitTermination(timeout, timeUnit);

		//Then
		verify(underlying).awaitTermination(timeout, timeUnit);
	}

	@Test
	public void shouldDelegateSubmitCallableToUnderlying() throws Exception {
		//Given
		CustomCallable callable = new CustomCallable();

		//When
		setMdcContext(mdcContext);
		mdcExecutor.submit(callable).get();

		//Then
		assertThat(callable.getLocalMdcContext()).isEqualTo(mdcContext);
	}

	@Test
	public void shouldDelegateSubmitRunnableWithResultToUnderlying() throws Exception {
		//Given
		CustomRunnable task = new CustomRunnable();

		// When
		setMdcContext(mdcContext);
		mdcExecutor.submit(task,"ok").get();

		//Then
		assertThat(task.getLocalMdcContext()).isEqualTo(mdcContext);
	}

	@Test
	public void shouldDelegateSubmitRunnableToUnderlying() throws Exception {
		//Given
		CustomRunnable task = new CustomRunnable();

		// When
		setMdcContext(mdcContext);
		mdcExecutor.submit(task).get();

		//Then
		assertThat(task.getLocalMdcContext()).isEqualTo(mdcContext);
	}

	@Test
	public void shouldDelegateInvokeAllCallableToUnderlying() throws Exception {
		//Given
		CustomCallable callable1 = new CustomCallable();
		CustomCallable callable2 = new CustomCallable();


		//When
		setMdcContext(mdcContext);
		mdcExecutor.invokeAll(asList(callable1, callable2)).get(1);

		//Then
		assertThat(callable1.getLocalMdcContext()).isEqualTo(mdcContext);
		assertThat(callable2.getLocalMdcContext()).isEqualTo(mdcContext);
	}

	@Test
	public void shouldDelegateInvokeAllCallableWithUnitToUnderlying() throws Exception {
		//Given
		CustomCallable callable1 = new CustomCallable();
		CustomCallable callable2 = new CustomCallable();


		//When
		setMdcContext(mdcContext);
		mdcExecutor.invokeAll(asList(callable1, callable2), 1L, TimeUnit.SECONDS).get(1);

		//Then
		assertThat(callable1.getLocalMdcContext()).isEqualTo(mdcContext);
		assertThat(callable2.getLocalMdcContext()).isEqualTo(mdcContext);
	}

	@Test
	public void shouldDelegateInvokeAnyCallableToUnderlying() throws Exception {
		//Given
		CustomCallable callable1 = new CustomCallable();
		CustomCallable callable2 = new CustomCallable();


		//When
		setMdcContext(mdcContext);
		Map<String, String> invokeAny = mdcExecutor.invokeAny(asList(callable1, callable2));

		//Then
		assertThat(invokeAny).isEqualTo(mdcContext);
	}

	@Test
	public void shouldDelegateInvokeAnyCallableWithTimeoutToUnderlying() throws Exception {
		//Given
		CustomCallable callable1 = new CustomCallable();
		CustomCallable callable2 = new CustomCallable();


		//When
		setMdcContext(mdcContext);
		Map<String, String> invokeAny = mdcExecutor.invokeAny(asList(callable1, callable2), 1L, TimeUnit.SECONDS);

		//Then
		assertThat(invokeAny).isEqualTo(mdcContext);
	}

	@Test
	public void shouldDelegateExecuteToUnderlying() throws Exception {
		//Given
		CustomRunnable task = new CustomRunnable();

		// When
		setMdcContext(mdcContext);
		mdcExecutor.execute(task);
		waitForExecutionToFinish();

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

	private void waitForExecutionToFinish() throws InterruptedException {
		mdcExecutor.shutdown();
		mdcExecutor.awaitTermination(2, TimeUnit.SECONDS);
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

	class CustomCallable implements Callable<Map<String, String>> {

		private Map<String, String> localMdcContext;

		@Override
		public Map<String, String> call() throws Exception {
			this.localMdcContext = MDC.getCopyOfContextMap();
			return localMdcContext;
		}

		private Map<String, String> getLocalMdcContext() {
			return localMdcContext;
		}
	}
}
