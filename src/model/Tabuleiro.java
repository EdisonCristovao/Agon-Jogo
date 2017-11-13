package model;

import java.util.List;

public class Tabuleiro {
	
	private List<Posicao> posicoes;
	
	public Tabuleiro(List<Posicao> posicoes) {
		this.posicoes = posicoes;
	}

	public List<Posicao> getPosicoes() {
		return posicoes;
	}

	public void setPosicoes(List<Posicao> posicoes) {
		this.posicoes = posicoes;
	}
	
	

}
