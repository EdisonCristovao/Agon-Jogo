package model;

import javax.swing.ImageIcon;

import control.Principal;

public class Rainha extends Pecinha {
	
	public Rainha(String cor) {
		super(cor);
		this.setPosF6(true);
		criaIcon();
	}
	
	public void criaIcon() {
		if (this.getCor().equals("preto")) {
			this.setIcon(new ImageIcon(Principal.class.getResource("/img/rainhaPreto.png")));
		} else if (this.getCor().equals("vermelho")) {
			this.setIcon(new ImageIcon(Principal.class.getResource("/img/rainhaVermelho.png")));
		}
	}
}
