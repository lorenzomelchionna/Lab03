package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	
	List<String> Dizionario;
	List<RichWord> ParoleControllate;
	String paroleSbagliate = "";
	int numeroErrori = 0;
	
	public Dictionary() {
		Dizionario = new ArrayList<>();
		ParoleControllate = new ArrayList<>();
	}
	
	public String getParoleSbagliate() {
		return this.paroleSbagliate;
	}
	
	public int getNumeroErrori() {
		return this.numeroErrori;
	}

	public void loadDictionary(String language) {
		
		try {
			
			FileReader fr = new FileReader("src/main/resources/"+language+".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			
			while ((word = br.readLine()) != null) 
				Dizionario.add(word);
			
			br.close();
			
		} catch (IOException e){
			System.out.println("Errore nella lettura del file");
		}
			
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		
		boolean corrispondenza;
		
		for(String i : inputTextList) {
			
			corrispondenza = false;
			
			for(String d : Dizionario) 
				if(i.equals(d)) {
					corrispondenza = true;
					break;
				}
			
			ParoleControllate.add(new RichWord(i,corrispondenza));
		
		}
		
		for(RichWord r : ParoleControllate)
			if(r.corretta == false) {
				
				paroleSbagliate += r.parola+"\n";
				numeroErrori++;
				
			}
		
		return ParoleControllate;
		
	}
	
}
