package br.com.estudos.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> {

	private PrintStream saidaCliente;

	public ComandoC2ChamaWS(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Executando comando C2 - WS");
		Thread.sleep(10);
		Integer numero = new Random().nextInt(100) + 1;
		return String.valueOf(numero);
	}

}
