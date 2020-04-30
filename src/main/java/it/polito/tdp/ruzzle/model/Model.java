package it.polito.tdp.ruzzle.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import it.polito.tdp.ruzzle.db.DizionarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	private final int SIZE = 4;
	private Board board;
	private List<String> dizionario;
	private StringProperty statusText;

	public Model() {
		this.statusText = new SimpleStringProperty();

		this.board = new Board(SIZE);
		DizionarioDAO dao = new DizionarioDAO();
		this.dizionario = dao.listParola();
		statusText.set(String.format("%d parole lette", this.dizionario.size()));

	}

	public List<Pos> trovaParola(String parola) {
		Ricerca r = new Ricerca();
		return r.trovaParola(parola, board);
	}

	public void reset() throws FileNotFoundException {
		Map<Character, Double> frequenze= new TreeMap<>();
		
	FileReader fr = new FileReader("FrequenzaAlfabeto");
	BufferedReader br = new BufferedReader(fr);
	String riga;

	try {
		while ((riga = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(riga, " ");
			String s1= st.nextToken();
			
			Character c= s1 .charAt(0);
			
			String s2= st.nextToken();
		    double freq= Double.parseDouble(s2);
			frequenze.put(c,freq);
		}
		fr.close();
	} catch (Exception e) {

}

		this.board.reset(frequenze);
		this.statusText.set("Board Reset");
	}

	public Board getBoard() {
		return this.board;
	}

	public final StringProperty statusTextProperty() {
		return this.statusText;
	}

	public final String getStatusText() {
		return this.statusTextProperty().get();
	}

	public final void setStatusText(final String statusText) {
		this.statusTextProperty().set(statusText);
	}

	public List<String> trovaTutte() {
		List<String> tutte = new ArrayList<>();
		for (String s : this.dizionario) {
			s = s.toUpperCase();
			if (s.length() > 1) {
				if (this.trovaParola(s) != null) {
					tutte.add(s);
				}
			}
		}
		return tutte;
	}

}
