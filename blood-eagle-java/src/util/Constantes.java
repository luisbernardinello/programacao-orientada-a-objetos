package util;

import principal.Game;

public class Constantes {
	
	
	
	public static final float GRAVIDADE = 0.04f * Game.ESCALA;
	public static final int ANIMA_VELOC = 25;
	
	
	
	public static class Direcao {
		public static final int ESQUERDA = 0;
		public static final int CIMA = 1;
		public static final int DIREITA = 2;
		public static final int BAIXO = 3;
	}

	public static class ConstantesPlayer {
		public static final int PARADO = 0;
		public static final int CORRENDO = 2;
		public static final int ATAQUE_1 = 4;
		public static final int ATAQUE_2 = 6;
		public static final int SALTO = 7;
		public static final int HIT = 8;
		public static final int MORTO = 9;



		

		public static int quantidadeSprites(int player_action) {
			switch (player_action) {
			case PARADO:
				return 6;
			case CORRENDO:
				return 6;
			case ATAQUE_1:
				return 4;
			case ATAQUE_2:
				return 4;
			case SALTO:
				return 5;
			case HIT:
				return 2;
			case MORTO:
				return 4;
			
			default:
				return 1;
			}
		}
		
		/*public static int GetP1Dmg(int player_attack) {
			switch(player_attack) {
			case ATAQUE_1:
				return 60;
				
			case ATAQUE_2:
				return 40;
			
			default:
				return 0;
			}
			
		} */
	}
	
	public static class ConstantesPlayer2 {
		public static final int P2_PARADO = 0;
		public static final int P2_CORRENDO = 2;
		public static final int P2_ATAQUE_1 = 4;
		public static final int P2_ATAQUE_2 = 6;
		public static final int P2_SALTO = 7;
		public static final int P2_HIT = 8;
		public static final int P2_MORTO = 9;


		

		public static int quantidadeSpritesP2(int player_action) {
			switch (player_action) {
			case P2_PARADO:
				return 5;
			case P2_CORRENDO:
				return 6;
			case P2_ATAQUE_1:
				return 3;
			case P2_ATAQUE_2:
				return 4;
			case P2_SALTO:
				return 8;
			case P2_HIT:
				return 2;
			case P2_MORTO:
				return 4;
			
			default:
				return 1;
			}
		}
		
		/*public static int GetP2Dmg(int player_attack) {
			switch(player_attack) {
			case P2_ATAQUE_1:
				return 60;
				
			case P2_ATAQUE_2:
				return 40;
			
			default:
				return 0;
			}
			
		}
	}
	*/
	
	
	}
	
	
	

}