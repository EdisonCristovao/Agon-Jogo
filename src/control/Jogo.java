package control;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

	private Posicao deBtn = null, paBtn = null;
	private Pecinha pecinha;

	public Jogo(AtorNetGames atorNetGames, AtorJogador atorJogador) {
		this.atorNetGames = atorNetGames;
		this.atorJogador = atorJogador;
	}

	public void criarParticipante(String nome) {
		if (jogador1 == null) {
			jogador1 = new Jogador(nome, "preto");
			jogador1.tomaVez();
			jogadorDaVez = jogador1;
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

	// algoritimo 3
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

	public boolean verificaPecinhaDoJogador(Posicao posicao) {
		if (jogadorDaVez.getCor().equals(posicao.getPecinha().getCor())) {
			System.out.println("Pecinha do jogador");
			return true;
		}
		return false;
	}

	public void recebeEvento(ActionEvent arg0) {
		// verifica se jogador é da vez

		// recebe evento do jogador
		try {
			Movimento movimento;
			if (deBtn == null && pecinha == null) {
				if (verificaPecinhaDoJogador((Posicao) arg0.getSource())) {
					deBtn = (Posicao) arg0.getSource();
				} else {
					atorJogador.informarMsg("Essa peça nao e sua, escolha uma peça valida");
				}
			} else if (deBtn == null && pecinha != null) {
				deBtn = (Posicao) arg0.getSource();

			} else if (deBtn != null && paBtn == null) {
				// realiza movimento
				paBtn = (Posicao) arg0.getSource();
				movimento = new Movimento(deBtn, paBtn, "mover");
				tratarMovimento(movimento);
				atorNetGames.enviarJogada(movimento);
				verificaSeVenceu();
				passaVez();
				esvaziarTudo();
				atorJogador.setQtdPecinhas();
			}

			if (deBtn != null && pecinha != null) {
				// realiza inserir
				paBtn = deBtn; // pra nao fica sintaticamente feio
				movimento = new Movimento(paBtn, pecinha, "inserir");
				tratarMovimento(movimento);
				atorNetGames.enviarJogada(movimento);
				// verificaSeVenceu();
				passaVez();
				esvaziarTudo();
				atorJogador.setQtdPecinhas();

			}

			//
		} catch (Exception e) {
			esvaziarTudo();
		}

	}

	public void verificaSeVenceu() {
		Tabuleiro t1 = atorJogador.getTabuleiro();
		boolean ganhou = true;
		int x = 5;
		while (ganhou && x < 8) {
			int y = 5;
			while (ganhou && y < 8) {
				Posicao pos = atorJogador.getPosicaoDoTabuleiro(x, y);
				if ((pos.getPecinha() == null) || !pos.getPecinha().getCor().equals(jogadorDaVez.getCor())) {
					ganhou = false;
				}
				y++;
			}
			x++;
		}
		System.out.println("VERIFICADO");
		if (ganhou) {
			atorJogador.informarMsg("eee venceu");
		}

	}

	public void verificaSePerdeu() {
		Tabuleiro t1 = atorJogador.getTabuleiro();
		boolean ganhou = true;
		int x = 5;
		while (ganhou && x < 8) {
			int y = 5;
			while (ganhou && y < 8) {
				Posicao pos = atorJogador.getPosicaoDoTabuleiro(x, y);
				if ((pos.getPecinha() == null) || !pos.getPecinha().getCor().equals(jogadorDaVez.getCor())) {
					ganhou = false;
				}
				y++;
			}
			x++;
		}
		System.out.println("VERIFICADO");
		if (ganhou) {
			atorJogador.informarMsg("Perdeu cara poxa");
		}
	}

	private void esvaziarTudo() {
		pecinha = null;
		deBtn = null;
		paBtn = null;
	}

	public Pecinha getPecinha() {
		return pecinha;
	}

	public void setPecinha(Pecinha pecinha) {
		this.pecinha = pecinha;
	}

	public void setPartidaEmAndamento(boolean partida) {
		this.partidaEmAndamento = partida;
	}

}
