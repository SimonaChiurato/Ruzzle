package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {

	public List<Pos> trovaParola(String parola, Board board) {
		for (Pos p : board.getPositions()) {
			if (board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0)) {
				// inizio potenziale della parola
				List<Pos> percorso = new ArrayList<>();
				percorso.add(p);
				if (this.cerca(parola, 1, percorso, board)) {
					return percorso;
				}

			}
		}
		return null;
	}

	private boolean cerca(String parola, int livello, List<Pos> percorso, Board board) {
		// caso terminale
		if (livello == parola.length()) {
			return true;
		}

		Pos ultima = percorso.get(percorso.size() - 1);
		List<Pos> adiacenti = board.getAdjacencies(ultima);
		for (Pos p : adiacenti) {
			if (!percorso.contains(p) && parola.charAt(livello) == board.getCellValueProperty(p).get().charAt(0)) {
				percorso.add(p);
				if (cerca(parola, livello + 1, percorso, board)) {
					return true;
				}
				percorso.remove(percorso.size() - 1);
			}
		}
		return false;
	}

}
