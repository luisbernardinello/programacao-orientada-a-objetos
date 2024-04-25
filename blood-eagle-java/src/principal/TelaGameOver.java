package principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import estadosdojogo.EstadoJogo;
import estadosdojogo.Jogando;
import principal.Game;

public class TelaGameOver {

	private Jogando playing;

	public TelaGameOver(Jogando playing) {
		this.playing = playing;
	}

	public void desenha(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_COMPRIMENTO, Game.GAME_ALTURA);

		g.setColor(Color.white);
		g.drawString("GAME OVER", Game.GAME_COMPRIMENTO / 2, 150);
		g.drawString("APERTE ESC PARA JOGAR NOVAMENTE!", Game.GAME_COMPRIMENTO / 2, 300);

	}

	public void teclaPressionada(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetaTudo();
			EstadoJogo.estado = EstadoJogo.JOGANDO;
		}
	}
}