import javafx.event.*;

class PilBehandler implements EventHandler<ActionEvent> {
	UtveiViser utveiViser;
	int increment;
	
	public PilBehandler(UtveiViser utveiViser, int increment) {
		this.utveiViser = utveiViser;
		this.increment = increment;
	}
	
	@Override
	public void handle(ActionEvent e) {
		utveiViser.nesteUtvei(increment);
	}
}