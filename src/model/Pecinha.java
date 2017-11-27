package model;

import javax.swing.ImageIcon;

import br.ufsc.inf.leobr.cliente.Jogada;


public class Pecinha implements Jogada{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cor;
	private Boolean posF6;
	private ImageIcon icon;
	
	public Pecinha(String cor) {
		this.cor = cor;
		this.posF6 = false;
		this.icon = new ImageIcon("");
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Boolean getPosF6() {
		return posF6;
	}

	public void setPosF6(Boolean posF6) {
		this.posF6 = posF6;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
}
