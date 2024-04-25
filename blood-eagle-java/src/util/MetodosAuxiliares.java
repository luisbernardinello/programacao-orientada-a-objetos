package util;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Game;

import java.awt.Color;

public class MetodosAuxiliares {

	public static boolean MovimentoPermitido(float x, float y, float comprimento, float altura, int[][] lvlDados) {
		if (!verificaSeSolido(x, y, lvlDados))
			if (!verificaSeSolido(x + comprimento, y + altura, lvlDados))
				if (!verificaSeSolido(x + comprimento, y, lvlDados))
					if (!verificaSeSolido(x, y + altura, lvlDados))
						return true;
		return false;
	}

	private static boolean verificaSeSolido(float x, float y, int[][] lvlDados) {
		if (x < 0 || x >= Game.GAME_COMPRIMENTO)
			return true;
		if (y < 0 || y >= Game.GAME_ALTURA)
			return true;

		float xIndex = x / Game.TILES_TAMANHO;
		float yIndex = y / Game.TILES_TAMANHO;

		int valor = lvlDados[(int) yIndex][(int) xIndex];

		if (valor >= 48 || valor < 0 || valor != 11)
			return true;
		return false;
	}
	
	public static float GetEntidadeXProxCanto(Rectangle2D.Float hitbox, float xVeloc) {
		int tileAtual = (int)(hitbox.x / Game.TILES_TAMANHO);
		
		if( xVeloc > 0) {
			//direita
			int tileXPos = tileAtual * Game.TILES_TAMANHO;
			int xOffset = (int)(Game.TILES_TAMANHO - hitbox.width); //diferenca entre tilesize da box e do jogador
			return tileXPos + xOffset - 1;
		}else {
			//esquerda
			return tileAtual * Game.TILES_TAMANHO;
			
		}
	}
	
	public static float GetEntidadeYDentroLimiteMapa(Rectangle2D.Float hitbox, float arVeloc) {
		int tileAtual = (int)(hitbox.y / Game.TILES_TAMANHO);
		
		if(arVeloc > 0) {
			//caindo e no chao
			int tileYPos = tileAtual * Game.TILES_TAMANHO;
			int yOffset = (int)(Game.TILES_TAMANHO - hitbox.height); //diferenca entre tilesize da box e do jogador
			return tileYPos + yOffset - 1; // se nao tiver -1 podemos estar dentro da tile, bugando
		}else {
			//no ar
			return tileAtual * Game.TILES_TAMANHO;
		}
		
	}
	
	public static boolean EntidadeNoChao(Rectangle2D.Float hitbox, int[][] lvlDados) {
		// verifica o pixel a baixo do canto inferior esquerdo e do canto inferior direito 
		if(!verificaSeSolido(hitbox.x, hitbox.y + hitbox.height+1, lvlDados)) // +1 pixel, se nao estamos na parede
			if(!verificaSeSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height+1, lvlDados))
				return false; // nao esta no chao
		
		return true; // esta no chao
	}
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xVeloc, int[][] lvlDados) {
		if (xVeloc > 0)
			return verificaSeSolido(hitbox.x + hitbox.width + xVeloc, hitbox.y + hitbox.height + 1, lvlDados);
		else
			return verificaSeSolido(hitbox.x + xVeloc, hitbox.y + hitbox.height + 1, lvlDados);
	}

	public static boolean verificChao(Rectangle2D.Float hitbox, int[][] lvlDados) {
		if (!verificaSeSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlDados))
			if (!verificaSeSolido(hitbox.x, hitbox.y + hitbox.height + 1, lvlDados))
				return false;
		return true;
	}

	
	
}