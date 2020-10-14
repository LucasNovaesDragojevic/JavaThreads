package br.com.estudos.threads;

public class TesteVerIdThread {

	public static void main(String[] args) {
		new Thread(new TarefaImprimeNumeros()).start();
		new Thread(new TarefaImprimeNumeros()).start();
	}
}
