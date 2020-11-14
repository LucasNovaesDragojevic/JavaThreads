package br.com.estudos.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	private ServerSocket servidor;
	private ExecutorService threadPool;
	private AtomicBoolean estaRodando;
	private BlockingQueue<String> filaComandos;
	
	public ServidorTarefas() throws Exception {
		System.out.println("Servidor iniciado");
		servidor = new ServerSocket(12345);
		threadPool = Executors.newCachedThreadPool(new FabricaThreads());
		estaRodando = new AtomicBoolean(true);
		filaComandos = new ArrayBlockingQueue<>(2);
		iniciarConsumidores();
	}

	private void iniciarConsumidores() {
		for (int i = 0; i < 2; i++) {
			TarefaConsumir tarefaConsumir = new TarefaConsumir(filaComandos);
			threadPool.execute(tarefaConsumir);
		}
	}

	public void rodar() throws Exception {
		while (estaRodando.get()) {
			try {
				Socket socket = servidor.accept();
				System.out.println("Cliente aceito na porta: " + socket.getPort());
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool, filaComandos, socket, this);
				threadPool.execute(distribuirTarefas);
			} catch (SocketException e) {
				System.out.println("SocketException, está rodando? " + estaRodando);
			}
		}
	}
	
	public void parar() throws Exception {
		estaRodando.set(false);
		threadPool.shutdown();
		servidor.close();
	}
	
	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
	}
}
