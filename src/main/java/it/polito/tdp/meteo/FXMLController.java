/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import it.polito.tdp.meteo.model.Rilevamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
    
	private Model model;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
         txtResult.clear();
         model.trovaSequenza(boxMese.getValue());
         List<Rilevamento> rs;
         rs = model.getMigliore();
         txtResult.appendText("Il prezzo ottimizzato è: "+model.getCostoMigliore()+"\n"+"La sequenza di città è:"+rs.toString());
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
         txtResult.clear();
         txtResult.setText("Umidità media a Genova nel mese di "+Month.of(boxMese.getValue()) +":   "+model.getUmiditaMedia(boxMese.getValue(),"Genova")+"\n"+
        		 "Umidità media a Milano nel mese di "+Month.of(boxMese.getValue()) +":   "+model.getUmiditaMedia(boxMese.getValue(),"Milano")+"\n"+ 
        		 "Umidità media a Torino nel mese di "+Month.of(boxMese.getValue()) +":   "+model.getUmiditaMedia(boxMese.getValue(),"Torino"));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        for(int i=1;i<13;i++) {
        	boxMese.getItems().add(i);
        }
    }
    
    public void setModel(Model model) {
    	this.model=model;
    }
}

