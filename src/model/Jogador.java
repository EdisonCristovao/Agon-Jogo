package model;

import java.util.ArrayList;
import java.util.List;

public class Jogador {

	private String nome;
	private Boolean vez;
	private List<Rainha> rainhas;
	private List<Guerreiro> guerreiros;
	private String cor;
	
	public Jogador(String nome, String cor) {
		this.nome = nome;
		this.vez = false;
		this.cor = cor;
		rainhas = criaRainhas();
		guerreiros = criaGuerreiros();
		
		
	}

	private List<Guerreiro> criaGuerreiros() {
		List<Guerreiro> retorno = new ArrayList<Guerreiro>();;
		for (int i = 0; i < 8; i++) {
			Guerreiro g1 = new Guerreiro(this.cor);
			retorno.add(g1);	
		}
		return retorno;
	}

	private List<Rainha> criaRainhas() {
		List<Rainha> retorno = new ArrayList<Rainha>();
		Rainha r1 = new Rainha(this.cor);
		retorno.add(r1);
		
		return retorno;
	}

	public void tomaVez() {
		this.vez = true;
	}
	
	public void passaVez() {
		this.vez = false;
	}

	public String getNome() {
		return nome;
	}
	
	public String getCor() {
		return cor;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rainha> getRainhas() {
		return rainhas;
	}

	public List<Guerreiro> getGuerreiros() {
		return guerreiros;
	}

	
	
	public Boolean getVez() {
		return vez;
	}

	public void setVez(Boolean vez) {
		this.vez = vez;
	}

	public Guerreiro getUmGuerreiro() {
		if (guerreiros.size() == 0) {
			return null;
		}
		Guerreiro retorno = guerreiros.get(0);
		guerreiros.remove(0);
		return retorno;
	}
	
	public Rainha getUmRainha() {
		if (rainhas.size() == 0) {
			return null;
		}
		Rainha retorno = rainhas.get(0);
		rainhas.remove(0);
		return retorno;
	}
	
	
	
	

}
