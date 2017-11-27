package model;

import javax.swing.ImageIcon;

import control.Principal;

public class Guerreiro extends Pecinha {

	public Guerreiro(String cor) {
		super(cor);
		criaIcon();
	}

	public void criaIcon() {
		if (this.getCor().equals("preto")) {
			this.setIcon(new ImageIcon(Principal.class.getResource("/img/peaoPreto.png")));
		} else if (this.getCor().equals("vermelho")) {
			this.setIcon(new ImageIcon(Principal.class.getResource("/img/peaoVermelho.png")));
		}
	}
}
