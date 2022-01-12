import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;


// denne klassen brukes for aa vise neste utvei
class UtveiViser {
	private Liste<String> utveiListe;
	private GridPane gridPane;
	private int pos = 0;
	private Labyrint labyrint;
	private Text info;
	
	public UtveiViser(Labyrint labyrint, GridPane gridPane, Text info) {
		this.labyrint = labyrint;
		this.gridPane = gridPane;
		this.info = info;
	}
	
	public void nesteUtvei(int increment) {
		
		this.utveiListe = labyrint.hentUtveiListe();
		
		
		// om det ikke er noen utveier skjer ingenting
		if (utveiListe.stoerrelse() == 0) return;
		
		// vi renser rutenettet
		Oblig7.clearGridPane(gridPane);
		pos += increment;
		
		/*
		 * om vi har kommet til siste utvei og trykker neste 
		 * gaar vi tilbake til foerste, og omvendt
		 */
		if (pos < 0) pos = utveiListe.stoerrelse()-1;
		if (pos >= utveiListe.stoerrelse()) pos = 0;
		
		Liste<int[]> koordinater = 
			labyrint.hentKoordinater(utveiListe.hent(pos));
			
		// her gjoer vi bare det samme som 
		Oblig7.visUtvei(koordinater, gridPane);
		
		info.setText((pos+1) + "/" + labyrint.hentAntallUtveier());
		
	}
	public void resetPos() {pos = 0;}
}