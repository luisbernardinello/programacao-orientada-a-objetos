package principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import estadosdojogo.EstadoJogo;
import estadosdojogo.Jogando;
import principal.Game;
import principal.NewConnection;
//tela de carregando ate o player 2 conectar
public class TelaLoading {
    private Jogando playing;

    public TelaLoading(Jogando playing) {
        this.playing = playing;
    }

    public void desenha(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_COMPRIMENTO, Game.GAME_ALTURA);

        g.setColor(Color.white);
        g.drawString("LOADING", Game.GAME_COMPRIMENTO / 2, 150);
        g.drawString("AGUARDE O PLAYER 2 SE CONECTAR!", Game.GAME_COMPRIMENTO / 2, 300);
    }

    public void update() {
        
    }
}
