package br.com.estudos.cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("Conexão estabelecida");
		
		Thread recebe = new Thread(() -> {
			try {
				Scanner respostaServidor = new Scanner(socket.getInputStream());
				while (respostaServidor.hasNextLine())
					System.out.println(respostaServidor.nextLine());
				respostaServidor.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		Thread envia = new Thread(() -> {
			try {
				PrintStream saida = new PrintStream(socket.getOutputStream());
				Scanner teclado = new Scanner(System.in);
				while (teclado.hasNextLine()) {
					String linha = teclado.nextLine();
					if (linha.trim().equals(""))
						break;
					saida.println(linha);
				}
				teclado.close();
				saida.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		recebe.start();
		envia.start();
		envia.join();
		socket.close();
	}
}