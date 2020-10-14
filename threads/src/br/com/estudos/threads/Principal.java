package br.com.estudos.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Principal {

	public static void main(String[] args) throws InterruptedException {
		
		List<String> lista = Collections.synchronizedList(new ArrayList<String>());
		//List<String> lista = new Vector<>();

		for (int i = 0; i < 10; i++) {
			new Thread(new TarefaAdicionaElemento(lista, i)).start();
		} 
		
		Thread.sleep(2000);
		
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(i + " - " + lista.get(i));
		}
	}

}
