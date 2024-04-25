package nivel;

import java.awt.image.BufferedImage;

import principal.Game;
import util.MetodosAuxiliares;

import java.awt.Color;
public class Level {
	
	private int[][] lvlDados;

	private BufferedImage img;


	public Level(int[][] lvlDados) {
		this.lvlDados = lvlDados;
		
	}

	
	public int getSpriteIndex(int x, int y) {
		return lvlDados[y][x];
	}
	public int[][] getLevelDados() {
		return lvlDados;
	}
	

	


}


