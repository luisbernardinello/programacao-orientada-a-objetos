package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import principal.Game;

public class CarregaSprite {
	
	public static final String PLAYERATLAS = "Warrior_1.png"; 
	public static final String PLAYER2ATLAS = "Warrior_2.png"; 
	public static final String LEVELDADOSATLAS = "spriteslevel.png"; 
	public static final String LEVELDADOSRGB = "level.png"; 
	public static final String BACKGROUND = "background.png";
	public static final String BARRADEHP = "barradehp.png";
	
	

	
	public static BufferedImage carregaAtlasSprite(String fileName) {
		BufferedImage img = null;
		InputStream inputStream = CarregaSprite.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(inputStream);
			
			} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return img;
		
		
	}
	
	public static int[][] GetDadoslvl(){
	int[][] lvlDados = new int[Game.TILES_ALTURA][Game.TILES_COMPRIMENTO];
	BufferedImage img = carregaAtlasSprite(LEVELDADOSRGB);
	
	for(int j = 0; j< img.getHeight(); j++)
		for(int i = 0; i < img.getWidth(); i++) {
			Color color = new Color(img.getRGB(i, j));
			int valor = color.getRed();
			if (valor >= 48) 
				valor = 0;
			lvlDados[j][i] = valor;
			
		}
	return lvlDados;
	}
	


	


}
