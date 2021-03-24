package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary model;
	
	ObservableList<String> Language = FXCollections.observableArrayList("English", "Italian");
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private ChoiceBox<String> choiceLanguage;
    
    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Label lblErrorNumber;

    @FXML
    private Label lblSpellTime;
    
    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	List<String> Parole = new ArrayList<>();
    	
    	String input = txtInput.getText();
    	
    	if(input.equals("")) {
    		
    		txtOutput.setText("Devi inserire delle parole!");
    		return;
    		
    	}
    	
    	String language = choiceLanguage.getValue();
    	
    	model.loadDictionary(language);
    	
    	input.toLowerCase();
    	input.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	
    	String Campi[] = input.split(" ");
    	
    	for(String s : Campi) {
    		Parole.add(s);
    	}
    	
    	long in = System.nanoTime();
    	//model.spellCheckTextLinear(Parole);
    	model.spellCheckTextDicotomic(Parole);
    	long fin = System.nanoTime();
    	
    	long dif = fin-in;
    	
    	txtOutput.setText(model.getParoleSbagliate());
    	lblErrorNumber.setText("The text contains: "+String.valueOf(model.getNumeroErrori())+" errors.");
    	lblSpellTime.setText("Spell check completed in "+String.valueOf((double)dif/10E9)+" second.");
    	
    	txtInput.clear();
    	
    }
    
    @FXML
    void doClearText(ActionEvent event) {
    	
    	txtInput.clear();
    	txtOutput.clear();
    	lblErrorNumber.setText("Error number");
    	lblSpellTime.setText("Spell time");
    	choiceLanguage.setValue(null);

    }
    
    public void setModel(Dictionary Dizionario) {
    	this.model = Dizionario;
    }
    
    @FXML
    void initialize() {
    	
    	assert choiceLanguage != null : "fx:id=\"choiceLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrorNumber != null : "fx:id=\"lblErrorNumber\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblSpellTime != null : "fx:id=\"lblSpellTime\" was not injected: check your FXML file 'Scene.fxml'.";
        
        choiceLanguage.setItems(Language);

    }
}


