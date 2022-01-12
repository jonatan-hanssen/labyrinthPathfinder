import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.geometry.Pos; 
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.Paths;


public class Oblig7 extends Application {
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) {
		FileChooser fileC = new FileChooser();
		
		fileC.setTitle("Velg labyrint");
		fileC.getExtensionFilters().add(
			new FileChooser.ExtensionFilter("IN (*.in)", "*.in"));
		
		
		/*
		 * vi vil gjerne ha mappen som vi programmet er i,
		 * den har sikkert filene
		 */
		String naaverendeMappe = Paths.get(".").
							toAbsolutePath().
							normalize().
							toString();
		fileC.setInitialDirectory(new File(naaverendeMappe));
		
		Labyrint labyrint = null;
		File file = fileC.showOpenDialog(stage);
		try {
			labyrint = Labyrint.lesFraFil(file);
		}
		catch (FileNotFoundException e) {
			System.exit(1);
		}
		
		Rute[][] rutenett = labyrint.hentRutenett();
		
		// alle elementer som skal paa scenen
		
		VBox root = new VBox();
		HBox hbox = new HBox();
		Text tittel = new Text(file.getName().replaceAll(".in",""));
		
		
		double rutenettStoerrelse = 400;
		double ruteStoerrelse = 10;
		
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(rutenettStoerrelse,rutenettStoerrelse);
		gridPane.setAlignment(Pos.CENTER);
	
		Text info = new Text("-/-");
		UtveiViser utveiViser = new UtveiViser(labyrint, gridPane,info);
		
		Button hoyre = new Button(">");
		hoyre.setOnAction(new PilBehandler(utveiViser,1));
		hoyre.getStyleClass().add("pil");
		
		Button venstre = new Button("<");
		venstre.setOnAction(new PilBehandler(utveiViser,-1));
		venstre.getStyleClass().add("pil");
		
		hbox.getChildren().add(venstre);
		hbox.getChildren().add(hoyre);
		
		
		
		// vi setter ruteStoerrelsen til aa vaere basert paa den stoerste
		if (rutenett.length > rutenett[0].length) {
			ruteStoerrelse = rutenettStoerrelse/rutenett.length;
		}
		else {
			ruteStoerrelse = rutenettStoerrelse/rutenett[0].length;
		}
		
		
		// vi lager knapper basert paa rutenettet
		for (int i = 0; i < rutenett.length; i++) {
			//gridPane.getRowConstraints().add(new RowConstraints(ruteStoerrelse));
			for (int j = 0; j < rutenett[i].length; j++) {
				if (rutenett[i][j].tilTegn() == '#') {
					Button button = new Button();
					button.setId(i + "," + j);
					button.setMinSize(1,1);
					button.setPrefSize(ruteStoerrelse,ruteStoerrelse);
					button.getStyleClass().add("button-black");
					gridPane.add(button,j,i);
				}
				else if (rutenett[i][j].tilTegn() == '.') {
					Button button = new Button();
					// vi gir alle knapper en id med koordinater
					button.setId(i + "," + j);
					button.setMinSize(1,1);
					button.setPrefSize(ruteStoerrelse,ruteStoerrelse);
					button.getStyleClass().add("button-white");
					button.setOnAction(
						new RuteTrykkBehandler(i,j,labyrint,gridPane,utveiViser,info));
					gridPane.add(button,j,i);
				}
			}
		}
		
		
		// vi legger alt til scenen
		root.getChildren().add(tittel);
		root.getChildren().add(gridPane);
		root.getChildren().add(info);
		root.getChildren().add(hbox);
		
		// layout
		double vinduStr = 800;
		root.setAlignment(Pos.CENTER);
		root.setPrefSize(vinduStr,vinduStr);
		root.setSpacing(20);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		
		// ferdig
		Scene scene = new Scene(root);
		scene.getStylesheets().add("stylesheet.css");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void clearGridPane(GridPane gridPane) {
		for (Node node : gridPane.getChildren()) {
			if (node.getStyleClass().contains("button-red")) {
				node.getStyleClass().clear();
				node.getStyleClass().add("button-white");
				
			}
		}
	}
	
	public static void visUtvei(Liste<int[]> koordinater, GridPane gridPane) {
		for (int[] tallPar : koordinater) {
			for (Node node : gridPane.getChildren()) {
				
				// vi tar de rutene med id som passer koordinatene og gjoer dem roede
				String[] splitId = node.getId().split(",");
				if (Integer.parseInt(splitId[1]) == tallPar[0] && 
					Integer.parseInt(splitId[0]) == tallPar[1])
				{
					node.getStyleClass().clear();
					node.getStyleClass().add("button-red");
				}
			}
		}	
	}
}
	