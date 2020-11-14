package br.com.estudos.servidor;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {

	private PrintStream saidaCliente;

	public ComandoC1(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;
	}

	@Override
	public void run() {
		System.out.println("Executando comando C1");
		try {
			Thread.sleep(20000);
		} catch (Exception e) {
		 	throw new RuntimeException(e);
		}
		saidaCliente.println("Comando C1 executado");
	}

}
