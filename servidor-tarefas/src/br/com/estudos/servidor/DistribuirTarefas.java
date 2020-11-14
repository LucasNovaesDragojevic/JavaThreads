package br.com.estudos.servidor;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService threadPool;
	private BlockingQueue<String> filaComandos;

	public DistribuirTarefas(ExecutorService threadPool, BlockingQueue<String> filaComandos, Socket socket, ServidorTarefas servidor) {
		this.threadPool = threadPool;
		this.filaComandos = filaComandos;
		this.socket = socket;
		this.servidor = servidor;
	}

	@Override
	public void run() {
		System.out.println("Tarefa distribuida para " + socket);
		try {
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			while (entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println(comando);
				switch (comando) {
					case "c1" : {
						saidaCliente.println("Confirmado comando c1");
						ComandoC1 c1 = new ComandoC1(saidaCliente);
						threadPool.execute(c1);
						break;
					}
					case "c2" : {
						saidaCliente.println("Confirmado comando c2");
						ComandoC2ChamaWS c2ChamaWS = new ComandoC2ChamaWS(saidaCliente);
						ComandoC2AcessaBanco c2AcessaBanco = new ComandoC2AcessaBanco(saidaCliente);
						Future<String> futureWS = threadPool.submit(c2ChamaWS);
						Future<String> futureBD = threadPool.submit(c2AcessaBanco);
						threadPool.submit(new JuntaResultadosFutureC2(futureWS, futureBD, saidaCliente));
						break;
					}
					case "c3" : {
						filaComandos.put(comando);
						saidaCliente.println("Confirmando comando C3");
						break;
					}
					case "fim" : {
						saidaCliente.println("Desligando servidor");
						servidor.parar();
						return;
					}
					default: {
						saidaCliente.println("Comando desconhecido");
					}
				}
			}
			entradaCliente.close();
			saidaCliente.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
