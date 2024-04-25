package principal;

import java.awt.Graphics;
import java.awt.Menu;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entidades.Player;
import entidades.Player2;
import estadosdojogo.EstadoJogo;
import estadosdojogo.Jogando;
import estadosdojogo.Jogando.*;
import nivel.GerenciaLevel;
import util.CarregaSprite;

public class Game implements Runnable {

	private GameJanela janelaGame;
	private GamePainel painelGame;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private Player player;
	private Player2 player2;

	private Jogando jogando;
	private Menu menu;

	private boolean pausado = false;

	public final static int TILES_PADRAO = 32;
	public final static float ESCALA = 2f;
	public final static int TILES_COMPRIMENTO = 26;
	public final static int TILES_ALTURA = 14;
	public final static int TILES_TAMANHO = (int) (TILES_PADRAO * ESCALA);
	public final static int GAME_COMPRIMENTO = TILES_TAMANHO * TILES_COMPRIMENTO;
	public final static int GAME_ALTURA = TILES_TAMANHO * TILES_ALTURA;

	
	public Game() {
		iniciaClass();

		painelGame = new GamePainel(this);
		janelaGame = new GameJanela(painelGame);
		painelGame.requestFocus();

		iniciaGameLoop();
		

	}

	private void iniciaClass() {
		
		jogando = new Jogando(player, player2, this);
	}
	

	
	

	private void iniciaGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		switch (EstadoJogo.estado) {	
		case JOGANDO:
			jogando.update();
			break;
		case PAUSE:
		default:
			System.exit(0);
		}
	}

	public void renderiza(Graphics g) {
		switch (EstadoJogo.estado) {
		case PAUSE:
		case JOGANDO:
			jogando.desenha(g);
			break;
		default:
			break;
		}
	}

	@Override//logica de FPS e UPS para devolver o tempo perdido durante o jogo e evitar travamento das sprites
	public void run() {

		double tempoFrame = 1000000000.0 / FPS_SET;
		double tempoUpdate = 1000000000.0 / UPS_SET;

		long tempoAnterior = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long ultimaVerific = System.currentTimeMillis();

		double variacaoUpdate = 0;
		double variacaoFrame = 0;

		while (true) {
			long tempoAtual = System.nanoTime();

			variacaoUpdate += (tempoAtual - tempoAnterior) / tempoUpdate;
			variacaoFrame += (tempoAtual - tempoAnterior) / tempoFrame;
			tempoAnterior = tempoAtual;

			if (variacaoUpdate >= 1) {
				update();
				updates++;
				variacaoUpdate--;
			}

			if (variacaoFrame >= 1) {
				painelGame.repaint();
				frames++;
				variacaoFrame--;
			}

			if (System.currentTimeMillis() - ultimaVerific >= 1000) {
				ultimaVerific = System.currentTimeMillis();
				System.out.println("FPS:    " + frames + " | UPS:    " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}

	public void percaFocoJanela() {
		if (EstadoJogo.estado == EstadoJogo.JOGANDO)
			jogando.getPlayer().resetaDireBool();
			//playing.getPlayer2().resetDirBooleans();
	}

	public Jogando getJogando() {
		return jogando;
	}

}