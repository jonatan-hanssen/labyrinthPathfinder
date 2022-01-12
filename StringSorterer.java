/* 
 * ettersom sortertLenkeliste bare bruker
 * compareTo, og strings sorteres alfabetisk,
 * maatte jeg lage min egen sortering om jeg ville ha
 * utveiene i stigende rekkefoelge
 */

class StringSorterer {
	
	public static void sort(Liste<String> liste) {
		sort(liste,0,liste.stoerrelse()-1);
	}
	/*
	 * vi bruker quicksort: vi velger en verdi,
	 * og legger alle som er stoerre enn den verdien paa en side
	 * og alle som er mindre paa den andre.
	 * Da har vi oppnaad to ting; vi har plassert verdien vi valgte
	 * paa riktig sted og vi har delt listen i to deler vi vet er 
	 * sortert i forhold til hverandre
	 */
	private static void sort(Liste<String> liste,int low, int high) {
		/*  
		 * om avstanden mellom high og low er mindre enn 1 er de
		 * ved siden av hverandre og vi er ferdige.
		 */
		if ((high - low) > 1) {
			/*
			 * vi utfoerer en partisjon, og bruker denne til aa dele
			 * listen i to
			 */
			int pivot = partisjon(liste,low,high);
			
			// deretter sorterer vi de to nye delene rekursivt
			sort(liste,low,pivot-1);
			sort(liste,pivot+1,high);
		}
	}
	
	// hjelpefunksjon
	private static void swap(Liste<String> liste, int a, int b) {
		String lagret = liste.hent(a);
		liste.sett(a, liste.hent(b));
		liste.sett(b, lagret);
	}
	
	
	private static int partisjon(Liste<String> liste, int low, int high) {
		// foerste element er vaar pivot
		
		int i = low + 1;
		/*
		 * denne for loekka gaar gjennom alle elementene og flytter dem
		 * til venstre dersom de er mindre en pivoten (liste.hent(low)).
		 * Denne flyttinga gjoer ogsaa at vi flytter en som er stoerre
		 * en pivoten over til hoyre
		 * Naar vi flytter oeker vi i, som kan sees paa som en grense mellom
		 * de hoye og de lave tallene.
		 * Tilslutt putter vi pivoten i midten, og vi returnerer posisjonen
		 * til pivoten
		 */
		for (int j = i; j <= high; j++) {
			if (liste.hent(j).length() < liste.hent(low).length()) {
				swap(liste,j,i);
				i++;
			}
		}
		swap(liste, low, i-1);
		return i-1;
	}
}