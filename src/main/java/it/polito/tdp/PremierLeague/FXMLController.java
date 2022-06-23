/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Mese;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Mese> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	if(!this.model.grafoCreato()) {
    		txtResult.appendText("ERRORE: Creare prima il grafo!\n");
    		return;
    	}
    	List<Adiacenza> connessioniMax = new ArrayList<>(this.model.getConnessioniMax());
    	txtResult.appendText("COPPIE CON CONNESSIONE MASSIMA: \n");
    	for(Adiacenza a: connessioniMax) {
    		txtResult.appendText(a.getM1() + " - " + a.getM2() + " (" + a.getPeso() +")\n");
    	}
    	
    }

	@FXML
    void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		int min = 0;
		try {
			min = Integer.parseInt(txtMinuti.getText());
		}catch(NumberFormatException e) {
			txtResult.appendText("ERRORE: Inserire un numero valido minimo di minuti giocati!\n");
			return;
		}
		Mese mese = cmbMese.getValue();
		if(mese == null) {
			txtResult.appendText("ERRORE: Selezionare un mese dal menu a tendina!\n");
		}
		this.model.creaGrafo(mese.getNumero(), min);
		btnConnessioneMassima.setDisable(false);
	    btnCollegamento.setDisable(false);
	    cmbM1.getItems().addAll(this.model.getVertici());
	    cmbM2.getItems().addAll(this.model.getVertici());   
	     
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#VERTICI: "+ this.model.nVertici()+"\n");
    	txtResult.appendText("#ARCHI: "+ this.model.nArchi()+"\n");
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	txtResult.clear();
    	Match m1 = cmbM1.getValue();
    	Match m2 = cmbM2.getValue();
    	if(m1 == null || m2 == null) {
    		txtResult.appendText("ERRORE: Selezionare correttamente entrambi i match dai menu a tendina!\n");
    	}
    	List<Match> cammino = new ArrayList<>(this.model.trovaCammino(m1, m2));
    	txtResult.appendText("TROVATO PERCORSO MIGLIORE:\n");
    	        for(Match m: cammino) {
    	        	txtResult.appendText(m.getTeamHomeNAME() + " vs " + m.getTeamAwayNAME() + "\n");
    	        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        btnConnessioneMassima.setDisable(true);
        btnCollegamento.setDisable(true);
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	cmbMese.getItems().add(new Mese(1, "Gennaio"));
    	cmbMese.getItems().add(new Mese(2, "Febbraio"));
    	cmbMese.getItems().add(new Mese(3, "Marzo"));
    	cmbMese.getItems().add(new Mese(4, "Aprile"));
    	cmbMese.getItems().add(new Mese(5, "Maggio"));
    	cmbMese.getItems().add(new Mese(6, "Giugno"));
    	cmbMese.getItems().add(new Mese(7, "Luglio"));
    	cmbMese.getItems().add(new Mese(8, "Agosto"));
    	cmbMese.getItems().add(new Mese(9, "Settembre"));
    	cmbMese.getItems().add(new Mese(10, "Ottobre"));
    	cmbMese.getItems().add(new Mese(11, "Novembre"));
    	cmbMese.getItems().add(new Mese(12, "Dicembre"));
    	
    	
    }
    
    
}
