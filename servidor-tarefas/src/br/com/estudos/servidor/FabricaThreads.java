package br.com.estudos.servidor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class FabricaThreads implements ThreadFactory {

	private static Integer numero = 1;

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, "Thread-Servidor-Tarefas-" + numero);
		numero++;
		thread.setUncaughtExceptionHandler(new TratadorExcecao());
		return thread;
	}

}
