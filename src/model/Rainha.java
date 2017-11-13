package model;

import javax.swing.ImageIcon;

public class Rainha extends Pecinha {
	
	public Rainha(String cor) {
		super(cor);
		this.setPosF6(true);
		criaIcon();
	}
	
	public void criaIcon() {
		if (this.getCor().equals("preto")) {
			this.setIcon(new ImageIcon("/home/jokerjones/Modelos/java/Agon Game/img/rainhaPreto.png"));
		} else if (this.getCor().equals("vermelho")) {
			this.setIcon(new ImageIcon("/home/jokerjones/Modelos/java/Agon Game/img/rainhaVermelho.png"));
		}
	}
}
