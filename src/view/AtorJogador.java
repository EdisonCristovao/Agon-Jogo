package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import control.Jogo;
import model.Guerreiro;
import model.Pecinha;
import model.Posicao;
import model.Rainha;
import model.Tabuleiro;
import rede.AtorNetGames;
import rede.Movimento;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class AtorJogador {

	private JFrame frmAgon;
	private AtorNetGames atorNetGames;
	private Tabuleiro tabuleiro;
	private Jogo jogo;
	private String nome;

	Posicao deBtn, paBtn = null;

	Pecinha pecinha = null;

	JPanel panel_1, panel_2;

	JButton btnColocarRainha_jogador1;
	JButton btnColocarGuerreiro_jogador1;
	JButton btnRenderse_jogador1;

	JButton btnColocarRainha_jogador2;
	JButton btnColocarGuerreiro_jogador2;
	JButton btnRenderse_jogador2;

	JLabel rainhasJogador1 = new JLabel("0");
	JLabel guerreirosJogador1 = new JLabel("0");

	JLabel rainhasJogador2 = new JLabel("0");
	JLabel guerreirosJogador2 = new JLabel("0");

	public AtorJogador() {
		this.atorNetGames = new AtorNetGames(this);
		List<Posicao> Posicoes = initialize();
		tabuleiro = new Tabuleiro(Posicoes);

	}

	public void setQtdPecinhas() {
		rainhasJogador1.setText("" + jogo.getJogador1().getRainhas().size());
		guerreirosJogador1.setText("" + jogo.getJogador1().getGuerreiros().size());

		rainhasJogador2.setText("" + jogo.getJogador2().getRainhas().size());
		guerreirosJogador2.setText("" + jogo.getJogador2().getGuerreiros().size());
	}

	public void desabilitaBotoesParaJogador1() {

		btnColocarRainha_jogador2.setEnabled(false);
		btnColocarGuerreiro_jogador2.setEnabled(false);
		btnRenderse_jogador2.setEnabled(false);
	}

	public void desabilitaBotoesParaJogador2() {

		btnColocarRainha_jogador1.setEnabled(false);
		btnColocarGuerreiro_jogador1.setEnabled(false);
		btnRenderse_jogador1.setEnabled(false);
	}

	public Tabuleiro getTabuleiro() {
		return this.tabuleiro;
	}

	public JFrame getFrmAgon() {
		return frmAgon;
	}

	private List<Posicao> initialize() {
		List<Posicao> retorno = new ArrayList<Posicao>();

		ActionListener trocaBotao = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (atorNetGames.getEhMinhaVez()) {
					try {
						Movimento movimento;
						if (deBtn == null && pecinha == null) {
							// Mover
							if (jogo.verificaPecinhaDoJogador((Posicao) arg0.getSource())) {
								deBtn = (Posicao) arg0.getSource();
							} else {
								informarMsg("Essa peça nao e sua, escolha uma peça valida");
							}
						} else if (deBtn == null && pecinha != null) {
							// inserir
							deBtn = (Posicao) arg0.getSource();

						} else if (deBtn != null && paBtn == null) {
							// realiza movimento
							paBtn = (Posicao) arg0.getSource();
							movimento = new Movimento(deBtn, paBtn, "mover");
							jogo.tratarMovimento(movimento);
							atorNetGames.enviarJogada(movimento);
							jogo.passaVez();
							esvaziarTudo();
							setQtdPecinhas();
						}

						if (deBtn != null && pecinha != null) {
							// realiza inserir
							paBtn = deBtn; // pra nao fica sintaticamente feio
							movimento = new Movimento(paBtn, pecinha, "inserir");
							jogo.tratarMovimento(movimento);
							atorNetGames.enviarJogada(movimento);
							jogo.passaVez();
							esvaziarTudo();
							setQtdPecinhas();
						}

						//
					} catch (Exception e) {
						esvaziarTudo();

					}
				} else {
					JOptionPane.showMessageDialog(frmAgon, "Aguarde sua vez");
				}
			}

			
			
			private void esvaziarTudo() {
				deBtn = null;
				paBtn = null;
				pecinha = null;
			}
		};

		frmAgon = new JFrame();
		frmAgon.setTitle("Agon");
		frmAgon.setBounds(100, 100, 920, 528);
		frmAgon.setResizable(false);
		frmAgon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmAgon.getContentPane().add(panel, BorderLayout.NORTH);

		Posicao btn1 = new Posicao(1, 11, 'A');
		btn1.setBackground(Color.WHITE);
		btn1.addActionListener(trocaBotao);
		retorno.add(btn1);

		Posicao btn2 = new Posicao(2, 11, 'A');
		btn2.setBackground(Color.WHITE);
		btn2.addActionListener(trocaBotao);
		retorno.add(btn2);

		Posicao btn3 = new Posicao(3, 11, 'A');
		btn3.setBackground(Color.WHITE);
		btn3.addActionListener(trocaBotao);
		retorno.add(btn3);

		Posicao btn4 = new Posicao(4, 11, 'A');
		btn4.setBackground(Color.WHITE);
		btn4.addActionListener(trocaBotao);
		retorno.add(btn4);

		Posicao btn6 = new Posicao(6, 11, 'A');
		btn6.setBackground(Color.WHITE);
		btn6.addActionListener(trocaBotao);
		retorno.add(btn6);

		Posicao btn5 = new Posicao(5, 11, 'A');
		btn5.setBackground(Color.WHITE);
		btn5.addActionListener(trocaBotao);
		retorno.add(btn5);

		Posicao btn7 = new Posicao(7, 11, 'A');
		btn7.setBackground(Color.WHITE);
		btn7.addActionListener(trocaBotao);
		retorno.add(btn7);

		Posicao btn8 = new Posicao(8, 11, 'A');
		btn8.setBackground(Color.WHITE);
		btn8.addActionListener(trocaBotao);
		retorno.add(btn8);

		Posicao btn9 = new Posicao(9, 11, 'A');
		btn9.setBackground(Color.WHITE);
		btn9.addActionListener(trocaBotao);
		retorno.add(btn9);

		Posicao btn10 = new Posicao(10, 11, 'A');
		btn10.setBackground(Color.WHITE);
		btn10.addActionListener(trocaBotao);
		retorno.add(btn10);

		Posicao btn11 = new Posicao(11, 11, 'A');
		btn11.setBackground(Color.WHITE);
		btn11.addActionListener(trocaBotao);
		retorno.add(btn11);

		Posicao btn12 = new Posicao(1, 10, 'A');
		btn12.setBackground(Color.WHITE);
		btn12.addActionListener(trocaBotao);
		retorno.add(btn12);

		Posicao btn13 = new Posicao(2, 10, 'B');
		btn13.setBackground(Color.LIGHT_GRAY);
		btn13.addActionListener(trocaBotao);
		retorno.add(btn13);

		Posicao btn14 = new Posicao(3, 10, 'B');
		btn14.setBackground(Color.LIGHT_GRAY);
		btn14.addActionListener(trocaBotao);
		retorno.add(btn14);

		Posicao btn15 = new Posicao(4, 10, 'B');
		btn15.setBackground(Color.LIGHT_GRAY);
		btn15.addActionListener(trocaBotao);
		retorno.add(btn15);

		Posicao btn16 = new Posicao(5, 10, 'B');
		btn16.setBackground(Color.LIGHT_GRAY);
		btn16.addActionListener(trocaBotao);
		retorno.add(btn16);

		Posicao btn17 = new Posicao(6, 10, 'B');
		btn17.setBackground(Color.LIGHT_GRAY);
		btn17.addActionListener(trocaBotao);
		retorno.add(btn17);

		Posicao btn18 = new Posicao(7, 10, 'B');
		btn18.setBackground(Color.LIGHT_GRAY);
		btn18.addActionListener(trocaBotao);
		retorno.add(btn18);

		Posicao btn19 = new Posicao(8, 10, 'B');
		btn19.setBackground(Color.LIGHT_GRAY);
		btn19.addActionListener(trocaBotao);
		retorno.add(btn19);

		Posicao btn20 = new Posicao(9, 10, 'B');
		btn20.setBackground(Color.LIGHT_GRAY);
		btn20.addActionListener(trocaBotao);
		retorno.add(btn20);

		Posicao btn21 = new Posicao(10, 10, 'B');
		btn21.setBackground(Color.LIGHT_GRAY);
		btn21.addActionListener(trocaBotao);
		retorno.add(btn21);

		Posicao btn22 = new Posicao(11, 10, 'B');
		btn22.setBackground(Color.WHITE);
		btn22.addActionListener(trocaBotao);
		retorno.add(btn22);

		// caracter esta errado aqui
		Posicao btn24 = new Posicao(2, 9, 'B');
		btn24.setBackground(Color.LIGHT_GRAY);
		btn24.addActionListener(trocaBotao);
		retorno.add(btn24);

		Posicao btn25 = new Posicao(3, 9, 'B');
		btn25.setBackground(Color.WHITE);
		btn25.addActionListener(trocaBotao);
		retorno.add(btn25);

		Posicao btn26 = new Posicao(4, 9, 'B');
		btn26.setBackground(Color.WHITE);
		btn26.addActionListener(trocaBotao);
		retorno.add(btn26);

		Posicao btn28 = new Posicao(6, 9, 'B');
		btn28.setBackground(Color.WHITE);
		btn28.addActionListener(trocaBotao);
		retorno.add(btn28);

		Posicao btn27 = new Posicao(5, 9, 'B');
		btn27.setBackground(Color.WHITE);
		btn27.addActionListener(trocaBotao);
		retorno.add(btn27);

		Posicao btn29 = new Posicao(7, 9, 'B');
		btn29.setBackground(Color.WHITE);
		btn29.addActionListener(trocaBotao);
		retorno.add(btn29);

		Posicao btn30 = new Posicao(8, 9, 'B');
		btn30.setBackground(Color.WHITE);
		btn30.addActionListener(trocaBotao);
		retorno.add(btn30);

		Posicao btn31 = new Posicao(9, 9, 'B');
		btn31.setBackground(Color.WHITE);
		btn31.addActionListener(trocaBotao);
		retorno.add(btn31);

		Posicao btn32 = new Posicao(10, 9, 'B');
		btn32.setBackground(Color.LIGHT_GRAY);
		btn32.addActionListener(trocaBotao);
		retorno.add(btn32);

		Posicao btn33 = new Posicao(11, 9, 'B');
		btn33.setBackground(Color.WHITE);
		btn33.addActionListener(trocaBotao);
		retorno.add(btn33);

		Posicao btn34 = new Posicao(1, 8, 'B');
		btn34.setBackground(Color.WHITE);
		btn34.addActionListener(trocaBotao);
		retorno.add(btn34);

		Posicao btn35 = new Posicao(2, 8, 'B');
		btn35.setBackground(Color.LIGHT_GRAY);
		btn35.addActionListener(trocaBotao);
		retorno.add(btn35);

		Posicao btn36 = new Posicao(3, 8, 'B');
		btn36.setBackground(Color.WHITE);
		btn36.addActionListener(trocaBotao);
		retorno.add(btn36);

		Posicao btn37 = new Posicao(4, 8, 'B');
		btn37.setBackground(Color.LIGHT_GRAY);
		btn37.addActionListener(trocaBotao);
		retorno.add(btn37);

		Posicao btn38 = new Posicao(5, 8, 'B');
		btn38.setBackground(Color.LIGHT_GRAY);
		btn38.addActionListener(trocaBotao);
		retorno.add(btn38);

		Posicao btn39 = new Posicao(6, 8, 'B');
		btn39.setBackground(Color.LIGHT_GRAY);
		btn39.addActionListener(trocaBotao);
		retorno.add(btn39);

		Posicao btn40 = new Posicao(7, 8, 'B');
		btn40.setBackground(Color.LIGHT_GRAY);
		btn40.addActionListener(trocaBotao);
		retorno.add(btn40);

		Posicao btn41 = new Posicao(8, 8, 'B');
		btn41.setBackground(Color.LIGHT_GRAY);
		btn41.addActionListener(trocaBotao);
		retorno.add(btn41);

		Posicao btn42 = new Posicao(9, 8, 'B');
		btn42.setBackground(Color.WHITE);
		btn42.addActionListener(trocaBotao);
		retorno.add(btn42);

		Posicao btn43 = new Posicao(10, 8, 'B');
		btn43.setBackground(Color.LIGHT_GRAY);
		btn43.addActionListener(trocaBotao);
		retorno.add(btn43);

		Posicao btn44 = new Posicao(11, 8, 'B');
		btn44.setBackground(Color.WHITE);
		btn44.addActionListener(trocaBotao);
		retorno.add(btn44);

		Posicao btn23 = new Posicao(1, 9, 'B');
		btn23.setBackground(Color.WHITE);
		btn23.addActionListener(trocaBotao);
		retorno.add(btn23);

		Posicao btn46 = new Posicao(2, 7, 'B');
		btn46.setBackground(Color.LIGHT_GRAY);
		btn46.addActionListener(trocaBotao);
		retorno.add(btn46);

		Posicao btn47 = new Posicao(3, 7, 'B');
		btn47.setBackground(Color.WHITE);
		btn47.addActionListener(trocaBotao);
		retorno.add(btn47);

		Posicao btn48 = new Posicao(4, 7, 'B');
		btn48.setBackground(Color.LIGHT_GRAY);
		btn48.addActionListener(trocaBotao);
		retorno.add(btn48);

		Posicao btn50 = new Posicao(6, 7, 'B');
		btn50.setBackground(Color.WHITE);
		btn50.addActionListener(trocaBotao);
		retorno.add(btn50);

		Posicao btn49 = new Posicao(5, 7, 'B');
		btn49.setBackground(Color.WHITE);
		btn49.addActionListener(trocaBotao);
		retorno.add(btn49);

		Posicao btn51 = new Posicao(7, 7, 'B');
		btn51.setBackground(Color.WHITE);
		btn51.addActionListener(trocaBotao);
		retorno.add(btn51);

		Posicao btn52 = new Posicao(8, 7, 'B');
		btn52.setBackground(Color.LIGHT_GRAY);
		btn52.addActionListener(trocaBotao);
		retorno.add(btn52);

		Posicao btn53 = new Posicao(9, 7, 'B');
		btn53.setBackground(Color.WHITE);
		btn53.addActionListener(trocaBotao);
		retorno.add(btn53);

		Posicao btn54 = new Posicao(10, 7, 'B');
		btn54.setBackground(Color.LIGHT_GRAY);
		btn54.addActionListener(trocaBotao);
		retorno.add(btn54);

		Posicao btn55 = new Posicao(11, 7, 'B');
		btn55.setBackground(Color.WHITE);
		btn55.addActionListener(trocaBotao);
		retorno.add(btn55);

		Posicao btn56 = new Posicao(1, 6, 'B');
		btn56.setBackground(Color.WHITE);
		btn56.addActionListener(trocaBotao);
		retorno.add(btn56);

		Posicao btn57 = new Posicao(2, 6, 'B');
		btn57.setBackground(Color.LIGHT_GRAY);
		btn57.addActionListener(trocaBotao);
		retorno.add(btn57);

		Posicao btn58 = new Posicao(3, 6, 'B');
		btn58.setBackground(Color.WHITE);
		btn58.addActionListener(trocaBotao);
		retorno.add(btn58);

		Posicao btn59 = new Posicao(4, 6, 'B');
		btn59.setBackground(Color.LIGHT_GRAY);
		btn59.addActionListener(trocaBotao);
		retorno.add(btn59);

		Posicao btn60 = new Posicao(5, 6, 'B');
		btn60.setBackground(Color.WHITE);
		btn60.addActionListener(trocaBotao);
		retorno.add(btn60);

		Posicao btn61 = new Posicao(6, 6, 'B');
		btn61.setBackground(Color.LIGHT_GRAY);
		btn61.addActionListener(trocaBotao);
		retorno.add(btn61);

		Posicao btn62 = new Posicao(7, 6, 'B');
		btn62.setBackground(Color.WHITE);
		btn62.addActionListener(trocaBotao);
		retorno.add(btn62);

		Posicao btn63 = new Posicao(8, 6, 'B');
		btn63.setBackground(Color.LIGHT_GRAY);
		btn63.addActionListener(trocaBotao);
		retorno.add(btn63);

		Posicao btn64 = new Posicao(9, 6, 'B');
		btn64.setBackground(Color.WHITE);
		btn64.addActionListener(trocaBotao);
		retorno.add(btn64);

		Posicao btn65 = new Posicao(10, 6, 'B');
		btn65.setBackground(Color.LIGHT_GRAY);
		btn65.addActionListener(trocaBotao);
		retorno.add(btn65);

		Posicao btn66 = new Posicao(11, 6, 'B');
		btn66.setBackground(Color.WHITE);
		btn66.addActionListener(trocaBotao);
		retorno.add(btn66);

		Posicao btn68 = new Posicao(2, 5, 'B');
		btn68.setBackground(Color.LIGHT_GRAY);
		btn68.addActionListener(trocaBotao);
		retorno.add(btn68);

		Posicao btn69 = new Posicao(3, 5, 'B');
		btn69.setBackground(Color.WHITE);
		btn69.addActionListener(trocaBotao);
		retorno.add(btn69);

		Posicao btn70 = new Posicao(4, 5, 'B');
		btn70.setBackground(Color.LIGHT_GRAY);
		btn70.addActionListener(trocaBotao);
		retorno.add(btn70);

		Posicao btn72 = new Posicao(6, 5, 'B');
		btn72.setBackground(Color.WHITE);
		btn72.addActionListener(trocaBotao);
		retorno.add(btn72);

		Posicao btn71 = new Posicao(5, 5, 'B');
		btn71.setBackground(Color.WHITE);
		btn71.addActionListener(trocaBotao);
		retorno.add(btn71);

		Posicao btn73 = new Posicao(7, 5, 'B');
		btn73.setBackground(Color.WHITE);
		btn73.addActionListener(trocaBotao);
		retorno.add(btn73);

		Posicao btn74 = new Posicao(8, 5, 'B');
		btn74.setBackground(Color.LIGHT_GRAY);
		btn74.addActionListener(trocaBotao);
		retorno.add(btn74);

		Posicao btn75 = new Posicao(9, 5, 'B');
		btn75.setBackground(Color.WHITE);
		btn75.addActionListener(trocaBotao);
		retorno.add(btn75);

		Posicao btn76 = new Posicao(10, 5, 'B');
		btn76.setBackground(Color.LIGHT_GRAY);
		btn76.addActionListener(trocaBotao);
		retorno.add(btn76);

		Posicao btn77 = new Posicao(11, 5, 'B');
		btn77.setBackground(Color.WHITE);
		btn77.addActionListener(trocaBotao);
		retorno.add(btn77);

		Posicao btn78 = new Posicao(1, 4, 'B');
		btn78.setBackground(Color.WHITE);
		btn78.addActionListener(trocaBotao);
		retorno.add(btn78);

		Posicao btn79 = new Posicao(2, 4, 'B');
		btn79.setBackground(Color.LIGHT_GRAY);
		btn79.addActionListener(trocaBotao);
		retorno.add(btn79);

		Posicao btn80 = new Posicao(3, 4, 'B');
		btn80.setBackground(Color.WHITE);
		btn80.addActionListener(trocaBotao);
		retorno.add(btn80);

		Posicao btn81 = new Posicao(4, 4, 'B');
		btn81.setBackground(Color.LIGHT_GRAY);
		btn81.addActionListener(trocaBotao);
		retorno.add(btn81);

		Posicao btn82 = new Posicao(5, 4, 'B');
		btn82.setBackground(Color.LIGHT_GRAY);
		btn82.addActionListener(trocaBotao);
		retorno.add(btn82);

		Posicao btn83 = new Posicao(6, 4, 'B');
		btn83.setBackground(Color.LIGHT_GRAY);
		btn83.addActionListener(trocaBotao);
		retorno.add(btn83);

		Posicao btn84 = new Posicao(7, 4, 'B');
		btn84.setBackground(Color.LIGHT_GRAY);
		btn84.addActionListener(trocaBotao);
		retorno.add(btn84);

		Posicao btn85 = new Posicao(8, 4, 'B');
		btn85.setBackground(Color.LIGHT_GRAY);
		btn85.addActionListener(trocaBotao);
		retorno.add(btn85);

		Posicao btn86 = new Posicao(9, 4, 'B');
		btn86.setBackground(Color.WHITE);
		btn86.addActionListener(trocaBotao);
		retorno.add(btn86);

		Posicao btn87 = new Posicao(10, 4, 'B');
		btn87.setBackground(Color.LIGHT_GRAY);
		btn87.addActionListener(trocaBotao);
		retorno.add(btn87);

		Posicao btn67 = new Posicao(1, 5, 'B');
		btn67.setBackground(Color.WHITE);
		btn67.addActionListener(trocaBotao);
		retorno.add(btn67);

		Posicao btn45 = new Posicao(1, 7, 'B');
		btn45.setBackground(Color.WHITE);
		btn45.addActionListener(trocaBotao);
		retorno.add(btn45);

		Posicao btn88 = new Posicao(11, 4, 'B');
		btn88.setBackground(Color.WHITE);
		btn88.addActionListener(trocaBotao);
		retorno.add(btn88);

		Posicao btn89 = new Posicao(1, 3, 'B');
		btn89.setBackground(Color.WHITE);
		btn89.addActionListener(trocaBotao);
		retorno.add(btn89);

		Posicao btn90 = new Posicao(2, 3, 'B');
		btn90.setBackground(Color.LIGHT_GRAY);
		btn90.addActionListener(trocaBotao);
		retorno.add(btn90);

		Posicao btn91 = new Posicao(3, 3, 'B');
		btn91.setBackground(Color.WHITE);
		btn91.addActionListener(trocaBotao);
		retorno.add(btn91);

		Posicao btn92 = new Posicao(4, 3, 'B');
		btn92.setBackground(Color.WHITE);
		btn92.addActionListener(trocaBotao);
		retorno.add(btn92);

		Posicao btn93 = new Posicao(5, 3, 'B');
		btn93.setBackground(Color.WHITE);
		btn93.addActionListener(trocaBotao);
		retorno.add(btn93);

		Posicao btn94 = new Posicao(6, 3, 'B');
		btn94.setBackground(Color.WHITE);
		btn94.addActionListener(trocaBotao);
		retorno.add(btn94);

		Posicao btn95 = new Posicao(7, 3, 'B');
		btn95.setBackground(Color.WHITE);
		btn95.addActionListener(trocaBotao);
		retorno.add(btn95);

		Posicao btn96 = new Posicao(8, 3, 'B');
		btn96.setBackground(Color.WHITE);
		btn96.addActionListener(trocaBotao);
		retorno.add(btn96);

		Posicao btn97 = new Posicao(9, 3, 'B');
		btn97.setBackground(Color.WHITE);
		btn97.addActionListener(trocaBotao);
		retorno.add(btn97);

		Posicao btn98 = new Posicao(10, 3, 'B');
		btn98.setBackground(Color.LIGHT_GRAY);
		btn98.addActionListener(trocaBotao);
		retorno.add(btn98);

		Posicao btn99 = new Posicao(11, 3, 'B');
		btn99.setBackground(Color.WHITE);
		btn99.addActionListener(trocaBotao);
		retorno.add(btn99);

		Posicao btn100 = new Posicao(1, 2, 'B');
		btn100.setBackground(Color.WHITE);
		btn100.addActionListener(trocaBotao);
		retorno.add(btn100);

		Posicao btn101 = new Posicao(2, 2, 'B');
		btn101.setBackground(Color.LIGHT_GRAY);
		btn101.addActionListener(trocaBotao);
		retorno.add(btn101);

		Posicao btn102 = new Posicao(3, 2, 'B');
		btn102.setBackground(Color.LIGHT_GRAY);
		btn102.addActionListener(trocaBotao);
		retorno.add(btn102);

		Posicao btn103 = new Posicao(4, 2, 'B');
		btn103.setBackground(Color.LIGHT_GRAY);
		btn103.addActionListener(trocaBotao);
		retorno.add(btn103);

		Posicao btn104 = new Posicao(5, 2, 'B');
		btn104.setBackground(Color.LIGHT_GRAY);
		btn104.addActionListener(trocaBotao);
		retorno.add(btn104);

		Posicao btn105 = new Posicao(6, 2, 'B');
		btn105.setBackground(Color.LIGHT_GRAY);
		btn105.addActionListener(trocaBotao);
		retorno.add(btn105);

		Posicao btn106 = new Posicao(7, 2, 'B');
		btn106.setBackground(Color.LIGHT_GRAY);
		btn106.addActionListener(trocaBotao);
		retorno.add(btn106);

		Posicao btn107 = new Posicao(8, 2, 'B');
		btn107.setBackground(Color.LIGHT_GRAY);
		btn107.addActionListener(trocaBotao);
		retorno.add(btn107);

		Posicao btn108 = new Posicao(9, 2, 'B');
		btn108.setBackground(Color.LIGHT_GRAY);
		btn108.addActionListener(trocaBotao);
		retorno.add(btn108);

		Posicao btn109 = new Posicao(10, 2, 'B');
		btn109.setBackground(Color.LIGHT_GRAY);
		btn109.addActionListener(trocaBotao);
		retorno.add(btn109);

		Posicao btn110 = new Posicao(11, 2, 'B');
		btn110.setBackground(Color.WHITE);
		btn110.addActionListener(trocaBotao);
		retorno.add(btn110);

		Posicao btn111 = new Posicao(1, 1, 'B');
		btn111.setBackground(Color.WHITE);
		btn111.addActionListener(trocaBotao);
		retorno.add(btn111);

		Posicao btn112 = new Posicao(2, 1, 'B');
		btn112.setBackground(Color.WHITE);
		btn112.addActionListener(trocaBotao);
		retorno.add(btn112);

		Posicao btn113 = new Posicao(3, 1, 'B');
		btn113.setBackground(Color.WHITE);
		btn113.addActionListener(trocaBotao);
		retorno.add(btn113);

		Posicao btn114 = new Posicao(4, 1, 'B');
		btn114.setBackground(Color.WHITE);
		btn114.addActionListener(trocaBotao);
		retorno.add(btn114);

		Posicao btn115 = new Posicao(5, 1, 'B');
		btn115.setBackground(Color.WHITE);
		btn115.addActionListener(trocaBotao);
		retorno.add(btn115);

		Posicao btn116 = new Posicao(6, 1, 'B');
		btn116.setBackground(Color.WHITE);
		btn116.addActionListener(trocaBotao);
		retorno.add(btn116);

		Posicao btn117 = new Posicao(7, 1, 'B');
		btn117.setBackground(Color.WHITE);
		btn117.addActionListener(trocaBotao);
		retorno.add(btn117);

		Posicao btn118 = new Posicao(8, 1, 'B');
		btn118.setBackground(Color.WHITE);
		btn118.addActionListener(trocaBotao);
		retorno.add(btn118);

		Posicao btn119 = new Posicao(9, 1, 'B');
		btn119.setBackground(Color.WHITE);
		btn119.addActionListener(trocaBotao);
		retorno.add(btn119);

		Posicao btn120 = new Posicao(10, 1, 'B');
		btn120.setBackground(Color.WHITE);
		btn120.addActionListener(trocaBotao);
		retorno.add(btn120);

		Posicao btn121 = new Posicao(11, 1, 'B');
		btn121.setBackground(Color.WHITE);
		btn121.addActionListener(trocaBotao);
		retorno.add(btn121);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Jogador 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Jogador 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		rainhasJogador2 = new JLabel("0");

		guerreirosJogador2 = new JLabel("0");

		JLabel label_2 = new JLabel("Rainha:");

		JLabel label_5 = new JLabel("Guerreiros:");

		btnRenderse_jogador2 = new JButton("Render-se");

		btnColocarRainha_jogador2 = new JButton("Colocar Rainha");

		btnColocarGuerreiro_jogador2 = new JButton("Colocar Guerreiro");

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 362, Short.MAX_VALUE)
						.addGroup(gl_panel_2.createSequentialGroup().addGap(40)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(rainhasJogador2)
								.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(guerreirosJogador2, GroupLayout.PREFERRED_SIZE, 8,
										GroupLayout.PREFERRED_SIZE)
								.addGap(30))
						.addGroup(gl_panel_2.createSequentialGroup().addGap(125).addContainerGap(141, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup().addGap(128).addComponent(btnRenderse_jogador2)
								.addContainerGap(142, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup().addGap(26).addComponent(btnColocarRainha_jogador2)
								.addGap(51).addComponent(btnColocarGuerreiro_jogador2).addGap(25)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 189, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap().addGap(18)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(label_2)
								.addComponent(label_5).addComponent(rainhasJogador2).addComponent(guerreirosJogador2))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnColocarRainha_jogador2).addComponent(btnColocarGuerreiro_jogador2))
						.addGap(26).addComponent(btnRenderse_jogador2).addContainerGap(277, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn3, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn4, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn5, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn6, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn7, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn8, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn9, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn10, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn11,
												GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn12, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn13, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn14, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn15, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn16, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn17, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn18, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn19, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn20, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn21, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(btn22, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn23, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn24, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn25, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn26, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn27, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn28, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn29, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn30, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn31, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn32, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(btn33, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn34, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn35, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn36, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn37, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn38, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn39, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn40, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn41, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn42, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn43, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(btn44, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn45, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn46, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn47, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn48, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn49, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn50, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn51, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn52, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn53, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn54, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(btn55, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn56, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn57, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn58, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn59, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn60, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn61, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn62, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn63, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn64, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn65, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(btn66, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn67, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn68, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn69, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn70, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn71, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn72, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn73, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn74, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn75, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn76, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(btn77, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn78, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn79, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn80, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn81, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn82, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn83, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn84, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn85, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn86, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(btn87, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn88,
												GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btn89, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn90, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn91, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn92, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn93, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn94, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn95, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn96, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn97, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btn98, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn99,
												GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
								.addGroup(
										gl_panel.createSequentialGroup()
												.addComponent(btn100, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn101, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn102, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn103, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn104, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn105, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn106, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn107, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn108, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btn109, GroupLayout.PREFERRED_SIZE, 38,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btn110,
														GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(btn111, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn112, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn113, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn114, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn115, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn116, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn117, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn118, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn119, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn120, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn121, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(46, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(20)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn11, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn10, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn9, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn8, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn7, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn6, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn5, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn3, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn2, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn12, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn13, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn14, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn15, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn16, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn17, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn18, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn19, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn20, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn21, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn22, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn23, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn24, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn25, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn26, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn27, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn28, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn29, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn30, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn31, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn32, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn33, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addGap(6)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn34, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn35, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn36, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn37, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn38, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn39, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn40, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn41, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn42, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn43, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn44, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn45, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn46, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn47, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn48, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn49, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn50, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn51, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn52, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn53, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn54, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn55, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addGap(6)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn56, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn57, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn58, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn59, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn60, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn61, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn62, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn63, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn64, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn65, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn66, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addGap(6)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn67, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn68, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn69, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn70, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn71, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn72, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn73, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn74, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn75, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn76, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn77, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addGap(6)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn78, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn79, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn80, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn81, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn82, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn83, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn84, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn85, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn86, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn87, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn88, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn89, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn90, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn91, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn92, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn93, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn94, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn95, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn96, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn97, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn98, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(btn99, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn100, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn101, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn102, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn103, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn104, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn105, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn106, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn107, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn108, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn109, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn110, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btn111, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn112, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn113, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn114, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn115, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn116, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn117, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn118, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn119, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn120, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn121, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap()));

		JLabel lblRainha_jogador1 = new JLabel("Rainha: ");

		JLabel lblGuerreiro_jogador1 = new JLabel("Guerreiros: ");

		btnColocarRainha_jogador1 = new JButton("Colocar Rainha");

		btnColocarGuerreiro_jogador1 = new JButton("Colocar Guerreiro");

		btnRenderse_jogador1 = new JButton("Render-se 1");

		rainhasJogador1 = new JLabel("0");

		guerreirosJogador1 = new JLabel("0");

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGap(40)
						.addComponent(lblRainha_jogador1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(rainhasJogador1)
						.addPreferredGap(ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
						.addComponent(lblGuerreiro_jogador1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(guerreirosJogador1, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
						.addGap(30))
				.addGroup(gl_panel_1.createSequentialGroup().addGap(125).addContainerGap(152, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup().addGap(128).addComponent(btnRenderse_jogador1)
						.addContainerGap(153, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup().addGap(26).addComponent(btnColocarRainha_jogador1)
						.addGap(51).addComponent(btnColocarGuerreiro_jogador1).addGap(25)));
		gl_panel_1
				.setVerticalGroup(
						gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addGap(18)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblRainha_jogador1).addComponent(lblGuerreiro_jogador1)
												.addComponent(rainhasJogador1).addComponent(guerreirosJogador1))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnColocarRainha_jogador1)
												.addComponent(btnColocarGuerreiro_jogador1))
										.addGap(26).addComponent(btnRenderse_jogador1)
										.addContainerGap(60, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);

		JMenuBar menuBar = new JMenuBar();
		frmAgon.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Jogo");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNovaPartida = new JMenuItem("Nova Partida");
		mnNewMenu.add(mntmNovaPartida);
		mntmNovaPartida.setEnabled(false);

		JMenuItem mntmConectar = new JMenuItem("Conectar");
		mnNewMenu.add(mntmConectar);

		JMenuItem mntmDesconectar = new JMenuItem("Desconectar");
		mnNewMenu.add(mntmDesconectar);
		mntmDesconectar.setEnabled(false);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mnNewMenu.add(mntmSair);

		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);

		JMenuItem mntmAjuda = new JMenuItem("Ajuda");
		mnAjuda.add(mntmAjuda);

		frmAgon.setVisible(true);

		ActionListener conectar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				solicitarNome();
				atorNetGames.conectar(nome, "localhost");
				mntmNovaPartida.setEnabled(true);
				mntmDesconectar.setEnabled(true);
				informarMsg("Conectado");
			}

			private void solicitarNome() {
				nome = JOptionPane.showInputDialog("Digite o nome do jogador");
			}
		};

		ActionListener desconectar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				atorNetGames.desconectar();
				mntmNovaPartida.setEnabled(false);
				mntmDesconectar.setEnabled(false);
				informarMsg("Desconectado");
			}
		};

		ActionListener iniciarPartida = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					atorNetGames.iniciarPartidaRede();
				} catch (NaoConectadoException e1) {
					JOptionPane.showMessageDialog(getFrmAgon(), "Erro ao iniciar partida");
					e1.printStackTrace();
				}
			}
		};

		ActionListener colocarGuerreiro = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (atorNetGames.getEhMinhaVez() && pecinha == null) {
					pecinha = jogo.getGuerreiroDoJogador();
				} else {
					JOptionPane.showMessageDialog(getFrmAgon(), "Aguarde sua vez");
					pecinha = null;
				}
			}
		};

		ActionListener colocarRainha = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (atorNetGames.getEhMinhaVez() && pecinha == null) {
					pecinha = jogo.getRainhaDoJogador();
				} else {
					JOptionPane.showMessageDialog(getFrmAgon(), "Aguarde sua vez");
					pecinha = null;
				}
			}
		};

		mntmConectar.addActionListener(conectar);
		mntmDesconectar.addActionListener(desconectar);
		mntmNovaPartida.addActionListener(iniciarPartida);

		btnColocarGuerreiro_jogador2.addActionListener(colocarGuerreiro);
		btnColocarRainha_jogador2.addActionListener(colocarRainha);

		btnColocarRainha_jogador1.addActionListener(colocarRainha);
		btnColocarGuerreiro_jogador1.addActionListener(colocarGuerreiro);

		// ==========Pra nao ter o trabalho de conecta toda hora==================
		/*
		 * nome = "Edison"; atorNetGames.conectar(nome, "localhost");
		 * mntmNovaPartida.setEnabled(true); mntmDesconectar.setEnabled(true);
		 */
		// ========================================

		return retorno;
	}

	public void informarMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public void iniciarPartidaRede(boolean comecaFalando) {

		String nomeAdversario = atorNetGames.obterNomeAdversario();
		jogo = new Jogo();

		if (comecaFalando) {
			jogo.criarParticipante(this.nome);
			jogo.criarParticipante(nomeAdversario);
			JOptionPane.showMessageDialog(this.getFrmAgon(), "Voce Começa Jogando");
			desabilitaBotoesParaJogador1();
		} else {
			jogo.criarParticipante(nomeAdversario);
			jogo.criarParticipante(this.nome);
			JOptionPane.showMessageDialog(this.getFrmAgon(), "Aguarde a jogada");
			desabilitaBotoesParaJogador2();
		}
		panel_1.setBorder(new TitledBorder(null, jogo.getJogador1().getNome(), TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		panel_2.setBorder(new TitledBorder(null, jogo.getJogador2().getNome(), TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		setQtdPecinhas();
	}

	public void receberMovimentoRede(Movimento movimento) {
		if (movimento.getTipoMovimento().equals("inserir")) {
			movimento.setPaBtn(
					getPosicaoDoTabuleiro(movimento.getPaBtn().getCordenadaX(), movimento.getPaBtn().getCordenadaY()));
			if (movimento.getPecinha() instanceof Guerreiro) {
				movimento.setPecinha(jogo.getGuerreiroDoJogador());
			} else if (movimento.getPecinha() instanceof Rainha) {
				movimento.setPecinha(jogo.getRainhaDoJogador());
			}
		} else if (movimento.getTipoMovimento().equals("mover")) {
			movimento.setDeBtn(
					getPosicaoDoTabuleiro(movimento.getDeBtn().getCordenadaX(), movimento.getDeBtn().getCordenadaY()));
			movimento.setPaBtn(
					getPosicaoDoTabuleiro(movimento.getPaBtn().getCordenadaX(), movimento.getPaBtn().getCordenadaY()));

		}

		jogo.tratarMovimento(movimento);
		jogo.passaVez();
		setQtdPecinhas();

	}

	public Posicao getPosicaoDoTabuleiro(Integer cordenadaX, Integer cordenadaY) {
		for (Posicao pos : getTabuleiro().getPosicoes()) {
			if (pos.getCordenadaX().equals(cordenadaX) && pos.getCordenadaY().equals(cordenadaY)) {
				return pos;
			}
		}
		return null;
	}

}
