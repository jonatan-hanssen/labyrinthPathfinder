import java.util.concurrent.locks.*;
class UtveiMonitor {
	
	private Lock utveiLaas = new ReentrantLock();
	
	private Liste<String> utveiListe = new Lenkeliste<String>();
	
	
	public void leggTil(String utvei) {
		utveiLaas.lock();
		try {
			utveiListe.leggTil(utvei);
		}
		finally {
			utveiLaas.unlock();
		}
	}
	
	public void reset() {
		utveiListe = new Lenkeliste<String>();
	}
	
	public Liste<String> hentUtveier() {
		return utveiListe;
	}
}