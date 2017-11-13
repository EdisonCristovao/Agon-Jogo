package rede;

import br.ufsc.inf.leobr.cliente.Jogada;
import model.Pecinha;
import model.Posicao;

public class Movimento implements Jogada {

	private static final long serialVersionUID = 1L;
	private Posicao deBtn;
	private Posicao paBtn;
	private Pecinha pecinha;
	private String tipoMovimento;
	
	public Movimento(Posicao deBtn, Posicao paBtn, String tipoMovimento) {
		this.paBtn = paBtn;
		this.deBtn = deBtn;
		this.pecinha = null;
		this.tipoMovimento = tipoMovimento;
	}
	
	public Movimento(Posicao paBtn, Pecinha pecinha, String tipoMovimento) {
		this.pecinha = pecinha;
		this.paBtn = paBtn;
		this.deBtn = null;
		this.tipoMovimento = tipoMovimento;
	}

	public Posicao getDeBtn() {
		return deBtn;
	}

	public void setDeBtn(Posicao deBtn) {
		this.deBtn = deBtn;
	}

	public Posicao getPaBtn() {
		return paBtn;
	}

	public void setPaBtn(Posicao paBtn) {
		this.paBtn = paBtn;
	}

	public Pecinha getPecinha() {
		return pecinha;
	}

	public void setPecinha(Pecinha pecinha) {
		this.pecinha = pecinha;
	}

	public String getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	
	
	

}
