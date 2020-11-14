package br.com.estudos.servidor;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratadorExcecao implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Exceção na thread " + t.getName() + ", " + e.getMessage());
	}
}
