package br.com.estudos.banheiro;

public class Banheiro {
	
	private Boolean ehSujo = true;
	
	public synchronized void fazNumero1() { 
		String nome = Thread.currentThread().getName();
		
        System.out.println(nome + " entrando no banheiro");
        
        veSeOBanheiroEstaSujo(nome);
        
        System.out.println(nome + " fazendo coisa rapida");

        dormeThread(8000);
        
        ehSujo = true;
        
        System.out.println(nome + " dando descarga");
        System.out.println(nome + " lavando a mao");
        System.out.println(nome + " saindo do banheiro");
    }

    public synchronized void fazNumero2() {
    	String nome = Thread.currentThread().getName();  
    	
        System.out.println(nome + " entrando no banheiro");
        
        veSeOBanheiroEstaSujo(nome);
        
        System.out.println(nome + " fazendo coisa demorada");

        dormeThread(15000);
        
        ehSujo = true;

        System.out.println(nome + " dando descarga");
        System.out.println(nome + " lavando a mao");
        System.out.println(nome + " saindo do banheiro");
    }

	private void dormeThread(Integer segundos) {
		try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
    
	private void veSeOBanheiroEstaSujo(String nomeThread) {
		if (ehSujo) {
        	System.out.println(nomeThread + " eca, banheiro sujo");
        	try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
	
	public void limpa() {		
		String nome = Thread.currentThread().getName();

		synchronized (this) {
	        System.out.println(nome + " entrando no banheiro");     
	        
	        if (!ehSujo) {
	        	System.out.println(nome + " o banheiro não está sujo");
	        	return;
	        }
	        
	        System.out.println(nome + " limpando o banheiro");
	        
	        ehSujo = false;
	        
	        try {
	            Thread.sleep(10000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
	        this.notifyAll();
	        
	        System.out.println(nome + " saindo do banheiro");
		}
	}
}