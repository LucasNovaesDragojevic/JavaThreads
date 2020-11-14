package br.com.estudos.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class JuntaResultadosFutureC2 implements Callable<Void> {

	private Future<String> futureWS;
	private Future<String> futureBD;
	private PrintStream saidaCliente;

	public JuntaResultadosFutureC2(Future<String> futureWS, Future<String> futureBD, PrintStream saidaCliente) {
		this.futureWS = futureWS;
		this.futureBD = futureBD;
		this.saidaCliente = saidaCliente;
	}

	@Override
	public Void call() {
		try {
			String numeroMagico = futureWS.get(15, TimeUnit.SECONDS);
			String numeroMagico2 = futureBD.get(15, TimeUnit.SECONDS);
			saidaCliente.println("Resultado comando C2: " + numeroMagico + " - " + numeroMagico2);
		} catch (Exception e) {
			saidaCliente.println("Timeout comando C2");
			futureWS.cancel(true);
			futureBD.cancel(true);
		}
		return null;
	}

}
