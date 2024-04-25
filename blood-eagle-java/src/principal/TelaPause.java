package principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import estadosdojogo.EstadoJogo;
import estadosdojogo.Jogando;
import principal.Game;

public class TelaPause {

	
	private Jogando playing;
	

	public TelaPause(Jogando playing) {
		this.playing = playing;
	}

	public void update() {

	}
	
	public void desenha(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_COMPRIMENTO, Game.GAME_ALTURA);

		g.setColor(Color.white);
		g.drawString("PAUSE", Game.GAME_COMPRIMENTO / 2, 150);
		g.drawString("APERTE ESC PARA VOLTAR AO JOGO!", Game.GAME_COMPRIMENTO / 2, 300);

	}

	public void teclaPressionada(KeyEvent p) {
		if (p.getKeyCode() == KeyEvent.VK_ESCAPE) {
			EstadoJogo.estado = EstadoJogo.JOGANDO;
		}
	}
}