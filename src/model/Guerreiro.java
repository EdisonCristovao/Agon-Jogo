package model;

import javax.swing.ImageIcon;

public class Guerreiro extends Pecinha{

	public Guerreiro(String cor) {
		super(cor);
		criaIcon();
	}
		
	public void criaIcon() {
		if (this.getCor().equals("preto")) {
			this.setIcon(new ImageIcon("/home/jokerjones/Modelos/java/Agon Game/img/peaoPreto.png"));
		} else if (this.getCor().equals("vermelho")) {
			this.setIcon(new ImageIcon("/home/jokerjones/Modelos/java/Agon Game/img/peaoVermelho.png"));
		}
	}
}
