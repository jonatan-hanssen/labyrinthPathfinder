abstract class Rute {
	protected int y;
	protected int x;
	protected Labyrint hjem;
	protected Rute[] naboer = new Rute[4];
	/*	
	 * En rutes naboer representeres av arrayet slik:
	 *			1
	 *		  2 R 0
	 *			3
	 */
	public Rute(int y, int x, Labyrint hjem) {
		this.y = y;
		this.x = x;
		this.hjem = hjem;
		
	}
	
	public void gaa(String vei) {
		vei += hentKoordinater() + "-->";


		/* 
		 * foerst saa sjekker vi hvor mange ruter som gir gyldige veier,
		 * det kan paa det meste vaere 4
		 */
		Rute[] muligeVeier = new Rute[4];
		int veierFunnet = 0;
		for (int i = 0; i < naboer.length; i++) {
			/* 
			 * vi trenger vel strengt tatt ikke aa sjekke om naboer er null
			 * siden bare aapninger har en nabo som er null, men det er
			 * best aa vaere paa den sikre siden
			 */
			if (naboer[i] != null 
			&& !vei.contains(naboer[i].hentKoordinater())
			&& !(naboer[i] instanceof SortRute)) {
				muligeVeier[veierFunnet] = naboer[i];
				veierFunnet++;
			}
		}
		
		
		/* 
		 * siden det bare er fem muligheter (0,1,2,3,4) er det nok mer effektivt
		 * aa hardkode disse mulighetene enn aa lage kode som tar hoyde for ulike muligheter
		 */
		if (veierFunnet == 0) { /*ingenting skjer*/ }
		else if (veierFunnet == 1) muligeVeier[0].gaa(vei);
		else if (veierFunnet == 2) {
			Thread traad = new Thread(new Loeper(muligeVeier[0],vei));
			traad.start();
			/* 
			 * vi maa starte traaden foer vi starter vaar egen gaa(),
			 * ettersom gaa() vil starte en lang rekke med rekursjon som 
			 * vil gaa videre i labyrinten. Kun naar gaa() har kommet til 
			 * en aapning eller en blindvei vil vi fortsette koden,
			 * og dersom vi hadde starta en ny traad etter gaa ville det ikke blitt
			 * noen parallellisering
			 */
			muligeVeier[1].gaa(vei);
			
			try {
				/* 
				 * dette betyr at vi venter paa at den andre traaden blir ferdig
				 * og dersom den traaden ogsaa venter paa noe faar vi rekursiv venting
				 * saann soerger vi for at vi er ferdig med alle traader foer vi gaar videre
				 */
				traad.join();
			}
			catch (InterruptedException e) {
				System.exit(1);
			}
		}
		else if (veierFunnet == 3) {
			Thread traad1 = new Thread(new Loeper(muligeVeier[0],vei));
			traad1.start();
			
			Thread traad2 = new Thread(new Loeper(muligeVeier[1],vei));
			traad2.start();
			
			muligeVeier[2].gaa(vei);
			
			try {
				traad1.join();
				traad2.join();
			}
			catch (InterruptedException e) {
				System.exit(1);
			}
		}
		else if (veierFunnet == 4) {
			Thread traad1 = new Thread(new Loeper(muligeVeier[0],vei));
			traad1.start();
			
			Thread traad2 = new Thread(new Loeper(muligeVeier[1],vei));
			traad2.start();
			
			Thread traad3 = new Thread(new Loeper(muligeVeier[2],vei));
			traad3.start();
			
			muligeVeier[3].gaa(vei);
			
			try {
				traad1.join();
				traad2.join();
				traad3.join();
			}
			catch (InterruptedException e) {
				System.exit(1);
			}
		}
	}
	
	public void nyNabo(int indeks, Rute nyNabo) {
		naboer[indeks] = nyNabo;
	}
	
	public String hentKoordinater() {
		return "(" + x + "," + y + ")";
	}
	
	abstract char tilTegn();
	
	@Override
	public String toString() {
		String naboString = "";
		if (naboer[0] != null) naboString += "\nNabo til hoyre: " + naboer[0].tilTegn();
		else naboString += "\nIngen nabo til hoyre.";
		
		if (naboer[1] != null) naboString += "\nNabo over: " + naboer[1].tilTegn();
		else naboString += "\nIngen nabo over.";
		
		if (naboer[2] != null) naboString += "\nNabo til venstre: " + naboer[2].tilTegn();
		else naboString += "\nIngen nabo til venstre.";
		
		if (naboer[3] != null) naboString += "\nNabo under: " + naboer[3].tilTegn();
		else naboString += "\nIngen nabo under.";
		
		return "Rute ved y = " + y + " x = " + x + " med representasjon " + tilTegn() + naboString;
	}
}