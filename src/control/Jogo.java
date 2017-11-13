package control;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Guerreiro;
import model.Jogador;
import model.Pecinha;
import model.Posicao;
import model.Rainha;
import model.Tabuleiro;
import rede.AtorNetGames;
import rede.Movimento;
import view.AtorJogador;

public class Jogo {

	private Boolean partidaEmAndamento;

	private Jogador jogador1;
	private Jogador jogador2;

	private Jogador jogadorDaVez;

	private AtorNetGames atorNetGames;
	private AtorJogador atorJogador;

	public void criarParticipante(String nome) {
		if (jogador1 == null) {
			jogador1 = new Jogador(nome, "preto");
			jogador1.tomaVez();
		} else if (jogador2 == null) {
			jogador2 = new Jogador(nome, "vermelho");
			jogador2.passaVez();
		}
	}

	public void movePecinha(Posicao deBtn, Posicao paBtn) {
		if (jogador1.getVez()) {
			Pecinha pecinha = deBtn.removePecinha();
			paBtn.setPecinha(pecinha);
		} else if (jogador2.getVez()) {
			Pecinha pecinha = deBtn.removePecinha();
			paBtn.setPecinha(pecinha);
		}
	}

	public void inserePecinha(Pecinha pecinha, Posicao paBtn) {
		if (jogador1.getVez()) {
			paBtn.setPecinha(pecinha);
		} else if (jogador2.getVez()) {
			paBtn.setPecinha(pecinha);
		}
	}

	public Pecinha getGuerreiroDoJogador() {
		if (jogador1.getVez()) {
			return jogador1.getUmGuerreiro();
		} else if (jogador2.getVez()) {
			return jogador2.getUmGuerreiro();
		}
		return null;
	}

	public Pecinha getRainhaDoJogador() {
		if (jogador1.getVez()) {
			return jogador1.getUmRainha();
		} else if (jogador2.getVez()) {
			return jogador2.getUmRainha();
		}
		return null;
	}

	// diferencia o tipo de movimento e manda fazer o trabalho
	public void tratarMovimento(Movimento movimento) {
		if (movimento.getTipoMovimento().equals("inserir")) {
			inserePecinha(movimento.getPecinha(), movimento.getPaBtn());
		} else if (movimento.getTipoMovimento().equals("mover")) {
			movePecinha(movimento.getDeBtn(), movimento.getPaBtn());
		}
	}

	public void passaVez() {
		if (jogador1.getVez()) {
			jogador1.passaVez();
			jogador2.tomaVez();
			jogadorDaVez = jogador2;
		} else if (jogador2.getVez()) {
			jogador1.passaVez();
			jogador1.tomaVez();
			jogadorDaVez = jogador1;
		}
	}

	// get and set

	public Jogador getJogador1() {
		return jogador1;
	}

	public Jogador getJogadorDaVez() {
		return jogadorDaVez;
	}

	public Jogador getJogador2() {
		return jogador2;
	}

	public void setJogadorDaVez(Jogador jogadorDaVez) {
		this.jogadorDaVez = jogadorDaVez;
	}

	public void tratarRecebeMovimento(Movimento movimento) {
		Pecinha pecinha = getGuerreiroDoJogador();

		Posicao pos = pegaPosicaoTabuleiro(movimento);
		pos.setPecinha(pecinha);

		System.out.println("setou");
	}

	private Posicao pegaPosicaoTabuleiro(Movimento movimento) {
		for (int i = 0; i < atorJogador.getTabuleiro().getPosicoes().size(); i++) {
			if (atorJogador.getTabuleiro().getPosicoes().get(i).getCordenadaX().equals(1)) {
				return atorJogador.getTabuleiro().getPosicoes().get(i);
			}
		}
		return null;
	}

	public void trataRecebeMovimento(Movimento movimento) {
		
	}

	public boolean verificaPecinhaDoJogador(Posicao posicao) {
		if (jogadorDaVez.getCor().equals(posicao.getPecinha().getCor())) {
			System.out.println("Pecinha do jogador");
			return true;
		}
		return false;
	}
}
