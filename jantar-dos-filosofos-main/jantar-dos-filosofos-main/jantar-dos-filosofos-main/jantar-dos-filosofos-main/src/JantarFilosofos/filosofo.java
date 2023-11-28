package JantarFilosofos;

public class filosofo extends Thread {

	private int filosofo;
	private int estado;
	private Jantar jantar;

	public filosofo(int chave, Jantar j) {
		this.filosofo = chave;
		this.jantar = j;
	}

	public int getChave() {
		return filosofo;
	}

	public void setStatus(int i) {
		estado = i;
		switch (i) {
			case 0:
				jantar.SetInfo(filosofo, 0);
				break;
			case 1:
				jantar.SetInfo(filosofo, 1);
				break;
			case 2:
				jantar.SetInfo(filosofo, 2);
				break;
		}
	}

	private void pensando() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}

	private void comendo() {
		try {
			Thread.sleep(2500);
		} catch (Exception e) {
		}
	}

	private long totalEspera = 0;
	private long maxEspera = 0;
	private int vezesComeu = 0;

	public void run() {
		while (true) {
			setStatus(0);
			long inicioEspera = System.currentTimeMillis();// Marca o inicio da espera
			pensando();
			long tempoEspera = System.currentTimeMillis() - inicioEspera; // tempo de espera
			totalEspera += tempoEspera;
			if (tempoEspera > maxEspera)
				maxEspera = tempoEspera;

			jantar.fork.pegar(this);
			setStatus(1);// Filosofo comecou a comer
			comendo();
			setStatus(0);// terminou de comer
			jantar.fork.liberar(this);
			vezesComeu++;

			// Imprimindo as informações
			System.out.println("Filósofo " + getChave() + ":");
			System.out.println("  - Tempo sem comer: " + tempoEspera + " ms");
			System.out.println("  - Número de vezes que comeu: " + vezesComeu);
			System.out.println("  - Média de espera: " + (totalEspera / vezesComeu) + " ms");
			System.out.println("  - Máximo de espera: " + maxEspera + " ms");
		}
	}
}
