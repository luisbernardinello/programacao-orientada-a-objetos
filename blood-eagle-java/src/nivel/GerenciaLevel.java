package nivel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import estadosdojogo.EstadoJogo;
import principal.Game;
import util.CarregaSprite;

public class GerenciaLevel {

	private Game game;
	private BufferedImage[] spriteDoLevel;


	private Level levelUm;
	private int lvlIndex = 0, animaAtual, animaIndexMax;

	public GerenciaLevel(Game game) {
		this.game = game;
		importaSprites();
		
		levelUm = new Level(CarregaSprite.GetDadoslvl());
		
	}
	

	
	private void importaSprites() {
		BufferedImage img = CarregaSprite.carregaAtlasSprite(CarregaSprite.LEVELDADOSATLAS);
		spriteDoLevel = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				spriteDoLevel[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	
	public void desenha(Graphics g, int deslocamentoLevelX) {
		for (int j = 0; j < Game.TILES_ALTURA; j++)
			for (int i = 0; i < levelUm.getLevelDados()[0].length; i++) {
				int index = levelUm.getSpriteIndex(i, j);
				int x = Game.TILES_TAMANHO * i - deslocamentoLevelX;
				int y = Game.TILES_TAMANHO * j;
				
					g.drawImage(spriteDoLevel[index], x, y, Game.TILES_TAMANHO, Game.TILES_TAMANHO, null);
			}
	}

	public void update() {
		
	
	}



	public Level getLevelAtual() {
		return levelUm;
	}

}