package rede;

import javax.swing.JOptionPane;

import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import view.AtorJogador;

public class AtorNetGames implements OuvidorProxy {

	private Proxy proxy;
	private boolean ehMinhaVez = false;
	private AtorJogador atorJogador;
	
	public AtorNetGames(AtorJogador atorJogador) {
		super();
		proxy = proxy.getInstance();
		proxy.addOuvinte(this);
		this.atorJogador = atorJogador;
	}

	public boolean getEhMinhaVez() {
		return this.ehMinhaVez;
	}
	
	@Override
	public void iniciarNovaPartida(Integer posicao) {
		
		if (posicao == 1){
			ehMinhaVez = true;
		}else if (posicao == 2){
			ehMinhaVez = false;
		}
		
		atorJogador.iniciarPartidaRede(ehMinhaVez);

	}

	@Override
	public void finalizarPartidaComErro(String message) {

	}

	@Override
	public void receberMensagem(String msg) {

	}

	@Override
	public void receberJogada(Jogada jogada) {
		
		Movimento movimento = (Movimento) jogada;
		atorJogador.receberMovimentoRede(movimento);
		ehMinhaVez = true;
		
	}

	@Override
	public void tratarConexaoPerdida() {

	}

	@Override
	public void tratarPartidaNaoIniciada(String message) {

	}

	public void conectar(String nome, String ip) {
		try {
			proxy.conectar(ip, nome);
		} catch (JahConectadoException e) {
			e.printStackTrace();
		} catch (NaoPossivelConectarException e) {
			e.printStackTrace();
		} catch (ArquivoMultiplayerException e) {
			e.printStackTrace();
		}
	}

	public void desconectar() {
		try {
			proxy.desconectar();
		} catch (NaoConectadoException e) {
			JOptionPane.showMessageDialog(atorJogador.getFrmAgon(), "Erro ao se Desconectar");
			e.printStackTrace();
		}
	}

	public void iniciarPartidaRede() throws NaoConectadoException {
			proxy.iniciarPartida(2);
		
	}

	public String obterNomeAdversario() {
		String nome = "";
		if (ehMinhaVez) {
			nome = proxy.obterNomeAdversario(2);
		} else {
			nome = proxy.obterNomeAdversario(1);
		}
		return nome;
	}

	public void enviarJogada(Movimento movimento) {
		try {
			System.out.println("Enviar Jogada");
			proxy.enviaJogada(movimento);
			ehMinhaVez = false;
		} catch (NaoJogandoException e) {
			JOptionPane.showMessageDialog(atorJogador.getFrmAgon(), e.getMessage());
			System.out.println("Erro no enviar jogada");
		}
	}

}
