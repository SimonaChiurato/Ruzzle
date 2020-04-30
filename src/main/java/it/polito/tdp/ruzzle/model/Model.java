package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

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

	public void reset() {
		this.board.reset();
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
