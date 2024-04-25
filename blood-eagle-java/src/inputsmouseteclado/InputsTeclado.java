package inputsmouseteclado;

import static util.Constantes.Direcao.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entidades.Player;
import entidades.Player2;
import estadosdojogo.EstadoJogo;
import nivel.GerenciaLevel;
import principal.Game;
import principal.GamePainel;



public class InputsTeclado implements KeyListener {

	private GamePainel gamePanel;
	
	public InputsTeclado(GamePainel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (EstadoJogo.estado) {
		case JOGANDO:
			gamePanel.getGame().getJogando().teclaSolta(e);
			break;
		default:
			break;

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (EstadoJogo.estado) {
		case JOGANDO:
			gamePanel.getGame().getJogando().teclaPressionada(e);
			break;
		default:
			break;

		}
	}
}