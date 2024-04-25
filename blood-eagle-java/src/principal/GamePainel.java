package principal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputsmouseteclado.InputsMouse;
import inputsmouseteclado.InputsTeclado;

import static util.Constantes.Direcao.*;
import static principal.Game.GAME_ALTURA;
import static principal.Game.GAME_COMPRIMENTO;
import static util.Constantes.ConstantesPlayer.*;

public class GamePainel extends JPanel {

	private InputsMouse mouseInputs;

	private Game game;
	public GamePainel(Game game) {
		
		mouseInputs = new InputsMouse(this);
		this.game = game;

		setTamanhoPainel();
		addKeyListener(new InputsTeclado(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}



	private void setTamanhoPainel() {
		Dimension size = new Dimension(GAME_COMPRIMENTO, GAME_ALTURA);
		setPreferredSize(size);
	}



	
	public void updateGame() {


	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		game.renderiza(g);

	}
	public Game getGame() {
		return game;
	}
}