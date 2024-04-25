package estadosdojogo;

import java.awt.event.MouseEvent;

import principal.Game;


public class Estado {

	protected Game game;

	public Estado(Game game) {
		this.game = game;
	}
	
	
	

	public Game getGame() {
		return game;
	}
}