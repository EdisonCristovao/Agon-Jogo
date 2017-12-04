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

	public Posicao getPosicaoDoTabuleiro(Integer cordenadaX, Integer cordenadaY) {
		for (Posicao pos : atorJogador.getTabuleiro().getPosicoes()) {
			if (pos.getCordenadaX().equals(cordenadaX) && pos.getCordenadaY().equals(cordenadaY)) {
				return pos;
			}
		}
		return null;
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
		System.out.println("Vez passada");
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
		//marcação
	public void setJogadorDaVez(Jogador jogadorDaVez) {
		this.jogadorDaVez = jogadorDaVez;
	}

	public boolean verificaPecinhaDoJogador(Posicao posicao) {
		if (posicao.getPecinha() != null) {
			if (jogadorDaVez.getCor().equals(posicao.getPecinha().getCor())) {
				System.out.println("Pecinha do jogador");
				return true;
			}
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
				if (movimentoPossivel() && deBtn.getPecinha() != null) {
					movimento = new Movimento(deBtn, paBtn, "mover");
					tratarMovimento(movimento);
					atorNetGames.enviarJogada(movimento);
					verificaSeVenceu();
					limpaPecinhasCercadas();
					passaVez();
					esvaziarTudo();
					atorJogador.setQtdPecinhas();
				} else {
					atorJogador.informarMsg("Jogada invalida, Escolha uma peça e uma posiçao valda");
					esvaziarTudo();
				}
			}
			if (deBtn != null && pecinha != null) {
				// realiza inserir
				paBtn = deBtn; // pra nao fica sintaticamente feio
				if (inserirPossivel()) {
					movimento = new Movimento(paBtn, pecinha, "inserir");
					tratarMovimento(movimento);
					passaVez();
					esvaziarTudo();
					atorJogador.setQtdPecinhas();
					atorNetGames.enviarJogada(movimento);
				} else {
					atorJogador.informarMsg("Inserir apenas na borda do tabuleiro, escolha outra posição");
				}
			}
			//
		} catch (Exception e) {
			esvaziarTudo();
		}

	}

	public void limpaPecinhasCercadas() {
		for (Posicao pos : atorJogador.getTabuleiro().getPosicoes()) {
			if (!pos.getTrilha().equals('A') && pos.getPecinha() != null) {
				if (verificaRetiradaDePecinha(pos)) {
					devolvePecinhaJogado(pos);
				}
			}
		}
	}

	private boolean verificaRetiradaDePecinha(Posicao pos) {
		String cor;
		boolean retorno = false;
		if (pos.getPecinha().getCor().equals("vermelho")) {
			cor = "preto";
		} else {
			cor = "vermelho";
		}
		
		if (getPosicaoDoTabuleiro((pos.getCordenadaX() + 1), pos.getCordenadaY()).getPecinha() != null
				&& getPosicaoDoTabuleiro((pos.getCordenadaX() - 1), pos.getCordenadaY()).getPecinha() != null) {
			if (getPosicaoDoTabuleiro((pos.getCordenadaX() + 1), pos.getCordenadaY()).getPecinha().getCor().equals(cor)
					&& getPosicaoDoTabuleiro((pos.getCordenadaX() - 1), pos.getCordenadaY()).getPecinha().getCor()
							.equals(cor)) {
				retorno = true;
			}

		}
		if (getPosicaoDoTabuleiro(pos.getCordenadaX(), (pos.getCordenadaY() + 1)).getPecinha() != null
				&& getPosicaoDoTabuleiro(pos.getCordenadaX(), (pos.getCordenadaY() - 1)).getPecinha() != null) {
			if (getPosicaoDoTabuleiro(pos.getCordenadaX(), (pos.getCordenadaY() + 1)).getPecinha().getCor().equals(cor)
					&& getPosicaoDoTabuleiro(pos.getCordenadaX(), (pos.getCordenadaY() - 1)).getPecinha().getCor()
							.equals(cor)) {
				retorno = true;
			}
		}

		if (getPosicaoDoTabuleiro((pos.getCordenadaX() + 1), pos.getCordenadaY() + 1).getPecinha() != null
				&& getPosicaoDoTabuleiro((pos.getCordenadaX() - 1), pos.getCordenadaY() - 1).getPecinha() != null) {
			if (getPosicaoDoTabuleiro(pos.getCordenadaX() + 1, (pos.getCordenadaY() + 1)).getPecinha().getCor()
					.equals(cor)
					&& getPosicaoDoTabuleiro(pos.getCordenadaX() - 1, (pos.getCordenadaY() - 1)).getPecinha().getCor()
							.equals(cor)) {
				retorno = true;
			}

		}
		if (getPosicaoDoTabuleiro((pos.getCordenadaX() - 1), pos.getCordenadaY() + 1).getPecinha() != null
				&& getPosicaoDoTabuleiro((pos.getCordenadaX() + 1), pos.getCordenadaY() - 1).getPecinha() != null) {
			if (getPosicaoDoTabuleiro(pos.getCordenadaX() - 1, (pos.getCordenadaY() + 1)).getPecinha().getCor()
					.equals(cor)
					&& getPosicaoDoTabuleiro(pos.getCordenadaX() - 1, (pos.getCordenadaY() + 1)).getPecinha().getCor()
							.equals(cor)) {
				retorno = true;
			}
		}

		return retorno;
	}

	public void devolvePecinhaJogado(Posicao pos) {
		Pecinha pecinha = pos.removePecinha();
		if (pecinha instanceof Guerreiro) {
			if (jogador1.getCor().equals(pecinha.getCor())) {
				Guerreiro g = (Guerreiro) pecinha;
				jogador1.getGuerreiros().add(g);
			}
			if (jogador2.getCor().equals(pecinha.getCor())) {
				Guerreiro g = (Guerreiro) pecinha;
				jogador2.getGuerreiros().add(g);
			}
		} else if (pecinha instanceof Rainha) {
			if (jogador1.getCor().equals(pecinha.getCor())) {
				Rainha r = (Rainha) pecinha;
				jogador1.getRainhas().add(r);
			}
			if (jogador2.getCor().equals(pecinha.getCor())) {
				Rainha r = (Rainha) pecinha;
				jogador2.getRainhas().add(r);
			}
		}
	}

	public boolean inserirPossivel() {
		if (paBtn.getTrilha().equals('A')) {
			return true;
		}
		paBtn = null;
		deBtn = null;
		return false;
	}

	public boolean movimentoPossivel() {
		boolean retorno = false;

		// essas 4 comparaçoes fazem os movimentos horizontal e vertical e horizontais
		if ((deBtn.getCordenadaX().equals(paBtn.getCordenadaX())) && (deBtn.getCordenadaY() + 1 == paBtn.getCordenadaY()
				|| deBtn.getCordenadaY() - 1 == paBtn.getCordenadaY())) {
			retorno = true;
		} else if ((deBtn.getCordenadaY().equals(paBtn.getCordenadaY()))
				&& (deBtn.getCordenadaX() + 1 == paBtn.getCordenadaX()
						|| deBtn.getCordenadaX() - 1 == paBtn.getCordenadaX())) {
			retorno = true;
		} else if ((deBtn.getCordenadaX() + 1 == paBtn.getCordenadaX())
				&& (deBtn.getCordenadaY() + 1 == paBtn.getCordenadaY()
						|| deBtn.getCordenadaY() - 1 == paBtn.getCordenadaY())) {
			retorno = true;
		} else if ((deBtn.getCordenadaX() - 1 == paBtn.getCordenadaX())
				&& (deBtn.getCordenadaY() + 1 == paBtn.getCordenadaY()
						|| deBtn.getCordenadaY() - 1 == paBtn.getCordenadaY())) {
			retorno = true;
		}
		
		// essa comparação faz com que o guerreiro trave na Trilha E enquanto a rainha pode avançar para F
		if (deBtn.getPecinha() instanceof Guerreiro && deBtn.getTrilha().equals('E')) {
			retorno = false;
		}		
		
		//essa comparação, faz com que o jogador nao possa volta pra trilha anterior
		if (deBtn.getTrilha() > paBtn.getTrilha() || paBtn.getPecinha() != null) {
			retorno = false;
		}

		return retorno;
	}

	public void verificaSeVenceu() {
		boolean ganhou = true;
		int x = 5;
		while (ganhou && x < 8) {
			int y = 5;
			while (ganhou && y < 8) {
				Posicao pos = getPosicaoDoTabuleiro(x, y);
				if ((pos.getPecinha() == null) || !pos.getPecinha().getCor().equals(jogadorDaVez.getCor())) {
					ganhou = false;
				}
				y++;
			}
			x++;
		}
		if (ganhou) {
			System.out.println("jogador "+jogadorDaVez+" Venceu a partida");
			atorJogador.informarMsg("Parabens voce é o vencedor");
			atorJogador.finalizaPartida();
		}

	}

	public void verificaSePerdeu() {
		boolean ganhou = true;
		int x = 5;
		while (ganhou && x < 8) {
			int y = 5;
			while (ganhou && y < 8) {
				Posicao pos = getPosicaoDoTabuleiro(x, y);
				if ((pos.getPecinha() == null) || !pos.getPecinha().getCor().equals(jogadorDaVez.getCor())) {
					ganhou = false;
				}
				y++;
			}
			x++;
		}

		if (ganhou) {
			System.out.println("Perdeu");
			atorJogador.informarMsg("Que pena vocè perdeu");
			atorJogador.finalizaPartida();
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

	public void setJogadores() {
		jogador1 = null;
		jogador2 = null;
		partidaEmAndamento = false;
		atorNetGames.setEhMinhaVez(false);
	}

}
