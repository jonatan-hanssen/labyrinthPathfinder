class Loeper implements Runnable {
	
	Rute rute;
	String vei;
	
	public Loeper(Rute rute, String vei) {
		this.rute = rute;
		this.vei = vei;
	}
	
	public void run() {
		rute.gaa(vei);
	}
}