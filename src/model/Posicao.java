package model;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Posicao extends JButton implements Jogada{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer cordenadaX, cordenadaY; 
	private Character trilha; // Boso deu a ideia de usar a trilha junto com as cordenadas
	private Boolean posOcupada; 
	private Pecinha pecinha;
	
	public Posicao(Integer cordx, Integer cordy, Character trilh) {
		super();
		this.cordenadaX = cordx;
		this.cordenadaY = cordy;
		this.trilha = trilh;
	}

	public void setPecinha(Pecinha pecinha) {
		this.pecinha = pecinha;
		this.setIcon(pecinha.getIcon());
		this.posOcupada = true;
	}
	
	public Pecinha getPecinha() {
		return this.pecinha;
	}

	public Boolean getPosOcupada() {
		return posOcupada;
	}

	public void setPosOcupada(Boolean posOcupada) {
		this.posOcupada = posOcupada;
	}
	
	
	
	public Integer getCordenadaX() {
		return cordenadaX;
	}

	public void setCordenadaX(Integer cordenadaX) {
		this.cordenadaX = cordenadaX;
	}

	public Integer getCordenadaY() {
		return cordenadaY;
	}

	public void setCordenadaY(Integer cordenadaY) {
		this.cordenadaY = cordenadaY;
	}

	public Character getTrilha() {
		return trilha;
	}

	public void setTrilha(Character trilha) {
		this.trilha = trilha;
	}

	public Pecinha removePecinha() {
		Pecinha retorno = pecinha;
		this.pecinha = null;
		this.setIcon(null);
		this.posOcupada = false;
		return retorno;
	}
	
	

}
