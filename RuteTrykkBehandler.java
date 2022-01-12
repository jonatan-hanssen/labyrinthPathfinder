import javafx.event.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.text.*;

class RuteTrykkBehandler implements EventHandler<ActionEvent> {
	private int y;
	private int x;
	private Labyrint labyrint;
	private GridPane gridPane;
	private UtveiViser utveiViser;
	private Text info;
	
	public RuteTrykkBehandler(
		int y, int x, Labyrint labyrint, GridPane gridPane, 
		UtveiViser utveiViser, Text info) {
		this.y = y;
		this.x = x;
		this.labyrint = labyrint;
		this.gridPane = gridPane;
		this.utveiViser = utveiViser;
		this.info = info;
	}
	
	@Override
	public void handle(ActionEvent e) {
		utveiViser.resetPos();
		Oblig7.clearGridPane(gridPane);
		// vi finner utvei med labyrinten
		labyrint.finnUtveiFra(x,y);
		if (labyrint.hentAntallUtveier() == 0) {
			info.setText("-/-");
			return;
		}
		// vi sorterer dem slik at den korteste veien er foerst
		labyrint.sorterUtveier();
		
		info.setText("1/" + labyrint.hentAntallUtveier());
		
		Liste<int[]> koordinater = 
			labyrint.hentKoordinater(labyrint.hentUtveiListe().hent(0));
			
		Oblig7.visUtvei(koordinater, gridPane);
	}
}