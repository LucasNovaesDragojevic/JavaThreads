package br.com.estudos.banco;

public class TarefaAcessoBancoProcedimento implements Runnable {

	private PoolDeConexao pool;
	private GerenciadorDeTransacao tx;

	public TarefaAcessoBancoProcedimento(PoolDeConexao pool, GerenciadorDeTransacao tx) {
		this.pool = pool;
		this.tx = tx;
	}

	@Override
	public void run() {
		synchronized (pool) {
			System.out.println("peguei a chave do pool");
			pool.getConnection();
			
			synchronized (tx) {
				System.out.println("comecando a gerenciar a tx");
				tx.begin();
			}
		}
	}

}
