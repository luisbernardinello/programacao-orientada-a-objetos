package estadosdojogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import entidades.Player;
import entidades.Player2;
import nivel.GerenciaLevel;
import principal.Game;
import principal.NewConnection;
import principal.TelaGameOver;
import principal.TelaLoading;
import principal.TelaPause;
import util.CarregaSprite;







public class Jogando extends Estado implements EstadoMetodosInterface {
	private Player player;
	private Player2 player2;
	private GerenciaLevel gerenciaLevel;
	private TelaPause pauseOverlay;
	private TelaGameOver gameOverOverlay;
	//private LoadingOverlay loadingOverlay;
	//private NewConnection newConnection;
	private boolean pausado = false;
	private boolean isP2InGame;
	

	private int deslocamentoLevelX; //na possibilidade do mapa ser grande, tela se movimentar conforme player prox canto
	private int cantoEsquerdo = (int) (0.25 * Game.GAME_COMPRIMENTO);
	private int cantoDireito = (int) (0.75 * Game.GAME_COMPRIMENTO);
	private int maxLvlOffsetX;

	private BufferedImage fundoImg;
	//private Random rnd = new Random();
	private boolean playerMorrendo;
	private boolean player2Morrendo;
	private boolean gameOver;
	

	

    public Jogando(Player player, Player2 player2, Game game) {
    	
    	
    	
        super(game);
        fundoImg = CarregaSprite.carregaAtlasSprite(CarregaSprite.BACKGROUND);
        this.player = player;
        this.player2 = player2;
        iniciaClass();
        
        
        
    }
	
	private void iniciaClass() {
		gerenciaLevel = new GerenciaLevel(game);
		player = new Player(150, 200, (int) (64 * Game.ESCALA), (int) (40 * Game.ESCALA), this);
		player2 = new Player2(1230, 200, (int) (64 * Game.ESCALA), (int) (40 * Game.ESCALA), this);

		player.carregaDadosLvl(gerenciaLevel.getLevelAtual().getLevelDados());
		player2.carregaDadosLvl(gerenciaLevel.getLevelAtual().getLevelDados());
		

		pauseOverlay = new TelaPause(this);
		gameOverOverlay = new TelaGameOver(this);
		//loadingOverlay = new LoadingOverlay(this);
		
		//newConnection = new NewConnection(player, player2, this);

	}

	@Override
	public void update() {
		if (!pausado && !gameOver) {
			gerenciaLevel.update();
			player.update();
			player2.update();
		//	verificaCantoP1();
		//	verificaCantoP2();
		} else if (playerMorrendo)
			player.update();
		else if (player2Morrendo)
			player2.update();
		else if(pausado)
			pauseOverlay.update();
		//else if(!isP2InGame)
	      // loadingOverlay.update();
	 

	}
	/*// verifica se a tela visivel do mapa esta em tela na possibilidade do mapa ser grande	
		private void verificaCantoP1() {
			int playerX = (int) player.getHitbox().x;
			int diff = playerX - deslocamentoLevelX;

			if (diff > cantoDireito)
				deslocamentoLevelX += diff - cantoDireito;
			else if (diff < cantoEsquerdo)
				deslocamentoLevelX += diff - cantoEsquerdo;

			deslocamentoLevelX = Math.max(Math.min(deslocamentoLevelX, maxLvlOffsetX), 0);
		}
		
		private void verificaCantoP2() {
			int playerY = (int) player2.getHitbox().x;
			int diff = playerY - deslocamentoLevelX;

			if (diff > cantoDireito)
				deslocamentoLevelX += diff - cantoDireito;
			else if (diff < cantoEsquerdo)
				deslocamentoLevelX += diff - cantoEsquerdo;

			deslocamentoLevelX = Math.max(Math.min(deslocamentoLevelX, maxLvlOffsetX), 0);
		}
*/
	
		
	@Override
	public void desenha(Graphics g) {
		g.drawImage(fundoImg, 0, 0, Game.GAME_COMPRIMENTO, Game.GAME_ALTURA, null);



		gerenciaLevel.desenha(g, deslocamentoLevelX);
		player.renderiza(g, deslocamentoLevelX);
		player2.renderiza(g, deslocamentoLevelX);
		
		
		if (pausado) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_COMPRIMENTO, Game.GAME_ALTURA);
			pauseOverlay.desenha(g);
		} else if (gameOver)
			gameOverOverlay.desenha(g);
			//if (!isP2InGame)
				//loadingOverlay.draw(g);
		
	    
		
	}

	public void resetaTudo() {
		gameOver = false;
		pausado = false;
		playerMorrendo = false;
		player2Morrendo = false;
		player.resetaTudo();
		player2.resetaTudo();
		


	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void verificaHitP2(Rectangle2D.Float attackBox) {
	    if (attackBox.intersects(player2.getHitbox())) {
	        player2.apanhou(35);
	        return;
	    }
	}

	public void verificaHitP1(Rectangle2D.Float attackBoxP2) {
	    if (attackBoxP2.intersects(player.getHitbox())) {
	        player.apanhou(35);
	        return;
	    }
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAtacando(true);
	}

	@Override
	public void teclaPressionada(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.teclaPressionada(e);
		else
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setEsquerda(true);
				break;
			case KeyEvent.VK_D:
				player.setDireita(true);
				break;
			case KeyEvent.VK_W:
				player.setSalto(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setAtacando(true);
				break;
			case KeyEvent.VK_ESCAPE:
				pausado = !pausado;
				break;
				
				//p2
			case KeyEvent.VK_NUMPAD4:
				player2.setEsquerda(true);
				break;
			case KeyEvent.VK_NUMPAD6:
				player2.setDireita(true);
				break;
			case KeyEvent.VK_NUMPAD8:
				player2.setSalto(true);
				break;
			case KeyEvent.VK_NUMPAD0:
				player2.setAtacando(true);
				break;
			
			}
		
	
	}

	@Override
	public void teclaSolta(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setEsquerda(false);
				break;
			case KeyEvent.VK_D:
				player.setDireita(false);
				break;
			case KeyEvent.VK_W:
				player.setSalto(false);
				break;
				
				//p2
			case KeyEvent.VK_NUMPAD4:
				player2.setEsquerda(false);
				break;
			case KeyEvent.VK_NUMPAD6:
				player2.setDireita(false);
				break;
			case KeyEvent.VK_NUMPAD8:
				player2.setSalto(false);
				break;
			
			}

	}
		

	@Override
	public void mousePressionado(MouseEvent e) {
	
	}

	@Override
	public void mouseSolto(MouseEvent e) {
	
	}

	@Override
	public void mouseMovimento(MouseEvent e) {
		
	}

	public void despausaJogo() {
		pausado = false;
	}

	public void windowFocusLost() {
		player.resetaDireBool();
		player2.resetDireBool();
	}

	public Player getPlayer() {
		return player;
	}


	
	public Player2 getPlayer2() {
		return player2;
	}
	
	public void setPlayerMorrendo(boolean playerMorrendo) {
		this.playerMorrendo = playerMorrendo;
		
		
	}
	
	public void setPlayer2Morrendo(boolean player2Morrendo) {
		this.player2Morrendo = player2Morrendo;
	}
	

	public GerenciaLevel gerenciaLevel() {
		return gerenciaLevel;
	}

	public boolean getIsP2InGame() {
	return isP2InGame;
	
	}

	 public void setIsP2InGame(boolean isP2InGame) {
	       this.isP2InGame = isP2InGame;
	   }
	
	
	

}